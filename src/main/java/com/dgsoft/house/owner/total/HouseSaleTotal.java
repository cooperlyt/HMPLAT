package com.dgsoft.house.owner.total;

import com.dgsoft.house.SaleType;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.total.data.ContractTypeTotal;
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
import javax.persistence.NoResultException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cooper on 11/03/2017.
 */
@Name("houseSaleTotal")
public class HouseSaleTotal {


    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private FacesContext facesContext;

    @In(create = true)
    private FacesMessages facesMessages;

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


    public void total(){
        HouseSaleTotalData projectDataAll;
                try {
                    projectDataAll =ownerEntityLoader.getEntityManager().createQuery("select new  com.dgsoft.house.owner.total.data.HouseSaleTotalData(sum(p.houseCount),sum(p.area)) from ProjectSellInfo p where p.businessProject.ownerBusiness.status in ('RUNNING','COMPLETE','SUSPEND') and p.businessProject.ownerBusiness.recorded = true and p.businessProject.ownerBusiness.regTime >= :beginTime and p.businessProject.ownerBusiness.regTime <= :endTime", HouseSaleTotalData.class)
                            .setParameter("beginTime", fromDateTime)
                            .setParameter("endTime", toDateTime).getSingleResult();
                }catch (NoResultException e){
                    projectDataAll = null;
                }

        HouseSaleTotalData projectDataD ;
                try {
                    projectDataD =  ownerEntityLoader.getEntityManager().createQuery("select new  com.dgsoft.house.owner.total.data.HouseSaleTotalData(sum(st.count), sum(st.area)) from SellTypeTotal st where st.useType = 'DWELLING_KEY' and st.businessBuild.businessProject.ownerBusiness.status in ('RUNNING','COMPLETE','SUSPEND') and st.businessBuild.businessProject.ownerBusiness.recorded = true and st.businessBuild.businessProject.ownerBusiness.regTime >= :beginTime and st.businessBuild.businessProject.ownerBusiness.regTime < :endTime", HouseSaleTotalData.class)
                            .setParameter("beginTime", fromDateTime)
                            .setParameter("endTime", toDateTime).getSingleResult();

                }catch (NoResultException e){
                    projectDataD = null;
                }

        HouseSaleTotalData preSellAll = null;
        HouseSaleTotalData sellAll = null;


        for (ContractTypeTotal totalData: ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.ContractTypeTotal(hc.type,count(hb),sum(h.houseArea),sum(h.saleInfo.sumPrice)) from HouseBusiness hb left join hb.afterBusinessHouse h left join h.houseContracts hc where hb.ownerBusiness.status in ('RUNNING','COMPLETE','SUSPEND') and hb.ownerBusiness.recorded = true and hb.ownerBusiness.defineId = 'WP42' and hb.ownerBusiness.regTime >= :beginTime and hb.ownerBusiness.regTime <= :endTime group by hc.type", ContractTypeTotal.class)
                .setParameter("beginTime",fromDateTime)
                .setParameter("endTime",toDateTime).getResultList()){
            if (SaleType.NOW_SELL.equals(totalData.getSaleType())){
                sellAll = totalData;
            }else if (SaleType.MAP_SELL.equals(totalData.getSaleType())){
                preSellAll= totalData;
            }
        }

        if (preSellAll == null){
            preSellAll = new HouseSaleTotalData(new Long(0),BigDecimal.ZERO);
        }
        if (sellAll == null){
            sellAll = new HouseSaleTotalData(new Long(0),BigDecimal.ZERO);
        }


        HouseSaleTotalData preSellD = null;
        HouseSaleTotalData sellD = null;

        for (ContractTypeTotal totalData:ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.ContractTypeTotal(hc.type,count(hb),sum(h.houseArea),sum(h.saleInfo.sumPrice)) from HouseBusiness hb left join hb.afterBusinessHouse h left join h.houseContracts hc where h.useType = 'DWELLING_KEY' and hb.ownerBusiness.status in ('RUNNING','COMPLETE','SUSPEND') and hb.ownerBusiness.recorded = true and hb.ownerBusiness.defineId = 'WP42' and hb.ownerBusiness.regTime >= :beginTime and hb.ownerBusiness.regTime <= :endTime group by hc.type", ContractTypeTotal.class)
                .setParameter("beginTime",fromDateTime)
                .setParameter("endTime",toDateTime).getResultList()){
            if (SaleType.NOW_SELL.equals(totalData.getSaleType())){
                sellD = totalData;
            }else if (SaleType.MAP_SELL.equals(totalData.getSaleType())){
                preSellD = totalData;
            }
        }

        if (preSellD == null){
            preSellD = new HouseSaleTotalData(new Long(0),BigDecimal.ZERO);
        }
        if (sellD == null){
            sellD = new HouseSaleTotalData(new Long(0),BigDecimal.ZERO);
        }

        HouseSaleTotalData oldSaleAll;
        HouseSaleTotalData oldSaleD;

        try {
            oldSaleAll = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.HouseSaleTotalData(count(hb),sum(h.houseArea),sum(h.saleInfo.sumPrice)) from HouseBusiness hb left join hb.afterBusinessHouse h where hb.ownerBusiness.status in ('RUNNING','COMPLETE','SUSPEND') and hb.ownerBusiness.recorded = true and hb.ownerBusiness.defineId = 'WP41' and hb.ownerBusiness.regTime >= :beginTime and hb.ownerBusiness.regTime <= :endTime ", HouseSaleTotalData.class)
                    .setParameter("beginTime", fromDateTime)
                    .setParameter("endTime", toDateTime).getSingleResult();
        }catch (NoResultException e){
            oldSaleAll = null;
        }

        try {
            oldSaleD = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.HouseSaleTotalData(count(hb),sum(h.houseArea),sum(h.saleInfo.sumPrice)) from HouseBusiness hb left join hb.afterBusinessHouse h where  h.useType = 'DWELLING_KEY' and hb.ownerBusiness.status in ('RUNNING','COMPLETE','SUSPEND') and hb.ownerBusiness.recorded = true and hb.ownerBusiness.defineId = 'WP41' and hb.ownerBusiness.regTime >= :beginTime and hb.ownerBusiness.regTime <= :endTime ", HouseSaleTotalData.class)
                    .setParameter("beginTime", fromDateTime)
                    .setParameter("endTime", toDateTime).getSingleResult();
        }catch (NoResultException e){
            oldSaleD = null;
        }


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle headCellStyle  = workbook.createCellStyle();

        headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        XSSFSheet
                sheet = workbook.createSheet("交易情况表");
        int rowIndex = 0;


        Row row1 = sheet.createRow(rowIndex++);
        Row row = sheet.createRow(rowIndex++);

        Cell cell = row1.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("统计项目");

        cell = row1.createCell(1);
        sheet.addMergedRegion(new CellRangeAddress(0,1,1,1));
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("计量单位");

        cell = row1.createCell(2);
        sheet.addMergedRegion(new CellRangeAddress(0,0,2,4));
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("合计");


        cell = row1.createCell(5);
        sheet.addMergedRegion(new CellRangeAddress(0,0,5,7));
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("住宅");

        int cellIndex = 2;
        for (int i = 0 ; i < 2 ; i++) {
            cell = row.createCell(cellIndex++);
            cell.setCellStyle(headCellStyle);
            cell.setCellValue("本年");

            cell = row.createCell(cellIndex++);
            cell.setCellStyle(headCellStyle);
            cell.setCellValue("上年");

            cell = row.createCell(cellIndex++);
            cell.setCellStyle(headCellStyle);
            cell.setCellValue("同比%");
        }


        row = sheet.createRow(rowIndex++);

        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("新建设商品房批准预售面积");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("万平方米");

        if (projectDataAll != null) {
            cell = row.createCell(2);
            cell.setCellValue(projectDataAll.getArea().divide(new BigDecimal("10000"), 2).doubleValue());
        }

        if (projectDataD != null) {
            cell = row.createCell(5);
            cell.setCellValue(projectDataD.getArea().divide(new BigDecimal("10000"), 2).doubleValue());
        }

        row = sheet.createRow(rowIndex++);

        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("新建设商品房批准预售面积");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("套");

        if (projectDataAll != null) {
            cell = row.createCell(2);
            cell.setCellValue(projectDataAll.getCount());
        }

        if (projectDataD != null) {
            cell = row.createCell(5);
            cell.setCellValue(projectDataD.getCount());
        }

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("商品房销售面积");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("万平方米");

        cell = row.createCell(2);
        cell.setCellValue(preSellAll.getArea().add(sellAll.getArea()).divide(new BigDecimal("10000"),2).doubleValue());

        cell = row.createCell(5);
        cell.setCellValue(preSellD.getArea().add(sellD.getArea()).divide(new BigDecimal("10000"),2).doubleValue());

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("其中：现房销售面积");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("万平方米");

        cell = row.createCell(2);
        cell.setCellValue(sellAll.getArea().divide(new BigDecimal("10000"),2).doubleValue());

        cell = row.createCell(5);
        cell.setCellValue(sellD.getArea().divide(new BigDecimal("10000"),2).doubleValue());


        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("期房销售面积");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("万平方米");

        cell = row.createCell(2);
        cell.setCellValue(preSellAll.getArea().divide(new BigDecimal("10000"),2).doubleValue());

        cell = row.createCell(5);
        cell.setCellValue(preSellD.getArea().divide(new BigDecimal("10000"),2).doubleValue());


        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("商品房销售额");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("亿元");

        cell = row.createCell(2);
        cell.setCellValue(preSellAll.getMoney().add(sellAll.getMoney()).divide(new BigDecimal("100000000"),2).doubleValue());

        cell = row.createCell(5);
        cell.setCellValue(preSellD.getMoney().add(sellD.getMoney()).divide(new BigDecimal("100000000"),2).doubleValue());

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("其中：现房销售额");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("亿元");

        cell = row.createCell(2);
        cell.setCellValue(sellAll.getMoney().divide(new BigDecimal("100000000"),2).doubleValue());

        cell = row.createCell(5);
        cell.setCellValue(sellD.getMoney().divide(new BigDecimal("100000000"),2).doubleValue());


        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("期房销售额");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("亿元");

        cell = row.createCell(2);
        cell.setCellValue(preSellAll.getMoney().divide(new BigDecimal("100000000"),2).doubleValue());

        cell = row.createCell(5);
        cell.setCellValue(preSellD.getMoney().divide(new BigDecimal("100000000"),2).doubleValue());


        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("商品房销售套数");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("套");

        cell = row.createCell(2);
        cell.setCellValue(preSellAll.getCount());

        cell = row.createCell(5);
        cell.setCellValue(preSellD.getCount());

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("其中：现房销售套数");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("套");

        cell = row.createCell(2);
        cell.setCellValue(sellAll.getCount());

        cell = row.createCell(5);
        cell.setCellValue(sellD.getCount());


        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("期房销售套数");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("套");

        cell = row.createCell(2);
        cell.setCellValue(preSellAll.getCount());

        cell = row.createCell(5);
        cell.setCellValue(preSellD.getCount());


        //
        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("商品房销售平均价格");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("元/平方米");

        if (preSellAll.getArea().add(sellAll.getArea()).doubleValue() > 0 ) {
            cell = row.createCell(2);
            cell.setCellValue(preSellAll.getMoney().add(sellAll.getMoney()).divide(preSellAll.getArea().add(sellAll.getArea()), 2).doubleValue());
        }

        if (preSellD.getArea().add(sellD.getArea()).doubleValue() > 0) {
            cell = row.createCell(5);
            cell.setCellValue(preSellD.getMoney().add(sellD.getMoney()).divide(preSellD.getArea().add(sellD.getArea()), 2).doubleValue());
        }

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("其中：现房销售平均价格");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("元/平方米");

        if (sellAll.getArea().doubleValue() > 0) {
            cell = row.createCell(2);
            cell.setCellValue(sellAll.getMoney().divide(sellAll.getArea(), 2).doubleValue());
        }

        if (sellD.getArea().doubleValue() > 0) {
            cell = row.createCell(5);
            cell.setCellValue(sellD.getMoney().divide(sellD.getArea(), 2).doubleValue());
        }


        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("期房销售平均价格");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("元/平方米");

        if (preSellAll.getArea().doubleValue() > 0) {
            cell = row.createCell(2);
            cell.setCellValue(preSellAll.getMoney().divide(preSellAll.getArea(), 2).doubleValue());
        }

        if (preSellD.getArea().doubleValue() > 0) {
            cell = row.createCell(5);
            cell.setCellValue(preSellD.getMoney().divide(preSellD.getArea(), 2).doubleValue());
        }


        //oldSaleAll
        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("存量房成交面积");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("万平方米");

        cell = row.createCell(2);
        cell.setCellValue(oldSaleAll.getArea().divide(new BigDecimal("10000"),2).doubleValue());

        cell = row.createCell(5);
        cell.setCellValue(oldSaleD.getArea().divide(new BigDecimal("10000"),2).doubleValue());

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("存量房成交额");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("亿元");

        cell = row.createCell(2);
        cell.setCellValue(oldSaleAll.getMoney().divide(new BigDecimal("100000000"),2).doubleValue());

        cell = row.createCell(5);
        cell.setCellValue(oldSaleD.getMoney().divide(new BigDecimal("100000000"),2).doubleValue());

        //
        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("存量房成交套数");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("套");

        cell = row.createCell(2);
        cell.setCellValue(oldSaleAll.getCount());

        cell = row.createCell(5);
        cell.setCellValue(oldSaleD.getCount());


        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("存量房销售平均价格");

        cell = row.createCell(1);
        cell.setCellStyle(headCellStyle);
        cell.setCellValue("元/平方米");

        if (oldSaleAll.getArea().doubleValue() > 0) {
            cell = row.createCell(2);
            cell.setCellValue(oldSaleAll.getMoney().divide(oldSaleAll.getArea(), 2).doubleValue());
        }

        if (oldSaleD.getArea().doubleValue() > 0) {
            cell = row.createCell(5);
            cell.setCellValue(oldSaleD.getMoney().divide(oldSaleD.getArea(), 2).doubleValue());
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
