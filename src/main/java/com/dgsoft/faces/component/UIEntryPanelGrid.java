package com.dgsoft.faces.component;

import org.jboss.seam.log.Logging;

import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import java.util.List;

/**
 * Created by cooper on 11/17/14.
 */
public class UIEntryPanelGrid extends UIPanel {


    protected enum Properties {
        styleClass,
        style,
        valueWidth,
        keyWidth,
        groupWidth,
        autoLastWidth
    }

    public UIEntryPanelGrid() {
        super();
    }

    public Integer getGroupWidth() {
        Integer result =(Integer) getStateHelper().eval(Properties.groupWidth);
        if (result == null || (result.compareTo(0) <= 0) ){
            return null;
        }else {
            return result;
        }

    }



    public void setGroupWidth(Integer groupWidth) {
        getStateHelper().put(Properties.groupWidth, groupWidth);
    }

    public Integer getValueWidth() {
        Integer result =(Integer) getStateHelper().eval(Properties.valueWidth);
        if (result == null || (result.compareTo(0) <= 0) ){
            return null;
        }else {
            return result;
        }
    }

    public void setValueWidth(Integer valueWidth) {

        getStateHelper().put(Properties.valueWidth,valueWidth);
    }

    public Integer getKeyWidth() {

        Integer result =(Integer) getStateHelper().eval(Properties.keyWidth);
        if (result == null || (result.compareTo(0) <= 0) ){
            return null;
        }else {
            return result;
        }
    }

    public void setKeyWidth(Integer keyWidth) {

        getStateHelper().put(Properties.keyWidth,keyWidth);
    }

    public String getStyleClass() {
        return (String) getStateHelper().eval(Properties.styleClass);
    }

    public void setStyleClass(String styleClass) {
        getStateHelper().put(Properties.styleClass,styleClass);
    }

    public String getStyle() {
        return (String) getStateHelper().eval(Properties.style);
    }

    public void setStyle(String style) {
        getStateHelper().put(Properties.style,style);
    }

    private int getMaxColumn(List<UIComponent> components){
        int result = 0;
        for(UIComponent child: components){
            int childColCount = 0;
            if ((child instanceof UIEntryGridBlock) && child.isRendered()){
                childColCount = ((UIEntryGridBlock)child).getColumns();

            }else if (child.isRendered() && (child.getChildCount() > 0)){
                childColCount = getMaxColumn(child.getChildren());
            }
            if (childColCount > result){
                result = childColCount;
            }
        }
        return  result;
    }

    public int getMaxColumn(){
        return getMaxColumn(getChildren());
    }

    private boolean isHaveGroup(List<UIComponent> components){
        for(UIComponent child: components){

            if ((child instanceof UIEntryGridBlock) && child.isRendered()){
                if (((UIEntryGridBlock) child).isHaveGroup()){
                    return true;
                }

            }else if (child.isRendered() && (child.getChildCount() > 0)){
               if (isHaveGroup(child.getChildren())){
                   return true;
               }
            }
        }
        return false;
    }

    public boolean isHaveGroup(){
        return isHaveGroup(getChildren());
    }

    public boolean isAutoLastWidth() {
        return (Boolean) getStateHelper().eval(Properties.autoLastWidth,true);
    }

    public void setAutoLastWidth(boolean autoLastWidth) {
        getStateHelper().put(Properties.autoLastWidth,autoLastWidth);
    }

    @Override
    public String getFamily() {
        return "com.dgsoft.faces.EntryGrid";
    }

}
