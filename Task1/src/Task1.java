import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
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
    
    @Rule
    public ExpectedException thrown= ExpectedException.none();
    
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
    public void EM_spec1_Test1() {
    	thrown.expect(RuntimeException.class);
    	map.store(null, "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    @Test
    public void EM_spec1_Test2() {
    	thrown.expect(RuntimeException.class);
    	map.store("", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    @Test
    public void EM_spec2_Test1(){
    	thrown.expect(RuntimeException.class);
    	map.store("name", null, false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    @Test
    public void EM_spec3_Test1(){
    	map.store("Name", "Adam", null);
        map.store("surnamE", "Dykes", false);
        map.store("aGe", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    @Test
    public void EM_spec3_Test2(){
    	map.store("Name", "Adam", false);
        map.store("surnamE", "Dykes", false);
        map.store("aGe", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    @Test
    public void EM_spec3_Test3(){
    	map.store("Name", "Adam", true);
        map.store("surnamE", "Dykes", true);
        map.store("aGe", "29", true);
        String result = engine.evaluate("Hello ${Name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age ", result);
    }
    
    @Test
    public void EM_spec4_Test1(){
    	map.store("name", "Nick", false);
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Nick, is your age 29", result);
    }
    
    @Test
    public void EM_spec5_Test1(){
    	map.store("Name", "Adam", true);
    	map.store("Name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    @Test
    public void TE_spec1_Test1(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate(null, map,"delete-unmatched");
        assertEquals(null, result);
    }
    
    @Test
    public void TE_spec1_Test2(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("", map,"delete-unmatched");
        assertEquals("", result);
    }
    
    
    @Test
    public void TE_spec2_Test1(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", null,"delete-unmatched");
        assertEquals("Hello ${name}, is your age ${age ${symbol}}", result);
    }
    
    @Test
    public void TE_spec3_Test1(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map, null);
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    @Test
    public void TE_spec3_Test2(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map, "other possible values");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    @Test
    public void TE_spec3_Test3(){
    	map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map, "keep-unmatched");
        assertEquals("Hello Adam, is your age ${age ${symbol}}", result);
    }
    
    @Test
    public void TE_spec4_Test1(){
    	map.store("name", "Adam", false);
    	map.store("${item", "Adam", false);
        map.store("item}", "pen", false);
        map.store("${item}", "pen", false);
        String result = engine.evaluate("Hello ${name}, could you please give me your ${item} ?", map, "delete-unmatched");
        assertEquals("Hello Adam, could you please give me your  ?", result);
    }
    
    @Test
    public void TE_spec5_Test1(){
    	map.store("middle name", "Adam", false);
        map.store("item", "pen", false);
        String result = engine.evaluate("Hello ${middlename}, could you please give me your ${it  em} ?", map, "delete-unmatched");
        assertEquals("Hello Adam, could you please give me your pen ?", result);
    }
    
    @Test
    public void TE_spec6_Test1(){
    	map.store("name", "Adam", false);
    	map.store("competition", "World", false);
        map.store("we should try or best for winning the World cup.", "I am happy.", false);
        String result = engine.evaluate( "I heard that }: ${name} said: ${we should try or best for winning the ${competition} cup.}", map, "delete-unmatched");
        assertEquals("I heard that }: Adam said: I am happy.", result);
    }
    
    @Test
    public void TE_spec7_Test1(){
    	map.store("s", "0", false);
    	map.store("de", "1", false);
    	map.store("lm", "2", false);
        map.store("fgijk2nopqr", "3", false);
        map.store("fgijk${lm}nopqr", "4", false);
        String result = engine.evaluate( "abc}${de}${fgijk${lm}nopqr}${s}uvw${xyz", map, "delete-unmatched");
        assertEquals("abc}130uvw${xyz", result);
    }
    
    @Test
    public void TE_spec8_Test1(){
    	map.store("s", "0", false);
    	map.store("de", "1", false);
    	map.store("lm", "2", false);
        map.store("fgijk${lm}nopqr", "3", false);
        String result = engine.evaluate( "abc}${de}${fgijk${lm}nopqr}${s}uvw${xyz", map, "delete-unmatched");
        assertEquals("abc}10uvw${xyz", result);
    }
    
}