package com.dgsoft.house.owner.business;

import com.dgsoft.common.exception.TaskOutException;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.bpm.Actor;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Created by cooper on 7/25/15.
 */
@Name("taskOwnerBusinessFile")
public class TaskOwnerBusinessFile extends OwnerBusinessFile {

    @In
    private Actor actor;

    @In
    private TaskInstance taskInstance;

    @Override
    @Transactional
    public void changeListener(){


        if (taskInstance.getActorId() == null || !taskInstance.getActorId().equals(actor.getId())){
            throw new TaskOutException();
        }
        save();
        ownerBusinessHome.update();
    }
}
