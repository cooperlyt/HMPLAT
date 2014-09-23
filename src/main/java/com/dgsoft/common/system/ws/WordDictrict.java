package com.dgsoft.common.system.ws;

import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.model.Word;
import com.dgsoft.common.system.model.WordCategory;
import org.jboss.seam.Component;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.jws.WebMethod;
import javax.jws.WebService;
/**
 * Created by cooper on 9/22/14.
 */

@WebService
public class WordDictrict {

    @WebMethod
    public String getAllDictrict() {
        DictionaryWord dictionary = (DictionaryWord) Component.getInstance(DictionaryWord.class, true);
        dictionary.loadWord();
        JSONObject categoryOjb = new JSONObject();
        try {

            for (WordCategory category : dictionary.getAllWordCategory().values()) {

                JSONArray jsonArray = new JSONArray();

                for (Word word : dictionary.getWordList(category.getId())) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("ID", word.getId());
                    jsonObject.put("VALUE", word.getValue());
                    jsonObject.put("KEY", word.getKey());
                    jsonObject.put("DESCRIPTION", word.getDescription());
                    jsonObject.put("PRIORITY", word.getPriority());
                    jsonArray.put(jsonObject);
                }

                categoryOjb.put(category.getId(), jsonArray);

            }


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


        return categoryOjb.toString();
    }


}
