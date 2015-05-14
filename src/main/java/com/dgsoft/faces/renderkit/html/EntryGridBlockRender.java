package com.dgsoft.faces.renderkit.html;

import com.dgsoft.faces.component.UIEntryColumn;
import com.dgsoft.faces.component.UIEntryGridBlock;
import com.dgsoft.faces.component.UIEntryPanelGrid;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import java.io.IOException;

/**
 * Created by cooper on 11/18/14.
 */
public class EntryGridBlockRender extends Renderer {

    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        //do not encode Children
    }

    @Override
    public void encodeEnd(FacesContext facesContext, UIComponent component)
            throws IOException {

        int childCount = component.getChildren().size();

        if (childCount <= 0) {
            return;
        }

        UIEntryGridBlock blockComponent = (UIEntryGridBlock) component;
        if (!blockComponent.isRendered()){
            return;
        }

        UIComponent parent = component.getParent();
        while ((parent != null) && !(parent instanceof UIEntryPanelGrid)) {
            parent = parent.getParent();
        }
        if (parent == null) {
            return;
        }
        UIEntryPanelGrid parentGrid = (UIEntryPanelGrid) parent;

        ResponseWriter writer = facesContext.getResponseWriter();


        writer.startElement("tr", component);

        boolean haveGroup = (blockComponent.getGroup() != null) && !"".equals(blockComponent.getGroup().trim());

        if (haveGroup) {
            int rowCount = 1;
            int count = 0;
            for (int i = 0; i < childCount; i++) {
                UIComponent child = component.getChildren().get(i);

                if (child instanceof UIEntryColumn) {
                    count = count + ((UIEntryColumn) child).getColspan();
                } else {
                    count++;
                }

                if (count >= blockComponent.getColumns()) {
                    count = 0;
                    if (i < (childCount - 1)) {
                        rowCount++;
                    }
                }

            }

            writer.startElement("th", component);
            writer.writeAttribute("rowspan", rowCount, null);


            writer.writeAttribute("style","width:" + parentGrid.getGroupWidth(),null);
            writer.writeAttribute("class","details-table-group",null);
            writer.write(blockComponent.getGroup());

            writer.endElement("th");

        }

        int count = 0;
        String elem = "th";

        for (int i = 0; i < childCount; i++) {
            UIComponent child = component.getChildren().get(i);
            if (count == 0) {
                if (i > 0) {
                    writer.startElement("tr", component);
                }

                if (childCount < 2){
                    elem = "td";
                }else{
                    elem = "th";
                }
            }

            int colspan = 1;
            if (child instanceof UIEntryColumn) {
                colspan = ((UIEntryColumn) child).getColspan();
            }

            if ((count == 0) && !haveGroup) {
                colspan = colspan + 1;
                count--;
            }
            count = count + colspan;

            if (count >= blockComponent.getColumns()) {
                int maxColumn = parentGrid.getMaxColumn();
                if (maxColumn > blockComponent.getColumns()) {
                    colspan = colspan + (maxColumn - blockComponent.getColumns());
                }
                count = 0;
            }

            writer.startElement(elem, component);

            if (colspan > 1) {
                writer.writeAttribute("colspan", colspan, null);
            }
            if (!parentGrid.isAutoLastWidth() || (count != 0)){
                String width = "th".equals(elem) ? parentGrid.getKeyWidth() : parentGrid.getValueWidth();
                if ((width != null) && !"".equals(width.trim())){
                    writer.writeAttribute("style","width:" + width,null);
                }
            }


            if (childCount < 2){
                writer.writeAttribute("class","details-context-td",null);
            }

            child.encodeAll(facesContext);

            writer.endElement(elem);

            if ("th".equals(elem)) {
                elem = "td";
            } else {
                elem = "th";
            }

            if (count == 0) {
                writer.endElement("tr");
            }

        }
    }
}