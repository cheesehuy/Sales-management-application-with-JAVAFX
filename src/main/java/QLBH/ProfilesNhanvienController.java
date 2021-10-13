package QLBH;

import java.io.File;
import java.io.FileOutputStream;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import entities.*;

public class ProfilesNhanvienController {

	 @FXML
	    private Text hovatennv;

	    @FXML
	    private Text chucvunhanvien;

	    @FXML
	    private Text gioitinhnhanvien;

	    @FXML
	    private Text sdtnhanvien;

	    @FXML
	    private Text cmndnhanvien;

	    @FXML
	    private Text diachinhanvien;

	    @FXML
	    private ImageView imagenhanvien;

	    @FXML
	    private Text ngaysinhnhanvien;

	    @FXML
	    private Text ngayvaolam;
	    
	    @FXML
		private ImageView exit;

		@FXML
		void exit(MouseEvent event) {	
			if( GiaoDienQLController.getInstance() ==null) {
        		Stage stage = (Stage) exit.getScene().getWindow();
        		stage.close();
        		GiaoDienNhanvienController.getInstance().falsedisable();
          	}
        	else if(GiaoDienNhanvienController.getInstance() ==null){
        	Stage stage = (Stage) exit.getScene().getWindow();
    		stage.close();
    		GiaoDienQLController.getInstance().falsedisable();
    		}
        	else {
        		Stage stage = (Stage) exit.getScene().getWindow();
        		stage.close();
        		GiaoDienQLController.getInstance().falsedisable();
        		GiaoDienNhanvienController.getInstance().falsedisable();
        	}
		}


    
    
    public void loadData(Taikhoannv taikhoannv) {
    	hovatennv.setText(taikhoannv.getNhanvien().getHovaten());
    	ngaysinhnhanvien.setText(String.valueOf(taikhoannv.getNhanvien().getNgaysinh()));
    	gioitinhnhanvien.setText(taikhoannv.getNhanvien().getGioitinh());
    	sdtnhanvien.setText(String.valueOf(taikhoannv.getNhanvien().getSdt()));
    	chucvunhanvien.setText(taikhoannv.getNhanvien().getChucvu());
    	cmndnhanvien.setText(String.valueOf(taikhoannv.getNhanvien().getCmnd()));
    	diachinhanvien.setText(taikhoannv.getNhanvien().getDiachi());
    	ngayvaolam.setText(String.valueOf(taikhoannv.getNhanvien().getNgayvaolam()));
    	byte[] getImageInBytes = taikhoannv.getNhanvien().getImage();

		try {
			FileOutputStream fos = new FileOutputStream(new File("photo3.jpg"));
			fos.write(getImageInBytes);
			fos.close();
			Image image = new Image("file:photo3.jpg", imagenhanvien.getFitHeight(), imagenhanvien.getFitHeight(), true, true);
			imagenhanvien.setImage(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }

}
