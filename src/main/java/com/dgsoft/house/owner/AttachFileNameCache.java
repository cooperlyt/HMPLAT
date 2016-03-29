package com.dgsoft.house.owner;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * Created by cooper on 3/29/16.
 */
@Name("attachFileNameCache")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class AttachFileNameCache {

    @In(create = true)
    private EntityManager ownerEntityManager;

    private Set<String> names;

    public Set<String> getNames() {
        if (names == null){
            names = new HashSet<String>(ownerEntityManager.createQuery("select distinct f.name from BusinessFile f where f.important = false and f.ownerBusiness.status in ('COMPLETE','COMPLETE_CANCEL')", String.class).getResultList());
        }
        return names;
    }

    public List<String> getNameList(){
        List<String> result = new ArrayList<String>(getNames());
        Collections.sort(result);
        return result;
    }

    public void putName(String name){
        getNames().add(name);
    }

    public String getAttachFileAutoComplete(){
        String otherFileAutoComplete = "";
        for(String name: getNameList()){
            if (!"".equals(otherFileAutoComplete)){
                otherFileAutoComplete += ",";
            }
            otherFileAutoComplete += "'" + name + "'";
        }
        return "[" + otherFileAutoComplete + "]";
    }

    public void refresh(){
        names = null;
    }


}
