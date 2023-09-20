
package com.anthorra.html;

import java.util.ArrayList;

/**
 *
 * @author Anthorra
 */
public class HtmlList extends HtmlBodyElement
{
    private String listClass;
    private String listItemClass;
    private boolean isOrdered;
    private ArrayList<String> listItems;
    private ArrayList<HtmlBodyElement> listElements;

    /* CONSTRUCTORS */
    public HtmlList(String[] listItems)
    {
        this.listItems = new ArrayList<String>();
        for(String i : listItems)
        {
            this.listItems.add(i);
        }
        isOrdered = false;
               
    }
    public HtmlList(HtmlBodyElement[] listElements)
    {
        this.listElements = new ArrayList<HtmlBodyElement>();
        for(HtmlBodyElement e : listElements)
        {
            this.listElements.add(e);
        }
        isOrdered = false;
               
    }
    public HtmlList(String[] listItems, boolean isOrderedList)
    {
        this.listItems = new ArrayList<String>();
        for(String i : listItems)
        {
            this.listItems.add(i);
        }
        isOrdered = isOrderedList;  
    }
    public HtmlList(boolean isOrderedList)
    {
        this.listItems = new ArrayList<String>();
        this.listElements = new ArrayList<HtmlBodyElement>();
        isOrdered = isOrderedList;  
    }    
    
    
    @Override
    String contructElement()
    {
        String tag = "ul";
        if(isOrdered){tag = "ol";}
        
        String list = "<";
        list += tag;
        
        if(listClass != null)
        {
            list += " class=\"" + listClass + "\"";
        }
        
        list += ">";
        
        if(listItems != null)
        {
            String listItemOpen;
            if(listItemClass != null)
            {
                listItemOpen = "<li class=\"" + listItemClass + "\">";
            }
            else
            {
                listItemOpen = "<li>";
            }
            
            for(String l : listItems)
            {
                list += listItemOpen;
                list += l;
                list += "</li>";
            }
        }
        if(listElements != null)
        {
            String listItemOpen;
            if(listItemClass != null)
            {
                listItemOpen = "<li class=\"" + listItemClass + "\">";
            }
            else
            {
                listItemOpen = "<li>";
            }
            
            for(HtmlBodyElement e : listElements)
            {
                list += listItemOpen;
                list += e.getElement();
                list += "</li>";
            }
        }
        
        list += "</";
        list += tag;
        list += ">";
        return list;
    }
    
    public HtmlList setListClass(String listClass)
    {
        this.listClass = listClass;
        return this;
    }
    public HtmlList setListItemClass(String listItemClass)
    {
        this.listItemClass = listItemClass;
        return this;
    }
    public HtmlList addListItem(String listItem)
    {
        this.listItems.add(listItem);
        return this;
    }
    public HtmlList addListItem(HtmlBodyElement listElement)
    {
        this.listElements.add(listElement);
        return this;
    }
}
