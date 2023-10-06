
package com.anthorra.html;



/**
 *
 * @author Anthorra
 */
public abstract class HtmlBodyElement
{
    
    
    abstract String contructElement(); /* minden element html részlet kell hogy rendelkezzen egy egyedi construct metódussal, ahogyan létrejön a html kód*/
    public String getElement() /* minden element a getElement függvénnyel "épül fel" és áll össze egy html oldallá */
    {
        return contructElement();
    }
    
}
