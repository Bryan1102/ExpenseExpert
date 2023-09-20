
package com.anthorra.html;

import java.util.HashMap;

/**
 *
 * @author Anthorra
 */
public class HtmlButton extends HtmlBodyElement
{
    private HashMap<String, String> attr;
    private HtmlBodyDiv parentDiv;
    private String text;
    private String type;

    public HtmlButton(HtmlBodyDiv parentDiv, String text, String type)
    {
        this.parentDiv = parentDiv;
        this.text = text;
        this.type = type;
        this.attr = new HashMap<String, String>();
        addAttribute("type", type);
    }
    
    
    
    @Override
    String contructElement()
    {
        String button = "<button";
        if(!this.attr.isEmpty())
            {button += SupportFunctions.decodeAttributes(attr);}
        button += ">";
        button += text;
        
        button += "</button>";
        
        return button;
    }
    
    
    
    
    public HtmlButton addAttribute(String id, String value)
    {
        attr.put(id, value);
        return this;
    }

    public HtmlBodyDiv getParentDiv()
    {
        return parentDiv;
    }
    
}
