package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.Card;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-9
 * Time: 下午4:29
 * To change this template use File | Settings | File Templates.
 */
@Name("cardSaleLicenseSubsrcibe")
public class CardSaleLicenseSubsrcibe extends OwnerEntityHome<Card> {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public Card createInstance(){
        return new Card(Card.CardType.SALE_LICENSE);
    }

    @Override
    public void create(){
        super.create();
        for(Card card:ownerBusinessHome.getInstance().getCards()){
            if (card.getType().equals(Card.CardType.SALE_LICENSE)){
                setId(card.getId());
                return;
            }

        }
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getCards().add(getInstance());

    }
}
