package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by cooper on 19/07/2017.
 */
@Entity
@Table(name = "RECORD_ROOM", catalog = "HOUSE_OWNER_RECORD")
public class RecordRoom implements java.io.Serializable {

    private String id;
    private String name;
    private Set<RecordFrame> recordFrames;

    public RecordRoom() {
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

    @Column(name = "NAME", unique = true, nullable = false, length = 200)
    @NotNull
    @Size(max = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recordRoom" , cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<RecordFrame> getRecordFrames() {
        return recordFrames;
    }

    public void setRecordFrames(Set<RecordFrame> recordFrames) {
        this.recordFrames = recordFrames;
    }



}
