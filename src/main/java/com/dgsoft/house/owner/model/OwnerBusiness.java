package com.dgsoft.house.owner.model;
// Generated Aug 19, 2014 4:32:06 PM by Hibernate Tools 4.0.0

import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.model.House;
import org.jboss.seam.log.Logging;
import sun.rmi.runtime.Log;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * OwnerBusiness generated by hbm2java
 */
@Entity
@Table(name = "OWNER_BUSINESS", catalog = "HOUSE_OWNER_RECORD")
@Inheritance(strategy = InheritanceType.JOINED)
public class OwnerBusiness implements java.io.Serializable, BusinessInstance {


    private String id;
    private Integer version;
    private BusinessSource source;
    private BusinessType type;


    private String memo;
    private BusinessStatus status;
    private String defineName;
    private String defineId;
    private Integer defineVersion;

    private Date createTime;
    private Date applyTime;
    private Date checkTime;
    private Date regTime;
    private Date recordTime;


    private boolean recorded;

    private Set<BusinessFile> uploadFileses = new HashSet<BusinessFile>(0);
    private Set<Reason> reasons = new HashSet<Reason>(0);
    private Set<BusinessMoney> businessMoneys = new HashSet<BusinessMoney>(0);
    private Set<MappingCorp> mappingCorps = new HashSet<MappingCorp>(0);
    private Set<BusinessEmp> businessEmps = new HashSet<BusinessEmp>(0);
    private Set<Card> cards = new HashSet<Card>(0);
    private Set<MakeCard> makeCards = new HashSet<MakeCard>(0);
    private Set<BusinessPersion> businessPersions = new HashSet<BusinessPersion>(0);

    private Set<Evaluate> evaluates = new HashSet<Evaluate>(0);
    private Set<MortgaegeRegiste> mortgaegeRegistes = new HashSet<MortgaegeRegiste>(0);
    private Set<HouseCloseCancel> houseCloseCancels = new HashSet<HouseCloseCancel>(0);
    private Set<HouseBusiness> houseBusinesses = new HashSet<HouseBusiness>(0);
    private Set<CloseHouse> closeHouses = new HashSet<CloseHouse>(0);



    private Set<TaskOper> taskOpers = new HashSet<TaskOper>(0);
    private OwnerBusiness selectBusiness;
    private Set<BusinessProject> businessProjects = new HashSet<BusinessProject>(0);
    private Set<ProcessMessage> processMessages = new HashSet<ProcessMessage>(0);
    private Set<FactMoneyInfo> factMoneyInfos = new HashSet<FactMoneyInfo>(0);
    private Set<RecordStore> recordStores = new HashSet<RecordStore>(0);
    private Set<ContractOwner> contractOwners = new HashSet<ContractOwner>(0);
    private Set<ProjectMortgage> projectMortgages = new HashSet<ProjectMortgage>(0);
    private Set<GiveCard> giveCards = new HashSet<GiveCard>(0);

    private Set<SubStatus> subStatuses = new HashSet<SubStatus>(0);
    public OwnerBusiness() {
    }

    public OwnerBusiness(BusinessSource source, BusinessStatus status, Date createTime, boolean recorded, BusinessType type) {
        this.source = source;
        this.status = status;
        this.createTime = createTime;
        this.applyTime = createTime;
        this.recorded = recorded;
        this.type = type;
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

    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "SOURCE", nullable = false, length = 20)
    @NotNull
    public BusinessSource getSource() {
        return this.source;
    }

    public void setSource(BusinessSource source) {
        this.source = source;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Set<ProcessMessage> getProcessMessages() {
        return processMessages;
    }

    public void setProcessMessages(Set<ProcessMessage> processMessages) {
        this.processMessages = processMessages;
    }

    @Transient
    public List<ProcessMessage> getProcessMessageList() {
        List<ProcessMessage> result = new ArrayList<ProcessMessage>(getProcessMessages());
        Collections.sort(result, new Comparator<ProcessMessage>() {
            @Override
            public int compare(ProcessMessage o1, ProcessMessage o2) {
                return Integer.valueOf(o1.getLevel().getPri()).compareTo(o2.getLevel().getPri());
            }
        });
        return result;
    }

    @Column(name = "DEFINE_NAME", nullable = false, length = 50)
    @Size(max = 50)
    @NotNull
    public String getDefineName() {
        return defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    @Column(name = "DEFINE_ID", nullable = false, length = 32)
    @Size(max = 32)
    @NotNull
    public String getDefineId() {
        return defineId;
    }

    public void setDefineId(String defineId) {
        this.defineId = defineId;
    }

    @Column(name = "DEFINE_VERSION", nullable = true)
    public Integer getDefineVersion() {
        return defineVersion;
    }

    public void setDefineVersion(Integer defineVersion) {
        this.defineVersion = defineVersion;
    }

    @Column(name = "MEMO", length = 200)
    @Size(max = 200)
    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    @NotNull
    public BusinessStatus getStatus() {
        return this.status;
    }

    public void setStatus(BusinessStatus status) {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false, length = 20)
    @NotNull
    public BusinessType getType() {
        return type;
    }

    public void setType(BusinessType type) {
        this.type = type;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Set<BusinessFile> getUploadFileses() {
        return this.uploadFileses;
    }

    public void setUploadFileses(Set<BusinessFile> uploadFileses) {
        this.uploadFileses = uploadFileses;
    }

    @Transient
    public List<BusinessFile>  getVaidBusinessFileList(){
        List<BusinessFile> result = new ArrayList<BusinessFile>();
        for (BusinessFile businessFile: getUploadFileses()){
            if (businessFile.isNoFile() || !businessFile.getUploadFiles().isEmpty()){
                result.add(businessFile);
            }
        }
        Collections.sort(result,OrderBeanComparator.getInstance());
        return result;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Set<Reason> getReasons() {
        return this.reasons;
    }

    public void setReasons(Set<Reason> reasons) {
        this.reasons = reasons;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Set<BusinessMoney> getBusinessMoneys() {
        return this.businessMoneys;
    }

    public void setBusinessMoneys(Set<BusinessMoney> businessMoneys) {
        this.businessMoneys = businessMoneys;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Set<MappingCorp> getMappingCorps() {
        return this.mappingCorps;
    }

    public void setMappingCorps(Set<MappingCorp> mappingCorps) {
        this.mappingCorps = mappingCorps;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Set<BusinessEmp> getBusinessEmps() {
        return this.businessEmps;
    }

    public void setBusinessEmps(Set<BusinessEmp> businessEmps) {
        this.businessEmps = businessEmps;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Set<GiveCard> getGiveCards() {
        return giveCards;
    }

    public void setGiveCards(Set<GiveCard> giveCards) {
        this.giveCards = giveCards;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Set<Card> getCards() {
        return this.cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", cascade = {CascadeType.ALL})
    public Set<MakeCard> getMakeCards() {
        return makeCards;
    }

    public void setMakeCards(Set<MakeCard> makeCards) {
        this.makeCards = makeCards;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Set<BusinessPersion> getBusinessPersions() {
        return this.businessPersions;
    }

    public void setBusinessPersions(Set<BusinessPersion> businessPersions) {
        this.businessPersions = businessPersions;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Set<Evaluate> getEvaluates() {
        return this.evaluates;
    }

    public void setEvaluates(Set<Evaluate> evaluates) {
        this.evaluates = evaluates;
    }

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "ownerBusiness",cascade = {CascadeType.ALL})
    public Set<MortgaegeRegiste> getMortgaegeRegistes() {
        return this.mortgaegeRegistes;
    }

    public void setMortgaegeRegistes(Set<MortgaegeRegiste> mortgaegeRegistes) {
        this.mortgaegeRegistes = mortgaegeRegistes;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Set<HouseCloseCancel> getHouseCloseCancels() {
        return this.houseCloseCancels;
    }

    public void setHouseCloseCancels(Set<HouseCloseCancel> houseCloseCancels) {
        this.houseCloseCancels = houseCloseCancels;
    }



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Set<HouseBusiness> getHouseBusinesses() {
        return this.houseBusinesses;
    }

    public void setHouseBusinesses(Set<HouseBusiness> houseBusinesses) {
        this.houseBusinesses = houseBusinesses;
    }

    @Transient
    public List<HouseBusiness> getHouseBusinessList() {
        List<HouseBusiness> result = new ArrayList<HouseBusiness>(getHouseBusinesses());
        Collections.sort(result, new Comparator<HouseBusiness>() {
            @Override
            public int compare(HouseBusiness o1, HouseBusiness o2) {
                if (o1.getId() == null || o2.getId() == null) {
                    if (o1.getHouseCode() == null || o2.getHouseCode() == null) {
                        return 0;
                    } else {
                        return o1.getHouseCode().compareTo(o2.getHouseCode());
                    }
                }
                return o1.getId().compareTo(o2.getId());
            }
        });
        return result;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", cascade = {CascadeType.ALL}, orphanRemoval = true)
    public Set<CloseHouse> getCloseHouses() {
        return this.closeHouses;
    }

    public void setCloseHouses(Set<CloseHouse> closeHouses) {
        this.closeHouses = closeHouses;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", orphanRemoval = true, cascade = {CascadeType.ALL})
    public Set<TaskOper> getTaskOpers() {
        return taskOpers;
    }

    public void setTaskOpers(Set<TaskOper> taskOpers) {
        this.taskOpers = taskOpers;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "SELECT_BUSINESS", nullable = true)
    public OwnerBusiness getSelectBusiness() {
        return selectBusiness;
    }

    public void setSelectBusiness(OwnerBusiness selectBusiness) {
        this.selectBusiness = selectBusiness;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", orphanRemoval = true, cascade = CascadeType.ALL)
    public Set<BusinessProject> getBusinessProjects() {
        return businessProjects;
    }

    public void setBusinessProjects(Set<BusinessProject> businessProjects) {
        this.businessProjects = businessProjects;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness", orphanRemoval = true, cascade = CascadeType.ALL)
    public Set<FactMoneyInfo> getFactMoneyInfos() {
        return factMoneyInfos;
    }

    public void setFactMoneyInfos(Set<FactMoneyInfo> factMoneyInfos) {
        this.factMoneyInfos = factMoneyInfos;
    }

    @Transient
    public FactMoneyInfo getFactMoneyInfo(){
        if (getFactMoneyInfos().isEmpty()){
            return null;
        }
        return getFactMoneyInfos().iterator().next();
    }

    @Transient
    public void setFactMoneyInfo(FactMoneyInfo info){
        getFactMoneyInfos().clear();
        getFactMoneyInfos().add(info);
    }

    @Column(name = "RECORDED", nullable = false)
    public boolean isRecorded() {
        return recorded;
    }

    public void setRecorded(boolean recorded) {
        this.recorded = recorded;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME", nullable = false, length = 19)
    @NotNull
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "APPLY_TIME", nullable = false, length = 19)
    @NotNull
    public Date getApplyTime() {
        return this.applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CHECK_TIME", nullable = true, length = 19)
    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL,mappedBy = "ownerBusiness")
    public Set<SubStatus> getSubStatuses() {
        return subStatuses;
    }

    public void setSubStatuses(Set<SubStatus> subStatuses) {
        this.subStatuses = subStatuses;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REG_TIME", nullable = true, length = 19)
    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RECORD_TIME", nullable = true, length = 19)
    public Date getRecordTime() {
        return this.recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true, mappedBy = "ownerBusiness")
    public Set<RecordStore> getRecordStores() {
        return recordStores;
    }

    public void setRecordStores(Set<RecordStore> recordStores) {
        this.recordStores = recordStores;
    }

    @Transient
    public List<RecordStore> getRecordStoreList(){
        List<RecordStore> result = new ArrayList<RecordStore>(getRecordStores());
        Collections.sort(result, new Comparator<RecordStore>() {
            @Override
            public int compare(RecordStore o1, RecordStore o2) {
                return o1.getCreateTime().compareTo(o2.getCreateTime());
            }
        });
        return result;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownerBusiness")
    public Set<ContractOwner> getContractOwners() {
        return contractOwners;
    }

    public void setContractOwners(Set<ContractOwner> contractOwners) {
        this.contractOwners = contractOwners;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "ownerBusiness")
    public Set<ProjectMortgage> getProjectMortgages() {
        return projectMortgages;
    }

    public void setProjectMortgages(Set<ProjectMortgage> projectMortgages) {
        this.projectMortgages = projectMortgages;
    }

    @Transient
    public ProjectMortgage getProjectMortgage(){
        if (getProjectMortgages().isEmpty()){
            return null;
        }
        return getProjectMortgages().iterator().next();
    }

    @Transient
    public void setProjectMortgage(ProjectMortgage projectMortgage){
        getProjectMortgages().clear();
        getProjectMortgages().add(projectMortgage);
    }

    @Transient
    public BusinessProject getBusinessProject() {
        if (getBusinessProjects().isEmpty()) {
            return null;
        } else {
            return getBusinessProjects().iterator().next();
        }
    }

    @Transient
    public void setBusinessProject(BusinessProject businessProject) {
        getBusinessProjects().clear();
        if (businessProject != null) {
            getBusinessProjects().add(businessProject);
        }
    }


    @Transient
    public MortgaegeRegiste getMortgaegeRegiste() {
        if (getMortgaegeRegistes().isEmpty()) {
            return null;
        } else {
            return getMortgaegeRegistes().iterator().next();
        }
    }

    @Transient
    public void setMortgaegeRegiste(MortgaegeRegiste mortgaegeRegiste) {
        getMortgaegeRegistes().clear();
        getMortgaegeRegistes().add(mortgaegeRegiste);
    }


    @Transient
    public List<BusinessMoney> getBusinessMoneyList() {
        List<BusinessMoney> result = new ArrayList<BusinessMoney>(getBusinessMoneys());
        Collections.sort(result, OrderBeanComparator.getInstance());
        return result;
    }

    @Transient
    public List<MakeCard> getMakeCardList() {
        List<MakeCard> result = new ArrayList<MakeCard>(getMakeCards());
        Collections.sort(result, new Comparator<MakeCard>() {
            @Override
            public int compare(MakeCard o1, MakeCard o2) {
                int result = Integer.valueOf(o1.getType().ordinal()).compareTo(o2.getType().ordinal());
                if (result == 0){
                    return o1.getNumber().compareTo(o2.getNumber());
                }
                return result;
            }
        });
        return result;
    }

    @Transient
    public HouseBusiness getSingleHoues() {
        Set<HouseBusiness> houseBusinesses = getHouseBusinesses();
        if (houseBusinesses.size() > 1) {
            throw new IllegalArgumentException("HouseBusiness count > 1 :" + getId());
        } else if (houseBusinesses.size() == 1) {
            return houseBusinesses.iterator().next();
        } else
            return null;

    }




    @Transient
    public HouseBusiness getHouseBusiness(String houseCode){
        Set<HouseBusiness> houseBusinesses = getHouseBusinesses();
        for (HouseBusiness houseBusiness: houseBusinesses){
            if (houseBusiness.getHouseCode().equals(houseCode)){
                return houseBusiness;
            }
        }
        return null;
    }


    /**
     * 申请人
     */
    @Transient
    public BusinessPersion getApplyPersion() {
        return getBusinessPersion(BusinessPersion.PersionType.CORRECT);
    }

    /**
     * 抵押权人代理人
     */
    @Transient
    public BusinessPersion getMortgageObligee() {
        return getBusinessPersion(BusinessPersion.PersionType.MORTGAGE_OBLIGEE);

    }

    /**
     * 抵押人代理人
     */
    @Transient
    public BusinessPersion getMortgage() {
        return getBusinessPersion(BusinessPersion.PersionType.MORTGAGE);

    }

    /**
     * 债务人
     */
    @Transient
    public BusinessPersion getMortgageObligor() {
        return getBusinessPersion(BusinessPersion.PersionType.MORTGAGE_OBLIGOR);
    }


    /**
     * 测绘公司
     */
    @Transient
    public MappingCorp getMappingCorp() {
        if (!getMappingCorps().isEmpty()) {
            return getMappingCorps().iterator().next();
        }
        return null;
    }

    /**
     * 备案人
     */
    @Transient
    public ContractOwner getContractOwner() {
        if (!getContractOwners().isEmpty()) {
            return getContractOwners().iterator().next();
        }
        return null;
    }

    /**
     * 评估公司
     */
    @Transient
    public Evaluate getEvaluate() {
        if (!getEvaluates().isEmpty()) {
            return getEvaluates().iterator().next();
        }
        return null;
    }

    /**
     * 交易信息
     */
    @Transient
    public SaleInfo getSaleInfo() {
        return getSingleHoues().getAfterBusinessHouse().getSaleInfo();
    }

    /**
     * 查封信息
     */
    @Transient
    public CloseHouse getCloseHouse() {

        if (!getCloseHouses().isEmpty()) {

            return getCloseHouses().iterator().next();
        }
        return null;
    }
    /**
     * 解封封信息
     */
    @Transient
    public HouseCloseCancel getHouseCloseCancel() {
        if (!getHouseCloseCancels().isEmpty()) {
            return getHouseCloseCancels().iterator().next();
        }
        return null;
    }


    @Transient
    public BusinessEmp getOperEmp(BusinessEmp.EmpType empType){
        for(BusinessEmp emp: getBusinessEmps()){
            if(emp.getType().equals(empType)){
                return emp;
            }
        }
        return null;
    }
    /**
     *
     * @return 共有权证数量
     */
    @Transient
    public List<MakeCard> getPoolRship(){
        List<MakeCard> poolMarkCards = new ArrayList<MakeCard>();
        for(MakeCard makeCard:getMakeCards()){
            if (makeCard.getType().equals(MakeCard.CardType.POOL_RSHIP)){
                poolMarkCards.add(makeCard);
            }
        }
        return poolMarkCards;
    }

    /**
     *
     * @return 审核人
     */
    @Transient
    public BusinessEmp getCheckEmp(){
        return getOperEmp(BusinessEmp.EmpType.CHECK_EMP);
    }

    /**
     *
     * @return 登簿人
     */
    @Transient
    public BusinessEmp getRegisterEmp(){
        return getOperEmp(BusinessEmp.EmpType.REG_EMP);
    }

    /**
     *
     * @return 制证人
     */
    @Transient
    public BusinessEmp getCardPrinterEmp(){
        return getOperEmp(BusinessEmp.EmpType.CARD_PRINTER);
    }

    /**
     *
     * @return 建立业务人
     */
    @Transient
    public BusinessEmp getCreateEmp(){
        return getOperEmp(BusinessEmp.EmpType.CREATE_EMP);
    }
    /**
     *
     * @return 归档人
     */
    @Transient
    public BusinessEmp getRecordEmp(){
        return getOperEmp(BusinessEmp.EmpType.RECORD_EMP);
    }





    /**
     *
     * @return 商品房初始登记多房屋中的其中一个房屋信息
     */
    @Transient
    public BusinessHouse getBusinessHouse(){
        return getHouseBusinesses().iterator().next().getAfterBusinessHouse();
    }

    /**
     *
     * @return 受理人
     */
    @Transient
    public BusinessEmp getApplyEmp(){
        return getOperEmp(BusinessEmp.EmpType.APPLY_EMP);
    }


    @Transient
    public Reason getReason(Reason.ReasonType type){
        for(Reason reason: getReasons()){
            if (reason.getType().equals(type)){
                return reason;
            }
        }
        return null;
    }

    @Transient
    public BusinessPersion getBusinessPersion(BusinessPersion.PersionType type){
        for (BusinessPersion businessPersion: getBusinessPersions()){
            if(businessPersion.getType().equals(type)){
                return businessPersion;
            }
        }
        return null;
    }

    @Transient
    public BusinessPersion getSellEntrust(){
        return getBusinessPersion(BusinessPersion.PersionType.SELL_ENTRUST);
    }

    @Transient
    public BusinessPersion getOwnerEntrust(){
        return getBusinessPersion(BusinessPersion.PersionType.OWNER_ENTRUST);
    }


    @Transient
    public BusinessPersion getPreSaleEntrust(){
        return getBusinessPersion(BusinessPersion.PersionType.PRE_SALE_ENTRUST);
    }

    @Transient
    public Reason getReceive(){
        return getReason(Reason.ReasonType.RECEIVE);
    }
    @Transient
    public Reason getHighDebtor(){
        return getReason(Reason.ReasonType.High_DEBTOR);
    }
    @Transient
    public Reason getFillChange(){
        return getReason(Reason.ReasonType.FILL_CHANGE);
    }

    /**
     * 异议事项
     */
    @Transient
    public Reason getDifficulty(){
        return getReason(Reason.ReasonType.DIFFICULTY);
    }

    @Transient
    public Reason getContractReason(){
        return getReason(Reason.ReasonType.CONTRACT);
    }

    /**
     * 注销原因
     */
    @Transient
    public Reason getLogout(){
        return getReason(Reason.ReasonType.LOGOUT);
    }

    @Transient
    public Reason getChangBeforReason(){
        return getReason(Reason.ReasonType.CHANG_BEFOR_RESON);
    }

    @Transient
    public Reason getChangAfterReason(){
        return getReason(Reason.ReasonType.CHANG_AFTER_RESON);
    }

    @Transient
    public Reason getModifyBeforReason(){
        return getReason(Reason.ReasonType.MODIFY_BEFOR_RENSON);
    }
    @Transient
    public Reason getModifyAfterReason(){
        return getReason(Reason.ReasonType.MODIFY_AFTER_RENSON);
    }

    /**
     * 发证信息
     */
    @Transient
    public GiveCard getGiveCard(){
        if (!getGiveCards().isEmpty()){
            return getGiveCards().iterator().next();
        }
        return null;
    }
}