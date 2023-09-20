
package com.anthorra.html;

/**
 *
 * @author Anthorra
 */
public class HtmlPage
{
    private String page;
    private String lang;
    
    
    private HtmlHead htmlHeader;
    private HtmlBody htmlBody;

    public HtmlPage()
    {
        htmlHeader = new HtmlHead();
        htmlBody = new HtmlBody();
    }

    /* Construct HTML page */
    private String constructPage()
    {
        if(lang==null)
        {lang = "\"en\"";}
        
        page =  "<!DOCTYPE html>";
        page += "<html lang=" + lang + ">";
        page += htmlHeader.getHead();
        page += htmlBody.getBody();
        page += "</html>";
        
        
        return page;
    }
    /* Return constructed page */
    public String getPage()
    {
        return constructPage();
    }
    
    /* Page parts */
    public HtmlPage setLang(String lang)
    {
        if(lang != null)
        {
            this.lang = lang;
        }
        return this;
    }
    
    
    /* Get parts*/
    public HtmlHead getHtmlHeader()
    {
        return htmlHeader;
    }

    public HtmlBody getHtmlBody()
    {
        return htmlBody;
    }

    public void setHtmlHeader(HtmlHead htmlHeader)
    {
        this.htmlHeader = htmlHeader;
    }

    public void setHtmlBody(HtmlBody htmlBody)
    {
        this.htmlBody = htmlBody;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    /* sub class methods*/
    /* Header parts */
    public void headerTitle(String title)
    {
        htmlHeader.addTitle(title);
    }

    
    /* Body parts */
    
    //TO BE COMMENTED OUT
    public HtmlBodySection addBodySection()
    {
        return htmlBody.addSection();
    }
    public HtmlBodySection addBodySection(HtmlBodySection section)
    {
        return htmlBody.addSection(section);
    }
    
}
