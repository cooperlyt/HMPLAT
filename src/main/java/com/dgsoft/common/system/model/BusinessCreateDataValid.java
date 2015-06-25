package com.dgsoft.common.system.model;

import com.dgsoft.common.OrderModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 6/25/15.
 */
@Entity
@Table(name = "BUSSINESS_CREATE_DATA_VALID",catalog = "DB_PLAT_SYSTEM",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"VALIDATION","BUSINESS_ID"})})
public class BusinessCreateDataValid implements java.io.Serializable,OrderModel {

    private String id;
    private String validation;
    private int priority;
    private BusinessDefine businessDefine;

    public BusinessCreateDataValid() {
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

    @Column(name = "VALIDATION", nullable = true , length = 50)
    @NotNull
    @Size(max = 50)
    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    @Override
    @Column(name = "PRIORITY", nullable = false)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUSINESS_ID", nullable = false)
    @NotNull
    public BusinessDefine getBusinessDefine() {
        return businessDefine;
    }

    public void setBusinessDefine(BusinessDefine businessDefine) {
        this.businessDefine = businessDefine;
    }
}
