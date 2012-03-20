package Prospection.vaadin;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;
import java.net.MalformedURLException;
import java.sql.SQLException;



/**
 *
 * @author weizhe.jiao
 */
public class LoginFormWindow extends Window
{
    private Panel loginPanel;
    private LoginFormExample login;

    public LoginFormWindow () throws MalformedURLException, SQLException
    {
        super("LoginFormWindow!");
        MyApplication.debug(3, "LoginFormWindow");
        //login = new LoginFormExample();
        this.addComponent(new LoginFormExample());

    }
}
