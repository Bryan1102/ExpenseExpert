
package com.anthorra.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Anthorra
 */
public class DbConnection
{
    public enum dbType {mysql, mssql};
    
    public static Connection getConnection(dbType dbType, String connPath, String connPort, String connDb, String username, String password)
    {
        String driverClassName = "";
        String url = "";
        Connection con = null;
        Driver driver = null;
        
        switch(dbType)
        {
            case mysql:
                if(connPort==null){connPort="3306";}
                driverClassName = "com.mysql.cj.jdbc.Driver";
                url = "jdbc:mysql://"+connPath+":"+connPort+"/"+connDb;
                break;
            case mssql:
                if(connPort==null){connPort="1433";}
                
                driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                url = "jdbc:sqlserver://"+connPath+":"+connPort+";databaseName="+connDb+";encrypt=true;trustServerCertificate=true;user="+username+";password="+password;
                
                break;
        }
        //System.out.println("DB Url = " + url);
        
        try
        {
            // Load driver class
            Class.forName(driverClassName);
            System.out.println("DB Driver Loaded: " + driverClassName);
        } 
        catch (ClassNotFoundException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        try
        {
            if(dbType==dbType.mssql)
            {
                driver = new com.microsoft.sqlserver.jdbc.SQLServerDriver();
                DriverManager.registerDriver(driver);
            }
            
            con = DriverManager.getConnection(url, username, password);
            if(con != null)
            {
                DatabaseMetaData dm = (DatabaseMetaData) con.getMetaData();
                //System.out.println("Driver name: " + dm.getDriverName());
                //System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Connected to: " + dm.getDatabaseProductName());
                //System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
            
            
            return con;
        } 
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
            //System.out.println(ex.printStackTrace());
        }
        finally
        {
            if(driver!=null)
            {
                try{DriverManager.deregisterDriver(driver); System.out.println("Driver de-registered!");} 
                catch (SQLException ex){System.out.println(ex.getMessage());}
            }
        }
        return null;
    }
   
}
