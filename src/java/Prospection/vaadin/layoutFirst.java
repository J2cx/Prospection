/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Prospection.vaadin;

import Prospection.module.AppData;
import Prospection.module.User;
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

        MyApplication.debug(3, "buildMainLayout end");
    }

        private Label labUser=new Label();
}
