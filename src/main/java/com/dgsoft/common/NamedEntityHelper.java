package com.dgsoft.common;

import org.jboss.seam.framework.EntityNotFoundException;

/**
 * Created by cooper on 8/21/14.
 */
public abstract class NamedEntityHelper<E extends NamedEntity> {

    protected abstract EntityHomeAdapter<E> getEntityHome();

    public void setPinyinSearchName(String searchStr) {
        String id = PinyinTools.splitPinyinId(searchStr);
        if (id == null) {
            if ((searchStr == null) || searchStr.trim().equals("")) {
                getEntityHome().setId(null);
            }
            return;
        }
        try {
            getEntityHome().setId(id);
        } catch (javax.persistence.EntityNotFoundException e) {
            getEntityHome().setId(null);
        }
    }

    public String getPinyinSearchName() {
        if (getEntityHome().isIdDefined()) {
            try {
                return getEntityHome().getInstance().getName();
            }catch (EntityNotFoundException e) {
                getEntityHome().setId(null);
            }
        }
        return "";
    }

}
