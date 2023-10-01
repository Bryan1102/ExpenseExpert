package com.anthorra.model;

import com.anthorra.db.DbConnection;
import com.anthorra.expenseexpert.FinancialRecord;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Anthorra
 */
public class ExpenseModel
{
    private ArrayList<FinancialRecord> frList;
    private String[][] frListAsTable;
    
    /* CONSTRUCTOR */
    public ExpenseModel()
    {
        this.frList = new ArrayList<>();
        
    }
    
    public int saveFinancialRecord(FinancialRecord fr)
    {
        int retVal = -1;
        Connection con = null;
        PreparedStatement st = null;
        
        try
        {
            con = DbConnection.getConnection(DbConnection.dbType.mssql, "localhost\\SQLEXPRESS","1433","expenseexpert","expe","12345");
            
            String v_sql = "DECLARE @RC int\n" +
                            "EXECUTE @RC = [dbo].[P_INSERT_FR] ?,?,?,?,?  ,?\n" +
                            "SELECT @RC";
            
            // Obtain a statement
            st = con.prepareStatement(v_sql);
            st.setInt(1, fr.isIsExpense() ? 1 : 0 );
            st.setDouble(2, fr.getAmount());
            st.setInt(3, fr.getCategory());
            st.setInt(4, fr.getSubCategory());
            st.setString(5, fr.getRealizedDate());
            st.setString(6, fr.getComment());
            
            // Execute the query
            //retVal = st.executeUpdate();
            //because of 'A result set was generated for update' type error
            ResultSet rs = st.executeQuery(); // Not update, you're returning a ResultSet.
                        if (rs.next()) {
                          retVal = (rs.getInt(1));
                          System.out.println("new FinancialRecord ID: " + retVal);
                        }
            //System.out.println("saveFinancialRecord: " + retVal);
            
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

    
    
    public ArrayList<FinancialRecord> getFrList()
    {
        getFinancialRecords(2023, 9);
        return frList;
    }
    private void getFinancialRecords(int year, int month)
    {
        int retVal = -1;
        Connection con = null;
        Statement stmt = null;
        
        try
        {
            con = DbConnection.getConnection(DbConnection.dbType.mssql, "localhost\\SQLEXPRESS","1433","expenseexpert","expe","12345");
            
            /*
            String v_sql = "Select DISTINCT fr.AMOUNT, fr.ISEXPENSE, fr.TYPE, fr.SUBTYPE, fr.COMMENT, CONVERT(VARCHAR, fr.REALIZED_DATE, 23), fr.ID\n" +
                           "FROM [expenseexpert].[dbo].[financialrecords] fr";*/
            String v_sql = "Select DISTINCT fr.AMOUNT, fr.ISEXPENSE, fr.TYPE as CATEGORY_ID, fr.SUBTYPE as SUBCATEGORY_ID, fr.COMMENT, \n" +
                            "CONVERT(VARCHAR, fr.REALIZED_DATE, 23), fr.ID, t.TYPE_DESC as CATEGORY_NAME, st.TYPE_DESC as SUBCATEGORY_NAME\n" +
                            "                           FROM [expenseexpert].[dbo].[financialrecords] fr \n" +
                            "						   LEFT JOIN [expenseexpert].[dbo].[fintypes] t ON  fr.TYPE = t.ID\n" +
                            "						   LEFT JOIN [expenseexpert].dbo.finsubtypes st ON fr.SUBTYPE = st.ID";
            
            // Obtain a statement
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(v_sql);
            
            // Execute the query
            while(rs.next())
            {
                FinancialRecord fr = new FinancialRecord(rs.getDouble(1), 
                                        (rs.getInt(2)==1),
                                        rs.getInt(3),
                                        rs.getInt(4),
                                        rs.getString(5),
                                        rs.getString(6));
                fr.setId(rs.getInt(7));
                fr.setCategoryName(rs.getString(8));
                fr.setSubcategoryName(rs.getString(9));
                frList.add(fr);
            }
            // Closing the connection as per the requirement with connection is completed
            con.close();
            
        } 
        catch (SQLException ex)
        {
            /*Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);*/
            System.out.println(ex);
        }
        finally
        {
            try{stmt.close();} catch (SQLException ex){}
            try{con.close();} catch (SQLException ex){}
        }
        
        
    }

    public String[][] getFrListAsTable()
    {
        ConvertArrayToTable();
        return frListAsTable;
    }
    private void ConvertArrayToTable()
    {
        this.frListAsTable = new String[frList.size() + 1][7];
        
        frListAsTable[0][0] = "Azonostó";
        frListAsTable[0][1] = "Dátum";
        frListAsTable[0][2] = "Összeg";
        frListAsTable[0][3] = "Kiadás/Bevétel";
        frListAsTable[0][4] = "Kategória";
        frListAsTable[0][5] = "Alkategória";
        frListAsTable[0][6] = "Komment";
        
        
        int i = 1;
        for(FinancialRecord fr : frList)
        {
            frListAsTable[i][0] = String.valueOf(fr.getId());
            frListAsTable[i][1] = fr.getRealizedDate();
            frListAsTable[i][2] = String.valueOf(fr.getAmount());
            frListAsTable[i][3] = fr.isIsExpense()?"Kiadás":"Bevétel";
            frListAsTable[i][4] = String.valueOf(fr.getCategory());
            frListAsTable[i][5] = String.valueOf(fr.getSubCategory());
            frListAsTable[i][6] = fr.getComment();
            i++;        
        }
    }
    public FinancialRecord getFinancialRecordById(int id)
    {
        FinancialRecord fr;
        for(FinancialRecord f : frList)
        {
            if(f.getId()==id)
            {
                fr = f;
                return fr;
            }
        }
        return null;
    }
}
