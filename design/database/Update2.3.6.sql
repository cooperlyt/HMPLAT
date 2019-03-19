ALTER TABLE UPLOAD_FILE MODIFY MD5 varchar(500);

alter table UPLOAD_FILE
  drop column PRI;
