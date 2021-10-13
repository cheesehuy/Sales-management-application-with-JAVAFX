package entities;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.swing.ImageIcon;

import javafx.scene.image.ImageView;
@Entity
@Table(name="sanpham")
public class Sanpham implements Serializable{
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	@Id 
	private int masanpham;
	//private int id;
	@OneToMany(mappedBy = "sanpham",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Chitiethoadon> chitiethoadon;
	
	
	@OneToMany(mappedBy = "sanpham",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Chitietdathang> chitietdathang;
	
	private String tensanpham;
	private String loaisanpham;
	private String donvi;
	private int giatien;
	private int donvitinh;
	private byte[] imagesp;


	
	public Sanpham(String tensanpham,String loaisanpham,String donvi,int giatien,int donvitinh,byte[] imagesp) {
		super();
		this.tensanpham=tensanpham;
	//	this.masanpham=masanpham;
		this.donvi=donvi;
		this.giatien=giatien;
		this.donvitinh=donvitinh;
		this.loaisanpham=loaisanpham;
		this.imagesp=imagesp;
		
	}
	
	public Sanpham(String tensanpham) {
		super();
		this.tensanpham=tensanpham;
	}
	
	public Sanpham(String tensanpham,String loaisanpham,String donvi,int giatien,int donvitinh) {
		super();
		this.tensanpham=tensanpham;
	//	this.masanpham=masanpham;
		this.donvi=donvi;
		this.giatien=giatien;
		this.donvitinh=donvitinh;
		this.loaisanpham=loaisanpham;
	//	this.imagesp=imagesp;
		
	}
	public Sanpham(int masanpham,String tensanpham,String loaisanpham,String donvi,int giatien) {
		super();
		this.tensanpham=tensanpham;
		this.masanpham=masanpham;
		this.donvi=donvi;
		this.giatien=giatien;
	//	this.donvitinh=donvitinh;
		this.loaisanpham=loaisanpham;
	//	this.imagesp=imagesp;
		
	}

	public Sanpham(int masanpham) {
		super();
	}
	
	public Sanpham(int masanpham, String tensanpham,String loaisanpham,String donvi,int giatien,int donvitinh) {
			this.tensanpham=tensanpham;
			this.masanpham=masanpham;
			this.donvi=donvi;
			this.giatien=giatien;
			this.donvitinh=donvitinh;
			this.loaisanpham=loaisanpham;
	}
	
	public Sanpham() {
		super();
	}
	
	public byte[] getImagesp() {
		return imagesp;
	}
	public void setImagesp(byte[] imagesp) {
		this.imagesp = imagesp;
	}
	public String getTensanpham() {
		return tensanpham;
	}
	public void setTensanpham(String tensanpham) {
		this.tensanpham = tensanpham;
	}
	public int getMasanpham() {
		return masanpham;
	}
	public void setMasanpham(int masanpham) {
		this.masanpham = masanpham;
	}
	public String getLoaisanpham() {
		return loaisanpham;
	}
	public void setLoaisanpham(String loaisanpham) {
		this.loaisanpham = loaisanpham;
	}
	public String getDonvi() {
		return donvi;
	}
	public void setDonvi(String donvi) {
		this.donvi = donvi;
	}
	public int getGiatien() {
		return giatien;
	}
	public void setGiatien(int giatien) {
		this.giatien = giatien;
	}
	public int getDonvitinh() {
		return donvitinh;
	}
	
		public void setDonvitinh(int donvitinh) {
			this.donvitinh = donvitinh;
		}
		public List<Chitiethoadon> getChitiethoadon() {
			return chitiethoadon;
		}
		public void setChitiethoadon(List<Chitiethoadon> chitiethoadon) {
			this.chitiethoadon = chitiethoadon;
		}
		public List<Chitietdathang> getChitietdathang() {
			return chitietdathang;
		}
		public void setChitietdathang(List<Chitietdathang> chitietdathang) {
			this.chitietdathang = chitietdathang;
		}		
		
		public Sanpham(String tensanpham,int masanpham ,String donvi,int donvitinh, int giatien,String loaisanpham) {
			this.tensanpham=tensanpham;
			this.masanpham=masanpham;
			this.donvi=donvi;
			this.giatien=giatien;
			this.donvitinh=donvitinh;
			this.loaisanpham=loaisanpham;
			
			//this.soluong=soluong;	
			//this.masanpham=masanpham;
			
	}
}
		/*public ImageView getImage() {
		return new ImageView(imagesp);
	}*/