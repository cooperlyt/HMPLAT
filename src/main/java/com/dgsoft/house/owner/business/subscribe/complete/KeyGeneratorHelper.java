package com.dgsoft.house.owner.business.subscribe.complete;

/**
 * Created by cooper on 20/11/2016.
 */
public class KeyGeneratorHelper {

    private String key;

    public KeyGeneratorHelper() {
        key = "";
    }

    public KeyGeneratorHelper(String key) {
        this.key = key;
        if (key == null){
            this.key = "";
        }
    }

    private String genSearchWord(String word){
        if (word != null) {
            return "[" + word + "]";
        }else {
            return "";
        }
    }

    public boolean addWord(String word){
        String searchKey = genSearchWord(word);
        if (key.contains(searchKey)){
            return false;
        }else{
            key += searchKey;
        }
        return true;
    }

    public String getKey() {
        return key;
    }
}
