package estadistica;

import java.util.LinkedList;

public class Estadisticas {

    // Objetivos
    //      Tiempo medio de transito de los pacientes de cada tipo (3 valores)
    //      Tiempo medio de espera en cola de cada tipo (3 valores)
    //      Porcentaje de tiempo ocioso de cada servidor (5 valores, uno por cada servidor)

    private static LinkedList<Corrida> listaCorridas = new LinkedList<>();

    // Variables de analisis de Resultados. No se deben resetear a lo largo de las multiples ejecuciones
    private static double[] tiempoTotalMedioEspera = new double[3];
    private static double[] tiempoTotalMedioOcio = new double[3];

    private static double[] tiempoDesviacionesEspera = new double[3];
    private static double[] tiempoDesviacionesOcio = new double[3];

    private static double z_90= 1.65;


    public static void analisisPostIteraciones(int cantIteraciones) {
        // Recorremos toda la lista de corridas
        // Recuperammos las medias por corrida y las sumamos a las medias de medias
        for (Corrida c : listaCorridas) {
            for (int i = 0; i < 3; i++) {
                tiempoTotalMedioEspera[i] += c.tiempoEsperaColaMedia[i];
                tiempoTotalMedioOcio[i] += c.tiempoMedioOcio[i];
            }
        }
        // Calculamos media de medias
        for (int i = 0; i < 3; i++) {
            tiempoTotalMedioEspera[i] = tiempoTotalMedioEspera[i] / cantIteraciones;
            tiempoTotalMedioOcio[i] = tiempoTotalMedioOcio[i] / cantIteraciones;
        }

        // Recorremos una segunda vez, calculamos las desviaciones de las medias x corrida con respecto a la media de media
        for (Corrida c : listaCorridas) {
            for (int i = 0; i < 3; i++) {
                tiempoDesviacionesEspera[i] = Math.pow( (tiempoTotalMedioEspera[i] - c.tiempoEsperaColaMedia[i]),2 );
                tiempoDesviacionesOcio[i] = Math.pow( (tiempoTotalMedioOcio[i] - c.tiempoMedioOcio[i]),2);
            }
        }

        for (int i = 0; i < 3; i++) {
            tiempoDesviacionesEspera[i] = tiempoDesviacionesEspera[i] / (cantIteraciones - 1);
            tiempoDesviacionesOcio[i] = tiempoDesviacionesOcio[i] / (cantIteraciones - 1);
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



            System.out.printf("Intervalo de tiempo medio de ocio de los médicos: %.2f" + calculoIntervalo(i,cantIteraciones,0)+"\n");
            System.out.printf("Intervalo de tiempo medio de Espera de los médicos" + calculoIntervalo(i,cantIteraciones,1)+ "\n");
            System.out.println("----------------------------------------");
        }
    }

    public static void addCorrida(Corrida corrida) {
        listaCorridas.add(corrida);
    }
    private static String calculoIntervalo(int indice, int cantIteraciones, int tipo){
        String s = "";

        if (tipo==0)    {
            s=s.concat(" (%%");
            s=s.concat(Double.toString(100*(tiempoTotalMedioOcio[indice]-z_90*(tiempoDesviacionesOcio[indice] / Math.sqrt(cantIteraciones) )) /tiempoTotalMedioOcio[indice]) );
            s=s.concat(" , %%");
            s=s.concat(Double.toString(100*(tiempoTotalMedioOcio[indice]+z_90*(tiempoDesviacionesOcio[indice] / Math.sqrt(cantIteraciones) )) /tiempoTotalMedioOcio[indice])  );
            s=s.concat(" )");
        }
        else{
            s=s.concat(" (");
            s=s.concat(Double.toString(tiempoTotalMedioEspera[indice]-z_90*(tiempoDesviacionesEspera[indice] / Math.sqrt(cantIteraciones) )));
            s=s.concat(" , ");
            s=s.concat(Double.toString(tiempoTotalMedioEspera[indice]+z_90*(tiempoDesviacionesEspera[indice] / Math.sqrt(cantIteraciones) )));
            s=s.concat(" )");
        }

    return  s;
    }

    }
