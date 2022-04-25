-- 销售许可
USE HOUSE_CENTER_PUSH;
DROP PROCEDURE IF EXISTS PUSH_PROJECT_DATA;
DROP PROCEDURE IF EXISTS PUSH_PROJECT;
UPDATE WAITING_DATA SET PUSHED = false;
DELETE FROM TRADE_RECORD;
DELIMITER ||
CREATE PROCEDURE PUSH_PROJECT_DATA(IN area_code char(6),IN area_name char(200), IN business_id varchar(32), IN P_JYZT char(2))
BEGIN
  DECLARE C_YWBH char(100);
  DECLARE P_TYPE varchar(20);
  DECLARE P_STATUS varchar(20);

  DECLARE C_LLYWLB char(100);
  DECLARE C_LLYWLBBM char(3);
  DECLARE C_YYWBH char(100);

  DECLARE C_JYYWZMZSMC char(200);
  DECLARE C_JYYWZMZSMCBM char(3);
  DECLARE V_JYYWZMZSH char(200);
  DECLARE V_JYZQC char(200);
  DECLARE V_JYZZJHM char(100);
  DECLARE V_YWBJSJ char(19);

  DECLARE P_PROJECT varchar(32);

  DECLARE C_FWZT char(2);
  DECLARE V_QX char(200);
  DECLARE V_XQ char(200);

   
  
  SELECT ob.STATUS, pi.TYPE , c.NUMBER , p.DEVELOPER_NAME ,ad.LICENSE_NUMBER ,DATE_FORMAT(IFNULL(ob.RECORD_TIME,ob.APPLY_TIME), '%Y-%m-%d %T'), p.ID ,p.DISTRICT_NAME, p.SECTION_NAME
  INTO P_STATUS,P_TYPE , V_JYYWZMZSH , V_JYZQC , V_JYZZJHM ,V_YWBJSJ, P_PROJECT ,V_QX ,V_XQ
  FROM HOUSE_OWNER_RECORD.PROJECT p 
  LEFT JOIN HOUSE_OWNER_RECORD.OWNER_BUSINESS ob ON ob.ID = p.BUSINESS 
  LEFT JOIN HOUSE_OWNER_RECORD.PROJECT_SELL_INFO pi ON pi.ID = p.ID 
  LEFT JOIN HOUSE_OWNER_RECORD.PROJECT_CARD pc ON pc.PROJECT = pi.ID
  LEFT JOIN HOUSE_OWNER_RECORD.MAKE_CARD c ON c.ID = pc.ID
  LEFT JOIN HOUSE_INFO.DEVELOPER d ON d.ID = p.DEVELOPER_CODE
  LEFT JOIN HOUSE_INFO.ATTACH_CORPORATION ad ON d.ATTACH_ID = ad.ID
  WHERE p.BUSINESS = business_id and p.DEVELOPER_NAME is not null;

  IF P_JYZT='01' THEN
    SET C_YWBH = CONCAT('D-',business_id);
    SET C_YYWBH = business_id;

    IF P_TYPE = 'NOW_SELL' THEN
      SET C_LLYWLB = '现售许可注销';
      SET C_LLYWLBBM = '101';
    ELSEIF P_TYPE = 'MAP_SELL' THEN
      SET C_LLYWLB = '预售许可注销';
      SET C_LLYWLBBM = '036';
    END IF;
  ELSE
    SET C_YWBH = business_id;
    SET C_YYWBH = '无';
    IF P_TYPE = 'NOW_SELL' THEN
      SET C_LLYWLB = '现售许可';
      SET C_LLYWLBBM = '037';     
    ELSEIF P_TYPE = 'MAP_SELL' THEN
      SET C_LLYWLB = '预售许可';
      SET C_LLYWLBBM = '001';
    END IF;
  END IF;

  IF P_TYPE = 'NOW_SELL' THEN
      SET C_JYYWZMZSMC = '商品房现售许可证';
      SET C_JYYWZMZSMCBM = '006';
      SET C_FWZT = '02';
  ELSEIF P_TYPE = 'MAP_SELL' THEN
      SET C_JYYWZMZSMC = '商品房预售许可证';
      SET C_JYYWZMZSMCBM = '001';
      SET C_FWZT = '01';
  END IF;

  BEGIN
    DECLARE V_FWBM char(100);
    DECLARE V_LH char(100);
    DECLARE V_MYC char(100);
    DECLARE V_DY char(100);
    DECLARE V_FH varchar(100);
    DECLARE V_FWZL varchar(500);
    DECLARE V_JZMJ float(18,2);
    DECLARE V_TNJZMJ float(18,2);
    DECLARE V_GTJZMJ float(18,2);
    DECLARE D_JZJG varchar(100);
    DECLARE C_JZJGBM char(2);
    DECLARE D_FWYT varchar(100);
    DECLARE C_FWYTBM char(3);

    DECLARE E_FWXZBM varchar(32);
    DECLARE C_FWXZBM char(2);
    DECLARE C_FWXZ char(60);
    DECLARE E_FWLXBM varchar(32);
    DECLARE C_FWLXBM char(2);
    DECLARE C_FWLX char(60);
    DECLARE C_SZQSC int(4);

    DECLARE C_JYZZJMC char(100);
    DECLARE C_JYZZJMCBM char(2);


    DECLARE fetch_eof boolean DEFAULT FALSE;
    DECLARE house_cursor CURSOR FOR SELECT h.ID, b.BUILD_NO,h.IN_FLOOR_NAME,h.HOUSE_UNIT_NAME,IFNULL(h.HOUSE_ORDER,'无'), IFNULL(h.ADDRESS,b.NAME), 
        IFNULL(h.HOUSE_AREA,0.0) as JZMJ , IFNULL(h.USE_AREA,0.0) as TNJZMJ, IFNULL(h.COMM_AREA,0.0) as GTJZMJ,
        IFNULL(IFNULL(JZJG._VALUE,h.STRUCTURE),'无'), IFNULL(h.DESIGN_USE_TYPE,'无') as FWYT, h.HOUSE_TYPE as FWXZBM , h.USE_TYPE as FWLXBM
        FROM HOUSE_INFO.HOUSE h 
        LEFT JOIN HOUSE_OWNER_RECORD.BUILD b on b.BUILD_CODE = h.BUILDID 
        LEFT JOIN DB_PLAT_SYSTEM.WORD JZJG on JZJG.ID = h.STRUCTURE
        WHERE b.PROJECT = P_PROJECT AND IFNULL(h.HOUSE_AREA,0) > 0 AND h.ID not in (SELECT eh.HOUSE_CODE FROM HOUSE_OWNER_RECORD.EXCEPT_HOUSE eh LEFT JOIN HOUSE_OWNER_RECORD.BUILD ehb on ehb.ID = eh.BUILD WHERE ehb.PROJECT = P_PROJECT);
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET fetch_eof=TRUE;
    OPEN house_cursor;
    house_loop: LOOP
      FETCH house_cursor INTO V_FWBM,V_LH,V_MYC,V_DY,V_FH,V_FWZL,V_JZMJ,V_TNJZMJ,V_GTJZMJ,D_JZJG,D_FWYT,E_FWXZBM,E_FWLXBM;
      IF fetch_eof THEN
        LEAVE house_loop;
      END IF;
        IF (V_TNJZMJ + V_GTJZMJ) <> V_JZMJ THEN

          IF (V_TNJZMJ > 0) and (V_GTJZMJ <=0) THEN
            SET V_GTJZMJ = V_JZMJ - V_TNJZMJ;
          ELSEIF (V_TNJZMJ <= 0) and (V_GTJZMJ > 0) THEN
            SET V_TNJZMJ = V_JZMJ - V_GTJZMJ;
          ELSE
            SET V_TNJZMJ = NULL;
            SET V_GTJZMJ = NULL;
          END IF;
    
        END IF;

        SET C_SZQSC = START_NUM(V_MYC);
        IF ISNULL(C_SZQSC) OR C_SZQSC >= 9999 THEN
          SET C_SZQSC = 1;
        END IF;

        IF ISNULL(V_FWZL) THEN 
          SET V_FWZL = V_QX;
          SET V_FWZL = IFNULL(V_FWZL,'无');
        END IF;


        IF LENGTH(TRIM(V_FH)) <= 0 THEN
          SET V_FH = '无';
        END IF;

        IF D_JZJG = '钢结构' or D_JZJG = '钢' THEN
          SET C_JZJGBM = '01';
        ELSEIF D_JZJG = '钢、钢筋混凝土结构' or D_JZJG = '钢、钢筋混凝土' or D_JZJG = '钢、钢混' THEN
          SET C_JZJGBM = '02';
        ELSEIF D_JZJG ='钢筋混凝土结构' or D_JZJG ='钢筋混凝土' THEN
          SET C_JZJGBM = '03';
        ELSEIF D_JZJG = '混合结构' or D_JZJG = '混合' THEN
          SET C_JZJGBM = '04';
        ELSEIF D_JZJG = '砖木结构' or D_JZJG = '砖木' THEN
          SET C_JZJGBM = '05';
        ELSE 
          SET C_JZJGBM = '06';
        END IF;


        IF D_FWYT = '住宅' THEN
          SET C_FWYTBM = '001'; 
        ELSEIF  D_FWYT = '成套住宅' THEN
          SET C_FWYTBM = '002'; 
        ELSEIF  D_FWYT = '别墅' THEN
          SET C_FWYTBM = '003'; 
        ELSEIF  D_FWYT = '高档公寓' THEN
          SET C_FWYTBM = '004'; 
        ELSEIF  D_FWYT = '非成套住宅' THEN
          SET C_FWYTBM = '005'; 
        ELSEIF  D_FWYT = '集体宿舍' THEN
          SET C_FWYTBM = '006'; 
        ELSEIF  D_FWYT = '工业、交通、仓储' THEN
          SET C_FWYTBM = '007'; 
        ELSEIF  D_FWYT = '工业' THEN
          SET C_FWYTBM = '008'; 
        ELSEIF  D_FWYT = '公共设施' THEN
          SET C_FWYTBM = '009'; 
        ELSEIF  D_FWYT = '铁路' THEN
          SET C_FWYTBM = '010'; 
        ELSEIF  D_FWYT = '民航' THEN
          SET C_FWYTBM = '011'; 
        ELSEIF  D_FWYT = '航运' THEN
          SET C_FWYTBM = '012'; 
        ELSEIF  D_FWYT = '公共运输' THEN
          SET C_FWYTBM = '013'; 
        ELSEIF  D_FWYT = '仓储' THEN
          SET C_FWYTBM = '014'; 
        ELSEIF  D_FWYT = '商业、金融、信息' THEN
          SET C_FWYTBM = '015'; 
        ELSEIF  D_FWYT = '商业服务' THEN
          SET C_FWYTBM = '016'; 
        ELSEIF  D_FWYT = '经营' THEN
          SET C_FWYTBM = '017'; 
        ELSEIF  D_FWYT = '旅游' THEN
          SET C_FWYTBM = '018'; 
        ELSEIF  D_FWYT = '金融保险' THEN
          SET C_FWYTBM = '019'; 
        ELSEIF  D_FWYT = '电讯信息' THEN
          SET C_FWYTBM = '020'; 
        ELSEIF  D_FWYT = '教育、医疗、卫生、科研' THEN
          SET C_FWYTBM = '021'; 
        ELSEIF  D_FWYT = '教育' THEN
          SET C_FWYTBM = '022'; 
        ELSEIF  D_FWYT = '医疗卫生' THEN
          SET C_FWYTBM = '023'; 
        ELSEIF  D_FWYT = '科研' THEN
          SET C_FWYTBM = '024'; 
        ELSEIF  D_FWYT = '文化、娱乐、体育' THEN
          SET C_FWYTBM = '025'; 
        ELSEIF  D_FWYT = '文化' THEN
          SET C_FWYTBM = '026'; 
        ELSEIF  D_FWYT = '新闻' THEN
          SET C_FWYTBM = '027'; 
        ELSEIF  D_FWYT = '娱乐' THEN
          SET C_FWYTBM = '028'; 
        ELSEIF  D_FWYT = '园林绿化' THEN
          SET C_FWYTBM = '029'; 
        ELSEIF  D_FWYT = '体育' THEN
          SET C_FWYTBM = '030'; 
        ELSEIF  D_FWYT = '办公' THEN
          SET C_FWYTBM = '031'; 
        ELSEIF  D_FWYT = '军事' THEN
          SET C_FWYTBM = '032'; 
        ELSEIF  D_FWYT = '涉外' THEN
          SET C_FWYTBM = '034'; 
        ELSEIF  D_FWYT = '宗教' THEN
          SET C_FWYTBM = '035'; 
        ELSEIF  D_FWYT = '监狱' THEN
          SET C_FWYTBM = '036'; 
        ELSEIF  D_FWYT = '物管用房' THEN
          SET C_FWYTBM = '037';
        ELSE
          SET C_FWYTBM = '033';
        END IF;

        IF E_FWXZBM='SALE_HOUSE' THEN 
          SET C_FWXZBM = '01'; 
          SET C_FWXZ = '市场化商品房'; 
        ELSEIF E_FWXZBM='BACK_HOUSE' THEN 
          SET C_FWXZBM = '02'; 
          SET C_FWXZ = '动迁房'; 
        ELSEIF E_FWXZBM='PUBLIC_RENT' THEN 
          SET C_FWXZBM = '04'; 
          SET C_FWXZ = '公共租赁住房'; 
        ELSEIF E_FWXZBM='GOV_RENT' THEN 
          SET C_FWXZBM = '05'; 
          SET C_FWXZ = '廉租住房'; 
        ELSEIF E_FWXZBM='LIMIT_PRICE_HOUSE' THEN 
          SET C_FWXZBM = '06'; 
          SET C_FWXZ = '限价普通商品住房'; 
        ELSEIF E_FWXZBM='GOV_SALE_HOUSE' THEN 
          SET C_FWXZBM = '07'; 
          SET C_FWXZ = '经济适用住房'; 
        ELSEIF E_FWXZBM='TARGET_HOUSE' THEN 
          SET C_FWXZBM = '08'; 
          SET C_FWXZ = '定销商品房'; 
        ELSEIF E_FWXZBM='GROUP_HOUSE' THEN 
          SET C_FWXZBM = '09'; 
          SET C_FWXZ = '集资建房'; 
        ELSEIF E_FWXZBM='WELFARE_HOUSE' THEN 
          SET C_FWXZBM = '10'; 
          SET C_FWXZ = '福利房'; 
        ELSEIF E_FWXZBM='OTHER' THEN 
          SET C_FWXZBM = '99'; 
          SET C_FWXZ = '其它'; 
        ELSEIF E_FWXZBM='COMM_USE_HOUSE' THEN 
          SET C_FWXZBM = '99'; 
          SET C_FWXZ = '共有建筑'; 
        ELSEIF E_FWXZBM='SELF_CREATE' THEN 
          SET C_FWXZBM = '99'; 
          SET C_FWXZ = '自建'; 
        ELSEIF E_FWXZBM='GOV_GROUP_HOUSE' THEN 
          SET C_FWXZBM = '99'; 
          SET C_FWXZ = '房改房'; 
        ELSEIF E_FWXZBM='STORE_HOUSE' THEN 
          SET C_FWXZBM = '01'; 
          SET C_FWXZ = '市场化商品房'; 
        ELSE
          SET C_FWXZBM = '99'; 
          SET C_FWXZ = '无'; 
        END IF;

        IF E_FWLXBM='DWELLING_KEY' THEN
          SET C_FWLXBM = '01';
          SET C_FWLX = '住宅';
        ELSEIF E_FWLXBM='SHOP_HOUSE_KEY' THEN
          SET C_FWLXBM = '02';
          SET C_FWLX = '商业用房';
        ELSEIF E_FWLXBM='STORE_HOUSE' THEN
          SET C_FWLXBM = '05';
          SET C_FWLX = '仓储用房';
        ELSEIF E_FWLXBM='OFFICE' THEN
          SET C_FWLXBM = '03';
          SET C_FWLX = '办公用房';
        ELSEIF E_FWLXBM='CAR_STORE' THEN
          SET C_FWLXBM = '06';
          SET C_FWLX = '车库';
        ELSEIF E_FWLXBM='INDUSTRY' THEN
          SET C_FWLXBM = '04';
          SET C_FWLX = '工业用房';
        ELSEIF E_FWLXBM='OTHER' THEN
          SET C_FWLXBM = '99';
          SET C_FWLX = '其它';
        ELSE
          SET C_FWLXBM = '99';
          SET C_FWLX = '无';
        END IF;       

        IF ISNULL(V_JYZZJHM) OR LENGTH(TRIM(V_JYZZJHM)) < 1 THEN
          SET C_JYZZJMC = '无';
          SET C_JYZZJMCBM = '99';
          SET V_JYZZJHM = V_JYZQC;
        ELSE
          SET C_JYZZJMC = '统一社会信用代码证';
          SET C_JYZZJMCBM = '02';        
        END IF;
        INSERT INTO TRADE_RECORD(YWBH,LLYWLB,LLYWLBBM,SJYWLB,YYWBH,JYZT,JYYWZMZSMC,JYYWZMZSMCBM,JYYWZMZSH,DJYWZMZSMC,DJYWZMZSMCBM,DJYWZMZSH,
              JYZLB,JYZLBBM,JYZQC,JYZZJMC,JYZZJMCBM ,JYZZJHM,JYZXZ,JYZXZBM,JYZHJ,JYZHJXZQH,
              BDCDYH,FWBM,FWZT,XZQHDM,QX,XZJDB,JJXZQH,JJXZQHDM,LJX,XQ,LH,SZQSC,MYC,DY,FH,FWZL,HXJS,HXJSBM,HXJG,HXJGBM,FWCX,FWCXBM,JZMJ,TNJZMJ,GTJZMJ,GHYT,JZJG,JZJGBM,FWYT,FWYTBM,FWXZ,FWXZBM,FWLX,FWLXBM,
              GYFS,GYFSBM,SZFE,FKLX,FKLXBM,DKFS,DKFSBM,HTSXRQ,YWBJSJ,YWBJRSFZH,YWBLSZXZQHDM)
        VALUES(C_YWBH,C_LLYWLB,C_LLYWLBBM,C_LLYWLB,C_YYWBH,P_JYZT,C_JYYWZMZSMC,C_JYYWZMZSMCBM,V_JYYWZMZSH,'无','999','无',
        '出让人','01',V_JYZQC,C_JYZZJMC,C_JYZZJMCBM, V_JYZZJHM ,'无','99','无','999999',
        '无',V_FWBM,C_FWZT,area_code,area_name,'无','无','999999','无',V_XQ,V_LH,C_SZQSC,V_MYC,V_DY,V_FH,V_FWZL,'无','99','无','99','无','99',V_JZMJ,V_TNJZMJ,V_GTJZMJ,'无',D_JZJG,C_JZJGBM,D_FWYT,C_FWYTBM,C_FWXZ,C_FWXZBM,C_FWLX,C_FWLXBM,
        '无','99','无','无','99','不确定','04',V_YWBJSJ,V_YWBJSJ,'无',area_code);

    END LOOP house_loop;
    CLOSE house_cursor;

  END;
END||
CREATE PROCEDURE PUSH_PROJECT(IN area_code char(6),IN area_name char(200))
BEGIN
  DECLARE fetch_eof boolean DEFAULT FALSE;

  DECLARE wait_id bigint;
  DECLARE business varchar(32);

  DECLARE wait_cursor CURSOR FOR SELECT ID,BUSIINESS_ID from WAITING_DATA WHERE NOT PUSHED AND DEFINE_ID = 'WP50'  ORDER BY CREATED;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET fetch_eof=TRUE;

  OPEN wait_cursor;

  wait_loop: LOOP
    FETCH wait_cursor INTO wait_id,business;

    IF fetch_eof THEN
      LEAVE wait_loop;
    END IF;
    START TRANSACTION;
    BEGIN
      DECLARE data_id varchar(32);
      DECLARE EXIT HANDLER FOR SQLEXCEPTION
      BEGIN
        ROLLBACK;
        SELECT 'an Error!';
      END;


      
      SELECT SELECT_BUSINESS INTO data_id FROM HOUSE_OWNER_RECORD.OWNER_BUSINESS WHERE ID = business;

      IF NOT ISNULL(data_id) THEN
        CALL PUSH_PROJECT_DATA(area_code,area_name,data_id,'01');
      END IF;
      CALL PUSH_PROJECT_DATA(area_code,area_name,business,'02');
      UPDATE WAITING_DATA SET PUSHED = true , PUSH_TIME = now() WHERE ID = wait_id;

    END;
    COMMIT;

  END LOOP wait_loop;

  CLOSE wait_cursor;

END||
DELIMITER ;