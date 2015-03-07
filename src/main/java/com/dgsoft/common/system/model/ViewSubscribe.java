package com.dgsoft.common.system.model;

import com.dgsoft.common.system.business.Subscribe;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 3/2/15.
 */
@Entity
@Table(name = "VIEW_SUBSCRIBE", catalog = "DB_PLAT_SYSTEM")
public class ViewSubscribe implements java.io.Serializable, Subscribe {

    private String id;
    private String regName;
    private int priority;
    private SubscribeGroup subscribeGroup;

    public ViewSubscribe() {
    }

    public ViewSubscribe(String id, String regName, int priority, SubscribeGroup subscribeGroup) {
        this.id = id;
        this.regName = regName;
        this.priority = priority;
        this.subscribeGroup = subscribeGroup;
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


    @Override
    @Transient
    public String getTask() {
        return getSubscribeGroup().getTask();
    }

    @Override
    @Column(name = "REG_NAME", nullable = false , length = 200)
    @Size(max = 200)
    @NotNull
    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }

    @Override
    @Transient
    public SubscribeType getType() {
        return getSubscribeGroup().getType();
    }

    @Override
    @Transient
    public BusinessDefine getBusinessDefine() {
        return getSubscribeGroup().getBusinessDefine();
    }


    @Override
    @Column(name = "PRIORITY",nullable = false)
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "GROUP_ID", nullable = false)
    @NotNull
    public SubscribeGroup getSubscribeGroup() {
        return subscribeGroup;
    }

    public void setSubscribeGroup(SubscribeGroup subscribeGroup) {
        this.subscribeGroup = subscribeGroup;
    }
}