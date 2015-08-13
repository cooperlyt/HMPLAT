package com.dgsoft.house.owner.model;

import com.dgsoft.house.HouseInfo;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 8/11/15.
 */
@Entity
@Table(name = "ADD_HOUSE_STATUS", catalog = "HOUSE_OWNER_RECORD")
public class AddHouseStatus implements java.io.Serializable {

    private HouseInfo.HouseStatus status;
    private String id;
    private HouseBusiness houseBusiness;

    public AddHouseStatus() {
    }

    public AddHouseStatus(HouseInfo.HouseStatus status,HouseBusiness houseBusiness){
        this.status = status;
        this.houseBusiness = houseBusiness;

    }

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 32)
    @NotNull
    public HouseInfo.HouseStatus getStatus() {
        return status;
    }

    public void setStatus(HouseInfo.HouseStatus status) {
        this.status = status;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUSINESS", nullable = false)
    @NotNull
    public HouseBusiness getHouseBusiness() {
        return houseBusiness;
    }

    public void setHouseBusiness(HouseBusiness houseBusiness) {
        this.houseBusiness = houseBusiness;
    }
}