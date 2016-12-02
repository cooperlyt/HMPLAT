package com.dgsoft.house.owner.model;

import cc.coopersoft.house.UseType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created by cooper on 02/12/2016.
 */
@Entity
@Table(name = "SELL_TYPE_TOTAL", catalog = "HOUSE_OWNER_RECORD")
public class SellTypeTotal implements java.io.Serializable {

    private String id;
    private UseType useType;
    private int count;
    private BigDecimal area;

    private BusinessBuild businessBuild;

    public SellTypeTotal() {
    }

    public SellTypeTotal(UseType useType, BusinessBuild businessBuild) {
        this.useType = useType;
        this.businessBuild = businessBuild;
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
    @Column(name = "USE_TYPE",nullable = false,length = 20)
    @NotNull
    public UseType getUseType() {
        return useType;
    }

    public void setUseType(UseType useType) {
        this.useType = useType;
    }

    @Column(name = "COUNT", nullable = false)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Column(name = "AREA", nullable = false, length = 18, scale = 3)
    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BUILD_ID", nullable = false)
    @NotNull
    public BusinessBuild getBusinessBuild() {
        return businessBuild;
    }

    public void setBusinessBuild(BusinessBuild businessBuild) {
        this.businessBuild = businessBuild;
    }
}
