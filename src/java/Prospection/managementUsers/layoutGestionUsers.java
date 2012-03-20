/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Prospection.managementUsers;

/**
 *
 * @author weizhe.jiao
 */
import Prospection.Stats.layoutStatPrp;
import Prospection.vaadin.MenuBarLay;
import java.util.Arrays;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.NativeSelect;



import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;


//import java.util.Calendar;
 //import java.text.SimpleDateFormat;
 


import Prospection.vaadin.MyApplication;
import Prospection.module.Person;
import java.sql.SQLException;
import java.util.HashSet;

import com.vaadin.data.Item;
import com.vaadin.event.Action;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.CellStyleGenerator;
import com.vaadin.ui.VerticalLayout;

import com.vaadin.addon.sqlcontainer.SQLContainer;


import java.util.logging.Level;
import java.util.logging.Logger;
import Prospection.SQL.SQLContainerHelper;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import java.net.MalformedURLException;
import java.net.URL;


@SuppressWarnings("serial")
public class layoutGestionUsers extends VerticalLayout {
    
    Person person;
    BeanItem<Person> personItem;
    
    private HorizontalLayout main=new HorizontalLayout();
    private TableListUsers tblist;
    private VerticalLayout infos = new VerticalLayout();
    private FormInfosUsers fInfos;
    private TextField textNb=new TextField();
    
    private SQLContainer containerTable;
    private SQLContainer containerProspecteursForm;

    private int nbUsers;
    private int nbSelected;
    private Item itemForm;

    public layoutGestionUsers() throws SQLException, MalformedURLException {
            // Our main layout is a horizontal layout
            main.setSizeFull();


            itemForm=firstUser();
            nbSelected=1;

            containerProspecteursForm=SQLContainerHelper.GetProspecteursFormTable();

            tblist=new TableListUsers();
            fInfos=new FormInfosUsers(itemForm);
            infos.addComponent(fInfos);




            main.addComponent(tblist);
            main.addComponent(infos);

            addComponent(main);
    }

    public void setTextValue()
    {
            MyApplication.debug(3, Integer.toString(nbSelected)+" / "+Integer.toString(nbUsers));
            if(nbUsers>=0 && nbSelected>=0)
            {
                textNb.setValue(Integer.toString(nbSelected)+" / "+Integer.toString(nbUsers));
            }
    }

    private Item firstUser() throws SQLException
    {
        /*String query="SELECT top 1 mail as login, Pre as firstName, Nom as lastName,"
                + " Adr_Nr as nb, Adr_Rue as rue, Adr_CP as cp, Adr_Ville as ville, "
                + " Tel_Fix as fix, Tel_Pro as fax, Tel_Mob as portable "
                + " FROM Prospecteurs";*/
        String query ="select top 1 * from prospecteurs";
        SQLContainer container=SQLContainerHelper.ContainerFreeQuery(query);
        return container.getItem(container.firstItemId());
    }

    public static final Object[] NATURAL_COL_ORDER = new Object[] {
                "FIRSTNAME", "LASTNAME", "EMAIL", "PHONENUMBER", "STREETADDRESS",
                 "POSTALCODE", "CITYID" };
    public static final String[] COL_HEADERS_ENGLISH = new String[] {
                "First name", "Last name", "Email", "Phone number",
                "Street Address", "Postal Code", "City" };

    private static final ThemeResource retourrapide = new ThemeResource("../mytheme/icons/20x20_lectureretourrapide.png");
    private static final ThemeResource retour = new ThemeResource("../mytheme/icons/20x20_lectureretour.png");
    private static final ThemeResource avant = new ThemeResource("../mytheme/icons/20x20_lectureavant.png");
    private static final ThemeResource avancerapide = new ThemeResource("../mytheme/icons/20x20_lectureavancerapide.png");
    private static final ThemeResource add = new ThemeResource("../mytheme/icons/add-icon.png");
    private static final ThemeResource valid = new ThemeResource("../mytheme/icons/validation.jpg");
    private static final String[] lstatus = new String[] { "active", "suspendre" };
    
    Window subwindow;
    Boolean bvalid;
    
    final class FormInfosUsers extends Form {

        private GridLayout ourLayout;
        //Person person;

        private static final String COMMON_FIELD_WIDTH = "12em";
        private static final String LONG_FIELD_WIDTH = "26em";
        private static final String BUTTON_NAV_WIDTH = "4em";

        private Button btnBackRapid=new Button();
        private Button btnBack=new Button();
        private Button btnForward=new Button();
        private Button btnForwardRapid=new Button();

        private Button btnAdd=new Button();
        private Button btnValid=new Button();
        
        private Button btnStats=new Button("Stats");

        
        

        @SuppressWarnings("LeakingThisInConstructor")
        public FormInfosUsers(Item personItem) throws MalformedURLException {
            setCaption("Personal details");
            // Create our layout (3x3 GridLayout)
            ourLayout = new GridLayout(6,19);

            // Use top-left margin and spacing
            ourLayout.setMargin(true, false, false, true);
            ourLayout.setSpacing(true);

            ourLayout.addComponent(new Label("Address:"),0,3,5,3);
            ourLayout.addComponent(new Label("Contact:"),0,6,5,6);

            setTextValue();
            ourLayout.addComponent(textNb,2,16,3,16);

            btnBackRapid.setIcon(retourrapide);
            btnBack.setIcon(retour);
            btnForward.setIcon(avant);
            btnForwardRapid.setIcon(avancerapide);
            btnAdd.setIcon(add);
            btnValid.setIcon(valid);

            btnBackRapid.setWidth(BUTTON_NAV_WIDTH);
            btnBack.setWidth(BUTTON_NAV_WIDTH);
            btnForward.setWidth(BUTTON_NAV_WIDTH);
            btnForwardRapid.setWidth(BUTTON_NAV_WIDTH);
            btnAdd.setWidth(BUTTON_NAV_WIDTH);
            btnValid.setWidth(BUTTON_NAV_WIDTH);

            btnBackRapid.addListener(
                    new Button.ClickListener() {
                @Override
                    public void buttonClick(ClickEvent event) {
                        MyApplication.debug(3, "buttonClick");
                        if(nbSelected>1)
                        {
                        try {
                            nbSelected = 1;
                            setFormbyIndex(nbSelected);
                        } catch (SQLException ex) {
                            Logger.getLogger(layoutGestionUsers.class.getName()).log(Level.SEVERE, null, ex);
                        }
                                
                        }
                    }
            });

            btnBack.addListener(
                    new Button.ClickListener() {
                @Override
                    public void buttonClick(ClickEvent event) {
                        MyApplication.debug(3, "buttonClick");
                        if(nbSelected>1)
                        {
                        try {
                            nbSelected--;
                            setFormbyIndex(nbSelected);
                        } catch (SQLException ex) {
                            Logger.getLogger(layoutGestionUsers.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                    }
            });

            btnForward.addListener(
                    new Button.ClickListener() {
                @Override
                    public void buttonClick(ClickEvent event) {
                        MyApplication.debug(3, "buttonClick");
                        if(nbSelected<nbUsers)
                        {
                        try {
                            nbSelected++;
                            setFormbyIndex(nbSelected);
                        } catch (SQLException ex) {
                            Logger.getLogger(layoutGestionUsers.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                    }
            });

            btnForwardRapid.addListener(
                    new Button.ClickListener() {
                @Override
                    public void buttonClick(ClickEvent event) {
                        MyApplication.debug(3, "buttonClick");
                        if(nbSelected<nbUsers)
                        {
                        try {
                            nbSelected=nbUsers;
                            setFormbyIndex(nbSelected);
                        } catch (SQLException ex) {
                            Logger.getLogger(layoutGestionUsers.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                    }
            });

            btnAdd.addListener(
                    new Button.ClickListener() {
                @Override
                    public void buttonClick(ClickEvent event) {
                        MyApplication.debug(3, "btnAddClick");
                        setFormNull();
                    }
            });
            
            btnValid.addListener(
                    new Button.ClickListener() {
                @Override
                    public void buttonClick(ClickEvent event) {
                        MyApplication.debug(3, "btnValidClick");
                        bvalid=false;

                        openSubWindowValid();
                        }
            });

            btnStats.addListener(new Button.ClickListener() {
                @Override
                    public void buttonClick(ClickEvent event) {
                    try {
                        MyApplication.debug(3, "btnValidClick");
                        getWindow().showNotification("Stats");
                        
                        VerticalLayout vl = new VerticalLayout();
                        MenuBarLay mte = new MenuBarLay();
                        int id_prp=nbSelected;
                        layoutStatPrp lsp = new layoutStatPrp(id_prp);
                        MyApplication.debug(3, "menuCommand_Prospection");
                        vl.addComponent(mte);
                        vl.addComponent(lsp);
                        getWindow().setContent(vl);
                    } catch (SQLException ex) {
                        Logger.getLogger(layoutGestionUsers.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        }
            });
            

            ourLayout.addComponent(btnBackRapid,0,16);
            ourLayout.addComponent(btnBack,1,16);
            ourLayout.addComponent(btnForward,4,16);
            ourLayout.addComponent(btnForwardRapid,5,16);
            ourLayout.addComponent(btnAdd,0,17);
            ourLayout.addComponent(btnValid,1,17);

            
            
            //Panel paneImage=new Panel();
               URL url = new URL("http://dev.directmandat.com/ul/601DD106-CE30-43DF-ADE2-37F720D84A46.jpeg");
                Embedded image = new Embedded("", new ExternalResource(url));
                image.setSizeFull();
                ourLayout.addComponent(image,0,14,5,14);
                ourLayout.addComponent(btnStats,0,18,5,18);

            
            
            
            setLayout(ourLayout);
            // Set up buffering
            setWriteThrough(false);
            // we want explicit 'apply'
            setInvalidCommitted(false);
            // no invalid values in datamodel
            // FieldFactory for customizing the fields and adding validators
            setFormFieldFactory(new PersonFieldFactory());

            setItemDataSource(personItem);
            // bind to POJO via BeanItem
            // Determines which properties are shown, and in which order:
            setVisibleItemProperties(Arrays.asList(new String[] { "Pre",
            "Nom", "mail","pass","Adr_Nr","Adr_Rue","Adr_CP","Adr_Ville",
            "Tel_Fix","Tel_DA","Tel_Mob","birthday","observation","status",
            "DATE","ID"
            }));

            
            MyApplication.debug(3, "FormInfosUsers!!!!!!!!!!!!!!!!!!!!!");

        }

        public void doValid()
        {
                        MyApplication.debug(3, "after openSubWindowValid ");
                        if(!bvalid)
                        {return;}
MyApplication.debug(3, "after bvalid ");
                        Item item = fInfos.getItemDataSource();

                        if(itemForm==null && item!=null)
                        {
                            try {
                                MyApplication.debug(3, "hello valid Click");
                                //Item item = fInfos.getItemDataSource();

                                
                                

                                fInfos.commit();
                                containerProspecteursForm.commit();
                                //MyApplication.debug(3, "container size: "+Integer.toString(containerProspecteursForm.size()));
                                itemForm = item;
                                //btnValid.removeListener(this);
                                tblist.updateTable();
                                tblist.requestRepaint();
                                nbSelected=nbUsers;
                                setTextValue();
                            } catch (UnsupportedOperationException ex) {
                                Logger.getLogger(layoutGestionUsers.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(layoutGestionUsers.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else if(itemForm != null && item != null)
                        {
                            try { 
                               MyApplication.debug(3, "hello valid Click");
                                
                                Date dateNow=new Date();
                                Timestamp time=new Timestamp(dateNow.getTime());
                                item.getItemProperty("LASTMODIFY").setValue(time);
                                //containerProspecteursForm.getContainerProperty(tempItemId, "LASTMODIFY").setValue(time);

                                fInfos.commit();
                                containerProspecteursForm.commit();
                                //MyApplication.debug(3, "container size: "+Integer.toString(containerProspecteursForm.size()));
                                itemForm = item;
                                //btnValid.removeListener(this);
                                tblist.updateTable();
                                tblist.requestRepaint();
                                //nbSelected=nbUsers;
                                setTextValue();
                            } catch (UnsupportedOperationException ex) {
                                Logger.getLogger(layoutGestionUsers.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(layoutGestionUsers.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
        


        public void subWindowValid()
        {
            // Create the window...
            subwindow = new Window("Valider votre modification");
            // ...and make it modal
            subwindow.setModal(true);

            // Configure the windws layout; by default a VerticalLayout
            VerticalLayout layout = (VerticalLayout) subwindow.getContent();
            layout.setMargin(true);
            layout.setSpacing(true);
            // Add some content; a label and a close-button
            Label message = new Label("Valider le mise à jour / l'addition.");
            subwindow.addComponent(message);

            Button close = new Button("Cancel", new Button.ClickListener() {
                // inline click-listener
                @Override
                public void buttonClick(ClickEvent event) {
                    // close the window by removing it from the parent window
                    bvalid=false;
                    ((Window)(subwindow.getParent())).removeWindow(subwindow);
                }
            });


            Button valid = new Button("Valider", new Button.ClickListener() {

                @Override
                 public void buttonClick(ClickEvent event) {
                    // close the window by removing it from the parent window
                     bvalid=true;
                    ((Window)(subwindow.getParent())).removeWindow(subwindow);
                    doValid();
                }
            });
            // The components added to the window are actually added to the window's
            // layout; you can use either. Alignments are set using the layout
            HorizontalLayout hlayout=new HorizontalLayout();

            hlayout.addComponent(valid);
            hlayout.addComponent(close);

            hlayout.setComponentAlignment(valid, Alignment.BOTTOM_RIGHT);
            hlayout.setComponentAlignment(close, Alignment.BOTTOM_RIGHT);
            layout.addComponent(hlayout);
            layout.setComponentAlignment(hlayout, Alignment.BOTTOM_RIGHT);
        }

        public void openSubWindowValid()
        {
            subWindowValid();
            if (subwindow.getParent() != null) {
                // window is already showing
                getWindow().showNotification("Window is already open");
            }
            else {
                // Open the subwindow by adding it to the parent
                // window
                getWindow().addWindow(subwindow);
            }
        }

        public void setFormbyIndex(int Index) throws SQLException
        {
            String mail = containerTable.getItem(containerTable.getIdByIndex(Index-1)).getItemProperty("mail").getValue().toString();
            itemForm = SQLContainerHelper.GetProspecteurByLogin(mail);
            if (itemForm != null) {
                MyApplication.debug(3, "item not null~~~~");
                setTextValue();
                fInfos.setItemDataSource(itemForm);
                fInfos.requestRepaint();
            }
        }

        public void setFormNull()
        {

            itemForm=null;
            /*
                fInfos.setItemDataSource(bItem);

             */
            try {
                containerProspecteursForm.rollback();
        } catch (UnsupportedOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            containerProspecteursForm.setAutoCommit(false);
            Object tempItemId = containerProspecteursForm.addItem();
            /*Calendar currentDate = Calendar.getInstance();
            SimpleDateFormat formatter=
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.000");
            String dateNow = formatter.format(currentDate.getTime());
 */
            Date dateNow=new Date();
            Timestamp time=new Timestamp(dateNow.getTime());

            containerProspecteursForm.getContainerProperty(tempItemId, "DATE").setValue(time);
            containerProspecteursForm.getContainerProperty(tempItemId, "LASTMODIFY").setValue(time);
            UUID idOne = UUID.randomUUID();
            containerProspecteursForm.getContainerProperty(tempItemId, "UID").setValue(idOne);
                            fInfos.setItemDataSource(containerProspecteursForm.getItem(tempItemId));
                            fInfos.setReadOnly(false);
                            
                            
                fInfos.requestRepaint();
        }
       /*
        * Override to get control over where fields are placed.
        */
        /*@Override
        public void addField(Object propertyId, Field field) {
            //super.addField(propertyId,field);
            //ourLayout.removeComponent(field);
            registerField(propertyId,field);
            attachField(propertyId, field);
            requestRepaint();
        }*/
        @Override
        protected void attachField(Object propertyId, Field field) {
               MyApplication.debug(3, "override AttachField!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
MyApplication.debug(3, propertyId.toString());
               if (propertyId.equals("Pre")) {
                   ourLayout.addComponent(field, 0, 0,2,0);
               }
               else if (propertyId.equals("Nom")) {
                   ourLayout.addComponent(field, 3, 0,5,0);
               }
               else if (propertyId.equals("mail")) {
                   ourLayout.addComponent(field, 0, 1,5,1);
               }
               else if (propertyId.equals("pass")) {
                   ourLayout.addComponent(field, 0, 2,5,2);
               }
                          else if (propertyId.equals("Adr_Nr")) {
                   ourLayout.addComponent(field, 0, 4,2,4);
               }
                          else if (propertyId.equals("Adr_Rue")) {
                   ourLayout.addComponent(field, 3,4,5,4);
               }
                          else if (propertyId.equals("Adr_CP")) {
                   ourLayout.addComponent(field, 0,5,2,5);
               }
                          else if (propertyId.equals("Adr_Ville")) {
                   ourLayout.addComponent(field, 3,5,5,5);
               }
                          else if (propertyId.equals("Tel_Fix")) {
                   ourLayout.addComponent(field, 0,7,5,7);
               }
                          else if (propertyId.equals("Tel_DA")) {
                   ourLayout.addComponent(field, 0,8,5,8);
               }
                          else if (propertyId.equals("Tel_Mob")) {
                   ourLayout.addComponent(field, 0, 9,5,9);
               }
                          else if (propertyId.equals("birthday")) {
                              field.setCaption("Date de Naissance");
                              field.setWidth(COMMON_FIELD_WIDTH);
                   ourLayout.addComponent(field, 0,10,5,10);
               } else if (propertyId.equals("DATE")) {

                   ourLayout.addComponent(field, 0,13,2,13);
               } else if (propertyId.equals("ID")) {
                           
                   ourLayout.addComponent(field, 3,13,5,13);
               }
else if (propertyId.equals("observation")) {
                   ourLayout.addComponent(field, 0,11,5,11);
               }
else if (propertyId.equals("status")) {
                   ourLayout.addComponent(field, 0,12,5,12);
               }
           }

        private class PersonFieldFactory extends DefaultFieldFactory {

            @Override
            public Field createField(Item item, Object propertyId,
                    Component uiContext) {
                Field f;
                f = super.createField(item, propertyId, uiContext);

                if ("Pre".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setRequired(true);
                    tf.setRequiredError("Please enter a First Name");
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.addValidator(new StringLengthValidator(
                            "First Name must be 3-25 characters", 3, 25, false));
                    tf.setCaption("Prenom");
                } else if ("Nom".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setRequired(true);
                    tf.setRequiredError("Please enter a Last Name");
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.addValidator(new StringLengthValidator(
                            "Last Name must be 3-50 characters", 3, 50, false));
                    tf.setCaption("Nom");
                } else if ("mail".equals(propertyId)) {
                    TextField  tf = (TextField ) f;
                    tf.setNullRepresentation("");
                    tf.setRequired(true);
                    tf.setRequiredError("Please enter a mail");
                    tf.setWidth(LONG_FIELD_WIDTH);
                    tf.setCaption("Mail");
                    
                }
                else if ("pass".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setRequired(true);
                    tf.setRequiredError("Please enter a password");
                    tf.setWidth(LONG_FIELD_WIDTH);
                    tf.setCaption("Password");
                    tf.setSecret(true);
                }else if ("Adr_Nr".equals(propertyId)) {
                      TextField tf = (TextField) f;
                      tf.setNullRepresentation("");
                    tf.setRequired(true);
                    tf.setRequiredError("Please enter a number");
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.setCaption("No.");
                }else if ("Adr_Rue".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setRequired(true);
                    tf.setRequiredError("Please enter a rue");
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.setCaption("Rue");
                }else if ("Adr_CP".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setRequired(true);
                    tf.setRequiredError("Please enter a code postale");
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.setCaption("Code Postale");
                }else if ("Adr_Ville".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setRequired(true);
                    tf.setRequiredError("Please enter a city");
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.setCaption("Ville");
                }else if ("Tel_Fix".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setRequired(true);
                    tf.setRequiredError("Please enter a numéro téléphone Fixe");
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.setCaption("Fixe");
                }else if ("Tel_DA".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setRequired(true);
                    tf.setRequiredError("Please enter a fax");
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.setCaption("Téléphone DirectAnnonces");
                }else if ("Tel_Mob".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setRequired(true);
                    tf.setRequiredError("Please enter a portable");
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.setCaption("Portable");
                }
                else if ("observation".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setRows(5);
                    tf.setNullRepresentation("");
                    tf.setWidth(LONG_FIELD_WIDTH);
                    tf.setCaption("Observation");
                }
                
                                else if ("status".equals(propertyId)) {

                                    NativeSelect tf = new NativeSelect("Status");
                   tf.setCaption("Status");
                   for (int i = 0; i < lstatus.length; i++) {
                       tf.addItem(lstatus[i]);
                   }
                   tf.setRequired(true);
                   //tf.setNullSelectionAllowed(false);
                   tf.setRequiredError("Choisissez un Status, si vous plait!");
                   return tf;
                   
                }else if ("ID".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.setCaption("ID");
                    tf.setReadOnly(true);
                }else if ("DATE".equals(propertyId)) {
                    DateField df=(DateField) f;;
                    df.setDateFormat("dd/MM/yyyy HH:mm");
                    df.setWidth(COMMON_FIELD_WIDTH);
                    df.setCaption("Date de Création");
                    df.setReadOnly(true);
                }
                
                return f;
            }
        }
    }


    static final Action ACTION_MARK = new Action("Mark");
    static final Action ACTION_UNMARK = new Action("Unmark");
    static final Action ACTION_LOG = new Action("Save");
    static final Action[] ACTIONS_UNMARKED = new Action[] { ACTION_MARK,
    ACTION_LOG
    };
    static final Action[] ACTIONS_MARKED = new Action[] { ACTION_UNMARK,
    ACTION_LOG
    };


    class TableListUsers extends VerticalLayout {
        Table table = new Table("List des Users");

        HashSet<Object> markedRows = new HashSet<Object>();

        //private BeanItemContainer<Person> personItems;



        public TableListUsers() {


            addComponent(table);

        // size
        table.setWidth("100%");
        table.setHeight("100%");
        // selectable
        table.setSelectable(true);
        table.setImmediate(true);
        // react at once when something is selected
        // connect data source
        //personItems = new BeanItemContainer<Person>(Person.class);
        updateTable();


        //..........................
        //table.setContainerDataSource(ExampleUtil.getISO3166Container());
        // turn on column reordering and collapsing
        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(true);
        table.setColumnHeaders(new String[] { "login", "First Name", "Last Name" });
        // set column headers

        // Actions (a.k.a context menu)
        table.addActionHandler(new Action.Handler() {
            public Action[] getActions(Object target, Object sender) {
                if (markedRows.contains(target)) {
                    return ACTIONS_MARKED;
                }
                 else {
                    return ACTIONS_UNMARKED;
                }
        }




        public void handleAction(Action action, Object sender, Object target) {
            if (ACTION_MARK == action) {
                markedRows.add(target);
                //table.refreshRowCache();
            } else if (ACTION_UNMARK == action) {
                markedRows.remove(target);
                //table.refreshRowCache();
            } else if (ACTION_LOG == action) {
                Item item = table.getItem(target);
                /*
                addComponent(new Label("Saved: "
                        + target
                        + ", "
                        + item.getItemProperty(
                        ExampleUtil.iso3166_PROPERTY_NAME)
                        .getValue()));*/
            }
        }
    });
    // style generator
    table.setCellStyleGenerator(new CellStyleGenerator() {
        public String getStyle(Object itemId, Object propertyId) {
            if (propertyId == null) {
                // no propertyId, styling row
                return (markedRows.contains(itemId) ? "marked" : null);
            } /*else if (ExampleUtil.iso3166_PROPERTY_NAME.equals(propertyId)) {
                return "bold";
            }*/
         else {
            // no style
            return null;
        }
    }
            });
            // toggle cell 'marked' styling when double-clicked
            table.addListener(new ItemClickListener() {
                @Override
                public void itemClick(ItemClickEvent event) {
                    MyApplication.debug(3, "item not null in click");
                    if (event.getButton() == ItemClickEvent.BUTTON_LEFT) {
                        MyApplication.debug(3, "ItemClickEvent.BUTTON_LEFT");
                        MyApplication.debug(3, Integer.toString(containerTable.size()));
                        MyApplication.debug(3, Integer.toString(containerTable.indexOfId(event.getItemId())));
                        nbSelected=containerTable.indexOfId(event.getItemId())+1;
                                           // you can handle left/right/middle -mouseclick
                            //Object itemId = event.getItem();
                            //String mail=event.getItem().getItemProperty("mail").getValue().toString();
                            
                            //Item item= SQLContainerHelper.GetProspecteurByLogin(mail);
                            Item item= containerProspecteursForm.getItem(containerProspecteursForm.getIdByIndex(nbSelected-1));
                            if(item!=null)
                            {
                                
                                MyApplication.debug(3, "item not null~~~~");
                                setTextValue();
                                fInfos.setItemDataSource(item);
                                itemForm=item;
                                fInfos.requestRepaint();
                            }
         
                        /*
                        if (event.isDoubleClick()) {
                        Object itemId = event.getItemId();
                        Object propertyId = event.getPropertyId();
                        HashSet<Object> cells = markedCells.get(itemId);
                        if (cells == null) {
                        cells = new HashSet<Object>();
                        markedCells.put(itemId, cells);
                        }
                        if (cells.contains(propertyId)) {
                        // toggle marking off
                        cells.remove(propertyId);
                        } else {
                        // toggle marking on
                        cells.add(propertyId);
                        }*/
                        // this causes the CellStyleGenerator to return new styles,
                        // but table can't automatically know, we must tell it:
                        //table.refreshRowCache();

                    }
                    /*
                    if (event.isDoubleClick()) {
                        Object itemId = event.getItemId();
                    Object propertyId = event.getPropertyId();
                    HashSet<Object> cells = markedCells.get(itemId);
                    if (cells == null) {
                        cells = new HashSet<Object>();
                        markedCells.put(itemId, cells);
                    }
                    if (cells.contains(propertyId)) {
                        // toggle marking off
                        cells.remove(propertyId);
                    } else {
                        // toggle marking on
                        cells.add(propertyId);
                    }*/
                    // this causes the CellStyleGenerator to return new styles,
                    // but table can't automatically know, we must tell it:
                    //table.refreshRowCache();
                }

            });
        }
                    public void updateTable()
            {
                        try {
                String query="SELECT  mail, Pre, Nom FROM Prospecteurs";
                containerTable=SQLContainerHelper.ContainerFreeQuery(query);

                nbUsers=containerTable.size();
                table.setContainerDataSource(containerTable);
            } catch (SQLException ex) {
                Logger.getLogger(TableListUsers.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    }

}
