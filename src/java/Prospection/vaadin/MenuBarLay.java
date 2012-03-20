/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Prospection.vaadin;

/**
 *
 * @author weizhe.jiao
 */
import Prospection.Stats.StatsAgence;
import Prospection.Stats.StatsTousPrp;
import Prospection.Stats.layoutStatPrp;
import Prospection.Prospecter.layoutProspection;
import Prospection.managementUsers.layoutGestionUsers;
import Prospection.module.AppData;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

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

        MenuBar.MenuItem item;

        final MenuBar.MenuItem edit = menubar.addItem("Stats", null);
        //edit.setDescription("Edit menu");
        item = edit.addItem("Stats Prospecteur", StatsPrpCommand);
        //item.setDescription("Reverses recent changes");
        item = edit.addItem("Stats Tous les Prospecteurs", StatsTousPrpCommand);
        item = edit.addItem("Stats Agences", StatsAgencesCommand);
        
        final MenuBar.MenuItem Logout = menubar.addItem("Déconnecter", menuCommandLogout);

        addComponent(menubar);
    }
    private Command menuCommand = new Command() {
            public void menuSelected(MenuItem selectedItem) {
                getWindow().showNotification("Action " + selectedItem.getText());
            }
    };

    private Command menuCommandLogout = new Command() {
            public void menuSelected(MenuItem selectedItem) {
                AppData.Clear();
                getWindow().getApplication().close();
                
                
            }
    };
    
    
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

}