package eventos;

import fel.Fel;
import fel.GeneradorTiempos;
import hospital.Estadisticas;
import hospital.Paciente;
import servers.Servidor;
import servers.Servidores;

public class EventoArribo extends Evento {

    public EventoArribo(float tiempo, byte cuadroClinico) {
        // Considero que inicia en item n°1
        super((byte) 0, tiempo, new Paciente(tiempo, cuadroClinico));

        // Actualizo la cantidad de Items.
        Estadisticas.actualizarCantidadPacientes(this.getPaciente().getCuadroClinico());
    }

    public Servidor servidorDeEvento(Servidores servidores) {
        if (servidores.estanOcupados(this.getPaciente().getCuadroClinico())) {
            return servidores.asignacionServidor(this.getPaciente().getCuadroClinico());
        } else {
            return servidores.getServidorDesocupado(this.getPaciente().getCuadroClinico());
        }
    }

    public void planificarEvento(Servidores servidores) {
        Servidor servidorActual = servidorDeEvento(servidores);


        // Si todos los servidores estan ocupados, del cuadro dado, lo ponemos en la cola mas corta

        if (servidorActual.isOcupado()) {
            servidorActual.getCola().insertarCola(this.getPaciente());
        } else {

            // Recuperamos el servidor desocupado, el cual sabemos que existe.

            servidorActual.setPaciente(this.getPaciente());
            // Le ponemos el paciente en cuestion

            servidorActual.setOcupado(true);
            // Lo marcamos como ocupado.

            this.getPaciente().setTiempoDuracionServicio((float) GeneradorTiempos.getTiempoDuracionServicio(this.getPaciente().getCuadroClinico()));
            // Se setea a si mismo el tiempo de duracion del servicio.

            EventoSalida eventoSalida = new EventoSalida(this.getTiempo() + this.getPaciente().getTiempoDuracionServicio(), this.getPaciente());
            Fel.getFel().insertarFel(eventoSalida);
            // Agregamos a la fel el evento de salida.

            servidorActual.setTiempoOcioso(this.getTiempo());
            // Recolectamos el tiempo ocioso de este servidor.
        }

        // Un paciente de cuadro 'a' genera siempre uno de cuadro 'a'. Recordar a la hora de inicializar el sistema.
        Fel.getFel().insertarFel(new EventoArribo(this.getTiempo() + GeneradorTiempos.getTiempoEntreArribos(this.getTiempo(), this.getPaciente().getCuadroClinico()), this.getPaciente().getCuadroClinico()));
    }

}
