package com.anthorra.controller;

import com.anthorra.expenseexpert.FinancialRecord;
import com.anthorra.model.ExpenseModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Anthorra
 */
@WebServlet(name = "AddExpense", urlPatterns ={"/AddExpense"})
public class AddExpense extends HttpServlet
{
    int dbResponse;
    
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter())
        {
            
            
            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet AddExpense</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet AddExpense at " + request.getContextPath() + "</h1>");
//            String dbSuccess;
//            if(dbResponse>=1){dbSuccess = "Pénzügyi tétel felvétele sikeres!";}
//            else{dbSuccess = "Pénzügyi tétel felvétele sikertelen!";}
//            out.println("<h2>" + dbSuccess + "</h2>");
//            
//            out.println("</body>");
//            out.println("</html>");
            
            response.sendRedirect("ManageExpense.jsp");
        }
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
        System.out.println("doGet method, process előtt");
        processRequest(request, response);
        System.out.println("doGet method, process előtt");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        /*amount*/
        String amount = request.getParameter("amount");
        double amountDouble = Double.parseDouble(amount);
        //System.out.println(amountDouble);
        
        /*comment*/
        String comment = request.getParameter("comment");
        //System.out.println(comment);
        
        /*isExpense*/
        String isExpense = request.getParameter("isExpense");
        if(isExpense==null)
                {
                    isExpense = "false";
                }
        Boolean isExpenseBoolean = Boolean.parseBoolean(isExpense);
        System.out.println(isExpenseBoolean);
        
        
        ExpenseModel model = new ExpenseModel();
        dbResponse = model.saveFinancialRecord(new FinancialRecord(amountDouble, isExpenseBoolean, 1, 1, comment, "2023-07-17"));
        System.out.println("dbResponse: " + dbResponse);
        
        processRequest(request, response);
        
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
