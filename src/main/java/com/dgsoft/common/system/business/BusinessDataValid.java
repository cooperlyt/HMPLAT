package com.dgsoft.common.system.business;


import org.jboss.seam.international.StatusMessage;

/**
 * Created by cooper on 6/25/15.
 */
public interface BusinessDataValid {


    public abstract ValidResult valid(Object data);

    public enum ValidResultLevel {

        SUCCESS(1, StatusMessage.Severity.INFO),
        INFO(2, StatusMessage.Severity.INFO),
        WARN(3, StatusMessage.Severity.WARN),
        ERROR(4, StatusMessage.Severity.ERROR),
        FATAL(5, StatusMessage.Severity.FATAL);

        private int pri;
        private StatusMessage.Severity severity;

        private ValidResultLevel(int pri, StatusMessage.Severity severity){
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


    public static class ValidResult {

        private String msgKey;

        private Object[] params;

        private ValidResultLevel result;

        public ValidResult(ValidResultLevel result) {
            this.result = result;
        }

        public ValidResult(String msgKey, ValidResultLevel result, Object... params) {
            this.msgKey = msgKey;
            this.result = result;
            this.params = params;
        }

        public String getMsgKey() {
            return msgKey;
        }

        public ValidResultLevel getResult() {
            return result;
        }

        public Object[] getParams() {
            return params;
        }
    }
}
