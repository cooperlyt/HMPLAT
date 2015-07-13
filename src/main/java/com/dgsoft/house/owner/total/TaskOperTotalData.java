package com.dgsoft.house.owner.total;

import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.log.Logging;

import java.util.logging.LoggingMXBean;

/**
 * Created by cooper on 7/13/15.
 */
public class TaskOperTotalData implements Comparable<TaskOperTotalData>{

    private Integer timeBlock;

    private Long count;

    private TaskOper.OperType operType;

    public TaskOperTotalData(Integer timeBlock, Long count, TaskOper.OperType operType) {

        this.timeBlock = timeBlock;
        this.count = count;
        this.operType = operType;
    }


    public Integer getTimeBlock() {

        return timeBlock;
    }

    public Long getCount() {
        return count;
    }

    public TaskOper.OperType getOperType() {
        return operType;
    }

    @Override
    public int compareTo(TaskOperTotalData o) {
        return timeBlock.compareTo(o.getTimeBlock());
    }
}
