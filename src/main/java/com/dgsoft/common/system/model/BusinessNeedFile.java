package com.dgsoft.common.system.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cooper on 10/30/14.
 */
@Entity
@Table(name = "BUSINESS_NEED_FILE",catalog = "DB_PLAT_SYSTEM",uniqueConstraints = @UniqueConstraint(columnNames = {
        "NEED_FILE", "BUSINESS"}))
public class BusinessNeedFile implements java.io.Serializable{

    private String id;
    private int priority;
    private String taskName;
    private FileSubscribe fileSubscribe;
    private BusinessDefine businessDefine;

    public BusinessNeedFile() {
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

    @Column(name = "PRIORITY", nullable = false)
    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Column(name = "TASK_NAME",nullable = true,length = 200)
    @Size(max = 200)
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false,cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(nullable = false,name = "NEED_FILE")
    public FileSubscribe getFileSubscribe() {
        return fileSubscribe;
    }

    public void setFileSubscribe(FileSubscribe fileSubscribe) {
        this.fileSubscribe = fileSubscribe;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BUSINESS",nullable = false)
    public BusinessDefine getBusinessDefine() {
        return businessDefine;
    }

    public void setBusinessDefine(BusinessDefine businessDefine) {
        this.businessDefine = businessDefine;
    }
}
