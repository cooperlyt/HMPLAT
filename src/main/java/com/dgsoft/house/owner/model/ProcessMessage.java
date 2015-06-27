package com.dgsoft.house.owner.model;

import com.dgsoft.common.system.business.TaskSubscribeComponent;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by cooper on 6/27/15.
 */
@Entity
@Table(name = "PROCESS_MESSAGES", catalog = "HOUSE_OWNER_RECORD")
public class ProcessMessage implements java.io.Serializable{

    private TaskSubscribeComponent.ValidResult level;

    private String messages;

    private String id;

    private OwnerBusiness ownerBusiness;

    @Enumerated(EnumType.STRING)
    @Column(name="LEVEL",nullable = false,length = 20)
    @NotNull
    public TaskSubscribeComponent.ValidResult getLevel() {
        return level;
    }

    public void setLevel(TaskSubscribeComponent.ValidResult level) {
        this.level = level;
    }

    @Column(name = "MESSAGES" ,nullable = false, length = 200)
    @NotNull
    @Size(max = 200)
    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUSINESS_ID", nullable = false)
    @NotNull
    public OwnerBusiness getOwnerBusiness() {
        return ownerBusiness;
    }

    public void setOwnerBusiness(OwnerBusiness ownerBusiness) {
        this.ownerBusiness = ownerBusiness;
    }
}
