package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 4/13/16.
 */
@Entity
@Table(name = "RECORD_LOCAL", catalog = "HOUSE_OWNER_RECORD")
public class RecordLocal implements java.io.Serializable{

    private String id;
    private String frame;
    private String cabinet;
    private String box;
    private String recordCode;
    private BusinessFile businessFile;
    private Long version;

    public RecordLocal() {
    }

    public RecordLocal(String frame, String cabinet, String box, String recordCode, BusinessFile businessFile) {
        this.frame = frame;
        this.cabinet = cabinet;
        this.box = box;
        this.recordCode = recordCode;
        this.businessFile = businessFile;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    @GenericGenerator(name = "pkGenerator",
            strategy = "foreign",
            parameters = { @Parameter(name = "property", value = "businessFile") })
    @GeneratedValue(generator = "pkGenerator")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @PrimaryKeyJoinColumn
    @NotNull
    public BusinessFile getBusinessFile() {
        return businessFile;
    }

    public void setBusinessFile(BusinessFile businessFile) {
        this.businessFile = businessFile;
    }

    @Column(name = "RECORD_CODE", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    @Column(name = "FRAME", nullable = true, length = 10)
    @Size(max = 10)
    public String getFrame() {
        return this.frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    @Column(name = "CABINET", nullable = true, length = 20)
    @Size(max = 20)
    public String getCabinet() {
        return this.cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    @Column(name = "BOX", nullable = true, length = 50)
    @Size(max = 50)
    public String getBox() {
        return this.box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    @Version
    @Column(name = "VERSION" , nullable = true)
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
