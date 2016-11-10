package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.Card;
import com.dgsoft.house.owner.model.CardInfo;
import com.dgsoft.house.owner.model.MakeCard;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 4/26/16.
 */
@Name("printCardInfoInput")
public class PrintCardInfoInput {


    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private AuthenticationInfo authInfo;


    private List<CardInfo> cardInfos = new ArrayList<CardInfo>();

    public List<CardInfo> getCardInfos() {
        return cardInfos;
    }

    public void setCardInfos(List<CardInfo> cardInfos) {
        this.cardInfos = cardInfos;
    }

    @Create
    public void init(){
        for(MakeCard makeCard: ownerBusinessHome.getInstance().getMakeCardList()){
            if (makeCard.getCardInfo() == null){
                CardInfo cardInfo = new CardInfo(makeCard,authInfo.getLoginEmployee().getPersonName(),authInfo.getLoginEmployee().getId());
                makeCard.setCardInfo(cardInfo);
                cardInfos.add(cardInfo);
            }else{
                cardInfos.add(makeCard.getCardInfo());
            }
        }
    }
}
