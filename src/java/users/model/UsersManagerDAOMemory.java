package users.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Modulo de autenticación de usuarios provisional.
 * @author mamorosal
 */
public class UsersManagerDAOMemory implements IUserDAO {
    
    private Map<String,User> users;
    
    public UsersManagerDAOMemory() {
        users = new HashMap<>();
        users.put("dawbio2",
            new User("dawbio2","admin",Role.ADMIN));
        users.put("prof",
            new User("prof","admin",Role.ADMIN));
        users.put("dawbio1",
            new User("dawbio1","alumne",Role.USER));
        users.put("daw2",
            new User("daw2","alumne",Role.USER));
        users.put("dam2",
            new User("dam2","alumne",Role.USER));
        users.put("asix2",
            new User("asix2","alumne",Role.USER));
    }
    
    public String getRole(String username) {
        // Existe nombre de usuario ?
        boolean exists = users.containsKey(username);
        // Contraseña correcta ? 
        User dbUser = users.get(username);
        if(dbUser!=null) {
            Role role = dbUser.getRole();
            return role.toString();
        } else {
           return null; 
        }
    }
    
    public ArrayList<User> listAllUsers() {
        // https://www.baeldung.com/java-convert-collection-arraylist
        Collection<User> colUsers = users.values();
        ArrayList<User> usersList = new ArrayList<>(colUsers);
        return usersList;
    }

    @Override
    public User login(String username, String password) {
        // Existe nombre de usuario ?
        boolean exists = users.containsKey(username);
        User dbUser = new User();
        String pwForm = "";
        // Contraseña correcta ? 
        if(exists) {
            dbUser = users.get(username);
            pwForm = dbUser.getPassword();
        }
        return dbUser;
    }

    @Override
    public boolean logout(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
