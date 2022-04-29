package com.dgsoft.house.action;

import com.dgsoft.house.AttachCorpType;
import com.dgsoft.house.model.AttachCorporation;
import com.dgsoft.house.model.MappingCorporation;
import com.dgsoft.house.model.Mcompany;
import org.jboss.seam.annotations.Name;

import java.util.Date;

@Name("propertyCorpHome")
public class PropertyCorpHome extends AttachCorporationHome{

  private Mcompany propertyCorp;

  public Mcompany getPropertyCorp() {
    if (propertyCorp == null){
      initInstance();
    }
    return propertyCorp;
  }

  @Override
  protected AttachCorporation createInstance(){
    AttachCorporation result =  new AttachCorporation(String.valueOf(HouseNumberBuilder.instance().useNumber(NUMBER_KEY)), AttachCorpType.MAPPING,true, new Date());
    propertyCorp = new Mcompany(result);
    result.setPropertyCorporation(propertyCorp);

    return result;
  }


  @Override
  protected boolean verifyPersistAvailable() {
    boolean result = super.verifyPersistAvailable();
    if (result){
      propertyCorp.setId(getInstance().getId());
    }
    return result;
  }


  @Override
  protected void initInstance(){
    super.initInstance();
    if (isIdDefined()){
      propertyCorp = getInstance().getPropertyCorporation();
    }
  }
}
