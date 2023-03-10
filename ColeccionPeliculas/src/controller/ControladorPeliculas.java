package controller;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import model.Pelicula;

public class ControladorPeliculas implements Initializable {
	//Atributos creados en los ficheros FXML InterfazTabla y InterfazModal
	@FXML
	private Button btnEditar;

	@FXML
	private Button btnEliminar;

	@FXML
	private Button btnInsertar;

	@FXML
	private Button btnFotografia;

	@FXML
	private Button btnAceptar;

	@FXML
	private Button btnCancelar;

	@FXML
	private TableColumn<Pelicula, String> columnGenero;

	@FXML
	private TableColumn<Pelicula, Integer> columnId;

	@FXML
	private TableColumn<Pelicula, String> columnTitulo;

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
	private TextField tfTitulo;

	@FXML
	private TextField tfFotografia;

	@FXML
	private TextField tfId;

	@FXML
	private MenuItem menuItemActualizar;

	@FXML
	private MenuItem menuItemEliminar;

	@FXML
	private MenuItem menuItemNuevo;
	
	@FXML
	private MenuBar menuBar1;
	/**
	 * M??todo que muestra una ventana modal para a??adir un registro
	 * @param event
	 */
	@FXML
	void nuevoRegistroMenu(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root;
			//carga el FXML de la ventana modal
			root = FXMLLoader.load(ControladorPeliculas.class.getResource("/view/InterfazModalNuevoRegistro.fxml"));
			stage.setScene(new Scene(root));
			stage.setTitle("My modal window");
			stage.initModality(Modality.WINDOW_MODAL);
				  stage.initOwner(
				    ( (Node) event.getSource()).getScene().getWindow());
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Evento del bot??n A??adir que a??ade un registro a la DB
	 * 
	 * @param event
	 */
	@FXML
	void add(ActionEvent event) {
		insertar();
		limpiarCampos();
	}

	/**
	 * Evento del bot??n Editar que edita el registro seleccionado en la interfaz de
	 * la DB
	 * 
	 * @param event
	 */
	@FXML
	void editar(ActionEvent event) {
		editar();
		limpiarCampos();
	}

	/**
	 * Evento del bot??n eliminar que elimina un registro seleccionado en la interfaz
	 * de la DB
	 * 
	 * @param event
	 */
	@FXML
	void eliminar(ActionEvent event) {
		eliminar();
		limpiarCampos();
	}

	/**
	 * Al iniciar la aplicaci??n ejecuta un m??todo que muestra la lista de las
	 * pel??culas actuales dentro de la DB
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		mostrarPeliculas();
	}

	/**
	 * Sustituye el contenido de los TextField al hacer click en un registro
	 * 
	 * @param event
	 */
	@FXML
	void manejadorClick(MouseEvent event) {
		try {
			//Instancia de un objeto de la clase pelicula para invocar sus Getters y Setters
			Pelicula pelicula = tablaPeliculas.getSelectionModel().getSelectedItem();
			tfId.setText("" + pelicula.getId());
			tfTitulo.setText(pelicula.getTitulo());
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
	 * 
	 * @return conexi??n exitosa
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			//Conexi??n a la DB
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/peliculas", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}

	/**
	 * Realiza una consulta de selecci??n a la DB y guarda en la variable tipo
	 * ObservableList los registros de la DB en un ObservableArrayList
	 * 
	 * @return
	 */
	public ObservableList<Pelicula> getListaPeliculas() {
		Statement st;
		ResultSet rs;
		ObservableList<Pelicula> listaPeliculas = FXCollections.observableArrayList();
		Connection conn = getConnection();
		//sentencia SQL que seleccionar?? todas las entradas en la tabla
		String query = "SELECT * FROM peliculas";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			Pelicula pelicula;

			while (rs.next()) {
				//Se crea una instancia de la clase Pelicula y se asigna a cada columna su tipo de dato y se almacena
				pelicula = new Pelicula(rs.getInt("id"), rs.getString("titulo"), rs.getString("genero"),
						rs.getString("duracion"), rs.getString("sinopsis"), rs.getString("idioma"),
						rs.getString("pais"), rs.getString("actores"), rs.getString("fotografia"));
				//se a??ade a la ObservableList
				listaPeliculas.add(pelicula);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaPeliculas;
	}

	/**
	 * M??todo para mostrar las pel??culas. Se utiliza al iniciar la aplicaci??n,
	 * insertar, editar, o eliminar un registro
	 */
	public void mostrarPeliculas() {
		ObservableList<Pelicula> lista = getListaPeliculas();
		columnId.setCellValueFactory(new PropertyValueFactory<Pelicula, Integer>("id"));
		columnTitulo.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("titulo"));
		columnGenero.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("genero"));
		columnDuracion.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("duracion"));
		columnSinopsis.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("sinopsis"));
		columnIdioma.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("idioma"));
		columnPais.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("pais"));
		columnActores.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("actores"));
		columnFotografia.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("fotografia"));

		tablaPeliculas.setItems(lista);
	}

	/**
	 * Inserta un registro y actualiza la lista de pel??culas
	 */
	private void insertar() {
		String query = "INSERT INTO peliculas VALUES (" + tfId.getText() + ", '" + tfTitulo.getText() + "', '"
				+ tfGenero.getText() + "', '" + tfDuracion.getText() + "', '" + tfSinopsis.getText() + "', '"
				+ tfIdioma.getText() + "', '" + tfPais.getText() + "', '" + tfActores.getText() + "', '"
				+ tfFotografia.getText() + "');";
		executeQuery(query);
		mostrarPeliculas();
	}

	/**
	 * Edita un registro y actualiza la lista de pel??culas
	 */
	private void editar() {
		String query = "UPDATE peliculas SET titulo  = '" + tfTitulo.getText() + "', genero = '" + tfGenero.getText()
				+ "', duracion = '" + tfDuracion.getText() + "', sinopsis = '" + tfSinopsis.getText() + "', idioma = '"
				+ tfIdioma.getText() + "', pais = '" + tfPais.getText() + "', actores = '" + tfActores.getText()
				+ "', fotografia = '" + tfFotografia.getText() + "' WHERE id = " + tfId.getText() + "";

		executeQuery(query);
		mostrarPeliculas();
	}

	/**
	 * Elimina un registro y actualiza la lista de pel??culas
	 */
	private void eliminar() {
		String query = "DELETE FROM peliculas WHERE id =" + tfId.getText() + "";
		executeQuery(query);
		mostrarPeliculas();
	}

	/**
	 * M??todo que maneja las consultas SQL
	 * 
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
	
	/**
	 * Limpia los campos de datos de los TextField
	 */
	private void limpiarCampos() {
		tfActores.clear();
		tfDuracion.clear();
		tfFotografia.clear();
		tfGenero.clear();
		tfId.clear();
		tfIdioma.clear();
		tfPais.clear();
		tfSinopsis.clear();
		tfTitulo.clear();
	}

}
