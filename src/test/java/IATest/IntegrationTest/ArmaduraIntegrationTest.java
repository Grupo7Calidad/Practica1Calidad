package IATest.IntegrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.*;
import java.util.ArrayList;

import Equipo.Armadura;
import org.junit.jupiter.api.Test;

public class ArmaduraIntegrationTest {

    @Test
    void testSerializacionDeserializacion() {
        // Crear una instancia de Armadura
        Armadura armaduraOriginal = new Armadura(5, 10, "Armadura de Placas", "Pesada", new ArrayList<>());
        armaduraOriginal.getListaMateriales().add("Hierro");
        armaduraOriginal.getListaMateriales().add("Acero");

        // Serialización
        try {
            FileOutputStream fileOut = new FileOutputStream("armadura.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(armaduraOriginal);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }

        // Deserialización
        Armadura armaduraDeserializada = null;
        try {
            FileInputStream fileIn = new FileInputStream("armadura.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            armaduraDeserializada = (Armadura) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Verificar que la armadura deserializada no sea nula
        assertNotNull(armaduraDeserializada);

        // Verificar que los atributos de la armadura deserializada sean los mismos que los de la armadura original
        assertEquals(armaduraOriginal.getModAtaque(), armaduraDeserializada.getModAtaque());
        assertEquals(armaduraOriginal.getModDefensa(), armaduraDeserializada.getModDefensa());
        assertEquals(armaduraOriginal.getNombre(), armaduraDeserializada.getNombre());
        assertEquals(armaduraOriginal.getCategoria(), armaduraDeserializada.getCategoria());
        assertEquals(armaduraOriginal.getListaMateriales().size(), armaduraDeserializada.getListaMateriales().size());
        for (int i = 0; i < armaduraOriginal.getListaMateriales().size(); i++) {
            assertEquals(armaduraOriginal.getListaMateriales().get(i), armaduraDeserializada.getListaMateriales().get(i));
        }
    }
}