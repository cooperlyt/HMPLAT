INSERT INTO DB_PLAT_SYSTEM.SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('idleGirdMapUnitSort','STRING','desc','自动分户图单元排序方式');

INSERT INTO DB_PLAT_SYSTEM.SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('moneyCalcAllowFree','BOOLEAN','true','是否允许全部减免');

INSERT INTO DB_PLAT_SYSTEM.SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('moneyCalcAllowHalf','BOOLEAN','true','是否允许半价减免');

INSERT INTO DB_PLAT_SYSTEM.SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('applyPersonTelMust','BOOLEAN','true','申请人电话必填');

INSERT INTO DB_PLAT_SYSTEM.FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('owner.todayBusinessList', '当日业务列表', 'DAY_WORK', '', '/func/house/owner/TodayBusinessList.xhtml', '', '5', '',b'0');

insert SYSTEM_PARAM (ID, TYPE, VALUE, MEMO)
  value ('MortgagePrintOwnerCard','INTEGER','2','1-他项权证打印所有权证号只没有共有权证号;2-他项权证打印所有权证带共有权证号');

insert SYSTEM_PARAM (ID, TYPE, VALUE, MEMO)
  value ('MortgageMonery','INTEGER','1','1-债权数额不打0;2-债权数额打0');

insert SYSTEM_PARAM (ID, TYPE, VALUE, MEMO)
  value ('RegerEmpAddBotime','INTEGER','1','1-AddRegerEmp不添加归档时间;2-AddRegerEmp添加归档时间');



INSERT INTO DB_PLAT_SYSTEM.ROLE (ID, NAME, DESCRIPTION,PRIORITY) VALUES ('total.export.oldHouseSale', '存量房销售情况统计', '', 30);