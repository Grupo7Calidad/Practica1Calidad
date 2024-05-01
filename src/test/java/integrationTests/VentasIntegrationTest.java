package integrationTests;

import Personajes.Cazador;
import Personajes.Personaje;
import Sistema.Sistema;
import Usuario.Jugador;
import Ventas.Oferta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VentasIntegrationTest {
    private Sistema sharedSistemaInstance;
    private Jugador sharedSamplePlayer;

    @BeforeEach
    @Test
    // Test: Register a new user and create a new offer, check if that new offer is correctly saved into Sistema
    void registerNewUserAndCreateOffer(){
        // 1) Creating the new player
        Jugador testPlayer = new Jugador(
                "Jorge", "ItsGeorge", "1234", null, null
        );
        sharedSamplePlayer = testPlayer;
        // 2) Adding the new offer
        List<Oferta> offerList = new ArrayList<>();
        Oferta testOffer = new Oferta(
                null, null, 1000, testPlayer
        );
        offerList.add(testOffer);
        // 3) Saving offer
        try {
            Field listaOfertasField = Sistema.class.getDeclaredField("listaOfertasNoValidadas");
            listaOfertasField.setAccessible(true);
            Sistema testSystem = new Sistema();
            listaOfertasField.set(testSystem, offerList);

            sharedSistemaInstance = testSystem;
            assertEquals(testOffer, testSystem.getListaOfertasNoValidadas().get(0));
        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    // Test: Check if the created offers are accessible
    void viewAvailableOffersTest() throws IOException {
        // 1) If there are not any offers
        Sistema testSystem = new Sistema();
        assertEquals(0, testSystem.getListaOfertas().size());
        // 2) If there is at least one existing offer
        assertNotEquals(0, sharedSistemaInstance.getListaOfertas().size() + sharedSistemaInstance.getListaOfertasNoValidadas().size());
    }
    @Test
    // Test: Checks if a purchased items is correctly serialized
    void acceptOfferAndPurchaseTest(){
        // 1) Creating the buyer
        Personaje testCharacter = new Cazador(
                "Eren", null, null, null, null, 2000, 3
        );
        Jugador testPlayer = new Jugador(
                "Matt", "Matt4321", "1234", testCharacter, null
        );
        // 2) Validating an offer
        try {
            Field listaOfertasField = Sistema.class.getDeclaredField("listaOfertas");
            listaOfertasField.setAccessible(true);
            listaOfertasField.set(sharedSistemaInstance, sharedSistemaInstance.getListaOfertasNoValidadas());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        // 3) Verificar el inventario de un jugador
    }
    @Test
    void validateOfferAndPurchaseWithOperatorTest(){
        // Checks if an operator is able to view all new offers and validate them
    }
    @Test
    void doNotSeeNonValidatedOffersTest(){
        // Checks none all non-validated offers can be seen by a user
    }
}
