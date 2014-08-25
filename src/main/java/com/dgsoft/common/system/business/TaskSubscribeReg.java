package com.dgsoft.common.system.business;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.reflections.Reflections;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by cooper on 8/25/14.
 */
@Scope(ScopeType.APPLICATION)
@AutoCreate
@Startup
@Name("taskSubscribeReg")
public class TaskSubscribeReg {


    @Create
    public void load() {
        Reflections reflections = new Reflections("com.dgsoft");
        Set<String> properties =  reflections.getResources(Pattern.compile(".*\\.tasksubscribe\\.xml"));
    }



    public static class TaskSubscribeDefine{

        private String name;

        private String description;

        private String page;

        private String component;


        public TaskSubscribeDefine(String name, String description, String page, String component) {
            this.name = name;
            this.description = description;
            this.page = page;
            this.component = component;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getComponent() {
            return component;
        }

        public void setComponent(String component) {
            this.component = component;
        }
    }

}
