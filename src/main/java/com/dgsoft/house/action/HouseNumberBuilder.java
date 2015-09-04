package com.dgsoft.house.action;

import com.dgsoft.common.DataFormat;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.HouseNumberPool;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.contexts.Contexts;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cooper on 12/27/14.
 */
@Name("houseNumberBuilder")
public class HouseNumberBuilder {

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @Transactional
    public String useNumber(String type, int length){
        long number = useNumber(type);
        StringBuffer result =  new StringBuffer(String.valueOf(number));
        while (result.length() < length){
            result.insert(0,'0');
        }
        return result.toString();
    }

    @Transactional
    public long useNumber(String type){
        HouseNumberPool op =  houseEntityLoader.getEntityManager().find(HouseNumberPool.class,type);
        if (op == null){
            op = new HouseNumberPool(type,1, HouseNumberPool.NumberType.FLOW_NUMBER,new Date());
            houseEntityLoader.getEntityManager().persist(op);
            return 1;
        }else{
            op.setNumber(op.getNumber() + 1);
            return op.getNumber();
        }
    }


    @Transactional
    public String useDayNumber(String type){
        long result;
        HouseNumberPool op =  houseEntityLoader.getEntityManager().find(HouseNumberPool.class,type);
        if (op == null){
            op = new HouseNumberPool(type,1, HouseNumberPool.NumberType.DAY_NUMBER,new Date());
            houseEntityLoader.getEntityManager().persist(op);
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



    public static HouseNumberBuilder instance()
    {
        if ( !Contexts.isEventContextActive() )
        {
            throw new IllegalStateException("no active event context");
        }
        return (HouseNumberBuilder) Component.getInstance(HouseNumberBuilder.class, true);
    }
}
