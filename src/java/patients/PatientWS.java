/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patients;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import patients.model.Patient;
import patients.model.PatientsSQLDAO;

/**
 *
 * @author alumne
 */
@WebServlet(name = "PatientWS", urlPatterns = {"/PatientWS"})
public class PatientWS extends HttpServlet {

    private PatientsSQLDAO patientDAO;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session=request.getSession();
        String patientsJsonResult = "";
        if(session.getAttribute("user")==null){
            // JSONWriter(response, "Error! No estàs registrat al servei.");
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print("Error! No estàs registrat al servei.");
            out.flush();
        } else {
            // tot lo que haviem fet abans.

            Gson gson = new Gson();
            // calcula la ruta absoluta para llegar a WEB-INF, si tenemos un fichero.
            // Cuando hacemos Clean & Build, se genera otra estructura de directorios: LoginApplication/build/web/WEB-INF/
            String path = getServletContext().getRealPath("/WEB-INF");
            
            patientDAO = new PatientsSQLDAO(path);
            
            List<Patient> patients = new ArrayList<>();
            Patient patient = new Patient();
            String action=request.getParameter("action");
            switch(action){
                case "ListAll":
                    // Llegim els pacients de la base de dades
                    patients = patientDAO.listAllPatients();
                    // Posem la llista de pacients en un fitxer JSON.
                    patientsJsonResult = gson.toJson(patients);
                    break;
                case "ListNormalPatients": 
                    // Llegim els pacients de la base de dades
                    patients = patientDAO.filterByClassification("NORMAL");
                    // Posem la llista de pacients en un fitxer JSON.
                    patientsJsonResult = gson.toJson(patients);
                    break; 
                case "ListPatientsByClassification": 
                    String classification=request.getParameter("classification");
                    String result = patient.getClassificationValues().get(classification);
                    if(result!=null) {
                       // Llegim els pacients de la base de dades
                        patients = patientDAO.filterByClassification(classification);
                    } else {
                        patientsJsonResult = "Error! Classificació del pacient no vàlida. Possibles valors: NORMAL, OESTOPENIA, OESTOPOROSI";
                    }
                    break; 
            }  
            patientsJsonResult = gson.toJson(patients);
            // Responem incloent la llista de pacients en format JSON
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(patientsJsonResult);
            out.flush();
        }
    }


    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
