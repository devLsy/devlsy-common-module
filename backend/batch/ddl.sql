--oracle version

--BATCH_JOB_INSTANCE definition

CREATE TABLE "BATCH_JOB_INSTANCE" 
   (   "JOB_INSTANCE_ID" NUMBER(20,0) NOT NULL ENABLE, 
   "VERSION" NUMBER(20,0) DEFAULT NULL, 
   "JOB_NAME" VARCHAR2(100) NOT NULL ENABLE, 
   "JOB_KEY" VARCHAR2(32) NOT NULL ENABLE, 
    PRIMARY KEY ("JOB_INSTANCE_ID")
   ); 


-- BATCH_JOB_EXECUTION definition
CREATE TABLE "BATCH_JOB_EXECUTION" 
   (   "JOB_EXECUTION_ID" NUMBER(20,0) NOT NULL ENABLE, 
   "VERSION" NUMBER(20,0) DEFAULT NULL, 
   "JOB_INSTANCE_ID" NUMBER(20,0) NOT NULL ENABLE, 
   "CREATE_TIME" DATE NOT NULL ENABLE, 
   "START_TIME" DATE DEFAULT NULL, 
   "END_TIME" DATE DEFAULT NULL, 
   "STATUS" VARCHAR2(10) DEFAULT NULL, 
   "EXIT_CODE" VARCHAR2(2500) DEFAULT NULL, 
   "EXIT_MESSAGE" VARCHAR2(2500) DEFAULT NULL, 
   "LAST_UPDATED" DATE DEFAULT NULL, 
   "JOB_CONFIGURATION_LOCATION" VARCHAR2(2500) DEFAULT NULL, 
    PRIMARY KEY ("JOB_EXECUTION_ID")
   ); 

ALTER TABLE "BATCH_JOB_EXECUTION" ADD CONSTRAINT "FK_BATCH_JOB_INSTANCE" FOREIGN KEY ("JOB_INSTANCE_ID")
     REFERENCES "BATCH_JOB_INSTANCE" ("JOB_INSTANCE_ID") ENABLE;


-- BATCH_JOB_EXECUTION_CONTEXT definition
CREATE TABLE "BATCH_JOB_EXECUTION_CONTEXT" 
   (   "JOB_EXECUTION_ID" NUMBER(20,0) NOT NULL ENABLE, 
   "SHORT_CONTEXT" VARCHAR2(2500) NOT NULL ENABLE, 
   "SERIALIZED_CONTEXT" CLOB DEFAULT NULL, 
    PRIMARY KEY ("JOB_EXECUTION_ID")
   ); 

ALTER TABLE "BATCH_JOB_EXECUTION_CONTEXT" ADD CONSTRAINT "FK_BATCH_JOB_EXECUTION" FOREIGN KEY ("JOB_EXECUTION_ID")
     REFERENCES "BATCH_JOB_EXECUTION" ("JOB_EXECUTION_ID") ENABLE;

-- BATCH_JOB_EXECUTION_PARAMS definition
CREATE TABLE "BATCH_JOB_EXECUTION_PARAMS" 
   (   "JOB_EXECUTION_ID" NUMBER(20,0) NOT NULL ENABLE, 
   "TYPE_CD" VARCHAR2(6) NOT NULL ENABLE, 
   "KEY_NAME" VARCHAR2(100) NOT NULL ENABLE, 
   "STRING_VAL" VARCHAR2(250) DEFAULT NULL, 
   "DATE_VAL" DATE DEFAULT NULL, 
   "LONG_VAL" NUMBER(20,0) DEFAULT NULL, 
   "DOUBLE_VAL" FLOAT(126) DEFAULT NULL, 
   "IDENTIFYING" CHAR(1) NOT NULL ENABLE
   ); 

ALTER TABLE "BATCH_JOB_EXECUTION_PARAMS" ADD CONSTRAINT "JOB_EXEC_PARAMS_FK" FOREIGN KEY ("JOB_EXECUTION_ID")
     REFERENCES "BATCH_JOB_EXECUTION" ("JOB_EXECUTION_ID") ENABLE;


-- BATCH_STEP_EXECUTION definition

CREATE TABLE "BATCH_STEP_EXECUTION" 
   (   "STEP_EXECUTION_ID" NUMBER(20,0) NOT NULL ENABLE, 
   "VERSION" NUMBER(20,0) DEFAULT NULL, 
   "STEP_NAME" VARCHAR2(100) NOT NULL ENABLE, 
   "JOB_EXECUTION_ID" NUMBER(20,0) NOT NULL ENABLE, 
   "START_TIME" DATE DEFAULT NULL, 
   "END_TIME" DATE DEFAULT NULL, 
   "STATUS" VARCHAR2(10) DEFAULT NULL, 
   "COMMIT_COUNT" NUMBER(20,0) DEFAULT NULL, 
   "READ_COUNT" NUMBER(20,0) DEFAULT NULL, 
   "FILTER_COUNT" NUMBER(20,0) DEFAULT NULL, 
   "WRITE_COUNT" NUMBER(20,0) DEFAULT NULL, 
   "READ_SKIP_COUNT" NUMBER(20,0) DEFAULT NULL, 
   "WRITE_SKIP_COUNT" NUMBER(20,0) DEFAULT NULL, 
   "PROCESS_SKIP_COUNT" NUMBER(20,0) DEFAULT NULL, 
   "ROLLBACK_COUNT" NUMBER(20,0) DEFAULT NULL, 
   "EXIT_CODE" VARCHAR2(2500) DEFAULT NULL, 
   "EXIT_MESSAGE" VARCHAR2(2500) DEFAULT NULL, 
   "LAST_UPDATED" VARCHAR2(2500) DEFAULT NULL, 
    PRIMARY KEY ("STEP_EXECUTION_ID")
   ); 

ALTER TABLE "BATCH_STEP_EXECUTION" ADD CONSTRAINT "JOB_EXEC_STEP_FK" FOREIGN KEY ("JOB_EXECUTION_ID")
     REFERENCES "BATCH_JOB_EXECUTION" ("JOB_EXECUTION_ID") ENABLE;


-- BATCH_STEP_EXECUTION_CONTEXT definition
CREATE TABLE "BATCH_STEP_EXECUTION_CONTEXT" 
   (   "STEP_EXECUTION_ID" NUMBER(20,0) NOT NULL ENABLE, 
   "SHORT_CONTEXT" VARCHAR2(2500) NOT NULL ENABLE, 
   "SERIALIZED_CONTEXT" CLOB DEFAULT NULL, 
    PRIMARY KEY ("STEP_EXECUTION_ID")
   ); 

ALTER TABLE "BATCH_STEP_EXECUTION_CONTEXT" ADD CONSTRAINT "STEP_EXEC_CTX_FK" FOREIGN KEY ("STEP_EXECUTION_ID")
     REFERENCES "BATCH_STEP_EXECUTION" ("STEP_EXECUTION_ID") ENABLE;