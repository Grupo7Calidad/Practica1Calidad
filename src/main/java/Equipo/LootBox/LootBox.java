package Equipo.LootBox;

import Personajes.Personaje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class LootBox {
    private List<List<Object>> items = new ArrayList<>();
    private List<List<Double>> probabilidades = new ArrayList<>();

    private final int precioComun = 100;
    private final int precioRaro = 200;
    private final int precioEpico = 300;


    public LootBox() {
        inicializarItems();
        inicializarProbabilidades();
    }

    public LootBox(List<List<Double>> probabilidades) {
        this.probabilidades = probabilidades;
        inicializarItems();
    }

    abstract void inicializarItems();

    private void inicializarProbabilidades() {
        //Probabilidades Comun
        probabilidades.add(Arrays.asList(0.7, 0.2, 0.08, 0.02));
        //Probabilidades Raro
        probabilidades.add(Arrays.asList(0.0, 0.85, 0.1, 0.05));
        //Probabilidades Epico
        probabilidades.add(Arrays.asList(0.0, 0.0, 0.9, 0.1));
    }
    public void abrirLootBox(String categoria, Personaje personaje){
        Random random = new Random();
        double rand = random.nextDouble();
        double cumulativeProbability = 0.0;
        int numCategoria;
        int precio;
        switch(categoria.toLowerCase()){
            case "comun":
                numCategoria = 0;
                precio = precioComun;
                break;
            case "raro":
                numCategoria = 1;
                precio = precioRaro;
                break;
            case "epico":
                numCategoria = 2;
                precio = precioEpico;
                break;
            default:
                throw new IllegalArgumentException("La categoria de LootBox no existe. Unicamente hay Común, Raro y Épico");
        }

        if (precio > personaje.getCantidadOro()){
            throw new ArithmeticException("No tienes suficiente dinero");
        }

        Object premio = null;
        for (int i = 0; i < probabilidades.get(numCategoria).size(); i++) {
            cumulativeProbability += probabilidades.get(numCategoria).get(i);
            if ((rand < cumulativeProbability) && !items.get(i).isEmpty()) {
                int itemCategorySize = items.get(i).size();
                premio = items.get(i).get(random.nextInt(itemCategorySize));
                guardarPremio(premio, personaje);
            }
        }
        if (premio == null){
            throw new IllegalStateException("No hay item válido para esta categoria de lootbox");
        }

        personaje.setCantidadOro(personaje.getCantidadOro()-precio);

        System.out.println("Has obtenido:");
        System.out.println(premio);

    }

    abstract void guardarPremio(Object premio, Personaje personaje);
    public void mostrarItems(){
        for (java.util.List<Object> category : items) {
            for (Object item : category) {
                System.out.println(item.toString());
            }
        }
    }

    public int getPrecioComun() {
        return precioComun;
    }

    public int getPrecioRaro() {
        return precioRaro;
    }

    public int getPrecioEpico() {
        return precioEpico;
    }

    public void mostrarPrecioCategorias() {
        System.out.println("Común: " + precioComun);
        System.out.println("Raro: " + precioRaro);
        System.out.println("Épico: " + precioEpico);
    }

    public List<List<Object>> getItems() {
        return items;
    }

    //Solo para testear
    public void setItems(List<List<Object>> items) {
        this.items = items;
    }

}
