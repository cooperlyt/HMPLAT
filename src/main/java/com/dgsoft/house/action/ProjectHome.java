package com.dgsoft.house.action;

import com.dgsoft.common.SetLinkList;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.Build;
import com.dgsoft.house.model.Project;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-12
 * Time: 下午4:49
 */
@Name("projectHome")
public class ProjectHome extends HouseEntityHome<Project> {


    private SetLinkList<Build> projectBuilds;

    @DataModel
    public SetLinkList<Build> getProjectBuilds() {
        if (projectBuilds == null) {
            projectBuilds = new SetLinkList<Build>(getInstance().getBuilds());
        }
        return projectBuilds;
    }

    @DataModelSelection
    private Build build;

    private Build editingBuild;

    public void beginEditBuild() {
        editingBuild = build;
        actionExecuteState.clearState();
    }

    public void beginCreateBuild() {
        editingBuild = new Build();
        actionExecuteState.clearState();
    }

    public Build getEditingBuild() {

        return editingBuild;
    }

    public void setEditingBuild(Build editingBuild) {
        this.editingBuild = editingBuild;
    }

    public void saveBuild() {
        if (!projectBuilds.contains(editingBuild)) {
            projectBuilds.add(editingBuild);
        }
        actionExecuteState.actionExecute();
    }

    @Override
    protected Project createInstance() {
        return new Project(Project.ProjectState.BUILDING);
    }


    @In(create = true)
    private DeveloperHome developerHome;

    @Override
    protected void initInstance() {
        super.initInstance();
        developerHome.setId(getInstance().getId());
    }

    @Override
    protected boolean wire() {
        getInstance().setDeveloper(developerHome.getInstance());
        return true;
    }

}
