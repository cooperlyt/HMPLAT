
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


