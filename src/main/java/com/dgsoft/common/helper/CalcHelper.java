package com.dgsoft.common.helper;

import org.jboss.seam.annotations.Name;

import java.util.Arrays;

/**
 * Created by cooper on 1/30/15.
 */
@Name("calcHelper")
public class CalcHelper {

    public boolean stringIn(String src,String... descs){
        return Arrays.asList(descs).contains(src);
    }

}
