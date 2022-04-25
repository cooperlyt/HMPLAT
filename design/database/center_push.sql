SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS TRADE_RECORD;
DROP TABLE IF EXISTS WAITING_DATA;




/* Create Tables */

CREATE TABLE TRADE_RECORD
(
	XH int NOT NULL AUTO_INCREMENT,
	YWBH char(100),
	LLYWLB char(100),
	LLYWLBBM char(3),
	SJYWLB char(100),
	YYWBH char(100),
	-- 01 交易已完成            02 交易将完成
	JYZT char(2) COMMENT '01 交易已完成            02 交易将完成',
	JYYWZMZSMC char(200),
	-- 001 商品房预售许可证           002 商品期房转让合同备案证明
	-- 003 商品期房抵押合同备案证明   004 商品期房租赁合同备案证明
	-- 005 商品期房查封限制备案证明   006 商品房现售许可证
	-- 007 商品现房转让合同备案证明   008 商品现房抵押合同备案证明
	-- 009 商品现房租赁合同备案证明   010 存量房转让合同备案证明
	-- 011 存量房抵押合同备案证明     012 存量房租赁合同备案证明013 商品期房转让合同           014 商品期房抵押合同
	-- 015 商品期房租赁合同           016 商品现房转让合同
	-- 017 商品现房抵押合同           018 商品现房租赁合同
	-- 019 存量房转让合同             020 存量房抵押合同
	-- 021 存量房租赁合同
	JYYWZMZSMCBM char(3) COMMENT '001 商品房预售许可证           002 商品期房转让合同备案证明
003 商品期房抵押合同备案证明   004 商品期房租赁合同备案证明
005 商品期房查封限制备案证明   006 商品房现售许可证
007 商品现房转让合同备案证明   008 商品现房抵押合同备案证明
009 商品现房租赁合同备案证明   010 存量房转让合同备案证明
011 存量房抵押合同备案证明     012 存量房租赁合同备案证明013 商品期房转让合同           014 商品期房抵押合同
',
	JYYWZMZSH char(200),
	DJYWZMZSMC char(200),
	-- 001 不动产权证书     002 不动产登记证明
	-- 003 房屋所有权证     004 房屋他项权证
	-- 
	DJYWZMZSMCBM char(3) COMMENT '001 不动产权证书     002 不动产登记证明
003 房屋所有权证     004 房屋他项权证
',
	DJYWZMZSH char(200),
	JYZLB char(100),
	-- 01 出让人                  02 抵押人
	-- 03 出租人                  04 被查封限制人
	-- 05 受让方                  06 抵押权人
	-- 07 承租人                  08 查封限制人
	-- 09 出让人委托的代理人      10 抵押人委托的代理人
	-- 11 出租人委托的代理人      12 被查封限制人委托的代理人
	-- 13 受让方委托的代理人      14 抵押权人委托的代理人
	-- 15 承租人委托的代理人      16 查封限制人委托的代理人
	-- 17 出让人的法定代理人      18 抵押人的法定代理人
	-- 19 出租人的法定代理人      20 被查封限制人的法定代理人
	-- 21 受让方的法定代理人      22 抵押权人的法定代理人
	-- 23 承租人的法定代理人      24 查封限制人的法定代理人
	-- 
	JYZLBBM char(2) COMMENT '01 出让人                  02 抵押人
03 出租人                  04 被查封限制人
05 受让方                  06 抵押权人
07 承租人                  08 查封限制人
09 出让人委托的代理人      10 抵押人委托的代理人
11 出租人委托的代理人      12 被查封限制人委托的代理人
13 受让方委托的代理人      14 抵押权人委托的代理人
15 承租人委托的代理人      16 ',
	JYZQC char(200),
	JYZZJMC char(100),
	-- 01 居民身份证        02 统一社会信用代码证
	-- 03 港澳台身份证      04 护照
	-- 05 户口簿            06 军官证（士兵证）
	-- 07 组织机构代码      08 营业执照
	JYZZJMCBM char(2) COMMENT '01 居民身份证        02 统一社会信用代码证
03 港澳台身份证      04 护照
05 户口簿            06 军官证（士兵证）
07 组织机构代码      08 营业执照',
	JYZZJHM char(100),
	JYZXZ char(200),
	-- 01 本市城镇居民      02 本市非城镇居民
	-- 03 外省市个人        04 华侨
	-- 05 香港同胞          06 澳门同胞
	-- 07 台湾同胞          08 军人
	-- 09 外国个人          10 本省外市居民
	JYZXZBM char(2) COMMENT '01 本市城镇居民      02 本市非城镇居民
03 外省市个人        04 华侨
05 香港同胞          06 澳门同胞
07 台湾同胞          08 军人
09 外国个人          10 本省外市居民',
	JYZHJ char(500),
	JYZHJXZQH char(6),
	BDCDYH char(28),
	FWBM char(100),
	-- 01 期房              02 现房
	FWZT char(2) COMMENT '01 期房              02 现房',
	XZQHDM char(6),
	QX char(200),
	XZJDB char(200),
	JJXZQH char(200),
	JJXZQHDM char(6),
	LJX char(200),
	XQ char(200),
	LH char(200),
	SZQSC int(4),
	SZZZC int(4),
	MYC char(100),
	-- 原为单元
	DY char(100) COMMENT '原为单元',
	FH char(100),
	FWZL char(500),
	HXJS char(60),
	-- 01 一居室   02 二居室   03 三居室   04 四居室   05 五居室
	HXJSBM char(2) COMMENT '01 一居室   02 二居室   03 三居室   04 四居室   05 五居室',
	HXJG char(60),
	-- 01 平层         02 错层       03 复式楼          04 跃层
	HXJGBM char(2) COMMENT '01 平层         02 错层       03 复式楼          04 跃层',
	FWCX char(60),
	-- 01 东           02 西           03 南         04 北
	-- 05 东北         06 东南         07 西北       08 西南
	-- 
	FWCXBM char(2) COMMENT '01 东           02 西           03 南         04 北
05 东北         06 东南         07 西北       08 西南
',
	JZMJ float(18,2),
	TNJZMJ float(18,2),
	GTJZMJ float(18,2),
	GHYT char(60),
	JZJG char(60),
	-- 01 钢结构            02 钢、钢筋混凝土结构
	-- 03 钢筋混凝土结构    04 混合结构
	-- 05 砖木结构          06 其他结构
	-- 
	JZJGBM char(2) COMMENT '01 钢结构            02 钢、钢筋混凝土结构
03 钢筋混凝土结构    04 混合结构
05 砖木结构          06 其他结构
',
	FWYT char(60),
	-- 001 住宅        002 成套住宅        003 别墅
	-- 004 高档公寓    005 非成套住宅      006 集体宿舍
	-- 007 工业、交通、仓储  008 工业      009 公共设施
	-- 010 铁路        011 民航            012 航运
	-- 013 公共运输    014 仓储         015 商业、金融、信息
	-- 016 商业服务    017 经营            018 旅游
	-- 019 金融保险    020 电讯信息     021 教育、医疗、卫生、科研
	-- 022 教育        023 医疗卫生        024 科研
	-- 025 文化、娱乐、体育  026 文化      027 新闻
	-- 028 娱乐        029 园林绿化        030 体育
	-- 031 办公        032 军事            033 其它
	-- 034 涉外     035 宗教       036 监狱      037 物管用房
	FWYTBM char(3) COMMENT '001 住宅        002 成套住宅        003 别墅
004 高档公寓    005 非成套住宅      006 集体宿舍
007 工业、交通、仓储  008 工业      009 公共设施
010 铁路        011 民航            012 航运
013 公共运输    014 仓储         015 商业、金融、信息
016 商业服务    017 经营            018 旅游
019 金融保险    020 电讯信息     ',
	FWXZ char(60),
	-- 01 市场化商品房      02 动迁房
	-- 03 配套商品房        04 公共租赁住房
	-- 05 廉租住房          06 限价普通商品住房
	-- 07 经济适用住房      08 定销商品房
	-- 09 集资建房          10 福利房
	-- 
	FWXZBM char(2) COMMENT '01 市场化商品房      02 动迁房
03 配套商品房        04 公共租赁住房
05 廉租住房          06 限价普通商品住房
07 经济适用住房      08 定销商品房
09 集资建房          10 福利房
',
	FWLX char(60),
	-- 01 住宅              02 商业用房
	-- 03 办公用房          04 工业用房
	-- 05 仓储用房          06 车库
	FWLXBM char(2) COMMENT '01 住宅              02 商业用房
03 办公用房          04 工业用房
05 仓储用房          06 车库',
	-- 01 单独占有          02 共同共有        03 按份共有
	GYFS char(20) COMMENT '01 单独占有          02 共同共有        03 按份共有',
	-- 01 单独占有          02 共同共有        03 按份共有
	GYFSBM char(2) COMMENT '01 单独占有          02 共同共有        03 按份共有',
	SZFE char(60),
	CJJE float(18,2),
	FKLX char(60),
	-- 01 无抵押一次性付款
	-- 02 无抵押分期付款
	-- 03 抵押贷款一次性付款
	-- 04 抵押贷款分期付款
	-- 
	FKLXBM char(2) COMMENT '01 无抵押一次性付款
02 无抵押分期付款
03 抵押贷款一次性付款
04 抵押贷款分期付款
',
	DKFS char(100),
	-- 01 商业贷款   02 公积金贷款    03 组合贷款   04 不确定
	DKFSBM char(2) COMMENT '01 商业贷款   02 公积金贷款    03 组合贷款   04 不确定',
	HTSXRQ char(19),
	YWBJSJ char(19),
	YWBJRSFZH char(100),
	YWBLSZXZQHDM char(6),
	PRIMARY KEY (XH)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;


CREATE TABLE WAITING_DATA
(
	BUSIINESS_ID varchar(32) NOT NULL,
	DEFINE_ID varchar(32) NOT NULL,
	PUSHED boolean NOT NULL,
	CREATED datetime NOT NULL,
	PUSH_TIME timestamp,
	ID bigint NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (ID)
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8;



