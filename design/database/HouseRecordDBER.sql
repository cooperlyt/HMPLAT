SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Indexes */

DROP INDEX IF EXISTS BUSINESS_HOUSE_CODE_INDEX ON HOUSE_OWNER_RECORD.BUSINESS_HOUSE;
DROP INDEX IF EXISTS OWNER_BUSINESS_REG_TIME_INDEX ON HOUSE_OWNER_RECORD.OWNER_BUSINESS;



/* Drop Tables */

DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.ADD_HOUSE_STATUS;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.SELL_TYPE_TOTAL;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.EXCEPT_HOUSE;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.BUILD;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.BUSINESS_EMP;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.UPLOAD_FILE;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.BUSINESS_FILE;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.REPAIR_MONEY_INFO;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.REPAIR_MONEY_PAY;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.REG_INFO;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.MONEY_PAY_INFO;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.MONEY_BUSINESS;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.CONTRACT_NUMBER;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.CONTRACT_SUBMIT;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.HOUSE_CONTRACT;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.OUTSIDE_REG_INFO;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.BUSINESS_HOUSE;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.BUSINESS_MONEY;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.BUSINESS_PERSION;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.CARD;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.CARD_INFO;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.CLOSE_HOUSE;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.EVALUATE;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.FACT_MONEYINFO;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.PROJECT_MORTGAGE;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.HOUSE_MORTGAEGE;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.MORTGAEGE_REGISTE;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.FINANCIAL;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.GIVE_CARD;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.HOUSE_OWNER;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.HOUSE_RECORD;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.HOUSE_SOURCE;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.HOUSE;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.HOUSE_CLOSE_CANCEL;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.HOUSE_REG_INFO;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.LAND_END_TIME;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.LAND_INFO;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.LEASE;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.LOCKED_HOUSE;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.PROJECT_CARD;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.POWER_OWNER;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.MAKE_CARD;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.MAPPING_CORP;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.MONEY_SAFE;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.PROCESS_MESSAGES;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.SUB_STATUS;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.REASON;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.RECORD_STORE;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.TASK_OPER;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.PROJECT_SELL_INFO;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.PROJECT;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.OWNER_BUSINESS;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.PROXY_PERSON;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.RECORD_FRAME;
DROP TABLE IF EXISTS HOUSE_OWNER_RECORD.RECORD_ROOM;




/* Create Tables */

CREATE TABLE HOUSE_OWNER_RECORD.ADD_HOUSE_STATUS
(
	ID VARCHAR(32) NOT NULL,
	BUSINESS VARCHAR(32) NOT NULL,
	STATUS VARCHAR(32) NOT NULL,
	IS_REMOVE BOOLEAN NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUILD
(
	ID VARCHAR(32) NOT NULL,
	MAP_NUMBER VARCHAR(4),
	BLOCK_NO VARCHAR(10) NOT NULL,
	BUILD_NO VARCHAR(24) NOT NULL,
	BUILD_CODE VARCHAR(32) NOT NULL,
	STREET_CODE VARCHAR(4),
	NAME VARCHAR(100) NOT NULL,
	DOOR_NO VARCHAR(50),
	UNINT_COUNT INT,
	FLOOR_COUNT INT NOT NULL,
	UP_FLOOR_COUNT INT NOT NULL,
	DOWN_FLOOR_COUNT INT NOT NULL,
	HOUSE_COUNT INT,
	AREA DECIMAL(18,3),
	BUILD_TYPE VARCHAR(32),
	STRUCTURE VARCHAR(32) NOT NULL,
	PROJECT VARCHAR(32) NOT NULL,
	COMPLETE_DATE VARCHAR(6),
	BUILD_DEVELOPER_NUMBER VARCHAR(20),
	MAP_TIME TIMESTAMP,
	CONSTRAINT PK_BUILD PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_EMP
(
	ID VARCHAR(32) NOT NULL,
	TYPE VARCHAR(20) NOT NULL,
	EMP_CODE VARCHAR(32) NOT NULL,
	EMP_NAME VARCHAR(50) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	OPER_TIME DATETIME NOT NULL,
	COMMENTS VARCHAR(500),
	WINDOW_NO VARCHAR(10),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_FILE
(
	ID VARCHAR(32) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	NAME VARCHAR(100) NOT NULL,
	IMPORTANT_CODE VARCHAR(32),
	NO_FILE BOOLEAN NOT NULL,
	MEMO VARCHAR(200),
	PRIORITY INT NOT NULL,
	RECORD_STORE VARCHAR(32),
	TYPE VARCHAR(20) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


-- 应把OWNER_BUSINESS上的部分状态和SELECT_BIZ移动到此表中, 主键不应自动生成，应转为业务编号+房屋序号
CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE
(
	ID VARCHAR(32) NOT NULL,
	HOUSE_CODE VARCHAR(32) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	START_HOUSE VARCHAR(32) NOT NULL,
	AFTER_HOUSE VARCHAR(32) NOT NULL,
	CANCELED BOOLEAN NOT NULL,
	SEARCH_KEY VARCHAR(1024) NOT NULL,
	DISPLAY LONGTEXT NOT NULL,
	CONSTRAINT PK_HOUSE PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '应把OWNER_BUSINESS上的部分状态和SELECT_BIZ移动到此表中, 主键不应自动生成，应转为业务编号+房屋' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_MONEY
(
	ID VARCHAR(32) NOT NULL,
	TYPE_NAME VARCHAR(100) NOT NULL,
	MONEY_TYPE_ID VARCHAR(32) NOT NULL,
	CHECK_MONEY DECIMAL(19,4) NOT NULL,
	SHOULD_MONEY DECIMAL(19,4) NOT NULL,
	FACT_MONEY DECIMAL(19,4) NOT NULL,
	CHARGE_DETAILS VARCHAR(200),
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	FEE VARCHAR(32),
	PRI INT NOT NULL,
	PREFERENTIAL VARCHAR(20),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_PERSION
(
	ID VARCHAR(32) NOT NULL,
	ID_NO VARCHAR(100),
	ID_TYPE VARCHAR(32) NOT NULL,
	-- 领证人
	NAME VARCHAR(50) NOT NULL COMMENT '领证人',
	TYPE VARCHAR(20) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	PHONE VARCHAR(15),
	PROXY_PERSON VARCHAR(32),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


-- 记录业务关联时，权证号，和同同，法律文件号，等相关证件的正价编号
CREATE TABLE HOUSE_OWNER_RECORD.CARD
(
	ID VARCHAR(32) NOT NULL,
	TYPE VARCHAR(20) NOT NULL,
	NUMBER VARCHAR(100) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	CODE VARCHAR(100),
	MEMO VARCHAR(200),
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '记录业务关联时，权证号，和同同，法律文件号，等相关证件的正价编号' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.CARD_INFO
(
	CODE VARCHAR(100),
	MEMO VARCHAR(200),
	MAKE_EMP_CODE VARCHAR(32),
	MAKE_EMP_NAME VARCHAR(50),
	PRINT_TIME TIMESTAMP,
	ID VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.CLOSE_HOUSE
(
	ID VARCHAR(32) NOT NULL,
	CLOSE_DOWN_CLOUR VARCHAR(100) NOT NULL,
	ACTION VARCHAR(100),
	CLOSE_DATE DATETIME NOT NULL,
	TO_DATE DATETIME,
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	-- 裁定书、判决书或其它生效的法律文书 (查封文件)
	LEGAL_DOCUMENTS VARCHAR(50) COMMENT '裁定书、判决书或其它生效的法律文书 (查封文件)',
	-- 查封文号
	EXECUTION_NOTICE VARCHAR(50) COMMENT '查封文号',
	SEND_PEOPLE VARCHAR(10),
	PHONE VARCHAR(15),
	EXECUTION_CARD_NO VARCHAR(30),
	WORK_CARD_NO VARCHAR(30),
	HOUSECARDNO VARCHAR(30),
	CONTRACTCODE VARCHAR(30),
	PROJECTRSIHP VARCHAR(30),
	TIME_AREA_TYPE VARCHAR(20),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.CONTRACT_NUMBER
(
	CONTRACT_NUMBER VARCHAR(32) NOT NULL,
	STATUS VARCHAR(20) NOT NULL,
	VERSION BIGINT NOT NULL,
	TYPE VARCHAR(32) NOT NULL,
	APPLY_TIME DATETIME,
	NUMBER BIGINT NOT NULL,
	CREATE_TIME DATETIME NOT NULL,
	GROUP_NUMBER VARCHAR(32) NOT NULL,
	GROUP_NAME VARCHAR(100),
	CONTRACT VARCHAR(32),
	PRIMARY KEY (CONTRACT_NUMBER)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.CONTRACT_SUBMIT
(
	ATTACH_EMP_ID VARCHAR(32) NOT NULL,
	ATTACH_EMP_NAME VARCHAR(50) NOT NULL,
	CONTRACT LONGTEXT NOT NULL,
	CONTRACT_VERSION INT NOT NULL,
	FILE_ID VARCHAR(32),
	ID VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.EVALUATE
(
	EVALUATE_CORP_NAME VARCHAR(60),
	EVALUATE_CORP_N0 VARCHAR(32),
	ASSESSMENT_PRICE DECIMAL(19,4) NOT NULL,
	ID VARCHAR(32) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.EXCEPT_HOUSE
(
	ID VARCHAR(32) NOT NULL,
	BUILD VARCHAR(32) NOT NULL,
	HOUSE_CODE VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID),
	CONSTRAINT BUILD_AND_HOUSE_UNIQUE UNIQUE (BUILD, HOUSE_CODE)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.FACT_MONEYINFO
(
	ID VARCHAR(32) NOT NULL,
	FACT_TIME DATETIME NOT NULL,
	PAYMENT_NO VARCHAR(25) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	MEMO VARCHAR(200),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.FINANCIAL
(
	ID VARCHAR(32) NOT NULL,
	NAME VARCHAR(120) NOT NULL,
	CODE VARCHAR(100),
	PHONE VARCHAR(15),
	-- 个人
	-- 机构
	FINANCIAL_TYPE VARCHAR(20) NOT NULL COMMENT '个人
机构',
	ID_TYPE VARCHAR(32),
	BANK VARCHAR(32),
	CREATE_TIME TIMESTAMP NOT NULL,
	CARD VARCHAR(32),
	PROXY_PERSON VARCHAR(32),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


-- 非房产部门的证
CREATE TABLE HOUSE_OWNER_RECORD.GIVE_CARD
(
	ID VARCHAR(32) NOT NULL UNIQUE,
	-- 倒库导入原库的NO
	BUISINESS_ID VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	-- 领证人
	NAME VARCHAR(50) NOT NULL COMMENT '领证人',
	ID_NO VARCHAR(100) NOT NULL,
	ID_TYPE VARCHAR(32) NOT NULL,
	PHONE VARCHAR(15),
	GIVE_TIME DATETIME,
	MEMO VARCHAR(200),
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '非房产部门的证' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.HOUSE
(
	ID VARCHAR(32) NOT NULL,
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
	EAST_WALL VARCHAR(32),
	WEST_WALL VARCHAR(32),
	SOUTH_WALL VARCHAR(32),
	NORTH_WALL VARCHAR(32),
	MAP_TIME TIMESTAMP,
	DIRECTION VARCHAR(32),
	HOUSE_CODE VARCHAR(32) NOT NULL,
	BUILD_CODE VARCHAR(32) NOT NULL,
	HAVE_DOWN_ROOM BOOLEAN NOT NULL,
	MAP_NUMBER VARCHAR(4),
	BLOCK_NO VARCHAR(10) NOT NULL,
	BUILD_NO VARCHAR(24) NOT NULL,
	STREET_CODE VARCHAR(4),
	DOOR_NO VARCHAR(50),
	UP_FLOOR_COUNT INT NOT NULL,
	FLOOR_COUNT INT NOT NULL,
	DOWN_FLOOR_COUNT INT NOT NULL,
	BUILD_TYPE VARCHAR(32),
	PROJECT_CODE VARCHAR(32),
	PROJECT_NAME VARCHAR(50) NOT NULL,
	COMPLETE_DATE VARCHAR(6),
	DEVELOPER_CODE VARCHAR(32),
	DEVELOPER_NAME VARCHAR(100),
	SECTION_CODE VARCHAR(32),
	SECTION_NAME VARCHAR(50) NOT NULL,
	DISTRICT_CODE VARCHAR(32) NOT NULL,
	DISTRICT_NAME VARCHAR(100) NOT NULL,
	BUILD_NAME VARCHAR(100) NOT NULL,
	BUILD_DEVELOPER_NUMBER VARCHAR(20),
	POOL_MEMO VARCHAR(32),
	LAND_INFO VARCHAR(32),
	REG_INFO VARCHAR(32),
	MAIN_OWNER VARCHAR(32),
	DESIGN_USE_TYPE VARCHAR(512) NOT NULL,
	UNIT_NUMBER VARCHAR(32),
	OLD_POOL_MEMO VARCHAR(32),
	CONSTRAINT PK_HOUSE PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.HOUSE_CLOSE_CANCEL
(
	ID VARCHAR(32) NOT NULL,
	CANCEL_DATE DATETIME NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	CANCEL_DOWN_CLOUR VARCHAR(100) NOT NULL,
	ACTION VARCHAR(100),
	-- 裁定书、判决书或其它生效的法律文书 (查封文件)
	LEGAL_DOCUMENTS VARCHAR(50) COMMENT '裁定书、判决书或其它生效的法律文书 (查封文件)',
	-- 查封文号
	EXECUTION_NOTICE VARCHAR(50) COMMENT '查封文号',
	SEND_PEOPLE VARCHAR(10),
	PHONE VARCHAR(15),
	EXECUTION_CARD_NO VARCHAR(30),
	WORK_CARD_NO VARCHAR(30),
	HOUSECARDNO VARCHAR(30),
	CONTRACTCODE VARCHAR(30),
	PROJECTRSIHP VARCHAR(30),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.HOUSE_CONTRACT
(
	ID VARCHAR(32) NOT NULL,
	CONTRACT_NUMBER VARCHAR(50) NOT NULL,
	CONTRACT_DATE DATETIME NOT NULL,
	TYPE VARCHAR(32) NOT NULL,
	PROJECT_RSHIP_NUMBER VARCHAR(50),
	PAY_TYPE VARCHAR(32),
	SUM_PRICE DECIMAL(19,4) NOT NULL,
	-- 计算收费面积使用
	SALEAREA DECIMAL(19,4) NOT NULL COMMENT '计算收费面积使用',
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.HOUSE_MORTGAEGE
(
	MORTGAEGE VARCHAR(32) NOT NULL,
	HOUSE VARCHAR(32) NOT NULL,
	PRIMARY KEY (MORTGAEGE, HOUSE)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.HOUSE_OWNER
(
	HOUSE VARCHAR(32) NOT NULL,
	POOL VARCHAR(32) NOT NULL,
	PRIMARY KEY (HOUSE, POOL)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.HOUSE_RECORD
(
	HOUSE_CODE VARCHAR(32) NOT NULL,
	HOUSE VARCHAR(32) NOT NULL UNIQUE,
	HOUSE_STATUS VARCHAR(32),
	DISPLAY LONGTEXT NOT NULL,
	SEARCH_KEY VARCHAR(1024) NOT NULL,
	LAST_CHANGE_DATE TIMESTAMP NOT NULL,
	PRIMARY KEY (HOUSE_CODE),
	CONSTRAINT HOUSE_CODE_UNIQUE UNIQUE (HOUSE_CODE, HOUSE)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.HOUSE_REG_INFO
(
	ID VARCHAR(32) NOT NULL,
	HOUSE_PORPERTY VARCHAR(32) NOT NULL,
	HOUSE_FROM VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.HOUSE_SOURCE
(
	ID VARCHAR(32) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	HOUSE VARCHAR(32) NOT NULL,
	-- 是否审核
	CHECKED BOOLEAN NOT NULL COMMENT '是否审核',
	-- 审核意见
	MESSAGES VARCHAR(200) COMMENT '审核意见',
	-- 房源ID
	SOURCE_ID VARCHAR(32) NOT NULL COMMENT '房源ID',
	SOURCE_DATA LONGTEXT NOT NULL,
	DISPLAY LONGTEXT NOT NULL,
	SEARCH_KEY VARCHAR(1024) NOT NULL,
	-- 从业机构ID
	ATTR_ID VARCHAR(32) NOT NULL COMMENT '从业机构ID',
	-- 从业机构名称
	ATTR_NAME VARCHAR(100) NOT NULL COMMENT '从业机构名称',
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.LAND_END_TIME
(
	PROJECT_ID VARCHAR(32) NOT NULL,
	ID VARCHAR(32) NOT NULL,
	END_TIME DATETIME NOT NULL,
	USE_TYPE VARCHAR(20) NOT NULL,
	PRIMARY KEY (ID),
	CONSTRAINT LAND_TIME_PROJECT_USE_TYPE UNIQUE (PROJECT_ID, USE_TYPE)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.LAND_INFO
(
	ID VARCHAR(32) NOT NULL,
	LAND_CARD_NO VARCHAR(50),
	NUMBER VARCHAR(50) NOT NULL,
	LAND_PROPERTY VARCHAR(32),
	BEGIN_USE_TIME DATETIME,
	END_USE_TIME DATETIME NOT NULL,
	LAND_GET_MODE VARCHAR(32) NOT NULL,
	LAND_AREA DECIMAL(18,3),
	CONSTRAINT PK_LANDINFO PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.LEASE
(
	ID VARCHAR(32) NOT NULL,
	NAME VARCHAR(100) NOT NULL,
	LEASE_AREA DECIMAL(18,3) NOT NULL,
	SELF_RENT DECIMAL(19,4) NOT NULL,
	EVALUATE_RENT DECIMAL(19,4) NOT NULL,
	SUM_RENT DECIMAL(19,4) NOT NULL,
	START_DATE DATETIME NOT NULL,
	END_DATE DATETIME,
	SUM_MONTH INT NOT NULL,
	TIME_AREA_TYPE VARCHAR(20),
	-- 倒库导入原库的NO
	BUISINESS_ID VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	SELL_COMPANY_NAME VARCHAR(100),
	SEARCH_KEY VARCHAR(1024),
	DISPLAY LONGTEXT,
	LEASE_NO VARCHAR(20),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.LOCKED_HOUSE
(
	HOUSE_CODE VARCHAR(32) NOT NULL,
	DESCRIPTION VARCHAR(500),
	-- CANT_SALE:预售许可证设置的不可售
	-- SYSTEM_LOCKED:系统锁定
	-- HOUSE_LOCKED:预警
	-- CLOSE_REG:查封登记
	-- DISPUTE_REG:异议登记
	-- OTHER_REG:其它限制登记
	-- 
	-- 
	-- 
	TYPE VARCHAR(32) NOT NULL COMMENT 'CANT_SALE:预售许可证设置的不可售
SYSTEM_LOCKED:系统锁定
HOUSE_LOCKED:预警
CLOSE_REG:查封登记
DISPUTE_REG:异议登记
OTHER_REG:其它限制登记


',
	EMP_CODE VARCHAR(32) NOT NULL,
	EMP_NAME VARCHAR(50) NOT NULL,
	LOCKED_TIME TIMESTAMP NOT NULL,
	ID VARCHAR(32) NOT NULL,
	BUILD_CODE VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.MAKE_CARD
(
	ID VARCHAR(32) NOT NULL,
	NUMBER VARCHAR(100) NOT NULL,
	TYPE VARCHAR(20) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	-- true 生效
	ENABLE BOOLEAN NOT NULL COMMENT 'true 生效',
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.MAPPING_CORP
(
	ID VARCHAR(32) NOT NULL,
	NAME VARCHAR(100) NOT NULL,
	CODE VARCHAR(100) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.MONEY_BUSINESS
(
	ID VARCHAR(32) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	STATUS VARCHAR(32) NOT NULL,
	-- 填写空间库 （MONEY_BANK ID)
	BANK VARCHAR(32) NOT NULL COMMENT '填写空间库 （MONEY_BANK ID)',
	-- 填写空间库 （MONEY_BANK NAME)
	BANK_NAME VARCHAR(128) NOT NULL COMMENT '填写空间库 （MONEY_BANK NAME)',
	-- 填写空间库 （MONEY_BANK ACCOUNT_NUMBER)
	ACCOUNT_NUMBER VARCHAR(32) NOT NULL COMMENT '填写空间库 （MONEY_BANK ACCOUNT_NUMBER)',
	-- 更正一次加1
	VER INT NOT NULL COMMENT '更正一次加1',
	VERSION INT NOT NULL,
	MONEY DECIMAL(19,4) NOT NULL,
	CONTRACT VARCHAR(32) NOT NULL,
	DISPLAY LONGTEXT,
	SEARCH_KEY VARCHAR(1024),
	-- 业务是否通过审批
	CHECKED BOOLEAN NOT NULL COMMENT '业务是否通过审批',
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.MONEY_PAY_INFO
(
	-- 收款方，银行开户行
	BANK_NAME VARCHAR(512) NOT NULL COMMENT '收款方，银行开户行',
	-- 持卡人银行卡号 （收款人,卖方）
	CARD_NUMBER VARCHAR(128) NOT NULL COMMENT '持卡人银行卡号 （收款人,卖方）',
	-- 持卡人姓名（收款方）
	CARD_NAME VARCHAR(50) NOT NULL COMMENT '持卡人姓名（收款方）',
	ID VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.MONEY_SAFE
(
	ID VARCHAR(32) NOT NULL,
	BANK VARCHAR(32) NOT NULL,
	ACCOUNT_NAME VARCHAR(128) NOT NULL,
	CREATE_BANK_NAME VARCHAR(512) NOT NULL,
	CARD_NUMBER VARCHAR(128) NOT NULL,
	CARD_NAME VARCHAR(50) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.MORTGAEGE_REGISTE
(
	HIGHEST_MOUNT_MONEY DECIMAL(19,4) NOT NULL,
	WARRANT_SCOPE VARCHAR(100),
	INTEREST_TYPE VARCHAR(32) NOT NULL,
	MORTGAGE_DUE_TIME_S DATETIME NOT NULL,
	MORTGAGE_TIME DATETIME NOT NULL,
	MORTGAGE_AREA DECIMAL(19,4) NOT NULL,
	TIME_AREA_TYPE VARCHAR(20) NOT NULL,
	ID VARCHAR(32) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	OLD_FIN VARCHAR(32),
	FIN VARCHAR(32),
	ORG_NAME VARCHAR(100) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.OUTSIDE_REG_INFO
(
	ID VARCHAR(32) NOT NULL,
	STATUS VARCHAR(16) NOT NULL,
	SEND_TIME DATETIME,
	RECEIVE_TIME DATETIME,
	REG_TIME DATETIME,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.OWNER_BUSINESS
(
	-- 倒库导入原库的NO
	ID VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	VERSION INT,
	SOURCE VARCHAR(20) NOT NULL,
	MEMO VARCHAR(200),
	STATUS VARCHAR(20) NOT NULL,
	DEFINE_NAME VARCHAR(50) NOT NULL,
	DEFINE_ID VARCHAR(32) NOT NULL,
	DEFINE_VERSION INT,
	-- 倒库导入原库的NO
	SELECT_BUSINESS VARCHAR(32) COMMENT '倒库导入原库的NO',
	CREATE_TIME TIMESTAMP NOT NULL,
	APPLY_TIME DATETIME NOT NULL,
	CHECK_TIME DATETIME,
	REG_TIME DATETIME,
	RECORD_TIME DATETIME,
	-- 业务是否以生效，业务一但生效不可回退，一般放在审批或归档
	RECORDED BOOLEAN NOT NULL COMMENT '业务是否以生效，业务一但生效不可回退，一般放在审批或归档',
	-- 正常业务
	-- 撤消业务
	-- 更正业务
	TYPE VARCHAR(20) NOT NULL COMMENT '正常业务
撤消业务
更正业务',
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.POWER_OWNER
(
	ID VARCHAR(32) NOT NULL,
	NAME VARCHAR(50) NOT NULL,
	ID_TYPE VARCHAR(32) NOT NULL,
	ID_NO VARCHAR(100) NOT NULL,
	RELATION VARCHAR(32),
	POOL_AREA DECIMAL(19,4),
	PERC DECIMAL(19,4),
	PHONE VARCHAR(15) NOT NULL,
	LEGAL_PERSON VARCHAR(50),
	-- LEGAL_OWNER:法人
	-- LEGAL_MANAGER:负责人
	LEGAL_TYPE VARCHAR(20) COMMENT 'LEGAL_OWNER:法人
LEGAL_MANAGER:负责人',
	ROOT_ADDRESS VARCHAR(50),
	ADDRESS VARCHAR(200),
	BIRTHDAY DATE,
	SEX VARCHAR(10),
	-- OWNER:共有人/产权人
	-- CONTRACT:备案人
	-- PREPARE:预告人
	-- INIT:初始登记人/预告登记时的产权人
	-- 
	-- 
	TYPE VARCHAR(16) NOT NULL COMMENT 'OWNER:共有人/产权人
CONTRACT:备案人
PREPARE:预告人
INIT:初始登记人/预告登记时的产权人

',
	PRI INT NOT NULL,
	CARD VARCHAR(32),
	OLD BOOLEAN NOT NULL,
	PROXY_PERSON VARCHAR(32),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.PROCESS_MESSAGES
(
	ID VARCHAR(32) NOT NULL,
	LEVEL VARCHAR(20) NOT NULL,
	MESSAGES VARCHAR(200) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.PROJECT
(
	ID VARCHAR(32) NOT NULL,
	PROJECT_CODE VARCHAR(32) NOT NULL,
	NAME VARCHAR(50) NOT NULL,
	ADDRESS VARCHAR(200),
	DEVELOPER_NAME VARCHAR(100),
	DEVELOPER_CODE VARCHAR(32),
	SECTION_NAME VARCHAR(50) NOT NULL,
	SECTION_CODE VARCHAR(32) NOT NULL,
	DISTRICT_CODE VARCHAR(32) NOT NULL,
	DISTRICT_NAME VARCHAR(100) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	CORP_ADDRESS VARCHAR(100),
	CORP_LEVEL VARCHAR(32),
	CORP_PROPERTY VARCHAR(32),
	SEARCH_KEY VARCHAR(1024) NOT NULL,
	DISPLAY LONGTEXT NOT NULL,
	CONSTRAINT PK_PROJECT PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.PROJECT_CARD
(
	ID VARCHAR(32) NOT NULL,
	YEAR_NUMBER VARCHAR(20),
	ORDER_NUMBER VARCHAR(20),
	MEMO VARCHAR(200),
	PRINT_TIME TIMESTAMP NOT NULL,
	MAKE_EMP_CODE VARCHAR(32),
	MAKE_EMP_NAME VARCHAR(50),
	PROJECT VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.PROJECT_MORTGAGE
(
	ID VARCHAR(32) NOT NULL,
	LAND_NUMBER VARCHAR(32),
	ADDRESS VARCHAR(512),
	DEVELOPER_CODE VARCHAR(32) NOT NULL,
	DEVELOPER_NAME VARCHAR(100),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.PROJECT_SELL_INFO
(
	ID VARCHAR(32) NOT NULL,
	HOUSE_COUNT INT,
	BUILD_COUNT INT,
	AREA DECIMAL(18,3),
	USE_TYPE VARCHAR(32),
	SELL_OBJECT VARCHAR(50),
	-- 预售 现售
	TYPE VARCHAR(20) NOT NULL COMMENT '预售 现售',
	LAND_CARD_NO VARCHAR(50),
	NUMBER VARCHAR(50) NOT NULL,
	LAND_PROPERTY VARCHAR(32),
	BEGIN_USE_TIME DATETIME NOT NULL,
	END_USE_TIME DATETIME NOT NULL,
	LAND_GET_MODE VARCHAR(32),
	LAND_AREA DECIMAL(18,3),
	BUILD_SIZE VARCHAR(32),
	LAND_ADDRESS VARCHAR(255),
	LAND_CARD_TYPE VARCHAR(32) NOT NULL,
	LAND_USE_TYPE VARCHAR(32) NOT NULL,
	CREATE_CARD_CODE VARCHAR(50) NOT NULL,
	CREATE_PREPARE_CARD_CODE VARCHAR(50) NOT NULL,
	LICENSE_NUMBER VARCHAR(50),
	CREATE_LAND_CARD_CODE VARCHAR(50),
	PROOF_MATERIAL VARCHAR(50),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.PROXY_PERSON
(
	ID VARCHAR(32) NOT NULL,
	TYPE VARCHAR(16) NOT NULL,
	NAME VARCHAR(50) NOT NULL,
	ID_TYPE VARCHAR(32) NOT NULL,
	ID_NO VARCHAR(100) NOT NULL,
	PHONE VARCHAR(15) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


-- 记录注销原因，更正前，更正后，变更前，变更后等事由信息
CREATE TABLE HOUSE_OWNER_RECORD.REASON
(
	ID VARCHAR(32) NOT NULL,
	TYPE VARCHAR(20) NOT NULL,
	REASON VARCHAR(400),
	-- 倒库导入原库的NO
	BUISINESS_ID VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '记录注销原因，更正前，更正后，变更前，变更后等事由信息' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.RECORD_FRAME
(
	ID VARCHAR(32) NOT NULL,
	NAME VARCHAR(32) NOT NULL,
	ROOM VARCHAR(32) NOT NULL,
	VERSION BIGINT NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.RECORD_ROOM
(
	ID VARCHAR(32) NOT NULL,
	NAME VARCHAR(200) NOT NULL UNIQUE,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.RECORD_STORE
(
	ID VARCHAR(32) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	VERSION BIGINT NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	RECORD_CODE VARCHAR(50) NOT NULL,
	FRAME VARCHAR(32) NOT NULL,
	CABINET VARCHAR(32) NOT NULL,
	BOX VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.REG_INFO
(
	QUERY_TIME DATETIME NOT NULL,
	DATA LONGTEXT NOT NULL,
	ID VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.REPAIR_MONEY_INFO
(
	ID VARCHAR(32) NOT NULL,
	PUBLIC_AREA DECIMAL(18,3) NOT NULL,
	PRIVATE_AREA DECIMAL(18,3),
	PUBLIC_RATE DECIMAL(18,2) NOT NULL,
	PRIVATE_RATE DECIMAL(18,2),
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.REPAIR_MONEY_PAY
(
	ID VARCHAR(32) NOT NULL,
	BUSINESS VARCHAR(32) NOT NULL,
	CALC_DETAILS VARCHAR(512),
	MUST_MONEY DECIMAL(18,2) NOT NULL,
	MONEY DECIMAL(18,2) NOT NULL,
	DESCRIPTION VARCHAR(512),
	NOTICE_TIME DATETIME NOT NULL,
	EMP_CODE VARCHAR(32) NOT NULL,
	EMP_NAME VARCHAR(50) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.SELL_TYPE_TOTAL
(
	ID VARCHAR(32) NOT NULL,
	BUILD_ID VARCHAR(32) NOT NULL,
	USE_TYPE VARCHAR(20) NOT NULL,
	COUNT INT NOT NULL,
	AREA DECIMAL(18,3) NOT NULL,
	PRIMARY KEY (ID),
	UNIQUE (BUILD_ID, USE_TYPE)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.SUB_STATUS
(
	ID VARCHAR(32) NOT NULL,
	DEFINE_ID VARCHAR(32) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	STATUS VARCHAR(20) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.TASK_OPER
(
	ID VARCHAR(32) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	OPER_TIME TIMESTAMP NOT NULL,
	EMP_CODE VARCHAR(32) NOT NULL,
	EMP_NAME VARCHAR(50) NOT NULL,
	TASK_NAME VARCHAR(100),
	COMMENTS VARCHAR(500),
	OPER_TYPE VARCHAR(20) NOT NULL,
	TASK_DESCRIPTION VARCHAR(500),
	TASK_ID BIGINT,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.UPLOAD_FILE
(
	FILE_NAME VARCHAR(255) NOT NULL,
	EMP_NAME VARCHAR(50) NOT NULL,
	EMP_CODE VARCHAR(32) NOT NULL,
	MD5 VARCHAR(500) NOT NULL,
	BUSINESS_FILE_ID VARCHAR(32) NOT NULL,
	ID VARCHAR(32) NOT NULL,
	SIZE BIGINT,
	UPLOAD_TIME TIMESTAMP NOT NULL,
	PRI INT NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;



/* Create Foreign Keys */

ALTER TABLE HOUSE_OWNER_RECORD.SELL_TYPE_TOTAL
	ADD FOREIGN KEY (BUILD_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUILD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.EXCEPT_HOUSE
	ADD FOREIGN KEY (BUILD)
	REFERENCES HOUSE_OWNER_RECORD.BUILD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.UPLOAD_FILE
	ADD FOREIGN KEY (BUSINESS_FILE_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_FILE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.REPAIR_MONEY_PAY
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_CONTRACT
	ADD FOREIGN KEY (ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.OUTSIDE_REG_INFO
	ADD FOREIGN KEY (ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.ADD_HOUSE_STATUS
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.CONTRACT_NUMBER
	ADD FOREIGN KEY (CONTRACT)
	REFERENCES HOUSE_OWNER_RECORD.CONTRACT_SUBMIT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_MONEY
	ADD FOREIGN KEY (FEE)
	REFERENCES HOUSE_OWNER_RECORD.FACT_MONEYINFO (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.MORTGAEGE_REGISTE
	ADD FOREIGN KEY (OLD_FIN)
	REFERENCES HOUSE_OWNER_RECORD.FINANCIAL (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.MORTGAEGE_REGISTE
	ADD FOREIGN KEY (FIN)
	REFERENCES HOUSE_OWNER_RECORD.FINANCIAL (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_OWNER
	ADD FOREIGN KEY (HOUSE)
	REFERENCES HOUSE_OWNER_RECORD.HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE
	ADD FOREIGN KEY (START_HOUSE)
	REFERENCES HOUSE_OWNER_RECORD.HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_MORTGAEGE
	ADD FOREIGN KEY (HOUSE)
	REFERENCES HOUSE_OWNER_RECORD.HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE
	ADD FOREIGN KEY (AFTER_HOUSE)
	REFERENCES HOUSE_OWNER_RECORD.HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_RECORD
	ADD FOREIGN KEY (HOUSE)
	REFERENCES HOUSE_OWNER_RECORD.HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_SOURCE
	ADD FOREIGN KEY (HOUSE)
	REFERENCES HOUSE_OWNER_RECORD.HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.MONEY_BUSINESS
	ADD FOREIGN KEY (CONTRACT)
	REFERENCES HOUSE_OWNER_RECORD.HOUSE_CONTRACT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.CONTRACT_SUBMIT
	ADD FOREIGN KEY (ID)
	REFERENCES HOUSE_OWNER_RECORD.HOUSE_CONTRACT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE
	ADD FOREIGN KEY (REG_INFO)
	REFERENCES HOUSE_OWNER_RECORD.HOUSE_REG_INFO (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE
	ADD FOREIGN KEY (LAND_INFO)
	REFERENCES HOUSE_OWNER_RECORD.LAND_INFO (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.PROJECT_CARD
	ADD FOREIGN KEY (ID)
	REFERENCES HOUSE_OWNER_RECORD.MAKE_CARD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.FINANCIAL
	ADD FOREIGN KEY (CARD)
	REFERENCES HOUSE_OWNER_RECORD.MAKE_CARD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.POWER_OWNER
	ADD FOREIGN KEY (CARD)
	REFERENCES HOUSE_OWNER_RECORD.MAKE_CARD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.CARD_INFO
	ADD FOREIGN KEY (ID)
	REFERENCES HOUSE_OWNER_RECORD.MAKE_CARD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.REG_INFO
	ADD FOREIGN KEY (ID)
	REFERENCES HOUSE_OWNER_RECORD.MONEY_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.MONEY_PAY_INFO
	ADD FOREIGN KEY (ID)
	REFERENCES HOUSE_OWNER_RECORD.MONEY_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.PROJECT_MORTGAGE
	ADD FOREIGN KEY (ID)
	REFERENCES HOUSE_OWNER_RECORD.MORTGAEGE_REGISTE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_MORTGAEGE
	ADD FOREIGN KEY (MORTGAEGE)
	REFERENCES HOUSE_OWNER_RECORD.MORTGAEGE_REGISTE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_FILE
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.OWNER_BUSINESS
	ADD FOREIGN KEY (SELECT_BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.MAPPING_CORP
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_SOURCE
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.MONEY_BUSINESS
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.LEASE
	ADD FOREIGN KEY (BUISINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_CLOSE_CANCEL
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.CARD
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.PROCESS_MESSAGES
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.FACT_MONEYINFO
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.SUB_STATUS
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.REASON
	ADD FOREIGN KEY (BUISINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.RECORD_STORE
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.EVALUATE
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.MORTGAEGE_REGISTE
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.CLOSE_HOUSE
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.TASK_OPER
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_PERSION
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_MONEY
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.GIVE_CARD
	ADD FOREIGN KEY (BUISINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.PROJECT
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.MAKE_CARD
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_EMP
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE
	ADD FOREIGN KEY (MAIN_OWNER)
	REFERENCES HOUSE_OWNER_RECORD.POWER_OWNER (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_OWNER
	ADD FOREIGN KEY (POOL)
	REFERENCES HOUSE_OWNER_RECORD.POWER_OWNER (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.PROJECT_SELL_INFO
	ADD FOREIGN KEY (ID)
	REFERENCES HOUSE_OWNER_RECORD.PROJECT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.MONEY_SAFE
	ADD FOREIGN KEY (ID)
	REFERENCES HOUSE_OWNER_RECORD.PROJECT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUILD
	ADD FOREIGN KEY (PROJECT)
	REFERENCES HOUSE_OWNER_RECORD.PROJECT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.PROJECT_CARD
	ADD FOREIGN KEY (PROJECT)
	REFERENCES HOUSE_OWNER_RECORD.PROJECT_SELL_INFO (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.LAND_END_TIME
	ADD FOREIGN KEY (PROJECT_ID)
	REFERENCES HOUSE_OWNER_RECORD.PROJECT_SELL_INFO (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.FINANCIAL
	ADD CONSTRAINT FINANCIAL_PROXY_IBFK FOREIGN KEY (PROXY_PERSON)
	REFERENCES HOUSE_OWNER_RECORD.PROXY_PERSON (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.POWER_OWNER
	ADD FOREIGN KEY (PROXY_PERSON)
	REFERENCES HOUSE_OWNER_RECORD.PROXY_PERSON (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_PERSION
	ADD CONSTRAINT BUSINESS_PERSON_IBFK FOREIGN KEY (PROXY_PERSON)
	REFERENCES HOUSE_OWNER_RECORD.PROXY_PERSON (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.RECORD_STORE
	ADD FOREIGN KEY (FRAME)
	REFERENCES HOUSE_OWNER_RECORD.RECORD_FRAME (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.RECORD_FRAME
	ADD FOREIGN KEY (ROOM)
	REFERENCES HOUSE_OWNER_RECORD.RECORD_ROOM (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_FILE
	ADD FOREIGN KEY (RECORD_STORE)
	REFERENCES HOUSE_OWNER_RECORD.RECORD_STORE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.REPAIR_MONEY_INFO
	ADD FOREIGN KEY (ID)
	REFERENCES HOUSE_OWNER_RECORD.REPAIR_MONEY_PAY (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



/* Create Indexes */

CREATE INDEX BUSINESS_HOUSE_CODE_INDEX USING BTREE ON HOUSE_OWNER_RECORD.BUSINESS_HOUSE (HOUSE_CODE ASC);
CREATE INDEX OWNER_BUSINESS_REG_TIME_INDEX ON HOUSE_OWNER_RECORD.OWNER_BUSINESS (REG_TIME ASC);



