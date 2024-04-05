import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.naming.InitialContext;

public class Main {
    public static void main(String[] args) {
        QuanLySinhVien quanLySinhVien = new QuanLySinhVien();
        QuanLyMonHoc quanLyMonHoc = new QuanLyMonHoc();
        QuanLyDiem quanLyDiem = new QuanLyDiem();
        
        ArrayList<SinhVien> danhSachSinhVien = new ArrayList<SinhVien>();
        
        SinhVien sinhVien1 = new SinhVien("SV001", "Trần Thanh", "Độ", Date.valueOf("2004-09-13"), "Nam");
        SinhVien sinhVien2 = new SinhVien("SV002", "Nguyễn Huỳnh", "Tâm Như", Date.valueOf("2002-06-17"), "Nữ");
        SinhVien sinhVien3 = new SinhVien("SV003", "Bill", "Gate", Date.valueOf("2002-03-03"), "Nam");
        SinhVien sinhVien4 = new SinhVien("SV004", "Mark", "Zuckerberg", Date.valueOf("2001-05-14"), "Nam");
        SinhVien sinhVien5 = new SinhVien("SV005", "Elon", "Musk", Date.valueOf("2000-06-28"), "Nam");
        SinhVien sinhVien6 = new SinhVien("SV006", "Ada", "Lovelace", Date.valueOf("1815-12-10"), "Nữ");
        SinhVien sinhVien7 = new SinhVien("SV007", "Grace", "Hopper", Date.valueOf("1906-12-09"), "Nữ");
        quanLySinhVien.addSinhVien(sinhVien1);
        quanLySinhVien.addSinhVien(sinhVien2);
        quanLySinhVien.addSinhVien(sinhVien3);
        quanLySinhVien.addSinhVien(sinhVien4);
        quanLySinhVien.addSinhVien(sinhVien5);
        quanLySinhVien.addSinhVien(sinhVien6);
        quanLySinhVien.addSinhVien(sinhVien7);

        
        quanLyMonHoc.taoMonHocSan("IT", "Trí tuệ nhân tạo", 3);
        quanLyMonHoc.taoMonHocSan("MKT", "Marketing căn bản", 4);
        quanLyMonHoc.taoMonHocSan("ENG7", "Tiếng Anh 7", 3);
        quanLyMonHoc.taoMonHocSan("MATH", "Toán cao cấp", 4);
        quanLyMonHoc.taoMonHocSan("PHY", "Vật lý đại cương", 3);
        quanLyMonHoc.taoMonHocSan("CHEM", "Hóa học cơ bản", 3);
        quanLyMonHoc.taoMonHocSan("BIO", "Sinh học cơ bản", 3);
        quanLyMonHoc.taoMonHocSan("HIST", "Lịch sử thế giới", 3);
        quanLyMonHoc.taoMonHocSan("GEOM", "Hình học không gian", 4);
        quanLyMonHoc.taoMonHocSan("COMP", "Lập trình căn bản", 4);

        
        Scanner sc = new Scanner(System.in);
        int choice = -1;
        do {
            System.out.println("===== MENU TOOL QUẢN LÝ SINH VIÊN BY Độ Ximi=====");
            System.out.println("1. Thêm sinh viên mới");
            System.out.println("2. Sửa thông tin sinh viên");
            System.out.println("3. Xóa sinh viên");
            System.out.println("4. Hiển thị danh sách sinh viên");
            System.out.println("5. Hiển thị môn học có sẵn");
            System.out.println("6. Thêm môn học mới cho sinh viên");
            System.out.println("7. Thêm sinh viên vào môn học");
            System.out.println("8. Sửa thông tin môn học cho sinh viên");
            System.out.println("9. Xóa môn học cho sinh viên");
            System.out.println("10. Hiển thị danh sách sinh viên kèm theo môn học");
            System.out.println("11. Sắp xếp danh sách sinh viên và môn học");
            System.out.println("12. Thêm điểm cho sinh viên");
            System.out.println("13. Sửa điểm cho sinh viên");
            System.out.println("14. Xóa điểm của sinh viên");
            System.out.println("15. Hiển thị bảng điểm");
            System.out.println("16. Xuất danh sách sinh viên ra file");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            
            while (!sc.hasNextInt()) {
                System.out.println("Vui lòng chỉ nhập số nguyên: ");
                sc.next();
            }
            
            choice = sc.nextInt();
            sc.nextLine();
            
            if (choice < 0 || choice > 16) {
                System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            } else {
                switch (choice) {
                
                    case 0:
                        System.out.println("Kết thúc chương trình.");
                        break;
                        
                    case 1:
                        boolean continueAdding = true;
                        while (continueAdding) {
                            quanLySinhVien.addSinhVienFromInput();
                            
                            System.out.print("Bạn có muốn thêm sinh viên nào khác không? (Y/N): ");
                            String confirm = sc.nextLine().trim().toLowerCase();

                            if (confirm.equals("n")) {
                                continueAdding = false;
                                System.out.println("Bạn đã chọn No. Thoát ra menu chính.");
                            } else if (!confirm.equals("y")) {
                                System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập lại.");
                            }
                        }
                        break;

                    case 2:
                        quanLySinhVien.editSinhVien();
                        break;
                        
                    case 3:
                        quanLySinhVien.removeSinhVien();
                        break;
                        
                    case 4:
                    	
                        quanLySinhVien.hideSinhVien();
                        break;
                        
                    case 5: 
                    	quanLyMonHoc.hienThiDanhSachMonHocCoSan();
                    	break;
                    	
                    case 6:
                    	 System.out.print("Nhập mã số sinh viên cần thêm môn học (hoặc bấm 'cancel' để thoát): ");
                         String maSoSinhVien = sc.nextLine().trim();

                         if (maSoSinhVien.equalsIgnoreCase("cancel")) {
                             System.out.println("Thoát chương trình.");
                             break;
                         }
                        
                        SinhVien sinhVien = quanLySinhVien.timSinhVienTheoMa(maSoSinhVien);
                        
                        if (sinhVien != null) {
                            quanLyMonHoc.themMonHocChoSinhVien(sinhVien);
                        } else {
                            System.out.println("Không tìm thấy sinh viên với mã số " + maSoSinhVien);
                        }
                        break;
                        
                    case 7:
                        System.out.print("Nhập mã số sinh viên cần thêm môn học (hoặc bấm 'cancel' để thoát): ");
                        String maSoSinhVien1 = sc.nextLine().trim();

                        if (maSoSinhVien1.equalsIgnoreCase("cancel")) {
                            System.out.println("Thoát chương trình.");
                            break;
                        }

                        SinhVien sinhVien11 = quanLySinhVien.timSinhVienTheoMa(maSoSinhVien1);

                        if (sinhVien11 != null) {
                            quanLyMonHoc.themSinhVienVaoMonHoc(sinhVien11);
                        } else {
                            System.out.println("Không tìm thấy sinh viên với mã số " + maSoSinhVien1);
                        }
                        break;

                    case 8: 
                    	 System.out.print("Nhập mã số sinh viên cần sửa môn học (hoặc bấm 'cancel' để thoát): ");
                         String maSoSinhVien11 = sc.nextLine().trim();

                         if (maSoSinhVien11.equalsIgnoreCase("cancel")) {
                             System.out.println("Thoát chương trình.");
                             break;
                         }
                         
                         SinhVien sinhVien111 = quanLySinhVien.timSinhVienTheoMa(maSoSinhVien11);

                         if (sinhVien111 != null) {
                             quanLyMonHoc.suaThongTinMonHocChoSinhVien(sinhVien111);
                         } else {
                             System.out.println("Không tìm thấy sinh viên với mã số " + maSoSinhVien11);
                         }
                         break;
                         
                    case 9: 
                        System.out.print("Nhập mã số sinh viên cần xóa môn học (hoặc bấm 'cancel' để thoát): ");
                        String maSoSinhVien111 = sc.nextLine().trim();

                        if (maSoSinhVien111.equalsIgnoreCase("cancel")) {
                            System.out.println("Thoát chương trình.");
                            break;
                        }
                        
                        SinhVien sinhVien1111 = quanLySinhVien.timSinhVienTheoMa(maSoSinhVien111);

                        if (sinhVien1111 != null) {
                            System.out.println("1. Xóa tất cả môn học của sinh viên");
                            System.out.println("2. Xóa một môn học của sinh viên");
                            System.out.println("3. Khôi phục môn học đã xóa của sinh viên");

                            int luaChonXoa = -1;
                            while (luaChonXoa < 1 || luaChonXoa > 3) {
                                try {
                                    System.out.print("Nhập lựa chọn của bạn: ");
                                    luaChonXoa = Integer.parseInt(sc.nextLine());
                                    if (luaChonXoa < 1 || luaChonXoa > 3) {
                                        System.out.println("Vui lòng chọn 1, 2 hoặc 3.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Vui lòng nhập một số nguyên.");
                                }
                            }

                            switch (luaChonXoa) {
                                case 1:
                                    quanLyMonHoc.xoaTatCaMonHocChoSinhVien(sinhVien1111);
                                    break;
                                case 2:
                                    quanLyMonHoc.xoaMotMonHocChoSinhVien(sinhVien1111);
                                    break;
                                case 3:
                                    quanLyMonHoc.khoiPhucMonHocDaXoa(sinhVien1111);
                                    break;
                                default:
                                    System.out.println("Lựa chọn không hợp lệ!");
                            }
                        } else {
                            System.out.println("Không tìm thấy sinh viên với mã số " + maSoSinhVien111);
                        }
                        break;

                    case 10:
                    	quanLySinhVien.displaySinhVienWithSubjects();
                    	break;
                    	
                    case 11:
                        int choice11 = -1;
                        do {
                            System.out.println("===== MENU SẮP XẾP =====");
                            System.out.println("1. Sắp xếp sinh viên");
                            System.out.println("2. Sắp xếp môn học");
                            System.out.println("0. Quay lại");
                            System.out.print("Chọn chức năng: ");
                            
                            while (!sc.hasNextInt()) {
                                System.out.println("Vui lòng chỉ nhập số nguyên: ");
                                sc.next();
                            }
                            
                            choice11 = sc.nextInt();
                            sc.nextLine();
                            
                            switch (choice11) {
                                case 1:  
                                    quanLySinhVien.menuSapXep();
                                    break;
                                case 2:
                                    quanLyMonHoc.menuSapXep();
                                    break;
                                case 0:
                                    break;
                                default:
                                    System.out.println("Lựa chọn không hợp lệ!");
                            }
                        } while (choice11 != 0 && (choice11 < 1 || choice11 > 2));
                        break;
                        
                    case 12: 
                    	quanLyDiem.themDiemChoSinhVien(quanLySinhVien);
                    	break;
                    	
                    case 13: 
                    	quanLySinhVien.suaDiemChoSinhVien(quanLySinhVien,quanLyMonHoc,quanLyDiem);
                    	break;	
                    	
                    case 14:
                    	quanLyDiem.xoaDiemChoSinhVien(quanLySinhVien);
                    	break;
                    	
                    case 15: 
                    	 System.out.print("Nhập mã số sinh viên cần sửa môn học (hoặc bấm 'cancel' để thoát): ");
                         String maSoSinhVien1111 = sc.nextLine().trim();

                         if (maSoSinhVien1111.equalsIgnoreCase("cancel")) {
                             System.out.println("Thoát chương trình.");
                             break;
                         }
                         
                         SinhVien sinhVien11111 = quanLySinhVien.timSinhVienTheoMa(maSoSinhVien1111);

                         if (sinhVien11111 != null) {
                        	 quanLyDiem.hienThiBangDiem(sinhVien11111);
                         } else {
                             System.out.println("Không tìm thấy sinh viên với mã số " + maSoSinhVien1111);
                         }
                         break;
                    
                    case 16:
                    	System.out.print("Nhập tên file xuất danh sách sinh viên: ");
                        String tenFile = sc.nextLine();
                        quanLySinhVien.xuatDanhSachSinhVien(tenFile);
                        break;

                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            }
        } while (choice != 0);
        
    }
}
