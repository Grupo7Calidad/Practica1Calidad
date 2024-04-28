package IATest.InterfaceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Sistema.Sistema;
import Usuario.Jugador;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Equipo.Arma;
import Personajes.Cazador;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TestCrearOferta {
    /*Inputs:
    1: Vender armas
    0: La primera opcion de armas (guantes)
    -1: Dejar de elegir armas
    4: Cancelar la venta
    5: Salir de la opción
    */
    private final ByteArrayInputStream input = new ByteArrayInputStream("1\n0\n-1\n4\n5\n".getBytes());
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
    public void testCrearOferta() throws Exception {
        Jugador tester = new Jugador("Tester", "test", "12345", null, "42");

        ArrayList<String> materiales2 = new ArrayList<>(Arrays.asList("Cuero"));
        Arma guantesMagicos = new Arma(3, 1, 2, "Guantes Mágicos", "Epico", materiales2);
        ArrayList<Arma> armas = new ArrayList<>();
        armas.add(guantesMagicos);

        Cazador personaje = new Cazador("Ejemplo", armas, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 200, 2);
        tester.setPersonaje(personaje);
        sistema.setUsuario(tester);

        sistema.crearOferta(scanner);
        String expectedOutput = "Introduzca el tipo de artículo que quieres vender: \n" +
                "1) Armas\n" +
                "2) Equipo.Armadura\n" +
                "3) Esbirros\n" +
                "4) Cancelar\n" +
                "0) \n" +
                "Tipo: Equipo.Arma\n" +
                "Nombre: Guantes Mágicos\n" +
                "Modificador de ataque: 3\n" +
                "Modificador de defensa: 1\n" +
                "Categoria: Epico\n" +
                "Numero de manos: 2\n" +
                "Materiales:\n" +
                "-Cuero\n" +
                "Introduzca el tipo de artículo que quieres vender: \n" +
                "1) Armas\n" +
                "2) Equipo.Armadura\n" +
                "3) Esbirros\n" +
                "4) Cancelar\n" +
                "5) Finalizar oferta\n";
        assertEquals(expectedOutput, output.toString().replaceAll("\r", ""));
    }
}