package banca.presentation.Cliente.cuentas;
import banca.logic.Cliente;
import banca.logic.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "ClienteCuentasController", urlPatterns = {
    "/presentation/cliente/cuentas/show",
    "/presentation/cliente/cuentas/detalles",
    "/presentation/cliente/cuentas/acomodo"})
public class Controller extends HttpServlet {
    
  protected void processRequest(HttpServletRequest request, 
                                HttpServletResponse response)
         throws ServletException, IOException { 
        request.setAttribute("model",new Model());
        HttpSession session = request.getSession(true);
        Usuario real = (Usuario) session.getAttribute("cliente");
        String viewUrl="";
        if(real!= null){
        switch (request.getServletPath()) {
          case "/presentation/cliente/cuentas/show":
              viewUrl = this.show(request);
              break;
          case "/presentation/cliente/cuentas/detalles":
              viewUrl = this.detalles(request);
              break;
          case  "/presentation/cliente/cuentas/acomodo":
              viewUrl = this.detalleRango(request);
              break;
        }   
        }else{viewUrl="/presentation/sesionCaducada.jsp";}
        request.getRequestDispatcher(viewUrl).forward( request, response); 
  }
    //===================CUENTAS SHOW========================
    public String show(HttpServletRequest request) {
        return this.showAction(request);
    }
    public String showAction(HttpServletRequest request) {
        Model model = (Model) request.getAttribute("model");
        banca.logic.Model domainModel = banca.logic.Model.instance();
        HttpSession session = request.getSession(true);
        Usuario usuario = (Usuario) session.getAttribute("cliente");
try {        
            model.setCuentas(domainModel.cuentasFind(new Cliente(usuario.getCedula(),usuario.toString(),usuario)));
            return "/presentation/cliente/cuentas/View.jsp";
        } catch (Exception ex) {
            model.setCuentas(new ArrayList<>());
            return "/presentation/cliente/cuentas/View.jsp";
        }
    }
    //==================DETALLES DE CUENTAS=====================
    private String detalles(HttpServletRequest request) {
        return this.detallesAction(request);
   
    }    
    private String detallesAction(HttpServletRequest request) {
        Model model = (Model) request.getAttribute("model");
        banca.logic.Model domainModel = banca.logic.Model.instance();
        model.setMovimientos(domainModel.MovimientosFind(Integer.parseInt(request.getParameter("numeroFld"))));
        return "/presentation/cliente/cuentas/View.jsp";
    }
    
     //==================DETALLES DE CUENTAS RANGO=====================
    private String detalleRango(HttpServletRequest request) {
        Model model = (Model) request.getAttribute("model");
        banca.logic.Model domainModel = banca.logic.Model.instance();
        
        String f1= request.getParameter("fecha1");
        
        model.setMovimientos(domainModel.MovimientosRangoFind(
                Integer.parseInt(request.getParameter("numeroFld")),
                request.getParameter("fecha1"),
                request.getParameter("fecha2")
                ));
        
        return "/presentation/cliente/cuentas/View.jsp";
    }
    
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
 
}