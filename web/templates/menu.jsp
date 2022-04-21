<nav class="navbar sticky-top navbar-dark bg-danger">
    <!-- Header amb el banner de la web -->
    <!-- 
    <a class="navbar-brand" href="index.jsp">
        <img id="banner-img" src="../img/bioinfo-banner.jpg" 
             class="d-inline-block align-middle" alt="Banner Bioinformatica">
    </a> 
    -->
    <h3>M015-PT16-SQL-PACIENTS</h3>
    <!-- Menú navegació visitants (que no estan registrats) -->
    <% if(session.getAttribute("user")==null) { %>
    <nav class="nav nav-pills flex-column flex-sm-row">
        <a class='flex-sm-fill text-sm-center nav-link opt-menu' 
           href='./news.jsp'>News</a>
        <!-- I18N -->
        <a class='flex-sm-fill text-sm-center nav-link active opt-menu' 
           href='<%= request.getContextPath() %>?locale=es'>ES</a>
        <a class='flex-sm-fill text-sm-center nav-link active opt-menu' 
           href='<%= request.getContextPath() %>?locale=en'>EN</a>
        <a class='flex-sm-fill text-sm-center nav-link active opt-menu' 
           href='./login.jsp'>Login</a>
    <% } %> 
    <!-- Menú navegació usuaris registrats Intranet. -->
    <% if(session.getAttribute("user")!=null) { %>
        <!--
        <a class='flex-sm-fill text-sm-center nav-link opt-menu' 
           href='../intranet/news.jsp'>News</a>
        -->
        <% if( (session.getAttribute("user")!=null) 
            && (session.getAttribute("role").equals("ADMIN")) ) { %>
        <!-- Menú navegació usuaris registrats Intranet com a admin. -->
            <a class='flex-sm-fill text-sm-center nav-link opt-menu' 
               href='<%= request.getContextPath() %>/patient?action=AddPatientForm'>Add</a>
            <a class='flex-sm-fill text-sm-center nav-link opt-menu' 
               href='<%= request.getContextPath() %>/user?action=AdminPage'>AdminUsers</a>
        <%      
            } else {
        %>
        <a class='flex-sm-fill text-sm-center nav-link opt-menu' 
           href='../intranet/adn.jsp'>ADNValidator</a>
        <% 
            }
        %>
        <!-- 
        <a class='flex-sm-fill text-sm-center nav-link opt-menu' 
           href='../intranet/listAllPatients.jsp'>List Patients</a> 
        -->
        <a class='flex-sm-fill text-sm-center nav-link opt-menu' 
           href='<%= request.getContextPath() %>/patient?action=ListAll'>List</a> 
        <a class='flex-sm-fill text-sm-center nav-link opt-menu' 
           href='<%= request.getContextPath() %>/intranet/filterPatients.jsp'>Filter</a>
        <a class='flex-sm-fill text-sm-center nav-link opt-menu' 
           href='<%= request.getContextPath() %>/patient?action=GraficCircularPacients'>Graph</a> 
        <!--
        <a class='flex-sm-fill text-sm-center nav-link opt-menu' 
           href='../intranet/adn-gen.jsp'>ADN Gen (Pt14Opt)</a>
        -->
        <a class='flex-sm-fill text-sm-center nav-link active opt-menu' 
           href='../user?action=Invalidate'>Logout</a>
        <%      
            } 
        %>
        
    </nav>
</nav>
