package com.dgsoft.common.system.action;

import com.dgsoft.common.system.SystemEntityHome;
import com.dgsoft.common.system.business.Subscribe;
import com.dgsoft.common.system.model.SubscribeGroup;
import com.dgsoft.common.system.model.ViewSubscribe;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 3/2/15.
 */
@Name("subscribeGroupHome")
public class SubscribeGroupHome extends SystemEntityHome<SubscribeGroup> {


    public int getMaxPriority(Subscribe.SubscribeType type){
        int result = 0;
        for (ViewSubscribe subscribe: getInstance().getViewSubscribes()){
            if (type.equals(subscribe.getType()) && (subscribe.getPriority() > result)){
                 result = subscribe.getPriority();
            }
        }
        return result;
    }


}
