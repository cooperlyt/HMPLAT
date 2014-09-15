package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.model.BusinessDefine;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-15
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */
@Name("ownerBusinessHelper")
public class BusinessHelper {
    /**
     * 不收费的业务ID
     */
    private static final String[]  NOT_HAVE_CHARGE_BUSINESS_ID ={
     "WP38","WP43","WP46","WP4","WP8","WP12","WP17","WP21","WP73","WP74","WP84"
    };
    /**
     * 不打证的业务ID
    */

    private static final String[] NOT_HAVE_PRINT_CARD_BUSINESS_ID ={
      "WP38","WP42","WP43","WP46","WP4","WP8","WP12","WP17","WP21","WP73","WP74","WP84"

    };

    @In
    private BusinessDefineHome businessDefineHome;
    /**
     * true=不收费
    */
    public boolean isHaveCharge(){
        if  (Arrays.asList(NOT_HAVE_CHARGE_BUSINESS_ID).contains(businessDefineHome.getInstance().getId())) {
            return true;
        } else {
            return false;
        }

    }
    /**
     * true=不打证
     */
    public boolean isPrintCard(){
        if  (Arrays.asList(NOT_HAVE_PRINT_CARD_BUSINESS_ID).contains(businessDefineHome.getInstance().getId())) {
            return true;
        } else {
            return false;
        }
    }

}
