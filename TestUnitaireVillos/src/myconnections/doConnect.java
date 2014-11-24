package myconnections;

import java.sql.Connection;

public class doConnect {
    protected static Connection dbConnect=null;
    
    public static void connect() throws Exception {
        DBConnection dbc = DBConnection.getInstance();
        dbConnect = dbc.getConnection();
        if(dbConnect==null) throw new Exception("Connection à la base de données impossible!");
        // TOUT MES DB
       
    }
    
    public static void disconnect() {
        try {
            dbConnect.close();
        } catch(Exception e) {}
    }
    
}
