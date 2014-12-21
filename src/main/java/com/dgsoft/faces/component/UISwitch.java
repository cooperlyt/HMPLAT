package com.dgsoft.faces.component;

import java.util.*;

/**
 * Created by cooper on 12/20/14.
 */
public class UISwitch extends javax.faces.component.UISelectBoolean {


    protected enum PropertyKeys {
        accesskey,
        dir,
        disabled,
        label,
        lang,
        readonly,
        style,
        styleClass,
        tabindex,
        title,
        data_size,
        data_animate,
        data_indeterminate,
        data_inverse,
        data_radio_all_off,
        data_on_color,
        data_off_color,
        data_on_text,
        data_off_text,
        data_label_text,
        data_handle_width,
        data_label_width,
        data_base_class,
        data_wrapper_class,
        data_in_link;

        String toString;

        PropertyKeys(String toString) {
            this.toString = toString;
        }

        PropertyKeys() {
        }

        public String toString() {
            return ((toString != null) ? toString : super.toString());
        }
    }


    /**
     * <p>Return the value of the <code>accesskey</code> property.</p>
     * <p>Contents: Access key that, when pressed, transfers focus
     * to this element.
     */
    public java.lang.String getAccesskey() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.accesskey);

    }

    /**
     * <p>Set the value of the <code>accesskey</code> property.</p>
     */
    public void setAccesskey(java.lang.String accesskey) {
        getStateHelper().put(PropertyKeys.accesskey, accesskey);
    }


    /**
     * <p>Return the value of the <code>dir</code> property.</p>
     * <p>Contents: Direction indication for text that does not inherit directionality.
     * Valid values are "LTR" (left-to-right) and "RTL" (right-to-left).
     */
    public java.lang.String getDir() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.dir);

    }

    /**
     * <p>Set the value of the <code>dir</code> property.</p>
     */
    public void setDir(java.lang.String dir) {
        getStateHelper().put(PropertyKeys.dir, dir);
    }


    /**
     * <p>Return the value of the <code>disabled</code> property.</p>
     * <p>Contents: Flag indicating that this element must never receive focus or
     * be included in a subsequent submit.  A value of false causes
     * no attribute to be rendered, while a value of true causes the
     * attribute to be rendered as disabled="disabled".
     */
    public boolean isDisabled() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.disabled, false);

    }

    /**
     * <p>Set the value of the <code>disabled</code> property.</p>
     */
    public void setDisabled(boolean disabled) {
        getStateHelper().put(PropertyKeys.disabled, disabled);
    }


    /**
     * <p>Return the value of the <code>label</code> property.</p>
     * <p>Contents: A localized user presentable name for this component.
     */
    public java.lang.String getLabel() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.label);

    }

    /**
     * <p>Set the value of the <code>label</code> property.</p>
     */
    public void setLabel(java.lang.String label) {
        getStateHelper().put(PropertyKeys.label, label);
    }


    /**
     * <p>Return the value of the <code>lang</code> property.</p>
     * <p>Contents: Code describing the language used in the generated markup
     * for this component.
     */
    public java.lang.String getLang() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.lang);

    }

    /**
     * <p>Set the value of the <code>lang</code> property.</p>
     */
    public void setLang(java.lang.String lang) {
        getStateHelper().put(PropertyKeys.lang, lang);
    }


    /**
     * <p>Return the value of the <code>readonly</code> property.</p>
     * <p>Contents: Flag indicating that this component will prohibit changes by
     * the user.  The element may receive focus unless it has also
     * been disabled.  A value of false causes
     * no attribute to be rendered, while a value of true causes the
     * attribute to be rendered as readonly="readonly".
     */
    public boolean isReadonly() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.readonly, false);

    }

    /**
     * <p>Set the value of the <code>readonly</code> property.</p>
     */
    public void setReadonly(boolean readonly) {
        getStateHelper().put(PropertyKeys.readonly, readonly);
    }


    /**
     * <p>Return the value of the <code>style</code> property.</p>
     * <p>Contents: CSS style(s) to be applied when this component is rendered.
     */
    public java.lang.String getStyle() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.style);

    }

    /**
     * <p>Set the value of the <code>style</code> property.</p>
     */
    public void setStyle(java.lang.String style) {
        getStateHelper().put(PropertyKeys.style, style);
    }


    /**
     * <p>Return the value of the <code>styleClass</code> property.</p>
     * <p>Contents: Space-separated list of CSS style class(es) to be applied when
     * this element is rendered.  This value must be passed through
     * as the "class" attribute on generated markup.
     */
    public java.lang.String getStyleClass() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.styleClass);

    }

    /**
     * <p>Set the value of the <code>styleClass</code> property.</p>
     */
    public void setStyleClass(java.lang.String styleClass) {
        getStateHelper().put(PropertyKeys.styleClass, styleClass);
    }


    /**
     * <p>Return the value of the <code>tabindex</code> property.</p>
     * <p>Contents: Position of this element in the tabbing order
     * for the current document.  This value must be
     * an integer between 0 and 32767.
     */
    public java.lang.String getTabindex() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.tabindex);

    }

    /**
     * <p>Set the value of the <code>tabindex</code> property.</p>
     */
    public void setTabindex(java.lang.String tabindex) {
        getStateHelper().put(PropertyKeys.tabindex, tabindex);
    }


    /**
     * <p>Return the value of the <code>title</code> property.</p>
     * <p>Contents: Advisory title information about markup elements generated
     * for this component.
     */
    public java.lang.String getTitle() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.title);

    }

    /**
     * <p>Set the value of the <code>title</code> property.</p>
     */
    public void setTitle(java.lang.String title) {
        getStateHelper().put(PropertyKeys.title, title);
    }
    //switch property

    public String getDataSize() {
        return (String) getStateHelper().eval(PropertyKeys.data_size);
    }

    public void setDataSize(String dataSize) {
        getStateHelper().put(PropertyKeys.data_size, dataSize);
    }

    public Boolean getDataAnimate() {
        return (Boolean) getStateHelper().eval(PropertyKeys.data_animate);
    }

    public void setDataAnimate(Boolean dataAnimate) {
        getStateHelper().put(PropertyKeys.data_animate, dataAnimate);
    }

    public Boolean getDataIndeterminate() {
        return (Boolean) getStateHelper().eval(PropertyKeys.data_indeterminate);
    }

    public void setDataIndeterminate(Boolean dataIndeterminate) {
        getStateHelper().put(PropertyKeys.data_indeterminate, dataIndeterminate);
    }

    public Boolean getDataInverse() {
        return (Boolean) getStateHelper().eval(PropertyKeys.data_inverse);
    }

    public void setDataInverse(Boolean dataInverse) {
        getStateHelper().put(PropertyKeys.data_inverse, dataInverse);
    }

    public Boolean getDataRadioAllOff() {
        return (Boolean) getStateHelper().eval(PropertyKeys.data_radio_all_off);
    }

    public void setDataRadioAllOff(Boolean dataRadioAllOff) {
        getStateHelper().put(PropertyKeys.data_radio_all_off, dataRadioAllOff);
    }

    public String getDataOnColor() {
        return (String) getStateHelper().eval(PropertyKeys.data_on_color);
    }

    public void setDataOnColor(String dataOnColor) {
        getStateHelper().put(PropertyKeys.data_on_color, dataOnColor);
    }

    public String getDataOffColor() {
        return (String) getStateHelper().eval(PropertyKeys.data_off_color);
    }

    public void setDataOffColor(String dataOffColor) {
        getStateHelper().put(PropertyKeys.data_off_color, dataOffColor);
    }

    public String getDataOnText() {
        return (String) getStateHelper().eval(PropertyKeys.data_on_text);
    }

    public void setDataOnText(String dataOnText) {
        getStateHelper().put(PropertyKeys.data_on_text, dataOnText);
    }

    public String getDataOffText() {
        return (String) getStateHelper().eval(PropertyKeys.data_off_text);
    }

    public void setDataOffText(String dataOffText) {
        getStateHelper().put(PropertyKeys.data_off_text, dataOffText);
    }

    public String getDataLabelText() {
        return (String) getStateHelper().eval(PropertyKeys.data_label_text);
    }

    public void setDataLabelText(String dataLabelText) {
        getStateHelper().put(PropertyKeys.data_label_text, dataLabelText);
    }

    public String getDataHandleWidth() {
        return (String) getStateHelper().eval(PropertyKeys.data_handle_width);
    }

    public void setDataHandleWidth(String dataHandleWidth) {
        getStateHelper().put(PropertyKeys.data_handle_width, dataHandleWidth);
    }

    public String getDataLabelWidth() {
        return (String) getStateHelper().eval(PropertyKeys.data_label_width);
    }

    public void setDataLabelWidth(String dataLabelWidth) {
        getStateHelper().put(PropertyKeys.data_label_width, dataLabelWidth);
    }

    public String getDataBaseClass() {
        return (String) getStateHelper().eval(PropertyKeys.data_base_class);
    }

    public void setDataBaseClass(String dataBaseClass) {
        getStateHelper().put(PropertyKeys.data_base_class, dataBaseClass);
    }

    public String getDataWrapperClass() {
        return (String) getStateHelper().eval(PropertyKeys.data_wrapper_class);
    }

    public void setDataWrapperClass(String dataWrapperClass) {
        getStateHelper().put(PropertyKeys.data_wrapper_class, dataWrapperClass);
    }

    public Boolean getDataInLink(){
        return (Boolean) getStateHelper().eval(PropertyKeys.data_in_link);
    }

    public void setDataInLink(Boolean value){
        getStateHelper().put(PropertyKeys.data_in_link,value);
    }

    private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("blur", "change", "click", "valueChange", "dblclick", "focus", "keydown", "keypress", "keyup", "mousedown", "mousemove", "mouseout", "mouseover", "mouseup", "select"));

    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }


    public String getDefaultEventName() {
        return "valueChange";
    }


    @Override
    public String getFamily() {

        return "com.dgsoft.faces.BootStrap";
    }




}
