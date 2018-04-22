ALTER TABLE HOUSE_OWNER_RECORD.HOUSE DROP FOREIGN KEY HOUSE_ibfk_8;
-- ALTER TABLE HOUSE_OWNER_RECORD.HOUSE DROP FOREIGN KEY house_ibfk_1;
ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_CONTRACT DROP FOREIGN KEY HOUSE_CONTRACT_ibfk_1;

UPDATE HOUSE_CONTRACT hc left join BUSINESS_HOUSE bh on bh.BUSINESS_ID = hc.BUSINESS set hc.BUSINESS = bh.ID;

-- UPDATE HOUSE_CONTRACT hc LEFT JOIN HOUSE h on h.SALE_CONTRACT = hc.ID left join BUSINESS_HOUSE bh on bh.AFTER_HOUSE = h.ID set hc.BUSINESS = bh.ID;

SET FOREIGN_KEY_CHECKS = 0;

UPDATE CONTRACT_NUMBER cn LEFT JOIN HOUSE_CONTRACT hc on hc.ID = cn.CONTRACT set cn.CONTRACT = hc.BUSINESS;
UPDATE CONTRACT_SUBMIT cs LEFT JOIN HOUSE_CONTRACT hc on hc.ID = cs.ID set cs.ID = hc.BUSINESS;

UPDATE HOUSE_CONTRACT hc set hc.ID = hc.BUSINESS;

SET FOREIGN_KEY_CHECKS = 1;


ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_CONTRACT
	ADD FOREIGN KEY (ID)
REFERENCES HOUSE_OWNER_RECORD.BUSINESS_HOUSE (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

DROP INDEX BUSINESS ON HOUSE_OWNER_RECORD.HOUSE_CONTRACT;
ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_CONTRACT DROP BUSINESS;


DROP INDEX SALE_CONTRACT ON HOUSE_OWNER_RECORD.HOUSE;
ALTER TABLE HOUSE_OWNER_RECORD.HOUSE DROP SALE_CONTRACT;

--  以加是资金监管

CREATE TABLE HOUSE_INFO.MONEY_BANK
(
	ID VARCHAR(32) NOT NULL,
	NAME VARCHAR(50) NOT NULL,
	KEYT VARCHAR(16) NOT NULL,
	SECRET_TYPE VARCHAR(16) NOT NULL,
	PRI INT NOT NULL,
	ACCOUNT_NUMBER VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE HOUSE_OWNER_RECORD.MONEY_BUSINESS
(
	ID VARCHAR(32) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	CONTRACT VARCHAR(32) NOT NULL,
	STATUS VARCHAR(32) NOT NULL,
	BANK VARCHAR(32) NOT NULL,
	BANK_NAME VARCHAR(128) NOT NULL,
	ACCOUNT_NUMBER VARCHAR(32) NOT NULL,
	-- 更正一次加1
	VER INT NOT NULL COMMENT '更正一次加1',
	VERSION INT NOT NULL,
	MONEY DECIMAL(19,4) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;

CREATE TABLE HOUSE_OWNER_RECORD.MONEY_PAY_INFO
(
	BANK_NAME VARCHAR(512) NOT NULL,
	CARD_NUMBER VARCHAR(19) NOT NULL,
	CARD_NAME VARCHAR(50) NOT NULL,
	ID VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;

CREATE TABLE HOUSE_OWNER_RECORD.REG_INFO
(
	QUERY_TIME DATETIME NOT NULL,
	DATA LONGTEXT NOT NULL,
	ID VARCHAR(32) NOT NULL,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;

ALTER TABLE HOUSE_OWNER_RECORD.MONEY_BUSINESS
	ADD FOREIGN KEY (CONTRACT)
	REFERENCES HOUSE_OWNER_RECORD.HOUSE_CONTRACT (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE HOUSE_OWNER_RECORD.MONEY_PAY_INFO
	ADD FOREIGN KEY (ID)
	REFERENCES HOUSE_OWNER_RECORD.MONEY_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE HOUSE_OWNER_RECORD.REG_INFO
	ADD FOREIGN KEY (ID)
	REFERENCES HOUSE_OWNER_RECORD.MONEY_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.MONEY_BUSINESS
	ADD FOREIGN KEY (BUSINESS)
	REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_CONTRACT ADD SUM_PRICE DECIMAL(19,4) NOT NULL;
ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_CONTRACT ADD PAY_TYPE VARCHAR(32) NULL;
ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_CONTRACT ADD SALEAREA DECIMAL(19,4) NULL;

UPDATE SALE_INFO s left join BUSINESS_HOUSE bh on bh.AFTER_HOUSE = s.HOUSEID LEFT JOIN HOUSE_CONTRACT hc on hc.ID = bh.ID set hc.PAY_TYPE = s.PAY_TYPE, hc.SUM_PRICE = s.SUM_PRICE, hc.SALEAREA = s.SALEAREA where hc.ID is not null;

select count(*) from  SALE_INFO s left join BUSINESS_HOUSE bh on bh.AFTER_HOUSE = s.HOUSEID LEFT JOIN HOUSE_CONTRACT hc on hc.ID = bh.ID where hc.ID is not null;


INSERT INTO HOUSE_CONTRACT(ID,CONTRACT_NUMBER,CONTRACT_DATE,TYPE,PAY_TYPE,SUM_PRICE,SALEAREA)
	(select  bh.ID,'未知',ob.APPLY_TIME,'OTHER', s.PAY_TYPE,s.SUM_PRICE,s.SALEAREA from SALE_INFO s LEFT JOIN BUSINESS_HOUSE bh on bh.AFTER_HOUSE = s.HOUSEID left join HOUSE_CONTRACT hc on hc.ID = bh.ID left join OWNER_BUSINESS ob on ob.ID = bh.BUSINESS_ID
	WHERE hc.ID is null and ((s.PAY_TYPE is not null )  or (s.SUM_PRICE is not null and s.SUM_PRICE > 0) or (s.SALEAREA is not null and s.SALEAREA > 0)));

-- TODO 改丹东个人住房视图


UPDATE DB_PLAT_SYSTEM.SYSTEM_PARAM set VALUE='2.2.1' where ID='database_version';


UPDATE DB_PLAT_SYSTEM.TASK_SUBSCRIBE SET REG_NAME='GenSearchKeyForNewOwner' WHERE REG_NAME='HouseBusinessMortgageKeyGen';
DELETE FROM DB_PLAT_SYSTEM.VIEW_SUBSCRIBE WHERE REG_NAME='StartSaleInfoView';
update DB_PLAT_SYSTEM.VIEW_SUBSCRIBE set REG_NAME='SaleInfoView' where REG_NAME='SaleInfoSuperviseView';
update DB_PLAT_SYSTEM.VIEW_SUBSCRIBE set REG_NAME='SaleInfoView' where REG_NAME='SaleInfoGiftView';
update DB_PLAT_SYSTEM.VIEW_SUBSCRIBE set REG_NAME='saleInfoEdit' where REG_NAME='SaleInfoSuperviseEdit';
update DB_PLAT_SYSTEM.VIEW_SUBSCRIBE set REG_NAME='saleInfoEdit' where REG_NAME='SaleInfoGiftEdit';

UPDATE DB_PLAT_SYSTEM.SUBSCRIBE_GROUP AS G  LEFT JOIN DB_PLAT_SYSTEM.VIEW_SUBSCRIBE AS V
    ON G.ID = V.GROUP_ID
SET V.REG_NAME='SaleInfoSuperviseView'
WHERE (G.DEFINE_ID='WP41' or G.DEFINE_ID='WP42' OR G.DEFINE_ID='WP72' ) and V.REG_NAME='SaleInfoView';

UPDATE DB_PLAT_SYSTEM.SUBSCRIBE_GROUP AS G  LEFT JOIN DB_PLAT_SYSTEM.VIEW_SUBSCRIBE AS V
    ON G.ID = V.GROUP_ID
SET V.REG_NAME='SaleInfoSuperviseEdit'
WHERE (G.DEFINE_ID='WP41' or G.DEFINE_ID='WP42' OR G.DEFINE_ID='WP72') and V.REG_NAME='saleInfoEdit';


UPDATE DB_PLAT_SYSTEM.SUBSCRIBE_GROUP AS G  LEFT JOIN DB_PLAT_SYSTEM.VIEW_SUBSCRIBE AS V
    ON G.ID = V.GROUP_ID
SET V.REG_NAME='SaleInfoSuperviseEdit'
WHERE (G.DEFINE_ID='WP72') and V.REG_NAME='SaleInfoBackHouseEdit';

DELETE V FROM DB_PLAT_SYSTEM.VIEW_SUBSCRIBE AS V,DB_PLAT_SYSTEM.SUBSCRIBE_GROUP AS G
WHERE V.GROUP_ID=G.ID AND (V.REG_NAME='saleInfoEdit' OR V.REG_NAME='SaleInfoView')
      AND G.DEFINE_ID IN ('WP46','WP57','WP58','WP59','WP60','WP61', 'WP62',
                          'WP63','WP64','WP65','WP66','WP67','WP68', 'WP70',
                          'WP71','WP86','WP87', 'WP90');


insert DB_PLAT_SYSTEM.SYSTEM_PARAM (ID, TYPE, VALUE, MEMO) values ('SiteShort','STRING','DG','内网生成合同编号位置缩写');
ALTER TABLE HOUSE_OWNER_RECORD.MONEY_BUSINESS ADD SEARCH_KEY varchar(1024) NULL;
ALTER TABLE HOUSE_OWNER_RECORD.MONEY_BUSINESS ADD DISPLAY longtext NULL;
ALTER TABLE HOUSE_OWNER_RECORD.MONEY_BUSINESS ADD CHECKED BOOLEAN NULL;

update HOUSE_OWNER_RECORD.MONEY_BUSINESS AS MB LEFT JOIN HOUSE_OWNER_RECORD.OWNER_BUSINESS AS OB
    ON MB.BUSINESS=OB.ID SET MB.CHECKED=OB.RECORDED