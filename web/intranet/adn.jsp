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
            <%@include file="./userValidation.jsp" %>
            <% 
                // Obtenció ADN del form.
                String ADN_field = request.getParameter("ADN_field");
                // Si hem obtingut un valor d'ADN de la request 
                // l'omplim, sinó posem la variable en blanc 
                // (així evitem NullPExc)
                ADN_field=ADN_field!=null?ADN_field:"";
            %>
        </header>
        <main>
            <!-- Main Content -->
            <div class="container mw-100"> 
                <h3>VALIDACIÓ FORMAT ADN.</h3>
                <!-- RWD Form -->
                <div class="form-row">
                    <form method="post" action="adn.jsp">
                        <div class="form-group col-md-6">
                            
                            <label for='ADN_field'>ADN (sense punts, ni separadors) <strong>Exemple:</strong> AGCTAGC</label>
                            <textarea id='ADN_field' name="ADN_field" rows="15" cols="50" 
                                placeholder='Exemple: AGCTAGC'><%=ADN_field %></textarea>
                        </div>
                        <div class="form-group col-md-6">
                            <input type="submit" name="ok" value="Calcular"/>
                        </div>
                    </form> 
                </div>
        
        <%
            // Si ha clicat o no al botó del formulari
           if(request.getParameter("ok")!=null) {
              // Obtenció ADN del form.
              ADN_field = request.getParameter("ADN_field");    
              out.println(ADN_field);
              // Valida l'ADN 
              ADN_Manager adnManager = new ADN_Manager();
              boolean result = adnManager.validaADN(ADN_field);
              out.println(result);
              // Si es vàlid, retorna resultat.
              if(result) {
                int a = adnManager.numAdenines(ADN_field);
                int c = adnManager.numCitosines(ADN_field);
                int g = adnManager.numGuanines(ADN_field);
                int t = adnManager.numTimines(ADN_field);

  //              request.setParameter("resultat","---" + ADN_field + "---");
  //              request.getRequestDispatcher("index.jsp").forward(request, response); 

                // Mostrar resultats.
                StringBuilder sb = new StringBuilder();

                sb.append("<dl>");
                sb.append("<dt>Num A</dt><dd>");
                sb.append(a);
                sb.append("<dt>Num C</dt><dd>");
                sb.append(c);
                sb.append("<dt>Num T</dt><dd>");
                sb.append(t);
                sb.append("<dt>Num G</dt><dd>");
                sb.append(g);
                sb.append("</dl>");
                out.println(sb.toString());
                
              } else {
                  // Si no es vàlid, retorna missatge.
                  out.println("<p class='error'> "
                          + "L'ADN NO ÉS VALID, NOMÉS POT TENIR A,G,C,T </p>");
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
