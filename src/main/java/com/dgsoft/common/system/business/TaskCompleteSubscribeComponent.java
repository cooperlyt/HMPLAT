package com.dgsoft.common.system.business;

/** 任务完成组件
 * Created by cooper on 5/15/15.
 */
public interface TaskCompleteSubscribeComponent {

    /**验证数据 添加消息
     * 在确认面加载时执行
     */
    void valid();

    /** 是否可以完成本业务 不要添加消息
     *  在建立业务或完成任务时调用
     * @return true:成功 false: 不成功，返回fase系统会抛出异常
     */
    boolean isPass();


    /** 完成的动做
     *  在建立业务或完成任务时调用
     */
    void complete();

}
