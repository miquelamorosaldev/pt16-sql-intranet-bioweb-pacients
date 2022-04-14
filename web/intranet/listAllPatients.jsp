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
                
                    <%-- requestScope, Per a llegir  request.getAttribute dels servlets --%>
                    <c:if test="${requestScope.sucess != null}">
                        <p class='alert alert-success'>${requestScope.sucess}</p>
                    </c:if>
                    <c:if test="${requestScope.error != null}">
                        <p class='alert alert-warning'>${requestScope.error}</p>
                    </c:if>
                    <!-- List All Friends  -->
                    <c:if test="${patientsList != null}">
                        <form action="patient" method="POST">
                            <table class="table">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">Patient ID</th>
                                        <th scope="col">Age</th>
                                        <th scope="col">AgeGroup</th>
                                        <th scope="col">IMC</th>
                                        <th scope="col">Classificació</th>
                                        <th scope="col">Options</th>
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
                                            <td scope="col">
                                                <!--Delete-->
                                                <button class="btn btn-danger" type="submit" 
                                                        value="${patient.registerId}" id="patientDelete" name="patientDelete" 
                                                        onclick="return confirm('Are you sure you want to Delete this Patient? ');" >Supr</button>
                                                <input type="hidden" name="action" value="patientOptions"/>
                                                <!--Edit-->
                                                <button class="btn btn-primary" type="submit" value="${patient.registerId}" 
                                                        name="patientEdit" id="patientEdit">Edit</button>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                </tbody>
                            </table>
                        </form> 
                        <p class="alert alert-success">${patientsList.size()} pacientes encontrados.</p>
                    </c:if>
                    <!-- Modify Patient -->
                    <c:if test="${patientModify != null}">
                        <form action="patientController" method="POST">
                            <div class="form-group">
                                <label for="inputCat">ID</label>
                                <input type="text" class="form-control" readonly="true" id="inputRegisterId" value="${patientModify.registerId}" name="classification" required>
                            </div>
                                <input type="hidden" value="${patientModify.registerId}" name="registerId" />
                            <button type="submit" class="btn btn-primary" name="actionCategory" value="patientModify">Modify</button>
                        </form>
                    </c:if>
            </div>
        </main>
        <footer>
            <%@include file="../../templates/footer.jsp" %>
        </footer>
    </body>
</html>
