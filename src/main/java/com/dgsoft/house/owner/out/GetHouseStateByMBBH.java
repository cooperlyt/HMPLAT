package com.dgsoft.house.owner.out;

import com.dgsoft.common.helper.JsonDataProvider;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;

/**
 * Created by cooper on 4/9/16.
 */
@Name("getHouseStateByMBBH")
@Scope(ScopeType.STATELESS)
public class GetHouseStateByMBBH implements JsonDataProvider.JsonDataProviderFunction{

    @RequestParameter
    private String mapNumber;

    @RequestParameter
    private String blockNumber;

    @RequestParameter
    private String buildNumber;

    @RequestParameter
    private String houseOrder;

    @Override
    public String getJsonData() {
        return null;
    }
}
