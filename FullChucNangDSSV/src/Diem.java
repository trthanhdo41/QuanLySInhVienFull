
public class Diem {
	private String maSinhVien;
	private String maMonHoc;
	private double  diemSo;
	
	public Diem(String maSinhVien, String maMonHoc, double diemSo) {
		this.maSinhVien = maSinhVien;
		this.maMonHoc = maMonHoc;
		this.diemSo = diemSo;
	}
	
	public Diem() {
	}

	public String getMaSinhVien() {
		return maSinhVien;
	}

	public void setMaSinhVien(String maSinhVien) {
		this.maSinhVien = maSinhVien;
	}

	public String getMaMonHoc() {
		return maMonHoc;
	}

	public void setMaMonHoc(String maMonHoc) {
		this.maMonHoc = maMonHoc;
	}

	public double getDiemSo() {
		return diemSo;
	}

	public void setDiemSo(double diemSo) {
		this.diemSo = diemSo;
	}
	
	
}
