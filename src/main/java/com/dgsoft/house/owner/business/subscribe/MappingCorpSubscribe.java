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

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-2
 * Time: 下午3:31
 * To change this template use File | Settings | File Templates.
 */
@Name("mappingCorpSubscribe")
public class MappingCorpSubscribe extends OwnerEntityHome<MappingCorp> {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @Override
    public void create(){
        super.create();
        if(!ownerBusinessHome.getInstance().getMappingCorps().isEmpty()){
            setId(ownerBusinessHome.getInstance().getMappingCorps().iterator().next().getId());
        }else {
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getMappingCorps().add(getInstance());
        }

    }

    public void valueChangeListener(ValueChangeEvent e){
        getInstance().setName(houseEntityLoader.getEntityManager().find(MappingCorporation.class, e.getNewValue()).getName());


    }

}
