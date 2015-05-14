package com.dgsoft.common.system.business;

import com.dgsoft.common.system.model.SubscribeGroup;
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

    private final static String VIEW_SUBSCRIBE_NODE_NAME = "view-subscribe";
    private final static String EDIT_SUBSCRIBE_NODE_NAME = "edit-subscribe";


    //private Map<String,SubscribeDefine> subscribeDefines;

    private List<EditSubscribeDefine> editSubscribeDefines;

    private List<SubscribeDefine> viewSubScribeDefines;

    public List<EditSubscribeDefine> getEditSubscribeDefines() {

        return editSubscribeDefines;
    }

    public List<SubscribeDefine> getViewSubScribeDefines() {

        return viewSubScribeDefines;
    }


    public EditSubscribeDefine getEditDefineByName(String name){
        for(EditSubscribeDefine subscribeDefine: editSubscribeDefines){
            if (subscribeDefine.getName().equals(name)){
                return subscribeDefine;
            }
        }
        return null;
    }

    public SubscribeDefine getViewDefineByName(String name){
        for(SubscribeDefine subscribeDefine: viewSubScribeDefines){
            if (subscribeDefine.getName().equals(name)){
                return subscribeDefine;
            }
        }
        return null;
    }

    @Create
    public void load() {

        editSubscribeDefines = new ArrayList<EditSubscribeDefine>();
        viewSubScribeDefines = new ArrayList<SubscribeDefine>();
        //subscribeDefines = new HashMap<String, SubscribeDefine>();
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
                        String description = null;
                        String page = regNode.getAttributes().getNamedItem("page").getNodeValue();
                        for(int j = 0, subSize = regNode.getChildNodes().getLength(); j < subSize; j++){
                            Node subNode = regNode.getChildNodes().item(j);
                            if ((subNode.getNodeType() == Node.ELEMENT_NODE) && "description".equals(subNode.getNodeName())){
                                description = subNode.getFirstChild().getNodeValue();
                                break;
                            }
                        }

                        if (VIEW_SUBSCRIBE_NODE_NAME.equals(regNode.getNodeName().trim().toLowerCase())){

                            viewSubScribeDefines.add(new SubscribeDefine(name,description,page));
                        } else if (EDIT_SUBSCRIBE_NODE_NAME.equals(regNode.getNodeName().trim().toLowerCase())){
                            editSubscribeDefines.add(new EditSubscribeDefine(name,description,page,
                                    regNode.getAttributes().getNamedItem("component").getNodeValue(),
                                    (regNode.getAttributes().getNamedItem("out-page") == null) ? "" : regNode.getAttributes().getNamedItem("out-page").getNodeValue()));
                        }


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



    public static class SubscribeDefineGroup {


        private SubscribeGroup group;


        private List<SubscribeDefine> blocks = new ArrayList<SubscribeDefine>();

        public SubscribeDefineGroup(SubscribeGroup group){
            this.group = group;
        }

        public SubscribeGroup getGroup() {
            return group;
        }

        public void add(SubscribeDefine subscribeDefine){
            blocks.add(subscribeDefine);
        }

        public List<SubscribeDefine> getDefineList() {
            return blocks;
        }
    }


    public static class EditSubscribeDefine extends SubscribeDefine{
        private String component;
        private String outPage;

        public EditSubscribeDefine(String name, String description, String page, String component, String outPage) {
            super(name, description, page);
            this.component = component;
            this.outPage = outPage;
        }

        public String getComponent() {
            return component;
        }

        public String getOutPage() {
            return outPage;
        }

        public boolean isHaveOutPage(){
            return (outPage != null) && (!outPage.trim().equals(""));
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


    public static class SubscribeDefine {

        private String name;

        private String description;

        private String page;




        public SubscribeDefine(String name, String description, String page) {
            this.name = name;
            this.description = description;
            this.page = page;

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



    }

}
