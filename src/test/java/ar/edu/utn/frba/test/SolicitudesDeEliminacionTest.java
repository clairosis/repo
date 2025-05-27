package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.hechos.GestorHechosEliminados;
import ar.edu.utn.frba.dds.hechos.Header;
import ar.edu.utn.frba.dds.hechos.Hecho;
import ar.edu.utn.frba.dds.solicitudes.EstadoSolicitud;
import ar.edu.utn.frba.dds.solicitudes.solicitudDeEliminacion.GestorSolicitudesEliminacion;
import ar.edu.utn.frba.dds.solicitudes.solicitudDeEliminacion.SolicitudEliminacion;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SolicitudesDeEliminacionTest {
  private Hecho hecho1 = new Hecho(new Header("Titulo1", null, null), null, null, null, null);
  private Hecho hecho2 = new Hecho(new Header("Titulo1", null, null), null, null, null, null);

  @BeforeEach
  void setup() {
    GestorHechosEliminados.limpiarLista();
    GestorSolicitudesEliminacion.eliminarSolicitudes();
  }

  @Test
  void laSolicitudSeAlmacenaCorrectamenteAlCrearla() {
    SolicitudEliminacion solicitud1 = new SolicitudEliminacion(hecho1, "Justificacion1");
    SolicitudEliminacion solicitud2 = new SolicitudEliminacion(hecho2, "Justificacion2");

    assertEquals(2, GestorSolicitudesEliminacion.getInstancia().getSolicitudes().size());
    assertTrue(GestorSolicitudesEliminacion.getInstancia().getSolicitudes().contains(solicitud1));
    assertTrue(GestorSolicitudesEliminacion.getInstancia().getSolicitudes().contains(solicitud2));
  }

  @Test
  void unaSolicitudEsAceptada() {
    SolicitudEliminacion solicitud = new SolicitudEliminacion(hecho1, "Justificacion1");
    new SolicitudEliminacion(hecho2, "Justificacion2");
    new SolicitudEliminacion(hecho1, "Justificacion1.1");
    new SolicitudEliminacion(hecho2, "Justificacion2.1");
    new SolicitudEliminacion(hecho1, "Justificacion1.2");
    new SolicitudEliminacion(hecho2, "Justificacion2.2");

    assertTrue(
        GestorSolicitudesEliminacion.getInstancia().getSolicitudes().stream()
            .filter(s -> s.getHecho().equals(hecho1))
            .allMatch(s -> s.getEstado().name().equals("PENDIENTE")));

    solicitud.serAceptada();

    assertTrue(
        GestorSolicitudesEliminacion.getInstancia().getSolicitudes().stream()
            .filter(s -> s.getHecho().equals(hecho1))
            .allMatch(s -> s.getEstado().name().equals("ACEPTADA")));

    List<Hecho> hechosEliminados = GestorHechosEliminados.getInstancia().getHechosEliminados();

    assertEquals(3, hechosEliminados.size());

    Hecho hechoEliminado = hechosEliminados.get(0);
    assertEquals("Titulo1", hechoEliminado.getTitulo());
  }

  @Test
  void unaSolicitudEsRechazada() {
    SolicitudEliminacion solicitud1 = new SolicitudEliminacion(hecho1, "Justificacion1");
    SolicitudEliminacion solicitud2 = new SolicitudEliminacion(hecho2, "Justificacion2");
    new SolicitudEliminacion(hecho1, "Justificacion1.1");
    new SolicitudEliminacion(hecho2, "Justificacion2.1");
    new SolicitudEliminacion(hecho1, "Justificacion1.2");
    new SolicitudEliminacion(hecho2, "Justificacion2.2");

    assertTrue(
        GestorSolicitudesEliminacion.getInstancia().getSolicitudes().stream()
            .allMatch(s -> s.getEstado().name().equals("PENDIENTE")));

    solicitud1.serRechazada();

    assertEquals(
        1,
        GestorSolicitudesEliminacion.getInstancia().getSolicitudes().stream()
            .filter(s -> s.getHecho().equals(hecho1) && s.getEstado().name().equals("RECHAZADA"))
            .count());
  }

  @Test
  void importoSoloLasSolicitudesPendientes() {
    SolicitudEliminacion solicitud1 = new SolicitudEliminacion(hecho1, "Justificacion1");
    SolicitudEliminacion solicitud2 = new SolicitudEliminacion(hecho2, "Justificacion2");
    new SolicitudEliminacion(hecho1, "Justificacion1.1");
    new SolicitudEliminacion(hecho2, "Justificacion2.1");
    new SolicitudEliminacion(hecho1, "Justificacion1.2");
    new SolicitudEliminacion(hecho2, "Justificacion2.2");

    solicitud1.serAceptada();

    assertEquals(
        3,
        GestorSolicitudesEliminacion.getInstancia()
            .getSolicitudes(EstadoSolicitud.PENDIENTE)
            .size());
    assertTrue(
        GestorSolicitudesEliminacion.getInstancia()
            .getSolicitudes(EstadoSolicitud.PENDIENTE)
            .stream()
            .allMatch(s -> s.getEstado().name().equals("PENDIENTE")));
  }
}
