package IATest.InterfaceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Sistema.Sistema;
import Usuario.Jugador;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestMostrarNotificaciones {

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
    public void testMostrarNotificaciones() throws Exception {
        Jugador tester = new Jugador("Tester", "test", "12345", null, "42");
        sistema.setUsuario(tester);
        tester.addNotificacion("El test funciona!");
        tester.addNotificacion("Y sigue funcionando!");
        sistema.mostrarNotificaciones();
        String expectedOutput = "El test funciona!\n" +
                "Y sigue funcionando!\n";
        // Comprobar si la salida coincide con lo esperado
        assertEquals(expectedOutput, output.toString().replaceAll("\r", ""));
    }
}