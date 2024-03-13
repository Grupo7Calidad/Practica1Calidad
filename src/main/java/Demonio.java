import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Demonio extends Esbirro implements Serializable {
    private static final int MAX_ESBIRROS_HIJOS = 3;
    private static final int MAX_TIPOS_ESBIRRO = 3;
    private static final int SALUD_MAXIMA = 3;
    private static final int SALUD_MINIMA = 1;
    private static final int INDEPENDENCIA_MAXIMA = 5;
    private static final int INDEPENDENCIA_MINIMA = 1;
    private static final String[] LEALTADES = {"ALTA", "MEDIA", "BAJA"};

    private List<Esbirro> conjuntoEsbirros = new ArrayList<>();
    private String pacto;

    public Demonio(String nombre, int salud, String pacto) {
        super(nombre, salud);
        this.pacto = pacto;
        this.conjuntoEsbirros = generarEsbirrosAleatorios();
    }

    private List<Esbirro> generarEsbirrosAleatorios() {
        List<Esbirro> esbirros = new ArrayList<>();
        int numEsbirrosHijos = (int) (Math.random() * MAX_ESBIRROS_HIJOS);
        for (int i = 0; i < numEsbirrosHijos; i++) {
            int tipoEsbirro = (int) (Math.random() * MAX_TIPOS_ESBIRRO);
            switch (tipoEsbirro) {
                case 0:
                    esbirros.add(crearHumano());
                    break;
                case 1:
                    esbirros.add(crearGhoul());
                    break;
                case 2:
                    esbirros.add(crearDemonio());
                    break;
            }
        }
        return esbirros;
    }

    private Humano crearHumano() {
        return new Humano(generarNombre("Humano"), generarSalud(), generarLealtad());
    }

    private Ghoul crearGhoul() {
        return new Ghoul(generarNombre("Ghoul"), generarSalud(), generarIndependencia());
    }

    private Demonio crearDemonio() {
        String nombre = generarNombre("Demonio");
        return new Demonio(nombre, generarSalud(), nombre + " ha pactado con " + getNombre());
    }

    private String generarNombre(String tipo) {
        return tipo + " #" + (int) (Math.random() * 10000);
    }

    private int generarSalud() {
        return (int) (Math.random() * (SALUD_MAXIMA - SALUD_MINIMA + 1)) + SALUD_MINIMA;
    }

    private String generarLealtad() {
        return LEALTADES[(int) (Math.random() * LEALTADES.length)];
    }

    private int generarIndependencia() {
        return (int) (Math.random() * (INDEPENDENCIA_MAXIMA - INDEPENDENCIA_MINIMA + 1)) + INDEPENDENCIA_MINIMA;
    }

    public List<Esbirro> getConjuntoEsbirros() {
        return conjuntoEsbirros;
    }

    public void setConjuntoEsbirros(List<Esbirro> conjuntoEsbirros) {
        this.conjuntoEsbirros = conjuntoEsbirros;
    }

    public String getPacto() {
        return pacto;
    }

    public void setPacto(String pacto) {
        this.pacto = pacto;
    }

    @Override
    public void mostrarEsbirro() {
        System.out.println("Tipo: Demonio");
        System.out.println("Nombre: " + getNombre());
        System.out.println("Salud: " + getSalud());
        if (!getConjuntoEsbirros().isEmpty()) {
            System.out.println("Esbirros: ");
            for (Esbirro esbirro : getConjuntoEsbirros()) {
                esbirro.mostrarEsbirro();
            }
        } else {
            System.out.println("Este demonio no tiene más esbirros");
        }
    }
}
