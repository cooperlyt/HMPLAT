package com.dgsoft.house.owner.total;

import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.common.system.SystemEntityLoader;
import com.dgsoft.common.system.model.BusinessCategory;
import com.dgsoft.common.system.model.BusinessDefine;
import com.dgsoft.house.owner.OwnerEntityLoader;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.*;

/**
 * Created by cooper on 8/5/15.
 */
@Name("ownerBusinessTotal")
public class OwnerBusinessTotal {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private SystemEntityLoader systemEntityLoader;

    private Map<BusinessCategory,List<OwnerBusinessTotalData>> resultData;

    private void initData(){
        resultData = new HashMap<BusinessCategory, List<OwnerBusinessTotalData>>();
        List<OwnerBusinessTotalData> datas = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.OwnerBusinessTotalData(ob.defineId,count(ob.id)) from OwnerBusiness ob where ob.status = 'COMPLETE' or ob.status = 'COMPLETE_CANCEL' group by ob.defineId", OwnerBusinessTotalData.class).getResultList();
        for(OwnerBusinessTotalData data: datas){
            BusinessDefine define = systemEntityLoader.getEntityManager().find(BusinessDefine.class,data.getDefineId());
            if (define != null && define.isEnable()){
                data.setBusinessDefine(define);
                List<OwnerBusinessTotalData> defineDatas = resultData.get(define.getBusinessCategory());
                if (defineDatas == null){
                    defineDatas = new ArrayList<OwnerBusinessTotalData>();
                    resultData.put(define.getBusinessCategory(),defineDatas);
                }
                defineDatas.add(data);

            }
        }

        for(List<OwnerBusinessTotalData> item: resultData.values()){
            Collections.sort(item, new Comparator<OwnerBusinessTotalData>() {
                @Override
                public int compare(OwnerBusinessTotalData o1, OwnerBusinessTotalData o2) {
                    return Integer.valueOf(o1.getBusinessDefine().getPriority()).compareTo(o2.getBusinessDefine().getPriority());
                }
            });
        }
    }

    public List<Map.Entry<BusinessCategory,List<OwnerBusinessTotalData>>> getResultData(){
        if(resultData == null){
            initData();
        }
        List<Map.Entry<BusinessCategory,List<OwnerBusinessTotalData>>> result = new ArrayList<Map.Entry<BusinessCategory, List<OwnerBusinessTotalData>>>(resultData.entrySet());


        Collections.sort(result, new Comparator<Map.Entry<BusinessCategory, List<OwnerBusinessTotalData>>>() {
            @Override
            public int compare(Map.Entry<BusinessCategory, List<OwnerBusinessTotalData>> o1, Map.Entry<BusinessCategory, List<OwnerBusinessTotalData>> o2) {
                return Integer.valueOf(o1.getKey().getPriority()).compareTo(o2.getKey().getPriority());
            }
        });
        return result;
    }


}
