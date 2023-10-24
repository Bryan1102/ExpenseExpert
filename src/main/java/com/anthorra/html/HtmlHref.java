
package com.anthorra.html;

import java.util.HashMap;

/**
 *
 * @author Anthorra
 */
public class HtmlHref extends HtmlBodyElement
{

    private String url;
    private String text;
    private HashMap<String, String> attr;

    public HtmlHref(String url, String text)
    {
        this.url = url;
        this.text = text;
        this.attr = new HashMap<String, String>();
    }
    
    
    
    @Override
    String contructElement()
    {
        String href = "<a";
        
        if(!this.attr.isEmpty())
            {href += SupportFunctions.decodeAttributes(attr);}
        
        href += " href=\"";
        href += this.url;
        href += "\">";
        href += this.text;
        href += "</a>";
        
        return href;
    }
    
    public HtmlHref addAttribute(String id, String value)
    {
        attr.put(id, value);
        return this;
    }
    
}
