package com.dgsoft.house.owner.total;

import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.common.SearchDateArea;
import com.dgsoft.common.system.SystemEntityLoader;
import com.dgsoft.common.system.business.BusinessDefineCache;
import com.dgsoft.common.system.model.Fee;
import com.dgsoft.common.system.model.FeeCategory;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.total.data.FeeTotalData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;

/**
 * Created by cooper on 10/29/15.
 */
@Name("totalDataExport")
public class TotalDataExport {


    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private SystemEntityLoader systemEntityLoader;

    @In(create = true)
    private FacesContext facesContext;

    @In(create = true)
    private FacesMessages facesMessages;

    @In(create = true)
    private BusinessDefineCache businessDefineCache;

    private SearchDateArea searchDateArea = new SearchDateArea();

    public SearchDateArea getSearchDateArea() {
        return searchDateArea;
    }


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

    public void totalFeeData(){

        Map<String,FeeCategory> feeCategoryMap = new HashMap<String, FeeCategory>();
        Map<String,Fee> feeMap = new HashMap<String, Fee>();

        for (Fee fee: systemEntityLoader.getEntityManager().createQuery("select fee from Fee fee left join fetch fee.category",Fee.class).getResultList()){
            feeMap.put(fee.getId(),fee);
            feeCategoryMap.put(fee.getCategory().getId(),fee.getCategory());
        }

        List<FeeCategory> feeCategories = new ArrayList<FeeCategory>(feeCategoryMap.values());
        Collections.sort(feeCategories, OrderBeanComparator.getInstance());



        List<FeeTotalData> datas = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.FeeTotalData(bm.ownerBusiness.defineId,sum(bm.factMoney),count(bm.id),bm.moneyTypeId) from BusinessMoney bm where bm.factMoneyInfo.factTime >= :beginDate and bm.factMoneyInfo.factTime <= :endDate group by bm.ownerBusiness.defineId , bm.moneyTypeId",FeeTotalData.class)
                .setParameter("beginDate",fromDateTime)
                .setParameter("endDate", toDateTime).getResultList();

        Map<String, Map<String,FeeTotalData>> format = new HashMap<String,  Map<String, FeeTotalData>>();


        for(FeeTotalData data: datas){
            Map<String,FeeTotalData> feeCategoryData = format.get(data.getDefineId());
            if (feeCategoryData == null){
                feeCategoryData = new HashMap<String, FeeTotalData>();
                format.put(data.getDefineId(),feeCategoryData);
            }
            String categoryId = feeMap.get(data.getItemId()).getCategory().getId();
            FeeTotalData itemTotalData = feeCategoryData.get(categoryId);
            if (itemTotalData == null){

                itemTotalData = new FeeTotalData();
                feeCategoryData.put(categoryId,itemTotalData);
            }

            itemTotalData.put(data);
        }


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle headCellStyle  = workbook.createCellStyle();

        headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        XSSFSheet
                sheet = workbook.createSheet("收费数据统计");
        //sheet.addMergedRegion(new CellRangeAddress())
        int rowIndex = 0;
        int cellIndex = 0;
        Row row1 = sheet.createRow(rowIndex++);
        Row row2 = sheet.createRow(rowIndex++);
        Cell cell = row1.createCell(cellIndex++);


        sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
        cell.setCellValue("业务名称");
        cell.setCellStyle(headCellStyle);



        for(FeeCategory category: feeCategories){
            cell = row1.createCell(cellIndex);
            sheet.addMergedRegion(new CellRangeAddress(0,0,cellIndex,cellIndex + 1));
            cell.setCellValue(category.getName());
            cell.setCellStyle(headCellStyle);

            cell = row2.createCell(cellIndex);
            cell.setCellValue("数量");
            cell.setCellStyle(headCellStyle);

            cell = row2.createCell(cellIndex + 1);
            cell.setCellValue("金额");
            cell.setCellStyle(headCellStyle);
            cellIndex += 2;
        }

        cell = row1.createCell(cellIndex);
        sheet.addMergedRegion(new CellRangeAddress(0,0,cellIndex,cellIndex + 1));
        cell.setCellValue("合计");
        cell.setCellStyle(headCellStyle);
        cell = row2.createCell(cellIndex);
        cell.setCellValue("数量");
        cell.setCellStyle(headCellStyle);
        cell = row2.createCell(cellIndex + 1);
        cell.setCellValue("金额");
        cell.setCellStyle(headCellStyle);


        for(Map.Entry<String,Map<String,FeeTotalData>> entry: format.entrySet()){
            cellIndex = 0;
            Row row = sheet.createRow(rowIndex++);
            cell = row.createCell(cellIndex++);

            String countFormula = null;
            String moneyFormula = null;
            cell.setCellValue(businessDefineCache.getDefine(entry.getKey()).getName());
            for(FeeCategory category: feeCategories){
                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_NUMERIC);

                FeeTotalData totalData = entry.getValue().get(category.getId());
                if (totalData == null){
                    cell.setCellValue(0);
                }else {
                    cell.setCellValue(totalData.getCount());
                }
                if (countFormula == null){
                    countFormula = CellReference.convertNumToColString(cellIndex - 1) + rowIndex;
                }else{
                    countFormula += "+" + CellReference.convertNumToColString(cellIndex - 1) + rowIndex ;
                }

                cell = row.createCell(cellIndex++,Cell.CELL_TYPE_NUMERIC);
                if (totalData == null){
                    cell.setCellValue(0.0);
                }else{
                    cell.setCellValue(totalData.getFactMoney().doubleValue());
                }

                if (moneyFormula == null){

                    moneyFormula = CellReference.convertNumToColString(cellIndex - 1) + rowIndex ;
                }else{
                    moneyFormula += "+" + CellReference.convertNumToColString(cellIndex - 1) + rowIndex;
                }

            }

            cell = row.createCell(cellIndex++,Cell.CELL_TYPE_FORMULA);
            cell.setCellFormula(countFormula);
            cell = row.createCell(cellIndex++,Cell.CELL_TYPE_FORMULA);
            cell.setCellFormula(moneyFormula);
        }

        cellIndex = 0;

        if (rowIndex > 2) {
            Row row = sheet.createRow(rowIndex);
            cell = row.createCell(cellIndex++);
            cell.setCellValue("合计");
            for (int i = 0 ; i < (feeCategories.size() + 2) ; i++) {
                cell = row.createCell(cellIndex++, Cell.CELL_TYPE_FORMULA);
                cell.setCellFormula("SUM(" + CellReference.convertNumToColString(cellIndex - 1) + "3:" + CellReference.convertNumToColString(cellIndex - 1) + rowIndex + ")");
                //cell.setCellValue();
                cell = row.createCell(cellIndex++, Cell.CELL_TYPE_FORMULA);
                cell.setCellFormula("SUM(" + CellReference.convertNumToColString(cellIndex - 1) + "3:" + CellReference.convertNumToColString(cellIndex - 1) + rowIndex  + ")");

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

//        Iterator<Row>
//                rowIterator = sheet.iterator();
//
//        Iterator<Cell>
//                cellIterator = row.cellIterator();



    }




}
