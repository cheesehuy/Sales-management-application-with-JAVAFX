package KhachHang;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("khachhang.fxml"));
		//Parent root = FXMLLoader.load(getClass().getResource("nhanvien.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Phan mem quan ly ban hang");
		stage.show();
	}
}
