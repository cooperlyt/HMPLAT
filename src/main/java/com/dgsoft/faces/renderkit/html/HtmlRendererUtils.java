package com.dgsoft.faces.renderkit.html;


/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import com.dgsoft.faces.renderkit.RendererUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlMessages;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;


public final class HtmlRendererUtils {
    //private static final Log log = LogFactory.getLog(HtmlRendererUtils.class);
    private static final Logger log = Logger.getLogger(HtmlRendererUtils.class
            .getName());
    //private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final String LINE_SEPARATOR = System.getProperty(
            "line.separator", "\r\n");
    private static final char TABULATOR = '\t';
    public static final String HIDDEN_COMMANDLINK_FIELD_NAME = "_idcl";
    public static final String HIDDEN_COMMANDLINK_FIELD_NAME_MYFACES_OLD = "_link_hidden_";
    public static final String HIDDEN_COMMANDLINK_FIELD_NAME_TRINIDAD = "source";
    public static final String CLEAR_HIDDEN_FIELD_FN_NAME = "clearFormHiddenParams";
    public static final String SUBMIT_FORM_FN_NAME = "oamSubmitForm";
    public static final String SUBMIT_FORM_FN_NAME_JSF2 = "myfaces.oam.submitForm";
    public static final String ALLOW_CDATA_SECTION_ON = "org.apache.myfaces.ResponseWriter.CdataSectionOn";
    public static final String NON_SUBMITTED_VALUE_WARNING
            = "There should always be a submitted value for an input if it is rendered,"
            + " its form is submitted, and it was not originally rendered disabled or read-only."
            + "  You cannot submit a form after disabling an input element via javascript."
            + "  Consider setting read-only to true instead"
            + " or resetting the disabled value back to false prior to form submission.";
    public static final String STR_EMPTY = "";

    private HtmlRendererUtils() {
        // utility class, do not instantiate
    }

    /**
     * Utility to set the submitted value of the provided component from the
     * data in the current request object.
     * <p/>
     * Param component is required to be an EditableValueHolder. On return
     * from this method, the component's submittedValue property will be
     * set if the submitted form contained that component.
     */
    public static void decodeUIInput(FacesContext facesContext, UIComponent component) {
        if (!(component instanceof EditableValueHolder)) {
            throw new IllegalArgumentException("Component "
                    + component.getClientId(facesContext)
                    + " is not an EditableValueHolder");
        }
        Map paramMap = facesContext.getExternalContext()
                .getRequestParameterMap();
        String clientId = component.getClientId(facesContext);
        if (isDisabledOrReadOnly(component)) {
            return;
        }
        if (paramMap.containsKey(clientId)) {
            ((EditableValueHolder) component).setSubmittedValue(paramMap
                    .get(clientId));
        } else {
            log.warning(NON_SUBMITTED_VALUE_WARNING + " Component : "
                    + RendererUtils.getPathToComponent(component));
        }
    }

    /**
     * X-CHECKED: tlddoc h:selectBooleanCheckbox
     *
     * @param facesContext
     * @param component
     */
    public static void decodeUISelectBoolean(FacesContext facesContext, UIComponent component) {
        if (!(component instanceof EditableValueHolder)) {
            throw new IllegalArgumentException("Component "
                    + component.getClientId(facesContext)
                    + " is not an EditableValueHolder");
        }
        if (isDisabledOrReadOnly(component)) {
            return;
        }
        Map paramMap = facesContext.getExternalContext()
                .getRequestParameterMap();
        String clientId = component.getClientId(facesContext);
        if (paramMap.containsKey(clientId)) {
            String reqValue = (String) paramMap.get(clientId);
            if ((reqValue.equalsIgnoreCase("on")
                    || reqValue.equalsIgnoreCase("yes") || reqValue
                    .equalsIgnoreCase("true"))) {
                ((EditableValueHolder) component).setSubmittedValue(Boolean.TRUE);
            } else {
                ((EditableValueHolder) component).setSubmittedValue(Boolean.FALSE);
            }
        } else {
            ((EditableValueHolder) component).setSubmittedValue(Boolean.FALSE);
        }
    }

    public static boolean isDisabledOrReadOnly(UIComponent component) {
        return isDisabled(component) || isReadOnly(component);
    }

    public static boolean isDisabled(UIComponent component) {
        return isTrue(component.getAttributes().get("disabled"));
    }

    public static boolean isReadOnly(UIComponent component) {
        return isTrue(component.getAttributes().get("readonly"));
    }

    private static boolean isTrue(Object obj) {
        if (obj instanceof String) {
            return Boolean.valueOf((String) obj);
        }
        if (!(obj instanceof Boolean)) {
            return false;
        }
        return ((Boolean) obj).booleanValue();
    }

    /**
     * X-CHECKED: tlddoc h:selectManyListbox
     *
     * @param facesContext
     * @param component
     */
    public static void decodeUISelectMany(FacesContext facesContext, UIComponent component) {
        if (!(component instanceof EditableValueHolder)) {
            throw new IllegalArgumentException("Component "
                    + component.getClientId(facesContext)
                    + " is not an EditableValueHolder");
        }
        Map paramValuesMap = facesContext.getExternalContext().getRequestParameterValuesMap();
        String clientId = component.getClientId(facesContext);
        if (isDisabledOrReadOnly(component)) {
            return;
        }
        if (paramValuesMap.containsKey(clientId)) {
            String[] reqValues = (String[]) paramValuesMap.get(clientId);
            ((EditableValueHolder) component).setSubmittedValue(reqValues);
        } else {
            /* request parameter not found, nothing to decode - set submitted value to an empty array
               as we should get here only if the component is on a submitted form, is rendered
               and if the component is not readonly or has not been disabled.
               So in fact, there must be component value at this location, but for listboxes, comboboxes etc.
               the submitted value is not posted if no item is selected. */
            ((EditableValueHolder) component).setSubmittedValue(new String[]{});
        }
    }

    /**
     * X-CHECKED: tlddoc h:selectManyListbox
     *
     * @param facesContext
     * @param component
     */
    public static void decodeUISelectOne(FacesContext facesContext, UIComponent component) {
        if (!(component instanceof EditableValueHolder)) {
            throw new IllegalArgumentException("Component "
                    + component.getClientId(facesContext)
                    + " is not an EditableValueHolder");
        }
        if (isDisabledOrReadOnly(component)) {
            return;
        }
        Map paramMap = facesContext.getExternalContext().getRequestParameterMap();
        String clientId = component.getClientId(facesContext);
        if (paramMap.containsKey(clientId)) {
            //request parameter found, set submitted value
            ((EditableValueHolder) component).setSubmittedValue(paramMap.get(clientId));
        } else {
            //see reason for this action at decodeUISelectMany
            ((EditableValueHolder) component).setSubmittedValue(STR_EMPTY);
        }
    }

    /**
     * @since 4.0.0
     */
    public static void decodeClientBehaviors(FacesContext facesContext, UIComponent component) {
        if (component instanceof ClientBehaviorHolder) {
            ClientBehaviorHolder clientBehaviorHolder = (ClientBehaviorHolder) component;
            Map<String, List<ClientBehavior>> clientBehaviors = clientBehaviorHolder
                    .getClientBehaviors();
            if (clientBehaviors != null && !clientBehaviors.isEmpty()) {
                Map<String, String> paramMap = facesContext
                        .getExternalContext().getRequestParameterMap();
                String behaviorEventName = paramMap
                        .get("javax.faces.behavior.event");
                if (behaviorEventName != null) {
                    List<ClientBehavior> clientBehaviorList = clientBehaviors
                            .get(behaviorEventName);
                    if (clientBehaviorList != null
                            && !clientBehaviorList.isEmpty()) {
                        String clientId = paramMap.get("javax.faces.source");
                        if (component.getClientId(facesContext).equals(clientId)) {
                            if (clientBehaviorList instanceof RandomAccess) {
                                for (int i = 0, size = clientBehaviorList.size(); i < size; i++) {
                                    ClientBehavior clientBehavior = clientBehaviorList.get(i);
                                    clientBehavior.decode(facesContext, component);
                                }
                            } else {
                                for (ClientBehavior clientBehavior : clientBehaviorList) {
                                    clientBehavior.decode(facesContext, component);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * @return true, if the attribute was written
     * @throws java.io.IOException
     */
    public static boolean renderHTMLAttribute(ResponseWriter writer,
                                              String componentProperty, String attrName, Object value)
            throws IOException {
        if (!RendererUtils.isDefaultAttributeValue(value)) {
            // render JSF "styleClass" and "itemStyleClass" attributes as "class"
            String htmlAttrName = attrName.equals(HTML.STYLE_CLASS_ATTR) ? HTML.CLASS_ATTR
                    : attrName;
            writer.writeAttribute(htmlAttrName, value, componentProperty);
            return true;
        }

        return false;
    }

    /**
     * @return true, if the attribute was written
     * @throws java.io.IOException
     */
    public static boolean renderHTMLAttribute(ResponseWriter writer,
                                              UIComponent component, String componentProperty, String htmlAttrName)
            throws IOException {
        Object value = component.getAttributes().get(componentProperty);
        return renderHTMLAttribute(writer, componentProperty, htmlAttrName,
                value);
    }

    private static String convertComponentName(String componentProperty) {
        String result = "";
        boolean up = false;
        for (char c : componentProperty.toCharArray()) {
            if ( c == '_'){
                up = true;

            }else{
                if (up){
                    result = String.valueOf(c).toUpperCase();
                    up = false;
                }else{
                    result += c;
                }

            }
        }
        return result;

    }


    public static boolean render_html_attribute(ResponseWriter writer,
                                                UIComponent component, String componentProperty, String htmlAttrName) throws IOException {

        Object value = component.getAttributes().get(htmlAttrName);

        return renderHTMLAttribute(writer, convertComponentName(componentProperty), htmlAttrName,
                value);
    }

    private static String convertHtmlAttrName(String componentProperty) {
        return componentProperty.replace("_", "-");
    }

    public static boolean render_html_attributes(ResponseWriter writer,
                                                 UIComponent component, String[] componentProperty) throws IOException {

        boolean somethingDone = false;
        for (int i = 0, len = componentProperty.length; i < len; i++) {
            String propertyName = componentProperty[i];
            if (render_html_attribute(writer, component, propertyName, convertHtmlAttrName(propertyName))) {
                somethingDone = true;
            }
        }
        return somethingDone;
    }

    /**
     * @return true, if an attribute was written
     * @throws java.io.IOException
     */
    public static boolean renderHTMLAttributes(ResponseWriter writer,
                                               UIComponent component, String[] attributes) throws IOException {
        boolean somethingDone = false;
        for (int i = 0, len = attributes.length; i < len; i++) {
            String attrName = attributes[i];
            if (renderHTMLAttribute(writer, component, attrName, attrName)) {
                somethingDone = true;
            }
        }
        return somethingDone;
    }

    public static boolean renderHTMLAttributeWithOptionalStartElement(
            ResponseWriter writer, UIComponent component, String elementName,
            String attrName, Object value, boolean startElementWritten)
            throws IOException {
        if (!RendererUtils
                .isDefaultAttributeValue(value)) {
            if (!startElementWritten) {
                writer.startElement(elementName, component);
                startElementWritten = true;
            }
            renderHTMLAttribute(writer, attrName, attrName, value);
        }
        return startElementWritten;
    }

    public static boolean renderHTMLAttributesWithOptionalStartElement(
            ResponseWriter writer, UIComponent component, String elementName,
            String[] attributes) throws IOException {
        boolean startElementWritten = false;
        for (int i = 0, len = attributes.length; i < len; i++) {
            String attrName = attributes[i];
            Object value = component.getAttributes().get(attrName);
            if (!RendererUtils.isDefaultAttributeValue(value)) {
                if (!startElementWritten) {
                    writer.startElement(elementName, component);
                    startElementWritten = true;
                }
                renderHTMLAttribute(writer, attrName, attrName, value);
            }
        }
        return startElementWritten;
    }

    public static boolean renderOptionalEndElement(ResponseWriter writer,
                                                   UIComponent component, String elementName, String[] attributes)
            throws IOException {
        boolean endElementNeeded = false;
        for (int i = 0, len = attributes.length; i < len; i++) {
            String attrName = attributes[i];
            Object value = component.getAttributes().get(attrName);
            if (!RendererUtils.isDefaultAttributeValue(value)) {
                endElementNeeded = true;
                break;
            }
        }
        if (endElementNeeded) {
            writer.endElement(elementName);
            return true;
        }

        return false;
    }

    public static void writeIdIfNecessary(ResponseWriter writer,
                                          UIComponent component, FacesContext facesContext)
            throws IOException {
        if (component.getId() != null
                && !component.getId().startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
            writer.writeAttribute(HTML.ID_ATTR, component.getClientId(facesContext), null);
        }
    }

    public static void writeIdAndNameIfNecessary(ResponseWriter writer,
                                                 UIComponent component, FacesContext facesContext)
            throws IOException {
        if (component.getId() != null
                && !component.getId().startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
            String clientId = component.getClientId(facesContext);
            writer.writeAttribute(HTML.ID_ATTR, clientId, null);
            writer.writeAttribute(HTML.NAME_ATTR, clientId, null);
        }
    }

    /**
     * Renders a html string type attribute. If the value retrieved from the component
     * property is "", the attribute is rendered.
     *
     * @param writer
     * @param component
     * @param componentProperty
     * @param htmlAttrName
     * @return
     * @throws IOException
     */
    public static final boolean renderHTMLStringPreserveEmptyAttribute(ResponseWriter writer,
                                                                       UIComponent component, String componentProperty, String htmlAttrName)
            throws IOException {
        String value = (String) component.getAttributes().get(componentProperty);
        if (!isDefaultStringPreserveEmptyAttributeValue(value)) {
            writer.writeAttribute(htmlAttrName, value, componentProperty);
            return true;
        }
        return false;
    }

    /**
     * Renders a html string type attribute. If the value retrieved from the component
     * property is "", the attribute is rendered.
     *
     * @param writer
     * @param componentProperty
     * @param htmlAttrName
     * @return
     * @throws IOException
     */
    public static boolean renderHTMLStringPreserveEmptyAttribute(ResponseWriter writer,
                                                                 String componentProperty, String htmlAttrName, String value)
            throws IOException {
        if (!isDefaultStringPreserveEmptyAttributeValue(value)) {
            writer.writeAttribute(htmlAttrName, value, componentProperty);
            return true;
        }
        return false;
    }

    /**
     * Check if the value is the default for String type attributes that requires preserve "" as
     * a valid value.
     *
     * @param value
     * @return
     */
    private static boolean isDefaultStringPreserveEmptyAttributeValue(String value) {
        if (value == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Renders a html string type attribute. If the value retrieved from the component
     * property is "" or null, the attribute is not rendered.
     *
     * @param writer
     * @param component
     * @param componentProperty
     * @param htmlAttrName
     * @return
     * @throws IOException
     */
    public static boolean renderHTMLStringAttribute(ResponseWriter writer,
                                                    UIComponent component, String componentProperty, String htmlAttrName)
            throws IOException {
        String value = (String) component.getAttributes().get(componentProperty);
        if (!isDefaultStringAttributeValue(value)) {
            writer.writeAttribute(htmlAttrName, value, componentProperty);
            return true;
        }
        return false;
    }

    /**
     * Renders a html string type attribute. If the value retrieved from the component
     * property is "" or null, the attribute is not rendered.
     *
     * @param writer
     * @param componentProperty
     * @param htmlAttrName
     * @param value
     * @return
     * @throws IOException
     */
    public static boolean renderHTMLStringAttribute(ResponseWriter writer,
                                                    String componentProperty, String htmlAttrName, String value)
            throws IOException {
        if (!isDefaultStringAttributeValue(value)) {
            writer.writeAttribute(htmlAttrName, value, componentProperty);
            return true;
        }
        return false;
    }

    /**
     * Check if the value is the default for String type attributes (null or "").
     *
     * @param value
     * @return
     */
    private static boolean isDefaultStringAttributeValue(String value) {
        if (value == null) {
            return true;
        } else if (value.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean renderHTMLStringNoStyleAttributes(ResponseWriter writer,
                                                            UIComponent component, String[] attributes) throws IOException {
        boolean somethingDone = false;
        for (int i = 0, len = attributes.length; i < len; i++) {
            String attrName = attributes[i];
            if (renderHTMLStringAttribute(writer, component, attrName, attrName)) {
                somethingDone = true;
            }
        }
        return somethingDone;
    }

    public static void writeIdAndName(ResponseWriter writer, UIComponent component, FacesContext facesContext)
            throws IOException {
        String clientId = component.getClientId(facesContext);
        writer.writeAttribute(HTML.ID_ATTR, clientId, null);
        writer.writeAttribute(HTML.NAME_ATTR, clientId, null);
    }


    private static void renderSelectOptionsAsText(FacesContext context,
                                                  UIComponent component, Converter converter, Set lookupSet,
                                                  List selectItemList, boolean isSelectOne) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        for (Iterator it = selectItemList.iterator(); it.hasNext(); ) {
            SelectItem selectItem = (SelectItem) it.next();

            if (selectItem instanceof SelectItemGroup) {
                SelectItem[] selectItems = ((SelectItemGroup) selectItem).getSelectItems();
                renderSelectOptionsAsText(context, component, converter,
                        lookupSet, Arrays.asList(selectItems), isSelectOne);
            } else {
                String itemStrValue = RendererUtils.getConvertedStringValue(
                        context, component, converter, selectItem);

                if (lookupSet.contains(itemStrValue)) {
                    //TODO/FIX: we always compare the String vales, better fill lookupSet with Strings
                    //only when useSubmittedValue==true, else use the real item value Objects
                    if (!isSelectOne) {
                        writer.startElement(HTML.LI_ELEM, null); // component);
                    }
                    writer.writeText(selectItem.getLabel(), null);
                    if (!isSelectOne) {
                        writer.endElement(HTML.LI_ELEM);
                    }
                    if (isSelectOne) {
                        //take care of several choices with the same value; use only the first one
                        return;
                    }
                }
            }
        }
    }

    public static void renderTableCaption(FacesContext context,
                                          ResponseWriter writer, UIComponent component) throws IOException {
        UIComponent captionFacet = component.getFacet("caption");
        if (captionFacet == null) {
            return;
        }
        String captionClass;
        String captionStyle;
        if (component instanceof HtmlPanelGrid) {
            HtmlPanelGrid panelGrid = (HtmlPanelGrid) component;
            captionClass = panelGrid.getCaptionClass();
            captionStyle = panelGrid.getCaptionStyle();
        } else if (component instanceof HtmlDataTable) {
            HtmlDataTable dataTable = (HtmlDataTable) component;
            captionClass = dataTable.getCaptionClass();
            captionStyle = dataTable.getCaptionStyle();
        } else {
            captionClass = (String) component.getAttributes()
                    .get(JSFAttr.CAPTION_CLASS_ATTR);
            captionStyle = (String) component.getAttributes()
                    .get(JSFAttr.CAPTION_STYLE_ATTR);
        }
        writer.startElement(HTML.CAPTION_ELEM, null); // component);
        if (captionClass != null) {
            writer.writeAttribute(HTML.CLASS_ATTR, captionClass, null);
        }

        if (captionStyle != null) {
            writer.writeAttribute(HTML.STYLE_ATTR, captionStyle, null);
        }
        //RendererUtils.renderChild(context, captionFacet);
        captionFacet.encodeAll(context);
        writer.endElement(HTML.CAPTION_ELEM);
    }


    public static boolean isAllowedCdataSection(FacesContext fc) {
        Boolean value = null;
        if (fc != null) {
            value = (Boolean) fc.getExternalContext().getRequestMap().get(ALLOW_CDATA_SECTION_ON);
        }
        return value != null && ((Boolean) value).booleanValue();
    }

    public static void allowCdataSection(FacesContext fc, boolean cdataSectionAllowed) {
        fc.getExternalContext().getRequestMap().put(ALLOW_CDATA_SECTION_ON, Boolean.valueOf(cdataSectionAllowed));
    }

    public static class LinkParameter {
        private String _name;

        private Object _value;

        public String getName() {
            return _name;
        }

        public void setName(String name) {
            _name = name;
        }

        public Object getValue() {
            return _value;
        }

        public void setValue(Object value) {
            _value = value;
        }
    }

    public static void renderHiddenCommandFormParams(ResponseWriter writer,
                                                     Set dummyFormParams) throws IOException {
        for (Iterator it = dummyFormParams.iterator(); it.hasNext(); ) {
            Object name = it.next();
            renderHiddenInputField(writer, name, null);
        }
    }

    public static void renderHiddenInputField(ResponseWriter writer,
                                              Object name, Object value) throws IOException {
        writer.startElement(HTML.INPUT_ELEM, null);
        writer.writeAttribute(HTML.TYPE_ATTR, HTML.INPUT_TYPE_HIDDEN, null);
        writer.writeAttribute(HTML.NAME_ATTR, name, null);
        if (value != null) {
            writer.writeAttribute(HTML.VALUE_ATTR, value, null);
        }
        writer.endElement(HTML.INPUT_ELEM);
    }

    /**
     * @deprecated Replaced by
     * renderLabel(ResponseWriter writer,
     * UIComponent component,
     * String forClientId,
     * SelectItem item,
     * boolean disabled).
     * Renders a label HTML element
     */
    @Deprecated
    public static void renderLabel(ResponseWriter writer,
                                   UIComponent component, String forClientId, String labelValue,
                                   boolean disabled) throws IOException {
        writer.startElement(HTML.LABEL_ELEM, null); // component);
        writer.writeAttribute(HTML.FOR_ATTR, forClientId, null);
        String labelClass = null;
        if (disabled) {
            labelClass = (String) component.getAttributes().get(JSFAttr.DISABLED_CLASS_ATTR);
        } else {
            labelClass = (String) component.getAttributes()
                    .get(JSFAttr.ENABLED_CLASS_ATTR);
        }
        if (labelClass != null) {
            writer.writeAttribute("class", labelClass, "labelClass");
        }
        if ((labelValue != null) && (labelValue.length() > 0)) {
            writer.write(HTML.NBSP_ENTITY);
            writer.writeText(labelValue, null);
        }
        writer.endElement(HTML.LABEL_ELEM);
    }

    /**
     * Renders a label HTML element
     */
    public static void renderLabel(ResponseWriter writer,
                                   UIComponent component, String forClientId, SelectItem item,
                                   boolean disabled) throws IOException {
        writer.startElement(HTML.LABEL_ELEM, null); // component);
        writer.writeAttribute(HTML.FOR_ATTR, forClientId, null);
        String labelClass = null;
        if (disabled) {
            labelClass = (String) component.getAttributes().get(JSFAttr.DISABLED_CLASS_ATTR);
        } else {
            labelClass = (String) component.getAttributes()
                    .get(JSFAttr.ENABLED_CLASS_ATTR);
        }
        if (labelClass != null) {
            writer.writeAttribute("class", labelClass, "labelClass");
        }
        if ((item.getLabel() != null) && (item.getLabel().length() > 0)) {
            // writer.write(HTML.NBSP_ENTITY);
            writer.write(" ");
            if (item.isEscape()) {
                //writer.write(item.getLabel());
                writer.writeText(item.getLabel(), null);
            } else {
                //writer.write(HTMLEncoder.encode (item.getLabel()));
                writer.write(item.getLabel());
            }
        }
        writer.endElement(HTML.LABEL_ELEM);
    }

    /**
     * Renders a label HTML element
     */
    public static void renderLabel(ResponseWriter writer,
                                   UIComponent component, String forClientId, SelectItem item,
                                   boolean disabled, boolean selected) throws IOException {
        writer.startElement(HTML.LABEL_ELEM, null); // component);
        writer.writeAttribute(HTML.FOR_ATTR, forClientId, null);
        String labelClass = null;
        if (disabled) {
            labelClass = (String) component.getAttributes().get(JSFAttr.DISABLED_CLASS_ATTR);
        } else {
            labelClass = (String) component.getAttributes()
                    .get(JSFAttr.ENABLED_CLASS_ATTR);
        }
        String labelSelectedClass = null;
        if (selected) {
            labelSelectedClass = (String) component.getAttributes().get(JSFAttr.SELECTED_CLASS_ATTR);
        } else {
            labelSelectedClass = (String) component.getAttributes().get(JSFAttr.UNSELECTED_CLASS_ATTR);
        }
        if (labelSelectedClass != null) {
            if (labelClass == null) {
                labelClass = labelSelectedClass;
            } else {
                labelClass = labelClass + " " + labelSelectedClass;
            }
        }
        if (labelClass != null) {
            writer.writeAttribute("class", labelClass, "labelClass");
        }
        if ((item.getLabel() != null) && (item.getLabel().length() > 0)) {
            writer.write(HTML.NBSP_ENTITY);
            if (item.isEscape()) {
                //writer.write(item.getLabel());
                writer.writeText(item.getLabel(), null);
            } else {
                //writer.write(HTMLEncoder.encode (item.getLabel()));
                writer.write(item.getLabel());
            }
        }
        writer.endElement(HTML.LABEL_ELEM);
    }


    private static List splitContentTypeListString(String contentTypeListString) {
        List contentTypeList = new ArrayList();
        StringTokenizer st = new StringTokenizer(contentTypeListString, ",");
        while (st.hasMoreTokens()) {
            String contentType = st.nextToken().trim();
            int semicolonIndex = contentType.indexOf(";");
            if (semicolonIndex != -1) {
                contentType = contentType.substring(0, semicolonIndex);
            }
            contentTypeList.add(contentType);
        }
        return contentTypeList;
    }

    public static String getJavascriptLocation(UIComponent component) {
        if (component == null) {
            return null;
        }
        return (String) component.getAttributes().get(JSFAttr.JAVASCRIPT_LOCATION);
    }

    public static String getImageLocation(UIComponent component) {
        if (component == null) {
            return null;
        }
        return (String) component.getAttributes().get(JSFAttr.IMAGE_LOCATION);
    }

    public static String getStyleLocation(UIComponent component) {
        if (component == null) {
            return null;
        }
        return (String) component.getAttributes().get(JSFAttr.STYLE_LOCATION);
    }

    /**
     * Checks if the given component has a behavior attachment with a given name.
     *
     * @param eventName the event name to be checked for
     * @param behaviors map of behaviors attached to the component
     * @return true if client behavior with given name is attached, false otherwise
     * @since 4.0.0
     */
    public static boolean hasClientBehavior(String eventName,
                                            Map<String, List<ClientBehavior>> behaviors,
                                            FacesContext facesContext) {
        if (behaviors == null) {
            return false;
        }
        return (behaviors.get(eventName) != null);
    }

    public static Collection<ClientBehaviorContext.Parameter> getClientBehaviorContextParameters(
            Map<String, String> params) {
        List<ClientBehaviorContext.Parameter> paramList = null;
        if (params != null) {
            paramList = new ArrayList<ClientBehaviorContext.Parameter>(params.size());
            for (Map.Entry<String, String> paramEntry : params.entrySet()) {
                paramList.add(new ClientBehaviorContext.Parameter(paramEntry
                        .getKey(), paramEntry.getValue()));
            }
        }
        return paramList;
    }


    /**
     * Returns the value of the hideNoSelectionOption attribute of the given UIComponent
     *
     * @param component
     * @return
     */
    public static boolean isHideNoSelectionOption(UIComponent component) {
        // check hideNoSelectionOption for literal value (String) or ValueExpression (Boolean)
        Object hideNoSelectionOptionAttr = component.getAttributes().get(
                JSFAttr.HIDE_NO_SELECTION_OPTION_ATTR);
        return ((hideNoSelectionOptionAttr instanceof String && "true"
                .equalsIgnoreCase((String) hideNoSelectionOptionAttr)) ||
                (hideNoSelectionOptionAttr instanceof Boolean && ((Boolean) hideNoSelectionOptionAttr)));
    }

    /**
     * Renders all FacesMessages which have not been rendered yet with
     * the help of a HtmlMessages component.
     *
     * @param facesContext
     */
    public static void renderUnhandledFacesMessages(FacesContext facesContext)
            throws IOException {
        // create and configure HtmlMessages component
        HtmlMessages messages = (HtmlMessages) facesContext.getApplication()
                .createComponent(HtmlMessages.COMPONENT_TYPE);
        messages.setId("javax_faces_developmentstage_messages");
        messages.setTitle("Project Stage[Development]: Unhandled Messages");
        messages.setStyle("color:orange");
        messages.setRedisplay(false);
        // render the component
        messages.encodeAll(facesContext);
    }

}
