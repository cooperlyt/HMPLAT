package com.dgsoft.house;

import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.model.Word;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.contexts.Contexts;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cooper on 8/16/15.
 */
@Name("useTypeWordAdapter")
public class UseTypeWordAdapter {

    private static final String CATEGORY_ID = "house.useType";
    private static final String DWELLING_KEY= "1";
    private static final String SHOP_HOUSE_KEY = "2";

    @In
    private DictionaryWord dictionary;


    public Set<String> getDwellingTypes(){
        Set<String> result = new HashSet<String>();
        for(Word word: dictionary.getWordList(CATEGORY_ID)){
            if (DWELLING_KEY.equals(word.getKey())){
                result.add(word.getId());
            }
        }
        return result;
    }

    public Set<String> getShopHouseTypes(){
        Set<String> result = new HashSet<String>();
        for(Word word: dictionary.getWordList(CATEGORY_ID)){
            if (SHOP_HOUSE_KEY.equals(word.getKey())){
                result.add(word.getId());
            }
        }
        return result;
    }

    public Set<String> getUnDwellingTypes(){
        Set<String> result = new HashSet<String>();
        for(Word word: dictionary.getWordList(CATEGORY_ID)){
            if (!DWELLING_KEY.equals(word.getKey())){
                result.add(word.getId());
            }
        }
        return result;
    }

    public UseType getUseType(String id){
        return new UseType(dictionary.getWord(id));
    }





    public static class UseType{

        public UseType(Word word) {
            this.word = word;
        }

        private Word word;

        public boolean isDwelling(){
            return DWELLING_KEY.equals(word.getKey());
        }

        public boolean isShopHouse(){
            return SHOP_HOUSE_KEY.equals(word.getKey());
        }

    }

    public static UseTypeWordAdapter instance()
    {
        if ( !Contexts.isEventContextActive() )
        {
            throw new IllegalStateException("no active event context");
        }
        return (UseTypeWordAdapter) Component.getInstance(UseTypeWordAdapter.class, true);
    }

}
