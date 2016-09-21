package com.dgsoft.house.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 9/17/16.
 */
@Entity
@Table(name = "SELL_SHOW_IMG", catalog = "HOUSE_INFO")
public class SellShowImg implements java.io.Serializable {

    private String id;
    private String title;
    private String description;
    private HouseSellInfo houseSellInfo;


    public SellShowImg() {
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

    @Column(name = "TITLE", nullable = false, length = 64)
    @Size(max = 64)
    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "DESCRIPTION", length = 512)
    @Size(max = 64)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "HOUSE_SELL_INFO",nullable = false)
    @NotNull
    public HouseSellInfo getHouseSellInfo() {
        return houseSellInfo;
    }

    public void setHouseSellInfo(HouseSellInfo houseSellInfo) {
        this.houseSellInfo = houseSellInfo;
    }
}
