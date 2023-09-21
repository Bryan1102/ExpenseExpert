package com.anthorra.model;

import com.anthorra.db.DbConnection;
import com.anthorra.expenseexpert.FinancialRecord;
import java.sql.*;
import java.sql.SQLException;

/**
 *
 * @author Anthorra
 */
public class ExpenseModel
{
    
    public int saveFinancialRecord(FinancialRecord fr)
    {
        int retVal = -1;
        Connection con = null;
        PreparedStatement st = null;
        
        System.out.println(fr.getRealizedDate());
        
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
            st.setInt(3, fr.getType());
            st.setInt(4, fr.getSubtype());
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
}
