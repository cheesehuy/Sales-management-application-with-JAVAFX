package entities;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="chitiethoadon")


public class Chitiethoadon implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="mahoadon")	
	private Hoadon hoadon;
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="masanpham")
	//@JoinColumn(name="masanpham")
	private Sanpham sanpham;
	private int soluong;
	private double thanhtien;
	
	
	public Chitiethoadon(int soluong,Hoadon hoadon,Sanpham sanpham) {
		super();
		this.soluong=soluong;
		this.hoadon= hoadon;
		this.sanpham=sanpham;
	
	}
	public Chitiethoadon(int soluong,Hoadon hoadon,Sanpham sanpham,Double thanhtien) {
		super();
		this.soluong=soluong;
		this.hoadon= hoadon;
		this.sanpham=sanpham;
		this.thanhtien=thanhtien;
	}
	
	public Chitiethoadon(int soluong,Sanpham sanpham,Double thanhtien) {
		super();
		this.soluong=soluong;
		//this.hoadon= hoadon;
		this.sanpham=sanpham;
		this.thanhtien=thanhtien;
	}
	
	
	
	
	

/*	public String getMahoadon() {
		return mahoadon;
	}


	public void setMahoadon(String mahoadon) {
		this.mahoadon = mahoadon;
	}


	public int getMasanpham() {
		return masanpham;
	}


	public void setMasanpham(int masanpham) {
		this.masanpham = masanpham;
	}*/
	


	public int getSoluong() {
		return soluong;
	}


	public double getThanhtien() {
		return thanhtien;
	}


	public void setThanhtien(double thanhtien) {
		this.thanhtien = thanhtien;
	}


	public void setSoluong(int soluong) {
		this.soluong = soluong;
	}
	
	public Hoadon getHoadon() {
		return hoadon;
	}


	public void setHoadon(Hoadon hoadon) {
		this.hoadon = hoadon;
	}
	

	public Sanpham getSanpham() {
		return sanpham;
	}

	public void setSanpham(Sanpham sanpham) {
		this.sanpham = sanpham;
	}

	public Chitiethoadon() {
		
	}
	public Chitiethoadon(int soluong) {
		super();
		this.soluong=soluong;
		
	
	}

}
