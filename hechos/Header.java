package ar.edu.utn.frba.dds.hechos;

public class Header {
  private final String titulo;
  private final String descripcion;
  private final String categoria;

  public Header(String titulo, String descripcion, String categoria) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
  }

  public String getTitulo() {
    return this.titulo;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public String getCategoria() {
    return this.categoria;
  }
}
