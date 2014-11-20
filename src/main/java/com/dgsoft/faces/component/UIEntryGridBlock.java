package com.dgsoft.faces.component;

import javax.el.ValueExpression;
import javax.faces.component.UIPanel;

/**
 * Created by cooper on 11/18/14.
 */
public class UIEntryGridBlock extends UIPanel {

    protected enum Properties {
        group,
        columns,
        rendered
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

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    @Override
    public String getFamily() {
        return "com.dgsoft.faces.EntryGrid";
    }
}
