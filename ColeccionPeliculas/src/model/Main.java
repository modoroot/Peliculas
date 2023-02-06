package model;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.antonio.pantallalogin.Login;

/**
 * Clase lanzadora (Main)
 * @author Antonio Miguel Núñez Ariza
 * @version 1.0
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
    	//Creación del objeto Login
    	Login login = new Login(stage->{
    		// Carga del archivo FXML que contiene la interfaz para la tabla
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/InterfazTabla.fxml"));
            Parent root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
            Scene scene = new Scene(root);
            primaryStage.setTitle("Películas DB - Antonio Miguel Núñez Ariza");
            primaryStage.setScene(scene);
            primaryStage.show();
    	});
    	login.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}