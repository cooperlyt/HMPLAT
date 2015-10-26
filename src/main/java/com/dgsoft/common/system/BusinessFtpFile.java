package com.dgsoft.common.system;


import com.dgsoft.common.exception.FileOperException;
import org.jboss.seam.log.Logging;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpDirEntry;
import sun.net.ftp.FtpProtocolException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cooper on 10/26/15.
 */
public class BusinessFtpFile implements BusinessFileOperation{

    private FtpClient ftpClient;

    public BusinessFtpFile() throws FileOperException {

        SocketAddress addr = new InetSocketAddress(RunParam.instance().getStringParamValue("businessFile.address"), RunParam.instance().getIntParamValue("businessFile.port"));
        ftpClient = FtpClient.create();
        try {

            ftpClient.connect(addr);
            ftpClient.login(RunParam.instance().getStringParamValue("businessFile.userName"), RunParam.instance().getStringParamValue("businessFile.password").toCharArray());
            ftpClient.setBinaryType();
            ftpClient.enablePassiveMode(true);
            ftpClient.changeDirectory(RunParam.instance().getStringParamValue("businessFile.rootDir"));
        } catch (FtpProtocolException e) {
            Logging.getLog(getClass()).error(e.getMessage(), e);
            throw new FileOperException(e);
        } catch (IOException e) {
            Logging.getLog(getClass()).error(e.getMessage(), e);
            throw new FileOperException(e);
        }
    }


    public void close() throws FileOperException {
        try {
            ftpClient.close();
        } catch (IOException e) {
            Logging.getLog(getClass()).error(e.getMessage(), e);
            throw new FileOperException(e);
        }
    }

    public List<String> listFiles(String dir) throws FileOperException {
        try {

            List<String> result = new ArrayList<String>();
            Iterator<FtpDirEntry> it = ftpClient.listFiles(dir);
            while (it.hasNext()){
                FtpDirEntry entry = it.next();
                if (FtpDirEntry.Type.FILE.equals(entry.getType()) && !entry.getName().endsWith(".thumb.jpg")){
                    result.add(entry.getName());
                }
            }
            return result;

        } catch (FtpProtocolException e) {
            Logging.getLog(getClass()).error(e.getMessage(), e);
            throw new FileOperException(e);
        } catch (IOException e) {
            Logging.getLog(getClass()).error(e.getMessage(), e);
            throw new FileOperException(e);
        }
    }


}
