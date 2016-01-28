package com.dgsoft.house.owner.total.data;

/**
 * Created by cooper on 1/28/16.
 */
public class InitCardTotalData {

    private String developerName;

    private String sectionName;

    private Long cardCount;

    public InitCardTotalData(String developerName, String sectionName, Long cardCount) {
        this.developerName = developerName;
        this.sectionName = sectionName;
        this.cardCount = cardCount;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public Long getCardCount() {
        return cardCount;
    }
}
