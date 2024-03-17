package Personajes;

import Equipo.Arma;
import Equipo.Armadura;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Creator implements Serializable {
    public abstract Personaje crearPersonaje(String nombre, ArrayList<Arma> listaArmas, ArrayList<Arma> armasActivas, ArrayList<Armadura> listaArmaduras, ArrayList<Esbirro> listaEsbirros, int cantidadOro, Scanner sc);
}
