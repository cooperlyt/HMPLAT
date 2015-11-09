package com.dgsoft.house.owner.total;

import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessEmp;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
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
 * Created by wxy on 2015-11-08.
 * 业务移交单
 */
@Name("businessTransferDetailed")
public class BusinessTransferDetailed {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private FacesMessages facesMessages;

    @In(create = true)
    private FacesContext facesContext;

    private Date fromDateTime;

    private Date toDateTime;

    private String workerName;

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

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




    public void totalTransferDetailedData(){


//        List<OwnerBusiness> ownerBusinessList = ownerEntityLoader.getEntityManager().createQuery
//                ("select ob from OwnerBusiness ob left join ob.businessEmps be where ob.applyTime >= :beginDate and ob.applyTime <= :endDate and ob.recorded=:record and ob.source= :source and be.type=:businessEmptype and be.empName=:empName order by ob.defineId,ob.applyTime", OwnerBusiness.class)
//                .setParameter("beginDate",fromDateTime)
//                .setParameter("endDate",toDateTime)
//                .setParameter("record",false)
//                .setParameter("source",OwnerBusiness.BusinessSource.BIZ_CREATE)
//                .setParameter("businessEmptype",BusinessEmp.EmpType.APPLY_EMP)
//                .setParameter("empName",workerName).getResultList();


        List<OwnerBusiness> ownerBusinessList = ownerEntityLoader.getEntityManager().createQuery
                ("select ob from OwnerBusiness ob left join ob.businessEmps be where ob.status in ('RUNNING') and ob.applyTime >= :beginDate and ob.applyTime <= :endDate and ob.recorded=:record and ob.source= :source and be.type=:businessEmptype order by ob.defineId,ob.applyTime", OwnerBusiness.class)
                .setParameter("beginDate",fromDateTime)
                .setParameter("endDate",toDateTime)
                .setParameter("record",false)
                .setParameter("source",OwnerBusiness.BusinessSource.BIZ_CREATE)
                .setParameter("businessEmptype",BusinessEmp.EmpType.APPLY_EMP).getResultList();


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle headCellStyle1  = workbook.createCellStyle();
        XSSFCellStyle headCellStyle2  = workbook.createCellStyle();
        XSSFCellStyle cellStyle  = workbook.createCellStyle();

        //标题一格式
        headCellStyle1.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
        headCellStyle1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
        XSSFFont font = workbook.createFont();// 创建字体对象
        font.setFontHeightInPoints((short) 15);// 设置字体大小
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体
        headCellStyle1.setFont(font);

        //标题二格式
        headCellStyle2.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
        headCellStyle2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
        XSSFFont font2 = workbook.createFont();// 创建字体对象
        font2.setFontHeightInPoints((short) 12);// 设置字体大小
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体
        headCellStyle2.setFont(font2);

        //段落格式
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);



        int row  =0;
        int col =0;

        XSSFSheet sheet = workbook.createSheet("业务移交单");

        //打印标题1
        Row row1 = sheet.createRow(row++);//行
        Cell cell1 = row1.createCell(0);//列
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,9));
        cell1.setCellValue("业务移交单");
        cell1.setCellStyle(headCellStyle1);

       //打印标题2
        Row row2 = sheet.createRow(row++);
        Cell cell2 = row2.createCell(col ++);//列
        cell2.setCellValue("业务名称");
        cell2.setCellStyle(headCellStyle2);

        Cell cell3 = row2.createCell(col++);//列
        cell3.setCellValue("业务编号");
        cell3.setCellStyle(headCellStyle2);

        Cell cell4 = row2.createCell(col++);//列
        cell4.setCellValue("申请人");
        cell4.setCellStyle(headCellStyle2);

        Cell cell5 = row2.createCell(col++);//列
        cell5.setCellValue("受理人");
        cell5.setCellStyle(headCellStyle2);

        Cell cell6 = row2.createCell(col++);//列
        cell6.setCellValue("受理日期");
        cell6.setCellStyle(headCellStyle2);

        Cell cell7 = row2.createCell(col++);//列
        cell7.setCellValue("审核人签字");
        cell7.setCellStyle(headCellStyle2);


        Cell cell8 = row2.createCell(col++);//列
        cell8.setCellValue("审核人日期");
        cell8.setCellStyle(headCellStyle2);

        Cell cell9 = row2.createCell(col++);//列
        cell9.setCellValue("审批人签字");
        cell9.setCellStyle(headCellStyle2);

        Cell cell10 = row2.createCell(col++);//列
        cell10.setCellValue("审批日期");
        cell10.setCellStyle(headCellStyle2);

        Cell cell11 = row2.createCell(col++);//列
        cell11.setCellValue("备注");
        cell11.setCellStyle(headCellStyle2);

        for (OwnerBusiness ob: ownerBusinessList){
            Row r = sheet.createRow(row++);
            col = 0;
            Cell cell = r.createCell(col++);
            cell.setCellValue(ob.getDefineName());
            cell = r.createCell(col++);
            cell.setCellValue(ob.getId());
            cell = r.createCell(col++);
            cell.setCellValue(ob.getApplyPersion().getPersonName());
            cell = r.createCell(col++);
            cell.setCellValue(ob.getApplyEmp().getEmpName());
            cell = r.createCell(col++);
            cell.setCellValue(ob.getApplyTime().toString());

        }


        sheet.setForceFormulaRecalculation(true);
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Content-Disposition", "attachment;filename=exportBusiness.xlsx");
        try {
            workbook.write(externalContext.getResponseOutputStream());
            facesContext.responseComplete();
        } catch (IOException e) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "ExportIOError");
            Logging.getLog(getClass()).error("export error", e);
        }

    }















}
