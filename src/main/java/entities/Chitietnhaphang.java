package entities;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="chitietnhaphang")
public class Chitietnhaphang implements Serializable{
	
//	private int manhaphang;
//	private int masanpham;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name="manhaphang")
	private Phieunhaphang phieunhaphang;
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn (name="masanpham")
	private Sanpham sanpham;
	
	private int soluong;
	private double thanhtien;
	private int dongia;
	

	
	public Chitietnhaphang() {
		super();
		}
	
	public Chitietnhaphang(Phieunhaphang phieunhaphang, Sanpham sanpham, int soluong, double thanhtien,Integer dongia) {
		super();
		this.phieunhaphang = phieunhaphang;
		this.sanpham = sanpham;
		this.soluong = soluong;
		this.thanhtien = thanhtien;
		this.dongia = dongia;
	}
	
	
	public Chitietnhaphang( Sanpham sanpham, int soluong, double thanhtien,int dongia) {
		super();
		
		this.sanpham = sanpham;
		this.soluong = soluong;
		this.thanhtien = thanhtien;
		this.dongia = dongia;
	}
	
	public double getThanhtien() {
		return thanhtien;
	}

	public void setThanhtien(double thanhtien) {
		this.thanhtien = thanhtien;
	}

	public Sanpham getSanpham() {
		return sanpham;
	}
	public void setSanpham(Sanpham sanpham) {
		this.sanpham = sanpham;
	}
	public void setSoluong(int soluong) {
		this.soluong = soluong;
	}
	public int getSoluong() {
		return soluong;
	}

	public Phieunhaphang getPhieunhaphang() {
		return phieunhaphang;
	}
	

	public int getDongia() {
		return dongia;
	}

	public void setDongia(int dongia) {
		this.dongia = dongia;
	}

	public void setPhieunhaphang(Phieunhaphang phieunhaphang) {
		this.phieunhaphang = phieunhaphang;
	}
	
	
	
}
