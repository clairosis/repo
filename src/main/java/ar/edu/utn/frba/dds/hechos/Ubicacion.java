package ar.edu.utn.frba.dds.hechos;

public class Ubicacion {
  private final Float latitud;
  private final Float longitud;

  public Ubicacion(Float latitud, Float longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  public Float getLatitud() {
    return this.latitud;
  }

  public Float getLongitud() {
    return this.longitud;
  }
}
