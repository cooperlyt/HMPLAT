package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.Project;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-12
 * Time: 下午4:49
 */
@Name("projectHome")
public class ProjectHome extends HouseEntityHome<Project>{

    @Override
    protected Project createInstance(){
        return new Project(Project.ProjectState.BUILDING);
    }

}
