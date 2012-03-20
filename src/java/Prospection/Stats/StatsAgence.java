/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Prospection.Stats;

import Prospection.vaadin.MyApplication;
import Prospection.SQL.SQLContainerHelper;
import com.vaadin.data.Item;
import com.vaadin.ui.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author weizhe.jiao
 */

@SuppressWarnings("serial")
public class StatsAgence  extends VerticalLayout {

    private ArrayList ids_agence;
    private static final String COMMON_FIELD_WIDTH = "16em";
    private static final String LONG_FIELD_WIDTH = "34em";
    
    public StatsAgence() throws SQLException 
    {
        ids_agence=SQLContainerHelper.GetIddsAgences();
        for(Object i:ids_agence)
        {
            FormStatsAgence lsp=new FormStatsAgence((Integer)i);
            this.addComponent(lsp);
        }
    }
    
    final class FormStatsAgence extends Form{
        
        private int id_agence;
        private GridLayout gridL;
        
        public FormStatsAgence(int idAgence) throws SQLException 
        {
            setCaption("Stats Agence");
            this.id_agence=idAgence;
            
            gridL = new GridLayout(2,14);

            // Use top-left margin and spacing
            gridL.setMargin(true, false, false, true);
            gridL.setSpacing(true);
            
            setLayout(gridL);
            // Set up buffering
            setWriteThrough(false);
            // we want explicit 'apply'
            setInvalidCommitted(false);
            // no invalid values in datamodel
            // FieldFactory for customizing the fields and adding validators
            setFormFieldFactory(new PersonFieldFactory());

            Item item =GetStatsAgence(id_agence);
            
            setItemDataSource(item);
            // bind to POJO via BeanItem
            // Determines which properties are shown, and in which order:
            setVisibleItemProperties(Arrays.asList(new String[] { 
                "id_agence","denomination","nbAnnAFaire","nbAnnTraite","nbRDV","nbRAP"
                ,"nbEnCours","nbNonAbouti","nbRefus","RDVinAnn","RDVinTous"
                ,"RDVdifMoyen","nbRDVConteste","ConinRDV","ConinTous"
                ,"CondifMoyen"
            }));
            
        }
        
        public Item GetStatsAgence(int idprp) throws SQLException
        {
            Item item;
            item=SQLContainerHelper.ItemStatsAgence(idprp);
            return item;
        }
        
        
         @Override
        protected void attachField(Object propertyId, Field field) {
                MyApplication.debug(3, "override AttachField!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                MyApplication.debug(3, propertyId.toString());
                if (propertyId.equals("id_agence")) {
                    gridL.addComponent(field, 0, 0);
                }else if (propertyId.equals("denomination")) {
                    gridL.addComponent(field, 1,0);
                }
                else if (propertyId.equals("nbAnnTraite")) {
                    gridL.addComponent(field, 0,1);
                }
                else if (propertyId.equals("nbAnnAFaire")) {
                    gridL.addComponent(field, 1,1);
                }
                else if (propertyId.equals("nbRDV")) {
                    gridL.addComponent(field, 0,2);
                }else if (propertyId.equals("RDVinAnn")) {
                    gridL.addComponent(field, 1,2);
                }else if (propertyId.equals("RDVinTous")) {
                    gridL.addComponent(field, 1,3);
                }else if (propertyId.equals("RDVfifMoyen")) {
                    gridL.addComponent(field, 1,4);
                }
                else if (propertyId.equals("nbRDVConteste")) {
                    gridL.addComponent(field, 0,5);
                }
                else if (propertyId.equals("ConinRDV")) {
                    gridL.addComponent(field, 1,5);
                }
                else if (propertyId.equals("ConinTous")) {
                    gridL.addComponent(field, 1,6);
                }
                else if (propertyId.equals("CondifMoyen")) {
                    gridL.addComponent(field, 1,7);
                }
                else if (propertyId.equals("nbRAP")) {
                    gridL.addComponent(field, 0,8);
                }
                else if (propertyId.equals("nbEnCours")) {
                    gridL.addComponent(field, 0,9);
                }else if (propertyId.equals("nbNonAbouti")) {
                    gridL.addComponent(field, 0,10);
                }else if (propertyId.equals("nbRefus")) {
                    gridL.addComponent(field, 0,11);
                }
           }

        private class PersonFieldFactory extends DefaultFieldFactory {

            @Override
            public Field createField(Item item, Object propertyId,
                    Component uiContext) {
                Field f;
                f = super.createField(item, propertyId, uiContext);

                if ("id_agence".equals(propertyId)) {
                   makeTextField((TextField) f,"ID Agence");
                }else if ("denomination".equals(propertyId)) {
                   makeTextField((TextField) f,"Nom d'Agence");
                }else if ("nbAnnTraite".equals(propertyId)) {
                   makeTextField((TextField) f,"Nombre d'Annonce Traité");
                }else if ("nbAnnAFaire".equals(propertyId)) {
                   makeTextField((TextField) f,"Nombre d'Annonce à Faire");
                }else if ("nbRDV".equals(propertyId)) {
                   makeTextField((TextField) f,"Nombre de RDV");
                }
                else if ("RDVinAnn".equals(propertyId)) {
                   makeTextField((TextField) f,"Pourcent RDV");
                }
                else if ("RDVinTous".equals(propertyId)) {
                   makeTextField((TextField) f,"Pourcent RDV sur Tous RDV");
                }
                else if ("RDVfifMoyen".equals(propertyId)) {
                   makeTextField((TextField) f,"Pourcent RDV sur Moyen");
                } else if ("nbRDVConteste".equals(propertyId)) {
                   makeTextField((TextField) f,"Nombre de RDV Conteste");
                } else if ("ConinRDV".equals(propertyId)) {
                   makeTextField((TextField) f,"Pourcent Conteste sur RDV");
                } else if ("ConinTous".equals(propertyId)) {
                   makeTextField((TextField) f,"Pourcent Conteste sur Tous");
                } else if ("CondifMoyen".equals(propertyId)) {
                   makeTextField((TextField) f,"Pourcent Conteste sur Moyen");
                }
                else if ("nbRAP".equals(propertyId)) {
                   makeTextField((TextField) f,"Nombre de Rappele");
                }else if ("nbEnCours".equals(propertyId)) {
                   makeTextField((TextField) f,"Nombre de En Cours");
                }else if ("nbNonAbouti".equals(propertyId)) {
                   makeTextField((TextField) f,"Nombre de Non Abouti");
                }else if ("nbRefus".equals(propertyId)) {
                   makeTextField((TextField) f,"Nombre de Refus");
                }                
                
                return f;
            }
        }
        
        public void makeTextField(TextField f, String Nom)
        {
            TextField tf=f;
            tf.setNullRepresentation("");
            tf.setWidth(COMMON_FIELD_WIDTH);
            tf.setReadOnly(true);
            tf.setCaption(Nom);
        }
    
    }
}
