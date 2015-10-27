package com.dgsoft.common.system;

import com.dgsoft.common.exception.FileOperException;

import java.util.List;

/**
 * Created by cooper on 10/26/15.
 */
public interface BusinessFileOperation {


     void close() throws FileOperException;

    List<String> listFiles(String dir) throws FileOperException;



}
