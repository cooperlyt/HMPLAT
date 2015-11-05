package com.dgsoft.house.action;

import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.model.Word;
import com.dgsoft.house.model.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Logging;
import org.richfaces.event.DropEvent;
import org.richfaces.event.DropListener;
import org.richfaces.event.FileUploadEvent;


import java.math.BigDecimal;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 8/2/13
 * Time: 3:06 PM
 */
@Name("buildGridMapHome")
@Scope(ScopeType.CONVERSATION)
public class BuildGridMapHome implements DropListener {

    @In
    private BuildHome buildHome;

    @In
    private BuildGridMapHome buildGridMapHome;

    private BuildGridMap curBuildGridMap;

    private List<BuildGridMap> gridMaps;

    @In
    private FacesMessages facesMessages;

    public BuildGridMap getInstance(){
        return curBuildGridMap;
    }

    private boolean replaceGridMap;

    public void emptyIdle(){
        List<House> removeHouse = new ArrayList<House>();
        for(House house : idleHouses) {
            if (buildHome.getEntityManager().contains(house)) {
                house.setDeleted(true);
            } else {
                buildHome.getInstance().getHouses().remove(house);
                removeHouse.add(house);
            }
        }
        idleHouses.removeAll(removeHouse);

    }

    public String saveGridMap() {
        emptyIdle();

        for (House house : buildHome.getInstance().getHouses()) {
            if (!house.isValidator()) {
                if (!house.getOrderValid()) {
                    facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "HouseOrderConflictTemplate", house.getHouseOrder());
                }
                if (!house.getDetailsValid()) {
                    facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "HouseDetailsErrorTemplate", house.getHouseOrder());
                }
                return null;
            }
        }


        return buildHome.update();

    }




    public List<BuildGridMap> getBuildGridPages() {
        return gridMaps;
    }

    public boolean isReplaceGridMap() {
        return replaceGridMap;
    }

    public void setReplaceGridMap(boolean replaceGridMap) {
        this.replaceGridMap = replaceGridMap;
    }

    public void nextPage() {
        int nowPage = gridMaps.indexOf(buildGridMapHome.getInstance());
        if (nowPage < gridMaps.size()) {
            curBuildGridMap = gridMaps.get(nowPage + 1);
        }
    }

    public void lastPage() {

        curBuildGridMap = gridMaps.get(gridMaps.size() - 1);
    }

    public void firstPage() {
        curBuildGridMap = getBuildGridPages().get(0);

    }

    public void previousPage() {
        int nowPage = gridMaps.indexOf(getInstance());
        if (nowPage >= 1) {
            curBuildGridMap = gridMaps.get(nowPage - 1);
        }
    }

    private int gotoPage;


    public int getGotoPage() {
        return gotoPage;
    }

    public void setGotoPage(int gotoPage) {
        this.gotoPage = gotoPage;
    }

    public void toPage() {

        for (BuildGridMap buildGridMap : getBuildGridPages()) {
            if (buildGridMap.getOrder() == gotoPage) {
                curBuildGridMap = buildGridMap;
                break;
            }
        }
    }




    @Create
    public void create() {
        gridMaps = new ArrayList<BuildGridMap>(buildHome.getInstance().getBuildGridMaps()) ;

        Collections.sort(gridMaps, new Comparator<BuildGridMap>() {
            @Override
            public int compare(BuildGridMap o1, BuildGridMap o2) {
                return (new Integer(o1.getOrder())).compareTo(o2.getOrder());
            }
        });

        List<House> houses = new ArrayList<House>(buildHome.getInstance().getHouses());
        for (BuildGridMap map: gridMaps){

            for (GridRow row: map.getGridRows()){
                for (GridBlock block: row.getGridBlocks()){
                    for(House house: houses){
                        if (house.getHouseCode().equals(block.getHouseCode())){
                            if (!house.isDeleted()) {
                                block.setHouse(house);
                                houses.remove(house);
                            }
                            break;
                        }
                    }

                }
            }
        }
        idleHouses.clear();
        idleHouses.addAll(houses);


        if ((curBuildGridMap == null)) {
            if (!gridMaps.isEmpty()) {
                curBuildGridMap = gridMaps.get(0);
            }

        }
    }

    public void deleteToIdle() {
        for (GridRow gridRow : getInstance().getGridRows()) {
            for (GridBlock gridBlock : gridRow.getGridBlocks()) {
                if (gridBlock.getHouse() != null){
                    House house = (House)gridBlock.getHouse();
                    idleHouses.add(house);
                    gridBlock.setHouseCode(null);
                }

            }
        }

        buildHome.getInstance().getBuildGridMaps().remove(getInstance());
    }

    private void matchIdle(boolean changeInfo){

    }

    public void matchIdle() {
        for (GridRow gridRow : getInstance().getGridRows()) {
            for (GridBlock gridBlock : gridRow.getGridBlocks()) {
                if (gridBlock.getHouse() == null) {
                    for (House house : idleHouses) {
                        if (house.getHouseOrder() != null && gridBlock.getHouseOrder() != null && house.getHouseOrder().equals(gridBlock.getHouseOrder())) {

                            gridBlock.setHouse(house);
                            house.setDeleted(false);
                            idleHouses.remove(house);

                            break;
                        }
                    }
                }
            }
        }
    }

    public void assginHouseInfo(){
        for (GridRow gridRow : getInstance().getGridRows()) {
            for (GridBlock gridBlock : gridRow.getGridBlocks()) {
                if (gridBlock.getHouse() != null) {
                    ((House)gridBlock.getHouse()).assginInfo(gridBlock);
                }
            }
        }
    }


    public void templeteFileUploadListener(FileUploadEvent event) throws Exception {

        if (curBuildGridMap != null) {

            buildHome.getInstance().getBuildGridMaps().remove(curBuildGridMap);

        }
        SAXReader reader = new SAXReader();
        Document doc = reader.read(event.getUploadedFile().getInputStream());
        Element root = doc.getRootElement();
        Logging.getLog(getClass()).debug("replaceGridMap:" + replaceGridMap);
        int order;
        String gridMapName = "新建分户图";
        if (replaceGridMap) {
            order = getInstance().getOrder();
            gridMapName = getInstance().getName();
            deleteToIdle();
        } else {
            order = 0;
        }
        curBuildGridMap = new BuildGridMap();


        buildHome.getInstance().getBuildGridMaps().add(getInstance());
        getInstance().setBuild(buildHome.getInstance());
        if (!replaceGridMap) {

            for (BuildGridMap gridMap : getBuildGridPages()) {
                if (gridMap.getOrder() > order) {
                    order = gridMap.getOrder();
                }
            }
            order = order + 1;
            if (order != 1) {
                gridMapName = "新建分户图-" + order;
            }

        }
        getInstance().setOrder(order);
        getInstance().setName(gridMapName);

        analyzeTemplete(root);

        if (replaceGridMap || !idleHouses.isEmpty()) {
            matchIdle();
        } else {
            generateHouse();
        }

        Logging.getLog(getClass()).debug("upload comlete" );
    }

    private void analyzeTemplete(Element rootElement) {

        Iterator<Element> iterator = rootElement.element("HEAD").elementIterator();
        int i = 0;
        while (iterator.hasNext()) {
            Element unitElement = iterator.next();
            getInstance().getHouseGridTitles().add(
                    new HouseGridTitle(getInstance(), i,
                            unitElement.attributeValue("title", ""),
                            Integer.parseInt(unitElement.attributeValue("colSpan", "1"))));
            i++;
        }
        iterator = rootElement.element("BODY").elementIterator();
        i = 0;
        while (iterator.hasNext()) {
            Element floorElement = iterator.next();
            GridRow floor = new GridRow(getInstance(), floorElement.attributeValue("title", ""), i,
                    Integer.parseInt(floorElement.attributeValue("floor", "0")));
            Iterator<Element> houseIterator = floorElement.elementIterator();
            int j = 0;
            while (houseIterator.hasNext()) {
                Element houseElement = houseIterator.next();

                floor.getGridBlocks().add(new GridBlock(UUID.randomUUID().toString().replace("-", "").toUpperCase(), floor, j,
                        Integer.parseInt(houseElement.attributeValue("colSpan", "1")),
                        Integer.parseInt(houseElement.attributeValue("RowSpan", "1")),
                        Integer.parseInt(houseElement.attributeValue("UnitIndex", "1")),
                        houseElement.attributeValue("UnitName", ""),
                        new BigDecimal(houseElement.attributeValue("Area", "0")),
                        new BigDecimal(houseElement.attributeValue("UseArea", "0")),
                        new BigDecimal(houseElement.attributeValue("CommArea", "0")),
                        new BigDecimal(houseElement.attributeValue("ShineArea", "0")),
                        new BigDecimal(houseElement.attributeValue("LoftArea", "0")),
                        new BigDecimal(houseElement.attributeValue("CommParam", "0")),
                        houseElement.attributeValue("UseType", ""),
                        houseElement.attributeValue("Structure", ""),
                        houseElement.attributeValue("HouseType", ""),
                        Boolean.parseBoolean(houseElement.attributeValue("IsDeleted", "false")) ? null :houseElement.attributeValue("Order", ""),
                        houseElement.attributeValue("Direction", ""),
                        houseElement.attributeValue("EastWall", ""),
                        houseElement.attributeValue("WestWall", ""),
                        houseElement.attributeValue("SouthWall", ""),
                        houseElement.attributeValue("NorthWall", ""),
                        houseElement.attributeValue("KnotSize", ""),
                        Boolean.parseBoolean(houseElement.attributeValue("HalfDownRoom", "false")),
                        houseElement.attributeValue("InFloorName", "")
                ));

                j++;
            }

            getInstance().getGridRows().add(floor);
            i++;
        }
        //return result;
    }


    @DataModel("idleHouses")
    private List<House> idleHouses = new ArrayList<House>();

    @DataModelSelection
    private House selectIdleHouse;

    private House selectHouse;

    public void selectedIdleHouse() {
        selectHouse = selectIdleHouse;
    }

    public void generateHouse() {
        if (getInstance() == null){
            return;
        }
        for (GridRow row : getInstance().getGridRowList()) {
            for (GridBlock block : row.getGridBlockList()) {
                if (block.getHouse() == null && block.getHouseOrder() != null && !"".equals(block.getHouseOrder().trim())) {

                    House newHouse = new House(buildHome.genHouseOrder(), buildHome.getInstance(), block);
                    block.setHouseCode(newHouse.getHouseCode());
                    block.setHouse(newHouse);
                    buildHome.getInstance().getHouses().add(newHouse);
                }
            }
        }
    }


    public String getSelectBlockId() {
        if (selectBlock != null) {
            return selectBlock.getId();
        } else {
            return null;
        }
    }

    public void setSelectBlockId(String selectBlockId) {
        if ((selectBlockId == null) || (selectBlockId.trim().equals(""))) {
            selectBlock = null;
        } else {
            selectHouse = null;
            for (GridRow row : getInstance().getGridRowList()) {
                for (GridBlock block : row.getGridBlocks()) {
                    if (block.getId().equals(selectBlockId)) {
                        selectBlock = block;
                        selectHouse = (House)block.getHouse();
                        return;
                    }
                }
            }
        }
    }

    private GridBlock selectBlock;

    public GridBlock getSelectBlock() {
        return selectBlock;
    }

    public House getSelectHouse() {
        return selectHouse;
    }

    public void deleteBlockHouse() {
        deleteBlockHouse(getSelectBlock());
    }

    private void deleteBlockHouse(GridBlock block){
        if (block != null) {
            if (block.getHouse() != null) {
                House house = (House)block.getHouse();
                block.setHouse(null);
                house.setDeleted(true);
                idleHouses.add(house);
            }
        }
    }

    public boolean isSelectHouseManaged(){
        if (selectHouse == null)
            return false;
        return buildHome.getEntityManager().contains(selectHouse);
    }


    @Override
    public void processDrop(DropEvent dropEvent) {

        if (! (dropEvent.getDropValue() instanceof  GridBlock)) {//拖到分户图
            deleteBlockHouse((GridBlock) dropEvent.getDragValue());

        }else{//拖到回收站

            GridBlock targetBlock = (GridBlock) dropEvent.getDropValue();
            if (dropEvent.getDragValue() instanceof GridBlock) {

                House tempHouse = (House) targetBlock.getHouse();
                targetBlock.setHouse(((GridBlock) dropEvent.getDragValue()).getHouse());
                ((GridBlock) dropEvent.getDragValue()).setHouse(tempHouse);
                targetBlock.setHouseCode(tempHouse.getHouseCode());
            } else if (dropEvent.getDragValue() instanceof House) {
                House tempHouse = (House) dropEvent.getDragValue();
                if (targetBlock.getHouse() != null) {
                    House house = (House) targetBlock.getHouse();
                    house.setDeleted(true);
                    idleHouses.add(house);
                    targetBlock.setHouseCode(null);
                }
                targetBlock.setHouse(tempHouse);
                tempHouse.setDeleted(false);
                idleHouses.remove(tempHouse);

            } else {
                House newHouse = new House(buildHome.genHouseOrder(), buildHome.getInstance(), targetBlock);
                targetBlock.setHouse(newHouse);
                buildHome.getInstance().getHouses().add(newHouse);
            }

        }
    }


    public BigDecimal getTotalHouseArea() {
        BigDecimal result = BigDecimal.ZERO;
        for (GridRow row : getInstance().getGridRows()) {
            for (GridBlock block : row.getGridBlocks())
                if (block.getHouse() != null && block.getHouse().getHouseArea() != null)
                    result = result.add(block.getHouse().getHouseArea());
        }
        return result;
    }

    public BigDecimal getTotalHouseUseArea() {
        BigDecimal result = BigDecimal.ZERO;
        for (GridRow row : getInstance().getGridRows()) {
            for (GridBlock block : row.getGridBlocks())
                if (block.getHouse() != null && block.getHouse().getUseArea() != null)
                    result = result.add(block.getHouse().getUseArea());
        }
        return result;
    }

    public int getHouseCount() {
        int result = 0;
        for (GridRow row : getInstance().getGridRows()) {
            for (GridBlock block : row.getGridBlocks())
                if (block.getHouse() != null) {
                    result++;
                }
        }
        return result;
    }

    public Map<Word, BuildHome.CountAreaEntry> getUseTypeTotalMap() {
        Map<Word, BuildHome.CountAreaEntry> result = new HashMap<Word, BuildHome.CountAreaEntry>();
        for (GridRow row : getInstance().getGridRows()) {
            for (GridBlock block : row.getGridBlocks())
                if (block.getHouse() != null) {
                    BuildHome.CountAreaEntry entry = result.get(DictionaryWord.instance().getWord(block.getHouse().getUseType()));
                    if (entry != null) {
                        entry.addArea(block.getHouse().getHouseArea(), block.getHouse().getUseArea());
                    } else {
                        result.put(DictionaryWord.instance().getWord(block.getHouse().getUseType()),
                                new BuildHome.CountAreaEntry(block.getHouse().getHouseArea(), block.getHouse().getUseArea()));
                    }
                }
        }
        return result;
    }

    public List<Map.Entry<Word, BuildHome.CountAreaEntry>> getUseTypeTotalList() {
        List<Map.Entry<Word, BuildHome.CountAreaEntry>> result = new ArrayList<Map.Entry<Word, BuildHome.CountAreaEntry>>(getUseTypeTotalMap().entrySet());
        Collections.sort(result, new Comparator<Map.Entry<Word, BuildHome.CountAreaEntry>>() {
            @Override
            public int compare(Map.Entry<Word, BuildHome.CountAreaEntry> o1, Map.Entry<Word, BuildHome.CountAreaEntry> o2) {
                if((o1.getKey() != null) && (o2.getKey() != null)) {
                    return new Integer(o1.getKey().getPriority()).compareTo(o2.getKey().getPriority());
                }else {
                    if (o1.getKey() == null){
                        return -1;
                    }else{
                        return 1;
                    }
                }
            }
        });
        return result;
    }

    private void removeThisPage() {
        int oldOrder = getInstance().getOrder();
        buildHome.getInstance().getBuildGridMaps().remove(getInstance());
        gridMaps.remove(getInstance());
        List<BuildGridMap> gms = getBuildGridPages();

        if (gms.isEmpty()) {
            curBuildGridMap = null;
        } else {
            for (BuildGridMap gm : gms) {
                gm.setOrder(gms.indexOf(gm) + 1);
            }
            while (gms.size() < oldOrder) {
                oldOrder--;
            }

            curBuildGridMap = gms.get(oldOrder - 1);

        }


    }

    public void deleteGridMapIdleHouse() {
        for (GridRow row : getInstance().getGridRowList()) {
            for (GridBlock block : row.getGridBlockList()) {
                if (block.getHouse() != null) {
                    House house = (House) block.getHouse();
                    house.setDeleted(true);
                    idleHouses.add(house);
                    block.setHouse(null);
                }
            }
        }
        removeThisPage();
    }

}
