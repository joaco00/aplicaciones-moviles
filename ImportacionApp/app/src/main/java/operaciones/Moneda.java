package operaciones;

public class Moneda {
    private int valorCifClp;
    private int valorDolar = 890;
    private int valorTotalPedidoClp;
    private int valorEnvioClp;
    private int valorAduanaClp;
    private int valorIvaClp;
    private int totalImpuestosClp;
    private int totalCompraClp;
    private int totalCompraUsd;

/* Pasamos la moneda a CLP */
    public void calcularValorCifClp(int valorCif ){
        valorCifClp = valorCif * valorDolar;
    }

    public void calcularTotalPedidoClp(int totalPedido){
        this.valorTotalPedidoClp = totalPedido * valorDolar;
    }

    public void calcularEnvioClp(int valorEnvio){
        this.valorEnvioClp = valorEnvio * valorDolar;
    }


    public void calcularAduanaClp(int valorTasaAduana ){
        this.valorAduanaClp = valorDolar * valorTasaAduana;
    }

    public void calcularIvaClp(int valorIva ){
        this.valorIvaClp = valorIva * valorDolar;
    }

    public void calcularTotalImpuesto(int totalImpuestos){
        this.totalImpuestosClp = totalImpuestos * valorDolar;
    }

    public void calcularTotalCompraClp(){
        this.totalCompraClp = this.valorCifClp + this.totalImpuestosClp;
    }


    public void calcularTotalCompraUsd(int valorCif, int totalImpuestos){
        this.totalCompraUsd = valorCif + totalImpuestos;
    }





    public int getValorCifClp() {
        return valorCifClp;
    }

    public void setValorCifClp(int valorCifClp) {
        this.valorCifClp = valorCifClp;
    }

    public int getValorDolar() {
        return valorDolar;
    }

    public void setValorDolar(int valorDolar) {
        this.valorDolar = valorDolar;
    }

    public int getValorTotalPedidoClp() {
        return valorTotalPedidoClp;
    }

    public void setValorTotalPedidoClp(int valorTotalPedidoClp) {
        this.valorTotalPedidoClp = valorTotalPedidoClp;
    }

    public int getValorEnvioClp() {
        return valorEnvioClp;
    }

    public void setValorEnvioClp(int valorEnvioClp) {
        this.valorEnvioClp = valorEnvioClp;
    }

    public int getValorAduanaClp() {
        return valorAduanaClp;
    }

    public void setValorAduanaClp(int valorAduanaClp) {
        this.valorAduanaClp = valorAduanaClp;
    }

    public int getValorIvaClp() {
        return valorIvaClp;
    }

    public void setValorIvaClp(int valorIvaClp) {
        this.valorIvaClp = valorIvaClp;
    }

    public int getTotalImpuestosClp() {
        return totalImpuestosClp;
    }

    public void setTotalImpuestosClp(int totalImpuestosClp) {
        this.totalImpuestosClp = totalImpuestosClp;
    }

    public int getTotalCompraClp() {
        return totalCompraClp;
    }

    public void setTotalCompraClp(int totalCompraClp) {
        this.totalCompraClp = totalCompraClp;
    }

    public int getTotalCompraUsd() {
        return totalCompraUsd;
    }

    public void setTotalCompraUsd(int totalCompraUsd) {
        this.totalCompraUsd = totalCompraUsd;
    }




}
