
package com.anthorra.html;


public class HtmlHead
{
    private String header;
    private String title;
    private String charset;
    private String stylesheet;
    private String jScript;
    
    private String constructHeader()
    {
        header = "<head>";
        
        if(title != null){header += title;}
        if(charset != null){header += "<meta charset=\"" + charset + "\">";}else{header += "<meta charset=\"UTF-8\">";}
        header += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">";
        if(stylesheet != null){header += "<link rel=\"stylesheet\" href=\"" + stylesheet + "\">";}
        if(jScript != null){header += "<script>" + jScript + "</script>";}
        header += "</head>";
        
        return header;
    }

    public String getHead()
    {
        return constructHeader();
    }
    
    public void addTitle(String title)
    {
        if (title != null)
        {
            this.title = "<title>" + title + "</title>";
        }
        
    }
    public void setCharset(String charset)
    {
        if(charset != null)
        {
            this.charset = charset;
        }
        else
        {
            this.charset = "UTF-8";
        }
        
    }
    public void setStylesheet(String path)
    {
        if(path != null)
        {
            stylesheet = path;
        }
    }

    public void setjScript(String jScript)
    {
        if(jScript != null)
        {
            this.jScript = jScript;
        }
        
    }

    
}
