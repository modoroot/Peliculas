package controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pelicula {

	private IntegerProperty id;
	private StringProperty titulo;
	private StringProperty director;
//	private IntegerProperty anio;

	public Pelicula(int id, String titulo, String director) {
		this.id = new SimpleIntegerProperty(id);
		this.titulo = new SimpleStringProperty(titulo);
		this.director = new SimpleStringProperty(director);
//		this.anio = new SimpleIntegerProperty(anio);
	}

	// Getters y setters para cada atributo
	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public String getTitulo() {
		return titulo.get();
	}

	public void setTitulo(String titulo) {
		this.titulo.set(titulo);
	}

	public StringProperty tituloProperty() {
		return titulo;
	}

	public String getDirector() {
		return director.get();
	}

	public void setDirector(String director) {
		this.director.set(director);
	}

	public StringProperty directorProperty() {
		return director;
	}

//	public int getAño() {
//		return anio.get();
//	}
//
//	public void setAño(int anio) {
//		this.anio.set(anio);
//	}
//
//	public IntegerProperty añoProperty() {
//		return anio;
//	}

	
	
}
