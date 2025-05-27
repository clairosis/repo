package ar.edu.utn.frba.dds.lectores.csv.formato;

import ar.edu.utn.frba.dds.hechos.GestorHechosEliminados;
import ar.edu.utn.frba.dds.hechos.Header;
import ar.edu.utn.frba.dds.hechos.Hecho;
import ar.edu.utn.frba.dds.hechos.Ubicacion;
import ar.edu.utn.frba.dds.origen.Dataset;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class DefaultCsv extends FormatoCsv {

  public DefaultCsv() {
    this.campos =
        List.of("Titulo", "Descripcion", "Categoria", "Latitud", "Longitud", "FechaDelHecho");
  }

  @Override
  public List<Hecho> importarHechos(CSVParser csvParser, String path) {
    Map<String, Hecho> hechosMap = new LinkedHashMap<>();
    List<String> hechosEliminados =
        GestorHechosEliminados.getInstancia().getHechosEliminados().stream()
            .map(h -> h.getTitulo().toLowerCase())
            .toList();
    Dataset origen = new Dataset(path);

    for (CSVRecord record : csvParser) {
      try {
        String titulo = obtenerCampoObligatorio(record, "Titulo").toLowerCase();
        if (hechosEliminados.contains(titulo)) continue;

        Hecho hecho = construirHecho(record, origen);
        hechosMap.put(titulo, hecho);

      } catch (IllegalArgumentException e) {
        throw new RuntimeException(
            "Error al procesar registro #" + record.getRecordNumber() + ". " + e.getMessage());
      }
    }

    return List.copyOf(hechosMap.values());
  }

  private Hecho construirHecho(CSVRecord record, Dataset origen) {
    String titulo = obtenerCampoObligatorio(record, "Titulo");
    String descripcion = obtenerCampoObligatorio(record, "Descripcion");
    String categoria = obtenerCampoObligatorio(record, "Categoria");
    float latitud = parsearFloat(record.get("Latitud"), "Latitud");
    float longitud = parsearFloat(record.get("Longitud"), "Longitud");
    LocalDate fecha = parsearFecha(record.get("FechaDelHecho"));

    return new Hecho(
        new Header(titulo, descripcion, categoria),
        new Ubicacion(latitud, longitud),
        fecha,
        origen);
  }
}
