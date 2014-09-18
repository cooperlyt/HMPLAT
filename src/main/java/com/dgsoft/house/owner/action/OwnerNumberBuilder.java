package com.dgsoft.house.owner.action;

import com.dgsoft.common.DataFormat;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.OwnerNumberPool;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.contexts.Contexts;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cooper on 9/16/14.
 */
@Name("ownerNumberBuilder")
@AutoCreate
public class OwnerNumberBuilder {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @Transactional
    public long useNumber(String type){
        OwnerNumberPool op =  ownerEntityLoader.getEntityManager().find(OwnerNumberPool.class,type);
        if (op == null){
            op = new OwnerNumberPool(type,1, OwnerNumberPool.NumberType.FLOW_NUMBER,new Date());
            ownerEntityLoader.getEntityManager().persist(op);
            return 1;
        }else{
            op.setNumber(op.getNumber() + 1);
            return op.getNumber();
        }
    }


    @Transactional
    public String useDayNumber(String type){
        long result;
        OwnerNumberPool op =  ownerEntityLoader.getEntityManager().find(OwnerNumberPool.class,type);
        if (op == null){
            op = new OwnerNumberPool(type,1, OwnerNumberPool.NumberType.DAY_NUMBER,new Date());
            ownerEntityLoader.getEntityManager().persist(op);
            result = 1;
        }else{
            if (DataFormat.halfTime(new Date(op.getUseTime().getTime())).getTime() != DataFormat.halfTime(new Date()).getTime()){
                op.setUseTime(new Date());
                op.setNumber(1);
                result = 1;
            }else {
                op.setNumber(op.getNumber() + 1);
                result = op.getNumber();
            }
        }

        SimpleDateFormat numberDateformat = new SimpleDateFormat("yyyyMMdd");
        String datePart = numberDateformat.format(new Date());

        return datePart + "-" + result;
    }



    public static OwnerNumberBuilder instance()
    {
        if ( !Contexts.isEventContextActive() )
        {
            throw new IllegalStateException("no active event context");
        }
        return (OwnerNumberBuilder) Component.getInstance(OwnerNumberBuilder.class,true);
    }

}
