/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import persist.DBConnect;
import persist.DBConnectionException;

/**
 *
 * @author alumne
 */
public class UserDAOSQL implements IUserDAO {

    private static DBConnect dataSource;
    private final Properties queries;
    private static String PROPS_FILE;

    public UserDAOSQL(String ruta) throws IOException {
        queries = new Properties();
        PROPS_FILE = ruta + "/resources/user_queries.properties";
        queries.load(new FileInputStream(PROPS_FILE));

        dataSource = DBConnect.getInstance(ruta);
    }
    
    @Override
    public User login(String username, String password) {
        User loggedUser = new User();
        // https://www.baeldung.com/sql-injection
        try ( Connection conn = dataSource.getConnection();
              PreparedStatement st = conn.prepareStatement(getQuery("LOGIN")); )
        {
            st.setString(1, username);
            st.setString(2, password);
            ResultSet res = st.executeQuery();
            while (res.next()) {
                loggedUser.setUsername(res.getString("username"));
                loggedUser.setPassword(res.getString("password"));
                // https://www.baeldung.com/java-string-to-enum
                loggedUser.setRole(Role.ADMIN);
//                loggedUser.setRole(
//                    Role.valueOf(
//                        res.getString("role")));
            }
        } catch (SQLException e) {
            try {
                throw new DBConnectionException("Error en la conexión a la base de datos.");
            } catch (DBConnectionException ex) {
                Logger.getLogger(UserDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            try {
                throw new DBConnectionException("Error en el sistema.");
            } catch (DBConnectionException ex) {
                Logger.getLogger(UserDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        return loggedUser;
    }
    
    
    public ArrayList<User> listAllUsers() {
        ArrayList<User> list = new ArrayList<>();
        try ( Connection conn = dataSource.getConnection();
              Statement st = conn.createStatement(); )
        {    
            ResultSet res = st.executeQuery(getQuery("FIND_ALL"));

            while (res.next()) {
                User user = new User();
                user.setUsername(res.getString("username"));
                user.setPassword(res.getString("password"));
                user.setRole(
                        Role.valueOf(res.getString("role")));
                list.add(user);
            }

        } catch (SQLException e) {
            list = new ArrayList<>();
        }
        
        return list;
    }
    
    public String getQuery(String queryName) {
        return queries.getProperty(queryName);
    }

    @Override
    public boolean logout(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
