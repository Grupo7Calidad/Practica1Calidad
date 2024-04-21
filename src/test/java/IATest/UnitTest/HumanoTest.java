package IATest.UnitTest;

import Personajes.Humano;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HumanoTest {

    @Test
    public void testConstructorAndGetters() {
        String nombre = "John";
        int salud = 100;
        String lealtad = "Reino de Eldoria";

        Humano humano = new Humano(nombre, salud, lealtad);

        assertEquals(nombre, humano.getNombre());
        assertEquals(salud, humano.getSalud());
        assertEquals(lealtad, humano.getLealtad());
    }

    @Test
    public void testSetterAndGetters() {
        Humano humano = new Humano("John", 100, "Reino de Eldoria");

        humano.setNombre("Arthur");
        assertEquals("Arthur", humano.getNombre());

        humano.setSalud(80);
        assertEquals(80, humano.getSalud());

        humano.setLealtad("Reino de Camelot");
        assertEquals("Reino de Camelot", humano.getLealtad());
    }

    @Test
    public void testMostrarEsbirro() {
        Humano humano = new Humano("John", 100, "Reino de Eldoria");

        // Redirigir System.out a un ByteArrayOutputStream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Llamar al método mostrarEsbirro()
        humano.mostrarEsbirro();

        // Obtener la salida del ByteArrayOutputStream como una cadena
        String expectedOutput = "Tipo: Personajes.Humano\n" +
                "Nombre: John\n" +
                "Salud: 100\n" +
                "Lealtad: Reino de Eldoria\n";
        assertEquals(expectedOutput, outContent.toString().replaceAll("\r", ""));
    }
}
