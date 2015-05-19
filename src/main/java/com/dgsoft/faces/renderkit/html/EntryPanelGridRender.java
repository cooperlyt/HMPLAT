package com.dgsoft.faces.renderkit.html;

import com.dgsoft.faces.component.UIEntryGridBlock;
import com.dgsoft.faces.component.UIEntryPanelGrid;
import org.jboss.seam.log.Logging;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.util.List;

/**
 * Created by cooper on 11/17/14.
 */
public class EntryPanelGridRender extends Renderer {


    @Override
    public void encodeBegin(FacesContext facesContext, UIComponent component) throws IOException {
        ResponseWriter writer = facesContext.getResponseWriter();
        UIEntryPanelGrid gridComponent = (UIEntryPanelGrid) component;


        writer.startElement("table", component);
        if ((gridComponent.getStyleClass() != null) && !"".equals(gridComponent.getStyleClass().trim())) {
            writer.writeAttribute("class", gridComponent.getStyleClass(), null);
        }

        if ((gridComponent.getStyle() != null) && !"".equals(gridComponent.getStyle().trim())) {
            writer.writeAttribute("style", gridComponent.getStyle(), null);
        }

        if (gridComponent.isHaveGroup()) {

            writer.startElement("colgroup", component);
            writer.writeAttribute("span",2,null);

            if (gridComponent.getKeyWidth() != null)
                writer.writeAttribute("width",  gridComponent.getKeyWidth() + "px", null);

            writer.startElement("col",component);
            if ( gridComponent.getGroupWidth() != null) {
                writer.writeAttribute("width",gridComponent.getGroupWidth() + "px",null);
            }

            writer.endElement("col");

            writer.startElement("col", component);

            if ((gridComponent.getGroupWidth() != null) && (gridComponent.getKeyWidth() != null)){
                writer.writeAttribute("width",(gridComponent.getKeyWidth() - gridComponent.getGroupWidth()) + "px",null);
            }
            writer.endElement("col");

            writer.endElement("colgroup");

        }

        writer.startElement("colgroup", component);

        int col = gridComponent.getMaxColumn();
        boolean keyCol = false;
        for (int i = 1; i < col; i++) {


            writer.startElement("col", component);

            if (!gridComponent.isAutoLastWidth() || (i != (col - 1))) {

                if (keyCol && gridComponent.getKeyWidth() != null)
                    writer.writeAttribute("width",  gridComponent.getKeyWidth() + "px", null);
                if (!keyCol && gridComponent.getValueWidth() != null) {
                    writer.writeAttribute("width", gridComponent.getValueWidth() + "px", null);
                }
            }

            writer.endElement("col");
            keyCol = !keyCol;
        }

        writer.endElement("colgroup");


        writer.startElement("tbody", component);
    }


    @Override
    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException {

        facesContext.getResponseWriter().endElement("tbody");
        facesContext.getResponseWriter().endElement("table");


    }
}
