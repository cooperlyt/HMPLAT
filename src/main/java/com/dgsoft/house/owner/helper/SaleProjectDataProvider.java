package com.dgsoft.house.owner.helper;

import com.dgsoft.common.utils.seam.MultiOperatorEntityQuery;
import com.dgsoft.common.utils.seam.RestrictionGroup;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.Project;
import com.dgsoft.house.owner.OwnerEntityQuery;
import com.dgsoft.house.owner.total.data.SaleProjectData;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cooper on 12/20/15.
 */
@Name("saleProjectDataProvider")
public class SaleProjectDataProvider extends MultiOperatorEntityQuery<SaleProjectData> implements RestDataProvider{


    private final static int MAX_RESULT_COUNT = 20;

    private final static String EJBQL = "select new com.dgsoft.house.owner.total.data.SaleProjectData(p.projectCode,sum(p.projectSellInfo.houseCount),sum(p.projectSellInfo.buildCount),sum(p.projectSellInfo.area),max(p.ownerBusiness.regTime)) from BusinessProject p where p.ownerBusiness.status in ('COMPLETE','MODIFYING')";

    private static final String[] RESTRICTIONS_DISTRICT = {
            "p.districtCode = #{saleProjectDataProvider.districtCode}"
    };

    private static final String[] RESTRICTIONS = {
            "p.id = #{saleProjectDataProvider.searchKey}",
            "p.name like lower(concat('%',concat(#{saleProjectDataProvider.searchKey},'%')))",
            "p.address like lower(concat('%',concat(#{saleProjectDataProvider.searchKey},'%')))",
            "p.developerName like lower(concat('%',concat(#{saleProjectDataProvider.searchKey},'%')))",
            "p.sectionName like lower(concat('%',concat(#{saleProjectDataProvider.searchKey},'%')))"
    };


    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @RequestParameter
    private String searchKey;

    @RequestParameter
    private String districtCode;


    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public SaleProjectDataProvider() {
        setMaxResults(MAX_RESULT_COUNT);
        setEjbql(EJBQL);
        setUseWildcardAsCountQuerySubject(false);
        setOrder(" max(p.ownerBusiness.regTime) desc");
        setGroupBy("p.projectCode");
        RestrictionGroup mainRestriction = new RestrictionGroup("and", Arrays.asList(RESTRICTIONS_DISTRICT));
        mainRestriction.getChildren().add(new RestrictionGroup("or",Arrays.asList(RESTRICTIONS)));
        setRestrictionGroup(mainRestriction);
    }





    @RequestParameter
    @Override
    public void setFirstResult(Integer firstResult){
        super.setFirstResult(firstResult);
    }

    @Override
    public JSONObject getJsonData() {

        JSONObject result = new JSONObject();
        try {


            Set<String> resultIds = new HashSet<String>(MAX_RESULT_COUNT);
            for(SaleProjectData data: getResultList()){
                resultIds.add(data.getId());
            }

            List<Project> projects = houseEntityLoader.getEntityManager().createQuery("select p from Project p left join fetch p.developer where p.id in (:ids)")
                    .setParameter("ids",resultIds).getResultList();


            JSONArray dataArray = new JSONArray();
            for(SaleProjectData data: getResultList()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",data.getId());
                jsonObject.put("houseCount", data.getHouseCount());
                jsonObject.put("houseArea", data.getHouseArea());
                jsonObject.put("buildCount",data.getBuildCount());
                jsonObject.put("regDate",data.getRegDate().getTime());

                for(Project project: projects){
                    if (project.getId().equals(data.getId())){
                        jsonObject.put("name",project.getName());
                        jsonObject.put("address",project.getAddress());
                        jsonObject.put("developerCode",project.getDeveloper().getId());
                        jsonObject.put("developerName",project.getDeveloper().getName());

                        break;
                    }
                }


                dataArray.put(jsonObject);
            }

            result.put("datas",dataArray);
            result.put("pageSize",MAX_RESULT_COUNT);
            result.put("recordCount",getResultCount());
            result.put("nextExists",isNextExists());
            result.put("pageCount",getPageCount());
            result.put("previousExists",isPreviousExists());
            return result;
        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e.getMessage(), e);
            return null;
        }
    }


    @Override
    protected String getPersistenceContextName() {
        return "ownerEntityManager";
    }


}
