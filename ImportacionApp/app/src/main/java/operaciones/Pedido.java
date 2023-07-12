package operaciones;

public class Pedido {

    private int costo;
    private String nombre;
    private int cantidad;
    private int valorEnvio;
    private int codigo;
    private String proveedor;
    private int valorCif;
    private int totalPedido;

    /* Calculamos en total del pedido */
    public void calcularTotalDelPedido(){
       this.totalPedido = cantidad    * costo;
       this.valorCif    = totalPedido + valorEnvio;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getValorEnvio() {
        return valorEnvio;
    }

    public void setValorEnvio(int valorEnvio) {
        this.valorEnvio = valorEnvio;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public int getValorCif() {
        return valorCif;
    }

    public void setValorCif(int valorCif) {
        this.valorCif = valorCif;
    }

    public int getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(int totalPedido) {
        this.totalPedido = totalPedido;
    }


}
