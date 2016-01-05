package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessFile;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.log.Logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by cooper on 1/4/16.
 */
@Name("recordCoverPrint")
@Scope(ScopeType.CONVERSATION)
public class RecordCoverPrint {

    public static class TempStore{

        private BusinessFile businessFile;

        private int pageCount;

        private String memo;

        public TempStore(BusinessFile businessFile) {
            this.businessFile = businessFile;
            if (businessFile.getUploadFiles().size() > 0){
                pageCount = businessFile.getUploadFiles().size();
            }
        }

        public BusinessFile getBusinessFile() {
            return businessFile;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }

    @In()
    private OwnerBusinessHome ownerBusinessHome;

    private List<TempStore> fileList;

    private List<TempStore> genPageCover(){
        List<TempStore> result = new ArrayList<TempStore>();

        return result;
    }

    public void refresh(){
        fileList = null;
    }


    @DataModelSelection
    private TempStore selected;

    public void up(){
        int i = fileList.indexOf(selected);
        if (i > 0){
            fileList.remove(selected);
            fileList.set(i - 1, selected);
        }
    }

    public void down(){
        int i = fileList.indexOf(selected);
        if (i < (fileList.size() - 1)){
            fileList.remove(selected);
            fileList.set(i + 1, selected);
        }
    }

    public void top(){
        fileList.remove(selected);
        fileList.set(0, selected);
    }

    public void last(){
        fileList.remove(selected);
        fileList.set(fileList.size() - 1, selected);
    }

    @DataModel("recordCoverItems")
    public List<TempStore> getFileList() {
        Logging.getLog(getClass()).debug("------------------- getRecord Cover");
        if (fileList == null){
            fileList = new ArrayList<TempStore>();
            for(BusinessFile businessFile: ownerBusinessHome.getInstance().getUploadFileses()){
                fileList.add(new TempStore(businessFile));
            }
            Collections.sort(fileList, new Comparator<TempStore>() {
                @Override
                public int compare(TempStore o1, TempStore o2) {
                    if (o1.getBusinessFile().isImportant() == o2.getBusinessFile().isImportant()) {
                        return Integer.valueOf(o1.getBusinessFile().getPriority()).compareTo(o2.getBusinessFile().getPriority());
                    }else{
                        return Boolean.valueOf(o1.getBusinessFile().isImportant()).compareTo(o2.getBusinessFile().isImportant());
                    }
                }
            });
        }
        return fileList;
    }




}
