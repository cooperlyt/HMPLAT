package com.dgsoft.house.owner.action;

import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.AttachCorpType;
import com.dgsoft.house.owner.model.BusinessCorp;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;

@Name("corpRegisterBusinessList")
public class CorpRegisterBusinessList extends MultiOperatorEntityQuery<BusinessCorp> {


  private static final String EJBQL = "select cb from BusinessCorp cb " +
          "left join cb.ownerBusiness ob ";

  private static final String[] RESTRICTIONS_ROOT = {
          "cb.type = #{corpRegisterBusinessCondition.type}",
  };

  private static final String[] RESTRICTIONS = {
          "lower(cb.name) like lower(concat('%',#{corpRegisterBusinessCondition.searchKey},'%'))",
          "lower(cb.ownerName) like lower(concat(#{corpRegisterBusinessCondition.searchKey},'%'))",
          "lower(cb.ownerCard) like lower(concat(#{corpRegisterBusinessCondition.searchKey},'%'))",
          "lower(cb.licenseNumber) like lower(concat(#{corpRegisterBusinessCondition.searchKey},'%'))",
          "lower(cb.cerCode) like lower(concat(#{corpRegisterBusinessCondition.searchKey},'%'))",
          "lower(cb.companyCode) like lower(concat(#{corpRegisterBusinessCondition.searchKey},'%'))",

          "lower(ob.id) = lower(#{corpRegisterBusinessCondition.searchKey})",
  };

  public CorpRegisterBusinessList() {
    RestrictionGroup restrictionGroup = new RestrictionGroup("and", Arrays.asList(RESTRICTIONS_ROOT));
    restrictionGroup.getChildren().add(new RestrictionGroup("or",Arrays.asList(RESTRICTIONS)));
    setEjbql(EJBQL);
    setRestrictionLogicOperator("and");
    setRestrictionGroup(restrictionGroup);
    setOrderColumn("ob.createTime");
    setOrderDirection("desc");
    setMaxResults(20);
  }

  private AttachCorpType type;

  private String searchKey;

  public String getSearchKey() {
    return searchKey;
  }

  public void setSearchKey(String searchKey) {
    this.searchKey = searchKey;
  }

  public AttachCorpType getType() {
    return type;
  }

  public void setType(AttachCorpType type) {
    this.type = type;
  }

  public void setTypeName(String name){
    if (name == null || name.trim().equals("")){
      type = null;
    }else{
      type = AttachCorpType.valueOf(name);
    }
  }

  public String getTypeName(){
    if (type == null)
      return null;
    return type.name();
  }

  public void searchAction(){
    setFirstResult(0);
  }

  @Override
  protected String getPersistenceContextName() {
    return "ownerEntityManager";
  }
}
