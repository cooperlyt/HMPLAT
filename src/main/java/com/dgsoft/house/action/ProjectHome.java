package com.dgsoft.house.action;

import com.dgsoft.common.SetLinkList;
import com.dgsoft.common.helper.ActionExecuteState;
import com.dgsoft.common.system.NumberBuilder;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.Build;
import com.dgsoft.house.model.Project;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;

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

    @In
    private FacesMessages facesMessages;

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

    public void removeBuild(){

        if (getEntityManager().contains(build)){
            if (getEntityManager().createQuery("select count(house.id) from House house where house.build.id = :buildId",Long.class).
                    setParameter("buildId",build.getId()).getSingleResult() > 0){
                facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"BuildHaveHouseCantDel");
                return;
            }
        }
        projectBuilds.remove(build);

    }

    public void beginEditBuild() {
        editingBuild = build;
        ActionExecuteState.instance().clearState();
    }

    public void beginCreateBuild() {
        editingBuild = new Build(getInstance());
        ActionExecuteState.instance().clearState();
    }

    public Build getEditingBuild() {
        return editingBuild;
    }

    public void setEditingBuild(Build editingBuild) {
        this.editingBuild = editingBuild;
    }

    private void addBuildMBBConflictMessages(){
        facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"ConflictMBB");
        ActionExecuteState.instance().setLastState("MBBConfict");
    }

    private void addBuildPBConflictMessages(){
        facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"ConfilicPB");
        ActionExecuteState.instance().setLastState("MBBConfict");
    }

    public void saveBuild() {
        if (getEntityManager().createQuery("select count(build.id) from Build build " +
                "where build.mapNumber = :mapNumber and build.blockNo = :blockNumber and " +
                "build.buildNo = :buildNumber and build.id <> :buildId",Long.class)
                .setParameter("mapNumber",editingBuild.getMapNumber())
                .setParameter("blockNumber",editingBuild.getBlockNo())
                .setParameter("buildNumber",editingBuild.getBuildNo())
                .setParameter("buildId",editingBuild.getId()).getSingleResult() > 0){
            addBuildMBBConflictMessages();
            return;
        }
        for (Build build: projectBuilds){
            if ((build != editingBuild) && (build.getMapNumber().equals(editingBuild.getMapNumber()))
                    && (build.getBlockNo().equals(editingBuild.getBlockNo()))
                    && (build.getBuildNo().equals(editingBuild.getBuildNo()))){
                addBuildMBBConflictMessages();
                return;
            }
            if ( (build != editingBuild) && build.getBuildNo().equals(editingBuild.getBuildNo())){
                addBuildPBConflictMessages();
                return;
            }
        }

        if (isManaged()){
            if (getEntityManager().createQuery("select count(build.id) from Build build " +
                    "where build.project.id = :projectId and " +
                    "build.buildNo = :buildNumber and build.id <> :buildId",Long.class)
                    .setParameter("projectId",getInstance().getId())
                    .setParameter("buildNumber",editingBuild.getBuildNo())
                    .setParameter("buildId",editingBuild.getId()).getSingleResult() > 0){
                addBuildPBConflictMessages();
                return;
            }
        }




        if (!projectBuilds.contains(editingBuild)) {
            projectBuilds.add(editingBuild);
        }
        editingBuild = null;
        ActionExecuteState.instance().actionExecute();
    }

    public void cancelBuildEdit(){
        editingBuild = null;
    }


    @Override
    protected Project createInstance() {
        return new Project(((SectionHome)Component.getInstance("sectionHome")).getInstance(), numberBuilder.getSampleNumber("PROJECT_NUMBER") ,Project.ProjectState.BUILDING,new Date());
    }


    @In(create = true)
    private DeveloperHome developerHome;

    @In
    private HouseCodeHelper houseCodeHelper;

    @Override
    protected void initInstance() {
        super.initInstance();
        developerHome.setId(getInstance().getId());
    }

    public boolean wireProject() {
        getInstance().setDeveloper(developerHome.getInstance());
        for(Build build: projectBuilds){
          if (!getEntityManager().contains(build)){
                houseCodeHelper.genBuildCode(build);
          }
        }

        return true;
    }

}
