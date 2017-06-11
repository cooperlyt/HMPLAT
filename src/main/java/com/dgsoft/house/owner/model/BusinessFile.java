package com.dgsoft.house.owner.model;
// Generated Aug 19, 2014 4:32:06 PM by Hibernate Tools 4.0.0

import com.dgsoft.common.OrderModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.swing.tree.TreeNode;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * UploadFiles generated by hbm2java
 */
@Entity
@Table(name = "BUSINESS_FILE", catalog = "HOUSE_OWNER_RECORD")
public class BusinessFile implements java.io.Serializable, OrderModel {

	public enum DocType {
		IMPORTANT,ADDITIONAL,AFTER
	}

	private String id;
	private OwnerBusiness ownerBusiness;
	private String name;
	private String importantCode;
	private String memo;
    private boolean noFile;
    //private boolean important;
	private DocType type;
	private RecordLocal recordLocal;
    private Set<UploadFile> uploadFiles = new HashSet<UploadFile>(0);
	private RecordStore recordStore;

    private int priority;

	public BusinessFile() {
	}

	public BusinessFile(OwnerBusiness ownerBusiness,String name, String importantCode, int priority) {
		this.id=UUID.randomUUID().toString().replace("-", "");
		this.name = name;
		this.importantCode = importantCode;
		this.noFile = false;
		this.type = DocType.IMPORTANT;
		this.priority = priority;
		this.ownerBusiness = ownerBusiness;
	}



	public BusinessFile(OwnerBusiness ownerBusiness, String name, int priority) {
		this.id=UUID.randomUUID().toString().replace("-", "");
		this.name = name;
		this.noFile = false;
		this.type = DocType.ADDITIONAL;
		this.priority = priority;
		this.ownerBusiness = ownerBusiness;
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


	@OneToOne(fetch = FetchType.LAZY,optional = true ,cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	public RecordLocal getRecordLocal() {
		return recordLocal;
	}

	public void setRecordLocal(RecordLocal recordLocal) {
		this.recordLocal = recordLocal;
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

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE",nullable = false, length = 20)
	@NotNull
	public DocType getType() {
		return type;
	}

	public void setType(DocType type) {
		this.type = type;
	}

	@Transient
    public boolean isImportant() {
        return DocType.IMPORTANT.equals(getType());
    }


    @Column(name = "IMPORTANT_CODE",nullable = true,length = 32)
    public String getImportantCode() {
        return importantCode;
    }

    public void setImportantCode(String importantCode) {
        this.importantCode = importantCode;
    }

    @Column(name = "MEMO", length = 200)
	@Size(max = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

    @Column(name = "NO_FILE",nullable = false)
    public boolean isNoFile() {
        return noFile;
    }

    public void setNoFile(boolean noFile) {
        this.noFile = noFile;
    }

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "businessFile")
    public Set<UploadFile> getUploadFiles() {
        return uploadFiles;
    }

    public void setUploadFiles(Set<UploadFile> uploadFiles) {
        this.uploadFiles = uploadFiles;
    }


	@Override
    @Column(name = "PRIORITY", nullable = false)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

	@Transient
	public List<UploadFile> getUploadFileList(){
		List<UploadFile> result = new ArrayList<UploadFile>(getUploadFiles());
		Collections.sort(result, new Comparator<UploadFile>() {
			@Override
			public int compare(UploadFile o1, UploadFile o2) {
				return o1.getUploadTime().compareTo(o2.getUploadTime());
			}
		});
		return result;
	}

	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = true)
	@JoinColumn(name = "RECORD_STORE", nullable = true)
	public RecordStore getRecordStore() {
		return recordStore;
	}

	public void setRecordStore(RecordStore recordStore) {
		this.recordStore = recordStore;
	}
}
