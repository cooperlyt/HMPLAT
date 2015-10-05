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

        private BusinessDefine.RegBookBizType regBookBizType;

        private HouseBusiness mainBusiness;

        private HouseBusiness confirmBusiness;

        private HouseBusiness cancelBusiness;

        private int pri;

        public RegBookItem(BusinessDefine.RegBookBizType regBookBizType, HouseBusiness mainBusiness, int pri) {
            putBusiness(regBookBizType,mainBusiness);
            this.pri = pri;
        }

        public void putBusiness(BusinessDefine.RegBookBizType regBookBizType, HouseBusiness houseBusiness){
            this.regBookBizType = regBookBizType;
            if (regBookBizType.isMaster()){
                this.mainBusiness = houseBusiness;
            }else{
                switch (regBookBizType){
                    case COURT_CLOSE_CANCEL_PART:
                        this.cancelBusiness = houseBusiness;
                        break;
                    case HIGHEST_MORTGAGE_CONFIRM:
                        this.confirmBusiness = houseBusiness;
                        break;
                    case MORTGAGE_CANCEL:
                        this.cancelBusiness = houseBusiness;
                        break;
                    default:
                        throw new IllegalArgumentException("type not define:" + regBookBizType);
                }
            }
        }

        public int getPri() {
            return pri;
        }

        public BusinessDefine.RegBookBizType getRegBookBizType() {
            return regBookBizType;
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

        @Override
        public int compareTo(RegBookItem o) {
            return Integer.valueOf(pri).compareTo(o.getPri());
        }
    }

    public static class RegBookPage{

        private int pageNumber;

        private RegBookItem firstItem;

        private RegBookItem secondItem;

        public RegBookPage(int pageNumber, RegBookItem firstItem) {
            this.pageNumber = pageNumber;
            this.firstItem = firstItem;
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

    private Map<BusinessDefine.RegBookPagePart,List<RegBookPage>> regBookPages;

    private List<RegBookInfoPage> regBookInfoPages;

    //private Map<BusinessDefine.RegBookPart, Integer> pages;


    public List<RegBookPage> getRegBookPages(BusinessDefine.RegBookPagePart type){
        if (regBookPages == null){
            initRegBook();
        }
        return regBookPages.get(type);
    }

    public List<RegBookPage> getRegOwnerPages(){
        return getRegBookPages(BusinessDefine.RegBookPagePart.OWNER_CHANGE);
    }

    public List<RegBookPage> getRegProjectMortgagePages(){
        return getRegBookPages(BusinessDefine.RegBookPagePart.PROJECT_MORTGAGE_PART);
    }

    public List<RegBookPage> getOwnerHouseMortgagePages(){
        return getRegBookPages(BusinessDefine.RegBookPagePart.OWNER_HOUSE_MORTGAGE);
    }

    public List<RegBookPage> getCourtClosePages(){
        return getRegBookPages(BusinessDefine.RegBookPagePart.COURT_CLOSE_PART);
    }

    public List<RegBookPage> getDissidencePages(){
        return getRegBookPages(BusinessDefine.RegBookPagePart.DISSIDENCE);
    }

    public List<RegBookPage> getPrepareOwnerPages(){
        return getRegBookPages(BusinessDefine.RegBookPagePart.PREPARE_OWNER);
    }

    public List<RegBookInfoPage> getRegBookInfoPages(){
        if (regBookInfoPages == null){
            initRegBook();
        }
        return regBookInfoPages;
    }

    public void initRegBook(){
        regBookInfoPages = new ArrayList<RegBookInfoPage>();

        List<HouseBusiness> houseBusinessList = getEntityManager().createQuery("select houseBusiness from HouseBusiness houseBusiness " +
                "where houseBusiness.ownerBusiness.recorded = true and houseBusiness.houseCode =:houseCode order by houseBusiness.ownerBusiness.regTime", HouseBusiness.class)
                .setParameter("houseCode", getInstance().getHouseCode()).getResultList();

        Map<String, RegBookItem> masterBookItems = new HashMap<String, RegBookItem>();

        int pri = 1;
        for(HouseBusiness houseBusiness: houseBusinessList){

            Set<BusinessDefine.RegBookBizType> businessBookBizTypes = BusinessDefineCache.instance().getDefine(houseBusiness.getOwnerBusiness().getDefineId()).getRegisterBookParts();
            if (regBookInfoPages.isEmpty() || businessBookBizTypes.contains(BusinessDefine.RegBookBizType.OWNER_HOUSE_CHANGE)){
                regBookInfoPages.add(new RegBookInfoPage(regBookInfoPages.size() + 1,houseBusiness.getAfterBusinessHouse()));
            }

            for(BusinessDefine.RegBookBizType regBookBizType: businessBookBizTypes){
                if (regBookBizType.isMaster()){
                    masterBookItems.put(houseBusiness.getOwnerBusiness().getId(),new RegBookItem(regBookBizType,houseBusiness,pri++));
                }else{
                    RegBookItem item = null;
                    if (houseBusiness.getOwnerBusiness().getSelectBusiness() != null){
                        item = masterBookItems.get(houseBusiness.getOwnerBusiness().getSelectBusiness().getId());
                    }


                    if (item == null){
                        Logging.getLog(getClass()).warn("select Business not fount businessId:" + houseBusiness.getOwnerBusiness().getId());
                        masterBookItems.put(houseBusiness.getOwnerBusiness().getId(),new RegBookItem(regBookBizType,houseBusiness,pri++));
                    }else{
                        item.putBusiness(regBookBizType,houseBusiness);
                    }
                }

            }
        }


        regBookPages = new HashMap<BusinessDefine.RegBookPagePart, List<RegBookPage>>();

        List<RegBookItem> items = new ArrayList<RegBookItem>(masterBookItems.values());
        Collections.sort(items);

        for(RegBookItem item : items){
            List<RegBookPage> pages = regBookPages.get(item.getRegBookBizType().getPart());
            if (pages == null){
                pages = new ArrayList<RegBookPage>();
                regBookPages.put(item.getRegBookBizType().getPart(),pages);
            }
            if (pages.isEmpty() || (pages.get(pages.size() - 1).getSecondItem() != null)){
                RegBookPage page = new RegBookPage(pages.size() + 1,item);
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
        regBookPages = null;
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
