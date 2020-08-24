package com.dgsoft.house.owner.business.subscribe;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.EvaluateCorporation;
import com.dgsoft.house.model.MappingCorporation;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.MappingCorp;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import javax.faces.event.ValueChangeEvent;

@Name("mappingCorpSubscribe2")
public class MappingCorpSubscribe2 extends OwnerEntityHome<MappingCorp> {
    @In
    private OwnerBusinessHome ownerBusinessHome;


    @Override
    public void create(){
        super.create();
        if(!ownerBusinessHome.getInstance().getMappingCorps().isEmpty()){
            setId(ownerBusinessHome.getInstance().getMappingCorps().iterator().next().getId());
        }else {
            getInstance().setCode(ownerBusinessHome.getInstance().getId());
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getMappingCorps().add(getInstance());
        }

    }
}
