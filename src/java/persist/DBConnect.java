package persist;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

// Baixem el connector de MYSQl amb JDBC de la web 
// https://dev.mysql.com/downloads/connector/j/ en l' opció: PLATFORM INDEPENDENT
// AFEGIM EL .JAR QUE HI HA DINS a la categoria de Libraries
// també afegirem per més endavant les llibreries internes que 
// té NetBeans de jstl
public final class DBConnect {

    private static DBConnect instance = null;
    
    private final Properties configDB;
    String Fitxer_Config = "/resources/database_config_lab.properties";
    private String PROPS_FILE;
    
    private DBConnect(String ruta) {
        
        configDB = new Properties();
        try {
            PROPS_FILE = ruta + Fitxer_Config;
            configDB.load(new FileInputStream(PROPS_FILE));
            // Class.forName(DRIVER);
            Class.forName(configDB.getProperty("DRIVER"));
            
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * <strong>getInstance()</strong>
     * gets an unique instance of DBConnect.
     *
     * @return an instance of DBConnect.
     */
    public static DBConnect getInstance(String ruta) {
        if (instance == null) {
            instance = new DBConnect(ruta);
        }
        return instance;
    }

    /**
     * <strong>getConnection()</strong>
     * establishes a connection to the database.
     *
     * @return Connection to database.
     * @throws SQLException if connection error occurs.
     */
    public Connection getConnection() throws SQLException {
        Connection c = DriverManager.getConnection(
                configDB.getProperty("BD_URL"), 
                configDB.getProperty("USUARI"), 
                configDB.getProperty("PASSWORD"));
        return c;
    }

}
