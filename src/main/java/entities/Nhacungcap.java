package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;

@Entity
@Table(name = "nhacungcap")
public class Nhacungcap implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer mancc;
	private String tenncc;
	private String diachi;
	private String sodienthoai;
	private String email;
	private Integer sotienno;
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY,mappedBy = "nhacungcap")
	private List<Phieutrahang> phieutrahang;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY,mappedBy = "nhacungcap")
	private List<Phieunhaphang> phieunhaphang;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY,mappedBy = "nhacungcap")
	private List<Phieudathang> phieudathang;
	//private Button xoa;
	
	public Nhacungcap() {
		super();
	}
	
	
	
	public Nhacungcap(int mancc) {
		super();
		this.mancc = mancc;
	}
	
//	public Nhacungcap(String tenncc) {
//		super();
//		this.tenncc=tenncc;
//	}
	
	public Nhacungcap(String sodienthoai) {
		this.sodienthoai = sodienthoai;
	}
	

	public Nhacungcap(Integer mancc, String tenncc, String diachi, String sodienthoai, String email,Integer sotienno) {
		super();
		this.mancc = mancc;
		this.tenncc = tenncc;
		this.diachi = diachi;
		this.sodienthoai = sodienthoai;
		this.email = email;
		this.sotienno = sotienno; 
	//	this.xoa = xoa;
	}
	
	

//	public Button getXoa() {
	//	return xoa;
	//}



	//public void setXoa(Button xoa) {
		//this.xoa = xoa;
//	}



	public Nhacungcap(String tenncc, String diachi, String sodienthoai, String email,Integer sotienno) {
		super();

		this.tenncc = tenncc;
		this.diachi = diachi;
		this.sodienthoai = sodienthoai;
		this.email = email;
		this.sotienno = sotienno;
	}
	
	

	public Integer getSotienno() {
		return sotienno;
	}

	public void setSotienno(Integer sotienno) {
		this.sotienno = sotienno;
	}

	public Integer getMancc() {
		return mancc;
	}

	public void setMancc(Integer mancc) {
		this.mancc = mancc;
	}

	public String getTenncc() {
		return tenncc;
	}

	public void setTenncc(String tenncc) {
		this.tenncc = tenncc;
	}

	public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public String getSodienthoai() {
		return sodienthoai;
	}

	public void setSodienthoai(String sodienthoai) {
		this.sodienthoai = sodienthoai;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
		return String.valueOf(mancc);
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



}
