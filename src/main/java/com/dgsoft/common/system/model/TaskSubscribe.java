package com.dgsoft.common.system.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 8/25/14.
 */

@Entity
@Table(name = "TASK_SUBSCRIBE", catalog = "DB_PLAT_SYSTEM")
public class TaskSubscribe implements java.io.Serializable {

    public enum SubscribeType {
        START_TASK, TASK_OPER;
    }

    private String id;
    private String taskName;

    private String regName;
    private SubscribeType type;
    private BusinessDefine businessDefine;
    private int priority;

    public TaskSubscribe() {
    }

    public TaskSubscribe(String id, BusinessDefine businessDefine) {
        this.id = id;
        this.businessDefine = businessDefine;
    }

    public TaskSubscribe(String id, String taskName, String regName, SubscribeType type, BusinessDefine businessDefine, int priority) {
        this.id = id;
        this.taskName = taskName;
        this.regName = regName;
        this.type = type;
        this.businessDefine = businessDefine;
        this.priority = priority;
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

    @Transient
    public String getTask(){
        if (SubscribeType.START_TASK.equals(getType())){
            return "";
        }else{
            return getTaskName();
        }
    }

    @Column(name = "REG_NAME", nullable = false , length = 200)
    @Size(max = 200)
    @NotNull
    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
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

    @Column(name = "PRIORITY",nullable = false)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
