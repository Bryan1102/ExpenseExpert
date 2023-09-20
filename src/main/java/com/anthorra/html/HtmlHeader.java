
package com.anthorra.html;

import java.util.HashMap;

/**
 *
 * @author Anthorra
 */
public class HtmlHeader extends HtmlBodyElement
{

    private String text;
    private int size;
    //private String hClass;
    private HashMap<String, String> attr;

    public HtmlHeader(String text, int size)
    {
        this.attr = new HashMap<>();
        this.text = text;
        if(size < 1 || size > 6){this.size = 1;}
            else {this.size = size;}
        
    }
    public HtmlHeader(String text, int size, String hClass)
    {
        this.attr = new HashMap<>();
        this.text = text;
        if(size < 1 || size > 6){this.size = 1;}
            else {this.size = size;}
        //this.hClass = hClass;
        addAttribute("class", hClass);
    }   
    
    
    @Override
    String contructElement()
    {
        //if(hClass == null){hClass = "";}
        
        //String header = "<h" + size + " class=\"" + hClass + "\">";
        String header = "<h" + size;
        if(!this.attr.isEmpty())
            {header += SupportFunctions.decodeAttributes(attr);}
        header += ">";
        header += text;
        header += "</h" + size + ">";
        return header;
    }

    public HtmlHeader addAttribute(String id, String value)
    {
        attr.put(id, value);
        return this;
    }
     
    
}
