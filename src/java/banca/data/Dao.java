package banca.data;
import banca.logic.Cliente;
import banca.logic.Cuenta;
import banca.logic.CuentaFavorita;
import banca.logic.Deposito;
import banca.logic.Moneda;
import banca.logic.Movimiento;
import banca.logic.Retiro;
import banca.logic.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Dao {
    RelDatabase db;
    
    public Dao(){
        db= new RelDatabase();
    }

    //Agregar A TABLAS
    
     public void UsuarioAdd(Usuario u) throws Exception{
        String sql="insert into Usuario (cedula,nombre,apellido1,apellido2,"
                + "contraseña,`is`,telefono) values('%s','%s','%s','%s','%s','%s',"
                + "'%s')";
        sql=String.format(sql,u.getCedula(),u.getNombre(),u.getApellido1(),
                u.getApellido2(),u.getContraseña(),u.toIs()
                ,u.getTelefono());
        int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("Usuario ya existe");
        }
    }
     
     public void CuentaAdd(Cuenta c) throws Exception{
        String sql="insert into Cuenta (saldo,estado,descripcion,"
                + "interesesG,limite,Usuario_cedula,Moneda_id) "
                + "values(%s,%s,'%s','%s',%s,%s,'%s')";
        sql=String.format(sql,c.getSaldo(),c.estado(),
                c.getDescripcion(),c.getInteresesG(),c.getLimite(),
                c.getUsuario().getCedula(),c.getMoneda().getId());
        int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("Cuenta ya existe");
        }
     }
     
    
     
    public void MonedaAdd(Moneda m) throws Exception{
        String sql="insert into Moneda (id,tipo_cambio,interes) "
                + "values('%s','%s','%s')";
        sql=String.format(sql,m.getId(),m.getTipo_cambio(),m.getInteres());
        int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("Moneda ya existe");
        }
    }
     
     
     
    public void CuentaFavoritadd(int numero, String cedula) throws Exception{
        String sql="insert into CuentaFavorita (Cuenta_numero,Usuario_cedula) "
                + "values('%s','%s')";
        sql=String.format(sql,numero,cedula);
        int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("CuentaFavorita ya existe");
        }
    }
    
    
    
     public void Depositoadd(Deposito d) throws Exception{
        String sql="insert into Deposito (id,monto,motivo,fecha,nombreDepositante) "
                + "values(%s,'%s','%s','%s','%s')";
        sql=String.format(sql,d.getId(),d.getMonto(),d.getMotivo(),d.getFecha(),
                d.getNombreDepositante());
        int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("Deposito ya existe");
        }
    }
    
    public void Retiroadd(Retiro r) throws Exception{
        String sql="insert into Retiro (id,monto,fecha) "
                + "values('%s','%s','%s')";
        sql=String.format(sql,r.getId(),r.getMonto(),r.getFecha());
        int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("Retiro ya existe");
        }
    }
    
    public void MovimientoAdd(Movimiento m) throws Exception{
        String sql="insert into Movimiento (fecha,detalle,Deposito_id,"
                + "Retiro_id,Cuenta_numero) "
                + "values('%s','%s','%s','%s','%s')";
        sql=String.format(sql,m.getFecha(),m.getDetalle(),m.getDeposito().getId(),
                m.getRetiro().getId(),m.getCuenta().getNumero());
        int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("Movimiento ya existe");
        }
    }
    
    
    
    // UDATES
   public void UsuarioUpdate(Usuario cliente) {
   String sql="update Usuario set nombre='%s',apellido1='%s',apellido2='%s',"
                + "contraseña='%s',`is`='%s',telefono='%s' "
                +"where cedula='%s'";
        sql=String.format(sql,cliente.getNombre(),cliente.getApellido1(),
                cliente.getApellido2(),cliente.getContraseña(),cliente.toIs()
                ,cliente.getTelefono(),cliente.getCedula());
         db.executeUpdate(sql);
   }

     public void CuentaUpdate(Cuenta c) throws Exception{
        String sql="update Cuenta set saldo='%s',estado='%s',descripcion='%s',"
                + "interesesG='%s',limite='%s',Usuario_cedula='%s',Moneda_id='%s' "
                + "where numero='%s'";
        sql=String.format(sql,c.getSaldo(),c.estado(),
                c.getDescripcion(),c.getInteresesG(),c.getLimite(),
                c.getUsuario().getCedula(),c.getMoneda().getId(),c.getNumero());
        
        int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("Cuenta NO UPDATE");
        }
    }
     
     
     public void MonedaUpdate(Moneda m) throws Exception{
        String sql="update Moneda set tipo_cambio='%s',interes='%s' "+
                   "where id='%s'";
        sql=String.format(sql,m.getTipo_cambio(),m.getInteres(),m.getId());
        int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("Moneda NO UPDATE");
        }
    }
     
     
     
     // GETS LISTAS 
     
     public List<Cuenta> ListaCuentas(String cedula){
        List<Cuenta> resultado = new ArrayList<Cuenta>();
        try {
            String sql="select * from Cuenta c "+
                    "where c.Usuario_cedula like '%%%s%%'";
            sql=String.format(sql,cedula);
            ResultSet rs =  db.executeQuery(sql);
            while (rs.next()) {
                resultado.add(CuentaRender(rs));
            }
        } catch (SQLException ex) {
        return resultado;
        }
        return resultado;
    }
     
     public List<Movimiento> ListaMovimientos(int numero) {
       List<Movimiento> resultado = new ArrayList<Movimiento>();
        try {
            String sql="select * from Movimiento m "+
                    "where m.Cuenta_numero like '%%%s%%'";
            sql=String.format(sql,numero);
            ResultSet rs =  db.executeQuery(sql);
            while (rs.next()) {
                resultado.add(MovimientoRender(rs));
            }
        } catch (SQLException ex) {
          return resultado;
        }
        return resultado;
     }
     
    public List<Movimiento> ListaMovimientosRango(int c, String F1, String F2) {
     List<Movimiento> resultado = new ArrayList<Movimiento>();
        try {
            String sql="select * from Movimiento m "+
                    "where m.fecha <='%s 23:59:59' AND "
                    + "m.fecha >= '%s 00:00:00'  and "
                    + "m.Cuenta_numero = '%s'";
            sql=String.format(sql,F2,F1,c);
            ResultSet rs =  db.executeQuery(sql);
            while (rs.next()) {
                resultado.add(MovimientoRender(rs));
            }
        } catch (SQLException ex) {
          return resultado;
        }
        return resultado;
    }
     
     
     
 public int ContadorRetiros() {
    List<Retiro> resultado = new ArrayList<Retiro>();
        try {
            String sql="select * from Retiro";
            ResultSet rs =  db.executeQuery(sql);
            while (rs.next()) {
                resultado.add(RetiroRender(rs));
            }
        } catch (SQLException ex) {
          return 0;
        }
        return resultado.size();
    }

    public int ContadorDepositos() {
    List<Deposito> resultado = new ArrayList<Deposito>();
        try {
            String sql="select * from Deposito";
            ResultSet rs =  db.executeQuery(sql);
            while (rs.next()) {
                resultado.add(DepositoRender(rs));
            }
        } catch (SQLException ex) {
          return 0;
        }
        return resultado.size();
    }
    
 public List<CuentaFavorita> FavoritasList(Cliente cliente) {
    List<CuentaFavorita> resultado = new ArrayList<CuentaFavorita>();
        try {
            String sql="select * from CuentaFavorita f where Usuario_cedula like '%%%s%%'";
            sql=String.format(sql,cliente.getCedula());
            ResultSet rs =  db.executeQuery(sql);
            while (rs.next()) {
                resultado.add(CuentaFavoritaRender(rs));
            }
        } catch (SQLException ex) {
          return resultado;
        }
        return resultado;
    } 
 public List<Moneda> MonedasList() {
    List<Moneda> resultado = new ArrayList<Moneda>();
        try {
            String sql="select * from Moneda";
            ResultSet rs =  db.executeQuery(sql);
            while (rs.next()) {
                resultado.add(MonedaRender(rs));
            }
        } catch (SQLException ex) {
          return resultado;
        }
        return resultado;
    } 
// RENDER
    private Usuario UsuarioRender(ResultSet rs) {
      Usuario user = new Usuario();
        try {
            user.setCedula(rs.getString("cedula"));
            user.setNombre(rs.getString("nombre"));
            user.setApellido1(rs.getString("apellido1"));
            user.setApellido2(rs.getString("apellido2"));
            user.setContraseña(rs.getString("contraseña"));
            user.setIs(rs.getBoolean("is"));
            user.setTelefono(rs.getNString("telefono"));
            return user;
        } catch (SQLException ex) {
        return null;
        }
    }
    
    private Cuenta CuentaRender(ResultSet rs){
        Cuenta cuenta = new Cuenta();
        try {
            cuenta.setNumero(rs.getInt("numero"));
            cuenta.setSaldo(rs.getDouble("saldo"));
            cuenta.setEstado(rs.getBoolean("estado"));
            cuenta.setDescripcion(rs.getString("descripcion"));
            cuenta.setInteresesG(rs.getDouble("interesesG"));
            cuenta.setLimite(rs.getDouble("limite"));
            cuenta.setMoneda(GetMoneda(rs.getString("Moneda_id")));
            cuenta.setUsuario(GetUsuario(rs.getString("Usuario_cedula")));
            return cuenta;
        } catch (Exception ex) {
            return null;
        }
    }

    private Deposito DepositoRender(ResultSet rs){
     Deposito deposito = new Deposito();
        try {
            deposito.setId(rs.getInt("id"));
            deposito.setMonto(rs.getDouble("monto"));
            deposito.setMotivo(rs.getString("motivo"));
            deposito.setFecha(rs.getString("fecha"));
            deposito.setNombreDepositante(rs.getString("nombreDepositante"));
            return deposito;
        } catch (SQLException ex) {
           return null;
        }
     
    }
    
    private Retiro RetiroRender(ResultSet rs){
     Retiro retiro = new Retiro();
        try {
            retiro.setId(rs.getInt("id"));
            retiro.setMonto(rs.getDouble("monto"));
            retiro.setFecha(rs.getString("fecha"));
            return retiro;
        } catch (SQLException ex) {
           return null;
        }
     
    }
    
    private Moneda MonedaRender(ResultSet rs) {
     Moneda moneda = new Moneda();
        try {
            moneda.setId(rs.getString("id"));
            moneda.setTipo_cambio(rs.getDouble("tipo_cambio"));
            moneda.setInteres(rs.getDouble("interes"));
            return moneda;
        } catch (SQLException ex) {
           return null;
        }
     
    }
    
     private Movimiento MovimientoRender(ResultSet rs)  {
     Movimiento movimiento = new Movimiento();
        try {
            movimiento.setId(rs.getInt("id"));
            movimiento.setFecha(rs.getString("fecha"));
            movimiento.setDetalle(rs.getString("detalle"));
            movimiento.setDeposito(GetDeposito(rs.getInt("Deposito_id")));
            movimiento.setRetiro(GetRetiro(rs.getInt("Retiro_id")));
            movimiento.setCuenta(GetCuenta(rs.getInt("Cuenta_numero")));
            return movimiento;
        } catch (Exception ex) {
           return null;
        }
     
    }
    
    private CuentaFavorita CuentaFavoritaRender(ResultSet rs){
     CuentaFavorita cuentaF = new CuentaFavorita();
        try {
            cuentaF.setId(rs.getInt("numero"));
            cuentaF.setCuenta(GetCuenta(rs.getInt("Cuenta_numero")));
            cuentaF.setUser(GetUsuario(rs.getString("Usuario_cedula")));
            return cuentaF;
        } catch (Exception ex) {
           return null;
        }
     
    }
    
//GETS   
    

  public Moneda GetMoneda(String id) throws Exception {
      String sql="select * from "+
                    "Moneda m where m.id like '%%%s%%'";
        sql = String.format(sql,id);
        ResultSet rs =  db.executeQuery(sql);
        if (rs.next()) {
            return MonedaRender(rs);
        }
        else{
            throw new Exception ("Moneda no Existe");
        }
    }
  
    public Usuario GetUsuario(String cedula) throws Exception {
     String sql="select * from "+
                    "Usuario u where u.cedula like '%%%s%%'";
        sql = String.format(sql,cedula);
        ResultSet rs =  db.executeQuery(sql);
        if (rs.next()) {
            return UsuarioRender(rs);
        }
        else{
            return null;   
        }
    }

    public Cuenta GetCuenta(int ncuenta) throws Exception {
       String sql="select * from "+
                    "cuenta c where c.numero like '%%%s%%'";
        sql = String.format(sql,ncuenta);
        ResultSet rs =  db.executeQuery(sql);
        if (rs.next()) {
            return CuentaRender(rs);
        }
        else{
            return null;
        } 
    }

    public Deposito GetDeposito(int dep) throws Exception {
      String sql="select * from "+
                    "Deposito d where d.id like '%%%s%%'";
        sql = String.format(sql,dep);
        ResultSet rs =  db.executeQuery(sql);
        if (rs.next()) {
            return DepositoRender(rs);
        }
        else{
            throw new Exception ("Cuenta no Existe");
        }
    }

    public Retiro GetRetiro(int ret) throws Exception {
       String sql="select * from "+
                    "Retiro r where r.id like '%%%s%%'";
        sql = String.format(sql,ret);
        ResultSet rs =  db.executeQuery(sql);
        if (rs.next()) {
            return RetiroRender(rs);
        }
        else{
            throw new Exception ("Cuenta no Existe");
        }
    }

    public CuentaFavorita GetFavoritaxCed(String cedF, String cedC) throws Exception{
       String sql="select * from CuentaFavorita f inner join Cuenta c on c.numero = f.Cuenta_numero" +
        "  where f.Usuario_cedula like '%%%s%%' and c.Usuario_cedula like '%%%s%%'";
        sql = String.format(sql,cedC,cedF);
        ResultSet rs =  db.executeQuery(sql);
        if (rs.next()) {
            return CuentaFavoritaRender(rs);
        }
        else{
            return null;
        }
    }

    public Object GetFavorita(String ced, String num) throws Exception {
      String sql="select * from CuentaFavorita f where f.Usuario_cedula like '%%%s%%' and f.Cuenta_numero like '%%%s%%'";
        sql = String.format(sql,ced,num);
        ResultSet rs =  db.executeQuery(sql);
        if (rs.next()) {
            return CuentaFavoritaRender(rs);
        }
        else{
            return null;
        }
    }


 
   


   

    

   
    

    

    
}


