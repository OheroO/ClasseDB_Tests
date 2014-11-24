package testReservation;

import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import myconnections.DBConnection;
import be.condorcet.testunitairevillos.ReservationDB;

public class TestConsoleReservation {
	
public static void main(String[] args) {
        
        DBConnection dbc =new DBConnection();
        Connection con = dbc.getConnection();
    
        if(con==null) { 
            System.out.println("connexion impossible");
            System.exit(0);
        }
        
        ReservationDB.setConnection(con);
        ReservationDB r1=null,r2=null; 
        
     // TEST AJOUT D UNE RESERVATION DANS LA BASE DE DONNEES + TEST DE DOUBLONS (on veut reserver le meme vélo à la meme date)
        try{
        	System.out.println("test ajout d'une reservation");
            System.out.println("============================"); 
            
            r1=new ReservationDB(500,150,new java.sql.Date(System.currentTimeMillis()),"en cours");

            r1.create();

            System.out.println("r1="+r1);
            System.out.println("Ajout r1 OK");

            r2=new ReservationDB(500,149,new java.sql.Date(System.currentTimeMillis()),"en cours");
            r2.create();
        } catch(Exception e){
            System.out.println("BAD exception d'ajout"+e);
        } 
        
        try{ 
           r1.delete();
            System.out.println("Suppresion de r1 OK !");
        } catch(Exception e) {}
        
       // TEST AJOUT D UNE RESERVATION DANS LA BASE DE DONNEES d'un vélo inclu dans un site qui a 3 vélos
        try{
        	System.out.println("\ntest ajout d'une reservation avec erreur !");
            System.out.println("============================================");
            r1=new ReservationDB(504,150,new java.sql.Date(System.currentTimeMillis()),"en cours");
            r1.create();
        } catch(Exception e){
            System.out.println("BAD exception d'ajout"+e);
        } 
        
     // TEST AJOUT D UNE RESERVATION DANS LA BASE DE DONNEES avec des données qui n'existe pas
        try{
        	System.out.println("\ntest ajout d'une reservation avec erreur (idvelo et client n'existent pas) !");
            System.out.println("==============================================================================");
            r1=new ReservationDB(0,0,new java.sql.Date(System.currentTimeMillis()),"en cours");
            r1.create();
        } catch(Exception e){
            System.out.println("BAD exception d'ajout"+e);
        } 
        
        // TEST SUPPRESSION D UNE RESERVATION (Et par la même occasion, on essaie de récupérer les données d'une reservation supprimer, ce qui provoquera une erreur) 
        try{
             System.out.println("\ntest d'effacement fructueux d'une reservation");
             System.out.println("===============================================");
             r1=new ReservationDB(500,150,new java.sql.Date(System.currentTimeMillis()),"en cours");
             r1.create();
             System.out.println("r1="+r1);
             
             int idReservation=r1.getIdReservation();
             
             r1.delete();
             System.out.println("Suppresion de s1 OK !");
             
             r2=new ReservationDB(idReservation);
             System.out.println("On essaie de récupérer les données de r1");
             r2.read();
             
             System.out.println("r2 ="+r2);
             System.out.println("BAD");   
         } catch(Exception e){
             System.out.println("OK exception normale d'effacement"+e);
         }
         
         try{ 
             r1.delete();
         } catch(Exception e){}
         
         // TEST DE MIS A JOUR D UNE RESERVATION
         try{ 
             System.out.println("\ntest mise à jour d'une reservation");
             System.out.println("==================================");
             
             r1=new ReservationDB(500,150,new java.sql.Date(System.currentTimeMillis()),"en cours");
             r1.create();
             System.out.println("r1="+r1);
             
             int idReservation=r1.getIdReservation();
             
             r1.setEtat("Fini");
             
             r1.update();
             
             r2=new ReservationDB(idReservation);
             r2.read();
             
             System.out.println("r2="+r2);
             System.out.println("MIS A JOUR OK");
         } catch(Exception e){
             System.out.println("BAD exception de mise à jour "+e);
         }
         
         try{ 
             r1.delete();
         } catch(Exception e){}
         
      // Recherche de toutes les reservations pour le client 'mik1'
         try{
             System.out.println("\ntest de recherche fructueuse de reservation");
             System.out.println("===========================================");
             ArrayList<ReservationDB> liste=ReservationDB.rechReservation("mik1");
             for(ReservationDB r:liste){
                System.out.println(r);
             }
             System.out.println("RECHERCHE OK");
         } catch(Exception e){
             System.out.println("exception de recherche "+e);
         }
         
      // Recherche toutes les reservation pour le client 'OOOO'
         try{
             System.out.println("\ntest de recherche infructueuse de reservation");
             System.out.println("=============================================");
             ArrayList<ReservationDB> liste=ReservationDB.rechReservation("OOOO");
             for(ReservationDB r:liste){
                 System.out.println(r);
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
