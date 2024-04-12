package Equipo.LootBox;

import Equipo.Arma;
import Equipo.Armadura;

import java.util.ArrayList;
import java.util.Arrays;

public class LootBoxDefault extends LootBoxEquipo {

    @Override
    void inicializarItems() {
        Arma espadaLarga = new Arma(2, 0, 1, "Espada Larga", "Comun", new ArrayList<>(Arrays.asList("Hierro", "Madera")));
        Arma lanzaPlata = new Arma(2, 1, 2, "Lanza de Plata", "Raro", new ArrayList<>(Arrays.asList("Plata", "Hierro")));
        Arma arcoElfico = new Arma(3, 2, 3, "Arco Élfico", "Epico", new ArrayList<>(Arrays.asList("Madera", "Diamante")));
        Arma dagaObsidiana = new Arma(2, 1, 2, "Daga de Obsidiana", "Legendario", new ArrayList<>(Arrays.asList("Obsidiana", "Hierro")));

        Armadura camisaLino = new Armadura(1, 1, "Camisa de Lino", "Comun", new ArrayList<>(Arrays.asList("Lino", "Algodon")));
        Armadura petoForjado = new Armadura(2, 2, "Peto Forjado", "Raro", new ArrayList<>(Arrays.asList("Platino", "Hierro")));
        Armadura capaOrnamentada = new Armadura(1, 2, "Capa Ornamentada", "Raro", new ArrayList<> (Arrays.asList("Oro", "Diamante")));
        Armadura armaduraRoble = new Armadura(2, 3, "Armadura de Roble", "Epico", new ArrayList<> (Arrays.asList("Madera", "Acero")));
        Armadura armaduraTitanica = new Armadura(3, 3, "Armadura Titánica", "Legendario", new ArrayList<> (Arrays.asList("Titanio", "Cuero")));

        //Equipo Comun
        getItems().add(Arrays.asList(espadaLarga, camisaLino));

        //Equipo Raro
        getItems().add(Arrays.asList(lanzaPlata, petoForjado, capaOrnamentada));

        //Equipo Epico
        getItems().add(Arrays.asList(arcoElfico, armaduraRoble));

        //Equipo Legendario
        getItems().add(Arrays.asList(dagaObsidiana, armaduraTitanica));
    }


}
