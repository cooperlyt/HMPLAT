package com.dgsoft.faces.component;

import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
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

    public String getGroupWidth() {
        return (String) getStateHelper().eval(Properties.groupWidth);
    }

    public void setGroupWidth(String groupWidth) {
        getStateHelper().put(Properties.groupWidth, groupWidth);
    }

    public String getValueWidth() {
        return (String) getStateHelper().eval(Properties.valueWidth);
    }

    public void setValueWidth(String valueWidth) {
        getStateHelper().put(Properties.valueWidth,valueWidth);
    }

    public String getKeyWidth() {
        return (String) getStateHelper().eval(Properties.keyWidth);
    }

    public void setKeyWidth(String keyWidth) {
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

    public int getMaxColumn(){
        int result = 0;
        for(UIComponent child: getChildren()){
           if (child instanceof UIEntryGridBlock){
               int childColCount = ((UIEntryGridBlock)child).getColumns();
               if (childColCount > result){
                   result = childColCount;
               }
           }
        }
        return result;
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
