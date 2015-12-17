package com.dgsoft.common.system.model;

import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.common.OrderModel;
import com.google.common.collect.Iterators;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.criteria.Order;
import javax.swing.tree.TreeNode;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by cooper on 10/30/14.
 */
@Entity
@Table(name = "BUSINESS_NEED_FILE",catalog = "DB_PLAT_SYSTEM")
public class BusinessNeedFile implements java.io.Serializable, OrderModel{

    public enum NeedFileNodeFile{
       ALL,ANYONE,CHILDREN,OTHER
    }

    private String id;
    private int priority;
    private String taskName;
    private BusinessDefine businessDefine;
    private BusinessNeedFile parent;
    private Set<BusinessNeedFile> children = new HashSet<BusinessNeedFile>(0);
    private NeedFileNodeFile type;
    private String name;
    private String description;
    private String condition;


    public BusinessNeedFile() {
    }

    public BusinessNeedFile(BusinessDefine businessDefine, NeedFileNodeFile type, BusinessNeedFile parent,int priority) {
        this.businessDefine = businessDefine;
        this.type = type;
        this.parent = parent;
        this.priority = priority;
    }

    public BusinessNeedFile(String taskName, BusinessDefine businessDefine, String name, NeedFileNodeFile type) {
        this.taskName = taskName;
        this.businessDefine = businessDefine;
        this.name = name;
        this.type = type;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Size(max = 32)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    @Column(name = "PRIORITY", nullable = false)
    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Column(name = "TASK_NAME",nullable = true,length = 200)
    @Size(max = 200)
    public String getTaskNames() {
        return taskName;
    }

    public void setTaskNames(String taskName) {
        this.taskName = taskName;
    }

    @Transient
    public List<String> getTaskNameList(){
        if (getTaskNames() == null){
            return new ArrayList<String>(0);
        }
        return Arrays.asList(getTaskNames().split(","));
    }


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BUSINESS",nullable = false)
    public BusinessDefine getBusinessDefine() {
        return businessDefine;
    }

    public void setBusinessDefine(BusinessDefine businessDefine) {
        this.businessDefine = businessDefine;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PARENT_ID", nullable = true)
    public BusinessNeedFile getParent() {
        return parent;
    }

    public void setParent(BusinessNeedFile parent) {
        this.parent = parent;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL},orphanRemoval = true,mappedBy = "parent")
    public Set<BusinessNeedFile> getChildren() {
        return children;
    }

    public void setChildren(Set<BusinessNeedFile> children) {
        this.children = children;
    }

    @Transient
    public List<BusinessNeedFile> getChildrenList(){
        List<BusinessNeedFile> result = new ArrayList<BusinessNeedFile>(getChildren());
        Collections.sort(result,OrderBeanComparator.getInstance());
        return result;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE",nullable = false,length = 20)
    @NotNull
    public NeedFileNodeFile getType() {
        return type;
    }

    public void setType(NeedFileNodeFile type) {
        this.type = type;
    }

    @Column(name = "NAME",nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="DESCRIPTION", nullable = true, length = 200)
    @Size(max = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="_CONDITION",length = 500)
    @Size(max = 500)
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }




}
