package QLBH;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class QLBH extends Application {
	private static double xoffset =0; 
	private static double yoffset =0; 
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("loginform.fxml"));
		Scene scene = new Scene(root);
		scene.setFill(null);
		scene.getStylesheets().add(getClass().getResource("QLBH.css").toExternalForm());
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
			    xoffset = stage.getX() - mouseEvent.getScreenX();
			    yoffset = stage.getY() - mouseEvent.getScreenY();
			  }
			});
			scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
			    stage.setX(mouseEvent.getScreenX() + xoffset);
			    stage.setY(mouseEvent.getScreenY() + yoffset);
			  }
			});
			
		stage.getIcons().add(new Image(QLBH.class.getResourceAsStream("backgroundSGU.png")));
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setTitle("Phần mềm quản lý cửa hàng tiện lợi");
		stage.show();	
		

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub 
		launch(args);
	}
}
