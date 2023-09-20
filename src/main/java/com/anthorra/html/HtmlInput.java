
package com.anthorra.html;

import java.util.HashMap;

/**
 *
 * @author Anthorra
 */
public class HtmlInput extends HtmlBodyElement
{
    //private String text;
    private String type;
    private HashMap<String, String> attr;
    private HtmlBodyDiv parentDiv;

    public HtmlInput(String type, HtmlBodyDiv parentDiv)
    {
        this.type = type;
        this.parentDiv = parentDiv;
        
        this.attr = new HashMap<>();
        attr.put("type", "text");
    }
    
    
    
    @Override
    String contructElement()
    {
        String input = "<input";
        if(!this.attr.isEmpty())
            {input += SupportFunctions.decodeAttributes(attr);}
        input += ">";
        
        
        input += "</input>";
        
        return input;
    }
    
    
    
    
    public HtmlInput addAttribute(String id, String value)
    {
        attr.put(id, value);
        return this;
    }

    public HtmlBodyDiv getParentDiv()
    {
        return parentDiv;
    }
    
}
