package QLKho;
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
public class KhoControllerTest {
	private QLKhoController createTestSubject() {
		return new QLKhoController();
	}
	@Test
	public void testgetSanPham() throws Exception {
		QLKhoController testSubject;
		ObservableList<Sanpham> result;
		testSubject = createTestSubject();
		result = testSubject.getSanpham();
		assertNotNull(result);
		assertTrue("Hiển thị danh sách sản phẩm thành công",result.size()>0);
	}

}
