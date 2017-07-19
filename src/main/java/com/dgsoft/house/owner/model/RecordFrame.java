package com.dgsoft.house.owner.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 19/07/2017.
 */
@Entity
@Table(name = "RECORD_FRAME", catalog = "HOUSE_OWNER_RECORD")
public class RecordFrame implements java.io.Serializable {

    private String id;
    private String name;
    private RecordRoom recordRoom;
    private Long version;

    public RecordFrame() {
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

    @Column(name = "NAME", nullable = false, length = 200)
    @NotNull
    @Size(max = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM", nullable = false)
    public RecordRoom getRecordRoom() {
        return recordRoom;
    }

    public void setRecordRoom(RecordRoom recordRoom) {
        this.recordRoom = recordRoom;
    }

    @Version
    @Column(name = "VERSION" , nullable = false)
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
