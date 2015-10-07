package com.dgsoft.common.system.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by Administrator on 15-7-25.
 */
@Entity
@Table(name = "FEE",catalog = "DB_PLAT_SYSTEM")
public class Fee implements java.io.Serializable {

    private String id;
    private FeeCategory category;
    private String name;
    private String description;
    private String feeEl;
    private String detailsEl;
    private int priority;
    private String forEachValues;
    private String forEachVar;
    private Set<FeeTimeArea> feeTimeAreas = new HashSet<FeeTimeArea>(0);



    public Fee(){

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


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "CATEGORY",nullable = false)
    public FeeCategory getCategory() {
        return category;
    }

    public void setCategory(FeeCategory category) {
        this.category = category;
    }

    @Column(name = "NAME",nullable = false,length = 50)
    @NotNull
    @Size(max = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "DESCRIPTION",nullable = true)
    @Size(max=400)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "FEE_EL",nullable = false, length = 512)
    @NotNull
    @Size(max =512)
    public String getFeeEl() {
        return feeEl;
    }

    public void setFeeEl(String feeEl) {
        this.feeEl = feeEl;
    }

    @Column(name = "DETAILS_EL",nullable = true, length = 512)
    @Size(max = 512)
    public String getDetailsEl() {
        return detailsEl;
    }

    public void setDetailsEl(String detailsEl) {
        this.detailsEl = detailsEl;
    }
    @Column(name = "PRIORITY",nullable = false)
    @NotNull
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true,cascade = {CascadeType.ALL},mappedBy = "fee")
    public Set<FeeTimeArea> getFeeTimeAreas() {
        return feeTimeAreas;
    }

    public void setFeeTimeAreas(Set<FeeTimeArea> feeTimeAreas) {
        this.feeTimeAreas = feeTimeAreas;
    }

    @Column(name = "FOR_EACH_VALUES",length = 512)
    @Size(max = 512)
    public String getForEachValues() {
        return forEachValues;
    }

    public void setForEachValues(String forEachValues) {
        this.forEachValues = forEachValues;
    }

    @Column(name="FOR_EACH_VAR", length = 32)
    @Size(max = 32)
    public String getForEachVar() {
        return forEachVar;
    }

    public void setForEachVar(String forEachVar) {
        this.forEachVar = forEachVar;
    }

    @Transient
    public List<FeeTimeArea> getFeeTimeAreaList(){
        List<FeeTimeArea> result = new ArrayList<FeeTimeArea>(getFeeTimeAreas());
        Collections.sort(result, new Comparator<FeeTimeArea>() {
            @Override
            public int compare(FeeTimeArea o1, FeeTimeArea o2) {
                return o1.getBeginTime().compareTo(o2.getBeginTime());
            }
        });
        return result;
    }


}