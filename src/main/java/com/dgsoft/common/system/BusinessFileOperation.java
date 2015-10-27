package com.dgsoft.common.system;

import java.io.IOException;
import java.util.List;

/**
 * Created by cooper on 10/26/15.
 */
public interface BusinessFileOperation {


     void close() throws IOException;

    List<String> listFiles(String dir) throws IOException;


}
