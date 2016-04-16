use DB_PLAT_SYSTEM;
insert FEE (ID,CATEGORY,NAME,DESCRIPTION,FOR_EACH_VALUES,FOR_EACH_VAR,FEE_EL,DETAILS_EL,PRIORITY)
    value ('REGISTER.HB','REGISTER','登记费','','#{ownerBusinessHome.instance.houseBusinesses}','_houses','#{_houses.afterBusinessHouse.useType==''80''?160.0:1100.0}','#{_houses.afterBusinessHouse.useType==''80'' ? ''160'' : ''1100''}',14);
insert BUSINESS_AND_FEE (BUSINESS,FEE) values ('WP150','REGISTER.HB');


delete from VIEW_SUBSCRIBE where REG_NAME='RecordStoreHouseView';
delete from VIEW_SUBSCRIBE where REG_NAME='CheckEmpRecordEdit';
DELETE FROM TASK_SUBSCRIBE WHERE REG_NAME='RecordCoverPrint';
DELETE FROM VIEW_SUBSCRIBE WHERE REG_NAME='CheckEmpRecordEdit';
