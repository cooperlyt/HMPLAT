package com.dgsoft.common.system;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.ui.JpaEntityLoader;

import javax.persistence.EntityManager;

import static org.jboss.seam.ScopeType.STATELESS;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 6/13/13
 * Time: 4:26 PM
 */
@Name("systemEntityLoader")
@Scope(STATELESS)
public class SystemEntityLoader extends JpaEntityLoader {

    @Override
    protected String getPersistenceContextName()
    {
        return "systemEntityManager";
    }

    @In
    private EntityManager systemEntityManager;

    //TODO entityConverter saveing EntityLoader why?
    @Override
    public EntityManager getPersistenceContext(){

        return systemEntityManager;
    }


    public static SystemEntityLoader instance(){
        return (SystemEntityLoader) Component.getInstance(SystemEntityLoader.class,true);
    }
}