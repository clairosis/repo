package ar.edu.utn.frba.dds.hechos;

import ar.edu.utn.frba.dds.criteriodepertenencia.CriterioDePertenencia;
import ar.edu.utn.frba.dds.fuentes.Fuente;
import java.util.List;
import java.util.stream.Collectors;

public class Coleccion {
  private final String titulo;
  private final Fuente fuente;
  private final String descripcion;
  private final CriterioDePertenencia criterio;

  public Coleccion(
      String titulo, String descripcion, Fuente fuente, CriterioDePertenencia criterio) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.fuente = fuente;
    this.criterio = criterio;
  }

  // --- Getters ---
  public String getTitulo() {
    return titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public Fuente getFuente() {
    return fuente;
  }

  public CriterioDePertenencia getCriterio() {
    return criterio;
  }

  public List<Hecho> getHechos() {
    return fuente.leerHechos().stream().filter(criterio::cumple).collect(Collectors.toList());
  }

  public List<Hecho> getHechos(CriterioDePertenencia criterioAdicional) {
    return fuente.leerHechos().stream()
        .filter(h -> criterioAdicional.cumple(h) && criterio.cumple(h))
        .collect(Collectors.toList());
  }
}
