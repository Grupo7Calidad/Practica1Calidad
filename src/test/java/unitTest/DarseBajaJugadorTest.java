package unitTest;

import org.junit.jupiter.api.Test;
import Sistema.Sistema;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DarseBajaJugadorTest {
    @Test
    public void darseDeBajaJugadorTest() throws IOException {
        String data =
                "\n1" +//registrar
                        "\n1" +// jugador
                        "\nTester" +//nombre
                        "\nTester1" +//nick
                        "\n12345678" +//contraseña
                        "\nPersonaje" +//nombre del personaje
                        "\n200" +//cantidad de oro
                        "\n1" +//rol personaje
                        "\n1" +//puntos de voluntad
                        "\n1" +//tipo de esbirro
                        "\nEsbirro" +//nombre del esbirro
                        "\n1" +//salud del esbirro
                        "\nALTA" +//tipo de lealtad
                        "\n19" +//seleccione equipo a añadir
                        "\n0" +//no se añaden mas
                        "\n1" +//elegir arma activa
                        "\n0" +//darse de baja
                        "\n3"+ //darse de baja
                        "\n3"; //terminar ejecucicon

        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Sistema sistema = new Sistema();
        assertEquals(0, sistema.getWhiteList().size(), "Hay un error en el codigo");
    }
}
