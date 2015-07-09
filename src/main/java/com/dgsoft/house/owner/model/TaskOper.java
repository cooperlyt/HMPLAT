package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 9/16/14.
 */
@Entity
@Table(name = "TASK_OPER", catalog = "HOUSE_OWNER_RECORD")
public class TaskOper implements java.io.Serializable {

    public enum TaskType {
        CREATE, TASK, CHECK, MANAGER
    }

    public enum OperType{
        NEXT,BACK,TERMINATION,SUSPEND,CONTINUE,ASSIGN
    }



    private String id;
    private Long taskId;
    private OwnerBusiness ownerBusiness;
    private Date operTime;
    private String empCode;
    private String empName;
    private String taskName;
    private String comments;
    private OperType operType;
    private TaskType taskType;
    private boolean accept;

    public TaskOper() {
    }


    public TaskOper(Long taskId, OwnerBusiness ownerBusiness, String empCode, String empName) {
        this.taskId = taskId;
        this.ownerBusiness = ownerBusiness;
        this.operTime = new Date();
        this.empCode = empCode;
        this.empName = empName;
        this.taskName = "create";
        this.operType = OperType.NEXT;
        this.taskType = TaskType.CREATE;
        this.accept = true;
    }


    public TaskOper(Long taskId,TaskType taskType, OperType operType, OwnerBusiness ownerBusiness, String empCode, String empName, String taskName, String comments, boolean accept) {
        this.taskId = taskId;
        this.ownerBusiness = ownerBusiness;
        this.operTime = new Date();
        this.empCode = empCode;
        this.empName = empName;
        this.taskName = taskName;
        this.comments = comments;
        this.operType = operType;
        this.taskType = taskType;
        this.accept = accept;
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

    @Column(name = "TASK_ID",nullable = true)
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BUSINESS", nullable = false)
    @NotNull
    public OwnerBusiness getOwnerBusiness() {
        return ownerBusiness;
    }

    public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
        this.ownerBusiness = ownerBusiness;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OPER_TIME", nullable = false)
    @NotNull
    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    @Column(name = "EMP_CODE", nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    @Column(name = "EMP_NAME", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Column(name = "TASK_NAME", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Column(name = "COMMENTS", nullable = true, length = 500)
    @Size(max = 500)
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "OPER_TYPE", nullable = false, length = 20)
    @NotNull
    public OperType getOperType() {
        return operType;
    }

    public void setOperType(OperType operType) {
        this.operType = operType;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TASK_TYPE", nullable = false, length = 20)
    @NotNull
    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Column(name = "ACCEPT", nullable = false)
    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }
}
