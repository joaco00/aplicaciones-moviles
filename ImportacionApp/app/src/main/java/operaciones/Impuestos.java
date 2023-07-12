package operaciones;

public class Impuestos {
    private int valorIva;
    private int valorTasaAduana;
    private int totalImpuestos;

    /* Calculamos la tasa de importacion */
    public void calcularTasaImportacion(int valorCif){
        this.valorTasaAduana = valorCif * 6 / 100;

    }
    /* Calculamos el iva */
    public void calcularIva(int valorCif){
        this.valorIva = valorCif * 19 / 100;

    }
    /* Calculamos el total de impuestos */
    public void calcularTotalImpuesto(){
        this.totalImpuestos = valorIva + valorTasaAduana;
    }


    public int getValorIva() {
        return valorIva;
    }

    public void setValorIva(int valorIva) {
        this.valorIva = valorIva;
    }

    public int getValorTasaAduana() {
        return valorTasaAduana;
    }

    public void setValorTasaAduana(int valorTasaAduana) {
        this.valorTasaAduana = valorTasaAduana;
    }

    public int getTotalImpuestos() {
        return totalImpuestos;
    }

    public void setTotalImpuestos(int totalImpuestos) {
        this.totalImpuestos = totalImpuestos;
    }










}
