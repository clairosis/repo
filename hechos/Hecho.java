package ar.edu.utn.frba.dds.hechos;

import ar.edu.utn.frba.dds.origen.Origen;
import java.time.LocalDate;

public class Hecho {
  private final Header header;
  private final Ubicacion ubicacion;
  private final LocalDate fechaHecho;
  private final LocalDate fechaCarga = LocalDate.now();
  private final Origen origen;
  private final String multimedia;

  // --- Constructores ---

  public Hecho(
      Header header, Ubicacion ubicacion, LocalDate fechaHecho, Origen origen, String multimedia) {
    this.header = header;
    this.ubicacion = ubicacion;
    this.fechaHecho = fechaHecho;
    this.origen = origen;
    this.multimedia = multimedia;
  }

  public Hecho(Header header, Ubicacion ubicacion, LocalDate fechaHecho, Origen origen) {
    this.header = header;
    this.ubicacion = ubicacion;
    this.fechaHecho = fechaHecho;
    this.origen = origen;
    this.multimedia = null;
  }

  // --- Getters ---

  public Header getHeader() {
    return this.header;
  }

  public String getTitulo() {
    return this.header.getTitulo();
  }

  public String getDescripcion() {
    return this.header.getDescripcion();
  }

  public String getCategoria() {
    return this.header.getCategoria();
  }

  public Ubicacion getUbicacion() {
    return this.ubicacion;
  }

  public Float getLatitud() {
    return this.ubicacion.getLatitud();
  }

  public Float getLongitud() {
    return this.ubicacion.getLongitud();
  }

  public LocalDate getFechaHecho() {
    return this.fechaHecho;
  }

  public LocalDate getFechaCarga() {
    return this.fechaCarga;
  }

  public Origen getOrigen() {
    return this.origen;
  }

  public String getMultimedia() {
    return this.multimedia;
  }
}
