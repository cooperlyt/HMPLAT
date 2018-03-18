ALTER TABLE HOUSE_INFO.ATTACH_CORPORATION DROP TAX_LICENSE;
ALTER TABLE HOUSE_INFO.ATTACH_CORPORATION DROP MANAGER;

ALTER TABLE HOUSE_INFO.ATTACH_CORPORATION ADD CREDENTIALS_TYPE VARCHAR(16) NOT NULL;
ALTER TABLE HOUSE_INFO.ATTACH_CORPORATION ADD LEGAL_TYPE VARCHAR(16) NOT NULL;

update HOUSE_INFO.ATTACH_CORPORATION set  CREDENTIALS_TYPE = 'MASTER_ID';
update HOUSE_INFO.ATTACH_CORPORATION set  LEGAL_TYPE  = 'LEGAL_OWNER';