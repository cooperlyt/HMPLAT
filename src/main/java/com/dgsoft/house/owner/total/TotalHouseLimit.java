package com.dgsoft.house.owner.total;

import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.model.BusinessPool;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseRecord;
import com.dgsoft.house.owner.model.LockedHouse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cooper on 4/26/16.
 */
@Name("totalHouseLimit")
public class TotalHouseLimit {

    @In(create = true)
    private EntityManager houseEntityManager;

    @In(create = true)
    private EntityManager ownerEntityManager;

    @In(create = true)
    private FacesContext facesContext;

    @In(create = true)
    private FacesMessages facesMessages;


    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public void export(){

        String[] keys = searchKey.split(",");
        Map<String,HouseInfo> data = new HashMap<String, HouseInfo>();


        for(String key : keys){
            String[] mbbh = key.split("-");
            if (mbbh.length == 4){
                try {
                    data.put(key,ownerEntityManager.createQuery("select houseRecord from HouseRecord houseRecord where houseRecord.businessHouse.mapNumber = :mapNumber and houseRecord.businessHouse.blockNo = :blockNumber and houseRecord.businessHouse.buildNo = :buildNumber and houseRecord.businessHouse.houseOrder = :houseOrder ", HouseRecord.class)
                            .setParameter("mapNumber", mbbh[0]).setParameter("blockNumber", mbbh[1]).setParameter("buildNumber", mbbh[2]).setParameter("houseOrder", mbbh[3]).getSingleResult().getBusinessHouse());
                }catch (NoResultException e){
                    data.put(key,null);
                }
            }
        }




        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle headCellStyle = workbook.createCellStyle();
        XSSFCellStyle cellStyle = workbook.createCellStyle();

        headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        XSSFCellStyle dateStyle = workbook.createCellStyle();

        XSSFDataFormat dataFormat = workbook.createDataFormat();
        dateStyle.setDataFormat(dataFormat.getFormat("yyyy-m-d"));

        XSSFSheet sheet = workbook.createSheet("房屋限制情况查询");

        int rowIndex = 0;
        int cellIndex = 0;
        Cell cell;
        Row row = sheet.createRow(rowIndex++);
        cell = row.createCell(cellIndex++);
        cell.setCellValue("产籍号");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(cellIndex++);
        cell.setCellValue("面积");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(cellIndex++);
        cell.setCellValue("产权人");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(cellIndex++);
        cell.setCellValue("共有权人");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(cellIndex++);
        cell.setCellValue("抵押权人");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(cellIndex++);
        cell.setCellValue("抵押金额");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(cellIndex++);
        cell.setCellValue("抵押时间");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(cellIndex++);
        cell.setCellValue("查封法院");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(cellIndex++);
        cell.setCellValue("查封人");
        cell.setCellStyle(headCellStyle);

        cell = row.createCell(cellIndex++);
        cell.setCellValue("查封时间");
        cell.setCellStyle(headCellStyle);







        for(Map.Entry<String,HouseInfo> entry: data.entrySet()){

            int startRow = rowIndex;


            String houseCode = null;
            if (entry.getValue() == null){
                String[] mbbh = entry.getKey().split("-");
                if (mbbh.length == 4){
                    try {
                        row = sheet.createRow(rowIndex++);
                        cell = row.createCell(0);
                        cell.setCellValue(entry.getKey());
                        cell.setCellStyle(headCellStyle);

                        entry.setValue(houseEntityManager.createQuery("select house from House house where house.deleted = false and house.build.mapNumber = :mapNumber and house.build.blockNo = :blockNumber and house.build.buildNo = :buildNumber and house.houseOrder = :houseOrder", House.class)
                                .setParameter("mapNumber", mbbh[0]).setParameter("blockNumber", mbbh[1]).setParameter("buildNumber", mbbh[2]).setParameter("houseOrder", mbbh[3]).getSingleResult());

                        cell = row.createCell(1);
                        cell.setCellValue(entry.getValue().getHouseArea().doubleValue());
                        cell.setCellStyle(cellStyle);

                        houseCode = entry.getValue().getHouseCode();

                    }catch (NoResultException e){

                    }
                }



            }else{
                houseCode = entry.getValue().getHouseCode();

                List<HouseBusiness> houseBusinessList = ownerEntityManager.createQuery("select houseBusiness from HouseBusiness houseBusiness where houseBusiness.canceled = false and houseBusiness.houseCode =:houseCode and houseBusiness.ownerBusiness.status in ('COMPLETE','MODIFYING') and houseBusiness.ownerBusiness.defineId in ('WP73','WP153','WP151','WP152','WP9','WP10','WP13','WP14','WP15','WP18','WP19','WP150','WP1','WP2') order by houseBusiness.ownerBusiness.regTime", HouseBusiness.class)
                        .setParameter("houseCode",entry.getValue().getHouseCode()).getResultList();

                if (houseBusinessList.isEmpty()){
                    row = sheet.createRow(rowIndex++);
                    cell = row.createCell(0);
                    cell.setCellValue(entry.getKey());
                    cell.setCellStyle(headCellStyle);

                    cell = row.createCell(1);
                    cell.setCellValue(entry.getValue().getHouseArea().doubleValue());
                    cell.setCellStyle(cellStyle);
                }

                boolean headAdded = false;

                for(HouseBusiness houseBusiness: houseBusinessList){
                    row = sheet.createRow(rowIndex++);

                    if (!headAdded){
                        cell = row.createCell(0);
                        cell.setCellValue(entry.getKey());
                        cell.setCellStyle(headCellStyle);
                        headAdded = true;
                    }

                    cellIndex =1;
                    cell = row.createCell(cellIndex++);
                    cell.setCellValue(houseBusiness.getAfterBusinessHouse().getHouseArea().doubleValue());
                    cell.setCellStyle(cellStyle);


                    cell = row.createCell(cellIndex++);
                    if (houseBusiness.getAfterBusinessHouse().getBusinessHouseOwner() != null)
                        cell.setCellValue(houseBusiness.getAfterBusinessHouse().getBusinessHouseOwner().getPersonName());
                    cell.setCellStyle(cellStyle);


                    cell = row.createCell(cellIndex++);
                    if (!houseBusiness.getAfterBusinessHouse().getBusinessPools().isEmpty()) {
                        String poolNames = "";
                        for (BusinessPool pool : houseBusiness.getAfterBusinessHouse().getBusinessPoolList()) {
                            if ("".equals(poolNames)){
                                poolNames += ",";
                            }
                            poolNames += pool.getPersonName();
                        }
                        cell.setCellValue(poolNames);
                        cell.setCellStyle(cellStyle);
                    }

                    if (!houseBusiness.getOwnerBusiness().getDefineId().equals("WP73")){
                        cell = row.createCell(cellIndex++);
                        cell.setCellValue(houseBusiness.getOwnerBusiness().getMortgaegeRegiste().getFinancial().getName());
                        cell.setCellStyle(cellStyle);

                        cell = row.createCell(cellIndex++);
                        cell.setCellValue(houseBusiness.getOwnerBusiness().getMortgaegeRegiste().getHighestMountMoney().doubleValue());
                        cell.setCellStyle(cellStyle);

                        cell = row.createCell(cellIndex++);
                        cell.setCellStyle(dateStyle);
                        cell.setCellValue(houseBusiness.getOwnerBusiness().getMortgaegeRegiste().getMortgageDueTimeS());

                    }else{
                        cellIndex += 3;

                        cell = row.createCell(cellIndex++);
                        cell.setCellValue(houseBusiness.getOwnerBusiness().getCloseHouse().getCloseDownClour());
                        cell.setCellStyle(cellStyle);

                        cell = row.createCell(cellIndex++);
                        cell.setCellValue(houseBusiness.getOwnerBusiness().getCloseHouse().getSendPeople());
                        cell.setCellStyle(cellStyle);

                        cell = row.createCell(cellIndex++);
                        cell.setCellValue(houseBusiness.getOwnerBusiness().getCloseHouse().getCloseDate());
                        cell.setCellStyle(dateStyle);


                    }


                }



            }

            if (houseCode != null){
                try{
                    HouseBusiness houseBusiness = ownerEntityManager.createQuery("select houseBusiness from HouseBusiness houseBusiness where houseBusiness.houseCode =:houseCode and houseBusiness.ownerBusiness.status in ( 'RUNNING', 'ABORT', 'SUSPEND') ",HouseBusiness.class)
                            .setParameter("houseCode",houseCode).getSingleResult();


                        row = sheet.createRow(rowIndex++);
                        cell = row.createCell(1);
                        cell.setCellValue("正在办理业务:" + houseBusiness.getOwnerBusiness().getDefineName());
                        cell.setCellStyle(cellStyle);
                        sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1,rowIndex - 1,1,9));

                }catch (NoResultException e){

                }

                List<LockedHouse> lockedHouses = ownerEntityManager.createQuery("select lh from LockedHouse  lh where lh.houseCode = :houseCode", LockedHouse.class)
                        .setParameter("houseCode",houseCode).getResultList();
                for(LockedHouse lh: lockedHouses){

                    row = sheet.createRow(rowIndex++);
                    cell = row.createCell(1);
                    cell.setCellValue("预警:" + lh.getDescription());
                    cell.setCellStyle(cellStyle);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1,rowIndex - 1,1,9));

                }


            }


            sheet.addMergedRegion(new CellRangeAddress(startRow,rowIndex - 1,0,0));

        }


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
