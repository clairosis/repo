package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.criteriodepertenencia.CriterioDePertenencia;
import ar.edu.utn.frba.dds.criteriodepertenencia.CriterioPorAutor;
import ar.edu.utn.frba.dds.criteriodepertenencia.CriterioPorCategoria;
import ar.edu.utn.frba.dds.fuentes.Fuente;
import ar.edu.utn.frba.dds.fuentes.FuenteEstatica;
import ar.edu.utn.frba.dds.hechos.Coleccion;
import ar.edu.utn.frba.dds.hechos.Header;
import ar.edu.utn.frba.dds.hechos.Hecho;
import ar.edu.utn.frba.dds.hechos.Ubicacion;
import ar.edu.utn.frba.dds.lectores.csv.LectorCsv;
import ar.edu.utn.frba.dds.origen.Dataset;
import ar.edu.utn.frba.dds.solicitudes.solicitudDeEliminacion.GestorSolicitudesEliminacion;
import ar.edu.utn.frba.dds.usuario.Persona;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

public class VisualizadoresTest {
  private static final FuenteEstatica FUENTE_TEST =
      new FuenteEstatica("./inc/fuentesEstaticas/tests/pruebaColecciones.csv");

  @Test
  void unVisualizadorNavegaTodosLosHechosDisponiblesDeUnaColeccion() {
    Persona visualizador = new Persona();

    Coleccion coleccion =
        new Coleccion(
            "test-id",
            "ColeccionTest",
            "Coleccion para realizar los test",
            List.of(FUENTE_TEST),
            new CriterioPorAutor("pruebaColecciones.csv"));

    assertEquals(10, visualizador.visualizarHechos(coleccion).size());
  }

  @Test
  void unVisualizadorNavegaTodosLosHechosDisponiblesDeUnaColeccionAplicandoUnFiltro() {
    Persona visualizador = new Persona();

    Coleccion coleccion =
        new Coleccion(
            "test-id",
            "ColeccionTest",
            "Coleccion para realizar los test",
            List.of(FUENTE_TEST),
            new CriterioPorAutor("pruebaColecciones.csv"));

    CriterioDePertenencia criterioAdicional = new CriterioPorCategoria("categoria1");

    assertEquals(5, visualizador.visualizarHechos(coleccion, criterioAdicional).size());
  }

  @Test
  void elCampoNombreEsObligatorioParaSubirHechos() {
    Persona visualizador = new Persona();

    Exception e = assertThrows(Exception.class, () -> visualizador.subirHecho(null));

    assertEquals("El campo \"Nombre\" es obligatorio para subir hechos", e.getMessage());
  }

  @Test
  void unVisualizadorSeConvierteEnContribuyente() {
    Persona visualizador = new Persona();
    visualizador.setNombre("Nicolas");

    assertFalse(visualizador.esContribuyente());

    visualizador.subirHecho(null);

    assertTrue(visualizador.esContribuyente());
  }

  @Test
  void unVisualizadorNoPuedeCrearSolicitudes() {
    Persona visualizador = new Persona();

    Exception e =
        assertThrows(
            Exception.class,
            () -> visualizador.solicitarEliminacionDeHecho(null, "JustificacionTotalmenteValida"));

    assertEquals("Solo los contribuyentes pueden realizar solicitudes", e.getMessage());
  }

  @Test
  void unContribuyenteCrearSolicitudes() {
    Persona visualizador = new Persona();
    visualizador.setNombre("Nicolas");
    visualizador.subirHecho(null);

    Fuente fuente = new FuenteEstatica("./inc/fuentesEstaticas/tests/pruebaColecciones.csv");

    File archivoHechos = new File("./inc/solicitudesDeEliminacion.csv");
    if (archivoHechos.exists()) {
      archivoHechos.delete();
    }

    Hecho hecho =
        new Hecho(
            new Header("Titulo1", "a", "b"),
            new Ubicacion(.0f, 1.0f),
            LocalDate.now(),
            new Dataset("./inc/fuentesEstaticas/tests/pruebaColecciones.csv"));

    visualizador.solicitarEliminacionDeHecho(hecho, "UnaJustificacionTotalmenteValida");

    LectorCsv lector = new LectorCsv();

    assertTrue(
        GestorSolicitudesEliminacion.getInstancia().getSolicitudes().stream()
            .anyMatch(s -> s.getHecho().equals(hecho)));
  }
}
