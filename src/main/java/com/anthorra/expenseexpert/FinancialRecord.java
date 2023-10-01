
package com.anthorra.expenseexpert;

/**
 *
 * @author Anthorra
 */
public class FinancialRecord
{
    private double amount;
    private boolean isExpense;
    private int category, subCategory, id;
    //String typeName, subTypeName;
    private String realizedDate;
    private String comment;

    public FinancialRecord(double amount, boolean isExpense, int type, int subtype, String comment, String realizedDate)
    {
        //this.amount = amount<0?0:((double)(Math.round(amount * 1000.00)/1000.00));
        setAmount(amount);
        this.isExpense = isExpense;
        this.category = type;
        this.subCategory = subtype;
        this.comment = comment;
        this.realizedDate = realizedDate;
        this.id = 0;
        
    }

    /* GETTER SECTION */
    public double getAmount()    {        return amount;    }
    public boolean isIsExpense()    {        return isExpense;    }
    public int getCategory()    {        return category;    }
    public int getSubCategory()    {        return subCategory;    }
    public String getComment()    {        return comment;    }
    public String getRealizedDate()    {        return realizedDate;    }
    public int getId()    {        return id;    }
    
    /* SETTER SECTION */
    public void setAmount(double amount)    
    {        
        //Ellenőrzés negativ értékre és kerekítés
        this.amount = amount<0?0:((double)(Math.round(amount * 1000.00)/1000.00));
        //this.amount = amount;    
    }
    public void setIsExpense(boolean isExpense)    {        this.isExpense = isExpense;    }
    public void setCategory(int category)    {        this.category = category;    }
    public void setSubCategory(int subCategory)    {        this.subCategory = subCategory;    }
    public void setRealizedDate(String realizedDate)    {        this.realizedDate = realizedDate;    }
    public void setComment(String comment)    {        this.comment = comment;    }
    public void setId(int id)    {        this.id = id;    }
    
    
    @Override
    public String toString()
    {
        return "FinancialRecord{" + "amount=" + amount + ", isExpense=" + isExpense + ", type=" + category + ", subtype=" + subCategory + ", realizedDate=" + realizedDate + ", comment=" + comment + '}';
    }
    
    
    
    
    
}
