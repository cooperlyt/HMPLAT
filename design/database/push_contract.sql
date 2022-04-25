
-- 备案
USE HOUSE_CENTER_PUSH;




UPDATE WAITING_DATA SET PUSHED = false;
DELETE FROM TRADE_RECORD;
DROP PROCEDURE IF EXISTS PUSH_CONTRACT;
DELIMITER ||
CREATE PROCEDURE PUSH_CONTRACT(IN area_code char(6),IN area_name char(200))
BEGIN
  DECLARE fetch_eof boolean DEFAULT FALSE;

  DECLARE wait_id bigint;
  DECLARE business_id varchar(32);
  DECLARE filter_id varchar(32);

  DECLARE wait_cursor CURSOR FOR SELECT ID,BUSIINESS_ID,DEFINE_ID from WAITING_DATA WHERE NOT PUSHED AND (DEFINE_ID = 'WP42' OR DEFINE_ID = 'WP43') ORDER BY CREATED;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET fetch_eof=TRUE;
  OPEN wait_cursor;

    wait_loop: LOOP
      FETCH wait_cursor INTO wait_id,business_id,filter_id;
      IF fetch_eof THEN
    	  LEAVE wait_loop;
      END IF;

      START TRANSACTION;
      BEGIN
        DECLARE P_TYPE varchar(32);

        DECLARE C_LLYWLB char(100);
        DECLARE C_LLYWLBBM char(3);
        DECLARE C_SJYWLB char(100);

        DECLARE C_JYYWZMZSMC char(200);
        DECLARE C_JYYWZMZSMCBM char(3);
        DECLARE V_JYYWZMZSH varchar(50);


        DECLARE V_FWBM varchar(32);
        DECLARE C_FWZT char(2);

        DECLARE V_XQ varchar(200);
        DECLARE V_LH varchar(100);

        DECLARE V_MYC varchar(100);
        DECLARE V_DY varchar(100);
        DECLARE V_FH varchar(100);
        DECLARE V_FWZL varchar(500);

        DECLARE V_JZMJ float(18,2);
        DECLARE V_TNJZMJ float(18,2);
        DECLARE V_GTJZMJ float(18,2);

        DECLARE D_JZJG varchar(100);
        DECLARE C_JZJGBM varchar(2);

        DECLARE D_FWYT varchar(60);
        DECLARE C_FWYTBM char(3);

        DECLARE E_FWXZBM varchar(32);
        DECLARE C_FWXZBM char(2);
        DECLARE C_FWXZ char(60);

        DECLARE E_FWLXBM varchar(32);
        DECLARE C_FWLXBM char(2);
        DECLARE C_FWLX char(60);

        DECLARE E_GYFSBM varchar(32);
        DECLARE C_GYFS char(20);
        DECLARE C_GYFSBM char(2);

        DECLARE V_CJJE float(18,2);
        DECLARE E_FKLXBM varchar(32);
        DECLARE C_FKLX char(60);
        DECLARE C_FKLXBM char(2);

        DECLARE V_HTSXRQ char(19);
        DECLARE V_YWBJSJ char(19);

        DECLARE C_SZQSC int(4);



        DECLARE house_id varchar(32);

        DECLARE developer char(200);
        DECLARE developer_id varchar(32);
        DECLARE developer_license char(100);

        DECLARE project_license char(200);
        DECLARE project_JYYWZMZSMC char(200);
        DECLARE project_JYYWZMZSMCBM char(3);

        DECLARE data_business varchar(32);

        DECLARE C_YYWBH char(100);

        DECLARE C_JYZT char(2);

        DECLARE C_D_JYZZJMC char(100);
        DECLARE C_D_JYZZJMCBM char(2);


        IF filter_id = 'WP42' THEN
          SET data_business = business_id;
          SET C_YYWBH = '无';
          SET C_JYZT = '02';
        ELSEIF filter_id = 'WP43' THEN
          SELECT SELECT_BUSINESS INTO data_business FROM HOUSE_OWNER_RECORD.OWNER_BUSINESS WHERE ID = business_id;
          SET C_YYWBH = data_business;
          SET C_JYZT = '01';
        ELSE
          ROLLBACK;
          LEAVE wait_loop;
        END IF;       


        SELECT hc.TYPE as type, hc.CONTRACT_NUMBER as JYYWZMZSH, bh.HOUSE_CODE as FWBM , h.SECTION_NAME as XQ, h.BUILD_NO as LH ,
        IFNULL(h.IN_FLOOR_NAME,'') as MYC, IFNULL(h.HOUSE_UNIT_NAME,'') as DY, h.HOUSE_ORDER as FH, IFNULL(h.ADDRESS,h.SECTION_NAME) as FWZL, IFNULL(h.HOUSE_AREA,0.0) as JZMJ , IFNULL(h.USE_AREA,0.0) as TNJZMJ, IFNULL(h.COMM_AREA,0.0) as GTJZMJ,
        IFNULL(IFNULL(JZJG._VALUE,h.STRUCTURE),'无'), IFNULL(h.DESIGN_USE_TYPE,'无') as FWYT, h.HOUSE_TYPE as FWXZBM , h.USE_TYPE as FWLXBM, h.POOL_MEMO as GYFSBM,
        IFNULL(hc.SUM_PRICE,0.00) as CJJE, hc.PAY_TYPE as FKLXBM , DATE_FORMAT(hc.CONTRACT_DATE, '%Y-%m-%d %T') as HTSXRQ, DATE_FORMAT(IFNULL(ob.RECORD_TIME,ob.APPLY_TIME), '%Y-%m-%d %T') as YWBJSJ, h.ID, h.DEVELOPER_NAME , h.DEVELOPER_CODE, hc.PROJECT_RSHIP_NUMBER
        INTO P_TYPE,V_JYYWZMZSH,V_FWBM,V_XQ,V_LH,V_MYC,V_DY,V_FH,V_FWZL,V_JZMJ,V_TNJZMJ,V_GTJZMJ,D_JZJG,D_FWYT,E_FWXZBM,E_FWLXBM,E_GYFSBM,V_CJJE,E_FKLXBM,V_HTSXRQ,V_YWBJSJ,house_id, developer , developer_id , project_license
        FROM HOUSE_OWNER_RECORD.OWNER_BUSINESS ob left join HOUSE_OWNER_RECORD.BUSINESS_HOUSE bh on bh.BUSINESS_ID = ob.ID left join HOUSE_OWNER_RECORD.HOUSE_CONTRACT hc on hc.ID = bh.ID left join HOUSE_OWNER_RECORD.HOUSE h on h.ID = bh.AFTER_HOUSE  
        LEFT JOIN DB_PLAT_SYSTEM.WORD JZJG on JZJG.ID = h.STRUCTURE WHERE ob.ID=data_business;
        IF V_CJJE <= 0 THEN
          UPDATE WAITING_DATA SET PUSHED = true , PUSH_TIME = now() WHERE ID = wait_id;
          COMMIT;
          ITERATE wait_loop;
        END IF;

        IF LENGTH(TRIM(V_FH)) <= 0 THEN
          SET V_FH = '无';
        END IF;

        SET C_SZQSC = START_NUM(V_MYC);
        IF ISNULL(C_SZQSC) OR C_SZQSC >= 9999 THEN
          SET C_SZQSC = 1;
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

        IF P_TYPE = 'NOW_SELL' THEN
          IF filter_id = 'WP42' THEN
            SET C_LLYWLBBM = '041';
            SET C_LLYWLB = '商品现房转让网签';
            SET C_SJYWLB = '商品现房转让网签';
          ELSE
            SET C_LLYWLBBM = '042';
            SET C_LLYWLB = '商品现房转让网签注销';
            SET C_SJYWLB = '商品现房转让网签注销';
          END IF;


          SET C_JYYWZMZSMCBM = '016';
          SET C_JYYWZMZSMC = '商品现房转让合同';
          SET C_FWZT = '02';
          SET project_JYYWZMZSMC = '商品房现售许可证';
          SET project_JYYWZMZSMCBM = '006';
        ELSEIF P_TYPE = 'MAP_SELL' THEN
          IF filter_id = 'WP42' THEN
            SET C_LLYWLBBM = '005';
            SET C_LLYWLB = '商品期房转让网签';
            SET C_SJYWLB = '商品期房转让网签';
          ELSE
            SET C_LLYWLBBM = '006';
            SET C_LLYWLB = '商品期房转让网签注销';
            SET C_SJYWLB = '商品期房转让网签注销';
          END IF;


          SET C_JYYWZMZSMCBM = '013';
          SET C_JYYWZMZSMC = '商品期房转让合同';
          SET C_FWZT = '01';
          SET project_JYYWZMZSMC = '商品房预售许可证';
          SET project_JYYWZMZSMCBM = '001';
        ELSE
          ROLLBACK;
          LEAVE wait_loop;
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


        IF E_GYFSBM = 'SHARE_OWNER' THEN
          SET C_GYFS = '按份共有';
          SET C_GYFSBM = '03';
        ELSEIF E_GYFSBM = 'SINGLE_OWNER' THEN
          SET C_GYFS = '单独占有';
          SET C_GYFSBM = '01';
        ELSEIF E_GYFSBM = 'TOGETHER_OWNER' THEN
          SET C_GYFS = '共同共有';
          SET C_GYFSBM = '02';
        ELSE
          SET C_GYFS = '无';
          SET C_GYFSBM = '99';       
        END IF;


        IF E_FKLXBM = 'ALL_PAY' THEN
          SET C_FKLX = '无抵押一次性付款';
          SET C_FKLXBM = '01';
        ELSEIF E_FKLXBM = 'DEBIT_PAY' THEN
          SET C_FKLX = '抵押贷款一次性付款';
          SET C_FKLXBM = '03';
        ELSEIF E_FKLXBM = 'OTHER_PAY' THEN
          SET C_FKLX = '其它方式';
          SET C_FKLXBM = '99';
        ELSEIF E_FKLXBM = 'PART_PAY' THEN
          SET C_FKLX = '无抵押分期付款';
          SET C_FKLXBM = '02';
        ELSE
          SET C_FKLX = '无';
          SET C_FKLXBM = '99';
        END IF;

        SELECT LICENSE_NUMBER INTO developer_license FROM HOUSE_INFO.DEVELOPER d LEFT JOIN HOUSE_INFO.ATTACH_CORPORATION a on a.ID = d.ATTACH_ID WHERE d.ID = developer_id;

        IF ISNUll(developer_license) OR LENGTH(TRIM(developer_license)) < 1 THEN
          SET developer_license = developer;
          SET C_D_JYZZJMC = '无';
          SET C_D_JYZZJMCBM = '99';
        ELSE
          SET C_D_JYZZJMC = '统一社会信用代码证';
          SET C_D_JYZZJMCBM = '02';
        END IF;


        -- 开发商

        INSERT INTO TRADE_RECORD(YWBH,LLYWLB,LLYWLBBM,SJYWLB,YYWBH,JYZT,JYYWZMZSMC,JYYWZMZSMCBM,JYYWZMZSH,DJYWZMZSMC,DJYWZMZSMCBM,DJYWZMZSH,
            	JYZLB,JYZLBBM,JYZQC,JYZZJMC,JYZZJMCBM ,JYZZJHM,JYZXZ,JYZXZBM,JYZHJ,JYZHJXZQH,
            	BDCDYH,FWBM,FWZT,XZQHDM,QX,XZJDB,JJXZQH,JJXZQHDM,LJX,XQ,LH,SZQSC,MYC,DY,FH,FWZL,HXJS,HXJSBM,HXJG,HXJGBM,FWCX,FWCXBM,JZMJ,TNJZMJ,GTJZMJ,GHYT,JZJG,JZJGBM,FWYT,FWYTBM,FWXZ,FWXZBM,FWLX,FWLXBM,
            	GYFS,GYFSBM,SZFE,CJJE,FKLX,FKLXBM,DKFS,DKFSBM,HTSXRQ,YWBJSJ,YWBJRSFZH,YWBLSZXZQHDM)
        VALUES( business_id,C_LLYWLB,C_LLYWLBBM,C_SJYWLB,C_YYWBH,'01',project_JYYWZMZSMC,project_JYYWZMZSMCBM,project_license,'无','999','无',
            '出让人','01',developer,C_D_JYZZJMC,C_D_JYZZJMCBM,developer_license,'无','99','无','999999',
            '无',V_FWBM,C_FWZT,area_code,area_name,'无','无','999999','无',V_XQ,V_LH,C_SZQSC,V_MYC,V_DY,V_FH,V_FWZL,'无','99','无','99','无','99',V_JZMJ,V_TNJZMJ,V_GTJZMJ,'无',D_JZJG,C_JZJGBM,D_FWYT,C_FWYTBM,C_FWXZ,C_FWXZBM,C_FWLX,C_FWLXBM,
            C_GYFS,C_GYFSBM,'无',V_CJJE,C_FKLX,C_FKLXBM,'不确定','04',V_HTSXRQ,V_YWBJSJ,'无',area_code);

        
        BEGIN
          DECLARE V_JYZQC char(200);

          DECLARE E_JYZZJMCBM varchar(32);
          DECLARE C_JYZZJMCBM char(2);
          DECLARE C_JYZZJMC char(100);
          DECLARE V_JYZZJHM char(100);

          DECLARE C_JYZXZ char(200);
          DECLARE C_JYZXZBM char(2);

          DECLARE V_JYZHJ varchar(500);
          DECLARE C_JYZHJXZQH char(6) DEFAULT '999999';

          DECLARE V_SZFE char(60);

          DECLARE proxy_person varchar(32);


          DECLARE owner_cursor CURSOR FOR SELECT po.NAME , po.ID_TYPE, po.ID_NO, IFNULL(po.POOL_AREA,'无'),po.ROOT_ADDRESS, po.PROXY_PERSON  from HOUSE_OWNER_RECORD.POWER_OWNER po LEFT JOIN HOUSE_OWNER_RECORD.HOUSE_OWNER ho on ho.POOL = po.ID WHERE ho.HOUSE = house_id AND po.TYPE = 'CONTRACT' AND po.OLD = false;
          
          OPEN owner_cursor;

          owner_loop: LOOP
            FETCH owner_cursor INTO V_JYZQC,E_JYZZJMCBM,V_JYZZJHM,V_SZFE,V_JYZHJ,proxy_person;
            IF fetch_eof THEN
              SET fetch_eof = false;
    	      LEAVE owner_loop;
            END IF;

            IF V_SZFE = NULL or V_SZFE like '%NULL%' or ISNUll(V_SZFE) or V_SZFE = 'null' THEN
              SET V_SZFE = '无';
            END IF;



            IF E_JYZZJMCBM = 'SOLDIER_CARD' THEN
              SET C_JYZZJMCBM = '06';
              SET C_JYZZJMC = '士兵证';
              SET C_JYZXZ = '军人';
              SET C_JYZXZBM = '08';              
            ELSEIF E_JYZZJMCBM = 'MASTER_ID' THEN
              SET C_JYZZJMCBM = '01';
              SET C_JYZZJMC = '居民身份证';
                IF V_JYZZJHM LIKE '2106%' THEN
                  SET C_JYZXZ = '本市城镇居民';
                  SET C_JYZXZBM = '01';
                ELSEIF V_JYZZJHM LIKE '21%' THEN
                  SET C_JYZXZ = '本省外市居民';
                  SET C_JYZXZBM = '10';                 
                ELSEIF LENGTH(TRIM(V_JYZZJHM)) > 0 THEN
                  SET C_JYZXZ = '外省市个人';
                  SET C_JYZXZBM = '03';
                ELSE
                  SET C_JYZXZ = '无';
                  SET C_JYZXZBM = '99';                   
                END IF;

                SET V_JYZHJ = LOCATION_NAME(V_JYZZJHM);    

                IF V_JYZHJ = '无' THEN 
                  IF C_JYZXZBM = '99' THEN
                    SET C_JYZHJXZQH = '999999';
                  ELSE
                    SET C_JYZHJXZQH = LEFT(TRIM(V_JYZZJHM),6);
                  END IF;
                ELSE
                  SET C_JYZHJXZQH = LEFT(TRIM(V_JYZZJHM),6);
                END IF;

          
            ELSEIF E_JYZZJMCBM = 'COMPANY_CODE' THEN
              SET C_JYZZJMCBM = '02';
              SET C_JYZZJMC = '统一社会信用代码证';
              SET C_JYZXZ = '无';
              SET C_JYZXZBM = '99';                
            ELSEIF E_JYZZJMCBM = 'CORP_CODE' THEN
              SET C_JYZZJMCBM = '07';
              SET C_JYZZJMC = '组织机构代码';
              SET C_JYZXZ = '无';
              SET C_JYZXZBM = '99';                
            ELSEIF E_JYZZJMCBM = 'PASSPORT' THEN
              SET C_JYZZJMCBM = '04';
              SET C_JYZZJMC = '护照';
              SET C_JYZXZ = '外国个人';
              SET C_JYZXZBM = '09';  
            ELSEIF E_JYZZJMCBM = 'TW_ID' THEN
              SET C_JYZZJMCBM = '03';
              SET C_JYZZJMC = '港澳台身份证';
              SET C_JYZXZ = '台湾同胞';
              SET C_JYZXZBM = '07';               
            ELSEIF E_JYZZJMCBM = 'OFFICER_CARD' THEN
              SET C_JYZZJMCBM = '06';
              SET C_JYZZJMC = '军官证';
              SET C_JYZXZ = '军人';
              SET C_JYZXZBM = '08';              
            ELSEIF E_JYZZJMCBM = 'GA_ID' THEN
              SET C_JYZZJMCBM = '03';
              SET C_JYZZJMC = '港澳台身份证';
              IF V_JYZZJHM like 'H%' THEN
                SET C_JYZXZ = '香港同胞';
                SET C_JYZXZBM = '05';   
              ELSEIF V_JYZZJHM like 'M%' THEN
                SET C_JYZXZ = '澳门同胞';
                SET C_JYZXZBM = '06'; 
              ELSE
                SET C_JYZXZ = '无';
                SET C_JYZXZBM = '99'; 
              END IF;
            ELSE
              SET C_JYZZJMCBM = '99';
              SET C_JYZZJMC = '其它';
              SET C_JYZXZ = '无';
              SET C_JYZXZBM = '99';              
            END IF;

            IF V_JYZHJ = NULL THEN
              SET V_JYZHJ = '无';
            ELSEIF LENGTH(TRIM(V_JYZHJ)) <= 0  THEN
              SET V_JYZHJ = '无';
            END IF;

            SET V_JYZZJHM = IFNULL(V_JYZZJHM,V_JYZQC);

            INSERT INTO TRADE_RECORD(YWBH,LLYWLB,LLYWLBBM,SJYWLB,YYWBH,JYZT,JYYWZMZSMC,JYYWZMZSMCBM,JYYWZMZSH,DJYWZMZSMC,DJYWZMZSMCBM,DJYWZMZSH,
            	JYZLB,JYZLBBM,JYZQC,JYZZJMC,JYZZJMCBM ,JYZZJHM,JYZXZ,JYZXZBM,JYZHJ,JYZHJXZQH,
            	BDCDYH,FWBM,FWZT,XZQHDM,QX,XZJDB,JJXZQH,JJXZQHDM,LJX,XQ,LH,SZQSC,MYC,DY,FH,FWZL,HXJS,HXJSBM,HXJG,HXJGBM,FWCX,FWCXBM,JZMJ,TNJZMJ,GTJZMJ,GHYT,JZJG,JZJGBM,FWYT,FWYTBM,FWXZ,FWXZBM,FWLX,FWLXBM,
            	GYFS,GYFSBM,SZFE,CJJE,FKLX,FKLXBM,DKFS,DKFSBM,HTSXRQ,YWBJSJ,YWBJRSFZH,YWBLSZXZQHDM)
            VALUES( business_id,C_LLYWLB,C_LLYWLBBM,C_SJYWLB,C_YYWBH,C_JYZT,C_JYYWZMZSMC,C_JYYWZMZSMCBM,V_JYYWZMZSH,'无','999','无',
            '受让方','05',V_JYZQC,C_JYZZJMC,C_JYZZJMCBM,V_JYZZJHM,C_JYZXZ,C_JYZXZBM,V_JYZHJ,C_JYZHJXZQH,
            '无',V_FWBM,C_FWZT,area_code,area_name,'无','无','999999','无',V_XQ,V_LH,C_SZQSC,V_MYC,V_DY,V_FH,V_FWZL,'无','99','无','99','无','99',V_JZMJ,V_TNJZMJ,V_GTJZMJ,'无',D_JZJG,C_JZJGBM,D_FWYT,C_FWYTBM,C_FWXZ,C_FWXZBM,C_FWLX,C_FWLXBM,
            C_GYFS,C_GYFSBM,V_SZFE,V_CJJE,C_FKLX,C_FKLXBM,'不确定','04',V_HTSXRQ,V_YWBJSJ,'无',area_code);



            IF NOT ISNULL(proxy_person) THEN
            BEGIN
              DECLARE proxy_type varchar(16);

              DECLARE C_JYZLB char(100);
              DECLARE C_JYZLBBM char(2);

              SET C_JYZHJXZQH = '999999';
              SET V_JYZHJ = '无';

              SELECT TYPE,NAME,ID_TYPE,IFNULL(ID_NO,NAME) INTO proxy_type,V_JYZQC,E_JYZZJMCBM,V_JYZZJHM FROM HOUSE_OWNER_RECORD.PROXY_PERSON WHERE ID = proxy_person;

              IF proxy_type = 'ENTRUSTED' THEN
                SET C_JYZLB = '受让方委托的代理人';
                SET C_JYZLBBM = '13';
              ELSE
                SET C_JYZLB = '受让方的法定代理人';
                SET C_JYZLBBM = '21';
              END IF;

              IF E_JYZZJMCBM = 'SOLDIER_CARD' THEN
                SET C_JYZZJMCBM = '06';
                SET C_JYZZJMC = '士兵证';
                SET C_JYZXZ = '军人';
                SET C_JYZXZBM = '08';              
              ELSEIF E_JYZZJMCBM = 'MASTER_ID' THEN
                SET C_JYZZJMCBM = '01';
                SET C_JYZZJMC = '居民身份证';
                IF V_JYZZJHM LIKE '2106%' THEN
                  SET C_JYZXZ = '本市城镇居民';
                  SET C_JYZXZBM = '01';
                ELSEIF V_JYZZJHM LIKE '21%' THEN
                  SET C_JYZXZ = '本省外市居民';
                  SET C_JYZXZBM = '10';                 
                ELSEIF LENGTH(TRIM(V_JYZZJHM)) > 0 THEN
                  SET C_JYZXZ = '外省市个人';
                  SET C_JYZXZBM = '03';
                ELSE
                  SET C_JYZXZ = '无';
                  SET C_JYZXZBM = '99';                   
                END IF;


                SET V_JYZHJ = LOCATION_NAME(V_JYZZJHM);

                IF V_JYZHJ = '无' THEN 
                  IF C_JYZXZBM = '99' THEN
                    SET C_JYZHJXZQH = '999999';
                  ELSE
                    SET C_JYZHJXZQH = LEFT(TRIM(V_JYZZJHM),6);
                  END IF;
                ELSE
                  SET C_JYZHJXZQH = LEFT(TRIM(V_JYZZJHM),6);
                END IF;
      


              ELSEIF E_JYZZJMCBM = 'COMPANY_CODE' THEN
                SET C_JYZZJMCBM = '02';
                SET C_JYZZJMC = '统一社会信用代码证';
                SET C_JYZXZ = '无';
                SET C_JYZXZBM = '99';                
              ELSEIF E_JYZZJMCBM = 'CORP_CODE' THEN
                SET C_JYZZJMCBM = '07';
                SET C_JYZZJMC = '组织机构代码';
                SET C_JYZXZ = '无';
                SET C_JYZXZBM = '99';                
              ELSEIF E_JYZZJMCBM = 'PASSPORT' THEN
                SET C_JYZZJMCBM = '04';
                SET C_JYZZJMC = '护照';
                SET C_JYZXZ = '外国个人';
                SET C_JYZXZBM = '09';  
              ELSEIF E_JYZZJMCBM = 'TW_ID' THEN
                SET C_JYZZJMCBM = '03';
                SET C_JYZZJMC = '港澳台身份证';
                SET C_JYZXZ = '台湾同胞';
                SET C_JYZXZBM = '07';               
              ELSEIF E_JYZZJMCBM = 'OFFICER_CARD' THEN
                SET C_JYZZJMCBM = '06';
                SET C_JYZZJMC = '军官证';
                SET C_JYZXZ = '军人';
                SET C_JYZXZBM = '08';              
              ELSEIF E_JYZZJMCBM = 'GA_ID' THEN
                SET C_JYZZJMCBM = '03';
                SET C_JYZZJMC = '港澳台身份证';
                IF V_JYZZJHM like 'H%' THEN
                  SET C_JYZXZ = '香港同胞';
                  SET C_JYZXZBM = '05';   
                ELSEIF V_JYZZJHM like 'M%' THEN
                  SET C_JYZXZ = '澳门同胞';
                  SET C_JYZXZBM = '06'; 
                ELSE
                  SET C_JYZXZ = '无';
                  SET C_JYZXZBM = '99'; 
                END IF;
              ELSE
                SET C_JYZZJMCBM = '99';
                SET C_JYZZJMC = '其它';
                SET C_JYZXZ = '无';
                SET C_JYZXZBM = '99';              
              END IF;

            IF V_JYZHJ = NULL THEN
              SET V_JYZHJ = '无';
            ELSEIF LENGTH(TRIM(V_JYZHJ)) <= 0  THEN
              SET V_JYZHJ = '无';
            END IF;

            SET V_JYZZJHM = IFNULL(V_JYZZJHM,V_JYZQC);


	          INSERT INTO TRADE_RECORD(YWBH,LLYWLB,LLYWLBBM,SJYWLB,YYWBH,JYZT,JYYWZMZSMC,JYYWZMZSMCBM,JYYWZMZSH,DJYWZMZSMC,DJYWZMZSMCBM,DJYWZMZSH,
	          	JYZLB,JYZLBBM,JYZQC,JYZZJMC,JYZZJMCBM ,JYZZJHM,JYZXZ,JYZXZBM,JYZHJ,JYZHJXZQH,
	          	BDCDYH,FWBM,FWZT,XZQHDM,QX,XZJDB,JJXZQH,JJXZQHDM,LJX,XQ,LH,SZQSC,MYC,DY,FH,FWZL,HXJS,HXJSBM,HXJG,HXJGBM,FWCX,FWCXBM,JZMJ,TNJZMJ,GTJZMJ,GHYT,JZJG,JZJGBM,FWYT,FWYTBM,FWXZ,FWXZBM,FWLX,FWLXBM,
	          	GYFS,GYFSBM,SZFE,CJJE,FKLX,FKLXBM,DKFS,DKFSBM,HTSXRQ,YWBJSJ,YWBJRSFZH,YWBLSZXZQHDM)
	          VALUES( business_id,C_LLYWLB,C_LLYWLBBM,C_SJYWLB,C_YYWBH,C_JYZT,C_JYYWZMZSMC,C_JYYWZMZSMCBM,V_JYYWZMZSH,'无','999','无',
	            C_JYZLB,C_JYZLBBM,V_JYZQC,C_JYZZJMC,C_JYZZJMCBM,V_JYZZJHM,C_JYZXZ,C_JYZXZBM,'无',C_JYZHJXZQH,
	          '无',V_FWBM,C_FWZT,area_code,area_name,'无','无','999999','无',V_XQ,V_LH,C_SZQSC,V_MYC,V_DY,V_FH,V_FWZL,'无','99','无','99','无','99',V_JZMJ,V_TNJZMJ,V_GTJZMJ,'无',D_JZJG,C_JZJGBM,D_FWYT,C_FWYTBM,C_FWXZ,C_FWXZBM,C_FWLX,C_FWLXBM,
	          C_GYFS,C_GYFSBM,'无',V_CJJE,C_FKLX,C_FKLXBM,'不确定','04',V_HTSXRQ,V_YWBJSJ,'无',area_code);              
            END;
            END IF;

          END LOOP owner_loop;

          CLOSE owner_cursor;

        END;
      END;

      UPDATE WAITING_DATA SET PUSHED = true , PUSH_TIME = now() WHERE ID = wait_id;
      COMMIT;
    END LOOP wait_loop;
  CLOSE wait_cursor;

END||
DELIMITER ;