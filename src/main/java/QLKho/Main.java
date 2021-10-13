package QLKho;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application {
	private static double xoffset =0; 
	private static double yoffset =0; 
	public static void main(String[] args)  {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("QLKho.fxml"));
		Scene scene = new Scene(root);
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
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
		scene.getStylesheets().add(getClass().getResource("QLKho.css").toExternalForm());
		stage.getIcons().add(new Image(Main.class.getResourceAsStream("backgroundSGU.png")));
		stage.setScene(scene);
		stage.setTitle("Phan mem quan ly ban hang");
		stage.show();
	}

}
