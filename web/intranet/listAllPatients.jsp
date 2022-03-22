<%@page import="patients.model.IPatientsDAO" %>
<%@page import="patients.model.PatientsMemoryDAO" %>
<%@page import="patients.model.Patient" %>
<%@page import="patients.model.PatientsSQLDAO" %>
<%@page import="java.util.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PT151-21-PatientsBioInformatics-List-Patients</title>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/vendors/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <script src="<%= request.getContextPath() %>/vendors/jquery/jquery-3.3.1.min.js"></script>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
    </head>
    <body>
        <header>
            <!-- Ull amb les rutes. En aquest cas �s .. (carpeta anterior) /templates -->
            <%@include file="../templates/menu.jsp" %>
            <%@include file="./userValidation.jsp" %>
        </header>
        <main>
            <div class="container mw-100"> 
                <h3>List Patients</h3>
                    <ul>
                    <% 
                        // PatientsMemoryDAO daoPatients = new PatientsMemoryDAO();
                        // PatientsSQLDAO
                        // Inyectamos el DAO de la fuente que preferimos.
                        String path = getServletContext().getRealPath("/WEB-INF");
                        IPatientsDAO daoPatients = new PatientsSQLDAO(path);
                        
                        List<Patient> resultList = daoPatients.listAllPatients();
                        for(Patient pa: resultList) {
                    %>
                    <li><%=pa.toString()%></li>
                    <% 
                        }
                    %>
                    </ul>
                    <p><%=resultList.size()%> pacientes encontrados.</p>
            </div>
        </main>
        <footer>
            <%@include file="../../templates/footer.jsp" %>
        </footer>
    </body>
</html>
