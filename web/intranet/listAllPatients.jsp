<%@page import="patients.model.IPatientsDAO" %>
<%@page import="patients.model.PatientsMemoryDAO" %>
<%@page import="patients.model.Patient" %>
<%@page import="patients.model.PatientsSQLDAO" %>
<%@page import="java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
            <!-- Ull amb les rutes. En aquest cas és .. (carpeta anterior) /templates -->
            <%@include file="../templates/menu.jsp" %>
            <%@include file="./userValidation.jsp" %>
        </header>
        <main>
            <div class="container mw-100"> 
                <h3>List Patients</h3>
                    <ul>
                    <!-- List All Friends  -->
                    <c:if test="${patientsList != null}">
                        <!-- <form action="patientController" method="POST"> -->
                            <table class="table">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">Patient ID</th>
                                        <th scope="col">Age</th>
                                        <th scope="col">AgeGroup</th>
                                        <th scope="col">IMC</th>
                                        <th scope="col">Classificació</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${patientsList}" var="patient">
                                        <tr>
                                            <td scope="row">${patient.registerId}</td>
                                            <td>${patient.age}</td>
                                            <td>${patient.ageGroup}</td>
                                            <td>${patient.imc}</td>
                                            <td>${patient.classification}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        <!-- </form>  -->
                    </c:if>
                    <p>${patientsList.size()} pacientes encontrados.</p>
            </div>
        </main>
        <footer>
            <%@include file="../../templates/footer.jsp" %>
        </footer>
    </body>
</html>
