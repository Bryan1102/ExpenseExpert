
package com.anthorra.controller;

import com.anthorra.html.HtmlPage;
import com.anthorra.model.CatModel;
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
    private String message;
    private String messageError;
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
        optionsCategories = cm.getOptionsCategories();
        optionsSubCategories = cm.getOptionsSubCategories();
        categoriesJson = cm.getCategoriesJson();
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter())
        {
            HtmlPage page = new HtmlPage();
            page = ExpenseView.getPageExpense(optionsCategories, optionsSubCategories, categoriesJson, message);
            out.println(page.getPage());
        }
        message = "";
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
