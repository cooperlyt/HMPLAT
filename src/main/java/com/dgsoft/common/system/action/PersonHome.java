package com.dgsoft.common.system.action;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.system.SystemEntityHome;
import com.dgsoft.common.system.model.Person;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 5/15/13
 * Time: 1:55 PM
 */
@Name("personHome")
public class PersonHome extends SystemEntityHome<Person> {

    @Factory(value = "sexs", scope = ScopeType.SESSION)
    public Person.Sex[] getSexs() {
        return Person.Sex.values();
    }

    @Factory(value = "credentialsTypes",scope = ScopeType.SESSION )
    public PersonEntity.CredentialsType[] getCredentialsTypes(){
        return PersonEntity.CredentialsType.values();
    }

}
