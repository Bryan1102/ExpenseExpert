
package com.anthorra.html;

import java.util.ArrayList;


public class HtmlBody
{
    private String body;
    private ArrayList<HtmlBodySection> sections;

    /* CONSTRUCTOR */
    public HtmlBody()
    {
        this.sections = new ArrayList<HtmlBodySection>();
    }
    
    
    
    private String constructBody()
    {
        body = "<body>";
        
        if(sections.size() > 0)
        {
            for(int i = 0; i < sections.size(); i++)
            {
                body += sections.get(i).getSection();
            }
        }
        
        
        body += "</body>";
        
        return body;
    }

    public String getBody()
    {
        return constructBody();
    }
    
    
    
    /* SECTIONS */
    public HtmlBodySection addSection()
    {
        HtmlBodySection section = new HtmlBodySection();
        sections.add(section);
        return section;
    }
    public HtmlBodySection addSection(HtmlBodySection section)
    {
        sections.add(section);
        return section;
    }
    
    /*public HtmlBodySection getSection(int index)
    {
        return sections.get(index);
    }*/
    
    
}
