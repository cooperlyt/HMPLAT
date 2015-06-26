package com.dgsoft.common.system.business;

import org.jboss.seam.international.StatusMessage;

import javax.faces.application.FacesMessage;

/**
 * Created by cooper on 8/25/14.
 */
public interface TaskSubscribeComponent {

    public enum ValidResult{


        SUCCESS(1, StatusMessage.Severity.INFO),
        INFO(2, StatusMessage.Severity.INFO),
        WARN(3, StatusMessage.Severity.WARN),
        ERROR(4, StatusMessage.Severity.ERROR),
        FATAL(5, StatusMessage.Severity.FATAL);

        private int pri;
        private StatusMessage.Severity severity;

        private ValidResult(int pri,StatusMessage.Severity severity){
            this.pri = pri;
            this.severity = severity;
        }

        public int getPri() {
            return pri;
        }

        public StatusMessage.Severity getSeverity() {
            return severity;
        }
    }

    public abstract void initSubscribe();


    public abstract ValidResult validSubscribe();


    public abstract boolean saveSubscribe();



}
