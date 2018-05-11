package servers;

import hospital.Paciente;
import hospital.Principal;

public class Servidor {
    private Paciente paciente;
    private boolean ocupado;
    private float tiempoOcioso;
    private float tiempoInicioOcio;

    //flagTrabajo es para saber si un servidor ya trabajó al menos una vez, para que en tiempo ocioso cuando de 0 y trabajó, sea 0% el tiempo de ocio.

    private boolean flagTrabajo;

    private Queue cola;


    private float porcentajeTiempoOcioso;

    public Servidor() {
        paciente = null;
        // No hay items en el servidor
        ocupado = false;
        // Desocupado
        tiempoOcioso = 0;
        // Tiempo ocioso inical es de 0.
        tiempoInicioOcio = 0;
        // Inicio de Ocio en 0

        setCola(new Queue()); //Inicialización de la cola, vacia.

        flagTrabajo = false;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
        flagTrabajo = true;
    }

    /**
     * Calcula y guarda el tiempo ocioso total del servidor
     *
     * @param tiempoActual es el tiempo actual del sistema.
     */
    public void setTiempoOcioso(float tiempoActual) {
        this.tiempoOcioso += (tiempoActual - this.getTiempoInicioOcio());
    }

    public float getTiempoInicioOcio() {
        return tiempoInicioOcio;
    }

    public void setTiempoInicioOcio(float tiempoInicioOcio) {
        this.tiempoInicioOcio = tiempoInicioOcio;
    }

    public Queue getCola() {
        return cola;
    }

    public void setCola(Queue cola) {
        this.cola = cola;
    }


    public float getTiempoOcioso() {
        /*Si el tiempo ocioso es 0 y nunca trabajó, devuelvo el tiempo total de ejecución.*/
        if (tiempoOcioso == 0 && !flagTrabajo)
            return Principal.tiempoEsperadoDeEjecucion;

        return tiempoOcioso;
    }

}
