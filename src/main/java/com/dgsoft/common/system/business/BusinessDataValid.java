package com.dgsoft.common.system.business;


/**
 * Created by cooper on 6/25/15.
 */
public interface BusinessDataValid {


    public abstract ValidResult valid(Object data);


    public class ValidResult {

        private String msgKey;

        private Object[] params;

        private TaskSubscribeComponent.ValidResult result;

        public ValidResult(TaskSubscribeComponent.ValidResult result) {
            this.result = result;
        }

        public ValidResult(String msgKey, TaskSubscribeComponent.ValidResult result, Object... params) {
            this.msgKey = msgKey;
            this.result = result;
            this.params = params;
        }

        public String getMsgKey() {
            return msgKey;
        }

        public TaskSubscribeComponent.ValidResult getResult() {
            return result;
        }

        public Object[] getParams() {
            return params;
        }
    }
}
