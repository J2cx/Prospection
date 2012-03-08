/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.vaadin.SQL;

/**
 *
 * @author weizhe.jiao
 */
import com.example.vaadin.MyApplication;
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
import java.sql.PreparedStatement;



public class Connect{
     private java.sql.Connection  con = null;
     private final String url = "jdbc:sqlserver://";
     private final String serverName= "da-dmdb";
     private final String portNumber = "1433";
     private final String databaseName= "db_prospection";
     private final String userName = "sa";
     private final String password = "limubai64";
     // Informs the driver to use server a side-cursor,
     // which permits more than one active statement
     // on a connection.
     private final String selectMethod = "cursor";

     // Constructor
     public Connect(){}

     private String getConnectionUrl(){
          return url+serverName+":"+portNumber+";databaseName="+databaseName+";selectMethod="+selectMethod+";";
     }

     private java.sql.Connection getConnection(){
          try{
               Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
               
               con = java.sql.DriverManager.getConnection(getConnectionUrl(),userName,password);
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
          java.sql.DatabaseMetaData dm = null;
          java.sql.ResultSet rs = null;
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
     /*
     public static void main(String[] args) throws Exception
       {
          Connect myDbTest = new Connect();
          myDbTest.displayDbProperties();
       }*/

      public void excuter(String s){
          java.sql.ResultSet rs = null;
          try{
               con= this.getConnection();
               if(con!=null){
                    String SQL = "exec sp_authenticate 'weizhe.jiao@directannonces.com', '123'";
                    Statement stmt = con.createStatement();
                    rs = stmt.executeQuery(SQL);
                 while (rs.next()) {
                    System.out.println(rs.getString("checked"));
                   }
                    rs.close();
                    stmt.close();
              }
          }catch(Exception e){
               e.printStackTrace();
          }
     }
      
       public Boolean authenticate(String mail, String pass){
          java.sql.ResultSet rs = null;
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

