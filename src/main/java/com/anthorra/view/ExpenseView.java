
package com.anthorra.view;

import com.anthorra.html.HtmlBodyDiv;
import com.anthorra.html.HtmlBodySection;
import com.anthorra.html.HtmlPage;
import com.anthorra.expenseexpert.Category;
import com.anthorra.expenseexpert.SubCategory;
import java.util.ArrayList;
/**
 *
 * @author Anthorra
 */
public class ExpenseView
{
    
    public static HtmlPage getPageExpense(ArrayList<Category> mainCategories, ArrayList<SubCategory> subCategories, String message)
    {
        String[] optionsCategories = new String[mainCategories.size()];
        String[] optionsSubCategories = new String[subCategories.size()];
        
            
            for(int i = 0; i < mainCategories.size(); i++)
            {
                optionsCategories[i] = mainCategories.get(i).getCategoryName();
            }    
        
            int i = 0;
            for(SubCategory sc : subCategories)
            {
                optionsSubCategories[i] = sc.getCategoryName();
                i += 1;
            }
        String[] dummy = {" - válassz - "};
        
        HtmlPage page = new HtmlPage();
            
        /* HTML HEAD */
        page.setLang("hu");
        page.headerTitle("Expense Expert - Kiadások");
        page.getHtmlHeader().setStylesheet("https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css");
        page.getHtmlHeader().setjScript(getCascadeJs(mainCategories, subCategories));

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
            HtmlBodyDiv mainLeftDiv = new HtmlBodyDiv().setDivClass("col-sm-4");
            row.addNestedDiv(mainLeftDiv);
                    
                mainLeftDiv
                    .addNestedDiv().addHeaderText("Új Költség / Bevétel Létrehozása", 4)
                        .addNestedDiv().setIsForm(true)
                            .addAttribute("method", "post")
                            .addAttribute("action", "AddExpense")
                            .addAttribute("style", "margin:10px")
                                .addNestedDiv().addAttribute("class", "input-group mb-3")
                                    .addInputField("checkbox")
                                        .addAttribute("name", "isExpense")
                                        .addAttribute("value", "true")
                                        .addAttribute("class", "form-check-input")
                                        .addLabel("Kiadás?")
                                        .getParentDiv().getParentDiv()
                                .addNestedDiv().addAttribute("class", "input-group mb-3")
                                    .addInputField("number").addAttribute("name", "amount")
                                        .addAttribute("class", "form-control")
                                        .addAttribute("placeholder", "Összeg")
                                        .addAttribute("max", "9999999999")
                                        .addAttribute("min", "0")
                                        .addAttribute("pattern", "[0-9]{9}")
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
                                        .getParentDiv().getParentDiv()
                                .addNestedDiv().addAttribute("class", "input-group mb-3")
                                    .addSelectList(dummy)
                                        .addAttribute("class", "form-control")
                                        .addAttribute("name", "subCategory")
                                        .addAttribute("id", "subtype")
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
            HtmlBodyDiv mainRightDiv = new HtmlBodyDiv().setDivClass("col-sm-8");
            row.addNestedDiv(mainRightDiv);
                mainRightDiv.addParagraph("ez a jobb oldal lesz");
                
                
                
                
                
                
                page.addBodySection(mainSection);
        return page;
        
    }
    
    private static String getTypesAsJson(ArrayList<Category> mainCategories, ArrayList<SubCategory> subCategories)
    {
        String typesJson = "{";
        int catSize = mainCategories.size();
        int subCatSize = subCategories.size();
        int parentId = 0;
        
        for(int i = 0; i < catSize; i++)
            {
                typesJson += " \""  + mainCategories.get(i).getCategoryName() +  "\"";
                
                typesJson += ": {";
                for(int j = 0; j < subCatSize; j++)
                {
                    if(parentId==subCategories.get(j).getParentId() && parentId==mainCategories.get(i).getId())
                    {
                        typesJson += ",";
                    }
                    parentId = subCategories.get(j).getParentId();
                    
                    if(parentId==mainCategories.get(i).getId())
                    {
                        typesJson += " \""  + subCategories.get(j).getCategoryName() +  "\": []";
                        //if(j<subCatSize-1){typesJson += ",";}
                    }
                }
                typesJson += " }";
                if(i<catSize-1){typesJson += ",";}
            }
        typesJson += " }";
        //System.out.println(typesJson);
        
        return typesJson;
    }
    private static String getCascadeJs(ArrayList<Category> mainCategories, ArrayList<SubCategory> subCategories)
    {
        String jsCascade = "var subjectObject = \n" +
                        getTypesAsJson(mainCategories, subCategories) + ";\n" +
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

}
