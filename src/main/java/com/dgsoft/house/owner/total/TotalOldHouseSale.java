package com.dgsoft.house.owner.total;

import com.dgsoft.common.SearchDateArea;
import com.dgsoft.house.UseTypeWordAdapter;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.total.data.HouseSaleTotalData;
import com.ibm.icu.math.BigDecimal;
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
import java.util.Calendar;
import java.util.Date;

/**
 * Created by cooper on 8/12/16.
 */
@Name("totalOldHouseSale")
public class TotalOldHouseSale {


    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private FacesContext facesContext;

    @In(create = true)
    private FacesMessages facesMessages;

    private static final String SALE_SQL = "select new com.dgsoft.house.owner.total.data.HouseSaleTotalData(count(hb.id),sum(bh.houseArea) ) from HouseBusiness hb left join hb.afterBusinessHouse bh where hb.ownerBusiness.regTime >= :beginTime and hb.ownerBusiness.regTime <= :endTime and bh.useType in (:useTypes) and hb.ownerBusiness.defineId ='WP56' and hb.ownerBusiness.recorded = true ";

    private Date fromDateTime;

    private Date toDateTime;


    public Date getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(Date fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public Date getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(Date toDateTime) {
        this.toDateTime = toDateTime;
    }

    public void totalSale(){

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle headCellStyle  = workbook.createCellStyle();

        headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        XSSFSheet
                sheet = workbook.createSheet("存量房情况统计");

        int rowIndex = 0;
        int colIndex = 0;
        Row row = sheet.createRow(rowIndex++);
        Cell cell = row.createCell(colIndex);
        sheet.addMergedRegion(new CellRangeAddress(0,0,colIndex,colIndex + 3));
        colIndex = colIndex + 4;
        cell.setCellValue("60平方米以下");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(colIndex);
        sheet.addMergedRegion(new CellRangeAddress(0,0,colIndex,colIndex + 3));
        colIndex = colIndex + 4;
        cell.setCellValue("60-90平方米");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(colIndex);
        sheet.addMergedRegion(new CellRangeAddress(0,0,colIndex,colIndex + 3));
        colIndex = colIndex + 4;
        cell.setCellValue("90-120平方米");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(colIndex);
        sheet.addMergedRegion(new CellRangeAddress(0,0,colIndex,colIndex + 3));
        colIndex = colIndex + 4;
        cell.setCellValue("120-140平方米以下");
        cell.setCellStyle(headCellStyle);


        cell = row.createCell(colIndex);
        sheet.addMergedRegion(new CellRangeAddress(0,0,colIndex,colIndex + 3));
        colIndex = colIndex + 4;
        cell.setCellValue("140平方米以上");
        cell.setCellStyle(headCellStyle);


        row = sheet.createRow(rowIndex++);
        colIndex = 0;
        boolean flag = true;
        for(int i = 0 ; i < 10; i++){
            cell = row.createCell(colIndex);
            sheet.addMergedRegion(new CellRangeAddress(1,1,colIndex,colIndex + 1));
            colIndex = colIndex + 2;
            if (flag){
                cell.setCellValue("住宅");
            }else{
                cell.setCellValue("非住宅");
            }

            flag = !flag;
            cell.setCellStyle(headCellStyle);
        }

        row = sheet.createRow(rowIndex++);
        colIndex = 0;
        flag = true;
        for(int i = 0 ; i < 20; i++){
            cell = row.createCell(colIndex++);
            if (flag){
                cell.setCellValue("套数");
            }else{
                cell.setCellValue("面积");
            }

            flag = !flag;
            cell.setCellStyle(headCellStyle);
        }

        //data
        row = sheet.createRow(rowIndex++);
        colIndex = 0;
        HouseSaleTotalData totalData =  ownerEntityLoader.getEntityManager().createQuery(SALE_SQL + " and bh.houseArea > 0 ", HouseSaleTotalData.class).setParameter("beginTime",fromDateTime).setParameter("endTime",toDateTime).setParameter("useTypes", UseTypeWordAdapter.instance().getDwellingTypes()).getSingleResult();
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getCount());
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getArea().doubleValue());

        totalData =  ownerEntityLoader.getEntityManager().createQuery(SALE_SQL + " and bh.houseArea > 0 ", HouseSaleTotalData.class).setParameter("beginTime",fromDateTime).setParameter("endTime",toDateTime).setParameter("useTypes", UseTypeWordAdapter.instance().getUnDwellingTypes()).getSingleResult();
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getCount());
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getArea().doubleValue());


        totalData =  ownerEntityLoader.getEntityManager().createQuery(SALE_SQL + " and bh.houseArea >=60   and bh.houseArea <90 ", HouseSaleTotalData.class).setParameter("beginTime",fromDateTime).setParameter("endTime",toDateTime).setParameter("useTypes", UseTypeWordAdapter.instance().getDwellingTypes()).getSingleResult();
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getCount());
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getArea().doubleValue());

        totalData =  ownerEntityLoader.getEntityManager().createQuery(SALE_SQL + " and bh.houseArea >=60   and bh.houseArea <90 ", HouseSaleTotalData.class).setParameter("beginTime",fromDateTime).setParameter("endTime",toDateTime).setParameter("useTypes", UseTypeWordAdapter.instance().getUnDwellingTypes()).getSingleResult();
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getCount());
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getArea().doubleValue());


        totalData =  ownerEntityLoader.getEntityManager().createQuery(SALE_SQL + " and bh.houseArea >=90   and bh.houseArea <120 ", HouseSaleTotalData.class).setParameter("beginTime",fromDateTime).setParameter("endTime",toDateTime).setParameter("useTypes", UseTypeWordAdapter.instance().getDwellingTypes()).getSingleResult();
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getCount());
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getArea().doubleValue());

        totalData =  ownerEntityLoader.getEntityManager().createQuery(SALE_SQL + " and bh.houseArea >=90   and bh.houseArea <120 ", HouseSaleTotalData.class).setParameter("beginTime",fromDateTime).setParameter("endTime",toDateTime).setParameter("useTypes", UseTypeWordAdapter.instance().getUnDwellingTypes()).getSingleResult();
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getCount());
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getArea().doubleValue());

        totalData =  ownerEntityLoader.getEntityManager().createQuery(SALE_SQL + " and bh.houseArea >=120   and bh.houseArea <140 ", HouseSaleTotalData.class).setParameter("beginTime",fromDateTime).setParameter("endTime",toDateTime).setParameter("useTypes", UseTypeWordAdapter.instance().getDwellingTypes()).getSingleResult();
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getCount());
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getArea().doubleValue());

        totalData =  ownerEntityLoader.getEntityManager().createQuery(SALE_SQL + " and bh.houseArea >=120   and bh.houseArea <140 ", HouseSaleTotalData.class).setParameter("beginTime",fromDateTime).setParameter("endTime",toDateTime).setParameter("useTypes", UseTypeWordAdapter.instance().getUnDwellingTypes()).getSingleResult();
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getCount());
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getArea().doubleValue());

        totalData =  ownerEntityLoader.getEntityManager().createQuery(SALE_SQL + " and bh.houseArea >=140  ", HouseSaleTotalData.class).setParameter("beginTime",fromDateTime).setParameter("endTime",toDateTime).setParameter("useTypes", UseTypeWordAdapter.instance().getDwellingTypes()).getSingleResult();
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getCount());
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getArea().doubleValue());

        totalData =  ownerEntityLoader.getEntityManager().createQuery(SALE_SQL  + " and bh.houseArea >=140  ", HouseSaleTotalData.class).setParameter("beginTime",fromDateTime).setParameter("endTime",toDateTime).setParameter("useTypes", UseTypeWordAdapter.instance().getUnDwellingTypes()).getSingleResult();
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getCount());
        cell = row.createCell(colIndex++,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(totalData.getArea().doubleValue());



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
