
<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="banca.logic.Moneda"%>
<%@page import="banca.logic.Cuenta"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="banca.presentation.Admin.deposito.Model"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/presentation/Head.jsp" %>
        <title>Login</title>
    </head>
    <header>
        <div class="columna">
            <div class="logo">
                <a href="/BancoUNA/presentation/Index.jsp">
                    <img src="/BancoUNA/images/logo.png">
                </a>
            </div> 
        </div>
        <div class="hr"></div>
    </header>
    <%@include file="/presentation/Toolbar.jsp" %>
    <br>
    <% Model model = (Model) request.getAttribute("model"); %>
    <% Map<String, String> errores = (Map<String, String>) request.getAttribute("errores"); %>
    <% Map<String, String[]> form = (errores == null) ? this.getForm(model) : request.getParameterMap();%>

 <div class="EspacioLogin"></div>
<div class="EspacioLogin" id="loginP">
     <div class="FormT">
     <div class="fila encabezado"><b><p>Menu Deposito</b></p></div>
        <%if(model.getCliente().getCuentas().isEmpty() && !model.getCuenta().isEstado()){%>
      <form action="/BancoUNA/presentation/admin/deposito/show" method="post">
        <div class="fila">
                        <div class="etiqueta">Cedula Cliente</div>
                        <input id="chekCed" type="checkbox"  onchange="document.getElementById('Cedula_C').disabled = !this.checked,
                                        document.getElementById('Numero_C').value = '',
                                        document.getElementById('Cedula_C').value = '',
                                        document.getElementById('Numero_C').disabled = this.checked,
                                        document.getElementById('chekNum').checked = !this.checked;"
                                        checked />
                       <input type="text" placeholder="102340567" name="Cedula_C" id="Cedula_C"
                               onclick="document.getElementById('ouput1').value ='' "
                               class="<%=erroneo("Cedula_C", errores)%>"
                               title="<%=title("Cedula_C", errores)%>"
                              value="<%=title("Cedula_C2", errores)%>" 
                      required>
                  <%if(!title("Cedula_C2", errores).isEmpty()){%>
                  <output id="ouput1">Cliente no existe</output>
                  <%}%>
        </div>
         <br>
       <div class="fila">
                        <div class="etiqueta">Numero De Cuenta</div>
                        <input id="chekNum" type="checkbox" onchange="document.getElementById('Numero_C').disabled = !this.checked,
                                        document.getElementById('Numero_C').value = '', 
                                         document.getElementById('Cedula_C').value = '',
                                        document.getElementById('Cedula_C').disabled = this.checked,
                                        document.getElementById('chekCed').checked = !this.checked;" 
                                         />
                        <input type="text" placeholder="numero" name="Numero_C" id="Numero_C" disabled
                               onclick="document.getElementById('ouput2').value ='' "
                               class="<%=erroneo("Numero_C", errores)%>"
                               title="<%=title("Numero_C", errores)%>"
                               value="<%=title("Numero_C2", errores)%>" 
                               required>
                   <%if(!title("Numero_C2", errores).isEmpty()){%>
                  <output id="ouput2">Cuenta no existe</output>
                  <%}%>               
         </div>
       <br>
       <div class="fila encabezado"><button    id="Butonbuscar"  style="margin-bottom: 15px">Buscar</button> </div>
 </form>
<%}else if(!model.isListo()){%>      
   <form action="/BancoUNA/presentation/admin/deposito/add" method="post">
     
     <%if(!model.getCliente().getCuentas().isEmpty()){%>                      
     <div class="fila">
              <div class="etiqueta"> Cuenta destino :
                  <select name="Numero_C" id="Numero_C" required>
                           <%for(Cuenta f:model.getCliente().getCuentas()){%>
                                <option  value="<%=f.getNumero()%>"><%=f.toStringFavorita()%> </option>
                            <%}%>          
                   </select>
               </div>               
          </div>
          <br>
      <%}else if(model.getCuenta().isEstado()){%>
         <div class="fila">
              <div class="etiqueta"> Cuenta :
                  <select name="Numero_C" id="Numero_C" required>
                       <option  value="<%=model.getCuenta().getNumero()%>"><%=model.getCuenta().toStringFavorita()%></option>
                  </select>
               </div>               
          </div>
          <br>
      <%}%>
                    <div class="fila">
                        <div class="etiqueta">Monto</div>
                        <div class="campo"><input  placeholder="Monto" type="text" name="Monto_D" 
                                                    class="<%=erroneo("Monto_D", errores)%>"
                                                    title="<%=title("Monto_D", errores)%>"
                                                    value="<%=form.get("Monto_D")[0]%>"
                                                    required>
                        </div>
                   <select name="Moneda_C" id="Moneda_C" required>
                           <%for(Moneda m:model.getMonedas()){%>
                                <option  value="<%=m.getTipo_cambio()%>"><%=m.getId()%> </option>
                            <%}%>          
                   </select>
                    </div>
                    <br />
                    <div class="fila">
                        <div class="etiqueta">Motivo</div>
                        <div class="campo"><input  placeholder="Motivo" type="text" name="Motivo_D"  
                                                   value="<%=form.get("Motivo_D")[0]%>" 
                                                   required></div>
                    </div>
                    <br />
                    <div class="fila">
                        <div class="etiqueta">Nombre Del Depositante</div>
                        <div class="campo"><input  placeholder="Nombre" type="text" name="NombreD_D"  
                                                   value="<%=form.get("NombreD_D")[0]%>" 
                                                   required></div>
                    </div>
                    <br />
                    <div class="fila encabezado"><button  style="margin-bottom: 15px">Realizar</button> </div>
  
    </form>
<%}else{%> 
 <%
                          DecimalFormat df2 = new DecimalFormat("#.##");
                          df2.setRoundingMode(RoundingMode.DOWN);  
%>
 <div class="fila encabezado"><b><p>Deposito Realizado </b></p></div>   
 <div class="fila"> Fecha :  <%=model.getMovimiento().getFecha()%></div>
 <div class="fila"> Depositante :  <%=model.getMovimiento().getDeposito().getNombreDepositante()%></div>
 <div class="fila"> Motivo :  <%=model.getMovimiento().getDeposito().getMotivo()%></div>
 <div class="fila"> Monto :  <%=df2.format(model.getMovimiento().getDeposito().getMonto())%>  Colones</div>

 
<%}%>   
</div>
</div> 
        <div class="EspacioLogin"></div>
</html>
<%!
    private String erroneo(String campo, Map<String,String> errores){
      if ( (errores!=null) && (errores.get(campo)!=null) )
        return "is-invalid";
      else
        return "";
    }

    private String title(String campo, Map<String,String> errores){
      if ( (errores!=null) && (errores.get(campo)!=null) )
        return errores.get(campo);
      else
        return "";
    }

    private Map<String,String[]> getForm(Model model){
       Map<String,String[]> values = new HashMap<>();
       values.put("Monto_D", new String[]{String.valueOf(model.getDeposito().getMonto())});
       values.put("Motivo_D", new String[]{model.getDeposito().getMotivo()});
       values.put("NombreD_D", new String[]{model.getDeposito().getNombreDepositante()});
       return values;
    }
    
%> 