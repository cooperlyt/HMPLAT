SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS TASK_ACTION;
DROP TABLE IF EXISTS BUSINESS_REPORT;
DROP TABLE IF EXISTS ROLE_BIZ;
DROP TABLE IF EXISTS SUBSCRIBE;
DROP TABLE IF EXISTS SUBSCRIBE_GROUP;
DROP TABLE IF EXISTS BUSINESS_NEED_FILE;
DROP TABLE IF EXISTS BUSINESS_DEFINE;
DROP TABLE IF EXISTS BUSINESS_CATEGORY;
DROP TABLE IF EXISTS NUMBER_POOL;
DROP TABLE IF EXISTS REPORT;
DROP TABLE IF EXISTS SYSTEM_PARAM;
DROP TABLE IF EXISTS WORD;
DROP TABLE IF EXISTS WORD_CATEGORY;




/* Create Tables */

CREATE TABLE BUSINESS_CATEGORY
(
	ID varchar(32) NOT NULL,
	NAME varchar(50) NOT NULL,
	PRIORITY int NOT NULL,
	TYPE varchar(16),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE BUSINESS_DEFINE
(
	ID varchar(32) NOT NULL,
	NAME varchar(64) NOT NULL,
	WF_NAME varchar(32),
	START_PAGE varchar(256),
	CATEGORY varchar(32) NOT NULL,
	MEMO varchar(512),
	-- 乐观锁
	VERSION int COMMENT '乐观锁',
	ROLE_PREFIX varchar(16),
	-- 动态
	DESCRIPTION varchar(512) COMMENT '动态',
	PRIORITY int NOT NULL,
	ENABLE boolean NOT NULL,
	WF_VER int NOT NULL,
	CREATE_TITLE varchar(8),
	CREATE_COMPLETE_PAGE varchar(256),
	CONSTRAINT PK_DGBIZ PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE BUSINESS_NEED_FILE
(
	BUSINESS varchar(32) NOT NULL,
	ID bigint NOT NULL AUTO_INCREMENT,
	PRIORITY int NOT NULL,
	TASK_NAME varchar(128),
	PARENT_ID bigint,
	-- ALL
	-- ONE
	-- CHILD
	TYPE varchar(20) NOT NULL COMMENT 'ALL
ONE
CHILD',
	NAME varchar(50) NOT NULL,
	DESCRIPTION varchar(200),
	_CONDITION varchar(500),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE BUSINESS_REPORT
(
	BUSINESS varchar(32) NOT NULL,
	REPORT varchar(32) NOT NULL,
	ID bigint NOT NULL AUTO_INCREMENT,
	PRIORITY int NOT NULL,
	TASK_NAME varchar(128) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE NUMBER_POOL
(
	ID varchar(32) NOT NULL,
	NUMBER bigint NOT NULL,
	-- DATE
	-- MONTH
	-- YEAR
	-- ORDER
	-- 
	-- 
	TYPE varchar(16) NOT NULL COMMENT 'DATE
MONTH
YEAR
ORDER

',
	VERSION int NOT NULL,
	DESCRIPTION varchar(512),
	NUMBER_DATE date NOT NULL,
	LENGTH int NOT NULL,
	PREFIX varchar(16),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE REPORT
(
	ID varchar(32) NOT NULL,
	NAME varchar(100) NOT NULL,
	DESCRIPTION varchar(200),
	PAGE varchar(200) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE ROLE_BIZ
(
	ROLE varchar(32) NOT NULL,
	BIZ_ID varchar(32) NOT NULL,
	CONSTRAINT PK_DGROLEBIZ PRIMARY KEY (ROLE, BIZ_ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE SUBSCRIBE
(
	ID bigint NOT NULL AUTO_INCREMENT,
	REG_NAME varchar(32) NOT NULL,
	PRIORITY int NOT NULL,
	GROUP_ID bigint NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE SUBSCRIBE_GROUP
(
	ID bigint NOT NULL AUTO_INCREMENT,
	NAME varchar(32) NOT NULL,
	PRIORITY int NOT NULL,
	TASK_NAME varchar(128),
	TYPE varchar(8) NOT NULL,
	DEFINE_ID varchar(32) NOT NULL,
	ICON_CSS varchar(64),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE SYSTEM_PARAM
(
	_KEY varchar(32) NOT NULL,
	_VALUE varchar(512) NOT NULL,
	DESCRIPTION varchar(512) NOT NULL,
	PRIMARY KEY (_KEY)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE TASK_ACTION
(
	DEFINE_ID varchar(32) NOT NULL,
	TASK_NAME varchar(128),
	TYPE varchar(8) NOT NULL,
	REG_NAME varchar(32) NOT NULL,
	PRIORITY int NOT NULL,
	ID bigint NOT NULL AUTO_INCREMENT,
	POSITION varchar(8) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE WORD
(
	ID varchar(32) NOT NULL,
	_VALUE varchar(100) NOT NULL,
	CATEGORY varchar(32) NOT NULL,
	DESCRIPTION varchar(200),
	PRIORITY int NOT NULL,
	ENABLE boolean NOT NULL,
	CONSTRAINT PK_DGWORDBOOK PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE WORD_CATEGORY
(
	ID varchar(32) NOT NULL,
	NAME varchar(50) NOT NULL,
	MEMO varchar(100),
	SYSTEM boolean NOT NULL,
	CONSTRAINT PK_DGWORDBOOKTYPE PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;



/* Create Foreign Keys */

ALTER TABLE BUSINESS_DEFINE
	ADD FOREIGN KEY (CATEGORY)
	REFERENCES BUSINESS_CATEGORY (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TASK_ACTION
	ADD FOREIGN KEY (DEFINE_ID)
	REFERENCES BUSINESS_DEFINE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE BUSINESS_REPORT
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES BUSINESS_DEFINE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE ROLE_BIZ
	ADD CONSTRAINT FK_DGROLEBI_REFERENCE_DGBIZ FOREIGN KEY (BIZ_ID)
	REFERENCES BUSINESS_DEFINE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE SUBSCRIBE_GROUP
	ADD FOREIGN KEY (DEFINE_ID)
	REFERENCES BUSINESS_DEFINE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE BUSINESS_NEED_FILE
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES BUSINESS_DEFINE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE BUSINESS_NEED_FILE
	ADD FOREIGN KEY (PARENT_ID)
	REFERENCES BUSINESS_NEED_FILE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE BUSINESS_REPORT
	ADD FOREIGN KEY (REPORT)
	REFERENCES REPORT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE SUBSCRIBE
	ADD FOREIGN KEY (GROUP_ID)
	REFERENCES SUBSCRIBE_GROUP (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE WORD
	ADD CONSTRAINT FK_DGWORDBO_RELATIONS_DGWORDBO FOREIGN KEY (CATEGORY)
	REFERENCES WORD_CATEGORY (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



