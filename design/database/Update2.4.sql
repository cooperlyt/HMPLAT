INSERT DB_PLAT_SYSTEM.REPORT (ID, NAME, DESCRIPTION, PAGE) VALUE ('93','商品房预售监管资金网签未备案退款审批单','商品房预售监管资金网签未备案退款审批单','/report/dgfcc/DgMoneyBackAbortReisterBook.xhtml');
INSERT DB_PLAT_SYSTEM.REPORT (ID, NAME, DESCRIPTION, PAGE) VALUE ('94','商品房预售监管资金网签未备案退款申请表','商品房预售监管资金网签未备案退款申请表','/report/dgfcc/DgMoneyBackAbortTicket.xhtml');
INSERT DB_PLAT_SYSTEM.REPORT (ID, NAME, DESCRIPTION, PAGE) VALUE ('95','商品房预售监管资金网签未备案退款通知书','商品房预售监管资金网签未备案退款通知书','/report/dgfcc/DgMoneyBackAbortNotice.xhtml');
HANGE PICK_BUSINESS_DEFINE_STATUS PICK_BUSINESS_DEFINE_STATUS varchar(100);

INSERT DB_PLAT_SYSTEM.BUSINESS_DEFINE (ID, NAME, WF_NAME, START_PAGE, CATEGORY, MEMO, VERSION, ROLE_PREFIX, DESCRIPTION, PRIORITY, ENABLE, PICK_BUSINESS_DEFINE_ID, PICK_BUSINESS_VIEW_PAGE, MODIFY_PAGE, REQUIRED_BIZ, REGISTER_BOOK_PART, UNION_BIZ, PICK_BUSINESS_DEFINE_STATUS)
 VALUE ('MB1','商品房网签未备案退款','HouseOwnerBusiness','moneyBackBusinessCreate','house.owner.commodity','',1,'cq','申请人：#{ownerBusinessHome.getBusinessPersionByType(''CORRECT'').personName} 房屋编号：#{ownerBusinessHome.singleHoues.houseCode} 合同编号: #{ownerBusinessHome.selectBusiness.houseContract.contractNumber}',
99,true ,'WP42','','',true,'','','ABORT');
INSERT DB_PLAT_SYSTEM.BUSINESS_DEFINE (ID, NAME, WF_NAME, START_PAGE, CATEGORY, MEMO, VERSION, ROLE_PREFIX, DESCRIPTION, PRIORITY, ENABLE, PICK_BUSINESS_DEFINE_ID, PICK_BUSINESS_VIEW_PAGE, MODIFY_PAGE, REQUIRED_BIZ, REGISTER_BOOK_PART, UNION_BIZ, PICK_BUSINESS_DEFINE_STATUS)
 VALUE ('MB2','商品网签未备案冲正退款','HouseOwnerBusiness','moneyBackBusinessCreate','house.owner.commodity','',1,'cq','申请人：#{ownerBusinessHome.getBusinessPersionByType(''CORRECT'').personName} 房屋编号：#{ownerBusinessHome.selectBusiness.singleHoues.houseCode} 合同编号: #{ownerBusinessHome.selectBusiness.houseContract.contractNumber}',
100,true ,'WP42','','',true,'','','COMPLETE,ABORT,SUSPEND,CANCEL,MODIFYING,COMPLETE_CANCEL');

INSERT DB_PLAT_SYSTEM.REPORT (ID, NAME, DESCRIPTION, PAGE) VALUE ('96','商品房预售监管资金冲正审批单','商品房预售监管资金冲正审批单','/report/dgfcc/DgMoneyBackDeleteReisterBook.xhtml');
INSERT DB_PLAT_SYSTEM.REPORT (ID, NAME, DESCRIPTION, PAGE) VALUE ('97','商品房预售监管资金冲正申请表','商品房预售监管资金冲正申请表','/report/dgfcc/DgMoneyBackDeleteTicket.xhtml');
INSERT DB_PLAT_SYSTEM.REPORT (ID, NAME, DESCRIPTION, PAGE) VALUE ('98','商品房预售监管资金冲正通知书','商品房预售监管资金冲正通知书','/report/dgfcc/DgMoneyBackDeleteNotice.xhtml');


CREATE TABLE HOUSE_OWNER_RECORD.BUILD_POINT
(
  ID VARCHAR(32) NOT NULL,
  NAME VARCHAR(100) NOT NULL,
  PERCENT INT NOT NULL,
  PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;



CREATE TABLE HOUSE_OWNER_RECORD.PROJECT_CHECK
(
  ID VARCHAR(32) NOT NULL,
  BUILD_CODE VARCHAR(32) NOT NULL,
-- 倒库导入原库的NO
  BUSINESS VARCHAR(32) NOT NULL COMMENT '倒库导入原库的NO',
  POINT VARCHAR(32) NOT NULL,
  POINT_NAME VARCHAR(100) NOT NULL,
  PAY_PERCENT INT NOT NULL,
  PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;
