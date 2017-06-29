package com.dgsoft.house.owner.business.create;

import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.Card;
import com.dgsoft.house.owner.model.MakeCard;
import com.dgsoft.house.owner.model.Reason;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2017-06-29.
 */
@Name("makeCardInitFill")
public class MakeCardInitFill extends MakeCardBaseFill {


    @Override
    protected MakeCard.CardType getType() {
        return MakeCard.CardType.OWNER_RSHIP;
    }
}
