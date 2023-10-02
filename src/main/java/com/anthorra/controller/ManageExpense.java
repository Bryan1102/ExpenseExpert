
package com.anthorra.controller;

import com.anthorra.expenseexpert.Category;
import com.anthorra.expenseexpert.FinancialRecord;
import com.anthorra.expenseexpert.SubCategory;
import com.anthorra.html.HtmlPage;
import com.anthorra.model.CategoryModel;
import com.anthorra.model.ExpenseModel;
import com.anthorra.view.ExpenseView;
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
@WebServlet(name = "ManageExpense", urlPatterns =
{
    "/ManageExpense"
})
public class ManageExpense extends HttpServlet
{
    
    private CategoryModel cm;
    private ExpenseModel emodel;
    private String message;
    private boolean isError;
    private String errorMessage;
    private String[][] optionsCategories;
    private String[][] optionsSubCategories;
    private String categoriesJson;
    private String[][] frTable;
    private FinancialRecord fr;
    String editCat, editSubCat;
    
    private ArrayList<Category> mainCategories;
    private ArrayList<SubCategory> subCategories;
    
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
        emodel = new ExpenseModel();
        
        optionsCategories = cm.getOptionsCategories(); /* select list value-option feltöltéshez szükséges String [][] elemek */
        optionsSubCategories = cm.getOptionsSubCategories();
        
        categoriesJson = cm.getCategoriesJson();
        emodel.getFrList();
        frTable = emodel.getFrListAsTable(); /* A FinancialRecord entitások listája táblázatosan - így fogadja a HTMLTable az inputot */
        
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter())
        {
            HtmlPage page = new HtmlPage();
            page = ExpenseView.getPageExpense(optionsCategories, optionsSubCategories, categoriesJson, frTable, isError?errorMessage:message, isError, fr, editCat, editSubCat);
            out.println(page.getPage());
        }
        
        message = "";
        isError = false;
        fr = null;
    }
    
    
    

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
        
        /* COMMENT: Minden gombnak van egy név-érték párosa, ami RequestType-value, ami value a kért akció pl.: requestSave */
        String requestType = request.getParameter("requestType");
        String mainCat = request.getParameter("mainCategory");
        String subCat = request.getParameter("subCategory");
        
        /* REQUEST: Meglévő record szerkesztése */
        if(requestType.indexOf("Edit=") != -1)
        {
            int frId = Integer.parseInt(requestType.replaceAll("Edit=", ""));
            this.fr = emodel.getFinancialRecordById(frId);
            this.editCat = cm.getCategoryNameById(fr.getCategory());
            this.editSubCat = cm.getSubCategoryNameById(fr.getSubCategory());
        }
        else
        {
            switch(requestType)
            {
            case "requestSaveFrecord": /* REQUEST: új rekord mentése */
                /*get categories*/
                errorMessage = "";
                
                int mainCatId = Integer.parseInt(mainCat);
                int subCatId = Integer.parseInt(subCat);
                
                if(mainCatId < 0 || subCatId < 0)
                {
                    isError = true;
                    if(mainCatId < 0){errorMessage+=" Kategória nem található!";}
                    if(subCatId < 0){errorMessage+=" Alkategória nem található!";}
                    
                }
                else
                {
                    /*amount*/
                    String amount = request.getParameter("amount");
                    double amountDouble = Double.parseDouble(amount);
                    System.out.println("amount=" + amountDouble);

                    /*comment*/
                    String comment = request.getParameter("comment");
                    System.out.println("comment=" + comment);

                    /*isExpense*/
                    String isExpense = request.getParameter("isExpense");
                    if(isExpense==null)
                            {
                                isExpense = "false";
                            }
                    Boolean isExpenseBoolean = Boolean.valueOf(isExpense);
                    System.out.println("isExpense? " + isExpenseBoolean);
                    /*date*/
                    String rDate = request.getParameter("datum");
                    
                    int dbResponse = emodel.saveFinancialRecord(new FinancialRecord(amountDouble, isExpenseBoolean, mainCatId, subCatId, comment, rDate));
                    System.out.println("dbResponse: " + dbResponse);
                    message = (isExpenseBoolean?" Kiadás":" Bevétel") + " mentése sikeres! Azonosítója: " + dbResponse;
                }
                break;
                
            
            } /*end of switch*/
        } /*end of if*/
        
        
            
        
        
        //processRequest(request, response);
        response.sendRedirect("ManageExpense");
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
