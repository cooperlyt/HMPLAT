package com.dgsoft.common.system.business;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.log.Logging;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by cooper on 8/25/14.
 */
@Scope(ScopeType.APPLICATION)
@AutoCreate
@Startup
@Name("taskSubscribeReg")
public class TaskSubscribeReg {


    private Map<String,TaskSubscribeDefine> subscribeDefines;

    public List<TaskSubscribeDefine> getTaskSubscribeDefine(){
        return new ArrayList<TaskSubscribeDefine>(subscribeDefines.values());
    }

    public TaskSubscribeDefine getDefineByName(String name){
        return subscribeDefines.get(name);
    }

    @Create
    public void load() {
        subscribeDefines = new HashMap<String, TaskSubscribeDefine>();
        Reflections reflections = new Reflections(new ConfigurationBuilder().addUrls(ClasspathHelper.forPackage("com.dgsoft")).addScanners(new ResourcesScanner()));
        Set<String> confings = reflections.getResources(Pattern.compile(".*\\.tasksubscribe\\.xml"));
        Logging.getLog(getClass()).debug("find subscribe config  :" + confings.size());
        for (String conf : confings) {

            Logging.getLog(getClass()).debug("loading :" + getClass().getClassLoader().getResource(conf));
            try {
                DocumentBuilder domBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = domBuilder.parse(getClass().getClassLoader().getResourceAsStream(conf));
                Element root = doc.getDocumentElement();
                NodeList regNodes = root.getChildNodes();
                for (int i = 0, size = regNodes.getLength(); i < size; i++){

                    Node regNode = regNodes.item(i);
                    if (regNode.getNodeType() == Node.ELEMENT_NODE) {
                        Logging.getLog(getClass()).debug(regNode.getNodeName());
                        String name = regNode.getAttributes().getNamedItem("name").getNodeValue();
                        subscribeDefines.put(name, new TaskSubscribeDefine(name,
                                regNode.getFirstChild().getNodeValue(),
                                regNode.getAttributes().getNamedItem("page").getNodeValue(),
                                regNode.getAttributes().getNamedItem("component").getNodeValue(),
                                TaskSubscribeDefineType.valueOf(regNode.getAttributes().getNamedItem("type").getNodeValue()),
                                regNode.getAttributes().getNamedItem("cagegory").getNodeValue()));
                        Logging.getLog(getClass()).debug("add subscribeDefine :" + name);
                    }

                }
            } catch (FileNotFoundException e) {
                Logging.getLog(getClass()).error(e.getMessage(),e);
                throw new IllegalArgumentException(e);
            } catch (ParserConfigurationException e) {
                Logging.getLog(getClass()).error(e.getMessage(),e);
                throw new IllegalArgumentException(e);
            } catch (SAXException e) {
                Logging.getLog(getClass()).error(e.getMessage(),e);
                throw new IllegalArgumentException(e);
            } catch (IOException e) {
                Logging.getLog(getClass()).error(e.getMessage(),e);
                throw new IllegalArgumentException(e);
            }
        }


    }

    public enum TaskSubscribeDefineType{
        EDIT,VIEW,ALONE_EDIT,ALONE_VIEW;
    }


    public static class TaskSubscribeDefine {

        private String name;

        private String description;

        private String page;

        private String component;

        private TaskSubscribeDefineType type;

        public String category;

        public TaskSubscribeDefine(String name, String description, String page,
                                   String component, TaskSubscribeDefineType type, String category) {
            this.name = name;
            this.description = description;
            this.page = page;
            this.component = component;
            this.type = type;
            this.category = category;
        }

        public TaskSubscribeDefineType getType() {
            return type;
        }

        public String getCategory() {
            return category;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getPage() {
            if (isHavePage()) {
                return page;
            }else{
                return null;
            }
        }

        public String getTitle(){
            return description + "[" + name + "]";
        }

        public boolean isHavePage(){
            return (page != null) && (!page.trim().equals(""));
        }

        public boolean isHaveComponent(){
            return (component != null) && (!component.trim().equals(""));
        }

        public TaskSubscribeComponent getComponents(){
            if (isHaveComponent()) {
                return (TaskSubscribeComponent) Component.getInstance(component, true, true);
            }else
                return null;
        }
    }

}
