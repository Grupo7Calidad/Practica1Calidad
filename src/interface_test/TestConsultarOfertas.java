package interface_test;

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

public class TestConsultarOfertas {

    private final ByteArrayInputStream input = new ByteArrayInputStream("2\n".getBytes());
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private Scanner scanner;
    private static Sistema sistema;

    @BeforeAll
    public static void setup() {
        try {
            sistema = new Sistema();
        } catch (Exception e) {
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
    public void testConsultarOfertas() throws Exception {
        sistema.consultarOferta(scanner);
        String expectedOutput = "No hay ofertas disponibles\n";
        // Comprobar si la salida coincide con lo esperado
        assertEquals(expectedOutput, output.toString().replaceAll("\r", ""));
    }
}