import java.io.Serializable;
import java.util.Scanner;

public class Usuario implements Serializable {
    private String nombre;
    private String nick;
    private String password;
    private String numReistro;

    public Usuario(){
    }

    public Usuario(String nombre, String nick, String password) {
        this.nombre = nombre;
        this.nick = nick;
        this.password = password;
    }

    public void registro(){
        try (Scanner sc = new Scanner(System.in)){
            System.out.println("Introduce tu nombre");
            nombre = sc.nextLine();

        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumReistro() {
        return numReistro;
    }

    public void setNumReistro(String numReistro) {
        this.numReistro = numReistro;
    }
}
