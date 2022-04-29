package com.dgsoft.house.owner.helper;

import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.AttachCorpType;
import com.dgsoft.house.model.AttachCorporation;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

@Name("corpRegisterDataProvider")
public class CorpRegisterDataProvider extends MultiOperatorEntityQuery<AttachCorporation> implements RestDataProvider{

  private final static int MAX_RESULT_COUNT = 20;

  private static final String EJBQL = "select aCorp from AttachCorporation aCorp " +
          "left join fetch aCorp.developer developer " +
          "left join fetch aCorp.mappingCorporation mapping " +
          "left join fetch aCorp.evaluateCorporation evaluate " +
          "left join fetch aCorp.agencies houseSellCompany " +
          "left join fetch aCorp.propertyCorporation propertyCorp ";

  private static final String[] RESTRICTIONS1 = {
          "aCorp.type = #{attachCorporationList.type}" ,
          "aCorp.enable = true "
  };

  private static final String[] RESTRICTIONS = {
          "lower(aCorp.id) = lower(#{attachCorporationList.searchKey})",
          "lower(developer.name) like lower(concat('%',#{attachCorporationList.searchKey},'%'))",
          "lower(developer.id) = lower(#{attachCorporationList.searchKey})",
          "lower(mapping.name) like lower(concat('%',#{attachCorporationList.searchKey},'%'))",
          "lower(mapping.id) = lower(#{attachCorporationList.searchKey})",
          "lower(evaluate.name) like lower(concat('%',#{attachCorporationList.searchKey},'%'))",
          "lower(evaluate.id) = lower(#{attachCorporationList.searchKey})",
          "lower(houseSellCompany.id) = lower(#{attachCorporationList.searchKey})",
          "lower(houseSellCompany.name) = like lower(concat('%',#{attachCorporationList.searchKey},'%'))",
          "lower(propertyCorp.id) = lower(#{attachCorporationList.searchKey})",
          "lower(propertyCorp.name) = like lower(concat('%',#{attachCorporationList.searchKey},'%'))"

  };

  @RequestParameter
  @Override
  public Integer getFirstResult(){
    return super.getFirstResult();
  }

  @Override
  public void setFirstResult(Integer firstResult){
    super.setFirstResult(firstResult);
  }

  @RequestParameter
  private String searchKey;

  public String getSearchKey() {
    return searchKey;
  }

  public void setSearchKey(String searchKey) {
    this.searchKey = searchKey;
  }


  private AttachCorpType type;

  public AttachCorpType getType() {
    if (type == null)
      return AttachCorpType.DEVELOPER;
    return type;
  }

  public void setType(AttachCorpType type) {
    this.type = type;
  }

  @RequestParameter
  public void setTypeName(String name){
    if (name == null || name.trim().equals("")){
      type = null;
    }else{
      type = AttachCorpType.valueOf(name);
    }
  }

  public String getTypeName(){
    if (type == null)
      return AttachCorpType.DEVELOPER.name();
    return type.name();
  }

  public CorpRegisterDataProvider() {
    setEjbql(EJBQL);
    setRestrictionLogicOperator("and");
    RestrictionGroup rg = new RestrictionGroup("and", Arrays.asList(RESTRICTIONS1));
    rg.getChildren().add(new RestrictionGroup("or",Arrays.asList(RESTRICTIONS)));
    setRestrictionGroup(rg);
    setOrderColumn("aCorp.recordDate");
    setOrderDirection("desc");
    setMaxResults(MAX_RESULT_COUNT);
  }

  @Override
  public JSONObject getJsonData() {
    if (getType() == null){
      return null;
    }
    JSONObject result = new JSONObject();
    try{
      JSONArray dataArray = new JSONArray();
      for (AttachCorporation corporation: getResultList()){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",corporation.getId());
        jsonObject.put("name",corporation.getName());
        jsonObject.put("recordDate",corporation.getRecordDate().getTime());
        jsonObject.put("address",corporation.getAddress());
        jsonObject.put("phone",corporation.getPhone());
        jsonObject.put("ownerName",corporation.getName());
        jsonObject.put("licenseNumber",corporation.getLicenseNumber());
        jsonObject.put("cerCode",corporation.getCerCode());
      }

      result.put("datas",dataArray);
      result.put("pageSize",MAX_RESULT_COUNT);
      result.put("recordCount",getResultCount());
      result.put("nextExists",isNextExists());
      result.put("pageCount",getPageCount());
      result.put("previousExists",isPreviousExists());
      return result;
    }catch (JSONException e) {
      Logging.getLog(getClass()).error(e.getMessage(), e);
      return null;
    }
  }


  @Override
  protected String getPersistenceContextName() {
    return "houseEntityManager";
  }
}
