package operaciones;


public class Recepcion {
   private int id;
   private String total_pedido;
   private String valor_cif;
   private String costo_envio;
   private String aduana;
   private String iva;
   private String total_impuesto;
   private String total_compra;



    public Recepcion(int id,String total_pedido, String valor_cif, String costo_envio, String aduana, String iva,String total_impuesto,String total_compra)
    {
        this.id = id;
        this.total_pedido = total_pedido;
        this.valor_cif = valor_cif;
        this.costo_envio = costo_envio;
        this.aduana = aduana;
        this.iva = iva;
        this.total_impuesto = total_impuesto;
        this.total_compra = total_compra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTotal_pedido() {
        return total_pedido;
    }

    public void setTotal_pedido(String total_pedido) {
        this.total_pedido = total_pedido;
    }

    public String getValor_cif() {
        return valor_cif;
    }

    public void setValor_cif(String valor_cif) {
        this.valor_cif = valor_cif;
    }

    public String getCosto_envio() {
        return costo_envio;
    }

    public void setCosto_envio(String costo_envio) {
        this.costo_envio = costo_envio;
    }

    public String getAduana() {
        return aduana;
    }

    public void setAduana(String aduana) {
        this.aduana = aduana;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getTotal_impuesto() {
        return total_impuesto;
    }

    public void setTotal_impuesto(String total_impuesto) {
        this.total_impuesto = total_impuesto;
    }

    public String getTotal_compra() {
        return total_compra;
    }

    public void setTotal_compra(String total_compra) {
        this.total_compra = total_compra;
    }
}
