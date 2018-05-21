package hospital;

import estadistica.Corrida;
import estadistica.Estadisticas;
import eventos.Evento;
import eventos.EventoArribo;
import eventos.EventoFinSimulacion;
import fel.Fel;
import servers.Servidores;


public class Principal {

    public static final float tiempoEsperadoDeEjecucion = 604800; // 168 Horas = 604800 Minutos
    private static final int cantIteraciones = 50;

    public static void main(String[] args) {


        boolean finSimulacion;
        float tiempoSimulacion;
        Evento actual;
        Fel fel = Fel.getFel();
        Servidores servidores;

        for (int i = 0; i < cantIteraciones; i++) {

            // Creamos una nueva corrida.
            Corrida corrida = new Corrida();

            // Creo la Fel e inicializo los  Servidores
            fel.resetFel();
            servidores = new Servidores();
            servidores.inicializarServidores(2, 1, 2);

            // Inicializamos el tiempo de la simulacion.
            tiempoSimulacion = 0;

            finSimulacion = false;
            // Creo evento de Fin de Simulacion y lo cargo a la FEL, con 'tiempo' igual al tiempo que se desea ejecutar la simulacion.
            fel.insertarFel(new EventoFinSimulacion(tiempoEsperadoDeEjecucion));

            // Creo primer evento de Arribo de cada tipo
            fel.insertarFel(new EventoArribo(tiempoSimulacion, (byte) 0, corrida));

            fel.insertarFel(new EventoArribo(tiempoSimulacion, (byte) 1, corrida));

            fel.insertarFel(new EventoArribo(tiempoSimulacion, (byte) 2, corrida));

            // Mostrar la lista para hacer Debug
            // fel.mostrarFel();

            while (!finSimulacion) {
                // Actual toma el primer elemento del la Fel, el cual es el mas cercano en el tiempo.
                actual = fel.suprimirFel();
                // Actualizamos el tiempo de Simulacion.
                tiempoSimulacion = actual.getTiempo();

                // Planificamos el evento proximo a partir de 'actual'
                actual.planificarEvento(servidores, corrida);

                if (actual.getTipo() == 2) {
                    // Si el evento es de 'FinSimulacion' terminar con el loop.
                    finSimulacion = true;
                }
                // Mostrar la lista para hacer Debug
                // fel.mostrarFel();
            }
            // Calculamos las estadisticas por corrida
            corrida.calcularEstadisticas(servidores, tiempoSimulacion);

            // Reseteamos el numero de pacientes
            Paciente.resetNumeroPaciente();
        }
        // Calcula las medias de medias con sus desviaciones
        Estadisticas.analisisPostIteraciones(cantIteraciones);

        // Muestra de resultads
        Estadisticas.mostrarResutlados(cantIteraciones);
    }
}
