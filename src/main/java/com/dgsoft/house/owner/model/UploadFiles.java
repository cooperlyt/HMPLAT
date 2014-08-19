package com.dgsoft.house.owner.model;
// Generated Aug 19, 2014 4:32:06 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * UploadFiles generated by hbm2java
 */
@Entity
@Table(name = "UPLOAD_FILES", catalog = "HOUSE_OWNER_RECORD")
public class UploadFiles implements java.io.Serializable {

	private String id;
	private OwnerBusiness ownerBusiness;
	private String name;
	private String type;
	private String empName;
	private String empCode;
	private String md5;
	private String fileName;
	private boolean important;
	private String memo;

	public UploadFiles() {
	}

	public UploadFiles(String id, OwnerBusiness ownerBusiness, String name,
			String type, String empName, String empCode, String md5,
			String fileName, boolean important) {
		this.id = id;
		this.ownerBusiness = ownerBusiness;
		this.name = name;
		this.type = type;
		this.empName = empName;
		this.empCode = empCode;
		this.md5 = md5;
		this.fileName = fileName;
		this.important = important;
	}
	public UploadFiles(String id, OwnerBusiness ownerBusiness, String name,
			String type, String empName, String empCode, String md5,
			String fileName, boolean important, String memo) {
		this.id = id;
		this.ownerBusiness = ownerBusiness;
		this.name = name;
		this.type = type;
		this.empName = empName;
		this.empCode = empCode;
		this.md5 = md5;
		this.fileName = fileName;
		this.important = important;
		this.memo = memo;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	@NotNull
	@Size(max = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BUSINESS_ID", nullable = false)
	@NotNull
	public OwnerBusiness getOwnerBusiness() {
		return this.ownerBusiness;
	}

	public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
		this.ownerBusiness = ownerBusiness;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	@NotNull
	@Size(max = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TYPE", nullable = false, length = 10)
	@NotNull
	@Size(max = 10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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

	@Column(name = "MD5", nullable = false, length = 200)
	@NotNull
	@Size(max = 200)
	public String getMd5() {
		return this.md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Column(name = "FILE_NAME", nullable = false)
	@NotNull
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "IMPORTANT", nullable = false)
	public boolean isImportant() {
		return this.important;
	}

	public void setImportant(boolean important) {
		this.important = important;
	}

	@Column(name = "MEMO", length = 200)
	@Size(max = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
