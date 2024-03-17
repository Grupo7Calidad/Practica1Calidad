package Usuario;

import java.io.Serializable;

public class Operador extends Usuario implements Serializable {

    public Operador(String nombre, String nick, String password) {
        super(nombre, nick, password);
    }

    @Override
    public String getNombre() {
        return super.getNombre();
    }

    @Override
    public void setNombre(String nombre) {
        super.setNombre(nombre);
    }

    @Override
    public String getNick() {
        return super.getNick();
    }

    @Override
    public void setNick(String nick) {
        super.setNick(nick);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }
}
