package com.dgsoft.house.owner.total;

import com.dgsoft.common.system.SystemEntityLoader;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessEmp;
import com.dgsoft.house.owner.model.ContractOwner;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
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

//        List<OwnerBusiness> ownerBusinessList = ownerEntityLoader.getEntityManager().createQuery
//                ("select ob from OwnerBusiness ob left join fetch ob.contractOwners ContractOwners left join fetch ContractOwners.houseContract left join fetch ob.saleInfo" +
//                        " where ob.defineId=:defineId and  ob.createTime >= :beginDate and ob.createTime <= :endDate order by ContractOwners AfterBusinessHouse.sectionName,ob.createTime", OwnerBusiness.class)
//                .setParameter("beginDate", fromDateTime)
//                .setParameter("endDate", toDateTime)
//                .setParameter("defineId","WP42").getResultList();

        List<ContractOwner> contractOwnerList =ownerEntityLoader.getEntityManager().createQuery("select contractOwner from ContractOwner contractOwner left join fetch contractOwner.ownerBusiness OwnerBusiness left join fetch contractOwner.houseContract " +
                "left join fetch contractOwner.businessHouse HOUSE left join fetch OwnerBusiness.saleInfos left join fetch House.houseBusinessForAfter" +
                " where OwnerBusiness.defineId=:defineId and OwnerBusiness.createTime >= :beginDate and OwnerBusiness.createTime <= :endDate order by HOUSE.sectionName")
                .setParameter("beginDate", fromDateTime)
                .setParameter("endDate", toDateTime)
                .setParameter("defineId", "WP42").getResultList();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle headCellStyle  = workbook.createCellStyle();
        XSSFCellStyle cellStyle  = workbook.createCellStyle();


        headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
        headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
        XSSFFont font = workbook.createFont();// 创建字体对象
        font.setFontHeightInPoints((short) 12);// 设置字体大小
        headCellStyle.setFont(font);

        int rowIndex = 0;//行
        int cellIndex = 0;//列

        XSSFSheet sheet = workbook.createSheet("备案网签明细");


        Row row = sheet.createRow(rowIndex++);
        Cell cell = row.createCell(cellIndex ++);
        cell.setCellValue("业务名称");
        cell.setCellStyle(headCellStyle);


        Cell cell1 = row.createCell(cellIndex ++);
        cell1.setCellValue("业务编号");
        cell1.setCellStyle(headCellStyle);


        Cell cell2 = row.createCell(cellIndex ++);
        cell2.setCellValue("建立时间");
        cell2.setCellStyle(headCellStyle);

        Cell cell3 = row.createCell(cellIndex ++);
        cell3.setCellValue("备案人");
        cell3.setCellStyle(headCellStyle);

        Cell cell4 = row.createCell(cellIndex ++);
        cell4.setCellValue("备案人证件号");
        cell4.setCellStyle(headCellStyle);

        Cell cell7 = row.createCell(cellIndex ++);
        cell7.setCellValue("购房款");
        cell7.setCellStyle(headCellStyle);

        Cell cell5 = row.createCell(cellIndex ++);
        cell5.setCellValue("房屋编号");
        cell5.setCellStyle(headCellStyle);

        Cell cell6 = row.createCell(cellIndex ++);
        cell6.setCellValue("小区名称");
        cell6.setCellStyle(headCellStyle);

        if (contractOwnerList!=null && contractOwnerList.size()>0){
            for(ContractOwner contractOwner:contractOwnerList) {

                cellIndex = 0;
                Row row1 = sheet.createRow(rowIndex++);

                Cell cell8 = row1.createCell(cellIndex++);
                cell8.setCellValue(contractOwner.getOwnerBusiness().getDefineName());
                cell8.setCellStyle(headCellStyle);

                cell8 = row1.createCell(cellIndex++);
                cell8.setCellValue(contractOwner.getOwnerBusiness().getId());
                cell8.setCellStyle(headCellStyle);

                cell8 = row1.createCell(cellIndex++);
                cell8.setCellValue(contractOwner.getOwnerBusiness().getCreateTime().toString());
                cell8.setCellStyle(headCellStyle);

                cell8 = row1.createCell(cellIndex++);
                cell8.setCellValue(contractOwner.getPersonName());
                cell8.setCellStyle(headCellStyle);


                cell8 = row1.createCell(cellIndex++);
                cell8.setCellValue(contractOwner.getCredentialsNumber());
                cell8.setCellStyle(headCellStyle);


                cell8 = row1.createCell(cellIndex++);
                if (contractOwner.getOwnerBusiness().getSaleInfo()!=null) {
                    cell8.setCellValue(contractOwner.getOwnerBusiness().getSaleInfo().getSumPrice().doubleValue());
                }
                cell8.setCellStyle(headCellStyle);


                cell8 = row1.createCell(cellIndex++);
                cell8.setCellValue(contractOwner.getHouseCode());
                cell8.setCellStyle(headCellStyle);


                cell8 = row1.createCell(cellIndex++);
                cell8.setCellValue(contractOwner.getBusinessHouse().getSectionName());
                cell8.setCellStyle(headCellStyle);
            }


        }




        sheet.setForceFormulaRecalculation(true);
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
