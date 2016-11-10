package com.dgsoft.house.owner.action;

import com.dgsoft.common.DataFormat;
import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.business.BusinessDefineCache;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.OwnerEntityQuery;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.bpm.ManagedJbpmContext;
import org.jboss.seam.log.Logging;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;

import java.util.*;

/**
 * Created by cooper on 8/9/16.
 */
@Name("todayBusinessList")
@Scope(ScopeType.EVENT)
public class TodayBusinessList extends OwnerEntityQuery<OwnerBusiness> {

    public static class BusinessInfo {
        private OwnerBusiness ownerBusiness;

        private ProcessInstance processInstance;

        public BusinessInfo(OwnerBusiness ownerBusiness, ProcessInstance processInstance) {
            this.ownerBusiness = ownerBusiness;
            this.processInstance = processInstance;
        }

        public OwnerBusiness getOwnerBusiness() {
            return ownerBusiness;
        }

        public BusinessHouse getBusinessHouse() {
            if (ownerBusiness.getHouseBusinesses().size() == 1) {
                return ownerBusiness.getSingleHoues().getAfterBusinessHouse();
            } else {
                return null;
            }
        }

        private List<org.jbpm.taskmgmt.exe.TaskInstance> taskInstances;


        private List<org.jbpm.taskmgmt.exe.TaskInstance> getOpenTaskInstances(){
            if (taskInstances == null) {
                taskInstances = new ArrayList<org.jbpm.taskmgmt.exe.TaskInstance>();
                if (processInstance != null) {

                    List<org.jbpm.taskmgmt.exe.TaskInstance> result = new ArrayList<org.jbpm.taskmgmt.exe.TaskInstance>(processInstance.getTaskMgmtInstance().getTaskInstances());
                    Collections.sort(result, new Comparator<org.jbpm.taskmgmt.exe.TaskInstance>() {
                        @Override
                        public int compare(org.jbpm.taskmgmt.exe.TaskInstance o1, org.jbpm.taskmgmt.exe.TaskInstance o2) {
                            return o1.getCreate().compareTo(o2.getCreate());
                        }
                    });
                    for (org.jbpm.taskmgmt.exe.TaskInstance ti : result) {
                        if (ti.isOpen() && ti.isSignalling())
                            taskInstances.add(ti);
                    }

                }
            }
            return taskInstances;
        }

        public String getOpenTaskName(){
            String result = "";
            for (org.jbpm.taskmgmt.exe.TaskInstance ti: getOpenTaskInstances()){
                if (!"".equals(result)){
                    result += ",";
                }
                result += ti.getName();

            }
            return result;
        }

        public String getOpenEmployee(){
            String result = "";
            for (org.jbpm.taskmgmt.exe.TaskInstance ti: getOpenTaskInstances()){
                if (ti.getActorId() != null && !"".equals(ti.getActorId().trim())) {
                    if (!"".equals(result)) {
                        result += ",";
                    }
                    result += DictionaryWord.instance().getEmpNameById(ti.getActorId());
                }

            }
            return result;
        }


    }

    private static final String EJBQL = "select biz from OwnerBusiness biz where biz.status <> 'ABORT'";

    private static final String[] RESTRICTIONS = {
            "biz.applyTime >= lower(#{todayBusinessList.dateFrom})",
            "biz.applyTime <= lower(#{todayBusinessList.dateTo})"
    };

    private List<BusinessInfo> businessResult;

    @Override
    public void refresh() {
        businessResult = null;
    }

    public Date getDateFrom() {
        return DataFormat.getDayBeginTime(new Date());
    }

    public Date getDateTo() {
        return DataFormat.getDayEndTime(new Date());
    }

    public TodayBusinessList() {

        setEjbql(EJBQL);
        setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
        setRestrictionLogicOperator("and");
        setMaxResults(100);
    }

    public List<BusinessInfo> getBusinessResult() {
        if (businessResult == null) {
            getResultList();
            businessResult = new ArrayList<BusinessInfo>();
            for (OwnerBusiness ob : getResultList()) {
                ProcessInstance processInstance = null;
                if (BusinessInstance.BusinessSource.BIZ_CREATE.equals(ob.getSource()) && BusinessInstance.BusinessStatus.RUNNING.equals(ob.getStatus()) && ob.getDefineId() != null && !"".equals(ob.getDefineId().trim())) {
                    ProcessDefinition definition = null;
                    if (ob.getDefineVersion() != null) {
                        definition = ManagedJbpmContext.instance().getGraphSession().findProcessDefinition(BusinessDefineCache.instance().getDefine(ob.getDefineId()).getWfName(), ob.getDefineVersion());
                    } else {
                        definition = ManagedJbpmContext.instance().getGraphSession().findLatestProcessDefinition(BusinessDefineCache.instance().getDefine(ob.getDefineId()).getWfName());
                    }


                   // Logging.getLog(getClass()).debug("d:" + definition);
                    if (definition != null) {
                        processInstance = ManagedJbpmContext.instance().getProcessInstance(definition, ob.getId());
                    }
                }

               // Logging.getLog(getClass()).debug("pi:" + ob.getSource() + "|" + ob.getStatus() + "|" + ob.getDefineId());
                businessResult.add(new BusinessInfo(ob, processInstance));
            }
        }
        return businessResult;
    }
}
