package entities;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import javafx.scene.control.DatePicker;


@Entity
@Table(name="nhanvien")
public class Nhanvien implements Serializable{
	
	
//	private Taikhoannv taikhoannv;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GeneratedValue
	@Id
	private int manv;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY,mappedBy = "nhanvien")
	private List<Hoadon> hoadon;
	
	/*@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "nhanvien")
	private List<Hoadon> hoadon;*/
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true,mappedBy = "nhanvien")
	private Taikhoannv taikhoannv;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY,mappedBy = "nhanvien")
	private List<Phieutrahang> phieutrahang;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY,mappedBy = "nhanvien")
	private List<Phieunhaphang> phieunhaphang;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY,mappedBy = "nhanvien")
	private List<Phieudathang> phieudathang; 
	private String hovaten;
	private LocalDate ngaysinh;
	private String chucvu;
	private String sdt;
	private int cmnd;
	private String gioitinh;
	private String diachi;
	private byte[] image;
	private LocalDate ngayvaolam;
	public Nhanvien() {
		super();
	}
	
	
	public Nhanvien(int manv,String hovaten,LocalDate ngaysinh,String chucvu,String gioitinh,String sdt,int cmnd,String diachi,byte[] image,LocalDate ngayvaolam) {
		super();
		this.manv=manv;
		this.hovaten=hovaten;
		this.ngaysinh=ngaysinh;
		this.chucvu=chucvu;
		this.gioitinh=gioitinh;
		this.sdt=sdt;
		this.cmnd=cmnd;
		this.diachi=diachi;
		this.image = image;
		this.ngayvaolam = ngayvaolam;
	}
	public Nhanvien(int manv,String hovaten,LocalDate ngaysinh,String chucvu,String gioitinh,String sdt,int cmnd,String diachi,LocalDate ngayvaolam) {
		super();
		this.manv=manv;
		this.hovaten=hovaten;
		this.ngaysinh=ngaysinh;
		this.chucvu=chucvu;
		this.gioitinh=gioitinh;
		this.sdt=sdt;
		this.cmnd=cmnd;
		this.diachi=diachi;
		this.ngayvaolam=ngayvaolam;
	}
	public Nhanvien(String hovaten,LocalDate ngaysinh,String chucvu,String gioitinh,String sdt,int cmnd,String diachi,byte[] image,LocalDate ngayvaolam) {
		super();
		this.hovaten=hovaten;
		this.ngaysinh=ngaysinh;
		this.chucvu=chucvu;
		this.gioitinh=gioitinh;
		this.sdt=sdt;
		this.cmnd=cmnd;
		this.diachi=diachi;
		this.image = image;
		this.ngayvaolam=ngayvaolam;
	}
	
	public Nhanvien(int manv) {
		super();
		this.manv=manv;
	}
			
	public Nhanvien(String hovaten,LocalDate ngaysinh,String chucvu,String sdt,int cmnd,String diachi) {
		super();
		//this.id=id;
		this.hovaten=hovaten;
		this.ngaysinh=ngaysinh;
		this.chucvu=chucvu;
		this.sdt=sdt;
		this.cmnd=cmnd;
		this.diachi=diachi;
	}
	
	/*public Nhanvien(Image imgnhanvien,String hovaten,int ngaysinh,String chucvu,int sdt,int cmnd,String diachi ) {
		super();
		this.imgnhanvien=imgnhanvien;
		//this.id=id;
		this.hovaten=hovaten;
		this.ngaysinh=ngaysinh;
		this.chucvu=chucvu;
		this.sdt=sdt;
		this.cmnd=cmnd;
		this.diachi=diachi;
	}*/
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	
	public LocalDate getNgayvaolam() {
		return ngayvaolam;
	}

	public void setNgayvaolam(LocalDate ngayvaolam) {
		this.ngayvaolam = ngayvaolam;
	}

	public int getManv() {
		return manv;
	}
	public void setManv(int manv) {
		this.manv = manv;
	}
	public String getGioitinh() {
		return gioitinh;
	}

	public void setGioitinh(String gioitinh) {
		this.gioitinh = gioitinh;
	}

	//	public Image getImgnhanvien() {
	//	return imgnhanvien;
	//}
//	public void setImgnhanvien(Image imgnhanvien) {
	//	this.imgnhanvien = imgnhanvien;
//	}
	public String getDiachi() {
		return diachi;
	}
	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}
	public String getHovaten() {
		return hovaten;
	}
	public void setHovaten(String hovaten) {
		this.hovaten = hovaten;
	}
	
	public LocalDate getNgaysinh() {
		return ngaysinh;
	}

	public void setNgaysinh(LocalDate ngaysinh) {
		this.ngaysinh = ngaysinh;
	}

	public String getChucvu() {
		return chucvu;
	}
	public void setChucvu(String chucvu) {
		this.chucvu = chucvu;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public int getCmnd() {
		return cmnd;
	}
	public void setCmnd(int cmnd) {
		this.cmnd = cmnd;
	}


/*	public List<Hoadon> getHoadon() {
		return hoadon;
	}


	public void setHoadon(List<Hoadon> hoadon) {
		this.hoadon = hoadon;
	}*/
	
	 public String toString() {
	        return String.valueOf(manv);
	    }
	
	public List<Hoadon> getHoadon() {
		return hoadon;
	}


	public void setHoadon(List<Hoadon> hoadon) {
		this.hoadon = hoadon;
	}


	public List<Phieutrahang> getPhieutrahang() {
		return phieutrahang;
	}


	public void setPhieutrahang(List<Phieutrahang> phieutrahang) {
		this.phieutrahang = phieutrahang;
	}


	public List<Phieunhaphang> getPhieunhaphang() {
		return phieunhaphang;
	}


	public void setPhieunhaphang(List<Phieunhaphang> phieunhaphang) {
		this.phieunhaphang = phieunhaphang;
	}


	public List<Phieudathang> getPhieudathang() {
		return phieudathang;
	}


	public void setPhieudathang(List<Phieudathang> phieudathang) {
		this.phieudathang = phieudathang;
	}


	public Taikhoannv getTaikhoannv() {
		return taikhoannv;
	}

	public void setTaikhoannv(Taikhoannv taikhoannv) {
		this.taikhoannv = taikhoannv;
	}


	
	
	
 ;

	

}
