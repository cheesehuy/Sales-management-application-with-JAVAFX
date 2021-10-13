package entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "chitietdathang")
public class Chitietdathang implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "madathang")
	private Phieudathang phieudathang;

	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "masanpham")
	private Sanpham sanpham;
	private int dongia;

	private int soluong;

	private double thanhtien;

	public double getThanhtien() {
		return thanhtien;
	}

	public void setThanhtien(double thanhtien) {
		this.thanhtien = thanhtien;
	}

	public Chitietdathang() {
		super();
	}

	public Sanpham getSanpham() {
		return sanpham;
	}

	public void setSanpham(Sanpham sanpham) {
		this.sanpham = sanpham;
	}

	public int getSoluong() {
		return soluong;
	}

	public void setSoluong(int soluong) {
		this.soluong = soluong;
	}

	public Chitietdathang(Phieudathang phieudathang, Sanpham sanpham, int soluong) {
		super();

		this.phieudathang = phieudathang;
		this.sanpham = sanpham;
		this.soluong = soluong;
	}

	public Chitietdathang(int soluong, Sanpham sanpham, double thanhtien,int dongia) {
		super();
		this.thanhtien = thanhtien;
		this.sanpham = sanpham;
		this.soluong = soluong;
		this.dongia = dongia;
	}
	
	public Chitietdathang(int soluong, Sanpham sanpham,int dongia) {
		super();
		this.sanpham = sanpham;
		this.soluong = soluong;
		this.dongia = dongia;
	}
	

	public Phieudathang getPhieudathang() {
		return phieudathang;
	}

	public void setPhieudathang(Phieudathang phieudathang) {
		this.phieudathang = phieudathang;
	}

	public Chitietdathang(Phieudathang phieudathang, Sanpham sanpham, int soluong, double thanhtien,int dongia) {
		super();

		this.phieudathang = phieudathang;
		this.sanpham = sanpham;
		this.soluong = soluong;
		this.thanhtien = thanhtien;
		this.dongia = dongia;
	}

	public int getDongia() {
		return dongia;
	}

	public void setDongia(int dongia) {
		this.dongia = dongia;
	}
	

}
