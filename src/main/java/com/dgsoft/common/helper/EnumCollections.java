package com.dgsoft.common.helper;

import com.dgsoft.common.TimeArea;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 4/18/16.
 */
@Name("enumCollections")
public class EnumCollections {

    @Factory(value = "allTimeShowTypes", scope = ScopeType.SESSION)
    public TimeArea.TimeShowType[] getAllTimeShowTypes(){
        return TimeArea.TimeShowType.values();
    }
}
