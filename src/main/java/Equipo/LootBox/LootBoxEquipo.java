package Equipo.LootBox;

import Equipo.Arma;
import Equipo.Armadura;
import Personajes.Personaje;

public abstract class LootBoxEquipo extends LootBox{
    @Override
    void guardarPremio(Object premio, Personaje personaje) {
        if (premio instanceof Arma){
            personaje.getListaArmas().add((Arma) premio);
        } else if (premio instanceof Armadura) {
            personaje.getListaArmaduras().add((Armadura) premio);
        } else{
            throw new IllegalArgumentException("No es de tipo Equipo el premio");
        }
    }
}
