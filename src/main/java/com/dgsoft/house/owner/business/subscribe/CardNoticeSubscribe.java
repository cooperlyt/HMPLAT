package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.HouseBusinessHome;
import com.dgsoft.house.owner.model.Card;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-4
 * Time: 下午3:43
 * To change this template use File | Settings | File Templates.
 */
@Name("cardNoticeSubscribe")
public class CardNoticeSubscribe extends OwnerEntityHome<Card> {

    @In
    private HouseBusinessHome houseBusinessHome;

    @Override
    public Card createInstance(){
        return new Card(Card.CardType.NOTICE);
    }

    @Override
    public void create(){
        super.create();
        for(Card card:houseBusinessHome.getInstance().getCards()){
            if (card.getType().equals(Card.CardType.NOTICE)){
                setId(card.getId());
                return;
            }

        }
        getInstance().setOwnerBusiness(houseBusinessHome.getInstance());
        houseBusinessHome.getInstance().getCards().add(getInstance());

    }



}
