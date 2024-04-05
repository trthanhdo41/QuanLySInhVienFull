import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class SinhVien {
	private String maSinhVien;
	private String hoDem;
	private String ten;
	private Date namSinh;
	private String gioiTinh;
	private static int soLuongSinhVien = 0;
	private ArrayList<MonHoc> danhSachMonHoc = new ArrayList<>();
	private ArrayList<MonHoc> danhSachMonHocDaXoa = new ArrayList<>();
	protected HashMap<MonHoc, Double> bangDiem;
	protected HashMap<MonHoc, String> bangXepLoai;
	protected HashMap<String, Double> diemMonHoc;
	
	public SinhVien(String maSinhVien, String hoDem, String ten, Date namSinh, String gioiTinh) {
		this.maSinhVien = maSinhVien;
		this.hoDem = hoDem;
		this.ten = ten;
		this.namSinh = namSinh;
		this.gioiTinh = gioiTinh;
		soLuongSinhVien++;
		this.diemMonHoc = new HashMap<>();
		this.bangDiem = new HashMap<>();
        this.bangXepLoai = new HashMap<>();
	}

	public SinhVien() {
		//rỗng
	}

	public String getMaSinhVien() {
		return maSinhVien;
	}

	public void setMaSinhVien(String maSinhVien) {
		this.maSinhVien = maSinhVien;
	}

	public String getHoDem() {
		return hoDem;
	}

	public void setHoDem(String hoDem) {
		this.hoDem = hoDem;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public Date getNamSinh() {
		return namSinh;
	}

	public void setNamSinh(Date namSinh) {
		this.namSinh = namSinh;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public static int getSoLuongSinhVien() {
		return soLuongSinhVien;
	}

	public static void setSoLuongSinhVien(int soLuongSinhVien) {
		SinhVien.soLuongSinhVien = soLuongSinhVien;
	}
	// Thêm môn học cho sinh viên
	public void themMonHoc(MonHoc monHoc) {
        danhSachMonHoc.add(monHoc);
    }
	// Xóa môn học của sinh viên
	 public void xoaMonHoc(MonHoc monHoc) {
		 danhSachMonHoc.remove(monHoc);
	}

	public ArrayList<MonHoc> getDanhSachMonHoc() {
		return danhSachMonHoc;
	}

	public void setDanhSachMonHoc(ArrayList<MonHoc> danhSachMonHoc) {
		this.danhSachMonHoc = danhSachMonHoc;
	}
	 
	public void xoaTatCaMonHoc() {
	    danhSachMonHoc.clear();
    }
	
	public ArrayList<MonHoc> getDanhSachMonHocDaXoa() {
        return danhSachMonHocDaXoa;
    }
	
	public void setDanhSachMonHocDaXoa(ArrayList<MonHoc> danhSachMonHocDaXoa) {
        this.danhSachMonHocDaXoa = danhSachMonHocDaXoa;
    }
	
	 public void themMonHocDaXoa(MonHoc monHoc) {
	        danhSachMonHocDaXoa.add(monHoc);
	    }
	
	public void khoiPhucTatCaMonHoc() {
        danhSachMonHoc.addAll(danhSachMonHocDaXoa);
        danhSachMonHocDaXoa.clear();
    }
	
	public void khoiPhucMonHoc(MonHoc monHoc) {
        if (danhSachMonHocDaXoa.contains(monHoc)) {
            danhSachMonHoc.add(monHoc);
            danhSachMonHocDaXoa.remove(monHoc);
        } else {
            System.out.println("Môn học không tồn tại trong danh sách đã xóa.");
        }
    }
	
	public MonHoc timMonHocTheoMa(String maMonHoc) {
	    for (MonHoc monHoc : danhSachMonHoc) {
	        if (monHoc.getMaMonHoc().equalsIgnoreCase(maMonHoc)) {
	            return monHoc;
	        }
	    }
	    return null;
	}

	public void themDiem(MonHoc monHoc, double diem) {
	    if (bangDiem == null) {
	        bangDiem = new HashMap<>();
	    }
	    bangDiem.put(monHoc, diem);
	}

	public void themXepLoai(MonHoc monHoc, String xepLoai) {
	    if (bangXepLoai == null) {
	        bangXepLoai = new HashMap<>();
	    }
	    bangXepLoai.put(monHoc, xepLoai);
	}

	
	public void setDiem(MonHoc monHoc, double diem) {
	    if (bangDiem == null) {
	        bangDiem = new HashMap<>();
	    }
	    bangDiem.put(monHoc, diem);
	}

	public Double getDiem(MonHoc monHoc) {
	    if (bangDiem == null) {
	        return null;
	    }
	    return bangDiem.get(monHoc);
	}

    public HashMap<MonHoc, Double> getBangDiem() {
        return bangDiem;
    }

    public void setBangDiem(HashMap<MonHoc, Double> bangDiem) {
        this.bangDiem = bangDiem;
    }

    public HashMap<MonHoc, String> getBangXepLoai() {
        return bangXepLoai;
    }

    public void setBangXepLoai(HashMap<MonHoc, String> bangXepLoai) {
        this.bangXepLoai = bangXepLoai;
    }
  
    public void displayDanhSachMonHoc() {
        System.out.println("Danh sách môn học đã đăng ký của sinh viên " + hoDem + " " + ten + ":");
        for (MonHoc monHoc : danhSachMonHoc) {
            System.out.println(monHoc.getMaMonHoc() + " - " + monHoc.getTenMonHoc());
        }
    }

}

