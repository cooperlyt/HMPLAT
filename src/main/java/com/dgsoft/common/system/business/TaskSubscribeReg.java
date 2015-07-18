package com.dgsoft.common.system.business;

import com.dgsoft.common.system.model.CreateComponent;
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
    private final static String END_SUBSCRIBE_NODE_NAME = "end-subscribe";
    private final static String OPERATOR_SUBSCRIBE_NODE_NAME = "oper-subscribe";
    private final static String CREATE_COMPONENT_NODE_NAME ="create-component";

    private Map<CreateComponent.CreateComponentType,Map<String,String>> createComponents;


    private List<EditSubscribeDefine> editSubscribeDefines;

    private List<SubscribeDefine> viewSubScribeDefines;

    private List<SubscribeDefine> operSubscribeDefines;

    private List<CompleteSubscribeDefine> completeSubscribeDefines;

    public Map<String,String> getCreateComponentsByType(CreateComponent.CreateComponentType type) {
        Map<String,String> result = createComponents.get(type);
        if (result == null){
            return new HashMap<String, String>(0);
        }else{
            return result;
        }
    }

    public List<EditSubscribeDefine> getEditSubscribeDefines() {

        return editSubscribeDefines;
    }

    public List<SubscribeDefine> getViewSubScribeDefines() {

        return viewSubScribeDefines;
    }

    public List<SubscribeDefine> getOperSubscribeDefines() {
        return operSubscribeDefines;
    }

    public List<CompleteSubscribeDefine> getCompleteSubscribeDefines() {
        return completeSubscribeDefines;
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

    public SubscribeDefine getOperDefineByName(String name){
        for(SubscribeDefine subscribeDefine: operSubscribeDefines){
            if (subscribeDefine.getName().equals(name)){
                return subscribeDefine;
            }
        }
        return null;
    }

    public CompleteSubscribeDefine getCompleteDefineByName(String name){
        for(CompleteSubscribeDefine subscribeDefine: completeSubscribeDefines){
            if (subscribeDefine.getName().equals(name)){
                return subscribeDefine;
            }
        }
        return null;
    }


    @Create
    public void load() {
        createComponents = new HashMap<CreateComponent.CreateComponentType, Map<String, String>>();

        editSubscribeDefines = new ArrayList<EditSubscribeDefine>();
        viewSubScribeDefines = new ArrayList<SubscribeDefine>();
        completeSubscribeDefines = new ArrayList<CompleteSubscribeDefine>();
        operSubscribeDefines = new ArrayList<SubscribeDefine>();
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
                        String name = null;
                        if (regNode.getAttributes().getNamedItem("name") != null){
                            name = regNode.getAttributes().getNamedItem("name").getNodeValue();
                        }

                        String description = null;
                        //String page = regNode.getAttributes().getNamedItem("page").getNodeValue();
                        for(int j = 0, subSize = regNode.getChildNodes().getLength(); j < subSize; j++){
                            Node subNode = regNode.getChildNodes().item(j);
                            if ((subNode.getNodeType() == Node.ELEMENT_NODE) && "description".equals(subNode.getNodeName())){
                                description = subNode.getFirstChild().getNodeValue();
                                break;
                            }
                        }

                        if (VIEW_SUBSCRIBE_NODE_NAME.equals(regNode.getNodeName().trim().toLowerCase())){

                            viewSubScribeDefines.add(new SubscribeDefine(name,description,regNode.getAttributes().getNamedItem("page").getNodeValue()));
                        } else if (EDIT_SUBSCRIBE_NODE_NAME.equals(regNode.getNodeName().trim().toLowerCase())){
                            editSubscribeDefines.add(new EditSubscribeDefine(name,description,regNode.getAttributes().getNamedItem("page").getNodeValue(),
                                    regNode.getAttributes().getNamedItem("component").getNodeValue(),
                                    (regNode.getAttributes().getNamedItem("out-page") == null) ? "" : regNode.getAttributes().getNamedItem("out-page").getNodeValue()));
                        } else if (END_SUBSCRIBE_NODE_NAME.equals(regNode.getNodeName().trim().toLowerCase())){
                            completeSubscribeDefines.add( new CompleteSubscribeDefine(name,description,regNode.getAttributes().getNamedItem("component").getNodeValue()));
                        } else if (OPERATOR_SUBSCRIBE_NODE_NAME.equals(regNode.getNodeName().trim().toLowerCase())){
                            operSubscribeDefines.add(new SubscribeDefine(name,description,regNode.getAttributes().getNamedItem("page").getNodeValue()));
                        }else if (CREATE_COMPONENT_NODE_NAME.equals(regNode.getNodeName().trim().toLowerCase())){
                            CreateComponent.CreateComponentType type = CreateComponent.CreateComponentType.valueOf(regNode.getAttributes().getNamedItem("type").getNodeValue());
                            Map<String,String> cMap = createComponents.get(type);
                            if (cMap == null){
                                cMap = new HashMap<String, String>();
                                createComponents.put(type,cMap);
                            }
                            cMap.put(regNode.getAttributes().getNamedItem("component").getNodeValue(),description);

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

    public static class CompleteSubscribeDefine extends SubscribeDefineBase{

        private String component;


        public CompleteSubscribeDefine(String name, String description, String component) {
            super(name, description);
            this.component = component;
        }

        public TaskCompleteSubscribeComponent getComponents(){
            return (TaskCompleteSubscribeComponent) Component.getInstance(component, true, true);
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

    public static class SubscribeDefineBase{
        private String name;

        private String description;

        public SubscribeDefineBase(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getTitle(){
            return description + "[" + name + "]";
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }


    public static class SubscribeDefine extends SubscribeDefineBase{


        private String page;


        public SubscribeDefine(String name, String description, String page) {
            super(name,description);
            this.page = page;

        }

        public String getPage() {
            if (isHavePage()) {
                return page;
            }else{
                return null;
            }
        }


        public boolean isHavePage(){
            return (page != null) && (!page.trim().equals(""));
        }


    }

}
