package patients;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import patients.model.IPatientsDAO;
import patients.model.Patient;
import patients.model.PatientsMemoryDAO;
import patients.model.PatientsSQLDAO;
import users.UserController;

/**
 *
 * @author mamorosal
 */
@WebServlet(name = "PatientsController", urlPatterns = {"/patient"})
public class PatientsController extends HttpServlet {

    /**
     * Llama a la clase Manager de los pacientes de la app.
     */
    IPatientsDAO patientsManager;
   
    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        String path = getServletContext().getRealPath("/WEB-INF");
        
        try {
             // Debug
             // patientsDAO = new PatientsMemoryDAO();
            // Inyectamos el DAO de la fuente que preferimos.
            patientsManager = new PatientsSQLDAO(path);
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if(request.getParameter("action")!=null){
            String action=request.getParameter("action");
            switch(action){
                case "ListAll":
                    listAll(request,response);
                break;
                case "FILTER_BYCLASSIFICATION":
                    filterByClassification(request,response);
                break;
                
                // NO HO NECESSITEM.
//                case "DeletePatient":
//                    //response.sendRedirect("friend.jsp?showFormDelete=1");
//                    deletePatientForm(request, response);
//                    break;
                
                // TODO PENDENT.
                case "patientToDelete":
                    deletePatient(request, response);
                    break;
            }  
        } else{
            response.sendRedirect("login.jsp");
        }
    }
    
    private void listAll(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Agafem les dades del formulari de filtre.
//        String username=request.getParameter("username");
//        String password=request.getParameter("password"); 

        // 1. Verifiquem la sessió.
        // Agafem les dades de la sessió.
        HttpSession session=request.getSession();
        if(session.getAttribute("user")==null){
            response.sendRedirect("login.jsp");
        } else {
            // 2. Obtenim la llista de pacients.
            List<Patient> resultList = patientsManager.listAllPatients();

            // 3. Enviem la llista resultant a la JSP 
            request.setAttribute("patientsList", resultList);
            RequestDispatcher rd = request.getRequestDispatcher("./intranet/listAllPatients.jsp");
            rd.forward(request, response);
        }
    }

    private void filterByClassification(HttpServletRequest request, HttpServletResponse response) 
             throws ServletException, IOException {
        // 1. Verifiquem la sessió.
        // Agafem les dades de la sessió.
        HttpSession session=request.getSession();
        if(session.getAttribute("user")==null){
            response.sendRedirect("login.jsp");
        } else {
            // 2. Agafem les dades del filtre de pacients.
//            String rh_form = request.getParameter("rh_form");
//            String bloodType_form = request.getParameter("bloodType_form");
            String classification = request.getParameter("classfication_filter");
            
            // Edat mínima i màxima
            String edatMinForm = request.getParameter("edatMin");
            int edatMin = edatMinForm!=null?
                    Integer.parseInt(edatMinForm):18;
            
            String edatMaxForm = request.getParameter("edatMax");
            int edatMax = edatMaxForm!=null?
                    Integer.parseInt(edatMaxForm):150;
            
            // 2.2 Validem les dades del formulari. Si ens retornen valor nul 
            // (en el nostre cas un valor -)
            boolean classificationSelected = !classification.equals("*");
            // boolean BloodSelected = !bloodType_form.equals("*");   
            
            // 3. Cridem patientsManager per a què ens retorni la llista de pacients 
            // filtrada.
            List<Patient> patientsList = patientsManager.filter(classification,edatMin,edatMax);
            
            // 4. Enviem la llista resultant a la JSP 
            session.setAttribute("patientsList", patientsList);
            // https://stackoverflow.com/questions/24905788/dispatch-request-to-jsp-page-in-specific-folder
            RequestDispatcher rd = request.getRequestDispatcher("./intranet/filterPatients.jsp");
            rd.forward(request, response);
        }
    }

  

    private void deletePatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        System.out.println("aaaaa");
        String patientIdForm = request.getParameter("patient");
        int patientId = Integer.parseInt(patientIdForm);
            
//        String[] friendParams = friend.split(";");
        Patient deletePatient 
                = new Patient(patientId);

        int rowsAffected = patientsManager.remove(deletePatient);

        if (rowsAffected > 0) {
            request.setAttribute("success", "Patient " + patientId + " DELETED ;) !");
            // Recarreguem la llista després d'esborrar un element.
            List<Patient> resultList = patientsManager.listAllPatients();
            request.setAttribute("patientsList", resultList);
        } else {
            switch (rowsAffected) {
                case -1:
                    request.setAttribute("error", "Patient not deleted due to a Constraint fail.");
                    break;
                case -2:
                    request.setAttribute("error", "Patient not deleted due to an Error, contact administrator.");
                    break;
                default:
                    response.sendRedirect("./intranet/listAllPatients.jsp");
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("./intranet/listAllPatients.jsp");
        dispatcher.forward(request, response);
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
