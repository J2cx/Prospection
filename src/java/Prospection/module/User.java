/* * To change this template, choose Tools | Templates
 * and open the template in the editor. */

package Prospection.module;

/** * * @author weizhe.jiao */

public class User 
{   
    static String username;    
    static String password; 
    static int id_prospecteur;  
    
    public User() 
    {  
    }  
    
    public User(String username, String password, int id)
    {       
        this.username = username;    
        this.password = password; 
        this.id_prospecteur=id;  
    }    
    
    static public User setUser(String username, String password, int id) 
    {      
        if ("user".equals(username) && "pass".equals(password)) 
        {      
            User u = new User(username, password,id);   
            return u;      
        }    
        return null;  
    }       
    
    public static String getUsername()
    {        
        return username; 
    }        
    
    public static int getId()  
    {       
        return id_prospecteur;  
    }    
    
    public static String getPassword()
    {     
        return password;   
    }

}