
package com.anthorra.controller;

import com.anthorra.expenseexpert.FinancialRecord;
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
import java.time.LocalDate;
import java.time.YearMonth;

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
    private String startDate = "2023-09-01";
    private String endDate = "2023-09-30";
     
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
        /* default dátum megadás */
        LocalDate now = LocalDate.now();  
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);  
        System.out.println(firstDayOfMonth);  
        
        YearMonth ym = YearMonth.now();
        int lastDayOfMonth = ym.atEndOfMonth().getDayOfMonth();  
        System.out.println(lastDayOfMonth);
        
        
        /* model-el létrehozása */
        cm = new CategoryModel();
        emodel = new ExpenseModel(startDate, endDate);
        
        /* kategóriák kérése a modeltől */
        optionsCategories = cm.getOptionsCategories(); /* select list value-option feltöltéshez szükséges String [][] elemek */
        optionsSubCategories = cm.getOptionsSubCategories();
        
        /* json file kérése a modeltől - jelenleg nem használt beágyazott kategóriák és alkategóriákhoz szükséges - lásd: ExpenseView/getCascadeJs method */
        categoriesJson = cm.getCategoriesJson();
        
        /* pénzügyi tételek kérése a modeltől */
        frTable = emodel.getFrListAsTable(); /* A FinancialRecord entitások listája táblázatosan - így fogadja a HTMLTable az inputot */
        
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter())
        {
            HtmlPage page = new HtmlPage();
            page = ExpenseView.getPageExpense(optionsCategories, optionsSubCategories, categoriesJson, frTable, isError?errorMessage:message, isError, fr);
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
        
        int mainCatId = -1;
        int subCatId = -1;
        
        /* REQUEST: Meglévő record szerkesztése */
        if(requestType.indexOf("Edit=") != -1)
        {
            int frId = Integer.parseInt(requestType.replaceAll("Edit=", ""));
            this.fr = emodel.getFinancialRecordById(frId);
        }
        else
        {
            if(mainCat != null)
            {mainCatId = Integer.parseInt(mainCat);}
            if(subCat != null)
            {subCatId = Integer.parseInt(subCat);}
            
            
            switch(requestType)
            {
            case "requestSaveFrecord": /* REQUEST: új rekord mentése */
                /*get categories*/
                errorMessage = "";
                
                
                if(mainCatId < 0 || subCatId < 0)
                {
                    isError = true;
                    if(mainCatId < 0){errorMessage+=" Kategória nem található!";}
                    if(subCatId < 0){errorMessage+=" Alkategória nem található!";}
                    break;
                    
                }
                else
                {
                    /*amount*/
                    String amount = request.getParameter("amount");
                    double amountDouble = Double.parseDouble(amount);
                    
                    /*comment*/
                    String comment = request.getParameter("comment");
                    
                    /*isExpense*/
                    String isExpense = request.getParameter("isExpense");
                    if(isExpense==null)
                            {
                                isExpense = "false";
                            }
                    Boolean isExpenseBoolean = Boolean.valueOf(isExpense);
                    
                    /*date*/
                    String rDate = request.getParameter("datum");
                    
                    int dbResponse = emodel.saveFinancialRecord(new FinancialRecord(amountDouble, isExpenseBoolean, mainCatId, subCatId, comment, rDate));
                    message = (isExpenseBoolean?" Kiadás":" Bevétel") + " mentése sikeres! Azonosítója: " + dbResponse;
                }
                break;
            case "requestSaveEditFrecord":
                errorMessage = "";
                
                if(mainCatId < 0 || subCatId < 0)
                {
                    isError = true;
                    if(mainCatId < 0){errorMessage+=" Kategória nem található!";}
                    if(subCatId < 0){errorMessage+=" Alkategória nem található!";}
                    break;
                }
                else
                {
                    /*id*/
                    int id = -1;
                    String idString = request.getParameter("frId");
                    if(idString != null)
                    {id = Integer.parseInt(idString);}
                    
                    /*amount*/
                    String amount = request.getParameter("amount");
                    double amountDouble = Double.parseDouble(amount);
                    
                    /*comment*/
                    String comment = request.getParameter("comment");
                    
                    /*isExpense*/
                    String isExpense = request.getParameter("isExpense");
                    if(isExpense==null)
                            {
                                isExpense = "false";
                            }
                    Boolean isExpenseBoolean = Boolean.valueOf(isExpense);
                    
                    /*date*/
                    String rDate = request.getParameter("datum");
                    
                    FinancialRecord updatedFr = new FinancialRecord(amountDouble, isExpenseBoolean, mainCatId, subCatId, comment, rDate);
                    updatedFr.setId(id);
                    
                    int dbResponse = emodel.updateFinancialRecord(updatedFr);
                    /*System.out.println("dbResponse: " + dbResponse);*/ /*a procedura a módosított sorok számát adja, tehát ha > 0 akkor sikeres*/
                    message = (isExpenseBoolean?" Kiadás":" Bevétel") + " módosítása sikeres!";
                }
                break;   
            
            case "requestDeleteFr":
                /*id*/
                int id = -1;
                String idString = request.getParameter("frId");
                if(idString != null)
                {id = Integer.parseInt(idString);}
                int dbResponse = emodel.deleteFinancialRecord(id);
                message = (" Tétel törlése sikeres!");
                
                break;
                
            case "requestCancel":
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
