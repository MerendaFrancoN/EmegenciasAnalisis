package servers;

import hospital.Paciente;

import java.util.LinkedList;

public class Servidores {

    private LinkedList<Servidor> medicoResidente;
    private LinkedList<Servidor> medicoGeneral;
    private LinkedList<Servidor> medicoEspecialista;

    public Servidores() {
        this.medicoEspecialista = new LinkedList<>();
        this.medicoGeneral = new LinkedList<>();
        this.medicoResidente = new LinkedList<>();
    }

    public void inicializarServidores(int cantResidente, int cantGeneral, int cantEsp) {
        for (int i = 0; i < cantResidente; i++) {
            this.medicoResidente.add(new Servidor());
        }
        for (int i = 0; i < cantGeneral; i++) {
            this.medicoGeneral.add(new Servidor());
        }
        for (int i = 0; i < cantEsp; i++) {
            this.medicoEspecialista.add(new Servidor());
        }
    }

    //Metodo con otra implementación más simple
    public Servidor asignacionServidor(byte cuadroClinico) {
        Servidor servidorAsignado = null;

        int colaMasCorta = 1000000000;
        // Valor para comparar el tamaño de cola
        LinkedList<Servidor> listaServidores = listaServidoresPorTipo(cuadroClinico);
        // Lista con todos los servidores del tipo indicado.

        for (Servidor x : listaServidores) {
            if (x.getCola().getCantidadItems() < colaMasCorta) {
                colaMasCorta = x.getCola().getCantidadItems();
                servidorAsignado = x;
            }
        }
        return servidorAsignado;
    }


    /**
     * @param cuadroClinico Cuadro Clinico del que queremos ver si estan o no los servidores ocupados
     * @return Verdadero en caso de que todos los servidores esten ocupados, falso en caso de que encuentre por lo menos uno servidor del tipo indicado como libre.
     */
    public Boolean estanOcupados(byte cuadroClinico) {
        LinkedList<Servidor> listaServidores = listaServidoresPorTipo(cuadroClinico);

        for (Servidor x : listaServidores) {
            if (!x.isOcupado()) {
                return false;
            }
        }
        return true;
    }

    public Servidor getServidorDesocupado(byte cuadroClinico) {
        LinkedList<Servidor> listaServidores = listaServidoresPorTipo(cuadroClinico);

        for (Servidor x : listaServidores) {
            if (!x.isOcupado()) {
                return x;
            }
        }
        return null;
    }

    public Servidor getServidorConPaciente(Paciente x) {
        LinkedList<Servidor> listaServidores = listaServidoresPorTipo(x.getCuadroClinico());

        for (Servidor y : listaServidores) {
            if (y.getPaciente().equals(x)) {
                return y;
            }
        }
        return null;
    }

    public LinkedList<Servidor> listaServidoresPorTipo(byte tipo) {
        LinkedList<Servidor> x = null;
        switch (tipo) {
            case 0: {
                x = this.medicoResidente;
                break;
            }
            case 1: {
                x = this.medicoGeneral;
                break;
            }
            case 2: {
                x = this.medicoEspecialista;
                break;
            }
        }
        return x;
    }
}
