/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Prospection.vaadin;

/**
 *
 * @author weizhe.jiao
 */



import Prospection.SQL.SQLContainerHelper;
import Prospection.module.AppData;
import Prospection.module.User;
import com.vaadin.addon.sqlcontainer.SQLContainer;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class LoginFormExample extends VerticalLayout
{
    public LoginFormExample() 
    {
        LoginForm login = new LoginForm();
        login.setWidth("100%");
        login.setHeight("300px");
        login.addListener(new LoginForm.LoginListener() {
            @SuppressWarnings("empty-statement")
            public void onLogin(LoginEvent event) {
                try {
                    if (authenticateClient(event.getLoginParameter("username"), event.getLoginParameter("password"))) 
                    /*Connect con=new Connect();
                    if(con.authenticate(event.getLoginParameter("username"),event.getLoginParameter("password")))*/
                    {
                        FirstPage fp = new FirstPage();
                        getWindow().getApplication().addWindow(fp);
                        getWindow().open(new ExternalResource(fp.getURL()));
                    }
                } catch (Exception ex) {
                    Logger.getLogger(LoginFormExample.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        addComponent(login);



    }

    VerticalLayout layout = new VerticalLayout();


    public Boolean authenticateClient( String login, String password) throws Exception
    {
        String query="SELECT  id,mail,nom,pass FROM Prospecteurs where mail='"+login+"' and pass='"+password+"'";
        SQLContainer container=SQLContainerHelper.ContainerFreeQuery(query);
        if(container.size()>0)
        {
            int id = (Integer)container.getItem(container.firstItemId()).getItemProperty("id").getValue();
            AppData.setUser(new User(login,password, id));
            this.getApplication().setMainWindow(new FirstPage());
                return true;
        }

        throw new Exception("Login failed!");
    }

    private void buildMainLayout() {
         //setMainWindow(new Window("Address Book Demo application"));
        
         MyApplication.debug(3, "buildMainLayout");
         //getWindow().setName("FirstPage!");
         layout = new VerticalLayout();
         layout.setSizeFull();

         layout.addComponent(createToolbar());
         layout.addComponent(horizontalSplit);

        /* Allocate all available extra space to the horizontal split panel */

        layout.setExpandRatio(horizontalSplit, 1);
        /* Set the initial split position so we can have a 200 pixel menu to the left */

        horizontalSplit.setSplitPosition(200, SplitPanel.UNITS_PIXELS);
MyApplication.debug(3, "buildMainLayout 2");
try
{
    if(layout!=null)
    {
        getWindow().setLayout(layout);
        MyApplication.debug(3, "buildMainLayout end");

    }

         }
catch(Exception e)
{
    e.printStackTrace();
}

    }

    public HorizontalLayout createToolbar() {

        MyApplication.debug(3, "createToolbar");
        HorizontalLayout lo = new HorizontalLayout();
         lo.addComponent(newContact);
         lo.addComponent(search);
         lo.addComponent(share);
         lo.addComponent(help);

        return lo;

    }
        private Button newContact = new Button("Add contact");
        private Button search = new Button("Search");
        private Button share = new Button("Share");
        private Button help = new Button("Help");
        private SplitPanel horizontalSplit = new SplitPanel(
                SplitPanel.ORIENTATION_HORIZONTAL);
}

