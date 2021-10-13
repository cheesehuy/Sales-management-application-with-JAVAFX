package Nhanvien;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("nhanvien.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Phan mem quan ly ban hang");
		stage.show();
	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
