package ar.edu.utn.frba.dds.hechos;

import ar.edu.utn.frba.dds.origen.Origen;
import java.time.LocalDate;

public class Hecho {
  private final Header header;
  private final Ubicacion ubicacion;
  private final LocalDate fechaHecho;
  private final LocalDate fechaCarga;
  private final Origen origen;
  private final String multimedia;

  // --- Constructores ---

  public Hecho(Hecho hecho) {
    this.header = hecho.header;
    this.ubicacion = hecho.ubicacion;
    this.fechaHecho = hecho.fechaHecho;
    this.fechaCarga = hecho.fechaCarga;
    this.origen = hecho.origen;
    this.multimedia = hecho.multimedia;
  }

  public Hecho(
      Header header, Ubicacion ubicacion, LocalDate fechaHecho, Origen origen, String multimedia) {
    this.header = header;
    this.ubicacion = ubicacion;
    this.fechaHecho = fechaHecho;
    this.origen = origen;
    this.multimedia = multimedia;
    this.fechaCarga = LocalDate.now();
  }

  public Hecho(Header header, Ubicacion ubicacion, LocalDate fechaHecho, Origen origen) {
    this.header = header;
    this.ubicacion = ubicacion;
    this.fechaHecho = fechaHecho;
    this.origen = origen;
    this.multimedia = null;
    this.fechaCarga = LocalDate.now();
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

  // --- Metodos ---
  public boolean esModificable() {
    return !this.fechaCarga.isBefore(LocalDate.now().minusWeeks(1));
  }
}
