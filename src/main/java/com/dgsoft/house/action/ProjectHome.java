package com.dgsoft.house.action;

import com.dgsoft.common.SetLinkList;
import com.dgsoft.common.system.NumberBuilder;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.Build;
import com.dgsoft.house.model.Project;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-12
 * Time: 下午4:49
 */
@Name("projectHome")
public class ProjectHome extends HouseEntityHome<Project> {

    @In(create = true)
    private NumberBuilder numberBuilder;

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
        return new Project(numberBuilder.getSampleNumber("PROJECT_NUMBER") ,Project.ProjectState.BUILDING,new Date());
    }


    @In(create = true)
    private DeveloperHome developerHome;

    @In(create = true)
    private SectionHome sectionHome;

    @Override
    protected void initInstance() {
        super.initInstance();
        developerHome.setId(getInstance().getId());
    }

    @Override
    protected boolean wire() {
        getInstance().setDeveloper(developerHome.getInstance());
        getInstance().setSection(sectionHome.getInstance());
        return true;
    }

}
