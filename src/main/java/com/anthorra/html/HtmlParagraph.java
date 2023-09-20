
package com.anthorra.html;

/**
 *
 * @author Anthorra
 */
public class HtmlParagraph extends HtmlBodyElement
{
    private String text;

    /* CONSTRUCTOR */
    public HtmlParagraph(String text)
    {
        this.text = text;
    }
    
    
    @Override
    String contructElement()
    {
        String paragraph = "<p>";
        paragraph += text;
        paragraph += "</p>";
        return paragraph;
    }

    

    
}


