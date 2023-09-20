
package com.anthorra.view;

import com.anthorra.html.HtmlBodyElement;
import com.anthorra.html.HtmlBodySection;
import com.anthorra.html.HtmlHref;

/**
 *
 * @author Anthorra
 */
public class PageStandardParts
{
     
    public static HtmlBodySection getPageHeader()
    {
        HtmlBodySection headerSection = new HtmlBodySection()
                    .setSectionType("header").addDiv()
                    .setDivStyle("margin-bottom:0; padding:10px")
                    .setDivClass("jumbotron text-center")
                    .addHeaderText("Expense Expert", 1)
                    .addParagraph("Üdvözöllek az Expense Expert alkalmazásban")
                    .getParentSection();
            
        return  headerSection;   
    }
    
    public static HtmlBodySection getPageNavbar()
    {
        HtmlBodySection navbarSection = new HtmlBodySection();
            HtmlBodyElement[] navListLinks = {
                new HtmlHref("index","Home").addAttribute("class", "nav-link"),
                new HtmlHref("ManageExpense","Kiadások").addAttribute("class", "nav-link"),
                new HtmlHref("ManageCategories","Kategóriák").addAttribute("class", "nav-link"),
                new HtmlHref("https://www.w3schools.com/bootstrap4/default.asp","W3SCHOOLS BS4")
                        .addAttribute("class", "nav-link")
                        .addAttribute("target", "_blank"),
                new HtmlHref("https://www.w3schools.com/html/default.asp","W3SCHOOLS HTML")
                        .addAttribute("class", "nav-link")
                        .addAttribute("target", "_blank")};
            
            navbarSection.setSectionType("nav")
                    .setSectionClass("navbar navbar-expand-sm bg-dark navbar-dark")
                    .addDiv().setDivClass("collapse navbar-collapse")
                    .addList(navListLinks).setListClass("navbar-nav").setListItemClass("nav-item")
                    ;
                
                
        return  navbarSection;
    }
    
    
}
