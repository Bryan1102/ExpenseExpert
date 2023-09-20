
package com.anthorra.html;

import java.util.ArrayList;


/**
 *
 * @author Anthorra
 */

public class HtmlBodySection
{
    private String section;
    private String sectionId;
    private String headerText;
    private String sectionType;
    private String sectionClass;
    private String sectionStyle;
    //private enum Type {SECTION, MAIN, ARTICLE, ASIDE, DETAILS, /*FIGCAPTION, FIGURE,*/ FOOTER, HEADER, /*MARK,*/ NAV, /*SUMMARY,*/ TIME}; 
    private ArrayList<HtmlBodyDiv> divs;

    public HtmlBodySection()
    {
        this.divs = new ArrayList<HtmlBodyDiv>();
    }
    
    private String constructSection()
    {
        if(this.sectionType == null){this.sectionType = "section";}
        
        /* SECTION START */   
        section =  "<";
        section +=  this.sectionType;
        
        if(sectionId != null)
        {
            section +=  " id=\"" + sectionId + "\"";
        }
        if(sectionClass != null)
        {
            section +=  " class=\"" + sectionClass + "\"";
        }
        if(sectionStyle != null)
        {
            section +=  " style=\"" + sectionStyle + "\"";
        }
        section +=  ">";
        
        /* SECTION HEADER */
        if(headerText != null)
        {
            section += "<h1> " + this.headerText + " </h1>";
        }
        
        /* SECTION PARTS */
        if(divs != null && !divs.isEmpty())
        {
            for(int i = 0; i < divs.size(); i++)
            {
                section += divs.get(i).getDiv();
            }
        }
        
        
        /* SECTION END */
        section += "</" + this.sectionType + ">";
        
        return section;
    }

    public String getSection()
    {
        return constructSection();
    }
    
    /* Special chain setters */
    public HtmlBodySection addSectionHeader(String headerText)
    {
        this.headerText = headerText;
        return this;
    }
    public HtmlBodySection addSectionIdAttr(String idAttribute)
    {
        this.sectionId = idAttribute;
        return this;
    }
    public HtmlBodySection setSectionType(String type)
    {
        this.sectionType = type;
        return this;
    }
    public HtmlBodySection setSectionClass(String sectionClass)
    {
        this.sectionClass = sectionClass;
        return this;
    }
    public HtmlBodySection setSectionStyle(String sectionStyle)
    {
        this.sectionStyle = sectionStyle;
        return this;
    }
    
    public HtmlBodyDiv addDiv()
    {
        HtmlBodyDiv div = new HtmlBodyDiv(this);
        divs.add(div);
        return div; 
    }
    public HtmlBodyDiv addDiv(HtmlBodyDiv div)
    {
        div.setParentSection(this);
        divs.add(div);
        return div;
    }
}


