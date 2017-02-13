package com.dgsoft.house.owner.action;

import cc.coopersoft.comm.tools.IdCardUtils;
import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.utils.seam.RestrictionGroup;


/**
 * Created by cooper on 10/2/15.
 */
public abstract class BusinessHouseCondition {


    private String searchKey;

    private String mapNumber;

    private String blockNumber;

    private String buildNumber;

    private String houseNumber;


    private PersonEntity.CredentialsType credentialsType;

    public abstract String getEjbql();

    public abstract RestrictionGroup getRestrictionGroup();



    public String getMapNumber() {
        return mapNumber;
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey){
        this.searchKey = searchKey;
    }


    public PersonEntity.CredentialsType getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(PersonEntity.CredentialsType credentialsType) {
        this.credentialsType = credentialsType;
    }

    public String getCredentialsTypeName(){
        if (credentialsType == null){
            return null;
        }
        return credentialsType.name();
    }

    public void setCredentialsTypeName(String name){
        if ((name == null) || name.trim().equals("")){
            credentialsType = null;
        }else{
            credentialsType = PersonEntity.CredentialsType.valueOf(name);
        }
    }

    public String getCredentialsNumber18(){
        if (getSearchKey() == null){
            return null;
        }
        if (PersonEntity.CredentialsType.MASTER_ID.equals(credentialsType) && (getSearchKey().trim().length() == 15)){
           return IdCardUtils.conver15CardTo18(getSearchKey().trim());
        }else{
            return getSearchKey();
        }
    }

    public String getCredentialsNumber15(){
        if (getSearchKey() == null){
            return null;
        }
        if (PersonEntity.CredentialsType.MASTER_ID.equals(credentialsType) && (getSearchKey().trim().length() == 18)){
            return IdCardUtils.conver18CardTo15(getSearchKey().trim());
        }else{
            return getSearchKey();
        }
    }


    public void resetCondition(){
        searchKey = null;
        mapNumber = null;
        blockNumber = null;
        buildNumber = null;
        houseNumber = null;
    }




}
