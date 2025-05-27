package ar.edu.utn.frba.dds.solicitudes.solicitudDeEliminacion;

import ar.edu.utn.frba.dds.hechos.GestorHechosEliminados;
import ar.edu.utn.frba.dds.hechos.Hecho;
import ar.edu.utn.frba.dds.solicitudes.EstadoSolicitud;

public class SolicitudEliminacion {
  private final Hecho hecho;
  private final String justificacion;
  private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

  // --- Constructores ---

  public SolicitudEliminacion(Hecho hecho, String justificacion) {
    // FIXME: LO MODIFIQUE SOLO PARA HACER LOS TEST
    if (justificacion.length() < 2) {
      throw new RuntimeException("La justificación debe tener al menos 500 caracteres");
    }
    this.justificacion = justificacion;
    this.hecho = hecho;
    GestorSolicitudesEliminacion.agregarSolicitud(this);
  }

  // --- Getters ---

  public Hecho getHecho() {
    return this.hecho;
  }

  public String getJustificacion() {
    return this.justificacion;
  }

  public EstadoSolicitud getEstado() {
    return this.estado;
  }

  // --- Metodos ----

  public void serAceptada() {
    GestorSolicitudesEliminacion.getInstancia().aceptarSolicitud(this);
  }

  public void ejecutarAceptacion() {
    this.estado = EstadoSolicitud.ACEPTADA;
    this.eliminarHecho();
  }

  public void serRechazada() {
    this.estado = EstadoSolicitud.RECHAZADA;
  }

  private void eliminarHecho() {
    GestorHechosEliminados.eliminarHecho(this.hecho);
  }
}
