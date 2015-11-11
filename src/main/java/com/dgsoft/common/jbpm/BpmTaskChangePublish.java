package com.dgsoft.common.jbpm;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.bpm.Actor;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;
import org.richfaces.application.push.TopicKey;
import org.richfaces.application.push.TopicsContext;


import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 6/6/13
 * Time: 9:41 AM
 */
@Name("bpmTaskChangePublish")
@AutoCreate
@Scope(ScopeType.APPLICATION)
@Install(precedence = Install.APPLICATION, dependencies = "org.jboss.seam.bpm.jbpm")
@Synchronized(timeout = 5000)
@Startup(depends = "org.jboss.seam.bpm.jbpm")
public class BpmTaskChangePublish {

    public static final String PUSH_TASK_CHANGE_TOPIC = "pushTaskChange";

    //private List<TaskInstanceListCache> subscribers = new ArrayList<TaskInstanceListCache>();

    protected void sendTaskChangeMessage() {
        try {
            TopicKey topicKey = new TopicKey(PUSH_TASK_CHANGE_TOPIC);
            TopicsContext topicsContext = TopicsContext.lookup();
            topicsContext.publish(topicKey, "message");
        } catch (Exception e) {
            log.error("sendMessage error", e);
        }
    }


    @Logger
    private Log log;

    public BpmTaskChangePublish(){
        super();
    }

    @Transactional
    @Asynchronous
    public void onBusinessTaskChange() {
//        for(TaskInstanceListCache subscriber: subscribers){
//            //Logging.getLog(getClass()).debug("sub actor:" + subscriber.getActorId() + "=" + Actor.instance().getId());
//            //if (!subscriber.getActorId().equals(Actor.instance().getId()))
//            subscriber.refresh();
//        }
        sendTaskChangeMessage();
    }


//    public synchronized void unSubscribe(TaskInstanceListCache subscriber) {
//        log.debug("unSubscribe = Publish:" + this + "|subscriber:" + subscriber);
//        if (subscribers.contains(subscriber)) {
//            subscribers.remove(subscriber);
//        } else {
//            log.warn("unSubscribe fail! subscriber not in list");
//        }
//    }
//
//
//    public synchronized void subscribe(TaskInstanceListCache subscriber) {
//        log.debug("subscribe = Publish:" + this + "|subscriber:" + subscriber);
//        subscribers.add(subscriber);
//    }

}
