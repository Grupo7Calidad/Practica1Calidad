package unitTest;
import Sistema.Sistema;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import Usuario.Jugador;
import Personajes.Licantropo;
import Personajes.Humano;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class CrearLicantropoConHumanoTest {
    @Test
    public void crearLicantropoConHumano() throws IOException { // hay que borrar los daatos para ejecutar este test
        int numEquipo = 15; //Es un arma
        String data =
                "1" + //registrarse
                        "\n1" + //registrar jugador
                        "\nTester" + //nombre usuario
                        "\nTester1" + //nick
                        "\n123412344" + //contraseña
                        "\nPersonaje" + //nombre personaje
                        "\n500" + //cantidad oro
                        "\n3" +  //Rol de Personaje
                        "\n1" + //puntos de voluntad
                        "\n1" + //tipo de esbirro
                        "\nHumano" + //nombre de esbirro
                        "\n1" + //salud de esbirro
                        "\nALTA" + //lealtad (en este caso)
                        "\n" + numEquipo +//numero de arma a elegir
                        "\n0" + //no quiero más armas
                        "\n1" + //Seleccionas arma activa y como no tengo más me envía al menú
                        "\n0" + //Salir
                        "\n5" + //Log out
                        "\n3"; //Terminar ejecucion
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Sistema sistema = new Sistema();
        assertInstanceOf(Licantropo.class, ((Jugador) sistema.getUsuario()).getPersonaje());
        assertInstanceOf(Humano.class, ((Jugador) sistema.getUsuario()).getPersonaje().getListaEsbirros().get(0));
    }
}
