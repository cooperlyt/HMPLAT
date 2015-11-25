package com.dgsoft.house.owner.total;

import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.total.data.DaySaleData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by cooper on 11/25/15.
 */

@Name("totalSaleDetails")
public class TotalSaleDetails {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private FacesContext facesContext;

    @In(create = true)
    private FacesMessages facesMessages;

    private Date searchDate;

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }

    private void createSheet(XSSFWorkbook workbook, String name, String businessDefineId){

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(searchDate);




        XSSFSheet
                sheet = workbook.createSheet( gc.get(Calendar.YEAR) + "年" + (gc.get(Calendar.MONTH) + 1) + "月" + gc.get(Calendar.DAY_OF_MONTH) + "日" + name);


        XSSFCellStyle headCellStyle  = workbook.createCellStyle();

        headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        int rowIndex = 0;
        int colIndex = 0;
        Row row = sheet.createRow(rowIndex++);
        Cell cell = row.createCell(colIndex++);
        cell.setCellValue("业务编号");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(colIndex++);
        cell.setCellValue("房屋编号");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(colIndex++);
        cell.setCellValue("小区名称");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(colIndex++);
        cell.setCellValue("房屋坐落");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(colIndex++);
        cell.setCellValue("面积");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(colIndex++);
        cell.setCellValue("购房款");
        cell.setCellStyle(headCellStyle);


        List<DaySaleData> datas = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.DaySaleData(hb.ownerBusiness.id,hb.houseCode,hb.afterBusinessHouse.address,hb.afterBusinessHouse.houseArea,hb.afterBusinessHouse.saleInfo.sumPrice,hb.afterBusinessHouse.sectionName) from HouseBusiness hb " +
                "where hb.ownerBusiness.status = 'RUNNING' and year(hb.ownerBusiness.applyTime) =:searchYear " +
                "and month(hb.ownerBusiness.applyTime) = :searchMonth and day(hb.ownerBusiness.applyTime) = :searchDay " +
                "and hb.ownerBusiness.defineId = :defineId order by hb.afterBusinessHouse.sectionCode",DaySaleData.class).setParameter("defineId",businessDefineId)
                .setParameter("searchYear",gc.get(Calendar.YEAR))
                .setParameter("searchMonth",gc.get(Calendar.MONTH) + 1)
                .setParameter("searchDay",gc.get(Calendar.DAY_OF_MONTH)).getResultList();

        for (DaySaleData data: datas){
            colIndex = 0;
            row = sheet.createRow(rowIndex++);
            cell = row.createCell(colIndex++);
            cell.setCellValue(data.getBusinessId());
            cell = row.createCell(colIndex++);
            cell.setCellValue(data.getHouseCode());
            cell = row.createCell(colIndex++);
            cell.setCellValue(data.getSectionName());
            cell = row.createCell(colIndex++);
            cell.setCellValue(data.getAddress());
            cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(data.getArea().doubleValue());
            cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(data.getMoney().doubleValue());
        }

        row = sheet.createRow(rowIndex);
        cell = row.createCell(0);
        cell.setCellValue("合计");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(4, Cell.CELL_TYPE_FORMULA);
        cell.setCellFormula("SUM(" + CellReference.convertNumToColString(4) + "2:" + CellReference.convertNumToColString(4) + rowIndex + ")");
        cell = row.createCell(5, Cell.CELL_TYPE_FORMULA);
        cell.setCellFormula("SUM(" + CellReference.convertNumToColString(5) + "2:" + CellReference.convertNumToColString(5) + rowIndex + ")");

        sheet.setForceFormulaRecalculation(true);

    }

    public void exportSaleDetails(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        createSheet(workbook,"商品房销售明细", RunParam.instance().getStringParamValue("newHouseSaleBizDefineId"));
        createSheet(workbook,"存量房销售明细", RunParam.instance().getStringParamValue("oldHouseSaleBizDefineId"));

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
