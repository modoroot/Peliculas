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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorPeliculas implements Initializable {

	@FXML
	private Button btnEditar;

	@FXML
	private Button btnEliminar;

	@FXML
	private Button btnInsertar;

	@FXML
	private Button btnFotografia;

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
	private MenuItem menuItemActualizar;

	@FXML
	private MenuItem menuItemEliminar;

	@FXML
	private MenuItem menuItemNuevo;

	@FXML
	private ImageView imageViewFotografia;
	

	@FXML
	void nuevoRegistroMenu(ActionEvent event) {
			showModal();
	}
	
	private void showModal() {
		Stage modal = new Stage();
		modal.initModality(Modality.APPLICATION_MODAL);
		modal.show();
	}

	@FXML
    void subirImagen(ActionEvent event) {
		
    }
	/**
	 * Evento del botón Añadir que añade un registro a la DB
	 * @param event
	 */
	@FXML
	void add(ActionEvent event) {
		insertar();
	}
	/**
	 * Evento del botón Editar que edita el registro seleccionado en la interfaz de la DB
	 * @param event
	 */
	@FXML
	void editar(ActionEvent event) {
		editar();
	}
	/**
	 * Evento del botón eliminar que elimina un registro seleccionado en la interfaz de la DB
	 * @param event
	 */
	@FXML
	void eliminar(ActionEvent event) {
		eliminar();
	}
	/**
	 * Al iniciar la aplicación ejecuta un método que muestra la lista de las películas actuales dentro de la DB
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		mostrarPeliculas();
	}
	/**
	 * Sustituye el contenido de los TextField al hacer click en un registro
	 * @param event
	 */
	@FXML
	void manejadorClick(MouseEvent event) {
		try {
			Pelicula pelicula = tablaPeliculas.getSelectionModel().getSelectedItem();
			txtId.setText("" + pelicula.getId());
			txtTitulo.setText(pelicula.getTitulo());
			tfGenero.setText(pelicula.getGenero());
			tfDuracion.setText(pelicula.getDuracion());
			tfSinopsis.setText(pelicula.getSinopsis());
			tfIdioma.setText(pelicula.getIdioma());
			tfPais.setText(pelicula.getPais());
			tfActores.setText(pelicula.getActores());
			tfFotografia.setText(pelicula.getFotografia());
		} catch (Exception e) {
			System.out.println("");
		}
		
	}
	/**
	 * Objeto Connection para conectar a la DB
	 * @return conexión exitosa
	 */
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
	/**
	 * Realiza una consulta de selección a la DB y guarda en la variable tipo ObservableList
	 * los registros de la DB en un ArrayList
	 * @return
	 */
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
	/**
	 * Método para mostrar las películas. Se utiliza al iniciar la aplicación, insertar, editar, o eliminar un registro
	 */
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
	/**
	 * Inserta un registro y actualiza la lista de películas
	 */
	private void insertar() {
		String query = "INSERT INTO peliculas VALUES (" + txtId.getText() + ", '" + txtTitulo.getText() + "', '"
				+ tfGenero.getText() + "', '" + tfDuracion.getText() + "', '" + tfSinopsis.getText() + "', '"
				+ tfIdioma.getText() + "', '" + tfPais.getText() + "', '" + tfActores.getText() + "', '"
				+ tfFotografia.getText() + "');";
		executeQuery(query);
		mostrarPeliculas();
	}
	/**
	 * Edita un registro y actualiza la lista de películas
	 */
	private void editar() {
		String query = "UPDATE peliculas SET titulo  = '" + txtTitulo.getText() + "', genero = '" + tfGenero.getText()
				+ "', duracion = '" + tfDuracion.getText() + "', sinopsis = '" + tfSinopsis.getText() + "', idioma = '"
				+ tfIdioma.getText() + "', pais = '" + tfPais.getText() + "', actores = '" + tfActores.getText()
				+ "', fotografia = '" + tfFotografia.getText() + "' WHERE id = " + txtId.getText() + "";

		executeQuery(query);
		mostrarPeliculas();
	}
	/**
	 * Elimina un registro y actualiza la lista de películas
	 */
	private void eliminar() {
		String query = "DELETE FROM peliculas WHERE id =" + txtId.getText() + "";
		executeQuery(query);
		mostrarPeliculas();
	}
	/**
	 * Método que maneja las consultas SQL
	 * @param query consulta a la DB
	 */
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
