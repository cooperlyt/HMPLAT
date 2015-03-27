package com.dgsoft.house.model;
// Generated Jul 12, 2013 11:32:23 AM by Hibernate Tools 4.0.0

import com.dgsoft.house.ProjectInfo;
import com.google.common.collect.Iterators;
import org.jboss.seam.international.StatusMessage;

import javax.persistence.*;
import javax.swing.tree.TreeNode;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.dgsoft.common.utils.persistence.UniqueVerify;

import java.math.BigDecimal;
import java.util.*;

/**
 * Project generated by hbm2java
 */
@Entity
@Table(name = "PROJECT", catalog = "HOUSE_INFO")
@UniqueVerify(name = "name", severity = StatusMessage.Severity.ERROR, field = {"name"})
public class Project implements java.io.Serializable, TreeNode, ProjectInfo {

    @Override
    @Transient
    public String getDistrictName() {
        return getSection().getDistrictName();
    }

    @Override
    @Transient
    public String getDistrictCode() {
        return getSection().getDistrictCode();
    }

    @Override
    @Transient
    public String getSectionName() {
        return getSection().getSectionName();
    }

    @Override
    @Transient
    public String getSectionCode() {
        return getSection().getSectionCode();
    }

    public enum ProjectState {
        BUILDING, SALE, LOCKED;
    }

    private String id;
    private Integer version;
    private Section section;
    private Developer developer;
    private String name;
    private String address;
    private String buildSize;
    private Integer buildCount;
    private BigDecimal area;
    private BigDecimal sumArea;
    private ProjectState state;
    private String memo;
    private Date mapTime;
    private Date createTime;
    private Date completeDate;

    private Set<Build> builds = new HashSet<Build>(0);
    private Set<ProjectBuildProcess> projectBuildProcesses = new HashSet<ProjectBuildProcess>(0);


    public Project() {
    }

    public Project(Section section, Developer developer) {
        this.section = section;
        this.developer = developer;
    }

    public Project(Section section,String id, ProjectState state, Date createTime) {
        this.state = state;
        this.section = section;
        this.createTime = createTime;
        this.id = id;
        this.address = section.getAddress();
    }

    public Project(String id, ProjectState state, Date createTime) {
        this.state = state;
        this.createTime = createTime;
        this.id = id;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "SECTIONID", nullable = false)
    @NotNull
    public Section getSection() {
        return this.section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "DEVELOPERID")
    public Developer getDeveloper() {
        return this.developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    @Column(name = "NAME", nullable = false, length = 50, unique = true)
    @NotNull
    @Size(max = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ADDRESS", length = 200)
    @Size(max = 200)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    @Column(name = "BUILD_SIZE", length = 32)
    @Size(max = 32)
    public String getBuildSize() {
        return this.buildSize;
    }

    public void setBuildSize(String buildSize) {
        this.buildSize = buildSize;
    }

    @Column(name = "BUILD_COUNT")
    public Integer getBuildCount() {
        return this.buildCount;
    }

    public void setBuildCount(Integer buildCount) {
        this.buildCount = buildCount;
    }


    @Column(name = "AREA", precision = 18, scale = 3)
    public BigDecimal getArea() {
        return this.area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    @Column(name = "SUM_AREA", precision = 18, scale = 3)
    public BigDecimal getSumArea() {
        return this.sumArea;
    }

    public void setSumArea(BigDecimal sumArea) {
        this.sumArea = sumArea;
    }


    @Enumerated(EnumType.STRING)
    @Column(name = "STATE", nullable = false)
    @NotNull
    public ProjectState getState() {
        return this.state;
    }

    public void setState(ProjectState state) {
        this.state = state;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME",nullable = false)
    @NotNull
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "MEMO", length = 200)
    @Size(max = 200)
    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MAP_TIME", nullable = true, length = 19)
    public Date getMapTime() {
        return this.mapTime;
    }

    public void setMapTime(Date mapTime) {
        this.mapTime = mapTime;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true,cascade = {CascadeType.ALL})
    public Set<Build> getBuilds() {
        return this.builds;
    }

    public void setBuilds(Set<Build> builds) {
        this.builds = builds;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    public Set<ProjectBuildProcess> getProjectBuildProcesses() {
        return this.projectBuildProcesses;
    }

    public void setProjectBuildProcesses(
            Set<ProjectBuildProcess> projectBuildProcesses) {
        this.projectBuildProcesses = projectBuildProcesses;
    }

    @Override
    @Transient
    public String getDeveloperName() {
        return getDeveloper().getName();
    }

    @Override
    @Transient
    public String getDeveloperCode() {
        return getDeveloper().getId();
    }

    @Override
    @Transient
    public String getProjectName() {
        return getName();
    }

    @Override
    @Transient
    public String getProjectCode() {
        return getId();
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "COMPLETE_DATE",nullable = true)
    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    @Transient
    public List<Build> getBuildList() {
        List<Build> result = new ArrayList<Build>(getBuilds());
        Collections.sort(result, new Comparator<Build>() {
            @Override
            public int compare(Build o1, Build o2) {
                if ((o1.getDevBuildNumber() == null) && (o2.getDevBuildNumber() == null)){
                    return o1.getBuildNo().compareTo(o2.getBuildNo());
                } else if ((o1.getDevBuildNumber() != null) && (o2.getDevBuildNumber() != null)){
                    return o1.getDevBuildNumber().compareTo(o2.getDevBuildNumber());
                }else{
                    if (o1.getDevBuildNumber() == null){
                        return -1;
                    }else{
                        return 1;
                    }
                }
            }
        });
        return result;
    }

    @Override
    @Transient
    public TreeNode getChildAt(int childIndex) {
        return getBuildList().get(childIndex);
    }

    @Override
    @Transient
    public int getChildCount() {
        return getBuilds().size();
    }

    @Override
    @Transient
    public TreeNode getParent() {
        return getSection();
    }

    @Override
    @Transient
    public int getIndex(TreeNode node) {
        return getBuildList().indexOf(node);
    }

    @Override
    @Transient
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    @Transient
    public boolean isLeaf() {
        return getBuilds().isEmpty();
    }

    @Override
    @Transient
    public Enumeration children() {
        return Iterators.asEnumeration(getBuilds().iterator());
    }

}
