package ar.edu.utn.frba.dds.hechos;

import ar.edu.utn.frba.dds.criteriodepertenencia.CriterioDePertenencia;
import ar.edu.utn.frba.dds.fuentes.Fuente;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Coleccion {
  private String identificador; // handle alfanumerico unico
  private final String titulo;
  private final String descripcion;
  private final CriterioDePertenencia criterio;
  private final List<Fuente> fuentes; // es una lista porque ahora son muchas fuentes

  public Coleccion(
      String identificador,
      String titulo,
      String descripcion,
      List<Fuente> fuentes,
      CriterioDePertenencia criterio) {
    this.identificador = identificador;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.fuentes = new ArrayList<>(fuentes);
    this.criterio = criterio;
  }

  // --- Getters ---
  public String getTitulo() {
    return titulo;
  }

  public String getIdentificador() {
    return identificador;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public List<Fuente> getFuentes() {
    return Collections.unmodifiableList(fuentes);
  }

  public CriterioDePertenencia getCriterio() {
    return criterio;
  }

  // aca obtengo hechos de TODAS las fuentes aplicando el criterio
  public List<Hecho> getHechos() {
    return fuentes.stream()
        .flatMap(fuente -> fuente.leerHechos().stream())
        .filter(criterio::cumple)
        .collect(Collectors.toList());
  }

  // Versión con criterio adicional
  public List<Hecho> getHechos(CriterioDePertenencia criterioAdicional) {
    return fuentes.stream()
        .flatMap(fuente -> fuente.leerHechos().stream())
        .filter(h -> criterio.cumple(h) && criterioAdicional.cumple(h))
        .collect(Collectors.toList());
  }

  public void agregarFuente(Fuente fuente) {
    this.fuentes.add(fuente);
  }
}
