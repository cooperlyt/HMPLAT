package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

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
    private String ext;
    private String id;

    public UploadFile() {
    }

    public UploadFile(BusinessFile businessFile) {
        this.businessFile = businessFile;
    }

    public UploadFile(String empName, String empCode, String md5, String fileName, BusinessFile businessFile, String ext ) {
        this.empName = empName;
        this.empCode = empCode;
        this.md5 = md5;
        this.fileName = fileName;
        this.businessFile = businessFile;
        this.ext = ext;
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

    @Column(name = "EXT",nullable = false,length = 10)
    @NotNull
    @Size(max = 10)
    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
