package patients;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    /**
    * Pàgina JSP on gestionem la llista de pacients.
    */
    private static final String PAGINA_LLISTA_PACIENTS = "./intranet/listAllPatients.jsp";
    private static final String PAGINA_ALTA_PACIENT 
            = "./intranet/admin/addPatient.jsp";
    private static final String PAGINA_GRAFIC_PACIENTS
            = "./intranet/grafic2.jsp";
    
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
            // Si el action es patientOptions vol dir que hem pitjat un dels següents botons des de patientList:
            // El d'editar o el d'esborar.
            String idPatientEditForm = "";
            String idPatientDeleteForm = "";
            if(action.equals("patientOptions")) {
                 idPatientEditForm=request.getParameter("patientEdit");
                 idPatientDeleteForm=request.getParameter("patientDelete");
            }
            switch(action){
                case "ListAll":
                    listAll(request,response);
                break;
                case "FILTER_BYCLASSIFICATION":
                    filterByClassification(request,response);
                break;
                case "GraficCircularPacients":
                    pieChartPatientsClassification(request,response);
                break;
                
                // NO HO NECESSITEM.
//                case "DeletePatient":
//                    //response.sendRedirect("friend.jsp?showFormDelete=1");
//                    deletePatientForm(request, response);
//                    break;
                case "AddPatientForm":
                    addPatientForm(request,response);
                break;
                case "AddPatient":
                    addPatient(request,response);
                break;
                
                case "patientOptions":
                    if(idPatientDeleteForm!=null)
                        // Selected the Patient to delete.
                        deletePatient(request, response);
                        // Selected the Patient to edit and load the Patient data in a form.
                    if(idPatientEditForm!=null)
                        patientToModify(request, response);
                    break;
                
                // 12 - Finally, the user press the submit button with the new data.
                // This operation updates the Patient data in database.  
                case "patientUpdate":
                   updatePatient(request, response);
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
            RequestDispatcher rd = request.getRequestDispatcher(PAGINA_LLISTA_PACIENTS);
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
        String patientIdForm = request.getParameter("patientDelete");
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
                    response.sendRedirect(PAGINA_LLISTA_PACIENTS);
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(PAGINA_LLISTA_PACIENTS);
        dispatcher.forward(request, response);
    }
    
    
    private void addPatientForm(HttpServletRequest request, 
            HttpServletResponse response)
           throws ServletException, IOException {

        // 1. Verifiquem la validesa de la sessió de l'usuari. 
        HttpSession session=request.getSession();
        if(session.getAttribute("user")==null) { 
            if(session.getAttribute("admin")==null){
                response.sendRedirect("login.jsp");
            }
        } else {

            // 2. Enviem un pacient buit a la JSP per a qu'e l'usuari l'ompli.
            Patient patientAdd = new Patient();
            patientAdd.getClassificationValues();
            patientAdd.setAge(40);
            patientAdd.setMenarche(15);
            patientAdd.setImc(25.0);
            patientAdd.setClassification("---");
            request.setAttribute("patientAdd", patientAdd);
            RequestDispatcher rd = request.getRequestDispatcher(PAGINA_ALTA_PACIENT);
            rd.forward(request, response);
        }
    }

    private void addPatient(HttpServletRequest request, 
            HttpServletResponse response)
           throws ServletException, IOException {

        // Pas 1. Verifiquem la validesa de la sessió de l'usuari. 
        HttpSession session=request.getSession();
        if(session.getAttribute("user")==null) { 
            if(session.getAttribute("admin")==null){
                response.sendRedirect("login.jsp");
            }
        } else {
            // Pas 2. Verifiquem la validesa de les dades introduïdes.
            boolean validFields = true;
                int edat = 0, menarquia = 0, registerId = 0;

                // Agafem el id de la categoria des d'un camp select.
                String inputClassification = request.getParameter("inputClassification");
                validFields = inputClassification!=null;

                // Camps numèrics enters.
                String inputAge = request.getParameter("inputAge");
                if(inputAge!=null)
                    edat = Integer.parseInt(inputAge);

                String inputMenarche = request.getParameter("inputMenarche");
                menarquia = Integer.parseInt(inputMenarche);

                // Camp numèric decimal.
                String inputIMC = request.getParameter("inputIMC");
                double imc = Double.parseDouble(inputIMC);

                // Pendent d'implementar el camp.
                boolean hasMenopause = edat>50?false:true;

                // Pas 2. Validem les dades del form si han estat totes inserides. 
                // Si no son correctes, informem a l'usuari.
               if(validFields = true) {
                    validFields = validFields && registerId > 1;
                    validFields = validFields && menarquia > 5 && menarquia < 25;
                    validFields = validFields && edat > 15 && edat < 150;
               }
               
            // Pas 3. Crear el objecte Pacient amb les dades vàlides.
            // int idFriend, String phone, String name, int age, int categoryId

            // Pas 3.1 Consultem llista de pacients per a saber l'id de l'últim pacient.
            List<Patient> lastPatient = patientsManager.listAllPatients();
            registerId = lastPatient.get(lastPatient.size()-1).getRegisterId();
            registerId++;
            
            Patient newPatientData = 
                new Patient(registerId, edat, 75, 170, imc, inputClassification, menarquia, hasMenopause, "NORMAL");
        
            // Pas 4. Inserim el pacient a la base de dades.
            int rowsAffected = patientsManager.insert(newPatientData);
            
            // Pas 5. Informar a l'usuari com ha anat l'inser.
            if (rowsAffected > 0) {
                request.setAttribute("success", "Patient with id " 
                        + newPatientData.getRegisterId() + " Successfully modified :) !");
            } else {
                request.setAttribute("error", "Patient not updated :( !");
            }
          
            Patient patientAdd = new Patient();
            request.setAttribute("patientAdd", patientAdd);
            RequestDispatcher rd = request.getRequestDispatcher(PAGINA_ALTA_PACIENT);
            rd.forward(request, response);
        }
    }
    
    private void patientToModify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Llegim l'id del pacient seleccionat.
        String patientIdForm = request.getParameter("patientEdit");
        int patientId = Integer.parseInt(patientIdForm);
//        Patient patientModify = new Patient(
//                Integer.parseInt(patientIdForm));
        
        // Fem select per a consultar totes les dades del pacient.
        Patient patientModify = patientsManager.findOne(patientId);
        patientModify.getClassificationValues();
        request.setAttribute("patientModify", patientModify);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(PAGINA_LLISTA_PACIENTS);
        dispatcher.forward(request, response);
    }
   
    private void updatePatient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Patient newPatientData = null;
        
        // inputRegisterId, inputIMC, inputClassification, inputMenarche, inputAge
        // Pas 1. Recollir dades del form.
        // També validarem si han estat inserides o no.
        boolean validFields = true;
        int edat = 0, menarquia = 0, registerId = 0;
        
        if (request.getParameter("inputRegisterId") != null) {
            String inputRegisterId = request.getParameter("inputRegisterId");
            registerId = Integer.parseInt(inputRegisterId);
        }
        // Agafem el id de la categoria des d'un camp select.
        String inputClassification = request.getParameter("inputClassification");
        validFields = inputClassification!=null;
        
        // Camps numèrics enters.
        String inputAge = request.getParameter("inputAge");
        if(inputAge!=null)
            edat = Integer.parseInt(inputAge);
            
        String inputMenarche = request.getParameter("inputMenarche");
        menarquia = Integer.parseInt(inputMenarche);
            
        // Camp numèric decimal.
        String inputIMC = request.getParameter("inputIMC");
        double imc = Double.parseDouble(inputIMC);
            
        // Pendent d'implementar el camp.
        boolean hasMenopause = edat>50?false:true;

        // Pas 2. Validem les dades del form si han estat totes inserides. 
        // Si no son correctes, informem a l'usuari.
       if(validFields = true) {
            validFields = validFields && registerId > 1;
            validFields = validFields && menarquia > 5 && menarquia < 25;
            validFields = validFields && edat > 15 && edat < 150;
       }
 
        // Pas 3. Crear el objecte Pacient amb les dades vàlides.
        // int idFriend, String phone, String name, int age, int categoryId
            
        newPatientData = 
           new Patient(registerId, edat, 75, 170, imc, inputClassification, menarquia, hasMenopause , "NORMAL");
            
        // Pas 4. TODO Realitzar update a la BBDD.
        int rowsAffected = patientsManager.update(newPatientData);
        
        // Pas 5. Informar a l'usuari com ha anat l'udpate.
        if (rowsAffected > 0) {
            request.setAttribute("success", "Patient with id " 
                    + newPatientData.getRegisterId() + " Successfully modified :) !");
        } else {
            request.setAttribute("error", "Error. Patient not updated :( !");
        }
          
        // Pas 6. Tornar a la jsp.
        RequestDispatcher dispatcher = request.getRequestDispatcher(PAGINA_LLISTA_PACIENTS);
        dispatcher.forward(request, response);
    }
    
    
    /**
     * Retorna a la JSP les dades necessàries per a crear un diagrama
     * circular de la classificació de casos dels pacients; quants tenen
     * una situació normal, quants tenen oestopenia i quants tenen oestoporosi.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void pieChartPatientsClassification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Llegim els pacients de la base de dades
        List<Patient> lastPatient = patientsManager.listAllPatients();
        
        // Ara, creem un mapa que presenti les dades que necessita pel gràifc.
        Map<String,Double> patientsClasssificationPieChart 
                = patientsClasssificationPieChart(lastPatient);
               
//        // Li podem enviar la llista de pacients com a Atribut.
        request.setAttribute("patientsMap", patientsClasssificationPieChart);
        
        // Hem usat la llibreria JFreeChart per aconseguir-ho, està aplicada a la JSP.
        RequestDispatcher rd = request.getRequestDispatcher(PAGINA_GRAFIC_PACIENTS);
        rd.forward(request, response);
    }
    
    
    /**
     * Fa un recompte de la classifiació del número 
     * de pacients. És a dir; quants estan normal, quants tenen osteopenia 
     * i quants osteoporosi
     * @return a Map with
     */
    private Map<String,Double> patientsClasssificationPieChart(List<Patient> listPatients) {
        Map<String,Double> result 
                = new HashMap<String,Double>();
        
        // The result should be like this:
//        result.put("Normal",6.0);
//        result.put("Oestopenia",3.0);
//        result.put("Oestoporosi",2.0);

        // Steps
        
        // 1. Define names and counters for each patient classification.
        // The solution is not generic, but in this case, 
        // with only 3 categories it's OK
        double cat1NumNormal = 0.0;
        double cat2NumOestopenia = 0.0;
        double cat3NumOestoporosi = 0.0;
        
        // Aux String with the class. of the patient in the loop.
        String selectedPatientClassification = "";
        
        // 2. Now we loop all the patients and count 
        // the number of patients in each cateogory. 
        for(Patient pat: listPatients) {
            selectedPatientClassification = pat.getClassification();
            switch (selectedPatientClassification) {
                case "NORMAL":
                    cat1NumNormal++;
                    break;
                case "OSTEOPENIA":
                    cat2NumOestopenia++;
                    break;
                case "OSTEOPOROSI":
                    cat3NumOestoporosi++;
                    break;
            }
        }
        
        // 3. At last, we put the results in the Map
        result.put("Normal",cat1NumNormal);
        result.put("Oestopenia",cat2NumOestopenia);
        result.put("Oestoporosi",cat3NumOestoporosi);        
        return result;
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
