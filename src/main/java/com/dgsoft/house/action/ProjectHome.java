package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.Project;
import org.jboss.seam.annotations.In;
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


    @In(create = true)
    private DeveloperHome developerHome;

    @Override
    protected void initInstance(){
        super.initInstance();
        developerHome.setId(getInstance().getId());
    }

    @Override
    protected boolean wire(){
        getInstance().setDeveloper(developerHome.getInstance());
        return true;
    }

}
