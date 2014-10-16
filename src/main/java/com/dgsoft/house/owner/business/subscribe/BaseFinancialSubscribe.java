package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.EvaluateCorporation;
import com.dgsoft.house.model.FinancialCorporation;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.Financial;
import org.jboss.seam.annotations.In;

import javax.faces.event.ValueChangeEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-14
 * Time: 下午4:55
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseFinancialSubscribe extends OwnerEntityHome<Financial> {

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    protected abstract Financial.FinancialType getType();

    @Override
    protected Financial createInstance() {
        return new Financial(getType());
    }

    @Override
    public Class<Financial> getEntityClass() {
        return Financial.class;
    }

    @Override
    public void create(){
        super.create();
        // ownerBusinessHome.getSingleHoues().getOwnerBusiness().getFinancials()
        for(Financial financial:ownerBusinessHome.getInstance().getFinancials()){
            if(financial.getType().equals(getType())){
                setId(financial.getId());
                return;
            }
        }
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getFinancials().add(getInstance());
    }

    public void valueChangeListener(ValueChangeEvent e){
       getInstance().setName(houseEntityLoader.getEntityManager().find(FinancialCorporation.class,e.getNewValue()).getName());
       getInstance().setPhone(houseEntityLoader.getEntityManager().find(FinancialCorporation.class,e.getNewValue()).getAttachCorporation().getPhone());
    }
}
