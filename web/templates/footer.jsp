<p class="mt-5 mb-3">
    Institut Provençana CC-BY-SA. 
    2021-2022.
    <BR/>
    <strong>OPCIONS: </strong>
    <% if(session.getAttribute("user")==null) { %>
        <a href='./news.jsp'>News</a>
        <!-- I18N -->
        <a href='<%= request.getContextPath() %>?locale=es'>ES</a>
        <a href='<%= request.getContextPath() %>?locale=en'>EN</a>
        <a href='./login.jsp'>Login</a>
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
            <a href='<%= request.getContextPath() %>/patient?action=AddPatientForm'>Add Patient</a>
            <a href='<%= request.getContextPath() %>/user?action=AdminPage'>List Users</a>
        <%      
            } else {
        %>
        <a href='../intranet/adn.jsp'>ADNValidator</a>
        <% 
            }
        %>
        <!-- 
        <a class='flex-sm-fill text-sm-center nav-link opt-menu' 
           href='../intranet/listAllPatients.jsp'>List Patients</a> 
        -->
        <a href='<%= request.getContextPath() %>/patient?action=ListAll'>List Patients</a> 
        <a href='<%= request.getContextPath() %>/intranet/filterPatients.jsp'>Filter Patients</a>
        <!--
        <a class='flex-sm-fill text-sm-center nav-link opt-menu' 
           href='../intranet/adn-gen.jsp'>ADN Gen (Pt14Opt)</a>
        -->
        <a href='../user?action=Invalidate'>Logout</a>
        <%      
            } 
        %>
    <BR/>
    <a href="https://github.com/miquelamorosaldev/pt16-sql-intranet-bioweb-pacients" class="opt-menu"> Codi Font Projecte M015-UF1-PT16.</a>
</p>