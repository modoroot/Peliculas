package model;

/**
 * Clase que contiene atributos, constructores, Getters-Setters de las películas
 * 
 * @author Antonio Miguel Núñez Ariza
 * @version 1.0
 */
public class Pelicula {

	private int id;
	private String titulo;
	private String genero;
	private String duracion;
	private String sinopsis;
	private String idioma;
	private String pais;
	private String actores;
	private String fotografia;
	
	
	public Pelicula() {}
	public Pelicula(int id, String titulo, String genero, String duracion, String sinopsis, String idioma, String pais,
			String actores, String fotografia) {
		this.id = id;
		this.titulo = titulo;
		this.genero = genero;
		this.duracion = duracion;
		this.sinopsis = sinopsis;
		this.idioma = idioma;
		this.pais = pais;
		this.actores = actores;
		this.fotografia = fotografia;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getDuracion() {
		return duracion;
	}
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	public String getSinopsis() {
		return sinopsis;
	}
	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getActores() {
		return actores;
	}
	public void setActores(String actores) {
		this.actores = actores;
	}
	public String getFotografia() {
		return fotografia;
	}
	public void setFotografia(String fotografia) {
		this.fotografia = fotografia;
	}
	
	
	
}
