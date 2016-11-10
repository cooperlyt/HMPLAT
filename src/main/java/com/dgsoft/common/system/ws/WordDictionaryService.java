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

@WebService()
public class WordDictionaryService {

    @WebMethod
    public String getAllDictionary() {
        DictionaryWord dictionary = (DictionaryWord) Component.getInstance(DictionaryWord.class, true);
        dictionary.loadWord();
        JSONObject categoryOjb = new JSONObject();
        try {

            for (WordCategory category : dictionary.getAllWordCategory().values()) {

                JSONArray jsonArray = new JSONArray();

                for (Word word : dictionary.getWordList(category.getId())) {
                    jsonArray.put(genWordJsonObject(word));
                }

                categoryOjb.put(category.getId().replace(".", "_"), jsonArray);

            }


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


        return categoryOjb.toString();
    }

    private JSONObject genWordJsonObject(Word word) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ID", word.getId());
        jsonObject.put("VALUE", word.getValue());
        jsonObject.put("KEY", word.getKey());
        jsonObject.put("DESCRIPTION", word.getDescription());
        jsonObject.put("PRIORITY", word.getPriority());
        return jsonObject;
    }

    @WebMethod
    public String getDictionaryWords(String categoryId) {
        DictionaryWord dictionary = (DictionaryWord) Component.getInstance(DictionaryWord.class, true);

        JSONArray jsonArray = new JSONArray();
        try {
            for (Word word : dictionary.getWordList(categoryId)) {

                jsonArray.put(genWordJsonObject(word));

            }
            return jsonArray.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);

        }
    }

    @WebMethod
    public String getDictionaryWordValue(String id){
        DictionaryWord dictionary = (DictionaryWord) Component.getInstance(DictionaryWord.class, true);
        return dictionary.getWordValue(id);
    }

    @WebMethod
    public String getDictionaryWord(String id){
        DictionaryWord dictionary = (DictionaryWord) Component.getInstance(DictionaryWord.class, true);
        try {
            Word word = dictionary.getWord(id);
            if (word == null){
                return null;
            }
            return genWordJsonObject(word).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }




}
