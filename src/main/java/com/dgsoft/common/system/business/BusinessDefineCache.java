package com.dgsoft.common.system.business;

import com.dgsoft.common.system.SystemEntityLoader;
import com.dgsoft.common.system.model.BusinessCategory;
import com.dgsoft.common.system.model.BusinessDefine;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cooper on 7/6/15.
 */
@Name("businessDefineCache")
@Scope(ScopeType.SESSION)
public class BusinessDefineCache {

    private Map<String,BusinessDefine> result;

    private Map<String,BusinessCategory> category;

    @In(create = true)
    private SystemEntityLoader systemEntityLoader;

    public BusinessDefine getDefine(String id){
        initResult();
        BusinessDefine define;
        define = result.get(id);
        if (define == null){
            result = null;
            initResult();
            define = result.get(id);
        }
        return define;
    }

    protected void initResult(){
        if (result == null){
            List<BusinessDefine> defines = systemEntityLoader.getEntityManager().createQuery("select define from BusinessDefine define left join fetch define.businessCategory",BusinessDefine.class).getResultList();
            result = new HashMap<String, BusinessDefine>();
            for(BusinessDefine define: defines){
                result.put(define.getId(),define);
            }

        }
    }


    protected void initCategory(){
        if (category == null){
            List<BusinessCategory> categories = systemEntityLoader.getEntityManager().createQuery("select category from BusinessCategory category",BusinessCategory.class).getResultList();
            category = new HashMap<String, BusinessCategory>();
            for (BusinessCategory c: categories){
                category.put(c.getId(),c);
            }
        }
    }

    public BusinessCategory getCategory(String id){
        initCategory();
        return category.get(id);
    }

    public static BusinessDefineCache instance()
    {
        if ( !Contexts.isSessionContextActive() )
        {
            throw new IllegalStateException("no active session context");
        }
        return (BusinessDefineCache) Component.getInstance(BusinessDefineCache.class,ScopeType.SESSION, true);
    }

}
