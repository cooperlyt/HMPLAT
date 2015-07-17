package com.dgsoft.house.owner.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    public UploadFile() {
    }

    public UploadFile(BusinessFile businessFile) {
        this.businessFile = businessFile;
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

    @Column(name = "MD5", nullable = false, length = 500)
    @NotNull
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
}
