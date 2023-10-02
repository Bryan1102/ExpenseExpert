
package com.anthorra.view;

import com.anthorra.html.HtmlBodyDiv;
import com.anthorra.html.HtmlBodySection;
import com.anthorra.html.HtmlPage;


/**
 *
 * @author Anthorra
 */
public class CategoriesView
{
    //public static HtmlPage getPageCategories(ArrayList<Category> mainCategories, ArrayList<SubCategory> subCategories, String message)
    public static HtmlPage getPageCategories(String[][] optionsCategories, String[][] optionsSubCategories, String[][] categoriesTable, String message)
    {
        
        HtmlPage page = new HtmlPage();


        /* HTML HEAD */
        page.setLang("hu");
        page.headerTitle("Expense Expert - Kategóriák");
        page.getHtmlHeader().setStylesheet("https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css");

        /* HTML BODY */
            /* HEADER */
        HtmlBodySection headerSection = PageStandardParts.getPageHeader();
        page.addBodySection(headerSection);

            /* NAVBAR */
        HtmlBodySection navbarSection = PageStandardParts.getPageNavbar();
        page.addBodySection(navbarSection);

        /* MAIN */
        HtmlBodyDiv mainRow = page.addBodySection()
                        .addSectionHeader("Kategóriák Menedzselése")
                        .setSectionType("main")
                        .setSectionStyle("margin:15px")
                            .addDiv()
                                .setDivClass("container")
                                .setDivStyle("margin:30px")
                                .addNestedDiv().setDivClass("row");




        /* LEFT COLUMN */
        HtmlBodyDiv mainLeftDiv = new HtmlBodyDiv(mainRow).setDivClass("col-sm-6");
        mainRow.addNestedDiv(mainLeftDiv);


        /* SUCCESS DIV */
        HtmlBodyDiv success = new HtmlBodyDiv();
        if(message != null && !message.isEmpty())
        {
            success.addAttribute("class", "alert alert-success")
                    .addParagraph("<strong>Sikeres!</strong>" + message);
            mainLeftDiv.addNestedDiv(success);
        }

        /* Create new category form */
        HtmlBodyDiv mainCreateDiv = new HtmlBodyDiv();
        mainCreateDiv.addHeaderText("Új Kategória Létrehozása", 4)
            .addNestedDiv()
                .setIsForm(true)
                .addAttribute("method", "post")
                .addAttribute("action", "ManageCategories")
                .addAttribute("style", "margin:10px")
                .addNestedDiv().addAttribute("class", "input-group mb-3")
                    .addInputField("text")
                        .addAttribute("class", "form-control")
                        .addAttribute("name", "newCategory")
                        .addAttribute("placeholder", "Új kategória neve")
                        .addAttribute("required", "required")
                        .getParentDiv()
                    .addButton("Mentés", "submit")
                        .addAttribute("class", "btn btn-success")
                        .addAttribute("name", "requestType")
                        .addAttribute("value", "requestNewCategory")
                        ;

        /* Create new subcategory form */
        HtmlBodyDiv mainCreateSubDiv = new HtmlBodyDiv();
        mainCreateSubDiv.addHeaderText("Új Alkategória Létrehozása", 4)
            .addNestedDiv()
                .setIsForm(true)
                    .addAttribute("method", "post")
                    .addAttribute("action", "ManageCategories")
                    .addAttribute("style", "margin:10px")
                .addNestedDiv().addAttribute("class", "input-group mb-3")
                    .addSelectList(optionsCategories)
                        .addAttribute("class", "form-control")
                        .addAttribute("name", "mainCategory")
                        .getParentDiv().getParentDiv()
                .addNestedDiv().addAttribute("class", "input-group mb-3")
                    .addInputField("text")
                        .addAttribute("class", "form-control")
                        .addAttribute("name", "newSubCategory")
                        .addAttribute("placeholder", "Új alkategória neve")
                        .addAttribute("required", "required")
                        .getParentDiv()
                    .addButton("Mentés", "submit")
                        .addAttribute("class", "btn btn-success")
                        .addAttribute("name", "requestType")
                        .addAttribute("value", "requestNewSubCategory")
                        ;

        /* Rename category form */                    
        HtmlBodyDiv mainRenameDiv = new HtmlBodyDiv();
        mainRenameDiv.addHeaderText("Kategória Átnevezése", 4)
            .addNestedDiv()
                .setIsForm(true)
                    .addAttribute("method", "post")
                    .addAttribute("action", "ManageCategories")
                    .addAttribute("style", "margin:10px")
                .addNestedDiv().addAttribute("class", "input-group mb-3")
                    .addSelectList(optionsCategories)
                        .addAttribute("class", "form-control")
                        .addAttribute("name", "mainCategory")
                        .getParentDiv().getParentDiv()
                .addNestedDiv().addAttribute("class", "input-group mb-3")
                    .addInputField("text")
                        .addAttribute("class", "form-control")
                        .addAttribute("name", "renameCategoryTo")
                        .addAttribute("placeholder", "Kategória új neve")
                        .addAttribute("required", "required")
                        .getParentDiv()
                    .addButton("Módosítás", "submit")
                        .addAttribute("class", "btn btn-success")
                        .addAttribute("name", "requestType")
                        .addAttribute("value", "requestRenameCategory")
                        .getParentDiv()
                        ;

        /* Rename subcategory form */                    
        HtmlBodyDiv mainRenameSubDiv = new HtmlBodyDiv();
        mainRenameSubDiv.addHeaderText("Alkategória Átnevezése", 4)
            .addNestedDiv()
                .setIsForm(true)
                    .addAttribute("method", "post")
                    .addAttribute("action", "ManageCategories")
                    .addAttribute("style", "margin:10px")
                .addNestedDiv().addAttribute("class", "input-group mb-3")
                    .addSelectList(optionsCategories)
                        .addAttribute("class", "form-control")
                        .addAttribute("name", "mainCategory")
                        .getParentDiv().getParentDiv()
                .addNestedDiv().addAttribute("class", "input-group mb-3")
                    .addSelectList(optionsSubCategories)
                        .addAttribute("class", "form-control")
                        .addAttribute("name", "subCategory")
                        .getParentDiv().getParentDiv()
                .addNestedDiv().addAttribute("class", "input-group mb-3")
                    .addInputField("text")
                        .addAttribute("class", "form-control")
                        .addAttribute("name", "renameSubCategoryTo")
                        .addAttribute("placeholder", "Alkategória új neve")
                        .addAttribute("required", "required")
                        .getParentDiv()
                    .addButton("Módosítás", "submit")
                        .addAttribute("class", "btn btn-success")
                        .addAttribute("name", "requestType")
                        .addAttribute("value", "requestRenameSubCategory")
                        .getParentDiv()
                    ;

        /* DELETE subcategory form */                    
        HtmlBodyDiv mainDeleteDiv = new HtmlBodyDiv();
        mainDeleteDiv.addHeaderText("Kategória Törlése", 4)
            .addNestedDiv()
                .setIsForm(true)
                    .addAttribute("method", "post")
                    .addAttribute("action", "ManageCategories")
                    .addAttribute("style", "margin:10px")
                .addNestedDiv().addAttribute("class", "input-group mb-3")
                    .addSelectList(optionsCategories)
                        .addAttribute("class", "form-control")
                        .addAttribute("name", "mainCategory")
                        .getParentDiv()
                    .addButton("Törlés", "submit")
                        .addAttribute("class", "btn btn-danger")
                        .addAttribute("name", "requestType")
                        .addAttribute("value", "requestDeleteCategory")
                        .getParentDiv().getParentDiv()
            .addNestedDiv()
                .setIsForm(true)
                .addNestedDiv().addAttribute("class", "input-group mb-3")
                    .addSelectList(optionsSubCategories)
                            .addAttribute("class", "form-control")
                            .addAttribute("name", "subCategory")
                            .getParentDiv()//.getParentDiv()  
                    .addButton("Törlés", "submit")
                            .addAttribute("class", "btn btn-danger")
                            .addAttribute("name", "requestType")
                            .addAttribute("value", "requestDeleteSubCategory")
                            .getParentDiv().getParentDiv()
                ;


        mainLeftDiv.addNestedDiv(mainCreateDiv);
        mainLeftDiv.addNestedDiv(mainCreateSubDiv);
        mainLeftDiv.addNestedDiv(mainRenameDiv);
        mainLeftDiv.addNestedDiv(mainRenameSubDiv);
        mainLeftDiv.addNestedDiv(mainDeleteDiv);


        /* RIGHT COLUMN */    
        HtmlBodyDiv mainRightDiv = new HtmlBodyDiv(mainRow).setDivClass("col-sm-6");
        mainRow.addNestedDiv(mainRightDiv);
        /* CATEGORY LIST */            
        HtmlBodyDiv mainListDiv = new HtmlBodyDiv(mainRightDiv)
                    .addHeaderText("Meglévő Kategóriák listája", 4)
                    ;   
        mainListDiv.addTable(categoriesTable, true)
                .addAttribute("style", "width:100%")
                .addAttribute("class", "table table-hover"); 
        mainRightDiv.addNestedDiv(mainListDiv);


        return page;
    }
             
}
