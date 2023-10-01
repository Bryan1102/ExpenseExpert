
package com.anthorra.expenseexpert;

import static org.junit.Assert.*;

/**
 *
 * @author Anthorra
 */
public class FinancialRecordTest
{
    
    public FinancialRecordTest()
    {
        FinancialRecord fr = new FinancialRecord(15000, true, 1, 1, "Teszt kiadás", "2023-09-30");
    }

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @org.junit.AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @org.junit.Before
    public void setUp() throws Exception
    {
    }

    @org.junit.After
    public void tearDown() throws Exception
    {
    }
    
    

    @org.junit.Test
    public void testGetAmount()
    {
        System.out.println("getAmount");
        FinancialRecord instance = new FinancialRecord(15000, true, 1, 1, "Teszt kiadás", "2023-09-30");
        double expResult = 15000;
        double result = instance.getAmount();
        assertEquals(expResult, result, 0);
        
        instance = new FinancialRecord(-15000, true, 1, 1, "Teszt kiadás", "2023-09-30");
        assertEquals(0, instance.getAmount(), 0);
    }

    @org.junit.Test
    public void testIsIsExpense()
    {
        System.out.println("isIsExpense");
        FinancialRecord instance = new FinancialRecord(15000, true, 1, 1, "Teszt kiadás", "2023-09-30");
        boolean expResult = true;
        boolean result = instance.isIsExpense();
        assertEquals(expResult, result);
        
    }

    @org.junit.Test
    public void testGetType()
    {
        System.out.println("getType");
        FinancialRecord instance = new FinancialRecord(15000, true, 1, 1, "Teszt kiadás", "2023-09-30");
        int expResult = 1;
        int result = instance.getType();
        assertEquals(expResult, result);
        
    }

    @org.junit.Test
    public void testGetSubtype()
    {
        System.out.println("getSubtype");
        FinancialRecord instance = new FinancialRecord(15000, true, 1, 1, "Teszt kiadás", "2023-09-30");
        int expResult = 1;
        int result = instance.getSubtype();
        assertEquals(expResult, result);
        
    }

    @org.junit.Test
    public void testGetComment()
    {
        System.out.println("getComment");
        FinancialRecord instance = new FinancialRecord(15000, true, 1, 1, "Teszt kiadás", "2023-09-30");
        String expResult = "Teszt kiadás";
        String result = instance.getComment();
        assertEquals(expResult, result);
        
    }

    @org.junit.Test
    public void testGetRealizedDate()
    {
        System.out.println("getRealizedDate");
        FinancialRecord instance = new FinancialRecord(15000, true, 1, 1, "Teszt kiadás", "2023-09-30");
        String expResult = "2023-09-30";
        String result = instance.getRealizedDate();
        assertEquals(expResult, result);
        
    }

    @org.junit.Test
    public void testSetAmount()
    {
        System.out.println("setAmount");
        double amount = 20000;
        FinancialRecord instance = new FinancialRecord(15000, true, 1, 1, "Teszt kiadás", "2023-09-30");
        instance.setAmount(amount);
        assertEquals(amount, instance.getAmount(), 0);
        
        amount = -20000;
        instance.setAmount(amount);
        assertEquals(0, instance.getAmount(), 0);
    }

    @org.junit.Test
    public void testSetIsExpense()
    {
        System.out.println("setIsExpense");
        boolean isExpense = false;
        FinancialRecord instance = new FinancialRecord(15000, true, 1, 1, "Teszt kiadás", "2023-09-30");
        instance.setIsExpense(isExpense);
        assertEquals(isExpense, instance.isIsExpense());
        
    }

    @org.junit.Test
    public void testSetType()
    {
        System.out.println("setType");
        int type = 2;
        FinancialRecord instance = new FinancialRecord(15000, true, 1, 1, "Teszt kiadás", "2023-09-30");
        instance.setType(type);
        assertEquals(type, instance.getType());
        
    }

    @org.junit.Test
    public void testSetSubtype()
    {
        System.out.println("setSubtype");
        int subtype = 3;
        FinancialRecord instance = new FinancialRecord(15000, true, 1, 1, "Teszt kiadás", "2023-09-30");
        instance.setSubtype(subtype);
        assertEquals(subtype, instance.getSubtype());
    }

    @org.junit.Test
    public void testSetRealizedDate()
    {
        System.out.println("setRealizedDate");
        String realizedDate = "2023-09-01";
        FinancialRecord instance = new FinancialRecord(15000, true, 1, 1, "Teszt kiadás", "2023-09-30");
        instance.setRealizedDate(realizedDate);
        assertEquals(realizedDate, instance.getRealizedDate());
    }

    @org.junit.Test
    public void testSetComment()
    {
        System.out.println("setComment");
        String comment = "ReComment";
        FinancialRecord instance = new FinancialRecord(15000, true, 1, 1, "Teszt kiadás", "2023-09-30");
        instance.setComment(comment);
        assertEquals(comment, instance.getComment());
    }

    @org.junit.Test
    public void testSetId()
    {
        System.out.println("setId");
        int id = 1000;
        FinancialRecord instance = new FinancialRecord(15000, true, 1, 1, "Teszt kiadás", "2023-09-30");
        instance.setId(id);
        assertEquals(id, instance.getId());
    }

    
    
}
