package com.dgsoft.house;

import com.dgsoft.common.Entry;
import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.model.Word;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.contexts.Contexts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cooper on 8/16/15.
 */
@Name("useTypeWordAdapter")
public class UseTypeWordAdapter {

    private static final String CATEGORY_ID = "house.useType";
    private static final String DWELLING_KEY= "1";//住宅
    private static final String SHOP_HOUSE_KEY = "2";//商业 网点
    private static final String STORE_HOUSE="4";//工业仓储
    private static final String OFFICE_KEY = "3";//办公
    private static final String OHTER_KEY = "0";//其它

    @Factory(scope = ScopeType.SESSION)
    public List<Entry<String,String>> getUseTypeCategoryList(){
        List<Entry<String,String>> result = new ArrayList<Entry<String, String>>(5);
        result.add(new Entry<String, String>(DWELLING_KEY,"住宅"));
        result.add(new Entry<String, String>(SHOP_HOUSE_KEY,"商业"));
        result.add(new Entry<String, String>(STORE_HOUSE,"工业仓储"));
        result.add(new Entry<String, String>(OFFICE_KEY,"办公"));
        result.add(new Entry<String, String>(OHTER_KEY,"其它"));
        return result;
    }

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
