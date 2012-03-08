package com.example.vaadin.Prospecter;

import com.example.vaadin.MyApplication;
import com.example.vaadin.SQL.SQLContainerHelper;
import com.example.vaadin.module.AppData;
import com.vaadin.addon.sqlcontainer.SQLContainer;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import org.w3c.dom.*;
import java.io.*;
import javax.validation.constraints.Null;


/**
 *
 * @author weizhe.jiao
 */
@SuppressWarnings("serial")
public class layoutProspection extends VerticalLayout {

    private HorizontalLayout main = new HorizontalLayout();
    private VerticalLayout layoutAgence = new VerticalLayout();
    private HorizontalLayout layoutImage = new HorizontalLayout();
    //private Panel  paneImage=new Panel();
    private FichAgence formAgence;
    private FichAnnonce fannonce;
    // private HorizontalSplitPanel horiz = new HorizontalSplitPanel();
    private Item aFichAgence;
    private int id_Prospecteur = 0;
    private int id_Agence = 0;
    private int id_Annonce = 0;
    private int aux = 0;
    private static final String[] ltrans = new String[]{"V", "L"};
    private static final String[] ltypebien = new String[]{"AP", "MA"};
    private static final String[] lchauffage = new String[]{"Chauffage électrique", "Chauffage collective"};

    public int GetIdAgence() {
        return id_Agence;
    }

    public int GetIdAnnonce() {
        return id_Annonce;
    }

    public int GetAux() {
        return aux;
    }

    public layoutProspection() throws SQLException, MalformedURLException {
        main.setWidth("1055px");

        //main.setSizeFull();
        if (AppData.getUser() == null) {
            //new MyApplication();
            return;
        }
        id_Prospecteur = AppData.getUser().getId();
        id_Agence = SQLContainerHelper.IdAgence(id_Prospecteur);
        if (id_Agence == 0) {
            //getWindow().showNotification("Il n'y a pas d'agence pour prospecter!");
            MyApplication.debug(3, "id_Agence==0");
            return;
        }
        aFichAgence = SQLContainerHelper.ItemFichAgence(id_Agence);

        formAgence = new FichAgence();
        fannonce=new FichAnnonce();
        //main.addComponent(new Label("Prospection"));

        InitLayoutImage();
        layoutAgence.addComponent(formAgence);
        formAgence.addStyleName("formAgence");
        layoutAgence.addComponent(layoutImage);

        TextField tfNotesAgence = new TextField("Notes sur l'agence");
        tfNotesAgence.setRows(2);
        tfNotesAgence.setWidth(LONG_FIELD_WIDTH);
        layoutAgence.addComponent(tfNotesAgence);

        InitPreference();
        layoutAgence.setWidth("400px");
        layoutAgence.addStyleName("layoutAgence");
        fannonce.setWidth("645px");

        main.addComponent(layoutAgence);
        main.addComponent(fannonce);

        //addComponent(new Label("Prospection"));
        addComponent(main);

        //setComponentAlignment(main, Alignment.TOP_CENTER);
    }
    private PopupDateField dateStart;
    private PopupDateField dateEnd;
    private Button btnValidPreference;

    public void InitPreference() {
        setSpacing(true);
        dateStart = new PopupDateField("Please select the starting time:");
        // Set the value of the PopupDateField to current date 
        dateStart.setValue(new java.util.Date());
        // Set the correct resolution   
        dateStart.setResolution(PopupDateField.RESOLUTION_MIN);

        dateEnd = new PopupDateField("Please select the starting time:");
        // Set the value of the PopupDateField to current date 
        dateEnd.setValue(new java.util.Date());
        // Set the correct resolution   
        dateEnd.setResolution(PopupDateField.RESOLUTION_MIN);

        btnValidPreference = new Button("Valider l'heure de péférence");



        btnValidPreference.addListener(
                new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        MyApplication.debug(3, "buttonClick");

                        if (((Date) dateEnd.getValue()).after((Date) dateStart.getValue())) {
                            MyApplication.debug(3, "after");
                            TextField tfP = new TextField();
                            tfP.setWidth(LONG_FIELD_WIDTH);
                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            String ss = dateFormat.format((Date) dateStart.getValue());
                            String sd = dateFormat.format((Date) dateEnd.getValue());
                            tfP.setValue(ss + " - " + sd);
                            layoutAgence.addComponent(tfP);
                            requestRepaint();
                        }
                    }
                });

        layoutAgence.addComponent(dateStart);
        layoutAgence.addComponent(dateEnd);
        layoutAgence.addComponent(btnValidPreference);
    }

    public void InitLayoutImage() throws SQLException {
        layoutImage.setHeight("100%");
        layoutImage.setWidth("400px");
        MyApplication.debug(3, "Init Images");

        Object objImages = aFichAgence.getItemProperty("photos").getValue();
        String strImages = (String) objImages;
        String[] arraysImages = strImages.split("\\|");
        //ArrayList<String> urlImages=new ArrayList<String>();
        for (int i = 0; i < arraysImages.length; i++) {
            String urlImage = "http://dev.directmandat.com/ul/" + arraysImages[i] + ".jpeg";
            //urlImages.add(urlImage);
            MyApplication.debug(3, urlImage);
            try {
                // String s=Integer.toString(i);
                Panel paneImage = new Panel();
                URL url = new URL(urlImage);
                Embedded image = new Embedded("", new ExternalResource(url));
                image.setSizeFull();
                paneImage.addComponent(image);

                layoutImage.addComponent(paneImage);
                break;
                //BufferedImage image = ImageIO.read(new File(urlImage));
            } catch (Exception e) {
            }

            //Image image=new Image(urlImage);

        }

        //layoutImage.addComponent(paneImage);
        //layoutImage.setExpandRatio(paneImage, 1.0f);

    }
    private static final String COMMON_FIELD_WIDTH = "12em";
    private static final String LONG_FIELD_WIDTH = "26em";
    private static final String High_FIELD_Height = "50px";

    class FichAgence extends Form {

        private GridLayout gridFichAgence;

        //private static final String FormWidth = "28em";
        @SuppressWarnings("LeakingThisInConstructor")
        public FichAgence() throws SQLException {
            setCaption("FICHE AGENCE");
            //setWidth(FormWidth);
            gridFichAgence = new GridLayout(6, 13);

            gridFichAgence.setMargin(true, false, false, true);
            gridFichAgence.setSpacing(true);



            setLayout(gridFichAgence);
            // Set up buffering
            setWriteThrough(false);
            // we want explicit 'apply'
            setInvalidCommitted(false);
            // no invalid values in datamodel
            // FieldFactory for customizing the fields and adding validators
            setFormFieldFactory(new PersonFieldFactory());

            setItemDataSource(aFichAgence);
            // Determines which properties are shown, and in which order:
            setVisibleItemProperties(Arrays.asList(new String[]{
                        "denomination", "reseau", "adresse", "cp", "co", "fix", "fax",
                        "mob", "mel", "responsable", "noNegociateurs", "noAcheteurs",
                        "secteur"
                    }));


            MyApplication.debug(3, "FormInfosUsers!!!!!!!!!!!!!!!!!!!!!");

        }
        /*
         * @Override public void addField(Object propertyId, Field field) {
         * super.addField(propertyId,field);
         * gridFichAgence.removeComponent(field); attachField(propertyId,
         * field); requestRepaint(); }
         */

        @Override
        protected void attachField(Object propertyId, Field field) {

            MyApplication.debug(3, propertyId.toString());

            if (propertyId.equals("denomination")) {
                gridFichAgence.addComponent(field, 0, 0, 5, 0);
            } else if (propertyId.equals("reseau")) {
                gridFichAgence.addComponent(field, 0, 1, 5, 1);
            } else if (propertyId.equals("adresse")) {
                gridFichAgence.addComponent(field, 0, 2, 5, 2);
            } else if (propertyId.equals("cp")) {
                gridFichAgence.addComponent(field, 0, 3, 2, 3);
            } else if (propertyId.equals("co")) {
                gridFichAgence.addComponent(field, 0, 4, 5, 4);
            } else if (propertyId.equals("fix")) {
                gridFichAgence.addComponent(field, 0, 5, 2, 5);
            } else if (propertyId.equals("fax")) {
                gridFichAgence.addComponent(field, 3, 5, 5, 5);
            } else if (propertyId.equals("mob")) {
                gridFichAgence.addComponent(field, 0, 6, 2, 6);
            } else if (propertyId.equals("mel")) {
                gridFichAgence.addComponent(field, 0, 7, 5, 7);
            } else if (propertyId.equals("responsable")) {
                gridFichAgence.addComponent(field, 0, 8, 5, 8);
            } else if (propertyId.equals("noNegociateurs")) {
                gridFichAgence.addComponent(field, 0, 9, 2, 9);
            } else if (propertyId.equals("noAcheteurs")) {

                gridFichAgence.addComponent(field, 3, 9, 5, 9);
            } else if (propertyId.equals("secteur")) {
                gridFichAgence.addComponent(field, 0, 10, 2, 10);
            }
            /*
             * else if (propertyId.equals("photos")) { //field=new Image();
             * //field=new HorizontalLayout();
             *
             * gridFichAgence.addComponent(field, 0,11,5,11); }
             */
        }

        private class PersonFieldFactory extends DefaultFieldFactory {

            @Override
            public Field createField(Item item, Object propertyId,
                    Component uiContext) {
                Field f;
                f = super.createField(item, propertyId, uiContext);

                if ("denomination".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.addStyleName("DeniminationTextField");
                    tf.setNullRepresentation("");
                    tf.setReadOnly(true);
                    tf.setWidth(LONG_FIELD_WIDTH);
                    //tf.setHeight(High_FIELD_Height);
                    tf.setCaption("Denomination:");
                } else if ("reseau".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setReadOnly(true);
                    tf.setWidth(LONG_FIELD_WIDTH);
                    tf.setCaption("Réseau:");
                } else if ("adresse".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setReadOnly(true);
                    tf.setWidth(LONG_FIELD_WIDTH);
                    tf.setCaption("Adresse:");
                } else if ("cp".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setReadOnly(true);
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.setCaption("Code postal:");
                } else if ("co".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setReadOnly(true);
                    tf.setWidth(LONG_FIELD_WIDTH);
                    tf.setCaption("Ville:");
                } else if ("fix".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setReadOnly(true);
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.setCaption("Téléphone Fixe:");
                } else if ("fax".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setReadOnly(true);
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.setCaption("Fax:");
                } else if ("mob".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setReadOnly(true);
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.setCaption("Mobile:");
                } else if ("mel".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setReadOnly(true);
                    tf.setWidth(LONG_FIELD_WIDTH);
                    tf.setCaption("Email:");
                } else if ("responsable".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setReadOnly(true);
                    tf.setWidth(LONG_FIELD_WIDTH);
                    tf.setCaption("Responsable:");
                } else if ("noNegociateurs".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setReadOnly(true);
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.setCaption("Nombre de Negociateurs:");
                } else if ("noAcheteurs".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setReadOnly(true);
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.setCaption("Nombre d'Acheteurs:");
                } else if ("secteur".equals(propertyId)) {
                    TextField tf = (TextField) f;
                    tf.setNullRepresentation("");
                    tf.setReadOnly(true);
                    tf.setWidth(COMMON_FIELD_WIDTH);
                    tf.setCaption("Secteur:");
                }
                /*
                 * else if ("photos".equals(propertyId)) { TextField tf =
                 * (TextField) f;
                 *
                 * tf.setWidth(LONG_FIELD_WIDTH); tf.setCaption("Photos:"); }
                 */

                return f;
            }
        }
    }
    
    
    class FichAnnonce extends VerticalLayout
    {

        private VerticalLayout InfoAnnonce;
        //private Panel InfoAnnonce;
        private HorizontalLayout layoutInfos;
        private VerticalLayout verInfos;

        private FormActions actionsForm;

        private FormBien InfosBien;
        private FormPro InfosProprietaire;
  
        private VerticalLayout Notes;
        private TabSheet NotesRead;
        private TabSheet NotesInput;
        private Button btnValidNotes;
        
        private Button btnValidInfos;
        
        public FichAnnonce() throws SQLException, MalformedURLException
        {
            super.setCaption("FICHE ANNONCE");
            MyApplication.debug(3, "FichAnnonce Init.");
            id_Annonce=SQLContainerHelper.GetIdAnnonce(id_Prospecteur, id_Agence);
            aux=SQLContainerHelper.GetAuxbyIdAnnonce(id_Annonce);
            if(id_Annonce==0)
            {
                 //getWindow().showNotification("Il n'y a pas d'annonce pour prospecter!");
                return;
            }
            containerItemRecent=SQLContainerHelper.ContainerFichAnnonceRecent(id_Agence, 
id_Annonce,aux);
            itemRecent=containerItemRecent.getItem(containerItemRecent.firstItemId());
            if(itemRecent==null)
            {
                return;
            }
            
            
            InfoAnnonce=new VerticalLayout();
            //InfoAnnonce=new Panel();
            InitInfoAnnonce();

            actionsForm=new FormActions();

            actionsForm.addStyleName("formAnnonce");
            //InitActionForm();

            layoutInfos=new HorizontalLayout();

            verInfos=new VerticalLayout();
            InfosBien=new FormBien();
            InfosBien.addStyleName("formAnnonce");
            InfosProprietaire=new FormPro();
            InfosProprietaire.addStyleName("formAnnonce");

            //Notes=new VerticalLayout();
            //NotesRead=new TabSheet();
            //InitNotesRead();
            //NotesInput=new TabSheet();
            //btnValidNotes=new Button("Valider Notes");
            
            //InitNotesInput();

            verInfos.addComponent(InfosBien);
            verInfos.addComponent(InfosProprietaire);
            //Notes.addComponent(NotesRead);
            //Notes.addComponent(NotesInput);
            //Notes.addComponent(btnValidNotes);

            layoutInfos.addComponent(verInfos);
  //          layoutInfos.addComponent(Notes);

            
            btnValidInfos=new Button("Valider INFOS BIEN ET PROPETAIRE!");
            btnValidInfos.addListener(
                    new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        MyApplication.debug(3, "buttonClick");
                        openSubWindowValid();


                    }
                });
            
            addComponent(InfoAnnonce);
            addComponent(actionsForm);
            addComponent(layoutInfos);
            addComponent(btnValidInfos);
        }


        private Window subwindow;
        private Boolean bvalid;

        public void openSubWindowValid()
        {
            subWindowValid();
            if (subwindow.getParent() != null) {
                // window is already showing
                getWindow().showNotification("Window is already open");
            }
            else {
                getWindow().addWindow(subwindow);
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
            Label message = new Label("Valider le mise à jour!");
            subwindow.addComponent(message);

            Button close = new Button("Cancel", new Button.ClickListener() {
                // inline click-listener
                public void buttonClick(Button.ClickEvent event) {
                    // close the window by removing it from the parent window
                    bvalid=false;
                    ((Window)(subwindow.getParent())).removeWindow(subwindow);
                }
            });


            Button valid = new Button("Valider", new Button.ClickListener() {

                    public void buttonClick(Button.ClickEvent event) {
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


        public void doValid()
        {
            if(!bvalid)
            {return;}
            try {

                InfosBien.commit();
                InfosProprietaire.commit();
                containerItemRecent.commit();
            } catch (UnsupportedOperationException ex) {
                Logger.getLogger(layoutProspection.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(layoutProspection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        private GridLayout InfoTitre;
        public Item itemRecent;
        private SQLContainer containerItemRecent;
        private HorizontalLayout horizonInfo=new HorizontalLayout();
        private Panel panelInfo=new Panel();

        public void InitInfoAnnonce() throws MalformedURLException, SQLException
        {
            InitInfoTitre();
            InfoAnnonce.addComponent(InfoTitre);
            InitHorizonInfo();

            InfoAnnonce.addComponent(horizonInfo);
        }

        public void InitInfoTitre() 
        {
            InfoTitre=new GridLayout(4,1);
            GridAddComponent("ann_cp",0,0,0,0);
            GridAddComponent("ann_ville",1,0,1,0);
            GridAddComponent("ann_titre",2,0,2,0);
            GridAddComponent("ann_datepar",3,0,3,0);
        }

        public void GridAddComponent(Object pid,int col1,int row1,int col2,int row2)
        {
            TextField tfcp=new TextField();
            MyApplication.debug(3, pid.toString());

            Object tfvalue=itemRecent.getItemProperty(pid).getValue();
            if((String)pid=="ann_datepar")
            {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                tfvalue=dateFormat.format((Date)tfvalue);
               // tfvalue=((Date)tfvalue);
            }
            tfcp.setValue(tfvalue);
            tfcp.setReadOnly(true);
            //MyApplication.debug(3, tfcp.getValue().toString());
            InfoTitre.addComponent(tfcp,col1,row1,col2,row2);
        }

        public void InitHorizonInfo() throws MalformedURLException
        {
            InitPanelInfo();
            
            horizonInfo.addComponent(panelInfo);


            
        }

        public void InitPanelInfo() throws MalformedURLException
        {
            HorizontalLayout versssss=new HorizontalLayout();

            Object date=itemRecent.getItemProperty("ann_datepar").getValue();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            date=dateFormat.format((Date)date);

            Object prix=itemRecent.getItemProperty("ann_prix").getValue();

            Object surface=itemRecent.getItemProperty("ann_surface").getValue();

            int prixsurface;

            prixsurface=(new Integer(prix.toString()))/(new Integer(surface.toString()));

            //String strPrixSurface=Integer.toString(prixsurface);

            String titre=date.toString()+" - "
                        +prix.toString()+"\u20AC  - "
                        +surface.toString()+"\u33A1 - "
                        +Integer.toString(prixsurface)
                        +"\u20AC"+"/"+"\u33A1";

            panelInfo.setCaption(titre);

            
            VerticalLayout verPanel=new VerticalLayout();
            
            TextField tfAnnText=new TextField();
            
            Object ann_text=itemRecent.getItemProperty("ann_texte").getValue();
            String ann_text_100=ann_text.toString().substring(0, 99)+"...";

            tfAnnText.setRows(2);
            tfAnnText.setValue(ann_text_100);
            tfAnnText.setReadOnly(true);
            tfAnnText.setWidth("275px");
            tfAnnText.setHeight("80px");

            verPanel.addComponent(tfAnnText);

            Object ann_code=itemRecent.getItemProperty("ann_code").getValue();
            Object nom_site=itemRecent.getItemProperty("nom_site").getValue();
            //Label labelText="";
            String CAPTION=nom_site.toString();
            URL urlSite=new URL(ann_code.toString());
            Link l=new Link(CAPTION,
                    new ExternalResource(urlSite));

            verPanel.addComponent(new Label("Cliquez ici pour voir l'annonce parue sur: "));
            verPanel.addComponent(l);

            TextField tfTel=new TextField();

            Object p_telephone=itemRecent.getItemProperty("p_telephone").getValue();
            tfTel.setValue(p_telephone.toString());
            tfTel.setCaption("Contact:");
            tfTel.setReadOnly(true);

            verPanel.addComponent(tfTel);

            versssss.addComponent(verPanel);

            Object objImages=aFichAgence.getItemProperty("photos").getValue();
            String strImages=(String)objImages;
            String[] arraysImages=  strImages.split("\\|");

            String urlImage="http://dev.directmandat.com/ul/"+arraysImages
[arraysImages.length-1]+".jpeg";

            //Panel paneImage=new Panel();
            URL url = new URL(urlImage);
            Embedded image = new Embedded("", new ExternalResource(url));
            image.setSizeFull();
            //paneImage.addComponent(image);

            versssss.addComponent(image);


            panelInfo.addComponent(versssss);
            panelInfo.setWidth("425px");
        }



        public void InitNotesInput()
        {
            // Tab 1 content
            //VerticalLayout l1=new VerticalLayout();
            //l1.addComponent(new Label("Hello"));
            final TextField l1 = new TextField();
            l1.setSizeFull();
            l1.setCaption("Concernant le bien");
            // Tab 2 content
            final TextField l2 = new TextField();
            l2.setSizeFull();
            //l2.setRows(5);
            l2.setCaption("Sur le proprio");
            NotesInput = new TabSheet();

            NotesInput.setHeight("225px");
            NotesInput.setWidth("300px");
            NotesInput.addTab(l1);
            NotesInput.addTab(l2);
            //NotesInput.addListener(this);
            //Notes.addComponent(NotesInput);
            
            btnValidNotes.addListener(
                        new Button.ClickListener() {
                        public void buttonClick(Button.ClickEvent event) {
                            MyApplication.debug(3, "buttonClick");
                            if(NotesInput.getSelectedTab()==l1)
                            {
                                MyApplication.debug(3, "l1 selected");
                                if(l1.getValue()!=null && l1.getValue().toString().length()>0)
                                {
                                                                        Date d = new Date();
                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    String tfvalue=dateFormat.format(d);
                                    tfBien.setReadOnly(false);
                                    tfBien.setValue(tfBien.getValue().toString()+"\n\n"
                                            +tfvalue
                                            +"\n"
                                            +l1.getValue().toString());
                                    tfBien.setReadOnly(true);

                                    tfTous.setReadOnly(false);
                                    
                                    tfTous.setValue(tfTous.getValue().toString()+"\n\n"
                                            +tfvalue
                                            +" Sur Bien\n"
                                            +l1.getValue().toString());
                                    tfTous.setReadOnly(true);
                                }
                            }else if(NotesInput.getSelectedTab()==l2)
                            {
                                MyApplication.debug(3, "l2 selected");
                                if(l2.getValue()!=null && l2.getValue().toString().length()>0)
                                {
                                                                        Date d = new Date();
                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    String tfvalue=dateFormat.format(d);
                                    tfProprio.setReadOnly(false);
                                    tfProprio.setValue(tfProprio.getValue().toString()+"\n\n"
                                            +tfvalue
                                            +"\n"
                                            +l2.getValue().toString());
                                    tfProprio.setReadOnly(true);

                                    tfTous.setReadOnly(false);

                                    tfTous.setValue(tfTous.getValue().toString()+"\n\n"
                                            +tfvalue
                                            +" Sur Proprio\n"
                                            +l2.getValue().toString());
                                    tfTous.setReadOnly(true);
                                }
                            }
                        }



                    });

        }

        private final TextField tfTous=new TextField("");
        private final TextField tfBien=new TextField("");
        private final TextField tfProprio=new TextField("");
        private final TextField tfhisto=new TextField("");

        public void InitNotesRead()
        {
            // Tab 1 content
            VerticalLayout l1 = new VerticalLayout();
            l1.setCaption("Tous");
            l1.setMargin(true);
            tfTous.setRows(15);
            tfTous.setWidth("290px");
            tfTous.setHeight("700px");
            tfTous.setReadOnly(true);
            l1.addComponent(tfTous);

            VerticalLayout l2 = new VerticalLayout();
            l2.setCaption("Bien");
            l2.setMargin(true);
                        tfBien.setRows(15);
                                    tfBien.setWidth("290px");
            tfBien.setHeight("700px");
            tfBien.setReadOnly(true);
            l2.addComponent(tfBien);

            VerticalLayout l3 = new VerticalLayout();
            l3.setCaption("Proprio");
            l3.setMargin(true);
                        tfProprio.setRows(15);
                                    tfProprio.setWidth("290px");
            tfProprio.setHeight("700px");
            tfProprio.setReadOnly(true);
            l3.addComponent(tfProprio);

            VerticalLayout l4 = new VerticalLayout();
            l4.setCaption("Histo");
            l4.setMargin(true);
                        tfhisto.setRows(15);
                                    tfhisto.setWidth("290px");
            tfhisto.setHeight("700px");
            tfhisto.setReadOnly(true);
            l4.addComponent(tfhisto);

            NotesRead.addTab(l1);
            NotesRead.addTab(l2);
            NotesRead.addTab(l3);
            NotesRead.addTab(l4);

            

            //String h=Float.toString(InfosBien.getHeight())+"px";
            //MyApplication.debug(3, "Height:"+h);
            NotesRead.setHeight("790px");
            NotesRead.setWidth("300px");
        }


        class FormActions extends VerticalLayout
        {
            private GridLayout gridFormActions;

            private final String[] actions = new String[] {
            "RDV", "AR","NA","RF","CONCLU(LOUE/VENDU)"};

            public FormActions() throws SQLException
            {
                
                BeanItemContainer<Planet> container =
                    new BeanItemContainer<Planet>(Planet.class);

                // Put some example data in it
                container.addItem(new Planet("isDigicode"));
                container.addItem(new Planet("isGardien"));


                BeanItemContainer<String> containerS =
                    new BeanItemContainer<String>(String.class);

                for(int i=0;i<actions.length;i++)
                {
                    containerS.addItem(actions[i]);
                }


                // Create a selection component bound to the container
                OptionGroup og = new OptionGroup("Actions", containerS);
                og.addStyleName("oghorizon");
                // Set the caption mode to read the caption directly
                // from the 'name' property of the bean
                og.setItemCaptionMode(
                        OptionGroup.ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID);
                //og.setItemCaptionPropertyId("propertyName");

                addComponent(og);


                //Date dt=new Date();
                DateField df=new DateField("Date et Heure:");
                df.setDateFormat("dd/MM/yyyy HH:mm");
                df.setResolution(DateField.RESOLUTION_MIN);
                addComponent(df);

                TextField tfNotesRDVRA=new TextField("Notes sur le RDV/RA");
                tfNotesRDVRA.setRows(2);
                tfNotesRDVRA.setWidth(LONG_FIELD_WIDTH);
                addComponent(tfNotesRDVRA);

                BeanItemContainer<String> containerCivil =
                    new BeanItemContainer<String>(String.class);

                // Put some example data in it
                containerCivil.addItem("Madame");
                containerCivil.addItem("Mademoiselle");
                containerCivil.addItem("Monsieur");

                // Create a selection component bound to the container
                OptionGroup ogCivil = new OptionGroup("Civilité", containerCivil);
                ogCivil.addStyleName("oghorizon");
                // Set the caption mode to read the caption directly
                // from the 'name' property of the bean
                ogCivil.setItemCaptionMode(
                        OptionGroup.ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID);
                //og.setItemCaptionPropertyId("propertyName");

                addComponent(ogCivil);

                TextField tfName=new TextField("Nom");
                addComponent(tfName);
            }

            public class Planet implements Serializable {
                Boolean name;
                String propertyName;

                public Planet(String property) {
                    propertyName=property;
                    if(itemRecent.getItemProperty(property).getValue()==null)
                    {
                        this.name = false;
                    }
                    else
                    {
                        this.name = (Boolean)itemRecent.getItemProperty(property).getValue();
                    }
                }

                public void setName(Boolean value) {
                    this.name = value;
                    itemRecent.getItemProperty(propertyName).setValue(value);
                }

                public Boolean getName() {
                    return name;
                }

                public String getPropertyName() {
                return propertyName;
                }
            }
        }

        
        
        class FormBien extends Form
        {
            private GridLayout gridBien;
            //private Button btnValidBien;

            public FormBien()
            {
                super.setCaption("INFOS BIEN");
                gridBien=new GridLayout(2,18);

                //gridBien.setMargin(true, false, false, true);
                //gridBien.setSpacing(true);

                

               // gridBien.addComponent(btnValidBien,0,18,1,18);
                
                setLayout(gridBien);
                            // Set up buffering
                setWriteThrough(false);
                // we want explicit 'apply'
                setInvalidCommitted(false);
                // no invalid values in datamodel
                // FieldFactory for customizing the fields and adding validators
                setFormFieldFactory(new PersonFieldFactory());

                setItemDataSource(itemRecent);
                
                // Determines which properties are shown, and in which order:
                setVisibleItemProperties(Arrays.asList(new String[] {
                    "trans","ann_type","ann_surface","ann_prix","ann_nbpieces",
                    "coAdresse"
                        ,"ann_cp","ann_ville","isInterphone","coInterphone",
                    "isDigicode","coDigicode","isGardien","isAscenseur","coEtage",
                    "coChauffage","isCave","isGarage","isParking","isJardin",
                    "isTerrasse","isBalcon"
                }));


            }

        @Override
        protected void attachField(Object propertyId, Field field)
        {

            MyApplication.debug(3, propertyId.toString());

            if (propertyId.equals("trans")) {
               gridBien.addComponent(field, 0, 0,1,0);
           }else if (propertyId.equals("ann_type")) {
               gridBien.addComponent(field, 0, 1,1,1);
           }else if (propertyId.equals("ann_surface")) {
               gridBien.addComponent(field, 0, 2);
           }else if (propertyId.equals("ann_prix")) {
               gridBien.addComponent(field, 0, 3);
           }else if (propertyId.equals("ann_nbpieces")) {
               gridBien.addComponent(field, 0, 4);
           }else if (propertyId.equals("coAdresse")) {
               gridBien.addComponent(field, 0, 5,1,5);
           }else if (propertyId.equals("ann_cp")) {
               gridBien.addComponent(field, 0, 6);
           }else if (propertyId.equals("ann_ville")) {
               gridBien.addComponent(field, 1, 6);
           }else if (propertyId.equals("isInterphone")) {
               gridBien.addComponent(field, 0, 7);
           }else if (propertyId.equals("coInterphone")) {
               gridBien.addComponent(field, 1, 7);
           }else if (propertyId.equals("isDigicode")) {
               gridBien.addComponent(field, 0, 8);
           }else if (propertyId.equals("coDigicode")) {
               gridBien.addComponent(field, 1, 8);
           }else if (propertyId.equals("isGardien")) {
               gridBien.addComponent(field, 0, 9);
           }else if (propertyId.equals("isAscenseur")) {
               gridBien.addComponent(field, 0, 10);
           }else if (propertyId.equals("coEtage")) {
               gridBien.addComponent(field, 1, 10);
           }else if (propertyId.equals("coChauffage")) {
               gridBien.addComponent(field, 0, 11,1,11);
           }else if (propertyId.equals("isCave")) {
               gridBien.addComponent(field, 0, 12);
           }else if (propertyId.equals("isGarage")) {
               gridBien.addComponent(field, 0, 13);
           }else if (propertyId.equals("isParking")) {
               gridBien.addComponent(field, 0, 14);
           }else if (propertyId.equals("isJardin")) {
               gridBien.addComponent(field, 0, 15);
           }else if (propertyId.equals("isTerrasse")) {
               gridBien.addComponent(field, 0, 16);
           }else if (propertyId.equals("isBalcon")) {
               gridBien.addComponent(field, 0, 17);
           }


        }


        private class PersonFieldFactory extends DefaultFieldFactory
        {

            @Override
            public Field createField(Item item, Object propertyId,
                    Component uiContext) {
                    Field f;
                    f = super.createField(item, propertyId, uiContext);

                    if ("trans".equals(propertyId))
                    {
                        OptionGroup ogTrans=new OptionGroup();
                        ogTrans.setCaption("Type transaction");
                       for (int i = 0; i < ltrans.length; i++) {
                           ogTrans.addItem(ltrans[i]);
                       }

                       ogTrans.setItemCaption("V", "Vente");
                       ogTrans.setItemCaption("L", "Location");

                       ogTrans.setRequired(true);
                       ogTrans.addStyleName("oghorizon");
                       //tf.setNullSelectionAllowed(false);
                       ogTrans.setRequiredError("Choisissez une Transcation, si vous plait!");
                       return ogTrans;
                    }else if ("ann_type".equals(propertyId))
                    {
                        NativeSelect ogTrans=new NativeSelect("Type bien");
                       for (int i = 0; i < ltypebien.length; i++) {
                           ogTrans.addItem(ltypebien[i]);
                       }

                       ogTrans.setItemCaption("AP", "Appartement");
                       ogTrans.setItemCaption("MA", "Maison");

                       ogTrans.setWidth(LONG_FIELD_WIDTH);
                       ogTrans.setRequired(true);
                       //ogTrans.addStyleName("oghorizon");
                       //tf.setNullSelectionAllowed(false);
                       ogTrans.setRequiredError("Choisissez un type de bien, si vous plait!");
                       return ogTrans;
                    }else if ("ann_surface".equals(propertyId)) {
                        TextField tf = (TextField) f;
                        tf.setNullRepresentation("");
                        tf.setWidth(COMMON_FIELD_WIDTH);
                        tf.setCaption("Surface");
                    }else if ("ann_prix".equals(propertyId)) {
                        TextField tf = (TextField) f;
                        tf.setNullRepresentation("");
                        tf.setWidth(COMMON_FIELD_WIDTH);
                        tf.setCaption("Prix bien");
                    }else if ("ann_nbpieces".equals(propertyId)) {
                        TextField tf = (TextField) f;
                        tf.setNullRepresentation("");
                        tf.setWidth(COMMON_FIELD_WIDTH);
                        tf.setCaption("Nb.pièces");
                    }else if ("coAdresse".equals(propertyId)) {
                        TextField tf = (TextField) f;
                        tf.setRows(2);
                        tf.setNullRepresentation("");
                        tf.setWidth(LONG_FIELD_WIDTH);
                        tf.setCaption("Addresse");
                    }else if ("ann_cp".equals(propertyId)) {
                        TextField tf = (TextField) f;
                        tf.setNullRepresentation("");
                        tf.setWidth(COMMON_FIELD_WIDTH);
                        tf.setCaption("Dépt/CP");
                    }else if ("ann_ville".equals(propertyId)) {
                        TextField tf = (TextField) f;
                        tf.setNullRepresentation("");
                        tf.setWidth(COMMON_FIELD_WIDTH);
                        tf.setCaption("Ville");
                    }else if ("isInterphone".equals(propertyId)) {
                       OptionGroup ogTrans=new OptionGroup("Interphone");
                        ogTrans.addItem(true);
                        ogTrans.addItem(false);

                       ogTrans.setItemCaption(true, "Oui");
                       ogTrans.setItemCaption(false, "Non");

                       ogTrans.addStyleName("oghorizon");
                       return ogTrans;
                    }else if ("coInterphone".equals(propertyId)) {
                        TextField tf = (TextField) f;
                        tf.setNullRepresentation("");
                        tf.setWidth(COMMON_FIELD_WIDTH);
                        tf.setCaption("Numéro Interphone");
                    }else if ("isDigicode".equals(propertyId)) {
                       OptionGroup ogTrans=new OptionGroup("Digicode");
                        ogTrans.addItem(true);
                        ogTrans.addItem(false);

                       ogTrans.setItemCaption(true, "Oui");
                       ogTrans.setItemCaption(false, "Non");

                       ogTrans.addStyleName("oghorizon");
                       return ogTrans;
                    }else if ("coDigicode".equals(propertyId)) {
                        TextField tf = (TextField) f;
                        tf.setNullRepresentation("");
                        tf.setWidth(COMMON_FIELD_WIDTH);
                        tf.setCaption("Numéro Digicode  ");
                    }else if ("isGardien".equals(propertyId)) {
                       OptionGroup ogTrans=new OptionGroup("Gardien");
                        ogTrans.addItem(true);
                        ogTrans.addItem(false);

                       ogTrans.setItemCaption(true, "Oui");
                       ogTrans.setItemCaption(false, "Non");

                       ogTrans.addStyleName("oghorizon");
                       return ogTrans;
                    }else if ("isAscenseur".equals(propertyId)) {
                       OptionGroup ogTrans=new OptionGroup("Ascenseur");
                        ogTrans.addItem(true);
                        ogTrans.addItem(false);

                       ogTrans.setItemCaption(true, "Oui");
                       ogTrans.setItemCaption(false, "Non");

                       ogTrans.addStyleName("oghorizon");
                       return ogTrans;
                    }else if ("coEtage".equals(propertyId)) {
                        TextField tf = (TextField) f;
                        tf.setNullRepresentation("");
                        tf.setWidth(COMMON_FIELD_WIDTH);
                        tf.setCaption("Etage");
                    }else if ("coChauffage".equals(propertyId)) {
                        NativeSelect ogTrans=new NativeSelect("Type bien");

                       for (int i = 0; i < lchauffage.length; i++) {
                           ogTrans.addItem(lchauffage[i]);
                       }

                       ogTrans.setWidth(LONG_FIELD_WIDTH);
                       return ogTrans;
                    }else if ("isCave".equals(propertyId)) {
                       OptionGroup ogTrans=new OptionGroup("Cave");
                        ogTrans.addItem(true);
                        ogTrans.addItem(false);

                       ogTrans.setItemCaption(true, "Oui");
                       ogTrans.setItemCaption(false, "Non");

                       ogTrans.addStyleName("oghorizon");
                       return ogTrans;
                    }else if ("isGarage".equals(propertyId)) {
                       OptionGroup ogTrans=new OptionGroup("Garage");
                        ogTrans.addItem(true);
                        ogTrans.addItem(false);

                       ogTrans.setItemCaption(true, "Oui");
                       ogTrans.setItemCaption(false, "Non");

                       ogTrans.addStyleName("oghorizon");
                       return ogTrans;
                    }else if ("isParking".equals(propertyId)) {
                       OptionGroup ogTrans=new OptionGroup("Parking");
                        ogTrans.addItem(true);
                        ogTrans.addItem(false);

                       ogTrans.setItemCaption(true, "Oui");
                       ogTrans.setItemCaption(false, "Non");

                       ogTrans.addStyleName("oghorizon");
                       return ogTrans;
                    }else if ("isJardin".equals(propertyId)) {
                       OptionGroup ogTrans=new OptionGroup("Jardin");
                        ogTrans.addItem(true);
                        ogTrans.addItem(false);

                       ogTrans.setItemCaption(true, "Oui");
                       ogTrans.setItemCaption(false, "Non");

                       ogTrans.addStyleName("oghorizon");
                       return ogTrans;
                    }else if ("isTerrasse".equals(propertyId)) {
                       OptionGroup ogTrans=new OptionGroup("Terrasse");
                        ogTrans.addItem(true);
                        ogTrans.addItem(false);

                       ogTrans.setItemCaption(true, "Oui");
                       ogTrans.setItemCaption(false, "Non");

                       ogTrans.addStyleName("oghorizon");
                       return ogTrans;
                    }else if ("isBalcon".equals(propertyId)) {
                       OptionGroup ogTrans=new OptionGroup("Balcon");
                        ogTrans.addItem(true);
                        ogTrans.addItem(false);

                       ogTrans.setItemCaption(true, "Oui");
                       ogTrans.setItemCaption(false, "Non");

                       ogTrans.addStyleName("oghorizon");
                       return ogTrans;
                    }

                    return f;
                }
            }
        }

          class FormPro extends Form
        {
            private GridLayout gridPro;
            Button valideTel;

            public FormPro()
            {
                super.setCaption("INFOS PROPRIETAIRE");
                gridPro=new GridLayout(4,10);

                //gridPro.setMargin(true, false, false, true);
                //gridPro.setSpacing(true);

                setTels();

                setLayout(gridPro);
                            // Set up buffering
                setWriteThrough(false);
                // we want explicit 'apply'
                setInvalidCommitted(false);
                // no invalid values in datamodel
                // FieldFactory for customizing the fields and adding validators
                setFormFieldFactory(new PersonFieldFactory());

                setItemDataSource(itemRecent);
                // Determines which properties are shown, and in which order:
                setVisibleItemProperties(Arrays.asList(new String[] {
                    "adr","cp","co","mail"
                }));


            }

        @Override
        protected void attachField(Object propertyId, Field field)
        {

            MyApplication.debug(3, propertyId.toString());

            if (propertyId.equals("adr")) {
               gridPro.addComponent(field, 0, 0,3,0);
           }else if (propertyId.equals("cp")) {
               gridPro.addComponent(field, 0, 1,1,1);
           }else if (propertyId.equals("co")) {
               gridPro.addComponent(field, 2, 1,3,1);
           }else if (propertyId.equals("mail")) {
               gridPro.addComponent(field, 0, 4,3,4);
           }


        }


        private class PersonFieldFactory extends DefaultFieldFactory
        {

            @Override
            public Field createField(Item item, Object propertyId,
                    Component uiContext) {
                    Field f;
                    f = super.createField(item, propertyId, uiContext);

                    if ("adr".equals(propertyId))
                    {
                        TextField tf = (TextField) f;
                        tf.setRows(2);
                        tf.setNullRepresentation("");
                        tf.setWidth(LONG_FIELD_WIDTH);
                        tf.setCaption("Addresse");
                    }else if ("cp".equals(propertyId))
                    {
                        TextField tf = (TextField) f;
                        tf.setNullRepresentation("");
                        tf.setWidth(COMMON_FIELD_WIDTH);
                        tf.setCaption("Dépt/CP");
                    }else if ("co".equals(propertyId))
                    {
                        TextField tf = (TextField) f;
                        tf.setNullRepresentation("");
                        tf.setWidth(COMMON_FIELD_WIDTH);
                        tf.setCaption("Ville");
                    }else if ("mail".equals(propertyId))
                    {
                        TextField tf = (TextField) f;
                        tf.setNullRepresentation("");
                        tf.setWidth(LONG_FIELD_WIDTH);
                        tf.setCaption("Email");
                    }
                    return f;
                }
              }
            public  String getCharacterDataFromElement(Element e) {
                Node child =  e.getFirstChild();
                if (child instanceof CharacterData) {
                   CharacterData cd = (CharacterData) child;
                   return cd.getData();
                }
                return "";
              }

            public void setTels()
            {
                final NativeSelect ogTrans=new NativeSelect("Téléphone(s)");
                String strTels=itemRecent.getItemProperty("tel").getValue().toString();
                String XMLFormated = "<Tels>"+strTels+"</Tels>";
                try {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder;
                    builder = factory.newDocumentBuilder();
                    InputSource is = new InputSource();
                    is.setCharacterStream(new StringReader(XMLFormated));
                    Document XMLDoc= (Document) builder.parse(is);
                    NodeList nodes =  XMLDoc.getElementsByTagName("obj");

                    for (int i = 0; i < nodes.getLength(); i++) {
                       Element element = (Element) nodes.item(i);

                       NodeList name = element.getElementsByTagName("key");
                       Element line = (Element) name.item(0);
                       String key= getCharacterDataFromElement(line);

                       NodeList title = element.getElementsByTagName("val");
                       line = (Element) title.item(0);
                       String val= getCharacterDataFromElement(line);
                       ogTrans.addItem(key+" "+val);
                       ogTrans.select(key+" "+val);
                    }

                    ogTrans.setWidth(LONG_FIELD_WIDTH);
                    gridPro.addComponent(ogTrans, 0, 2,3,2);

                    final MaskedTextField tfTel = new MaskedTextField("Téléphone(+)", "## ## ## ## ##");

                    tfTel.setWidth(COMMON_FIELD_WIDTH);
                    gridPro.addComponent(tfTel, 0, 3,1,3);
                    final NativeSelect nsTypeTel=new NativeSelect("Type");
                    nsTypeTel.addItem("Fixe");
                    nsTypeTel.addItem("Mobile");
                    nsTypeTel.addItem("Fax");
                    nsTypeTel.select("Fixe");
                    gridPro.addComponent(nsTypeTel, 2,3);
                    valideTel=new Button("valider");

                    valideTel.addListener(
                        new Button.ClickListener() {
                        public void buttonClick(Button.ClickEvent event) {
                            MyApplication.debug(3, "buttonClick");
                            if(tfTel.getValue()!=null &&
                                    tfTel.getValue().toString().length()==14)
                            {
                                String sType;
                                if(nsTypeTel.getValue().toString()!=null)
                                {
                                    sType=nsTypeTel.getValue().toString();
                                }
                                else
                                {
                                    sType="";
                                }

                                String sTel=tfTel.getValue().toString()+" "+sType;
                                ogTrans.addItem(sTel);
                                ogTrans.select(sTel);
                            }
                        }

    

                    });

                    gridPro.addComponent(valideTel, 3, 3);
                    gridPro.setComponentAlignment(valideTel, Alignment.BOTTOM_RIGHT);


                } catch (SAXException ex) {
                    Logger.getLogger(layoutProspection.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(layoutProspection.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(layoutProspection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
              
        }
        
    }
}