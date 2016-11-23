package com.dgsoft.faces.validator;

/**
 * Created by cooper on 10/1/16.
 */
import cc.coopersoft.comm.tools.IdCardUtils;
import org.jboss.seam.faces.FacesMessages;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * Created by cooper on 10/1/16.
 */
public class IdCardNumberValidator implements javax.faces.validator.Validator {

    public static final String MATCH_CARD_NUMBER_EXCEPTION_MESSAGE_ID =
            "javax.faces.validator.CardNumber.MATCH_EXCEPTION";


    private Object resolveLabel(FacesContext facesContext, UIComponent component) {
        Object lbl = component.getAttributes().get("label");
        if (lbl == null || (lbl instanceof String && ((String) lbl).length() == 0))
        {
            lbl = component.getValueExpression("label");
        }
        if (lbl == null)
        {
            lbl = component.getClientId(facesContext);
        }
        return lbl;
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value != null){
            if (!IdCardUtils.validateCard(value.toString())){

                throw new ValidatorException(FacesMessages.createFacesMessage(FacesMessage.SEVERITY_ERROR, MATCH_CARD_NUMBER_EXCEPTION_MESSAGE_ID, resolveLabel(context,component)));


            }
        }
    }
}