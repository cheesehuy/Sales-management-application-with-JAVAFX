package BanHang;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import DanhMuc.danhmucController;
import Nhanvien.NhanvienController;
import QLBH.HibernateUtils;
import entities.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.stage.Stage;
@javax.annotation.processing.Generated(value = "org.junit-tools-1.1.0")

public class BanHangControllerTest {
	private BanHangController createTestSubject() {
		return new BanHangController();
	}
	@Test
	public void testgetSanPham() throws Exception {
		BanHangController testSubject;
		ObservableList<Sanpham> result;
		testSubject = createTestSubject();
		result = testSubject.getSanpham();
		assertNotNull(result);
		assertTrue("Hiển thị danh sách sản phẩm thất bại",result.size()>0);
	}
	
	@Test
	public void TestComboboxLoaiSP() {
		Session session = HibernateUtils.getSessionFactory().openSession();
		BanHangController testCombobox;
		testCombobox = createTestSubject();
		ObservableList<String> loaisanphamList = testCombobox.getLoaiSanpham();
		String combobox1 ="nước giải khát";
		String combobox2 ="snack";
		ArrayList<String> loaisanpham = new ArrayList<>();
		loaisanpham.add(combobox1);
		loaisanpham.add(combobox2);
		loaisanpham.toString();
		assertEquals(loaisanpham, loaisanphamList,"Thất bại");
	}
	
}
