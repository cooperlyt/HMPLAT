SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS HOUSE_INFO.HOUSE_SELL_COMPANY;
DROP TABLE IF EXISTS HOUSE_INFO.ORG_ATTACH_ACTION;
DROP TABLE IF EXISTS HOUSE_INFO.PROJECT_BUILD_PROCESS;
DROP TABLE IF EXISTS HOUSE_INFO.KEY_AND_PROJECT;
DROP TABLE IF EXISTS HOUSE_INFO.HOUSE;
DROP TABLE IF EXISTS HOUSE_INFO.MAP_BIZ_HOUSE;
DROP TABLE IF EXISTS HOUSE_INFO.MAP_BUSINESS;
DROP TABLE IF EXISTS HOUSE_INFO.GRID_BLOCK;
DROP TABLE IF EXISTS HOUSE_INFO.GRID_ROW;
DROP TABLE IF EXISTS HOUSE_INFO.HOUSE_GRID_TITLE;
DROP TABLE IF EXISTS HOUSE_INFO.BUILD_GRID_MAP;
DROP TABLE IF EXISTS HOUSE_INFO.BUILD;
DROP TABLE IF EXISTS HOUSE_INFO.PROJECT;
DROP TABLE IF EXISTS HOUSE_INFO.DEVELOPER;
DROP TABLE IF EXISTS HOUSE_INFO.MAPPING_CORPORATION;
DROP TABLE IF EXISTS HOUSE_INFO.DEVELOPER_ATTACH_LOGON;
DROP TABLE IF EXISTS HOUSE_INFO.EMPLOYEE_ATTACH_ACTION;
DROP TABLE IF EXISTS HOUSE_INFO.ATTACH_EMPLOYEE;
DROP TABLE IF EXISTS HOUSE_INFO.SMSUBCOMPANY;
DROP TABLE IF EXISTS HOUSE_INFO.MCOMPANY;
DROP TABLE IF EXISTS HOUSE_INFO.EVALUATE_CORPORATION;
DROP TABLE IF EXISTS HOUSE_INFO.ATTACH_CORPORATION;
DROP TABLE IF EXISTS HOUSE_INFO.OWNER_GROUP;
DROP TABLE IF EXISTS HOUSE_INFO.POOL_BUILD;
DROP TABLE IF EXISTS HOUSE_INFO.SECTION;
DROP TABLE IF EXISTS HOUSE_INFO.DISTRICT;
DROP TABLE IF EXISTS HOUSE_INFO.MONEY_BANK;
DROP TABLE IF EXISTS HOUSE_INFO.NUMBER_POOL;
DROP TABLE IF EXISTS HOUSE_INFO.PERSONAL_CONTRACT_LOGON;
DROP TABLE IF EXISTS HOUSE_INFO.PERSONAL_CONTRACT_USER;
DROP TABLE IF EXISTS HOUSE_INFO.SELL_AREA;




/* Create Tables */

CREATE TABLE HOUSE_INFO.ATTACH_CORPORATION
(
	ID VARCHAR(32) NOT NULL,
	RECORD_DATE DATETIME NOT NULL,
	TYPE VARCHAR(20) NOT NULL,
	ENABLE BIT(1) NOT NULL,
	DATE_TO DATETIME NOT NULL,
	ADDRESS VARCHAR(100) NOT NULL,
	PHONE VARCHAR(200) NOT NULL,
	OWNER_NAME VARCHAR(100) NOT NULL,
	OWNER_CARD VARCHAR(50),
	FAX VARCHAR(50),
	EMAIL VARCHAR(50),
	MEMO VARCHAR(200),
	POST_CODE VARCHAR(50),
	LICENSE_NUMBER VARCHAR(100) NOT NULL,
	COMPANY_CODE VARCHAR(100),
	COMPANY_TYPE VARCHAR(32),
	REGISTER_MONEY DECIMAL(19,4),
	LEVEL VARCHAR(32),
	COMPANY_CER_CODE VARCHAR(100),
	OWNER_TEL VARCHAR(100) NOT NULL,
	CREDENTIALS_TYPE VARCHAR(16) NOT NULL,
	LEGAL_TYPE VARCHAR(16) NOT NULL,
	CONSTRAINT PK_INTERMEDIARY PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.ATTACH_EMPLOYEE
(
	ID VARCHAR(32) NOT NULL,
	LICENSE_LEVE VARCHAR(32),
	LICENSE_NUMBER VARCHAR(50),
	LICENSE_TIME_TO DATETIME,
	PHONE VARCHAR(20),
	DEUCATION VARCHAR(32),
	NAME VARCHAR(50) NOT NULL,
	CREDENTIALS_TYPE VARCHAR(32),
	CREDENTIALS_NUMBER VARCHAR(100),
	ENABLE BIT(1),
	CREATE_TIME TIMESTAMP,
	CORP VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.BUILD
(
	ID VARCHAR(32) NOT NULL,
	MAP_NUMBER VARCHAR(4),
	BLOCK_NO VARCHAR(10) NOT NULL,
	BUILD_NO VARCHAR(24) NOT NULL,
	STREET_CODE VARCHAR(4),
	NAME VARCHAR(100) NOT NULL UNIQUE,
	COMPLETE_DATE VARCHAR(6),
	PROJECT_ID VARCHAR(32) NOT NULL,
	DOOR_NO VARCHAR(50),
	UNINT_COUNT INT,
	HOUSE_COUNT INT,
	AREA DECIMAL(18,3),
	LNG DECIMAL(18,14),
	LAT DECIMAL(18,14),
	ZOOM INT,
	BUILD_TYPE VARCHAR(32),
	STRUCTURE VARCHAR(32) NOT NULL,
	VERSION INT,
	MEMO VARCHAR(200),
	NEXT_HOUSE_ORDER INT NOT NULL,
	UP_FLOOR_COUNT INT NOT NULL,
	DOWN_FLOOR_COUNT INT NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	DEVELOPER_NUMBER VARCHAR(20),
	MAP_TIME DATETIME NOT NULL,
	MAP_CORP VARCHAR(32),
	STREET VARCHAR(256),
	CONSTRAINT PK_BUILD PRIMARY KEY (ID),
	CONSTRAINT MAP_BLOCK_BUILD_UNIQUE UNIQUE (MAP_NUMBER, BLOCK_NO, BUILD_NO)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.BUILD_GRID_MAP
(
	ID VARCHAR(32) NOT NULL,
	BUILD_ID VARCHAR(32) NOT NULL,
	NAME VARCHAR(50) NOT NULL,
	_ORDER INT NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.DEVELOPER
(
	ID VARCHAR(32) NOT NULL,
	NAME VARCHAR(100) NOT NULL,
	PYCODE VARCHAR(100),
	VERSION INT NOT NULL,
	ATTACH_ID VARCHAR(32),
	DESTROYED BOOLEAN NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	DESCRIPTION VARCHAR(200),
	CONSTRAINT PK_DEVELOPER PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.DEVELOPER_ATTACH_LOGON
(
	ID VARCHAR(32) NOT NULL,
	PASSWORD VARCHAR(32) NOT NULL,
	SESSION_KEY VARCHAR(32),
	USER_KEY VARCHAR(32) NOT NULL,
	EMP VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.DISTRICT
(
	ID VARCHAR(12) NOT NULL,
	NAME VARCHAR(100) NOT NULL UNIQUE,
	SHORT_NAME VARCHAR(10) NOT NULL,
	VERSION INT,
	CREATE_TIME TIMESTAMP NOT NULL,
	CONSTRAINT PK_DISTRICT PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.EMPLOYEE_ATTACH_ACTION
(
	ID VARCHAR(32) NOT NULL,
	TYPE INT NOT NULL,
	CONTEXT VARCHAR(200),
	CREATE_TIME TIMESTAMP,
	COMMIT_PERSION VARCHAR(50),
	ATTACH_EMPLOYEE_ID VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.EVALUATE_CORPORATION
(
	ID VARCHAR(32) NOT NULL,
	NAME VARCHAR(100) NOT NULL,
	PYCODE VARCHAR(100),
	VERSION INT NOT NULL,
	ATTACH_ID VARCHAR(32),
	DESTROYED BOOLEAN NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	CONSTRAINT PK_EVALUATECORPORATION PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.GRID_BLOCK
(
	ID VARCHAR(32) NOT NULL,
	ROW_ID VARCHAR(32) NOT NULL,
	_ORDER INT NOT NULL,
	COLSPAN INT NOT NULL,
	ROWSPAN INT NOT NULL,
	AREA DECIMAL(18,3),
	USE_AREA DECIMAL(18,3),
	COMM_AREA DECIMAL(18,3),
	SHINE_AREA DECIMAL(18,3),
	LOFT_AREA DECIMAL(18,3),
	COMM_PARAM DECIMAL(18,3),
	USE_TYPE VARCHAR(32),
	STRUCTURE VARCHAR(32),
	HOUSE_TYPE VARCHAR(32),
	UNIT_INDEX INT NOT NULL,
	UNIT_NAME VARCHAR(32) NOT NULL,
	HOUSE_ORDER VARCHAR(20),
	DIRECTION VARCHAR(32),
	EAST_WALL VARCHAR(32),
	WEST_WALL VARCHAR(32),
	SOUTH_WALL VARCHAR(32),
	NORTH_WALL VARCHAR(32),
	KNOT_SIZE VARCHAR(32),
	HAVE_DOWN_ROOM BOOLEAN NOT NULL,
	HOUSE_CODE VARCHAR(32),
	FLOOR_NAME VARCHAR(32),
	DESIGN_USE_TYPE VARCHAR(512),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.GRID_ROW
(
	ID VARCHAR(32) NOT NULL,
	TITLE VARCHAR(50) NOT NULL,
	_ORDER INT NOT NULL,
	GRID_ID VARCHAR(32) NOT NULL,
	FLOOR_INDEX INT NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.HOUSE
(
	ID VARCHAR(32) NOT NULL,
	BUILDID VARCHAR(32) NOT NULL,
	HOUSE_ORDER VARCHAR(20) NOT NULL,
	HOUSE_UNIT_NAME VARCHAR(20),
	IN_FLOOR_NAME VARCHAR(50) NOT NULL,
	HOUSE_AREA DECIMAL(18,3) NOT NULL,
	USE_AREA DECIMAL(18,3),
	COMM_AREA DECIMAL(18,3),
	SHINE_AREA DECIMAL(18,10),
	LOFT_AREA DECIMAL(18,3),
	COMM_PARAM DECIMAL(18,10),
	HOUSE_TYPE VARCHAR(32),
	USE_TYPE VARCHAR(32) NOT NULL,
	STRUCTURE VARCHAR(32) NOT NULL,
	KNOT_SIZE VARCHAR(32),
	ADDRESS VARCHAR(200) NOT NULL,
	DATA_SOURCE VARCHAR(32) NOT NULL,
	EAST_WALL VARCHAR(32),
	WEST_WALL VARCHAR(32),
	SOUTH_WALL VARCHAR(32),
	NORTH_WALL VARCHAR(32),
	DIRECTION VARCHAR(32),
	VERSION INT,
	MEMO VARCHAR(200),
	HAVE_DOWN_ROOM BOOLEAN NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	DELETED BOOLEAN NOT NULL,
	UNIT_NUMBER VARCHAR(32),
	DESIGN_USE_TYPE VARCHAR(512),
	FLOOR_INDEX INT,
	CONSTRAINT PK_HOUSE PRIMARY KEY (ID),
	CONSTRAINT HOUSE_ORDER_BUILD_UNIQUE UNIQUE (BUILDID, HOUSE_ORDER)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.HOUSE_GRID_TITLE
(
	ID VARCHAR(32) NOT NULL,
	_ORDER INT NOT NULL,
	TITLE VARCHAR(32) NOT NULL,
	COLSPAN INT NOT NULL,
	GRILD_ID VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.HOUSE_SELL_COMPANY
(
	ID VARCHAR(32) NOT NULL,
	NAME VARCHAR(100) NOT NULL,
	VERSION INT NOT NULL,
	ATTACH_ID VARCHAR(32),
	DESTROYED BOOLEAN NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	TEL VARCHAR(16) NOT NULL,
	SALE_TEL VARCHAR(16),
	CONTROL BOOLEAN NOT NULL,
	CONSTRAINT PK_INTERMEDIARY PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.KEY_AND_PROJECT
(
	PROJECT VARCHAR(32) NOT NULL,
	DEVELOPER_KEY VARCHAR(32) NOT NULL,
	PRIMARY KEY (PROJECT, DEVELOPER_KEY)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.MAPPING_CORPORATION
(
	ID VARCHAR(32) NOT NULL,
	NAME VARCHAR(100) NOT NULL,
	PYCODE VARCHAR(100),
	VERSION INT NOT NULL,
	ATTACH_ID VARCHAR(32),
	DESTROYED BOOLEAN NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	CONSTRAINT PK_MAPPINGCORPORATION PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.MAP_BIZ_HOUSE
(
	ID VARCHAR(32) NOT NULL,
	HOUSE_ORDER VARCHAR(20) NOT NULL,
	HOUSE_CODE VARCHAR(32) NOT NULL,
	UNIT_NUMBER VARCHAR(32) NOT NULL,
	-- ADD
	-- DEL
	-- CHANGE
	TYPE VARCHAR(20) NOT NULL COMMENT 'ADD
DEL
CHANGE',
	DATA LONGTEXT,
	BUSINESSS VARCHAR(32) NOT NULL,
	BEFOR_DATA LONGTEXT,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.MAP_BUSINESS
(
	ID VARCHAR(32) NOT NULL,
	BUILD VARCHAR(32) NOT NULL,
	DATA LONGTEXT NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.MCOMPANY
(
	ID VARCHAR(32) NOT NULL,
	NAME VARCHAR(50) NOT NULL,
	PYCODE VARCHAR(50),
	VERSION INT NOT NULL,
	ATTACH_ID VARCHAR(32),
	DESTROYED BOOLEAN NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	CONSTRAINT PK_MCOMPANY PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.MONEY_BANK
(
	ID VARCHAR(32) NOT NULL,
	-- 监管金融机构名称
	NAME VARCHAR(50) NOT NULL COMMENT '监管金融机构名称',
	KEYT VARCHAR(16) NOT NULL,
	SECRET_TYPE VARCHAR(16) NOT NULL,
	PRI INT NOT NULL,
	-- 监管机构的监管账户
	ACCOUNT_NUMBER VARCHAR(32) NOT NULL COMMENT '监管机构的监管账户',
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.NUMBER_POOL
(
	ID VARCHAR(32) NOT NULL,
	NUMBER BIGINT UNSIGNED NOT NULL,
	VERSION INT,
	TYPE VARCHAR(20) NOT NULL,
	USE_TIME TIMESTAMP NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.ORG_ATTACH_ACTION
(
	ID VARCHAR(32) NOT NULL,
	TYPE INT NOT NULL,
	CONTEXT VARCHAR(200),
	CREATE_TIME TIMESTAMP,
	COMMIT_PERSION VARCHAR(50),
	CORPORATION_ID VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.OWNER_GROUP
(
	ID VARCHAR(32) NOT NULL,
	SECTION VARCHAR(32) NOT NULL,
	NUMBER VARCHAR(50) NOT NULL UNIQUE,
	NAME VARCHAR(50) NOT NULL,
	PHONE VARCHAR(50),
	ADDRESS VARCHAR(200),
	REGISTER_TIME TIMESTAMP,
	MANAGER VARCHAR(50),
	TOTALCOUNT INT,
	MEMO VARCHAR(200),
	CREATE_TIME TIMESTAMP NOT NULL,
	CONSTRAINT PK_OWNERGROUP PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.PERSONAL_CONTRACT_LOGON
(
	ID VARCHAR(32) NOT NULL,
	HOUSE_CODE VARCHAR(32) NOT NULL,
	PASSWORD VARCHAR(32) NOT NULL,
	USER_KEY VARCHAR(32) NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	BUSINESS_ID VARCHAR(32) NOT NULL,
	DATE_TO DATETIME,
	ENABLE BIT(1) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.PERSONAL_CONTRACT_USER
(
	IDNO VARCHAR(50) NOT NULL,
	NAME VARCHAR(50) NOT NULL,
	PASSWORDWORD VARCHAR(20) NOT NULL,
	PHONE VARCHAR(13) NOT NULL,
	PRIMARY KEY (IDNO)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.POOL_BUILD
(
	ID VARCHAR(32) NOT NULL,
	MAP_NUMBER VARCHAR(50) NOT NULL,
	BLOCK_NO VARCHAR(10) NOT NULL,
	BUILD_NO VARCHAR(24) NOT NULL,
	HOUSE_NUMBER VARCHAR(50) NOT NULL,
	BUILD_NAME VARCHAR(50) NOT NULL,
	STRUCTURE VARCHAR(32) NOT NULL,
	ADDRESS VARCHAR(200),
	AREA DECIMAL(18,3) NOT NULL,
	MEMO VARCHAR(200),
	REG_TIME DATETIME,
	FLOOR_COUNT INT NOT NULL,
	SECTION_ID VARCHAR(32) NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	CONSTRAINT PK_POOLBUILD PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.PROJECT
(
	ID VARCHAR(32) NOT NULL,
	NAME VARCHAR(50) NOT NULL UNIQUE,
	SECTIONID VARCHAR(32) NOT NULL,
	DEVELOPERID VARCHAR(32),
	ADDRESS VARCHAR(200),
	SUM_AREA DECIMAL(18,3),
	MEMO VARCHAR(200),
	VERSION INT,
	CREATE_TIME TIMESTAMP NOT NULL,
	DATA_SOURCE VARCHAR(20) DEFAULT 'MAPPING' NOT NULL,
	ENABLE BOOLEAN NOT NULL,
	CONSTRAINT PK_PROJECT PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.PROJECT_BUILD_PROCESS
(
	ID VARCHAR(32) NOT NULL,
	PROJECT_ID VARCHAR(32),
	PROCESS_TIME TIMESTAMP,
	MONEY DECIMAL(18,3),
	MEMO VARCHAR(200),
	CONSTRAINT PK_PROJECTCREATEPROCESS PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.SECTION
(
	ID VARCHAR(32) NOT NULL,
	NAME VARCHAR(50) NOT NULL UNIQUE,
	PYCODE VARCHAR(50),
	ADDRESS VARCHAR(200),
	VERSION INT,
	CREATE_TIME TIMESTAMP NOT NULL,
	DISTRICT VARCHAR(12) NOT NULL,
	CONSTRAINT PK_SECTION PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_INFO.SELL_AREA
(
	ID VARCHAR(32) NOT NULL,
	NAME VARCHAR(64) NOT NULL,
	TYPE VARCHAR(16) NOT NULL,
	PRI INT NOT NULL,
	DISTRICT VARCHAR(32) NOT NULL,
	ENABLE BOOLEAN NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


-- 一个物业公司可能管理多个小区
CREATE TABLE HOUSE_INFO.SMSUBCOMPANY
(
	ID VARCHAR(32) NOT NULL,
	MCOMPANY VARCHAR(32),
	SECTION VARCHAR(32) NOT NULL,
	NAME VARCHAR(50),
	ADDRESS VARCHAR(100),
	MANAGER VARCHAR(50),
	PHONE VARCHAR(50),
	REGISTER_TIME TIMESTAMP,
	MEMO VARCHAR(200),
	DESTROYED BOOLEAN NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	CONSTRAINT PK_SMSUBCOMPANY PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '一个物业公司可能管理多个小区' DEFAULT CHARACTER SET utf8;



/* Create Foreign Keys */

ALTER TABLE HOUSE_INFO.HOUSE_SELL_COMPANY
	ADD FOREIGN KEY (ATTACH_ID)
	REFERENCES HOUSE_INFO.ATTACH_CORPORATION (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.ORG_ATTACH_ACTION
	ADD FOREIGN KEY (CORPORATION_ID)
	REFERENCES HOUSE_INFO.ATTACH_CORPORATION (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.DEVELOPER
	ADD FOREIGN KEY (ATTACH_ID)
	REFERENCES HOUSE_INFO.ATTACH_CORPORATION (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.MAPPING_CORPORATION
	ADD FOREIGN KEY (ATTACH_ID)
	REFERENCES HOUSE_INFO.ATTACH_CORPORATION (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.ATTACH_EMPLOYEE
	ADD FOREIGN KEY (CORP)
	REFERENCES HOUSE_INFO.ATTACH_CORPORATION (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.MCOMPANY
	ADD FOREIGN KEY (ATTACH_ID)
	REFERENCES HOUSE_INFO.ATTACH_CORPORATION (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.EVALUATE_CORPORATION
	ADD FOREIGN KEY (ATTACH_ID)
	REFERENCES HOUSE_INFO.ATTACH_CORPORATION (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.DEVELOPER_ATTACH_LOGON
	ADD FOREIGN KEY (EMP)
	REFERENCES HOUSE_INFO.ATTACH_EMPLOYEE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.EMPLOYEE_ATTACH_ACTION
	ADD FOREIGN KEY (ATTACH_EMPLOYEE_ID)
	REFERENCES HOUSE_INFO.ATTACH_EMPLOYEE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.HOUSE
	ADD CONSTRAINT FK_HOUSE_HOUSEANDB_BUILD FOREIGN KEY (BUILDID)
	REFERENCES HOUSE_INFO.BUILD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.MAP_BUSINESS
	ADD FOREIGN KEY (BUILD)
	REFERENCES HOUSE_INFO.BUILD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.BUILD_GRID_MAP
	ADD FOREIGN KEY (BUILD_ID)
	REFERENCES HOUSE_INFO.BUILD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.GRID_ROW
	ADD FOREIGN KEY (GRID_ID)
	REFERENCES HOUSE_INFO.BUILD_GRID_MAP (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.HOUSE_GRID_TITLE
	ADD FOREIGN KEY (GRILD_ID)
	REFERENCES HOUSE_INFO.BUILD_GRID_MAP (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.PROJECT
	ADD CONSTRAINT FK_PROJECT_REFERENCE_DEVELOPE FOREIGN KEY (DEVELOPERID)
	REFERENCES HOUSE_INFO.DEVELOPER (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.KEY_AND_PROJECT
	ADD FOREIGN KEY (DEVELOPER_KEY)
	REFERENCES HOUSE_INFO.DEVELOPER_ATTACH_LOGON (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.SECTION
	ADD CONSTRAINT FK_SECTION_REFERENCE_DISTRICT FOREIGN KEY (DISTRICT)
	REFERENCES HOUSE_INFO.DISTRICT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.GRID_BLOCK
	ADD FOREIGN KEY (ROW_ID)
	REFERENCES HOUSE_INFO.GRID_ROW (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.BUILD
	ADD FOREIGN KEY (MAP_CORP)
	REFERENCES HOUSE_INFO.MAPPING_CORPORATION (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.MAP_BIZ_HOUSE
	ADD FOREIGN KEY (BUSINESSS)
	REFERENCES HOUSE_INFO.MAP_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.SMSUBCOMPANY
	ADD CONSTRAINT FK_SMSUBCOM_REFERENCE_MCOMPANY FOREIGN KEY (MCOMPANY)
	REFERENCES HOUSE_INFO.MCOMPANY (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.PROJECT_BUILD_PROCESS
	ADD CONSTRAINT FK_PROJECTC_REFERENCE_PROJECT FOREIGN KEY (PROJECT_ID)
	REFERENCES HOUSE_INFO.PROJECT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.KEY_AND_PROJECT
	ADD FOREIGN KEY (PROJECT)
	REFERENCES HOUSE_INFO.PROJECT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.BUILD
	ADD CONSTRAINT FK_BUILD_REFERENCE_PROJECT FOREIGN KEY (PROJECT_ID)
	REFERENCES HOUSE_INFO.PROJECT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.SMSUBCOMPANY
	ADD CONSTRAINT FK_SMSUBCOM_REFERENCE_SECTION FOREIGN KEY (SECTION)
	REFERENCES HOUSE_INFO.SECTION (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.OWNER_GROUP
	ADD CONSTRAINT FK_OWNERGRO_REFERENCE_SECTION FOREIGN KEY (SECTION)
	REFERENCES HOUSE_INFO.SECTION (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.PROJECT
	ADD CONSTRAINT FK_PROJECT_REFERENCE_SECTION FOREIGN KEY (SECTIONID)
	REFERENCES HOUSE_INFO.SECTION (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_INFO.POOL_BUILD
	ADD FOREIGN KEY (SECTION_ID)
	REFERENCES HOUSE_INFO.SECTION (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



