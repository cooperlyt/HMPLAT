package com.dgsoft.common.system.model;
// Generated Apr 28, 2013 11:02:59 AM by Hibernate Tools 4.0.0

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * ComplexVarSubscribe generated by hbm2java
 */
@Entity
@Table(name = "COMPLEX_VAR_SUBSCRIBE", uniqueConstraints = @UniqueConstraint(columnNames = {
        "VIEW_LOCATION", "COMPONENT_NAME", "WF_TASK", "WF_VER", "BUSINESS_DEFIN"}))
public class ComplexVarSubscribe implements java.io.Serializable {

    private String id;
    private BusinessDefine businessDefine;
    private String wfTask;
    private String wfVer;
    private String title;
    private String view;
    private String componentName;
    private int priority;
    private boolean request;
    private boolean readonly;

    public ComplexVarSubscribe() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUSINESS_DEFIN", nullable = false)
    @NotNull
    public BusinessDefine getBusinessDefine() {
        return this.businessDefine;
    }

    public void setBusinessDefine(BusinessDefine businessDefine) {
        this.businessDefine = businessDefine;
    }


    @Column(name = "WF_TASK", nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getWfTask() {
        return this.wfTask;
    }

    public void setWfTask(String wfSection) {
        this.wfTask = wfSection;
    }

    @Column(name = "WF_VER", nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getWfVer() {
        return this.wfVer;
    }

    public void setWfVer(String wfVer) {
        this.wfVer = wfVer;
    }

    @Column(name = "TITLE", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "VIEW_LOCATION", nullable = false, length = 200)
    @NotNull
    @Size(max = 200)
    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    @Column(name = "COMPONENT_NAME", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    @Column(name = "PRIORITY", nullable = false)
    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Column(name = "REQUEST",nullable = false)
    public boolean isRequest() {
        return request;
    }

    public void setRequest(boolean request) {
        this.request = request;
    }

    @Column(name = "READONLY",nullable = false)
    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }
}
