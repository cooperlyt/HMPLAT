package com.dgsoft.common.helper;

import com.dgsoft.common.BigMoneyUtil;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Logging;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Created by wxy on 2015-08-28.
 */
@Name("bigMoneyConvert")
@org.jboss.seam.annotations.faces.Converter
@BypassInterceptors
public class BigMoneyConvert implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {

        try{
           return(BigMoneyUtil.getBigMoney(Double.valueOf(s)));
        }catch (Exception e){
            Logging.getLog(this.getClass()).warn("parse error:",e);
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {

        try {
            return(BigMoneyUtil.getBigMoney(Double.parseDouble(o.toString())));
        }catch (IllegalArgumentException e){
            Logging.getLog(this.getClass()).warn("parse error:",e);
            return null;

        }





    }
}
