
package com.anthorra.view;

import com.anthorra.expenseexpert.FinancialRecord;
import com.anthorra.html.HtmlBodyDiv;
import com.anthorra.html.HtmlBodySection;
import com.anthorra.html.HtmlButton;
import com.anthorra.html.HtmlPage;
/**
 *
 * @author Anthorra
 */
public class ExpenseView
{
    public static HtmlPage getPageExpense(String[][] optionsCategories, String[][] optionsSubCategories, 
                                        String categoriesJson, String[][] frTable, 
                                        String message, Boolean isError, FinancialRecord fr)
    {
        //String[] dummy = {" - válassz - "}; /* kezdeti érték a beágyazott kategóriákhoz, jelenleg nincs használatban */
        boolean isEdit = fr!=null;
        
        HtmlPage page = new HtmlPage();
            
        /* HTML HEAD */
        page.setLang("hu");
        page.headerTitle("Expense Expert - Kiadások");
        page.getHtmlHeader().setStylesheet("https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css");
        //page.getHtmlHeader().setjScript(getCascadeJs(categoriesJson)); /* az egymásba ágyazott kategóriák működtetéséhez szükséges Jscript */
        

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
            
            /* SUCCESS / ERROR DIV */
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
                    .addNestedDiv()
                        .addHeaderText(isEdit?"Költség / Bevétel Szerkesztése":"Új Költség / Bevétel Létrehozása", 4)
                        .addNestedDiv().setIsForm(true)
                            .addAttribute("method", "post")
                            .addAttribute("action", "ManageExpense")
                            .addAttribute("style", "margin:10px")
                                .addNestedDiv().addAttribute("class", "input-group mb-3")
                                    .addInputField("hidden").addAttribute("name", "frId")
                                        .addAttribute("class", "form-control")
                                        .addAttribute("placeholder", "id")
                                        .addAttribute("value", isEdit?fr.getId()+"":"")
                                        .getParentDiv()
                                    .addNestedDiv().addAttribute("class", "input-group-prepend")
                                        .addNestedDiv().addAttribute("class", "input-group-text")
                                            .addInputField("checkbox")
                                                .addAttribute("name", "isExpense")
                                                .addAttribute("value", "true")
                                                .addAttribute("class", "form-check-input")
                                                .addAttribute("checked", "checked")
                                                .addAttribute("style", "z-index:0")
                                                //.addLabel("Kiadás?")
                                            .getParentDiv()
                                            .getParentDiv()
                                            .getParentDiv()
                                            .addNestedDiv().addInputField("text")
                                                                .addAttribute("placeholder", "Kiadás?")
                                                                .addAttribute("style", "z-index:0")
                                                                .addAttribute("readonly", "")
                                                                .getParentDiv()
                                        .getParentDiv()
                                    .getParentDiv()
                                .addNestedDiv().addAttribute("class", "input-group mb-3")
                                    .addInputField("number")
                                        .addAttribute("name", "amount")
                                        .addAttribute("class", "form-control")
                                        .addAttribute("placeholder", "Összeg")
                                        .addAttribute("max", "9999999999")
                                        .addAttribute("min", "0")
                                        .addAttribute("pattern", "[0-9]{9}")
                                        .addAttribute("required", "required")
                                        .addAttribute("value", isEdit?String.valueOf(fr.getAmount()):"0")
                                        .getParentDiv().getParentDiv()
                                .addNestedDiv().addAttribute("class", "input-group mb-3")
                                    .addInputField("text").addAttribute("name", "comment")
                                        .addAttribute("class", "form-control")
                                        .addAttribute("placeholder", "komment")
                                        .addAttribute("value", isEdit?fr.getComment():"")
                                    .getParentDiv().getParentDiv()
                                /*select lists*/
                                .addNestedDiv().addAttribute("class", "input-group mb-3")
                                    .addSelectList(optionsCategories)
                                        .addAttribute("class", "form-control")
                                        .addAttribute("name", "mainCategory")
                                        .addAttribute("id", "type")
                                        .addAttribute("required", "required")
                                        .setSelectedOption(isEdit?fr.getCategoryName():"")
                                        .getParentDiv().getParentDiv()
                                .addNestedDiv().addAttribute("class", "input-group mb-3")
                                    .addSelectList(optionsSubCategories)
                                        .addAttribute("class", "form-control")
                                        .addAttribute("name", "subCategory")
                                        .addAttribute("id", "subtype")
                                        .addAttribute("required", "required")
                                        .setSelectedOption(isEdit?fr.getSubcategoryName():"")
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
                                        .addAttribute("value", isEdit?fr.getRealizedDate():"")
                                        .getParentDiv().getParentDiv()
                        
                                /*buttons*/
                                .addNestedDiv(getButtonsDiv(isEdit));
                            
                        
            /* RIGHT COLUMN */   
            HtmlBodyDiv mainRightDiv = new HtmlBodyDiv().setDivClass("col-sm-9");
            row.addNestedDiv(mainRightDiv);
            
            
            /* F.Record LIST */            
            HtmlBodyDiv mainListDiv = new HtmlBodyDiv(mainRightDiv)
                        .addHeaderText("Kiadások / Bevételek listája", 4)
                        ;   
            mainListDiv.addTable(frTable, true)
                    .addAttribute("style", "width:100%")
                    .addAttribute("class", "table table-hover")
                    .addAttribute("id", "frTable")
                    .addNestedDiv(getTableEditButton(), "Szerkesztés"); 
            mainRightDiv.addNestedDiv(mainListDiv);
                
                
        page.addBodySection(mainSection);
        return page;
    }
    

    /* Jscript = a beágyazott kategóriákat előre megkapja egy JSON stringben és a kiválasztás alapján listázza az alkategóriákat */
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

    
    /* Gombokat összefogó div */
    private static HtmlBodyDiv getButtonsDiv(boolean isEdit)
    {
        HtmlBodyDiv buttonsDiv = new HtmlBodyDiv();
        buttonsDiv.addAttribute("class", "input-group mb-3");
        buttonsDiv.addButton(isEdit?getSaveEditButton():getSaveButton())
                   .addButton(isEdit?getCancelButton():getResetButton());
        
        if(isEdit)
        {
            buttonsDiv.addButton(getDeleteButton());
        }
        
        return buttonsDiv;
    }
    
    /* Új form gombjai */
    private static HtmlButton getSaveButton()
    {
        HtmlButton button = new HtmlButton("Mentés", "submit");
        button
            .addAttribute("class", "btn btn-success")
            .addAttribute("name", "requestType")
            .addAttribute("value", "requestSaveFrecord");
        return button;
    }
    private static HtmlButton getResetButton()
    {
        HtmlButton button = new HtmlButton("Reset", "reset");
        button
            .addAttribute("class", "btn btn-secondary");
        return button;
    }
    
    /* Szerkesztés gombjai */
    private static HtmlButton getSaveEditButton()
    {
        HtmlButton button = new HtmlButton("Módosítás", "submit");
        button
            .addAttribute("class", "btn btn-success")
            .addAttribute("name", "requestType")
            .addAttribute("value", "requestSaveEditFrecord");
        return button;
    }
    private static HtmlButton getCancelButton()
    {
        HtmlButton button = new HtmlButton("Mégsem", "submit");
        button
            .addAttribute("class", "btn btn-secondary")
            .addAttribute("name", "requestType")
            .addAttribute("value", "requestCancel");
        return button;
    }
    private static HtmlButton getDeleteButton()
    {
        HtmlButton button = new HtmlButton("Törlés", "submit");
        button
            .addAttribute("class", "btn btn-danger")
            .addAttribute("name", "requestType")
            .addAttribute("value", "requestDeleteFr");
        return button;
    }
    
    /* Create subDiv for Table button - EDIT */
    private static HtmlBodyDiv getTableEditButton()
    {
        HtmlBodyDiv tableButton = new HtmlBodyDiv()
                    .setIsForm(true)
                        .addAttribute("method", "post")
                        .addAttribute("action", "ManageExpense")
                    .addButton("Szerkesztés", "submit")
                        .addAttribute("class", "btn btn-info")
                        .addAttribute("name", "requestType")
                        .addAttribute("value", "#Edit#")
                        .getParentDiv();
        return tableButton;
    }
}
