/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.vaadin;

/**
 *
 * @author weizhe.jiao
 */
import Stats.StatsAgence;
import Stats.StatsTousPrp;
import Stats.layoutStatPrp;
import com.example.vaadin.managementUsers.gestionUsers;
import com.example.vaadin.managementUsers.FormChercher;

import com.example.vaadin.Prospecter.layoutProspection;
//import com.example.vaadin.managementUsers.TableListUsers;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

import com.example.vaadin.managementUsers.layoutGestionUsers;
import com.example.vaadin.module.AppData;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuBarLay extends VerticalLayout {
    private MenuBar menubar = new MenuBar();
    

    public MenuBarLay() {
        MyApplication.debug(3, "buildMainLayout 4444");
        
        //menubar.setSizeFull();
        
        // Add tooltip to the menubar itself
        menubar.setDescription("Perform some actions by selecting them from the menus");
        // Save reference to individual items so we can add sub-menu items to
        // them
        final MenuBar.MenuItem prospection = menubar.addItem("Prospection", ProspecterCommand);
 
        final MenuBar.MenuItem file = menubar.addItem("Gestion des Users", GULUCommand);
/*
        //Weizhe JIAO, 
        final MenuBar.MenuItem itemGU = file.addItem("Gestion des Users", GUCommand);
        MenuBar.MenuItem itemsGU;

        itemsGU=itemGU.addItem("List des Users", GULUCommand);
       itemsGU=itemGU.addItem("Rechercher des Users", GUChercherCommand);
        itemsGU=itemGU.addItem("Création du User", GUCreateUserCommand);


        final MenuBar.MenuItem newItem = file.addItem("Gestion des Users", null);
        //newItem.setDescription("Add a new..");
        MenuBar.MenuItem open = file.addItem("Gestion des Authentifications", menuCommand);
        //open.setDescription("Retrieve a file from the filesystem");
        file.addSeparator();
 */
        MenuBar.MenuItem item;
/*        item = newItem.addItem("File", menuCommand);
        //item.setDescription("Open a file");
        item = newItem.addItem("Folder", menuCommand);
        //item.setDescription("Open a folder");
        item = newItem.addItem("Project...", menuCommand);
        //item.setDescription("Open a project");
        item = file.addItem("Close", menuCommand);
        //item.setDescription("Closes the selected file");
        item = file.addItem("Close All", menuCommand);
        //item.setDescription("Closes all files");
        file.addSeparator();
        item = file.addItem("Save", menuCommand);
        //item.setDescription("Saves the file");
        item = file.addItem("Save As...", menuCommand);
        //item.setDescription("Saves the file with a different name");
        item = file.addItem("Save All", menuCommand);
        //item.setDescription("Saves all files");
*/
        final MenuBar.MenuItem edit = menubar.addItem("Stats", null);
        //edit.setDescription("Edit menu");
        item = edit.addItem("Stats Prospecteur", StatsPrpCommand);
        //item.setDescription("Reverses recent changes");
        item = edit.addItem("Stats Tous les Prospecteurs", StatsTousPrpCommand);
        item = edit.addItem("Stats Agences", StatsAgencesCommand);
/*        //item.setDescription("Redoes undone changed");
        edit.addSeparator();
        item = edit.addItem("Cut", menuCommand);
        //item.setDescription("Copies the text to the clipboard and removes it");
        item = edit.addItem("Copy", menuCommand);
        //item.setDescription("Copies the text to the clipboard");
        item = edit.addItem("Paste", menuCommand);
        //item.setDescription("Copies the contents of the clipboard on to the document");
        edit.addSeparator();
        final MenuBar.MenuItem find = edit.addItem("Find/Replace", menuCommand);
        //find.setDescription("Find or Replace text");
        // Actions can be added inline as well, of course
        item = find.addItem("Google Search", new Command() {
            public void menuSelected(MenuItem selectedItem) {
                getWindow().open(new ExternalResource("http://www.google.com"));
            }
        });
        //item.setDescription("Search with Google");
        find.addSeparator();
        item = find.addItem("Find/Replace...", menuCommand);
        //item.setDescription("Finds or replaces text");
        item = find.addItem("Find Next", menuCommand);
        //item.setDescription("Find the next occurrence");
        item = find.addItem("Find Previous", menuCommand);
        //item.setDescription("Find the previous occurrence");
*/
        addComponent(menubar);
    }
    private Command menuCommand = new Command() {
            public void menuSelected(MenuItem selectedItem) {
                getWindow().showNotification("Action " + selectedItem.getText());
            }
    };
/*
   private Command GUCommand = new Command() {
            public void menuSelected(MenuItem selectedItem) {
               VerticalLayout vl=new VerticalLayout();
                MenuBarLay mte= new MenuBarLay();
                gestionUsers tmfe=new gestionUsers();
                vl.addComponent(mte);
                vl.addComponent(tmfe);
                //vl.setComponentAlignment(mte,Alignment.TOP_CENTER);
                //vl.setComponentAlignment(tmfe,Alignment.TOP_CENTER);
                getWindow().setContent(vl);
            }
    };
*/
               private Command ProspecterCommand = new Command() {
            public void menuSelected(MenuItem selectedItem) {
            try {
                VerticalLayout vl = new VerticalLayout();
                MenuBarLay mte = new MenuBarLay();
                layoutProspection lp = new layoutProspection();
                if(lp.GetIdAgence()==0 || lp.GetIdAnnonce()==0)
                {
                    getWindow().showNotification("Il n'y pas d'agence / annonce pour prospecter!");
                }
                MyApplication.debug(3, "menuCommand_Prospection");
                vl.addComponent(mte);
                //vl.addComponent(new Label("vl add new label"));
                vl.addComponent(lp);
                //vl.setComponentAlignment(mte,Alignment.TOP_CENTER);
                //vl.setComponentAlignment(lp,Alignment.TOP_CENTER);
                getWindow().setContent(vl);
            } catch (MalformedURLException ex) {
                Logger.getLogger(MenuBarLay.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(MenuBarLay.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    };
               
    private Command StatsPrpCommand = new Command() {
            public void menuSelected(MenuItem selectedItem) {
            try {
                VerticalLayout vl = new VerticalLayout();
                MenuBarLay mte = new MenuBarLay();
                int id_prp=AppData.getUser().getId();
                layoutStatPrp lsp = new layoutStatPrp(id_prp);
                MyApplication.debug(3, "menuCommand_Prospection");
                vl.addComponent(mte);
                vl.addComponent(lsp);
                getWindow().setContent(vl);
            } catch (SQLException ex) {
                Logger.getLogger(MenuBarLay.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    };
    
    private Command StatsTousPrpCommand = new Command() {
            public void menuSelected(MenuItem selectedItem) {
            try {
                VerticalLayout vl = new VerticalLayout();
                MenuBarLay mte = new MenuBarLay();
                StatsTousPrp lsp = new StatsTousPrp();
                MyApplication.debug(3, "menuCommand_Prospection");
                vl.addComponent(mte);
                vl.addComponent(lsp);
                getWindow().setContent(vl);
            } catch (SQLException ex) {
                Logger.getLogger(MenuBarLay.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    };
    
    private Command StatsAgencesCommand = new Command() {
            public void menuSelected(MenuItem selectedItem) {
            try {
                VerticalLayout vl = new VerticalLayout();
                MenuBarLay mte = new MenuBarLay();
                StatsAgence lsp = new StatsAgence();
                MyApplication.debug(3, "menuCommand_Prospection");
                vl.addComponent(mte);
                vl.addComponent(lsp);
                getWindow().setContent(vl);
            } catch (SQLException ex) {
                Logger.getLogger(MenuBarLay.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    };

    private Command GULUCommand = new Command() {
            public void menuSelected(MenuItem selectedItem) {
            try {
                VerticalLayout vl = new VerticalLayout();
                MenuBarLay mte = new MenuBarLay();
                layoutGestionUsers tmfe = new layoutGestionUsers();
                vl.addComponent(mte);
                vl.addComponent(tmfe);
                //vl.setComponentAlignment(mte,Alignment.TOP_CENTER);
                //vl.setComponentAlignment(tmfe,Alignment.TOP_CENTER);
                getWindow().setContent(vl);
            } catch (MalformedURLException ex) {
                Logger.getLogger(MenuBarLay.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(MenuBarLay.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    };
/*
        private Command GUChercherCommand = new Command() {
            public void menuSelected(MenuItem selectedItem) {
                VerticalLayout vl=new VerticalLayout();
                MenuBarLay mte= new MenuBarLay();
                FormChercher tmfe=new FormChercher();
                vl.addComponent(mte);
                vl.addComponent(tmfe);
                getWindow().setContent(vl);
            }
    };

       private Command GUCreateUserCommand = new Command() {
            public void menuSelected(MenuItem selectedItem) {
                VerticalLayout vl=new VerticalLayout();
                MenuBarLay mte= new MenuBarLay();
                gestionUsers tmfe=new gestionUsers();
                vl.addComponent(mte);
                vl.addComponent(tmfe);
                getWindow().setContent(vl);
            }
    };
*/
}