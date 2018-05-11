package eventos;

import fel.Fel;
import fel.GeneradorTiempos;
import hospital.Estadisticas;
import hospital.Paciente;
import servers.Servidor;
import servers.Servidores;

public class EventoSalida extends Evento {

    public EventoSalida(float tiempo, Paciente paciente) {
        super((byte) 1, tiempo, paciente);
    }

    @Override
    public void planificarEvento(Servidores servidores) {

        // Recuperamos el servidor que contenia el paciente que se esta yendo.
        Servidor servidorActual = servidores.getServidorConPaciente(this.getPaciente());

        if (servidorActual.getCola().hayCola()) {
            // Si el servidor en cuestion tiene cola tomamos el primer paciente de la cola
            Paciente sigPaciente = servidorActual.getCola().suprimirCola();
            // Lo ponemos en el servidor
            servidorActual.setPaciente(sigPaciente);
            // Y creamos un evento de salida para ese paciente.
            Fel.getFel().insertarFel(new EventoSalida(this.getTiempo() + (float) GeneradorTiempos.getTiempoEntreArribos(this.getTiempo(), this.getPaciente().getCuadroClinico()), sigPaciente));

        } else { // Si no hay cola...

            // Marcamos servidor como no ocupado
            servidorActual.setOcupado(false);

            // Empezamos a contar tiempo de ocio
            servidorActual.setTiempoInicioOcio(this.getTiempo());
        }

        // Colecto tiempo en espera
        Estadisticas.actualizarTiempoEsperaCola(this.getPaciente().getCuadroClinico(), this.getTiempo(), this.getPaciente().getTiempoDuracionServicio(), this.getPaciente().getTiempoArribo());

        // Colecto tiempo en tránsito
        //Estadisticas.actualizarTiempoTransito(this.getPaciente().getCuadroClinico(), this.getTiempo(), this.getPaciente().getTiempoArribo());

    }
}
