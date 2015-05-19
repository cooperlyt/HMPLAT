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

    public static final String CREATE_TASK_NAME = "_CREATE";
    public static final String BUSINESS_VIEW_TASK_NAME = "_VIEW";

    public enum SubscribeType {
        TASK_INFO, TASK_COMPLETE, TASK_OPERATOR
    }

    public abstract String getTask();

    public abstract String getRegName();

    public abstract SubscribeType getType();

    public abstract BusinessDefine getBusinessDefine();

}
