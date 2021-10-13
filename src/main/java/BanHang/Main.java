package BanHang;
import java.sql.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import QLBH.HibernateUtils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		HibernateUtils.getSessionFactory();
	//	Parent root = FXMLLoader.load(getClass().getResource("/QLBH/giaodienquanly.fxml"));
		Parent root = FXMLLoader.load(getClass().getResource("banhang.fxml"));
		//Parent root = FXMLLoader.load(getClass().getResource("nhanvien.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Phan mem quan ly ban hang");
		stage.show();
	}
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		launch(args);
		
	}

}
