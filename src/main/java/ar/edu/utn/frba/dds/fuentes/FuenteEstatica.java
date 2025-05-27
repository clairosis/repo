package ar.edu.utn.frba.dds.fuentes;

import ar.edu.utn.frba.dds.hechos.Hecho;
import ar.edu.utn.frba.dds.lectores.csv.LectorCsv;
import ar.edu.utn.frba.dds.lectores.csv.formato.DefaultCsv;
import java.util.ArrayList;
import java.util.List;

public class FuenteEstatica implements Fuente {
  private final LectorCsv lectorCsv = new LectorCsv();
  private final String archivoFuente;

  public FuenteEstatica(String archivoFuente) {
    this.archivoFuente = archivoFuente;
  }

  @Override
  public List<Hecho> leerHechos() {
    return new ArrayList<Hecho>(lectorCsv.importarHechos(archivoFuente, new DefaultCsv()));
  }

  public String getPath() {
    return this.archivoFuente;
  }
}
