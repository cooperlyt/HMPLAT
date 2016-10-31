package com.dgsoft.house.owner.action;

import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.action.AttachCorporationHome;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.ContractNumber;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cooper on 22/10/2016.
 */
@Name("contractNumberPublish")
public class ContractNumberPublish {

    @In
    private AttachCorporationHome attachCorporationHome;

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private FacesMessages facesMessages;

    private List<String> cantUseNumber = new ArrayList<String>();

    public void preparePublish(){
        cantUseNumber = getCantPublishNumber();
    }

    private List<String> getCantPublishNumber(){
        return ownerEntityLoader.getEntityManager().createQuery("select cn.contractNumber from ContractNumber cn where cn.contractNumber in (:numbers)", String.class)
                .setParameter("numbers",getRequestNumbers()).getResultList();
    }

    public void prepareTrash(){
        cantUseNumber = getCanTrashNumber();
    }

    private List<String> getCanTrashNumber(){
        return ownerEntityLoader.getEntityManager().createQuery("select cn.contractNumber from ContractNumber cn where cn.status = 'FREE' and cn.contractNumber in (:numbers)",String.class)
                .setParameter("numbers",getRequestNumbers()).getResultList();
    }

    public int getRequestCount(){
        if (batchNumber == null || "".equals(batchNumber.trim())){
            return 0;
        }
        int result = endNum - beginNum + 1;
        if (result >= 0){
            return result;
        }
        return 0;
    }

    public List<String> getCantUseNumber() {
        return cantUseNumber;
    }

    public List<String> getRequestNumbers(){
        List<String> result = new ArrayList<String>();
        for(int i= beginNum; i<= endNum; i++) {

            int length = RunParam.instance().getIntParamValue("contract_number_length");


            StringBuffer number = new StringBuffer(String.valueOf(i));
            while (number.length() < length) {
                number.insert(0, '0');
            }
            result.add(batchNumber + number.toString());
        }
        return result;
    }

    @Transactional
    public void publishNum(){
        int i = 0;
        try {
            i = ownerEntityLoader.getEntityManager().createQuery("select max(cn.number) from ContractNumber cn", Long.class).getSingleResult().intValue();
        }catch (NoResultException e){

        }


        List<String> requestNumber = getRequestNumbers();
        requestNumber.removeAll(getCantPublishNumber());
        for(String num: requestNumber){
            ContractNumber contractNumber = new ContractNumber(attachCorporationHome.getInstance().getType(), i++, num,
                    ContractNumber.ContractNumberStatus.FREE, new Date(),attachCorporationHome.getInstance().getId(),attachCorporationHome.getInstance().getName());
            contractNumber.setApplyTime(new Date());
            ownerEntityLoader.getEntityManager().persist(contractNumber);
        }

        ownerEntityLoader.getEntityManager().flush();

        facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO,"contractNumberPublished");
        clear();
    }

    @Transactional
    public void trashNum(){
        facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO,"contractNumberTrash",
            ownerEntityLoader.getEntityManager().createQuery("delete ContractNumber where id in (:numbers)").setParameter("numbers",getRequestNumbers()).executeUpdate());
        clear();
    }

    public void clear(){
        batchNumber = null;
        beginNum = 0;
        endNum = 0;

    }

    private String batchNumber;
    private int beginNum;
    private int endNum;

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        if (batchNumber != null){
            this.batchNumber = batchNumber.trim();
        }else{
            this.batchNumber = null;
        }

    }

    public int getBeginNum() {
        return beginNum;
    }

    public void setBeginNum(int beginNum) {
        this.beginNum = beginNum;
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }
}
