package entities;

import java.sql.Date;
import java.time.LocalDateTime;

public class DT_1ngay {
	LocalDateTime thang;
	double doanhthu;
	
	public DT_1ngay(LocalDateTime thang,double doanhthu) {
		super();
		this.thang =thang;
		this.doanhthu = doanhthu;
	}
	public DT_1ngay() {
		super();
	}
	
	
	
	
	public LocalDateTime getThang() {
		return thang;
	}
	public void setThang(LocalDateTime thang) {
		this.thang = thang;
	}
	public double getDoanhthu() {
		return doanhthu;
	}
	public void setDoanhthu(double doanhthu) {
		this.doanhthu = doanhthu;
	}
	
}
