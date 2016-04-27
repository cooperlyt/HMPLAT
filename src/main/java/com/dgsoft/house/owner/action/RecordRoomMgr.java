package com.dgsoft.house.owner.action;

import com.dgsoft.common.CalendarBean;
import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.house.owner.business.OwnerBusinessFileBase;
import com.dgsoft.house.owner.model.BusinessFile;
import com.dgsoft.house.owner.model.RecordStore;
import com.dgsoft.house.owner.model.UploadFile;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * Created by cooper on 4/17/16.
 */
@Name("recordRoomMgr")
public class RecordRoomMgr implements java.io.Serializable{

    @In(create = true)
    private EntityManager ownerEntityManager;
    @In
    private AuthenticationInfo authInfo;


    public class RecordBox{

        private String volume;

        private String frame;

        private String cabinet;

        private String box;

        private String recordNumber;

        public RecordBox(String frame, String cabinet, String box, List<BusinessFile> businessFiles) {
            this(businessFiles);
            this.frame = frame;
            this.cabinet = cabinet;
            this.box = box;
        }

        public RecordBox( String frame, String cabinet, String box, String volume, List<BusinessFile> businessFiles) {
            this(businessFiles);
            this.volume = volume;
            this.frame = frame;
            this.cabinet = cabinet;
            this.box = box;
        }

        private RecordBox(List<BusinessFile> businessFiles) {
            Map<RecordStore,RecordVolume> volumeMap = new HashMap<RecordStore,RecordVolume>();
            for(BusinessFile businessFile: businessFiles){
                RecordStore recordStore = businessFile.getRecordStore();
                RecordVolume volume = volumeMap.get(recordStore);
                if (volume == null){
                    volume = new RecordVolume(recordStore,recordStore.getCreateTime());
                    volumeMap.put(recordStore,volume);
                }
                volume.putFile(businessFile);
            }
            recordVolumes = new ArrayList<RecordVolume>(volumeMap.values());
            Collections.sort(recordVolumes);
        }

        private List<RecordVolume> recordVolumes;

        public boolean isEmptyBox(){
            return recordVolumes.isEmpty();
        }

        public String getTreeData() {
            try {
                JSONArray child = new JSONArray();
                for (RecordVolume volume: recordVolumes){
                    child.put(volume.getNodeJsonData());
                }
                if (child.length() <= 0){
                    return null;
                }

                JSONObject state = new JSONObject();
                state.put("selected",true);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("text",frame + "架" + cabinet + "柜" + box + "盒");
                jsonObject.put("nodes",child);

                jsonObject.put("nodeType","box");
                if (activeId == null)
                    jsonObject.put("state",state);

                return "[" + jsonObject.toString() + "]";
            } catch (JSONException e) {
                Logging.getLog(getClass()).debug("tree data error",e);
                throw  new IllegalArgumentException(e.getMessage(),e);
            }
        }

    }

    public class RecordVolume implements Comparable<RecordVolume>{

        private RecordStore recordStore;

        private Date volumeDate;

        public RecordVolume(RecordStore recordStore,Date volumeDate) {
            this.recordStore = recordStore;
            this.volumeDate = volumeDate;
        }

        public void putFile(BusinessFile businessFile){
            files.add(businessFile);
        }

        private  List<BusinessFile> files = new ArrayList<BusinessFile>();

        public List<BusinessFile> getFiles() {
            Collections.sort(files, OrderBeanComparator.getInstance());
            return files;
        }

        public JSONObject getNodeJsonData() throws JSONException {

            JSONObject result = new JSONObject();
            JSONArray child = new JSONArray();

            boolean childActive = false;
            for(BusinessFile businessFile: getFiles()){
                JSONObject node = new JSONObject();
                node.put("text", businessFile.getName());
                node.put("files",genFileDataArray(businessFile.getUploadFileList()));
                node.put("nodeType","file");
                node.put("id",businessFile.getId());
                node.put("volumeId",recordStore.getId());

                if (activeId != null && activeId.equals(businessFile.getId())){
                    JSONObject state = new JSONObject();
                    state.put("selected",true);
                    node.put("state",state);
                    childActive = true;
                }

                child.put(node);
            }
            result.put("text",recordStore.getRecordCode());
            result.put("nodes",child);
            result.put("nodeType","volume");
            result.put("volumeId",recordStore.getId());
            if (activeId != null && activeId.equals(recordStore.getId())){
                JSONObject state = new JSONObject();
                state.put("selected",true);
                result.put("state",state);
            }
            if (childActive){
                JSONObject state = new JSONObject();
                state.put("expanded",true);
                result.put("state",state);
            }
            return result;

        }

        private JSONArray genFileDataArray(List<UploadFile> files) throws JSONException {
            JSONArray result = new JSONArray();
            for(UploadFile file: files){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fid",file.getId());
                jsonObject.put("frame",file.getBusinessFile().getRecordLocal().getFrame());
                jsonObject.put("cabinet",file.getBusinessFile().getRecordLocal().getCabinet());
                jsonObject.put("box",file.getBusinessFile().getRecordLocal().getBox());
                jsonObject.put("emp",file.getEmpName());

                jsonObject.put("time",CalendarBean.instance().displayDateTime(file.getUploadTime()));
                jsonObject.put("title",file.getBusinessFile().getName());
                jsonObject.put("description", '由' +  file.getEmpName() + '于' + CalendarBean.instance().displayDateTime(file.getUploadTime()) + "上传");

                result.put(jsonObject);
            }

            return result;
        }

        @Override
        public int compareTo(RecordVolume o) {
            int result = volumeDate.compareTo(o.volumeDate);
            if (result == 0)
                return recordStore.getRecordCode().compareTo(o.recordStore.getRecordCode());
            else
                return result;
        }
    }

    public enum SearchType{
        RECORD_LOCATION("档案位置"),RECORD_NUMBER("档案编号");

        private String label;

        public String getLabel() {
            return label;
        }

        SearchType(String label) {
            this.label = label;
        }
    }

    private SearchType searchType = SearchType.RECORD_LOCATION;

    private String recordNumber;

    private String frame;

    private String cabinet;

    private String box;

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String activeId;

    public String replacePushAddress;

    public String addPushAddress;

    public String fileUploadData;

    public String getReplacePushAddress() {
        if (replacePushAddress  == null){
            return null;
        }else
        return replacePushAddress.replace(",","");
    }

    public void setReplacePushAddress(String replacePushAddress) {
        this.replacePushAddress = replacePushAddress;
    }

    public String getAddPushAddress() {
        return addPushAddress;
    }

    public void setAddPushAddress(String addPushAddress) {
        this.addPushAddress = addPushAddress;
    }

    public String getFileUploadData() {
        return fileUploadData;
    }

    public void setFileUploadData(String fileUploadData) {
        this.fileUploadData = fileUploadData;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        if (isDirty(this.frame,frame) ){
            businessFiles = null;
        }
        this.frame = frame;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        if (isDirty(this.cabinet,cabinet) ){
            businessFiles = null;
        }
        this.cabinet = cabinet;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        if (isDirty(this.box,box) ){
            businessFiles = null;
        }

        this.box = box;
    }

    protected <U> boolean isDirty(U oldValue, U newValue)
    {
        boolean attributeDirty = oldValue!=newValue && (
                oldValue==null ||
                        !oldValue.equals(newValue)
        );
        return attributeDirty;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        if (searchType != null) {
            if (!this.searchType.equals(searchType)){
                businessFiles = null;
            }
            this.searchType = searchType;
        }
    }

    public void setSearchTypeStr(String value){
        if (value == null || value.trim().equals("")){
            searchType = null;
        }else{
            searchType = SearchType.valueOf(value);
        }
    }

    public String getSearchTypeStr(){
        if (searchType == null){
            return null;
        }
        return searchType.name();
    }

    public SearchType[] getAllSearchTypes(){
        return SearchType.values();
    }


    public RecordBox getResultBox() {

        if (SearchType.RECORD_LOCATION.equals(searchType)){

            return new RecordBox(frame,cabinet,box,getResultFiles());

        }else{
            return null;
        }
    }


    private List<BusinessFile> businessFiles;



    private List<BusinessFile> getResultFiles(){

        if (businessFiles == null){
            businessFiles = ownerEntityManager.createQuery("select businessFile from BusinessFile businessFile left join fetch businessFile.recordLocal location left join fetch businessFile.recordStore where location.frame =:frame and location.cabinet = :cabinet and location.box = :box",BusinessFile.class)
                    .setParameter("frame",frame).setParameter("cabinet",cabinet).setParameter("box",box).getResultList();
        }
        return businessFiles;

    }

    public void refresh(){
        businessFiles = null;
    }

    public void reset(){
        frame = null;
        cabinet = null;
        box = null;
        recordNumber = null;
        businessFiles = null;
    }

    @Transactional
    public void addFile(){
        try {
            JSONObject jsonObject = new JSONObject(fileUploadData);


            String key = jsonObject.getString("key");



            for (BusinessFile businessFile: getResultFiles()){
                Logging.getLog(getClass()).debug(businessFile.getId() + "?=" + key);
                if (businessFile.getId().equals(key)){
                    Logging.getLog(getClass()).debug("key found:" + key);
                    assignFile(businessFile,jsonObject);
                    return;
                }
            }
            Logging.getLog(getClass()).debug("key not found" + key);

        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void assignFile(BusinessFile businessFile , JSONObject jsonObject) throws JSONException {
        JSONArray fileIdArray = jsonObject.getJSONArray("files");
        if (fileIdArray.length() > 0) {

            for (int i = 0; i < fileIdArray.length(); i++) {
                JSONObject fileInfo = fileIdArray.getJSONObject(i);

                businessFile.getUploadFiles().add(new UploadFile(fileInfo.getString("fid"), authInfo.getLoginEmployee().getPersonName(), authInfo.getLoginEmployee().getId(), fileInfo.getString("md5"), fileInfo.getString("name"), businessFile, fileInfo.getLong("size")));

            }
            activeId = businessFile.getId();
            ownerEntityManager.flush();
        }
    }


    //TODO HISTORY
    public void replaceFile(){

        try {
            JSONObject jsonObject = new JSONObject(fileUploadData);


            String key = jsonObject.getString("key");

            for (BusinessFile businessFile: getResultFiles()){
                for(UploadFile uploadFile: businessFile.getUploadFiles()){
                    if (uploadFile.getId().replace(",","").equals(key)){
                        businessFile.getUploadFiles().remove(uploadFile);
                        assignFile(businessFile,jsonObject);
                        return;
                    }
                }

            }


        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
