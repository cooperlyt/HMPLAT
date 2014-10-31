package com.dgsoft.house.action;

import com.dgsoft.house.HouseEntityLoader;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.List;

/**
 * Created by cooper on 10/31/14.
 */
@Name("pinyiList")
public class PinyiList {

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    public List<String> sectionAutoComplete(String prefix){
       return houseEntityLoader.getEntityManager().createQuery("select section.name from Section section where (lower(section.pyCode) like lower(concat('%',:prefix,'%'))) or (lower(section.name) like lower(concat('%',:prefix,'%'))) ",String.class).
               setParameter("prefix",prefix).getResultList();
    }

}
