package com.dgsoft.common.system.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 10/28/13
 * Time: 12:24 PM
 */
@Entity
@Table(name = "PROVINCE_CODE",catalog = "DB_PLAT_SYSTEM")
public class ProvinceCode implements java.io.Serializable{

    private String id;
    private String name;
    private int level;

    public ProvinceCode() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 6)
    @NotNull
    @Size(max = 6)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "NAME", nullable = false, length = 256)
    @NotNull
    @Size(max = 256)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "LEVEL", nullable = false)
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
