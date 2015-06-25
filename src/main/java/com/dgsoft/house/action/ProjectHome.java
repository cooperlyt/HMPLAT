package com.dgsoft.house.action;

import com.dgsoft.common.SetLinkList;
import com.dgsoft.common.helper.ActionExecuteState;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.Build;
import com.dgsoft.house.model.Project;
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

    private static final String NUMBER_KEY = "PROJECT_ID";

//    private SetLinkList<Build> projectBuilds;

    @In
    private FacesMessages facesMessages;




//    @DataModel
//    public SetLinkList<Build> getProjectBuilds() {
//        if (projectBuilds == null) {
//            projectBuilds = new SetLinkList<Build>(getInstance().getBuilds());
//        }
//        return projectBuilds;
//    }

//    @DataModelSelection
//    private Build build;
//
//    private Build editingBuild;

//    public void removeProjectBuild() {
//        if (removeBuild())
//            update();
//    }
//
//    public boolean removeBuild() {
//
//        if (getEntityManager().contains(build)) {
//            if (getEntityManager().createQuery("select count(house.id) from House house where house.build.id = :buildId", Long.class).
//                    setParameter("buildId", build.getId()).getSingleResult() > 0) {
//                facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "BuildHaveHouseCantDel");
//                return false;
//            }
//        }
//        projectBuilds.remove(build);
//        return true;
//
//    }


//
//    public void saveBuildAndUpdateProject() {
//
//        if (!getEntityManager().contains(editingBuild)) {
//            houseCodeHelper.genBuildCode(editingBuild);
//        }
//        saveBuild();
//        update();
//    }

//    public void saveBuild() {
//        if (getEntityManager().createQuery("select count(build.id) from Build build " +
//                "where build.mapNumber = :mapNumber and build.blockNo = :blockNumber and " +
//                "build.buildNo = :buildNumber and build.id <> :buildId", Long.class)
//                .setParameter("mapNumber", editingBuild.getMapNumber())
//                .setParameter("blockNumber", editingBuild.getBlockNo())
//                .setParameter("buildNumber", editingBuild.getBuildNo())
//                .setParameter("buildId", editingBuild.getId()).getSingleResult() > 0) {
//            addBuildMBBConflictMessages();
//            return;
//        }
//        for (Build build : projectBuilds) {
//            if ((build != editingBuild) && (build.getMapNumber().equals(editingBuild.getMapNumber()))
//                    && (build.getBlockNo().equals(editingBuild.getBlockNo()))
//                    && (build.getBuildNo().equals(editingBuild.getBuildNo()))) {
//                addBuildMBBConflictMessages();
//                return;
//            }
//            if ((build != editingBuild) && build.getBuildNo().equals(editingBuild.getBuildNo())) {
//                addBuildPBConflictMessages();
//                return;
//            }
//        }
//
//        if (isManaged()) {
//            if (getEntityManager().createQuery("select count(build.id) from Build build " +
//                    "where build.project.id = :projectId and " +
//                    "build.buildNo = :buildNumber and build.id <> :buildId", Long.class)
//                    .setParameter("projectId", getInstance().getId())
//                    .setParameter("buildNumber", editingBuild.getBuildNo())
//                    .setParameter("buildId", editingBuild.getId()).getSingleResult() > 0) {
//                addBuildPBConflictMessages();
//                return;
//            }
//        }
//
//        if (!projectBuilds.contains(editingBuild)) {
//            projectBuilds.add(editingBuild);
//        }
//        editingBuild = null;
//        ActionExecuteState.instance().actionExecute();
//    }
//
//    public void cancelBuildEdit() {
//        editingBuild = null;
//    }


    @Override
    protected Project createInstance() {
        return new Project(String.valueOf(HouseNumberBuilder.instance().useNumber(NUMBER_KEY)), new Date());
    }


//    @In(create = true)
//    private DeveloperHome developerHome;
//
//    @In
//    private HouseCodeHelper houseCodeHelper;

//    @Override
//    protected void initInstance() {
//        super.initInstance();
//        projectBuilds = null;
//    }

    @Override
    protected boolean verifyRemoveAvailable() {
        if (getEntityManager().createQuery("select count(build.id) from Build build where build.project.id = :projectId", Long.class).
                setParameter("projectId", getInstance().getId()).getSingleResult() > 0) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "ProjectCantDelete");
            return false;
        } else
            return true;
    }

//    public boolean wireProject() {
//        getInstance().setDeveloper(developerHome.getInstance());
//        for (Build build : projectBuilds) {
//            if (!getEntityManager().contains(build)) {
//                houseCodeHelper.genBuildCode(build);
//            }
//        }
//
//        return true;
//    }

}
