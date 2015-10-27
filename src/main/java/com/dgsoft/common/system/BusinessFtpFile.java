package com.dgsoft.common.system;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by cooper on 10/26/15.
 */
public class BusinessFtpFile implements BusinessFileOperation{

    private FTPClient ftpClient;

    private String path;

    public BusinessFtpFile(String businessId) throws IOException {
        //InetAddress.
        //InetAddress addr = new InetAddress(RunParam.instance().getStringParamValue("businessFile.address"),);
        ftpClient = new FTPClient();


        ftpClient.connect(RunParam.instance().getStringParamValue("businessFile.address"), RunParam.instance().getIntParamValue("businessFile.port"));
        ftpClient.login(RunParam.instance().getStringParamValue("businessFile.userName"), RunParam.instance().getStringParamValue("businessFile.password"));
        ftpClient.setControlEncoding("UTF-8");
            //ftpClient.setBinaryType();
        ftpClient.enterLocalPassiveMode();
        ftpClient.changeWorkingDirectory(RunParam.instance().getStringParamValue("businessFile.rootDir"));
        ftpClient.changeWorkingDirectory(businessId);

        path = RunParam.instance().getStringParamValue("businessFile.rootDir") + "/" + businessId + "/";
    }


    public void close() throws IOException {
        ftpClient.logout();
        ftpClient.disconnect();
    }

    public List<String> listFiles(String dir) throws  IOException {



            List<String> result = new ArrayList<String>();

            for (FTPFile enty : ftpClient.listFiles(dir,new FTPFileFilter() {
                @Override
                public boolean accept(FTPFile file) {
                    return file.getType() == FTPFile.FILE_TYPE && !file.getName().endsWith(".thumb.jpg");
                }
            })) {

                result.add(path + dir + "/" + enty.getName());
            }

            return result;


    }



}
