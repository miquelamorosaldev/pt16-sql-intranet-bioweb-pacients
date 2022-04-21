<%@page import="java.text.DecimalFormat"%>
<%@page import="org.jfree.chart.labels.StandardPieSectionLabelGenerator"%>
<%@page import="org.jfree.chart.labels.PieSectionLabelGenerator"%>
<%@page import="org.jfree.chart.plot.PiePlot"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="patients.model.Patient" %>
<%@page import="java.util.*" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.io.*" %>
<%@ page import="org.jfree.chart.*" %>
<%@ page import="org.jfree.chart.entity.*" %>
<%@ page import="org.jfree.data.general.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--VENDORS-->
        <link rel="stylesheet" href="<%= request.getContextPath() %>/vendors/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <script src="<%= request.getContextPath() %>/vendors/jquery/jquery-3.3.1.min.js"></script>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
        <title>PORTAL PT16 - PATIENTS FILTER</title>
    </head>
    <body>
        <header>
            <!-- Ull amb les rutes. En aquest cas és .. (carpeta anterior) /templates -->
            <%@include file="../templates/menu.jsp" %>
            <%@include file="./userValidation.jsp" %>
        </header>
        <%
            Map<String,Double> patientsMap = new HashMap<String,Double>();
            if(session.getAttribute("user")==null){
                response.sendRedirect("login.jsp");
            } else {
                // Carrega la llista de pacients si està buida.
                if(request.getAttribute("patientsMap")!=null) {
                    patientsMap = (Map<String,Double>) request.getAttribute("patientsMap"); 
                } 
            }
            
            // Example, obtained from:
            // https://stackoverflow.com/questions/24624903/how-to-bring-the-jfreechart-in-jsp
            final DefaultPieDataset data = new DefaultPieDataset();
            
            data.setValue("Normal", patientsMap.get("Normal"));
            data.setValue("Oestopenia", patientsMap.get("Oestopenia"));
            data.setValue("Oestoporosi", patientsMap.get("Oestoporosi"));

            // How to show absolute values; values and percents.
            // https://stackoverflow.com/questions/17501750/jfreechart-customize-piechart-to-show-absolute-values-and-percentages
            JFreeChart chart = ChartFactory.
                    createPieChart ("Classificació dels pacients de l'estudi", data, true, true, false);
            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setSectionPaint("K1", Color.green);
            plot.setSectionPaint("K2", Color.yellow);
            plot.setSectionPaint("K3", Color.red);
            // plot.setExplodePercent("K1", 0.10);
            plot.setSimpleLabels(true);

            PieSectionLabelGenerator gen = 
                    new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", 
                    new DecimalFormat("0"), 
                    new DecimalFormat("0%"));
            plot.setLabelGenerator(gen);
            
            try {
               final ChartRenderingInfo info = new 
                ChartRenderingInfo(new StandardEntityCollection());
               final File file1 = new 
                   File(getServletContext().getRealPath(".") + "/piechart-classification.png");
               ChartUtilities.saveChartAsPNG(file1, chart, 600, 400, info);
            } catch (Exception e) {
               out.println(e);
            }
        %>
        
        <main>
            <h3>Gràfics resultants de l'estudi.</h3>
            <!-- Crec que per a què sigui 100% accessible cal mostrar els resultats del gràfic o bé
            mostrar a la web els resultats. -->
            <%
                for (Map.Entry<String, Double> entry : patientsMap.entrySet()) {
            %>
            <dl>
                <dt><%=entry.getKey() %></dt>
                <dd><%=entry.getValue() %></dd>
            </dl>
            <% } %>
            <img SRC="piechart-classification.png" width="600" HEIGHT="400" 
                USEMAP="#chart" accesskey="" 
                alt="Gràfic circular del número de pacients classificats 
            en les següents categories: NORMAL, OESTOPENIA, OESTOPOROSI">
        </main>
        <footer>
               Institut Provençana 2020-2021, CC-BY.
        </footer>
    </body>
</html>