package com.dgsoft.house.owner.total;

import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.common.SearchDateArea;
import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.SystemEntityLoader;
import com.dgsoft.common.system.business.BusinessDefineCache;
import com.dgsoft.common.system.model.*;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.total.data.BusinessTotalData;
import com.dgsoft.house.owner.total.data.FeeTotalData;
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

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wxy on 2015-11-14.
 * 业务统计，数量，面积，购房款，评估价
 */
@Name("totalBusinessInfoData")
public class TotalBusinessInfoData {

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

    private String housePorperty;

    private BigDecimal beginHouseArea;

    private BigDecimal endHouseArea;

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


    public BigDecimal getEndHouseArea() {
        return endHouseArea;
    }

    public void setEndHouseArea(BigDecimal endHouseArea) {
        this.endHouseArea = endHouseArea;
    }

    public BigDecimal getBeginHouseArea() {
        return beginHouseArea;
    }

    public void setBeginHouseArea(BigDecimal beginHouseArea) {
        this.beginHouseArea = beginHouseArea;
    }

    public String getHousePorperty() {
        return housePorperty;
    }

    public void setHousePorperty(String housePorperty) {
        this.housePorperty = housePorperty;
    }

    public List<String> getHouseUseType(String key){
        List<Word> words =DictionaryWord.instance().getWordList("house.useType");
        List<String> xzwords = new ArrayList<String>();
        for (Word word:words){
            if (word.getKey().equals(key)){
                xzwords.add(word.getId());
            }
        }
        return xzwords;
    }

    public void totalBusiness(){

        Map<String,BusinessDefine> businessDefineMap = new HashMap<String, BusinessDefine>();


        for (BusinessDefine businessDefine: systemEntityLoader.getEntityManager().createQuery("select businessDefine from BusinessDefine businessDefine where businessDefine.enable=true order by businessDefine.priority",BusinessDefine.class).getResultList()){
            businessDefineMap.put(businessDefine.getId(),businessDefine);
        }

        List<BusinessDefine> businessDefines = new ArrayList<BusinessDefine>(businessDefineMap.values());
        Collections.sort(businessDefines, OrderBeanComparator.getInstance());



       //住宅
        List<BusinessTotalData> zcbusinessTotalDataList = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.BusinessTotalData(ob.defineId,ob.defineName,count(ob.id),sum(SaleInfos.sumPrice),sum(Evaluates.assessmentPrice),sum(AfterBusinessHouse.houseArea)) " +
                "from OwnerBusiness ob left join ob.evaluates Evaluates left join ob.houseBusinesses HouseBusinesses left join HouseBusinesses.afterBusinessHouse AfterBusinessHouse left join AfterBusinessHouse.saleInfos SaleInfos" +
                " where ob.status in ('COMPLETE','COMPLETE_CANCEL','MODIFYING') and ob.source in ('BIZ_CREATE','BIZ_IMPORT','BIZ_OUTSIDE') and AfterBusinessHouse.useType in (:usetype)  and ob.regTime >= :beginDate and ob.regTime <= :endDate group by ob.defineId",BusinessTotalData.class)
                .setParameter("beginDate",fromDateTime)
                .setParameter("endDate", toDateTime)
                .setParameter("usetype",getHouseUseType("1")).getResultList();

        Logging.getLog(getClass()).debug("zcbusinessTotalDataList---"+zcbusinessTotalDataList.size());
        //商业营业
        List<BusinessTotalData> sybusinessTotalDataList = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.BusinessTotalData(ob.defineId,ob.defineName,count(ob.id),sum(SaleInfos.sumPrice),sum(Evaluates.assessmentPrice),sum(AfterBusinessHouse.houseArea)) " +
                "from OwnerBusiness ob left join ob.evaluates Evaluates left join ob.houseBusinesses HouseBusinesses left join HouseBusinesses.afterBusinessHouse AfterBusinessHouse left join AfterBusinessHouse.saleInfos SaleInfos" +
                " where ob.status in ('COMPLETE','COMPLETE_CANCEL','MODIFYING') and ob.source in ('BIZ_CREATE','BIZ_IMPORT','BIZ_OUTSIDE') and AfterBusinessHouse.useType in (:usetype)  and ob.regTime >= :beginDate and ob.regTime <= :endDate group by ob.defineId",BusinessTotalData.class)
                .setParameter("beginDate",fromDateTime)
                .setParameter("endDate", toDateTime)
                .setParameter("usetype",getHouseUseType("2")).getResultList();


        Logging.getLog(getClass()).debug("sybusinessTotalDataList---"+sybusinessTotalDataList.size());
        //办公
        List<BusinessTotalData> bgbusinessTotalDataList = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.BusinessTotalData(ob.defineId,ob.defineName,count(ob.id),sum(SaleInfos.sumPrice),sum(Evaluates.assessmentPrice),sum(AfterBusinessHouse.houseArea)) " +
                "from OwnerBusiness ob left join ob.evaluates Evaluates left join ob.houseBusinesses HouseBusinesses left join HouseBusinesses.afterBusinessHouse AfterBusinessHouse left join AfterBusinessHouse.saleInfos SaleInfos" +
                " where ob.status in ('COMPLETE','COMPLETE_CANCEL','MODIFYING') and ob.source in ('BIZ_CREATE','BIZ_IMPORT','BIZ_OUTSIDE') and AfterBusinessHouse.useType in (:usetype)  and ob.regTime >= :beginDate and ob.regTime <= :endDate group by ob.defineId",BusinessTotalData.class)
                .setParameter("beginDate",fromDateTime)
                .setParameter("endDate", toDateTime)
                .setParameter("usetype",getHouseUseType("3")).getResultList();

        Logging.getLog(getClass()).debug("bgbusinessTotalDataList---"+bgbusinessTotalDataList.size());

        //工也仓储
        List<BusinessTotalData> gybusinessTotalDataList = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.BusinessTotalData(ob.defineId,ob.defineName,count(ob.id),sum(SaleInfos.sumPrice),sum(Evaluates.assessmentPrice),sum(AfterBusinessHouse.houseArea)) " +
                "from OwnerBusiness ob left join ob.evaluates Evaluates left join ob.houseBusinesses HouseBusinesses left join HouseBusinesses.afterBusinessHouse AfterBusinessHouse left join AfterBusinessHouse.saleInfos SaleInfos" +
                " where ob.status in ('COMPLETE','COMPLETE_CANCEL','MODIFYING') and ob.source in ('BIZ_CREATE','BIZ_IMPORT','BIZ_OUTSIDE') and AfterBusinessHouse.useType in (:usetype)  and ob.regTime >= :beginDate and ob.regTime <= :endDate group by ob.defineId",BusinessTotalData.class)
                .setParameter("beginDate",fromDateTime)
                .setParameter("endDate", toDateTime)
                .setParameter("usetype",getHouseUseType("4")).getResultList();
        Logging.getLog(getClass()).debug("gybusinessTotalDataList---"+gybusinessTotalDataList.size());
        //其它
        List<BusinessTotalData> qtbusinessTotalDataList = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.data.BusinessTotalData(ob.defineId,ob.defineName,count(ob.id),sum(SaleInfos.sumPrice),sum(Evaluates.assessmentPrice),sum(AfterBusinessHouse.houseArea)) " +
                "from OwnerBusiness ob left join ob.evaluates Evaluates left join ob.houseBusinesses HouseBusinesses left join HouseBusinesses.afterBusinessHouse AfterBusinessHouse left join AfterBusinessHouse.saleInfos SaleInfos" +
                " where ob.status in ('COMPLETE','COMPLETE_CANCEL','MODIFYING') and ob.source in ('BIZ_CREATE','BIZ_IMPORT','BIZ_OUTSIDE') and AfterBusinessHouse.useType in (:usetype)  and ob.regTime >= :beginDate and ob.regTime <= :endDate group by ob.defineId",BusinessTotalData.class)
                .setParameter("beginDate",fromDateTime)
                .setParameter("endDate", toDateTime)
                .setParameter("usetype",getHouseUseType("0")).getResultList();
        Logging.getLog(getClass()).debug("qtbusinessTotalDataList---"+qtbusinessTotalDataList.size());







        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle headCellStyle  = workbook.createCellStyle();

        headCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
        headCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
        XSSFFont font = workbook.createFont();// 创建字体对象
        font.setFontHeightInPoints((short) 12);// 设置字体大小
        headCellStyle.setFont(font);
        XSSFSheet sheet = workbook.createSheet("业务统计");

        int rowIndex = 0;
        int cellIndex = 0;
        List<String> strings=new ArrayList();
        strings.add("住宅");
        strings.add("商业营业");
        strings.add("办公");
        strings.add("工业仓储");
        strings.add("其他");
        Row row1 = sheet.createRow(rowIndex++);
        Row row2 = sheet.createRow(rowIndex++);


        Cell cell1 = row1.createCell(cellIndex++);
        sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
        cell1.setCellValue("业务名称");
        cell1.setCellStyle(headCellStyle);


        Cell cell2 = row1.createCell(cellIndex++);
        sheet.addMergedRegion(new CellRangeAddress(0,0,1,5));
        cell2.setCellValue("业务办理数量");
        cell2.setCellStyle(headCellStyle);
        for(int i=0;i<strings.size();i++){
            Cell cell = row2.createCell(i+1);
            cell.setCellValue(strings.get(i));
            cell.setCellStyle(headCellStyle);
        }

        cellIndex = cellIndex+4;
        Cell cell3 = row1.createCell(cellIndex);
        sheet.addMergedRegion(new CellRangeAddress(0,0,6,10));
        cell3.setCellValue("建筑面积");
        cell3.setCellStyle(headCellStyle);
        for(int i=0;i<strings.size();i++){
            Cell cell = row2.createCell(i+6);
            cell.setCellValue(strings.get(i));
            cell.setCellStyle(headCellStyle);
        }
        cellIndex = cellIndex+5;
        Cell cell4 = row1.createCell(cellIndex);
        sheet.addMergedRegion(new CellRangeAddress(0,0,11,15));
        cell4.setCellValue("购房款");
        cell4.setCellStyle(headCellStyle);
        for(int i=0;i<strings.size();i++){
            Cell cell = row2.createCell(i+11);
            cell.setCellValue(strings.get(i));
            cell.setCellStyle(headCellStyle);
        }
        cellIndex = cellIndex+5;
        Cell cell5 = row1.createCell(cellIndex);
        sheet.addMergedRegion(new CellRangeAddress(0,0,16,20));
        cell5.setCellValue("评估价");
        cell5.setCellStyle(headCellStyle);
        for(int i=0;i<strings.size();i++){
            Cell cell = row2.createCell(i+16);
            cell.setCellValue(strings.get(i));
            cell.setCellStyle(headCellStyle);
        }

       // rowIndex=3;

        int hjrowIndex=0;
        for(BusinessDefine businessDefine:businessDefines){
            cellIndex=0;
            Row row = sheet.createRow(rowIndex++);
            Cell cell = row.createCell(cellIndex++);
            cell.setCellValue(businessDefine.getName());
            cell.setCellStyle(headCellStyle);




            Cell zcCountCell =row.createCell(cellIndex);
            zcCountCell.setCellStyle(headCellStyle);
            Cell zcHouseArea = row.createCell(5+cellIndex);
            zcHouseArea.setCellStyle(headCellStyle);
            Cell zcSumPrice = row.createCell(10+cellIndex);
            zcSumPrice.setCellStyle(headCellStyle);
            Cell zcAssessmentPrice = row.createCell(15+cellIndex);
            zcAssessmentPrice.setCellStyle(headCellStyle);

            if (zcbusinessTotalDataList !=null && zcbusinessTotalDataList.size()>0) {

                for (BusinessTotalData businessTotalData : zcbusinessTotalDataList) {
                    if (businessTotalData.getBidId().equals(businessDefine.getId())) {
                        zcCountCell.setCellValue(businessTotalData.getCount());
                        zcHouseArea.setCellValue(businessTotalData.getHouseArea().doubleValue());
                        if (businessTotalData.getSumPrice()!=null) {
                            zcSumPrice.setCellValue(businessTotalData.getSumPrice().doubleValue());
                        }else{
                            zcSumPrice.setCellValue(0.0);
                        }
                        if (businessTotalData.getAssessmentPrice()!=null) {
                            zcAssessmentPrice.setCellValue(businessTotalData.getAssessmentPrice().doubleValue());
                        }else{
                            zcAssessmentPrice.setCellValue(0.0);
                        }

                        break;
                    }else{
                        zcCountCell.setCellValue(0);
                        zcHouseArea.setCellValue(0.0);
                        zcSumPrice.setCellValue(0.0);
                        zcAssessmentPrice.setCellValue(0.0);
                    }

                }
            }else{
                zcCountCell.setCellValue(0);
                zcHouseArea.setCellValue(0.0);
                zcSumPrice.setCellValue(0.0);
                zcAssessmentPrice.setCellValue(0.0);
            }

            Cell syCountCell =row.createCell(1+cellIndex);
            syCountCell.setCellStyle(headCellStyle);
            Cell syHouseArea = row.createCell(6+cellIndex);
            syHouseArea.setCellStyle(headCellStyle);
            Cell sySumPrice = row.createCell(11+cellIndex);
            sySumPrice.setCellStyle(headCellStyle);
            Cell syAssessmentPrice = row.createCell(16+cellIndex);
            syAssessmentPrice.setCellStyle(headCellStyle);
            if (sybusinessTotalDataList !=null && sybusinessTotalDataList.size()>0) {

                for (BusinessTotalData businessTotalData : sybusinessTotalDataList) {
                    if (businessTotalData.getBidId().equals(businessDefine.getId())) {
                        syCountCell.setCellValue(businessTotalData.getCount());
                        syHouseArea.setCellValue(businessTotalData.getHouseArea().doubleValue());
                        if (businessTotalData.getSumPrice()!=null) {
                            sySumPrice.setCellValue(businessTotalData.getSumPrice().doubleValue());
                        }else{
                            sySumPrice.setCellValue(0.0);
                        }
                        if (businessTotalData.getAssessmentPrice()!=null) {
                            syAssessmentPrice.setCellValue(businessTotalData.getAssessmentPrice().doubleValue());
                        }else{
                            syAssessmentPrice.setCellValue(0.0);
                        }

                        break;
                    }else{
                        syCountCell.setCellValue(0);
                        syHouseArea.setCellValue(0.0);
                        sySumPrice.setCellValue(0.0);
                        syAssessmentPrice.setCellValue(0.0);
                    }

                }
            }else{
                syCountCell.setCellValue(0);
                syHouseArea.setCellValue(0.0);
                sySumPrice.setCellValue(0.0);
                syAssessmentPrice.setCellValue(0.0);
            }


            Cell bgCountCell =row.createCell(2+cellIndex);
            bgCountCell.setCellStyle(headCellStyle);
            Cell bgHouseArea = row.createCell(7+cellIndex);
            bgHouseArea.setCellStyle(headCellStyle);
            Cell bgSumPrice = row.createCell(12+cellIndex);
            bgSumPrice.setCellStyle(headCellStyle);
            Cell bgAssessmentPrice = row.createCell(17+cellIndex);
            bgAssessmentPrice.setCellStyle(headCellStyle);
            if (bgbusinessTotalDataList !=null && bgbusinessTotalDataList.size()>0) {

                for (BusinessTotalData businessTotalData : bgbusinessTotalDataList) {
                    if (businessTotalData.getBidId().equals(businessDefine.getId())) {
                        bgCountCell.setCellValue(businessTotalData.getCount());
                        bgHouseArea.setCellValue(businessTotalData.getHouseArea().doubleValue());
                        if (businessTotalData.getSumPrice()!=null) {
                            bgSumPrice.setCellValue(businessTotalData.getSumPrice().doubleValue());
                        }else{
                            bgSumPrice.setCellValue(0.0);
                        }
                        if (businessTotalData.getAssessmentPrice()!=null) {
                            bgAssessmentPrice.setCellValue(businessTotalData.getAssessmentPrice().doubleValue());
                        }else{
                            bgAssessmentPrice.setCellValue(0.0);
                        }

                        break;
                    }else{
                        bgCountCell.setCellValue(0);
                        bgHouseArea.setCellValue(0.0);
                        bgSumPrice.setCellValue(0.0);
                        bgAssessmentPrice.setCellValue(0.0);
                    }

                }
            }else{
                bgCountCell.setCellValue(0);
                bgHouseArea.setCellValue(0.0);
                bgSumPrice.setCellValue(0.0);
                bgAssessmentPrice.setCellValue(0.0);
            }


            Cell gyCountCell =row.createCell(3+cellIndex);
            gyCountCell.setCellStyle(headCellStyle);
            Cell gyHouseArea = row.createCell(8+cellIndex);
            gyHouseArea.setCellStyle(headCellStyle);
            Cell gySumPrice = row.createCell(13+cellIndex);
            gySumPrice.setCellStyle(headCellStyle);
            Cell gyAssessmentPrice = row.createCell(18+cellIndex);
            gyAssessmentPrice.setCellStyle(headCellStyle);
            if (gybusinessTotalDataList !=null && gybusinessTotalDataList.size()>0) {

                for (BusinessTotalData businessTotalData : gybusinessTotalDataList) {
                    if (businessTotalData.getBidId().equals(businessDefine.getId())) {
                        gyCountCell.setCellValue(businessTotalData.getCount());
                        gyHouseArea.setCellValue(businessTotalData.getHouseArea().doubleValue());
                        if (businessTotalData.getSumPrice()!=null) {
                            gySumPrice.setCellValue(businessTotalData.getSumPrice().doubleValue());
                        }else{
                            gySumPrice.setCellValue(0.0);
                        }
                        if (businessTotalData.getAssessmentPrice()!=null) {
                            gyAssessmentPrice.setCellValue(businessTotalData.getAssessmentPrice().doubleValue());
                        }else{
                            gyAssessmentPrice.setCellValue(0.0);
                        }

                        break;
                    }else{
                        gyCountCell.setCellValue(0);
                        gyHouseArea.setCellValue(0.0);
                        gySumPrice.setCellValue(0.0);
                        gyAssessmentPrice.setCellValue(0.0);
                    }

                }
            }else{
                gyCountCell.setCellValue(0);
                gyHouseArea.setCellValue(0.0);
                gySumPrice.setCellValue(0.0);
                gyAssessmentPrice.setCellValue(0.0);
            }



            Cell qtCountCell =row.createCell(4+cellIndex);
            qtCountCell.setCellStyle(headCellStyle);
            Cell qtHouseArea = row.createCell(9+cellIndex);
            qtHouseArea.setCellStyle(headCellStyle);
            Cell qtSumPrice = row.createCell(14+cellIndex);
            qtSumPrice.setCellStyle(headCellStyle);
            Cell qtAssessmentPrice = row.createCell(19+cellIndex);
            qtAssessmentPrice.setCellStyle(headCellStyle);
            if (qtbusinessTotalDataList !=null && qtbusinessTotalDataList.size()>0) {

                for (BusinessTotalData businessTotalData : qtbusinessTotalDataList) {
                    if (businessTotalData.getBidId().equals(businessDefine.getId())) {
                        qtCountCell.setCellValue(businessTotalData.getCount());
                        qtHouseArea.setCellValue(businessTotalData.getHouseArea().doubleValue());
                        if (businessTotalData.getSumPrice()!=null) {
                            qtSumPrice.setCellValue(businessTotalData.getSumPrice().doubleValue());
                        }else{
                            qtSumPrice.setCellValue(0.0);
                        }
                        if (businessTotalData.getAssessmentPrice()!=null) {
                            qtAssessmentPrice.setCellValue(businessTotalData.getAssessmentPrice().doubleValue());
                        }else{
                            qtAssessmentPrice.setCellValue(0.0);
                        }

                        break;
                    }else{
                        qtCountCell.setCellValue(0);
                        qtHouseArea.setCellValue(0.0);
                        qtSumPrice.setCellValue(0.0);
                        qtAssessmentPrice.setCellValue(0.0);
                    }

                }
            }else{
                qtCountCell.setCellValue(0);
                qtHouseArea.setCellValue(0.0);
                qtSumPrice.setCellValue(0.0);
                qtAssessmentPrice.setCellValue(0.0);
            }

            hjrowIndex=rowIndex;
        }
        cellIndex=0;
        Row hjrow = sheet.createRow(hjrowIndex);
        Cell cell = hjrow.createCell(cellIndex);
        cell.setCellValue("合计");
        cell.setCellStyle(headCellStyle);

        Cell zcCountCell =hjrow.createCell(1+cellIndex,Cell.CELL_TYPE_FORMULA);
        zcCountCell.setCellFormula("SUM(" + CellReference.convertNumToColString(1+cellIndex) + "3:" + CellReference.convertNumToColString(1+cellIndex)+hjrowIndex+")");
        zcCountCell.setCellStyle(headCellStyle);

        Cell zcHouseArea = hjrow.createCell(6+cellIndex,Cell.CELL_TYPE_FORMULA);
        zcHouseArea.setCellFormula("SUM(" + CellReference.convertNumToColString(6+cellIndex) + "3:" + CellReference.convertNumToColString(6+cellIndex)+hjrowIndex+")");
        zcHouseArea.setCellStyle(headCellStyle);


        Cell zcSumPrice = hjrow.createCell(11+cellIndex,Cell.CELL_TYPE_FORMULA);
        zcSumPrice.setCellFormula("SUM(" + CellReference.convertNumToColString(11+cellIndex) + "3:" + CellReference.convertNumToColString(11+cellIndex)+hjrowIndex+")");
        zcSumPrice.setCellStyle(headCellStyle);

        Cell zcAssessmentPrice = hjrow.createCell(16+cellIndex,Cell.CELL_TYPE_FORMULA);
        zcAssessmentPrice.setCellFormula("SUM(" + CellReference.convertNumToColString(16+cellIndex) + "3:" + CellReference.convertNumToColString(16+cellIndex)+hjrowIndex+")");
        zcAssessmentPrice.setCellStyle(headCellStyle);



        Cell syCountCell =hjrow.createCell(2+cellIndex,Cell.CELL_TYPE_FORMULA);
        syCountCell.setCellFormula("SUM(" + CellReference.convertNumToColString(2+cellIndex) + "3:" + CellReference.convertNumToColString(2+cellIndex)+hjrowIndex+")");
        syCountCell.setCellStyle(headCellStyle);

        Cell syHouseArea = hjrow.createCell(7+cellIndex,Cell.CELL_TYPE_FORMULA);
        syHouseArea.setCellFormula("SUM(" + CellReference.convertNumToColString(7+cellIndex) + "3:" + CellReference.convertNumToColString(7+cellIndex)+hjrowIndex+")");
        syHouseArea.setCellStyle(headCellStyle);

        Cell sySumPrice = hjrow.createCell(12+cellIndex,Cell.CELL_TYPE_FORMULA);
        sySumPrice.setCellFormula("SUM(" + CellReference.convertNumToColString(12+cellIndex) + "3:" + CellReference.convertNumToColString(12+cellIndex)+hjrowIndex+")");
        sySumPrice.setCellStyle(headCellStyle);

        Cell syAssessmentPrice = hjrow.createCell(17+cellIndex,Cell.CELL_TYPE_FORMULA);
        syAssessmentPrice.setCellFormula("SUM(" + CellReference.convertNumToColString(17+cellIndex) + "3:" + CellReference.convertNumToColString(17+cellIndex)+hjrowIndex+")");
        syAssessmentPrice.setCellStyle(headCellStyle);

        Cell bgCountCell =hjrow.createCell(3+cellIndex,Cell.CELL_TYPE_FORMULA);
        bgCountCell.setCellFormula("SUM(" + CellReference.convertNumToColString(3+cellIndex) + "3:" + CellReference.convertNumToColString(3+cellIndex)+hjrowIndex+")");
        bgCountCell.setCellStyle(headCellStyle);

        Cell bgHouseArea = hjrow.createCell(8+cellIndex,Cell.CELL_TYPE_FORMULA);
        bgHouseArea.setCellFormula("SUM(" + CellReference.convertNumToColString(8+cellIndex) + "3:" + CellReference.convertNumToColString(8+cellIndex)+hjrowIndex+")");
        bgHouseArea.setCellStyle(headCellStyle);

        Cell bgSumPrice = hjrow.createCell(13+cellIndex,Cell.CELL_TYPE_FORMULA);
        bgSumPrice.setCellFormula("SUM(" + CellReference.convertNumToColString(13+cellIndex) + "3:" + CellReference.convertNumToColString(13+cellIndex)+hjrowIndex+")");
        bgSumPrice.setCellStyle(headCellStyle);

        Cell bgAssessmentPrice = hjrow.createCell(18+cellIndex,Cell.CELL_TYPE_FORMULA);
        bgAssessmentPrice.setCellFormula("SUM(" + CellReference.convertNumToColString(18+cellIndex) + "3:" + CellReference.convertNumToColString(18+cellIndex)+hjrowIndex+")");
        bgAssessmentPrice.setCellStyle(headCellStyle);



        Cell gyCountCell =hjrow.createCell(4+cellIndex);
        gyCountCell.setCellFormula("SUM(" + CellReference.convertNumToColString(4+cellIndex) + "3:" + CellReference.convertNumToColString(4+cellIndex)+hjrowIndex+")");
        gyCountCell.setCellStyle(headCellStyle);

        Cell gyHouseArea = hjrow.createCell(9+cellIndex);
        gyHouseArea.setCellFormula("SUM(" + CellReference.convertNumToColString(9+cellIndex) + "3:" + CellReference.convertNumToColString(9+cellIndex)+hjrowIndex+")");
        gyHouseArea.setCellStyle(headCellStyle);

        Cell gySumPrice = hjrow.createCell(14+cellIndex);
        gySumPrice.setCellFormula("SUM(" + CellReference.convertNumToColString(14+cellIndex) + "3:" + CellReference.convertNumToColString(14+cellIndex)+hjrowIndex+")");
        gySumPrice.setCellStyle(headCellStyle);

        Cell gyAssessmentPrice = hjrow.createCell(19+cellIndex);
        gyAssessmentPrice.setCellFormula("SUM(" + CellReference.convertNumToColString(19+cellIndex) + "3:" + CellReference.convertNumToColString(19+cellIndex)+hjrowIndex+")");
        gyAssessmentPrice.setCellStyle(headCellStyle);



        Cell qtCountCell =hjrow.createCell(5+cellIndex);
        qtCountCell.setCellFormula("SUM(" + CellReference.convertNumToColString(5+cellIndex) + "3:" + CellReference.convertNumToColString(5+cellIndex)+hjrowIndex+")");
        qtCountCell.setCellStyle(headCellStyle);

        Cell qtHouseArea = hjrow.createCell(10+cellIndex);
        qtHouseArea.setCellFormula("SUM(" + CellReference.convertNumToColString(10+cellIndex) + "3:" + CellReference.convertNumToColString(10+cellIndex)+hjrowIndex+")");
        qtHouseArea.setCellStyle(headCellStyle);

        Cell qtSumPrice = hjrow.createCell(15+cellIndex);
        qtSumPrice.setCellFormula("SUM(" + CellReference.convertNumToColString(15+cellIndex) + "3:" + CellReference.convertNumToColString(15+cellIndex)+hjrowIndex+")");
        qtSumPrice.setCellStyle(headCellStyle);

        Cell qtAssessmentPrice = hjrow.createCell(20+cellIndex);
        qtAssessmentPrice.setCellFormula("SUM(" + CellReference.convertNumToColString(20+cellIndex) + "3:" + CellReference.convertNumToColString(20+cellIndex)+hjrowIndex+")");
        qtAssessmentPrice.setCellStyle(headCellStyle);




        sheet.setForceFormulaRecalculation(true);
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Content-Disposition", "attachment;filename=exportBusinessInfo.xlsx");
        try {
            workbook.write(externalContext.getResponseOutputStream());
            facesContext.responseComplete();
        } catch (IOException e) {
            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "ExportIOError");
            Logging.getLog(getClass()).error("export error", e);
        }




    }



}
