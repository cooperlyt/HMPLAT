package com.dgsoft.house.owner.business;

import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.House;
import com.dgsoft.house.model.Project;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-10-01.
 * 根据房屋编号查询空间项目信息
 */
@Name("selectProjectByHouseCode")
public class SelectProjectByHouseCode {

    @In
    private OwnerBusinessHome ownerBusinessHome;


    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    private Project project;

    public Project getProject() {

        if (!ownerBusinessHome.getInstance().getHouseBusinesses().isEmpty()) {
            House house = houseEntityLoader.getEntityManager().find(House.class, ownerBusinessHome.getInstance().getHouseBusinesses().iterator().next().getHouseCode());
            if (house != null) {
                if (house.getBuild() != null) {
                    if (house.getBuild().getProject() != null) {
                        project=house.getBuild().getProject();
                    }
                }
            }
        }

        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }



}
