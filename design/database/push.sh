#!/bin/bash

UP_STATUS_CODE=1
ERR_STATUS_CODE=`date "+%Y%m%d%H"`

LOCAL_PWD="push@2020"
LOCAL_USER="push"
LOCAL_DB="HOUSE_CENTER_PUSH"
LOCAL_TABLE="TRADE_RECORD"

REMOTE_HOST="59.197.177.215"
REMOTE_USER="dandong123"
REMOTE_PWD="ddjngh@123"
REMOTE_PORT=6007
REMOTE_DB="dandongfengcheng"

CURRENT=`date "+%Y%m%d%H%M%S"`
echo "============================ ${CURRENT}  BEGIN ==================================="
TEMP_FILE_PATH="/tmp/push_${CURRENT}"

UP_STATUS_SQL="UPDATE ${LOCAL_TABLE} SET ISUP=${ERR_STATUS_CODE} WHERE ISUP=0;  UPDATE ${LOCAL_TABLE} SET ISUP = 0 WHERE ISUP = ${UP_STATUS_CODE};"

CLEAR_SQL="DELETE FROM ${LOCAL_TABLE} WHERE ISUP = 0 ;"
echo $UP_STATUS_SQL > ${TEMP_FILE_PATH}_1.sql \
  && mysql -u$LOCAL_USER -p$LOCAL_PWD -D $LOCAL_DB < ${TEMP_FILE_PATH}_1.sql \
  && echo "STEP 1: CHANGE STATUS TO ${UP_STATUS_CODE}" \
  && mysqldump -t --complete-insert --skip-add-locks  -u$LOCAL_USER -p$LOCAL_PWD $LOCAL_DB $LOCAL_TABLE --where " ISUP=0 " > ${TEMP_FILE_PATH}_data.sql \
  && echo "STEP 2: EXPORT DATA" \
  && mysql -h $REMOTE_HOST -P $REMOTE_PORT -u$REMOTE_USER -p$REMOTE_PWD -D $REMOTE_DB < ${TEMP_FILE_PATH}_data.sql \
  && echo "STEP 3: IMPORT DATA " \
  && echo $CLEAR_SQL > ${TEMP_FILE_PATH}_2.sql \
  && mysql -u$LOCAL_USER -p$LOCAL_PWD -D $LOCAL_DB < ${TEMP_FILE_PATH}_2.sql \
  && echo "STEP 4: CLEAR COMPLETE" \
  && rm -rf ${TEMP_FILE_PATH}*.sql \
  && echo "SUCCESS"
echo "=========================  ${CURRENT}  COMPLETED ================================="


#!/bin/bash
echo "PUSH"

DAY=`date "+%Y%m"`
/opt/tools/push_action.sh >> /opt/tools/push_${DAY}.log 2>&1