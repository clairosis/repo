package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.hechos.GestorHechosSubidos;
import ar.edu.utn.frba.dds.hechos.Header;
import ar.edu.utn.frba.dds.hechos.Hecho;
import ar.edu.utn.frba.dds.solicitudes.EstadoSolicitud;
import ar.edu.utn.frba.dds.solicitudes.solicitudDeModificacion.GestorSolicitudesModificacion;
import ar.edu.utn.frba.dds.solicitudes.solicitudDeModificacion.SolicitudModificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SolicitudesDeModificacionTest {
  private Hecho hecho1 = new Hecho(new Header("Titulo1", null, null), null, null, null, null);
  private Hecho hecho2 = new Hecho(new Header("Titulo2", null, null), null, null, null, null);

  @BeforeEach
  void setup() {
    GestorSolicitudesModificacion.eliminarSolicitudes();
    GestorHechosSubidos.limpiarLista();
    GestorHechosSubidos.agregarHecho(hecho1);
  }

  @Test
  void laSolicitudSeAlmacenaCorrectamenteAlCrearla() {
    SolicitudModificacion solicitud1 = new SolicitudModificacion(hecho1, hecho2);
    SolicitudModificacion solicitud2 = new SolicitudModificacion(hecho2, hecho2);

    assertEquals(2, GestorSolicitudesModificacion.getInstancia().getSolicitudes().size());
    assertTrue(GestorSolicitudesModificacion.getInstancia().getSolicitudes().contains(solicitud1));
    assertTrue(GestorSolicitudesModificacion.getInstancia().getSolicitudes().contains(solicitud2));
  }

  @Test
  void unaSolicitudEsAceptada() {
    SolicitudModificacion solicitud = new SolicitudModificacion(hecho1, hecho2);

    Hecho hecho = GestorHechosSubidos.getInstancia().getHechosSubidos().get(0);
    assertEquals("Titulo1", hecho.getTitulo());

    solicitud.serAceptada();

    hecho = GestorHechosSubidos.getInstancia().getHechosSubidos().get(0);
    assertEquals("Titulo2", hecho.getTitulo());

    assertTrue(
        GestorSolicitudesModificacion.getInstancia().getSolicitudes().stream()
            .allMatch(s -> s.getEstado().equals(EstadoSolicitud.ACEPTADA)));
  }

  @Test
  void unaSolicitudEsRechazada() {
    SolicitudModificacion solicitud = new SolicitudModificacion(hecho1, hecho2);

    assertTrue(
        GestorSolicitudesModificacion.getInstancia().getSolicitudes().stream()
            .allMatch(s -> s.getEstado().equals(EstadoSolicitud.PENDIENTE)));

    Hecho hecho = GestorHechosSubidos.getInstancia().getHechosSubidos().get(0);
    assertEquals("Titulo1", hecho.getTitulo());

    solicitud.serRechazada();

    assertTrue(
        GestorSolicitudesModificacion.getInstancia().getSolicitudes().stream()
            .allMatch(s -> s.getEstado().equals(EstadoSolicitud.RECHAZADA)));

    hecho = GestorHechosSubidos.getInstancia().getHechosSubidos().get(0);
    assertEquals("Titulo1", hecho.getTitulo());
  }

  @Test
  void importoSoloLasSolicitudesPendientes() {
    SolicitudModificacion solicitud = new SolicitudModificacion(hecho1, hecho2);
    new SolicitudModificacion(hecho1, hecho2);
    new SolicitudModificacion(hecho1, hecho2);
    new SolicitudModificacion(hecho1, hecho2);
    new SolicitudModificacion(hecho1, hecho2);

    assertEquals(
        5,
        GestorSolicitudesModificacion.getInstancia()
            .getSolicitudes(EstadoSolicitud.PENDIENTE)
            .size());

    solicitud.serAceptada();

    assertEquals(
        4,
        GestorSolicitudesModificacion.getInstancia()
            .getSolicitudes(EstadoSolicitud.PENDIENTE)
            .size());

    assertTrue(
        GestorSolicitudesModificacion.getInstancia()
            .getSolicitudes(EstadoSolicitud.PENDIENTE)
            .stream()
            .allMatch(s -> s.getEstado().name().equals("PENDIENTE")));
  }
}
