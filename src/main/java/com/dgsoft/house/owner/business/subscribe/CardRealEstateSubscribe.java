package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.Card;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

@Name("cardRealEstateSubscribe")
public class CardRealEstateSubscribe extends OwnerEntityHome<Card> {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public Card createInstance(){
        return new Card(Card.CardType.REAL_ESTATE);
    }

    @Override
    public void create(){
        super.create();
        for(Card card:ownerBusinessHome.getInstance().getCards()){
            if (card.getType().equals(Card.CardType.REAL_ESTATE)){
                if (card.getId()!=null){
                    setId(card.getId());
                }else{
                    setInstance(card);
                }
                return;
            }

        }
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getCards().add(getInstance());

    }
}
