/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.vaadin;

import com.example.vaadin.module.AppData;
import com.example.vaadin.module.User;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.*;
/**
 *
 * @author weizhe.jiao
 */
public class layoutFirst extends VerticalLayout {


    public layoutFirst() {


        MyApplication.debug(3, "buildMainLayout 33333");
         setSizeFull();
         MenuBarLay mbte=new MenuBarLay();
         addComponent(mbte);
         if(AppData.getUser()!=null)
         {
         String username=AppData.getUser().getUsername();
         labUser.setCaption(username+Integer.toString(AppData.getUser().getId()));
         addComponent(labUser);
        }

         /*
         addComponent(createToolbar());
         addComponent(horizontalSplit);
*/
        /* Allocate all available extra space to the horizontal split panel */
//        setExpandRatio(horizontalSplit, 1);
        /* Set the initial split position so we can have a 200 pixel menu to the left */
//        horizontalSplit.setSplitPosition(200, SplitPanel.UNITS_PIXELS);

        MyApplication.debug(3, "buildMainLayout end");
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
        private Label labUser=new Label();

        private Button newContact = new Button("Add contact");
        private Button search = new Button("Search");
        private Button share = new Button("Share");
        private Button help = new Button("Help");
        private SplitPanel horizontalSplit = new SplitPanel(
                SplitPanel.ORIENTATION_HORIZONTAL);

}
