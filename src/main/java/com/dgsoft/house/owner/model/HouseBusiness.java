package com.dgsoft.house.owner.model;
// Generated Oct 11, 2014 3:13:15 PM by Hibernate Tools 4.0.0

import com.dgsoft.house.model.House;
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
	private BusinessBuild businessBuild;
	private String houseCode;
	private Set<BusinessPool> businessPools = new HashSet<BusinessPool>(0);
	private Set<RecordStore> recordStores = new HashSet<RecordStore>(0);
	private Set<BusinessHouseOwner> businessHouseOwners = new HashSet<BusinessHouseOwner>(0);
	private Set<LandInfo> landInfos = new HashSet<LandInfo>(0);
	private Set<NewHouseContract> newHouseContracts = new HashSet<NewHouseContract>(0);


	public HouseBusiness() {
	}


    public HouseBusiness(OwnerBusiness ownerBusiness, House house) {
        this.ownerBusiness = ownerBusiness;
        this.houseCode = house.getId();
        //TODO house
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BUSINESS_ID", nullable = false)
	@NotNull
	public OwnerBusiness getOwnerBusiness() {
		return this.ownerBusiness;
	}

	public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
		this.ownerBusiness = ownerBusiness;
	}

    @OneToOne(fetch = FetchType.LAZY, optional = false,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "START_HOUSE",nullable = false)
    public BusinessHouse getStartBusinessHouse() {
        return startBusinessHouse;
    }

    public void setStartBusinessHouse(BusinessHouse startBusinessHouse) {
        this.startBusinessHouse = startBusinessHouse;
    }

    @OneToOne(fetch = FetchType.LAZY, optional = true,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "AFTER_HOUSE",nullable = true)
    public BusinessHouse getAfterBusinessHouse() {
        return afterBusinessHouse;
    }

    public void setAfterBusinessHouse(BusinessHouse afterBusinessHouse) {
        this.afterBusinessHouse = afterBusinessHouse;
    }

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BUILD_ID")
	public BusinessBuild getBusinessBuild() {
		return this.businessBuild;
	}

	public void setBusinessBuild(BusinessBuild businessBuild) {
		this.businessBuild = businessBuild;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "businessHouse")
	public Set<BusinessPool> getBusinessPools() {
		return this.businessPools;
	}

	public void setBusinessPools(Set<BusinessPool> businessPools) {
		this.businessPools = businessPools;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "businessHouse")
	public Set<RecordStore> getRecordStores() {
		return this.recordStores;
	}

	public void setRecordStores(Set<RecordStore> recordStores) {
		this.recordStores = recordStores;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "businessHouse")
    public Set<BusinessHouseOwner> getBusinessHouseOwners() {
        return businessHouseOwners;
    }

    public void setBusinessHouseOwners(Set<BusinessHouseOwner> businessHouseOwners) {
        this.businessHouseOwners = businessHouseOwners;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "businessHouse")
	public Set<LandInfo> getLandInfos() {
		return this.landInfos;
	}

	public void setLandInfos(Set<LandInfo> landInfos) {
		this.landInfos = landInfos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "businessHouse")
	public Set<NewHouseContract> getNewHouseContracts() {
		return this.newHouseContracts;
	}

	public void setNewHouseContracts(Set<NewHouseContract> newHouseContracts) {
		this.newHouseContracts = newHouseContracts;
	}

    @Transient
    public List<BusinessPool> getPoolsByType(BusinessPool.BusinessPoolType type) {
        List<BusinessPool> result = new ArrayList<BusinessPool>();
        for (BusinessPool pool : getBusinessPools()) {
            if (type.equals(pool.getType())) {
                result.add(pool);
            }
        }

        Collections.sort(result, new Comparator<BusinessPool>() {
            @Override
            public int compare(BusinessPool o1, BusinessPool o2) {
                if ((o1.getPoolArea() == null) && (o2.getPoolArea() == null)) {
                    return 0;
                } else if (o1.getPoolArea() == null) {
                    return 1;
                } else if (o2.getPoolArea() == null) {
                    return -1;
                } else
                    return o1.getPoolArea().compareTo(o2.getPoolArea());

            }
        });

        return result;
    }


    @Transient
    public List<BusinessPool> getNowPools() {
        return getPoolsByType(BusinessPool.BusinessPoolType.NOW_POOL);
    }

    @Transient
    public List<BusinessPool> getNewPools() {
        return getPoolsByType(BusinessPool.BusinessPoolType.NEW_POOL);
    }

}
