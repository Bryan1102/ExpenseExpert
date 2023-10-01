
package com.anthorra.expenseexpert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anthorra
 */
public class CategoryTest
{
    Category c;
            
    public CategoryTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
        c = new Category(0, "Teszt Kategória");
    }
    
    @After
    public void tearDown()
    {
    }

    @Test
    public void testGetId()
    {
        System.out.println("getId");
        Category instance = c;
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
        
    }

    @Test
    public void testGetCategoryName()
    {
        System.out.println("getCategoryName");
        Category instance = c;
        String expResult = "Teszt Kategória";
        String result = instance.getCategoryName();
        assertEquals(expResult, result);
        
    }

    @Test
    public void testSetCategoryName()
    {
        System.out.println("setCategoryName");
        String categoryName = "Átnevezett";
        Category instance = c;
        instance.setCategoryName(categoryName);
        assertEquals(categoryName, instance.getCategoryName());
    }

    
    
}
