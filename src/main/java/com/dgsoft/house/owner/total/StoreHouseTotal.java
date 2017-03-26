package com.dgsoft.house.owner.total;

import com.dgsoft.house.SaleType;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.ProjectSellInfo;
import com.dgsoft.house.owner.total.data.HouseSaleTotalData;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cooper on 21/03/2017.
 */
@Name("storeHouseTotal")
public class StoreHouseTotal {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private FacesContext facesContext;

    @In(create = true)
    private FacesMessages facesMessages;


    private boolean allowNegative = false;

    private Date toDateTime;

    public Date getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(Date toDateTime) {
        this.toDateTime = toDateTime;
    }

    public boolean isAllowNegative() {
        return allowNegative;
    }

    public void setAllowNegative(boolean allowNegative) {
        this.allowNegative = allowNegative;
    }

    private BigDecimal getArea(Map<String,HouseSaleTotalData>  data , String id){
        HouseSaleTotalData totalData = data.get(id);
        if (totalData != null){
            if (totalData.getArea() != null)
                return totalData.getArea();
        }
        return BigDecimal.ZERO;
    }

    private long getCount(Map<String,HouseSaleTotalData>  data , String id){
        HouseSaleTotalData totalData = data.get(id);
        if (totalData != null){
            if (totalData.getCount() != null)
                return totalData.getCount();
        }
        return 0;
    }

    private long outNumber(long number){
        if (number >= 0 || allowNegative){
            return number;
        }else{
            return 0;
        }
    }

    private double outNumber(BigDecimal number){
        if (number == null){
            return 0;
        }
        if (number.compareTo(BigDecimal.ZERO) >= 0 || allowNegative){
            return number.doubleValue();
        }else{
            return 0;
        }
    }

    public void total(){

        //总面积套数
        Map<String,ProjectSellInfo> all = new HashMap<String, ProjectSellInfo>();
        //住宅面积套数
        Map<String,HouseSaleTotalData> dw = new HashMap<String, HouseSaleTotalData>();

        //总现房面积套数
        Map<String,HouseSaleTotalData> initAll = new HashMap<String, HouseSaleTotalData>();
        //总现房住宅面积套数
        Map<String,HouseSaleTotalData> initDw = new HashMap<String, HouseSaleTotalData>();

        //总销售面积套数
        Map<String,HouseSaleTotalData> saledAll = new HashMap<String, HouseSaleTotalData>();
        //总销售住宅面积套数
        Map<String,HouseSaleTotalData> saledDw = new HashMap<String, HouseSaleTotalData>();

        //已售现房面积套数
        Map<String,HouseSaleTotalData> saledInitAll = new HashMap<String, HouseSaleTotalData>();
        //已售现房住宅面积套数
        Map<String,HouseSaleTotalData> saledInitDw = new HashMap<String, HouseSaleTotalData>();


        for(ProjectSellInfo data: ownerEntityLoader.getEntityManager().createQuery("select psi from ProjectSellInfo psi left join fetch psi.businessProject p left join p.ownerBusiness ob where ob.status in ('RUNNING','COMPLETE','SUSPEND') and ob.recorded = true and ob.defineId in ('WP50', 'WP51') and ob.regTime  <= :endTime" ,ProjectSellInfo.class)
                .setParameter("endTime", toDateTime).getResultList()){
            all.put(data.getBusinessProject().getProjectCode(),data);
        }

        for(HouseSaleTotalData data:  ownerEntityLoader.getEntityManager()
                .createQuery("select new com.dgsoft.house.owner.total.data.HouseSaleTotalData(p.projectCode,sum(stt.count),sum(stt.area)) from SellTypeTotal stt left join stt.businessBuild.businessProject p  left join p.ownerBusiness ob where stt.useType = 'DWELLING_KEY' and ob.status in ('RUNNING','COMPLETE','SUSPEND') and ob.recorded = true and ob.defineId in ('WP50', 'WP51') and ob.regTime  <= :endTime group by p.projectCode",HouseSaleTotalData.class)
                .setParameter("endTime", toDateTime).getResultList()){
            dw.put(data.getId(),data);
        }

        for(HouseSaleTotalData data: ownerEntityLoader.getEntityManager()
                .createQuery("select new com.dgsoft.house.owner.total.data.HouseSaleTotalData(h.projectCode,count(h.houseCode),sum(h.houseArea)) from HouseBusiness hb left join hb.afterBusinessHouse h left join hb.ownerBusiness ob where ob.status in ('RUNNING','COMPLETE','SUSPEND') and ob.recorded = true and ob.defineId = 'WP40' and ob.regTime  <= :endTime and h.projectCode in (select p1.projectCode from BusinessProject p1 left join p1.ownerBusiness ob1 where ob1.status in ('RUNNING','COMPLETE','SUSPEND') and ob1.recorded = true and ob1.defineId in ('WP50', 'WP51')) group by h.projectCode",HouseSaleTotalData.class)
                .setParameter("endTime", toDateTime).getResultList()){
            initAll.put(data.getId(),data);
        }

        for(HouseSaleTotalData data: ownerEntityLoader.getEntityManager()
                .createQuery("select new com.dgsoft.house.owner.total.data.HouseSaleTotalData(h.projectCode,count(h.houseCode),sum(h.houseArea)) from HouseBusiness hb left join hb.afterBusinessHouse h left join hb.ownerBusiness ob where h.useType = 'DWELLING_KEY' and ob.status in ('RUNNING','COMPLETE','SUSPEND') and ob.recorded = true and ob.defineId = 'WP40' and ob.regTime  <= :endTime and h.projectCode in (select p1.projectCode from BusinessProject p1 left join p1.ownerBusiness ob1 where ob1.status in ('RUNNING','COMPLETE','SUSPEND') and ob1.recorded = true and ob1.defineId in ('WP50', 'WP51')) group by h.projectCode",HouseSaleTotalData.class)
                .setParameter("endTime", toDateTime).getResultList()){
            initDw.put(data.getId(),data);
        }



        for(HouseSaleTotalData data: ownerEntityLoader.getEntityManager()
                .createQuery("select new com.dgsoft.house.owner.total.data.HouseSaleTotalData(h.projectCode,count(h.houseCode),sum(h.houseArea)) from HouseBusiness hb left join hb.afterBusinessHouse h left join hb.ownerBusiness ob where ob.status in ('RUNNING','COMPLETE','SUSPEND') and ob.recorded = true and ob.defineId = 'WP41' and ob.regTime  <= :endTime and h.projectCode in (select p1.projectCode from BusinessProject p1 left join p1.ownerBusiness ob1 where ob1.status in ('RUNNING','COMPLETE','SUSPEND') and ob1.recorded = true and ob1.defineId in ('WP50', 'WP51')) group by h.projectCode",HouseSaleTotalData.class)
                .setParameter("endTime", toDateTime).getResultList()){
            saledAll.put(data.getId(),data);
        }

        for(HouseSaleTotalData data: ownerEntityLoader.getEntityManager()
                .createQuery("select new com.dgsoft.house.owner.total.data.HouseSaleTotalData(h.projectCode,count(h.houseCode),sum(h.houseArea)) from HouseBusiness hb left join hb.afterBusinessHouse h left join hb.ownerBusiness ob where h.useType = 'DWELLING_KEY' and ob.status in ('RUNNING','COMPLETE','SUSPEND') and ob.recorded = true and ob.defineId = 'WP41' and ob.regTime  <= :endTime and h.projectCode in (select p1.projectCode from BusinessProject p1 left join p1.ownerBusiness ob1 where ob1.status in ('RUNNING','COMPLETE','SUSPEND') and ob1.recorded = true and ob1.defineId in ('WP50', 'WP51')) group by h.projectCode",HouseSaleTotalData.class)
                .setParameter("endTime", toDateTime).getResultList()){
            saledDw.put(data.getId(),data);
        }

        for(HouseSaleTotalData data: ownerEntityLoader.getEntityManager()
                .createQuery("select new com.dgsoft.house.owner.total.data.HouseSaleTotalData(h.projectCode,count(h.houseCode),sum(h.houseArea)) from HouseBusiness hb left join hb.afterBusinessHouse h left join hb.ownerBusiness ob where ob.status in ('RUNNING','COMPLETE','SUSPEND') and ob.recorded = true and ob.defineId = 'WP41' and ob.regTime  <= :endTime and h.projectCode in (select p1.projectCode from BusinessProject p1 left join p1.ownerBusiness ob1 where ob1.status in ('RUNNING','COMPLETE','SUSPEND') and ob1.recorded = true and ob1.defineId in ('WP50', 'WP51')) and h.houseCode in (select hb1.houseCode from HouseBusiness hb1 left join hb1.ownerBusiness ob1 where ob1.status in ('RUNNING','COMPLETE','SUSPEND') and ob1.recorded = true and ob1.defineId = 'WP40') group by h.projectCode",HouseSaleTotalData.class)
                .setParameter("endTime", toDateTime).getResultList()){
            saledInitAll.put(data.getId(),data);
        }

        for(HouseSaleTotalData data: ownerEntityLoader.getEntityManager()
                .createQuery("select new com.dgsoft.house.owner.total.data.HouseSaleTotalData(h.projectCode,count(h.houseCode),sum(h.houseArea)) from HouseBusiness hb left join hb.afterBusinessHouse h left join hb.ownerBusiness ob where h.useType = 'DWELLING_KEY' and ob.status in ('RUNNING','COMPLETE','SUSPEND') and ob.recorded = true and ob.defineId = 'WP41' and ob.regTime  <= :endTime and h.projectCode in (select p1.projectCode from BusinessProject p1 left join p1.ownerBusiness ob1 where ob1.status in ('RUNNING','COMPLETE','SUSPEND') and ob1.recorded = true and ob1.defineId in ('WP50', 'WP51')) and h.houseCode in (select hb1.houseCode from HouseBusiness hb1 left join hb1.ownerBusiness ob1 where ob1.status in ('RUNNING','COMPLETE','SUSPEND') and ob1.recorded = true and ob1.defineId = 'WP40') group by h.projectCode",HouseSaleTotalData.class)
                .setParameter("endTime", toDateTime).getResultList()){
            saledInitDw.put(data.getId(),data);
        }

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle headCellStyle  = workbook.createCellStyle();

        headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        XSSFSheet
                sheet = workbook.createSheet("库存商品房");
        int rowIndex = 0;
        Row row1 = sheet.createRow(rowIndex++);
        Row row2 = sheet.createRow(rowIndex++);
        Row row = sheet.createRow(rowIndex++);

        Cell cell = row1.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(0,2,0,0));
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("项目编号");

        cell = row1.createCell(1);
        sheet.addMergedRegion(new CellRangeAddress(0,2,1,1));
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("项目名称");

        cell = row1.createCell(2);
        sheet.addMergedRegion(new CellRangeAddress(0,2,2,2));
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("建设单位");

        //----
        cell = row1.createCell(3);
        sheet.addMergedRegion(new CellRangeAddress(0,1,3,4));
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("总建筑面积");

        cell = row1.createCell(5);
        sheet.addMergedRegion(new CellRangeAddress(0,1,5,6));
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("总套数");

        cell = row1.createCell(7);
        sheet.addMergedRegion(new CellRangeAddress(0,0,7,10));
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("已竣工待售商品房数量");

        cell = row2.createCell(7);
        sheet.addMergedRegion(new CellRangeAddress(1,1,7,8));
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("面积");

        cell = row2.createCell(9);
        sheet.addMergedRegion(new CellRangeAddress(1,1,9,10));
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("套数");

        cell = row1.createCell(11);
        sheet.addMergedRegion(new CellRangeAddress(0,0,11,14));
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("未竣工待售商品房数量");

        cell = row2.createCell(11);
        sheet.addMergedRegion(new CellRangeAddress(1,1,11,12));
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("面积");

        cell = row2.createCell(13);
        sheet.addMergedRegion(new CellRangeAddress(1,1,13,14));
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("套数");



        for(int i = 2 ; i <= 14 ; i=i+2){
            cell = row.createCell(i);
            cell.setCellStyle(headCellStyle);
            cell.setCellValue("住宅");
        }


        for(Map.Entry<String,ProjectSellInfo> entry: all.entrySet()){

            row = sheet.createRow(rowIndex++);
            int cellIndex = 0;
            cell = row.createCell(cellIndex++);
            cell.setCellValue(entry.getKey());

            cell = row.createCell(cellIndex++);
            cell.setCellValue(entry.getValue().getBusinessProject().getName());

            cell = row.createCell(cellIndex++);
            cell.setCellValue(entry.getValue().getBusinessProject().getDeveloperName());

            cell = row.createCell(cellIndex++);
            if (entry.getValue().getArea() != null)
                cell.setCellValue(entry.getValue().getArea().doubleValue());



            cell = row.createCell(cellIndex++);
            cell.setCellValue(getArea(dw,entry.getKey()).doubleValue());

            cell = row.createCell(cellIndex++);
            cell.setCellValue(entry.getValue().getHouseCount());

            cell = row.createCell(cellIndex++);
            cell.setCellValue(getCount(dw,entry.getKey()));


            BigDecimal initAllArea;
            BigDecimal initDwArea;
            long initAllCount;
            long initDwCount;
            if (SaleType.NOW_SELL.equals(entry.getValue().getType())){
                initAllArea = entry.getValue().getArea();
                initDwArea = getArea(dw,entry.getKey());
                initAllCount = entry.getValue().getHouseCount();
                initDwCount = getCount(dw,entry.getKey());
            }else{
                initAllArea = getArea(initAll,entry.getKey());
                initDwArea = getArea(initDw,entry.getKey());
                initAllCount = getCount(initAll,entry.getKey());
                initDwCount = getCount(initDw,entry.getKey());
            }

            //已竣工待售商品房数量
            cell = row.createCell(cellIndex++);
            BigDecimal a1 = initAllArea.subtract(getArea(saledInitAll,entry.getKey()));
            cell.setCellValue(outNumber(a1));

            cell = row.createCell(cellIndex++);
            BigDecimal a2 = initDwArea.subtract(getArea(saledInitDw,entry.getKey()));
            cell.setCellValue(outNumber(a2));

            cell = row.createCell(cellIndex++);
            long c1 = initAllCount - getCount(saledInitAll,entry.getKey());
            cell.setCellValue(outNumber(c1));

            cell = row.createCell(cellIndex++);
            long c2 = initDwCount - getCount(saledInitDw,entry.getKey());
            cell.setCellValue(outNumber(c2));


            //未竣工待售商品房数量
            cell = row.createCell(cellIndex++);
            if (SaleType.MAP_SELL.equals(entry.getValue().getType()))
                cell.setCellValue(outNumber(entry.getValue().getArea().subtract(getArea(saledAll,entry.getKey())).subtract(a1)));

            cell = row.createCell(cellIndex++);
            if (SaleType.MAP_SELL.equals(entry.getValue().getType()))
                cell.setCellValue(outNumber(getArea(dw,entry.getKey()).subtract(getArea(saledDw,entry.getKey())).subtract(a2)));

            cell = row.createCell(cellIndex++);
            if (SaleType.MAP_SELL.equals(entry.getValue().getType()))
                cell.setCellValue(outNumber(entry.getValue().getHouseCount() - getCount(saledAll,entry.getKey()) - c1 ));

            cell = row.createCell(cellIndex++);
            if (SaleType.MAP_SELL.equals(entry.getValue().getType()))
                cell.setCellValue(outNumber(getCount(dw,entry.getKey()) - getCount(saledDw,entry.getKey()) - c2 ));


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
