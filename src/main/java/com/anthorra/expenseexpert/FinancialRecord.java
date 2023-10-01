
package com.anthorra.expenseexpert;

/**
 *
 * @author Anthorra
 */
public class FinancialRecord
{
    private double amount;
    private boolean isExpense;
    private int type, subtype, id;
    //String typeName, subTypeName;
    private String realizedDate;
    private String comment;

    public FinancialRecord(double amount, boolean isExpense, int type, int subtype, String comment, String realizedDate)
    {
        //this.amount = amount<0?0:((double)(Math.round(amount * 1000.00)/1000.00));
        setAmount(amount);
        this.isExpense = isExpense;
        this.type = type;
        this.subtype = subtype;
        this.comment = comment;
        this.realizedDate = realizedDate;
        this.id = 0;
        
    }

    /* GETTER SECTION */
    public double getAmount()    {        return amount;    }
    public boolean isIsExpense()    {        return isExpense;    }
    public int getType()    {        return type;    }
    public int getSubtype()    {        return subtype;    }
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
    public void setType(int type)    {        this.type = type;    }
    public void setSubtype(int subtype)    {        this.subtype = subtype;    }
    public void setRealizedDate(String realizedDate)    {        this.realizedDate = realizedDate;    }
    public void setComment(String comment)    {        this.comment = comment;    }
    public void setId(int id)    {        this.id = id;    }
    
    
    @Override
    public String toString()
    {
        return "FinancialRecord{" + "amount=" + amount + ", isExpense=" + isExpense + ", type=" + type + ", subtype=" + subtype + ", realizedDate=" + realizedDate + ", comment=" + comment + '}';
    }
    
    
    
    
    
}
