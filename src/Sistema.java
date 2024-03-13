import java.io.*;
import java.util.*;

public class Sistema implements Serializable {
    private Usuario usuario;
    private List<Usuario> whiteList, blackList = new ArrayList<>();
    private List<Oferta> listaOfertas, listaOfertasNoValidadas = new ArrayList<>();
    private List<Notificador> listaNotificadores = new ArrayList<>();
    private List<VentaLog> listaLogs = new ArrayList<>();
    private List<Arma> conjuntoArmas = new ArrayList<>();
    private List<Armadura> conjuntoArmaduras = new ArrayList<>();
    private Personaje p;
    private SuscripcionOferta suscripcionOferta;

    public Sistema() throws IOException {
        listaOfertas = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        inicializarArmaduras();
        inicializarArmas();
        menuInicio(sc);
        suscripcionOferta = new SuscripcionOferta(sc, usuario, listaNotificadores);
    }

    public void consultarOferta(Scanner sc) {
        if (listaOfertas.isEmpty()) {
            System.out.println("No hay ofertas disponibles");
            return;
        }

        int ofertasMostradas = mostrarOfertas();

        if (ofertasMostradas > 0) {
            manejarSeleccionOferta(sc, ofertasMostradas);
        } else {
            System.out.println("No hay ofertas ajenas disponibles");
        }
    }

    private int mostrarOfertas() {
        int ofertasMostradas = 0;
        for (int i = 0; i < listaOfertas.size(); i++) {
            Oferta oferta = listaOfertas.get(i);
            if (!oferta.getUsuarioVendedor().getNick().equals(usuario.getNick())) {
                System.out.println("Oferta numero " + (i + 1) + ")");
                oferta.mostrarOferta();
                ofertasMostradas++;
            }
        }
        return ofertasMostradas;
    }

    private void manejarSeleccionOferta(Scanner sc, int ofertasMostradas) {
        System.out.println("----------------------------------");
        System.out.println("Seleccione el numero de la oferta que quiere comprar o presione 0 para salir");

        int option = leerOpcionValida(sc, ofertasMostradas);

        if (option != 0) {
            procesarCompraOferta(option);
        }
    }

    private int leerOpcionValida(Scanner sc, int maxOption) {
        int option;
        do {
            while (!sc.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido.");
                sc.next(); // descartar la entrada no válida
            }
            option = sc.nextInt();
        } while (option > maxOption || option < 0);

        return option;
    }

    private void procesarCompraOferta(int option) {
        boolean oroDisponible = comprarOferta(listaOfertas.get(option - 1));
        if (!oroDisponible) {
            System.out.println("No hay oro disponible");
        }
    }

    private boolean comprarOferta(Oferta oferta) {
        Jugador comprador = (Jugador) usuario;
        int cantidadOro = comprador.getPersonaje().getCantidadOro();

        if (!puedeComprar(cantidadOro, oferta.getPrecio())) {
            return false;
        }

        procesarCompra(comprador, oferta);
        return true;
    }

    private boolean puedeComprar(int cantidadOro, int precio) {
        return cantidadOro >= precio;
    }

    private void procesarCompra(Jugador comprador, Oferta oferta) {
        actualizarOroComprador(comprador, oferta.getPrecio());
        transferirEquipos(comprador, oferta);
        transferirEsbirros(comprador, oferta);
        registrarVenta(oferta);
        actualizarOroVendedor(oferta);
        removerOferta(oferta);
    }

    private void actualizarOroComprador(Jugador comprador, int precio) {
        int nuevoOro = comprador.getPersonaje().getCantidadOro() - precio;
        comprador.getPersonaje().setCantidadOro(nuevoOro);
    }

    private void transferirEquipos(Jugador comprador, Oferta oferta) {
        for (Equipo equipo : oferta.getListaEquipo()) {
            if (equipo instanceof Arma) {
                comprador.getPersonaje().addListaArmas((Arma) equipo);
            } else {
                comprador.getPersonaje().addListaArmaduras((Armadura) equipo);
            }
        }
    }

    private void transferirEsbirros(Jugador comprador, Oferta oferta) {
        for (Esbirro esbirro : oferta.getListaEsbirros()) {
            if (!(comprador.getPersonaje() instanceof Vampiro && esbirro instanceof Humano)) {
                comprador.getPersonaje().añadirEsbirro(esbirro);
            } else {
                System.out.println("Un vampiro no puede comprar un humano");
            }
        }
    }

    private void registrarVenta(Oferta oferta) {
        VentaLog ventaLog = oferta.generarVentaLog(usuario.getNombre());
        listaLogs.add(ventaLog);
    }

    private void actualizarOroVendedor(Oferta oferta) {
        Jugador vendedor = (Jugador) oferta.getUsuarioVendedor();
        int nuevoOro = vendedor.getPersonaje().getCantidadOro() + oferta.getPrecio();
        vendedor.getPersonaje().setCantidadOro(nuevoOro);
    }

    private void removerOferta(Oferta oferta) {
        listaOfertas.remove(oferta);
    }


    public void menuInicio(Scanner sc) throws IOException {
        int opcionMI;
        System.out.println("----------------------------------");
        System.out.println("   Bienvenido al Menu de Inicio   ");
        System.out.println("----------------------------------");
        System.out.println("       Seleccione una opción      ");
        System.out.println("1 - Registrarse                   ");
        System.out.println("2 - Iniciar sesion                ");
        System.out.println("3 - Terminar ejecucion");
        System.out.println("----------------------------------");
        do{
            opcionMI = sc.nextInt();
            if(opcionMI < 1 || opcionMI > 3){
                System.out.println("Introduce una opcion correcta");
            }
        } while(opcionMI < 1 || opcionMI > 3);
        switch (opcionMI) {
            case 1:
                usuario = null;
                registrarCuenta(sc);
                break;
            case 2:
                usuario = null;
                iniciarSesion(sc);
                break;
            case 3:
                System.out.println("Saliendo...");
                //System.exit(0);
                break;
        }
    }

    private void registrarCuenta(Scanner sc) throws IOException {
        mostrarMenuRegistro();
        int opcion = obtenerOpcionValida(sc, 1, 3);
        switch (opcion) {
            case 1:
                registrarJugador(sc);
                break;
            case 2:
                registrarOperador(sc);
                break;
            case 3:
                menuInicio(sc);
                return; // Salir del método para no ejecutar el código posterior si se elige volver al menú.
        }

        if (opcion != 3){
            crearUsuario();
            menuPrincipal(sc);
        }
    }

    private void mostrarMenuRegistro() {
        System.out.println("Como quieres registrate:");
        System.out.println("1. Jugador");
        System.out.println("2. Operador");
        System.out.println("3. Volver al menú de inicio");
    }

    private int obtenerOpcionValida(Scanner sc, int min, int max) {
        int opcion;
        do {
            opcion = sc.nextInt();
            if (opcion < min || opcion > max) {
                System.out.println("Introduce una opcion correcta");
            }
        } while (opcion < min || opcion > max);
        return opcion;
    }

    private void registrarJugador(Scanner sc) {
        String nombre = obtenerInput(sc, "Introduce el nombre");
        String nick = obtenerNickValido(sc);
        String contraseña = obtenerContraseñaValida(sc);
        registrarPersonaje(sc);
        Personaje personaje = p; // Asumo que p es una variable de instancia

        String numeroRegistro = calcularNumRegistro();
        Jugador player = new Jugador(nombre, nick, contraseña, personaje, numeroRegistro);
        usuario = player;

        gestionarEquipamientoJugador(sc);
        elegirArmasActivas(sc);
    }

    private void registrarOperador(Scanner sc) {
        System.out.println("Introduzca el codigo secreto o introduzca 1 para cancelar");
        int codigo = obtenerCodigoValido(sc);
        if (codigo == 1) {
            try {
                registrarCuenta(sc);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }

        String nombre = obtenerInput(sc, "Introduce el nombre");
        String nick = obtenerNickValido(sc);
        String contraseña = obtenerContraseñaValida(sc);
        Operador admin = new Operador(nombre, nick, contraseña);
        usuario = admin;
    }

    private String obtenerInput(Scanner sc, String mensaje) {
        System.out.println(mensaje);
        return sc.next();
    }

    private String obtenerNickValido(Scanner sc) {
        String nick;
        do {
            System.out.println("Introduce el nick");
            nick = sc.next();
        } while (encontrarNick(nick));
        return nick;
    }

    private String obtenerContraseñaValida(Scanner sc) {
        String contraseña;
        do {
            System.out.println("Introduce la contraseña");
            contraseña = sc.next();
            if (contraseña.length() < 8 || contraseña.length() > 12) {
                System.out.println("La contraseña debe ser entre 8 y 12 caracteres");
            }
        } while (contraseña.length() < 8 || contraseña.length() > 12);
        return contraseña;
    }

    private void gestionarEquipamientoJugador(Scanner sc) {
        int opcionEquipo;
        do {
            añadirEquipo(sc);
            System.out.println("Si quieres añadir más equipo pulsa 1, sino, pulsa 0");
            opcionEquipo = sc.nextInt();
        } while (opcionEquipo != 0);
    }

    private int obtenerCodigoValido(Scanner sc) {
        int codigo;
        do {
            codigo = sc.nextInt();
            if (codigo != 1 && codigo != 1234) {
                System.out.println("Codigo incorrecto, vuelva intentarlo o cancele la accion");
            }
        } while (codigo != 1 && codigo != 1234);
        return codigo;
    }


    private void crearUsuario() throws IOException {
        if (whiteList != null) {
            whiteList.add(usuario);
            serializarSistema();
        } else {
            whiteList = new ArrayList<>();
            whiteList.add(usuario);
            serializarSistema();
        }
    }

    public void menuPrincipal(Scanner sc) throws IOException {
        if (usuario instanceof Jugador) {
            menuJugador(sc);
        } else {
            menuOperador(sc);
        }
        serializarSistema();
    }

    private void menuOperador(Scanner sc) throws IOException {
        int opcionMOP = 0;

        while (opcionMOP != 5 && opcionMOP != 4) {
            mostrarMenuPrincipal();
            opcionMOP = obtenerOpcionValida(sc);

            switch (opcionMOP) {
                case 1:
                    validarOferta(sc);
                    break;
                case 2:
                    menuUsuario(sc);
                    break;
                case 3:
                    consultarVentas();
                    break;
                case 4:
                    darseDeBaja(sc);
                    break;
                case 5:
                    salir(sc);
                    break;
                default:
                    System.out.println("Opción no válida, por favor intente de nuevo.");
            }
        }
    }

    private void mostrarMenuPrincipal() {
        System.out.println("-----------------------------------------------------");
        System.out.println("Bienvenido al menú principal " + usuario.getNick());
        System.out.println("Elige una de las siguientes opciones");
        System.out.println("1. Validar ofertas");
        System.out.println("2. Gestionar usuarios");
        System.out.println("3. Consultar las ventas realizadas");
        System.out.println("4. Darse de baja");
        System.out.println("5. Salir");
        System.out.println("-----------------------------------------------------");
    }

    private int obtenerOpcionValida(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Por favor, introduzca un número.");
            sc.next(); // descartar la entrada incorrecta
        }
        int opcion = sc.nextInt();
        // Consumir el salto de línea
        sc.nextLine();
        return opcion;
    }


    private void menuJugador(Scanner sc) throws IOException {
        int opcionMJ;
        System.out.println("-----------------------------------------------------");
        System.out.println("Bienvenido al menu principal " + usuario.getNick());
        System.out.println("Elige una de las siguientes opciones");
        System.out.println("1. Gestion avanzada de personaje");
        System.out.println("2. Gestion avanzada de las ofertas");
        System.out.println("3. Darse de baja");
        System.out.println("4. Mostrar Notificaciones");
        System.out.println("5. Salir");
        System.out.println("-----------------------------------------------------");
        if (!((Jugador) usuario).getListaNotificaciones().isEmpty()){
            System.out.println("*¡Tienes nuevas notificaciones!*");
        }
        do{
            opcionMJ = sc.nextInt();
            if(opcionMJ > 5 || opcionMJ < 1){
                System.out.println("Introduce una opcion correcta");
            }
        }while(opcionMJ > 5 || opcionMJ < 1);

        switch (opcionMJ) {
            case 1:
                menuAvanzadoPersonaje(sc);
                break;
            case 2:
                menuAvanzadoOfertas(sc);
                break;
            case 3:
                darseDeBaja(sc);
                break;
            case 4:
                mostrarNotificaciones();
                break;
            case 5:
                salir(sc);
                break;

        }
        if (opcionMJ != 5 && opcionMJ != 3){
            menuPrincipal(sc);
        }
    }

    public void mostrarNotificaciones() {
        ((Jugador) usuario).mostrarNotificaciones();
        ((Jugador) usuario).vaciarListaNotificaciones();
    }

    private void menuAvanzadoPersonaje(Scanner sc) throws IOException {
        mostrarMenuAvanzado();
        int opcionMP2 = obtenerOpcionValida(sc);
        ejecutarAccionSeleccionada(opcionMP2, sc);
    }

    private void mostrarMenuAvanzado() {
        System.out.println("-----------------------------------------------------");
        System.out.println("Bienvenido al menu avanzado para personajes " + usuario.getNick());
        System.out.println("Elige una de las siguientes opciones");
        System.out.println("1. Modificar equipo");
        System.out.println("2. Modificar oro");
        System.out.println("3. Consultar información del personaje");
        System.out.println("4. Volver al menu principal");
        System.out.println("-----------------------------------------------------");
    }

    private void ejecutarAccionSeleccionada(int opcion, Scanner sc) throws IOException {
        switch (opcion) {
            case 1:
                modificarEquipo(sc);
                break;
            case 2:
                modificarOro(sc);
                break;
            case 3:
                consultarInformacionPersonaje();
                break;
            // No es necesario incluir el caso 4 porque no realiza ninguna acción
        }
    }


    private void menuAvanzadoOfertas(Scanner sc) {
        int opcionMOF = -1;
        System.out.println("Bienvenido al menu Avanzado de Ofertas " + usuario.getNick());
        System.out.println("¿Que operacion desea realizar?");
        System.out.println("--------------------------------------");
        System.out.println("1. Crear una oferta");
        System.out.println("2. Buscar y comprar ofertas");
        System.out.println("3. Suscribirse a una oferta");
        System.out.println("4. Desuscribirse a una oferta");
        System.out.println("5. Volver al menu principal");
        System.out.println("--------------------------------------");
        do {
            try {
                System.out.println("Escribe una de las opciones");
                opcionMOF = sc.nextInt();
                switch (opcionMOF) {
                    case 1:
                        crearOferta(sc);
                        break;
                    case 2:
                        consultarOferta(sc);
                        break;
                    case 3:
                        suscripcionOferta.suscribirseOferta();
                        break;
                    case 4:
                        desuscribirse(sc);
                        break;
                    default:
                        System.out.println("Solo números entre 1 y 4");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un número");
                sc.next();
            }
        }while (opcionMOF > 5 || opcionMOF < 1) ;

    }

    private void darseDeBaja(Scanner sc) throws IOException {
        whiteList.remove(usuario);
        System.out.println("Se ha dado de baja correctamente");
        menuInicio(sc);
    }

    public void consultarInformacionPersonaje() throws IOException {
        mostrarOroPersonaje();
        mostrarArmasPersonaje();
        mostrarArmadurasPersonaje();
        mostrarArmasActivasPersonaje();
        mostrarEsbirrosPersonaje();
    }

    private void mostrarOroPersonaje() {
        System.out.println("Cantidad de oro del Personaje: " + p.getCantidadOro() + " monedas de oro");
    }

    private void mostrarArmasPersonaje() {
        System.out.println("Armas del Personaje:");
        if (!p.getListaArmas().isEmpty()) {
            int i = 1;
            for (Arma arma : p.getListaArmas()) {
                if (!p.getArmasActivas().contains(arma)) {
                    System.out.println(i++ + ". " + arma.getNombre());
                }
            }
        } else {
            System.out.println("Este personaje no tiene Armas");
        }
    }

    private void mostrarArmadurasPersonaje() {
        System.out.println("Armaduras del Personaje:");
        if (!p.getListaArmaduras().isEmpty()) {
            int i = 1;
            for (Armadura armadura : p.getListaArmaduras()) {
                System.out.println(i++ + ". " + armadura.getNombre());
            }
        } else {
            System.out.println("Este personaje no tiene Armaduras");
        }
    }

    private void mostrarArmasActivasPersonaje() {
        System.out.println("Armas activas:");
        if (!p.getArmasActivas().isEmpty()) {
            int i = 1;
            for (Arma activa : p.getArmasActivas()) {
                System.out.println(i++ + ".");
                activa.mostrarEquipo();
            }
        } else {
            System.out.println("Este personaje no tiene Armas Activas");
        }
    }

    private void mostrarEsbirrosPersonaje() {
        System.out.println("Esbirros del Personaje:");
        if (!p.getListaEsbirros().isEmpty()) {
            for (Esbirro esbirro : p.getListaEsbirros()) {
                esbirro.mostrarEsbirro();
            }
        } else {
            System.out.println("Este personaje no tiene Esbirros");
        }
    }


    private void salir(Scanner sc) throws IOException {
        //Este método sale de la sesión
        menuInicio(sc);
    }

    private Personaje crearPersonajeBase(Scanner sc) {
        System.out.println("Introduce el nombre del personaje");
        String nombre = sc.next();
        System.out.println("Introduzca la cantidad de oro del personaje");
        int cantidadOro = sc.nextInt();
        ArrayList<Arma> armasActivas = new ArrayList<>();
        ArrayList<Esbirro> listaEsbirros = new ArrayList<>();
        p = new Personaje(nombre, new ArrayList<Arma>(), armasActivas, new ArrayList<Armadura>(), listaEsbirros, cantidadOro) {
            @Override
            public void añadirEsbirro(Esbirro esbirro) {

            }
        };
        return p;
    }

    public void crearEsbirro(Scanner sc) {
        System.out.println("Elige el tipo de esbirro que quieres crear");
        System.out.println("1 - Humano");
        System.out.println("2 - Ghoul");
        System.out.println("3 - Demonio");
        int opcion = sc.nextInt();
        System.out.println("Introduce el nombre del esbirro");
        String nombreEsbirro = sc.next();
        System.out.println("Introduce la salud para el esbirro");
        int salud = sc.nextInt();
        switch (opcion) {
            case 1:
                if (p instanceof Vampiro) {
                    System.out.println("Los vampiros no pueden tener humanos, introduzca otro tipo de esbirro");
                    crearEsbirro(sc);
                } else {
                    System.out.println("Introduce el tipo de lealtad (ALTA,NORMAL o BAJA)");
                    String lealtad = sc.next().toUpperCase();
                    while (!lealtad.equals("ALTA") && !lealtad.equals("NORMAL") && !lealtad.equals("BAJA")) {
                        System.out.println("El tipo de lealtad tiene que ser ALTO,NORMAL o BAJA");
                        lealtad = sc.next().toUpperCase();
                    }
                    Humano h = new Humano(nombreEsbirro, salud, lealtad);
                    p.getListaEsbirros().add(h);
                }
                break;
            case 2:
                boolean error = false;
                System.out.println("Introduce la dependencia");
                int dependencia = 0;
                do {
                    try {
                        dependencia = sc.nextInt();
                    } catch (NumberFormatException e) {
                        System.out.println("El valor debe ser numerico");
                        error = true;
                    }
                    while (dependencia < 1 || dependencia > 5) {
                        System.out.println("La dependencia debe ser un numero entre 1 y 5");
                        dependencia = sc.nextInt();
                    }
                } while (error);
                Ghoul g = new Ghoul(nombreEsbirro, salud, dependencia);
                p.getListaEsbirros().add(g);
                break;
            case 3:
                System.out.println("Dime la descripcion del pacto");
                String descripcion = sc.next();
                Demonio demonio = new Demonio(nombreEsbirro, salud, descripcion);
                p.getListaEsbirros().add(demonio);
                break;
            default:
                System.out.println("Introduce una opcion correcta");
                crearEsbirro(sc);
                break;
        }
    }


    public void desuscribirse(Scanner sc) {
        if (((Jugador) usuario).getListaNotificadores().isEmpty()) {
            System.out.println("No tienes ninguna suscripción!");
        } else {
            int opcion;
            System.out.println("Elija un filtro del que te quieres desuscribir:");
            int i = 0;
            for (Notificador notificador : ((Jugador) usuario).getListaNotificadores()) {
                System.out.println(i + ") " + notificador.getFiltro());
                i++;
            }
            do {
                opcion = sc.nextInt();
            } while (opcion > 0 || opcion > i--);
            ((Jugador) usuario).getListaNotificadores().get(opcion).desuscribirse((Observador) usuario);
            ((Jugador) usuario).getListaNotificadores().remove(opcion);
        }

    }

    private Personaje registrarPersonaje(Scanner sc) {
        int opcionRol;
        p = crearPersonajeBase(sc);
        System.out.println("Elige un rol");
        System.out.println("1. Cazador");
        System.out.println("2. Vampiro");
        System.out.println("3. Licantropo");
        opcionRol = sc.nextInt();
        switch (opcionRol) {
            case 1:
                CrearCazador cazador = new CrearCazador();
                p = cazador.crearPersonaje(p.getNombre(), p.getListaArmas(), p.getArmasActivas(), p.getListaArmaduras(), p.getListaEsbirros(), p.getCantidadOro(), sc);
                break;
            case 2:
                CrearVampiro vampiro = new CrearVampiro();
                p = vampiro.crearPersonaje(p.getNombre(), p.getListaArmas(), p.getArmasActivas(), p.getListaArmaduras(), p.getListaEsbirros(), p.getCantidadOro(), sc);
                break;
            case 3:
                CrearLicantropo licantropo = new CrearLicantropo();
                p = licantropo.crearPersonaje(p.getNombre(), p.getListaArmas(), p.getArmasActivas(), p.getListaArmaduras(), p.getListaEsbirros(), p.getCantidadOro(), sc);
                break;
            default:
                System.out.println("Introduce una opcion correcta");
                registrarPersonaje(sc);
                break;
        }
        crearEsbirro(sc);
        return p;
    }


    public void iniciarSesion(Scanner sc) throws IOException {

            System.out.println("Nombre de usuario");
            String nick = sc.next();
            System.out.println("Contraseña");
            String contraseña = sc.next();

            if (comprobarSesion(nick, contraseña) && !encontrarBaneado(nick)) {
                usuario = atribuirUsuario(nick,contraseña);
                if (usuario != null)
                    menuPrincipal(sc);
                else{
                    menuInicio(sc);
                }
            } else if (!comprobarSesion(nick, contraseña)) {
                System.out.println("Inicio de sesion erroneo vuelva a intentarlo");
                System.out.println();
                menuInicio(sc);
            } else {
                System.out.println("Su usuario esta baneado");
                System.out.println();
                menuInicio(sc);
            }
    }

    private Usuario atribuirUsuario(String nick, String contraseña) {
        if (whiteList != null) {
            int i = 0;
            boolean registrado = false;
            while (i < whiteList.size() && !registrado) {
                registrado = whiteList.get(i).getNick().equals(nick) && whiteList.get(i).getPassword().equals(contraseña);
                i = i + 1;
            }
            if (registrado && whiteList.get(i - 1) instanceof Jugador) {
                usuario = whiteList.get(i - 1);
                p = ((Jugador) whiteList.get(i - 1)).getPersonaje();
            }
            return whiteList.get(i-1);
        } else {
            System.out.println("Error");
        }
        return usuario;
    }

    public void modificarEquipo(Scanner sc) throws IOException {
        System.out.println("Seleccione una opcion");
        System.out.println("1. Añadir Equipo");
        System.out.println("2. Eliminar Equipo");
        System.out.println("3. Elegir Armas Activas");
        System.out.println("4. Volver al menu principal");
        int opcion;
        do {
            opcion = sc.nextInt();
            if(opcion < 1 || opcion > 4){
                System.out.println("Introduzca una opcion valida");
            }
        } while(opcion < 1 || opcion > 4);
        switch (opcion) {
            case 1:
                añadirEquipo(sc);
                break;
            case 2:
                eliminarEquipo(sc);
                break;
            case 3:
                elegirArmasActivas(sc);
                break;
        }
    }

    private void añadirEquipo(Scanner sc) {
        ArrayList<Equipo> listaEquipo = new ArrayList<>();
        System.out.println("Seleccione el equipo que desea añadir");
        System.out.println("Armaduras:");
        int i = 1;
        for (Armadura armadura : conjuntoArmaduras) {
            if (!((Jugador) usuario).getPersonaje().getListaArmaduras().contains(armadura)) {
                System.out.println("Numero: " + i + ")");
                armadura.mostrarEquipo();
                System.out.println();
                listaEquipo.add(armadura);
                i++;
            }
        }
        System.out.println("Armas:");
        for (Arma arma : conjuntoArmas) {
            if (!((Jugador) usuario).getPersonaje().getListaArmas().contains(arma)) {
                System.out.println("Numero: " + i + ")");
                arma.mostrarEquipo();
                System.out.println();
                listaEquipo.add(arma);
                i++;
            }
        }
        int opcion;
        do {
            opcion = sc.nextInt();
        } while (opcion > listaEquipo.size() || opcion < 1 );
        Equipo e = listaEquipo.get(opcion-1);
        if (e instanceof Arma) {
            if (((Jugador) usuario).getPersonaje().getListaArmas().size() < 4) {
                ((Jugador) usuario).getPersonaje().addListaArmas((Arma) e);
            } else {
                System.out.println("No puedes añadir mas de 3 armas a tu personaje");
            }
        } else {
            if (((Jugador) usuario).getPersonaje().getListaArmaduras().size() < 4) {
                ((Jugador) usuario).getPersonaje().addListaArmaduras((Armadura) e);
            } else {
                System.out.println("No puedes añadir mas de 3 armaduras a tu personaje");
            }
        }
    }

    private void eliminarEquipo(Scanner sc) {
        if (equipoEstaVacio()) {
            System.out.println("No tienes equipo para eliminar");
        } else {
            ArrayList<Equipo> listaEquipo = mostrarEquiposDisponibles();
            eliminarEquipoSeleccionado(sc, listaEquipo);
        }
    }

    private boolean equipoEstaVacio() {
        return ((Jugador) usuario).getPersonaje().getListaArmas().isEmpty() &&
                ((Jugador) usuario).getPersonaje().getListaArmaduras().isEmpty();
    }

    private ArrayList<Equipo> mostrarEquiposDisponibles() {
        ArrayList<Equipo> listaEquipo = new ArrayList<>();
        mostrarArmaduras(listaEquipo);
        mostrarArmas(listaEquipo);
        return listaEquipo;
    }

    private void mostrarArmaduras(ArrayList<Equipo> listaEquipo) {
        if (!((Jugador) usuario).getPersonaje().getListaArmaduras().isEmpty()) {
            System.out.println("Armaduras:");
            int i = 1;
            for (Armadura armadura : ((Jugador) usuario).getPersonaje().getListaArmaduras()) {
                System.out.println("Numero: " + i + ")");
                armadura.mostrarEquipo();
                System.out.println();
                listaEquipo.add(armadura);
                i++;
            }
        }
    }

    private void mostrarArmas(ArrayList<Equipo> listaEquipo) {
        if (!((Jugador) usuario).getPersonaje().getListaArmas().isEmpty()) {
            System.out.println("Armas:");
            int i = 1;
            for (Arma arma : ((Jugador) usuario).getPersonaje().getListaArmas()) {
                System.out.println("Numero: " + i + ")");
                arma.mostrarEquipo();
                System.out.println();
                listaEquipo.add(arma);
                i++;
            }
        }
    }

    private void eliminarEquipoSeleccionado(Scanner sc, ArrayList<Equipo> listaEquipo) {
        int opcion;
        do {
            opcion = sc.nextInt();
        } while (opcion > listaEquipo.size() || opcion < 1);

        Equipo equipo = listaEquipo.get(opcion - 1);
        eliminarEquipoDeJugador(equipo);
    }

    private void eliminarEquipoDeJugador(Equipo equipo) {
        if (equipo instanceof Arma) {
            ((Jugador) usuario).getPersonaje().removeListaArmas((Arma) equipo);
            if (((Jugador) usuario).getPersonaje().getArmasActivas().contains(equipo)) {
                ((Jugador) usuario).getPersonaje().removeArmasActivas((Arma) equipo);
            }
        } else {
            ((Jugador) usuario).getPersonaje().removeListaArmaduras((Armadura) equipo);
        }
    }

    private void elegirArmasActivas(Scanner sc) {
        Jugador jugador = (Jugador) usuario;
        List<Arma> listaArmas = jugador.getPersonaje().getListaArmas();
        List<Arma> armasActivas = jugador.getPersonaje().getArmasActivas();

        if (verificarArmasDisponibles(listaArmas)) return;

        armasActivas.clear();
        System.out.println("Elija una o dos armas activas o pulse 0 para salir\n");

        seleccionarArmas(sc, listaArmas, armasActivas);
    }

    private boolean verificarArmasDisponibles(List<Arma> listaArmas) {
        if (listaArmas.isEmpty()) {
            System.out.println("Este personaje no tiene armas, añada armas al personaje para poder añadirlas a las armas activas");
            return true;
        }
        return false;
    }

    private void seleccionarArmas(Scanner sc, List<Arma> listaArmas, List<Arma> armasActivas) {
        int numeroArmasSeleccionadas = 0;

        while (numeroArmasSeleccionadas < 2) {
            mostrarOpcionesArma(listaArmas, armasActivas);
            int opcion = obtenerOpcionValida(sc, listaArmas.size());

            if (opcion == 0) break;

            Arma armaSeleccionada = listaArmas.get(opcion - 1);
            if (agregarArmaSeleccionada(armasActivas, armaSeleccionada)) {
                numeroArmasSeleccionadas++;
            }
        }

        mostrarMensajeLimiteArmas(numeroArmasSeleccionadas);
    }

    private void mostrarOpcionesArma(List<Arma> listaArmas, List<Arma> armasActivas) {
        int numeroOpcion = 1;
        for (Arma arma : listaArmas) {
            if (!armasActivas.contains(arma)) {
                System.out.println("Arma " + numeroOpcion + ":\n");
                arma.mostrarEquipo();
                numeroOpcion++;
            }
        }
        System.out.println();
    }

    private int obtenerOpcionValida(Scanner sc, int maxOpcion) {
        int opcion = sc.nextInt();
        while (opcion < 0 || opcion > maxOpcion) {
            System.out.println("Ese número no es válido, por favor escoja un número válido para poder elegir el arma\n");
            opcion = sc.nextInt();
        }
        return opcion;
    }

    private boolean agregarArmaSeleccionada(List<Arma> armasActivas, Arma armaSeleccionada) {
        if (!armasActivas.contains(armaSeleccionada)) {
            armasActivas.add(armaSeleccionada);
            System.out.println("Si quieres salir pulsa 0, sino, pulse 1");
            return true;
        } else {
            System.out.println("El arma ya está seleccionada, elige otra.");
            return false;
        }
    }

    private void mostrarMensajeLimiteArmas(int numeroArmasSeleccionadas) {
        if (numeroArmasSeleccionadas >= 2) {
            System.out.println("Lo sentimos, no puede añadir más de 2 armas activas");
        }
    }

    public List<Oferta> getListaOfertasNoValidadas() {
        return listaOfertasNoValidadas;
    }

    private void inicializarArmas() {
        //armas ofensivas
        ArrayList<String> materiales1 = new ArrayList<>(Arrays.asList("Cobre", "Hierro", "Madera"));
        ArrayList<String> materiales2 = new ArrayList<>(Arrays.asList("Acero", "Hierro"));
        ArrayList<String> materiales3 = new ArrayList<>(Arrays.asList("Diamante", "Hierro", "Madera"));
        ArrayList<String> materiales4 = new ArrayList<>(Arrays.asList("Acero", "Cuero"));
        ArrayList<String> materiales5 = new ArrayList<>(Arrays.asList("Plata", "Acero"));
        ArrayList<String> materiales6 = new ArrayList<>(Arrays.asList("Madera"));
        ArrayList<String> materiales7 = new ArrayList<>(Arrays.asList("Cuero"));
        Arma espadaPequeña = new Arma(2, 0, 1, "Espada pequeña", "Comun", materiales2);
        Arma espadon = new Arma(3, 1, 2, "Espadón", "Raro", materiales1);
        Arma guadanya = new Arma(3, 1, 2, "Guadaña", "Raro", materiales3);
        Arma palo = new Arma(1, 1, 2, "¡Un Palo!", "Comun", materiales6);
        Arma cuchillo = new Arma(1, 1, 1, "Cuchillo", "Comun", materiales4);
        Arma guantesMagicos = new Arma(3, 1, 2, "Guantes Mágicos", "Epico", materiales7);
        Arma varitaMagica = new Arma(2, 1, 2, "Varita Mágica", "Legendario", materiales3);
        Arma varitaNoTanMagica = new Arma(1, 1, 2, "Varita No Tan Mágica", "Comun", materiales6);
        Arma ocarina = new Arma(1, 1, 2, "Ocarina", "Raro", materiales6);
        Arma bumeran = new Arma(2, 1, 1, "Bumerán", "Comun", materiales6);
        Arma bfs = new Arma(2, 1, 1, "B.F.Sword", "Epico", materiales5);
        Arma bajoAutoestima = new Arma(1, 1, 1, "Bajo Autoestima", "Legendario", materiales3);

        //armas defensivas
        Arma escudoPequeño = new Arma(1, 2, 1, "Escudo Pequeño", "Comun", materiales1);
        Arma escudoGrande = new Arma(1, 3, 1, "Escudo Grande", "Raro", materiales1);
        Arma hologramaFormacionTortuga = new Arma(1, 1, 1, "Holograma Formación Tortuga, (solamente intimida.)", "Epico", materiales5);

        conjuntoArmas = Arrays.asList(espadaPequeña, espadon, guadanya, palo, cuchillo, guantesMagicos, varitaMagica, varitaNoTanMagica, ocarina, bumeran, bfs, bajoAutoestima, escudoPequeño, escudoGrande, hologramaFormacionTortuga);

    }

    private void inicializarArmaduras() {
        //armadura
        ArrayList<String> materiales1 = new ArrayList<>(Arrays.asList("Algodon"));
        ArrayList<String> materiales2 = new ArrayList<>(Arrays.asList("Cuero"));
        ArrayList<String> materiales3 = new ArrayList<>(Arrays.asList("Hiero", "Acero", "Cuero", "Diamante"));
        ArrayList<String> materiales4 = new ArrayList<>(Arrays.asList("Diamante", "Hierro"));
        Armadura camisetaPrimark = new Armadura(1, 1, "Camiseta Primark", "Comun", materiales1);
        Armadura armaduraBasica = new Armadura(1, 3, "Armadura Básica", "Raro", materiales2);
        Armadura armaduraTortuga = new Armadura(1, 3, "Armadura Tortuga", "Epico", materiales4);
        Armadura armaduraDentada = new Armadura(2, 2, "Armadura Dentada", "Legendaria", materiales3);

        conjuntoArmaduras = Arrays.asList(camisetaPrimark, armaduraBasica, armaduraTortuga, armaduraDentada);

    }

    public void serializarSistema() throws IOException {
        String rutaArchivo = "./informacion.bin";
        File f1 = new File(rutaArchivo);
        ObjectOutputStream datosSalida = new ObjectOutputStream(new FileOutputStream(f1));
        datosSalida.writeObject(this);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getWhiteList() {
        return whiteList;
    }

    private Boolean comprobarSesion(String nick, String contraseña) {
        if (whiteList != null) {
            int i = 0;
            boolean registrado = false;
            while (i < whiteList.size() && !registrado) {
                registrado = whiteList.get(i).getNick().equals(nick) && whiteList.get(i).getPassword().equals(contraseña);
                i = i + 1;
            }
            if (registrado && whiteList.get(i - 1) instanceof Jugador) {
                usuario = whiteList.get(i - 1);
                p = ((Jugador) whiteList.get(i - 1)).getPersonaje();
            }
            return registrado;
        } else {
            return false;
        }
    }

    public void modificarOro(Scanner sc) {
        int cantidadOro = ((Jugador) usuario).getPersonaje().getCantidadOro();
        System.out.println("1) Sumar oro");
        System.out.println("2) Restar oro");
        int opcion;
        do {
            opcion = sc.nextInt();
        } while (opcion < 1 || opcion > 2);
        if (opcion == 1) {
            System.out.println("Indica cuánto oro quieres sumarte entre 0 y 1000");
        } else {
            System.out.println("Indica cuánto oro quieres restarte entre 0 y 1000");
        }
        int oroASumar = -1;
        while (oroASumar < 0 || oroASumar > 1000) {
            oroASumar = sc.nextInt();
            if (oroASumar < 0 || oroASumar > 1000) {
                System.out.println("Vuélvelo a intentar, introduzca un número entre 0 y 1000");
            }
        }
        if (opcion == 1) {
            cantidadOro += oroASumar;
        } else {
            cantidadOro -= oroASumar;
            if (cantidadOro < 0) {
                cantidadOro = 0;
            }
        }

        ((Jugador) usuario).getPersonaje().setCantidadOro(cantidadOro);
        System.out.println("El oro se ha modificado correctamente");
        System.out.println("Nuevo saldo: " + ((Jugador) usuario).getPersonaje().getCantidadOro());
    }

    public void menuUsuario(Scanner sc){
        int opcionMU;
        System.out.println("-----------------------------------------------------");
        System.out.println("Bienvenido al menu de gestion de usuarios " + usuario.getNick());
        System.out.println("Elige una de las siguientes opciones");
        System.out.println("1. Banear usuario");
        System.out.println("2. Desbanear usuario");
        System.out.println("3. Volver al menu del operador");
        System.out.println("-----------------------------------------------------");
        do{
            opcionMU = sc.nextInt();
            if(opcionMU < 1 || opcionMU > 3){
                System.out.println("Introduce una opcion correcta");
            }
        } while(opcionMU < 1 || opcionMU > 3);
        switch (opcionMU) {
            case 1:
                banearUsuario(sc);
                break;
            case 2:
                desbanearUsuario(sc);
                break;
        }
    }

    public List<Usuario> getBlackList() {
        return blackList;
    }

    private void banearUsuario(Scanner sc) {
        if (!whiteList.isEmpty()) {
            System.out.println("¿Qué usuario quieres banear?");
            int i = 0;
            for (Usuario user : whiteList) {
                System.out.println(i + ") " + user.getNick());
                i += 1;
            }
            int opcion = -1;
            i -= 1;
            while (opcion < 0 || opcion > i) {
                opcion = sc.nextInt();
            }

            Usuario user = whiteList.get(opcion);
            whiteList.remove(user);
            blackList.add(user);
        } else {
            System.out.println("No hay jugadores para banear");
        }
    }

    private void desbanearUsuario(Scanner sc) {
        if (!blackList.isEmpty()) {
            System.out.println("¿Qué usuario quieres desbanear?");
            int i = 0;
            for (Usuario user : blackList) {
                System.out.println(i + ") " + ((Jugador) user).getNick());
                i += 1;
            }
            i -= 1;
            int opcion = -1;
            while (opcion < 0 || opcion > i) {
                opcion = sc.nextInt();
            }

            Usuario user = blackList.get(opcion);
            blackList.remove(user);
            whiteList.add(user);
        } else {
            System.out.println("No hay jugadores baneados");
        }
    }

    public void consultarVentas() {
        if (!listaLogs.isEmpty()) {
            int i = 0;
            for (VentaLog log : listaLogs) {
                System.out.println(i + ") ");
                log.imprimirLog();
                i += 1;
            }
        } else {
            System.out.println("No tuvo lugar ninguna venta");
        }
    }

    public void crearOferta(Scanner sc) {
        ArrayList<Equipo> listaEquipo = new ArrayList<>();
        ArrayList<Esbirro> listaEsbirros = new ArrayList<>();
        int opcion = -1;
        int opcion2 = -1;
        int i;
        int contador = 0;
        while (opcion < 4 && contador < 3) {
            System.out.println("Introduzca el tipo de artículo que quieres vender: ");
            System.out.println("1) Armas");
            System.out.println("2) Armadura");
            System.out.println("3) Esbirros");
            System.out.println("4) Cancelar");
            if (contador >= 1) {
                System.out.println("5) Finalizar oferta");
                do {
                    opcion = sc.nextInt();
                } while (opcion > 5 || opcion < 1);
            } else {
                do{
                    opcion = sc.nextInt();
                } while (opcion > 4 || opcion < 1);
            }
            i = 0;
            switch (opcion) {
                case 1:
                    if (((Jugador) usuario).getPersonaje().getListaArmas().isEmpty()) {
                        System.out.println("No se ha encontrado nada!");
                    } else {
                        for (Arma arma : ((Jugador) usuario).getPersonaje().getListaArmas()) {
                            System.out.println(i + ") ");
                            arma.mostrarEquipo();
                            i++;
                        }
                        i -= 1;
                        while (opcion2 > i || opcion2 < 0) {
                            opcion2 = sc.nextInt();
                        }
                        listaEquipo.add(((Jugador) usuario).getPersonaje().getListaArmas().get(opcion2));
                        contador += 1;
                    }
                    break;
                case 2:
                    if (((Jugador) usuario).getPersonaje().getListaArmaduras().isEmpty()) {
                        System.out.println("No se ha encontrado nada!");
                    } else {
                        for (Armadura armadura : ((Jugador) usuario).getPersonaje().getListaArmaduras()) {
                            System.out.println(i + ") ");
                            armadura.mostrarEquipo();
                            i++;
                        }
                        i -= 1;
                        while (opcion2 > i || opcion2 < 0) {
                            opcion2 = sc.nextInt();
                        }
                        listaEquipo.add(((Jugador) usuario).getPersonaje().getListaArmaduras().get(opcion2));
                        contador += 1;
                    }
                    break;
                case 3:
                    if (((Jugador) usuario).getPersonaje().getListaEsbirros().isEmpty()) {
                        System.out.println("No se ha encontrado nada!");
                    } else {
                        for (Esbirro esbirro : ((Jugador) usuario).getPersonaje().getListaEsbirros()) {
                            System.out.println(i + ") ");
                            esbirro.mostrarEsbirro();
                            i++;
                        }
                        i -= 1;
                        while (opcion2 > i || opcion2 < 0) {
                            opcion2 = sc.nextInt();
                        }
                        listaEsbirros.add(((Jugador) usuario).getPersonaje().getListaEsbirros().get(opcion2));
                        contador += 1;
                    }
                    break;
            }
        }

        if (opcion != 4) {
            System.out.println("Introduzca un precio valido: ");
            int precio;
            do {
                precio = sc.nextInt();
            } while (precio < 0);
            Oferta oferta = new Oferta(listaEquipo, listaEsbirros, precio, usuario);
            listaOfertasNoValidadas.add(oferta);

            //Quitar armas del inventario del usuario
            for (Equipo equipo : listaEquipo) {
                if (equipo instanceof Arma) {
                    ((Jugador) usuario).getPersonaje().getListaArmas().remove((Arma) equipo);
                } else {
                    ((Jugador) usuario).getPersonaje().getListaArmaduras().remove((Armadura) equipo);
                }
            }
            for (Esbirro esbirro : listaEsbirros) {
                ((Jugador) usuario).getPersonaje().getListaEsbirros().remove(esbirro);
            }
        }

    }

    public void validarOferta(Scanner sc) {
        if (listaOfertasNoValidadas.isEmpty()) {
            System.out.println("No hay ofertas para validar.");
            return;
        }

        int opcion;
        do {
            mostrarMenuPrincipalOferta();
            opcion = obtenerOpcionValida(sc, 1, 2);

            if (opcion == 1) {
                procesarValidacionOferta(sc);
            }
        } while (opcion != 2 && !listaOfertasNoValidadas.isEmpty());

        if (listaOfertasNoValidadas.isEmpty()) {
            System.out.println("No hay mas ofertas para validar.");
        }
    }

    private void mostrarMenuPrincipalOferta() {
        System.out.println("1) Validar ofertas");
        System.out.println("2) Salir");
    }
    private void procesarValidacionOferta(Scanner sc) {
        mostrarOfertas();
        int indiceOferta = obtenerOpcionValida(sc, 0, listaOfertasNoValidadas.size() - 1);

        System.out.println("¿Desea validar la oferta?");
        System.out.println("0) Si");
        System.out.println("1) No");

        int confirmacion = obtenerOpcionValida(sc, 0, 1);

        if (confirmacion == 0) {
            validarOferta(indiceOferta);
        } else {
            devolverItemsVendedor(indiceOferta);
        }
    }

    private void validarOferta(int indiceOferta) {
        Oferta oferta = listaOfertasNoValidadas.remove(indiceOferta);
        listaOfertas.add(oferta);
        notificarOferta(oferta);
        System.out.println("La oferta ha sido validada");
    }

    private void devolverItemsVendedor(int indiceOferta) {
        Oferta oferta = listaOfertasNoValidadas.remove(indiceOferta);
        for (Equipo equipo : oferta.getListaEquipo()) {
            if (equipo instanceof Arma) {
                ((Jugador) oferta.getUsuarioVendedor()).getPersonaje().getListaArmas().add((Arma) equipo);
            } else {
                ((Jugador) oferta.getUsuarioVendedor()).getPersonaje().getListaArmaduras().add((Armadura) equipo);
            }
        }
        for (Esbirro esbirro : oferta.getListaEsbirros()) {
            ((Jugador) oferta.getUsuarioVendedor()).getPersonaje().getListaEsbirros().add(esbirro);
        }
        System.out.println("Los articulos fueron devueltos al inventario del usuario vendedor");
    }


    private Boolean encontrarNick(String nick) {
        if (whiteList != null && blackList != null) {
            int i = 0;
            boolean encontrado = false;
            while (i < whiteList.size() && !encontrado) {
                encontrado = whiteList.get(i).getNick().equals(nick);
                i = i + 1;
            }
            i = 0;
            while (i < blackList.size() && !encontrado) {
                encontrado = blackList.get(i).getNick().equals(nick);
                i = i + 1;
            }
            return encontrado;
        } else {
            return false;
        }
    }

    private Boolean encontrarBaneado(String nick) {
        if (!blackList.isEmpty()) {
            int i = 0;
            boolean encontrado = false;
            while (i < blackList.size() && !encontrado) {
                encontrado = blackList.get(i).getNick().equals(nick);
                i = i + 1;
            }
            return encontrado;
        } else {
            return false;
        }
    }

    private Boolean encontrarNumReg(String numReg) {
        if (whiteList != null) {
            int i = 0;
            boolean encontrado = false;
            while (i < whiteList.size() && !encontrado) {
                if (whiteList.get(i) instanceof Jugador) {
                    Jugador user = (Jugador) whiteList.get(i);
                    encontrado = user.getNumRegistro().equals(numReg);

                }
                i++;
            }
            i = 0;
            while (i < blackList.size() && !encontrado) {
                Jugador user2 = (Jugador) blackList.get(i);
                encontrado = user2.getNumRegistro().equals(numReg);
                i = i + 1;
            }
            return encontrado;
        } else {
            return false;
        }
    }

    private String calcularNumRegistro() {
        Random rd = new Random();
        String numero;
        do {
            numero = new String();
            char n = (char) (rd.nextInt(26) + 'a');
            String caracter = String.valueOf(n);
            numero = numero.concat(caracter);
            for (int i = 0; i < 2; i++) {
                n = (char) (rd.nextInt(9) + '0');
                caracter = String.valueOf(n);
                numero = numero.concat(caracter);
            }
            for (int i = 0; i < 2; i++) {
                n = (char) ((char) rd.nextInt(26) + 'a');
                caracter = String.valueOf(n);
                numero = numero.concat(caracter);
            }
        } while (encontrarNumReg(numero));
        return numero;
    }

    public List<Oferta> getListaOfertas() {
        return listaOfertas;
    }

    private void notificarOferta(Oferta oferta) {
        Jugador jugador = (Jugador) oferta.getUsuarioVendedor();

        for (Notificador notificador : listaNotificadores) {
            String filtro = notificador.getFiltro();
            RangoPrecio rangoPrecio = extraerRangoPrecio(filtro);

            boolean notificar = deberiaNotificar(oferta, filtro, jugador, rangoPrecio);

            if (notificar) {
                notificador.añadirOferta(oferta);
                notificador.notificar();
            }
        }
    }

    private RangoPrecio extraerRangoPrecio(String filtro) {
        if (filtro.startsWith("Min: ")) {
            String[] parts = filtro.split("\n");
            int min = Integer.parseInt(parts[0].replace("Min: ", "").replace(" oro \n", ""));
            int max = Integer.parseInt(parts[1].replace("Max: ", "").replace(" oro", ""));
            return new RangoPrecio(min, max);
        }
        return new RangoPrecio(-1, -1); // Rango no especificado
    }

    private boolean deberiaNotificar(Oferta oferta, String filtro, Jugador jugador, RangoPrecio rangoPrecio) {
        if (!oferta.getListaEquipo().isEmpty()) {
            return verificarNotificacionPorEquipo(oferta, filtro);
        } else if (!oferta.getListaEsbirros().isEmpty()) {
            return verificarNotificacionPorEsbirros(oferta, filtro);
        } else if (oferta.getPrecio() <= rangoPrecio.max && oferta.getPrecio() >= rangoPrecio.min) {
            return true;
        } else if (filtro.contains(jugador.getPersonaje().getClass().getName())) {
            return true;
        }
        return false;
    }

    private boolean verificarNotificacionPorEquipo(Oferta oferta, String filtro) {
        boolean hayArma = false, hayArmadura = false;
        for (Equipo equipo : oferta.getListaEquipo()) {
            if (filtro.contains(equipo.getCategoria())) {
                return true;
            }
            if (equipo instanceof Arma) {
                hayArma = true;
            } else {
                hayArmadura = true;
            }
        }
        return (filtro.contains("Armas") && hayArma) || (filtro.contains("Armaduras") && hayArmadura);
    }

    private boolean verificarNotificacionPorEsbirros(Oferta oferta, String filtro) {
        if (filtro.contains("Esbirros")) {
            return true;
        }
        for (Esbirro esbirro : oferta.getListaEsbirros()) {
            if (esbirro instanceof Humano && filtro.contains(((Humano) esbirro).getLealtad())) {
                return true;
            } else if (filtro.contains(esbirro.getClass().getName())) {
                return true;
            }
        }
        return false;
    }

    private class RangoPrecio {
        int min;
        int max;

        RangoPrecio(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }

    public List<VentaLog> getListaLogs() {
        return listaLogs;
    }

    public List<Arma> getConjuntoArmas() {
        return conjuntoArmas;
    }

    public List<Armadura> getConjuntoArmaduras() {
        return conjuntoArmaduras;
    }
}

