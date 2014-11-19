package com.dgsoft.faces.component;

import javax.el.ValueExpression;
import javax.faces.component.UIPanel;

/**
 * Created by cooper on 11/18/14.
 */
public class UIEntryGridBlock extends UIPanel {


    private String group;

    private int columns;

    private boolean rendered;

    public String getGroup() {
        if (this.group == null || "".equals(group.trim())){
            return group;
        }
        ValueExpression _ve = getValueExpression("group");
        if (_ve != null){
            return (String)_ve.getValue(getFacesContext().getELContext());
        }else {
            return null;
        }
    }

    public void setGroup(String group) {
        this.group = group;

    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    @Override
    public boolean getRendersChildren(){return true;}

    @Override
    public String getFamily() {
        return "com.dgsoft.faces.EntryGrid";
    }
}
