package com.dgsoft.common.system.action;

import com.dgsoft.common.system.model.CreateComponent;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 7/18/15.
 */
@Name("dataValidComponentConfig")
public class DataValidComponentConfig extends CreateComponentConfig{

    @Override
    protected CreateComponent.CreateComponentType getType() {
        return CreateComponent.CreateComponentType.DATA_VALID;
    }
}
