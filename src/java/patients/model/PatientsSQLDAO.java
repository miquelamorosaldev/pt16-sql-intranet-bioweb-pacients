/*
 * We used this Java Patters to design this class:
 * DAO 
 * https://www.oscarblancarteblog.com/2018/12/10/data-access-object-dao-pattern/
 * Singleton 
 * https://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples#lazy-initialization
 */
package patients.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import persist.DBConnect;
import persist.DBConnectionException;

/**
 *
 * @author alumne
 */
public class PatientsSQLDAO implements IPatientsDAO{
    
    private static List<Patient> patients;
            
    private static DBConnect dataSource;
    private final Properties queries;
    private static String PROPS_FILE;

    public PatientsSQLDAO(String ruta) throws IOException {
        queries = new Properties();
        PROPS_FILE = ruta + "/resources/patient_queries.properties";
        queries.load(new FileInputStream(PROPS_FILE));

        dataSource = DBConnect.getInstance(ruta);
    }
    
    
    @Override
    public List<Patient> listAllPatients() {
        ArrayList<Patient> list = new ArrayList<>();
        
        try ( Connection conn = dataSource.getConnection();
              Statement st = conn.createStatement(); )
        {
            ResultSet res = st.executeQuery(getQuery("FIND_ALL"));
            // DONE, 22/03/2022.
            while (res.next()) {
                Patient patient = new Patient();
                // Check the parameters match with friends.jsp form.
                //  SELECT p.idRegistre, p.edat, p.grupEdat, p.IMC, 
                // p.classificació, p.menarquia, p.tipusMenopausia FROM Patient p;
                patient.setRegisterId(res.getInt("idRegistre"));
                patient.setAge(res.getInt("edat"));
                patient.setAgeGroup(res.getString("grupEdat"));
                patient.setImc(res.getDouble("IMC"));
                patient.setClassification(res.getString("classificació"));
                patient.setMenarche(res.getInt("menarquia"));
                patient.setMenopauseType(res.getString("tipusMenopausia"));
                list.add(patient);
            }
            
        } catch (SQLException e) {
            try {
                throw new DBConnectionException("Error en la conexión a la base de datos.");
            } catch (DBConnectionException ex) {
                Logger.getLogger(PatientsSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            try {
                throw new DBConnectionException("Error en el sistema.");
            } catch (DBConnectionException ex) {
                Logger.getLogger(PatientsSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        
        return list;
    }
    
    /**
     * Mètode per a obtenir els pacients d'una classificació de l'estudi. Els
     * valors possibles són: NORMAL, OSTEOPOROSI, OSTEOPENIA.
     * @param classification
     * @return 
     */
    @Override
    public List<Patient> filterByClassification(String classification) {
        ArrayList<Patient> list = new ArrayList<>();
        
        try ( Connection conn = dataSource.getConnection();
              PreparedStatement st = conn.prepareStatement(getQuery("FILTER_BYCLASSIFICATION")); )
        {
            st.setString(1, classification);
            ResultSet res = st.executeQuery();
            // DONE, 22/03/2022.
            while (res.next()) {
                Patient patient = new Patient();
                // Check the parameters match with friends.jsp form.
                //  SELECT p.idRegistre, p.edat, p.grupEdat, p.IMC, 
                // p.classificació, p.menarquia, p.tipusMenopausia FROM Patient p;
                patient.setRegisterId(res.getInt("idRegistre"));
                patient.setAge(res.getInt("edat"));
                patient.setAgeGroup(res.getString("grupEdat"));
                patient.setImc(res.getDouble("IMC"));
                patient.setClassification(res.getString("classificació"));
                patient.setMenarche(res.getInt("menarquia"));
                patient.setMenopauseType(res.getString("tipusMenopausia"));
                list.add(patient);
            }
            
        } catch (SQLException e) {
            try {
                throw new DBConnectionException("Error en la conexión a la base de datos.");
            } catch (DBConnectionException ex) {
                Logger.getLogger(PatientsSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            try {
                throw new DBConnectionException("Error en el sistema.");
            } catch (DBConnectionException ex) {
                Logger.getLogger(PatientsSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        
        return list;
    }
    
    /**
     * Mètode per a obtenir les dades d'un pel seu id.
     * @param registerId Id del pacient.
     * @return Patient Dades del pacient
     */
    public Patient findOne(int registerId) {
        Patient patient = new Patient();
        
        try ( Connection conn = dataSource.getConnection();
              PreparedStatement st = conn.prepareStatement(getQuery("FIND_ONE")); )
        {
            st.setInt(1, registerId);
            ResultSet res = st.executeQuery();
            // DONE, 22/03/2022.
            while (res.next()) {
                // Check the parameters match with friends.jsp form.
                //  SELECT p.idRegistre, p.edat, p.grupEdat, p.IMC, 
                // p.classificació, p.menarquia, p.tipusMenopausia FROM Patient p;
                patient.setRegisterId(res.getInt("idRegistre"));
                patient.setAge(res.getInt("edat"));
                patient.setAgeGroup(res.getString("grupEdat"));
                patient.setImc(res.getDouble("IMC"));
                patient.setClassification(res.getString("classificació"));
                patient.setMenarche(res.getInt("menarquia"));
                patient.setMenopauseType(res.getString("tipusMenopausia"));
            }
            
        } catch (SQLException e) {
            try {
                throw new DBConnectionException("Error en la conexión a la base de datos.");
            } catch (DBConnectionException ex) {
                Logger.getLogger(PatientsSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            try {
                throw new DBConnectionException("Error en el sistema.");
            } catch (DBConnectionException ex) {
                Logger.getLogger(PatientsSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        
        return patient;
    }
    
    
    /**
     * Mètode per a obtenir els pacients d'una classificació de l'estudi.Els
 valors possibles són: NORMAL, OSTEOPOROSI, OSTEOPENIA.
     * @param classification
     * @param EdatMin
     * @param EdatMax
     * @return 
     */
    public List<Patient> filter(String classification, int edatMin, int edatMax) {
        ArrayList<Patient> list = new ArrayList<>();
        
        try ( Connection conn = dataSource.getConnection();
              PreparedStatement st = conn.prepareStatement(getQuery("FILTER")); )
        {
            st.setString(1, classification);
            st.setInt(2, edatMin);
            st.setInt(3, edatMax);
            ResultSet res = st.executeQuery();
            // DONE, 22/03/2022.
            while (res.next()) {
                Patient patient = new Patient();
                // Check the parameters match with friends.jsp form.
                //  SELECT p.idRegistre, p.edat, p.grupEdat, p.IMC, 
                // p.classificació, p.menarquia, p.tipusMenopausia FROM Patient p;
                patient.setRegisterId(res.getInt("idRegistre"));
                patient.setAge(res.getInt("edat"));
                patient.setAgeGroup(res.getString("grupEdat"));
                patient.setImc(res.getDouble("IMC"));
                patient.setClassification(res.getString("classificació"));
                patient.setMenarche(res.getInt("menarquia"));
                patient.setMenopauseType(res.getString("tipusMenopausia"));
                list.add(patient);
            }
            
        } catch (SQLException e) {
            try {
                throw new DBConnectionException("Error en la conexión a la base de datos.");
            } catch (DBConnectionException ex) {
                Logger.getLogger(PatientsSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            try {
                throw new DBConnectionException("Error en el sistema.");
            } catch (DBConnectionException ex) {
                Logger.getLogger(PatientsSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        
        return list;
    }
    
    public String getQuery(String queryName) {
        return queries.getProperty(queryName);
    }

    
    public int remove(Patient patient) {
        int rowsAffected = 0;

        try ( Connection conn = dataSource.getConnection();
              PreparedStatement pst = conn.prepareStatement(getQuery("DELETE")); )
        {
            pst.setInt(1, patient.getRegisterId());
            rowsAffected = pst.executeUpdate();
        } catch (SQLException e) {
            try {
                rowsAffected = -2;
                throw new DBConnectionException("Error a l'esborrar el pacient a la base de dades.");
            } catch (DBConnectionException ex) {
                Logger.getLogger(PatientsSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 

        return rowsAffected;
    }
    
    
    
    public int update(Patient patient) {
        int rowsAffected = 0;
        try ( Connection conn = dataSource.getConnection();
              PreparedStatement pst = conn.prepareStatement(getQuery("UPDATE")); )
        {
            // Fill the ? in the query: 
            // UPDATE = UPDATE Patient p SET p.edat=?, p.IMC=?, p.classificació=?, p.menarquia=? WHERE p.idRegistre=?
            pst.setInt(1, patient.getAge());
            pst.setDouble(2, patient.getImc());
            pst.setString(3, patient.getClassification());
            pst.setInt(4, patient.getMenarche());
            pst.setInt(5, patient.getRegisterId());
            rowsAffected = pst.executeUpdate();
        } catch (SQLException e) {
            try {
                rowsAffected = 0;
                throw new DBConnectionException("Error a l'actualitzar el pacient a la base de dades.");
            } catch (DBConnectionException ex) {
                Logger.getLogger(PatientsSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        return rowsAffected;
    }

    @Override
    public boolean addPatient(Patient patient) {
        // NOTA: Pending to validate Patient if exist or not.
        return patients.add(patient);
    }
    
    public List<Patient> listWomanPatients() {
        // List which will contain woman patients only. 
        List<Patient> womanPatients = new ArrayList<>();
        // gen-wom, gen-man, or gen-oth.
        for (Patient patient : patients) {
            if (patient.getGender().equals("gen-wom")) {
                womanPatients.add(patient);
            }
        }
        return womanPatients;
    }
    
    public List<Patient> listPatientsByRH(String rh) {
        List<Patient> filteredPatients = new ArrayList<>();
        char rhForm = rh.charAt(0);
        // gen-wom, gen-man, or gen-oth.
        for (Patient patient : patients) {
            if (patient.getRH() == rhForm) {
                filteredPatients.add(patient);
            }
        }
        return filteredPatients;
    }
    
    public List<Patient> listPatientsByBloodType(String bloodType) {
        List<Patient> filteredPatients = new ArrayList<>();
        // gen-wom, gen-man, or gen-oth.
        for (Patient patient : patients) {
            if (patient.getBloodType().equals(bloodType)) {
                filteredPatients.add(patient);
            }
        }
        return filteredPatients;
    }
    
    /**
     * Filter patients by BloodType and RH. 
     * @param bloodType Expected values: A, O, AB, B
     * @param rh Expected values: +, -
     * @return Filtered patients list.
     */
    public List<Patient> listPatientsByBloodTypeAndRH(String bloodType, String rh) {
        List<Patient> filteredPatients = new ArrayList<>();
        char rhForm = rh.charAt(0);
        // gen-wom, gen-man, or gen-oth.
        for (Patient patient : patients) {
            if (patient.getRH() == rhForm && 
                    patient.getBloodType().equals(bloodType) ) {
                filteredPatients.add(patient);
            }
        }
        return filteredPatients;
    }
    
}
