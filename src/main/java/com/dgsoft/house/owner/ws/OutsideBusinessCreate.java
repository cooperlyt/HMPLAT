package com.dgsoft.house.owner.ws;

import com.dgsoft.common.system.action.BusinessDefineHome;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 9/26/15.
 */
@Name("outsideBusinessCreate")
public class OutsideBusinessCreate {

    @In(create = true)
    private BusinessDefineHome businessDefineHome;



}
