package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class ControladorInterfazFXML implements Initializable {
	@FXML
	private TextField txtTitulo;
	@FXML
	private TextField txtDirector;
	@FXML
	private Button btnInsertar;
	@FXML
	private Button btnEditar;
	@FXML
	private Button btnEliminar;
	@FXML
	private TableView<Pelicula> tablaPeliculas;

	private ControladorPeliculas controladorPeliculas;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		controladorPeliculas = new ControladorPeliculas();
//		tablaPeliculas.setItems((ObservableList<Pelicula>) controladorPeliculas.getData());

		btnInsertar.setOnAction(event -> {
			String titulo = txtTitulo.getText();
			String director = txtDirector.getText();
			controladorPeliculas.insertarPelicula(titulo, director);
			controladorPeliculas.actualizarTabla();
			limpiarCampos();
		});

		btnEditar.setOnAction(event -> {
			Pelicula peliculaSeleccionada = tablaPeliculas.getSelectionModel().getSelectedItem();
			if (peliculaSeleccionada == null) {
				return;
			}
			int id = peliculaSeleccionada.getId();
			String titulo = txtTitulo.getText();
			String director = txtDirector.getText();
			controladorPeliculas.editarPelicula(id, titulo, director);
			controladorPeliculas.actualizarTabla();
			limpiarCampos();
		});

		btnEliminar.setOnAction(event -> {
			Pelicula peliculaSeleccionada = tablaPeliculas.getSelectionModel().getSelectedItem();
			if (peliculaSeleccionada == null) {
				return;
			}
			int id = peliculaSeleccionada.getId();
			controladorPeliculas.eliminarPelicula(id);
			controladorPeliculas.actualizarTabla();
			limpiarCampos();
		});
		tablaPeliculas.setOnMouseClicked(event -> {
			Pelicula peliculaSeleccionada = tablaPeliculas.getSelectionModel().getSelectedItem();
			if (peliculaSeleccionada == null) {
				return;
			}
			txtTitulo.setText(peliculaSeleccionada.getTitulo());
			txtDirector.setText(peliculaSeleccionada.getDirector());
		});
	}

	private void limpiarCampos() {
		txtTitulo.clear();
		txtDirector.clear();
	}
}