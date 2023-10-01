
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
    private String[] optionsCategories;
    private String[] optionsSubCategories;
    private String[][] categoriesTable;
    private String categoriesJson;

    public CatModel()
    {
        this.mainCategories = retrieveMainCategories();
        this.subCategories = retrieveSubCategories();
        ConvertCategoriesToArray();
        ConvertSubCategoriesToArray();
        ConvertCategoriesToTable();
        ConvertCategoriesToJson();
        
    }
    
    
    
    private ArrayList retrieveMainCategories()
    {
        ArrayList<Category> mainCategories = new ArrayList<>();
        
        Connection con = null;
        Statement stmt = null;
        try
        {
            con = DbConnection.getConnection(DbConnection.dbType.mssql, "localhost\\SQLEXPRESS","1433","expenseexpert","expe","12345");
            
            //String v_sql = "Select distinct ID, TYPE_DESC from fintypes WHERE IS_DEL = 0";
            String v_sql = "Select distinct ID, TYPE_DESC = CASE IS_DEL WHEN 1 THEN CONCAT('DEL ',TYPE_DESC) ELSE TYPE_DESC END from fintypes ";
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
            //String v_sql = "Select distinct ID, TYPE_DESC, PARENT_ID from finsubtypes WHERE IS_DEL = 0";
            String v_sql = "Select distinct ID, TYPE_DESC = CASE IS_DEL WHEN 1 THEN CONCAT('DEL ',TYPE_DESC) ELSE TYPE_DESC END, PARENT_ID from finsubtypes";
            
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
                          //System.out.println("new Category ID: " + retVal);
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

    public String[] getOptionsCategories()
    {
        return optionsCategories;
    }

    public String[] getOptionsSubCategories()
    {
        return optionsSubCategories;
    }

    public String[][] getCategoriesTable()
    {
        return categoriesTable;
    }

    public String getCategoriesJson()
    {
        return categoriesJson;
    }
    
    public int getCategoryIdByName(String mainCategory)
    {
        int parentCatId = -1;
        if(mainCategory != null)
        {
            for(Category c : mainCategories)
                    {
                        if(mainCategory.equals(c.getCategoryName()) )
                        {
                            parentCatId = c.getId();
                            break;
                        }
                    }
        }
        
        return parentCatId;
    }
    public int getSubCategoryIdByName(String subCategory)
    {
        int subCatId = -1;
        if(subCategory != null)
        {
            for(SubCategory sc : subCategories)
                    {
                        if(subCategory.equals(sc.getCategoryName()) )
                        {
                            subCatId = sc.getId();
                            break;
                        }
                    }
        }
        
        return subCatId;
    }
    public String getCategoryNameById(int id)
    {
        String categoryName = "";
        if(id > 0)
        {
            for(Category c : mainCategories)
                    {
                        if(c.getId() == id)
                        {
                            categoryName = c.getCategoryName();
                            break;
                        }
                    }
        }
        else
        {
            System.out.println("ERROR! - CatModel.getCategoryNameById received id <= 0!");
        }
        
        return categoryName;
    }
    public String getSubCategoryNameById(int id)
    {
        String subCategoryName = "";
        if(id > 0)
        {
            for(SubCategory sc : subCategories)
                    {
                        if(sc.getId() == id)
                        {
                            subCategoryName = sc.getCategoryName();
                            break;
                        }
                    }
        }
        else
        {
            System.out.println("ERROR! - CatModel.getCategoryNameById received id <= 0!");
        }
        
        return subCategoryName;
    }
    
    /* Convert ARRAYLIST TO ARRAYS */
    private void ConvertCategoriesToArray()
    {
        this.optionsCategories = new String[mainCategories.size()];
        for(int i = 0; i < mainCategories.size(); i++)
    {
        optionsCategories[i] = mainCategories.get(i).getCategoryName();
    }    

    }
    private void ConvertSubCategoriesToArray()
    {
        this.optionsSubCategories = new String[subCategories.size()];
        int i = 0;
        for(SubCategory sc : subCategories)
        {
            optionsSubCategories[i] = sc.getCategoryName();
            i += 1;
        }
        
    }
    private void ConvertCategoriesToTable()
    {
        categoriesTable = new String[mainCategories.size() + 1][2];
        categoriesTable[0][0] = "Főkategóriák";
        categoriesTable[0][1] = "Alkategóriák";
                
        for(int i = 0; i < mainCategories.size(); i++)
            {
                categoriesTable[i + 1][0] = mainCategories.get(i).getCategoryName();
                String subCategoryNames = "";
                boolean isFirst = false;
                
                for(int j = 0; j < subCategories.size(); j++)
                {
                    
                    if(mainCategories.get(i).getId() == subCategories.get(j).getParentId())
                    {
                        if(isFirst){subCategoryNames += ", ";}
                        subCategoryNames += subCategories.get(j).getCategoryName();
                        isFirst = true;
                    }
                }
                categoriesTable[i + 1][1] = subCategoryNames;
            }            
                
                
    }
    private void ConvertCategoriesToJson()
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
        categoriesJson = typesJson;
    }
    
        
                
            
            
        
            
    
}
