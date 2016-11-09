

-- 预告人

update  BUSINESS_HOUSE h
  left join OWNER_BUSINESS ob on ob.ID = h.BUSINESS_ID
  left join HOUSE bh on bh.ID = h.START_HOUSE
  left join HOUSE ah on ah.ID = h.AFTER_HOUSE

    set ah.NOITCE_OWNER = bh.NOITCE_OWNER
where ob.DEFINE_ID ='WP1' and ah.NOITCE_OWNER is null;

update  BUSINESS_HOUSE h
  left join OWNER_BUSINESS ob on ob.ID = h.BUSINESS_ID
  left join HOUSE bh on bh.ID = h.START_HOUSE
  left join HOUSE ah on ah.ID = h.AFTER_HOUSE

set ah.NOITCE_OWNER = bh.NOITCE_OWNER
where ob.DEFINE_ID ='WP45' and ah.NOITCE_OWNER is null;

update  BUSINESS_HOUSE h
  left join OWNER_BUSINESS ob on ob.ID = h.BUSINESS_ID
  left join HOUSE bh on bh.ID = h.START_HOUSE
  left join HOUSE ah on ah.ID = h.AFTER_HOUSE

set ah.NOITCE_OWNER = bh.NOITCE_OWNER
where ob.DEFINE_ID ='WP2' and ah.NOITCE_OWNER is null;

-- 开发商




update  BUSINESS_HOUSE h
  left join OWNER_BUSINESS ob on ob.ID = h.BUSINESS_ID
  left join HOUSE bh on bh.ID = h.START_HOUSE
  left join HOUSE ah on ah.ID = h.AFTER_HOUSE

set ah.OLD_OWNER = bh.OLD_OWNER
where ob.DEFINE_ID in ('WP46','WP45','WP44','WP1','WP2','WP4','WP33','WP32','WP101','WP52','WP102','WP53','WP54','WP55','WP9','WP10','WP12','WP13','WP14','WP15','WP17' ) and ah.OLD_OWNER is null;





