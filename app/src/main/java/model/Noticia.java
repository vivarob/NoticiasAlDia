package model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

public class Noticia implements Serializable {
    private String titulo;
    private String enlace;
    private String descripcion;
    private Date fecha;
    private String imagenUrl;
    private String contenido;
    private transient Bitmap imagenBitmap; // transient para indicar que no será serializado directamente

    public Noticia(){

    }
    public Noticia(String titulo, String enlace, String descripcion, Date fecha, String imagenUrl, Bitmap imagenBitmap, String Contenido) {
        this.titulo = titulo;
        this.enlace = enlace;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.imagenUrl = imagenUrl;
        this.imagenBitmap = imagenBitmap;
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Bitmap getImagenBitmap() {
        return imagenBitmap;
    }

    public void setImagenBitmap(Bitmap imagenBitmap) {
        this.imagenBitmap = imagenBitmap;
    }
}
