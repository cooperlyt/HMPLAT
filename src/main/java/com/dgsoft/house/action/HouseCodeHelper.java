package com.dgsoft.house.action;

import com.dgsoft.common.GBT;
import com.dgsoft.common.system.NumberBuilder;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.model.Build;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by cooper on 8/11/14.
 */
@Name("houseCodeHelper")
@AutoCreate
public class HouseCodeHelper {

    @In
    private RunParam runParam;

    @In
    private NumberBuilder numberBuilder;


    public boolean isRequestMapCode() {
        return GBT.HouseIdGenType.valueOf(runParam.getStringParamValue("house.id.gentype")).equals(GBT.HouseIdGenType.JDJT246_6) ||
                (runParam.getIntParamValue("HouseCodeDisplayModel") == 2);
    }

    public boolean isRequestStreetCode() {
        return GBT.HouseIdGenType.valueOf(runParam.getStringParamValue("house.id.gentype")).equals(GBT.HouseIdGenType.JDJT246_5);
    }

    public boolean isRequestCossCode() {
        return GBT.HouseIdGenType.valueOf(runParam.getStringParamValue("house.id.gentype")).equals(GBT.HouseIdGenType.JDJT246_4);
    }

    public String genHouseCode(String buildCode, int nextId){
        GBT.HouseIdGenType genType = GBT.HouseIdGenType.valueOf(runParam.getStringParamValue("house.id.gentype"));
        if (genType.isMark()){
            return GBT.getJDJT246(buildCode.substring(0, 21),nextId);
        }else{
            return buildCode + "-" + nextId;
        }

    }

    public void genBuildCode(Build build) {
        String result = GBT.formatCode(build.getProject().getSection().getDistrict().getId(), 9);

        GBT.HouseIdGenType genType = GBT.HouseIdGenType.valueOf(runParam.getStringParamValue("house.id.gentype"));
        switch (genType) {

            case JDJT246_4:
                DecimalFormat df = new DecimalFormat("#0");
                df.setGroupingUsed(false);
                df.setRoundingMode(RoundingMode.DOWN);
                result = result + GBT.formatCode(df.format(build.getLat()), 6) + GBT.formatCode(df.format(build.getLng()), 6);

                break;
            case JDJT246_3:
                String yearStr = build.getCompleteYear().trim();
                int j = 6 - yearStr.length();
                for (int i = 0; i < j; i++) {
                    yearStr = yearStr + "*";
                }
                result = result + yearStr;
                result = result + GBT.formatCode(String.valueOf(numberBuilder.getNumber("BUILDCODE_" + result)), 6);
                break;

            case JDJT246_5:
                result += GBT.formatCode(build.getStreetCode(), 4);
                result += GBT.formatCode(build.getBlockNo(), 4);

                result = result + GBT.formatCode(String.valueOf(numberBuilder.getNumber("BUILDCODE_" + result)), 4);
                break;
            case JDJT246_6:
                result += GBT.formatCode(build.getMapNumber() + build.getBlockNo(), 8);
                result = result + GBT.formatCode(String.valueOf(numberBuilder.getNumber("BUILDCODE_" + result)), 4);
                break;
            case SHORT:
                result = NumberBuilder.instance().getNumber("BUILD_CODE_SHORT",4);
                break;
        }
        if (genType.isMark()){
            build.setId(GBT.getJDJT246(result, 0));
        }else{
            build.setId(result);
        }


    }


}
