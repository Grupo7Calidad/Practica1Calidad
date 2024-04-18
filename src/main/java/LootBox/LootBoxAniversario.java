package LootBox;

import Equipo.Arma;
import Equipo.Armadura;

import java.util.ArrayList;
import java.util.Arrays;

public class LootBoxAniversario extends LootBoxEquipo{

    @Override
    void inicializarItems() {
        Arma espadaLargaAniversario = new Arma(3, 1, 2, "Espada Larga Aniversario", "Comun", new ArrayList<>(Arrays.asList("Hierro", "Madera")));
        Arma ballestaPlatinoAniversario = new Arma(2, 2, 2, "Ballesta de Platino Aniversario", "Raro", new ArrayList<>(Arrays.asList("Platino", "Acero")));
        Arma lanzaCelestialAniversario = new Arma(4, 3, 1, "Lanza Celestial Aniversario", "Epico", new ArrayList<>(Arrays.asList("Oricalco", "Diamante")));
        Arma hachaMalditaAniversario = new Arma(3, 2, 1, "Hacha Maldita Aniversario", "Legendario", new ArrayList<>(Arrays.asList("Obsidiana", "Acero")));

        Armadura tunicaArcanaAniversario = new Armadura(2, 2, "Túnica Arcana Aniversario", "Comun", new ArrayList<>(Arrays.asList("Seda", "Lana")));
        Armadura corazaDraconicaAniversario = new Armadura(3, 3, "Coraza Dracónica Aniversario", "Raro", new ArrayList<>(Arrays.asList("Dragón", "Acero")));
        Armadura escudoCentenarioAniversario = new Armadura(2, 3, "Escudo Centenario Aniversario", "Epico", new ArrayList<> (Arrays.asList("Adamantita", "Platino")));
        Armadura yelmoInmortalAniversario = new Armadura(3, 3, "Yelmo Inmortal Aniversario", "Legendario", new ArrayList<> (Arrays.asList("Mithril", "Diamante")));

        // Equipo Comun
        getItems().add(Arrays.asList(espadaLargaAniversario, tunicaArcanaAniversario));

        // Equipo Raro
        getItems().add(Arrays.asList(ballestaPlatinoAniversario, corazaDraconicaAniversario));

        // Equipo Epico
        getItems().add(Arrays.asList(lanzaCelestialAniversario, escudoCentenarioAniversario));

        // Equipo Legendario
        getItems().add(Arrays.asList(hachaMalditaAniversario, yelmoInmortalAniversario));
    }
}
