package IATest.UnitTest;

import Equipo.Armadura;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArmaduraTest {

    @Test
    public void testConstructorAndGetters() {
        int modAtaque = 5;
        int modDefensa = 15;
        String nombre = "Armadura de Placas";
        String categoria = "Placas";
        ArrayList<String> materiales = new ArrayList<>();
        materiales.add("Acero");
        materiales.add("Cuero");

        Armadura armadura = new Armadura(modAtaque, modDefensa, nombre, categoria, materiales);

        assertEquals(modAtaque, armadura.getModAtaque());
        assertEquals(modDefensa, armadura.getModDefensa());
        assertEquals(nombre, armadura.getNombre());
        assertEquals(categoria, armadura.getCategoria());
        assertEquals(materiales, armadura.getListaMateriales());
    }

    @Test
    public void testSetterAndGetters() {
        Armadura armadura = new Armadura(5, 15, "Armadura de Placas", "Placas", new ArrayList<>());

        armadura.setModAtaque(8);
        assertEquals(8, armadura.getModAtaque());

        armadura.setModDefensa(20);
        assertEquals(20, armadura.getModDefensa());

        armadura.setNombre("Armadura de Mithril");
        assertEquals("Armadura de Mithril", armadura.getNombre());

        armadura.setCategoria("Mithril");
        assertEquals("Mithril", armadura.getCategoria());

        ArrayList<String> nuevosMateriales = new ArrayList<>();
        nuevosMateriales.add("Mithril");
        nuevosMateriales.add("Adamantium");
        armadura.setListaMateriales(nuevosMateriales);
        assertEquals(nuevosMateriales, armadura.getListaMateriales());
    }

    @Test
    public void testMostrarEquipo() {
        Armadura armadura = new Armadura(5, 15, "Armadura de Placas", "Placas", new ArrayList<>());

        // Redirigir System.out a un ByteArrayOutputStream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Llamar al método mostrarEquipo()
        armadura.mostrarEquipo();

        // Obtener la salida del ByteArrayOutputStream como una cadena
        String expectedOutput = "Tipo: Equipo.Armadura\n" +
                "Nombre: Armadura de Placas\n" +
                "Modificador de ataque: 5\n" +
                "Modificador de defensa: 15\n" +
                "Categoria: Placas\n" +
                "Materiales:\n";
        assertEquals(expectedOutput, outContent.toString().replaceAll("\r", ""));
    }
}
