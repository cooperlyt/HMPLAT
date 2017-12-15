package com.dgsoft.house.action;

import com.dgsoft.common.DataFormat;
import com.dgsoft.common.GBT;
import com.dgsoft.common.SetLinkList;
import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.model.Word;
import com.dgsoft.house.AutoGridMapComparator;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.OrderComparator;
import com.dgsoft.house.model.*;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseRecord;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by cooper on 7/29/14.
 */
@Name("buildHome")
public class BuildHome extends HouseEntityHome<Build> {

    @In
    private FacesMessages facesMessages;

    @In
    private Map<String, String> messages;

    @In(create = true)
    private FacesContext facesContext;

    @In
    private HouseCodeHelper houseCodeHelper;

    @In(required = false)
    private ProjectHome projectHome;

//    private SetLinkList<House> houses;
//
//    public SetLinkList<House> getHouses() {
//        if (houses == null) {
//            houses = new SetLinkList<House>(getInstance().getHouses());
//        }
//        return houses;
//    }

    @Override
    protected Build createInstance(){
        return new Build(new Date());
    }

    private void addBuildMBBConflictMessages() {
        facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "ConflictMBB");
    }

    private void addBuildPBConflictMessages() {
        facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "ConfilicPB");
    }

    public String getShortName(){
        if (isIdDefined()){
            return getInstance().getBuildNo() + "幢" +  ((getInstance().getBuildDevNumber() == null || getInstance().getBuildDevNumber().trim().equals("")) ? "" : getInstance().getBuildDevNumber() + "号楼")
                    + (getInstance().getDoorNo() == null ? "" : getInstance().getDoorNo()) ;

        }else{
            return null;
        }
    }


    public void downloadHouseExcel(){
        //TODO change to record db



        try {
            Workbook workbook = WorkbookFactory.create(facesContext.getExternalContext().getResourceAsStream("/resources/houseMapping.xlsx"));
            Sheet sheet = workbook.getSheetAt(0);

            Row row = sheet.getRow(0);
            Cell cell = row.getCell(1);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(getInstance().getMapNumber());

            cell = row.getCell(3);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(getInstance().getBlockNo());

            cell = row.getCell(5);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(getInstance().getBuildNo());

            cell = row.getCell(7);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(getInstance().getBuildName());

            int rowIndex = 2;
            for(House house: getInstance().getHouses() ){

                int cellIndex = 0;
                row = sheet.createRow(rowIndex++);
                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_STRING);
                cell.setCellValue(house.getHouseOrder());

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_STRING);
                cell.setCellValue(house.getHouseUnitName());

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_STRING);
                cell.setCellValue(house.getInFloorName());

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_NUMERIC);
                if (house.getHouseArea() != null)
                    cell.setCellValue(house.getHouseArea().doubleValue());

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_NUMERIC);
                if (house.getUseArea() != null)
                    cell.setCellValue(house.getUseArea().doubleValue());


                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_NUMERIC);
                if (house.getCommArea() != null)
                    cell.setCellValue(house.getCommArea().doubleValue());

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_NUMERIC);
                if (house.getShineArea() != null)
                    cell.setCellValue(house.getShineArea().doubleValue());

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_NUMERIC);
                if (house.getLoftArea() != null)
                    cell.setCellValue(house.getLoftArea().doubleValue());

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_NUMERIC);
                if (house.getCommParam() != null)
                    cell.setCellValue(house.getCommParam().doubleValue());

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_STRING);
                cell.setCellValue(DictionaryWord.instance().getEnumLabel(house.getHouseType()));

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_STRING);
                cell.setCellValue(DictionaryWord.instance().getEnumLabel(house.getUseType()));

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_STRING);
                cell.setCellValue(house.getDesignUseType());


                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_STRING);
                cell.setCellValue(house.isHaveDownRoom()? "有" : "无");


                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_STRING);
                cell.setCellValue(house.getAddress());

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_STRING);
                cell.setCellValue(DictionaryWord.instance().getWordValue(house.getStructure()));

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_STRING);
                cell.setCellValue(DictionaryWord.instance().getWordValue(house.getKnotSize()));

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_STRING);
                cell.setCellValue(DictionaryWord.instance().getWordValue(house.getDirection()));


                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_STRING);
                cell.setCellValue(DictionaryWord.instance().getWordValue(house.getEastWall()));

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_STRING);
                cell.setCellValue(DictionaryWord.instance().getWordValue(house.getWestWall()));

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_STRING);
                cell.setCellValue(DictionaryWord.instance().getWordValue(house.getSouthWall()));

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_STRING);
                cell.setCellValue(DictionaryWord.instance().getWordValue(house.getNorthWall()));
            }


            sheet.setForceFormulaRecalculation(true);
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.responseReset();
            externalContext.setResponseContentType("application/vnd.ms-excel");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=houseMapping.xlsx");
            try {
                workbook.write(externalContext.getResponseOutputStream());
                facesContext.responseComplete();
            } catch (IOException e) {
                facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "ExportIOError");
                Logging.getLog(getClass()).error("export error", e);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected boolean verifyUpdateAvailable() {


        if (getEntityManager().createQuery("select count(build.id) from Build build " +
                "where build.project.id = :projectId and build.buildNo <> null and " +
                "build.buildNo = :buildNo and build.id <> :buildId", Long.class)
                .setParameter("projectId", getInstance().getProject().getId())
                .setParameter("buildNo", getInstance().getBuildNo())
                .setParameter("buildId", getInstance().getId()).getSingleResult() > 0) {
            addBuildPBConflictMessages();
            return false;
        }


        if (getEntityManager().createQuery("select count(build.id) from Build build " +
                "where build.mapNumber = :mapNumber and build.blockNo = :blockNumber and " +
                "build.buildNo = :buildNumber and build.id <> :buildId", Long.class)
                .setParameter("mapNumber", getInstance().getMapNumber())
                .setParameter("blockNumber", getInstance().getBlockNo())
                .setParameter("buildNumber", getInstance().getBuildNo())
                .setParameter("buildId", getInstance().getId()).getSingleResult() > 0) {
            addBuildMBBConflictMessages();
            return false;
        }

        if (getInstance().getFloorCount() <= 0){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "FloorCountIsZeroError");
            return false;
        }

        return true;
    }

    @Override
    protected boolean verifyPersistAvailable() {

        if (getEntityManager().createQuery("select count(build.id) from Build build " +
                "where build.project.id = :projectId and build.buildNo <> null and " +
                "build.buildNo = :buildNo", Long.class)
                .setParameter("projectId", projectHome.getInstance().getId())
                .setParameter("buildNo", getInstance().getBuildNo())
                .getSingleResult() > 0) {
            addBuildPBConflictMessages();
            return false;
        }


        if (getEntityManager().createQuery("select count(build.id) from Build build " +
                "where build.mapNumber = :mapNumber and build.blockNo = :blockNumber and " +
                "build.buildNo = :buildNumber", Long.class)
                .setParameter("mapNumber", getInstance().getMapNumber())
                .setParameter("blockNumber", getInstance().getBlockNo())
                .setParameter("buildNumber", getInstance().getBuildNo())
                .getSingleResult() > 0) {
            addBuildMBBConflictMessages();
            return false;
        }

        if (getInstance().getFloorCount() <= 0){
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "FloorCountIsZeroError");
            return false;
        }

        getInstance().setProject(projectHome.getInstance());
        if (getInstance().getId() == null)
            houseCodeHelper.genBuildCode(getInstance());
        return true;
    }


    public boolean isHaveHouse() {
        return !getInstance().getHouses().isEmpty();
    }


    public void genBuildName() {

        Logging.getLog(getClass()).debug("genBuildName");
        String result = projectHome.getInstance().getDistrictName() +
                projectHome.getInstance().getProjectName();

        if ((getInstance().getBuildNo() != null) && !getInstance().getBuildNo().trim().equals("")) {
            result += getInstance().getBuildNo() + messages.get("MapIdentification_build");
        }

        if (getInstance().getBuildDevNumber() != null) {
            result += getInstance().getBuildDevNumber() + messages.get("BuildBuildNameSuffix");
        }

        getInstance().setName(result);
    }

    public String genHouseOrder() {
        if (!isManaged()) {
            houseCodeHelper.genBuildCode(getInstance());
            getInstance().setNextHouseOrder(1);
        }

        String result = houseCodeHelper.genHouseCode(getInstance().getId(), getInstance().getNextHouseOrder());
        //String result = GBT.getJDJT246(getBuildCode(), getInstance().getNextHouseOrder());
        getInstance().setNextHouseOrder(getInstance().getNextHouseOrder() + 1);
        return result;
    }


    @Override
    protected boolean verifyRemoveAvailable() {
        if (isManaged()) {
            if (getEntityManager().createQuery("select count(house.id) from House house where house.build.id = :buildId", Long.class).
                    setParameter("buildId", getInstance().getId()).getSingleResult() > 0) {
                facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "BuildCantDelete");
                return false;
            }
        }
        return true;
    }


    public BigDecimal getTotalHouseArea() {
        BigDecimal result = BigDecimal.ZERO;
        for (House house : getInstance().getHouses()) {
            if (!house.isDeleted())
                result = result.add(house.getHouseArea());
        }
        return result;
    }

    public BigDecimal getTotalHouseUseArea() {
        BigDecimal result = BigDecimal.ZERO;
        for (House house : getInstance().getHouses()) {
            if (!house.isDeleted())
                result = result.add(house.getUseArea());
        }
        return result;
    }

    public int getHouseCount(){
        int result = 0;
        for (House house : getInstance().getHouses()) {
            if (!house.isDeleted())
                result ++;
        }
        return result;
    }

//    public Map<HouseInfo.HouseStatus, CountAreaEntry> getStatusTotalMap() {
//        Map<HouseInfo.HouseStatus, CountAreaEntry> result = new HashMap<HouseInfo.HouseStatus, CountAreaEntry>();
//        for (House house : getInstance().getHouses()) {
//            CountAreaEntry entry = result.get(house.getMasterStatus());
//            if (entry == null) {
//                result.put(house.getMasterStatus(), new CountAreaEntry(house.getHouseArea(), house.getUseArea()));
//            } else {
//                entry.addArea(house.getHouseArea(), house.getUseArea());
//            }
//        }
//        return result;
//    }

//    public List<Map.Entry<HouseInfo.HouseStatus, CountAreaEntry>> getStatusTotalList() {
//        List<Map.Entry<HouseInfo.HouseStatus, CountAreaEntry>> result = new ArrayList<Map.Entry<HouseInfo.HouseStatus, CountAreaEntry>>(getStatusTotalMap().entrySet());
//        Collections.sort(result, new Comparator<Map.Entry<HouseInfo.HouseStatus, CountAreaEntry>>() {
//            @Override
//            public int compare(Map.Entry<HouseInfo.HouseStatus, CountAreaEntry> o1, Map.Entry<HouseInfo.HouseStatus, CountAreaEntry> o2) {
//                return HouseInfo.StatusComparator.getInstance().compare(o1.getKey(), o2.getKey());
//            }
//        });
//        return result;
//    }

    public Map<Word, CountAreaEntry> getUseTypeTotalMap() {
        Map<Word, CountAreaEntry> result = new HashMap<Word, CountAreaEntry>();
        for (House house : getInstance().getHouses()) {
            if (!house.isDeleted()) {
                CountAreaEntry entry = result.get(house.getDesignUseType());
                if (entry != null) {
                    entry.addArea(house.getHouseArea(), house.getUseArea());
                } else {
                    result.put(DictionaryWord.instance().getWord(house.getDesignUseType()),
                            new CountAreaEntry(house.getHouseArea(), house.getUseArea()));
                }
            }
        }
        return result;
    }

    public List<Map.Entry<Word, CountAreaEntry>> getUseTypeTotalList() {
        List<Map.Entry<Word, CountAreaEntry>> result = new ArrayList<Map.Entry<Word, CountAreaEntry>>(getUseTypeTotalMap().entrySet());
        Collections.sort(result, new Comparator<Map.Entry<Word, CountAreaEntry>>() {
            @Override
            public int compare(Map.Entry<Word, CountAreaEntry> o1, Map.Entry<Word, CountAreaEntry> o2) {

                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return 1;
                }
                return new Integer(o1.getKey().getPriority()).compareTo(o2.getKey().getPriority());
            }
        });
        return result;
    }

    public static class CountAreaEntry {

        public CountAreaEntry(BigDecimal area, BigDecimal useArea) {
            this.count = 1;
            this.area = area;
            this.useArea = useArea;
        }

        private int count;

        private BigDecimal area;

        private BigDecimal useArea;

        public void addArea(BigDecimal area, BigDecimal useArea) {
            count++;
            if (area != null)
            this.area = this.area.add(area);
            if(useArea != null)
            this.useArea = this.useArea.add(useArea);
        }

        public int getCount() {
            return count;
        }


        public BigDecimal getArea() {
            return area;
        }


        public BigDecimal getUseArea() {
            return useArea;
        }

    }

    public static BuildGridMap genIdleHouseGridMap(Collection<? extends HouseInfo> houseInfos) {
        BuildGridMap result = new BuildGridMap();

        //所有单元和序名
        List<String> unitNames = new ArrayList<String>();
        List<Integer> floorNames = new ArrayList<Integer>();
        for(HouseInfo houseInfo: houseInfos){
            String unitName = houseInfo.getHouseUnitName();
            if (unitName == null){
                unitName = "";
            }
            if (!unitNames.contains(unitName)){
                unitNames.add(unitName);
            }
            Integer floorIndex =  AutoGridMapComparator.getFloorIndexExtract().getIndex(houseInfo.getInFloorName());

            if(!floorNames.contains(floorIndex)){
                floorNames.add(floorIndex);
            }
        }

        //排序
        Collections.sort(unitNames, AutoGridMapComparator.getUnitComparator());
        Collections.sort(floorNames,AutoGridMapComparator.getFloorComparator());

        List<HouseGridTitle> titleList = new ArrayList<HouseGridTitle>();

        HouseGridTitle bt = new HouseGridTitle(result,1,"");
        bt.setOrder(0);
        result.getHouseGridTitles().add(bt);

        int order = 1;
        for(String name: unitNames){

            Map<Integer,Integer> counts = new HashMap<Integer, Integer>();
            for(HouseInfo houseInfo: houseInfos){
                if (("".equals(name) &&  houseInfo.getHouseUnitName() == null) || name.equals(houseInfo.getHouseUnitName())) {
                    Integer count = counts.get(AutoGridMapComparator.getFloorIndexExtract().getIndex(houseInfo.getInFloorName()));
                    if (count == null) {
                        counts.put(AutoGridMapComparator.getFloorIndexExtract().getIndex(houseInfo.getInFloorName()), 1);
                    } else {
                        counts.put(AutoGridMapComparator.getFloorIndexExtract().getIndex(houseInfo.getInFloorName()), count.intValue() + 1);
                    }
                }
            }

            HouseGridTitle title = new HouseGridTitle(result,0,name);
            for(Integer i: counts.values()){
                if (title.getColspan() < i.intValue()){
                    title.setColspan(i.intValue());
                }
            }
            title.setOrder(order++);
            titleList.add(title);
            result.getHouseGridTitles().add(title);
        }



        int floorIndex = 0;
        for(Integer name: floorNames){
            Logging.getLog(BuildHome.class).debug("floorNames:" + name);
            int houseOrder = 0;
            GridRow row = new GridRow(result, String.valueOf(name), floorIndex++);
            row.setOrder(floorIndex);
            result.getGridRows().add(row);

            //String floorName = null;
            for(HouseGridTitle title: titleList){

                List<HouseInfo> pickHouse = new ArrayList<HouseInfo>();
                for(HouseInfo houseInfo: houseInfos){

                    if (name.equals(AutoGridMapComparator.getFloorIndexExtract().getIndex(houseInfo.getInFloorName()))
                            && title.getTitle().equals(houseInfo.getHouseUnitName())){
                        pickHouse.add(houseInfo);
                        //if (floorName == null || houseInfo.getInFloorName().length() < floorName.length()){
                        //    floorName = houseInfo.getInFloorName();
                        //}
                    }
                }

                Collections.sort(pickHouse,AutoGridMapComparator.getHouseComparator());


                for(HouseInfo houseInfo: pickHouse){
                    GridBlock gb = new GridBlock(houseInfo,1,1);
                    gb.setOrder(houseOrder++);
                    gb.setGridRow(row);
                    row.getGridBlocks().add(gb);
                }

                if (pickHouse.size() < title.getColspan()){
                    GridBlock gb = new GridBlock(title.getColspan() - pickHouse.size() ,1);
                    gb.setOrder(houseOrder++);
                    gb.setGridRow(row);
                    row.getGridBlocks().add(gb);
                }

            }
            //row.setTitle(floorName);
        }

        return result;
    }

}
