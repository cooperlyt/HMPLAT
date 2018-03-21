package com.dgsoft.house.owner.helper;

import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.Project;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.total.data.ProjectTopTenData;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by cooper on 12/21/15.
 */
@Name("projectSaleTopTen")
public class ProjectSaleTopTen implements RestDataProvider {


    private enum TotalTimeType {
        YEAR, QUARTER, MONTH;

        public Date getBeginDate() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            switch (this) {

                case YEAR:
                    calendar.set(Calendar.MONTH, 0);

                    break;
                case QUARTER:
                    int currentMonth = calendar.get(Calendar.MONTH) + 1;
                    if (currentMonth >= 1 && currentMonth <= 3)
                        calendar.set(Calendar.MONTH, 0);
                    else if (currentMonth >= 4 && currentMonth <= 6)
                        calendar.set(Calendar.MONTH, 3);
                    else if (currentMonth >= 7 && currentMonth <= 9)
                        calendar.set(Calendar.MONTH, 4);
                    else if (currentMonth >= 10 && currentMonth <= 12)
                        calendar.set(Calendar.MONTH, 9);
                    break;
                case MONTH:
                    break;
            }

            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            return calendar.getTime();

        }

        public Date getEndDate() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            switch (this) {

                case YEAR:

                    calendar.add(Calendar.YEAR, 1);
                    calendar.set(Calendar.MONTH, 0);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    break;
                case QUARTER:
                    int currentMonth = calendar.get(Calendar.MONTH) + 1;
                    if (currentMonth >= 1 && currentMonth <= 3)
                        calendar.set(Calendar.MONTH, 4);
                    else if (currentMonth >= 4 && currentMonth <= 6)
                        calendar.set(Calendar.MONTH, 7);
                    else if (currentMonth >= 7 && currentMonth <= 9)
                        calendar.set(Calendar.MONTH, 10);
                    else if (currentMonth >= 10 && currentMonth <= 12) {
                        calendar.add(Calendar.YEAR, 1);
                        calendar.set(Calendar.MONTH, 0);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                    }
                    break;
                case MONTH:
                    calendar.add(Calendar.MONTH, 1);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    break;
            }
            calendar.add(Calendar.DAY_OF_MONTH, -1);

            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            return calendar.getTime();
        }
    }

    @RequestParameter
    private String totalTime;

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @Override
    public JSONObject getJsonData() {

        TotalTimeType type = TotalTimeType.valueOf(totalTime);

        List<ProjectTopTenData> counts = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.ProjectTopTenData(hb.afterBusinessHouse.projectCode,count(hb.id) )  from HouseBusiness hb where hb.ownerBusiness.status in ('RUNNING', 'COMPLETE', 'SUSPEND', 'MODIFYING') and hb.ownerBusiness.defineId =:defineId and hb.ownerBusiness.createTime >= :beginTime and hb.ownerBusiness.createTime <= :endTime group by hb.afterBusinessHouse.projectCode order by count(hb.id) desc ", ProjectTopTenData.class)
                .setParameter("defineId", RunParam.instance().getStringParamValue("NewHouseContractBizId"))
                .setParameter("beginTime", type.getBeginDate())
                .setParameter("endTime", type.getEndDate()).setMaxResults(10).getResultList();

        List<ProjectTopTenData> areas = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.ProjectTopTenData(hb.afterBusinessHouse.projectCode,sum(hb.afterBusinessHouse.houseArea) )  from HouseBusiness hb where hb.ownerBusiness.status in ('RUNNING', 'COMPLETE', 'SUSPEND', 'MODIFYING') and hb.ownerBusiness.defineId =:defineId and hb.ownerBusiness.createTime >= :beginTime and hb.ownerBusiness.createTime <= :endTime group by hb.afterBusinessHouse.projectCode order by sum(hb.afterBusinessHouse.houseArea) desc ", ProjectTopTenData.class)
                .setParameter("defineId", RunParam.instance().getStringParamValue("NewHouseContractBizId"))
                .setParameter("beginTime", type.getBeginDate())
                .setParameter("endTime", type.getEndDate()).setMaxResults(10).getResultList();

        List<ProjectTopTenData> moneys = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.ProjectTopTenData(hb.afterBusinessHouse.projectCode,sum(hb.houseContract.sumPrice))  from HouseBusiness hb where hb.ownerBusiness.status in ('RUNNING', 'COMPLETE', 'SUSPEND', 'MODIFYING') and hb.ownerBusiness.defineId =:defineId and hb.ownerBusiness.createTime >= :beginTime and hb.ownerBusiness.createTime <= :endTime group by hb.afterBusinessHouse.projectCode order by sum(hb.houseContract.sumPrice) desc ", ProjectTopTenData.class)
                .setParameter("defineId", RunParam.instance().getStringParamValue("NewHouseContractBizId"))
                .setParameter("beginTime", type.getBeginDate())
                .setParameter("endTime", type.getEndDate()).setMaxResults(10).getResultList();


        Set<String> projectIds = new HashSet<String>();

        for (ProjectTopTenData item : counts) {
            projectIds.add(item.getId());
        }

        for (ProjectTopTenData item : areas) {
            projectIds.add(item.getId());
        }

        for (ProjectTopTenData item : moneys) {
            projectIds.add(item.getId());
        }

        Map<String, String> projectNameMap = new HashMap<String, String>();

        if (projectIds.isEmpty()){
            return null;
        }

        for (Project project : houseEntityLoader.getEntityManager().createQuery("select p from Project p where p.id in (:pids)", Project.class)
                .setParameter("pids", projectIds).getResultList()) {
            projectNameMap.put(project.getId(), project.getName());
        }




        try {
            JSONArray countJsonArray = new JSONArray();
            for (ProjectTopTenData item : counts) {
                JSONObject jo = new JSONObject();

                jo.put("id", item.getId());
                jo.put("name",projectNameMap.get(item.getId()));
                jo.put("count",item.getCount());
                countJsonArray.put(jo);
            }

            JSONArray areaJsonArray = new JSONArray();
            for (ProjectTopTenData item : areas) {
                JSONObject jo = new JSONObject();

                jo.put("id", item.getId());
                jo.put("name",projectNameMap.get(item.getId()));
                jo.put("area",item.getMoneyOrArea().toString());
                areaJsonArray.put(jo);
            }

            JSONArray moneyJsonArray = new JSONArray();
            for (ProjectTopTenData item : moneys) {
                JSONObject jo = new JSONObject();

                jo.put("id", item.getId());
                jo.put("name",projectNameMap.get(item.getId()));
                jo.put("money",item.getMoneyOrArea().toString());
                moneyJsonArray.put(jo);
            }

            JSONObject result = new JSONObject();
            result.put("count",countJsonArray);
            result.put("area",areaJsonArray);
            result.put("money",moneyJsonArray);
            return result;
        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e.getMessage(),e);
            return null;
        }

    }
}
