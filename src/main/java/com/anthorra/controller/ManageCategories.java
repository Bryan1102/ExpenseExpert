
package com.anthorra.controller;

import com.anthorra.expenseexpert.Category;
import com.anthorra.expenseexpert.FinancialRecord;
import com.anthorra.expenseexpert.SubCategory;
import com.anthorra.model.CatModel;
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
    private CatModel cm;
    private String[] optionsCategories;
    private String[] optionsSubCategories;
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
        cm = new CatModel();
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
        
        /*get selected mainCategory name and id*/
        String parentCat = request.getParameter("mainCategory");
        
        int parentCatId = 0;
        if(parentCat != null)
        {
            for(Category c : mainCategories)
                    {
                        if(parentCat.equals(c.getCategoryName()) )
                        {
                            parentCatId = c.getId();
                            break;
                        }
                    }
        }
        
        /*get selected subCategory name and id*/
        String subCat = request.getParameter("subCategory");
        int subCatId = 0;
        if(subCat != null)
        {
            for(SubCategory sc : subCategories)
                    {
                        if(subCat.equals(sc.getCategoryName()) )
                        {
                            subCatId = sc.getId();
                            break;
                        }
                    }
        }
        
        /*decide what to do based on request type*/
        switch(requestType)
        {
            case "requestNewCategory":
                String newCat = request.getParameter("newCategory");
                System.out.println("newCategory = " + newCat);
                Category cat = new Category(0, newCat);
                int newCatId = cm.saveCategory(cat);
                System.out.println("servlet dbResponse = " + newCatId);
                message = "\"" + newCat + "\" mentése sikeres! Azonosítója: " + newCatId;
                break;
                
            case "requestNewSubCategory":
                String newSubCat = request.getParameter("newSubCategory");
                //String parentCat = request.getParameter("mainCategory");
                
                SubCategory sc = new SubCategory(0, parentCatId, newSubCat);
                int newSubCatId = cm.saveSubCategory(sc);
                message = "\"" + newSubCat + "\" mentése sikeres! Azonosítója: " + newSubCatId;
                break;
                
            case "requestRenameCategory":
                String renameCategoryTo = request.getParameter("renameCategoryTo");
                System.out.println("renameCategory: " + parentCat + " TO this: " + renameCategoryTo);
                if(!renameCategoryTo.isBlank())
                {
                    int renamedId = cm.renameCategory(parentCatId, renameCategoryTo);
                    message = parentCat + " sikeresen átnevezve erre: " + renameCategoryTo + "(id=" + renamedId + ")";
                }
                break;
            
            case "requestRenameSubCategory":
                String renameSubCategoryTo = request.getParameter("renameSubCategoryTo");
                //System.out.println("renameCategory: " + parentCat + " TO this: " + renameCategoryTo);
                if(!renameSubCategoryTo.isBlank())
                {
                    int renamedId = cm.renameSubCategory(subCatId, renameSubCategoryTo);
                    message = subCat + " sikeresen átnevezve erre: " + renameSubCategoryTo + "(id=" + renamedId + ")";
                }
                break; 
                
            case "requestDeleteCategory":
                
                System.out.println("Delete Category: " + parentCat + " id=" + parentCatId);
                int deleteID = cm.deleteCategory(parentCatId);
                message = parentCat + "(id=" + parentCatId + " sikeresen törölve (rows=" + deleteID + ")";
                break;
            case "requestDeleteSubCategory":
                
                System.out.println("Delete Category: " + subCat + " id=" + subCatId);
                int deleteSubID = cm.deleteSubCategory(subCatId);
                message = subCat + "(id=" + subCatId + " sikeresen törölve (rows=" + deleteSubID + ")";
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
