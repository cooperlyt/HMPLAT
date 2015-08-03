package com.dgsoft.common.system.action;

import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.common.system.model.BusinessReport;
import com.dgsoft.common.system.model.Report;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cooper on 8/3/15.
 */

@Name("businessPrintConfig")
public class BusinessPrintConfig {

    @In
    private BusinessDefineHome businessDefineHome;

    private List<Report> allReport;

    private String selectId;

    public String getSelectId() {
        return selectId;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public List<Report> getAllReport() {
        if (allReport == null){
            allReport = businessDefineHome.getEntityManager().createQuery("select report from Report report").getResultList();
        }
        return allReport;
    }

    public List<Report> getCanAddReports(){
        List<Report> result = new ArrayList<Report>();
        for(Report report: getAllReport()){
            boolean exists = false;
            for(BusinessReport br: businessDefineHome.getInstance().getBusinessReports()){
                if (br.getTaskName().equals(businessDefineHome.getTaskName()) && br.getReport().getId().equals(report.getId())){
                    exists = true;
                    break;
                }
            }
            if (!exists){
                result.add(report);
            }
        }
        return result;
    }

    public void addReport(){
        businessDefineHome.getInstance().getBusinessReports().add(new BusinessReport(getMaxPri() + 1,businessDefineHome.getInstance(),businessDefineHome.getEntityManager().find(Report.class,selectId),businessDefineHome.getTaskName()));
        businessDefineHome.update();
    }

    public void removeReport(){
        for(BusinessReport br: businessDefineHome.getInstance().getBusinessReports()){
            if (br.getTaskName().equals(businessDefineHome.getTaskName()) &&
                    br.getReport().getId().equals(selectId)){
                businessDefineHome.getInstance().getBusinessReports().remove(br);
                businessDefineHome.update();
                return;
            }
        }
    }

    public List<BusinessReport> getBusinessReportList(){
        List<BusinessReport> result = new ArrayList<BusinessReport>();
        for(BusinessReport br: businessDefineHome.getInstance().getBusinessReports()){
            if (br.getTaskName().equals(businessDefineHome.getTaskName())){
                result.add(br);
            }
        }
        Collections.sort(result, OrderBeanComparator.getInstance());
        return result;
    }


    private int getMaxPri(){
        int result = 0;
        for(BusinessReport businessReport: getBusinessReportList())
            if (businessReport.getPriority() > result){
                result = businessReport.getPriority();
            }
        return result;
    }

    public void up(){
        List<BusinessReport> businessReports = getBusinessReportList();
        for (int i = 0 ; i< businessReports.size(); i++){
            if (businessReports.get(i).getReport().getId().equals(selectId)){

                if ((i - 1) >= 0){
                    int cup = businessReports.get(i).getPriority();

                    businessReports.get(i).setPriority(businessReports.get(i - 1).getPriority());
                    businessReports.get(i-1).setPriority(cup);
                    businessDefineHome.update();
                }


                return;
            }
        }
    }

    public void down(){
        List<BusinessReport> businessReports = getBusinessReportList();
        for (int i = 0 ; i< businessReports.size(); i++){
            if (businessReports.get(i).getReport().getId().equals(selectId)){

                if ((i + 1) < businessReports.size()){
                    int cup = businessReports.get(i).getPriority();

                    businessReports.get(i).setPriority(businessReports.get(i + 1).getPriority());
                    businessReports.get(i+1).setPriority(cup);
                    businessDefineHome.update();
                }


                return;
            }
        }
    }

}
