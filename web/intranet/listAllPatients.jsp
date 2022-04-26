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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
        <script type="text/javascript">
            var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
            var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
              return new bootstrap.Tooltip(tooltipTriggerEl)
            })
        </script>
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
                    <c:if test="${requestScope.success != null}">
                        <p class='alert alert-success'>${requestScope.success}</p>
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
                                                        onclick="return confirm('Are you sure you want to Delete this Patient? ');" >
                                                    <i class="fa fa-trash"></i>
                                                </button>
                                                <input type="hidden" name="action" value="patientOptions"/>
                                                <!--Edit-->
                                                <button class="btn btn-primary" type="submit" value="${patient.registerId}" 
                                                        name="patientEdit" id="patientEdit">
                                                     <i class="fa fa-pencil" rel="tooltip"></i>
                                                </button>
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
                        <form action="patient" method="POST">
                            <div class="row justify-content-center">
                                <div class="form-group col-md-6 col-sm-6 col-sm-12">
                                    <label for="inputRegisterId">ID</label>
                                    <input type="text" class="form-control" readonly="true" id="inputRegisterId" 
                                           value="${patientModify.registerId}" name="inputRegisterId" required>
                                </div>
                                <div class="form-group col-md-6 col-sm-6 col-sm-12">
                                    <label for="inputAge">Edat</label>
                                    <input type="text" class="form-control" id="inputAge" min="15" max="150" 
                                           value="${patientModify.age}" name="inputAge" required>
                                </div>
                                <div class="form-group col-md-6 col-sm-6 col-sm-12">
                                    <label for="inputMenarche">Menarquia</label>
                                    <i id="info-menarquia"  class="fa fa-info-circle" rel="tooltip" 
                                       title="La menarquia és l'edat en la que la pacient va tenir la primera menstruació." ></i>
                                    <input type="text" class="form-control" id="inputMenarche" min="5" max="20" 
                                           value="${patientModify.menarche}" name="inputMenarche" required>
                                </div>
                            </div>
                            <div class="row justify-content-center">
                                <div class="form-group col-md-6 col-sm-6 col-sm-12">
                                    <label for="inputClassification">
                                        Classficació resultats estudi.
                                    </label>
                                    <!-- https://metamug.com/article/jsp/jsp-select-option-list-index.html -->
                                    <select class="form-control" name="inputClassification"> 
                                         <c:forEach var="item" items="${patientModify.classificationValues}" >
                                            <option value='${item.key}'
                                                ${item.value == patientModify.classification ? 'selected="selected"' : ' ' }>
                                                ${item.value}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-md-6 col-sm-6 col-sm-12">
                                   <label for="inputIMC">IMC</label>
                                   <a class="small" href="https://medlineplus.gov/spanish/ency/article/007196.htm" target="_blank">Què és l'IMC?</a>
                                    <input type="text" class="form-control" id="inputIMC" 
                                           value="${patientModify.imc}" name="inputIMC" required>
                                </div>
                                <div class="form-group col">
                                   <input type="checkbox" name="inputMenopause" ${patientModify.menopause=='NO'?'':'checked'} class="form-check-input" id="menopause">
                                   <label class="form-check-label" for="inputMenopause">Té menopausia</label>
                                </div>
                            </div>
                            <button type="submit" class="btn btn-primary" 
                                    name="action" value="patientUpdate" id="patientUpdate">Modify</button>
                            <button type="submit" class="btn btn-danger" name="action" value="ListAll" id="patientModifyCancel">Cancel</button>
                        </form>
                    </c:if>
            </div>
        </main>
        <footer>
            <%@include file="../../templates/footer.jsp" %>
        </footer>
    </body>
</html>
