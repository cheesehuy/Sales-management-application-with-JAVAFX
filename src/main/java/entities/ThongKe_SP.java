package entities;

public class ThongKe_SP {
	private String tensanpham;
	private Long soluong;
	
	public ThongKe_SP(String tensanpham,Long soluong) {
		this.tensanpham = tensanpham;
		this.soluong = soluong;
	}
	public String getTensanpham() {
		return tensanpham;
	}
	public void setTensanpham(String tensanpham) {
		this.tensanpham = tensanpham;
	}
	public Long getSoluong() {
		return soluong;
	}
	public void setSoluong(Long soluong) {
		this.soluong = soluong;
	}
	
	
}
