package com.dgsoft.house.action;

import com.dgsoft.common.GBT;
import com.dgsoft.common.system.NumberBuilder;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.model.NumberPool;
import com.dgsoft.common.system.model.SystemParam;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.Build;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by cooper on 7/29/14.
 */
@Name("buildHome")
public class BuildHome extends HouseEntityHome<Build> {

    @In
    private RunParam runParam;

    @In
    private NumberBuilder numberBuilder;

    public boolean isHaveHouse() {
        return !getInstance().getHouses().isEmpty();
    }

    private String getBuildCode() {
        if (isManaged()) {
            return getInstance().getId().substring(0,21);
        } else {
            throw new IllegalArgumentException("build not manager!");
        }
    }

    @Override
    protected boolean wire() {
        if (!isManaged()) {
            getInstance().setId(GBT.getJDJT246(genBuildCode(), 0));
        }
        return true;
    }

    private String genBuildCode() {
        String result = GBT.formatCode(getInstance().getProject().getSection().getDistrict().getId(), 9);

        switch (GBT.HouseIdGenType.valueOf(runParam.getStringParamValue("house.id.gentype"))) {

            case JDJT246_4:
                DecimalFormat df = new DecimalFormat("#0");
                df.setGroupingUsed(false);
                df.setRoundingMode(RoundingMode.DOWN);
                return result + GBT.formatCode(df.format(getInstance().getLat()), 6) + GBT.formatCode(df.format(getInstance().getLng()), 6);


            case JDJT246_3:
                String yearStr = getInstance().getCompleteDate().trim();
                int j = 6 - yearStr.length();
                for (int i = 0; i < j; i++) {
                    yearStr = yearStr + "*";
                }
                result = result + yearStr;
                result = result + GBT.formatCode(String.valueOf(numberBuilder.getNumber("BUILDCODE_" + result)), 6);
                break;

            case JDJT246_5:
                result += GBT.formatCode(getInstance().getStreetCode(), 4);
                if (GBT.HouseIdBuildCodePath.valueOf(runParam.getStringParamValue("house.id.useBlock")).equals(GBT.HouseIdBuildCodePath.MAP_BLOCK)) {
                    result += GBT.formatCode(getInstance().getBlockNo(), 4);
                } else {
                    result += GBT.formatCode(getInstance().getLandBlockCode(), 4);
                }

                result = result + GBT.formatCode(String.valueOf(numberBuilder.getNumber("BUILDCODE_" + result)), 4);
                break;
            case JDJT246_6:
                result += GBT.formatCode(getInstance().getMapNumber() + getInstance().getBlockNo(), 8);
                result = result + GBT.formatCode(String.valueOf(numberBuilder.getNumber("BUILDCODE_" + result)), 4);
                break;
        }
        return result;
    }


    public String genHouseOrder(int order) {
        return GBT.getJDJT246(getBuildCode(), order);
    }


}
