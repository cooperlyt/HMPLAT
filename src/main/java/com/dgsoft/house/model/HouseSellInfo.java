package com.dgsoft.house.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cooper on 9/17/16.
 */
@Entity
@Table(name = "HOUSE_SELL_INFO", catalog = "HOUSE_INFO")
public class HouseSellInfo implements java.io.Serializable{

    private String id;
    private String title;
    private String tags;
    private String description;
    private String environment;
    private BigDecimal lat;
    private BigDecimal lng;
    private Integer zoom;
    private int roomCount;
    private int livingRommCount;
    private String localArea;
    private String schoolArea;
    private String metroArea;
    private String direction;
    private String decorate;
    private int createYear;
    private boolean elevator;
    private String img;

    private Set<SellShowImg> sellShowImgs = new HashSet<SellShowImg>(0);

    public HouseSellInfo() {
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

    @Column(name = "TITLE",nullable = false, length = 64)
    @NotNull
    @Size(max = 64)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "TAGS",length = 512)
    @Size(max = 512)
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Column(name = "DESCRIPTION",length = 512)
    @Size(max = 512)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "ENVIRONMENT",length = 512)
    @Size(max = 512)
    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Column(name = "LAT",precision = 18, scale = 14)
    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    @Column(name = "LNG",precision = 18, scale = 14)
    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    @Column(name = "ZOOM")
    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    @Column(name = "ROOM_COUNT",nullable = false)
    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    @Column(name = "LIVING_ROOM",nullable = false)
    public int getLivingRommCount() {
        return livingRommCount;
    }

    public void setLivingRommCount(int livingRommCount) {
        this.livingRommCount = livingRommCount;
    }

    @Column(name = "LOCAL_AREA", length = 32)
    @Size(max = 32)
    public String getLocalArea() {
        return localArea;
    }

    public void setLocalArea(String localArea) {
        this.localArea = localArea;
    }

    @Column(name = "SCHOOL_AREA", length = 32)
    @Size(max = 32)
    public String getSchoolArea() {
        return schoolArea;
    }

    public void setSchoolArea(String schoolArea) {
        this.schoolArea = schoolArea;
    }

    @Column(name = "METRO_AREA", length = 32)
    @Size(max = 32)
    public String getMetroArea() {
        return metroArea;
    }

    public void setMetroArea(String metroArea) {
        this.metroArea = metroArea;
    }

    @Column(name = "DIRECTION", length = 32, nullable = false)
    @Size(max = 32)
    @NotNull
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Column(name = "DECORATE", length = 32, nullable = false)
    @Size(max = 32)
    @NotNull
    public String getDecorate() {
        return decorate;
    }

    public void setDecorate(String decorate) {
        this.decorate = decorate;
    }

    @Column(name = "CREATE_YEAR", nullable = false)
    public int getCreateYear() {
        return createYear;
    }

    public void setCreateYear(int createYear) {
        this.createYear = createYear;
    }

    @Column(name = "ELEVATOR", nullable = false)
    public boolean isElevator() {
        return elevator;
    }

    public void setElevator(boolean elevator) {
        this.elevator = elevator;
    }

    @Column(name = "IMG",length = 32)
    @Size(max = 32)
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "houseSellInfo")
    public Set<SellShowImg> getSellShowImgs() {
        return sellShowImgs;
    }

    public void setSellShowImgs(Set<SellShowImg> sellShowImgs) {
        this.sellShowImgs = sellShowImgs;
    }
}
