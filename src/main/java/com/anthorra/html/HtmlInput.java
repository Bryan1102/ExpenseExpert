
package com.anthorra.html;

import java.util.HashMap;

/**
 *
 * @author Anthorra
 */
public class HtmlInput extends HtmlBodyElement
{
    //private String text;
    //private String type;
    private String name;
    private String label;
    private HashMap<String, String> attr;
    private HtmlBodyDiv parentDiv;

    public HtmlInput(String type, HtmlBodyDiv parentDiv)
    {
        //this.type = type;
        this.parentDiv = parentDiv;
        
        this.attr = new HashMap<>();
        attr.put("type", type);
    }
    
    
    
    @Override
    String contructElement()
    {
        String label = this.label==null?"":this.label;
        
        String input = label + "<input";
        if(!this.attr.isEmpty())
            {input += SupportFunctions.decodeAttributes(attr);}
        input += ">";
        
        
        input += "</input>";
        
        return input;
    }
    
    
    
    
    public HtmlInput addAttribute(String id, String value)
    {
        attr.put(id, value);
        if(id.equals("name")){this.name = value;}
        return this;
    }
    public HtmlInput addLabel(String text)
    {
        this.label = "<label for=\"" + this.name + "\">" + text + "</label><br>";
        return this;
    }
    
    public HtmlBodyDiv getParentDiv()
    {
        return parentDiv;
    }
    
    
}
