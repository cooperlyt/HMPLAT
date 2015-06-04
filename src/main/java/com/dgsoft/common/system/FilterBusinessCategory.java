package com.dgsoft.common.system;

import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.common.system.model.BusinessCategory;
import com.dgsoft.common.system.model.BusinessDefine;

import java.util.*;

/**
 * Created by cooper on 6/4/15.
 */
public class FilterBusinessCategory {


    private BusinessCategory category;

    private Set<BusinessDefine> businessDefines = new HashSet<BusinessDefine>();

    public FilterBusinessCategory(BusinessCategory category) {
        this.category = category;
    }

    public void putDefine(BusinessDefine define) {
        businessDefines.add(define);
    }

    public BusinessCategory getCategory() {
        return category;
    }

    public List<BusinessDefine> getDefineList() {
        List<BusinessDefine> result = new ArrayList<BusinessDefine>(businessDefines);
        Collections.sort(result, OrderBeanComparator.getInstance());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterBusinessCategory that = (FilterBusinessCategory) o;

        if (category != null ? !category.equals(that.category) : that.category != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return category != null ? category.hashCode() : 0;
    }
}
