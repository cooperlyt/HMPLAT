package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by cooper on 10/30/14.
 */
@Entity
@Table(name = "HOUSE_REG_INFO", catalog = "HOUSE_OWNER_RECORD")
public class HouseRegInfo implements java.io.Serializable {

    private String id;

    private BusinessHouse.PoolType poolType;
    private String houseFrom;
    private String houseProperty;
    private Set<BusinessPool> businessPools = new HashSet<BusinessPool>(0);

    public HouseRegInfo() {
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

    @Enumerated(EnumType.STRING)
    @Column(name = "POOL_MEMO", nullable = false, length = 32)
    @NotNull
    public BusinessHouse.PoolType getPoolType() {
        return this.poolType;
    }

    public void setPoolType(BusinessHouse.PoolType poolType) {
        this.poolType = poolType;
    }

    @Column(name = "HOUSE_FROM", nullable = false, length = 32)
    @Size(max = 32)
    @NotNull
    public String getHouseFrom() {
        return this.houseFrom;
    }

    public void setHouseFrom(String houseFrom) {
        this.houseFrom = houseFrom;
    }

    @Column(name = "HOUSE_PORPERTY", nullable = false, length = 32)
    @Size(max = 32)
    @NotNull
    public String getHouseProperty() {
        return this.houseProperty;
    }

    public void setHouseProperty(String houseProperty) {
        this.houseProperty = houseProperty;
    }


    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "houseRegInfo")
    public Set<BusinessPool> getBusinessPools() {
        return businessPools;
    }

    public void setBusinessPools(Set<BusinessPool> businessPools) {
        this.businessPools = businessPools;
    }

    @Transient
    public List<BusinessPool> getBusinessPoolList() {
        List<BusinessPool> result = new ArrayList<BusinessPool>(getBusinessPools());
        Collections.sort(result, new Comparator<BusinessPool>() {
            @Override
            public int compare(BusinessPool o1, BusinessPool o2) {
                return o1.getCreateTime().compareTo(o2.getCreateTime());
            }
        });
        return result;
    }

}
