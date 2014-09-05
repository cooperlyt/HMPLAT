package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.EvaluateCorporation;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.HouseBusinessHome;
import com.dgsoft.house.owner.model.Evaluate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import javax.faces.event.ValueChangeEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-2
 * Time: 下午2:20
 * To change this template use File | Settings | File Templates.
 */
@Name("evaluateSubscribe")
public class EvaluateSubscribe extends OwnerEntityHome<Evaluate> {

   @In
   private HouseBusinessHome houseBusinessHome;

   @In(create = true)
   private HouseEntityLoader houseEntityLoader;

   @Override
   public void create(){
       super.create();
       if(!houseBusinessHome.getInstance().getEvaluates().isEmpty()){
           setId(houseBusinessHome.getInstance().getEvaluates().iterator().next().getId());
       }else {
           getInstance().setOwnerBusiness(houseBusinessHome.getInstance());
           houseBusinessHome.getInstance().getEvaluates().add(getInstance());
       }

   }

   public void valueChangeListener(ValueChangeEvent e){
       getInstance().setEvaluateCorpName(houseEntityLoader.getEntityManager().find(EvaluateCorporation.class,e.getNewValue()).getName());


   }
}

