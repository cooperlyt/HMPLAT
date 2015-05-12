package com.dgsoft.common.system.business;

/**
 * Created by cooper on 8/25/14.
 */
public interface TaskSubscribeComponent {

    public enum ValidResult{
        SUCCESS(1),
        INFO(2),
        WARN(3),
        ERROR(4),
        FATAL(5);

        private int pri;

        private ValidResult(int pri){
            this.pri = pri;
        }

        public int getPri() {
            return pri;
        }
    }

    public abstract void initSubscribe();


    public abstract ValidResult validSubscribe();


    public abstract boolean saveSubscribe();



}
