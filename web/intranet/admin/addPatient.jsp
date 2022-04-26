<%@page import="adnutils.*" %>
<%@page import="patients.model.IPatientsDAO" %>
<%@page import="patients.model.PatientsSQLDAO" %>
<%@page import="patients.model.Patient" %>
<%@page import="java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PT151-21-PatientsBioInformatics - ADD PATIENT</title>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/vendors/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <script src="<%= request.getContextPath() %>/vendors/jquery/jquery-3.3.1.min.js"></script>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
        <script type="text/javascript">
            var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
            var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
              return new bootstrap.Tooltip(tooltipTriggerEl)
            })
        </script>
    </head>
    <body>
        <header>
            <!-- Ull amb les rutes. En aquest cas és ../.. (carpeta anterior) /templates -->
            <%@include file="../../templates/menu.jsp" %>
        </header>
        <main>
            <div class="container mw-100"> <!-- comment -->
                <h3>Afegir pacient</h3>
                <!-- RWD Form -->
                <!-- https://getbootstrap.com/docs/4.1/components/forms/ -->
                    <%-- requestScope, Per a llegir  request.getAttribute dels servlets --%>
                    <c:if test="${requestScope.success != null}">
                        <p class='alert alert-success'>${requestScope.success}</p>
                    </c:if>
                    <c:if test="${requestScope.error != null}">
                        <p class='alert alert-warning'>${requestScope.error}</p>
                    </c:if>
                    <form id="patientForm" name="patientForm" action="patient" method="POST">
                            <div class="row justify-content-center">
                                <!-- 
                                L'id del pacient es generarà posteriorment.
                                -->
                                <div class="form-group col-md-6 col-sm-6 col-xs-12">
                                    <label for="inputAge">Edat</label>
                                    <input type="number" class="form-control" id="inputAge" max="150" min="30"
                                           value="${patientAdd.age}" name="inputAge" required>
                                </div>
                                <div class="form-group col-md-6 col-sm-6 col-xs-12">
                                    <label for="inputMenarche">Menarquia</label>
                                    <i id="info-menarquia"  class="fa fa-info-circle" rel="tooltip" 
                                       title="La menarquia és l'edat en la que la pacient va tenir la primera menstruació." ></i>
                                    <input type="number" class="form-control" id="inputMenarche" min="5" max="20" 
                                           value="${patientAdd.menarche}" name="inputMenarche" required>
                                </div>
                            </div>
                            <div class="row justify-content-center">
                                <div class="form-group col-md-6 col-sm-6 col-sm-12">
                                    <label for="inputClassification">
                                        Classficació resultats estudi.
                                    </label>
                                    <!-- https://metamug.com/article/jsp/jsp-select-option-list-index.html -->
                                    <select class="form-control" name="inputClassification"> 
                                         <c:forEach var="item" items="${patientAdd.classificationValues}" >
                                            <option value='${item.key}'>
                                                ${item.value}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-md-6 col-sm-6 col-sm-12">
                                   <label for="inputIMC">IMC</label>
                                   <a class="small" href="https://medlineplus.gov/spanish/ency/article/007196.htm" target="_blank">Què és l'IMC?</a>
                                    <input type="text" class="form-control" id="inputIMC" 
                                           value="${patientAdd.imc}" name="inputIMC" required>
                                </div>
                            </div>
                            <button type="submit" class="btn btn-primary" 
                                    name="action" value="AddPatient" id="AddPatient">Afegir</button>
                   </form>
            </div>
        </main>
        <footer>
            <%@include file="../../templates/footer.jsp" %>
        </footer>
    </body>
</html>
