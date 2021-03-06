package Nhacungcap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("nhacungcap.fxml"));
		Scene scene = new Scene(root);
		stage.getIcons().add(new Image(Main.class.getResourceAsStream("backgroundSGU.png")));
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("Phan mem quan ly ban hang");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
