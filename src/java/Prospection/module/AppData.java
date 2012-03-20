/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Prospection.module;

import com.vaadin.Application;
import Prospection.SQL.SQL;
import Prospection.vaadin.MyApplication;

import com.vaadin.service.ApplicationContext.TransactionListener;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.terminal.gwt.server.WebBrowser;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;

/**
 *
 * @author weizhe.jiao
 */
/** Holds data for one user session. */
public class AppData
       implements TransactionListener, Serializable {
    private ResourceBundle bundle;
    private Locale locale;   // Current locale
    private static String userData; // Trivial data model for the user
    
    private Application app; // For distinguishing between apps

    private static ThreadLocal<AppData> instance =
        new ThreadLocal<AppData>();
    
    private static User user;
    
    private static String ip;
    private static String host;
    private static String sessionid;
    private static String userAgent;
    
    private static HttpSession httpsession;
    
    
    public AppData(Application app) {
        this.app = app;

        // It's usable from now on in the current request
        instance.set(this);
    }

    public static void Clear()
    {
        MyApplication.debug(3, "Session Clear: "+sessionid);
        if(sessionid!=null)
        {
            String uAgent="";
            if(userAgent!=null)
            {
                uAgent=userAgent;
            }

            Date dateNow=new Date();
            DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String s=df.format(dateNow);
            SQL.excuteUpdate("update Sessions set LASTMODIFY= '"+s+"'"
                    + " where SessionID='"+sessionid+"'");
            
            httpsession.invalidate();
        }
    }
    
    @Override
    public void transactionStart(Application application,
                                 Object transactionData) {
        // Set this data instance of this application
        // as the one active in the current thread. 
        if (this.app == application)
            instance.set(this);
        
        if (transactionData instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) transactionData;
            
            httpsession=((WebApplicationContext)  this.app.getContext()).getHttpSession();
            
            ip = httpServletRequest.getRemoteAddr();
            host = httpServletRequest.getRemoteHost();
            sessionid=httpsession.getId();
            
            WebBrowser browser =(WebBrowser) this.app.getMainWindow().getTerminal();
            if(browser!=null)
            {
                userAgent=browser.getBrowserApplication();
            }
            MyApplication.debug(3, "ip: "+ip);
            MyApplication.debug(3, "host: "+host);
             MyApplication.debug(3, "sessionid: "+sessionid); 
             MyApplication.debug(3, "userAgent: "+userAgent);
            
        }
    }

    @Override
    public void transactionEnd(Application application,
                               Object transactionData) {
        // Clear the reference to avoid potential problems
        if (this.app == application)
            instance.set(null);
        
        MyApplication.debug(3, "transactionEnd");
        
        
    }

    public static void initLocale(Locale locale,
                                  String bundleName) {
        instance.get().locale = locale;
        instance.get().bundle =
            ResourceBundle.getBundle(bundleName, locale);
    }
    
    public static Locale getLocale() {
        return instance.get().locale;
    }

    public static String getMessage(String msgId) {
        return instance.get().bundle.getString(msgId);
    }

    public static String getUserData() {
        return instance.get().userData;
    }

    public static void setUserData(String userData) {
        instance.get().userData = userData;
    }
    
    public static HttpSession getSession() {
        return httpsession;
    }
    
    public static User getUser() {
        return instance.get().user;
    }

    public static void setUser(User userData) {
        instance.get().user = userData;
        
        if(userData==null)
            return;
        
        if(userData.getId()>0)
        {
            String uAgent="";
            if(userAgent!=null)
            {
                uAgent=userAgent;
            }
            
            SQL.excuteUpdate("insert into Sessions (Prospecteur, SessionID"
                    + ", SessionIP, UserAgent)"
                    + "values"
                    + "("+Integer.toString(User.getId())+", '"+sessionid+"', '"
                    + ip+"', '"+uAgent+"')");
        }
    }
    
}
