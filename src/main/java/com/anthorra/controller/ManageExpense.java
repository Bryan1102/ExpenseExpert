
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
    private ExpenseModel emodel;
    private String message;
    private boolean isError;
    private String errorMessage;
    private String[] optionsCategories;
    private String[] optionsSubCategories;
    private String categoriesJson;
    private String[][] frTable;
    private FinancialRecord fr;
    String editCat, editSubCat;
    
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
        emodel.getFrList();
        frTable = emodel.getFrListAsTable();
        
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter())
        {
            replaceCategoryIdWithName();
            
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
        String requestType = request.getParameter("requestType");
        String mainCat = request.getParameter("mainCategory");
        String subCat = request.getParameter("subCategory");
        
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

    
    public void replaceCategoryIdWithName()
    {
        for(int i = 1; i < frTable.length; i++)
        {
            int categoryId = Integer.parseInt(frTable[i][4]);
            //System.out.println("categoryId=" + categoryId);
            int subCategoryId = Integer.parseInt(frTable[i][5]);
            //System.out.println("subCategoryId=" + subCategoryId);
            
            frTable[i][4] = cm.getCategoryNameById(categoryId);
            frTable[i][5] = cm.getSubCategoryNameById(subCategoryId);
            
        }
    }
}
