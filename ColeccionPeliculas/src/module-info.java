module ColeccionPeliculas {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	requires ComponentePeliculas;
	
	opens model to javafx.graphics, javafx.fxml, javafx.base, javafx.controls;
	opens controller to javafx.graphics, javafx.fxml, javafx.base, javafx.controls;
	
}
