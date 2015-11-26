package com.dgsoft.common.exception;

import org.jboss.seam.annotations.ApplicationException;

/**
 * Created by cooper on 11/26/15.
 */
@ApplicationException()
public class TaskOutException extends IllegalStateException {

    public TaskOutException() {
        super();
    }
}
