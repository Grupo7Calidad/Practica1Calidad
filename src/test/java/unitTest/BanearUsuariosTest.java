package unitTest;
import Sistema.Sistema;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BanearUsuariosTest {
    @Test
    public void banearUsuarioTest() throws IOException, ClassNotFoundException {

        String data =
                "\n" +
                        "1" + //entrar al menu para registrarse
                        "\n" +
                        "1" + //elegir registrarse como jugador
                        "\nTester" + // nombre
                        "\nTester1" + //nick
                        "\n123412344" + //contraseña
                        "\nPersonaje" + // nombre del personaje
                        "\n200" + //cantidad oro
                        "\n1" +  //Rol de Personaje(cazador)
                        "\n1" + //puntos de voluntad
                        "\n1" + // elegir el tipo de esbirro
                        "\nHumano" + //nombre del esbirro
                        "\n1" + //cantidad de salud del esbirro
                        "\nALTA" + // elegir la lealtad
                        "\n12" + // elegir el arma/armadura para el personaje
                        "\n0" + // salir de la eleccion de arma/armadura
                        "\n1" + //Seleccionas arma activa
                        "\n0" + //entras al menú
                        "\n5" +
                        "\n1" +//registrar
                        "\n2" +// op
                        "\n1234" +//codigo secreto
                        "\nTester" +//nombre
                        "\nTester1OP" +//nick
                        "\n12341234" +//contraseña
                        "\n2" + //entrar al menu avanzado de usuarios
                        "\n1" + //seleccion del menu para banear usuarios
                        "\n0" + // eleccion del usuario a banear
                        "\n5" + // vuelta al menu inicio
                        "\n3"; // acabar programa
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Sistema sistema = new Sistema();
        assertEquals(1, sistema.getWhiteList().size(), "Hay un error en el codigoS");
        assertEquals(1, sistema.getBlackList().size(), "Hay un error en el codigo");
    }
}
