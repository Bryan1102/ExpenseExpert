
package com.anthorra.view;

import com.anthorra.html.HtmlBodySection;
import com.anthorra.html.HtmlPage;

/**
 *
 * @author Anthorra
 */
public class IndexView
{
    public static HtmlPage getIndexPage()
    {
        HtmlPage page = new HtmlPage();
        /* HTML HEAD */
        page.setLang("hu");
        page.headerTitle("Expense Expert");
        page.getHtmlHeader().setStylesheet("https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css");

        /* HTML BODY */
            /* HEADER */
        HtmlBodySection headerSection = PageStandardParts.getPageHeader();
        page.addBodySection(headerSection);

            /* NAVBAR */
        HtmlBodySection navbarSection = PageStandardParts.getPageNavbar();
        page.addBodySection(navbarSection);
        
        page.addBodySection().setSectionType("main")
                .addDiv()
                    .addHeaderText("Üdvözöllek!", 2);
        page.addBodySection().setSectionType("article")
                .addDiv()
                    .addParagraph("Ez az oldal a kiadások és bevételek menedzselésében hivatott segíteni. Ezeket kezelheted kategóriákban és adhatsz meg értékbel vagy százalékos limitet is.");
        
        return page;
    }
}
