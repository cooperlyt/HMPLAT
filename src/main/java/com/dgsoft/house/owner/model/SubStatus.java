package com.dgsoft.house.owner.model;

import com.dgsoft.common.system.business.BusinessInstance;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 4/17/16.
 */
@Entity
@Table(name = "SUB_STATUS", catalog = "HOUSE_OWNER_RECORD")
public class SubStatus implements java.io.Serializable{

    private String id;

    private String defineId;

    private BusinessInstance.BusinessStatus status;

    private OwnerBusiness ownerBusiness;

    public SubStatus() {
    }

    public SubStatus(String defineId, BusinessInstance.BusinessStatus status, OwnerBusiness ownerBusiness) {
        this.defineId = defineId;
        this.status = status;
        this.ownerBusiness = ownerBusiness;
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

    @Column(name = "DEFINE_ID", nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getDefineId() {
        return defineId;
    }

    public void setDefineId(String defineId) {
        this.defineId = defineId;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    @NotNull
    public BusinessInstance.BusinessStatus getStatus() {
        return status;
    }

    public void setStatus(BusinessInstance.BusinessStatus status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "BUSINESS",nullable = false)
    @NotNull
    public OwnerBusiness getOwnerBusiness() {
        return ownerBusiness;
    }

    public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
        this.ownerBusiness = ownerBusiness;
    }
}
