package IATest.UnitTest;


import Equipo.Arma;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArmaTest {

    @Test
    public void testConstructorAndGetters() {
        int modAtaque = 10;
        int modDefensa = 5;
        int numManos = 2;
        String nombre = "Espada de Fuego";
        String categoria = "Espadas";
        ArrayList<String> materiales = new ArrayList<>();
        materiales.add("Acero");
        materiales.add("Fuego");

        Arma arma = new Arma(modAtaque, modDefensa, numManos, nombre, categoria, materiales);

        assertEquals(modAtaque, arma.getModAtaque());
        assertEquals(modDefensa, arma.getModDefensa());
        assertEquals(numManos, arma.getNumManos());
        assertEquals(nombre, arma.getNombre());
        assertEquals(categoria, arma.getCategoria());
        assertEquals(materiales, arma.getListaMateriales());
    }

    @Test
    public void testSetterAndGetters() {
        Arma arma = new Arma(10, 5, 2, "Espada de Fuego", "Espadas", new ArrayList<>());

        arma.setModAtaque(15);
        assertEquals(15, arma.getModAtaque());

        arma.setModDefensa(8);
        assertEquals(8, arma.getModDefensa());

        arma.setNumManos(1);
        assertEquals(1, arma.getNumManos());

        arma.setNombre("Espada de Hielo");
        assertEquals("Espada de Hielo", arma.getNombre());

        arma.setCategoria("Espadas heladas");
        assertEquals("Espadas heladas", arma.getCategoria());

        ArrayList<String> nuevosMateriales = new ArrayList<>();
        nuevosMateriales.add("Hielo");
        nuevosMateriales.add("Acero");
        arma.setListaMateriales(nuevosMateriales);
        assertEquals(nuevosMateriales, arma.getListaMateriales());
    }

    @Test
    public void testMostrarEquipo() {
        Arma arma = new Arma(10, 5, 2, "Espada de Fuego", "Espadas", new ArrayList<>());

        // Redirigir System.out a un ByteArrayOutputStream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Llamar al método mostrarEquipo()
        arma.mostrarEquipo();

        // Obtener la salida del ByteArrayOutputStream como una cadena
        String expectedOutput = "Tipo: Equipo.Arma\n" +
                "Nombre: Espada de Fuego\n" +
                "Modificador de ataque: 10\n" +
                "Modificador de defensa: 5\n" +
                "Categoria: Espadas\n" +
                "Numero de manos: 2\n" +
                "Materiales:\n";
        assertEquals(expectedOutput, outContent.toString().replaceAll("\r", ""));
    }
}
