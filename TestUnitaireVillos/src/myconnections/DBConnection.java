package myconnections;

import java.sql.*;
import java.util.*;

public class DBConnection {
    protected String serverName;
    protected String username;
    protected String password;
    protected String dbName;
    protected String dbPort;
    private static DBConnection instance = null;
 
    public DBConnection(){
        PropertyResourceBundle properties = (PropertyResourceBundle)
        PropertyResourceBundle.getBundle("resources.application");
        //nom du fichier properties à utiliser
        serverName=properties.getString("cours.DB.server");
        dbName =properties.getString("cours.DB.database");
        username=properties.getString("cours.DB.login");
        password=properties.getString("cours.DB.password");
        dbPort=properties.getString("cours.DB.port");    
    }

    public DBConnection(String username,String password){
      this.username=username;
      this.password=password;
    }
    
    public static DBConnection getInstance(){
        if(instance==null) instance = new DBConnection();
        return instance;
    }
    
    public static DBConnection getInstance(String username, String password) {
        if(instance==null) instance=new DBConnection(username,password);
        else {
            instance.setUsername(username);
            instance.setPassword(password);
        }
        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Connection getConnection() {
       try {
           Class.forName ("oracle.jdbc.OracleDriver");
           String url = "jdbc:oracle:thin:@//"+serverName+":"+dbPort+"/"+dbName;
           Connection dbConnect = DriverManager.getConnection(url, username, password);
           return dbConnect; 
       }
    
       catch(Exception e) {
           System.out.println("erreur de connexion "+e);
           e.printStackTrace();
           return null ;
       }
   }
}
 

