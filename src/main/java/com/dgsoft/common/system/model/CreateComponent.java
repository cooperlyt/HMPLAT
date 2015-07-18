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
@Table(name = "CREATE_COMPONENT",catalog = "DB_PLAT_SYSTEM",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"COMPONENT","BUSINESS_ID"})})
public class CreateComponent implements java.io.Serializable,OrderModel {


    public enum CreateComponentType{
        DATA_VALID, BIZ_SELECT , DATA_FILL
    }

    private String id;
    private String component;
    private int priority;
    private BusinessDefine businessDefine;
    private CreateComponentType type;

    public CreateComponent() {
    }

    public CreateComponent(String component, int priority, BusinessDefine businessDefine, CreateComponentType type) {
        this.component = component;
        this.priority = priority;
        this.businessDefine = businessDefine;
        this.type = type;
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

    @Column(name = "COMPONENT", nullable = true , length = 50)
    @NotNull
    @Size(max = 50)
    public String getComponent() {
        return component;
    }

    public void setComponent(String validation) {
        this.component = validation;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false, length = 20)
    @NotNull
    public CreateComponentType getType() {
        return type;
    }

    public void setType(CreateComponentType type) {
        this.type = type;
    }
}
