package NhaCungCap;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import BanHang.BanHangController;
import DanhMuc.danhmucController;
import Nhacungcap.nhacungcapController;
import Nhanvien.NhanvienController;
import QLBH.HibernateUtils;
import entities.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.stage.Stage;
@javax.annotation.processing.Generated(value = "org.junit-tools-1.1.0")
public class NhaCungCapControllerTest {
	private nhacungcapController createTestSubject() {
		return new nhacungcapController();
	}
	@Test
	public void testgetNhaCungCap() throws Exception {
		nhacungcapController testSubject;
		ObservableList<Nhacungcap> result;
		testSubject = createTestSubject();
		result = testSubject.getNhacungcap();
		assertNotNull(result);
		assertTrue("Hiển thị danh sách nhà cung cấp thành công",result.size()>0);
	}
	
}
