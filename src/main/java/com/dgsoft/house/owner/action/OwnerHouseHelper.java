package com.dgsoft.house.owner.action;

import com.dgsoft.house.DescriptionDisplay;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.SalePayType;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.business.subscribe.complete.KeyGeneratorHelper;
import com.dgsoft.house.owner.model.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by cooper on 8/11/15.
 */

@Name("ownerHouseHelper")
@Scope(ScopeType.STATELESS)
public class OwnerHouseHelper {


    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;


    public List<HouseStatus> getHouseAllStatus(String houseCode) {
        List<HouseStatus> statuses = new ArrayList<HouseStatus>();

        //包含 COMPLETE_CANCEL 不再讨论

        List<AddHouseStatus> addHouseStatusList = ownerEntityLoader.getEntityManager().createQuery("select addHouseStatus from AddHouseStatus  addHouseStatus where addHouseStatus.houseBusiness.houseCode = :houseCode and (addHouseStatus.houseBusiness.ownerBusiness.status = 'COMPLETE' or addHouseStatus.houseBusiness.ownerBusiness.status = 'COMPLETE_CANCEL' or addHouseStatus.houseBusiness.ownerBusiness.status = 'MODIFYING' or ((addHouseStatus.houseBusiness.ownerBusiness.status = 'RUNNING' or addHouseStatus.houseBusiness.ownerBusiness.status = 'SUSPEND') and addHouseStatus.houseBusiness.ownerBusiness.recorded = true)) ", AddHouseStatus.class).setParameter("houseCode",houseCode).getResultList();

        Collections.sort(addHouseStatusList, new Comparator<AddHouseStatus>() {
            @Override
            public int compare(AddHouseStatus o1, AddHouseStatus o2) {
                return Boolean.valueOf(o1.isRemove()).compareTo(o2.isRemove());
            }
        });

         for(AddHouseStatus status: addHouseStatusList){
            if(status.isRemove()){

                if (!statuses.remove(status.getStatus())){
                    Logging.getLog(getClass()).warn("calc all house status remove fail.");
                }
            }else {
                statuses.add(status.getStatus());
            }
         }

        List<HouseStatus> result = new ArrayList<HouseStatus>();
        for(HouseStatus status: statuses){
            if (!result.contains(status) || status.isAllowRepeat()){
                result.add(status);
            }
        }

        Collections.sort(result, new HouseStatus.StatusComparator());
        return result;
    }

    public HouseStatus getMasterStatus(String houseCode){
        List<HouseStatus> statuses = getHouseAllStatus(houseCode);
        if (statuses.isEmpty()){
           return null;
        }else
            return statuses.get(0);
    }


    public List<MortgaegeRegiste> getMortgaeges(String houseCode){
       List<HouseBusiness> ownerBusinesses = ownerEntityLoader.getEntityManager().createQuery("select hb from HouseBusiness hb left join fetch hb.ownerBusiness ob  where hb.houseCode =:houseCode and hb.canceled = false and (ob.status = 'COMPLETE'  or ob.status = 'MODIFYING' or ((ob.status = 'RUNNING' or ob.status = 'SUSPEND') and ob.recorded = true))", HouseBusiness.class)
               .setParameter("houseCode",houseCode)
               .getResultList();

        List<MortgaegeRegiste> result = new ArrayList<MortgaegeRegiste>();
        for (HouseBusiness houseBusiness: ownerBusinesses){
            if (houseBusiness.getOwnerBusiness().getMortgaegeRegiste() != null &&
                    houseBusiness.getOwnerBusiness().getMortgaegeRegiste().getFinancial() != null){
                result.add(houseBusiness.getOwnerBusiness().getMortgaegeRegiste());
            }
        }
        return result;
    }


    public static OwnerHouseHelper instance() {
        return (OwnerHouseHelper) Component.getInstance(OwnerHouseHelper.class, true);
    }

    public static String genHouseDisplay(BusinessHouse house) {
        DescriptionDisplay result = new DescriptionDisplay();
        result.newLine(DescriptionDisplay.DisplayStyle.NORMAL);


        String contractPersonNames = "";
        for (PowerPerson pp : house.getAllNewPowerPersonList()) {

            if (!"".equals(contractPersonNames)) {
                contractPersonNames += ",";
            }
            contractPersonNames += pp.getPersonName();

        }

        result.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, contractPersonNames);

        result.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
        result.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, house.getAddress());

        result.addData(DescriptionDisplay.DisplayStyle.IMPORTANT, house.getMapNumber());
        result.addData(DescriptionDisplay.DisplayStyle.DECORATE, "图");
        result.addData(DescriptionDisplay.DisplayStyle.IMPORTANT, house.getBlockNo());
        result.addData(DescriptionDisplay.DisplayStyle.DECORATE, "丘");
        result.addData(DescriptionDisplay.DisplayStyle.IMPORTANT, house.getBuildNo());
        result.addData(DescriptionDisplay.DisplayStyle.DECORATE, "幢");
        result.addData(DescriptionDisplay.DisplayStyle.IMPORTANT, house.getHouseOrder());
        result.addData(DescriptionDisplay.DisplayStyle.DECORATE, "房");

        result.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
        result.addData(DescriptionDisplay.DisplayStyle.LABEL, "规划用途");
        result.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, house.getDesignUseType());

        DecimalFormat df = new DecimalFormat("#0.000");
        df.setGroupingUsed(false);
        df.setRoundingMode(RoundingMode.HALF_UP);

        result.addData(DescriptionDisplay.DisplayStyle.LABEL, "建筑面积");
        result.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, df.format(house.getHouseArea()));

        if (house.getUseArea() != null){
            result.addData(DescriptionDisplay.DisplayStyle.LABEL, "套内面积");
            result.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, df.format(house.getUseArea()));
        }





        return DescriptionDisplay.toStringValue(result);
    }

    public static String FcgenHouseDisplay(BusinessHouse house,OwnerBusinessHome ownerBusinessHome) {
        DescriptionDisplay result = new DescriptionDisplay();
        result.newLine(DescriptionDisplay.DisplayStyle.NORMAL);


        String contractPersonNames = "";
        for (PowerPerson pp : house.getAllNewPowerPersonList()) {

            if (!"".equals(contractPersonNames)) {
                contractPersonNames += ",";
            }
            contractPersonNames += pp.getPersonName()+"["+pp.getCredentialsNumber()+"]";

        }

        if(ownerBusinessHome.getInstance().getDefineId().equals("WP73") || ownerBusinessHome.getInstance().getDefineId().equals("WP74")){
            if (ownerBusinessHome.getApplyPersion()!=null && ownerBusinessHome.getApplyPersion().getPersonName()!=null && !ownerBusinessHome.getApplyPersion().getPersonName().equals("")){
                contractPersonNames = ownerBusinessHome.getApplyPersion().getPersonName()+"["+ownerBusinessHome.getApplyPersion().getCredentialsNumber()+"]";
            }

        }

        result.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, contractPersonNames);
        result.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
        result.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, house.getAddress());

        result.addData(DescriptionDisplay.DisplayStyle.IMPORTANT, house.getMapNumber());
        result.addData(DescriptionDisplay.DisplayStyle.DECORATE, "图");
        result.addData(DescriptionDisplay.DisplayStyle.IMPORTANT, house.getBlockNo());
        result.addData(DescriptionDisplay.DisplayStyle.DECORATE, "丘");
        result.addData(DescriptionDisplay.DisplayStyle.IMPORTANT, house.getBuildNo());
        result.addData(DescriptionDisplay.DisplayStyle.DECORATE, "幢");
        result.addData(DescriptionDisplay.DisplayStyle.IMPORTANT, house.getHouseOrder());
        result.addData(DescriptionDisplay.DisplayStyle.DECORATE, "房");

        result.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
        result.addData(DescriptionDisplay.DisplayStyle.LABEL, "规划用途");
        result.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, house.getDesignUseType());

        DecimalFormat df = new DecimalFormat("#0.000");
        df.setGroupingUsed(false);
        df.setRoundingMode(RoundingMode.HALF_UP);

        result.addData(DescriptionDisplay.DisplayStyle.LABEL, "建筑面积");
        result.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, df.format(house.getHouseArea()));

        if (house.getUseArea() != null){
            result.addData(DescriptionDisplay.DisplayStyle.LABEL, "套内面积");
            result.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH, df.format(house.getUseArea()));
        }





        return DescriptionDisplay.toStringValue(result);
    }

    public static KeyGeneratorHelper genHouseSearchKey(BusinessHouse house){

        KeyGeneratorHelper key = new KeyGeneratorHelper();
        for (PowerPerson pp : house.getPowerPersons()){
            if (!pp.isOld()){
                key.addWord(pp.getPersonName());
                key.addWord(pp.getCredentialsNumber());;
            }
        }
        key.addWord(house.getDisplayHouseCode());
        key.addWord(house.getAddress());

        return key;
    }

    public static KeyGeneratorHelper FcgenHouseSearchKeyRecord(BusinessHouse house){

        KeyGeneratorHelper key = new KeyGeneratorHelper();
        for (PowerPerson pp : house.getPowerPersons()){
            if (!pp.isOld()){
                key.addWord(pp.getPersonName());
                key.addWord(pp.getCredentialsNumber());
            }
        }
        key.addWord(house.getHouseCode());
        key.addWord(house.getAddress());

        return key;
    }


    public static KeyGeneratorHelper fcgenHouseSearchKey(BusinessHouse house){

        KeyGeneratorHelper key = new KeyGeneratorHelper();
        for (PowerPerson pp : house.getPowerPersons()){

                key.addWord(pp.getPersonName());
                key.addWord(pp.getCredentialsNumber());;

        }
        key.addWord(house.getHouseCode());
        key.addWord(house.getAddress());

        return key;
    }

    public static KeyGeneratorHelper genProjectSearchKey(BusinessHouse house){

        KeyGeneratorHelper key = new KeyGeneratorHelper();
        key.addWord(house.getDeveloperName());
        key.addWord(house.getDisplayHouseCode());
        key.addWord(house.getAddress());

        return key;
    }

    public static KeyGeneratorHelper fcgenProjectSearchKey(BusinessHouse house){

        KeyGeneratorHelper key = new KeyGeneratorHelper();
        key.addWord(house.getDeveloperName());
        key.addWord(house.getHouseCode());
        key.addWord(house.getAddress());

        return key;
    }


    public SalePayType[] getSalePayType(){
        return SalePayType.values();
    }


}
