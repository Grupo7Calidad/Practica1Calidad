package IATest.UnitTest;

import Equipo.Arma;
import Equipo.Armadura;
import Personajes.Esbirro;
import Personajes.Humano;
import Personajes.Vampiro;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VampiroTest {

    @Test
    public void testConstructorAndGetters() {
        String nombre = "Drácula";
        ArrayList<Arma> listaArmas = new ArrayList<>();
        ArrayList<Arma> armasActivas = new ArrayList<>();
        ArrayList<Armadura> listaArmaduras = new ArrayList<>();
        ArrayList<Esbirro> listaEsbirros = new ArrayList<>();
        int cantidadOro = 1000;
        int puntosSangre = 50;
        int edad = 500;

        Vampiro vampiro = new Vampiro(nombre, listaArmas, armasActivas, listaArmaduras, listaEsbirros, cantidadOro, puntosSangre, edad);

        assertEquals(nombre, vampiro.getNombre());
        assertEquals(listaArmas, vampiro.getListaArmas());
        assertEquals(armasActivas, vampiro.getArmasActivas());
        assertEquals(listaArmaduras, vampiro.getListaArmaduras());
        assertEquals(listaEsbirros, vampiro.getListaEsbirros());
        assertEquals(cantidadOro, vampiro.getCantidadOro());
        assertEquals(puntosSangre, vampiro.getPuntosSangre());
        assertEquals(edad, vampiro.getEdad());
    }

    @Test
    public void testSetterAndGetters() {
        Vampiro vampiro = new Vampiro("Drácula", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 1000, 50, 500);

        vampiro.setNombre("Vlad");
        assertEquals("Vlad", vampiro.getNombre());

        ArrayList<Arma> nuevasArmas = new ArrayList<>();
        nuevasArmas.add(new Arma(10, 5, 2, "Espada de la Oscuridad", "Espadas", new ArrayList<>()));
        vampiro.setListaArmas(nuevasArmas);
        assertEquals(nuevasArmas, vampiro.getListaArmas());

        ArrayList<Arma> nuevasArmasActivas = new ArrayList<>();
        nuevasArmasActivas.add(new Arma(15, 8, 2, "Espada de la Sangre", "Espadas", new ArrayList<>()));
        vampiro.setArmasActivas(nuevasArmasActivas);
        assertEquals(nuevasArmasActivas, vampiro.getArmasActivas());

        ArrayList<Armadura> nuevasArmaduras = new ArrayList<>();
        nuevasArmaduras.add(new Armadura(5, 15, "Armadura de la Noche", "Noche", new ArrayList<>()));
        vampiro.setListaArmaduras(nuevasArmaduras);
        assertEquals(nuevasArmaduras, vampiro.getListaArmaduras());

        ArrayList<Esbirro> nuevosEsbirros = new ArrayList<>();
        nuevosEsbirros.add(new Humano("Jonathan Harker", 100, "Castillo de Drácula"));
        vampiro.setListaEsbirros(nuevosEsbirros);
        assertEquals(nuevosEsbirros, vampiro.getListaEsbirros());

        vampiro.setCantidadOro(1500);
        assertEquals(1500, vampiro.getCantidadOro());

        vampiro.setPuntosSangre(70);
        assertEquals(70, vampiro.getPuntosSangre());

        vampiro.setEdad(550);
        assertEquals(550, vampiro.getEdad());
    }

    @Test
    public void testAñadirEsbirro() {
        Vampiro vampiro = new Vampiro("Drácula", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 1000, 50, 500);
        Humano humano = new Humano("Jonathan Harker", 100, "Castillo de Drácula");
        Esbirro esbirro = humano;

        vampiro.añadirEsbirro(esbirro);

        // Se espera que no se añada el esbirro si no es de tipo Humano
        assertEquals(0, vampiro.getListaEsbirros().size());
    }
}
