package com.dgsoft.house;

import java.util.Date;

/**
 * Created by cooper on 10/11/14.
 */
public interface BaseProjectInfo extends BaseSectionInfo{

    public String getDeveloperName();

    public String getDeveloperCode();

    public String getProjectName();

    public String getProjectCode();

    public Date getCompleteDate();

    public String getBuildSize();


}
