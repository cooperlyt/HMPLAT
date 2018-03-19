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

CREATE TABLE HOUSE_OWNER_RECORD.MONEY_BUSINESS
(
	ID VARCHAR(32) NOT NULL,
	-- 倒库导入原库的NO
	BUSINESS VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
	CONTRACT VARCHAR(32) NOT NULL,
	STATUS VARCHAR(16) NOT NULL,
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