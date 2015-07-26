package com.dgsoft.house.owner.business;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * Created by cooper on 7/25/15.
 */
@Name("taskOwnerBusinessFile")
public class TaskOwnerBusinessFile extends OwnerBusinessFile {

    @Transactional
    public void setNoFile(boolean value){
        super.setNoFile(value);
        if (getSelectNode() != null){
            ownerBusinessHome.update();
        }
    }




}
