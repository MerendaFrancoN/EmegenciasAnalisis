package hospital;

import servers.Servidor;
import servers.Servidores;

public class Estadisticas {

    // Objetivos
    //      Tiempo medio de transito de los pacientes de cada tipo (3 valores)
    //      Tiempo medio de espera en cola de cada tipo (3 valores)
    //      Porcentaje de tiempo ocioso de cada servidor (5 valores, uno por cada servidor)

    // Guardamos las estadisticas de tiempo en cola en la clase de Estadisticas, no en Paciente.

    private static int[] cantPacientes = new int[3];
    private static double[] tiempoEsperaCola = new double[3];
    private static double[] tiempoEsperaColaMedia = new double[3];
    private static double[] tiempoMedioOcio = new double[3];

    // Variables de analisis de Resultados. No se deben resetear a lo largo de las multiples ejecuciones
    private static double[] tiempoTotalMedioEspera = new double[3];
    private static double[] tiempoTotalMedioOcio = new double[3];

    public static void actualizarCantidadPacientes(byte tipo) {
        // Suma 1 a la cantidad de Pacientes del tipo indicado.
        cantPacientes[tipo]++;
    }

    public static int getCantPacientes(byte tipo) {
        // Retorna la cantidad de pacientes del tipo indicado
        return cantPacientes[tipo];
    }

    public static int getCantPacientes() {
        // Retorna todos los pacientes que tuvo el sistema.
        return (cantPacientes[0] + cantPacientes[1] + cantPacientes[2]);
    }

    public static void actualizarTiempoEsperaCola(byte tipo, float tiempoActual, float tiempoDuracionServicio, float tiempoArribo) {
        // El tiempo de espera en cola total es igual a la sumatoria de todos los tiempos de espera en cola.
        // Cada tiempo de cola se calcula con el tiempo actual,
        // que seria el tiempo en el que termina de atenderse, menos el tiempo en el que llego menos el tiempo de duracion de servicio.
        tiempoEsperaCola[tipo] += tiempoActual - (tiempoDuracionServicio + tiempoArribo);
        // El valor sumado deberia ser mayor o igual a 0. tiempoActual debe ser siempre igual o mayor que la suma del tiempoDuracionServicio y tiempoArribo.
    }


    public static void calcularEstadisticas(Servidores servidores, float tiempoFinSimulacion) {
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

        // Cargamos los resultados en las listas de analisis
        for (int i = 0; i < 3; i++) {
            tiempoTotalMedioEspera[i] += tiempoEsperaColaMedia[i];
            tiempoTotalMedioOcio[i] += tiempoMedioOcio[i];
        }

    }

    public static void analisisPostIteraciones(int cantIteraciones) {
        for (int i = 0; i < 3; i++) {
            tiempoTotalMedioEspera[i] = tiempoTotalMedioEspera[i] / (float) cantIteraciones;
            tiempoTotalMedioOcio[i] = tiempoTotalMedioOcio[i] / (float) cantIteraciones;
        }
    }

    public static void mostrarResutlados(int cantIteraciones) {
        // Muestra de resultados. No realiza operaciones.
        System.out.println("##############################################");
        System.out.println("#######  RESULTADOS DE LA SIMULACION  ########");
        System.out.println("##############################################");
        System.out.println();
        System.out.println("Tiempos mostrados en minutos.");
        System.out.println("Cantidad de Simulaciones realizadas de 1 semana= " + cantIteraciones);
        System.out.println();
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    System.out.println("Datos de tipo Leve:");
                    break;
                case 1:
                    System.out.println("Datos de tipo Medio:");
                    break;
                case 2:
                    System.out.println("Datos de tipo Grave:");
                    break;
            }
            System.out.printf("Tiempo medio de ocio de los médicos: %.2f\n", tiempoTotalMedioOcio[i]);
            System.out.printf("Tiempo medio de Espera en Cola: %.2f\n", tiempoTotalMedioEspera[i]);
            System.out.println("----------------------------------------");
        }
    }

    public static void resetCorrida() {
        // Resetea los valores estaticos de la clase Estadistica para poder hacer multiples ejecuciones
        cantPacientes = new int[3];
        tiempoEsperaCola = new double[3];
        tiempoEsperaColaMedia = new double[3];
        tiempoMedioOcio = new double[3];
        Paciente.resetNumeroPaciente();
    }
}
