package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.*;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessCorp;
import com.dgsoft.common.PinyinTools;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import javax.persistence.TypedQuery;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wxy on 19/5/2022.
 * 档案库Corporation保存到空间库
 */

@Name("CorporationSaveHouseInfoComplete")
public class CorporationSaveHouseInfoComplete implements TaskCompleteSubscribeComponent {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @Override
    public void valid() {

    }

    @Override
    public boolean isPass() {
        return true;
    }

    /**
     * 按营业执照号查询空间库没有记录添加，
     */

    @Override
    public void complete() {

          if(ownerBusinessHome.getInstance().getBusinessCorp()!=null){
           BusinessCorp businessCorp = ownerBusinessHome.getInstance().getBusinessCorp();
           AttachCorporation attachCorporation = new AttachCorporation();

//           TypedQuery<AttachCorporation> query = houseEntityLoader.getEntityManager().createQuery("SELECT ac FROM AttachCorporation u WHERE ac.licenseNumber = :e", AttachCorporation.class);
//           query.setParameter("e",licenseNumber);
//           List<AttachCorporation> list = query.getResultList();
           java.util.Date nowDate=new Date();
           Calendar cal = Calendar.getInstance();
           cal.setTime(nowDate);
           cal.add(Calendar.YEAR, 1);

//            if (list.isEmpty()){

                attachCorporation.setId(businessCorp.getId());
                attachCorporation.setRecordDate(nowDate);
                attachCorporation.setAddress(businessCorp.getAddress());
                attachCorporation.setPhone(businessCorp.getPhone());
                attachCorporation.setOwnerTel(businessCorp.getOwnerTel());
                attachCorporation.setOwnerName(businessCorp.getOwnerName());
                attachCorporation.setOwnerCard(businessCorp.getOwnerCard());
                attachCorporation.setFax(businessCorp.getFax());
                attachCorporation.setEmail(businessCorp.getEmail());
                attachCorporation.setPostCode(businessCorp.getPostCode());
                attachCorporation.setEnable(true);
                attachCorporation.setLicenseNumber(businessCorp.getLicenseNumber());
                attachCorporation.setCerCode(businessCorp.getCerCode());
                attachCorporation.setCompanyCode(businessCorp.getCompanyCode());
                attachCorporation.setCompanyType(businessCorp.getCompanyType());
                attachCorporation.setRegisterMoney(businessCorp.getRegisterMoney());
                attachCorporation.setLevel(businessCorp.getLevel());
                attachCorporation.setDateTo(cal.getTime());
                attachCorporation.setLegalType(businessCorp.getLegalType());
                attachCorporation.setCredentialsType(businessCorp.getCredentialsType());
                attachCorporation.setType(businessCorp.getType());
              //  attachCorporation.setMemo("机构备案");


                switch (businessCorp.getType()){
                   case DEVELOPER:
                        Developer developer = new Developer();
                        developer.setId(businessCorp.getId());
                        developer.setName(businessCorp.getName());
                        developer.setCreateTime(nowDate);
                        developer.setDestroyed(false);
                        developer.setPyCode(PinyinTools.getPinyinCode(businessCorp.getName()));
                        developer.setVersion(10);
                        developer.setAttachCorporation(attachCorporation);
                        attachCorporation.setDeveloper(developer);
                        houseEntityLoader.getEntityManager().persist(developer);
                        houseEntityLoader.getEntityManager().flush();
                        break;
                   case MCOMPANY:
                       Mcompany mcompany = new Mcompany();
                       mcompany.setId(businessCorp.getId());
                       mcompany.setName(businessCorp.getName());
                       mcompany.setPyCode(PinyinTools.getPinyinCode(businessCorp.getName()));
                       mcompany.setDestroyed(false);
                       mcompany.setVersion(10);
                       mcompany.setAttachCorporation(attachCorporation);
                       attachCorporation.setPropertyCorporation(mcompany);

                       houseEntityLoader.getEntityManager().persist(mcompany);
                       houseEntityLoader.getEntityManager().flush();
                       break;

                   case EVALUATE:
                       EvaluateCorporation evaluateCorporation = new EvaluateCorporation();
                       //evaluateCorporation.setId(businessCorp.getId());
                       evaluateCorporation.setName(businessCorp.getName());
                       evaluateCorporation.setPyCode(PinyinTools.getPinyinCode(businessCorp.getName()));
                       evaluateCorporation.setDestroyed(false);
                       evaluateCorporation.setVersion(10);
                       evaluateCorporation.setAttachCorporation(attachCorporation);
                       attachCorporation.setEvaluateCorporation(evaluateCorporation);
                       houseEntityLoader.getEntityManager().persist(attachCorporation);
                       houseEntityLoader.getEntityManager().flush();
                       break;

                   case MAPPING:
                       MappingCorporation mappingCorp = new MappingCorporation();
                       //mappingCorp.setId(businessCorp.getId());
                       mappingCorp.setName(businessCorp.getName());
                       mappingCorp.setPyCode(PinyinTools.getPinyinCode(businessCorp.getName()));
                       mappingCorp.setDestroyed(false);
                       mappingCorp.setCreateTime(nowDate);
                       mappingCorp.setVersion(10);
                       mappingCorp.setAttachCorporation(attachCorporation);
                       attachCorporation.setMappingCorporation(mappingCorp);
                       houseEntityLoader.getEntityManager().persist(attachCorporation);
                       houseEntityLoader.getEntityManager().flush();

                       break;

                   case AGENCIES:
                       Agencies agencies = new Agencies();
                       agencies.setId(businessCorp.getId());
                       agencies.setName(businessCorp.getName());
                       agencies.setDestroyed(false);
                       agencies.setVersion(10);
                       agencies.setTel(businessCorp.getPhone());
                       agencies.setCreateTime(nowDate);
                       agencies.setControl(true);
                       agencies.setAttachCorporation(attachCorporation);
                       attachCorporation.setAgencies(agencies);
                       houseEntityLoader.getEntityManager().persist(attachCorporation);
                       houseEntityLoader.getEntityManager().flush();
                       break;

           }

        }
    }
}
