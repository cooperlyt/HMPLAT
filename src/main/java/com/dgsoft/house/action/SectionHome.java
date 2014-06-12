package com.dgsoft.house.action;

import com.dgsoft.common.SetLinkList;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.Project;
import com.dgsoft.house.model.Section;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-11
 * Time: 下午3:24
 */
@Name("sectionHome")
public class SectionHome extends HouseEntityHome<Section>{


    private SetLinkList<Project> projects;

    public SetLinkList<Project> getProjects() {
        if (projects == null){
            projects = new SetLinkList<Project>(getInstance().getProjects());
        }
        return projects;
    }


}
