package com.dgsoft.house.owner.model;
// Generated Oct 11, 2014 3:13:15 PM by Hibernate Tools 4.0.0

import org.hibernate.annotations.GenericGenerator;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * BusinessHouse generated by hbm2java
 */
@Entity
@Table(name = "BUSINESS_HOUSE", catalog = "HOUSE_OWNER_RECORD")
public class HouseBusiness implements java.io.Serializable {

    private String id;
    private OwnerBusiness ownerBusiness;
    private BusinessHouse startBusinessHouse;
    private BusinessHouse afterBusinessHouse;
    private String houseCode;
    private Set<RecordStore> recordStores = new HashSet<RecordStore>(0);


    public HouseBusiness() {
    }


    public HouseBusiness(OwnerBusiness ownerBusiness, BusinessHouse startBusinessHouse) {
        this.ownerBusiness = ownerBusiness;
        this.houseCode = startBusinessHouse.getHouseCode();
        this.startBusinessHouse = startBusinessHouse;
        this.afterBusinessHouse = new BusinessHouse(startBusinessHouse);

    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BUSINESS_ID", nullable = false)
    @NotNull
    public OwnerBusiness getOwnerBusiness() {
        return this.ownerBusiness;
    }

    public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
        this.ownerBusiness = ownerBusiness;
    }

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "START_HOUSE", nullable = false)
    public BusinessHouse getStartBusinessHouse() {
        return startBusinessHouse;
    }

    public void setStartBusinessHouse(BusinessHouse startBusinessHouse) {
        this.startBusinessHouse = startBusinessHouse;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "AFTER_HOUSE", nullable = true)
    public BusinessHouse getAfterBusinessHouse() {
        return afterBusinessHouse;
    }

    public void setAfterBusinessHouse(BusinessHouse afterBusinessHouse) {
        this.afterBusinessHouse = afterBusinessHouse;
    }


    @Column(name = "HOUSE_CODE", nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getHouseCode() {
        return this.houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "houseBusiness", cascade = CascadeType.ALL)
    public Set<RecordStore> getRecordStores() {
        return this.recordStores;
    }

    public void setRecordStores(Set<RecordStore> recordStores) {
        this.recordStores = recordStores;
    }

    @Transient
    public List<RecordStore> getRecordStoreList(){
        List<RecordStore> result = new ArrayList<RecordStore>(getRecordStores());
        Collections.sort(result, new Comparator<RecordStore>() {
            @Override
            public int compare(RecordStore o1, RecordStore o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        return result;
    }

}
