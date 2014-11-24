package testClient;

import java.sql.Connection;

import be.condorcet.testunitairevillos.ClientDB;
import myconnections.DBConnection;


public class TestConsoleClient {

public static void main(String[] args) {
        
        DBConnection dbc =new DBConnection();
        Connection con = dbc.getConnection();
    
        if(con==null) { 
            System.out.println("connexion impossible");
            System.exit(0);
        }
        
        ClientDB.setConnection(con);
        ClientDB c1=null,c2=null; 
        
        // TEST AJOUT D UN CLIENT DANS LA BASE DE DONNEES + TEST DE DOUBLONS (le meme pseudo et le meme email)
        try{
            System.out.println("test ajout d'un client");
            System.out.println("=======================");
            c1=new ClientDB("pseudo","password","email@hotmail.com","nom","prenom");
            c1.create();
            System.out.println("c1="+c1);
            System.out.println("Ajout c1 OK");

            c2=new ClientDB("pseudo","password2","email@hotmail.com2","nom2","prenom2"); // Le meme pseudo
            c2.create();
        } catch(Exception e){
            System.out.println("BAD exception d'ajout"+e);
        } 
        
        try {
        	c2 = new ClientDB("pseudo3","password3","email@hotmail.com","nom3","prenom3"); // Le meme email
        	c2.create();
        } catch(Exception e) {
        	System.out.println("BAD exception d'ajout"+e);
        }
        
        try{ 
            c1.delete();
            System.out.println("Suppresion de c1 OK !");
        } catch(Exception e) {}
        
        
      // TEST SUPPRESSION D UN CLIENT (Et par la même occasion, on essaie de récupérer les données d'un client supprimer, ce qui provoquera une erreur) 
       try{
            System.out.println("\ntest d'effacement fructueux d'un client");
            System.out.println("=======================================");
            c1=new ClientDB("pseudo","password","email@hotmail.com","nom","prenom");
            c1.create();
            System.out.println("c1="+c1);
            
            int idClient=c1.getIdClient();
            
            c1.delete(); // Suppression ok
            System.out.println("Suppresion de c1 OK !");
            
            c2=new ClientDB(idClient);
            System.out.println("On essaie de récupérer les données de e1");
            c2.read(); // On ne peut pas lire des données qui ont été éffacées
            
            System.out.println("c2 ="+c2);
            System.out.println("BAD");   
        } catch(Exception e){
            System.out.println("OK exception normale d'effacement"+e);
        }
        
        try{ 
            c1.delete();
        } catch(Exception e){}
        
        // TEST DE SUPPRESSION D UN CLIENT IMPLIQUE DANS UNE RESERVATION
       try{
            System.out.println("\ntest d'effacement infructueux d'un client impliqué dans une autre table, comme la table 'RESERVATION");
            System.out.println("================================================================================================");
            
            c1=new ClientDB(150);
            c1.delete();
        } catch(Exception e){
            System.out.println("OK exception normale d'effacement"+e);
        }
        
       // TEST DE MIS A JOUR D UN CLIENT
        try{ 
            System.out.println("\ntest mise à jour d'un client");
            System.out.println("===============================");
            
            c1=new ClientDB("pseudo","password","email@hotmail.com","nom","prenom");
            c1.create();
            System.out.println("c1="+c1);
            
            int idClient=c1.getIdClient();
            
            c1.setEmail("ChangementEmail");
            c1.setPassword("New pass");
            
            c1.update();
            
            c2=new ClientDB(idClient);
            c2.read();
            
            System.out.println("c2="+c2);
            System.out.println("MIS A JOUR OK");
        } catch(Exception e){
            System.out.println("BAD exception de mise à jour "+e);
        }
        
        try{ 
            c1.delete();
        } catch(Exception e){}
        
      // Recherche le client portant le pseudo de "mik1"
        try{
            System.out.println("\ntest de recherche fructueuse d'un client");
            System.out.println("===========================================");
            c1=ClientDB.rechClient("eti1");
            System.out.println(c1);
            System.out.println("RECHERCHE OK");
        } catch(Exception e){
            System.out.println("exception de recherche "+e);
        }
        
        // Recherche le client portant le nom de "tt"
        try{
            System.out.println("\ntest de recherche infructueuse d'un client");
            System.out.println("=============================================");
            c1 = ClientDB.rechClient("tt");
        } catch(Exception e){
            System.out.println("OK exception normale recherche "+e);
        }
        
        try{  
           con.close();
        } catch(Exception e){}
    } 
}

