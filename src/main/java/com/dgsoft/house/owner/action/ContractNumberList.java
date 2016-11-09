package com.dgsoft.house.owner.action;

import com.dgsoft.common.BatchOperData;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.owner.OwnerEntityQuery;
import com.dgsoft.house.owner.model.ContractNumber;
import org.jboss.seam.annotations.Name;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cooper on 31/10/2016.
 */
@Name("contractNumberList")
public class ContractNumberList extends OwnerEntityQuery<ContractNumber> {

    private static final String EJBQL = "select cn from ContractNumber cn";

    private static final String[] RESTRICTIONS = {
            "cn.id in (#{contractNumberList.searchNumberList})",
            "true = #{contractNumberList.haveCondition}"
    };

    public ContractNumberList() {
        setEjbql(EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setRestrictionLogicOperator("and");
        setMaxResults(null);
        setOrderColumn("cn.number");
        setOrderDirection("desc");
    }

    private String batchNumber;
    private Integer beginNum;
    private Integer endNum;

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Integer getBeginNum() {
        return beginNum;
    }

    public void setBeginNum(Integer beginNum) {
        this.beginNum = beginNum;
    }

    public Integer getEndNum() {
        return endNum;
    }

    public void setEndNum(Integer endNum) {
        this.endNum = endNum;
    }

    public boolean isHaveCondition(){
        return batchNumber != null && !batchNumber.trim().equals("") && beginNum != null && endNum != null;
    }

    public List<String> getSearchNumberList() {
        List<String> result = new ArrayList<String>();
        if (batchNumber != null && !batchNumber.trim().equals("") && beginNum != null && endNum != null)
            for (int i = beginNum; i <= endNum; i++) {

                int length = RunParam.instance().getIntParamValue("contract_number_length");


                StringBuffer number = new StringBuffer(String.valueOf(i));
                while (number.length() < length) {
                    number.insert(0, '0');
                }
                result.add(batchNumber + number.toString());
            }
        return result;
    }

    @Transient
    public void trachNumber(){
        for (BatchOperData<ContractNumber> bod: getResultData()){
            if (bod.isSelected())
                getEntityManager().remove(bod.getData());
        }
        getEntityManager().flush();
        clearData();

    }

    public void clearData(){
        refresh();
        resultData = null;
    }

    public List<BatchOperData<ContractNumber>> resultData;

    public List<BatchOperData<ContractNumber>> getResultData(){
        if (resultData == null){
            resultData = new ArrayList<BatchOperData<ContractNumber>>();
            for(ContractNumber cn: getResultList()){
                resultData.add(new BatchOperData<ContractNumber>(cn,false));
            }
        }
        return resultData;
    }

    public boolean isSelectAll(){
        for(BatchOperData<ContractNumber> bcn: getResultData()){
            if (! bcn.isSelected()){
                return false;
            }
        }
        return true;
    }

    public void setSelectAll(boolean value){
        for(BatchOperData<ContractNumber> bcn: getResultData()){
            bcn.setSelected(value);
        }
    }


}
