package com.dgsoft.house.owner.total;

import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.BusinessBuild;
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
 * Created by wxy on 2015-11-09.
 * 项目预售许可证统计
 */
@Name("totalProjectCard")
public class TotalProjectCard {
    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private FacesMessages facesMessages;

    @In(create = true)
    private FacesContext facesContext;

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


    public void totalProjectCardData(){

        List<OwnerBusiness> ownerBusinessList = ownerEntityLoader.getEntityManager().createQuery
                ("select ob from OwnerBusiness ob left join ob.businessProjects project"
                +" where ob.status in ('COMPLETE') and ob.defineId= :defineId and ob.regTime >= :beginDate and ob.regTime <= :endDate order by ob.regTime")
                .setParameter("defineId","WP50")
                .setParameter("beginDate",fromDateTime)
                .setParameter("endDate",toDateTime)
                .getResultList();
        if (ownerBusinessList!=null){
            Logging.getLog(getClass()).debug("aaaaa");
        }else {
            Logging.getLog(getClass()).debug("bbbbb");
        }
        if (ownerBusinessList!=null){

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFCellStyle headCellStyle  = workbook.createCellStyle();

            headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
            headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
            XSSFFont font2 = workbook.createFont();// 创建字体对象
            font2.setFontHeightInPoints((short) 12);// 设置字体大小
            headCellStyle.setFont(font2);


            int row  =0;//行
            int col =0;//列

            XSSFSheet sheet = workbook.createSheet("预售许可证统计");
            //打印标题2
            Row row2 = sheet.createRow(row++);
            Cell cell2 = row2.createCell(col ++);//列
            cell2.setCellValue("业务名称");
            cell2.setCellStyle(headCellStyle);

            Cell cell3 = row2.createCell(col++);//列
            cell3.setCellValue("业务编号");
            cell3.setCellStyle(headCellStyle);

            Cell cell4 = row2.createCell(col++);//列
            cell4.setCellValue("开发商");
            cell4.setCellStyle(headCellStyle);

            Cell cell5 = row2.createCell(col++);//列
            cell5.setCellValue("预售许可证号");
            cell5.setCellStyle(headCellStyle);

            Cell cell8 = row2.createCell(col++);//列
            cell8.setCellValue("审批时间");
            cell8.setCellStyle(headCellStyle);


            Cell cell6 = row2.createCell(col++);//列
            cell6.setCellValue("楼幢名称");
            cell6.setCellStyle(headCellStyle);

            Cell cell7 = row2.createCell(col++);//列
            cell7.setCellValue("门牌号");
            cell7.setCellStyle(headCellStyle);

            Cell cell9 = row2.createCell(col++);//列
            cell9.setCellValue("住宅套数");
            cell9.setCellStyle(headCellStyle);

            Cell cell10 = row2.createCell(col++);//列
            cell10.setCellValue("住宅面积");
            cell10.setCellStyle(headCellStyle);

            Cell cell11 = row2.createCell(col++);//列
            cell11.setCellValue("网点套数");
            cell11.setCellStyle(headCellStyle);

            Cell cell12 = row2.createCell(col++);//列
            cell12.setCellValue("网点面积");
            cell12.setCellStyle(headCellStyle);


            Cell cell13 = row2.createCell(col++);//列
            cell13.setCellValue("其它套数");
            cell13.setCellStyle(headCellStyle);

            Cell cell14 = row2.createCell(col++);//列
            cell14.setCellValue("其它面积");
            cell14.setCellStyle(headCellStyle);


            for (OwnerBusiness ob: ownerBusinessList){
                int sr=row;
                if(ob.getBusinessProject()!=null) {
                    Row r = sheet.createRow(row);//行

                    col = 0;//列

                    Cell cell = r.createCell(col++);
                    cell.setCellValue(ob.getDefineName());
                    cell = r.createCell(col++);
                    cell.setCellValue(ob.getId());
                    cell = r.createCell(col++);
                    cell.setCellValue(ob.getBusinessProject().getDeveloperCode());
                    cell = r.createCell(col++);
                    cell.setCellValue(ob.getMakeCards().iterator().next().getNumber());

                    cell = r.createCell(col++);
                    cell.setCellValue(ob.getRegTime().toString());

                    for (BusinessBuild businessBuild : ob.getBusinessProject().getBusinessBuilds()){
                        int buildCol=5;//列
                        Row br;
                        if (sr==row){
                            br = r;
                            row++;
                        }else
                            br = sheet.createRow(row++);//行
                        cell = br.createCell(buildCol++);
                        cell.setCellValue(businessBuild.getBuildName());
                        cell = br.createCell(buildCol++);
                        cell.setCellValue(businessBuild.getDoorNo());
                        cell = br.createCell(buildCol++);
                        cell.setCellValue(businessBuild.getHomeCount());
                        cell = br.createCell(buildCol++);
                        cell.setCellValue(businessBuild.getHomeArea().doubleValue());

                        cell = br.createCell(buildCol++);
                        cell.setCellValue(businessBuild.getShopCount());
                        cell = br.createCell(buildCol++);
                        cell.setCellValue(businessBuild.getShopArea().doubleValue());

                        cell = br.createCell(buildCol++);
                        cell.setCellValue(businessBuild.getUnhomeCount());

                        cell = br.createCell(buildCol++);
                        cell.setCellValue(businessBuild.getUnhomeArea().doubleValue());

                    }
                }


                sheet.addMergedRegion(new CellRangeAddress(sr,row-1,0,1));
                sheet.addMergedRegion(new CellRangeAddress(sr,row-1,1,1));
                sheet.addMergedRegion(new CellRangeAddress(sr,row-1,2,2));
                sheet.addMergedRegion(new CellRangeAddress(sr,row-1,3,3));
                sheet.addMergedRegion(new CellRangeAddress(sr,row-1,4,4));
            }



            sheet.setForceFormulaRecalculation(true);
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.responseReset();
            externalContext.setResponseContentType("application/vnd.ms-excel");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=exportProjectCard.xlsx");
            try {
                workbook.write(externalContext.getResponseOutputStream());
                facesContext.responseComplete();
            } catch (IOException e) {
                facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "ExportIOError");
                Logging.getLog(getClass()).error("export error", e);
            }






        }







    }

}
