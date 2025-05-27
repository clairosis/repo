package ar.edu.utn.frba.dds.solicitudes.solicitudDeModificacion;

import ar.edu.utn.frba.dds.hechos.GestorHechosSubidos;
import ar.edu.utn.frba.dds.hechos.Hecho;
import ar.edu.utn.frba.dds.solicitudes.EstadoSolicitud;

public class SolicitudModificacion {

  private Hecho hechoOriginal;
  private Hecho hechoModificado;
  private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

  // --- Constructores ---

  public SolicitudModificacion(Hecho hechoOriginal, Hecho hechoModificado) {
    this.verificarSiEsModificable(hechoOriginal);
    this.hechoOriginal = hechoOriginal;
    this.hechoModificado = hechoModificado;
    GestorSolicitudesModificacion.agregarSolicitud(this);
  }

  // --- Getters ---

  public Hecho getHechoOriginal() {
    return this.hechoOriginal;
  }

  public Hecho getHechoModificado() {
    return this.hechoModificado;
  }

  public EstadoSolicitud getEstado() {
    return this.estado;
  }

  // --- Metodos ----

  public void serAceptada() {
    this.estado = EstadoSolicitud.ACEPTADA;
    this.modificarHecho();
  }

  public void serRechazada() {
    this.estado = EstadoSolicitud.RECHAZADA;
  }

  private void modificarHecho() {
    GestorHechosSubidos.eliminarHecho(this.hechoOriginal);
    GestorHechosSubidos.agregarHecho(this.hechoModificado);
  }

  private void verificarSiEsModificable(Hecho hecho) {
    if (!hecho.esModificable()) {
      throw new RuntimeException("Este hecho ya no puede ser modificado");
    }
  }
}
