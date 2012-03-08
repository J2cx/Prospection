/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.vaadin.module;

/**
 *
 * @author weizhe.jiao
 */
public class User {
    String username;
    String password;
    int id_prospecteur;

   public User() {
    }

    public User(String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.id_prospecteur=id;
    }
    
    static public User setUser(String username, String password, int id) {
        if ("user".equals(username) && "pass".equals(password)) {
            User u = new User(username, password,id);
            return u;
        }
        return null;
    }
    
    public String getUsername(){
        return username;
    }
    
    public int getId()
    {
        return id_prospecteur;
    }
    
    /*
    public String getPassword(){
        return password;
    }*/
}
