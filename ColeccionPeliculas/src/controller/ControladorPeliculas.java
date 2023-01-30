package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Conexion;

public class ControladorPeliculas implements Initializable {
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
	private TableView<Pelicula> tableView;
	@FXML
	private TableColumn<Pelicula, Integer> colId;
	@FXML
	private TableColumn<Pelicula, String> colTitulo;
	@FXML
	private TableColumn<Pelicula, String> colDirector;

	private ObservableList<Pelicula> listaPeliculas = FXCollections.observableArrayList();
	
	private Connection conn;
	
	public ControladorPeliculas() {
		  conn = Conexion.getConnection();
		}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tableView.setItems((ObservableList<Pelicula>) getData());

		btnInsertar.setOnAction(event -> {
			String titulo = txtTitulo.getText();
			String director = txtDirector.getText();
			insertarPelicula(titulo, director);
			actualizarTabla();
			limpiarCampos();
		});

		btnEditar.setOnAction(event -> {
			Pelicula peliculaSeleccionada = tableView.getSelectionModel().getSelectedItem();
			if (peliculaSeleccionada == null) {
				return;
			}
			int id = peliculaSeleccionada.getId();
			String titulo = txtTitulo.getText();
			String director = txtDirector.getText();
			editarPelicula(id, titulo, director);
			actualizarTabla();
			limpiarCampos();
		});

		btnEliminar.setOnAction(event -> {
			Pelicula peliculaSeleccionada = tableView.getSelectionModel().getSelectedItem();
			if (peliculaSeleccionada == null) {
				return;
			}
			int id = peliculaSeleccionada.getId();
			eliminarPelicula(id);
			actualizarTabla();
			limpiarCampos();
		});
		tableView.setOnMouseClicked(event -> {
			Pelicula peliculaSeleccionada = tableView.getSelectionModel().getSelectedItem();
			if (peliculaSeleccionada == null) {
				return;
			}
			txtTitulo.setText(peliculaSeleccionada.getTitulo());
			txtDirector.setText(peliculaSeleccionada.getDirector());
		});
	}

	public void insertarPelicula(String titulo, String director) {
		// Insertar un nuevo registro en la base de datos
		try (Connection conn = Conexion.getConnection()) {
			String sql = "INSERT INTO peliculas (titulo, director) VALUES (?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, titulo);
			stmt.setString(2, director);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void editarPelicula(int id, String titulo, String director) {
		// Editar un registro existente en la base de datos
		try (Connection conn = Conexion.getConnection()) {
			String sql = "UPDATE peliculas SET titulo = ?, director = ? WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, titulo);
			stmt.setString(2, director);
			stmt.setInt(3, id);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void eliminarPelicula(int id) {
		// Eliminar un registro existente en la base de datos
		try {
			String sql = "DELETE FROM peliculas WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void actualizarTabla() {
		// Actualizar el TableView con los datos en la base de datos
		try {
			listaPeliculas.clear();
			String sql = "SELECT * FROM peliculas";
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				listaPeliculas.add(new Pelicula(rs.getInt("id"), rs.getString("titulo"), rs.getString("director")));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	public List<Pelicula> getData() {
		  List<Pelicula> peliculas = new ArrayList<>();
		  // Aquí se escribiría el código necesario para recuperar los datos de la base de datos y agregarlos a la lista de películas
		  return peliculas;
		}
	private void limpiarCampos() {
		txtTitulo.clear();
		txtDirector.clear();
	}
	
}
