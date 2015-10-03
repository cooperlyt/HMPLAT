package com.dgsoft.common.system.business;

/** 订阅者组件，需要在配置文件中注册
 * Created by cooper on 8/25/14.
 */
public interface TaskSubscribeComponent {


    /** 初始化
     * 在开始加载所有订阅者时执行，执行时本订阅可能还没显示
     */
    void initSubscribe();



    /** 在保存后验调用，验证是否正确 可添加消息
     *在订阅者都保存完毕后执行 ，在确认页面加载时执行。
     */
    void validSubscribe();



    /** 是否通过 不要添加消息
     *  在建立业务或完成任务时调用
     * @return true:成功 false: 不成功，返回fase系统会抛出异常
     */
    boolean isPass();


    /** 保存 可添加消息
     * 在保存此订阅时执行，如果是任务处理会保存到数据库中，如果是建立业务不会保存到数据库中
     * @return true 成功 false: 不成功
     */
    boolean saveSubscribe();



}
