
package com.anthorra.controller;

import com.anthorra.expenseexpert.FinancialRecord;
import com.anthorra.html.HtmlPage;
import com.anthorra.model.CatModel;
import com.anthorra.model.ExpenseModel;
import com.anthorra.view.ExpenseView;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;

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
    
    private CatModel cm;
    ExpenseModel emodel;
    private String message;
    private boolean isError;
    private String errorMessage;
    private String[] optionsCategories;
    private String[] optionsSubCategories;
    private String categoriesJson;
    
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
        emodel = new ExpenseModel();
        optionsCategories = cm.getOptionsCategories();
        optionsSubCategories = cm.getOptionsSubCategories();
        categoriesJson = cm.getCategoriesJson();
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter())
        {
            HtmlPage page = new HtmlPage();
            page = ExpenseView.getPageExpense(optionsCategories, optionsSubCategories, categoriesJson, isError?errorMessage:message, isError);
            out.println(page.getPage());
        }
        message = "";isError = false;
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
        String requestType = request.getParameter("requestType");
        String mainCat = request.getParameter("mainCategory");
        String subCat = request.getParameter("subCategory");
        
        
        
        switch(requestType)
        {
            case "requestSaveFrecord":
                /*get categories*/
                errorMessage = "";
                int mainCatId = cm.getCategoryIdByName(mainCat);
                int subCatId = cm.getSubCategoryIdByName(subCat);
                if(mainCatId==-1 || subCatId==-1)
                {
                    isError = true;
                    if(mainCatId==-1){errorMessage+=" Kategória nem található!";}
                    if(subCatId==-1){errorMessage+=" Alkategória nem található!";}
                    
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
                    Boolean isExpenseBoolean = Boolean.parseBoolean(isExpense);
                    System.out.println("isExpense? " + isExpenseBoolean);
                    /*date*/
                    String rDate = request.getParameter("datum");
                    
                    int dbResponse = emodel.saveFinancialRecord(new FinancialRecord(amountDouble, isExpenseBoolean, mainCatId, subCatId, comment, rDate));
                    System.out.println("dbResponse: " + dbResponse);
                    message = (isExpenseBoolean?" Kiadás":" Bevétel") + " mentése sikeres! Azonosítója: " + dbResponse;
                }
                break;
 
        }
            
        
        
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
