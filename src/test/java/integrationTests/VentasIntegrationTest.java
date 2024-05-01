package integrationTests;

import Sistema.Sistema;
import Usuario.Jugador;
import Ventas.Oferta;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VentasIntegrationTest {
    @Test
    // Test: Register a new user and create a new offer, check if that new offer is correctly saved into Sistema
    void registerNewUserAndCreateOffer(){
        // 1) Creating the new player
        Jugador testPlayer = new Jugador(
                "Jorge", "ItsGeorge", "1234", null, null
        );
        // 2) Adding the new offer
        List<Oferta> offerList = new ArrayList<>();
        Oferta testOffer = new Oferta(
                null, null, 1000, testPlayer
        );
        offerList.add(testOffer);
        // 3) Saving offer
        try {
            Field listaOfertasField = Sistema.class.getDeclaredField("listaOfertas");
            listaOfertasField.setAccessible(true);
            Sistema testSystem = new Sistema();
            listaOfertasField.set(testSystem, offerList);

            assertEquals(testOffer, testSystem.getListaOfertas().get(0));

        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void viewAvailableOffersTest(){
        // Check that if there are not any offer the app displays a message and if there were all offers are show to a user
    }
    @Test
    void acceptOfferAndPurchaseTest(){
        // Checks if a purchased items is correctly serialized
    }
    @Test
    void updateOfferStateAfterPurchaseTest(){
        // Checks if VentaLog objects are created and serialized
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
