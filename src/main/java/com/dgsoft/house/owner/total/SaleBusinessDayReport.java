package com.dgsoft.house.owner.total;

import com.dgsoft.house.owner.OwnerEntityLoader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by cooper on 26/07/2017.
 */

@Name("saleBusinessDayReport")
public class SaleBusinessDayReport extends NativeReportBase {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    private Date totalDate;

    public Date getTotalDate() {
        return totalDate;
    }

    public void setTotalDate(Date totalDate) {
        this.totalDate = totalDate;
    }

    @Override
    protected int genReportTitle(XSSFSheet sheet){
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);


        cell = row.createCell(1);
        cell.setCellValue("套数");

        cell = row.createCell(2);
        cell.setCellValue("面积");

        cell = row.createCell(3);
        cell.setCellValue("金额");


        return 1;
    }

    @Override
    protected List getData() {


        String SQL = "(select '商品房', count(bh.ID),sum(h.HOUSE_AREA), sum(si.SUM_PRICE) from BUSINESS_HOUSE bh " +
                " LEFT join HOUSE h on h.ID = bh.AFTER_HOUSE " +
                " LEFT join SALE_INFO si on si.HOUSEID = h.ID " +
                "left join OWNER_BUSINESS ob on ob.ID = bh.BUSINESS_ID " +
                "where ob.DEFINE_ID = 'WP42' and ob.RECORDED = true " +
                "and YEAR(REG_TIME) = :s_year and MONTH(REG_TIME) = :s_month and DAY(REG_TIME) = :s_day)" +
                " union all " +
                "(select '商品房住宅', count(bh.ID),sum(h.HOUSE_AREA), sum(si.SUM_PRICE) from BUSINESS_HOUSE bh " +
                " LEFT join HOUSE h on h.ID = bh.AFTER_HOUSE " +
                " LEFT join SALE_INFO si on si.HOUSEID = h.ID " +
                "left join OWNER_BUSINESS ob on ob.ID = bh.BUSINESS_ID " +
                "where ob.DEFINE_ID = 'WP42' and ob.RECORDED = true and h.USE_TYPE = 'DWELLING_KEY' " +
                "and YEAR(REG_TIME) = :s_year and MONTH(REG_TIME) = :s_month and DAY(REG_TIME) = :s_day) " +
                " union all " +
                "(select '二手房', count(bh.ID),sum(h.HOUSE_AREA), sum(si.SUM_PRICE) from BUSINESS_HOUSE bh " +
                " LEFT join HOUSE h on h.ID = bh.AFTER_HOUSE " +
                " LEFT join SALE_INFO si on si.HOUSEID = h.ID " +
                "left join OWNER_BUSINESS ob on ob.ID = bh.BUSINESS_ID " +
                "where ob.DEFINE_ID = 'WP56' and ob.RECORDED = true " +
                "and YEAR(REG_TIME) = :s_year and MONTH(REG_TIME) = :s_month and DAY(REG_TIME) = :s_day)" +
                " union all " +
                "(select '二手房住宅', count(bh.ID),sum(h.HOUSE_AREA), sum(si.SUM_PRICE) from BUSINESS_HOUSE bh " +
                " LEFT join HOUSE h on h.ID = bh.AFTER_HOUSE " +
                " LEFT join SALE_INFO si on si.HOUSEID = h.ID " +
                "left join OWNER_BUSINESS ob on ob.ID = bh.BUSINESS_ID " +
                "where ob.DEFINE_ID = 'WP56' and ob.RECORDED = true and h.USE_TYPE = 'DWELLING_KEY' " +
                "and YEAR(REG_TIME) = :s_year and MONTH(REG_TIME) = :s_month and DAY(REG_TIME) = :s_day) ";

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(totalDate);

        return ownerEntityLoader.getEntityManager().createNativeQuery(SQL)
                .setParameter("s_year",gc.get(Calendar.YEAR))
                .setParameter("s_month",gc.get(Calendar.MONTH) + 1)
                .setParameter("s_day", gc.get(Calendar.DAY_OF_MONTH))
                .getResultList();
    }


}
