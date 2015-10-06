package com.dgsoft.house.owner.action;

import com.dgsoft.common.system.business.BusinessDefineCache;
import com.dgsoft.common.system.model.BusinessDefine;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import javax.persistence.NoResultException;
import java.util.*;

/**
 * Created by cooper on 10/3/15.
 */
@Name("houseRecordHome")
public class HouseRecordHome extends OwnerEntityHome<HouseRecord> {


    public static class RegBookItem implements Comparable<RegBookItem>{

        private BusinessDefine.RegBookItemType regBookItemType;

        private HouseBusiness mainBusiness;

        private HouseBusiness confirmBusiness;

        private HouseBusiness cancelBusiness;

        private int pri;

        public RegBookItem(BusinessDefine.RegBookItemType regBookItemType, HouseBusiness mainBusiness, int pri) {
            putBusiness(regBookItemType,mainBusiness);
            this.pri = pri;
        }

        public void putBusiness(BusinessDefine.RegBookItemType regBookItemType, HouseBusiness houseBusiness){
            this.regBookItemType = regBookItemType;
            switch (regBookItemType.getLocation()){
                case MASTER:
                    this.mainBusiness = houseBusiness;
                    break;
                case MIDDLE:
                    this.confirmBusiness = houseBusiness;
                    break;
                case LAST:
                    this.cancelBusiness = houseBusiness;
                    break;
                default:
                    new IllegalArgumentException("local not defing");
            }
        }

        public int getPri() {
            return pri;
        }

        public BusinessDefine.RegBookItemType getRegBookItemType() {
            return regBookItemType;
        }

        public HouseBusiness getMainBusiness() {
            return mainBusiness;
        }

        public HouseBusiness getConfirmBusiness() {
            return confirmBusiness;
        }

        public HouseBusiness getCancelBusiness() {
            return cancelBusiness;
        }

        public HouseBusiness getTopBusiness(){
            if (mainBusiness != null){
                return mainBusiness;
            }
            if (confirmBusiness != null){
                return confirmBusiness;
            }
            return cancelBusiness;
        }

        @Override
        public int compareTo(RegBookItem o) {
            return Integer.valueOf(pri).compareTo(o.getPri());
        }
    }

    public static class RegBookPage{

        private BusinessDefine.RegBookPage pageType;

        private int pageNumber;

        private RegBookItem firstItem;

        private RegBookItem secondItem;

        public RegBookPage(BusinessDefine.RegBookPage pageType, int pageNumber, RegBookItem firstItem) {
            this.pageType = pageType;
            this.pageNumber = pageNumber;
            this.firstItem = firstItem;
        }

        public BusinessDefine.RegBookPage getPageType() {
            return pageType;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public RegBookItem getFirstItem() {
            return firstItem;
        }

        public RegBookItem getSecondItem() {
            return secondItem;
        }

        public void setSecondItem(RegBookItem secondItem) {
            this.secondItem = secondItem;
        }
    }

    public static class RegBookInfoPage{

        private int pageNumber;

        private BusinessHouse businessHouse;

        private String houseImgPath;

        public RegBookInfoPage(int pageNumber, BusinessHouse businessHouse) {
            this.pageNumber = pageNumber;
            this.businessHouse = businessHouse;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public BusinessHouse getBusinessHouse() {
            return businessHouse;
        }
    }

    private Map<BusinessDefine.RegBookPage,List<RegBookPage>> regBookPageMap;

    private List<RegBookInfoPage> regBookInfoPages;

    public List<RegBookInfoPage> getRegBookInfoPart(){
        if (regBookInfoPages == null){
            initRegBook();
        }
        return regBookInfoPages;
    }

    public Map<BusinessDefine.RegBookPage,List<RegBookPage>> getRegBookPageMap(){
        if (regBookPageMap == null){
            initRegBook();
        }
        return regBookPageMap;
    }

    public List<Map.Entry<BusinessDefine.RegBookPage,List<RegBookPage>>> getRegBookPages(){
        List<Map.Entry<BusinessDefine.RegBookPage,List<RegBookPage>>> result = new ArrayList<Map.Entry<BusinessDefine.RegBookPage,List<RegBookPage>>>(getRegBookPageMap().entrySet());
        Collections.sort(result, new Comparator<Map.Entry<BusinessDefine.RegBookPage, List<RegBookPage>>>() {
            @Override
            public int compare(Map.Entry<BusinessDefine.RegBookPage, List<RegBookPage>> o1, Map.Entry<BusinessDefine.RegBookPage, List<RegBookPage>> o2) {
                return Integer.valueOf(o1.getKey().getPri()).compareTo(o2.getKey().getPri());
            }
        });
        return result;
    }


    protected void initRegBook(){


        List<HouseBusiness> houseBusinessList = getEntityManager().createQuery("select houseBusiness from HouseBusiness houseBusiness " +
                "where houseBusiness.ownerBusiness.recorded = true and houseBusiness.houseCode =:houseCode order by houseBusiness.ownerBusiness.regTime", HouseBusiness.class)
                .setParameter("houseCode", getInstance().getHouseCode()).getResultList();
        regBookInfoPages = new ArrayList<RegBookInfoPage>();

        List<RegBookItem> regBookItemList = new ArrayList<RegBookItem>();
        Map<String, RegBookItem> masterBookItemMap = new HashMap<String, RegBookItem>();

        int pri = 1;
        for(HouseBusiness houseBusiness: houseBusinessList){

            Set<BusinessDefine.RegBookItemType> businessBookBizTypes = BusinessDefineCache.instance().getDefine(houseBusiness.getOwnerBusiness().getDefineId()).getRegisterBookParts();

            if (regBookInfoPages.isEmpty() || businessBookBizTypes.contains(BusinessDefine.RegBookItemType.OWNER_HOUSE_CHANGE)){
                regBookInfoPages.add(new RegBookInfoPage(regBookInfoPages.size() + 1,houseBusiness.getAfterBusinessHouse()));
            }

            for(BusinessDefine.RegBookItemType regBookItemType : businessBookBizTypes){
                RegBookItem item = null;
                if (BusinessDefine.RegBookItemTypeLocation.MASTER.equals(regBookItemType.getLocation())){
                    item = new RegBookItem(regBookItemType,houseBusiness,pri++);
                    regBookItemList.add(item);
                }else{
                    if (houseBusiness.getOwnerBusiness().getSelectBusiness() != null){
                        item = masterBookItemMap.get(houseBusiness.getOwnerBusiness().getSelectBusiness().getId());
                    }

                    if (item == null){
                        Logging.getLog(getClass()).warn("select Business not fount businessId:" + houseBusiness.getOwnerBusiness().getId());
                        item = new RegBookItem(regBookItemType,houseBusiness,pri++);
                        regBookItemList.add(item);
                    }else{
                        item.putBusiness(regBookItemType,houseBusiness);
                    }
                }
                if (!BusinessDefine.RegBookItemTypeLocation.LAST.equals(item.getRegBookItemType().getLocation()))
                    masterBookItemMap.put(houseBusiness.getOwnerBusiness().getId(),item);

            }
        }


        regBookPageMap = new HashMap<BusinessDefine.RegBookPage, List<RegBookPage>>();

        //List<RegBookItem> items = new ArrayList<RegBookItem>(masterBookItems.values());
        //Collections.sort(items);

        for(RegBookItem item : regBookItemList){
            List<RegBookPage> pages = regBookPageMap.get(item.getRegBookItemType().getPage());
            if (pages == null){
                pages = new ArrayList<RegBookPage>();
                regBookPageMap.put(item.getRegBookItemType().getPage(), pages);
            }
            if (pages.isEmpty() || (pages.get(pages.size() - 1).getSecondItem() != null)){
                RegBookPage page = new RegBookPage(item.getRegBookItemType().getPage(), pages.size() + 1,item);
                pages.add(page);
            }else{
                pages.get(pages.size() - 1).setSecondItem(item);
            }
        }
    }


    @Override
    protected HouseRecord loadInstance(){
        try {
            return getEntityManager().createQuery("select hr from HouseRecord hr " +
                    "left join fetch hr.businessHouse house " +
                    "left join fetch house.businessHouseOwner owner " +
                    "left join fetch owner.makeCard ownerCard " +
                    "left join fetch house.houseBusinessForAfter houseBusiness " +
                    "left join fetch houseBusiness.ownerBusiness ownerBusiness " +
                    "left join fetch houseBusiness.recordStore rs " +
                    "left join fetch house.businessPools pool " +
                    "left join fetch pool.makeCard poolCard where hr.houseCode = :houseCode", HouseRecord.class)
                    .setParameter("houseCode", getId()).getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    protected void initInstance(){
        super.initInstance();
        regBookPageMap = null;
        regBookInfoPages = null;
        ownerBusinessList = null;
    }

    public List<MortgaegeRegiste> getMortgaegeList(){
        return OwnerHouseHelper.instance().getMortgaeges(getInstance().getHouseCode());
    }

    private List<OwnerBusiness> ownerBusinessList;

    public List<OwnerBusiness> getOwnerBusinessList(){
        if (ownerBusinessList == null) {
            ownerBusinessList = getEntityManager().createQuery("select houseBusiness.ownerBusiness from HouseBusiness houseBusiness where houseBusiness.houseCode =:houseCode", OwnerBusiness.class)
                    .setParameter("houseCode", getInstance().getHouseCode()).getResultList();
        }
        return ownerBusinessList;
    }


}
