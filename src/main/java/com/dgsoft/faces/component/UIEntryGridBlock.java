package com.dgsoft.faces.component;

import javax.faces.component.UIPanel;

/**
 * Created by cooper on 11/18/14.
 */
public class UIEntryGridBlock extends UIPanel {

    protected enum Properties {
        group,
        columns,
        rendered,
        expandId
    }

    public String getGroup() {
        return (String) getStateHelper().eval(Properties.group, "");
    }

    public void setGroup(String group) {
        getStateHelper().put(Properties.group, group);
    }

    public int getColumns() {
        return (Integer) getStateHelper().eval(Properties.columns, 4);
    }

    public void setColumns(int columns) {
        getStateHelper().put(Properties.columns, columns);
    }

    public boolean isRendered() {
        return (Boolean) getStateHelper().eval(Properties.rendered, true);
    }

    public void setRendered(boolean rendered) {
        getStateHelper().put(Properties.rendered, rendered);
    }

    public boolean isHaveGroup(){
        return (getGroup() != null) && !"".equals(getGroup().trim());
    }

    public String getExpandId(){
        return (String) getStateHelper().eval(Properties.expandId,"");
    }

    public void setExpandId(String id){
        getStateHelper().put(Properties.expandId, id);
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    @Override
    public String getFamily() {
        return "com.dgsoft.faces.EntryGrid";
    }
}
