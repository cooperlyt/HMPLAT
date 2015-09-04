package com.dgsoft.house.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 9/3/15.
 */
@Entity
@Table(name = "DEVELOPER_ATTACH_LOGON", catalog = "HOUSE_INFO")
public class DeveloperLogonKey implements java.io.Serializable{

    private String id;
    private String password;
    private String sessionKey;
    private String userKey;
    private Project project;
    private AttachEmployee attachEmployee;

    public DeveloperLogonKey() {
    }

    public DeveloperLogonKey(String id, String password, String userKey, AttachEmployee attachEmployee, Project project) {
        this.id = id;
        this.password = password;
        this.userKey = userKey;
        this.attachEmployee = attachEmployee;
        this.project = project;
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

    @Column(name = "PASSWORD", nullable = false, length = 32)
    @Size(max = 32)
    @NotNull
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "SESSION_KEY", nullable = true, length = 32)
    @Size(max = 32)
    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    @Column(name = "USER_KEY", nullable = false, length = 32)
    @Size(max = 32)
    @NotNull
    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT", nullable = false)
    @NotNull
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMP", nullable = false)
    @NotNull
    public AttachEmployee getAttachEmployee() {
        return attachEmployee;
    }

    public void setAttachEmployee(AttachEmployee attachEmployee) {
        this.attachEmployee = attachEmployee;
    }
}
