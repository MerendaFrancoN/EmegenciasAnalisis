package hospital;

import eventos.Evento;
import eventos.EventoArribo;
import eventos.EventoFinSimulacion;
import fel.Fel;
import servers.Servidores;


public class Principal {

    private static final float tiempoEsperadoDeEjecucion = 604800; // 168 Horas = 604800 Minutos
    private static final int cantIteraciones = 100;

    public static void main(String[] args) {


        boolean finSimulacion;
        float tiempoSimulacion;
        Evento actual;
        Fel fel = Fel.getFel();
        Servidores servidores;

        for (int i = 0; i < cantIteraciones; i++) {

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
            fel.insertarFel(new EventoArribo(tiempoSimulacion, (byte) 0));

            fel.insertarFel(new EventoArribo(tiempoSimulacion, (byte) 1));

            fel.insertarFel(new EventoArribo(tiempoSimulacion, (byte) 2));

            // Mostrar la lista para hacer Debug
            // fel.mostrarFel();

            while (!finSimulacion) {
                // Actual toma el primer elemento del la Fel, el cual es el mas cercano en el tiempo.
                actual = fel.suprimirFel();
                // Actualizamos el tiempo de Simulacion.
                tiempoSimulacion = actual.getTiempo();

                // Planificamos el evento proximo a partir de 'actual'
                actual.planificarEvento(servidores);

                if (actual.getTipo() == 2) {
                    // Si el evento es de 'FinSimulacion' terminar con el loop.
                    finSimulacion = true;
                }
                // Mostrar la lista para hacer Debug
                // fel.mostrarFel();
            }
            // Muestra de resultados

            Estadisticas.calcularEstadisticas(servidores, tiempoSimulacion);
            Estadisticas.resetCorrida();
        }
        // Calcula los promedios acorde a la cantidad de iteraciones
        Estadisticas.analisisPostIteraciones(cantIteraciones);

        // Muestra de resultads
        Estadisticas.mostrarResutlados(cantIteraciones);
    }
}
