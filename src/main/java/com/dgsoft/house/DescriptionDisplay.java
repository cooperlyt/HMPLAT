package com.dgsoft.house;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 10/11/2016.
 */
public class DescriptionDisplay {


    public enum DisplayStyle{
        NORMAL
    }

    private class DisplayData{

        public DisplayData() {
        }

        public DisplayData(DisplayStyle displayStyle, String value) {
            this.displayStyle = displayStyle;
            this.value = value;
        }

        private DisplayStyle displayStyle;
        private String value;

        public DisplayStyle getDisplayStyle() {
            return displayStyle;
        }

        public void setDisplayStyle(DisplayStyle displayStyle) {
            this.displayStyle = displayStyle;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    private class DataLine{

        public DataLine() {
        }

        public DataLine(DisplayStyle displayStyle, List<DisplayData> displayDatas) {
            this.displayStyle = displayStyle;
            this.displayDatas = displayDatas;
        }

        private DisplayStyle displayStyle;
        private List<DisplayData> displayDatas = new ArrayList<DisplayData>();

        public DisplayStyle getDisplayStyle() {
            return displayStyle;
        }

        public void setDisplayStyle(DisplayStyle displayStyle) {
            this.displayStyle = displayStyle;
        }

        public List<DisplayData> getDisplayDatas() {
            return displayDatas;
        }

        public void setDisplayDatas(List<DisplayData> displayDatas) {
            this.displayDatas = displayDatas;
        }
    }

    private List<DataLine> dataLines = new ArrayList<DataLine>();

    public List<DataLine> getDataLines() {
        return dataLines;
    }

    public void setDataLines(List<DataLine> dataLines) {
        this.dataLines = dataLines;
    }
}
