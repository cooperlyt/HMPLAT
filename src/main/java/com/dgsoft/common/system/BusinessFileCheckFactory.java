package com.dgsoft.common.system;

import java.io.IOException;

/**
 * Created by cooper on 1/31/16.
 */
public class BusinessFileCheckFactory {

    public enum CheckType{
        FTP,LOCAL
    }

    public static BusinessFileOperation getFileOperation(String businessId) throws IOException {
        if(CheckType.FTP.name().equals(RunParam.instance().getStringParamValue("BUSINESS_FILE_CHECK_TYPE"))){
            return new BusinessFtpFile(businessId);
        }else{
            return new BusinessLocalFile(businessId);
        }
    }

}
