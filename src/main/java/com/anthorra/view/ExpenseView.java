
package com.anthorra.view;

import com.anthorra.html.HtmlBodySection;
import com.anthorra.html.HtmlPage;

/**
 *
 * @author Anthorra
 */
public class ExpenseView
{
    
    public static HtmlPage getPageExpense()
    {
        HtmlPage page = new HtmlPage();
            
        /* HTML HEAD */
        page.setLang("hu");
        page.headerTitle("Expense Expert - Kiadások");
        page.getHtmlHeader().setStylesheet("https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css");

        /* HTML BODY */
            /* HEADER */
            HtmlBodySection headerSection = PageStandardParts.getPageHeader();
            page.addBodySection(headerSection);

            /* NAVBAR */
            HtmlBodySection navbarSection = PageStandardParts.getPageNavbar();
            page.addBodySection(navbarSection);

            /* MAIN */
            HtmlBodySection mainSection = new HtmlBodySection();
                mainSection.addSectionHeader("Kiadások Kezelése");
                mainSection
                    .addDiv()
                        .addNestedDiv().setIsForm(true)
                            .addAttribute("method", "post")
                            .addAttribute("action", "AddExpense")
                            .addAttribute("style", "margin:10px")
                                .addNestedDiv().addAttribute("class", "input-group mb-3")
                        
                        
                        
                        
                        ;
            page.addBodySection(mainSection);
        return page;
        
    }
    

}
