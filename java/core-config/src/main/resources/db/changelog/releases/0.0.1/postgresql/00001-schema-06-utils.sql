--liquibase formatted sql

--changeset core-config team:search_request_id_function
--comment: Function to find all records with a request_id in audit tables
CREATE OR REPLACE FUNCTION search_request_id(requestId text, tableSuffix text)
RETURNS table(REQUEST_ID text, PAIR_KEY text, AUDIT_ACTION text, AUDIT_STEP text, AUDIT_USER text, AUDIT_DATE text, AUDIT_TABLE text)
LANGUAGE plpgsql
AS '
declare
	schemaname text;
	tablename text;
begin
  FOR schemaname, tablename IN
      SELECT c.table_schema, c.table_name, c.column_name
      FROM information_schema.columns c
      WHERE c.table_schema = ''public''
        AND lower(substring(c.table_name, length(c.table_name) - 3)) = lower(tableSuffix)
        and c.column_name = ''request_id''
  LOOP
    FOR REQUEST_ID, PAIR_KEY, AUDIT_ACTION, AUDIT_STEP, AUDIT_USER, AUDIT_DATE, AUDIT_TABLE IN
      EXECUTE format(''SELECT REQUEST_ID, PAIR_KEY, AUDIT_ACTION, AUDIT_STEP, AUDIT_USER, AUDIT_DATE, replace(%L, %L, '''''''') as AUDIT_TABLE FROM %I.%I WHERE REQUEST_ID=%L'',
       lower(tablename),
	   lower(tableSuffix),
       schemaname,
       tablename,
       requestId
      )
    LOOP
      RETURN NEXT;
    END LOOP;
 END LOOP;
END;
'
;