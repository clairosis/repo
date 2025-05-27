package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.criteriodepertenencia.*;
import ar.edu.utn.frba.dds.fuentes.FuenteEstatica;
import ar.edu.utn.frba.dds.hechos.Coleccion;
import ar.edu.utn.frba.dds.hechos.Hecho;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ColeccionesTest {
  private static final FuenteEstatica FUENTE_TEST =
      new FuenteEstatica("./inc/fuentesEstaticas/tests/pruebaColecciones.csv");

  @Test
  void filtrarPorAutor() {
    Coleccion coleccion =
        new Coleccion(
            "test-id",
            "ColeccionTest",
            "Coleccion para realizar los test",
            List.of(FUENTE_TEST),
            new CriterioPorAutor("pruebaColecciones.csv"));

    List<Hecho> hechosFiltrados = coleccion.getHechos();

    assertTrue(
        hechosFiltrados.stream()
            .allMatch(h -> h.getOrigen().getNombreFuente().equals("pruebaColecciones.csv")));
  }

  @Test
  void filtrarPorCategoria() {
    Coleccion coleccion =
        new Coleccion(
            "test-id",
            "ColeccionTest",
            "Coleccion para realizar los test",
            List.of(FUENTE_TEST),
            new CriterioPorCategoria("categoria1"));

    List<Hecho> hechosFiltrados = coleccion.getHechos();

    assertEquals(5, hechosFiltrados.size());
  }

  @Test
  void filtrarPorFecha() {
    Coleccion coleccion =
        new Coleccion(
            "test-id",
            "ColeccionTest",
            "Coleccion para realizar los test",
            List.of(FUENTE_TEST),
            new CriterioPorFecha(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31)));

    List<Hecho> hechosFiltrados = coleccion.getHechos();

    assertEquals(5, hechosFiltrados.size());
  }

  @Test
  void filtrarPorUbicacion() {
    Coleccion coleccion =
        new Coleccion(
            "test-id",
            "ColeccionTest",
            "Coleccion para realizar los test",
            List.of(FUENTE_TEST),
            new CriterioPorUbicacion(0, 1));

    List<Hecho> hechosFiltrados = coleccion.getHechos();

    assertEquals(4, hechosFiltrados.size());
  }

  @Test
  void criterioAnd() {
    CriterioPorUbicacion criterio1 = new CriterioPorUbicacion(1, 0);
    CriterioPorCategoria criterio2 = new CriterioPorCategoria("categoria1");

    Coleccion coleccion =
        new Coleccion(
            "test-id",
            "ColeccionTest",
            "Coleccion para realizar los test",
            List.of(FUENTE_TEST),
            new CriterioAnd(criterio1, criterio2));

    List<Hecho> hechosFiltrados = coleccion.getHechos();

    assertEquals(3, hechosFiltrados.size());
  }

  @Test
  void criterioOr() {
    CriterioDePertenencia criterio1 = new CriterioPorCategoria("categoria1");
    CriterioDePertenencia criterio2 = new CriterioPorCategoria("categoria2");

    Coleccion coleccion =
        new Coleccion(
            "test-id",
            "ColeccionTest",
            "Coleccion para realizar los test",
            List.of(FUENTE_TEST),
            new CriterioOr(criterio1, criterio2));

    List<Hecho> hechosFiltrados = coleccion.getHechos();

    assertEquals(10, hechosFiltrados.size());
  }

  @Test
  void criterioNot() {
    CriterioPorUbicacion criterio1 = new CriterioPorUbicacion(1, 0);
    CriterioPorCategoria criterio2 = new CriterioPorCategoria("categoria1");

    Coleccion coleccion =
        new Coleccion(
            "test-id",
            "ColeccionTest",
            "Coleccion para realizar los test",
            List.of(FUENTE_TEST),
            new CriterioNot(new CriterioAnd(criterio1, criterio2)));

    List<Hecho> hechosFiltrados = coleccion.getHechos();

    assertEquals(7, hechosFiltrados.size());
  }

  @Test
  void criterioAdicional() {
    Coleccion coleccion =
        new Coleccion(
            "test-id",
            "ColeccionTest",
            "Coleccion para realizar los test",
            List.of(FUENTE_TEST),
            new CriterioPorCategoria("Categoria1"));

    CriterioDePertenencia criterioAdicional = new CriterioPorUbicacion(1, 0);

    assertEquals(3, coleccion.getHechos(criterioAdicional).size());
  }
}
