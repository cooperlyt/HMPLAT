package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerNumberBuilder;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by Administrator on 15-5-27.
 */
@Name("makeCardOwnerRshipSubscribe")
public class MakeCardOwnerRshipSubscribe extends OwnerEntityHome<MakeCard> {

   @In
   private OwnerBusinessHome ownerBusinessHome;


   @In(create = true)
   private OwnerNumberBuilder ownerNumberBuilder;


  @Override
  public MakeCard createInstance(){

      return new MakeCard(MakeCard.CardType.OWNER_RSHIP,false,ownerNumberBuilder.useDayNumber("OWNER_RSHIP"));

  }
  @Override
  public void create(){
      super.create();
      if (!ownerBusinessHome.getInstance().getMakeCards().isEmpty()){
          setId(ownerBusinessHome.getInstance().getMakeCards().iterator().next().getId());
      }else{
          getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
          ownerBusinessHome.getInstance().getMakeCards().add(getInstance());
      }
  }








}
