package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityQuery;
import com.dgsoft.house.model.Section;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import javax.persistence.TypedQuery;
import java.util.Arrays;

/**
 * Created by cooper on 6/11/15.
 */
@Name("sectionSelectList")
@Scope(ScopeType.CONVERSATION)
public class SectionSelectList extends HouseEntityQuery<Section> {

    private static final String EJBQL = "select section from Section section";

    private static final int PAGE_COUNT = 12;


    public SectionSelectList() {
        setEjbql(EJBQL);

        setOrderColumn("section.createTime");
        setOrderDirection("desc");
        setMaxResults(PAGE_COUNT);
    }

    public void moreResult(){
        if (getMaxResults() == null){
            setMaxResults(PAGE_COUNT);
        }else{
            setMaxResults(getMaxResults() + PAGE_COUNT);
        }
    }

    protected void setSql(){
        String sql = "";
        if ((getDistrictId() != null ) && !getDistrictId().trim().equals("")) {
            sql += " where section.district.id = '" + getDistrictId().trim() + "' ";
        }
        if ((getSearchKey() != null) && !getSearchKey().trim().equals("")){
            if ("".equals(sql)){
                sql = " where ";
            }else {
                sql += " and ";
            }

            sql += "( (lower(section.pyCode) like lower(concat('%','" + getSearchKey().trim()
                    + "','%')) ) or (lower(section.name) like lower(concat('%','" + getSearchKey().trim() +"','%'))))";
        }
        setEjbql(EJBQL + sql);
        setMaxResults(PAGE_COUNT);

    }

    private String districtId;

    private String searchKey;

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
        setSql();
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
        setSql();
    }
}
