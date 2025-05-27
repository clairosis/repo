package ar.edu.utn.frba.dds.solicitudes.solicitudDeEliminacion;

import ar.edu.utn.frba.dds.solicitudes.EstadoSolicitud;
import java.util.ArrayList;
import java.util.List;

public class GestorSolicitudesEliminacion {

  private static final GestorSolicitudesEliminacion instancia = new GestorSolicitudesEliminacion();
  private static final List<SolicitudEliminacion> solicitudes = new ArrayList<>();

  public static GestorSolicitudesEliminacion getInstancia() {
    return instancia;
  }

  public List<SolicitudEliminacion> getSolicitudes() {
    return new ArrayList<SolicitudEliminacion>(solicitudes);
  }

  public List<SolicitudEliminacion> getSolicitudes(EstadoSolicitud estado) {
    return solicitudes.stream().filter(s -> s.getEstado() == estado).toList();
  }

  public static void agregarSolicitud(SolicitudEliminacion solicitud) {
    solicitudes.add(solicitud);
  }

  public void eliminarSolicitud(SolicitudEliminacion solicitud) {
    solicitudes.remove(solicitud);
  }

  public static void eliminarSolicitudes() {
    solicitudes.clear();
  }

  public void aceptarSolicitud(SolicitudEliminacion solicitud) {
    solicitudes.stream()
        .filter(s -> s.getHecho().equals(solicitud.getHecho()))
        .forEach(s -> s.ejecutarAceptacion());
  }
}
