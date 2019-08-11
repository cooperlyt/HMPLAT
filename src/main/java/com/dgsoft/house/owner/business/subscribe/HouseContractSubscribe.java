package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.NumberBuilder;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessBuild;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseContract;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wxy on 2018-03-20.
 * 交易，商品房 合同信息
 */
@Name("houseContractSubscribe")
public class HouseContractSubscribe extends OwnerEntityHome<HouseContract> {
    @In
    private OwnerBusinessHome ownerBusinessHome;




    @In(create = true)
    private NumberBuilder numberBuilder;

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;


    @Override
    public void create(){
        super.create();
        for (HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
            if (houseBusiness.getHouseContract()!=null){
                if (houseBusiness.getHouseContract().getId()!=null){
                   setId(houseBusiness.getHouseContract().getId());
                }else{
                   setInstance(houseBusiness.getHouseContract());
                }
            }else{
               // Logging.getLog(getClass()).debug("BuildCode-"+houseBusiness.getAfterBusinessHouse().getBuildCode());
                List<String> projectSaleCerNumber = ownerEntityLoader.getEntityManager().createQuery("select (bizBuid.businessProject.projectSellInfo.projectCard.makeCard.number) from BusinessBuild bizBuid where bizBuid.businessProject.ownerBusiness.status in('COMPLETE') and bizBuid.businessProject.ownerBusiness.type<>'CANCEL_BIZ' and bizBuid.buildCode=:buildCode", String.class)
                        .setParameter("buildCode",houseBusiness.getAfterBusinessHouse().getBuildCode()).getResultList();

                if (!projectSaleCerNumber.isEmpty() && projectSaleCerNumber.size()>0){
                   // Logging.getLog(getClass()).debug("projectSaleCerNumber-"+houseBusiness.getAfterBusinessHouse().getBuildCode());
                    getInstance().setProjectSaleCerNumber(projectSaleCerNumber.get(0));
                }else{
                    getInstance().setProjectSaleCerNumber(null);
                }

                SimpleDateFormat numberDateformat = new SimpleDateFormat("yyyyMMdd");
                String datePart = numberDateformat.format(new Date());
                String site = RunParam.instance().getStringParamValue("SiteShort");
                String no=site+datePart + Long.toString(numberBuilder.getNumber("HTBH"));
                getInstance().setContractNumber(no);
                getInstance().setHouseBusiness(houseBusiness);


                houseBusiness.setHouseContract(getInstance());
            }
        }
    }
}
