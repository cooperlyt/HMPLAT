package com.dgsoft.house.owner.business.subscribe;


import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessCorp;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import static com.dgsoft.common.system.PowerPersonEntity.LegalType.LEGAL_OWNER;

@Name("corpEditSubscribe")
@Scope(ScopeType.CONVERSATION)
public class CorpEditSubscribe implements TaskSubscribeComponent {

  private BusinessCorp corp;

  private PersonHelper<BusinessCorp> ownerHelper;

  @In(create = true)
  private OwnerBusinessHome ownerBusinessHome;

  public BusinessCorp getCorp() {
    return corp;
  }

  public void setCorp(BusinessCorp corp) {
    this.corp = corp;
  }

  public PersonHelper<BusinessCorp> getOwnerHelper() {
    if (ownerHelper == null){
      ownerHelper = new PersonHelper<BusinessCorp>(getCorp());
    }
    return ownerHelper;
  }

  @Override
  public void initSubscribe() {
    this.corp = ownerBusinessHome.getInstance().getBusinessCorp();
    if (this.corp == null){
      this.corp = new BusinessCorp();
      this.corp.setOwnerBusiness(ownerBusinessHome.getInstance());
      this.corp.setLegalType(LEGAL_OWNER);
      ownerBusinessHome.getInstance().setBusinessCorp(this.corp);
    }
  }

  @Override
  public void validSubscribe() {

  }

  @Override
  public boolean isPass() {
    return true;
  }

  @Override
  public boolean saveSubscribe() {
    return true;
  }
}
