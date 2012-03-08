/*
 * MyApplication.java
 *
 * Created on 5 décembre 2011, 17:09
 */
 
package com.example.vaadin;           

import com.example.vaadin.module.AppData;
import com.example.vaadin.module.MyAppCaptions;
import com.example.vaadin.module.User;
import com.vaadin.Application;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.vaadin.appfoundation.authentication.data.User;

/** 
 *
 * @author weizhe.jiao
 * @version 
 */

public class MyApplication extends Application {
    
    //private static User userData;


    @Override
    public void init() {


        setTheme("mytheme");
        //SessionHandler.initialize(this);
        MyApplication.debug(3, "SessionHandler");
        // Create another application-level window.
        User userData = new User();
        
        
                // Create the application data instance
        AppData sessionData = new AppData(this);
        
        // Register it as a listener in the application context
        getContext().addTransactionListener(sessionData);
        
        // Initialize the session-global data
        AppData.initLocale(getLocale(),
                           MyAppCaptions.class.getName());
        
        // Also set the user data model
        AppData.setUser(userData);

        
        
        try {
            try {
                setMainWindow(new LoginFormWindow());
            } catch (SQLException ex) {
                Logger.getLogger(MyApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(MyApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    private static int debugLevel=3;
    //public static  Window mywindow = new Window("Second Window");;
    public static void debug(int level, String s)
    {
        if(level<=debugLevel)
        {
            System.out.println(s);
        }
    }


}


