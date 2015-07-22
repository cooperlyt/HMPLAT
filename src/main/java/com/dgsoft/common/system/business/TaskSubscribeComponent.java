package com.dgsoft.common.system.business;

import org.jboss.seam.international.StatusMessage;

import javax.faces.application.FacesMessage;

/**
 * Created by cooper on 8/25/14.
 */
public interface TaskSubscribeComponent {


    public abstract void initSubscribe();


    //在保存后验调用，验证是否正确 可添加消息
    public abstract void validSubscribe();

    //是否通过 不要添加消息
    public abstract boolean isPass();

    //保存 可添回消息
    public abstract boolean saveSubscribe();



}
