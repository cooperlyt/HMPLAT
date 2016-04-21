
DELETE FROM SYSTEM_PARAM WHERE ID = 'IdCardImgPath';
DELETE FROM SYSTEM_PARAM WHERE ID = 'businessFilePath';
DELETE FROM SYSTEM_PARAM WHERE ID = 'businessFile.address';
DELETE FROM SYSTEM_PARAM WHERE ID = 'businessFile.port';
DELETE FROM SYSTEM_PARAM WHERE ID = 'businessFile.userName';
DELETE FROM SYSTEM_PARAM WHERE ID = 'businessFile.password';
DELETE FROM SYSTEM_PARAM WHERE ID = 'businessFile.rootDir';
DELETE FROM SYSTEM_PARAM WHERE ID = 'FTP_USER_NAME';
DELETE FROM SYSTEM_PARAM WHERE ID = 'FTP_PASSWORD';
DELETE FROM SYSTEM_PARAM WHERE ID = 'BUSINESS_FILE_CHECK_TYPE';
DELETE FROM SYSTEM_PARAM WHERE ID = 'LOCAL_FILE_UPLOAD_PATH';


INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('IMG_SERVER_ADDRESS','STRING','http://localhost:8090/','图片服务器地址');
INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('FILE_SERVER_ADDRESS','STRING','http://localhost:9333/','文件服务器地址');



ALTER TABLE HOUSE_OWNER_RECORD.UPLOAD_FILE DROP EXT;
ALTER TABLE HOUSE_OWNER_RECORD.UPLOAD_FILE ADD SIZE BIGINT NULL;
ALTER TABLE HOUSE_OWNER_RECORD.UPLOAD_FILE ADD UPLOAD_TIME TIMESTAMP NOT NULL;

-- 以下，先跟新部署项目，然后在运行sqL,否则组件无法添加
use DB_PLAT_SYSTEM;
-- 更新WP1，WP2 的预告生成证明号从预告生成改成预告抵押生成 生成单房屋预告抵押登记证明号
update TASK_SUBSCRIBE set REG_NAME='makeCardMorgageNoticeEnd' where (REG_NAME='makeCardNoticeEnd')
 and (DEFINE_ID='WP1' or DEFINE_ID='WP2');

-- 更新WP1，WP2 的预告生成证明号查看从预告生成改成预告抵押生成
update VIEW_SUBSCRIBE left join SUBSCRIBE_GROUP on VIEW_SUBSCRIBE.GROUP_ID=SUBSCRIBE_GROUP.ID
set VIEW_SUBSCRIBE.REG_NAME='MakeCardNoticeMortgageView'
where VIEW_SUBSCRIBE.REG_NAME='MakeCardNoticeView' and (DEFINE_ID='WP1' or DEFINE_ID='WP2' or DEFINE_ID='WP4');

-- 更新WP1，WP2 档案补录证号生成
update VIEW_SUBSCRIBE left join SUBSCRIBE_GROUP on VIEW_SUBSCRIBE.GROUP_ID=SUBSCRIBE_GROUP.ID
set VIEW_SUBSCRIBE.REG_NAME='MakeCardNoticeMortgageRecordEdit'
where VIEW_SUBSCRIBE.REG_NAME='MakeCardNoticeRecordEdit' and (DEFINE_ID='WP1' or DEFINE_ID='WP2' or DEFINE_ID='WP4');



USE HOUSE_OWNER_RECORD;
-- 更新WP1，WP2 的预告抵押登记预告登记权证类型改成
UPDATE OWNER_BUSINESS LEFT JOIN MAKE_CARD ON OWNER_BUSINESS.ID =MAKE_CARD.BUSINESS_ID SET MAKE_CARD.TYPE='NOTICE_MORTGAGE'
WHERE (OWNER_BUSINESS.DEFINE_ID='WP1' OR OWNER_BUSINESS.DEFINE_ID='WP2') AND MAKE_CARD.TYPE='NOTICE';


-- 更新归档部分

ALTER TABLE HOUSE_OWNER_RECORD.PROJECT DROP FOREIGN KEY PROJECT_ibfk_2;
DROP INDEX HOUSE_OWNER_RECORD.RECORD_STORE ON HOUSE_OWNER_RECORD.PROJECT;
ALTER TABLE HOUSE_OWNER_RECORD.PROJECT DROP RECORD_STORE;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE DROP FOREIGN KEY BUSINESS_HOUSE_ibfk_4;
DROP INDEX HOUSE_OWNER_RECORD.RECORD_STORE ON HOUSE_OWNER_RECORD.BUSINESS_HOUSE;
ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_HOUSE DROP RECORD_STORE;

ALTER TABLE HOUSE_OWNER_RECORD.RECORD_STORE DROP FRAME;
ALTER TABLE HOUSE_OWNER_RECORD.RECORD_STORE DROP CABINET;
ALTER TABLE HOUSE_OWNER_RECORD.RECORD_STORE DROP BOX;

ALTER TABLE HOUSE_OWNER_RECORD.RECORD_STORE ADD IN_ROOM BOOLEAN NOT NULL;
ALTER TABLE HOUSE_OWNER_RECORD.RECORD_STORE ADD VERSION BIGINT NULL;
ALTER TABLE HOUSE_OWNER_RECORD.RECORD_STORE ADD CREATE_TIME TIMESTAMP NOT NULL;


CREATE TABLE HOUSE_OWNER_RECORD.RECORD_LOCAL
(
 ID VARCHAR(32) NOT NULL,
 FRAME VARCHAR(10) NOT NULL,
 CABINET VARCHAR(20) NOT NULL,
 BOX VARCHAR(50) NOT NULL,
 RECORD_CODE VARCHAR(50) NOT NULL,
 VERSION BIGINT,
 PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;

ALTER TABLE HOUSE_OWNER_RECORD.RECORD_LOCAL
ADD FOREIGN KEY (ID)
REFERENCES HOUSE_OWNER_RECORD.BUSINESS_FILE (ID)
 ON UPDATE RESTRICT
 ON DELETE RESTRICT
;

CREATE TABLE HOUSE_OWNER_RECORD.SUB_STATUS
(
 ID VARCHAR(32) NOT NULL,
 DEFINE_ID VARCHAR(32) NOT NULL,
 BUSINESS VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
 STATUS VARCHAR(20) NOT NULL,
 PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;

ALTER TABLE HOUSE_OWNER_RECORD.SUB_STATUS
ADD FOREIGN KEY (BUSINESS)
REFERENCES HOUSE_OWNER_RECORD.OWNER_BUSINESS (ID)
 ON UPDATE RESTRICT
 ON DELETE RESTRICT
;


ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_FILE ADD RECORD_STORE VARCHAR(32) NULL;

ALTER TABLE HOUSE_OWNER_RECORD.BUSINESS_FILE
ADD FOREIGN KEY (RECORD_STORE)
REFERENCES HOUSE_OWNER_RECORD.RECORD_STORE (ID)
 ON UPDATE RESTRICT
 ON DELETE RESTRICT
;

INSERT INTO DB_PLAT_SYSTEM.REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES("record.cover","卷内目录","卷内目录","/report/RecordCover.xhtml");

INSERT INTO DB_PLAT_SYSTEM.ROLE (ID, NAME, DESCRIPTION,PRIORITY) VALUES ('recordRoomMgr','档案室管理员','档案室管理员',11);
INSERT INTO DB_PLAT_SYSTEM.ROLE (ID, NAME, DESCRIPTION,PRIORITY) VALUES ('recordFileMgr','档案管理员','可以补扫档案',10);
INSERT INTO DB_PLAT_SYSTEM.FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('owner.recordStore','档案上架','DAY_WORK','','/func/house/owner/RecordStore.xhtml','',13,'',b'0');


UPDATE HOUSE_OWNER_RECORD.RECORD_STORE set VERSION = 1;
UPDATE HOUSE_OWNER_RECORD.RECORD_LOCAL set VERSION = 1;

-- INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('RecordSave','owner.recordStore');


ALTER TABLE DB_PLAT_SYSTEM.BUSINESS_DEFINE ADD UNION_BIZ VARCHAR(64) NULL;

INSERT INTO DB_PLAT_SYSTEM.FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('owner.recordRoom','数字档案室','DATA_MGR','','/func/house/owner/RecordRoomMgr.xhtml','',15,'',b'0');

INSERT INTO DB_PLAT_SYSTEM.SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('recordRoom.enable','BOOLEAN','false','启用档案室管理');


INSERT INTO DB_PLAT_SYSTEM.SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('ShouldMoneyAllowChange','BOOLEAN','true','应收可是否可由用户修改');



use DB_PLAT_SYSTEM;
insert FEE (ID,CATEGORY,NAME,DESCRIPTION,FOR_EACH_VALUES,FOR_EACH_VAR,FEE_EL,DETAILS_EL,PRIORITY)
 value ('REGISTER.HB','REGISTER','登记费','','#{ownerBusinessHome.instance.houseBusinesses}','_houses','#{_houses.afterBusinessHouse.useType==''80''?160.0:1100.0}','#{_houses.afterBusinessHouse.useType==''80'' ? ''160'' : ''1100''}',14);
insert BUSINESS_AND_FEE (BUSINESS,FEE) values ('WP150','REGISTER.HB');

insert DB_PLAT_SYSTEM.SYSTEM_PARAM (ID, TYPE, VALUE, MEMO) values ('CreateCradNumberType','INTEGER','1','1:生成的权证号带-;2:生成的权证号不带-');
insert DB_PLAT_SYSTEM.SYSTEM_PARAM (ID, TYPE, VALUE, MEMO) values ('PoolInfoPrint','INTEGER','1','1:权证打印附记共有情况打印带所有权人;2:权证打印附记共有情况打印不带所有权人,只有共有权人姓名');


delete from VIEW_SUBSCRIBE where REG_NAME='RecordStoreHouseView';
delete from VIEW_SUBSCRIBE where REG_NAME='CheckEmpRecordEdit';
DELETE FROM TASK_SUBSCRIBE WHERE REG_NAME='RecordCoverPrint';
DELETE FROM VIEW_SUBSCRIBE WHERE REG_NAME='CheckEmpRecordEdit';


ALTER TABLE HOUSE_OWNER_RECORD.CLOSE_HOUSE ADD TIME_AREA_TYPE VARCHAR(20) NULL;
UPDATE HOUSE_OWNER_RECORD.CLOSE_HOUSE set TIME_AREA_TYPE = 'DATE_TIME';

ALTER TABLE HOUSE_OWNER_RECORD.MORTGAEGE_REGISTE ADD TIME_AREA_TYPE VARCHAR(20) NOT NULL;
UPDATE HOUSE_OWNER_RECORD.MORTGAEGE_REGISTE set TIME_AREA_TYPE = 'DATE_TIME';

INSERT DB_PLAT_SYSTEM.SYSTEM_PARAM(ID, TYPE, VALUE, MEMO) VALUES ('PatchFileUpload','BOOLEAN','true','档案补录是否上传文件');


CREATE TABLE HOUSE_OWNER_RECORD.HOUSE_AND_RECORD
(
 HOUSE VARCHAR(32) NOT NULL,
 RECORD VARCHAR(32) NOT NULL,
 PRIMARY KEY (HOUSE, RECORD)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;

ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_AND_RECORD
 ADD FOREIGN KEY (RECORD)
REFERENCES HOUSE_OWNER_RECORD.BUSINESS_HOUSE (ID)
 ON UPDATE RESTRICT
 ON DELETE RESTRICT
;

ALTER TABLE HOUSE_OWNER_RECORD.HOUSE_AND_RECORD
 ADD FOREIGN KEY (HOUSE)
REFERENCES HOUSE_OWNER_RECORD.RECORD_STORE (ID)
 ON UPDATE RESTRICT
 ON DELETE RESTRICT
;

