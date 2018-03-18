

ALTER TABLE HOUSE_INFO.PROJECT ADD DATA_SOURCE VARCHAR(20) DEFAULT 'MAPPING' NOT NULL;
UPDATE HOUSE_INFO.PROJECT set DATA_SOURCE = 'MAPPING';

-- 凤城专用
ALTER TABLE HOUSE_INFO.PROJECT ADD BDC_ID VARCHAR(32) NULL;
ALTER TABLE HOUSE_INFO.BUILD ADD BDC_ID VARCHAR(32) NULL;
ALTER TABLE HOUSE_INFO.HOUSE ADD BDC_ID VARCHAR(32) NULL;
ALTER TABLE HOUSE_INFO.HOUSE ADD FLOOR_INDEX INT NULL;

DROP INDEX NAME ON HOUSE_INFO.PROJECT;
DROP INDEX NAME ON HOUSE_INFO.SECTION;
DROP INDEX NAME ON HOUSE_INFO.BUILD;

-- ---------------


UPDATE DB_PLAT_SYSTEM.SYSTEM_PARAM set VALUE='2.1.4' where ID='database_version';