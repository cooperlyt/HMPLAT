package com.dgsoft.common.system;

import com.dgsoft.common.system.model.Employee;
import com.dgsoft.common.system.model.ProvinceCode;
import com.dgsoft.common.system.model.Word;
import com.dgsoft.common.system.model.WordCategory;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.contexts.Contexts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 8/20/13
 * Time: 7:47 AM
 */
@Name("dictionary")
@Scope(ScopeType.APPLICATION)
@Synchronized
@AutoCreate
@Startup
public class DictionaryWord {

    //private Map<String, List<Word>> wordsCache = new HashMap<String, List<Word>>();

    private Map<String, Word> wordCache;

    private Map<String, WordCategory> wordCategory;

    private Map<String, Employee> employeeMap;

   private Map<String, ProvinceCode> provinceMap;

    @In(create = true)
    private Map<String,String> messages;

    @Create
    @Transactional
    public void load() {
        loadWord();
        loadEmp();
        loadCity();
    }


    public void loadCity() {
        provinceMap = new HashMap<String, ProvinceCode>();
        List<ProvinceCode> citys = systemEntityLoader.getPersistenceContext().createQuery("select city from ProvinceCode city", ProvinceCode.class).getResultList();
        for (ProvinceCode city : citys) {
            provinceMap.put(city.getId(), city);
        }
    }

    @Observer({"org.jboss.seam.afterTransactionSuccess.WordCategory", "org.jboss.seam.afterTransactionSuccess.Word"})
    public void loadWord() {
        wordCache = new HashMap<String, Word>();
        wordCategory = new HashMap<String, WordCategory>();
        List<WordCategory> result = systemEntityLoader.getPersistenceContext().createQuery("select wordCategory from WordCategory wordCategory left join fetch wordCategory.words").getResultList();
        for (WordCategory category : result) {
            wordCategory.put(category.getId(), category);
            for (Word word : category.getWords()) {
                wordCache.put(word.getId(), word);
            }
        }
    }

    @Observer("org.jboss.seam.afterTransactionSuccess.Employee")
    public void loadEmp() {
        employeeMap = new HashMap<String, Employee>();
        List<Employee> employees = systemEntityLoader.getPersistenceContext().createQuery("select emp from Employee emp").getResultList();
        for (Employee emp : employees) {
            employeeMap.put(emp.getId(), emp);
        }
    }

    @In(create = true)
    private SystemEntityLoader systemEntityLoader;

    public List<Word> getWordList(String categoryId) {
        List<Word> result = new ArrayList<Word>();
        for (Word word : wordCategory.get(categoryId).getWordList()) {
            if (word.isEnable()) {
                result.add(word);
            }
        }
        return result;
    }

    public String getWordCategory(String categoryId) {
        if (categoryId == null || "".equals(categoryId.trim())) {
            return "";
        }

        WordCategory resultCategory = wordCategory.get(categoryId);
        if (resultCategory == null) {
            return "";
        } else
            return resultCategory.getName();
    }


    public Word getWord(String wordId) {
        if (wordId == null || "".equals(wordId.trim()))
            return null;
        return wordCache.get(wordId);
    }

    public String getWordValue(String wordId) {
        if (wordId == null || "".equals(wordId.trim()))
            return "";
        Word word = getWord(wordId);
        if (word != null) {
            return word.getValue();
        } else
            return wordId;

    }


    public Employee getEmpById(String id) {
        return employeeMap.get(id);
    }

    public String getEmpNameById(String id) {
        Employee emp = employeeMap.get(id);
        if (emp == null) {
            return "invalid";
        } else
            return emp.getPersonName();

    }

    public Map<String, WordCategory> getAllWordCategory() {
        return wordCategory;
    }


    public String getProvinceName(String value){
        if (value == null || value.trim().length() < 2){
            return null;
        }
        ProvinceCode provinceCode = provinceMap.get(value.trim().substring(0,2));
        if (provinceCode == null){
            return null;
        }else{
            return provinceCode.getName();
        }
    }

    public String getCityName(String value){
        if (value == null || value.trim().length() < 4) {
            return null;
        }
        ProvinceCode provinceCode = provinceMap.get(value.trim().substring(0,4));
        if (provinceCode == null){
            return null;
        }else{
            return provinceCode.getName();
        }
    }

    public String getDistrictName(String value){
        if (value == null || value.trim().length() < 6) {
            return null;
        }
        ProvinceCode provinceCode = provinceMap.get(value.trim().substring(0,6));
        if (provinceCode == null){
            return null;
        }else{
            return provinceCode.getName();
        }
    }

    public String getEnumLabel(Enum value){
        return messages.get(value.getClass().getName() + "." + value.name());
    }

    public static DictionaryWord instance()
    {
        if ( !Contexts.isEventContextActive() )
        {
            throw new IllegalStateException("no active event context");
        }
        return (DictionaryWord) Component.getInstance(DictionaryWord.class, ScopeType.APPLICATION);
    }

}
