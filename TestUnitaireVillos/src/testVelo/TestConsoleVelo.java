package testVelo;

import java.sql.Connection;
import java.util.ArrayList;

import be.condorcet.testunitairevillos.VeloDB;
import myconnections.DBConnection;


public class TestConsoleVelo {

public static void main(String[] args) {
        
        DBConnection dbc =new DBConnection();
        Connection con = dbc.getConnection();
    
        if(con==null) { 
            System.out.println("connexion impossible");
            System.exit(0);
        }
        
        VeloDB.setConnection(con);
        VeloDB v1=null,v2=null; 
        
        // TEST AJOUT D UN VELO DANS LA BASE DE DONNEES (On ne test pas les doublons car cela n'a pas de sens, s'il on ajoute un v�lo dans la base de donn�e, il aura automatiquement un nouvel identifiant)
        try{
            System.out.println("test ajout d'un v�lo");
            System.out.println("====================");
            v1=new VeloDB(150,"D");
            v1.create();
            
            int idVelo = v1.getIdVelo();
            
            v2=new VeloDB(idVelo);
            v2.read();
            System.out.println("v2="+v2);
            System.out.println("Ajout OK");
        } catch(Exception e){
            System.out.println("BAD exception d'ajout"+e);
        } 
        
        try{ 
            v1.delete();
            System.out.println("Suppresion de v1 OK !");
        } catch(Exception e) {}
        
        
       // TEST SUPPRESSION D UN VELO (Et par la m�me occasion, on essaie de r�cup�rer les donn�es d'un v�lo supprimer, ce qui provoquera une erreur) 
       try{
            System.out.println("\ntest d'effacement fructueux d'un v�lo");
            System.out.println("=====================================");
            v1=new VeloDB(150,"D");
            v1.create();
            System.out.println("v1="+v1);
            
            int idVelo=v1.getIdVelo();
            
            v1.delete();
            System.out.println("Suppresion de v1 OK !");
            
            v2=new VeloDB(idVelo);
            System.out.println("On essaie de r�cup�rer les donn�es de v1");
            v2.read();
            
            System.out.println("v2 ="+v2);
            System.out.println("BAD");   
        } catch(Exception e){
            System.out.println("OK exception normale d'effacement"+e);
        }
        
        try{ 
            v1.delete();
        } catch(Exception e){}
        
        // TEST DE SUPPRESSION D UN VELO IMPLIQUE DANS UNE RESERVATION  
       try{
            System.out.println("\ntest d'effacement infructueux d'un v�lo impliqu� dans une r�servation");
            System.out.println("=====================================================================");
            
            v1=new VeloDB(501);
            v1.delete();
        } catch(Exception e){
            System.out.println("OK exception normale d'effacement"+e);
        }
        
       // TEST DE MIS A JOUR D UN VELO (On change le v�lo d'un site et on change son �tat en non disponible)
        try{ 
            System.out.println("\ntest mise � jour d'un v�lo");
            System.out.println("==========================");
            
            v1=new VeloDB(150,"D");
            v1.create();
            System.out.println("v1="+v1);
            
            int idVelo=v1.getIdVelo();
            
            v1.setFkSite(310);
            v1.setEtat("ND");
            
            v1.update();
            
            v2=new VeloDB(idVelo);
            v2.read();
            
            System.out.println("v2="+v2);
            System.out.println("MIS A JOUR OK");
        } catch(Exception e){
            System.out.println("BAD exception de mise � jour "+e);
        }
        
        try{ 
            v1.delete();
        } catch(Exception e){}
        
        // Recherche de tous les v�los pr�sent dans le site n�150
        try{
            System.out.println("\ntest de recherche fructueuse d'un v�lo");
            System.out.println("======================================");
            ArrayList<VeloDB> liste=VeloDB.rechVelos(150);
            for(VeloDB v:liste){
               System.out.println(v);
            }
            System.out.println("RECHERCHE OK");
        } catch(Exception e){
            System.out.println("exception de recherche "+e);
        }
        
        // Recherche tous les v�los dans le site 5 (erreur car le site n'existe pas ou qu'il n'a pas de v�lo)
        try{
            System.out.println("\ntest de recherche infructueuse d'un v�lo");
            System.out.println("========================================");
            ArrayList<VeloDB> liste=VeloDB.rechVelos(5);
            for(VeloDB v:liste){
                System.out.println(v);
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

