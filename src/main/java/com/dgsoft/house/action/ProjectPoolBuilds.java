package com.dgsoft.house.action;

import com.dgsoft.common.SetLinkList;
import com.dgsoft.common.helper.ActionExecuteState;
import com.dgsoft.house.model.PoolBuild;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;

/**
 * Created by cooper on 7/14/14.
 */
@Name("projectPoolBuilds")
public class ProjectPoolBuilds {

    @In
    private ProjectHome projectHome;

    @In
    private ActionExecuteState actionExecuteState;

    private SetLinkList<PoolBuild> poolBuilds;

    @DataModel
    public SetLinkList<PoolBuild> getPoolBuilds() {
        if (poolBuilds == null){
            poolBuilds = new SetLinkList<PoolBuild>(projectHome.getInstance().getPoolBuilds());
        }
        return poolBuilds;
    }

    @DataModelSelection
    private PoolBuild selectedPoolBuild;

    private PoolBuild editingPoolBuild;

    public PoolBuild getEditingPoolBuild() {
        return editingPoolBuild;
    }

    public void setEditingPoolBuild(PoolBuild editingPoolBuild) {
        this.editingPoolBuild = editingPoolBuild;
    }

    public void beginEdit(){
        editingPoolBuild = selectedPoolBuild;
        actionExecuteState.clearState();
    }

    public void beginCreate(){
        editingPoolBuild = new PoolBuild();
        editingPoolBuild.setProject(projectHome.getInstance());
        actionExecuteState.clearState();
    }

    public void save(){
        //TODO check
        if (!poolBuilds.contains(editingPoolBuild)) {
            poolBuilds.add(editingPoolBuild);
        }
        actionExecuteState.actionExecute();
    }
}
