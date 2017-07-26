package com.dgsoft.house.owner.total;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.seam.annotations.In;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.List;

/**
 * Created by cooper on 26/07/2017.
 */
public abstract class NativeReportBase {

    @In(create = true)
    private FacesContext facesContext;

    @In(create = true)
    private FacesMessages facesMessages;

    protected int genReportTitle(XSSFSheet sheet){
        return 0;
    }

    protected abstract List getData();

    public void report(){

        List rows = getData();

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet
                sheet = workbook.createSheet("查询结果");

        int rowIndex = genReportTitle(sheet);

        for (Object row : rows) {
            Object[] cells = (Object[]) row;
            Row outRow = sheet.createRow(rowIndex++);

            for(int i = 0 ; i < cells.length; i++){

                if (cells[i] != null) {
                    Cell cell = outRow.createCell(i);
                    cell.setCellValue(cells[i].toString());
                }
            }
        }


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
