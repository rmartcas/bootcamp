# CAMBIO DE PRUEBA
# bootcamp
repo owner: 

- Compilación
	- Java 11
	- Maven 3.6.x
- Core
	- Spring boot
	- Lombok
- Seguridad
	- Spring security
	- Hdiv
	- Recaptcha
- Persistencia
	- MyBatis
	- Liquibase
- Test
	- Docker
	- Testcontainers
- Servidor embebido
	- Undertow

- Spring boot: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/

El proyecto se compila con maven 3.6.x con el comando: *mvn clean install -P{perfil}*

Perfiles:
- ibNoProductivo
- ibProductivo


A continuación se describen las distintas configuraciones necesarias en la aplicación.

Para el correcto funcionamiento del proyecto se deben instalar las librerías de lombok en eclipse. https://projectlombok.org/setup/eclipse

Para poder ejecutar los test de tipo "IT" se necesita una instancia de docker en el equipo. https://www.docker.com/products/docker-desktop

Es recomendable disponer del plugin spring boot dashboard, aunque no es obligatorio para el funcionamiento de la aplicación.

- Variables obligatorias
	- Datasource
		- **DATASOURCE_URL**: url de conexión a la bbdd
		- **DATASOURCE_USR**: usuario de conexión a la bbdd
		- **DATASOURCE_PWD**: password de acceso a la bbdd
	- SSO
		- **CONFIG_SAML_IDP_PUBLIC_LOGOUT_URL**: Url para cerrar sesión en adfs, por ejemplo: https://idp.ssocircle.com:443/sso/IDPSloRedirect/metaAlias/publicidp
		- **CONFIG_SAML_IDP_METADATA_URL**: Url donde se ubica el archivo de metadata para configurar la conexión SSO al IDP.
- Variables opcionales
	- Recaptcha
		- **CONFIG_RECAPTCHA_ENABLED**: Activa o desactiva el uso de recatpcha. Por defecto 'false'.
		- **CONFIG_RECAPTCHA_HEADER_NAME**: Header donde se recibe la respuesta del recaptcha. Por defecto 'g-recaptcha-response'.
		- **CONFIG_RECAPTCHA_KEY_SITE**: Clave del sitio. Obligatorio si CONFIG_RECAPTCHA_ENABLED = true.
		- **CONFIG_RECAPTCHA_KEY_SECRET**: Clave secreta. Obligatorio si CONFIG_RECAPTCHA_ENABLED = true.
		- **CONFIG_RECAPTCHA_VERIFY_URL**: Url de verificación del recaptcha. Por defecto 'https://www.google.com/recaptcha/api/siteverify'.
		- **CONFIG_RECAPTCHA_CONNECTION_TIMEOUT**: Tiempo máximo de de espera para establecer la conexión para validar el captcha. Por defecto 4000 ms
		- **CONFIG_RECAPTCHA_RECEIVE_TIMEOUT**: Tiempo máximo de espera para recibir la respuesta de validación. Por defecto 4000 ms.
		- **CONFIG_RECAPTCHA_GLOBAL_SCORE**: Umbral global que todas las peticiones validadas por recaptcha deben superar. Por defecto 0.5
	- Server
		- **SERVER_PORT**: Por defecto 8080
		- **SERVER_SSL_ENABLED**: Habilita o deshabilita el protocolo https en la aplicación. Por defecto false.
		- **SERVER_SSL_KEY_STORE**: Ruta al key store con los certificados para servir contenido https. Por defecto vacío.
		- **SERVER_SSL_KEY_STORE_PASSWORD**: Clave de acceso al keystore. Por defecto vacío.
		- **SERVER_SSL_KEY_PASSWORD**: Clave de acceso al certificado que servirá el https. Por defecto vacío.

La creación de módulos basados en CRUD permite de una manera rápida la creación de módulos administrables con un mínimo esfuerzo.

Para la creación de un nuevo módulo CRUD se seguirán los siguientes pasos:
1. Declaramos un nuevo paquete para el módulo, ej: com.nttdata.bootcamp.nuevoModuloCrud
2. Creamos el paquete model com.nttdata.bootcamp.nuevoModuloCrud.model
	1. Añadimos una clase Java que extenderá de Core identificando el tipo de dato que hará de identificador (String, Long...)
	```java
		@Getter
		@Setter
		@EqualsAndHashCode(callSuper = true)
		@NoArgsConstructor
		public class MyModel extends Core<Long> {
			...
		}
	```
	2. Añadimos una clase Java que extenderá de Page identificando el tipo de dato que manejará. Deberá ser del tipo creado en el punto anterior.
	```java
		@Getter
		@Setter
		@EqualsAndHashCode(callSuper = true)
		@NoArgsConstructor
		public class MyModelPage extends Page<MyModel> {

			/** serialVersionUID */
			private static final long serialVersionUID = 1L;
		}
	```
3. Creamos el paquete mapper com.nttdata.bootcamp.nuevoModuloCrud.mapper
	1. Añadimos una interfaz que extenderá de CrudMapper identificando el tipo de dato que manejará. Deberá ser del tipo creado en el punto 2.1
	```java
		@Mapper
		public interface MyModelMapper extends CrudMapper<MyModel> {

		}
	```
	2. Bajo el directorio src/main/resources creamos una jerarquía de carpetas identica a la del mapper: com.nttdata.bootcamp.nuevoModuloCrud.mapper
		donde ubicaremos los xml de mybatis con las consultas necesarias para el nuevo módulo.
		
4. Creamos el paquete service com.nttdata.bootcamp.nuevoModuloCrud.service
	1. Añadimos una interfaz que extenderá de CrudService identificando el tipo de dato que manejará. Deberá ser del tipo creado en el punto 2.1
	```java
		public interface MyModelService extends CrudService<MyModel> {

		}
	```
	2. Creamos el paquete impl dentro del service y declaramos una clase que implementará la interfaz creada en el punto anterior.
	```java
		@Service
		@Slf4j
		public class MyModelServiceImpl implements MyModelService {

		}
	```
5. Creamos el paquete web com.nttdata.bootcamp.nuevoModuloCrud.web
	1. Añadimos una clase que implementará la interfaz CrudController que identificará los tipos creados en los puntos 2.1 y 2.2.
	```java
		@RestController
		@RequestMapping("/myModel")
		class MyModelController implements CrudController<MyModel, MyModelPage> {

		}
	```






- Gestión de perfiles
- Gestión de permisos
- Gestión de usuarios
- Gestión de rutas
- Gestión de menús (TODO)