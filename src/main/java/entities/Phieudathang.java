package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "dathang")

public class Phieudathang implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int madathang;
	private LocalDateTime thoigian;
	private int tongtien;
	private Boolean kiemtrahang;

	@OneToMany(mappedBy = "phieudathang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Chitietdathang> chitietdathang;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "mancc", nullable = true)
	@GeneratedValue
	private Nhacungcap nhacungcap;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "manv", nullable = true)
	@GeneratedValue
	private Nhanvien nhanvien;

	public Phieudathang(int madathang, LocalDateTime thoigian, int tongtien, Nhacungcap nhacungcap) {
		super();
		this.madathang = madathang;
		this.thoigian = thoigian;
		this.tongtien = tongtien;
		this.nhacungcap = nhacungcap;
	}

	public Phieudathang(int madathang, LocalDateTime thoigian, int tongtien, Nhacungcap nhacungcap,
			Boolean kiemtrahang) {
		super();
		this.madathang = madathang;
		this.thoigian = thoigian;
		this.tongtien = tongtien;
		this.nhacungcap = nhacungcap;
		this.kiemtrahang = kiemtrahang;
	}

	public int getMadathang() {
		return madathang;
	}

	public void setMadathang(int madathang) {
		this.madathang = madathang;
	}

	public LocalDateTime getThoigian() {
		return thoigian;
	}

	public void setThoigiandat(LocalDateTime thoigian) {
		this.thoigian = thoigian;
	}

	public double getTongtien() {
		return tongtien;
	}

	public void setTongtien(int tongtien) {
		this.tongtien = tongtien;
	}

	public void setThoigian(LocalDateTime thoigian) {
		this.thoigian = thoigian;
	}

	public Boolean getKiemtrahang() {
		return kiemtrahang;
	}

	public void setKiemtrahang(Boolean kiemtrahang) {
		this.kiemtrahang = kiemtrahang;
	}

	public Phieudathang() {
	}

	public List<Chitietdathang> getChitietdathang() {
		return chitietdathang;
	}

	public void setChitietdathangs(List<Chitietdathang> chitietdathang) {
		this.chitietdathang = chitietdathang;
	}

	public Nhacungcap getNhacungcap() {
		return nhacungcap;
	}

	public void setNhacungcap(Nhacungcap nhacungcap) {
		this.nhacungcap = nhacungcap;
	}

	public void setChitietdathang(List<Chitietdathang> chitietdathang) {
		this.chitietdathang = chitietdathang;
	}

	public Nhanvien getNhanvien() {
		return nhanvien;
	}

	public void setNhanvien(Nhanvien nhanvien) {
		this.nhanvien = nhanvien;
	}

	public Phieudathang(LocalDateTime thoigian, int tongtien, Nhacungcap nhacungcap, Nhanvien nhanvien) {
		super();
		this.thoigian = thoigian;
		this.tongtien = tongtien;
		this.nhacungcap = nhacungcap;
		this.nhanvien = nhanvien;
	}

	public Phieudathang(LocalDateTime thoigian, int tongtien, Nhacungcap nhacungcap, Nhanvien nhanvien,
			boolean kiemtrahang) {
		super();
		this.thoigian = thoigian;
		this.tongtien = tongtien;
		this.nhacungcap = nhacungcap;
		this.nhanvien = nhanvien;
		this.kiemtrahang = kiemtrahang;
	}

}
