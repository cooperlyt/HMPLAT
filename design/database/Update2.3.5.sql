
USE HOUSE_OWNER_RECORD;
ALTER TABLE PROJECT_SELL_INFO ADD GOV_NAME VARCHAR(32) NOT NULL;

UPDATE PROJECT_SELL_INFO set GOV_NAME='东港市房地产管理处';
UPDATE DB_PLAT_SYSTEM.SYSTEM_PARAM set VALUE='2.3.5' where ID='database_version';