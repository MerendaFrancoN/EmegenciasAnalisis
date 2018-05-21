package estadistica;

import servers.Servidor;
import servers.Servidores;

public class Corrida {

    public int[] cantPacientes;
    public double[] tiempoEsperaCola;
    public double[] tiempoEsperaColaMedia;
    public double[] tiempoMedioOcio;


    public Corrida(){
        cantPacientes=new int[3];
        tiempoEsperaCola= new double[3];
        tiempoEsperaColaMedia= new double[3];
        tiempoMedioOcio= new double[3];

        Estadisticas.addCorrida(this);
    }

    public void actualizarCantidadPacientes(byte tipo) {
        // Suma 1 a la cantidad de Pacientes del tipo indicado.
        cantPacientes[tipo]++;
    }

    public void actualizarTiempoEsperaCola(byte tipo, float tiempoActual, float tiempoDuracionServicio, float tiempoArribo) {

        // El tiempo de espera en cola total es igual a la sumatoria de todos los tiempos de espera en cola.
        // Cada tiempo de cola se calcula con el tiempo actual,
        // que seria el tiempo en el que termina de atenderse, menos el tiempo en el que llego menos el tiempo de duracion de servicio.
        tiempoEsperaCola[tipo] += tiempoActual - (tiempoDuracionServicio + tiempoArribo);
        // El valor sumado deberia ser mayor o igual a 0. tiempoActual debe ser siempre igual o mayor que la suma del tiempoDuracionServicio y tiempoArribo.
    }


    public void calcularEstadisticas(Servidores servidores, float tiempoFinSimulacion) {
        // Calculamos tiempo de espera medio por cada tipo de caso

        for (int i = 0; i < 3; i++) {
            if (tiempoEsperaCola[i] != 0)
                // Se hacen controles para que no de 0.
                tiempoEsperaColaMedia[i] = tiempoEsperaCola[i] / (float) cantPacientes[i];
        }

        // Calculamos el tiempo ocioso por Servidor.
        for (int i = 0; i < 3; i++) {
            for (Servidor x : servidores.listaServidoresPorTipo((byte) i)) {
                tiempoMedioOcio[i] += x.getTiempoOcioso() / servidores.listaServidoresPorTipo((byte) i).size();
            }
        }
    }


}
