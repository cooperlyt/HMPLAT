package com.dgsoft.house.action;

import cc.coopersoft.house.UseType;
import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.HouseProperty;
import com.dgsoft.house.model.House;
import org.apache.poi.ss.usermodel.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;
import org.richfaces.event.FileUploadEvent;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cooper on 21/12/2016.
 */

@Name("mappingHouseOper")
public class MappingHouseOper {

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In
    private DictionaryWord dictionary;


    public void housesExcelUploadListener(FileUploadEvent event) throws Exception {
        Workbook workbook = WorkbookFactory.create(event.getUploadedFile().getInputStream());
        Logging.getLog(getClass()).debug("house excel begin import sheet count:" + workbook.getNumberOfSheets());
        for(int i = 0; i < workbook.getNumberOfSheets(); i++){

            Logging.getLog(getClass()).debug("house excel begin import sheet:" + i);

            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getLastRowNum() < 2) {
                Logging.getLog(getClass()).warn("house excel no line");
                break;
            }
            Row row = sheet.getRow(0);
            if (row == null) {
                Logging.getLog(getClass()).warn("house excel no row");
                break;
            }
            Cell cell = row.getCell(1);
            if (cell == null || cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals("")){
                Logging.getLog(getClass()).warn("house excel no map number");
                break;
            }
            String mapNumber = cell.getStringCellValue().trim();

            cell = row.getCell(3);
            if (cell == null || cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals("")){
                Logging.getLog(getClass()).warn("house excel no block number");
                break;
            }
            String blockNumber = cell.getStringCellValue().trim();

            cell = row.getCell(5);
            if (cell == null || cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals("")){
                Logging.getLog(getClass()).warn("house excel no build number");
                break;
            }
            String buildNumber = cell.getStringCellValue().trim();

            List<House> existsHouses = houseEntityLoader.getEntityManager().createQuery("select house from House house left join house.build build where build.mapNumber = :mapNumber and build.blockNo = :blockNumber and build.buildNo = :buildNumber", House.class)
                    .setParameter("mapNumber",mapNumber)
                    .setParameter("blockNumber", blockNumber)
                    .setParameter("buildNumber",buildNumber).getResultList();

            for(int j = 2; j <= sheet.getLastRowNum(); j++){
                row = sheet.getRow(j);
                if (row  == null)
                    break;
                int cellIndex = 0;

                cell = row.getCell(cellIndex++);
                if (cell == null || cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals("")){
                    break;
                }
                String houseOrder = cell.getStringCellValue().trim();
                boolean modify = false;
                for (House house: existsHouses){
                    if (houseOrder.equals(house.getHouseOrder().trim())){
                        modify = true;
                        cell = row.getCell(cellIndex++);
                        house.setHouseUnitName(cell.getStringCellValue());

                        cell = row.getCell(cellIndex++);
                        house.setInFloorName(cell.getStringCellValue());

                        cell = row.getCell(cellIndex++);
                        house.setHouseArea(new BigDecimal(cell.getNumericCellValue()));

                        cell = row.getCell(cellIndex++);
                        house.setUseArea(new BigDecimal(cell.getNumericCellValue()));

                        cell = row.getCell(cellIndex++);
                        house.setCommArea(new BigDecimal(cell.getNumericCellValue()));

                        cell = row.getCell(cellIndex++);
                        house.setShineArea(new BigDecimal(cell.getNumericCellValue()));

                        cell = row.getCell(cellIndex++);
                        house.setLoftArea(new BigDecimal(cell.getNumericCellValue()));

                        cell = row.getCell(cellIndex++);
                        house.setCommParam(new BigDecimal(cell.getNumericCellValue()));

                        cell = row.getCell(cellIndex++);
                        for (HouseProperty houseProperty: HouseProperty.values()){
                            if (cell.getStringCellValue().trim().equals(dictionary.getEnumLabel(houseProperty))){
                                house.setHouseType(houseProperty);
                                break;
                            }
                        }


                        cell = row.getCell(cellIndex++);
                        for (UseType useType: UseType.values()){
                            if (cell.getStringCellValue().trim().equals(dictionary.getEnumLabel(useType))){
                                house.setUseType(useType);
                                break;
                            }
                        }

                        cell = row.getCell(cellIndex++);
                        house.setDesignUseType(cell.getStringCellValue());

                        cell = row.getCell(cellIndex++);
                        house.setHaveDownRoom(cell.getStringCellValue().trim().equals("有") ? true : false );

                        cell = row.getCell(cellIndex++);
                        house.setAddress(cell.getStringCellValue());

                        cell = row.getCell(cellIndex++);
                        house.setStructure(dictionary.getWordIdByValue("house.structure",cell.getStringCellValue()));

                        cell = row.getCell(cellIndex++);
                        if (cell != null){
                            house.setKnotSize(dictionary.getWordIdByValue("house.knotSize",cell.getStringCellValue()));
                        }else{
                            house.setKnotSize(null);
                        }

                        cell = row.getCell(cellIndex++);
                        if (cell != null){
                            house.setDirection(dictionary.getWordIdByValue("house.direction",cell.getStringCellValue()));
                        }else{
                            house.setDirection(null);
                        }

                        cell = row.getCell(cellIndex++);
                        if (cell != null){
                            house.setEastWall(dictionary.getWordIdByValue("house.wall",cell.getStringCellValue()));
                        }else{
                            house.setEastWall(null);
                        }

                        cell = row.getCell(cellIndex++);
                        if (cell != null){
                            house.setWestWall(dictionary.getWordIdByValue("house.wall",cell.getStringCellValue()));
                        }else{
                            house.setWestWall(null);
                        }

                        cell = row.getCell(cellIndex++);
                        if (cell != null){
                            house.setSouthWall(dictionary.getWordIdByValue("house.wall",cell.getStringCellValue()));
                        }else{
                            house.setSouthWall(null);
                        }

                        cell = row.getCell(cellIndex++);
                        if (cell != null){
                            house.setNorthWall(dictionary.getWordIdByValue("house.wall",cell.getStringCellValue()));
                        }else{
                            house.setNorthWall(null);
                        }

                        cell = row.getCell(cellIndex++);
                        if (cell != null){
                            house.setMemo(cell.getStringCellValue());
                        }else {
                            house.setMemo(null);
                        }
                        break;
                    }
                }

                if (!modify){

                    //TODO add new HOUSE;  try house order is exists
                }




            }

        }



        houseEntityLoader.getEntityManager().flush();
        Logging.getLog(getClass()).debug("house excel saved");



    }

}
