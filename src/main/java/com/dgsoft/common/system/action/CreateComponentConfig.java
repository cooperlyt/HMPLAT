package com.dgsoft.common.system.action;

import com.dgsoft.common.Entry;
import com.dgsoft.common.system.business.TaskSubscribeReg;
import com.dgsoft.common.system.model.CreateComponent;
import com.dgsoft.common.system.model.TaskSubscribe;
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
        int result = 0;
        for(CreateComponent component: businessDefineHome.getInstance().getBusinessCreateDataValids()){
            if (type.equals(component.getType()) && (result < component.getPriority())){
                result = component.getPriority();
            }
        }
        return result + 1;
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
                businessDefineHome.getInstance().getBusinessCreateDataValids().remove(getSelectCreateComponent());
                businessDefineHome.update();
                selectComponentName = null;
                return;
    }

    private CreateComponent getSelectCreateComponent(){
        for(CreateComponent component: businessDefineHome.getInstance().getBusinessCreateDataValids()) {
            if (getType().equals(component.getType()) && component.getComponent().equals(selectComponentName)) {
                return component;
            }
        }
        return null;
    }

    private void sort(boolean up){
        CreateComponent srcComponent = getSelectCreateComponent();
        List<CreateComponent> components = new ArrayList<CreateComponent>(businessDefineHome.getInstance().getBusinessCreateDataValids());
        Collections.sort(components, new Comparator<CreateComponent>() {
            @Override
            public int compare(CreateComponent o1, CreateComponent o2) {
                return Integer.valueOf(o1.getPriority()).compareTo(o2.getPriority());
            }
        });

        int targetIndex = up ? components.indexOf(srcComponent) - 1 : components.indexOf(srcComponent) + 1;
        if ((targetIndex >= 0) && (targetIndex < components.size())){
            int srcPri = srcComponent.getPriority();
            srcComponent.setPriority(components.get(targetIndex).getPriority());
            components.get(targetIndex).setPriority(srcPri);
        }
        businessDefineHome.update();
    }

    public void up() {
        sort(true);
    }

    public void down() {
        sort(false);
    }

}
