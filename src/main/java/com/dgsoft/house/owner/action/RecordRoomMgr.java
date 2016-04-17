package com.dgsoft.house.owner.action;

import com.dgsoft.common.BatchOperData;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.owner.model.BusinessFile;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.List;

/**
 * Created by cooper on 4/17/16.
 */
@Name("recordRoomMgr")
public class RecordRoomMgr implements java.io.Serializable{

    @In(create = true)
    private RunParam runParam;

    public static class RecordBox{

        private List<RecordVolume> recordVolumes;

        private boolean active;




    }

    public static class RecordVolume{

        private  List<BatchOperData<BusinessFile>> files;

        private boolean active;

        public List<BatchOperData<BusinessFile>> getFiles() {
            return files;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
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


    private RecordBox resultBox;

    public RecordBox getResultBox() {
        if (!runParam.getBooleanParamValue("recordRoom.enable")) {
            if (SearchType.RECORD_LOCATION.equals(searchType)) {
                if ((getFrame() == null || "".equals(getFrame().trim()))
                        || (getCabinet() == null || "".equals(getCabinet().trim())) ||
                        (getBox() == null || "".equals(getBox().trim()))){
                    return null;
                }
            }else{
                if (getRecordNumber() == null || "".equals(getRecordNumber().trim())){
                    return null;
                }
            }
        }

        if (resultBox == null){
            initResultBox();
        }

        return resultBox;
    }


    private void initResultBox(){

    }

    public void refresh(){
        resultBox = null;
    }

    public void reset(){
        frame = null;
        cabinet = null;
        box = null;
        recordNumber = null;
        resultBox = null;
    }

}
