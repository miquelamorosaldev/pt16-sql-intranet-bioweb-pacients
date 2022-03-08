<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="adnutils.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Demo JSP Templates - Page 2</title>
        <link rel="stylesheet" href="../vendors/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <script src="../vendors/jquery/jquery-3.3.1.min.js"></script>
        <link rel="stylesheet" href="../css/styles.css">
    </head>
    <body>
        <header>
            <!-- Ull amb les rutes. En aquest cas és .. (carpeta anterior) /templates -->
            <%@include file="../templates/menu.jsp" %>
        </header>
        <main>
            <!-- Main Content -->
            <div class="container mw-100"> 
                <h3>PT14 - ESPAI APARTAT OPCIONAL</h3>
                <p>Generador cadenes ADN aleatories.</p>
                <!-- RWD Form -->
                <div class="form-row">
                    <form method="post" action="page2-adn.jsp">
                        <div class="form-group col-md-6">
                            <div class="form-group col-md-6">
                                <label for='name'>Núm. caràcters cada tros de cadena ADN.</label>
                                <input class="form-control" type="number" required id='numcaracters' name="numcaracters"
                                    placeholder='4'></input>
                            </div>
                            <div class="form-group col-md-6">
                                <label for='name'>Núm trossos cadena.</label>
                                <input class="form-control" type="number" required id='numtrossos' name="numtrossos"
                                    placeholder='4'></input>
                            </div>
                        <div class="form-group col-md-6">
                            <input type="submit" name="ok" value="Calcular"/>
                        </div>
                    </form> 
                </div>
        
        <%
            // Si ha clicat o no al botó del formulari
           if(request.getParameter("ok")!=null) {
 
              ADN_Manager adnManager = new ADN_Manager();
              boolean result = false;
              
              // Si camps són vàlids, retorna resultat.
              if(result) {
                
              } else {
                  // Si no són vàlid, retorna missatge.
                  out.println("<p class='error'> "
                          + "Camps no vàlids. </p>");
              }
           }
        %>
            </div>
        </main>
        <footer>
            <%@include file="../templates/footer.jsp" %>
        </footer>
    </body>
</html>
