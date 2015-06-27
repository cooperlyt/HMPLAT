package com.dgsoft.common.system.business;

import java.util.List;

/**
 * Created by cooper on 6/27/15.
 */
public interface BusinessPickSelect {

    public abstract List<? extends BusinessInstance> getAllowSelectBusiness(Object data);
}
