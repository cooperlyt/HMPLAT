DELIMITER $$

DROP PROCEDURE IF EXISTS `HOUSE_OWNER_RECORD`.`GenDisplayAndKey` $$
CREATE PROCEDURE `HOUSE_OWNER_RECORD`.`GenDisplayAndKey` ()
  BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE H_ID VARCHAR(32);
    DECLARE O_ID VARCHAR(32);
    DECLARE B_ID VARCHAR(32);
    DECLARE H_HOUSE_CODE VARCHAR(32);
    DECLARE H_ADDRESS VARCHAR(200);
    DECLARE H_CODE VARCHAR(32);

    DECLARE  cur_house CURSOR FOR   SELECT h.ID,bh.ID,bh.BUSINESS_ID, bh.HOUSE_CODE,h.ADDRESS,concat(h.MAP_NUMBER,'-',h.BLOCK_NO,'-',h.BUILD_NO,'-',h.HOUSE_ORDER) FROM BUSINESS_HOUSE bh LEFT JOIN HOUSE h on h.ID=bh.AFTER_HOUSE where bh.SEARCH_KEY is null or bh.DISPLAY is null;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN  cur_house;

    REPEAT
      FETCH cur_house INTO H_ID,B_ID,O_ID,H_HOUSE_CODE,H_ADDRESS,H_CODE;
      IF not done THEN
          DECLARE O_NAME VARCHAR(50);
          DECLARE O_ID_NO VARCHAR(100);
          DECLARE SK VARCHAR(2048);

          SET SK = concat('[',H_HOUSE_CODE,'][',H_ADDRESS,'][',H_CODE,']');

          DECLARE cur_owner CURSOR FOR SELECT po.NAME,po.ID_NO FROM HOUSE_OWNER ho LEFT JOIN POWER_OWNER po on po.ID = ho.POOL WHERE not po.OLD and ho.HOUSE = H_ID;
          DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

          OPEN cur_owner;
          REPEAT
            FETCH cur_owner INTO O_NAME,O_ID_NO;

            IF not done THEN
              SET SK = concat(SK,'[',O_NAME,'][',O_ID_NO,']');

            END IF;
          UNTIL done END REPEAT;
          SET done = 0;

          DECLARE C_NUMBER VARCHAR(50);
          SELECT CONTRACT_NUMBER INTO C_NUMBER FROM HOUSE_CONTRACT where BUSINESS = B_ID;
          if C_NUMBER is not null THEN
              SET SK = concat(SK,'[',C_NUMBER,']');
          END IF;



          SET done = 0;
      END IF;

    UNTIL done END REPEAT ;



  END $$