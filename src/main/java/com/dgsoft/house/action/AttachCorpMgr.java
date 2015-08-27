package com.dgsoft.house.action;

import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 8/26/15.
 */
@Name("attachCorpMgr")
public class AttachCorpMgr {

    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
