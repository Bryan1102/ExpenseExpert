
package com.anthorra.model;

import com.anthorra.db.DbConnection;
import com.anthorra.expenseexpert.Category;
import com.anthorra.expenseexpert.SubCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Anthorra
 */
public class CatModel
{
    private ArrayList<Category> mainCategories;
    private ArrayList<SubCategory> subCategories;

    public CatModel()
    {
        this.mainCategories = retrieveMainCategories();
        this.subCategories = retrieveSubCategories();
        System.out.println("new Category Model created");
    }
    
    
    
    private ArrayList retrieveMainCategories()
    {
        //System.out.println("inside: retrieveMainCategories");
        ArrayList<Category> mainCategories = new ArrayList<>();
        
        Connection con = null;
        Statement stmt = null;
        try
        {
            con = DbConnection.getConnection(DbConnection.dbType.mssql, "localhost\\SQLEXPRESS","1433","expenseexpert","expe","12345");
            
            String v_sql = "Select distinct ID, TYPE_DESC from fintypes WHERE IS_DEL = 0";
            
            // Obtain a statement
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(v_sql);
            
            // Execute the query
            while(rs.next())
            {
                Category c = new Category(rs.getInt(1), 
                                        rs.getString(2));
                
                //System.out.println(c);
                mainCategories.add(c);
            }
        } 
        catch (SQLException ex)
        {
            /*Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);*/
            System.out.println(ex.getMessage());
        }
        finally
        {
            try{stmt.close();} catch (SQLException ex){System.out.println(ex.getMessage());}
            try{con.close();} catch (SQLException ex){System.out.println(ex.getMessage());}
            System.out.println("connection closed");
        }
        
        return mainCategories;
    }
    private ArrayList retrieveSubCategories()
    {
        //System.out.println("inside: retrieveMainCategories");
        ArrayList<SubCategory> subCategories = new ArrayList<>();
        
        Connection con = null;
        Statement stmt = null;
        try
        {
            //con = DriverManager.getConnection(url, username, password);
            //con = DbConnection.getConnection("localhost","3306","expenseexpert","javaDev","java1234_");
            con = DbConnection.getConnection(DbConnection.dbType.mssql, "localhost\\SQLEXPRESS","1433","expenseexpert","expe","12345");
            String v_sql = "Select distinct ID, TYPE_DESC, PARENT_ID from finsubtypes WHERE IS_DEL = 0";
            
            // Obtain a statement
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(v_sql);
            
            // Execute the query
            while(rs.next())
            {
                SubCategory sc = new SubCategory(rs.getInt(1), 
                                        rs.getInt(3),
                                        rs.getString(2));
                
                //System.out.println(sc);
                subCategories.add(sc);
            }
        } 
        catch (SQLException ex)
        {
            /*Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);*/
            System.out.println(ex.getMessage());
        }
        finally
        {
            try{stmt.close();} catch (SQLException ex){System.out.println(ex.getMessage());}
            try{con.close();} catch (SQLException ex){System.out.println(ex.getMessage());}
            System.out.println("connection closed");
        }
        
        return subCategories;
    }
    
    public int saveCategory(Category c)
    {
        int retVal = -1;
        Connection con = null;
        PreparedStatement st = null;
        try
        {
            con = DbConnection.getConnection(DbConnection.dbType.mssql, "localhost\\SQLEXPRESS","1433","expenseexpert","expe","12345");
            
            String v_sql = "DECLARE @RC int\n" +
                            "EXECUTE @RC = [dbo].[P_INSERT_CAT] ?\n" +
                            "SELECT @RC";
            
            // Obtain a statement
            st = con.prepareStatement(v_sql);
            st.setString(1, c.getCategoryName() );
            
            // Execute the query
            //retVal = st.executeUpdate();
            
            //because of 'A result set was generated for update' type error
            ResultSet rs = st.executeQuery(); // Not update, you're returning a ResultSet.
                        if (rs.next()) {
                          retVal = (rs.getInt(1));
                          System.out.println("new Category ID: " + retVal);
                        }
            
            // Closing the connection as per the requirement with connection is completed
            con.close();
            return retVal;
        } 
        catch (SQLException ex)
        {
            /*Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);*/
            System.out.println(ex);
        }
        finally
        {
            try{st.close();} catch (SQLException ex){}
            try{con.close();} catch (SQLException ex){}
        }
        return retVal;
    }
    public int saveSubCategory(SubCategory sc)
    {
        int retVal = -1;
        Connection con = null;
        PreparedStatement st = null;
        try
        {
            con = DbConnection.getConnection(DbConnection.dbType.mssql, "localhost\\SQLEXPRESS","1433","expenseexpert","expe","12345");
            
            String v_sql = "DECLARE @RC int\n" +
                            "EXECUTE @RC = [dbo].[P_INSERT_SUBCAT] ?, ? \n" +
                            "SELECT @RC";
            
            // Obtain a statement
            st = con.prepareStatement(v_sql);
            st.setString(1, sc.getCategoryName() );
            st.setInt(2, sc.getParentId());
            
             ResultSet rs = st.executeQuery();
                        if (rs.next()) {
                          retVal = (rs.getInt(1));
                          System.out.println("new SubCategory ID: " + retVal);
                        }
            
            // Closing the connection as per the requirement with connection is completed
            con.close();
            return retVal;
        } 
        catch (SQLException ex)
        {
            /*Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);*/
            System.out.println(ex);
        }
        finally
        {
            try{st.close();} catch (SQLException ex){}
            try{con.close();} catch (SQLException ex){}
        }
        return retVal;
    }
    public int renameCategory(int categoryId, String renameTo)
    {
        int retVal = -1;
        Connection con = null;
        PreparedStatement st = null;
        try
        {
            con = DbConnection.getConnection(DbConnection.dbType.mssql, "localhost\\SQLEXPRESS","1433","expenseexpert","expe","12345");
            
            String v_sql = "DECLARE @RC int\n" +
                            "EXECUTE @RC = [dbo].[P_UPDATE_CAT] ?, ? \n" +
                            "SELECT @RC";
            
            // Obtain a statement
            st = con.prepareStatement(v_sql);
            st.setInt(1, categoryId);
            st.setString(2, renameTo);
            
             ResultSet rs = st.executeQuery();
                        if (rs.next()) {
                          retVal = (rs.getInt(1));
                          System.out.println("Renamed ID: " + retVal);
                        }
            
            // Closing the connection as per the requirement with connection is completed
            con.close();
            return retVal;
        } 
        catch (SQLException ex)
        {
            /*Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);*/
            System.out.println(ex);
        }
        finally
        {
            try{st.close();} catch (SQLException ex){}
            try{con.close();} catch (SQLException ex){}
        }
        return retVal;
    }
    public int renameSubCategory(int categoryId, String renameTo)
    {
        int retVal = -1;
        Connection con = null;
        PreparedStatement st = null;
        try
        {
            con = DbConnection.getConnection(DbConnection.dbType.mssql, "localhost\\SQLEXPRESS","1433","expenseexpert","expe","12345");
            
            String v_sql = "DECLARE @RC int\n" +
                            "EXECUTE @RC = [dbo].[P_UPDATE_SUBCAT] ?, ? \n" +
                            "SELECT @RC";
            
            // Obtain a statement
            st = con.prepareStatement(v_sql);
            st.setInt(1, categoryId);
            st.setString(2, renameTo);
            
             ResultSet rs = st.executeQuery();
                        if (rs.next()) {
                          retVal = (rs.getInt(1));
                          System.out.println("Renamed ID: " + retVal);
                        }
            
            // Closing the connection as per the requirement with connection is completed
            con.close();
            return retVal;
        } 
        catch (SQLException ex)
        {
            /*Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);*/
            System.out.println(ex);
        }
        finally
        {
            try{st.close();} catch (SQLException ex){}
            try{con.close();} catch (SQLException ex){}
        }
        return retVal;
    }
    public int deleteCategory(int categoryId)
    {
        int retVal = -1;
        Connection con = null;
        PreparedStatement st = null;
        try
        {
            con = DbConnection.getConnection(DbConnection.dbType.mssql, "localhost\\SQLEXPRESS","1433","expenseexpert","expe","12345");
            
            String v_sql = "DECLARE @RC int\n" +
                            "EXECUTE @RC = [dbo].[P_DELETE_CAT] ? \n" +
                            "SELECT @RC";
            
            // Obtain a statement
            st = con.prepareStatement(v_sql);
            st.setInt(1, categoryId);
                        
            ResultSet rs = st.executeQuery();
                        if (rs.next()) {
                          retVal = (rs.getInt(1));
                          System.out.println("Deleted rows: " + retVal);
                        }
            
            // Closing the connection as per the requirement with connection is completed
            con.close();
            return retVal;
        } 
        catch (SQLException ex)
        {
            /*Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);*/
            System.out.println(ex);
        }
        finally
        {
            try{st.close();} catch (SQLException ex){}
            try{con.close();} catch (SQLException ex){}
        }
        return retVal;
    }
    public int deleteSubCategory(int categoryId)
    {
        int retVal = -1;
        Connection con = null;
        PreparedStatement st = null;
        try
        {
            con = DbConnection.getConnection(DbConnection.dbType.mssql, "localhost\\SQLEXPRESS","1433","expenseexpert","expe","12345");
            
            String v_sql = "DECLARE @RC int\n" +
                            "EXECUTE @RC = [dbo].[P_DELETE_SUBCAT] ? \n" +
                            "SELECT @RC";
            
            // Obtain a statement
            st = con.prepareStatement(v_sql);
            st.setInt(1, categoryId);
            
            ResultSet rs = st.executeQuery();
                        if (rs.next()) {
                          retVal = (rs.getInt(1));
                          System.out.println("Deleted rows: " + retVal);
                        }
            
            // Closing the connection as per the requirement with connection is completed
            con.close();
            return retVal;
        } 
        catch (SQLException ex)
        {
            /*Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);*/
            System.out.println(ex);
        }
        finally
        {
            try{st.close();} catch (SQLException ex){}
            try{con.close();} catch (SQLException ex){}
        }
        return retVal;
    }
    
    
    /*GETTERS*/
    public ArrayList getSubCategories()
    {
        return subCategories;
    }

    public ArrayList getMainCategories()
    {
        return mainCategories;
    }
    
    
    
}
