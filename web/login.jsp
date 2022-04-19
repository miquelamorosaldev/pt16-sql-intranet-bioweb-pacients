<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Internacionalizacion, i18n
http://chuwiki.chuidiang.org/index.php?title=Internacionalizaci%C3%B3n_de_JSP_con_JSTL
Els missatges estan a WEB-INF/classes
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>M015-PT16-PACIENTS-BASEDADES-SQL</title>
        <link rel="stylesheet" href="vendors/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <script src="vendors/jquery/jquery-3.3.1.min.js"></script>
        <link rel="stylesheet" href="./css/styles.css">
    </head>
    <body>
        <header>
            <%@include file="templates/menu.jsp" %>
            <%@include file="userValidation.jsp" %>
        </header>
        <main class="container-fluid h-100">
            <fmt:setLocale value="${param.locale}" scope="session" />
            <fmt:setBundle basename="Messages2" />
                <!-- <h3>LOGIN</h3> -->
                <!-- 
                <p>Benvingut al nostre portal de bioinformàtica.</p>
                <p>On podràs trobar notícies, si estàs registrat, molt més.</p> 
                -->
                <!-- 
                RWD Form: 
                https://www.codeply.com/go/OWsGW3mxS2/bootstrap-4-full_screen-center-form
                -->
                <div class="row text-center justify-content-center align-items-center h-100">
                    <form method="post" class="form-signin" action="user">
                        <h3><fmt:message key="title" /></h3>
                        <img class="mb-4" src="./img/icon-bone.png" alt="icona d'un os trencant-se, oestoporosi" width="72" height="72">
                        <div class="form-group">
                            <label for="username">Username:</label> 
                            <input type="text" name="username" class="form-control form-control-lg" required/>
                        </div>
                        <div class="form-group">
                            <label for="password">Password:</label>
                            <input type="password" name="password" class="form-control form-control-lg" required/>
                        </div>    
                        <div class="form-group">
                            <button class="login-submit btn-lg" 
                                 accesskey="" type="submit" name="action" value="Validate">Log In</button>     
                        </div>
                    </form>
                </div>
                <%
                   if(request.getParameter("error")!=null){
                      //out.println("Usuario y/o contraseña incorrectas");
                       String error=request.getParameter("error");
                       
                       if(error.equals("1")) out.println("<p class='alert alert-danger'>Usuari y/o contrassenya incorrectes.</p>");
                       if(error.equals("2")) out.println("<p class='alert alert-danger'>Número de intents excedits.</p>");
                   } 
                %>
        </main>
        <footer>
            <%@include file="templates/footer.jsp" %>
        </footer>
    </body>
</html>
