package com.dgsoft.faces.component;

import javax.faces.component.UIPanel;

/**
 * Created by cooper on 11/17/14.
 */
public class UIEntryColumn extends UIPanel {


    public int getColspan() {
        Integer colspan = (Integer) getStateHelper().eval("colspan");
        if ((colspan == null) || (colspan < 1)){
            return 1;
        }
        return colspan;
    }

    public void setColspan(int colspan) {
        getStateHelper().put("colspan",colspan);
    }

    @Override
    public String getFamily() {
        return "com.dgsoft.faces.EntryGrid";
    }
}
