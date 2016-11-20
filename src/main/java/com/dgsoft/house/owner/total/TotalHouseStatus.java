package com.dgsoft.house.owner.total;

import com.dgsoft.common.system.DictionaryConverter;
import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.HouseRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cooper on 4/4/16.
 */
@Name("totalHouseStatus")
public class TotalHouseStatus {


    private String buildCode;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private DictionaryWord dictionary;

    @In(create = true)
    private FacesContext facesContext;

    @In(create = true)
    private FacesMessages facesMessages;

    @In(create = true)
    private Map<String,String> messages;

    public String getBuildCode() {
        return buildCode;
    }

    public void setBuildCode(String buildCode) {
        this.buildCode = buildCode;
    }

    public void totalHouseStatus(){

        List<House> houses = houseEntityLoader.getEntityManager().createQuery("select house from House house where house.deleted = false and house.build.id = :buildId")
                .setParameter("buildId",buildCode).getResultList();

        List<HouseRecord> houseRecords = ownerEntityLoader.getEntityManager().createQuery("select record from HouseRecord record left join fetch record.businessHouse bh where bh.buildCode = :buildCode")
                .setParameter("buildCode",buildCode).getResultList();

        Map<String,HouseRecord> recordMap = new HashMap<String, HouseRecord>(houseRecords.size());

        for (HouseRecord record: houseRecords){
            recordMap.put(record.getHouseCode(),record);
        }

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle headCellStyle  = workbook.createCellStyle();

        headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        XSSFSheet
                sheet = workbook.createSheet("房屋状态导出-" + buildCode);
        //sheet.addMergedRegion(new CellRangeAddress())
        int rowIndex = 0;
        int cellIndex = 0;
        Row row = sheet.createRow(rowIndex++);

        Cell cell = row.createCell(cellIndex++);
        cell.setCellValue("房屋编号");
        cell.setCellStyle(headCellStyle);

         cell = row.createCell(cellIndex++);
        cell.setCellValue("产籍号");
        cell.setCellStyle(headCellStyle);


         cell = row.createCell(cellIndex++);
        cell.setCellValue("房号");
        cell.setCellStyle(headCellStyle);


         cell = row.createCell(cellIndex++);
        cell.setCellValue("房屋性质");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(cellIndex++);
        cell.setCellValue("房屋状态");
        cell.setCellStyle(headCellStyle);


        for (House house: houses){


            HouseInfo houseInfo;
            HouseRecord houseRecord = recordMap.get(house.getId());
            if (houseRecord == null){
                houseInfo = house;
            }else{
                houseInfo = houseRecord.getBusinessHouse();
            }

            row = sheet.createRow(rowIndex++);
            cellIndex = 0;

            cell = row.createCell(cellIndex++);
            cell.setCellValue(houseInfo.getHouseCode());

            cell = row.createCell(cellIndex++);
            cell.setCellValue(houseInfo.getDisplayHouseCode());


            cell = row.createCell(cellIndex++);
            cell.setCellValue(dictionary.getEnumLabel(houseInfo.getHouseType()));


            if (houseRecord != null) {
                cell = row.createCell(cellIndex++);
                cell.setCellValue(messages.get(houseRecord.getHouseStatus().name()));
            }

        }

        sheet.setForceFormulaRecalculation(true);
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Content-Disposition", "attachment;filename=export.xlsx");
        try {
            workbook.write(externalContext.getResponseOutputStream());
            facesContext.responseComplete();
        } catch (IOException e) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "ExportIOError");
            Logging.getLog(getClass()).error("export error", e);
        }


    }
}
