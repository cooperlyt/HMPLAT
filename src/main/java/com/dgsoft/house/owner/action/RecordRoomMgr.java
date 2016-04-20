package com.dgsoft.house.owner.action;

import com.dgsoft.common.CalendarBean;
import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.house.owner.model.BusinessFile;
import com.dgsoft.house.owner.model.UploadFile;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
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

    public static class RecordBox{

        public RecordBox(List<BusinessFile> businessFiles) {

            Map<String,RecordVolume> volumeMap = new HashMap<String, RecordVolume>();
            for(BusinessFile businessFile: businessFiles){
                String volumeNumber = businessFile.getRecordStore().getRecordCode();
                RecordVolume volume = volumeMap.get(volumeNumber);
                if (volume == null){
                    volume = new RecordVolume(volumeNumber,businessFile.getRecordStore().getCreateTime());
                    volumeMap.put(volumeNumber,volume);
                }
                volume.putFile(businessFile);
            }
            recordVolumes = new ArrayList<RecordVolume>(volumeMap.values());
            Collections.sort(recordVolumes);
        }

        private List<RecordVolume> recordVolumes;

        public JSONArray getTreeData() throws JSONException {
            JSONArray child = new JSONArray();
            for (RecordVolume volume: recordVolumes){
                child.put(volume.getNodeJsonData());
            }
            return child;
        }

    }

    public static class RecordVolume implements Comparable<RecordVolume>{

        private String volumeNumber;

        private Date volumeDate;

        public RecordVolume(String volumeNumber,Date volumeDate) {
            this.volumeNumber = volumeNumber;
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

            for(BusinessFile businessFile: getFiles()){
                JSONObject node = new JSONObject();
                node.put("text", businessFile.getName());
                node.put("files",genFileDataArray(businessFile.getUploadFileList()));
                child.put(node);
            }
            result.put("text",volumeNumber);
            result.put("nodes",child);
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

                result.put(jsonObject);
            }

            return result;
        }

        @Override
        public int compareTo(RecordVolume o) {
            return volumeDate.compareTo(o.volumeDate);
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

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
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

    private String resultData;

    public String getResultBox() {

        if (resultData == null){
            initResultBox();
        }

        return resultData;
    }


    private void initResultBox(){
        if (SearchType.RECORD_LOCATION.equals(searchType)){

            List<BusinessFile> files = ownerEntityManager.createQuery("select businessFile from BusinessFile businessFile left join fetch businessFile.recordLocal location left join fetch businessFile.recordStore where location.frame =:frame and location.cabinet = :cabinet and location.box = :box",BusinessFile.class)
                    .setParameter("frame",frame).setParameter("cabinet",cabinet).setParameter("box",box).getResultList();

            RecordBox recordBox = new RecordBox(files);

            JSONObject jsonObject = new JSONObject();
            try {
                JSONArray child = recordBox.getTreeData();
                if (child.length() <= 0){

                }
                jsonObject.put("text",frame + "架" + cabinet + "柜" + box + "盒");
                jsonObject.put("nodes",child);
                JSONObject state = new JSONObject();
                state.put("selected",true);
                jsonObject.put("state",state);
                resultData = "[" + jsonObject.toString() + "]";
            } catch (JSONException e) {
                Logging.getLog(getClass()).debug("tree data error",e);
                throw  new IllegalArgumentException(e.getMessage(),e);
            }

        }else{

        }
    }

    public void refresh(){
        resultData = null;
    }

    public void reset(){
        frame = null;
        cabinet = null;
        box = null;
        recordNumber = null;
        resultData = null;
    }

}