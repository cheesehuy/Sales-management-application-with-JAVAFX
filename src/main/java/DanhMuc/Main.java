package DanhMuc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
	//	Parent root = FXMLLoader.load(getClass().getResource("/Nhanvien/nhanvien.fxml"));
	//	Parent root = FXMLLoader.load(getClass().getResource("chucnangquanly.fxml"));
		Parent root = FXMLLoader.load(getClass().getResource("danhmuc.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("danhmuc.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Phan mem quan ly ban hang");
		stage.show();	

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub 
		launch(args);
	}
}