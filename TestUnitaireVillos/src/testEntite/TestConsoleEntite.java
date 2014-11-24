package testEntite;

import java.sql.Connection;

import be.condorcet.testunitairevillos.EntiteDB;
import myconnections.DBConnection;


public class TestConsoleEntite {

public static void main(String[] args) {
        
        DBConnection dbc =new DBConnection();
        Connection con = dbc.getConnection();
    
        if(con==null) { 
            System.out.println("connexion impossible");
            System.exit(0);
        }
        
        EntiteDB.setConnection(con);
        EntiteDB e1=null,e2=null; 
        
        // TEST AJOUT D UNE ENTITE DANS LA BASE DE DONNEES + TEST DE DOUBLONS (le meme nom)
        try{
            System.out.println("test ajout d'une entit�");
            System.out.println("=======================");
            e1=new EntiteDB("Namur");
            e1.create();
            System.out.println("e1="+e1);
            System.out.println("Ajout e1 OK");

            e2=new EntiteDB("namur"); // On v�rifie m�me les majuscules/minuscules ! Car namur et NAMUR c'est la m�me ville
            e2.create();
        } catch(Exception e){
            System.out.println("BAD exception d'ajout"+e);
        } 
        
        try{ 
            e1.delete();
            System.out.println("Suppresion de e1 OK !");
        } catch(Exception e) {}
        
        
       // TEST SUPPRESSION D UNE ENTITE (Et par la m�me occasion, on essaie de r�cup�rer les donn�es d'une entit� supprimer, ce qui provoquera une erreur) 
       try{
            System.out.println("\ntest d'effacement fructueux d'un v�lo");
            System.out.println("=======================================");
            e1=new EntiteDB("Namur");
            e1.create();
            System.out.println("e1="+e1);
            
            int idEntite=e1.getIdEntite();
            
            e1.delete();
            System.out.println("Suppresion de e1 OK !");
            
            e2=new EntiteDB(idEntite);
            System.out.println("On essaie de r�cup�rer les donn�es de e1");
            e2.read();
            
            System.out.println("e2 ="+e2);
            System.out.println("BAD");   
        } catch(Exception e){
            System.out.println("OK exception normale d'effacement"+e);
        }
        
        try{ 
            e1.delete();
        } catch(Exception e){}
        
        // TEST DE SUPPRESSION D UNE ENTITE IMPLIQUE DANS UN SITE
       try{
            System.out.println("\ntest d'effacement infructueux d'une entit� impliqu� dans une autre table, comme la table 'SITE");
            System.out.println("================================================================================================");
            
            e1=new EntiteDB(150);
            e1.delete();
        } catch(Exception e){
            System.out.println("OK exception normale d'effacement"+e);
        }
        
      // TEST DE MIS A JOUR D UNE ENTITE (On change le nom d'une entite)
        try{ 
            System.out.println("\ntest mise � jour d'une entit�");
            System.out.println("===============================");
            
            e1=new EntiteDB("Namur");
            e1.create();
            System.out.println("e1="+e1);
            
            int idEntite=e1.getIdEntite();
            
            e1.setNom("Bruges");
            
            e1.update();
            
            e2=new EntiteDB(idEntite);
            e2.read();
            
            System.out.println("e2="+e2);
            System.out.println("MIS A JOUR OK");
        } catch(Exception e){
            System.out.println("BAD exception de mise � jour "+e);
        }
        
        try{ 
            e1.delete();
        } catch(Exception e){}
        
       // Recherche l'entit� portant le nom de "Mons"
        try{
            System.out.println("\ntest de recherche fructueuse d'une entit�");
            System.out.println("===========================================");
            e1=EntiteDB.rechEntite("Mons");
            System.out.println(e1);
            System.out.println("RECHERCHE OK");
        } catch(Exception e){
            System.out.println("exception de recherche "+e);
        }
        
        // Recherche l'entit� portant le nom de "tt"
        try{
            System.out.println("\ntest de recherche infructueuse d'une entit�");
            System.out.println("=============================================");
            e1 = EntiteDB.rechEntite("tt");
        } catch(Exception e){
            System.out.println("OK exception normale recherche "+e);
        }
        
        try{  
           con.close();
        } catch(Exception e){}
    } 
}

