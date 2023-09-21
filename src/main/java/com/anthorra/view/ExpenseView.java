
package com.anthorra.view;

import com.anthorra.html.HtmlBodyDiv;
import com.anthorra.html.HtmlBodySection;
import com.anthorra.html.HtmlPage;
import java.util.HashSet;
/**
 *
 * @author Anthorra
 */
public class ExpenseView
{
    public static HtmlPage getPageExpense(String[] optionsCategories, String[] optionsSubCategories, String categoriesJson, String[][] frTable, String message, Boolean isError)
    {
        String[] dummy = {" - válassz - "};
        
        HtmlPage page = new HtmlPage();
            
        /* HTML HEAD */
        page.setLang("hu");
        page.headerTitle("Expense Expert - Kiadások");
        page.getHtmlHeader().setStylesheet("https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css");
        page.getHtmlHeader().setjScript(getCascadeJs(categoriesJson));

        /* HTML BODY */
            /* HEADER */
            HtmlBodySection headerSection = PageStandardParts.getPageHeader();
            page.addBodySection(headerSection);

            /* NAVBAR */
            HtmlBodySection navbarSection = PageStandardParts.getPageNavbar();
            page.addBodySection(navbarSection);

            /* MAIN */
            HtmlBodySection mainSection = new HtmlBodySection();
                mainSection.addSectionHeader("Kiadások Kezelése")
                        .setSectionStyle("margin:15px");
                
            HtmlBodyDiv row = new HtmlBodyDiv(mainSection)
                                    .setDivClass("container")
                                    .setDivStyle("margin:30px")
                                    .addNestedDiv().setDivClass("row");
            mainSection.addDiv(row);
            
            /* LEFT COLUMN */
            HtmlBodyDiv mainLeftDiv = new HtmlBodyDiv().setDivClass("col-sm-3");
            
            /* SUCCESS DIV */
            HtmlBodyDiv success = new HtmlBodyDiv();
            if(message != null && !message.isEmpty())
            {
                if(isError)
                {
                    success.addAttribute("class", "alert alert-warning")
                        .addParagraph("<strong>Hiba! </strong>" + message);
                }
                else
                {
                    success.addAttribute("class", "alert alert-success")
                        .addParagraph("<strong>Sikeres! </strong>" + message);
                }
                
                mainLeftDiv.addNestedDiv(success);
            }
            
            
            row.addNestedDiv(mainLeftDiv);
                    
                mainLeftDiv
                    .addNestedDiv().addHeaderText("Új Költség / Bevétel Létrehozása", 4)
                        .addNestedDiv().setIsForm(true)
                            .addAttribute("method", "post")
                            .addAttribute("action", "ManageExpense")
                            .addAttribute("style", "margin:10px")
                                .addNestedDiv().addAttribute("class", "input-group mb-3")
                                    .addInputField("checkbox")
                                        .addAttribute("name", "isExpense")
                                        .addAttribute("value", "true")
                                        .addAttribute("class", "form-check-input")
                                        .addAttribute("checked", "checked")
                                        .addLabel("Kiadás?")
                                        .getParentDiv().getParentDiv()
                                .addNestedDiv().addAttribute("class", "input-group mb-3")
                                    .addInputField("number")
                                        .addAttribute("name", "amount")
                                        .addAttribute("class", "form-control")
                                        .addAttribute("placeholder", "Összeg")
                                        .addAttribute("max", "9999999999")
                                        .addAttribute("min", "0")
                                        .addAttribute("pattern", "[0-9]{9}")
                                        .addAttribute("required", "required")
                                        .getParentDiv().getParentDiv()
                                .addNestedDiv().addAttribute("class", "input-group mb-3")
                                    .addInputField("text").addAttribute("name", "comment")
                                    .addAttribute("class", "form-control")
                                    .addAttribute("placeholder", "komment")
                                    .getParentDiv().getParentDiv()
                                /*select lists*/
                                .addNestedDiv().addAttribute("class", "input-group mb-3")
                                    .addSelectList(dummy)
                                        .addAttribute("class", "form-control")
                                        .addAttribute("name", "mainCategory")
                                        .addAttribute("id", "type")
                                        .addAttribute("required", "required")
                                        .getParentDiv().getParentDiv()
                                .addNestedDiv().addAttribute("class", "input-group mb-3")
                                    .addSelectList(dummy)
                                        .addAttribute("class", "form-control")
                                        .addAttribute("name", "subCategory")
                                        .addAttribute("id", "subtype")
                                        .addAttribute("required", "required")
                                        .getParentDiv().getParentDiv()
                                /*date selector*/
                                .addNestedDiv().addAttribute("class", "input-group mb-3")
                                    .addInputField("date")
                                        .addAttribute("name", "datum")
                                        .addAttribute("class", "form-control")
                                        //.addAttribute("placeholder", "")
                                        .addAttribute("max", "2200-01-01")
                                        .addAttribute("min", "2000-01-01")
                                        .addAttribute("required", "required")
                                        .getParentDiv().getParentDiv()
                        
                                /*buttons*/
                                .addNestedDiv().addAttribute("class", "input-group mb-3")
                                    .addButton("Mentés", "submit")
                                        .addAttribute("class", "btn btn-success")
                                        .addAttribute("name", "requestType")
                                        .addAttribute("value", "requestSaveFrecord")
                                        .getParentDiv()
                                    .addButton("Reset", "reset")
                                        .addAttribute("class", "btn btn-secondary")
                                        .getParentDiv()
                                    ;
            
                            
                        
            /* RIGHT COLUMN */   
            HtmlBodyDiv mainRightDiv = new HtmlBodyDiv().setDivClass("col-sm-9");
            row.addNestedDiv(mainRightDiv);
                
            /* FIN.Record LIST */            
            HtmlBodyDiv mainListDiv = new HtmlBodyDiv(mainRightDiv)
                        .addHeaderText("Meglévő Kategóriák listája", 4)
                        ;   
            mainListDiv.addTable(frTable, true)
                    .addAttribute("style", "width:100%")
                    .addAttribute("class", "table table-hover"); 
            mainRightDiv.addNestedDiv(mainListDiv);
                
                
                
                
                page.addBodySection(mainSection);
        return page;
        
    }
    

    private static String getCascadeJs(String categoriesJson)
    {
        String jsCascade = "var subjectObject = \n" +
                        categoriesJson + ";\n" +
                        "\n" +
                        "window.onload = function() \n" +
                        "{\n" +
                        "    var subjectSel = document.getElementById(\"type\");\n" +
                        "    var topicSel = document.getElementById(\"subtype\");\n" +
                        "    \n" +
                        "    for (var x in subjectObject) \n" +
                        "    {\n" +
                        "      subjectSel.options[subjectSel.options.length] = new Option(x, x);\n" +
                        "    }\n" +
                        "    \n" +
                        "    subjectSel.onchange = function() \n" +
                        "    {\n" +
                        "        topicSel.length = 1;\n" +
                        "        for (var y in subjectObject[this.value]) \n" +
                        "        {\n" +
                        "          topicSel.options[topicSel.options.length] = new Option(y, y);\n" +
                        "        }\n" +
                        "    };\n" +
                        "    \n" +
                        "} ;";
        
        return jsCascade;
    }

    public HtmlBodyDiv getExpenseModal()
    {
        HtmlBodyDiv modal = new HtmlBodyDiv();
        modal.setDivClass("modal").addAttribute("id", "frModal");
        
        HtmlBodyDiv modalMain = modal.addNestedDiv().setDivClass("modal-dialog")
                .addNestedDiv().setDivClass("modal-content");
                
        HtmlBodyDiv modalHead = new HtmlBodyDiv().setDivClass("modal-header")
                .addHeaderText("Ez a modal header", 4, "modal-title")
                .addButton("&times;", "button")
                    .addAttribute("class", "close")
                    .addAttribute("data-dismiss", "modal")
                    .getParentDiv();
        
                
        
        modalMain.addNestedDiv(modalHead);
        
        return modal;
    }
}
