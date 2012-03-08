/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.vaadin;

import com.example.vaadin.module.User;
import com.vaadin.ui.*;

/**
 *
 * @author weizhe.jiao
 */
public class FirstPage extends Window
{
    public FirstPage ()
    {
        super("Hello First Page");
        
        MyApplication.debug(3, "First Page");
        layoutFirst loF=new layoutFirst();
        getWindow().setContent(loF);
        //buildMainLayout();
    //Window.Notification n= Window.Notification("yes!!");

//showNotification ( "Hello Login!!");
        //test();

    }
    @Override
     public void attach() {
         super.attach(); // Must call.
     }


    private Button newContact = new Button("Add contact");
        private Button search = new Button("Search");
        private Button share = new Button("Share");
        private Button help = new Button("Help");
        private SplitPanel horizontalSplit = new SplitPanel(
                SplitPanel.ORIENTATION_HORIZONTAL);

     private void buildMainLayout() {
         //setMainWindow(new Window("Address Book Demo application"));
         MyApplication.debug(3, "buildMainLayout");
         VerticalLayout layout = new VerticalLayout();
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
        this.setContent(layout);
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
}
