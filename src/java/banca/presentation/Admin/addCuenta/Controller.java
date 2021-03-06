package banca.presentation.Admin.addCuenta;
import banca.logic.Cuenta;
import banca.logic.Usuario;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "addCuentaController", urlPatterns = {"/presentation/admin/addCuenta/show",
    "/presentation/admin/addCuenta/add"})
public class Controller extends HttpServlet {
protected void processRequest(HttpServletRequest request,HttpServletResponse response)
        throws ServletException,IOException {
         request.setAttribute("model",new Model());
        HttpSession session = request.getSession(true);
        Usuario real = (Usuario) session.getAttribute("admin");
        String viewUrl="";
        if(real!= null){
        switch(request.getServletPath()){
            case "/presentation/admin/addCuenta/show":
                viewUrl=this.show(request);
                break; 
            case "/presentation/admin/addCuenta/add":
                viewUrl=this.add(request);
                break; 
        }
         }else{viewUrl="/presentation/sesionCaducada.jsp";} 
        request.getRequestDispatcher(viewUrl).forward( request, response); 
  }
 //================= Show menu=================
   public String show(HttpServletRequest request){
        CargarDatos(request);
        return this.showAction(request);
    }
    public String showAction(HttpServletRequest request){
        return "/presentation/admin/addCuenta/view.jsp"; 
    } 
    private void CargarDatos(HttpServletRequest request) {
        Model model = (Model) request.getAttribute("model");
        banca.logic.Model domainModel = banca.logic.Model.instance();
        model.setMonedas(domainModel.getMonedas());
        
    }
    private String add(HttpServletRequest request) {
    try {
            Map<String, String> errores = this.validar(request);
            if (errores.isEmpty()) {
             return this.addAction(request);
            } else {
                return "/presentation/admin/addCuenta/view.jsp";
            }
        } catch (Exception e) {
                return "/presentation/admin/addCuenta/view.jsp";
        }
    }
    
    private Map<String, String> validar(HttpServletRequest request) {
        Map<String,String> errores = new HashMap<>();
        banca.logic.Model domainModel = banca.logic.Model.instance();
        Usuario real = domainModel.usuarioFind(request.getParameter("Cedula_C"),"");
        if (real == null){
            errores.put("Cedula_C","Usuario NO Existe");
            request.setAttribute("errores",errores);
        }
        return errores;
    }

    private String addAction(HttpServletRequest request) {
         Model model = (Model) request.getAttribute("model");
         banca.logic.Model domainModel = banca.logic.Model.instance();
         Cuenta c = new Cuenta();
         c.setUsuario(domainModel.usuarioFind(request.getParameter("Cedula_C"),""));
         c.getMoneda().setId(request.getParameter("Moneda_C"));
         c.setDescripcion(request.getParameter("Descripcion_C"));
         c.setLimite(Double.valueOf(request.getParameter("LimiteD_C")));
         c.setEstado(true);
         model.setCuenta(c);
         domainModel.AddCuenta(c);
         return "/presentation/admin/addCuenta/view.jsp";
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
