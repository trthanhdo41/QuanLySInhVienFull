import java.util.ArrayList;

public class MonHoc {
	private String maMonHoc;
	private String tenMonHoc;
	private int soTinChi;
	private ArrayList<SinhVien> danhSachSinhVien = new ArrayList<>();
	
	public MonHoc(String maMonHoc, String tenMonHoc, int soTinChi) {
		this.maMonHoc = maMonHoc;
		this.tenMonHoc = tenMonHoc;
		this.soTinChi = soTinChi;
	}
	
	public MonHoc() {
		//rỗng
	}

	public String getMaMonHoc() {
		return maMonHoc;
	}

	public void setMaMonHoc(String maMonHoc) {
		this.maMonHoc = maMonHoc;
	}

	public String getTenMonHoc() {
		return tenMonHoc;
	}

	public void setTenMonHoc(String tenMonHoc) {
		this.tenMonHoc = tenMonHoc;
	}

	public int getSoTinChi() {
		return soTinChi;
	}

	public void setSoTinChi(int soTinChi) {
		this.soTinChi = soTinChi;
	}
	// Thêm sinh viên vào môn học
    public void themSinhVien(SinhVien sinhVien) {
        danhSachSinhVien.add(sinhVien);
    }

    // Xóa sinh viên khỏi môn học
    public void xoaSinhVien(SinhVien sinhVien) {
        danhSachSinhVien.remove(sinhVien);
    }
}
