package ar.edu.utn.frba.dds.solicitudes.solicitudDeModificacion;

import ar.edu.utn.frba.dds.solicitudes.EstadoSolicitud;
import java.util.ArrayList;
import java.util.List;

public class GestorSolicitudesModificacion {

  private static final GestorSolicitudesModificacion instancia =
      new GestorSolicitudesModificacion();
  private static final List<SolicitudModificacion> solicitudes = new ArrayList<>();

  public static GestorSolicitudesModificacion getInstancia() {
    return instancia;
  }

  public List<SolicitudModificacion> getSolicitudes() {
    return new ArrayList<SolicitudModificacion>(solicitudes);
  }

  public List<SolicitudModificacion> getSolicitudes(EstadoSolicitud estado) {
    return solicitudes.stream().filter(s -> s.getEstado() == estado).toList();
  }

  public static void agregarSolicitud(SolicitudModificacion solicitud) {
    solicitudes.add(solicitud);
  }

  public void eliminarSolicitud(SolicitudModificacion solicitud) {
    solicitudes.remove(solicitud);
  }

  public static void eliminarSolicitudes() {
    solicitudes.clear();
  }
}
