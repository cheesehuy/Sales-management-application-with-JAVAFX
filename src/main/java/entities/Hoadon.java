package entities;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import entities.*;
@Entity
@Table(name="hoadon")
//@Access(AccessType.FIELD)
public class Hoadon implements Serializable{
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id 
	private int mahoadon;
	//@ManyToOne
	//@PrimaryKeyJoinColumn(name ="makh")
	//@PrimaryKeyJoinColumn(name=" manv")
	//private Nhanvien nv;
	//private KhachHang kh;
	@OneToMany(mappedBy="hoadon",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	private List<Chitiethoadon> chitiethoadon;
//	@JoinColumn()
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST},fetch = FetchType.LAZY)
	@JoinColumn(name="makh")
	@GeneratedValue
	private KhachHang khachhang;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="manv",nullable = true)
	@GeneratedValue
	private Nhanvien nhanvien;
	
	
	private LocalDateTime thoigianmua;
	private Float tonggia;
	
	
		
	public Hoadon() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Hoadon(int mahoadon, LocalDateTime thoigianmua, Float tonggia,Nhanvien nhanvien,KhachHang khachhang) {
		super();
		//this.id=id;
		this.mahoadon=mahoadon;
		this.thoigianmua=thoigianmua;
		this.tonggia=tonggia;
		this.nhanvien = nhanvien;
		this.khachhang = khachhang;
	//	this.manv=manv;
	}
	public Hoadon( LocalDateTime dateTime, Float tonggia,Nhanvien nhanvien,KhachHang khachhang) {
		super();
		//this.id=id;
		//this.mahoadon=mahoadon;
		this.thoigianmua=dateTime;
		this.tonggia=tonggia;
		this.nhanvien = nhanvien;
		this.khachhang = khachhang;
	//	this.manv=manv;
	}
	public int getMahoadon() {
		return mahoadon;
	}
	public void setMahoadon(int mahoadon) {
		this.mahoadon = mahoadon;
	}
	public LocalDateTime getThoigianmua() {
		return thoigianmua;
	}
	public void setThoigianmua(LocalDateTime thoigianmua) {
		this.thoigianmua = thoigianmua;
	}
	
/*	public Nhanvien getNv() {
		return nv;
	}

	public void setNv(Nhanvien nv) {
		this.nv = nv;
	}

	public KhachHang getKh() {
		return kh;
	}

	public void setKh(KhachHang kh) {
		this.kh = kh;
	}
*/
	public Float getTonggia() {
		return tonggia;
	}
	public void setTonggia(Float tonggia) {
		this.tonggia = tonggia;
	}

	public List<Chitiethoadon> getChitiethoadon() {
		return chitiethoadon;
	}

	public void setChitiethoadon(List<Chitiethoadon> chitiethoadon) {
		this.chitiethoadon = chitiethoadon;
	}


	public KhachHang getKhachhang() {
		return khachhang;
	}


	public void setKhachhang(KhachHang khachhang) {
		this.khachhang = khachhang;
	}


	public Nhanvien getNhanvien() {
		return nhanvien;
	}


	public void setNhanvien(Nhanvien nhanvien) {
		this.nhanvien = nhanvien;
	}
	
	
	
	
	

}
