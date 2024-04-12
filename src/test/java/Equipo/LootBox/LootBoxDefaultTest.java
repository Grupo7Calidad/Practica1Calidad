package Equipo.LootBox;

import Equipo.Arma;
import Equipo.Armadura;
import Personajes.Esbirro;
import Personajes.Personaje;
import Personajes.Vampiro;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LootBoxDefaultTest {

    private LootBoxDefault lootBoxDefault = new LootBoxDefault();
    private int oroInicial = 500;
    private Personaje personaje = new Vampiro("Test", new ArrayList<Arma>(), new ArrayList<Arma>(), new ArrayList<Armadura>(), new ArrayList<Esbirro>(), oroInicial, 3, 18);

    private Arma espadaLarga = new Arma(2, 0, 1, "Espada Larga", "Comun", new ArrayList<>(Arrays.asList("Hierro", "Madera")));
    private Arma lanzaPlata = new Arma(2, 1, 2, "Lanza de Plata", "Raro", new ArrayList<>(Arrays.asList("Plata", "Hierro")));
    private Armadura armaduraTitanica = new Armadura(3, 3, "Armadura Titánica", "Legendario", new ArrayList<> (Arrays.asList("Titanio", "Cuero")));

    @Test
    void testGuardarPremio() {
        lootBoxDefault.guardarPremio(espadaLarga, personaje);
        lootBoxDefault.guardarPremio(armaduraTitanica, personaje);

        assert(personaje.getListaArmas().contains(espadaLarga) && personaje.getListaArmaduras().contains(armaduraTitanica));
    }

    @Test
    void testGuardadoAbrirLootBox() {
        lootBoxDefault.abrirLootBox("comun", personaje);
        assert(personaje.getListaArmaduras().size() + personaje.getListaArmas().size() >= 1);
    }

    @Test
    void testOroCategoriaAbrirLootBox() {
        personaje.setCantidadOro(oroInicial);
        lootBoxDefault.abrirLootBox("comun", personaje);
        assert((personaje.getCantidadOro() + lootBoxDefault.getPrecioComun()) == oroInicial);

        personaje.setCantidadOro(oroInicial);
        lootBoxDefault.abrirLootBox("raro", personaje);
        assert((personaje.getCantidadOro() + lootBoxDefault.getPrecioRaro()) == oroInicial);

        personaje.setCantidadOro(oroInicial);
        lootBoxDefault.abrirLootBox("epico", personaje);
        assert((personaje.getCantidadOro() + lootBoxDefault.getPrecioEpico()) == oroInicial);

        personaje.setCantidadOro(0);
        assertThrows(ArithmeticException.class, () -> {
            lootBoxDefault.abrirLootBox("comun", personaje);
        });

    }

    @Test
    void testCategoriaPremioAbrirLootBox() {
        lootBoxDefault.setItems(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(espadaLarga)), new ArrayList<>(), new ArrayList<>(), new ArrayList<>())));
        assertThrows(IllegalStateException.class, () -> {
            lootBoxDefault.abrirLootBox("raro", personaje);
        });

        lootBoxDefault.setItems(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(espadaLarga)), new ArrayList<>(Arrays.asList((lanzaPlata))), new ArrayList<>(), new ArrayList<>())));
        assertThrows(IllegalStateException.class, () -> {
            lootBoxDefault.abrirLootBox("epico", personaje);
        });

        lootBoxDefault.setItems(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(espadaLarga)), new ArrayList<>(Arrays.asList((lanzaPlata))), new ArrayList<>(), new ArrayList<>(Arrays.asList(armaduraTitanica)))));
        lootBoxDefault.abrirLootBox("epico", personaje);
        assert(personaje.getListaArmaduras().contains(armaduraTitanica));
    }

    @Test
    void testMostrarItems() {
    }

    @Test
    void testInicializarItems() {
    }
}