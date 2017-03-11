import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import st.EntryMap;
import st.TemplateEngine;

public class Task22 {

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
    
    @Test
    public void Test_for_0_and_2(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${}, is your age ${age ${symbol}}", map, "delete-unmatched");
        assertEquals("Hello , is your age 29", result);
    }
    
    @Test
    public void Test_for_1(){
    	map.store("name", "Adam", false);
        String result = engine.evaluate("$what{name}", map, "delete-unmatched");
        assertEquals("$what{name}", result);
    }
    
    @Test
    public void Test_for_3(){
    	map.store("name", "Adam", false);
        String result = engine.evaluate("${name}", map, "delete-unmatched");
        assertEquals("Adam", result);
    } 
    
    @Test
    public void Test_for_4_and_5(){
    	map.store("Name", "Adam", true);
    	map.store("Name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    @Test
    public void Test_for_6(){
  	    map.store("Name", "Adam", true);
        map.store("surnamE", "Dykes", true);
        map.store("aGe", "29", true);
        String result = engine.evaluate("Hello ${Name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age ", result);
    }
    
    @Test
    public void Test_for_7(){
    	map.store("s", "0", false);
    	map.store("de", "1", false);
    	map.store("lm", "2", false);
        map.store("fgijk2nopqr", "3", false);
        map.store("fgijk${lm}nopqr", "error", false);
        String result = engine.evaluate( "abc}${de}${fgijk${lm}nopqr}${s}uvw${xyz", map, "delete-unmatched");
        assertEquals("abc}130uvw${xyz", result);
    }
    
    @Test
    public void Test_for_8(){
    	map.store("name", "Adam", false);
    	map.store("whatAdam", "Kitty", false);
    	map.store("helloKitty", "Hi", false);
        String result = engine.evaluate("Hello, ${hello${what${name}}}, Nihao", map, "delete-unmatched");
        assertEquals("Hello, Hi, Nihao", result);
    }
    
    @Test
    public void Test_for_9(){
    	map.store("name", "Adam", false);
        String result = engine.evaluate("${hi}, ${name}", map, "keep-unmatched");
        assertEquals("${hi}, Adam", result);
    }    
    
}