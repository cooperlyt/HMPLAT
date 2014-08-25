package com.dgsoft.common.system.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 8/25/14.
 */

@Entity
@Table(name = "TASK_SUBSCRiBE", catalog = "DB_PLAT_SYSTEM")
public class TaskSubscribe implements java.io.Serializable {

    public enum SubscribeType {
        START_TASK, TASK_OPER;
    }

    private String id;
    private String taskName;
    private String operPage;
    private String componentName;
    private SubscribeType type;
    private BusinessDefine businessDefine;

    public TaskSubscribe() {
    }

    public TaskSubscribe(String id, BusinessDefine businessDefine) {
        this.id = id;
        this.businessDefine = businessDefine;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "TASK_NAME", nullable = true, length = 200)
    @Size(max = 200)
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Column(name = "OPER_PAGE", nullable = true, length = 200)
    @Size(max = 200)
    public String getOperPage() {
        return operPage;
    }

    public void setOperPage(String operPage) {
        this.operPage = operPage;
    }

    @Column(name = "COMPONENT_NAME", nullable = true, length = 200)
    @Size(max = 200)
    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false, length = 10)
    @NotNull
    public SubscribeType getType() {
        return type;
    }

    public void setType(SubscribeType type) {
        this.type = type;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEFINE_ID", nullable = false)
    public BusinessDefine getBusinessDefine() {
        return businessDefine;
    }

    public void setBusinessDefine(BusinessDefine businessDefine) {
        this.businessDefine = businessDefine;
    }
}
