import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class QuanLyMonHoc {
    private ArrayList<MonHoc> danhSachMonHoc = new ArrayList<>();
    private ArrayList<SinhVien> danhSachSinhVien = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);


    public void taoMonHocSan(String maMonHoc, String tenMonHoc, int soTinChi) {
        MonHoc monHoc = new MonHoc(maMonHoc, tenMonHoc, soTinChi);
        danhSachMonHoc.add(monHoc);
    }

    
    public void addMonHoc(MonHoc monHoc) {
        danhSachMonHoc.add(monHoc);
    }
    
    private MonHoc nhapThongTinMonHoc() {
        System.out.print("Mã môn học: ");
        String maMonHoc = sc.nextLine();

        System.out.print("Tên môn học: ");
        String tenMonHoc = sc.nextLine();

        int soTinChi = nhapSoTinChi();

        return new MonHoc(maMonHoc, tenMonHoc, soTinChi);
    }
    
    public boolean kiemTraMonHocTonTai(String maMonHoc) {
	    for (MonHoc monHoc : danhSachMonHoc) {
	        if (monHoc.getMaMonHoc().equalsIgnoreCase(maMonHoc)) {
	            return true;
	        }
	    }
	    return false;
	}
    
    public void themMonHocChoSinhVien(SinhVien sinhVien) {
        int soLuongMonHoc = nhapSoLuongMonHoc();
        for (int i = 0; i < soLuongMonHoc; i++) {
            MonHoc monHoc = nhapThongTinMonHoc();
            if (!this.kiemTraMonHocTonTai(monHoc.getMaMonHoc())) {
                sinhVien.themMonHoc(monHoc);
                System.out.println("Đã thêm môn học " + monHoc.getTenMonHoc() + " cho sinh viên " + sinhVien.getHoDem() + " " + sinhVien.getTen());
            } else {
                System.out.println("Môn học " + monHoc.getTenMonHoc() + " đã tồn tại trong danh sách của sinh viên.");
                i--;
            }
        }

        boolean xacNhan = xacNhanThongTinNhap(sinhVien);
        if (!xacNhan) {
            System.out.println("Thông tin nhập đã bị hủy bỏ.");
            sinhVien.xoaTatCaMonHoc();
        }
    }




    private int nhapSoLuongMonHoc() {
        System.out.print("Nhập số lượng môn học cần thêm: ");
        while (true) {
            try {
                int soLuong = Integer.parseInt(sc.nextLine());
                if (soLuong >= 0) {
                    return soLuong;
                } else {
                    System.out.print("Vui lòng nhập một số nguyên không âm: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Vui lòng nhập một số nguyên không âm: ");
            }
        }
    }

    private int nhapSoTinChi() {
        int soTinChi = 0;
        boolean nhapSoTinChiHopLe = false;
        while (!nhapSoTinChiHopLe) {
            System.out.print("Nhập số tín chỉ: ");
            try {
                soTinChi = Integer.parseInt(sc.nextLine());
                if (soTinChi > 0) {
                    nhapSoTinChiHopLe = true;
                } else {
                    System.out.println("Số tín chỉ phải là một số nguyên dương. Vui lòng nhập lại.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Số tín chỉ phải là một số nguyên dương. Vui lòng nhập lại.");
            }
        }
        return soTinChi;
    }

    public boolean xacNhanThongTinNhap(SinhVien sinhVien) {
        hienThiThongTinMonHoc(sinhVien);
        boolean xacNhan = xacNhanThongTin();
        if (xacNhan) {
            luuThongTinNhap();
        } else {
            huyBoLuuThongTin();
        }
        return xacNhan; 
    }

    private void hienThiThongTinMonHoc(SinhVien sinhVien) {
        if (danhSachMonHoc.isEmpty()) {
            System.out.println("Chưa có môn học nào trong danh sách.");
            return;
        }
        int j=1;
        System.out.println("Thông tin môn học đã nhập:");
        for (MonHoc mh : sinhVien.getDanhSachMonHoc()) {
        	System.out.println("Môn thứ " +(j++) + ": ");
        	System.out.println(mh.getMaMonHoc() + " - " + mh.getTenMonHoc() + " - " +  mh.getSoTinChi());
        }
    }

    private boolean xacNhanThongTin() {
        String luaChon;
        boolean hopLe = false;
        
        do {
            System.out.print("Bạn có muốn xác nhận thông tin nhập không? (Y/N): ");
            luaChon = sc.nextLine().toUpperCase();
            
            if (luaChon.equals("Y") || luaChon.equals("N")) {
                hopLe = true;
            } else {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại (Y/N).");
            }
        } while (!hopLe);
        
        return luaChon.equals("Y");
    }

    
    private void luuThongTinNhap() {
        // Lưu thông tin nhập vào hệ thống
        System.out.println("Thông tin môn học đã được lưu.");
    }

    private void huyBoLuuThongTin() {
        // Hủy bỏ việc lưu thông tin
        System.out.println("Việc lưu thông tin đã bị hủy bỏ.");
    }
    
    private SinhVien timSinhVienTheoMa(String maSinhVien) {
        for (SinhVien sv : danhSachSinhVien) {
            if (sv.getMaSinhVien().equals(maSinhVien)) {
                return sv;
            }
        }
        return null;
    }

    public MonHoc timMonHocTheoMa(String maMonHoc) {
        for (MonHoc mh : danhSachMonHoc) {
            if (mh.getMaMonHoc().equals(maMonHoc)) {
                return mh;
            }
        }
        return null;
    }
    
    public void hienThiDanhSachMonHoc() {
        if (danhSachMonHoc.isEmpty()) {
            System.out.println("Chưa có môn học nào trong danh sách.");
            return; 
        }
        System.out.println("Danh sách môn học:");
        for (int i = 0; i < danhSachMonHoc.size(); i++) {
            MonHoc monHoc = danhSachMonHoc.get(i);
            System.out.println((i + 1) + ". Mã môn học: " + monHoc.getMaMonHoc() + " - " + monHoc.getTenMonHoc());
        }
    }

    
    //5.
    public void hienThiDanhSachMonHocCoSan() {
        if (danhSachMonHoc.isEmpty()) {
            System.out.println("Chưa có môn học nào trong danh sách.");
            return;
        } else {
            System.out.println("Danh sách môn học có sẵn:");
            for (int i = 0; i < danhSachMonHoc.size(); i++) {
                MonHoc monHoc = danhSachMonHoc.get(i);
                System.out.println((i + 1) + ". Mã môn học: " + monHoc.getMaMonHoc() + 
                        " - Tên môn học: " + monHoc.getTenMonHoc() + 
                        " - Số tín chỉ: " + monHoc.getSoTinChi());
            }
           }
    }
    
    //7.
    public void themSinhVienVaoMonHoc(SinhVien sinhVien) {
        hienThiDanhSachMonHocCoSan();

        System.out.print("Nhập mã môn học cần thêm cho sinh viên: ");
        int index = -1;
        while (index < 0 || index >= danhSachMonHoc.size()) {
            try {
                index = Integer.parseInt(sc.nextLine()) - 1;
                if (index < 0 || index >= danhSachMonHoc.size()) {
                    System.out.println("Vui lòng nhập một số trong phạm vi từ 1 đến " + danhSachMonHoc.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số nguyên.");
            }
        }

        MonHoc monHoc = danhSachMonHoc.get(index);
        if (monHoc == null) {
            System.out.println("Không tìm thấy môn học với mã số " + (index + 1));
            return;
        }

        sinhVien.themMonHoc(monHoc);
        System.out.println("Đã thêm môn học " + monHoc.getTenMonHoc() + " cho sinh viên " + sinhVien.getHoDem() + " " + sinhVien.getTen());
    }

    //8.
    public void suaThongTinMonHocChoSinhVien(SinhVien sinhVien) {
        ArrayList<MonHoc> danhSachMonHoc = sinhVien.getDanhSachMonHoc();

        if (danhSachMonHoc.isEmpty()) {
            System.out.println("Sinh viên không có môn học nào để sửa.");
            return;
        }

        System.out.println("Danh sách môn học của sinh viên " + sinhVien.getHoDem() + " " + sinhVien.getTen() + ":");
        for (int i = 0; i < danhSachMonHoc.size(); i++) {
            MonHoc monHoc = danhSachMonHoc.get(i);
            System.out.println((i + 1) + ". " + monHoc.getMaMonHoc() + " - " + monHoc.getTenMonHoc() + " - " + monHoc.getSoTinChi());
        }

        System.out.print("Nhập chỉ số của môn học cần sửa: ");
        int index = -1;
        while (index < 0 || index >= danhSachMonHoc.size()) {
            try {
                index = Integer.parseInt(sc.nextLine()) - 1;
                if (index < 0 || index >= danhSachMonHoc.size()) {
                    System.out.println("Vui lòng nhập một số trong phạm vi từ 1 đến " + danhSachMonHoc.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số nguyên.");
            }
        }

        MonHoc monHocCanSua = danhSachMonHoc.get(index);

        System.out.println("Thông tin cũ của môn học:");
        System.out.println("Mã môn học: " + monHocCanSua.getMaMonHoc());
        System.out.println("Tên môn học: " + monHocCanSua.getTenMonHoc());
        System.out.println("Số tín chỉ: " + monHocCanSua.getSoTinChi());

        System.out.println("Nhập thông tin mới cho môn học:");

        System.out.print("Mã môn học mới: ");
        String maMonHocMoi = sc.nextLine();
        System.out.print("Tên môn học mới: ");
        String tenMonHocMoi = sc.nextLine();
        int soTinChiMoi = nhapSoTinChi();

        monHocCanSua.setMaMonHoc(maMonHocMoi);
        monHocCanSua.setTenMonHoc(tenMonHocMoi);
        monHocCanSua.setSoTinChi(soTinChiMoi);

        System.out.println("Thông tin môn học đã được cập nhật.");
    }
    
    //9.
    public void xoaTatCaMonHocChoSinhVien(SinhVien sinhVien) {
    	ArrayList<MonHoc> danhSachMonHocDaXoa = new ArrayList<>(sinhVien.getDanhSachMonHoc());
    	if (sinhVien.getDanhSachMonHoc().isEmpty()) {
            System.out.println("Sinh viên không có môn học nào để xóa.");
            return;
        }
    	
        sinhVien.xoaTatCaMonHoc();
        sinhVien.setDanhSachMonHocDaXoa(danhSachMonHocDaXoa);
        System.out.println("Đã xóa tất cả môn học của sinh viên " + sinhVien.getHoDem() + " " + sinhVien.getTen());
    }

    // Phương thức xóa một môn học của sinh viên
    public void xoaMotMonHocChoSinhVien(SinhVien sinhVien) {
        ArrayList<MonHoc> danhSachMonHoc = sinhVien.getDanhSachMonHoc();
        ArrayList<MonHoc> danhSachMonHocDaXoa = new ArrayList<>(sinhVien.getDanhSachMonHoc());

        if (sinhVien.getDanhSachMonHoc().isEmpty()) {
            System.out.println("Sinh viên không có môn học nào để xóa.");
            return;
        }

        System.out.println("Danh sách môn học của sinh viên " + sinhVien.getHoDem() + " " + sinhVien.getTen() + ":");
        for (int i = 0; i < danhSachMonHoc.size(); i++) {
            MonHoc monHoc = danhSachMonHoc.get(i);
            System.out.println((i + 1) + ". " + monHoc.getMaMonHoc() + " - " + monHoc.getTenMonHoc() + " - " + monHoc.getSoTinChi());
        }

        System.out.print("Nhập chỉ số của môn học cần xóa: ");
        int index = -1;
        while (index < 0 || index >= danhSachMonHoc.size()) {
            try {
                index = Integer.parseInt(sc.nextLine()) - 1;
                if (index < 0 || index >= danhSachMonHoc.size()) {
                    System.out.println("Vui lòng nhập một số trong phạm vi từ 1 đến " + danhSachMonHoc.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số nguyên.");
            }
        }

        MonHoc monHocCanXoa = danhSachMonHoc.get(index);
        sinhVien.xoaMonHoc(monHocCanXoa);
        sinhVien.setDanhSachMonHocDaXoa(danhSachMonHocDaXoa);
        System.out.println("Đã xóa môn học " + monHocCanXoa.getTenMonHoc() + " của sinh viên " + sinhVien.getHoDem() + " " + sinhVien.getTen());
    }

    // Phương thức khôi phục môn học vừa xóa
    public void khoiPhucMonHocDaXoa(SinhVien sinhVien) {
        ArrayList<MonHoc> danhSachMonHocDaXoa = sinhVien.getDanhSachMonHocDaXoa();

        if (danhSachMonHocDaXoa.isEmpty()) {
            System.out.println("Không có môn học nào đã bị xóa.");
            return;
        }

        System.out.println("Danh sách môn học đã xóa của sinh viên " + sinhVien.getHoDem() + " " + sinhVien.getTen() + ":");
        for (int i = 0; i < danhSachMonHocDaXoa.size(); i++) {
            MonHoc monHoc = danhSachMonHocDaXoa.get(i);
            System.out.println((i + 1) + ". " + monHoc.getMaMonHoc() + " - " + monHoc.getTenMonHoc() + " - " + monHoc.getSoTinChi());
        }

        System.out.println("Chọn phương thức khôi phục: ");
        System.out.println("1. Khôi phục tất cả môn học đã xóa");
        System.out.println("2. Khôi phục một môn học đã xóa theo chỉ số");

        int luaChon = -1;
        while (luaChon < 1 || luaChon > 2) {
            try {
                System.out.print("Nhập lựa chọn của bạn: ");
                luaChon = Integer.parseInt(sc.nextLine());
                if (luaChon < 1 || luaChon > 2) {
                    System.out.println("Vui lòng chọn 1 hoặc 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số nguyên.");
            }
        }

        if (luaChon == 1) {
            sinhVien.khoiPhucTatCaMonHoc();
            System.out.println("Đã khôi phục tất cả môn học đã xóa cho sinh viên " + sinhVien.getHoDem() + " " + sinhVien.getTen());
        } else {
            int index = -1;
            while (index < 0 || index >= danhSachMonHocDaXoa.size()) {
                try {
                    System.out.print("Nhập chỉ số của môn học cần khôi phục: ");
                    index = Integer.parseInt(sc.nextLine()) - 1;
                    if (index < 0 || index >= danhSachMonHocDaXoa.size()) {
                        System.out.println("Vui lòng nhập một số trong phạm vi từ 1 đến " + danhSachMonHocDaXoa.size());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Vui lòng nhập một số nguyên.");
                }
            }

            MonHoc monHocCanKhoiPhuc = danhSachMonHocDaXoa.get(index);
            sinhVien.khoiPhucMonHoc(monHocCanKhoiPhuc);
            System.out.println("Đã khôi phục môn học " + monHocCanKhoiPhuc.getTenMonHoc() + " cho sinh viên " + sinhVien.getHoDem() + " " + sinhVien.getTen());
        }
    }
    
    //11.
    public void inDanhSachMonHoc(ArrayList<MonHoc> danhSachMonHoc) {
        if (danhSachMonHoc.isEmpty()) {
            System.out.println("Danh sách môn học trống.");
        } else {
            System.out.println("===== DANH SÁCH MÔN HỌC =====");
            for (MonHoc monHoc : danhSachMonHoc) {
                System.out.println("Mã môn học: " + monHoc.getMaMonHoc());
                System.out.println("Tên môn học: " + monHoc.getTenMonHoc());
                System.out.println("Số tín chỉ: " + monHoc.getSoTinChi());
                System.out.println("-----------------------");
            }
        }
    }

    public void sapXepMonHocTheoTenAZ(ArrayList<MonHoc> danhSachMonHoc) {
        Collections.sort(danhSachMonHoc, Comparator.comparing(MonHoc::getTenMonHoc));
        inDanhSachMonHoc(danhSachMonHoc);
    }

    public void sapXepMonHocTheoTenZA(ArrayList<MonHoc> danhSachMonHoc) {
        Collections.sort(danhSachMonHoc, Comparator.comparing(MonHoc::getTenMonHoc).reversed());
        inDanhSachMonHoc(danhSachMonHoc);
    }

    public void sapXepMonHocTheoSoTinChiTangDan(ArrayList<MonHoc> danhSachMonHoc) {
        Collections.sort(danhSachMonHoc, Comparator.comparingInt(MonHoc::getSoTinChi));
        inDanhSachMonHoc(danhSachMonHoc);
    }

    public void sapXepMonHocTheoSoTinChiGiamDan(ArrayList<MonHoc> danhSachMonHoc) {
        Collections.sort(danhSachMonHoc, Comparator.comparingInt(MonHoc::getSoTinChi).reversed());
        inDanhSachMonHoc(danhSachMonHoc);
    }
    
    
    public void menuSapXep() {
    	Scanner sc = new Scanner(System.in);
    	
    	int choice = -1;
        do {
        System.out.println("===== MENU SẮP XẾP MÔN HỌC =====");
        System.out.println("1. Sắp xếp theo tên môn A-Z");
        System.out.println("2. Sắp xếp theo tên môn Z-A");
        System.out.println("3. Sắp xếp theo số tín chỉ tăng dần");
        System.out.println("4. Sắp xếp theo số tín chỉ giảm dần");
        System.out.println("0. Quay lại");
        System.out.print("Chọn cách sắp xếp: ");
        
        while (!sc.hasNextInt()) {
            System.out.println("Vui lòng chỉ nhập số nguyên: ");
            sc.next();
        }
        
        choice = sc.nextInt();
        sc.nextLine();
        
        switch (choice) {
            case 1:
            	sapXepMonHocTheoTenAZ(danhSachMonHoc);
                break;
            case 2:
            	sapXepMonHocTheoTenZA(danhSachMonHoc);
                break;
            case 3:
            	sapXepMonHocTheoSoTinChiTangDan(danhSachMonHoc);
                break;
            case 4:
            	sapXepMonHocTheoSoTinChiGiamDan(danhSachMonHoc);
                break;
            case 0:
                // Quay lại menu trước
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ.");
                break;
        	}
        }while (choice != 0 && (choice < 1 || choice > 4));
    }
}
