package com.dgsoft.house.owner.business;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Created by cooper on 9/30/15.
 */
@Name("projectBusinessModifyStart")
@Scope(ScopeType.CONVERSATION)
public class ProjectBusinessModifyStart {


    private String selectBusinessId;

    public String getSelectBusinessId() {
        return selectBusinessId;
    }

    public void setSelectBusinessId(String selectBusinessId) {
        this.selectBusinessId = selectBusinessId;
    }


}
