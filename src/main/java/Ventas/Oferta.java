package Ventas;

import Equipo.Equipo;
import Personajes.Esbirro;
import Usuario.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Oferta implements Serializable {
    private ArrayList<Equipo> listaEquipo;
    private ArrayList<Esbirro> listaEsbirros;
    private int precio;
    private Usuario usuarioVendedor;

    public Usuario getUsuarioVendedor() {
        return usuarioVendedor;
    }

    public Oferta(ArrayList<Equipo> listaEquipo, ArrayList<Esbirro> listaEsbirros, int precio, Usuario usuarioVendedor){
        this.listaEquipo = listaEquipo;
        this.listaEsbirros = listaEsbirros;
        this.precio = precio;
        this.usuarioVendedor = usuarioVendedor;
    }
    public void mostrarOferta(){
        System.out.println("Usuario.Usuario vendedor: " + usuarioVendedor.getNick());
        System.out.println("Precio: " + precio);
        System.out.println("Artículos vendidos: ");
        if (!listaEquipo.isEmpty()){
            String equipoVendido = "";
            for (Equipo equipo: listaEquipo ) {
                equipo.mostrarEquipo();
            }
            System.out.print(equipoVendido);
        }
        if (!listaEsbirros.isEmpty()){
            String esbirrosVendidos = "";
            for (Esbirro esbirro: listaEsbirros ) {
                esbirro.mostrarEsbirro();
            }
            System.out.print(esbirrosVendidos);
        }
    }
    public ArrayList<Equipo> getListaEquipo() {
        return listaEquipo;
    }

    public ArrayList<Esbirro> getListaEsbirros() {
        return listaEsbirros;
    }

    public int getPrecio() {
        return precio;
    }

    public VentaLog generarVentaLog(String usuario){
        Date date = new Date();
        return new VentaLog(date, usuarioVendedor.getNick(), usuario, listaEquipo, listaEsbirros, precio);
    }
}
