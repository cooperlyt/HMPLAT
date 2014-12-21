package com.dgsoft.faces.renderkit.html;

import com.dgsoft.faces.component.UISwitch;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * Created by cooper on 12/20/14.
 */
public class SwitchRender extends HtmlCheckboxRendererBase {


    @Override
    protected void renderCheckBoxAttributes(ResponseWriter writer,
                                            UIComponent uiComponent) throws IOException {


        super.renderCheckBoxAttributes(writer, uiComponent);
        if (uiComponent instanceof UISwitch) {

            HtmlRendererUtils.renderDataAttributes(writer,uiComponent);

        }

    }

}
