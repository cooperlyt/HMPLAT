package com.dgsoft.house;

import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 14-6-6
 * Time: 下午4:33
 */
@Name("sectionSearchCondition")
public class SectionSearchCondition {

    private String districtId;

    private String sectionName;

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
