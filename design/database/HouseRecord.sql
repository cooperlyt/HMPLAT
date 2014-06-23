CREATE DATABASE IF NOT EXISTS `HOUSE_OWNER_RECORD`  DEFAULT CHARACTER SET utf8 ;

USE `HOUSE_OWNER_RECORD`;


SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE HOUSE_OWNER_RECORD.SIMPLE_VAR_STORE;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_OWNER;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE_MONEY;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_POOL_BUILD;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_POOL_OWNER;
DROP TABLE HOUSE_OWNER_RECORD.RECORD_AND_HOUSE_HISTROY;
DROP TABLE HOUSE_OWNER_RECORD.HOUSE_CARD;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE_STATE;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_BUILD;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_PROJECT;
DROP TABLE HOUSE_OWNER_RECORD.RECORD;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_LAND_INFO;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_PROJECT_CARD;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_CARD;
DROP TABLE HOUSE_OWNER_RECORD.DGBIZFILE;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_CARD_INFO;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS;




/* Create Tables */

CREATE TABLE HOUSE_OWNER_RECORD.SIMPLE_VAR_STORE
(
	-- 倒库导入原库的NO
	ID VARCHAR(32) NOT NULL COMMENT 'ID : 倒库导入原库的NO',
	TITLE VARCHAR(50) NOT NULL COMMENT 'TITLE',
	CATEGORY VARCHAR(50) NOT NULL COMMENT '环节名称',
	TYPE INT(4) NOT NULL COMMENT 'TYPE',
	VALUE VARCHAR(500) COMMENT 'VALUE',
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT 'BUSINESS : 倒库导入原库的NO',
	NO VARCHAR(32) NOT NULL COMMENT 'NO',
	PRIMARY KEY (ID),
	CONSTRAINT BUSINESS_NO_UNIQUE UNIQUE (BUSINESS, NO)
) ENGINE = InnoDB COMMENT = '基本变量' DEFAULT CHARACTER SET utf8;


-- 按人查档
CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_OWNER
(
	-- 倒库导入原库的NO
	ID VARCHAR(32) NOT NULL COMMENT 'ID : 倒库导入原库的NO',
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT 'BUSINESS : 倒库导入原库的NO',
	TYPE INT COMMENT '业主类型',
	NAME VARCHAR(100) COMMENT '名称',
	CREDENTIALS_TYPE INT COMMENT '证件类型',
	CREDENTIALS_NUMBER VARCHAR(50) COMMENT '证件号',
	SEX BIGINT COMMENT '性别',
	PHONE VARCHAR(25) COMMENT '电话',
	CITY VARCHAR(50) COMMENT '产权人所在城市',
	ADDRESS VARCHAR(200) COMMENT '地址',
	NATION VARCHAR(20) COMMENT '民族',
	BIRTHDAY TIMESTAMP COMMENT '出生日期',
	DOMICILE BIGINT COMMENT '户籍所在地',
	MEMO VARCHAR(200) COMMENT 'MEMO',
	-- 参与方式
	JOIN_TYPE INT NOT NULL COMMENT '参与方式 : 参与方式',
	-- 与空间库的人员表关联
	PRSON_ID VARCHAR(32) NOT NULL COMMENT 'PRSON_ID : 与空间库的人员表关联',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = 'BUSINESS_OWNER : 按人查档' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE_MONEY
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT 'BUSINESS : 倒库导入原库的NO',
	MONEY_TYPE_ID VARCHAR(20) COMMENT '收费项目',
	SHOULD_MONEY DECIMAL(18,3) COMMENT '应收金额',
	FACT_MONEY DECIMAL(18,3) COMMENT '实收金额',
	CHARGE_DETAILS VARCHAR(200) COMMENT '收费细节',
	MEMO VARCHAR(200) COMMENT 'MEMO',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = 'BUSINESS_HOUSE_MONEY' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_POOL_BUILD
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	PROJECT_ID VARCHAR(32) NOT NULL COMMENT 'PROJECT_ID',
	MAP_NUMBER VARCHAR(50) COMMENT '图号',
	BLOCK_NO VARCHAR(50) COMMENT '丘号',
	BUILD_NO VARCHAR(50) COMMENT '幢号',
	HOUSE_NUMBER VARCHAR(50) COMMENT '房屋编号',
	BUILD_NAME VARCHAR(50) COMMENT '楼幢名称',
	STRUCTURE VARCHAR(32) COMMENT '结构',
	ADDRESS VARCHAR(200) COMMENT '地址',
	AREA DECIMAL(18,3) COMMENT '面积',
	MEMO VARCHAR(200) COMMENT '备注',
	CONSTRAINT PK_POOLBUILD PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '共有建筑' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_PROJECT
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	NAME VARCHAR(50) NOT NULL COMMENT '项目名称',
	ADDRESS VARCHAR(200) COMMENT '地址',
	OPEN_TIME TIMESTAMP COMMENT '开盘时间',
	BUILD_SIZE VARCHAR(32) COMMENT '建筑规模',
	BUILD_COUNT INT COMMENT '项目栋数',
	FINISH_DATE TIMESTAMP COMMENT '峻工日期',
	AREA DECIMAL(18,3) COMMENT '面积',
	SUM_AREA DECIMAL(18,3) COMMENT '总面积',
	COME_DATE TIMESTAMP COMMENT '入住日期',
	STATE INT NOT NULL COMMENT 'STATE',
	MEMO VARCHAR(200) COMMENT '备注',
	MAP_TIME TIMESTAMP COMMENT '测绘时间',
	MAP_MEMO VARCHAR(200) COMMENT 'MAP_MEMO',
	CAN_SELL BIT(1) COMMENT '是否可售',
	SECTION_NAME VARCHAR(100) COMMENT '小区名称',
	DISTRICT_NAME VARCHAR(100) COMMENT '城区名称',
	DEVELOPER_NAME VARCHAR(100) COMMENT '开发商名称',
	PROJECT_ID VARCHAR(32) COMMENT 'PROJECT_ID',
	CONSTRAINT PK_PROJECT PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '项目' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.RECORD
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	NO VARCHAR(100) NOT NULL COMMENT '档案编号',
	FRAME VARCHAR(20) COMMENT '架号',
	CABINET VARCHAR(20) COMMENT '柜号',
	VOLUME VARCHAR(20) COMMENT '卷号',
	HOUSE_ID VARCHAR(32) NOT NULL COMMENT '空间库房屋ID',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = 'RECORD' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.RECORD_AND_HOUSE_HISTROY
(
	RECORD_ID VARCHAR(32) NOT NULL COMMENT '档案ID',
	HOUSE_ID VARCHAR(32) NOT NULL COMMENT 'HOUSE_ID',
	PRIMARY KEY (RECORD_ID)
) ENGINE = InnoDB COMMENT = 'Record_AND_HOUSE_HISTROY' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.HOUSE_CARD
(
	CARD_ID VARCHAR(32) NOT NULL COMMENT 'CARD_ID',
	HOUSE_ID VARCHAR(32) NOT NULL COMMENT 'HOUSE_ID',
	PRIMARY KEY (CARD_ID, HOUSE_ID)
) ENGINE = InnoDB COMMENT = 'HOUSE_CARD' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_BUILD
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	MAP_NUMBER VARCHAR(50) NOT NULL COMMENT '图号',
	BLOCK_NO VARCHAR(50) NOT NULL COMMENT '丘号',
	BUILD_NO VARCHAR(50) NOT NULL COMMENT '幢号',
	BUILD_NAME VARCHAR(100) NOT NULL COMMENT '楼幢名称',
	PROJECT_ID VARCHAR(32) NOT NULL COMMENT '项目ID',
	DOOR_NO VARCHAR(10) COMMENT '门牌号',
	UNINT_COUNT INT COMMENT '单元数',
	FLOOR_COUNT INT COMMENT '层数',
	BUILD_ADDRESS VARCHAR(50) COMMENT '楼幢地址',
	HOUSE_COUNT INT COMMENT '套数',
	AREA DECIMAL(18,3) COMMENT '面积',
	BUILD_TYPE VARCHAR(32) COMMENT 'BUILD_TYPE',
	STRUCTURE VARCHAR(32) COMMENT '结构',
	MEMO VARCHAR(200) COMMENT '备注',
	LAND_ID VARCHAR(32) COMMENT '土地',
	HOME_COUNT INT COMMENT '住宅套数',
	HOME_AREA DECIMAL(18,3) COMMENT '住宅面积',
	UNHOME_COUNT INT COMMENT '非住宅套数',
	UNHOME_AREA DECIMAL(18,3) COMMENT '非住宅面积',
	SHOP_COUNT INT COMMENT '网点套数',
	SHOP_AREA DECIMAL(18,3) COMMENT '网点面积',
	CARD_ID VARCHAR(32) COMMENT 'CARD_ID',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) COMMENT 'BUSINESS_ID : 倒库导入原库的NO',
	CONSTRAINT PK_BUILD PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '楼幢表' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	BUILDID VARCHAR(32) NOT NULL COMMENT '楼幢ID',
	HOUSE_NUMBER VARCHAR(50) COMMENT '房屋编号',
	HOUSE_ORDER VARCHAR(20) NOT NULL COMMENT '房号',
	HOUSE_UNIT_NAME VARCHAR(20) COMMENT '单元名',
	IN_FLOOR_NAME VARCHAR(50) COMMENT '所在层名称',
	HOUSE_AREA DECIMAL(18,3) NOT NULL COMMENT '建筑面积',
	PREPARE_AREA DECIMAL(18,3) COMMENT '预测面积',
	TEMP_AREA DECIMAL(18,3) COMMENT '备案面积',
	USE_AREA DECIMAL(18,3) COMMENT '使用面积',
	COMM_AREA DECIMAL(18,3) COMMENT '分摊面积',
	SHINE_AREA DECIMAL(18,10) COMMENT '阳台面积',
	LOFT_AREA DECIMAL(18,3) COMMENT '阁楼面积',
	COMM_PARAM DECIMAL(18,3) COMMENT '分摊系数',
	PRIVATE_AREA DECIMAL(18,3) COMMENT '私有面积',
	HOUSE_STATE INT NOT NULL COMMENT '房屋状态',
	DOOR_NO VARCHAR(20) COMMENT '门牌号',
	HOUSE_TYPE VARCHAR(32) COMMENT '房屋类型',
	USE_TYPE VARCHAR(32) COMMENT '设计用途',
	HOUSE_PORPERTY VARCHAR(32) COMMENT '产别',
	STRUCTURE VARCHAR(32) COMMENT '结构',
	KNOT_SIZE VARCHAR(32) COMMENT '套型',
	HOUSE_FROM VARCHAR(32) COMMENT '房屋来源',
	HOUSE_STATION VARCHAR(200) COMMENT '房屋坐落',
	LAND_INFO VARCHAR(32) COMMENT '土地',
	DATA_SOURCE VARCHAR(32) COMMENT '数据来源',
	SOUTH_NEXT VARCHAR(50) COMMENT '南临',
	WEST_NEXT VARCHAR(50) COMMENT '西临',
	NORTH_NEXT VARCHAR(50) COMMENT '北临',
	EAST_NEXT VARCHAR(50) COMMENT '东临',
	EAST_WALL VARCHAR(32) COMMENT '东墙',
	WEST_WALL VARCHAR(32) COMMENT '西墙',
	SOUTH_WALL VARCHAR(32) COMMENT '南墙',
	NORTH_WALL VARCHAR(32) COMMENT '北墙',
	MAP_TIME TIMESTAMP COMMENT '测绘时间',
	DIRECTION VARCHAR(32) COMMENT '朝向',
	INIT_REGISTER BIT(1) NOT NULL COMMENT '初始登记',
	INIT_REGISTER_BUSINESS_CODE VARCHAR(32) COMMENT '初始登记业务编号',
	FIRMLY_POWER BIT(1) NOT NULL COMMENT '是否确权',
	FIRMLY_POWER_BUSINESS_CODE VARCHAR(32) COMMENT '确权业务编码',
	OUT_PLAN BIT(1) NOT NULL COMMENT '超规划',
	SUM_PRICE DECIMAL(18,3) COMMENT '购房款',
	PAY_TYPE VARCHAR(32) COMMENT '购买方式',
	OWNER_ID VARCHAR(32) NOT NULL COMMENT '产权人',
	MEMO VARCHAR(200) COMMENT '备注',
	RECORD_ID VARCHAR(32) NOT NULL COMMENT '备案人',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID : 倒库导入原库的NO',
	CONSTRAINT PK_HOUSE PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '房屋表' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_LAND_INFO
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	LAND_CARD_NO VARCHAR(50) COMMENT '土地证号',
	NUMBER VARCHAR(50) COMMENT '地号',
	LAND_PROPERTY VARCHAR(32) COMMENT '土地性质',
	BEGIN_USE_TIME TIMESTAMP COMMENT '土地使用年限始',
	END_USE_TIME TIMESTAMP COMMENT '土地使用年限止',
	AREA DECIMAL(18,3) COMMENT '面积',
	LAND_GET_MODE VARCHAR(32) COMMENT '取得方式',
	MEMO VARCHAR(200) COMMENT '备注',
	CONSTRAINT PK_LANDINFO PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = 'BUSINESS_LAND_INFO' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_PROJECT_CARD
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	CARD_ID VARCHAR(32) NOT NULL COMMENT 'CARD_ID',
	HOUSE_COUNT INT COMMENT '套数',
	BUILD_COUNT INT COMMENT '栋数',
	AREA DECIMAL(18,3) COMMENT '面积',
	PREPARE_SELL BIT(1) COMMENT '是否预售',
	USE_TYPE VARCHAR(32) COMMENT '规划用途',
	SELL_OBJECT VARCHAR(50) COMMENT '销售对象',
	YEAR_NUMBER VARCHAR(20) COMMENT '年号',
	ORDER_NUMBER VARCHAR(20) COMMENT '第号',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = 'BUSINESS_PROJECT_CARD' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_CARD
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	NUMBER VARCHAR(50) COMMENT '权证号',
	CARD_CODE VARCHAR(50) COMMENT '证编号',
	TYPE INT NOT NULL COMMENT '类型',
	BUSINESS_CODE VARCHAR(32) COMMENT '业务编号',
	PRINT_TIME TIMESTAMP COMMENT '填发时间',
	MEMO VARCHAR(200) COMMENT '备注',
	CONSTRAINT PK_PROJECTCARD PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = 'BUSINESS_CARD' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_POOL_OWNER
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	RELATION VARCHAR(32) COMMENT '与所有人关系',
	OWNE_SCALE DECIMAL(19,4) COMMENT '所占份额',
	HOUSE_ID VARCHAR(32) NOT NULL COMMENT 'HOUSE_ID',
	CARD_ID VARCHAR(32) NOT NULL COMMENT 'CARD_ID',
	OWNER_ID VARCHAR(32) NOT NULL COMMENT 'OWNER_ID',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '共有权人' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE_STATE
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	HOUSEID VARCHAR(32) NOT NULL COMMENT '房屋ID',
	STATE INT NOT NULL COMMENT 'STATE',
	BUSINESS_ID VARCHAR(32) COMMENT '业务ID',
	VERSION INT NOT NULL COMMENT 'VERSION',
	CONSTRAINT PK_HOUSESTATE PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '房屋状态关联表' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.DGBIZFILE
(
	-- 倒库导入原库的NO
	ID VARCHAR(32) NOT NULL COMMENT 'ID : 倒库导入原库的NO',
	FILENAME VARCHAR(200) COMMENT 'FileName',
	FILETYPE INT COMMENT 'FileType',
	UPDATEDATE TIMESTAMP COMMENT 'UpdateDate',
	MD5CODE VARCHAR(500) COMMENT 'MD5Code',
	FILEFROM INT COMMENT 'FileFrom',
	EMPLOYEEID INT COMMENT 'EmployeeID',
	DOCID INT COMMENT 'DocID',
	SRCFILENAME VARCHAR(100) COMMENT 'SrcFileName',
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT 'BUSINESS : 倒库导入原库的NO',
	CONSTRAINT PK_DGBIZFILE PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = 'DGBizFile' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_CARD_INFO
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	NUMBER VARCHAR(50) NOT NULL COMMENT '证号',
	CARD_CODE VARCHAR(32) NOT NULL COMMENT '证编号',
	TYPE INT COMMENT 'TYPE',
	RECEIVE_NAME VARCHAR(50) COMMENT '领证人',
	RECEIVE_TIME TIMESTAMP COMMENT '领证时间',
	RECEIVE_PHONE VARCHAR(30) COMMENT '领证人电话',
	CARD_ID VARCHAR(32) COMMENT '证ID',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID : 倒库导入原库的NO',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = 'BUSINESS_CARD_INFO' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS
(
	-- 倒库导入原库的NO
	ID VARCHAR(32) NOT NULL COMMENT 'ID : 倒库导入原库的NO',
	-- 倒库导入原库的NO
	BEFORE_BUSINESS_ID VARCHAR(32) COMMENT '启动业务ID : 倒库导入原库的NO',
	SOURCE INT NOT NULL COMMENT '业务来源',
	RECORD_TIME TIMESTAMP NOT NULL COMMENT '归档时间',
	ADD_BIZ_TIME TIMESTAMP COMMENT '补录时间',
	PROCESS_MESSAGE VARCHAR(400) COMMENT '流程提示',
	ENABLE BOOLEAN NOT NULL COMMENT '是否有效',
	MAPPING_COMPANY VARCHAR(32) COMMENT '测绘机构',
	EVALUATE_COMPANY VARCHAR(32) COMMENT '评估机构',
	MEMO VARCHAR(200) COMMENT '业务备注',
	VERSION INT COMMENT 'VERSION',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '业务' DEFAULT CHARACTER SET utf8;



/* Create Foreign Keys */

ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_POOL_BUILD
	ADD FOREIGN KEY (PROJECT_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_PROJECT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_BUILD
	ADD CONSTRAINT FK_BUILD_REFERENCE_PROJECT FOREIGN KEY (PROJECT_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_PROJECT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.RECORD_AND_HOUSE_HISTROY
	ADD FOREIGN KEY (RECORD_ID)
	REFERENCES HOUSE_OWNER_RECORD.RECORD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE
	ADD CONSTRAINT FK_HOUSE_HOUSEANDB_BUILD FOREIGN KEY (BUILDID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_BUILD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_POOL_OWNER
	ADD FOREIGN KEY (HOUSE_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.RECORD_AND_HOUSE_HISTROY
	ADD FOREIGN KEY (HOUSE_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_CARD
	ADD FOREIGN KEY (HOUSE_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE_STATE
	ADD CONSTRAINT FK_HOUSESTA_REFERENCE_HOUSE FOREIGN KEY (HOUSEID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE
	ADD CONSTRAINT FK_HOUSE_REFERENCE_LANDINFO FOREIGN KEY (LAND_INFO)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_LAND_INFO (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_BUILD
	ADD FOREIGN KEY (LAND_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_LAND_INFO (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_BUILD
	ADD FOREIGN KEY (CARD_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_PROJECT_CARD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_CARD
	ADD FOREIGN KEY (CARD_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_CARD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_PROJECT_CARD
	ADD FOREIGN KEY (CARD_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_CARD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_POOL_OWNER
	ADD FOREIGN KEY (CARD_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_CARD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE_MONEY
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_BUILD
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS
	ADD FOREIGN KEY (BEFORE_BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_CARD_INFO
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_OWNER
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.DGBIZFILE
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.SIMPLE_VAR_STORE
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



