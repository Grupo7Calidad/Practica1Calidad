package IATest.InterfaceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Sistema.Sistema;
import Usuario.Jugador;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestMenuAvanzado {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private static Sistema sistema;

    @BeforeAll
    public static void setup(){
        try {
            sistema = new Sistema();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    public void testMostrarMenuAvanzado() {
        sistema.setUsuario(new Jugador("", "", "", null, ""));
        // Llamar a la función bajo prueba
        sistema.mostrarMenuAvanzado();
        String expectedOutput = "-----------------------------------------------------\n" +
                "Bienvenido al menu avanzado para personajes " + sistema.getUsuario().getNick() + "\n" +
                "Elige una de las siguientes opciones\n" +
                "1. Modificar equipo\n" +
                "2. Modificar oro\n" +
                "3. Consultar información del personaje\n" +
                "4. Volver al menu principal\n" +
                "-----------------------------------------------------\n";
        // Comprobar si la salida coincide con lo esperado
        assertEquals(expectedOutput, output.toString().replaceAll("\r", ""));
    }
}
