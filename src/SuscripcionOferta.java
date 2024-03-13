import java.util.List;
import java.util.Scanner;

public class SuscripcionOferta {
    private Scanner sc;
    private String filtro;
    private Usuario usuario; // Asumiendo que hay una clase Usuario
    private List<Notificador> listaNotificadores; // Asumiendo una lista de notificadores

    public SuscripcionOferta(Scanner sc, Usuario usuario, List<Notificador> listaNotificadores) {
        this.sc = sc;
        this.usuario = usuario;
        this.listaNotificadores = listaNotificadores;
    }

    public void suscribirseOferta() {
        mostrarMenuPrincipal();
        int opcion = obtenerOpcionUsuario(0, 7);
        switch (opcion) {
            case 1:
                suscripcionPorTipo();
                break;
            case 2:
                suscripcionPorCategoria();
                break;
            case 3:
                suscripcionPorValor();
                break;
            case 4:
                suscripcionPorLealtad();
                break;
            case 5:
                suscripcionPorTipoEsbirro();
                break;
            case 6:
                suscripcionPorTipoUsuario();
                break;
            case 7:
                suscripcionPorPrecio();
                break;
            case 0:
                return;
        }
        procesarSuscripcion();
    }

    private void mostrarMenuPrincipal() {
        System.out.println("Elige el tipo de oferta al que te quieres suscribir");
        System.out.println("1 - Por tipo de equipo/esbirros");
        System.out.println("2 - Por categoría");
        System.out.println("3 - Por valor");
        System.out.println("4 - Por lealtad de esbirro");
        System.out.println("5 - Por tipo de esbirro");
        System.out.println("6 - Por tipo de usuario que realiza la oferta");
        System.out.println("7 - Por un precio mínimo-máximo");
        System.out.println("0 - Salir");
    }

    private int obtenerOpcionUsuario(int min, int max) {
        int opcion;
        do {
            opcion = sc.nextInt();
        } while (opcion < min || opcion > max);
        return opcion;
    }

    private void suscripcionPorTipo() {
        filtro = "Suscripcion por tipo de equipo/esbirros: \n";
        System.out.println("Elige una opcion para suscribirse");
        System.out.println("1 - Armas");
        System.out.println("2 - Armaduras");
        System.out.println("3 - Esbirros");
        System.out.println("4 - Armas y Armaduras");
        System.out.println("5 - Armas y Esbirros");
        System.out.println("6 - Armaduras y Esbirros");
        System.out.println("0 - Salir");
        int opcion;
        do {
            opcion = sc.nextInt();
        } while (opcion < 0 || opcion > 6);
        switch (opcion) {
            case 1:
                filtro = filtro.concat("-Armas \n");
                break;
            case 2:
                filtro = filtro.concat("-Armaduras \n");
                break;
            case 3:
                filtro = filtro.concat("-Esbirros \n");
                break;
            case 4:
                filtro = filtro.concat("-Armas \n-Armaduras \n");
                break;
            case 5:
                filtro = filtro.concat("-Armas \n-Esbirros \n");
                break;
            case 6:
                filtro = filtro.concat("-Armaduras \n-Esbirros \n");
                break;
        }
    }

    private void suscripcionPorCategoria() {
        filtro = "Suscripcion por categoría: \n";
        System.out.println("Elige una opcion para suscribirse");
        System.out.println("1 - Comun");
        System.out.println("2 - Raro");
        System.out.println("3 - Epico");
        System.out.println("4 - Legendario");
        System.out.println("0 - Salir");
        int opcion;
        do {
            opcion = sc.nextInt();
        } while (opcion < 0 || opcion > 4);
        switch (opcion) {
            case 1:
                filtro = filtro.concat("-Comun \n");
                break;
            case 2:
                filtro = filtro.concat("-Raro \n");
                break;
            case 3:
                filtro = filtro.concat("-Epico \n");
                break;
            case 4:
                filtro = filtro.concat("-Legendario \n");
                break;
        }
    }

    private void suscripcionPorValor() {
        filtro = "Suscripcion por valor de equipo: \n";
        System.out.println("Elige una opcion para suscribirse");
        System.out.println("1 - Valor de Armas");
        System.out.println("2 - Valor de Armaduras");
        System.out.println("0 - Salir");
        int opcion;
        do {
            opcion = sc.nextInt();
        } while (opcion < 0 || opcion > 2);
        switch (opcion) {
            case 1:
                System.out.println("Elige una opcion para suscribirse");
                System.out.println("1 - Modificador de Ataque del arma de 1");
                System.out.println("2 - Modificador de Ataque del arma de 2");
                System.out.println("3 - Modificador de Ataque del arma de 3");
                System.out.println("4 - Modificador de Defensa del arma de 1");
                System.out.println("5 - Modificador de Defensa del arma de 2");
                System.out.println("6 - Modificador de Defensa del arma de 3");
                System.out.println("0 - Salir");
                do {
                    opcion = sc.nextInt();
                } while (opcion < 0 || opcion > 6);
                switch (opcion) {
                    case 1:
                        filtro = filtro.concat("-Modificador de Ataque del arma de 1");
                        break;
                    case 2:
                        filtro = filtro.concat("-Modificador de Ataque del arma de 2");
                        break;
                    case 3:
                        filtro = filtro.concat("-Modificador de Ataque del arma de 3");
                        break;
                    case 4:
                        filtro = filtro.concat("-Modificador de Defensa del arma de 1");
                        break;
                    case 5:
                        filtro = filtro.concat("-Modificador de Defensa del arma de 2");
                        break;
                    case 6:
                        filtro = filtro.concat("-Modificador de Defensa del arma de 3");
                        break;
                }
                break;
            case 2:
                System.out.println("Elige una opcion para suscribirse");
                System.out.println("1 - Modificador de Ataque de la armadura de 1");
                System.out.println("2 - Modificador de Ataque de la armadura de 2");
                System.out.println("3 - Modificador de Ataque de la armadura de 3");
                System.out.println("4 - Modificador de Defensa de la armadura de 1");
                System.out.println("5 - Modificador de Defensa de la armadura de 2");
                System.out.println("6 - Modificador de Defensa de la armadura de 3");
                System.out.println("0 - Salir");
                do {
                    opcion = sc.nextInt();
                } while (opcion < 0 || opcion > 6);
                switch (opcion) {
                    case 1:
                        filtro = filtro.concat("-Modificador de Ataque de la armadura de 1");
                        break;
                    case 2:
                        filtro = filtro.concat("-Modificador de Ataque de la armadura del arma de 2");
                        break;
                    case 3:
                        filtro = filtro.concat("-Modificador de Ataque de la armadura del arma de 3");
                        break;
                    case 4:
                        filtro = filtro.concat("-Modificador de Defensa de la armadura del arma de 1");
                        break;
                    case 5:
                        filtro = filtro.concat("-Modificador de Defensa de la armadura del arma de 2");
                        break;
                    case 6:
                        filtro = filtro.concat("-Modificador de Defensa de la armadura del arma de 3");
                        break;
                }
                break;
        }
    }

    private void suscripcionPorLealtad() {
        filtro = "Suscripcion por lealtad de Esbirro: \n";
        System.out.println("Elige una opcion para suscribirse");
        System.out.println("1 - ALTA");
        System.out.println("2 - MEDIA");
        System.out.println("3 - BAJA");
        System.out.println("0 - Salir");
        int opcion;
        do {
            opcion = sc.nextInt();
        } while (opcion < 0 || opcion > 3);
        switch (opcion) {
            case 1:
                filtro = filtro.concat("-ALTA");
                break;
            case 2:
                filtro = filtro.concat("-MEDIA");
                break;
            case 3:
                filtro = filtro.concat("-BAJA");
                break;
        }
    }

    private void suscripcionPorTipoEsbirro() {
        filtro = "Suscripcion por tipo de esbirro: \n";
        System.out.println("Elige una opcion para suscribirse");
        System.out.println("1 - Demonio");
        System.out.println("2 - Ghoul");
        System.out.println("3 - Cazador");
        System.out.println("0 - Humano");
        int opcion;
        do {
            opcion = sc.nextInt();
        } while (opcion < 0 || opcion > 3);
        switch (opcion) {
            case 1:
                filtro = filtro.concat("-Demonio");
                break;
            case 2:
                filtro = filtro.concat("-Ghoul");
                break;
            case 3:
                filtro = filtro.concat("-Humano");
                break;
        }
    }

    private void suscripcionPorTipoUsuario() {
        filtro = "Suscripcion por tipo de usuario: \n";
        System.out.println("Elige una opcion para suscribirse");
        System.out.println("1 - Licantropo");
        System.out.println("2 - Vampiro");
        System.out.println("3 - Cazador");
        System.out.println("0 - Salir");
        int opcion;
        do {
            opcion = sc.nextInt();
        } while (opcion < 0 || opcion > 3);
        switch (opcion) {
            case 1:
                filtro = filtro.concat("-Licantropo");
                break;
            case 2:
                filtro = filtro.concat("-Vampiro");
                break;
            case 3:
                filtro = filtro.concat("-Cazador");
                break;
        }
    }

    private void suscripcionPorPrecio() {
        int opcion;
        filtro = "Suscripcion por un precio minimo-maximo: \n";
        System.out.println("Elige el precio minimo [0-1000]: ");
        do {
            opcion = sc.nextInt();
        } while (opcion < 0 || opcion > 1000);
        filtro = filtro.concat("Min: " + opcion + " oro \n");
        System.out.println("Elige el precio maximo [0-1000]: ");
        do {
            opcion = sc.nextInt();
        } while (opcion < 0 || opcion > 999);
        filtro = filtro.concat("Max: " + opcion + " oro");

    }

    private void procesarSuscripcion() {
        boolean encontrado = false;
        int i = 0;
        int indice = -1;
        for (Notificador notificador : listaNotificadores) {
            if (notificador.getFiltro().equals(filtro)) {
                encontrado = true;
                indice = i;
                i++;
            }
        }
        if (!encontrado) {
            Notificador notificador = new Notificador(filtro);
            notificador.suscribirse((Observador) usuario);
            listaNotificadores.add(notificador);
            ((Jugador) usuario).addNotificador(notificador);

        } else {
            listaNotificadores.get(indice).suscribirse((Observador) usuario);
            ((Jugador) usuario).addNotificador(listaNotificadores.get(indice));
        }
    }
}
