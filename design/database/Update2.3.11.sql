ALTER TABLE HOUSE_OWNER_RECORD.PROJECT_CHECK ADD SEARCH_KEY varchar(1024) NULL;
ALTER TABLE HOUSE_OWNER_RECORD.PROJECT_CHECK ADD DISPLAY longtext NULL;

INSERT DB_PLAT_SYSTEM.FUNCTION (ID, NAME, ICON, LOCATION, BANNER, PRIORITY, MEMO, CATEGORY, NEED_CONVERSATION)
  VALUE ('money.projectCheckBusinessSearch','项目勘察业务查询','','/func/house/owner/ProjectCheckBusinessSearch.xhtml',true,6,'','DAY_WORK',0);
INSERT DB_PLAT_SYSTEM.ROLE_FUNCTION (FUN_ID, ROL_ID) VALUE ('money.projectCheckBusinessSearch','cqsl');
INSERT DB_PLAT_SYSTEM.REPORT (ID, NAME, DESCRIPTION, PAGE) VALUE ('99','商品房预售监管资金监管现场勘察申请表','商品房预售监管资金监管现场勘察申请表','/report/dgfcc/DgProjectCheckTicket.xhtml');

UPDATE DB_PLAT_SYSTEM.SYSTEM_PARAM set VALUE='2.3.11' where ID='database_version';