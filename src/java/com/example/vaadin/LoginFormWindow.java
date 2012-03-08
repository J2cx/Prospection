package com.example.vaadin;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.example.vaadin.SQL.SQLContainerHelper;
import com.example.vaadin.managementUsers.layoutGestionUsers;
import com.vaadin.addon.sqlcontainer.SQLContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;



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
/*
        SQLContainer container=SQLContainerHelper.ContainerAnnoncePrixDate();
        container.setAutoCommit(false);
        container.getItem(container.firstItemId()).getItemProperty("ann_prix").setValue(145000);
        container.commit();

        
        final Table tb=new Table();
        final SQLContainer container=SQLContainerHelper.ContainerFichAnnonceRecent(12625,14,251945);
        tb.setContainerDataSource(container);
        tb.setVisibleColumns(new String[] { "coAdresse" });
        tb.setColumnHeaders(new String[] { "coAdresse" });
        tb.setEditable(true);
        this.addComponent(tb);
        
        Button btnValid=new Button("valider");
        btnValid.addListener(
                    new Button.ClickListener() {
                    public void buttonClick(Button.ClickEvent event) {
                try {
                    MyApplication.debug(3, "buttonClick");
                    tb.commit();
                    container.commit();
                } catch (UnsupportedOperationException ex) {
                    Logger.getLogger(LoginFormWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(LoginFormWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                    }
            });
        this.addComponent(btnValid);*/
    }
}
