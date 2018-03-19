package com.dgsoft.house.action;

import com.dgsoft.common.BatchOperData;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.Build;
import com.dgsoft.house.model.House;
import com.dgsoft.house.model.Project;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Role;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;

import javax.persistence.Transient;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-12
 * Time: 下午4:49
 */
@Name("projectHome")
@Role(name = "targetProjectHome")
public class ProjectHome extends HouseEntityHome<Project> {

    private static final String NUMBER_KEY = "PROJECT_ID";

//    private SetLinkList<Build> projectBuilds;

    @In
    private FacesMessages facesMessages;


    @Override
    protected Project createInstance() {
        return new Project(String.valueOf(HouseNumberBuilder.instance().useNumber(NUMBER_KEY)), new Date());
    }

    @Override
    protected boolean verifyRemoveAvailable() {
        if (getEntityManager().createQuery("select count(build.id) from Build build where build.project.id = :projectId", Long.class).
                setParameter("projectId", getInstance().getId()).getSingleResult() > 0) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "ProjectCantDelete");
            return false;
        } else
            return true;
    }

    private boolean moveChangeBuildName;

    private boolean moveChangeHouseAddress;

    private String moveBuildName;

    private String moveHouseAddress;


    public void moveOptionChaangeListener(){
        if (moveChangeBuildName){
            moveBuildName = getInstance().getDistrictName() + getInstance().getProjectName();
        }else{
            moveBuildName = null;
        }
        if (moveChangeHouseAddress){
            moveHouseAddress = getInstance().getAddress();
        }else{
            moveHouseAddress = null;
        }
    }

    public boolean isMoveChangeBuildName() {
        return moveChangeBuildName;
    }

    public void setMoveChangeBuildName(boolean moveChangeBuildName) {

        this.moveChangeBuildName = moveChangeBuildName;
    }

    public boolean isMoveChangeHouseAddress() {
        return moveChangeHouseAddress;
    }

    public void setMoveChangeHouseAddress(boolean moveChangeHouseAddress) {

        this.moveChangeHouseAddress = moveChangeHouseAddress;
    }

    public String getMoveBuildName() {
        return moveBuildName;
    }

    public void setMoveBuildName(String moveBuildName) {
        this.moveBuildName = moveBuildName;
    }

    public String getMoveHouseAddress() {
        return moveHouseAddress;
    }

    public void setMoveHouseAddress(String moveHouseAddress) {
        this.moveHouseAddress = moveHouseAddress;
    }

    @Override
    protected void initInstance(){
        super.initInstance();
        batchOperBuild = null;
    }

    private List<BatchOperData<Build>> batchOperBuild;

    @Transient
    public List<BatchOperData<Build>> getBatchOperBuild(){
        if (batchOperBuild == null){
            List<BatchOperData<Build>> result = new ArrayList<BatchOperData<Build>>();
            for(Build build: getInstance().getBuilds()){
                result.add(new BatchOperData<Build>(build,false));
            }

            Collections.sort(result, new Comparator<BatchOperData<Build>>() {
                @Override
                public int compare(BatchOperData<Build> o1, BatchOperData<Build> o2) {
                    if (o1.getData().getBuildNo() == null) {
                        return 0;
                    }
                    return o1.getData().getBuildNo().compareTo(o2.getData().getBuildNo());
                }
            });
            batchOperBuild = result;
        }



        return batchOperBuild;
    }


    public void refresh(){
        super.refresh();
        batchOperBuild = null;
    }

    @In
    private Map<String, String> messages;

    public void moveBuild(){
        ProjectHome ph = (ProjectHome)Component.getInstance("projectHome");

        House.AddressGenType addressGenType =  House.AddressGenType.valueOf(RunParam.instance().getStringParamValue("HouseAddressGen"));
        for(BatchOperData<Build> build: ph.getBatchOperBuild()) {
            Logging.getLog(getClass()).debug("move select build:" + build.getData().getId() + "-" + build.isSelected());
            if (build.isSelected()){
                Logging.getLog(getClass()).debug("move select build:" + build.getData().getId());
                getInstance().getBuilds().add(build.getData());
                build.getData().setProject(getInstance());
                ph.getInstance().getBuilds().remove(build);
                if (moveChangeBuildName && moveBuildName != null){


                    String buildName = moveBuildName;

                    if ((build.getData().getBuildNo() != null) && !build.getData().getBuildNo().trim().equals("")) {
                        buildName += build.getData().getBuildNo() + messages.get("MapIdentification_build");
                    }

                    if (build.getData().getBuildDevNumber() != null) {
                        buildName += build.getData().getBuildDevNumber() + messages.get("BuildBuildNameSuffix");
                    }
                    build.getData().setName(buildName);
                }

                if (moveChangeHouseAddress  && moveHouseAddress != null){
                    for(House house: build.getData().getHouses()){

                        String address = null;

                        if (House.AddressGenType.PA_BN_DO_HO.equals(addressGenType)) {

                            address = moveHouseAddress + build.getData().getBuildNo()  + "幢" + build.getData().getDoorNo() + " " + house.getHouseOrder();
                        }else if (House.AddressGenType.PA_UN_HO.equals(addressGenType)){
                            address = moveHouseAddress + house.getHouseUnitName() + " " + house.getHouseOrder();
                        }else if (House.AddressGenType.PA_DO_HO.equals(addressGenType)) {
                            address = moveHouseAddress  + build.getData().getDoorNo() + "号楼" + house.getHouseOrder();
                        }else
                            address = moveHouseAddress + " " + house.getHouseOrder();

                        house.setAddress(address);
                    }
                }

            }
        }

        update();
        ph.refresh();
    }
}
