package com.dgsoft.house.action;

import com.dgsoft.house.AttachCorpType;
import com.dgsoft.house.model.AttachCorporation;
import com.dgsoft.house.model.MappingCorporation;
import org.jboss.seam.annotations.Name;

import java.util.Date;

@Name("mapCorpHome")
public class MapCorpHome extends AttachCorporationHome{

  private MappingCorporation mappingCorp;

  public MappingCorporation getMappingCorp() {
    if (mappingCorp == null){
      initInstance();
    }
    return mappingCorp;
  }

  @Override
  protected AttachCorporation createInstance(){
    AttachCorporation result =  new AttachCorporation(String.valueOf(HouseNumberBuilder.instance().useNumber(NUMBER_KEY)), AttachCorpType.MAPPING,true, new Date());
    mappingCorp = new MappingCorporation(result);
    result.setMappingCorporation(mappingCorp);

    return result;
  }


  @Override
  protected boolean verifyPersistAvailable() {
    boolean result = super.verifyPersistAvailable();
    if (result){
      mappingCorp.setId(getInstance().getId());
    }
    return result;
  }


  @Override
  protected void initInstance(){
    super.initInstance();
    if (isIdDefined()){
      mappingCorp = getInstance().getMappingCorporation();
    }
  }
}
