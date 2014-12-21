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
            "data_size",
            "data_animate",
            "data_indeterminate",
            "data_inverse",
            "data_radio_all_off",
            "data_on_color",
            "data_off_color",
            "data_on_text",
            "data_off_text",
            "data_label_text",
            "data_handle_width",
            "data_label_width",
            "data_base_class",
            "data_wrapper_class",
            "data_in_link"
    };

    @Override
    protected void renderCheckBoxAttributes(ResponseWriter writer,
                                            UIComponent uiComponent) throws IOException {


        super.renderCheckBoxAttributes(writer, uiComponent);
        if (uiComponent instanceof UISwitch) {

            HtmlRendererUtils.renderDataAttributes(writer,uiComponent);

        }

    }

}
