package com.dgsoft.house.owner.business.create;

        import com.dgsoft.common.system.business.BusinessDataFill;
        import com.dgsoft.house.owner.OwnerEntityLoader;
        import com.dgsoft.house.owner.action.OwnerBusinessHome;
        import com.dgsoft.house.owner.model.*;
        import com.dgsoft.house.owner.total.data.InitToData;
        import org.jboss.seam.annotations.In;
        import org.jboss.seam.log.Logging;

        import java.util.List;

/**
 * Created by wxy on 2017-06-29.
 */
public abstract class MakeCardBaseFill implements BusinessDataFill {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    protected abstract MakeCard.CardType getType();

    protected MakeCard.CardType getToType(){
        return getType();
    }

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void fillData() {

        for (Card card: ownerBusinessHome.getInstance().getCards()){
            if (Card.CardType.OWNER_RSHIP.equals(card.getType())){
                return;
            }
        }

        List<HouseBusiness> houseBusiness =  ownerEntityLoader.getEntityManager().createQuery("select hb from HouseBusiness hb where hb.ownerBusiness.defineId='WP40' and hb.ownerBusiness.status='COMPLETE' and hb.houseCode=:houseCode",HouseBusiness.class)
                .setParameter("houseCode",ownerBusinessHome.getInstance().getSingleHoues().getHouseCode())
                .getResultList();


        String CardNo=null;
        if (houseBusiness.size()>0 && houseBusiness.get(0)!=null && houseBusiness.get(0).getAfterBusinessHouse().getPowerPersons()!=null){
            for(PowerPerson powerPerson:houseBusiness.get(0).getAfterBusinessHouse().getPowerPersons()){
                if (powerPerson.getType().equals(PowerPerson.PowerPersonType.INIT) && !powerPerson.isOld()){
                    if (powerPerson.getMakeCard()!=null) {
                        CardNo = powerPerson.getMakeCard().getNumber();
                    }
                }

            }


        }
        if (CardNo!=null){
            Card card = new Card();
            card.setType(Card.CardType.OWNER_RSHIP);
            card.setNumber(CardNo);
            card.setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getCards().add(card);
        }

    }

}
