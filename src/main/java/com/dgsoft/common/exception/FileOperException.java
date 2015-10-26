package com.dgsoft.common.exception;

import org.jboss.seam.annotations.ApplicationException;

/**
 * Created by cooper on 10/26/15.
 */
@ApplicationException()
public class FileOperException extends Exception {

    public FileOperException(Throwable cause) {
        super(cause);
    }
}
