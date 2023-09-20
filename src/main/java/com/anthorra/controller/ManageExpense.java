
package com.anthorra.controller;

import com.anthorra.expenseexpert.Category;
import com.anthorra.expenseexpert.SubCategory;
import com.anthorra.html.HtmlPage;
import com.anthorra.model.CatModel;
import com.anthorra.view.ExpenseView;
import jakarta.servlet.RequestDispatcher;
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
    
    private ArrayList<Category> mainCategories;
    private ArrayList<SubCategory> subCategories;
    private CatModel cm = new CatModel();
    
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
        CatModel cm = new CatModel();
        mainCategories = cm.getMainCategories();
        subCategories = cm.getSubCategories();
        
        response.setContentType("text/html;charset=UTF-8");
        
        
        try (PrintWriter out = response.getWriter())
        {
            HtmlPage page = new HtmlPage();
            page = ExpenseView.getPageExpense();
            out.println(page.getPage());
            
            
            /* TODO output your page here. You may use following sample code. */
            //out.println("<!DOCTYPE html>");
//            out.println("<html>");
//                out.println("<head>");
//                    out.println("<title>Servlet ManageExpense</title>");     
//                    /*JAVASCRIPT*/    
//                        out.println("<script>" + getCascadeJs() + "</script>");
//                out.println("</head>");
            
            out.println("<body>");
            out.println("<h1>Költségek Kezelése SERVLET</h1>");
            
                out.println("<form method=\"post\" action=\"AddExpense\">");
                out.println("<fieldset>");
                out.println("<legend>Költség:</legend>");
                    out.println("<label for=\"isExpense\">Kiadás:</label>");
                    out.println("<input type=\"checkbox\" name=\"isExpense\" value=\"true\" /></br></br>");

                    out.println("<label for=\"amount\">Érték:</label><br>");
                    out.println("<input type=\"text\" name=\"amount\" value=\"\" /></br>");

                    out.println("<label for=\"comment\">Komment:</label><br>");
                    out.println("<input type=\"text\" name=\"comment\" value=\"\" /></br>");
                    
                    /*CATEGORIES*/
                        out.println("<label for=\"categories\">Válassz kategóriát:</label></br>");
                        out.println("<select id=\"type\" name=\"categories\">");
                        /*for(Category c : mainCategories)
                            {
                                out.println("<option value=\"" + c.getId() + "\">" + c.getCategoryName() + "</option>");
                            }*/
                        out.println("<option value=\"\" selected=\"selected\">Válassz!</option>");
                        out.println("</select> </br></br>");
                    
                    /*SUBCATEGORIES*/
                        out.println("<label for=\"subcategories\">Válassz alkategóriát:</label></br>");
                        out.println("<select id=\"subtype\" name=\"subcategories\">");
                        /*for(SubCategory sc : subCategories)
                            {
                                out.println("<option value=\"" + sc.getId() + "\">" + sc.getCategoryName() + "</option>");
                            }*/
                        out.println("<option value=\"\" selected=\"selected\">Válassz!</option>");
                        out.println("</select> </br></br>");
                    
                    /*SUBMIT BUTTON*/
                    out.println("<input type=\"submit\" value=\"Mentés\" /></br>");
                out.println("</fieldset>");
                out.println("</form>");
            
            out.println("</body>");
            out.println("</html>");
            
            
        }
    }
    
    private String getTypesAsJson()
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
    private String getCascadeJs()
    {
        String jsCascade = "var subjectObject = \n" +
                        getTypesAsJson() + ";\n" +
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
