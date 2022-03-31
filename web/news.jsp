<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Demo JSP Templates - Page 2</title>
        <link rel="stylesheet" href="vendors/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <script src="vendors/jquery/jquery-3.3.1.min.js"></script>
        <link rel="stylesheet" href="css/styles.css">
    </head>
    <body>
        <header>
            <%@include file="templates/menu.jsp" %>
            <%@include file="userValidation.jsp" %>
        </header>
        <main>
            <!-- Main Content -->
            <div class="container mw-100">
                <h3>Notícies bioinformàtica.</h3>
                <div class="row">
                    <div class="col-md-6 col-sm-12">
                        Un sistema de reanálisis bioinformático aplicado a estudios genéticos aumenta el porcentaje de diagnóstico de enfermedad<br/>
                        <em>01/02/2022.</em><strong>Link:</strong><a href="https://www.infosalus.com/salud-investigacion/noticia-sistema-reanalisis-bioinformatico-aplicado-estudios-geneticos-aumenta-porcentaje-diagnostico-enfermedad-20220201114618.html">
                            Infosalus; feb 2022
                        </a>
                    </div>
                    <div class="col-md-6 col-sm-12">
                        Crean una herramienta bioinformática para analizar las variantes genéticas 
                        del coronavirus<br/>
                        <em>18/07/2022.</em><strong>Link:</strong><a href="https://www.eldiario.es/sociedad/crean-herramienta-bioinformatica-analizar-variantes-geneticas-coronavirus_1_8286727.html">
                            diario.es; set 2021
                        </a>
                    </div>
                    <div class="col-md-6 col-sm-12">
                        Científicos reclaman en la revista 'Nature' integrar genética, bioinformática y 
                        salud pública para evitar pandemias<br/>
                        <em>22/03/2021.</em><strong>Link:</strong><a href="https://www.infosalus.com/salud-investigacion/noticia-cientificos-reclaman-revista-nature-integrar-genetica-bioinformatica-salud-publica-evitar-pandemias-20210304154828.html">
                            Revista Nature; mar 2021
                        </a>
                    </div>
                </div>
            </div>
        </main>
        <footer>
            <%@include file="templates/footer.jsp" %>
        </footer>
    </body>
</html>
