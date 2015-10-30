-- If you are using Hibernate as the JPA provider, you can use this file to load seed data into the database using SQL statements
-- The portable approach is to use a startup component (such as the @PostConstruct method of a @Startup @Singleton) or observe a lifecycle event fired by Seam Servlet


INSERT INTO HOUSE_INFO.DISTRICT(ID, NAME, SHORT_NAME, VERSION) VALUES ('211282001000','新城街道','开原市',1);
INSERT INTO HOUSE_INFO.DISTRICT(ID, NAME, SHORT_NAME, VERSION) VALUES ('211282003000','兴开街道','开原市',1);
INSERT INTO HOUSE_INFO.DISTRICT(ID, NAME, SHORT_NAME, VERSION) VALUES ('211282104000','中固镇','开原市',1);


USE DB_PLAT_SYSTEM;

-- 系统参数
-- INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('system.business.forwordToTask','BOOLEAN','true','业务建立后是否自动跳转到业务处理页面,是:true');


INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('erp.finance.bankAccount','STRING','1002','银行总帐科目代码');
INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('erp.finance.cashAccount','STRING','1001','现金总帐科目代码');


INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('house.id.gentype','STRING','JDJT246_3','房屋编码生成方法： JDJT246_3 竣工时间法 JDJT246_4 坐标法 JDJT246_5 分宗法 JDJT246_6 分幅法');

INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('HouseEditStrategy','STRING','ownerHouseEditStrategy','测绘房屋编辑策略器');


INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('HouseBlockCodeLabel','STRING','丘号','丘号 或 宗地号');
INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('HouseStreetCodeLabel','STRING','街坊号','街坊号 或 房产分区码');

INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('HouseCodeDisplayModel','INTEGER','3','1:国标;2:图-丘-幢-房;3：行政区-丘-幢-房;:4:丘-幢-房;');

INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('CreateUploadFile','BOOLEAN','true','true: 建立业务需要上传文件，false: 建立业务不需要上传文件');


INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('AllowBusinessNoFile','BOOLEAN','true','true: 可以暂不上传要件，false: 不可以暂不上传要件');
INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('AllowNoFileRecord','BOOLEAN','true','true: 有暂缺要件可以归档，false: 有暂缺要件不可以归档');

INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('CreateBizTaskName','STRING','受理业务','建立业务的阶段名称');

INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('SetupName','STRING','某某房产名','');

INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('SetupCode','STRING','100','行政区编码');



INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('ContractNOCount','INTEGER','100','自动生成合同号数量，0为不生成');

INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('NewHouseContractBizId','STRING','WP42','商品房网上备案业务编号');

INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('AutoCalcMoney','BOOLEAN','true','自动核费');
INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('ShouldMoneyCalcType','INTEGER','3','1:手动；2：等于核费金额 3：四舍五入到元;4 四舍五入到角；5 四舍五入到分 6: 抹掉小数取整 7：进位取整');


INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('IdCardImgPath','STRING','ftp://192.168.0.6/person/','身份证照片地址');
INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('businessFilePath','STRING','ftp://192.168.0.6/','业务要件附件存储地址');

INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('businessFile.address','STRING','192.168.0.6','FTP地址');
INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('businessFile.port','INTEGER','21','FTP端口');
INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('businessFile.userName','STRING','hmplat','FTP用户名');
INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('businessFile.password','STRING','hmplat','FTP密码');
INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('businessFile.rootDir','STRING','business','FTP目录');

INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('FTP_USER_NAME','STRING','hmplat','FTP用户名');

INSERT INTO SYSTEM_PARAM(ID,TYPE,VALUE,MEMO) VALUES('FTP_PASSWORD','STRING','hmplat','FTP密码');


-- 业务类别
INSERT INTO BUSINESS_CATEGORY(ID, NAME, PRIORITY) VALUES('house.owner.initReg','初始登记',1);
INSERT INTO BUSINESS_CATEGORY(ID, NAME, PRIORITY) VALUES('house.owner.shift','转移登记',2);
INSERT INTO BUSINESS_CATEGORY(ID, NAME, PRIORITY) VALUES('house.owner.change','变更登记',3);
INSERT INTO BUSINESS_CATEGORY(ID, NAME, PRIORITY) VALUES('house.owner.destroy','注销登记',4);
INSERT INTO BUSINESS_CATEGORY(ID, NAME, PRIORITY) VALUES('house.owner.mortgage','抵押权登记',5);
INSERT INTO BUSINESS_CATEGORY(ID, NAME, PRIORITY) VALUES('house.owner.easement','地役权登记',6);
INSERT INTO BUSINESS_CATEGORY(ID, NAME, PRIORITY) VALUES('house.owner.notice','预告登记',7);
INSERT INTO BUSINESS_CATEGORY(ID, NAME, PRIORITY) VALUES('house.owner.other','其它登记',8);
INSERT INTO BUSINESS_CATEGORY(ID, NAME, PRIORITY) VALUES('house.owner.commodity','商品房登记',9);

-- 业务明细
INSERT INTO `business_define` (`ID`, `NAME`, `WF_NAME`, `START_PAGE`, `CATEGORY`, `MEMO`, `VERSION`, `START_PROPAGATION`, `ROLE_PREFIX`, `DESCRIPTION`, `PRIORITY`, `ENABLE`, `PICK_BUSINESS_DEFINE_ID`, `MODIFY_PAGE`, `MODIFY_SEARCH_PAGE`) VALUES ('WP1','预购商品房抵押权预告登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',14,NULL,NULL,NULL,0,1,NULL,'',''),('WP10','房屋所有权抵押变更登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.mortgage','',36,NULL,NULL,NULL,0,1,NULL,'',''),('WP102','改变用途','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.change','',15,NULL,NULL,NULL,0,1,NULL,'',''),('WP11','房屋所有权抵押转移登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.mortgage','',10,NULL,NULL,NULL,0,1,NULL,'',''),('WP110','房屋权利注销','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.destroy','',14,NULL,NULL,NULL,0,1,NULL,'',''),('WP111','房屋所有权转移预告变更登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',13,NULL,NULL,NULL,0,1,NULL,'',''),('WP112','房屋所有权转移预告转移登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',14,NULL,NULL,NULL,0,1,NULL,'',''),('WP113','撤销登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.other','',3,NULL,'','',0,0,NULL,'',''),('WP114','预购商品房预告转移登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',13,NULL,NULL,NULL,0,1,NULL,'',''),('WP115','在建工程抵押权设定转移登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.mortgage','',17,NULL,NULL,NULL,0,1,NULL,'',''),('WP12','房屋所有权抵押注销登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.mortgage','',10,NULL,NULL,NULL,0,1,NULL,'',''),('WP13','最高额抵押权设定登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.mortgage','',10,NULL,NULL,NULL,0,1,NULL,'',''),('WP14','最高额抵押权确定登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.mortgage','',10,NULL,NULL,NULL,0,1,NULL,'',''),('WP15','最高额抵押权设定变更登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.mortgage','',10,NULL,NULL,NULL,0,1,NULL,'',''),('WP16','最高额抵押权设定转移登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.mortgage','',12,NULL,NULL,NULL,0,1,NULL,'',''),('WP17','最高额抵押权设定注销登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.mortgage','',10,NULL,NULL,NULL,0,1,NULL,'',''),('WP18','在建工程抵押权设定登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.mortgage','',15,NULL,NULL,NULL,0,1,NULL,'',''),('WP19','在建工程抵押权设定变更登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.mortgage','',14,NULL,NULL,NULL,0,1,NULL,'',''),('WP2','预购商品房抵押权预告变更登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',15,NULL,NULL,NULL,0,1,NULL,'',''),('WP21','在建工程抵押权设定注销登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.mortgage','',14,NULL,NULL,NULL,0,1,NULL,'',''),('WP22','他项权更正登记','HouseOwnerBusiness','0','house.owner.mortgage','',1,NULL,'','',0,0,NULL,'',''),('WP23','他项权异议登记','HouseOwnerBusiness','0','house.owner.other','',1,NULL,'','',0,0,NULL,'',''),('WP24','他项权异议注销登记','HouseOwnerBusiness','0','house.owner.other','',1,NULL,'','',0,0,NULL,'',''),('WP25','他项权遗失补照','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.mortgage','',1,NULL,'','',0,0,NULL,'',''),('WP26','他项权预告登记证明补证','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',1,NULL,'','',0,0,NULL,'',''),('WP27','在建工程抵押权遗失补证','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.mortgage','',1,NULL,'','',0,0,NULL,'',''),('WP28','在建工程抵押权异议登记','HouseOwnerBusiness','0','house.owner.other','',1,NULL,'','',0,0,NULL,'',''),('WP29','在建工程抵押权异议注销登记','HouseOwnerBusiness','0','house.owner.other','',1,NULL,'','',0,0,NULL,'',''),('WP3','预购商品房抵押权预告转移登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',13,NULL,NULL,NULL,0,1,NULL,'',''),('WP30','新建房屋','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.initReg','',84,NULL,NULL,NULL,1,1,NULL,'',''),('WP31','无籍房屋','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.initReg','',30,NULL,NULL,NULL,2,1,NULL,'',''),('WP32','所有权遗失补照','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.initReg','',21,NULL,'','',4,0,NULL,'',''),('WP33','换照','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.initReg','',38,NULL,NULL,NULL,3,1,NULL,'',''),('WP35','房屋所有权更正登记 ','HouseOwnerBusiness','0','house.owner.initReg','',1,NULL,'','',0,0,NULL,'',''),('WP36','异议登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.other','',4,NULL,NULL,NULL,0,1,NULL,'',''),('WP37','异议注销登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.other','',3,NULL,NULL,NULL,0,1,NULL,'',''),('WP38','房屋灭藉','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.destroy','',13,NULL,NULL,NULL,0,1,NULL,'',''),('WP4','预购商品房抵押权预告注销登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',13,NULL,NULL,NULL,0,1,NULL,'',''),('WP40','商品房初始登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.initReg','',15,NULL,NULL,NULL,0,1,NULL,'',''),('WP41','商品房交易','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',13,NULL,NULL,NULL,0,1,NULL,'',''),('WP42','商品房合同备案登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.commodity','',24,NULL,NULL,NULL,0,1,NULL,'',''),('WP43','撤销商品房合同备案登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.commodity','',14,NULL,NULL,NULL,0,1,NULL,'',''),('WP44','预购商品房预告登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',27,NULL,NULL,NULL,0,1,NULL,'',''),('WP45','预购商品房预告变更登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',15,NULL,NULL,NULL,0,1,NULL,'',''),('WP46','预购商品房预告注销登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',13,NULL,NULL,NULL,0,1,NULL,'',''),('WP47','预购商品房预告更正登记','HouseOwnerBusiness','0','house.owner.other','',1,NULL,'','',0,0,NULL,'',''),('WP48','预购商品房预告异议登记','HouseOwnerBusiness','0','house.owner.other','',1,NULL,'','',0,0,NULL,'',''),('WP49','预购商品房预告异议注销登记','HouseOwnerBusiness','0','house.owner.other','',1,NULL,'','',0,0,NULL,'',''),('WP5','房屋抵押权预告登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',14,NULL,NULL,NULL,0,1,NULL,'',''),('WP50','商品房预售（销售）许可证','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.commodity','',0,NULL,NULL,NULL,0,1,NULL,'',''),('WP51','商品房预售（销售）许可证变更登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.commodity','',0,NULL,NULL,NULL,0,1,NULL,'',''),('WP52','名称变更','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.change','',15,NULL,NULL,NULL,0,1,NULL,'',''),('WP53','面积变更','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.change','',14,NULL,NULL,NULL,0,1,NULL,'',''),('WP54','分照','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.change','',14,NULL,NULL,NULL,0,1,NULL,'',''),('WP55','合照','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.change','',14,NULL,NULL,NULL,0,1,NULL,'',''),('WP56','二手房交易','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',13,NULL,NULL,NULL,0,1,NULL,'',''),('WP57','企业改制','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',15,NULL,NULL,NULL,0,1,NULL,'',''),('WP58','赠与','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',14,NULL,NULL,NULL,0,1,NULL,'',''),('WP59','继承','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',13,NULL,NULL,NULL,0,1,NULL,'',''),('WP6','房屋抵押权预告变更登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',15,NULL,NULL,NULL,0,1,NULL,'',''),('WP60','法院判决','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',12,NULL,NULL,NULL,0,1,NULL,'',''),('WP61','房屋拍卖','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',12,NULL,NULL,NULL,0,1,NULL,'',''),('WP62','投资入股','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',12,NULL,NULL,NULL,0,1,NULL,'',''),('WP63','兼并合并','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',12,NULL,NULL,NULL,0,1,NULL,'',''),('WP65','抵债业务','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',12,NULL,NULL,NULL,0,1,NULL,'',''),('WP66','政府奖励','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',30,NULL,NULL,NULL,0,1,NULL,'',''),('WP67','房改房屋','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',12,NULL,NULL,NULL,0,1,NULL,'',''),('WP69','房屋所有权转移预告登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',13,NULL,NULL,NULL,0,1,NULL,'',''),('WP7','房屋抵押权预告转移登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',14,NULL,NULL,NULL,0,1,NULL,'',''),('WP70','房屋所有权转移预告注销登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',14,NULL,NULL,NULL,0,1,NULL,'',''),('WP71','房屋交换','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',12,NULL,NULL,NULL,0,1,NULL,'',''),('WP72','回迁房屋','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',12,NULL,NULL,NULL,0,1,NULL,'',''),('WP73','查封登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.other','',4,NULL,NULL,NULL,0,1,NULL,'',''),('WP74','查封解除登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.other','',3,NULL,NULL,NULL,0,1,NULL,'',''),('WP76','租赁登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.other','',15,NULL,NULL,NULL,0,1,NULL,'',''),('WP77','地役权登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.easement','',0,NULL,NULL,NULL,0,1,NULL,'',''),('WP78','地役权变更登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.easement','',0,NULL,NULL,NULL,0,1,NULL,'',''),('WP79','地役权转移登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.easement','',0,NULL,NULL,NULL,0,1,NULL,'',''),('WP8','房屋抵押权预告注销登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.notice','',0,NULL,NULL,NULL,0,1,NULL,'',''),('WP80','地役权注销登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.easement','',0,NULL,NULL,NULL,0,1,NULL,'',''),('WP85','商品房确权登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.commodity','',9,NULL,NULL,NULL,0,1,NULL,'',''),('WP86','赠与与继承','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',12,NULL,NULL,NULL,0,1,NULL,'',''),('WP87','离婚裁决','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',51,NULL,NULL,NULL,0,1,NULL,'',''),('WP9','房屋所有权抵押登记','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.mortgage','',12,NULL,NULL,NULL,0,1,NULL,'',''),('WP90','婚前财产婚后约定为共有','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',13,NULL,NULL,NULL,0,1,NULL,'',''),('WP91','婚后财产增加共有人','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.change','',15,NULL,NULL,NULL,0,1,NULL,'',''),('WP99','国有直管房出售','HouseOwnerBusiness','/business/houseOwner/SingleHouseStart','house.owner.shift','',12,NULL,NULL,NULL,0,1,NULL,'','');


-- 报表
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('owner.apply.check','房地产登记受理审核业务书','房地产登记受理审核业务书','/report/ReisterApplyCheckTable.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('1','业务受理单','业务受理单','/report/BusinessHandleStub.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('2','询问笔录（初始登记）','询问笔录（初始登记）','/report/InitRegAskRecord.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('3','询问笔录（转移登记）','询问笔录（转移登记）','/report/ShiftAskRecord.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('4','房屋所有权转移登记申请书','房屋所有权转移登记申请书','/report/ApplyShifReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('4A','房地产抵押权设立登记申请书(预告)','房地产抵押权设立登记申请书(预告)','/report/ApplyHouseMortgageNoticeReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('5','房屋所有权变更登记申请书','房屋所有权变更登记申请书','/report/ApplyChangeReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('6','房地产登记撤回申请书','房地产登记撤回申请书','/report/ApplyBackReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('7','房地产抵押权设立登记申请书','房地产抵押权设立登记申请书','/report/ApplyHouseMortgageReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('8','房地产抵押权变更登记和注销登记申请书(抵押权变更)','房地产抵押权变更登记和注销登记申请书(抵押权变更)','/report/ApplyHouseMortgageChangeReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('9','房地产最高额抵押权确定登记申请书','房地产最高额抵押权确定登记申请书','/report/ApplyHouseMortgageHighReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('10','询问笔录（抵押登记）','询问笔录（抵押登记）','/report/MortgageAskRecord.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('11','房地产抵押权变更登记和注销登记申请书(抵押注销)','房地产抵押权变更登记和注销登记申请书(抵押注销)','/report/ApplyHouseMortgageLogoutReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('12','房地产抵押权设立登记申请书(在建)','房地产抵押权设立登记申请书(在建)','/report/ApplyProjectMortgageReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('13','在建工程抵押登记房屋明细表','在建工程抵押登记房屋明细表','/report/ApplyProjectMortgageHouseList.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('14','业务受理单（项目）','业务受理单（项目）','/report/ProjectBusinessHandleStub.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('15','房地产登记受理审核业务书（项目）','房地产登记受理审核业务书（项目）','/report/ReisterApplyProjectCheckTable.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('16','房屋所有权初始登记申请书','房屋所有权初始登记申请书','/report/ApplyInitReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('17','房地产更正登记登记、异议登记、换证、补证申请书','房地产更正登记登记、异议登记、换证、补证申请书','/report/ApplyCorrectObjectionChangeFillReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('18','预购商品房预告登记设立申请书','预购商品房预告登记设立申请书','/report/ApplyCommodityNoticeReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('19','预购商品房预告变更登记设立申请书','预购商品房预告变更登记设立申请书','/report/ApplyCommodityNoticeChangeReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('20','预购商品房预告注销登记设立申请书','预购商品房预告注销登记设立申请书','/report/ApplyCommodityNoticeLogoutReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('21','房地产抵押权变更登记和注销登记申请书(预告登记)','房地产抵押权变更登记和注销登记申请书(预告登记)','/report/ApplyHouseMortgageChangeNoticeReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('22','房地产抵押权变更登记和注销登记申请书(预告注销)','房地产抵押权变更登记和注销登记申请书(预告注销)','/report/ApplyHouseMortgageChangeNoticeLogOutReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('23','房地产异议销登记申请书','房地产异议销登记申请书','/report/ApplyHouseDifficultyLogOutReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('24','接收执行法律文书类业务书','接收执行法律文书类业务书','/report/ApplyCloseHouseReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('25','协助执行审查建议书','协助执行审查建议书','/report/ApplyCloseHouseExecutiveReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('26','解封接收执行法律文书类业务书','解封接收执行法律文书类业务书','/report/ApplyOpenHouseReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('27','解封协助执行审查建议书','解封协助执行审查建议书','/report/ApplyOpenHouseExecutiveReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('28','房地产抵押权变更登记和注销登记申请书(在建注销)','房地产抵押权变更登记和注销登记申请书(在建注销)','/report/ApplyProjectMortgageLogoutReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('29','业务受理单（在建）','业务受理单（在建）','/report/ProjectMortgageBusinessHandleStub.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('30','房地产登记受理审核业务书（在建）','房地产登记受理审核业务书（在建）','/report/ReisterApplyProjectMortgageCheckTable.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('31','商品房备案登记申请书','商品房备案登记申请书','/report/ApplyContractReisterBook.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('32','商品房撤销备案登记申请书','商品房撤销备案登记申请书','/report/ApplyContractReisterBookCancel.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('32','商品房撤销备案登记申请书','商品房撤销备案登记申请书','/report/ApplyContractReisterBookCancel.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('33','预购商品房预告登记询问笔录','预购商品房预告登记询问笔录','/report/NoticeRegAskRecord.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('34','预购商品房预告登记约定书','预购商品房预告登记约定书','/report/NoticeConvention.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('34','预购商品房预告登记约定书','预购商品房预告登记约定书','/report/NoticeConvention.xhtml');
INSERT INTO REPORT(ID,NAME,DESCRIPTION,PAGE) VALUES('35','其它登记询问笔录','其它登记询问笔录','/report/OtherRegAskRecord.xhtml');







-- 收费种类
INSERT INTO FEE_CATEGORY(ID, NAME, PRIORITY,DESCRIPTION) VALUES('REGISTER','登记费',1,null);
INSERT INTO FEE_CATEGORY(ID, NAME, PRIORITY,DESCRIPTION) VALUES('MAPPING','测绘费',2,null);
INSERT INTO FEE_CATEGORY(ID, NAME, PRIORITY,DESCRIPTION) VALUES('DEAL','交易费',3,null);
INSERT INTO FEE_CATEGORY(ID, NAME, PRIORITY,DESCRIPTION) VALUES('CARD','工本费',4,null);
-- 收费费用
INSERT INTO FEE(ID, CATEGORY, NAME, DESCRIPTION,FEE_EL,DETAILS_EL,PRIORITY) VALUES('REGISTER','REGISTER','登记费',null,null,null,1);
INSERT INTO FEE(ID, CATEGORY, NAME, DESCRIPTION,FEE_EL,DETAILS_EL,PRIORITY) VALUES('REGISTER.CHANG','REGISTER','变更登记费','半价登记费',null,null,2);
INSERT INTO FEE(ID, CATEGORY, NAME, DESCRIPTION,FEE_EL,DETAILS_EL,PRIORITY) VALUES('CARD','CARD','工本费',null,null,null,3);
INSERT INTO FEE(ID, CATEGORY, NAME, DESCRIPTION,FEE_EL,DETAILS_EL,PRIORITY) VALUES('DEAL.INHERIT.GIFT','DEAL','继承赠与手续费',null,null,null,4);
INSERT INTO FEE(ID, CATEGORY, NAME, DESCRIPTION,FEE_EL,DETAILS_EL,PRIORITY) VALUES('DEAL','DEAL','手续费',null,null,null,5);
INSERT INTO FEE(ID, CATEGORY, NAME, DESCRIPTION,FEE_EL,DETAILS_EL,PRIORITY) VALUES('DEAL.BACK','DEAL','回迁手续费',null,null,null,6);
INSERT INTO FEE(ID, CATEGORY, NAME, DESCRIPTION,FEE_EL,DETAILS_EL,PRIORITY) VALUES('DEAL.COMMERCIAL','DEAL','商品房手续费',null,null,null,7);
INSERT INTO FEE(ID, CATEGORY, NAME, DESCRIPTION,FEE_EL,DETAILS_EL,PRIORITY) VALUES('DEAL.RECORD','DEAL','备案手续费',null,null,null,8);
INSERT INTO FEE(ID, CATEGORY, NAME, DESCRIPTION,FEE_EL,DETAILS_EL,PRIORITY) VALUES('DEAL.RECORD.CANCEL','DEAL','撤销备案手续费',null,null,null,10);
INSERT INTO FEE(ID, CATEGORY, NAME, DESCRIPTION,FEE_EL,DETAILS_EL,PRIORITY) VALUES('MAPPING','MAPPING','测绘费',null,null,null,9);

-- 费用和业务
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP30','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP30','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP31','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP31','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP33','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP40','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP32','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP35','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP35','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP41','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP41','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP41','DEAL.COMMERCIAL');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP56','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP56','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP56','DEAL');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP57','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP57','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP57','DEAL');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP58','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP58','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP58','DEAL');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP59','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP59','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP59','DEAL');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP60','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP60','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP60','DEAL');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP61','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP61','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP61','DEAL');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP62','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP62','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP62','DEAL');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP63','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP63','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP63','DEAL');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP65','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP65','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP65','DEAL');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP66','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP66','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP66','DEAL');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP67','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP67','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP67','DEAL');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP86','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP86','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP86','DEAL.INHERIT.GIFT');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP90','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP90','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP90','DEAL');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP87','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP87','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP87','DEAL');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP71','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP71','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP71','DEAL');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP99','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP99','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP99','DEAL');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP72','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP72','CARD');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP72','DEAL.BACK');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP52','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP53','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP91','REGISTER.CHANG');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP102','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP55','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP9','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP10','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP11','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP13','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP14','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP15','REGISTER');
INSERT INTO BUFSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP16','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP18','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP19','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP83','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP100','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP115','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP44','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP45','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP114','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP1','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP2','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP3','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP69','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP111','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP112','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP5','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP6','REGISTER');
INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP7','REGISTER');

INSERT INTO BUSINESS_AND_FEE(BUSINESS,FEE) VALUES ('WP43','DEAL.RECORD.CANCEL');
-- 业务


-- 测试业务
-- INSERT INTO BUSINESS_DEFINE(ID, NAME, WF_NAME, START_PAGE, START_DATA_VALIDATOR, TASK_SERVICE, CATEGORY, MEMO, VERSION) VALUES('system.business.test','测试流程','processTest','','','','erp.sale','测试流程',0);

-- 功能
INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('system.param', '系统参数管理', 'DATA_MGR', '', '/func/system/config/SystemParams.xhtml', '', '2', '系统运行方式设置',b'0');
INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('system.person', '人员维护', 'DATA_MGR', '', '/func/system/manager/PersonMgr.xhtml', '', '3', '管理自然人',b'0');
INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('system.businessConfig','业务配置','DAY_WORK','','/func/system/config/BusinessMgr.xhtml','','3','业务处理配置',b'0');
INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('system.employee','员工管理','DATA_MGR', '', '/func/system/manager/EmployeeMgr.xhtml','','4','员工和组织机构管理',b'0');
INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('system.role','角色管理','DATA_MGR','','/func/system/config/RoleMgr.xhtml','','4','角色管理和角色分配启动业务',b'0');
INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('system.word','字典管理','DATA_MGR','','/func/system/manager/WordMgr.xhtml','','5','字典管理',b'0');
INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('system.jbpmProcessDeployment','流程部署','DAY_WORK','','/func/system/jbpm/ProcessDefinition.xhtml','',7,'部署JBPM PAR 流程',b'0');

INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('system.processInstanceMgr', '流程管理', 'DAY_WORK', '', '/func/system/jbpm/ProcessInstanceMgr.xhtml', '', '6', '',b'0');

INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('house.houseMgr', '房屋管理', 'DATA_MGR', '', '/func/house/datas/HouseMgr.xhtml', '', '2', '',b'0');

INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('house.map.baseData', '测绘成果数据', 'DATA_MGR', '', '/func/house/datas/BaseDataMgr.xhtml', '', '1', '',b'0');


INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('house.Developer', '开发商管理', 'DATA_MGR', '', '/func/house/datas/DeveloperMgr.xhtml', '', '1', '',b'0');
INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('house.MappingCorporation', '测绘机构管理', 'DATA_MGR', '', '/func/house/datas/MappingCorporationMgr.xhtml', '', '2', '',b'0');
INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('house.EvaluateCorporation', '评估机构管理', 'DATA_MGR', '', '/func/house/datas/EvaluateCorporationMgr.xhtml', '', '3', '',b'0');

INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('house.businessSearch','房屋业务查询','DAY_WORK','','/func/house/owner/HouseBusinessSearch.xhtml','',1,'',b'0');
INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('house.businessMgr','业务管理','DAY_WORK','','/func/house/owner/BusinessView.xhtml','',1,'',b'0');
INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('owner.patchBusiness','档案补录','DAY_WORK','','/func/house/owner/PatchOwnerBusiness.xhtml','',3,'',b'0');
INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('owner.houseRecordSearch','房屋档案查询','DATA_MGR','','/func/house/owner/HouseRecordSearch.xhtml','',4,'',b'0');
INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('owner.attrCorpMgr','从业机构管理','DATA_MGR','','/func/house/datas/AttrCorpMgr.xhtml','',3,'',b'0');


INSERT INTO FUNCTION (ID, NAME, CATEGORY, ICON, LOCATION, BANNER, PRIORITY, MEMO,NEED_CONVERSATION) VALUES ('owner.totalDataExport','数据统计','TOTAL_REPORT','','/func/house/owner/TotalDataExport.xhtml','',1,'',b'0');


INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('Money', 'owner.totalDataExport');




-- 角色

INSERT INTO ROLE (ID, NAME, DESCRIPTION, PRIORITY)  VALUES('total.export.fee','收费数据统计','收费数据统计',1);

INSERT INTO ROLE (ID, NAME, DESCRIPTION,PRIORITY) VALUES ('system.config', '系统设置', '调整系统运行方式', 1);
INSERT INTO ROLE (ID, NAME, DESCRIPTION,PRIORITY) VALUES ('system.manager', '系统管理', '系统管理', 2);
INSERT INTO ROLE (ID, NAME, DESCRIPTION,PRIORITY) VALUES ('system.runBusinessMgr', '业务运行维护', '中止，挂起，分发正在运行的业务', 5);

INSERT INTO ROLE (ID, NAME, DESCRIPTION,PRIORITY) VALUES ('owner.businessView', '业务档案查看', '查看档案', 6);


INSERT INTO ROLE (ID, NAME, DESCRIPTION,PRIORITY) VALUES ('house.data.manager', '测绘成果管理', '空间库房屋相关数据管理', 3);
INSERT INTO ROLE (ID, NAME, DESCRIPTION,PRIORITY) VALUES ('owner.newHouse', '商品房管理', '商品房科', 4);

INSERT INTO ROLE (ID, NAME, DESCRIPTION,PRIORITY) VALUES ('owner.patchBusiness', '档案管理', '档案管理', 7);


INSERT INTO ROLE(ID, NAME, DESCRIPTION, PRIORITY) VALUES ('contractApply','备案网签受理','备案网签受理,仅针对网签业务',8);


-- ROLE_FUNCTION 角色种类
INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.config', 'system.param');
INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.config', 'system.businessConfig');
INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.config', 'system.employee');
INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.config', 'system.person');
INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.config', 'system.role');
INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.config','system.jbpmProcessDeployment');
INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.config', 'system.word');

INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.manager', 'system.role');
INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.manager', 'system.employee');
INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.manager', 'system.person');
INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.manager', 'system.word');
INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.manager', 'system.processInstanceMgr');
INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.manager', 'house.runBusinessMgr');
INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.manager', 'house.businessSearch');
INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.manager', 'house.businessMgr');

INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.manager', 'house.businessMgr');

INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('house.data.manager', 'house.map.baseData');

INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('system.manager', 'owner.attrCorpMgr');


INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('owner.patchBusiness', 'owner.patchBusiness');
INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('owner.patchBusiness', 'owner.houseRecordSearch');

INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('owner.patchBusiness', 'house.map.baseData');

INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('owner.newHouse', 'owner.attrCorpMgr');

INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('apply', 'house.businessSearch');
INSERT INTO ROLE_FUNCTION (ROL_ID, FUN_ID) VALUES ('apply', 'house.businessSearch');

-- ADMIN INSERY
-- INSERT INTO PERSON (ID,NAME,CREDENTIALS_TYPE,_FOREIGN,CREDENTIALS_NUMBER,DATE_OF_BIRTH) VALUES ('admin','admin','OTHER',1,'1','2013-07-15 10:27:08');
-- INSERT INTO EMPLOYEE(ID,ENABLE,PERSON_ID,PASSWORD,ORGANIZATION) VALUES ('admin',b'1','admin','admin','0');
-- INSERT INTO ROLE_CATE_EMP (EMP_ID, ROLE_ID) VALUES ('admin','admin');
-- INSERT INTO ROLE_ROLE_CATEGROY(ROLE_ID, CAT_ID) VALUES ('system.manager','admin');



-- WORD

INSERT INTO WORD_CATEGORY(ID, NAME, MEMO, SYSTEM) VALUES ('house.project.buildSize','建筑规模','',b'1');

INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('buildSize.small','small','小','house.project.buildSize','','1',b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('buildSize.big','big','大','house.project.buildSize','','2',b'1');


INSERT INTO WORD_CATEGORY(ID, NAME, MEMO, SYSTEM) VALUES ('project.landCardType','土地权证类型','',b'1');

INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('landCardType.stateOwned','stateOwned','国有土地使用证号','project.landCardType','','1',b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('landCardType.collective','collective','集体土地使用证号','project.landCardType','','2',b'1');


INSERT INTO WORD_CATEGORY(ID, NAME, MEMO, SYSTEM) VALUES ('system.empJob','职位','',b'1');

INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('system.empJob.manager','manager','科长','system.empJob','','3',b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('system.empJob.boss','boss','科员','system.empJob','','5',b'1');


INSERT INTO WORD_CATEGORY(ID, NAME, MEMO, SYSTEM) VALUES ('erp.bank','银行','',b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('erp.bank.ccb','CCB','建设银行','erp.bank','',2,b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('erp.bank.boc','BOC','中国银行','erp.bank','',1,b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('erp.bank.abc','ABC','农业银行','erp.bank','',3,b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('erp.bank.icbc','ICBC','工商银行','erp.bank','',4,b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('erp.bank.cmbc','CMBC','民生银行','erp.bank','',12,b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('erp.bank.cmb','CMB','招商银行','erp.bank','',5,b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('erp.bank.cib','CIB','兴业银行','erp.bank','',13,b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('erp.bank.bob','BOB','北京银行','erp.bank','',14,b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('erp.bank.bcm','BCM','交通银行','erp.bank','',6,b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('erp.bank.ceb','CEB','光大银行','erp.bank','',7,b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('erp.bank.citic','CITIC','中信银行','erp.bank','',8,b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('erp.bank.gdb','GDB','广东发展银行','erp.bank','',9,b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('erp.bank.spdb','SPDB','上海浦东发展银行','erp.bank','',10,b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('erp.bank.sdb','SDB','深圳发展银行','erp.bank','',11,b'1');

INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES('house.knotSize','套型',b'1','1');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES('house.useType','设计用途',b'1','25');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES('house.structure','结构',b'1','27');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES('house.wall','四墙类型',b'1','33');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES('house.build.buildType','楼幢类型',b'1','62');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES('house.houseType','房屋性质',b'1','64');

INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES('house.direction','房屋朝向',b'1','');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('house.direction.east','E','东','house.direction','',1,b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('house.direction.west','W','西','house.direction','',2,b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('house.direction.south','S','南','house.direction','',3,b'1');
INSERT INTO WORD(ID, _KEY, _VALUE, CATEGORY, DESCRIPTION, PRIORITY, ENABLE) VALUES ('house.direction.north','N','北','house.direction','',4,b'1');


INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(70,'所属行业',b'1','');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(71,'单位性质',b'1','');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(26,'产别',b'1','');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(28,'产权来源',b'1','');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(32,'土地性质',b'1','');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(42,'权利种类',b'1','');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(52,'评估方法',b'1','');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(60,'申请配租类别',b'1','');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(66,'供役地房屋利用方式',b'1','');

INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(67,'与产权人关系',b'1','');

INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(68,'共有情况',b'1','');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(55,'土地取得方式',b'1','');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(75,'学历',b'1','');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(79,'国籍',b'1','');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(80,'户籍所在地',b'1','');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(1357,'变更原因',b'1','');
INSERT INTO WORD_CATEGORY(ID,NAME,SYSTEM, MEMO) VALUES(76,'证书级别',b'1','');

INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(11,'北京市',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(12,'天津市',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(13,'河北省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(14,'山西省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(15,'内蒙古自治区',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(21,'辽宁省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(22,'吉林省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(23,'黑龙江省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(31,'上海市',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(32,'江苏省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(33,'浙江省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(34,'安徽省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(35,'福建省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(36,'江西省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(37,'山东省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(41,'河南省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(42,'湖北省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(43,'湖南省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(44,'广东省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(45,'广西壮族自治区',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(46,'海南省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(50,'重庆市',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(51,'四川省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(52,'贵州省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(53,'云南省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(54,'西藏自治区',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(61,'陕西省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(62,'甘肃省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(63,'青海省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(64,'宁夏回族自治区',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(65,'新疆维吾尔自治区',0);


INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(71,'台湾省',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(81,'香港特别行政区',0);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(82,'澳门特别行政区',0);


INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1101,'市辖区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1201,'市辖区',1);;
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1301,'石家庄市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1302,'唐山市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1303,'秦皇岛市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1304,'邯郸市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1305,'邢台市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1306,'保定市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1307,'张家口市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1308,'承德市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1309,'沧州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1310,'廊坊市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1311,'衡水市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1401,'太原市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1402,'大同市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1403,'阳泉市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1404,'长治市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1405,'晋城市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1406,'朔州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1407,'晋中市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1408,'运城市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1409,'忻州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1410,'临汾市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1411,'吕梁市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1501,'呼和浩特市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1502,'包头市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1503,'乌海市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1504,'赤峰市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1505,'通辽市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1506,'鄂尔多斯市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1507,'呼伦贝尔市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1508,'巴彦淖尔市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1509,'乌兰察布市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1522,'兴安盟',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1525,'锡林郭勒盟',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(1529,'阿拉善盟',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2101,'沈阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2102,'大连市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2103,'鞍山市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2104,'抚顺市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2105,'本溪市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2106,'丹东市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2107,'锦州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2108,'营口市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2109,'阜新市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2110,'辽阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2111,'盘锦市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2112,'铁岭市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2113,'朝阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2114,'葫芦岛市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2201,'长春市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2202,'吉林市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2203,'四平市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2204,'辽源市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2205,'通化市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2206,'白山市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2207,'松原市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2208,'白城市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2224,'延边朝鲜族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2301,'哈尔滨市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2302,'齐齐哈尔市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2303,'鸡西市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2304,'鹤岗市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2305,'双鸭山市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2306,'大庆市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2307,'伊春市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2308,'佳木斯市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2309,'七台河市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2310,'牡丹江市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2311,'黑河市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2312,'绥化市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(2327,'大兴安岭地区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3101,'市辖区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3201,'南京市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3202,'无锡市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3203,'徐州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3204,'常州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3205,'苏州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3206,'南通市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3207,'连云港市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3208,'淮安市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3209,'盐城市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3210,'扬州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3211,'镇江市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3212,'泰州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3213,'宿迁市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3301,'杭州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3302,'宁波市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3303,'温州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3304,'嘉兴市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3305,'湖州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3306,'绍兴市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3307,'金华市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3308,'衢州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3309,'舟山市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3310,'台州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3311,'丽水市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3401,'合肥市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3402,'芜湖市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3403,'蚌埠市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3404,'淮南市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3405,'马鞍山市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3406,'淮北市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3407,'铜陵市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3408,'安庆市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3410,'黄山市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3411,'滁州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3412,'阜阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3413,'宿州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3415,'六安市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3416,'亳州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3417,'池州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3418,'宣城市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3501,'福州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3502,'厦门市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3503,'莆田市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3504,'三明市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3505,'泉州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3506,'漳州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3507,'南平市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3508,'龙岩市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3509,'宁德市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3601,'南昌市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3602,'景德镇市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3603,'萍乡市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3604,'九江市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3605,'新余市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3606,'鹰潭市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3607,'赣州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3608,'吉安市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3609,'宜春市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3610,'抚州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3611,'上饶市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3701,'济南市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3702,'青岛市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3703,'淄博市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3704,'枣庄市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3705,'东营市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3706,'烟台市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3707,'潍坊市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3708,'济宁市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3709,'泰安市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3710,'威海市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3711,'日照市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3712,'莱芜市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3713,'临沂市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3714,'德州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3715,'聊城市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3716,'滨州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(3717,'菏泽市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4101,'郑州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4102,'开封市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4103,'洛阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4104,'平顶山市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4105,'安阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4106,'鹤壁市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4107,'新乡市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4108,'焦作市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4109,'濮阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4110,'许昌市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4111,'漯河市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4112,'三门峡市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4113,'南阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4114,'商丘市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4115,'信阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4116,'周口市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4117,'驻马店市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4190,'省直辖县级行政区划',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4201,'武汉市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4202,'黄石市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4203,'十堰市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4205,'宜昌市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4206,'襄阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4207,'鄂州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4208,'荆门市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4209,'孝感市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4210,'荆州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4211,'黄冈市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4212,'咸宁市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4213,'随州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4228,'恩施土家族苗族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4290,'省直辖县级行政区划',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4301,'长沙市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4302,'株洲市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4303,'湘潭市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4304,'衡阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4305,'邵阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4306,'岳阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4307,'常德市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4308,'张家界市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4309,'益阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4310,'郴州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4311,'永州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4312,'怀化市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4313,'娄底市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4331,'湘西土家族苗族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4401,'广州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4402,'韶关市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4403,'深圳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4404,'珠海市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4405,'汕头市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4406,'佛山市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4407,'江门市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4408,'湛江市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4409,'茂名市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4412,'肇庆市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4413,'惠州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4414,'梅州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4415,'汕尾市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4416,'河源市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4417,'阳江市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4418,'清远市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4419,'东莞市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4420,'中山市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4451,'潮州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4452,'揭阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4453,'云浮市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4501,'南宁市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4502,'柳州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4503,'桂林市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4504,'梧州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4505,'北海市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4506,'防城港市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4507,'钦州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4508,'贵港市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4509,'玉林市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4510,'百色市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4511,'贺州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4512,'河池市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4513,'来宾市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4514,'崇左市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4601,'海口市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4602,'三亚市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4603,'三沙市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(4690,'省直辖县级行政区划',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5001,'市辖区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5101,'成都市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5103,'自贡市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5104,'攀枝花市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5105,'泸州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5106,'德阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5107,'绵阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5108,'广元市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5109,'遂宁市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5110,'内江市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5111,'乐山市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5113,'南充市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5114,'眉山市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5115,'宜宾市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5116,'广安市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5117,'达州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5118,'雅安市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5119,'巴中市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5120,'资阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5132,'阿坝藏族羌族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5133,'甘孜藏族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5134,'凉山彝族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5201,'贵阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5202,'六盘水市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5203,'遵义市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5204,'安顺市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5205,'毕节市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5206,'铜仁市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5223,'黔西南布依族苗族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5226,'黔东南苗族侗族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5227,'黔南布依族苗族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5301,'昆明市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5303,'曲靖市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5304,'玉溪市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5305,'保山市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5306,'昭通市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5307,'丽江市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5308,'普洱市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5309,'临沧市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5323,'楚雄彝族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5325,'红河哈尼族彝族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5326,'文山壮族苗族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5328,'西双版纳傣族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5329,'大理白族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5331,'德宏傣族景颇族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5333,'怒江傈僳族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5334,'迪庆藏族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5401,'拉萨市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5421,'昌都地区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5422,'山南地区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5423,'日喀则地区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5424,'那曲地区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5425,'阿里地区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(5426,'林芝地区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6101,'西安市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6102,'铜川市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6103,'宝鸡市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6104,'咸阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6105,'渭南市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6106,'延安市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6107,'汉中市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6108,'榆林市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6109,'安康市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6110,'商洛市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6201,'兰州市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6202,'嘉峪关市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6203,'金昌市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6204,'白银市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6205,'天水市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6206,'武威市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6207,'张掖市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6208,'平凉市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6209,'酒泉市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6210,'庆阳市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6211,'定西市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6212,'陇南市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6229,'临夏回族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6230,'甘南藏族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6301,'西宁市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6321,'海东地区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6322,'海北藏族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6323,'黄南藏族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6325,'海南藏族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6326,'果洛藏族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6327,'玉树藏族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6328,'海西蒙古族藏族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6401,'银川市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6402,'石嘴山市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6403,'吴忠市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6404,'固原市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6405,'中卫市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6501,'乌鲁木齐市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6502,'克拉玛依市',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6521,'吐鲁番地区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6522,'哈密地区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6523,'昌吉回族自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6527,'博尔塔拉蒙古自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6528,'巴音郭楞蒙古自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6529,'阿克苏地区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6530,'克孜勒苏柯尔克孜自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6531,'喀什地区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6532,'和田地区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6540,'伊犁哈萨克自治州',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6542,'塔城地区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6543,'阿勒泰地区',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(6590,'自治区直辖县级行政区划',1);

INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(7101,'台湾',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(8101,'香港',1);
INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(8201,'澳门',1);

INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(8201,'澳门',1);

INSERT INTO PROVINCE_CODE(ID,NAME,LEVEL)VALUES(211282,'开原市',2);

INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (1,'1','二室一厅','house.knotSize','house.knotSize',1,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (2,'1','三室二厅','house.knotSize','',2,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3,'1','一室一厅','house.knotSize','',3,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (11,'1','一室一厅一卫','house.knotSize','',4,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (905,'0','非成套住宅','house.knotSize','',6,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (900,'1','成套住宅','house.knotSize','',5,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (7,'1','收款收据',3,'house.knotSize',0,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (8,'1','转用收款收据',3,'',2,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (9,'1','商品房专用收据',3,'',3,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (10,'1','doc',4,'house.knotSize',1,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (65,'1','jpg',4,'',3,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (66,'1','gif',4,'',4,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (864,'1','dwf',4,'',6,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (68,'1','共有权证',4,'',4,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (868,'1','dwg',4,'',7,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4221,'0','配电所','house.useType','',69,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4223,'0','平房仓','house.useType','',70,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4224,'0','生产车间','house.useType','',71,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4245,'0','综合楼','house.useType','',89,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4249,'0','仓库厂房','house.useType','',92,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4250,'0','办公楼、厂房、仓库','house.useType','',93,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4176,'0','办公楼','house.useType','house.structure',2,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4178,'0','包装车间','house.useType','',29,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4179,'0','备品库','house.useType','',30,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4183,'0','仓库工厂','house.useType','house.wall',1,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4184,'0','厂房及办公楼','house.useType','',34,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4185,'0','厂房及变电所','house.useType','',35,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4186,'0','厂房及辅助用房','house.useType','',36,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4187,'0','厂房及检验室','house.useType','',37,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4188,'0','厂房综合楼','house.useType','',38,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4190,'0','车间及办公楼','house.useType','',40,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4193,'0','低温库','house.useType','',43,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4196,'0','辅助设施及变电所','house.useType','',46,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4198,'0','公厕','house.useType','',48,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4201,'0','后加工车间','house.useType','',50,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4220,'0','门卫','house.useType','',68,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4228,'0','生活用水泵房','house.useType','',74,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4233,'0','维修中心机加车','house.useType','',78,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4234,'0','文化娱乐','house.useType','',79,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4237,'0','研发楼','house.useType','',82,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4239,'0','营业','house.useType','',84,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4243,'0','展示厅','house.useType','',87,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4244,'0','职工餐厅','house.useType','',88,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4246,'0','综合楼装配车间','house.useType','',90,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4247,'0','综合训练馆','house.useType','',91,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4173,'0','锅炉房','house.useType','',24,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4174,'0','4S店','house.useType','house.useType',1,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4217,'0','库房','house.useType','',65,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4218,'0','冷加工车间','house.useType','',66,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4219,'0','铆焊厂房','house.useType','',67,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4225,'0','热泵机房','house.useType','',72,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4227,'0','生产辅助用房','house.useType','',73,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4229,'0','食堂','house.useType','',75,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4231,'0','食堂浴池','house.useType','',76,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4232,'0','维修车间','house.useType','',77,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4235,'0','污泥脱水车间','house.useType','',80,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4236,'0','物流中心','house.useType','',81,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4238,'0','营房','house.useType','',83,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4241,'0','幼儿园','house.useType','',85,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4242,'0','预处理工场','house.useType','',86,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4192,'0','粗细格栏间','house.useType','',42,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (2771,'0','阁楼','house.useType','house.wall',1,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4199,'0','公建','house.useType','',49,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4206,'0','集体宿舍','house.useType','',54,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3918,'16','附属用房','house.useType','',45,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3927,'0','公寓','house.useType','',47,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4208,'0','检测中心','house.useType','',56,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4209,'0','检验','house.useType','',57,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4210,'0','教育','house.useType','',58,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (796,'0','警卫室','house.useType','',59,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3920,'17','厂房','house.useType','',46,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4213,'0','科研','house.useType','',61,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4214,'0','科研楼','house.useType','',1,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4215,'0','空压站','house.useType','',63,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4216,'0','控制车间','house.useType','house.houseType',1,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3877,'0','洗车房','house.useType','',34,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3880,'0','非住宅','house.useType','',35,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3882,'0','加油站','house.useType','',36,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3887,'0','停车场','house.useType','',39,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (80,'12','住宅','house.useType','house.knotSize',1,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (81,'1','车库','house.useType','',2,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (82,'1','网点','house.useType','',3,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4161,'0','厕所','house.useType','',19,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4165,'0','农贸市场','house.useType','',20,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (785,'0','商业','house.useType','倒库',5,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4169,'0','商业网点','house.useType','',21,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4171,'0','摊位','house.useType','',22,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (788,'0','办公','house.useType','倒库',8,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (789,'0','商住','house.useType','倒库',9,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (791,'0','仓库','house.useType','倒库',11,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (800,'0','服务业','house.useType','倒库',20,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (802,'0','教育','house.useType','倒库',22,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (808,'0','其它用途','house.useType','倒库',28,b'0');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4172,'0','变电所','house.useType','',23,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4175,'0','MBR综合间','house.useType','',26,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4177,'0','办公楼及厂房','house.useType','',28,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4181,'0','泵房','house.useType','',31,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4182,'0','餐厅浴池','house.useType','',32,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4189,'0','超细格栏间','house.useType','',39,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4191,'0','成品库','house.useType','',41,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4194,'0','房管设施','house.useType','',44,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4195,'0','服务业','house.useType','',45,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4197,'0','钢管成品库','house.useType','',47,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4202,'0','换热站','house.useType','',51,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4203,'0','机加车间厂房','house.useType','',52,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4204,'0','机修车间厂房','house.useType','',53,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4207,'0','加氟车间','house.useType','',55,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4212,'0','净化车间','house.useType','',60,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (84,'1','直管产',26,'',1,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (85,'1','自管产',26,'',2,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (86,'1','军产',26,'',3,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (87,'1','私产',26,'',4,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (811,'0','个人独资',26,'倒库',5,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (812,'0','集体所有房产',26,'倒库',6,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (814,'0','涉外产',26,'倒库',8,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (817,'0','股份房产',26,'倒库',11,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (819,'0','有限责任',26,'倒库',13,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (909,'0','其它',26,'倒库',17,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (1559,'0','砖混','house.structure','',8,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (88,'1','钢','house.structure','',1,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4160,'0','框架','house.structure','',6,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4164,'0','剪力墙','house.structure','',7,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4168,'0','框架剪力墙','house.structure','',8,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (823,'0','钢混','house.structure','倒库',5,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (824,'0','混合','house.structure','倒库',6,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (827,'0','其它','house.structure','倒库',10,b'0'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4064,'0','合并',28,'',50,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3858,'0','商品房门市',28,'',44,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3874,'0','商品房初始登记',28,'',45,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3905,'0','注销',28,'',47,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (91,'1','自建',28,'',1,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (92,'1','回迁',28,'',2,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (93,'1','继承',28,'',3,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (910,'0','婚后财产增加共有人',28,'倒库',40,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (828,'0','买卖',28,'倒库',4,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (829,'0','翻建',28,'倒库',5,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (830,'0','购买商品房',28,'倒库',6,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (831,'0','商品房抵押',28,'倒库',7,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (833,'0','赠与',28,'倒库',9,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (834,'0','法院判决',28,'倒库',10,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (836,'0','公变私',28,'倒库',12,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (840,'0','离婚',28,'倒库',16,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (841,'0','拍卖',28,'倒库',17,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (843,'0','丢失补发',28,'倒库',19,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (844,'0','换证',28,'倒库',20,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (847,'0','更正',28,'倒库',23,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (854,'0','面积变更',28,'倒库',30,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (859,'0','民事调解',28,'倒库',35,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (860,'0','变更换证',28,'倒库',36,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (911,'0','直管产',28,'倒库',41,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (100,'1','集体',32,'',1,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (101,'1','私有',32,'',2,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (870,'0','国有',32,'倒库',3,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (912,'0','其它',32,'倒库',4,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (105,'1','自墙','house.wall','',1,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (106,'1','共墙','house.wall','',2,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (107,'1','借墙','house.wall','',3,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (121,'1','按揭贷款',42,'',1,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (122,'2','抵押贷款',42,'',2,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (166,'1','市场比较法',52,'',1,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (167,'1','收益还原法',52,'',2,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (168,'1','成本法',52,'',3,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (169,'1','剩余法',52,'',4,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (170,'1','趋势法',52,'',5,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (171,'1','购买年法',52,'',6,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (172,'1','路线价法',52,'',7,b'1');INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (180,'1','出让',55,'',1,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (181,'1','划拨',55,'',2,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (182,'1','集体建设用地',55,'',3,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (183,'1','出租',55,'',4,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (184,'1','宅基地使用权',55,'',5,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (185,'1','其他方式',55,'',6,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (913,'0','其它',55,'倒库',7,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (191,'1','实物配租',60,'',1,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (192,'1','租金补贴',60,'',2,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (193,'1','租金核减',60,'',3,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (2772,'4','平房','house.build.buildType','',4,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (202,'1','多层','house.build.buildType','',1,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (204,'3','别墅','house.build.buildType','',3,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (203,'2','高层','house.build.buildType','',2,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (4270,'0','其它',28,'',51,b'0');

INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (781,'0','经济适用住房','house.houseType','倒库',3,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (782,'0','廉租住房','house.houseType','倒库',4,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (783,'0','集资合作建房','house.houseType','倒库',5,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (1946,'1','回迁房屋','house.houseType','',7,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (213,'1','供役地房屋利用方式',66,'',1,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3850,'1','母女',67,'',4,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3851,'1','母子',67,'',5,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3852,'1','父子',67,'',6,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3853,'1','父女',67,'',7,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3854,'1','父亲',67,'',8,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3855,'1','母亲',67,'',9,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3856,'1','其它',67,'',10,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (215,'1','夫妻',67,'',1,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (216,'1','兄弟',67,'',2,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (217,'1','姐妹',67,'',3,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (218,'1','夫妻共有',68,'',1,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (219,'1','按份共有',68,'',2,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (221,'1','单独所有',68,'',3,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (222,'1','共同共有',68,'',4,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3909,'0','一级',70,'',2,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3910,'0','二级',70,'',3,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3926,'0','暂定资质',70,'',4,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (884,'0','三级',70,'',1,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (904,'0','合资经营',71,'倒库',5,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (880,'0','国有',71,'',1,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (886,'0','私有',71,'',2,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (902,'0','有限责任制',71,'倒库',3,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (903,'0','中外合资',71,'倒库',4,b'1');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (13,'0','其它',71,'倒库',4,b'0');
INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (3045,'0','中专',75,'',3,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (925,'0','大专',75,'',1,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (926,'0','本科',75,'',2,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (927,'0','普通',76,'',1,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (928,'0','高级',76,'',2,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (936,'0','境外',79,'',1,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (935,'0','境内',79,'',0,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (937,'0','城区',80,'',0,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (938,'0','农村',80,'',1,b'1'); INSERT INTO WORD(ID,_KEY,_VALUE,CATEGORY,DESCRIPTION,PRIORITY,ENABLE) VALUES (939,'0','外来',80,'',2,b'1');




