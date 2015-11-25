package com.dgsoft.house.owner.total;

import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.owner.total.data.MapHouseTotalData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
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
import java.util.List;

/**
 * Created by cooper on 11/25/15.
 */
@Name("totalMapData")
public class TotalMapData {


    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In(create = true)
    private FacesContext facesContext;

    @In(create = true)
    private FacesMessages facesMessages;


    public void totalMapHouse(){
       List<MapHouseTotalData> result = houseEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.MapHouseTotalData(section.name,section.id," +
                "(select sum(h.houseArea) from House h where h.build.project.section.id = section.id and h.useType = '80' and h.deleted = false), " +
                "(select sum(h.houseArea) from House h where h.build.project.section.id = section.id and h.useType <> '80' and h.deleted = false ) , " +
                "(select count(h.id) from House h where h.build.project.section.id = section.id and h.useType = '80' and h.deleted = false)," +
                "(select count(h.id) from House h where h.build.project.section.id = section.id and h.useType <> '80' and h.deleted = false)," +
                "(select count(b.id) from Build b where b.project.section.id = section.id)) from Section section " ,MapHouseTotalData.class).getResultList();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle headCellStyle  = workbook.createCellStyle();

        headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        XSSFSheet
                sheet = workbook.createSheet("测绘数据统计");
        //sheet.addMergedRegion(new CellRangeAddress())
        int rowIndex = 0;
        Row row1 = sheet.createRow(rowIndex++);
        Row row2 = sheet.createRow(rowIndex++);

        Cell cell = row1.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
        cell.setCellValue("小区");
        cell.setCellStyle(headCellStyle);

        cell = row1.createCell(1);
        sheet.addMergedRegion(new CellRangeAddress(0,1,1,1));
        cell.setCellValue("幢数");
        cell.setCellStyle(headCellStyle);

        cell = row1.createCell(2);
        sheet.addMergedRegion(new CellRangeAddress(0,0,2,4));
        cell.setCellValue("面积");
        cell.setCellStyle(headCellStyle);

        cell = row2.createCell(2);
        cell.setCellValue("住宅");
        cell.setCellStyle(headCellStyle);
        cell = row2.createCell(3);
        cell.setCellValue("非住宅");
        cell.setCellStyle(headCellStyle);
        cell = row2.createCell(4);
        cell.setCellValue("小计");
        cell.setCellStyle(headCellStyle);


        cell = row1.createCell(5);
        sheet.addMergedRegion(new CellRangeAddress(0,0,5,7));
        cell.setCellValue("套数");
        cell.setCellStyle(headCellStyle);

        cell = row2.createCell(5);
        cell.setCellValue("住宅");
        cell.setCellStyle(headCellStyle);
        cell = row2.createCell(6);
        cell.setCellValue("非住宅");
        cell.setCellStyle(headCellStyle);
        cell = row2.createCell(7);
        cell.setCellValue("小计");
        cell.setCellStyle(headCellStyle);


        int cellIndex;
        for(MapHouseTotalData data: result){
            cellIndex = 0;
            Row row = sheet.createRow(rowIndex++);
            cell = row.createCell(cellIndex++);
            cell.setCellValue(data.getSectionName());
            cell = row.createCell(cellIndex++,Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(data.getBuildCount());

            cell = row.createCell(cellIndex++,Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(data.getHomeArea().doubleValue());
            cell = row.createCell(cellIndex++,Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(data.getOtherArea().doubleValue());
            cell = row.createCell(cellIndex++,Cell.CELL_TYPE_FORMULA);
            cell.setCellFormula( CellReference.convertNumToColString(2) + rowIndex + "+"  + CellReference.convertNumToColString(3) + rowIndex );

            cell = row.createCell(cellIndex++,Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(data.getHomeCount());
            cell = row.createCell(cellIndex++,Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(data.getOtherCount());
            cell = row.createCell(cellIndex++,Cell.CELL_TYPE_FORMULA);
            cell.setCellFormula( CellReference.convertNumToColString(5) + rowIndex + "+"  + CellReference.convertNumToColString(6) + rowIndex );
        }


        Row row = sheet.createRow(rowIndex);
        cell = row.createCell(0);
        cell.setCellValue("合计");
        cell.setCellStyle(headCellStyle);
        for(int i = 0 ; i < 7 ;i++) {
            cell = row.createCell(i + 1, Cell.CELL_TYPE_FORMULA);
            cell.setCellFormula("SUM(" + CellReference.convertNumToColString(i + 1) + "3:" + CellReference.convertNumToColString(i + 1) + rowIndex + ")");
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
