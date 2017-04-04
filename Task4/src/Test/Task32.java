package Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import st.EntryMap;
import st.TemplateEngine;

public class Task32 {

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
    public void Test1(){
    	map.store("name", "Adam", false);
        map.store("we should try or best for winning the ${competition} cup.", "I am happy.", false);
        String result = engine.evaluate( "I heard that: ${name} said: ${we should try or best for winning the ${competition} cup.}", map, "optimization");
        assertEquals("I heard that: Adam said: I am happy.", result);
    }
    
    @Test
    public void Test2(){
    	map.store("name", "Adam", false);
        map.store("we should try or best for winning the  cup.", "I am not happy.", false);
        String result = engine.evaluate( "I heard that: ${name} said: ${we should try or best for winning the ${competition} cup.}", map, "optimization");
        assertEquals("I heard that: Adam said: I am not happy.", result);
    }
    
    @Test
    public void Test3(){
    	map.store("name", "Adam", false);
        map.store("we should try or best for winning the ${competition} cup.", "I am not happy.", false);
        String result = engine.evaluate( "I heard that: ${name${age}} said: ${we should try or best for winning the ${competition} cup.}", map, "optimization");
        assertEquals("I heard that: ${name${age}} said: I am not happy.", result);
    }
}
