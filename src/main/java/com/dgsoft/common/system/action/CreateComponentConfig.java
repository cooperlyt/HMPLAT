package com.dgsoft.common.system.action;

import com.dgsoft.common.Entry;
import com.dgsoft.common.system.business.TaskSubscribeReg;
import com.dgsoft.common.system.model.CreateComponent;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Transactional;

import java.util.*;

/**
 * Created by cooper on 7/18/15.
 */
public abstract class CreateComponentConfig {

    @In
    private BusinessDefineHome businessDefineHome;

    @In
    private TaskSubscribeReg taskSubscribeReg;

    private String selectComponentName;

    protected abstract CreateComponent.CreateComponentType getType();

    public List<Entry<CreateComponent,String>> getExistsComponent(){
        List<Entry<CreateComponent,String>> result = new ArrayList<Entry<CreateComponent, String>>();
        for(CreateComponent component: businessDefineHome.getInstance().getBusinessCreateDataValids()){
            if (getType().equals(component.getType())){

                result.add(
                        new Entry<CreateComponent, String>(component,
                                taskSubscribeReg.getCreateComponentsByType(getType()).get(component.getComponent())));

            }
        }
        Collections.sort(result, new Comparator<Entry<CreateComponent, String>>() {
            @Override
            public int compare(Entry<CreateComponent, String> o1, Entry<CreateComponent, String> o2) {
                return Integer.valueOf(o1.getKey().getPriority()).compareTo(o2.getKey().getPriority());
            }
        });
        return result;
    }

    public List<Map.Entry<String,String>> getCanAddCreateComponent(){
        Set<String> components = new HashSet<String>();
        for(CreateComponent component: businessDefineHome.getInstance().getBusinessCreateDataValids()){
            if (getType().equals(component.getType())){
                components.add(component.getComponent());
            }
        }


        List<Map.Entry<String,String>> result = new ArrayList<Map.Entry<String, String>>();
        for(Map.Entry<String,String> entry: taskSubscribeReg.getCreateComponentsByType(getType()).entrySet()){
            if (!components.contains(entry.getKey())){
                result.add(entry);
            }
        }
        return result;
    }

    public String getSelectComponentName() {
        return selectComponentName;
    }

    public void setSelectComponentName(String selectComponentName) {
        this.selectComponentName = selectComponentName;
    }


    private int getComponentMaxPri(CreateComponent.CreateComponentType type){
        int result = 1;
        for(CreateComponent component: businessDefineHome.getInstance().getBusinessCreateDataValids()){
            if (type.equals(component.getType()) && (result < component.getPriority())){
                result = component.getPriority();
            }
        }
        return result;
    }

    @Transactional
    public void createComponent(){
        businessDefineHome.getInstance().getBusinessCreateDataValids().add(
                new CreateComponent(selectComponentName,
                        getComponentMaxPri(getType()),
                        businessDefineHome.getInstance(),getType()));
        businessDefineHome.update();
        selectComponentName = null;
    }

    @Transactional
    public void removeComponent(){
        for(CreateComponent component: businessDefineHome.getInstance().getBusinessCreateDataValids()){
            if (getType().equals(component.getType()) &&  component.getComponent().equals(selectComponentName)){
                businessDefineHome.getInstance().getBusinessCreateDataValids().remove(component);
                businessDefineHome.update();
                selectComponentName = null;
                return;
            }
        }
    }

}
