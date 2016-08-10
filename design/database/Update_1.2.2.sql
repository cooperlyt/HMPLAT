﻿insert DB_PLAT_SYSTEM.SYSTEM_PARAM(ID, TYPE, VALUE, MEMO) VALUES ('ProjectRshipNamePrint','INTEGER',1,'1-预售许可证打印项目名称；2-打印项目中其中一个楼幢名称');
-- 先部署，后更新  修改 所有权证 缮证备注，权证编号 编辑，查看
use DB_PLAT_SYSTEM;
update VIEW_SUBSCRIBE left join SUBSCRIBE_GROUP on VIEW_SUBSCRIBE.GROUP_ID=SUBSCRIBE_GROUP.ID
set VIEW_SUBSCRIBE.REG_NAME='CardInfoOwnerRshipView'
where VIEW_SUBSCRIBE.REG_NAME='CardInfoView' and (DEFINE_ID='WP30' or DEFINE_ID='WP31'
or DEFINE_ID='WP1010' or DEFINE_ID='WP101' or DEFINE_ID='WP41' or DEFINE_ID='WP56'
or DEFINE_ID='WP59' or DEFINE_ID='WP58' or DEFINE_ID='WP60' or DEFINE_ID='WP86'
or DEFINE_ID='WP87' or DEFINE_ID='WP90' or DEFINE_ID='WP72' or DEFINE_ID='WP61'
or DEFINE_ID='WP62' or DEFINE_ID='WP63' or DEFINE_ID='WP65' or DEFINE_ID='WP66'
or DEFINE_ID='WP67' or DEFINE_ID='WP99' or DEFINE_ID='WP57' or DEFINE_ID='WP71'
or DEFINE_ID='WP52' or DEFINE_ID='WP102' or DEFINE_ID='WP53' or DEFINE_ID='WP91'
or DEFINE_ID='WP54' or DEFINE_ID='WP55' or DEFINE_ID='WP33' or DEFINE_ID='WP32'
or DEFINE_ID='WP153' or DEFINE_ID='WP151' or DEFINE_ID='WP152');

update VIEW_SUBSCRIBE left join SUBSCRIBE_GROUP on VIEW_SUBSCRIBE.GROUP_ID=SUBSCRIBE_GROUP.ID
set VIEW_SUBSCRIBE.REG_NAME='CardInfoOwnerRshipEdit'
where VIEW_SUBSCRIBE.REG_NAME='cardInfoEdit' and (DEFINE_ID='WP30' or DEFINE_ID='WP31'
or DEFINE_ID='WP1010' or DEFINE_ID='WP101' or DEFINE_ID='WP41' or DEFINE_ID='WP56'
or DEFINE_ID='WP59' or DEFINE_ID='WP58' or DEFINE_ID='WP60' or DEFINE_ID='WP86'
or DEFINE_ID='WP87' or DEFINE_ID='WP90' or DEFINE_ID='WP72' or DEFINE_ID='WP61'
or DEFINE_ID='WP62' or DEFINE_ID='WP63' or DEFINE_ID='WP65' or DEFINE_ID='WP66'
or DEFINE_ID='WP67' or DEFINE_ID='WP99' or DEFINE_ID='WP57' or DEFINE_ID='WP71'
or DEFINE_ID='WP52' or DEFINE_ID='WP102' or DEFINE_ID='WP53' or DEFINE_ID='WP91'
or DEFINE_ID='WP54' or DEFINE_ID='WP55' or DEFINE_ID='WP33' or DEFINE_ID='WP32');