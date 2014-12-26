package com.dgsoft.faces.renderkit.html;

import com.dgsoft.faces.component.UISwitch;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * Created by cooper on 12/20/14.
 */
public class SwitchRender extends HtmlCheckboxRendererBase {

    private final static String[] SWITCH_PROPERTY = {
            "data-size",
            "data-animate",
            "data-indeterminate",
            "data-inverse",
            "data-radio-all-off",
            "data-on-color",
            "data-off-color",
            "data-handle-width",
            "data-label-width",
            "data-base-class",
            "data-wrapper-class",
            "data-in-link"
    };

    @Override
    protected void renderCheckBoxAttributes(ResponseWriter writer,
                                            UIComponent uiComponent) throws IOException {


        super.renderCheckBoxAttributes(writer, uiComponent);
        if (uiComponent instanceof UISwitch) {

            HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, SWITCH_PROPERTY);

            UISwitch uiSwitch = (UISwitch) uiComponent;

            writer.writeAttribute("data-on-text", uiSwitch.getOnText(), "onText");
            writer.writeAttribute("data-off-text", uiSwitch.getOffText(), "offText");

            writer.writeAttribute("data-label-text", uiSwitch.getLabelText(), "labelText");


        }

    }

}
