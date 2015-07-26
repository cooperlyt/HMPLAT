package com.dgsoft.common.system.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 15-7-25.
 */
@Entity
@Table(name = "FEE_CATEGORY",catalog = "DB_PLAT_SYSTEM")
public class FeeCategory implements java.io.Serializable{


    private String id;
    private String name;
    private int priority;
    private String description;
    private Set<Fee> fees = new HashSet<Fee>(0);



    public FeeCategory(){

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




    @Column(name = "NAME", nullable = false, length = 50)
    @Size(max = 50)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "PRIORITY", nullable = false)
    @NotNull
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Column(name = "DESCRIPTION", nullable = true, length = 400)
    @Size(max = 400)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true,cascade ={CascadeType.ALL},mappedBy = "category")
    public Set<Fee> getFees() {
        return fees;
    }

    public void setFees(Set<Fee> fees) {
        this.fees = fees;
    }



}
