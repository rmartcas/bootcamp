/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.audit.aop;

import java.util.Calendar;
import java.util.UUID;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nttdata.core.audit.annotation.Auditable;
import com.nttdata.core.audit.AuditableStepsEnum;
import com.nttdata.core.audit.model.Audit;
import com.nttdata.core.audit.service.AuditableService;
import com.nttdata.core.common.model.Core;

import lombok.extern.slf4j.Slf4j;

/**
 * Auditable interceptor for all methods annotated with {@link Auditable}
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
@Slf4j
public class AuditableInterceptor implements MethodInterceptor {
	
	/** The auditable service */
	@Autowired
	private AuditableService auditableService;
	
	/**
	 * Create a new audit record with previous data before call the annotated method 
	 * and a second record with the data after call the annotated method.
	 * 
	 * @param invocation MethodInvocation with interceptor data
	 * @return Object intercepted method result
	 * @throws Throwable if any error
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Audit auditDto = null;
		try {
			Auditable auditable = AnnotationUtils.findAnnotation(invocation.getMethod(), Auditable.class);
			
			Core<?> data = getData(invocation.getArguments(), auditable);
			auditDto = getAudit(auditable, data);
			
			//Saves the audit record before the action
			auditableService.insert(auditDto);
		} catch (Exception e) {
			log.error("Error ocurred before audit.", e);
		}

		Object result = invocation.proceed();
		
		try {
			if (null != auditDto) {
				//Update the record inside auditDto
				if (result instanceof Core<?>) {
					auditDto.setData((Core<?>) result);
				}
				
				//Set the next step to perform
				auditDto.setStep(AuditableStepsEnum.AFTER);
				
				//Saves the audit record after the action
				auditableService.insert(auditDto);
			}	
		} catch (Exception e) {
			log.error("Error ocurred after audit.", e);
		}

	    return result;
	}
	
	/**
	 * Get the parameter at specified index.<br>
	 * If null or invalid iterates all parameters to find the object that extends {@link Core}.
	 * 
	 * @param args The arguments of the intercepted method.
	 * @param index {@link Byte} the index where the object should be.
	 * @return {@link Core} the record to audit or null if not found
	 */
	private Core<?> getData(Object[] args, Auditable auditable) {
		Core<?> data = null;
		try {
			data = (Core<?>) args[auditable.paramIndex()];
		} catch (Exception e) {
			log.error("The auditable paramIndex is out of bounds or cannot be casted to Core object", e);
			for (Object arg : args) {
				if (arg instanceof Core<?>) {
					data = (Core<?>) arg;
					break;
	            }
	        }
		}
		return data;
	}
		
	private Audit getAudit(Auditable auditable, Core<?> data) {
        String pairKey = String.valueOf(UUID.randomUUID().toString());
        String requestId = MDC.get("requestId");
        
		Audit auditDto = new Audit();
		auditDto.setTable(auditable.table());
		auditDto.setPrimaryKey(auditable.primaryKey());
		auditDto.setAction(auditable.action());
		auditDto.setStep(AuditableStepsEnum.BEFORE);
		auditDto.setPairKey(pairKey);
		auditDto.setRequestId(requestId);
		auditDto.setUser(SecurityContextHolder.getContext().getAuthentication().getName());
		auditDto.setData(data);
		auditDto.setCreated(Calendar.getInstance().getTime());
		
		return auditDto;
	}
}
