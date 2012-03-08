/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Stats;

import com.example.vaadin.MyApplication;
import com.example.vaadin.SQL.SQLContainerHelper;
import com.example.vaadin.managementUsers.layoutGestionUsers;
import com.vaadin.data.Item;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;
import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 * @author weizhe.jiao
 */
@SuppressWarnings("serial")
public class layoutStatPrp  extends VerticalLayout {
    
    private int id_prospecteur;
    private FormStatsPrp fStatsPrp;
    
    private static final String COMMON_FIELD_WIDTH = "16em";
    private static final String LONG_FIELD_WIDTH = "34em";
    
    public layoutStatPrp(int id_prp) throws SQLException
    {
        id_prospecteur=id_prp;
        fStatsPrp=new FormStatsPrp(id_prospecteur);
        
        this.addComponent(fStatsPrp);
    }
    
    final class FormStatsPrp extends Form
    {
        private GridLayout gridL;
        private int prospecteur;
        
        public FormStatsPrp(int id_prp) throws SQLException
        {
            setCaption("Stats Prospecteur");
            prospecteur=id_prp;
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

            Item item =GetStatsPrp(prospecteur);
            
            setItemDataSource(item);
            // bind to POJO via BeanItem
            // Determines which properties are shown, and in which order:
            setVisibleItemProperties(Arrays.asList(new String[] { 
                    "Prospecteur","Prenom","nbAgence","nbAnnonce","nbRDV"
                    ,"nbRAP","nbEnCours","nbNonAbouti","nbRefus"
                    ,"RDVinAnn","RDVinTous","RDVfifMoyen","NAinAnn"
                    ,"RefusinAnn","nbRDVConteste","ConinRDV","ConinTous"
                    ,"CondifMoyen"
            }));
        }
        
        public Item GetStatsPrp(int idprp) throws SQLException
        {
            Item item;
            item=SQLContainerHelper.ItemStatsPrp(idprp);
            return item;
        }
        
        
         @Override
        protected void attachField(Object propertyId, Field field) {
                MyApplication.debug(3, "override AttachField!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                MyApplication.debug(3, propertyId.toString());
                if (propertyId.equals("Prospecteur")) {
                    gridL.addComponent(field, 0, 0);
                }
                else if (propertyId.equals("Prenom")) {
                    gridL.addComponent(field, 1,0);
                }
                else if (propertyId.equals("nbAgence")) {
                    gridL.addComponent(field, 0,1);
                }
                else if (propertyId.equals("nbAnnonce")) {
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
                }else if (propertyId.equals("NAinAnn")) {
                    gridL.addComponent(field, 1,10);
                }
                else if (propertyId.equals("nbRefus")) {
                    gridL.addComponent(field, 0,11);
                }else if (propertyId.equals("RefusinAnn")) {
                    gridL.addComponent(field, 1,11);
                }
           }

        private class PersonFieldFactory extends DefaultFieldFactory {

            @Override
            public Field createField(Item item, Object propertyId,
                    Component uiContext) {
                Field f;
                f = super.createField(item, propertyId, uiContext);

                if ("Prospecteur".equals(propertyId)) {
                   makeTextField((TextField) f,"ID");
                } else if ("Prenom".equals(propertyId)) {
                   makeTextField((TextField) f,"Prénom");
                }else if ("nbAgence".equals(propertyId)) {
                   makeTextField((TextField) f,"Nombre d'Agence");
                }else if ("nbAnnonce".equals(propertyId)) {
                   makeTextField((TextField) f,"Nombre d'Annonce");
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
                }
                else if ("NAinAnn".equals(propertyId)) {
                   makeTextField((TextField) f,"Pourcent Non Abouti");
                }else if ("nbRefus".equals(propertyId)) {
                   makeTextField((TextField) f,"Nombre de Refus");
                }else if ("RefusinAnn".equals(propertyId)) {
                   makeTextField((TextField) f,"Pourcent Refus");
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
