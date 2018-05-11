package eventos;

import hospital.Paciente;
import servers.Servidores;

public abstract class Evento implements Comparable<Evento> {

    private byte tipo;
    /*
    Codificacion
        0: Arribo
        1: Fin de Servicio
        2: Fin de Simulacion
    */

    private float tiempo; // Considerese el 'clock'
    private Paciente paciente;

    public Evento(byte tipo, float tiempo, Paciente paciente) {
        this.tiempo = tiempo;
        this.paciente = paciente;
        this.tipo = tipo;
    }

    public byte getTipo() {
        return tipo;
    }

    public float getTiempo() {
        return tiempo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    // Implementa la planificacion de eventos.
    public abstract void planificarEvento(Servidores servidor);

    @Override
    public int compareTo(Evento o) {
        //Ordenar por menor tiempo
        return Float.compare(this.getTiempo(), o.getTiempo());
    }
}
