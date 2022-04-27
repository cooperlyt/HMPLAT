package com.dgsoft.house.action;

import com.dgsoft.house.AttachCorpType;
import com.dgsoft.house.model.AttachCorporation;
import com.dgsoft.house.model.EvaluateCorporation;
import org.jboss.seam.annotations.Name;

import java.util.Date;

@Name("evaluateCorpHome")
public class EvaluateCorpHome extends AttachCorporationHome{

  private EvaluateCorporation evaluateCorporation;

  public EvaluateCorporation getEvaluateCorporation() {
    if (evaluateCorporation == null){
      initInstance();
    }
    return evaluateCorporation;
  }

  @Override
  protected AttachCorporation createInstance(){
    AttachCorporation result =  new AttachCorporation(String.valueOf(HouseNumberBuilder.instance().useNumber(NUMBER_KEY)), AttachCorpType.EVALUATE,true, new Date());
    evaluateCorporation = new EvaluateCorporation(result);
    result.setEvaluateCorporation(evaluateCorporation);

    return result;
  }


  @Override
  protected boolean verifyPersistAvailable() {
    boolean result = super.verifyPersistAvailable();
    if (result){
      evaluateCorporation.setId(getInstance().getId());
    }
    return result;
  }


  @Override
  protected void initInstance(){
    super.initInstance();
    if (isIdDefined()){
      evaluateCorporation = getInstance().getEvaluateCorporation();
    }
  }
}
