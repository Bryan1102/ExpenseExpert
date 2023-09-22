
package com.anthorra.html;

import java.util.HashMap;

/**
 *
 * @author Anthorra
 */
public class HtmlSelectList extends HtmlBodyElement
{
    private HashMap<String, String> attr;
    private String[] options;
    private HtmlBodyDiv parentDiv;
    private String selected;
    
    public HtmlSelectList(String[] options, HtmlBodyDiv parentDiv)
    {
        this.attr = new HashMap<>();
        this.options = options;
        this.parentDiv = parentDiv;
    }
    
    
    
    @Override
    String contructElement()
    {
        String select = "<select";
        if(!this.attr.isEmpty())
            {select += SupportFunctions.decodeAttributes(attr);}
        select += ">";
        
        //select += "<option value=\"\" disabled hidden>Kérlek Válassz</option>"; /*nem működik?*/
        //value hozzáadható????
        //<option value="1">One</option>
        //<option value="2">Two</option>
        
        for(String o : options)
        {
            if(o.equals(selected))
            {
                select += "<option selected>";
            }
            else
            {
                select += "<option>";
            }
            select += o;
            
            select += "</option>";
        }
        
        
        select += "</select>";
        return select;
    }
    
    public HtmlSelectList addAttribute(String id, String value)
    {
        attr.put(id, value);
        return this;
    }
        public HtmlBodyDiv getParentDiv()
    {
        return parentDiv;
    }
    public HtmlSelectList setSelectedOption(String selected)
    {
        this.selected = selected;
        return this;
    }
}
