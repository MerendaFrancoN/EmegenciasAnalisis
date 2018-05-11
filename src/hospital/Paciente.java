package hospital;

public class Paciente {

    private static int genNumeroPaciente = 1;

    private int numero;
    private float tiempoArribo;
    private float tiempoDuracionServicio;
    private byte cuadroClinico;
    /*
    Codificación:
        0:Leve
        1:Medio
        2:Grave
     */

    public Paciente(float tiempoArribo, byte cuadroClinico) {
        this.numero = genNumeroPaciente;
        this.tiempoArribo = tiempoArribo;
        this.tiempoDuracionServicio = 0;
        this.cuadroClinico = cuadroClinico;

        genNumeroPaciente++;
    }

    public int getNumero() {
        return numero;
    }

    public float getTiempoArribo() {
        return tiempoArribo;
    }

    public float getTiempoDuracionServicio() {
        return tiempoDuracionServicio;
    }

    public void setTiempoDuracionServicio(float tiempoDuracionServicio) {
        this.tiempoDuracionServicio = tiempoDuracionServicio;
    }

    public byte getCuadroClinico() {
        return cuadroClinico;
    }

    public static void resetNumeroPaciente() {
        genNumeroPaciente = 0;
    }

}
