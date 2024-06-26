import Sistema.Sistema;

import java.io.*;
import java.util.Scanner;

public class Inicio {
    private Sistema sistema;

    public Inicio() throws IOException, ClassNotFoundException {
        String ruta = "./informacion.bin";
        File ficheroUsuarios = new File(ruta);
        if(!ficheroUsuarios.exists()){
            try {
                ficheroUsuarios.createNewFile();
                sistema = new Sistema();
                sistema.initMenu();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        else{
            sistema = deserializarSistema();
            Scanner sc = new Scanner(System.in);
            sistema.menuInicio(sc);
        }
    }

    public Sistema getSistema() {
        return sistema;
    }

    //método encargado de obtener la información en sesiones anteriores
    public Sistema deserializarSistema() throws FileNotFoundException, IOException, ClassNotFoundException {
        String rutaArchivo = "./informacion.bin";
        File fichero = new File(rutaArchivo);
        if (fichero.length()>0){
            ObjectInputStream datosEntrada = new ObjectInputStream(new FileInputStream(rutaArchivo));
            Sistema datos = (Sistema) datosEntrada.readObject();
            return datos;
        }else {
            Sistema sistema = new Sistema();
            sistema.initMenu();
            return sistema;
        }
    }

}
