package com.dgsoft.faces.renderkit.html;

import com.dgsoft.faces.component.UIEntryPanelGrid;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import java.io.IOException;

/**
 * Created by cooper on 11/17/14.
 */
public class EntryPanelGridRender extends Renderer {

    @Override
    public void encodeBegin(FacesContext facesContext, UIComponent component) throws IOException {
        ResponseWriter writer = facesContext.getResponseWriter();
        UIEntryPanelGrid gridComponent = (UIEntryPanelGrid) component;

        writer.startElement("table",component);


        if ((gridComponent.getStyleClass() != null) && !"".equals(gridComponent.getStyleClass().trim())){
            writer.writeAttribute("class",gridComponent.getStyleClass(),null);
        }

        if ((gridComponent.getStyle() != null) && !"".equals(gridComponent.getStyle().trim())){
            writer.writeAttribute("style",gridComponent.getStyle(),null);
        }

        writer.startElement("tbody",component);
    }


    @Override
    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException {

        facesContext.getResponseWriter().endElement("tbody");
        facesContext.getResponseWriter().endElement("table");


    }
}
