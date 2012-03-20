/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Prospection.Stats;


import Prospection.SQL.SQLContainerHelper;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author weizhe.jiao
 */
@SuppressWarnings("serial")
public class StatsTousPrp extends VerticalLayout{
    
    private ArrayList ids_prp;
    
    public StatsTousPrp() throws SQLException
    {
        ids_prp=SQLContainerHelper.GetIddsPrps();
        for(Object i:ids_prp)
        {
            layoutStatPrp lsp=new layoutStatPrp((Integer)i);
            this.addComponent(lsp);
        }
    
    }
    
}
