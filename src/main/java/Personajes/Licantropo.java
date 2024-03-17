package Personajes;

import Equipo.Arma;
import Equipo.Armadura;

import java.io.Serializable;
import java.util.ArrayList;

public class Licantropo extends Personaje implements Serializable {
    private int puntosRabia;

    public Licantropo(String nombre, ArrayList<Arma> listaArmas, ArrayList<Arma> armasActivas, ArrayList<Armadura> listaArmaduras, ArrayList<Esbirro> listaEsbirros, int cantidadOro, int puntosRabia) {
        super(nombre,listaArmas,armasActivas,listaArmaduras,listaEsbirros,cantidadOro);
        this.puntosRabia = puntosRabia;
    }


    @Override
    public void añadirEsbirro(Esbirro esbirro) {
            super.addListaEsbirros(esbirro);
    }

    public int getPuntosRabia() {
        return puntosRabia;
    }

    public void setPuntosRabia(int puntosRabia) {
        this.puntosRabia = puntosRabia;
    }
}
