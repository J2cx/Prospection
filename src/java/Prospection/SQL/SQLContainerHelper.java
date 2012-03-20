/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Prospection.SQL;

/**
 *
 * @author weizhe.jiao
 */

import Prospection.vaadin.MyApplication;
import com.vaadin.addon.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.addon.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.addon.sqlcontainer.query.TableQuery;
import com.vaadin.addon.sqlcontainer.SQLContainer;
import com.vaadin.addon.sqlcontainer.query.FreeformQuery;
import java.sql.SQLException;
import com.vaadin.addon.sqlcontainer.query.generator.MSSQLGenerator;
import com.vaadin.data.Item;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class SQLContainerHelper {

    private static SimpleJDBCConnectionPool pool;
    private static  SQLContainer container;

    class SQLContainerWithDefaultValue extends SQLContainer
    {
        public SQLContainerWithDefaultValue(TableQuery tq) throws SQLException
        {
            super(tq);
        }

        @Override
        public Object addItem()
        {
            super.addItem();
            Object o=new Object();
            return o;
        }
    }

    public SQLContainerHelper() throws SQLException
    {
        initPool();
    }

    private static void initPool() throws SQLException
    {
            pool = new SimpleJDBCConnectionPool("com.microsoft.sqlserver.jdbc.SQLServerDriver",
                "jdbc:sqlserver://da-newdm:1433;databaseName=db_prospection",
                "sa",
                "limubai64",
                5,
                100);
    }

    public static SQLContainer ContainerFreeQuery(String query) throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }
        container=new SQLContainer(new FreeformQuery(query,pool));
        return container;
    }

    public static SQLContainer GetProspecteursForm() throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }

        String query ="select  mail as login, Pre as firstName, Nom as lastName,"
                + " Adr_Nr as nb, Adr_Rue as rue, Adr_CP as cp, Adr_Ville as ville, "
                + " Tel_Fix as fix, Tel_Pro as fax, Tel_Mob as portable "
                + " FROM Prospecteurs";
        container=new SQLContainer(new FreeformQuery(query,pool));
        
        return container;

    }

        public static SQLContainer GetProspecteursFormTable() throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }

                container=new SQLContainer(new TableQuery("Prospecteurs",pool,new MSSQLGenerator()));
                
                
        return container;

    }

    public static Item GetProspecteurByLogin(String login) throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }
        
        Item item;
        /*String query ="select  mail as login, Pre as firstName, Nom as lastName,"
                + " Adr_Nr as nb, Adr_Rue as rue, Adr_CP as cp, Adr_Ville as ville, "
                + " Tel_Fix as fix, Tel_Pro as fax, Tel_Mob as portable "
                + " FROM Prospecteurs where mail='"+login+"'";
        */
        String query="select * from prospecteurs where mail='"+login+"'";
         container=new SQLContainer(new FreeformQuery(query,pool));
        item=container.getItem(container.firstItemId());
        return item;

    }



    public static SQLContainer ContainerTableQuery(String tableName) throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }
        container=null;
        TableQuery tq= new TableQuery(tableName, pool, new MSSQLGenerator());
        container = new SQLContainer(tq);
        //container=new SQLContainer(new TableQuery(tableName, pool));
        return container;
    }

        public static SQLContainer ProspecteursContainer() throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }
        container=null;
        TableQuery tq= new TableQuery("Prospecteurs", pool, new MSSQLGenerator());
        container = new SQLContainer(tq);
        //container=new SQLContainer(new TableQuery("Prospecteurs", pool));
        return container;
    }

    public static SQLContainer ContainerFichAgence(int id_Prospecteur) throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }
        container=null;
        String query="select * from db_directmandat.dbo.fn_getAgAux(12625)";
         container=new SQLContainer(new FreeformQuery(query,pool));
        //container=new SQLContainer(new TableQuery(tableName, pool));
        return container;
    }

    public static Item ItemFichAgence(int idAgence) throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }

        Item item;
        

           
        
        String query="select * from db_directmandat.dbo.fn_getAgAux("+idAgence+")";
         container=new SQLContainer(new FreeformQuery(query,pool));
        item=container.getItem(container.firstItemId());
        return item;
    }
    
    public static int IdAgence(int id_Prospecteur) throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }

        Item item;
        String queryGetAgence="exec sp_getAbonne "+Integer.toString(id_Prospecteur);
        SQLContainer containerAgence=new SQLContainer(new FreeformQuery(queryGetAgence,pool));
        int idAgence=0;
        if(containerAgence.size()>0)
        {
            idAgence=(Integer)containerAgence.getItem(containerAgence.firstItemId()).getItemProperty("agence").getValue();
        }
        return idAgence;
    }
    
        public static int GetIdAnnonce(int id_Prospecteur, int id_Agence) throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }

        String queryGetAnnonce="exec sp_getAnnonce "+Integer.toString(id_Prospecteur)
                +", "+Integer.toString(id_Agence);
        
         container=new SQLContainer(new FreeformQuery(queryGetAnnonce,pool));
         int  id_Annonce;
         container.setAutoCommit(false);
         
         if(container.getItem(container.firstItemId()).getItemProperty("annonce")==null)
         {
             id_Annonce=0;
         }
         else
         {
            id_Annonce=(Integer)container.getItem(container.firstItemId()).getItemProperty("annonce").getValue();
         }
        //return 0;
        return id_Annonce;
    }

    public static int GetAuxbyIdAnnonce(int id_Annonce) throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }

        String queryGetAnnonce="select top 1 aux from anns where id = "+id_Annonce;
        
        container=new SQLContainer(new FreeformQuery(queryGetAnnonce,pool));
        if(container.firstItemId()==null)
        {
            return 0;
        }
        
        int aux=(Integer)container.getItem(container.firstItemId()).getItemProperty("aux").getValue();
         
        return aux;
    }
        
    

        public static SQLContainer ContainerFichAnnonceRecent(int id_Agence, int id_Annonce, int aux) throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }

        //Item item;
        if(id_Agence!=0 && id_Annonce!=0)
        {
            try
            {
                /*
                String str_query = ""
                        + " select top 1 A.*"
                        + " from db_dmStock..dm_"+id_Agence+" as A"
                        + " right join anns as B"
                        + " on A.aux = B.aux and A.trans = B.trans"
                        + " where B.id = "+id_Annonce+""
                        + " order by A.ann_datepar DESC"
                        + "";
                
                //String str_query= "exec sp_getAnnDetail "+id_Agence+" , "+id_Annonce;
                String viewName = "db_dmStock..dm_" + id_Agence;
                */
                String str_query="select top 1 * from db_dmStock..vw_"+id_Agence+"_V where aux = "  
                        +"( select top 1 aux from anns where id = "    
                        +id_Annonce+ " )order by ann_datepar DESC";   
                //String str_query= "exec sp_getAnnDetail "+id_Agence+" , "+id_Annonce;     
                String viewName="db_dmStock..vw_"+id_Agence+"_V";
                
                
                FreeformQuery query=new FreeformQuery(str_query,
                        Arrays.asList("id"),pool);
                query.setDelegate(new ProspecterFreeFormDeleget(viewName,id_Annonce,aux));
                container=new SQLContainer(query);

                container.setAutoCommit(false);


                //item=container.getItem(container.firstItemId());
            
            }
            catch (SQLException sqle)
            {
                MyApplication.debug(3,"ItemFichAnnonceRecent SQL excepetion");
                container=null;
                //item=null;
            }
        }
        else
            //item=null;
            container=null;
        
        return container;
    }

    public static SQLContainer ContainerFichAnnonce() throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }
        container=null;
        String query="select top 1 * from db_dmStock..vw_12625_V where aux ="+
        "(        select top 1 aux        from db_directmandat..prospection "+
        " where agence = 12625        and status = 'pending request' "+
        " and trans = 'V') order by ann_datepar DESC";
         container=new SQLContainer(new FreeformQuery(query,pool));
        //container=new SQLContainer(new TableQuery(tableName, pool));
        return container;
    }
    
        public static SQLContainer ContainerAnnoncePrixDate() throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }
        container=null;
        FreeformQuery query=new FreeformQuery("select * from vwtest",
                Arrays.asList("id"), 
                 pool);
        query.setDelegate(new DemoFreeformQueryDelegate());
        
         container=new SQLContainer(query);
        //container=new SQLContainer(new TableQuery(tableName, pool,new MSSQLGenerator()));
        return container;
    }
        
    public static Item ItemStatsPrp(int idProspecteur) throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }

        Item item;
        Date dateNow=new Date();
        //java.sql.Date date = new java.sql.Date(dateNow.getTime()); 
        
        DateFormat df=new SimpleDateFormat("dd/MMM/yyyy");
        String s=df.format(dateNow);
 

        
        String query="select * from db_prospection.dbo.statsPrp("+idProspecteur
                        +", '10/10/2000','"+s +"')";
        MyApplication.debug(3, query) ;
        container=new SQLContainer(new FreeformQuery(query,pool));
        item=container.getItem(container.firstItemId());
        return item;
    }
    
        public static Item ItemStatsAgence(int idAgence) throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }

        Item item;
        Date dateNow=new Date();
        //java.sql.Date date = new java.sql.Date(dateNow.getTime()); 
        
        DateFormat df=new SimpleDateFormat("dd/MMM/yyyy");
        String s=df.format(dateNow);
 

        
        String query="select a.*,b.denomination from db_prospection.dbo.statsAbo("+idAgence
                        + ", '10/10/2000','"+s +"') as a "
                        + ", db_directmandat.dbo.fn_getAgAux("+idAgence
                        +") as b";
        MyApplication.debug(3, query) ;
        container=new SQLContainer(new FreeformQuery(query,pool));
        item=container.getItem(container.firstItemId());
        return item;
    }
    
    
    public static ArrayList GetIddsPrps() throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }
        
        ArrayList ids=new ArrayList();
       
        String query="select id from prospecteurs";
        container=null;
        //MyApplication.debug(3, Integer.toString(container.size())) ;
        container=new SQLContainer(new FreeformQuery(query,pool));
        for(Object o:container.getItemIds())
        //for()
        {
            int id=(Integer)container.getItem(o)
                    .getItemProperty("id").getValue();
            //int id=(Integer)container.getItem(container.getIdByIndex(i))
            //        .getItemProperty("id").getValue();
            ids.add(id);
        }
        return ids;
    }
    
    public static ArrayList GetIddsAgences() throws SQLException
    {
        if(pool==null)
        {
            initPool();
        }
        
        ArrayList ids=new ArrayList();
       
        String query="select abo from abonnes";
        container=null;
        //MyApplication.debug(3, Integer.toString(container.size())) ;
        container=new SQLContainer(new FreeformQuery(query,pool));
        for(Object o:container.getItemIds())
        //for()
        {
            int id=(Integer)container.getItem(o)
                    .getItemProperty("abo").getValue();
            //int id=(Integer)container.getItem(container.getIdByIndex(i))
            //        .getItemProperty("id").getValue();
            ids.add(id);
        }
        return ids;
    }
        
}
