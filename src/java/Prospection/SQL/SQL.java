/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Prospection.SQL;

/**
 *
 * @author weizhe.jiao
 */
import Prospection.vaadin.MyApplication;
import java.*;
import com.vaadin.ui.*;
import com.vaadin.Application;
import com.vaadin.ui.*;
import com.vaadin.data.*;
/**
 *
 * @author weizhe.jiao
 */
import com.vaadin.addon.sqlcontainer.ColumnProperty;
import com.vaadin.addon.sqlcontainer.OptimisticLockException;
import com.vaadin.addon.sqlcontainer.RowId;
import com.vaadin.addon.sqlcontainer.RowItem;
import com.vaadin.addon.sqlcontainer.TemporaryRowId;
import com.vaadin.addon.sqlcontainer.Util;
import com.vaadin.addon.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.addon.sqlcontainer.query.generator.DefaultSQLGenerator;
import com.vaadin.addon.sqlcontainer.query.generator.MSSQLGenerator;
import com.vaadin.addon.sqlcontainer.query.generator.SQLGenerator;
import com.vaadin.addon.sqlcontainer.query.generator.StatementHelper;

import com.vaadin.addon.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.addon.sqlcontainer.SQLContainer;
import com.vaadin.addon.sqlcontainer.query.FreeformQuery;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.sql.Driver;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.sql.*;

// if dont use SQLContainer, 
// we can use jdbc connection, statement, and executeQuery/executeUpdate

public class SQL{
     private static Connection  con = null;
     private final static String url = "jdbc:sqlserver://";
     private final static String serverName= "da-newdm";
     private final static String portNumber = "1433";
     private final static String databaseName= "db_prospection";
     private final static String userName = "sa";
     private final static String password = "limubai64";
     // Informs the driver to use server a side-cursor,
     // which permits more than one active statement
     // on a connection.
     private final static String selectMethod = "cursor";

     // Constructor
     public SQL(){}

     private static String getConnectionUrl(){
          return url+serverName+":"+portNumber+";databaseName="+databaseName+";selectMethod="+selectMethod+";";
     }

     private static Connection getConnection(){
          try{
               Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
               con = DriverManager.getConnection(getConnectionUrl(),userName,password);
               if(con!=null) System.out.println("Connection Successful!");
          }catch(Exception e){
               e.printStackTrace();
               System.out.println("Error Trace in getConnection() : " + e.getMessage());
         }
          return con;
      }

     /*
          Display the driver properties, database details
     */

     public void displayDbProperties(){
            //com.vaadin.ui.Window.Notification()
          DatabaseMetaData dm = null;
          ResultSet rs = null;
          try{
               con= this.getConnection();
               if(con!=null){
                    dm = con.getMetaData();
                     
                    System.out.println("Driver Information");
                    System.out.println("\tDriver Name: "+ dm.getDriverName());
                    System.out.println("\tDriver Version: "+ dm.getDriverVersion ());
                    System.out.println("\nDatabase Information ");
                    System.out.println("\tDatabase Name: "+ dm.getDatabaseProductName());
                    System.out.println("\tDatabase Version: "+ dm.getDatabaseProductVersion());
                    System.out.println("Avalilable Catalogs ");
                    rs = dm.getCatalogs();
                    while(rs.next()){
                         System.out.println("\tcatalog: "+ rs.getString(1));
                    }
                    rs.close();
                    rs = null;
                    closeConnection();
               }else System.out.println("Error: No active Connection");
          }catch(Exception e){
               e.printStackTrace();
          }
          dm=null;
     }

     private void closeConnection(){
          try{
               if(con!=null)
                    con.close();
               con=null;
          }catch(Exception e){
               e.printStackTrace();
          }
     }


      public static void excuteUpdate(String s){
          try{
               con= SQL.getConnection();
               if(con!=null){
                    String SQL = s;
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(SQL);
                    stmt.close();
              }
          }catch(Exception e){
               e.printStackTrace();
          }
     }
      
        public ResultSet excuteQuery(String s){
          ResultSet rs = null;
          try{
               con= this.getConnection();
               if(con!=null){
                    String SQL = s;
                    Statement stmt = con.createStatement();
                    rs = stmt.executeQuery(SQL);
                    stmt.close();
              }
          }catch(Exception e){
               e.printStackTrace();
          }
          return rs;
        }
      
      
       public Boolean authenticate(String mail, String pass){
          ResultSet rs = null;
          try{
               con= this.getConnection();
               if(con!=null){

                    String SQL = "exec sp_authenticate '"+mail+"', '"+pass+"'";
                    Statement stmt = con.createStatement();
                    MyApplication.debug(3, SQL);
                    rs = stmt.executeQuery(SQL);
                    if(rs.next() && (rs.getString("checked").equals("1")) )
                    //if(rs.first()==1)
                    {
                        MyApplication.debug(3, rs.getString("checked"));
                        rs.close();
                        stmt.close();
                        return true;
                    }
                    
                    
              }
          }catch(Exception e){
               e.printStackTrace();
          }
          return false;
     }


}

