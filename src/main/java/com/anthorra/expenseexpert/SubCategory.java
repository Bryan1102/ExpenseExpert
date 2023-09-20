
package com.anthorra.expenseexpert;

/**
 *
 * @author Anthorra
 */
public class SubCategory
{
    private int id;
    private int parentId;
    private String categoryName;

    public SubCategory(int id, int parentId, String categoryName)
    {
        this.id = id;
        this.parentId = parentId;
        this.categoryName = categoryName;
    }

    public int getId()
    {
        return id;
    }

    public int getParentId()
    {
        return parentId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setParentId(int parentId)
    {
        this.parentId = parentId;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    @Override
    public String toString()
    {
        return "SubCategory{" + "id=" + id + ", parentId=" + parentId + ", categoryName=" + categoryName + '}';
    }
    
    
}
