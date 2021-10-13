package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

@Entity
@Table (name="nhaphang")


public class Phieunhaphang implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int manhaphang;
	
	@OneToMany (mappedBy = "phieunhaphang",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Chitietnhaphang> chitietnhaphang;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="manv",nullable = true)
	@GeneratedValue
	private Nhanvien nhanvien;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="mancc",nullable = true)
	@GeneratedValue
	private Nhacungcap nhacungcap;
	
	
	private LocalDateTime thoigian;
	private int tongtien;
//	private int mancc;
//	private int manv;
	
	
	
	
//	public String getManhaphang() {
//		return manhaphang;
//	}

//	public void setManhaphang(String manhaphang) {
//		this.manhaphang = manhaphang;
//	}
	
	

	public Phieunhaphang( LocalDateTime dateTime, int tongtien,Nhacungcap nhacungcap, Nhanvien nhanvien) {
	super();
	
	this.thoigian =dateTime ;
	this.tongtien = tongtien;
	this.nhacungcap = nhacungcap;
	this.nhanvien = nhanvien;
}

	

	public LocalDateTime getThoigian() {
		return thoigian;
	}



	public void setThoigian(LocalDateTime thoigian) {
		this.thoigian = thoigian;
	}



	public List<Chitietnhaphang> getChitietnhaphang() {
		return chitietnhaphang;
	}

	public void setChitietnhaphang(List<Chitietnhaphang> chitietnhaphang) {
		this.chitietnhaphang = chitietnhaphang;
	}

	public Nhanvien getNhanvien() {
		return nhanvien;
	}

	public void setNhanvien(Nhanvien nhanvien) {
		this.nhanvien = nhanvien;
	}

	public Nhacungcap getNhacungcap() {
		return nhacungcap;
	}

	public void setNhacungcap(Nhacungcap nhacungcap) {
		this.nhacungcap = nhacungcap;
	}

	public int getManhaphang() {
		return manhaphang;
	}

	public void setManhaphang(int manhaphang) {
		this.manhaphang = manhaphang;
	}

	public int getTongtien() {
		return tongtien;
	}

	public void setTongtien(int tongtien) {
		this.tongtien = tongtien;
	}


	public Phieunhaphang() {
		super();
		// TODO Auto-generated constructor stub
	}

}
