package com.dgsoft.faces.renderkit.html;

import org.jboss.seam.ui.util.*;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import java.io.IOException;

/**
 * Created by cooper on 9/3/15.
 */
public class ObjectOutRender extends Renderer {

    @Override
    public void encodeBegin(FacesContext facesContext, UIComponent component) throws IOException {
        facesContext.getResponseWriter().startElement("object", component);
        facesContext.getResponseWriter().writeAttribute("type", "application/x-mtokenplugin", null);
        facesContext.getResponseWriter().writeAttribute("width","0",null);
        facesContext.getResponseWriter().writeAttribute("height", "0", null);
        String clientId = component.getClientId(facesContext);
        facesContext.getResponseWriter().writeAttribute(org.jboss.seam.ui.util.HTML.ID_ATTR, clientId, JSFAttr.ID_ATTR);
    }


    @Override
    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException {
        facesContext.getResponseWriter().endElement("object");

    }
}
