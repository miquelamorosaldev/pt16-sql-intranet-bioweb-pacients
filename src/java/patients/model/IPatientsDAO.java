/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patients.model;

import java.util.List;

/**
 *
 * @author alumne
 */
public interface IPatientsDAO {
    
    List<Patient> listAllPatients();
    Patient findOne(int registerId);
    boolean addPatient(Patient patient);
    List<Patient> listWomanPatients();
    List<Patient> listPatientsByRH(String rh);

    public List<Patient> filterByClassification(String classification);
    public int remove(Patient patient);

    public List<Patient> filter(String classification, int edatMin, int edatMax);

    public int update(Patient newPatientData);

    public int insert(Patient newPatientData);
}
