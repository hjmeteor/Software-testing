import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import st.EntryMap;
import st.TemplateEngine;

public class Task21 {

    private EntryMap map;

    private TemplateEngine engine;

    @Before
    public void setUp() throws Exception {
        map = new EntryMap();
        engine = new TemplateEngine();
    }
    
    //Define a rule to  verify that code throws a specific exception.
    @Rule
    public ExpectedException thrown= ExpectedException.none();
    
    //In these test cases below, if the corressponding test case could run correctly, 
    //the functional test of function specification could be pass.
    @Test
    public void Test1() {
        map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        String result = engine.evaluate("Hello ${name} ${surname}", map,"delete-unmatched");
        assertEquals("Hello Adam Dykes", result);
    }

    @Test
    public void Test2() {
        map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    //Set the template to null. There should be a runtime exception thrown.
    //If there is not a runtime exception thrown, the code may have mistakes.
    @Test
    public void EM_spec1_Test1() {
    	thrown.expect(RuntimeException.class);
    	map.store(null, "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    //Set the template to empty string. There should be a runtime exception thrown.
    //If there is not a runtime exception thrown, the code may have mistakes.
    @Test
    public void EM_spec1_Test2() {
    	thrown.expect(RuntimeException.class);
    	map.store("", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    //Set the replace value string to null. There should be a runtime exception thrown.
    //If there is not a runtime exception thrown, the code may have mistakes.
    @Test
    public void EM_spec2_Test1(){
    	thrown.expect(RuntimeException.class);
    	map.store("name", null, false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    //Set case sensitive flag to null. There should be case insensitive.
    //If there is not case insensitive, the code may have mistakes.
    @Test
    public void EM_spec3_Test1(){
    	map.store("Name", "Adam", null);
        map.store("surnamE", "Dykes", false);
        map.store("aGe", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    //Set case sensitive flag to false. There should be case insensitive.
    //If there is not case insensitive, the code may have mistakes.
    @Test
    public void EM_spec3_Test2(){
    	map.store("Name", "Adam", false);
        map.store("surnamE", "Dykes", false);
        map.store("aGe", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    //Set case sensitive flag to true. There should be case sensitive.
    //If there is not case sensitive, the code may have mistakes.
    @Test
    public void EM_spec3_Test3(){
    	map.store("Name", "Adam", true);
        map.store("surnamE", "Dykes", true);
        map.store("aGe", "29", true);
        String result = engine.evaluate("Hello ${Name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age ", result);
    }
    
    //Store two entries which have same template("name") 
    //and set the first one's value to "Nick", set second one's value to "Adam"
    //There should be "Nick" apearing in the result string.
    //If there is not "Nick" but others, the code may have mistakes.
    @Test
    public void EM_spec4_Test1(){
    	map.store("name", "Nick", false);
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Nick, is your age 29", result);
    }
    
    //Store two entries shown below(template "Name") which with two different case sensitive flag.
    //There should be "Adam" here after "Hello" in the result string not empty.
    //If there is nothing between "Hello" and ",", the code may have mistakes.
    @Test
    public void EM_spec5_Test1(){
    	map.store("Name", "Adam", true);
    	map.store("Name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    //line 80
    @Test
    public void EM_entry_equals_Test1(){
    	map.store("name", "Adam", false);
    	map.store("surname", "Dykes", false);
        map.store("age", "29", false);
    	Boolean result;
    	ArrayList entries = new ArrayList<>();
    	entries = map.getEntries();
    	result = entries.get(0).equals(null);
    	assertEquals(false, result);
    }
    
    //line 80
    @Test
    public void EM_entry_equals_Test2(){
    	map.store("name", "Adam", false);
    	map.store("surname", "Dykes", false);
        map.store("age", "29", false);
    	Boolean result;
    	ArrayList entries = new ArrayList<>();
    	entries = map.getEntries();
    	result = entries.get(0).equals("Hello");
    	assertEquals(false, result);
    }
    
    //line 82, 84
    @Test
    public void EM_entry_equals_Test3(){
    	map.store("name", "Adam", false);
    	map.store("Name", "Adam", false);
    	Boolean result;
    	ArrayList entries = new ArrayList<>();
    	entries = map.getEntries();
    	result = entries.get(0).equals(entries.get(1));
    	assertEquals(false, result);
    }
    
    //85
    @Test
    public void EM_entry_equals_Test4(){
    	map.store("Name", "Adam", false);
    	map.store("Name", "adam", false);
    	Boolean result;
    	ArrayList entries = new ArrayList<>();
    	entries = map.getEntries();
    	result = entries.get(0).equals(entries.get(1));
    	assertEquals(false, result);
    }
    
    //86
    @Test
    public void EM_entry_equals_Test5(){
    	map.store("name", "Adam", false);
    	map.store("name", "Adam", true);
    	Boolean result;
    	ArrayList entries = new ArrayList<>();
    	entries = map.getEntries();
    	result = entries.get(0).equals(entries.get(1));
    	assertEquals(false, result);
    }
    
    //86
    @Test
    public void EM_entry_equals_Test6(){
    	map.store("name", "Adam", null);
    	map.store("name", "Adam", true);
    	Boolean result;
    	ArrayList entries = new ArrayList<>();
    	entries = map.getEntries();
    	result = entries.get(0).equals(entries.get(1));
    	assertEquals(false, result);
    }
    
    //79
    @Test
    public void EM_entry_equals_Test7(){
    	map.store("name", "Adam", false);
    	Boolean result;
    	ArrayList entries = new ArrayList<>();
    	entries = map.getEntries();
    	result = entries.get(0).equals(entries.get(0));
    	assertEquals(true, result);
    }
    
    //26 and 42
    @Test
    public void EM_store_isEntryUnique_Test1(){
    	map.store("Name", "Adam", true);
    	map.store("Name", "Adam", true);
        map.store("surnamE", "Dykes", true);
        map.store("aGe", "29", true);
        String result = engine.evaluate("Hello ${Name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age ", result);
    }
    
    //Set template string to null, the unchanged template string(null) should be returned
    //If the unchanged template string(null) is not returned, the code may have mistakes.
    @Test
    public void TE_spec1_Test1(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate(null, map,"delete-unmatched");
        assertEquals(null, result);
    }
    
    //Set template string to empty, the unchanged template string("") should be returned
    //If the unchanged template string("") is not returned, the code may have mistakes.
    @Test
    public void TE_spec1_Test2(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("", map,"delete-unmatched");
        assertEquals("", result);
    }
    
    //Set EntryMap object to null, the unchanged template string should be returned
    //If the unchanged template string is not returned, the code may have mistakes.
    @Test
    public void TE_spec2_Test1(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", null,"delete-unmatched");
        assertEquals("Hello ${name}, is your age ${age ${symbol}}", result);
    }
  
    //Set matching mode to null.
    //There should be the same as setting to "delete-unmatched". The ${symbol} match nothing and is deleted.
    //If the result is not like what described above, the code may have mistakes.
    @Test
    public void TE_spec3_Test1(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map, null);
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    //Set matching mode to other words.
    //There should be the same as setting to "delete-unmatched". The ${symbol} match nothing and is deleted.
    //If the result is not like what described above, the code may have mistakes.
    @Test
    public void TE_spec3_Test2(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map, "other possible values");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    //Set matching mode to "keep-unmatched".
    //The ${symbol} match nothing and will not be deleted. And template ${age ${symbol}} also match nothing and will not be deleted.
    //So the result will be "Hello Adam, is your age ${age ${symbol}}"
    //If the result is not like what described above, the code may have mistakes.
    @Test
    public void TE_spec3_Test3(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map, "keep-unmatched");
        assertEquals("Hello Adam, is your age ${age ${symbol}}", result);
    }
    
    //Store three entries whose templates are "${item", "item}", "${item}", but no "item"
    //The template ${item} in the template string will match the template "item" in the entries.
    //So template ${item} should not match the "${item", "item}", "${item}".
    //So the result will be "Hello Adam, could you please give me your  ?"
    //If the result is not like what described above, the code may have mistakes.
    @Test
    public void TE_spec4_Test1(){
    	map.store("name", "Adam", false);
    	map.store("${item", "Adam", false);
        map.store("item}", "pen", false);
        map.store("${item}", "pen", false);
        String result = engine.evaluate("Hello ${name}, could you please give me your ${item} ?", map, "delete-unmatched");
        assertEquals("Hello Adam, could you please give me your  ?", result);
    }
    
    //When a template is matched against an entry key, any non visible character does not affect the result.
    //Using ${middlename} and ${it  em} as templates in template string.
    //So the result will be "Hello Adam, could you please give me your pen ?"
    //If the result is not like what described above, the code may have mistakes.
    @Test
    public void TE_spec5_Test1(){
    	map.store("middle name", "Adam", false);
        map.store("item", "pen", false);
        String result = engine.evaluate("Hello ${middlename}, could you please give me your ${it  em} ?", map, "delete-unmatched");
        assertEquals("Hello Adam, could you please give me your pen ?", result);
    }
    
    //1.In a template string every "${" and "}" occurrence acts as a boundary of at MOST one template.
    //2.Processing from left-to-right, each "}" occurrence that is not already a boundary to a template is matched to 
    //its closest preceding "${" occurrence which also is not already a boundary to a template.
    //If the code is right, the result will be "I heard that }: ${Adam said: I am happy."
    //If "}" occurrence acts as a boundary of two template, the "${${name}" will first match "name" that use "Adam" replaced template,
    //and then match "${Adam}" that use Alice replaced template.
    //If it doesn't do as the principle 2, there will be template ${we should try or best for winning the ${competition} and then will go wrong.
    @Test
    public void TE_spec6_Test1(){
    	map.store("name", "Adam", false);
    	map.store("${name", "Nick", false);
    	map.store("Adam", "Alice", false);
    	map.store("competition", "World", false);
        map.store("we should try or best for winning the World cup.", "I am happy.", false);
        String result = engine.evaluate( "I heard that }: ${${name} said: ${we should try or best for winning the ${competition} cup.}", map, "delete-unmatched");
        assertEquals("I heard that }: ${Adam said: I am happy.", result);
    }
    
    //In a template string the different templates are ordered according to their length.
    //The shorter templates precede.
    //If the code is right, the result will be "abc}130uvw${xyz".
    //Because "lm" will be replaced by 2, ${fgijk${lm}nopqr} will be ${fgijk2nopqr} and use 3 replaced template.
    //If the shorter templates don't precede, "lm" will not be replaced by 2, and "${fgijk${lm}nopqr}" will replaced by "error" not 3.
    @Test
    public void TE_spec7_Test1(){
    	map.store("s", "0", false);
    	map.store("de", "1", false);
    	map.store("lm", "2", false);
        map.store("fgijk2nopqr", "3", false);
        map.store("fgijk${lm}nopqr", "error", false);
        String result = engine.evaluate( "abc}${de}${fgijk${lm}nopqr}${s}uvw${xyz", map, "delete-unmatched");
        assertEquals("abc}130uvw${xyz", result);
    }
    
    //The engine processes one template at a time 
    //and attempts to match it against the keys of the EntryMap entries until there is a match or the entry list is exhausted.
    //If the code is right, the result will be "Hello Adam, is your age 29"
    //But if code isn't right and the engine processes not only one template at a time,
    //"${name ${symbol}}" will match "name ${symbol}" and use "error" replace the template.
    @Test 
    public void TE_spec8_Test1(){
    	map.store("age ${symbol}", "error", false);
    	map.store("name ${symbol}", "error", false);
    	map.store("name", "Adam", false);
        map.store("age", "29", false);
        String result = engine.evaluate( "Hello ${name ${symbol}}, is your age ${age ${symbol}}", map, "delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    //line 87
    @Test
    public void TE_identifytemplates_Test1(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${}, is your age ${age ${symbol}}", map, "delete-unmatched");
        assertEquals("Hello , is your age 29", result);
    }
    
    //line 122-124
    @Test
    public void TE_sorttemplates_Test1(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("cars", "benz", false);
        String result = engine.evaluate("Hello ${name}, is your ${cars}", map, "delete-unmatched");
        assertEquals("Hello Adam, is your benz", result);
    }
    
    //line 183-185
    @Test
    public void TE_doreplace_Test1(){
    	map.store("name", "Adam", false);
        String result = engine.evaluate("${name}", map, "delete-unmatched");
        assertEquals("Adam", result);
    } 
}