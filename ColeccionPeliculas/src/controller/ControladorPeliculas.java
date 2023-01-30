package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ControladorPeliculas implements Initializable {

	@FXML
	private Button btnEditar;

	@FXML
	private Button btnEliminar;

	@FXML
	private Button btnInsertar;

	@FXML
	private TableColumn<Pelicula, String> colGenero;

	@FXML
	private TableColumn<Pelicula, Integer> colId;

	@FXML
	private TableColumn<Pelicula, String> colTitulo;

	@FXML
	private TableColumn<Pelicula, String> columnActores;

	@FXML
	private TableColumn<Pelicula, String> columnDuracion;

	@FXML
	private TableColumn<Pelicula, String> columnFotografia;

	@FXML
	private TableColumn<Pelicula, String> columnIdioma;

	@FXML
	private TableColumn<Pelicula, String> columnPais;

	@FXML
	private TableColumn<Pelicula, String> columnSinopsis;

	@FXML
	private TableView<Pelicula> tablaPeliculas;

	@FXML
	private TextField tfActores;

	@FXML
	private TextField tfDuracion;

	@FXML
	private TextField tfGenero;

	@FXML
	private TextField tfIdioma;

	@FXML
	private TextField tfPais;

	@FXML
	private TextField tfSinopsis;

	@FXML
	private TextField txtTitulo;

	@FXML
	private TextField tfFotografia;

	@FXML
	private TextField txtId;

	@FXML
	void add(ActionEvent event) {
		insertar();
	}

	@FXML
	void editar(ActionEvent event) {
		editar();
	}

	@FXML
	void eliminar(ActionEvent event) {
		eliminar();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		mostrarPeliculas();
	}

	@FXML
	void manejadorClick(MouseEvent event) {
		Pelicula pelicula = tablaPeliculas.getSelectionModel().getSelectedItem();
		txtId.setText(""+pelicula.getId());
		txtTitulo.setText(pelicula.getTitulo());
		tfGenero.setText(pelicula.getGenero());
		tfDuracion.setText(pelicula.getDuracion());
		tfSinopsis.setText(pelicula.getSinopsis());
		tfIdioma.setText(pelicula.getIdioma());
		tfPais.setText(pelicula.getPais());
		tfActores.setText(pelicula.getActores());
		tfFotografia.setText(pelicula.getFotografia());
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}

	public ObservableList<Pelicula> getListaPeliculas() {
		Statement st;
		ResultSet rs;
		ObservableList<Pelicula> listaPeliculas = FXCollections.observableArrayList();
		Connection conn = getConnection();
		String query = "SELECT * FROM peliculas";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			Pelicula pelicula;

			while (rs.next()) {
				pelicula = new Pelicula(rs.getInt("id"), rs.getString("titulo"), rs.getString("genero"),
						rs.getString("duracion"), rs.getString("sinopsis"), rs.getString("idioma"),
						rs.getString("pais"), rs.getString("actores"), rs.getString("fotografia"));
				listaPeliculas.add(pelicula);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaPeliculas;
	}

	public void mostrarPeliculas() {
		ObservableList<Pelicula> lista = getListaPeliculas();
		colId.setCellValueFactory(new PropertyValueFactory<Pelicula, Integer>("id"));
		colTitulo.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("titulo"));
		colGenero.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("genero"));
		columnDuracion.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("duracion"));
		columnSinopsis.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("sinopsis"));
		columnIdioma.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("idioma"));
		columnPais.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("pais"));
		columnActores.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("actores"));
		columnFotografia.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("fotografia"));

		tablaPeliculas.setItems(lista);
	}

	private void insertar() {
		String query = "INSERT INTO peliculas VALUES (" + txtId.getText() + ", '" + txtTitulo.getText() + "', '"
				+ tfGenero.getText() + "', '" + tfDuracion.getText() + "', '" + tfSinopsis.getText() + "', '"
				+ tfIdioma.getText() + "', '" + tfPais.getText() + "', '" + tfActores.getText() + "', '"
				+ tfFotografia.getText() + "');";
		executeQuery(query);
		mostrarPeliculas();
	}

	private void editar() {
		String query = "UPDATE peliculas SET titulo  = '" + txtTitulo.getText() + "', genero = '" + tfGenero.getText()
				+ "', duracion = '" + tfDuracion.getText() + "', sinopsis = '" + tfSinopsis.getText() + "', idioma = '"
				+ tfIdioma.getText() + "', pais = '" + tfPais.getText() + "', actores = '" + tfActores.getText()
				+ "', fotografia = '" + tfFotografia.getText() + "' WHERE id = " + txtId.getText() + "";

		executeQuery(query);
		mostrarPeliculas();
	}

	private void eliminar() {
		String query = "DELETE FROM peliculas WHERE id =" + txtId.getText() + "";
		executeQuery(query);
		mostrarPeliculas();
	}

	private void executeQuery(String query) {
		Connection conn = getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
