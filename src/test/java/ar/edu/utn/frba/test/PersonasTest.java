package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.hechos.GestorHechosEliminados;
import ar.edu.utn.frba.dds.hechos.GestorHechosSubidos;
import ar.edu.utn.frba.dds.hechos.Header;
import ar.edu.utn.frba.dds.hechos.Hecho;
import ar.edu.utn.frba.dds.lectores.csv.LectorCsv;
import ar.edu.utn.frba.dds.lectores.csv.formato.IncendiosForestalesCsv;
import ar.edu.utn.frba.dds.origen.Contribuyente;
import ar.edu.utn.frba.dds.solicitudes.solicitudDeEliminacion.GestorSolicitudesEliminacion;
import ar.edu.utn.frba.dds.solicitudes.solicitudDeEliminacion.SolicitudEliminacion;
import ar.edu.utn.frba.dds.solicitudes.solicitudDeModificacion.GestorSolicitudesModificacion;
import ar.edu.utn.frba.dds.solicitudes.solicitudDeModificacion.SolicitudModificacion;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonasTest {

  @BeforeEach
  void setup() {
    GestorHechosEliminados.limpiarLista();
    GestorHechosSubidos.limpiarLista();
    GestorSolicitudesEliminacion.eliminarSolicitudes();
    GestorSolicitudesModificacion.eliminarSolicitudes();
  }

  @Test
  void unaPersonaPuedeVisualizarHechos() {
    LectorCsv lector = new LectorCsv();

    List<Hecho> hechos =
        lector.importarHechos(
            "./inc/fuentesEstaticas/fuentesDeEjemplo/FuenteEjemplo2.csv",
            new IncendiosForestalesCsv());

    assertEquals(284589, hechos.size());
  }

  @Test
  void unaPersonaPuedeSolicitarLaEliminacionDeUnHecho() {
    Hecho hecho =
        new Hecho(new Header("Titulo", null, null), null, null, new Contribuyente("Pepito"));

    new SolicitudEliminacion(hecho, "Justificacion totalmente valida");

    assertEquals(1, GestorSolicitudesEliminacion.getInstancia().getSolicitudes().size());
  }

  @Test
  void unaAdministradorPuedeAceptarLaEliminacionDeUnHecho() {
    Hecho hecho =
        new Hecho(new Header("Titulo", null, null), null, null, new Contribuyente("Pepito"));

    SolicitudEliminacion solicitud =
        new SolicitudEliminacion(hecho, "Justificacion totalmente valida");

    solicitud.serAceptada();

    assertEquals(1, GestorHechosEliminados.getInstancia().getHechosEliminados().size());
    assertEquals(
        "Titulo", GestorHechosEliminados.getInstancia().getHechosEliminados().get(0).getTitulo());
  }

  @Test
  void unaPersonaPuedeSubirUnHecho() {
    Hecho hecho =
        new Hecho(new Header("Titulo", null, null), null, null, new Contribuyente("Pepito"));

    GestorHechosSubidos.agregarHecho(hecho);

    assertEquals(1, GestorHechosSubidos.getInstancia().getHechosSubidos().size());
    assertEquals(
        "Pepito",
        GestorHechosSubidos.getInstancia().getHechosSubidos().get(0).getOrigen().getNombreFuente());
  }

  @Test
  void unaPersonaPuedeSolicitarLaModificacionDeUnHecho() {
    Hecho hecho1 =
        new Hecho(new Header("Titulo1", null, null), null, null, new Contribuyente("Pepito"));

    Hecho hecho2 =
        new Hecho(new Header("Titulo2", null, null), null, null, new Contribuyente("Pepito"));

    GestorHechosSubidos.agregarHecho(hecho1);

    assertEquals(1, GestorHechosSubidos.getInstancia().getHechosSubidos().size());
    assertEquals(
        "Pepito",
        GestorHechosSubidos.getInstancia().getHechosSubidos().get(0).getOrigen().getNombreFuente());

    new SolicitudModificacion(hecho1, hecho2);

    assertEquals(1, GestorSolicitudesModificacion.getInstancia().getSolicitudes().size());
  }

  @Test
  void unaAdministradorPuedeAceptarLaModificacionDeUnHecho() {
    Hecho hecho1 =
        new Hecho(new Header("Titulo1", null, null), null, null, new Contribuyente("Pepito"));

    Hecho hecho2 =
        new Hecho(new Header("Titulo2", null, null), null, null, new Contribuyente("Pepito"));

    GestorHechosSubidos.agregarHecho(hecho1);

    assertEquals(1, GestorHechosSubidos.getInstancia().getHechosSubidos().size());
    assertEquals(
        "Titulo1", GestorHechosSubidos.getInstancia().getHechosSubidos().get(0).getTitulo());

    SolicitudModificacion solicitud = new SolicitudModificacion(hecho1, hecho2);
    solicitud.serAceptada();

    assertEquals(
        "Titulo2", GestorHechosSubidos.getInstancia().getHechosSubidos().get(0).getTitulo());
  }
}
