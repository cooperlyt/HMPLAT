SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS CONSTRUCT_CORP_REG;
DROP TABLE IF EXISTS CONSTRUCT_EMPLOYEE;
DROP TABLE IF EXISTS LOGIN;
DROP TABLE IF EXISTS CORP_INFO;
DROP TABLE IF EXISTS DFCI;
DROP TABLE IF EXISTS DFCC;
DROP TABLE IF EXISTS DFCT;
DROP TABLE IF EXISTS FIRE_CHECK_SUB_ITEM;
DROP TABLE IF EXISTS FIRE_CHECK_ITEM;
DROP TABLE IF EXISTS FIRE_JOIN_CORP;
DROP TABLE IF EXISTS FIRE_CHECK;
DROP TABLE IF EXISTS FIRE_CHECK_COLLECT;
DROP TABLE IF EXISTS JOIN_PERSON;
DROP TABLE IF EXISTS JOIN_CORP;
DROP TABLE IF EXISTS PROJECT_INFO;




/* Create Tables */

CREATE TABLE CONSTRUCT_CORP_REG
(
	CORP_CODE varchar(32) NOT NULL,
	CORP_TYPE varchar(16) NOT NULL,
	REG_DATE date,
	REG_DATE_TO date,
	LEVEL varchar(8) NOT NULL,
	LEVEL_NUMBER varchar(32) NOT NULL,
	STATUS varchar(16) NOT NULL,
	PRIMARY KEY (CORP_CODE, CORP_TYPE)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONSTRUCT_EMPLOYEE
(
	ID bigint NOT NULL,
	REG_NUMBER varchar(32),
	REG_RIGHT bigint,
	IDENTIFY_TYPE varchar(16) NOT NULL,
	IDENTIFY_NUMBER varchar(32) NOT NULL,
	NAME varchar(64) NOT NULL,
	CORP_CODE varchar(32) NOT NULL,
	TEL varchar(16),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CORP_INFO
(
	CORP_CODE varchar(32) NOT NULL,
	NAME varchar(128) NOT NULL,
	REG_ID_TYPE varchar(16) NOT NULL,
	REG_ID_NUMBER varchar(32) NOT NULL,
	OWNER_NAME varchar(64) NOT NULL,
	OWNER_ID_TYPE varchar(16) NOT NULL,
	OWNER_ID_NUMBER varchar(32) NOT NULL,
	ADDRESS varchar(256),
	TEL varchar(16),
	PRIMARY KEY (CORP_CODE)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE DFCC
(
	ID bigint NOT NULL,
	NAME varchar(32) NOT NULL,
	_ORDER int NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE DFCI
(
	ID bigint NOT NULL,
	CHECK_ID bigint NOT NULL,
	COLLECT_ID bigint NOT NULL,
	_ORDER int NOT NULL,
	NAME varchar(32) NOT NULL,
	CONTENT varchar(256),
	_REQUIRE varchar(256) NOT NULL,
	LEVEL char NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE DFCT
(
	ID bigint NOT NULL,
	_ORDER int NOT NULL,
	NAME varchar(32) NOT NULL,
	FULL boolean NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE FIRE_CHECK
(
	ID bigint NOT NULL,
	CHECK_DATE date NOT NULL,
	PROJECT_CODE varchar(32) NOT NULL,
	NAME varchar(256) NOT NULL,
	ADDRESS varchar(512),
	FILE_NUMBER varchar(32),
	CONTACTS varchar(64),
	TEL varchar(16),
	TYPE varchar(8) NOT NULL,
	PROPERTY varchar(16) NOT NULL,
	DANGER varchar(16),
	CONTRACT_AREA decimal(19,2),
	ALL_AREA decimal(19,2),
	HEIGHT decimal(19,2),
	GROUND_FLOOR_COUNT int,
	UNDER_FLOOR_COUNT int,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE FIRE_CHECK_COLLECT
(
	ID bigint NOT NULL,
	_ORDER int NOT NULL,
	NAME varchar(32) NOT NULL,
	FULL boolean NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE FIRE_CHECK_ITEM
(
	ID bigint NOT NULL,
	NAME varchar(32) NOT NULL,
	_ORDER int NOT NULL,
	CONCLUSION varchar(16),
	CHECK_ID bigint NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE FIRE_CHECK_SUB_ITEM
(
	ID bigint NOT NULL,
	ITEM_ID bigint NOT NULL,
	COLLECT_ID bigint NOT NULL,
	_ORDER int NOT NULL,
	NAME varchar(32) NOT NULL,
	CONTENT varchar(256),
	_REQUIRE varchar(256) NOT NULL,
	LEVEL char NOT NULL,
	PARTS varchar(16),
	COUNT int,
	DESCRIPTION varchar(16),
	STANDARD boolean NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE FIRE_JOIN_CORP
(
	ID bigint NOT NULL,
	CHECK_ID bigint NOT NULL,
	CORP_TYPE varchar(16) NOT NULL,
	LEVEL varchar(8) NOT NULL,
	CORP_CODE varchar(32) NOT NULL,
	NAME varchar(128) NOT NULL,
	REG_ID_TYPE varchar(16) NOT NULL,
	REG_ID_NUMBER varchar(32) NOT NULL,
	CONTACTS varchar(64),
	TEL varchar(16),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE JOIN_CORP
(
	ID bigint NOT NULL,
	OUTSIDE_TEAM_FILE varchar(32),
	OUT_LEVEL boolean NOT NULL,
	OUT_LEVEL_FILE varchar(32),
	CORP_CODE varchar(32) NOT NULL,
	CORP_TYPE varchar(16) NOT NULL,
	NAME varchar(128) NOT NULL,
	REG_ID_TYPE varchar(16) NOT NULL,
	REG_ID_NUMBER varchar(32) NOT NULL,
	LEVEL varchar(8) NOT NULL,
	PROJECT_ID bigint NOT NULL,
	CONTACTS varchar(64),
	TEL varchar(16),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE JOIN_PERSON
(
	ID bigint NOT NULL,
	CORP bigint NOT NULL,
	JOIN_TYPE varchar(16) NOT NULL,
	LEVEL varchar(8),
	JOB varchar(32) NOT NULL,
	NAME varchar(64) NOT NULL,
	IDENTIFY_TYPE varchar(16) NOT NULL,
	IDENTIFY_NUMBER varchar(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE LOGIN
(
	CORP_CODE varchar(32) NOT NULL,
	ROLE varchar(16) NOT NULL,
	IDENTIFY_TYPE varchar(16) NOT NULL,
	IDENTIFY_NUMBER varchar(32) NOT NULL,
	NAME varchar(64) NOT NULL,
	PASSWORD varchar(32),
	LOGIN_ID varchar(32) NOT NULL,
	PRIMARY KEY (LOGIN_ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE PROJECT_INFO
(
	ID bigint NOT NULL,
	PROJECT_CODE varchar(32) NOT NULL,
	NAME varchar(256) NOT NULL,
	ADDRESS varchar(512),
	TYPE varchar(32),
	PROPERTY varchar(32),
	CONTRACT_AREA decimal(19,2),
	ALL_AREA decimal(19,2),
	GROUND_FLOOR_COUNT int,
	UNDER_FLOOR_COUNT int,
	BEGIN_DATE date,
	COMPLETED_DATE date,
	TENDER varchar(32),
	STRUCTURE varchar(32),
	COSTS decimal(18,2),
	MAIN_PROJECT boolean,
	MAIN_PROJECT_LEVEL varchar(16),
	MAIN_PROJECT_FILE varchar(32),
	MEMO varchar(512),
	REG_TIME datetime,
	STATUS varchar(16) NOT NULL,
	HEIGHT decimal(19,2),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;



/* Create Foreign Keys */

ALTER TABLE CONSTRUCT_CORP_REG
	ADD FOREIGN KEY (CORP_CODE)
	REFERENCES CORP_INFO (CORP_CODE)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE CONSTRUCT_EMPLOYEE
	ADD FOREIGN KEY (CORP_CODE)
	REFERENCES CORP_INFO (CORP_CODE)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE LOGIN
	ADD FOREIGN KEY (CORP_CODE)
	REFERENCES CORP_INFO (CORP_CODE)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE DFCI
	ADD FOREIGN KEY (CHECK_ID)
	REFERENCES DFCC (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE DFCI
	ADD FOREIGN KEY (COLLECT_ID)
	REFERENCES DFCT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE FIRE_CHECK_ITEM
	ADD FOREIGN KEY (CHECK_ID)
	REFERENCES FIRE_CHECK (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE FIRE_JOIN_CORP
	ADD FOREIGN KEY (CHECK_ID)
	REFERENCES FIRE_CHECK (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE FIRE_CHECK_SUB_ITEM
	ADD FOREIGN KEY (COLLECT_ID)
	REFERENCES FIRE_CHECK_COLLECT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE FIRE_CHECK_SUB_ITEM
	ADD FOREIGN KEY (ITEM_ID)
	REFERENCES FIRE_CHECK_ITEM (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE JOIN_PERSON
	ADD FOREIGN KEY (CORP)
	REFERENCES JOIN_CORP (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE JOIN_CORP
	ADD FOREIGN KEY (PROJECT_ID)
	REFERENCES PROJECT_INFO (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


