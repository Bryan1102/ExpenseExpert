
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
public class SubCategoryTest
{
    SubCategory sc;
    
    public SubCategoryTest()
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
        sc = new SubCategory(1, 10, "Alkategória Teszt");
    }
    
    @After
    public void tearDown()
    {
    }

    @Test
    public void testGetId()
    {
        System.out.println("getId");
        SubCategory instance = sc;
        int expResult = 1;
        int result = instance.getId();
        assertEquals(expResult, result);
        
    }

    @Test
    public void testGetParentId()
    {
        System.out.println("getParentId");
        SubCategory instance = sc;
        int expResult = 10;
        int result = instance.getParentId();
        assertEquals(expResult, result);
        
    }

    @Test
    public void testGetCategoryName()
    {
        System.out.println("getCategoryName");
        SubCategory instance = sc;
        String expResult = "Alkategória Teszt";
        String result = instance.getCategoryName();
        assertEquals(expResult, result);
        
    }

    @Test
    public void testSetParentId()
    {
        System.out.println("setParentId");
        int parentId = 11;
        SubCategory instance = sc;
        instance.setParentId(parentId);
        int result = instance.getParentId();
        assertEquals(parentId, result);
    }

    @Test
    public void testSetCategoryName()
    {
        System.out.println("setCategoryName");
        String categoryName = "Renamed";
        SubCategory instance = sc;
        instance.setCategoryName(categoryName);
        
        String result = instance.getCategoryName();
        assertEquals(categoryName, result);
    }

    
    
}
