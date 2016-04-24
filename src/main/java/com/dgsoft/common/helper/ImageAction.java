package com.dgsoft.common.helper;

import com.dgsoft.common.system.RunParam;
import org.jboss.seam.annotations.Name;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cooper on 3/30/16.
 */
@Name("imageAction")
public class ImageAction {




    public ByteArrayInputStream getImage(String fileId){

        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            URL url = new URL(RunParam.instance().getStringParamValue("IMG_SERVER_ADDRESS") + "img/800x600s/" + fileId);

            url.openConnection().connect();

            BufferedImage image = ImageIO.read(url.openStream());

            outStream.close();
            ImageIO.write(image,"JPEG",outStream);
            return new ByteArrayInputStream(outStream.toByteArray());

        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("url is fail");
        } catch (IOException e) {

            throw new IllegalArgumentException("url is fail");
        }
    }

}
