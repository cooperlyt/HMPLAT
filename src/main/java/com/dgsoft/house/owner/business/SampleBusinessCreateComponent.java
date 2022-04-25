package com.dgsoft.house.owner.business;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

@Name("sampleBusinessCreateComponent")
public class SampleBusinessCreateComponent extends OwnerBusinessCreateComponent{

  @In(create = true)
  private OwnerBusinessStart ownerBusinessStart;

  @Override
  protected String getNormalBusinessPage() {
    return ownerBusinessStart.beginEdit();
  }

  @Override
  protected String getPatchBusinessPage() {
    return null;
  }
}
