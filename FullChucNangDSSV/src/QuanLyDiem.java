import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class QuanLyDiem extends SinhVien {
    ArrayList<Diem> danhSachDiem = new ArrayList<Diem>();
    private Scanner sc;
    QuanLySinhVien quanLySinhVien = new QuanLySinhVien();
    public QuanLyDiem() {
        this.sc = new Scanner(System.in);
        this.quanLySinhVien = new QuanLySinhVien();
    }
    
    public void addDiem(Diem diem) {
        danhSachDiem.add(diem);
    }
    
    public void themDiemChoSinhVien(QuanLySinhVien quanLySinhVien) {
        Scanner scanner = new Scanner(System.in);

        quanLySinhVien.displaySinhVienWithSubjects();

        System.out.print("Nhập mã số sinh viên cần thêm điểm (hoặc nhập 'cancel' để thoát): ");
        String maSinhVien = scanner.nextLine().trim();

        if (maSinhVien.equalsIgnoreCase("cancel")) {
            System.out.println("Hủy bỏ thêm điểm.");
            return;
        }

        SinhVien sinhVien = quanLySinhVien.timSinhVienTheoMa(maSinhVien);

        if (sinhVien == null) {
            System.out.println("Không tìm thấy sinh viên với mã số " + maSinhVien);
            return;
        }

        sinhVien.displayDanhSachMonHoc();

        System.out.print("Nhập mã môn học: ");
        String maMonHoc = scanner.nextLine().trim();

        MonHoc monHoc = sinhVien.timMonHocTheoMa(maMonHoc);

        if (monHoc == null) {
            System.out.println("Không tìm thấy môn học với mã " + maMonHoc + " trong danh sách của sinh viên.");
            return;
        }

        System.out.print("Nhập điểm cho môn học " + monHoc.getTenMonHoc() + ": ");
        double diem = scanner.nextDouble();
        scanner.nextLine();

        String xepLoai;
        if (diem >= 9) {
            xepLoai = "A";
        } else if (diem >= 8) {
            xepLoai = "B";
        } else if (diem >= 7) {
            xepLoai = "C";
        } else if (diem >= 6) {
            xepLoai = "D";
        } else if (diem >= 5) {
            xepLoai = "E";
        } else {
            xepLoai = "F";
        }

        sinhVien.themDiem(monHoc, diem);
        sinhVien.themXepLoai(monHoc, xepLoai);

        System.out.println("Đã thêm điểm " + diem + " (" + xepLoai + ") cho sinh viên " + sinhVien.getHoDem() + " " + sinhVien.getTen() + " - " + monHoc.getTenMonHoc());
    }
    
    public void removeDiem(Diem diem) {
        if (danhSachDiem.contains(diem)) {
            danhSachDiem.remove(diem);
            System.out.println("Đã xóa điểm của sinh viên " + diem.getMaSinhVien() + " ở môn học " + diem.getMaMonHoc());
        } else {
            System.out.println("Không tìm thấy điểm để xóa.");
        }
    }
        
    public void displayDiem() {
        for(Diem diem: danhSachDiem) {
            System.out.println(diem.getMaSinhVien() + " - " + diem.getMaMonHoc() + " - " + diem.getDiemSo());
        }
    }
    
    public static void hienThiBangDiem(SinhVien sinhVien) {
        System.out.println("Bảng điểm của sinh viên " + sinhVien.getHoDem() + " " + sinhVien.getTen() + ":");
        System.out.println("Mã môn học | Tên môn học | Số tín chỉ | Điểm");
        System.out.println("---------------------------------------------");
        
        ArrayList<MonHoc> danhSachMonHoc = sinhVien.getDanhSachMonHoc();
        for (MonHoc monHoc : danhSachMonHoc) {
            Double diem = sinhVien.getDiem(monHoc);
            System.out.printf("%-10s | %-11s | %-10d | %-4.1f\n", monHoc.getMaMonHoc(), monHoc.getTenMonHoc(), monHoc.getSoTinChi(), diem);
        }
    }
    
    public void xoaDiemChoSinhVien(QuanLySinhVien quanLySinhVien) {
        Scanner sc = new Scanner(System.in);
        quanLySinhVien.displaySinhVienWithSubjects();

        System.out.print("Nhập mã số sinh viên cần xóa điểm (hoặc nhập 'cancel' để thoát): ");
        String maSV = sc.nextLine();

        if (maSV.equalsIgnoreCase("cancel")) {
            return;
        }

        SinhVien sinhVien = quanLySinhVien.timSinhVienTheoMa(maSV);

        if (sinhVien == null) {
            System.out.println("Không tìm thấy sinh viên với mã số " + maSV);
            return;
        }

        HashMap<MonHoc, Double> bangDiem = sinhVien.getBangDiem();

        if (bangDiem.isEmpty()) {
            System.out.println("Sinh viên không có điểm nào để xóa.");
            return;
        }

        System.out.println("Danh sách môn học đã có điểm của sinh viên " + sinhVien.getHoDem() + " " + sinhVien.getTen() + ":");
        for (MonHoc monHoc : bangDiem.keySet()) {
            System.out.println(monHoc.getMaMonHoc() + " - " + monHoc.getTenMonHoc());
        }

        System.out.print("Nhập mã môn học cần xóa điểm: ");
        String maMonHoc = sc.nextLine();

        MonHoc monHoc = sinhVien.timMonHocTheoMa(maMonHoc);

        if (monHoc == null) {
            System.out.println("Không tìm thấy môn học với mã " + maMonHoc);
            return;
        }

        if (!bangDiem.containsKey(monHoc)) {
            System.out.println("Môn học " + monHoc.getMaMonHoc() + " - " + monHoc.getTenMonHoc() + " không có điểm để xóa.");
            return;
        }

        bangDiem.remove(monHoc);
        System.out.println("Đã xóa điểm của môn học " + monHoc.getMaMonHoc() + " - " + monHoc.getTenMonHoc() + " cho sinh viên " + sinhVien.getHoDem() + " " + sinhVien.getTen());
    }

}
