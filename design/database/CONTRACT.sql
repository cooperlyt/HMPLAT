SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS CONTRACT.ARTICLE;
DROP TABLE IF EXISTS CONTRACT.ARTICLE_CATEGORY;
DROP TABLE IF EXISTS CONTRACT.POWER_PROXY_PERSON;
DROP TABLE IF EXISTS CONTRACT.BUSINESS_POOL;
DROP TABLE IF EXISTS CONTRACT.COMPANY_SELL_INFO;
DROP TABLE IF EXISTS CONTRACT.CONTRACT_NUMBER;
DROP TABLE IF EXISTS CONTRACT.CONTRACT_TEMPLATE;
DROP TABLE IF EXISTS CONTRACT.OLD_HOUSE_CONTRACT;
DROP TABLE IF EXISTS CONTRACT.SALE_PROXY_PERSON;
DROP TABLE IF EXISTS CONTRACT.SELL_SHOW_IMG;
DROP TABLE IF EXISTS CONTRACT.HOUSE_SELL_INFO;
DROP TABLE IF EXISTS CONTRACT.SELL_PROXY_PERSON;
DROP TABLE IF EXISTS CONTRACT.HOUSE_SOURCE;
DROP TABLE IF EXISTS CONTRACT.NEW_HOUSE_CONTRACT;
DROP TABLE IF EXISTS CONTRACT.HOUSE_CONTRACT;
DROP TABLE IF EXISTS CONTRACT.new_table;
DROP TABLE IF EXISTS CONTRACT.NUMBER_POOL;
DROP TABLE IF EXISTS CONTRACT.PROJECT_NUMBER_POOL;
DROP TABLE IF EXISTS CONTRACT.SYSTEM_PARAM;




/* Create Tables */

CREATE TABLE CONTRACT.ARTICLE
(
	ID varchar(32) NOT NULL,
	MAIN_TITLE varchar(256) NOT NULL,
	SUB_TITLE varchar(512),
	CATEGORY varchar(32) NOT NULL,
	PUBLISH_TIME datetime NOT NULL,
	CONTEXT longtext,
	FIX_TOP boolean NOT NULL,
	VIEW_TYPE varchar(16) NOT NULL,
	RESOURCE longblob,
	COVER_IMG longtext,
	SUMMARY varchar(1024),
	ON_FLOW boolean NOT NULL,
	RESOURCE_CONTENT_TYPE varchar(32),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.ARTICLE_CATEGORY
(
	ID varchar(32) NOT NULL,
	NAME varchar(32) NOT NULL,
	PRI int NOT NULL,
	TYPE varchar(16) NOT NULL,
	DESCRIPTION varchar(512),
	PRIMARY KEY (ID),
	UNIQUE (NAME)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.BUSINESS_POOL
(
	ID varchar(32) NOT NULL,
	NAME varchar(50) NOT NULL,
	ID_TYPE varchar(32) NOT NULL,
	ID_NO varchar(100) NOT NULL,
	RELATION varchar(32),
	POOL_AREA decimal(19,4),
	PERC decimal(19,4),
	PHONE varchar(15) NOT NULL,
	LEGAL_PERSON varchar(50),
	CONTRACT varchar(32) NOT NULL,
	LEGAL_TYPE varchar(20),
	ROOT_ADDRESS varchar(50),
	ADDRESS varchar(200),
	BIRTHDAY date,
	SEX varchar(10),
	TYPE varchar(16) NOT NULL,
	PRI int NOT NULL,
	POST_CODE varchar(9),
	PAPER_COPY_A varchar(32),
	PAPER_COPY_B varchar(32),
	PAPER_COPY_INFO varchar(1024),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.COMPANY_SELL_INFO
(
	GROUP_ID varchar(32) NOT NULL,
	ID varchar(32) NOT NULL,
	HOUSE_SOURCE varchar(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.CONTRACT_NUMBER
(
	CONTRACT_NUMBER varchar(32) NOT NULL,
	CONTRACT_ID varchar(32) NOT NULL,
	OWNER_NAME varchar(64),
	PASSWORD varchar(64),
	PRIMARY KEY (CONTRACT_NUMBER)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.CONTRACT_TEMPLATE
(
	PROJECT_ID varchar(32) NOT NULL,
	TYPE varchar(20) NOT NULL,
	CONTRACT longtext NOT NULL,
	CONTRACT_VERSION int NOT NULL,
	ID varchar(32) NOT NULL,
	NAME varchar(50) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.HOUSE_CONTRACT
(
	ID varchar(32) NOT NULL,
	GROUP_ID varchar(32) NOT NULL,
	HOUSE_CODE varchar(32) NOT NULL,
	TYPE varchar(20) NOT NULL,
	CREATE_TIME datetime NOT NULL,
	STATUS varchar(20) NOT NULL,
	ATTACH_EMP_ID varchar(32) NOT NULL,
	CONTRACT_PRICE decimal(21,3) NOT NULL,
	ATTACH_EMP_NAME varchar(50) NOT NULL,
	CONTRACT longtext,
	CONTRACT_VERSION int,
	VERSION int NOT NULL,
	POOL_TYPE varchar(32) NOT NULL,
	HOUSE_DESCRIPTION varchar(512) NOT NULL,
	HOUSE_AREA decimal(19,4) NOT NULL,
	CONTRACT_INDEX varchar(1024),
	SALE_PAY_TYPE varchar(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.HOUSE_SELL_INFO
(
	ID varchar(32) NOT NULL,
	TITLE varchar(64) NOT NULL,
	TAGS varchar(512),
	DESCRIPTION varchar(512),
	ENVIRONMENT varchar(512),
	LAT decimal(18,14),
	LNG decimal(18,14),
	ZOOM int,
	ROOM_COUNT int NOT NULL,
	LIVING_ROOM int NOT NULL,
	METRO_AREA varchar(32),
	DECORATE varchar(512),
	ELEVATOR boolean,
	COVER varchar(32),
	IN_FLOOR int NOT NULL,
	CREATE_YEAR int NOT NULL,
	DIRECTION varchar(32),
	USE_TYPE varchar(32) NOT NULL,
	IN_FLOOR_NAME varchar(50) NOT NULL,
	HOUSE_AREA decimal(18,3) NOT NULL,
	USE_AREA decimal(18,3),
	STRUCTURE varchar(32) NOT NULL,
	ADDRESS varchar(200) NOT NULL,
	DISTRICT varchar(32) NOT NULL,
	LOCAL_AREA varchar(32),
	SCHOOL_AREA varchar(32),
	PRICE decimal(19,4) NOT NULL,
	SUM_PRICE decimal(19,4) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.HOUSE_SOURCE
(
	ID varchar(32) NOT NULL,
	POWER_CARD_NUMBER varchar(50) NOT NULL,
	CREDENTIALS_TYPE varchar(32) NOT NULL,
	CREDENTIALS_NUMBER varchar(100) NOT NULL,
	OWNER_NAME varchar(50) NOT NULL,
	TEL varchar(16) NOT NULL,
	MASTER_GROUP_ID varchar(32) NOT NULL,
	VERSION bigint NOT NULL,
	CHECK_VIEW varchar(200),
	HOUSE_CODE varchar(32) NOT NULL,
	SOURCE_ID varchar(32) NOT NULL,
	TYPE varchar(20) NOT NULL,
	APPLY_TIME datetime NOT NULL,
	STATUS varchar(20) NOT NULL,
	SEARCH_KEY varchar(1024),
	SALE_TITLE varchar(200) NOT NULL,
	CHECK_TIME datetime NOT NULL,
	CONTRACT varchar(32),
	BUSINESS_ID varchar(32),
	MESSAGES longtext,
	UPDATE_TIME timestamp NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.NEW_HOUSE_CONTRACT
(
	ID varchar(32) NOT NULL,
	PROJECT_ID varchar(32) NOT NULL,
	PROJECT_CER_NUMBER varchar(100) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.new_table
(
	ppp varchar(32),
	p2 varchar(23),
	p3 bigint,
	UNIQUE (ppp),
	CONSTRAINT llll UNIQUE (ppp, p2, p3)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.NUMBER_POOL
(
	TYPE varchar(50) NOT NULL,
	NUMBER bigint NOT NULL,
	PROJECT_CODE varchar(32) NOT NULL,
	ID varchar(32) NOT NULL,
	VERSION bigint NOT NULL,
	PRIMARY KEY (ID),
	CONSTRAINT TYPE_PROJECT_UNIQUE UNIQUE (TYPE, PROJECT_CODE)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.OLD_HOUSE_CONTRACT
(
	ID varchar(32) NOT NULL,
	-- 三方合同
	CONTROL boolean NOT NULL COMMENT '三方合同',
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.POWER_PROXY_PERSON
(
	ID varchar(32) NOT NULL,
	TYPE varchar(16) NOT NULL,
	NAME varchar(50) NOT NULL,
	ID_TYPE varchar(32) NOT NULL,
	ID_NO varchar(100) NOT NULL,
	ROOT_ADDRESS varchar(50),
	BIRTHDAY date,
	SEX varchar(10),
	ADDRESS varchar(200),
	POST_CODE varchar(9),
	PHONE varchar(15) NOT NULL,
	FINGERPRINT varchar(1024),
	PAPER_COPY_A varchar(32),
	PAPER_COPY_B varchar(32),
	PAPER_COPY_INFO varchar(1024),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.PROJECT_NUMBER_POOL
(
	PROJECT_CODE varchar(32) NOT NULL,
	NUMBER bigint NOT NULL,
	PRIMARY KEY (PROJECT_CODE)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.SALE_PROXY_PERSON
(
	ID varchar(32) NOT NULL,
	ID_NO varchar(100) NOT NULL,
	ID_TYPE varchar(32) NOT NULL,
	NAME varchar(50) NOT NULL,
	PHONE varchar(15) NOT NULL,
	CREATE_DATE timestamp NOT NULL,
	DISABLED boolean NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.SELL_PROXY_PERSON
(
	CREDENTIALS_TYPE varchar(32) NOT NULL,
	CREDENTIALS_NUMBER varchar(100) NOT NULL,
	PERSON_NAME varchar(64) NOT NULL,
	TEL varchar(16) NOT NULL,
	ID varchar(32) NOT NULL,
	HOUSE_SOURCE varchar(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.SELL_SHOW_IMG
(
	ID varchar(32) NOT NULL,
	TITLE varchar(64) NOT NULL,
	DESCRIPTION varchar(512),
	HOUSE_SELL_INFO varchar(32) NOT NULL,
	PRI int NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE CONTRACT.SYSTEM_PARAM
(
	ID varchar(32) NOT NULL,
	VALUE longtext NOT NULL,
	DESCRIPTION varchar(512),
	TYPE varchar(16) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;



/* Create Foreign Keys */

ALTER TABLE CONTRACT.ARTICLE
	ADD FOREIGN KEY (CATEGORY)
	REFERENCES CONTRACT.ARTICLE_CATEGORY (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE CONTRACT.POWER_PROXY_PERSON
	ADD FOREIGN KEY (ID)
	REFERENCES CONTRACT.BUSINESS_POOL (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE CONTRACT.OLD_HOUSE_CONTRACT
	ADD FOREIGN KEY (ID)
	REFERENCES CONTRACT.HOUSE_CONTRACT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE CONTRACT.BUSINESS_POOL
	ADD FOREIGN KEY (CONTRACT)
	REFERENCES CONTRACT.HOUSE_CONTRACT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE CONTRACT.CONTRACT_NUMBER
	ADD FOREIGN KEY (CONTRACT_ID)
	REFERENCES CONTRACT.HOUSE_CONTRACT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE CONTRACT.SALE_PROXY_PERSON
	ADD FOREIGN KEY (ID)
	REFERENCES CONTRACT.HOUSE_CONTRACT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE CONTRACT.HOUSE_SOURCE
	ADD FOREIGN KEY (CONTRACT)
	REFERENCES CONTRACT.HOUSE_CONTRACT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE CONTRACT.NEW_HOUSE_CONTRACT
	ADD FOREIGN KEY (ID)
	REFERENCES CONTRACT.HOUSE_CONTRACT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE CONTRACT.SELL_SHOW_IMG
	ADD FOREIGN KEY (HOUSE_SELL_INFO)
	REFERENCES CONTRACT.HOUSE_SELL_INFO (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE CONTRACT.COMPANY_SELL_INFO
	ADD FOREIGN KEY (HOUSE_SOURCE)
	REFERENCES CONTRACT.HOUSE_SOURCE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE CONTRACT.HOUSE_SELL_INFO
	ADD FOREIGN KEY (ID)
	REFERENCES CONTRACT.HOUSE_SOURCE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE CONTRACT.SELL_PROXY_PERSON
	ADD FOREIGN KEY (HOUSE_SOURCE)
	REFERENCES CONTRACT.HOUSE_SOURCE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE CONTRACT.NUMBER_POOL
	ADD FOREIGN KEY (PROJECT_CODE)
	REFERENCES CONTRACT.PROJECT_NUMBER_POOL (PROJECT_CODE)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



