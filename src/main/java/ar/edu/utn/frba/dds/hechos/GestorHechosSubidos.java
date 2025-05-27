package ar.edu.utn.frba.dds.hechos;

import java.util.ArrayList;
import java.util.List;

public class GestorHechosSubidos {

  private static final GestorHechosSubidos instancia = new GestorHechosSubidos();
  private static final List<Hecho> hechosSubidos = new ArrayList<>();

  public static GestorHechosSubidos getInstancia() {
    return instancia;
  }

  public List<Hecho> getHechosSubidos() {
    return new ArrayList<Hecho>(hechosSubidos);
  }

  public static void agregarHecho(Hecho hecho) {
    hechosSubidos.add(hecho);
  }

  public static void eliminarHecho(Hecho hecho) {
    hechosSubidos.remove(hecho);
  }

  public static void limpiarLista() {
    hechosSubidos.clear();
  }
}
