package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by cooper on 10/30/14.
 */
@Entity
@Table(name = "UPLOAD_FILE", catalog = "HOUSE_OWNER_RECORD")
public class UploadFile implements java.io.Serializable{

    private String empName;
    private String empCode;
    private String md5;
    private String fileName;
    private BusinessFile businessFile;
    private Long size;
    private String id;
    private Date uploadTime;

    public UploadFile() {
    }

    public UploadFile(String id, String empName, String empCode, String md5, String fileName, BusinessFile businessFile, Long size ) {
        this.id = id;
        this.empName = empName;
        this.empCode = empCode;
        this.md5 = md5;
        this.fileName = fileName;
        this.businessFile = businessFile;
        this.size = size;
        uploadTime = new Date();
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    @Column(name = "FILE_NAME", unique = true, nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "EMP_NAME", nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    public String getEmpName() {
        return this.empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Column(name = "EMP_CODE", nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    public String getEmpCode() {
        return this.empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    @Column(name = "MD5", length = 500)
    @Size(max = 500)
    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="BUSINESS_FILE_ID",nullable = false)
    public BusinessFile getBusinessFile() {
        return businessFile;
    }

    public void setBusinessFile(BusinessFile businessFile) {
        this.businessFile = businessFile;
    }


    @Column(name = "SIZE",nullable = true)
    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPLOAD_TIME",nullable = false)
    @NotNull
    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}
