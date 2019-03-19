package com.dgsoft.house.owner.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by cooper on 12/11/2016.
 */
@Entity
@Table(name = "CONTRACT_SUBMIT", catalog = "HOUSE_OWNER_RECORD")
public class ContractSubmit implements java.io.Serializable {

    private String id;
    private String attachEmpId;
    private String attachEmpName;
    private String contractText;
    private String fid;
    private int contractVersion;
    private Set<ContractNumber> contractNumbers = new HashSet<ContractNumber>(0);
    private HouseContract houseContract;

    public ContractSubmit(String attachEmpId, String attachEmpName, String contractText, int contractVersion) {
        this.attachEmpId = attachEmpId;
        this.attachEmpName = attachEmpName;
        this.contractText = contractText;
        this.contractVersion = contractVersion;
    }

    public ContractSubmit() {
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    @GenericGenerator(name = "pkGenerator",
            strategy = "foreign",
            parameters = { @org.hibernate.annotations.Parameter(name = "property", value = "houseContract") })
    @GeneratedValue(generator = "pkGenerator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "contractSubmit")
    @PrimaryKeyJoinColumn
    public HouseContract getHouseContract() {
        return houseContract;
    }

    public void setHouseContract(HouseContract houseContract) {
        this.houseContract = houseContract;
    }

    @Column(name = "ATTACH_EMP_ID", nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getAttachEmpId() {
        return attachEmpId;
    }

    public void setAttachEmpId(String attachEmpId) {
        this.attachEmpId = attachEmpId;
    }


    @Column(name = "ATTACH_EMP_NAME", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    public String getAttachEmpName() {
        return attachEmpName;
    }

    public void setAttachEmpName(String attachEmpName) {
        this.attachEmpName = attachEmpName;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "CONTRACT", columnDefinition = "LONGTEXT")
    public String getContractText() {
        return contractText;
    }

    public void setContractText(String contractText) {
        this.contractText = contractText;
    }

    @Column(name = "CONTRACT_VERSION",nullable = false)
    public int getContractVersion() {
        return contractVersion;
    }

    public void setContractVersion(int contractVersion) {
        this.contractVersion = contractVersion;
    }

    @Column(name = "FILE_ID", length = 32)
    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "contractSubmit",cascade = CascadeType.ALL)
    public Set<ContractNumber> getContractNumbers() {
        return contractNumbers;
    }

    public void setContractNumbers(Set<ContractNumber> contractNumbers) {
        this.contractNumbers = contractNumbers;
    }

    @Transient
    public List<ContractNumber> getContractNumberList(){
        List<ContractNumber> result = new ArrayList<ContractNumber>(getContractNumbers());
        Collections.sort(result, new Comparator<ContractNumber>() {
            @Override
            public int compare(ContractNumber o1, ContractNumber o2) {
                return o1.getContractNumber().compareTo(o2.getContractNumber());
            }
        });
        return result;
    }


}
