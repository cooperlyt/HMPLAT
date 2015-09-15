package com.dgsoft.house.owner.action;

import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.ContractOwner;
import javassist.bytecode.stackmap.BasicBlock;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

/**
 * Created by wxy on 2015-09-15.
 * 查封登记 房屋所有权证，合同备案号，预售许可证号
 */
@Name("houseSellInfoSelect")
public class HouseSellInfoSelect {
   @In
   private OwnerBusinessHome ownerBusinessHome;

   @In(create =true)
   private OwnerEntityLoader ownerEntityLoader;


   @In
   private FacesMessages facesMessages;


   public String getContractOwnerCode() {
       String ContractCode;
       try {
           ContractCode = ownerEntityLoader.getEntityManager().createQuery("select contractOwner.contractCode from ContractOwner contractOwner where contractOwner.houseCode = :houseCode  and  contractOwner.ownerBusiness.status='COMPLETE'", String.class).setParameter("houseCode", ownerBusinessHome.getSingleHoues().getHouseCode()).getSingleResult();
           return ContractCode;
       } catch (NonUniqueResultException e) {
           facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"MoreHouseContractCode");
           return "";

       } catch (NoResultException e) {
           return "";
       }
   }

   public String getProjectRship(){
       String  ProjectRship = null;





       return ProjectRship;

   }
}
