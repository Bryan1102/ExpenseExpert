
package com.anthorra.controller;

import com.anthorra.expenseexpert.Category;
import com.anthorra.expenseexpert.FinancialRecord;
import com.anthorra.expenseexpert.SubCategory;
import com.anthorra.model.CategoryModel;
import com.anthorra.html.HtmlPage;
import com.anthorra.view.CategoriesView;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author Anthorra
 */
@WebServlet(name = "ManageCategories", urlPatterns =
{
    "/ManageCategories"
})
public class ManageCategories extends HttpServlet
{
    private ArrayList<Category> mainCategories;
    private ArrayList<SubCategory> subCategories;
    private CategoryModel cm;
    private String[][] optionsCategories;
    private String[][] optionsSubCategories;
    private String[][] categoriesTable;
    private String message;
    private String messageError;
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        cm = new CategoryModel();
        mainCategories = cm.getMainCategories();
        subCategories = cm.getSubCategories();
        optionsCategories = cm.getOptionsCategories();
        optionsSubCategories = cm.getOptionsSubCategories();
        categoriesTable = cm.getCategoriesTable();
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter())
        {
            
            HtmlPage page = new HtmlPage();
            page = CategoriesView.getPageCategories(optionsCategories, optionsSubCategories, categoriesTable, message);
            
            out.println(page.getPage());
            
        }
        
        message = "";
    }
    
    /* GET */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        
        processRequest(request, response);
    }

    //////////////////////////////////////////////////////////////////////////// 
    /* POST */
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        /*get request type for the switch*/
        String requestType = request.getParameter("requestType");
        String parentCatName = "";
        String subCatName = "";
        int parentCatId = -1;
        int subCatId = -1;
        
        /*get selected mainCategory name and id*/
        String parentCat = request.getParameter("mainCategory");
        if(parentCat != null)
        {
            parentCatId = Integer.parseInt(parentCat);
            parentCatName = cm.getCategoryNameById(parentCatId);
        }
        
        /*get selected subCategory name and id*/
        String subCat = request.getParameter("subCategory");
        if(subCat != null)
        {
            subCatId = Integer.parseInt(subCat);
            subCatName = cm.getSubCategoryNameById(subCatId);
        }
        
        /*decide what to do based on request type - request type is assigned to each button in html */
        switch(requestType)
        {
            case "requestNewCategory":
                String newCat = request.getParameter("newCategory");
                Category cat = new Category(0, newCat);
                int newCatId = cm.saveCategory(cat);
                message = "\"" + newCat + "\" mentése sikeres! Azonosítója: " + newCatId;
                break;
                
            case "requestNewSubCategory":
                String newSubCat = request.getParameter("newSubCategory");
                SubCategory sc = new SubCategory(0, parentCatId, newSubCat);
                int newSubCatId = cm.saveSubCategory(sc);
                message = "\"" + newSubCat + "\" mentése sikeres! Azonosítója: " + newSubCatId;
                break;
                
            case "requestRenameCategory":
                String renameCategoryTo = request.getParameter("renameCategoryTo");
                if(!renameCategoryTo.isBlank())
                {
                    int renamedId = cm.renameCategory(parentCatId, renameCategoryTo);
                    message = " " + parentCatName + " sikeresen átnevezve erre: " + renameCategoryTo /*+ "(rows=" + renamedId + ")"*/;
                }
                break;
            
            case "requestRenameSubCategory":
                String renameSubCategoryTo = request.getParameter("renameSubCategoryTo");
                if(!renameSubCategoryTo.isBlank())
                {
                    int renamedId = cm.renameSubCategory(subCatId, renameSubCategoryTo);
                    message = " " + subCatName + " sikeresen átnevezve erre: " + renameSubCategoryTo /*+ "(rows=" + renamedId + ")"*/;
                }
                break; 
                
            case "requestDeleteCategory":
                int deleteID = cm.deleteCategory(parentCatId);
                message = parentCatName + "(id=" + parentCatId + " sikeresen törölve";
                break;
            case "requestDeleteSubCategory":
                int deleteSubID = cm.deleteSubCategory(subCatId);
                message = subCatName + "(id=" + subCatId + " sikeresen törölve";
                break;
            
        }
        
        /* need redirect, otherwise browser refresh will re-call POST method again */
        /* https://stackoverflow.com/questions/21204189/servlet-cancel-post-request-in-refresh */
        response.sendRedirect("ManageCategories");
        //processRequest(request, response);
    }

    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
