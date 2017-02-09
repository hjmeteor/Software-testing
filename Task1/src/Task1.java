import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import st.EntryMap;
import st.TemplateEngine;

public class Task1 {

    private EntryMap map;

    private TemplateEngine engine;

    @Before
    public void setUp() throws Exception {
        map = new EntryMap();
        engine = new TemplateEngine();
    }

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
    
    @Test
    public void Test3(){
    	map.store(null, "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    @Test
    public void Test4(){
    	map.store("name", null, false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    @Test
    public void Test5(){
    	map.store("nAme", "Adam", null);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    @Test
    public void Test6(){
    	map.store("name", "Nick", false);
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    @Test
    public void Test7(){
    	map.store("name", "Adam", false);
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    @Test
    public void Test8(){
    	map.store("name", "Adam", false);
        map.store("name", "Adam", true);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    @Test
    public void Test9(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate(null, map,"delete-unmatched");
        assertEquals(null, result);
    }
    
    @Test
    public void Test10(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", null,"delete-unmatched");
        assertEquals("Hello ${name}, is your age ${age ${symbol}}", result);
    }
    
    @Test
    public void Test11(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map, null);
        assertEquals("Hello ${name}, is your age ${age ${symbol}}", result);
    }
    
    @Test
    public void Test12(){
    	map.store("${name", "Adam", false);
        map.store("item}", "pen}", false);
        String result = engine.evaluate("Hello ${${name}, could you please give me your ${item}} ?", map, "delete-unmatched");
        assertEquals("Hello Adam, could you please give me your pen ?", result);
    }
    
    @Test
    public void Test13(){
    	map.store("middle name", "Adam", false);
        map.store("item", "pen", false);
        String result = engine.evaluate("Hello ${middle       name}, could you please give me your ${item} ?", map, "delete-unmatched");
        assertEquals("Hello Adam, could you please give me your pen ?", result);
    }
    
}