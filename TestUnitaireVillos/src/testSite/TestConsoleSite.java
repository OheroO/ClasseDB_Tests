package testSite;

import java.sql.Connection;
import java.util.ArrayList;

import myconnections.DBConnection;
import be.condorcet.testunitairevillos.SiteDB;

public class TestConsoleSite {
	
public static void main(String[] args) {
        
        DBConnection dbc =new DBConnection();
        Connection con = dbc.getConnection();
    
        if(con==null) { 
            System.out.println("connexion impossible");
            System.exit(0);
        }
        
        SiteDB.setConnection(con);
        SiteDB s1=null,s2=null; 
        
     // TEST AJOUT D UN SITE DANS LA BASE DE DONNEES + TEST DE DOUBLONS (la meme adresse + le meme nom)
        try{
        	System.out.println("test ajout d'un site");
            System.out.println("=======================");
            s1=new SiteDB(150,"Site1","Rue","mikis",22,"mikis",30,30);
            s1.create();
            System.out.println("s1="+s1);
            System.out.println("Ajout s1 OK");

            s2=new SiteDB(150,"Site2","Rue","mikis",22,"mikis",30,30);
            s2.create();
        } catch(Exception e){
            System.out.println("BAD exception d'ajout"+e);
        } 
        
        try{ 
        	s2=new SiteDB(150,"Site1","nn","n",22,"nnn",30,30);
            s2.create();
            
        } catch(Exception e) {
        	System.out.println("BAD exception d'ajout"+e);
        }
        
        try{ 
            s1.delete();
            System.out.println("Suppresion de s1 OK !");
        } catch(Exception e) {}
        
        // TEST AJOUT D UN SITE DANS LA BASE DE DONNEES AVEC DES ERREURS DE CHECKCONSTRAINT (numero de porte négatif, emplacement max négatif, velo max négatif)
        try{
        	System.out.println("\ntest ajout d'un site avec erreur !");
            System.out.println("=======================");
            s1=new SiteDB(150,"Site5","Rue","mikis",22,"mikis",-1,30);
            s1.create();
        } catch(Exception e){
            System.out.println("BAD exception d'ajout"+e);
        } 
        
        try{
            s1=new SiteDB(150,"Site2","Rue","mikis",22,"mikis",30,-1);
            s1.create();
        } catch(Exception e){
            System.out.println("BAD exception d'ajout"+e);
        } 
            try{
            s1 = new SiteDB(150,"Site3","Rue","mikis",-1,"mikis",30,30);
            s1.create();
        } catch(Exception e){
            System.out.println("BAD exception d'ajout"+e);
        } 
        
        // TEST SUPPRESSION D UN Site (Et par la même occasion, on essaie de récupérer les données d'un site supprimer, ce qui provoquera une erreur) 
        try{
             System.out.println("\ntest d'effacement fructueux d'un site");
             System.out.println("=======================================");
             s1=new SiteDB(150,"Site1","Rue","mikis",22,"mikis",30,30);
             s1.create();
             System.out.println("s1="+s1);
             
             int idSite=s1.getIdSite();
             
             s1.delete();
             System.out.println("Suppresion de s1 OK !");
             
             s2=new SiteDB(idSite);
             System.out.println("On essaie de récupérer les données de s1");
             s2.read();
             
             System.out.println("s2 ="+s2);
             System.out.println("BAD");   
         } catch(Exception e){
             System.out.println("OK exception normale d'effacement"+e);
         }
         
         try{ 
             s1.delete();
         } catch(Exception e){}
         
         // TEST DE SUPPRESSION D UN SITE QUI POSSEDE DES VELOS
         try{
              System.out.println("\ntest d'effacement infructueux d'une site possédant des velos");
              System.out.println("==============================================================");
              
              s1=new SiteDB(31);
              s1.delete();
          } catch(Exception e){
              System.out.println("OK exception normale d'effacement"+e);
          }
         
         // TEST DE MIS A JOUR D UN SITE
         try{ 
             System.out.println("\ntest mise à jour d'un site");
             System.out.println("============================");
             
             s1=new SiteDB(150,"Site1","Rue","mikis",22,"mikis",30,30);
             s1.create();
             System.out.println("s1="+s1);
             
             int idSite=s1.getIdSite();
             
             s1.setNom("Le parc à villos de moi");
             
             s1.update();
             
             s2=new SiteDB(idSite);
             s2.read();
             
             System.out.println("s2="+s2);
             System.out.println("MIS A JOUR OK");
         } catch(Exception e){
             System.out.println("BAD exception de mise à jour "+e);
         }
         
         try{ 
             s1.delete();
         } catch(Exception e){}
         
      // Recherche de tous les sites présent dans l'entité s'appellant Mons
         try{
             System.out.println("\ntest de recherche fructueuse de sites");
             System.out.println("=======================================");
             ArrayList<SiteDB> liste=SiteDB.rechSites("Mons");
             for(SiteDB s:liste){
                System.out.println(s);
             }
             System.out.println("RECHERCHE OK");
         } catch(Exception e){
             System.out.println("exception de recherche "+e);
         }
         
      // Recherche tous les sites dans l'entitée s'appelant RRR (erreur car l'entitée n'existe pas)
         try{
             System.out.println("\ntest de recherche infructueuse de sites");
             System.out.println("=========================================");
             ArrayList<SiteDB> liste=SiteDB.rechSites("RRR");
             for(SiteDB s:liste){
                 System.out.println(s);
             }
             System.out.println("BAD");
         } catch(Exception e){
             System.out.println("OK exception normale recherche "+e);
         }
         
         try{  
            con.close();
         } catch(Exception e){}
        
}
        

}
