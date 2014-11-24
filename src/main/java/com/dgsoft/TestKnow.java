package com.dgsoft;

import com.dgsoft.common.DataFormat;
import com.dgsoft.common.GBT;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.faces.FacesPage;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 6/7/13
 * Time: 4:57 PM
 */
@Name("testKnow")
public class TestKnow {

    public List<TopLevel> getTopLevelDatas(){
        List<TopLevel> result = new ArrayList<TopLevel>(1);
        result.add(new TopLevel());
        return result;
    }

    public static class TopLevel {

        private String title;

        private List<SecondLevel> secondLevels = new ArrayList<SecondLevel>(1);

        public TopLevel() {
            this.title = "test Top level";
            secondLevels.add(new SecondLevel("test second Level"));
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<SecondLevel> getSecondLevels() {
            return secondLevels;
        }

        public void setSecondLevels(List<SecondLevel> secondLevels) {
            this.secondLevels = secondLevels;
        }
    }

    public static class SecondLevel {
        private String title;

        private boolean expand;

        private List<String> thirdLevels = new ArrayList<String>(15);

        public SecondLevel(String title) {
            this.title = title;
            for (int i = 0; i < 15; i++) {
                thirdLevels.add(String.valueOf(i));
            }
            expand = false;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getThirdLevels() {
            return thirdLevels;
        }

        public void setThirdLevels(List<String> thirdLevels) {
            this.thirdLevels = thirdLevels;
        }

        public boolean isExpand() {
            return expand;
        }

        public void setExpand(boolean expand) {
            this.expand = expand;
        }
    }


    public static class TestAnn {


        private String name;


        public String getName() {
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }
    }


    public static void main(String[] args) {
        //System.out.println(GBT.getGB17710("1101010102006050001090001") );
        // System.out.println(GBT.getGB17710("11010800000001") );
//        System.out.println(GBT.formatCode("123456789", 4) );
//        System.out.println(GBT.formatCode("3", 4) );
//        System.out.println(GBT.formatCode("1234", 4) );

//        javax.faces.component.html.HtmlPanelGrid
//        javax.faces.component.html.
        System.out.println("1234567890".substring(0, 5));

    }

    private String v1;

    private String v2;

    @Logger
    private org.jboss.seam.log.Log log;

    public void printPageContext() {
        log.debug("print page Context-------------");
        for (String name : Contexts.getPageContext().getNames()) {


            log.debug(name + ":" + Contexts.getPageContext().get(name));
        }


        org.jboss.seam.faces.FacesPage fp = (FacesPage) Contexts.getPageContext().get("org.jboss.seam.faces.facesPage");
        ;
        log.debug("page context convertID:" + fp.getConversationId());

        log.debug("print page Context end-------------");
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getV2() {

        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }
}
