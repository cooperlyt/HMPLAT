package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 9/17/16.
 */
@Entity
@Table(name = "SALE_SHOW_CHECK", catalog = "HOUSE_OWNER_RECORD")
public class SaleShowCheck implements java.io.Serializable {

    private String id;
    private boolean pass;
    private String sellShowId;
    private String messages;
    private HouseBusiness houseBusiness;

    public SaleShowCheck() {
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

    @Column(name = "PASS",nullable = false)
    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    @Column(name = "SELL_SHOW_ID", nullable = false, length = 32)
    @Size(max = 32)
    @NotNull
    public String getSellShowId() {
        return sellShowId;
    }

    public void setSellShowId(String sellShowId) {
        this.sellShowId = sellShowId;
    }

    @Column(name = "MESSAGES", length = 512)
    @Size(max = 512)
    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    @OneToOne(fetch = FetchType.LAZY,optional = false, mappedBy = "saleShowCheck")
    public HouseBusiness getHouseBusiness() {
        return houseBusiness;
    }

    public void setHouseBusiness(HouseBusiness houseBusiness) {
        this.houseBusiness = houseBusiness;
    }
}
