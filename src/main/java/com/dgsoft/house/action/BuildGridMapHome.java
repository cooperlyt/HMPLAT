package com.dgsoft.house.action;

import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.model.Word;
import com.dgsoft.house.HouseEditStrategy;
import com.dgsoft.house.HouseEntityHome;
import com.dgsoft.house.model.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
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
public class BuildGridMapHome extends HouseEntityHome<BuildGridMap> implements DropListener {

    @In
    private BuildHome buildHome;

    @In
    private FacesMessages facesMessages;

    private boolean replaceGridMap;

    public String saveGridMap() {
        String result = "updated";
        for (House house : buildHome.getInstance().getHouses()) {
            if (!house.isValidator()) {
                result = null;
                for (String msg : house.getValidMessages()) {
                    facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, msg, house.getHouseOrder());
                }
            }
        }
        if (result != null) {
            return buildHome.update();
        } else {
            return result;
        }
    }


    public boolean isReplaceGridMap() {
        return replaceGridMap;
    }

    public void setReplaceGridMap(boolean replaceGridMap) {
        this.replaceGridMap = replaceGridMap;
    }

    public void nextPage() {
        List<BuildGridMap> gridMaps = buildHome.getBuildGridPages();
        int nowPage = gridMaps.indexOf(getInstance());
        if (nowPage < gridMaps.size()) {
            clearInstance();
            setInstance(gridMaps.get(nowPage + 1));
        }
    }

    public void lastPage() {
        List<BuildGridMap> gridMaps = buildHome.getBuildGridPages();
        clearInstance();
        setInstance(gridMaps.get(gridMaps.size() - 1));
    }

    public void firstPage() {
        clearInstance();
        setInstance(buildHome.getBuildGridPages().get(0));
    }

    public void previousPage() {
        List<BuildGridMap> gridMaps = buildHome.getBuildGridPages();
        int nowPage = gridMaps.indexOf(getInstance());
        if (nowPage >= 1) {
            clearInstance();
            setInstance(gridMaps.get(nowPage - 1));
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

        for (BuildGridMap buildGridMap : buildHome.getBuildGridPages()) {
            if (buildGridMap.getOrder() == gotoPage) {
                clearInstance();
                setInstance(buildGridMap);
                break;
            }
        }
    }

    private void initIdleHouse() {
        List<House> result = new ArrayList<House>();
        for (BuildGridMap gridMap : buildHome.getInstance().getBuildGridMaps()) {
            for (GridRow gridRow : gridMap.getGridRows()) {
                for (GridBlock gridBlock : gridRow.getGridBlocks()) {
                    if (gridBlock.getHouse() != null)
                        result.add(gridBlock.getHouse());
                }
            }
        }
        idleHouses.clear();
        idleHouses.addAll(buildHome.getInstance().getHouses());
        idleHouses.removeAll(result);
    }


    @Override
    public void create() {
        super.create();
        List<BuildGridMap> gridMaps = buildHome.getBuildGridPages();
        if (!isIdDefined() && buildHome.isIdDefined() && !gridMaps.isEmpty()) {
            setId(gridMaps.get(0).getId());
            initIdleHouse();
        }
    }

    public void deleteToIdle() {
        for (GridRow gridRow : getInstance().getGridRows()) {
            for (GridBlock gridBlock : gridRow.getGridBlocks()) {
                if (gridBlock.getHouse() != null)
                    idleHouses.add(gridBlock.getHouse());
            }
        }

        buildHome.getInstance().getBuildGridMaps().remove(getInstance());
    }

    public void matchIdle() {
        for (GridRow gridRow : getInstance().getGridRows()) {
            for (GridBlock gridBlock : gridRow.getGridBlocks()) {
                if (gridBlock.getHouse() == null) {
                    for (House house : idleHouses) {
                        if (house.getHouseOrder().equals(gridBlock.getHouseOrder())) {
                            gridBlock.setHouse(house);
                            idleHouses.remove(house);
                            break;
                        }
                    }
                }
            }
        }
    }


    public void templeteFileUploadListener(FileUploadEvent event) throws Exception {

        if (isManaged()) {
            getEntityManager().remove(getInstance());
        }
        SAXReader reader = new SAXReader();
        Document doc = reader.read(event.getUploadedFile().getInputStream());
        Element root = doc.getRootElement();
        Logging.getLog(getClass()).debug("replaceGridMap:" + replaceGridMap);
        int order;
        String gridMapName = buildHome.getInstance().getName();
        if (replaceGridMap) {
            order = getInstance().getOrder();
            gridMapName = getInstance().getName();
            deleteToIdle();
        } else {
            order = 0;
        }
        clearInstance();


        buildHome.getInstance().getBuildGridMaps().add(getInstance());
        getInstance().setBuild(buildHome.getInstance());
        if (!replaceGridMap) {

            for (BuildGridMap gridMap : buildHome.getBuildGridPages()) {
                if (gridMap.getOrder() > order) {
                    order = gridMap.getOrder();
                }
            }
            order = order + 1;
            if (order != 1) {
                gridMapName = gridMapName + "-" + order;
            }

        }
        getInstance().setOrder(order);
        getInstance().setName(gridMapName);

        analyzeTemplete(root);

        if (!idleHouses.isEmpty()) {
            matchIdle();
        } else {
            generateHouse();
        }
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
                        houseElement.attributeValue("Order", ""),
                        houseElement.attributeValue("Direction", ""),
                        houseElement.attributeValue("EastWall", ""),
                        houseElement.attributeValue("WestWall", ""),
                        houseElement.attributeValue("SouthWall", ""),
                        houseElement.attributeValue("NorthWall", ""),
                        houseElement.attributeValue("KnotSize", ""),
                        Boolean.parseBoolean(houseElement.attributeValue("HalfDownRoom", "false"))
                ));

                j++;
            }

            getInstance().getGridRows().add(floor);
            i++;
        }
        //return result;
    }


    public List<Integer> getColList() {
        List<Integer> result = new ArrayList<Integer>();
        for (HouseGridTitle title : getInstance().getHouseGridTitles()) {
            for (int i = 0; i < title.getColspan(); i++) {
                result.add(i);
            }
        }
        return result;
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
        for (GridRow row : getInstance().getGridRowList()) {
            for (GridBlock block : row.getGridBlockList()) {
                if (block.getHouse() == null) {
                    House newHouse = new House(buildHome.genHouseOrder(),buildHome.getInstance(), block);
                    block.setHouse(newHouse);
                    buildHome.getHouses().add(newHouse);
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
                        selectHouse = block.getHouse();
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

    private HouseEditStrategy getHouseEditStrategy() {
        return (HouseEditStrategy) Component.getInstance(RunParam.instance().getStringParamValue(HouseEditStrategy.RUN_PARAM_NAME), true, true);
    }

    public boolean isHouseCanEdit() {
        return (selectHouse != null) && getHouseEditStrategy().isCanEdit(selectHouse);
    }

    public void idleBlockHouse() {
        GridBlock block = getSelectBlock();
        if (block != null) {
            if (block.getHouse() != null) {
                idleHouses.add(block.getHouse());
                block.getHouse().getGridBlock().clear();
                block.setHouse(null);

            }
        }
    }

    private boolean deleteHouse(House house) {
        if (isHouseCanEdit()) {
            house.getGridBlock().clear();
            buildHome.getHouses().remove(house);
            return true;
        }
        facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "HouseCantEdit");
        return false;
    }

    public void deleteBlockHouse() {
        GridBlock block = getSelectBlock();
        if (block != null) {
            if (block.getHouse() != null) {
                if (deleteHouse(block.getHouse())) {
                    block.setHouse(null);
                }
            }
        }
    }

    public void deleteIdleHouse() {
        if (isHouseCanEdit()) {
            if (deleteHouse(selectIdleHouse)) {
                idleHouses.remove(selectIdleHouse);
            }
        }
    }

    @Override
    public void processDrop(DropEvent dropEvent) {
        log.debug("processDrop");

        GridBlock targetBlock = (GridBlock) dropEvent.getDropValue();
        if (dropEvent.getDragValue() instanceof GridBlock) {

            House tempHouse = targetBlock.getHouse();
            targetBlock.setHouse(((GridBlock) dropEvent.getDragValue()).getHouse());
            ((GridBlock) dropEvent.getDragValue()).setHouse(tempHouse);
        } else if (dropEvent.getDragValue() instanceof House) {
            House tempHouse = (House) dropEvent.getDragValue();
            if (targetBlock.getHouse() != null) {
                idleHouses.add(targetBlock.getHouse());
            }
            targetBlock.setHouse(tempHouse);
            idleHouses.remove(tempHouse);
        } else {
            House newHouse = new House(buildHome.genHouseOrder(),buildHome.getInstance(), targetBlock);
            targetBlock.setHouse(newHouse);
            buildHome.getHouses().add(newHouse);
        }
    }


    public BigDecimal getTotalHouseArea() {
        BigDecimal result = BigDecimal.ZERO;
        for (GridRow row : getInstance().getGridRows()) {
            for (GridBlock block : row.getGridBlocks())
                if (block.getHouse() != null)
                    result = result.add(block.getHouse().getHouseArea());
        }
        return result;
    }

    public BigDecimal getTotalHouseUseArea() {
        BigDecimal result = BigDecimal.ZERO;
        for (GridRow row : getInstance().getGridRows()) {
            for (GridBlock block : row.getGridBlocks())
                if (block.getHouse() != null)
                    result = result.add(block.getHouse().getUseArea());
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
                return new Integer(o1.getKey().getPriority()).compareTo(o2.getKey().getPriority());
            }
        });
        return result;
    }

    private void removeThisPage(){
        int oldOrder = getInstance().getOrder();
        buildHome.getInstance().getBuildGridMaps().remove(getInstance());
        List<BuildGridMap> gms = buildHome.getBuildGridPages();

        if (gms.isEmpty()){
            clearInstance();
        }else {
            for (BuildGridMap gm : gms) {
                gm.setOrder(gms.indexOf(gm) + 1);
            }
            if (gms.size() >= oldOrder){
                setInstance(gms.get(gms.size() - 1));
            }else{
                setInstance(gms.get(oldOrder - 1));
            }
        }


    }

    public void deleteGridMapAndHouse() {
        for (GridRow row : getInstance().getGridRowList()) {
            for (GridBlock block : row.getGridBlockList()) {
                if ((block.getHouse() != null)) {
                    block.getHouse().getGridBlock().clear();
                    if (getHouseEditStrategy().isCanEdit(block.getHouse())) {
                        buildHome.getHouses().remove(block.getHouse());
                    }else{
                        idleHouses.add(block.getHouse());
                        facesMessages.addFromResourceBundle(StatusMessage.Severity.WARN,"HouseCantDeleteMoveToIdle",block.getHouse().getHouseOrder());
                    }
                    block.setHouse(null);
                }
            }
        }
        removeThisPage();
    }

    public void deleteGridMapIdleHouse() {
        for (GridRow row : getInstance().getGridRowList()) {
            for (GridBlock block : row.getGridBlockList()) {
                if (block.getHouse() != null) {
                    block.getHouse().getGridBlock().clear();
                    idleHouses.add(block.getHouse());
                    block.setHouse(null);
                }
            }
        }
        removeThisPage();
    }

}
