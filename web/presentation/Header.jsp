<%@include file="/presentation/Model.jsp" %>
<%@page import="javax.servlet.ServletException"%>
<header>
    <div class="columna">
         <div class="logo">
           <a href="/BancoUNA/presentation/Index.jsp">
               <img src="/BancoUNA/images/logo.png">
            </a>
         </div> 
    </div>
    
    <div class="columnadoble">
    </div>
     <%if(cliente==null){ %>
        <div class="columna">
        <a href="/BancoUNA/presentation/login/show">
        <img id="online" src="/BancoUNA/images/logoenlinea.png">
        </a>
       </div>
     <%}%>
    
</header>            
