package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 2/1/16.
 */
@Entity
@Table(name = "LAND_END_TIME", catalog = "HOUSE_OWNER_RECORD",uniqueConstraints = @UniqueConstraint(columnNames = {
        "PROJECT_ID", "USE_TYPE"}))
public class ProjectLandEndTime implements java.io.Serializable{


    private String id;
    private String useTypeCategory;
    private Date endTime;
    private ProjectSellInfo projectSellInfo;

    public ProjectLandEndTime() {
    }

    public ProjectLandEndTime(ProjectSellInfo projectSellInfo) {
        this.projectSellInfo = projectSellInfo;
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

    @Column(name = "USE_TYPE",nullable = false,length = 20)
    @NotNull
    @Size(max = 20)
    public String getUseTypeCategory() {
        return useTypeCategory;
    }

    public void setUseTypeCategory(String useTypeCategory) {
        this.useTypeCategory = useTypeCategory;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_TIME", nullable = false, length = 19)
    @NotNull
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PROJECT_ID",nullable = false)
    @NotNull
    public ProjectSellInfo getProjectSellInfo() {
        return projectSellInfo;
    }

    public void setProjectSellInfo(ProjectSellInfo projectSellInfo) {
        this.projectSellInfo = projectSellInfo;
    }



}
