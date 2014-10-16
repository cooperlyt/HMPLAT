SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE HOUSE_OWNER_RECORD.CARD;
DROP TABLE HOUSE_OWNER_RECORD.REASON;
DROP TABLE HOUSE_OWNER_RECORD.EVALUATE;
DROP TABLE HOUSE_OWNER_RECORD.MAPPING_CORP;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_EMP;
DROP TABLE HOUSE_OWNER_RECORD.UPLOAD_FILES;
DROP TABLE HOUSE_OWNER_RECORD.HOUSE_STATE;
DROP TABLE HOUSE_OWNER_RECORD.HOUSE_CLOSE_CANCEL;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_PERSION;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_MONEY;
DROP TABLE HOUSE_OWNER_RECORD.NEW_HOUSE_CONTRACT;
DROP TABLE HOUSE_OWNER_RECORD.CLOSE_HOUSE;
DROP TABLE HOUSE_OWNER_RECORD.MORTGAEGE_REGISTE;
DROP TABLE HOUSE_OWNER_RECORD.SALE_INFO;
DROP TABLE HOUSE_OWNER_RECORD.NUMBER_POOL;
DROP TABLE HOUSE_OWNER_RECORD.TASK_OPER;
DROP TABLE HOUSE_OWNER_RECORD.REGISTER_PROPERTY;
DROP TABLE HOUSE_OWNER_RECORD.RECORD_STORE;
DROP TABLE HOUSE_OWNER_RECORD.HOUSE_RECORD;
DROP TABLE HOUSE_OWNER_RECORD.PROJECT_RECORD_STORE;
DROP TABLE HOUSE_OWNER_RECORD.PROJECT_RECORD;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_AND_POOL;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE;
DROP TABLE HOUSE_OWNER_RECORD.BUILD;
DROP TABLE HOUSE_OWNER_RECORD.PROJECT_SELL_CARD;
DROP TABLE HOUSE_OWNER_RECORD.PROJECT;
DROP TABLE HOUSE_OWNER_RECORD.HOUSE_AND_POOL;
DROP TABLE HOUSE_OWNER_RECORD.OTHER_POWER_CARD_AND_HOUSE;
DROP TABLE HOUSE_OWNER_RECORD.HOUSE;
DROP TABLE HOUSE_OWNER_RECORD.LAND_INFO;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_OWNER;
DROP TABLE HOUSE_OWNER_RECORD.OTHER_POWER_CARD;
DROP TABLE HOUSE_OWNER_RECORD.BUSINESS_POOL;
DROP TABLE HOUSE_OWNER_RECORD.MAKE_CARD;
DROP TABLE HOUSE_OWNER_RECORD.FINANCIAL;
DROP TABLE HOUSE_OWNER_RECORD.OWNER_BUSINESS;
DROP TABLE HOUSE_OWNER_RECORD.BANK_FORK;




/* Create Tables */

-- 记录业务关联时，权证号，和同同，法律文件号，等相关证件的正价编号
CREATE TABLE HOUSE_OWNER_RECORD.CARD
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	TYPE VARCHAR(20) NOT NULL COMMENT 'TYPE',
	NUMBER VARCHAR(100) NOT NULL COMMENT 'NUMBER',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '业务证书号' DEFAULT CHARACTER SET utf8;


-- 记录注销原因，更正前，更正后，变更前，变更后等事由信息
CREATE TABLE HOUSE_OWNER_RECORD.REASON
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	TYPE VARCHAR(20) NOT NULL COMMENT '事由类型',
	REASON VARCHAR(200) COMMENT '事由',
	-- 倒库导入原库的NO
	BUISINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUISINESS_ID',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '事由表' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.EVALUATE
(
	EVALUATE_CORP_NAME VARCHAR(60) COMMENT '评估机构名称',
	EVALUATE_CORP_N0 VARCHAR(32) COMMENT '评估公司编号',
	ASSESSMENT_PRICE DECIMAL(19,4) NOT NULL COMMENT '评估价格',
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '评估' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.MAPPING_CORP
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	NAME VARCHAR(100) NOT NULL COMMENT '名称',
	CODE VARCHAR(32) NOT NULL COMMENT 'CODE',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '测绘机构' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_EMP
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	TYPE VARCHAR(20) NOT NULL COMMENT 'TYPE',
	TASK_NAME VARCHAR(100) NOT NULL COMMENT 'TASK_NAME',
	EMP_CODE VARCHAR(32) NOT NULL COMMENT 'EMP_CODE',
	EMP_NAME VARCHAR(50) NOT NULL COMMENT 'EMP_NAME',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = 'BUSINESS_EMP' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.UPLOAD_FILES
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID',
	NAME VARCHAR(100) NOT NULL COMMENT 'NAME',
	TYPE VARCHAR(20) NOT NULL COMMENT 'TYPE',
	EMP_NAME VARCHAR(50) NOT NULL COMMENT 'EMP_NAME',
	EMP_CODE VARCHAR(32) NOT NULL COMMENT 'EMP_CODE',
	MD5 VARCHAR(200) NOT NULL COMMENT 'MD5',
	FILE_NAME VARCHAR(255) NOT NULL COMMENT 'FILE_NAME',
	IMPORTANT BOOLEAN NOT NULL COMMENT 'IMPORTANT',
	MEMO VARCHAR(200) COMMENT 'MEMO',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = 'UPLOAD_FILES' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.HOUSE_STATE
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	HOUSE VARCHAR(32) NOT NULL COMMENT 'HOUSE',
	HOUSE_STATUS VARCHAR(32) NOT NULL COMMENT '房屋状态',
	CONSTRAINT PK_HOUSESTATE PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '房屋状态关联表' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.HOUSE_CLOSE_CANCEL
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	CANCEL_DATE DATETIME NOT NULL COMMENT '解封时间',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '解封登记' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_PERSION
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	ID_NO VARCHAR(100) NOT NULL COMMENT '证件号',
	ID_TYPE VARCHAR(32) NOT NULL COMMENT '证件类型',
	NAME VARCHAR(50) NOT NULL COMMENT '姓名',
	TYPE VARCHAR(20) NOT NULL COMMENT '类型',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID',
	PHONE VARCHAR(15) COMMENT 'PHONE',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '业务相关人' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_MONEY
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	MONEY_TYPE_ID VARCHAR(20) NOT NULL COMMENT '收费项目',
	SHOULD_MONEY DECIMAL(18,3) COMMENT '应收金额',
	FACT_MONEY DECIMAL(18,3) COMMENT '实收金额',
	CHARGE_DETAILS VARCHAR(200) COMMENT '收费细节',
	MEMO VARCHAR(200) COMMENT 'MEMO',
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT 'BUSINESS',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '收费表' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.NEW_HOUSE_CONTRACT
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	COMPACT_NO VARCHAR(30) NOT NULL COMMENT '商品房合同备案号',
	SIGN_DATE DATETIME NOT NULL COMMENT '签约时间',
	RECORD_DATE DATETIME NOT NULL COMMENT '备案时间',
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '商品房合同备案' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.CLOSE_HOUSE
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	CLOSE_DOWN_CLOUR VARCHAR(32) NOT NULL COMMENT '查封法院',
	ACTION VARCHAR(100) COMMENT '执行事项',
	CLOSE_DATE DATETIME NOT NULL COMMENT '查封时间',
	TO_DATE DATETIME COMMENT '查封期限',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '查封' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.MORTGAEGE_REGISTE
(
	HIGHEST_MOUNT_MONEY DECIMAL(19,4) NOT NULL COMMENT '债权数额',
	WARRANT_SCOPE VARCHAR(100) NOT NULL COMMENT '担保范围',
	INTEREST_TYPE VARCHAR(32) NOT NULL COMMENT '权利种类',
	MORTGAGE_DUE_TIME_S DATETIME NOT NULL COMMENT '抵押时间始',
	MORTGAGE_TIME VARCHAR(50) NOT NULL COMMENT '抵押时间',
	MORTGAGE_AREA DECIMAL(19,4) NOT NULL COMMENT '抵押面积',
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '抵押登记' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.SALE_INFO
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	PAY_TYPE VARCHAR(32) COMMENT '付款方式',
	SUM_PRICE DECIMAL(19,4) NOT NULL COMMENT '购房款',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '房屋交易信息' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.NUMBER_POOL
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	NUMBER BIGINT UNSIGNED NOT NULL COMMENT 'NUMBER',
	VERSION INT COMMENT 'VERSION',
	TYPE VARCHAR(20) NOT NULL COMMENT 'TYPE',
	USE_TIME TIMESTAMP NOT NULL COMMENT 'USE_TIME',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '号池' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.TASK_OPER
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT 'BUSINESS',
	OPER_TIME TIMESTAMP NOT NULL COMMENT 'OPER_TIME',
	EMP_CODE VARCHAR(32) NOT NULL COMMENT 'EMP_CODE',
	EMP_NAME VARCHAR(50) NOT NULL COMMENT 'EMP_NAME',
	TASK_NAME VARCHAR(100) NOT NULL COMMENT 'TASK_NAME',
	COMMENTS VARCHAR(500) COMMENT 'COMMENTS',
	OPER_TYPE VARCHAR(20) NOT NULL COMMENT 'OPER_TYPE',
	ACCEPT BOOLEAN NOT NULL COMMENT 'ACCEPT',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = 'TASK_OPER' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.REGISTER_PROPERTY
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID',
	POOL_MEMO VARCHAR(32) COMMENT '共有情况',
	HOUSE_PORPERTY VARCHAR(32) COMMENT '产别',
	HOUSE_FROM VARCHAR(32) COMMENT '产权来源',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '登记信息' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.RECORD_STORE
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	FRAME VARCHAR(10) NOT NULL COMMENT '架',
	CABINET VARCHAR(20) NOT NULL COMMENT '柜',
	BOX VARCHAR(50) NOT NULL COMMENT '盒',
	BUSINESS_HOUSE VARCHAR(32) NOT NULL COMMENT 'BUSINESS_HOUSE',
	HOUSE_RECORD VARCHAR(32) NOT NULL COMMENT 'HOUSE_RECORD',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = 'RECORD_STORE' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.HOUSE_RECORD
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	HOUSE_CODE VARCHAR(32) NOT NULL UNIQUE COMMENT '房屋编号',
	HOUSE VARCHAR(32) NOT NULL UNIQUE COMMENT 'HOUSE',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = 'HOUSE_RECORD' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.PROJECT_RECORD
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	SALE_CARD VARCHAR(32) COMMENT 'SALE_CARD',
	PROJECT_CODE VARCHAR(32) NOT NULL UNIQUE COMMENT 'PROJECT_CODE',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = 'PROJECT_RECORD' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	BUILD_ID VARCHAR(32) COMMENT 'BUILD_ID',
	HOUSE_CODE VARCHAR(32) NOT NULL COMMENT '房屋编号',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID',
	START_HOUSE VARCHAR(32) NOT NULL COMMENT 'START_HOUSE',
	AFTER_HOUSE VARCHAR(32) COMMENT 'AFTER_HOUSE',
	LAND_INFO VARCHAR(32) COMMENT 'LAND_INFO',
	HOUSE_OWNER VARCHAR(32) COMMENT 'HOUSE_OWNER',
	CONSTRAINT PK_HOUSE PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '房屋表' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.PROJECT_RECORD_STORE
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	FRAME VARCHAR(10) NOT NULL COMMENT '架',
	CABINET VARCHAR(20) NOT NULL COMMENT '柜',
	BOX VARCHAR(50) NOT NULL COMMENT '盒',
	RECORD VARCHAR(32) NOT NULL COMMENT 'RECORD',
	BUSINESS VARCHAR(32) NOT NULL COMMENT 'BUSINESS',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '项目档案存储' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.PROJECT
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	NAME VARCHAR(50) NOT NULL COMMENT '名称',
	ADDRESS VARCHAR(200) COMMENT '地址',
	BUILD_SIZE VARCHAR(32) COMMENT '建筑规模',
	BUILD_COUNT INT COMMENT '项目栋数',
	AREA DECIMAL(18,3) COMMENT '建筑面积',
	SUM_AREA DECIMAL(18,3) COMMENT '占地面积',
	MAP_TIME TIMESTAMP COMMENT '测绘时间',
	COMPLETE_DATE DATETIME COMMENT '竣工年份',
	DEVELOPER_NAME VARCHAR(100) COMMENT '开发商名称',
	DEVELOPER_CODE VARCHAR(32) COMMENT '开发商编号',
	SECTION_NAME VARCHAR(50) NOT NULL COMMENT '小区名称',
	SECTION_CODE VARCHAR(32) NOT NULL COMMENT '小区编号',
	DISTRICT_CODE VARCHAR(32) NOT NULL COMMENT '行政区编号',
	DISTRICT_NAME VARCHAR(100) NOT NULL COMMENT '行政区名称',
	PROJECT_CODE VARCHAR(32) NOT NULL COMMENT '项目编号',
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT 'BUSINESS',
	CONSTRAINT PK_PROJECT PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '项目' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.LAND_INFO
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	LAND_CARD_NO VARCHAR(50) COMMENT '土地证号',
	NUMBER VARCHAR(50) NOT NULL COMMENT '地号',
	LAND_PROPERTY VARCHAR(32) COMMENT '土地性质',
	BEGIN_USE_TIME TIMESTAMP NOT NULL COMMENT '土地使用年限始',
	END_USE_TIME TIMESTAMP NOT NULL COMMENT '土地使用年限止',
	LAND_GET_MODE VARCHAR(32) NOT NULL COMMENT '土地取得方式',
	LAND_AREA DECIMAL(18,3) COMMENT '土地面积',
	CONSTRAINT PK_LANDINFO PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '土地信息表' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.PROJECT_SELL_CARD
(
	HOUSE_COUNT INT COMMENT '套数',
	BUILD_COUNT INT COMMENT '栋数',
	AREA DECIMAL(18,3) COMMENT '面积',
	PREPARE_SELL BIT(1) COMMENT '是否预售',
	USE_TYPE VARCHAR(32) COMMENT '规划用途',
	SELL_OBJECT VARCHAR(50) COMMENT '销售对象',
	YEAR_NUMBER VARCHAR(20) COMMENT '年号',
	ORDER_NUMBER VARCHAR(20) COMMENT '第号',
	PRINT_TIME TIMESTAMP NOT NULL COMMENT '填发时间',
	MEMO VARCHAR(200) COMMENT '备注',
	PROJECT VARCHAR(32) NOT NULL COMMENT 'PROJECT',
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	TYPE VARCHAR(20) NOT NULL COMMENT 'TYPE',
	LAND_CARD_NO VARCHAR(50) COMMENT '土地证号',
	NUMBER VARCHAR(50) NOT NULL COMMENT '地号',
	LAND_PROPERTY VARCHAR(32) COMMENT '土地性质',
	BEGIN_USE_TIME TIMESTAMP NOT NULL COMMENT '土地使用年限始',
	END_USE_TIME TIMESTAMP NOT NULL COMMENT '土地使用年限止',
	LAND_GET_MODE VARCHAR(32) COMMENT '土地取得方式',
	LAND_AREA DECIMAL(18,3) COMMENT '土地面积',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = 'PROJECT_SELL_CARD' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUILD
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	LAND_BLOCK_CODE VARCHAR(4) COMMENT '宗地号',
	MAP_NUMBER VARCHAR(4) COMMENT '图号',
	BLOCK_NO VARCHAR(10) NOT NULL COMMENT '丘号',
	BUILD_NO VARCHAR(4) NOT NULL COMMENT '幢号',
	BUILD_CODE VARCHAR(32) NOT NULL COMMENT '楼幢编号',
	STREET_CODE VARCHAR(4) COMMENT '街坊号或房产分区代码',
	NAME VARCHAR(100) NOT NULL COMMENT '楼幢名称',
	DOOR_NO VARCHAR(32) COMMENT '门牌号',
	UNINT_COUNT INT COMMENT '单元数',
	FLOOR_COUNT INT NOT NULL COMMENT '总层数',
	UP_FLOOR_COUNT INT NOT NULL COMMENT '地上总层数',
	DOWN_FLOOR_COUNT INT NOT NULL COMMENT '地下总层数',
	ADDRESS VARCHAR(50) COMMENT '楼幢地址',
	HOUSE_COUNT INT COMMENT '套数',
	AREA DECIMAL(18,3) COMMENT '建筑面积',
	LNG DECIMAL(18,14) COMMENT 'LNG',
	LAT DECIMAL(18,14) COMMENT 'LAT',
	BUILD_TYPE VARCHAR(32) COMMENT '楼幢类型',
	STRUCTURE VARCHAR(32) NOT NULL COMMENT '结构',
	HOME_COUNT INT COMMENT '住宅套数',
	HOME_AREA DECIMAL(18,3) COMMENT '住宅面积',
	UNHOME_COUNT INT COMMENT '非住宅套数',
	UNHOME_AREA DECIMAL(18,3) COMMENT '非住宅面积',
	SHOP_COUNT INT COMMENT '网点套数',
	SHOP_AREA DECIMAL(18,3) COMMENT '网点面积',
	PROJECT VARCHAR(32) NOT NULL COMMENT 'PROJECT',
	COMPLETE_DATE VARCHAR(6) COMMENT '竣工日期',
	CONSTRAINT PK_BUILD PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '楼幢表' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.HOUSE
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	HOUSE_ORDER VARCHAR(20) NOT NULL COMMENT '房号',
	HOUSE_UNIT_NAME VARCHAR(20) COMMENT '单元名',
	IN_FLOOR_NAME VARCHAR(50) NOT NULL COMMENT '所在层名称',
	HOUSE_AREA DECIMAL(18,3) NOT NULL COMMENT '建筑面积',
	PREPARE_AREA DECIMAL(18,3) COMMENT '预测面积',
	USE_AREA DECIMAL(18,3) COMMENT '套内面积',
	COMM_AREA DECIMAL(18,3) COMMENT '分摊面积',
	SHINE_AREA DECIMAL(18,10) COMMENT '阳台面积',
	LOFT_AREA DECIMAL(18,3) COMMENT '阁楼面积',
	COMM_PARAM DECIMAL(18,3) COMMENT '分摊系数',
	HOUSE_TYPE VARCHAR(32) COMMENT '房屋性质',
	USE_TYPE VARCHAR(32) NOT NULL COMMENT '设计用途',
	STRUCTURE VARCHAR(32) NOT NULL COMMENT '结构',
	KNOT_SIZE VARCHAR(32) COMMENT '套型',
	ADDRESS VARCHAR(200) NOT NULL COMMENT '房屋坐落',
	EAST_WALL VARCHAR(32) COMMENT '东墙',
	WEST_WALL VARCHAR(32) COMMENT '西墙',
	SOUTH_WALL VARCHAR(32) COMMENT '南墙',
	NORTH_WALL VARCHAR(32) COMMENT '北墙',
	MAP_TIME TIMESTAMP COMMENT '测绘时间',
	DIRECTION VARCHAR(32) COMMENT '朝向',
	INIT_REGISTER BIT(1) NOT NULL COMMENT '初始登记',
	FIRMLY_POWER BIT(1) NOT NULL COMMENT '是否确权',
	HOUSE_CODE VARCHAR(32) NOT NULL COMMENT '房屋编号',
	POOL_MEMO VARCHAR(32) COMMENT '共有情况',
	HOUSE_FROM VARCHAR(32) COMMENT '产权来源',
	HOUSE_PORPERTY VARCHAR(32) COMMENT '产别',
	HAVE_DOWN_ROOM BOOLEAN NOT NULL COMMENT '有半地下室',
	BUILD_CODE VARCHAR(32) NOT NULL COMMENT '楼幢编号',
	LAND_BLOCK_CODE VARCHAR(4) COMMENT '宗地号',
	MAP_NUMBER VARCHAR(4) COMMENT '图号',
	BLOCK_NO VARCHAR(10) NOT NULL COMMENT '丘号',
	BUILD_NO VARCHAR(4) NOT NULL COMMENT '幢号',
	STREET_CODE VARCHAR(4) COMMENT '街坊号或房产分区代码',
	DOOR_NO VARCHAR(32) COMMENT '门牌号',
	UP_FLOOR_COUNT INT NOT NULL COMMENT '地上总层数',
	FLOOR_COUNT INT NOT NULL COMMENT '总层数',
	DOWN_FLOOR_COUNT INT NOT NULL COMMENT '地下总层数',
	BUILD_TYPE VARCHAR(32) COMMENT '楼幢类型',
	PROJECT_CODE VARCHAR(32) NOT NULL COMMENT '项目编号',
	PROJECT_NAME VARCHAR(50) NOT NULL COMMENT '项目名称',
	BUILD_SIZE VARCHAR(32) COMMENT '建筑规模',
	COMPLETE_DATE DATETIME COMMENT '竣工年份',
	DEVELOPER_CODE VARCHAR(32) COMMENT '开发商编号',
	DEVELOPER_NAME VARCHAR(100) COMMENT '开发商名称',
	SECTION_CODE VARCHAR(32) NOT NULL COMMENT '小区编号',
	SECTION_NAME VARCHAR(50) NOT NULL COMMENT '小区名称',
	DISTRICT_CODE VARCHAR(32) NOT NULL COMMENT '行政区编号',
	DISTRICT_NAME VARCHAR(100) NOT NULL COMMENT '行政区名称',
	BUILD_NAME VARCHAR(100) NOT NULL COMMENT '楼幢名称',
	HOUSE_STATUS VARCHAR(32) COMMENT '房屋状态',
	LAND_INFO VARCHAR(32) COMMENT 'LAND_INFO',
	HOUSE_OWNER VARCHAR(32) COMMENT 'HOUSE_OWNER',
	CONSTRAINT PK_HOUSE PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '房屋表' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_OWNER
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	NAME VARCHAR(50) NOT NULL COMMENT '姓名',
	ID_TYPE VARCHAR(32) NOT NULL COMMENT '证件类型',
	ID_NO VARCHAR(100) NOT NULL COMMENT '证件号',
	PHONE VARCHAR(15) NOT NULL COMMENT 'PHONE',
	ROOT_ADDRESS VARCHAR(50) COMMENT '户籍地',
	CARD VARCHAR(32) COMMENT 'CARD',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '产权人' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.HOUSE_AND_POOL
(
	POOL_OWNER VARCHAR(32) NOT NULL COMMENT 'POOL_OWNER',
	HOUSE VARCHAR(32) NOT NULL COMMENT 'HOUSE',
	PRIMARY KEY (POOL_OWNER, HOUSE)
) ENGINE = InnoDB COMMENT = 'HOUSE_AND_POOL' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.OTHER_POWER_CARD
(
	CARD VARCHAR(32) NOT NULL COMMENT 'CARD',
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	OWNER_CARD VARCHAR(32) NOT NULL COMMENT 'OWNER_CARD',
	FINANCIAL_NAME VARCHAR(120) NOT NULL COMMENT 'FINANCIAL_NAME',
	FINANCIAL_CODE VARCHAR(100) NOT NULL COMMENT 'FINANCIAL_CODE',
	FINANCIAL_PHONE VARCHAR(15) COMMENT 'FINANCIAL_PHONE',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '他项权证' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_AND_POOL
(
	BUSINESS VARCHAR(32) NOT NULL COMMENT 'BUSINESS',
	POOL_OWNER VARCHAR(32) NOT NULL COMMENT 'POOL_OWNER',
	PRIMARY KEY (BUSINESS, POOL_OWNER)
) ENGINE = InnoDB COMMENT = 'BUSINESS_AND_POOL' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BUSINESS_POOL
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	-- 原共有权人，现共有权人，备案共有权人
	TYPE VARCHAR(20) NOT NULL COMMENT '共有权人类型',
	NAME VARCHAR(50) NOT NULL COMMENT '姓名',
	ID_TYPE VARCHAR(32) NOT NULL COMMENT '证件类型',
	ID_NO VARCHAR(100) NOT NULL COMMENT '证件号',
	RELATION VARCHAR(32) COMMENT '共有关系',
	POOL_AREA DECIMAL(19,4) COMMENT '共有面积',
	PERC VARCHAR(10) COMMENT '所占份额',
	PHONE VARCHAR(15) COMMENT 'PHONE',
	CARD VARCHAR(32) COMMENT 'CARD',
	CREATE_TIME TIMESTAMP NOT NULL COMMENT 'CREATE_TIME',
	MEMO VARCHAR(200) COMMENT '备注',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '共有人' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.MAKE_CARD
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	TYPE VARCHAR(20) NOT NULL COMMENT 'TYPE',
	NUMBER VARCHAR(100) NOT NULL COMMENT '证号',
	CODE VARCHAR(100) COMMENT '证编号',
	MEMO VARCHAR(200) COMMENT '附记',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID',
	MAKE_EMP_CODE VARCHAR(32) NOT NULL COMMENT '制证人编号',
	MAKE_EMP_NAME VARCHAR(50) NOT NULL COMMENT '制证人',
	DISABLE BOOLEAN NOT NULL COMMENT 'DISABLE',
	PRINT_TIME TIMESTAMP NOT NULL COMMENT '出证时间',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '制证表' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.OWNER_BUSINESS
(
	-- 倒库导入原库的NO
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	SOURCE VARCHAR(20) NOT NULL COMMENT '业务来源',
	RECORD_TIME TIMESTAMP NOT NULL COMMENT '归档时间',
	PROCESS_MESSAGE VARCHAR(400) COMMENT '流程提示',
	MEMO VARCHAR(200) COMMENT '业务备注',
	VERSION INT COMMENT 'VERSION',
	STATUS VARCHAR(20) NOT NULL COMMENT '状态',
	APPLY_TIME DATETIME NOT NULL COMMENT '受理时间',
	CREATE_TIME TIMESTAMP NOT NULL COMMENT 'CREATE_TIME',
	DEFINE_NAME VARCHAR(50) COMMENT 'DEFINE_NAME',
	DEFINE_ID VARCHAR(32) COMMENT 'DEFINE_ID',
	-- 倒库导入原库的NO
	SELECT_BUSINESS VARCHAR(32) COMMENT 'SELECT_BUSINESS',
	APPLY_PERSON VARCHAR(50) COMMENT '申请人',
	REG_TIME DATETIME COMMENT '登记时间',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '业务' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.FINANCIAL
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	-- 原产权人 V:NOW 
	-- 现产权人 V:NEW E:NEW
	-- 产权人 V:NOW E:NOW
	-- 抵押人 V:NOW
	-- 备案人 V:NOW
	-- 预告人 V:NOW
	TYPE VARCHAR(20) NOT NULL COMMENT 'TYPE',
	NAME VARCHAR(120) NOT NULL COMMENT 'NAME',
	CODE VARCHAR(32) NOT NULL COMMENT 'CODE',
	-- 倒库导入原库的NO
	BUSINESS_ID VARCHAR(32) NOT NULL COMMENT 'BUSINESS_ID',
	PHONE VARCHAR(15) COMMENT 'PHONE',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '金融机构' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.BANK_FORK
(
	ID VARCHAR(32) NOT NULL COMMENT 'ID',
	NAME VARCHAR(120) NOT NULL COMMENT 'NAME',
	PHONE VARCHAR(15) COMMENT 'PHONE',
	BANK VARCHAR(32) NOT NULL COMMENT 'BANK',
	PRIMARY KEY (ID)
) ENGINE = InnoDB COMMENT = '支行' DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.OTHER_POWER_CARD_AND_HOUSE
(
	HOUSE VARCHAR(32) NOT NULL COMMENT 'HOUSE',
	CARD VARCHAR(32) NOT NULL COMMENT 'CARD',
	PRIMARY KEY (HOUSE, CARD)
) ENGINE = InnoDB COMMENT = '他项权房屋关联' DEFAULT CHARACTER SET utf8;



/* Create Foreign Keys */

ALTER TABLE HOUSE_OWNER_RECORD.RECORD_STORE
	ADD FOREIGN KEY (HOUSE_RECORD)
	REFERENCES HOUSE_OWNER_RECORD.HOUSE_RECORD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.PROJECT_RECORD_STORE
	ADD FOREIGN KEY (RECORD)
	REFERENCES HOUSE_OWNER_RECORD.PROJECT_RECORD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.RECORD_STORE
	ADD FOREIGN KEY (BUSINESS_HOUSE)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_AND_POOL
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.NEW_HOUSE_CONTRACT
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUILD
	ADD FOREIGN KEY (PROJECT)
	REFERENCES HOUSE_OWNER_RECORD.PROJECT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.PROJECT_SELL_CARD
	ADD FOREIGN KEY (PROJECT)
	REFERENCES HOUSE_OWNER_RECORD.PROJECT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.PROJECT_RECORD_STORE
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.PROJECT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE
	ADD FOREIGN KEY (LAND_INFO)
	REFERENCES HOUSE_OWNER_RECORD.LAND_INFO (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE
	ADD FOREIGN KEY (LAND_INFO)
	REFERENCES HOUSE_OWNER_RECORD.LAND_INFO (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.PROJECT_RECORD
	ADD FOREIGN KEY (SALE_CARD)
	REFERENCES HOUSE_OWNER_RECORD.PROJECT_SELL_CARD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE
	ADD FOREIGN KEY (BUILD_ID)
	REFERENCES HOUSE_OWNER_RECORD.BUILD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_AND_POOL
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


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE
	ADD FOREIGN KEY (AFTER_HOUSE)
	REFERENCES HOUSE_OWNER_RECORD.HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_STATE
	ADD FOREIGN KEY (HOUSE)
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


ALTER TABLE HOUSE_OWNER_RECORD.OTHER_POWER_CARD_AND_HOUSE
	ADD FOREIGN KEY (HOUSE)
	REFERENCES HOUSE_OWNER_RECORD.HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE
	ADD FOREIGN KEY (HOUSE_OWNER)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_OWNER (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE
	ADD FOREIGN KEY (HOUSE_OWNER)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_OWNER (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.OTHER_POWER_CARD_AND_HOUSE
	ADD FOREIGN KEY (CARD)
	REFERENCES HOUSE_OWNER_RECORD.OTHER_POWER_CARD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_AND_POOL
	ADD FOREIGN KEY (POOL_OWNER)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_POOL (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_AND_POOL
	ADD FOREIGN KEY (POOL_OWNER)
	REFERENCES HOUSE_OWNER_RECORD.BUSINESS_POOL (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.OTHER_POWER_CARD
	ADD FOREIGN KEY (OWNER_CARD)
	REFERENCES HOUSE_OWNER_RECORD.MAKE_CARD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.OTHER_POWER_CARD
	ADD FOREIGN KEY (CARD)
	REFERENCES HOUSE_OWNER_RECORD.MAKE_CARD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_POOL
	ADD FOREIGN KEY (CARD)
	REFERENCES HOUSE_OWNER_RECORD.MAKE_CARD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_OWNER
	ADD FOREIGN KEY (CARD)
	REFERENCES HOUSE_OWNER_RECORD.MAKE_CARD (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_MONEY
	ADD FOREIGN KEY (BUSINESS)
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


ALTER TABLE HOUSE_OWNER_RECORD.UPLOAD_FILES
	ADD FOREIGN KEY (BUSINESS_ID)
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


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_EMP
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.SALE_INFO
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


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_PERSION
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


ALTER TABLE HOUSE_OWNER_RECORD.MAKE_CARD
	ADD FOREIGN KEY (BUSINESS_ID)
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


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.REGISTER_PROPERTY
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


ALTER TABLE HOUSE_OWNER_RECORD.EVALUATE
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.FINANCIAL
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


ALTER TABLE HOUSE_OWNER_RECORD.MORTGAEGE_REGISTE
	ADD FOREIGN KEY (BUSINESS_ID)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



