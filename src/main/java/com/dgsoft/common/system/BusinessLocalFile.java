package com.dgsoft.common.system;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 1/31/16.
 */
public class BusinessLocalFile implements BusinessFileOperation {

    private String businessId;

    public BusinessLocalFile(String businessId) {
        this.businessId = businessId;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public List<String> listFiles(String dir) throws IOException {

        String path = RunParam.instance().getStringParamValue("businessFile.rootDir") + "/" + businessId + "/" + dir + "/";

        List<String> result = new ArrayList<String>();

        File file = new File(RunParam.instance().getStringParamValue("LOCAL_FILE_UPLOAD_PATH") + businessId + "/" + dir + "/");
        for (File f: file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".thumb.jpg");
            }
        })){
            //Logging.getLog(getClass()).debug(f.getName());
            result.add(path + f.getName());
        }

        return result;
    }
}
