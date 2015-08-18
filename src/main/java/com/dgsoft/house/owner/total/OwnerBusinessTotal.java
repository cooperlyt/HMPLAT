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

    //private Map<BusinessCategory,List<OwnerBusinessTotalData>> resultData;
    private List<OwnerBusinessTotalData> resultData;

    private void initData(){

        resultData = new ArrayList<OwnerBusinessTotalData>();
        List<OwnerBusinessTotalData> result = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.OwnerBusinessTotalData(ob.defineId,count(ob.id)) from OwnerBusiness ob where ob.status = 'COMPLETE' group by ob.defineId", OwnerBusinessTotalData.class).getResultList();
        for(OwnerBusinessTotalData data: result){
            BusinessDefine define = systemEntityLoader.getEntityManager().find(BusinessDefine.class,data.getDefineId());
            if (define != null && define.isEnable()){
                data.setBusinessDefine(define);
                resultData.add(data);
//                List<OwnerBusinessTotalData> defineDatas = resultData.get(define.getBusinessCategory());
//                if (defineDatas == null){
//                    defineDatas = new ArrayList<OwnerBusinessTotalData>();
//                    resultData.put(define.getBusinessCategory(),defineDatas);
//                }
//                defineDatas.add(data);

            }
        }
//
//        for(List<OwnerBusinessTotalData> item: resultData.values()){
//            Collections.sort(item, new Comparator<OwnerBusinessTotalData>() {
//                @Override
//                public int compare(OwnerBusinessTotalData o1, OwnerBusinessTotalData o2) {
//                    return Integer.valueOf(o1.getBusinessDefine().getPriority()).compareTo(o2.getBusinessDefine().getPriority());
//                }
//            });
//        }
        Collections.sort(resultData, new Comparator<OwnerBusinessTotalData>() {
            @Override
            public int compare(OwnerBusinessTotalData o1, OwnerBusinessTotalData o2) {
                return o1.getDefineId().compareTo(o2.getDefineId());
            }
        });
    }


    public List<OwnerBusinessTotalData> getResultData(){
        if(resultData == null){
            initData();
        }

        return resultData;
    }


}
