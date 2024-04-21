package IATest.InterfaceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Sistema.Sistema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TestMenuInicio {

    private final ByteArrayInputStream input = new ByteArrayInputStream("3\n".getBytes());
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private Scanner scanner;
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
        System.setIn(input);
        System.setOut(new PrintStream(output));
        scanner = new Scanner(System.in);
    }

    @AfterEach
    public void restoreStreams() {
        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test
    public void testMenuInicio_OpcionSalir() throws Exception{
        // Llamar a la función bajo prueba
        sistema.menuInicio(scanner);
        String expectedOutput = "----------------------------------\r\n" +
                "   Bienvenido al Menu de Inicio   \r\n" +
                "----------------------------------\r\n" +
                "       Seleccione una opción      \r\n" +
                "1 - Registrarse                   \r\n" +
                "2 - Iniciar sesion                \r\n" +
                "3 - Terminar ejecucion\r\n" +
                "----------------------------------\r\n" +
                "Saliendo...\r\n";
        // Comprobar si la salida coincide con lo esperado
        assertEquals(expectedOutput, output.toString());
    }
}
