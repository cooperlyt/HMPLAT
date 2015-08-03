package com.dgsoft.common.system.model;

import com.dgsoft.common.OrderModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 8/3/15.
 */
@Entity
@Table(name = "BUSINESS_REPROT",catalog = "DB_PLAT_SYSTEM", uniqueConstraints = {@UniqueConstraint(columnNames = {"BUSINESS","TASK_NAME","REPORT"})})
public class BusinessReport implements java.io.Serializable, OrderModel {

    private BusinessDefine businessDefine;
    private Report report;
    private String id;
    private String taskName;
    private int priority;

    public BusinessReport() {
    }

    public BusinessReport(int priority, BusinessDefine businessDefine, Report report, String taskName) {
        this.priority = priority;
        this.businessDefine = businessDefine;
        this.report = report;
        this.taskName = taskName;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BUSINESS" , nullable = false)
    @NotNull
    public BusinessDefine getBusinessDefine() {
        return businessDefine;
    }

    public void setBusinessDefine(BusinessDefine businessDefine) {
        this.businessDefine = businessDefine;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "REPORT", nullable = false)
    @NotNull
    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "TASK_NAME",nullable = false,length = 100)
    @NotNull
    @Size(max = 100)
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Column(name = "PRIORITY", nullable = false)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
