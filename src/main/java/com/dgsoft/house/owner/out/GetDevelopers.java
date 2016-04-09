package com.dgsoft.house.owner.out;

import com.dgsoft.common.helper.JsonDataProvider;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Created by cooper on 4/9/16.
 */

@Name("getDevelopers")
@Scope(ScopeType.STATELESS)
public class GetDevelopers implements JsonDataProvider.JsonDataProviderFunction{
    @Override
    public String getJsonData() {
        return null;
    }
}
