<%@page import="adnutils.*" %>
<%@page import="patients.model.IPatientsDAO" %>
<%@page import="patients.model.PatientsMemoryDAO" %>
<%@page import="patients.model.Patient" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PT151-21-PatientsBioInformatics - ADD PATIENT</title>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/vendors/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <script src="<%= request.getContextPath() %>/vendors/jquery/jquery-3.3.1.min.js"></script>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
    </head>
    <body>
        <header>
            <!-- Ull amb les rutes. En aquest cas �s ../.. (carpeta anterior) /templates -->
            <%@include file="../../templates/menu.jsp" %>
        </header>
        <main>
            <div class="container mw-100"> <!-- comment -->
                <h3>Afegir pacient</h3>
                <!-- RWD Form -->
                <!-- https://getbootstrap.com/docs/4.1/components/forms/ -->
                <form method="post" action="page3-addPatient.jsp">
                        <form action="patientController" method="POST">
                            <div class="row justify-content-center">
                                <div class="form-group col">
                                    <label for="inputRegisterId">ID</label>
                                    <input type="text" class="form-control" readonly="true" id="inputRegisterId" 
                                           value="${patientAdd.registerId}" name="inputRegisterId" required>
                                </div>
                                <div class="form-group col">
                                    <label for="inputAge">Edat</label>
                                    <input type="number" class="form-control" id="inputAge" max="150" min="10"
                                           value="${patientAdd.age}" name="inputAge" required>
                                </div>
                                <div class="form-group col">
                                    <label for="inputAge">Menarquia</label>
                                    <i id="info-menarquia"  class="fa fa-info-circle" rel="tooltip" 
                                       title="La menarquia �s l'edat en la que la pacient va tenir la primera menstruaci�." ></i>
                                    <input type="number" class="form-control" id="inputMenarche" min="5" max="20" 
                                           value="${patientAdd.menarche}" name="menarche" required>
                                </div>
                            </div>
                            <div class="row justify-content-center">
                                <div class="form-group col">
                                    <label for="inputClassification">
                                        Classficaci� resultats estudi.
                                    </label>
                                    <!-- https://metamug.com/article/jsp/jsp-select-option-list-index.html -->
                                    <select class="form-control" name="classificationValues"> 
                                         <c:forEach var="item" items="${patientAdd.classificationValues}" >
                                            <option value='${item.key}'
                                                ${item.value == patientAdd.classification ? 'selected="selected"' : ' ' }>
                                                ${item.value}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col">
                                   <label for="inputIMC">IMC</label>
                                   <a class="small" href="https://medlineplus.gov/spanish/ency/article/007196.htm" target="_blank">Qu� �s l'IMC?</a>
                                    <input type="text" class="form-control" id="imc" 
                                           value="${patientAdd.imc}" name="imc" required>
                                </div>
                            </div>
                            <button type="submit" class="btn btn-primary" 
                                    name="action" value="patientAdd" id="patientAdd">Afegir</button>
                        </form>
                </form> 
            </div>
        </main>
        <footer>
            <%@include file="../../templates/footer.jsp" %>
        </footer>
    </body>
</html>
