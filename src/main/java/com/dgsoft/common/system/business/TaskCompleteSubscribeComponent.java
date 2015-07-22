package com.dgsoft.common.system.business;

/**
 * Created by cooper on 5/15/15.
 */
public interface TaskCompleteSubscribeComponent {

    //验证数据 添加消息
    public abstract void valid();

    //是否可以完成本业务 不要添加消息
    public abstract boolean isPass();

    //完成的动做
    public abstract void complete();

}
