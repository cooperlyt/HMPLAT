package com.dgsoft.common.system.business;

import com.dgsoft.common.OrderModel;
import com.dgsoft.common.system.model.BusinessDefine;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 3/2/15.
 */
public interface Subscribe extends OrderModel {

    public enum SubscribeType {
        START_TASK, TASK_OPER, BUSINESS_VIEW
    }

    public abstract String getTask();

    public abstract String getRegName();

    public abstract SubscribeType getType();

    public abstract BusinessDefine getBusinessDefine();

}
