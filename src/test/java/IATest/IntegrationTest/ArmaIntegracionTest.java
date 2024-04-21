package IATest.IntegrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.*;
import java.util.ArrayList;

import Equipo.Arma;
import org.junit.jupiter.api.Test;

public class ArmaIntegracionTest {

    @Test
    void testSerializacionDeserializacion() {
        // Crear una instancia de Arma
        ArrayList<String> listaMateriales = new ArrayList<>();
        listaMateriales.add("Acero");
        Arma armaOriginal = new Arma(10, 5, 1, "Espada larga", "Corte", listaMateriales);

        // Serialización
        try {
            FileOutputStream fileOut = new FileOutputStream("arma.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(armaOriginal);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }

        // Deserialización
        Arma armaDeserializada = null;
        try {
            FileInputStream fileIn = new FileInputStream("arma.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            armaDeserializada = (Arma) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Verificar que el arma deserializada no sea nula
        assertNotNull(armaDeserializada);

        // Verificar que los atributos del arma deserializada sean iguales a los del arma original
        assertEquals(armaOriginal.getModAtaque(), armaDeserializada.getModAtaque());
        assertEquals(armaOriginal.getModDefensa(), armaDeserializada.getModDefensa());
        assertEquals(armaOriginal.getNombre(), armaDeserializada.getNombre());
        assertEquals(armaOriginal.getCategoria(), armaDeserializada.getCategoria());
        assertEquals(armaOriginal.getNumManos(), armaDeserializada.getNumManos());
        assertEquals(armaOriginal.getListaMateriales().size(), armaDeserializada.getListaMateriales().size());
        for (int i = 0; i < armaOriginal.getListaMateriales().size(); i++) {
            assertEquals(armaOriginal.getListaMateriales().get(i), armaDeserializada.getListaMateriales().get(i));
        }
    }
}