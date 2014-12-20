package com.dgsoft.faces.component;

import javax.el.ValueExpression;
import javax.faces.component.behavior.ClientBehaviorHolder;
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
        onblur,
        onchange,
        onclick,
        ondblclick,
        onfocus,
        onkeydown,
        onkeypress,
        onkeyup,
        onmousedown,
        onmousemove,
        onmouseout,
        onmouseover,
        onmouseup,
        onselect,
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
        data_wrapper_class;
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
        handleAttribute("accesskey", accesskey);
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
        handleAttribute("dir", dir);
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
        handleAttribute("lang", lang);
    }


    /**
     * <p>Return the value of the <code>onblur</code> property.</p>
     * <p>Contents: Javascript code executed when this element loses focus.
     */
    public java.lang.String getOnblur() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onblur);

    }

    /**
     * <p>Set the value of the <code>onblur</code> property.</p>
     */
    public void setOnblur(java.lang.String onblur) {
        getStateHelper().put(PropertyKeys.onblur, onblur);
        handleAttribute("onblur", onblur);
    }


    /**
     * <p>Return the value of the <code>onchange</code> property.</p>
     * <p>Contents: Javascript code executed when this element loses focus
     * and its value has been modified since gaining focus.
     */
    public java.lang.String getOnchange() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onchange);

    }

    /**
     * <p>Set the value of the <code>onchange</code> property.</p>
     */
    public void setOnchange(java.lang.String onchange) {
        getStateHelper().put(PropertyKeys.onchange, onchange);
        handleAttribute("onchange", onchange);
    }


    /**
     * <p>Return the value of the <code>onclick</code> property.</p>
     * <p>Contents: Javascript code executed when a pointer button is
     * clicked over this element.
     */
    public java.lang.String getOnclick() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onclick);

    }

    /**
     * <p>Set the value of the <code>onclick</code> property.</p>
     */
    public void setOnclick(java.lang.String onclick) {
        getStateHelper().put(PropertyKeys.onclick, onclick);
    }


    /**
     * <p>Return the value of the <code>ondblclick</code> property.</p>
     * <p>Contents: Javascript code executed when a pointer button is
     * double clicked over this element.
     */
    public java.lang.String getOndblclick() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.ondblclick);

    }

    /**
     * <p>Set the value of the <code>ondblclick</code> property.</p>
     */
    public void setOndblclick(java.lang.String ondblclick) {
        getStateHelper().put(PropertyKeys.ondblclick, ondblclick);
        handleAttribute("ondblclick", ondblclick);
    }


    /**
     * <p>Return the value of the <code>onfocus</code> property.</p>
     * <p>Contents: Javascript code executed when this element receives focus.
     */
    public java.lang.String getOnfocus() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onfocus);

    }

    /**
     * <p>Set the value of the <code>onfocus</code> property.</p>
     */
    public void setOnfocus(java.lang.String onfocus) {
        getStateHelper().put(PropertyKeys.onfocus, onfocus);
        handleAttribute("onfocus", onfocus);
    }


    /**
     * <p>Return the value of the <code>onkeydown</code> property.</p>
     * <p>Contents: Javascript code executed when a key is
     * pressed down over this element.
     */
    public java.lang.String getOnkeydown() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onkeydown);

    }

    /**
     * <p>Set the value of the <code>onkeydown</code> property.</p>
     */
    public void setOnkeydown(java.lang.String onkeydown) {
        getStateHelper().put(PropertyKeys.onkeydown, onkeydown);
        handleAttribute("onkeydown", onkeydown);
    }


    /**
     * <p>Return the value of the <code>onkeypress</code> property.</p>
     * <p>Contents: Javascript code executed when a key is
     * pressed and released over this element.
     */
    public java.lang.String getOnkeypress() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onkeypress);

    }

    /**
     * <p>Set the value of the <code>onkeypress</code> property.</p>
     */
    public void setOnkeypress(java.lang.String onkeypress) {
        getStateHelper().put(PropertyKeys.onkeypress, onkeypress);
        handleAttribute("onkeypress", onkeypress);
    }


    /**
     * <p>Return the value of the <code>onkeyup</code> property.</p>
     * <p>Contents: Javascript code executed when a key is
     * released over this element.
     */
    public java.lang.String getOnkeyup() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onkeyup);

    }

    /**
     * <p>Set the value of the <code>onkeyup</code> property.</p>
     */
    public void setOnkeyup(java.lang.String onkeyup) {
        getStateHelper().put(PropertyKeys.onkeyup, onkeyup);
        handleAttribute("onkeyup", onkeyup);
    }


    /**
     * <p>Return the value of the <code>onmousedown</code> property.</p>
     * <p>Contents: Javascript code executed when a pointer button is
     * pressed down over this element.
     */
    public java.lang.String getOnmousedown() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onmousedown);

    }

    /**
     * <p>Set the value of the <code>onmousedown</code> property.</p>
     */
    public void setOnmousedown(java.lang.String onmousedown) {
        getStateHelper().put(PropertyKeys.onmousedown, onmousedown);
        handleAttribute("onmousedown", onmousedown);
    }


    /**
     * <p>Return the value of the <code>onmousemove</code> property.</p>
     * <p>Contents: Javascript code executed when a pointer button is
     * moved within this element.
     */
    public java.lang.String getOnmousemove() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onmousemove);

    }

    /**
     * <p>Set the value of the <code>onmousemove</code> property.</p>
     */
    public void setOnmousemove(java.lang.String onmousemove) {
        getStateHelper().put(PropertyKeys.onmousemove, onmousemove);
        handleAttribute("onmousemove", onmousemove);
    }


    /**
     * <p>Return the value of the <code>onmouseout</code> property.</p>
     * <p>Contents: Javascript code executed when a pointer button is
     * moved away from this element.
     */
    public java.lang.String getOnmouseout() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onmouseout);

    }

    /**
     * <p>Set the value of the <code>onmouseout</code> property.</p>
     */
    public void setOnmouseout(java.lang.String onmouseout) {
        getStateHelper().put(PropertyKeys.onmouseout, onmouseout);
        handleAttribute("onmouseout", onmouseout);
    }


    /**
     * <p>Return the value of the <code>onmouseover</code> property.</p>
     * <p>Contents: Javascript code executed when a pointer button is
     * moved onto this element.
     */
    public java.lang.String getOnmouseover() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onmouseover);

    }

    /**
     * <p>Set the value of the <code>onmouseover</code> property.</p>
     */
    public void setOnmouseover(java.lang.String onmouseover) {
        getStateHelper().put(PropertyKeys.onmouseover, onmouseover);
        handleAttribute("onmouseover", onmouseover);
    }


    /**
     * <p>Return the value of the <code>onmouseup</code> property.</p>
     * <p>Contents: Javascript code executed when a pointer button is
     * released over this element.
     */
    public java.lang.String getOnmouseup() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onmouseup);

    }

    /**
     * <p>Set the value of the <code>onmouseup</code> property.</p>
     */
    public void setOnmouseup(java.lang.String onmouseup) {
        getStateHelper().put(PropertyKeys.onmouseup, onmouseup);
        handleAttribute("onmouseup", onmouseup);
    }


    /**
     * <p>Return the value of the <code>onselect</code> property.</p>
     * <p>Contents: Javascript code executed when text within this
     * element is selected by the user.
     */
    public java.lang.String getOnselect() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onselect);

    }

    /**
     * <p>Set the value of the <code>onselect</code> property.</p>
     */
    public void setOnselect(java.lang.String onselect) {
        getStateHelper().put(PropertyKeys.onselect, onselect);
        handleAttribute("onselect", onselect);
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
        handleAttribute("style", style);
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
        handleAttribute("tabindex", tabindex);
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
        handleAttribute("title", title);
    }
    //switch property

    public String getDataSize() {
        return (String) getStateHelper().eval(PropertyKeys.data_size);
    }

    public void setDataSize(String dataSize) {
        getStateHelper().put(PropertyKeys.data_size, dataSize);
        handleAttribute(PropertyKeys.data_size.name(),dataSize);
    }

    public Boolean getDataAnimate() {
        return (Boolean) getStateHelper().eval(PropertyKeys.data_animate);
    }

    public void setDataAnimate(Boolean dataAnimate) {
        getStateHelper().put(PropertyKeys.data_animate, dataAnimate);
        handleAttribute(PropertyKeys.data_animate.name(),dataAnimate);
    }

    public Boolean getDataIndeterminate() {
        return (Boolean) getStateHelper().eval(PropertyKeys.data_indeterminate);
    }

    public void setDataIndeterminate(Boolean dataIndeterminate) {
        getStateHelper().put(PropertyKeys.data_indeterminate, dataIndeterminate);
        handleAttribute(PropertyKeys.data_indeterminate.name(),dataIndeterminate);
    }

    public Boolean getDataInverse() {
        return (Boolean) getStateHelper().eval(PropertyKeys.data_inverse);
    }

    public void setDataInverse(Boolean dataInverse) {
        getStateHelper().put(PropertyKeys.data_inverse, dataInverse);
        handleAttribute(PropertyKeys.data_inverse.name(),dataInverse);
    }

    public Boolean getDataRadioAllOff() {
        return (Boolean) getStateHelper().eval(PropertyKeys.data_radio_all_off);
    }

    public void setDataRadioAllOff(Boolean dataRadioAllOff) {
        getStateHelper().put(PropertyKeys.data_radio_all_off, dataRadioAllOff);
        handleAttribute(PropertyKeys.data_radio_all_off.name(),dataRadioAllOff);
    }

    public String getDataOnColor() {
        return (String) getStateHelper().eval(PropertyKeys.data_on_color);
    }

    public void setDataOnColor(String dataOnColor) {
        getStateHelper().put(PropertyKeys.data_on_color, dataOnColor);
        handleAttribute(PropertyKeys.data_on_color.name(),dataOnColor);
    }

    public String getDataOffColor() {
        return (String) getStateHelper().eval(PropertyKeys.data_off_color);
    }

    public void setDataOffColor(String dataOffColor) {
        getStateHelper().put(PropertyKeys.data_off_color, dataOffColor);
        handleAttribute(PropertyKeys.data_off_color.name(),dataOffColor);
    }

    public String getDataOnText() {
        return (String) getStateHelper().eval(PropertyKeys.data_on_text);
    }

    public void setDataOnText(String dataOnText) {
        getStateHelper().put(PropertyKeys.data_on_text, dataOnText);
        handleAttribute(PropertyKeys.data_on_text.name(),dataOnText);
    }

    public String getDataOffText() {
        return (String) getStateHelper().eval(PropertyKeys.data_off_text);
    }

    public void setDataOffText(String dataOffText) {
        getStateHelper().put(PropertyKeys.data_off_text, dataOffText);
        handleAttribute(PropertyKeys.data_off_text.name(),dataOffText);
    }

    public String getDataLabelText() {
        return (String) getStateHelper().eval(PropertyKeys.data_label_text);
    }

    public void setDataLabelText(String dataLabelText) {
        getStateHelper().put(PropertyKeys.data_label_text, dataLabelText);
        handleAttribute(PropertyKeys.data_label_text.name(),dataLabelText);
    }

    public String getDataHandleWidth() {
        return (String) getStateHelper().eval(PropertyKeys.data_handle_width);
    }

    public void setDataHandleWidth(String dataHandleWidth) {
        getStateHelper().put(PropertyKeys.data_handle_width, dataHandleWidth);
        handleAttribute(PropertyKeys.data_handle_width.name(),dataHandleWidth);
    }

    public String getDataLabelWidth() {
        return (String) getStateHelper().eval(PropertyKeys.data_label_width);
    }

    public void setDataLabelWidth(String dataLabelWidth) {
        getStateHelper().put(PropertyKeys.data_label_width, dataLabelWidth);
        handleAttribute(PropertyKeys.data_label_width.name(),dataLabelWidth);
    }

    public String getDataBaseClass() {
        return (String) getStateHelper().eval(PropertyKeys.data_base_class);
    }

    public void setDataBaseClass(String dataBaseClass) {
        getStateHelper().put(PropertyKeys.data_base_class, dataBaseClass);
        handleAttribute(PropertyKeys.data_base_class.name(),dataBaseClass);
    }

    public String getDataWrapperClass() {
        return (String) getStateHelper().eval(PropertyKeys.data_wrapper_class);
    }

    public void setDataWrapperClass(String dataWrapperClass) {
        getStateHelper().put(PropertyKeys.data_wrapper_class, dataWrapperClass);
        handleAttribute(PropertyKeys.data_wrapper_class.name(),dataWrapperClass);
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


    private void handleAttribute(String name, Object value) {
        List<String> setAttributes = (List<String>) this.getAttributes().get("javax.faces.component.UIComponentBase.attributesThatAreSet");
        if (setAttributes == null) {
            String cname = this.getClass().getName();
            if (cname != null) {
                setAttributes = new ArrayList<String>(6);
                this.getAttributes().put("javax.faces.component.UIComponentBase.attributesThatAreSet", setAttributes);
            }
        }
        if (setAttributes != null) {
            if (value == null) {
                ValueExpression ve = getValueExpression(name);
                if (ve == null) {
                    setAttributes.remove(name);
                }
            } else if (!setAttributes.contains(name)) {
                setAttributes.add(name);
            }
        }
    }


}
