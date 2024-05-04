package unitTest;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import Usuario.Jugador;
import Equipo.Arma;
import Sistema.Sistema;

public class ModificarEquipoTest {
    @Test
    public void modificarEquipoTest() throws IOException, ClassNotFoundException {
        int numEquipo1 = 15; //Es un arma
        int numEquipo2 = 14;
        String data = "1" +
                "\n1" + //registrar jugador
                "\nTester" + //nombre usuario
                "\nTester1" + //nick
                "\n123412344" + //contraseña
                "\nPersonaje" + //nombre personaje
                "\n500" + //cantidad oro
                "\n1" +  //Rol de Personaje
                "\n1" + //puntos de voluntad
                "\n1" + //tipo de esbirro
                "\nHumano" + //nombre de esbirro
                "\n1" + //salud de esbirro
                "\nALTA" + //lealtad (en este caso)
                "\n" + numEquipo1 +//numero de arma a elegir
                "\n0" + //no quiero más armas
                "\n1" + //Seleccionas arma activa y como no tengo más me envía al menú
                "\n0" +
                "\n1" + //Gestion avanzada del personaje
                "\n1" + //modificarEquipo()
                "\n2" + //eliminarEquipo()
                "\n1" + //eliminar arma 1 (Debe eliminar esta arma de armas activas también)
                "\n1" + //Gestion avanzada del personaje
                "\n1" + //modificarEquipo()
                "\n3" + //elegirArmasActivas() (Debe mandarme de nuevo al menú porque no tengo armas en el inventario)
                "\n1" + //Gestion avanzada del personaje
                "\n1" + //modificarEquipo()
                "\n2" + //eliminarEquipo() //intento eliminar un equipo cuando no tengo equipo para eliminar
                "\n1" + //Gestion avanzada del personaje
                "\n1" + //modificarEquipo()
                "\n1" +  //añadirEquipo()
                "\n" + numEquipo1 + //equipar la misma arma del principio
                "\n1" + //Gestion avanzada del personaje
                "\n1" + //modificarEquipo()
                "\n1" +  //añadirEquipo()
                "\n" + numEquipo2 + //Equipar otra arma
                "\n1" + //Gestion avanzada del personaje
                "\n1" + //modificarEquipo()
                "\n3" + //elegirArmasActivas()
                "\n1" + //mi primer arma activa
                "\n1" + //selecciono que quiero añadir una segunda arma activa
                "\n2" + ///mi segunda arma activa
                "\n1" + //intentar añadir una tercera arma activa (No te permite)
                "\n5" +//salir
                "\n3" + //terminar ejecución del programa
                "\n3";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Sistema sistema = new Sistema();
        Arma arma1 = sistema.getConjuntoArmas().get(numEquipo1 - 5); //10, arma1 = B.F. sword
        Arma arma2 = sistema.getConjuntoArmas().get(numEquipo2 - 5);//9, arma2 = Bumerán
        boolean solucion = false;
        if (((Jugador) sistema.getUsuario()).getPersonaje().getArmasActivas().contains(arma1) && ((Jugador) sistema.getUsuario()).getPersonaje().getArmasActivas().contains(arma2)) {
            solucion = true;
        }
        assert (solucion);
    }
}
