package com.dgsoft.house.owner.total;

import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.SystemEntityLoader;
import com.dgsoft.common.system.model.Word;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.model.Project;
import com.dgsoft.house.model.Section;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessEmp;
import com.dgsoft.house.owner.model.ContractOwner;
import com.dgsoft.house.owner.model.OwnerBusiness;
import com.dgsoft.house.owner.total.data.BusinessTotalData;
import com.dgsoft.house.owner.total.data.TotalContractData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;
import sun.swing.SwingUtilities2;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wxy on 2015-11-16.
 * 商品房备案业务明细，签约和正常备案
 */
@Name("totalContract")
public class TotalContract {
    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In(create = true)
    private SystemEntityLoader systemEntityLoader;

    @In(create = true)
    private FacesContext facesContext;

    @In(create = true)
    private FacesMessages facesMessages;

    private Date fromDateTime;


    private Date toDateTime;


    public Date getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(Date toDateTime) {
        this.toDateTime = toDateTime;
    }

    public Date getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(Date fromDateTime) {
        this.fromDateTime = fromDateTime;
    }




    public void totalContractInfo() {

      //  List<Section> sectionList  = houseEntityLoader.getEntityManager().createQuery("select section from Section section order by section.id",Section.class).getResultList();

        //住宅 developerName,sectionName,count,sumPrice,houseArea
        List<TotalContractData> zcTotalContractDataList = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.TotalContractData(AfterBusinessHouse.developerName,AfterBusinessHouse.sectionName,count(ob.id),sum(SaleInfos.sumPrice),sum(AfterBusinessHouse.houseArea)) " +
                "from OwnerBusiness ob left join ob.houseBusinesses HouseBusinesses left join HouseBusinesses.afterBusinessHouse AfterBusinessHouse left join AfterBusinessHouse.saleInfos SaleInfos" +
                " where ob.defineId=:defineId and ob.status in ('COMPLETE','MODIFYING') and ob.source in ('BIZ_CREATE','BIZ_IMPORT','BIZ_OUTSIDE') and AfterBusinessHouse.useType in (:usetype)  and ob.regTime >= :beginDate and ob.regTime <= :endDate group by AfterBusinessHouse.sectionName,AfterBusinessHouse.developerName", TotalContractData.class)
                .setParameter("beginDate", fromDateTime)
                .setParameter("endDate", toDateTime)
                .setParameter("defineId", "WP42")
                .setParameter("usetype", "80").getResultList();

        List<TotalContractData> otherTotalContractDataList = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.TotalContractData(AfterBusinessHouse.developerName,AfterBusinessHouse.sectionName,count(ob.id),sum(SaleInfos.sumPrice),sum(AfterBusinessHouse.houseArea)) " +
                "from OwnerBusiness ob left join ob.houseBusinesses HouseBusinesses left join HouseBusinesses.afterBusinessHouse AfterBusinessHouse left join AfterBusinessHouse.saleInfos SaleInfos" +
                " where ob.defineId=:defineId and ob.status in ('COMPLETE','MODIFYING') and ob.source in ('BIZ_CREATE','BIZ_IMPORT','BIZ_OUTSIDE') and AfterBusinessHouse.useType <> (:usetype)  and ob.regTime >= :beginDate and ob.regTime <= :endDate group by AfterBusinessHouse.sectionName,AfterBusinessHouse.developerName", TotalContractData.class)
                .setParameter("beginDate", fromDateTime)
                .setParameter("endDate", toDateTime)
                .setParameter("defineId", "WP42")
                .setParameter("usetype", "80").getResultList();


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle headCellStyle = workbook.createCellStyle();
        XSSFCellStyle cellStyle = workbook.createCellStyle();


        headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
        headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
        XSSFFont font = workbook.createFont();// 创建字体对象
        font.setFontHeightInPoints((short) 12);// 设置字体大小
        headCellStyle.setFont(font);

        int rowIndex = 0;//行
        int cellIndex = 0;//列
        int fzrowIndex=0;
        int fzcellIndex=0;
        XSSFSheet sheet = workbook.createSheet("住宅备案信息统计");
        XSSFSheet sheet1 = workbook.createSheet("非住宅备案信息统计");
        List<String> strings = new ArrayList();
        strings.add("套数");
        strings.add("面积");
        strings.add("购房款");
        Row row1 = sheet.createRow(rowIndex++);
        Row row2 = sheet.createRow(rowIndex++);
        Row fzrow = sheet1.createRow(fzrowIndex++);


        Cell cell1 = row1.createCell(cellIndex++);
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        cell1.setCellValue("开发商名称");
        cell1.setCellStyle(headCellStyle);

        Cell fzcell = fzrow.createCell(fzcellIndex++);
        sheet1.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        fzcell.setCellValue("开发商名称");
        fzcell.setCellStyle(headCellStyle);

        Cell cell2 = row1.createCell(cellIndex++);
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
        cell2.setCellValue("小区名称");
        cell2.setCellStyle(headCellStyle);

        Cell fzcell2 = fzrow.createCell(fzcellIndex++);
        sheet1.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
        fzcell2.setCellValue("小区名称");
        fzcell2.setCellStyle(headCellStyle);


        Cell cell3 = row1.createCell(cellIndex++);
        sheet.addMergedRegion(new CellRangeAddress(0,0,2,4));
        cell3.setCellValue("住宅");
        cell3.setCellStyle(headCellStyle);
        for(int i=0;i<strings.size();i++){
            Cell cell = row2.createCell(i+2);
            cell.setCellValue(strings.get(i));
            cell.setCellStyle(headCellStyle);
        }

        Cell fzcell1 = fzrow.createCell(fzcellIndex++);
        sheet1.addMergedRegion(new CellRangeAddress(0,0,2,4));
        fzcell1.setCellValue("非住宅");
        fzcell1.setCellStyle(headCellStyle);
        Row fzrow2= sheet1.createRow(fzrowIndex++);
        for(int i=0;i<strings.size();i++){
            Cell fzcel1 = fzrow2.createCell(i+2);
            fzcel1.setCellValue(strings.get(i));
            fzcel1.setCellStyle(headCellStyle);
        }

        int hjrow=0;
        if (zcTotalContractDataList!=null && zcTotalContractDataList.size()>0){
            for(TotalContractData totalContractData:zcTotalContractDataList) {

                cellIndex = 0;

                row1 = sheet.createRow(rowIndex++);

                Cell cell8 = row1.createCell(cellIndex++);
                cell8.setCellValue(totalContractData.getDeveloperName());
                cell8.setCellStyle(headCellStyle);

                cell8 = row1.createCell(cellIndex++);
                cell8.setCellValue(totalContractData.getSectionName());
                cell8.setCellStyle(headCellStyle);

                cell8 = row1.createCell(cellIndex++);
                cell8.setCellValue(totalContractData.getCount());
                cell8.setCellStyle(headCellStyle);

                cell8 = row1.createCell(cellIndex++);
                cell8.setCellValue(totalContractData.getHouseArea().doubleValue());
                cell8.setCellStyle(headCellStyle);


                cell8 = row1.createCell(cellIndex++);
                if (totalContractData.getSumPrice()!=null) {
                    cell8.setCellValue(totalContractData.getSumPrice().doubleValue());
                }else{
                    cell8.setCellValue(0.0);
                }
                cell8.setCellStyle(headCellStyle);

                hjrow = rowIndex;
             }




        }
        cellIndex=0;
        row1 = sheet.createRow(hjrow);
        Cell zzhjcell = row1.createCell(cellIndex++);
        zzhjcell.setCellValue("合计");
        zzhjcell.setCellStyle(headCellStyle);

       Cell zzhjtscell = row1.createCell(2,Cell.CELL_TYPE_FORMULA);
       zzhjtscell.setCellFormula("SUM(" + CellReference.convertNumToColString(1+cellIndex) + "3:" + CellReference.convertNumToColString(1+cellIndex)+hjrow+")");
       zzhjtscell.setCellStyle(headCellStyle);

       Cell zzhmjscell = row1.createCell(3,Cell.CELL_TYPE_FORMULA);
       zzhmjscell.setCellFormula("SUM(" + CellReference.convertNumToColString(2+cellIndex) + "3:" + CellReference.convertNumToColString(2+cellIndex)+hjrow+")");
       zzhmjscell.setCellStyle(headCellStyle);

        Cell zzhjescell = row1.createCell(4,Cell.CELL_TYPE_FORMULA);
        zzhjescell.setCellFormula("SUM(" + CellReference.convertNumToColString(3+cellIndex) + "3:" + CellReference.convertNumToColString(3+cellIndex)+hjrow+")");
        zzhjescell.setCellStyle(headCellStyle);



        int fzhjrow=0;
        if (otherTotalContractDataList!=null && otherTotalContractDataList.size()>0){
            for(TotalContractData totalContractData:otherTotalContractDataList) {

                fzcellIndex = 0;

                row1 = sheet1.createRow(fzrowIndex++);

                Cell cell8 = row1.createCell(fzcellIndex++);
                cell8.setCellValue(totalContractData.getDeveloperName());
                cell8.setCellStyle(headCellStyle);

                cell8 = row1.createCell(fzcellIndex++);
                cell8.setCellValue(totalContractData.getSectionName());
                cell8.setCellStyle(headCellStyle);

                cell8 = row1.createCell(fzcellIndex++);
                cell8.setCellValue(totalContractData.getCount());
                cell8.setCellStyle(headCellStyle);

                cell8 = row1.createCell(fzcellIndex++);
                cell8.setCellValue(totalContractData.getHouseArea().doubleValue());
                cell8.setCellStyle(headCellStyle);


                cell8 = row1.createCell(fzcellIndex++);
                if (totalContractData.getSumPrice()!=null) {
                    cell8.setCellValue(totalContractData.getSumPrice().doubleValue());
                }else{
                    cell8.setCellValue(0.0);
                }
                cell8.setCellStyle(headCellStyle);

                fzhjrow = fzrowIndex;
            }
        }
        fzcellIndex=0;
        row1 = sheet1.createRow(fzhjrow);
        Cell fzhjcell = row1.createCell(fzcellIndex++);
        fzhjcell.setCellValue("合计");
        fzhjcell.setCellStyle(headCellStyle);

        Cell fzhjtscell = row1.createCell(2,Cell.CELL_TYPE_FORMULA);
        fzhjtscell.setCellFormula("SUM(" + CellReference.convertNumToColString(1+fzcellIndex) + "3:" + CellReference.convertNumToColString(1+fzcellIndex)+fzhjrow+")");
        fzhjtscell.setCellStyle(headCellStyle);

        Cell fzhmjscell = row1.createCell(3,Cell.CELL_TYPE_FORMULA);
        fzhmjscell.setCellFormula("SUM(" + CellReference.convertNumToColString(2+fzcellIndex) + "3:" + CellReference.convertNumToColString(2+fzcellIndex)+fzhjrow+")");
        fzhmjscell.setCellStyle(headCellStyle);

        Cell fzhjescell = row1.createCell(4,Cell.CELL_TYPE_FORMULA);
        fzhjescell.setCellFormula("SUM(" + CellReference.convertNumToColString(3+fzcellIndex) + "3:" + CellReference.convertNumToColString(3+fzcellIndex)+fzhjrow+")");
        fzhjescell.setCellStyle(headCellStyle);


        sheet.setForceFormulaRecalculation(true);
        sheet1.setForceFormulaRecalculation(true);
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Content-Disposition", "attachment;filename=exportContract.xlsx");
        try {
            workbook.write(externalContext.getResponseOutputStream());
            facesContext.responseComplete();
        } catch (IOException e) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "ExportIOError");
            Logging.getLog(getClass()).error("export error", e);

        }

    }
}
