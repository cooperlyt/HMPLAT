package com.dgsoft.common.system.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 8/3/15.
 */
@Entity
@Table(name = "REPORT",catalog = "DB_PLAT_SYSTEM")
public class Report implements java.io.Serializable{

    private String id;
    private String name;
    private String description;
    private String page;

    public Report() {
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

    @Column(name = "NAME",nullable = false,length = 100)
    @NotNull
    @Size(max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DESCRIPTION", nullable = true, length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "PAGE",nullable = false,length = 200)
    @NotNull
    @Size(max = 200)
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

}
