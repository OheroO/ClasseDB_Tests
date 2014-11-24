package be.condorcet.testunitairevillos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientDB extends Client implements CRUD {
	
	protected static Connection dbConnect=null;
	
	public ClientDB() {
		
	}

// Constructeur paramétré à utiliser avant lors de la création, l'idClient ne doit pas être précisé, il sera affecté par la base de données lors de la création
	public ClientDB(String pseudo, String password, String email, String nom, String prenom) {
		super(0,pseudo,password,email,nom,prenom);
	}
	
	// Constructeur paramétré à utiliser avant lorsque le client est déjà présent dans la base de données
	public ClientDB(int idClient, String pseudo, String password, String email, String nom, String prenom) {
		super(idClient,pseudo,password,email,nom,prenom);
	}
	
	// A utiliser lorsque l'on veut lire les infos d'un client mais que l'on ne connait que son numéro
	public ClientDB(int idClient) {
		super(idClient,"","","","","");
	}

	// méthode statique permettant de partager la connexion entre toutes les instances de ClientDB
	public static void setConnection(Connection nouvdbConnect) {
	      dbConnect=nouvdbConnect;
	}

	public void create() throws Exception{
		 CallableStatement   cstmt=null;
	       try{
		   String req = "call createclient(?,?,?,?,?,?)";
		     cstmt = dbConnect.prepareCall(req);
	         cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
	         cstmt.setString(2,pseudo);
		     cstmt.setString(3,password);
		     cstmt.setString(4,email);
		     cstmt.setString(5,nom);
		     cstmt.setString(6,prenom);
		     cstmt.executeUpdate();
	         this.idClient=cstmt.getInt(1);
	     }
       catch(Exception e ){
          
                throw new Exception("Erreur de création "+e.getMessage());
             }
       finally{
            try{
              cstmt.close();
            }
            catch (Exception e){}
        }
       }

	@Override
    public void read() throws Exception {

		String req = "select * from client where idclient =?"; 
        PreparedStatement  pstmt=null;
        try{
        	pstmt=dbConnect.prepareStatement(req);
            pstmt.setInt(1,idClient);
     	    ResultSet rs=(ResultSet)pstmt.executeQuery();	
     	    
     	    if(rs.next()){
     	    	this.pseudo = rs.getString("PSEUDO");
     	    	this.password = rs.getString("PASSWORD");
     	    	this.email=rs.getString("EMAIL");    	
     	    	this.nom=rs.getString("NOM");
     	    	this.prenom=rs.getString("PRENOM");
     	    }
     	    else { 
     	    	throw new Exception("Code inconnu");
     	    }
        } catch(Exception e) {
        	throw new Exception("Erreur de lecture "+e.getMessage());
        }
        finally {
            try {
              pstmt.close();
            } catch (Exception e){}
        }
     }

	public static ClientDB rechClient(String pseudoRech) throws Exception{ 
		ClientDB resultat = null;
	    String req = "select * from client where pseudo = ?";
            
	    PreparedStatement pstmt=null;
	    try{
	    	pstmt = dbConnect.prepareStatement(req);
	    	pstmt.setString(1,pseudoRech);
	    	ResultSet rs=(ResultSet)pstmt.executeQuery();
	    	boolean trouve=false;
            	while(rs.next()){
            		trouve=true;
            		int idClient=rs.getInt("IDCLIENT");
            		String pseudo = rs.getString("PSEUDO");
            		String password = rs.getString("PASSWORD");
            		String email = rs.getString("EMAIL");
            		String prenom=rs.getString("PRENOM");
            		String nom=rs.getString("NOM");
            		resultat = new ClientDB(idClient,pseudo,password,email,nom,prenom);
            	}
            	if(!trouve)throw new Exception("Aucun client avec ce pseudo");
            	else return resultat;
	    } catch(Exception e){
	    	throw new Exception("Erreur de lecture "+e.getMessage());
	    } finally{
	    	try{
              pstmt.close();
            } catch (Exception e){}
        }
     }
	
	@Override
    public void update() throws Exception {
		CallableStatement cstmt=null;

	    try{
		     String req = "call updateclient(?,?,?,?,?,?)";
		     cstmt=dbConnect.prepareCall(req);
		     cstmt.setInt(1,idClient);
		     cstmt.setString(2,pseudo);
		     cstmt.setString(3,password);
		     cstmt.setString(4,email);
		     cstmt.setString(5,nom);
		     cstmt.setString(6,prenom);
		     cstmt.executeUpdate();
	            
	    } catch (Exception e) {
            throw new Exception("Erreur de mise à jour " + e.getMessage());
        } finally {
            try {
                cstmt.close();
            } catch (Exception e) {}
        }
    }

	@Override
    public void delete() throws Exception {
		CallableStatement cstmt =null;
	 	   try{
	 	     String req = "call delclient(?)";
	 	     cstmt = dbConnect.prepareCall(req);
	 	     cstmt.setInt(1,idClient);
	 	     cstmt.executeUpdate();
	 	     	     
	 	     }	 catch (Exception e) {
            throw new Exception("Erreur d'effacement " + e.getMessage());
        } finally {
            try {
            	cstmt.close();
            } catch (Exception e) {}
        }
    }

}
