package operaciones;

public class Seguro {

    private double valorUF = 31500;
    private double  valorSeguro;
    private boolean asegurable;

    public void calcularValorSeguro(int antiguedad){
        this.valorSeguro = this.valorUF * 0.1 * antiguedad;

    }


    public double getValorUF() {
        return valorUF;
    }

    public void setValorUF(double valorUF) {
        this.valorUF = valorUF;
    }

    public double getValorSeguro() {
        return valorSeguro;
    }

    public void setValorSeguro(double valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    public boolean isAsegurable() {
        return asegurable;
    }

    public void setAsegurable(boolean asegurable) {
        this.asegurable = asegurable;
    }


}
