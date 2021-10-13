package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

@Entity
@Table (name="phieutra")

public class Phieutrahang implements Serializable{
	
	@Id 
	@GeneratedValue
	private int maphieutra;
	private LocalDateTime thoigian;
	//private int mancc;
	//private int manv;
	
	@OneToMany(mappedBy = "phieutrahang",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Chitietphieutra> chitietphieutra;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "mancc",nullable = true)
	private Nhacungcap nhacungcap;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "manv",nullable = true)
	private Nhanvien nhanvien;
	private int tongtien;
	
	public Phieutrahang(int maphieutra,LocalDateTime thoigian, Nhacungcap nhacungcap, Nhanvien nhanvien) {
		super();
		this.maphieutra = maphieutra;
		this.thoigian = thoigian;
		this.nhacungcap = nhacungcap;
		this.nhanvien = nhanvien;
	}

	public int getMaphieutra() {
		return maphieutra;
	}

	public void setMaphieutra(int maphieutra) {
		this.maphieutra = maphieutra;
	}

	public LocalDateTime getThoigian() {
		return thoigian;
	}

	public void setThoigian(LocalDateTime thoigian) {
		this.thoigian = thoigian;
	}

/*	public int getMancc() {
		return mancc;
	}

	public void setMancc(int mancc) {
		this.mancc = mancc;
	}

	public int getManv() {
		return manv;
	}

	public void setManv(int manv) {
		this.manv = manv;
	}*/

	public Phieutrahang() {
		// TODO Auto-generated constructor stub
	}

	public List<Chitietphieutra> getChitietphieutra() {
		return chitietphieutra;
	}

	public void setChitietphieutra(List<Chitietphieutra> chitietphieutra) {
		this.chitietphieutra = chitietphieutra;
	}

	public Nhacungcap getNhacungcap() {
		return nhacungcap;
	}

	public void setNhacungcap(Nhacungcap nhacungcap) {
		this.nhacungcap = nhacungcap;
	}

	public Nhanvien getNhanvien() {
		return nhanvien;
	}

	public void setNhanvien(Nhanvien nhanvien) {
		this.nhanvien = nhanvien;
	}
	public Phieutrahang(LocalDateTime thoigian,int tongtien, Nhacungcap nhacungcap, Nhanvien nhanvien) {
		super();
		//this.maphieutra = maphieutra;
		this.thoigian = thoigian;
		this.nhacungcap = nhacungcap;
		this.nhanvien = nhanvien;
		this.tongtien = tongtien;
	}

	public int getTongtien() {
		return tongtien;
	}

	public void setTongtien(int tongtien) {
		this.tongtien = tongtien;
	}
	

}
