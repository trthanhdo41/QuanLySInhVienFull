import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class QuanLySinhVien {
    ArrayList<SinhVien> danhSachSinhVien = new ArrayList<SinhVien>();
    private ArrayList<SinhVien> danhSachSinhVienDaXoa = new ArrayList<>();
    private static final Pattern CHU_CAI_PATTERN = Pattern.compile("^[\\p{L} ]+$");
    QuanLyMonHoc quanLyMonHoc = new QuanLyMonHoc();
    
    //1.
    // Phương thức kiểm tra tên có hợp lệ không
    private boolean isValidName(String name) {
        return CHU_CAI_PATTERN.matcher(name).matches();
    }

    public boolean validateGender(String input) {
        String lowercaseInput = input.toLowerCase();
        return lowercaseInput.equals("nam") || lowercaseInput.equals("nữ");
    }

    public boolean validateInput(String input) {
        // Kiểm tra xem input có rỗng không
        if (input.isEmpty()) {
            return false;
        }

        // Kiểm tra xem input có chứa số không
        if (input.matches(".*\\d.*")) {
            return false;
        }
        
        // Kiểm tra xem input có trùng với mã sinh viên của sinh viên nào khác không
        for (SinhVien sv : danhSachSinhVien) {
            if (sv.getMaSinhVien().equals(input)) {
                return false;
            }
        }

        // Kiểm tra xem input có chứa ký tự đặc biệt không
        if (!input.matches("[\\p{L} \\p{M}']+")) {
            return false;
        }

        return true;
    }

    public void addSinhVien(SinhVien sinhVien) {
        danhSachSinhVien.add(sinhVien);
    }
    
    
    public void addSinhVienFromInput() {
    	Scanner sc = new Scanner(System.in);

        while (true) {
            int soLuongSinhVien = 0;
            
            do {
                System.out.print("Nhập số lượng sinh viên cần thêm: ");
                String input = sc.nextLine().trim();

                try {
                    soLuongSinhVien = Integer.parseInt(input);
                    if (soLuongSinhVien <= 0) {
                        System.out.println("Vui lòng nhập một số nguyên dương lớn hơn không.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Vui lòng nhập một số nguyên dương.");
                }
            } while (soLuongSinhVien <= 0);
            
            
            System.out.print("Bạn có muốn thêm " + soLuongSinhVien + " sinh viên? (Y/N): ");
            String confirmAdd = sc.nextLine().trim().toLowerCase();

            if (!confirmAdd.equals("y")) {
                System.out.println("Bạn đã chọn No. Thêm sinh viên bị hủy.");
                return;
            }

            for (int i = 0; i < soLuongSinhVien; i++) {
                System.out.println("Nhập thông tin cho sinh viên thứ " + (i+1) + ": ");
                System.out.print("Mã sinh viên: ");
                String maSV = sc.nextLine();
                
                boolean daTonTai = false;
                for(SinhVien sv: danhSachSinhVien) {
                    if(sv.getMaSinhVien().equals(maSV)) {
                        daTonTai = true;
                        break;
                    }
                }
                if(daTonTai) {
                    System.out.println("Mã sinh viên đã tồn tại trong danh sách!");
                    return;
                }
                
                String hoDem = null;
                String ten = null; 
                String gioiTinh = null;
                do {
                    System.out.print("Họ đệm: ");
                    hoDem = sc.nextLine();
                    if (!isValidName(hoDem)) {
                        System.out.println("Họ đệm không hợp lệ. Vui lòng nhập lại!");
                    }
                } while (!isValidName(hoDem));

              
                do {
                    System.out.print("Tên: ");
                    ten = sc.nextLine();
                    if (!isValidName(ten)) {
                        System.out.println("Tên không hợp lệ. Vui lòng nhập lại!");
                    }
                } while (!isValidName(ten));
                
               
                do {
                    System.out.print("Giới tính (Nam/Nữ): ");
                    gioiTinh = sc.nextLine().toLowerCase(); 
                    if (!gioiTinh.equals("nam") && !gioiTinh.equals("nữ")) {
                        System.out.println("Giới tính phải là 'Nam' hoặc 'Nữ'.");
                    }
                } while (!gioiTinh.equals("nam") && !gioiTinh.equals("nữ"));
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                System.out.print("Nhập năm sinh (dd/MM/yyyy): ");
                java.util.Date namSinhUtil = null;
                try {
                    namSinhUtil = sdf.parse(sc.nextLine());
                } catch (ParseException e) {
                    System.out.println("Định dạng ngày không hợp lệ!");
                    return;
                }
                java.sql.Date namSinh = new java.sql.Date(namSinhUtil.getTime());
                SinhVien sinhVien = new SinhVien(maSV, hoDem, ten, namSinh, gioiTinh);
                addSinhVien(sinhVien);
            }
            soLuongSinhVien++;
            System.out.println("Thêm sinh viên thành công!");
            return;
        }
    }


      
    
    //2.
    public void editSinhVien() {
        if (danhSachSinhVien.isEmpty()) {
            System.out.println("Chưa có sinh viên nào trong danh sách.");
            return;
        }     
        
        Scanner sc = new Scanner(System.in);
        String confirm;
        
        do {
            System.out.print("Nhập mã sinh viên cần sửa thông tin (hoặc nhập 'cancel' để hủy): ");
            String maSV = sc.nextLine();
            
            if (maSV.equalsIgnoreCase("cancel")) {
                System.out.println("Đã hủy bỏ chức năng sửa thông tin sinh viên.");
                return;
            }
            
            SinhVien sinhVienCanSua = null;
            
            for(SinhVien sv : danhSachSinhVien) {
                if(sv.getMaSinhVien().equals(maSV)) {
                    sinhVienCanSua = sv;
                    break;
                }
            }
            
            if(sinhVienCanSua == null) {
                System.out.println("Không tìm thấy sinh viên có mã " + maSV);
                continue;
            } else {
                System.out.println("Thông tin của sinh viên cần sửa: ");
                System.out.println("Mã sinh viên: " + sinhVienCanSua.getMaSinhVien());
                System.out.println("Họ đệm: " + sinhVienCanSua.getHoDem());
                System.out.println("Tên: " + sinhVienCanSua.getTen());
                System.out.println("Giới tính: " + sinhVienCanSua.getGioiTinh());
                System.out.println("Năm sinh: " + sinhVienCanSua.getNamSinh());
                
                System.out.print("Bạn có muốn sửa thông tin sinh viên này không? (Y/N): ");
                confirm = sc.nextLine().trim().toLowerCase();
                
                if (!confirm.equals("y")) {
                    continue;
                }
                
                int choice = -1;
                do {
                    System.out.println("Chọn thông tin cần sửa:");
                    System.out.println("1. Mã sinh viên");
                    System.out.println("2. Họ đệm");
                    System.out.println("3. Tên");
                    System.out.println("4. Giới tính");
                    System.out.println("5. Năm sinh");
                    System.out.println("0. Thoát");
                    System.out.print("Chọn chức năng: ");
                    
                    while (!sc.hasNextInt()) {
                        System.out.println("Vui lòng chỉ nhập số nguyên!");
                        sc.next();
                    }
                    
                    choice = sc.nextInt();
                    sc.nextLine();
                    
                    if (choice < 0 || choice > 5) {
                        System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
                    } else {
                        switch (choice) {
                            case 1:
                                System.out.print("Mã sinh viên mới: ");
                                String maMoi = sc.nextLine();
                                
                                if (validateInput(maMoi)) {
                                    boolean daTonTai = false;
                                    
                                    for (SinhVien sv : danhSachSinhVien) {
                                        if (sv.getMaSinhVien().equals(maMoi)) {
                                            daTonTai = true;
                                            break;
                                        }
                                    }
                                    
                                    if (!daTonTai) {
                                        sinhVienCanSua.setMaSinhVien(maMoi);
                                        System.out.println("Sửa mã sinh viên thành công!");
                                    } else {
                                        System.out.println("Mã sinh viên đã tồn tại trong danh sách!");
                                    }
                                } else {
                                    System.out.println("Mã sinh viên không hợp lệ. Vui lòng nhập lại!");
                                }
                                break;
                            case 2:
                                System.out.print("Họ đệm mới: ");
                                String hoDemMoi = sc.nextLine();
                                
                                if (isValidName(hoDemMoi)) {
                                    sinhVienCanSua.setHoDem(hoDemMoi);
                                    System.out.println("Sửa họ đệm thành công!");
                                } else {
                                    System.out.println("Họ đệm không hợp lệ. Vui lòng nhập lại!");
                                }
                                break;
                            case 3:
                                System.out.print("Tên mới: ");
                                String tenMoi = sc.nextLine();
                                
                                if (isValidName(tenMoi)) {
                                    sinhVienCanSua.setTen(tenMoi);
                                    System.out.println("Sửa tên thành công!");
                                } else {
                                    System.out.println("Tên không hợp lệ. Vui lòng nhập lại!");
                                }
                                break;
                            case 4:
                                System.out.print("Giới tính mới: ");
                                String gioiTinhMoi = sc.nextLine();
                                
                                if (validateGender(gioiTinhMoi)) {
                                    sinhVienCanSua.setGioiTinh(gioiTinhMoi);
                                    System.out.println("Sửa giới tính thành công!");
                                } else {
                                	System.out.println("Giới tính không hợp lệ. Vui lòng nhập lại!");
                                }
                                break;
                            case 5:
                                System.out.print("Nhập năm sinh mới (dd/MM/yyyy): ");
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                try {
                                    Date namSinhMoi = new java.sql.Date(sdf.parse(sc.nextLine()).getTime());
                                    sinhVienCanSua.setNamSinh(namSinhMoi);
                                    System.out.println("Sửa năm sinh thành công!");
                                } catch (ParseException e) {
                                    System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập lại!");
                                }
                                break;
                            case 0:
                                System.out.println("Đã thoát khỏi chức năng sửa thông tin sinh viên.");
                                break;
                            default:
                                System.out.println("Lựa chọn không hợp lệ!");
                                return;
                        }
                        System.out.println("Sửa thông tin sinh viên thành công!");
                    }
                } while (choice != 0);
            }
        } while (true);
    }


    //3.
    public void removeSinhVien() {
        if (danhSachSinhVien.isEmpty()) {
            System.out.println("Chưa có sinh viên nào trong danh sách.");
            return;
        }
        Scanner sc = new Scanner(System.in);
        int choice = -1;
        do {
        	System.out.println("Chọn phương thức xóa sinh viên:");
            System.out.println("1. Xóa tất cả sinh viên trong danh sách.");
            System.out.println("2. Xóa sinh viên theo mã sinh viên.");
            System.out.println("3. Xóa sinh viên theo vị trí trong danh sách.");
            System.out.println("4. Khôi phục sinh viên vừa xóa.");
            System.out.println("5. Thùng rác.");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            
            while (!sc.hasNextInt()) {
                System.out.println("Vui lòng chỉ nhập số nguyên!");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine();
            
            if (choice < 0 || choice > 5) {
                System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            } else {
            switch (choice) {
            case 0:
                System.out.println("Đã thoát chức năng xóa sinh viên.");
                break;
            case 1:
                if (danhSachSinhVien.isEmpty()) {
                    System.out.println("Danh sách sinh viên đang rỗng(0).");
                    break;
                }
                
                boolean isValidChoice = false;
                String confirmDeleteAll = "";

                while (!isValidChoice) {
                    System.out.print("Bạn có chắc chắn muốn xóa tất cả sinh viên trong danh sách này? (Y/N): ");
                    confirmDeleteAll = sc.nextLine().trim().toLowerCase();

                    if (confirmDeleteAll.equals("y") || confirmDeleteAll.equals("n")) {
                        isValidChoice = true;
                    } else {
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng chỉ nhập 'Y' hoặc 'N'.");
                    }
                }

                if (confirmDeleteAll.equals("y")) {
                    danhSachSinhVienDaXoa.addAll(danhSachSinhVien);
                    danhSachSinhVien.clear();
                    System.out.println("Đã xóa tất cả sinh viên trong danh sách và chuyển vào thùng rác.");
                } else {
                    System.out.println("Hủy bỏ thao tác xóa tất cả sinh viên.");
                }
                break;

            case 2:
                if (danhSachSinhVien.isEmpty()) {
                    System.out.println("Danh sách sinh viên đang rỗng.");
                    break;
                }
                
                hideSinhVien();
                
                String maSV;
                boolean found = false;
                while (true) {
                    System.out.print("Nhập mã số sinh viên cần xóa (nhập 'cancel' để hủy bỏ thao tác): ");
                    maSV = sc.nextLine().trim().toLowerCase();
                    
                    if (maSV.equals("cancel")) {
                        System.out.println("Hủy bỏ thao tác xóa sinh viên.");
                        break;
                    }
                    
                    boolean validChoice = false;
                    for (SinhVien sv : danhSachSinhVien) {
                        if (sv.getMaSinhVien().equals(maSV)) {
                            System.out.println("Thông tin của sinh viên cần xóa:");
                            System.out.println(sv.toString());
                            while (!validChoice) {
                                System.out.print("Bạn có chắc chắn muốn xóa sinh viên này? (Y/N): ");
                                String confirm = sc.nextLine().trim().toLowerCase();

                                if (confirm.equals("y")) {
                                    danhSachSinhVien.remove(sv);
                                    System.out.println("Đã xóa sinh viên có mã số " + maSV);
                                    found = true;
                                    validChoice = true;
                                } else if (confirm.equals("n")) {
                                    System.out.println("Hủy bỏ thao tác xóa sinh viên.");
                                    validChoice = true;
                                } else {
                                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại (Y/N).");
                                }
                            }
                            break;
                        }
                    }
                    if (maSV.equals("cancel") || found) {
                        break;
                    } else if (!found) {
                        System.out.println("Không tìm thấy sinh viên có mã số " + maSV);
                    }
                }
                break;

            case 3:
                if (danhSachSinhVien.isEmpty()) {
                    System.out.println("Danh sách sinh viên đang rỗng.");
                    break;
                }
                
                hideSinhVien();
                
                int size = danhSachSinhVien.size();
                if (size == 0) {
                    System.out.println("Danh sách sinh viên đang trống.");
                    break;
                }
                
                System.out.print("Nhập vị trí của sinh viên cần xóa (nhập 'cancel' để hủy bỏ thao tác): ");
                String input = sc.nextLine().trim().toLowerCase();
                if (input.equals("cancel")) {
                    System.out.println("Hủy bỏ thao tác xóa sinh viên.");
                    break;
                }
                
                int position;
                try {
                    position = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại (Y/N/Hủy).");
                    break;
                }

                if (position >= 1 && position <= size) {
                    System.out.print("Bạn có chắc chắn muốn xóa sinh viên này? (Y/N/Hủy): ");
                    String confirm = sc.nextLine().trim().toLowerCase();

                    if (confirm.equals("y")) {
                        danhSachSinhVienDaXoa.add(danhSachSinhVien.get(position - 1));
                        danhSachSinhVien.remove(position - 1);
                        System.out.println("Đã xóa sinh viên ở vị trí " + position);
                    } else if (confirm.equals("n")) {
                        System.out.println("Hủy bỏ thao tác xóa sinh viên.");
                    } else if (confirm.equals("hủy")) {
                        System.out.println("Hủy bỏ thao tác xóa sinh viên.");
                    } else {
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại (Y/N/Hủy).");
                        confirm = sc.nextLine().trim().toLowerCase();
                        while (!confirm.equals("y") && !confirm.equals("n") && !confirm.equals("hủy")) {
                            System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại (Y/N/Hủy).");
                            confirm = sc.nextLine().trim().toLowerCase();
                        }
                        if (confirm.equals("y")) {
                            danhSachSinhVienDaXoa.add(danhSachSinhVien.get(position - 1));
                            danhSachSinhVien.remove(position - 1);
                            System.out.println("Đã xóa sinh viên ở vị trí " + position);
                        } else if (confirm.equals("hủy")) {
                            System.out.println("Hủy bỏ thao tác xóa sinh viên.");
                        } else {
                            System.out.println("Hủy bỏ thao tác xóa sinh viên.");
                        }
                    }
                } else {
                    System.out.println("Vị trí không hợp lệ. Vui lòng chọn lại.");
                }
                break;

            case 4:
                if (danhSachSinhVienDaXoa.isEmpty()) {
                    System.out.println("Danh sách sinh viên đã xóa đang rỗng (0).");
                    break;
                }
                
                System.out.print("Bạn có chắc chắn muốn khôi phục sinh viên vừa xóa? (Y/N): ");
                String confirmRestore = sc.nextLine().trim().toLowerCase();

                if (confirmRestore.equals("y")) {
                    restoreSinhVien();
                } else if (confirmRestore.equals("n")) {
                    System.out.println("Hủy bỏ thao tác khôi phục sinh viên.");
                } else {
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại (Y/N).");
                    confirmRestore = sc.nextLine().trim().toLowerCase();
                    while (!confirmRestore.equals("y") && !confirmRestore.equals("n")) {
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại (Y/N).");
                        confirmRestore = sc.nextLine().trim().toLowerCase();
                    }
                    if (confirmRestore.equals("y")) {
                        restoreSinhVien();
                    } else {
                        System.out.println("Hủy bỏ thao tác khôi phục sinh viên.");
                    }
                }  
                break;
            case 5:
            	if(danhSachSinhVienDaXoa.isEmpty()) {
            		System.out.println("Thùng rác đang trống");
            	return;
            	}
            	
            	System.out.println("Danh sách sinh viên trong thùng rác:");
            	int i = 1;
            	for(SinhVien sv : danhSachSinhVienDaXoa) {
            		System.out.println("STT "+ i++ + ":");
                    System.out.println(sv.getMaSinhVien() + " - " + sv.getHoDem() + " - " + sv.getTen() + " - " + sv.getNamSinh());
            	}
            	break;
	            default:
	                System.out.println("Lựa chọn không hợp lệ!");
			        }
            	}
			} while (choice != 0);
	}
	  
      
            
    public void restoreSinhVien() {
        Scanner sc = new Scanner(System.in);
        if (danhSachSinhVienDaXoa.isEmpty()) {
            System.out.println("Không có sinh viên nào đã xóa.");
            return;
        }
        
        System.out.println("Danh sách sinh viên đã xóa:");
        int index = 1;
        for (SinhVien sv : danhSachSinhVienDaXoa) {
            System.out.println(index++ + ". " + sv.getMaSinhVien() + " - " + sv.getHoDem() + " - " + sv.getTen() + " - " + sv.getNamSinh());
        }
        
        boolean validChoice = false;
        do {
            System.out.println("Chọn sinh viên cần khôi phục (nhập số thứ tự), hoặc nhập 'all' để khôi phục tất cả sinh viên:");
            String choiceInput = sc.nextLine().trim();
            
            if (choiceInput.equalsIgnoreCase("all")) {
                // Khôi phục tất cả sinh viên
                danhSachSinhVien.addAll(danhSachSinhVienDaXoa);
                danhSachSinhVienDaXoa.clear();
                System.out.println("Tất cả sinh viên đã được khôi phục thành công.");
                validChoice = true;
            } else {
                String[] choices = choiceInput.split(",");
                ArrayList<Integer> validIndexes = new ArrayList<>();
                for (String choice : choices) {
                    try {
                        int indexToRestore = Integer.parseInt(choice);
                        if (indexToRestore >= 1 && indexToRestore <= danhSachSinhVienDaXoa.size()) {
                            validIndexes.add(indexToRestore);
                        } else {
                            System.out.println("Lựa chọn không hợp lệ vui lòng nhập chính xác số thứ tự hoặc 'all'.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Lựa chọn không hợp lệ vui lòng nhập chính xác số thứ tự hoặc 'all'.");
                        break;
                    }
                }
                if (validIndexes.size() == choices.length) {
                    // Khôi phục các sinh viên được chọn
                    for (int indexToRestore : validIndexes) {
                        SinhVien sinhVienCanKhoiPhuc = danhSachSinhVienDaXoa.get(indexToRestore - 1);
                        danhSachSinhVien.add(sinhVienCanKhoiPhuc);
                        danhSachSinhVienDaXoa.remove(sinhVienCanKhoiPhuc);
                        System.out.println("Sinh viên đã được khôi phục thành công.");
                    }
                    validChoice = true;
                }
            }
        } while (!validChoice);
    }

    //4.
    public void hideSinhVien() {
        if (danhSachSinhVien.isEmpty()) {
            System.out.println("Chưa có sinh viên nào trong danh sách.");
            return;
        }
        
        Scanner sc = new Scanner(System.in);
        int i = 1;
        
        System.out.println("Danh sách sinh viên hiện có:");
        for(SinhVien sv: danhSachSinhVien) {
            System.out.println("STT "+ i + ": " + sv.getMaSinhVien() + " - " + sv.getHoDem() + " - " + sv.getTen());
            i++;
        }
        
        System.out.print("Nhập số thứ tự sinh viên bạn muốn xem chi tiết (nhập 0 để thoát): ");
        
        while (!sc.hasNextInt()) {
            System.out.println("Vui lòng chỉ nhập số nguyên: ");
            sc.next();
        }
        
        int choice = sc.nextInt();
        sc.nextLine();
        
        if (choice < 0 || choice > danhSachSinhVien.size()) {
            System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
            hideSinhVien();
            return;
        }
        
        if (choice == 0) {
            System.out.println("Thoát khỏi chức năng xem chi tiết sinh viên.");
            return;
        }
        
        SinhVien selectedSV = danhSachSinhVien.get(choice - 1);
        
        System.out.println("Thông tin chi tiết của sinh viên:");
        System.out.println("Mã sinh viên: " + selectedSV.getMaSinhVien());
        System.out.println("Họ đệm: " + selectedSV.getHoDem());
        System.out.println("Tên: " + selectedSV.getTen());
        System.out.println("Giới tính: " + selectedSV.getGioiTinh());
        System.out.println("Năm sinh: " + selectedSV.getNamSinh());

    }
    
    public SinhVien timSinhVienTheoMa(String maSoSinhVien) {
        for (SinhVien sinhVien : danhSachSinhVien) {
            if (sinhVien.getMaSinhVien().equals(maSoSinhVien)) {
                return sinhVien;
            }
        }
        return null;
    }
    
    public void inDanhSachSinhVien(ArrayList<SinhVien> danhSachSinhVien) {
        if (danhSachSinhVien.isEmpty()) {
            System.out.println("Danh sách sinh viên trống.");
        } else {
            System.out.println("===== DANH SÁCH SINH VIÊN =====");
            for (SinhVien sinhVien : danhSachSinhVien) {
                System.out.println("Mã sinh viên: " + sinhVien.getMaSinhVien());
                System.out.println("Họ và tên: " + sinhVien.getHoDem() + " " + sinhVien.getTen());
                System.out.println("Ngày sinh: " + sinhVien.getNamSinh());
                System.out.println("Giới tính: " + sinhVien.getGioiTinh());
                System.out.println("-----------------------");
            }
        }
    }
    
    //10.
    public void displaySinhVienWithSubjects() {
        if (danhSachSinhVien.isEmpty()) {
            System.out.println("Danh sách sinh viên trống.");
            return;
        }
        
        int i = 1;
        for (SinhVien sv : danhSachSinhVien) {
            System.out.println("Thông tin sinh viên STT " +(i++) + ": ");
            System.out.println("Mã sinh viên: " + sv.getMaSinhVien());
            System.out.println("Họ và tên: " + sv.getHoDem() + " " + sv.getTen());
            System.out.println("Ngày sinh: " + sv.getNamSinh());
            System.out.println("Giới tính: " + sv.getGioiTinh());
            System.out.println("Danh sách môn học:");

            if (sv.getDanhSachMonHoc().isEmpty()) {
                System.out.println("Sinh viên chưa đăng ký môn học nào.");
            } else {
            	int j = 1;
                for (MonHoc mh : sv.getDanhSachMonHoc()) {
                	System.out.println("Môn thứ " +(j++) + ": ");
                	System.out.println(mh.getMaMonHoc() + " - " + mh.getTenMonHoc() + " - " +  mh.getSoTinChi());
                }
            }
            System.out.println("-----------------------------------------");
        }
    }
    
    //11.
    public void sapXepSinhVienTheoTenAZ(ArrayList<SinhVien> danhSachSinhVien) {
        Collections.sort(danhSachSinhVien, Comparator.comparing(SinhVien::getTen));
        inDanhSachSinhVien(danhSachSinhVien);
    }

    public void sapXepSinhVienTheoTenZA(ArrayList<SinhVien> danhSachSinhVien) {
        Collections.sort(danhSachSinhVien, Comparator.comparing(SinhVien::getTen).reversed());
        inDanhSachSinhVien(danhSachSinhVien);
    }

    public void sapXepSinhVienTheoGioiTinhNamNu(ArrayList<SinhVien> danhSachSinhVien) {
        Collections.sort(danhSachSinhVien, Comparator.comparing(SinhVien::getGioiTinh));
        inDanhSachSinhVien(danhSachSinhVien);
    }

    public void sapXepSinhVienTheoGioiTinhNuNam(ArrayList<SinhVien> danhSachSinhVien) {
        Collections.sort(danhSachSinhVien, Comparator.comparing(SinhVien::getGioiTinh, Comparator.reverseOrder()));
        inDanhSachSinhVien(danhSachSinhVien);
    }

    //11.
    public void menuSapXep() {
    	Scanner sc = new Scanner(System.in);
    	
    	int choice = -1;
        do {
        System.out.println("===== MENU SẮP XẾP SINH VIÊN =====");
        System.out.println("1. Sắp xếp theo tên A-Z");
        System.out.println("2. Sắp xếp theo tên Z-A");
        System.out.println("3. Sắp xếp theo giới tính Nam-Nữ");
        System.out.println("4. Sắp xếp theo giới tính Nữ-Nam");
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
            	sapXepSinhVienTheoTenAZ(danhSachSinhVien);
                break;
            case 2:
                sapXepSinhVienTheoTenZA(danhSachSinhVien);
                break;
            case 3:
                sapXepSinhVienTheoGioiTinhNamNu(danhSachSinhVien);
                break;
            case 4:
                sapXepSinhVienTheoGioiTinhNuNam(danhSachSinhVien);
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
    

    public void hienThiDanhSachMonHocCuaSinhVien(SinhVien sinhVien) {
        System.out.println("Danh sách môn học của sinh viên " + sinhVien.getHoDem() + " " + sinhVien.getTen() + ":");
        List<MonHoc> danhSachMonHoc = sinhVien.getDanhSachMonHoc();
        if (danhSachMonHoc.isEmpty()) {
            System.out.println("Sinh viên chưa đăng ký môn học nào.");
        } else {
            for (MonHoc monHoc : danhSachMonHoc) {
                System.out.println(monHoc.getMaMonHoc() + " - " + monHoc.getTenMonHoc() + " - " + monHoc.getSoTinChi());
            }
        }
    }
    
    //13.
    public void hienThiDanhSachMonHocVaDiem(SinhVien sinhVien, QuanLyDiem quanLyDiem) {
        System.out.println("Danh sách môn học và điểm của sinh viên " + sinhVien.getHoDem() + " " + sinhVien.getTen() + ":");
        ArrayList<MonHoc> danhSachMonHoc = sinhVien.getDanhSachMonHoc();
        for (MonHoc monHoc : danhSachMonHoc) {
            System.out.println("Mã môn học: " + monHoc.getMaMonHoc());
            System.out.println("Tên môn học: " + monHoc.getTenMonHoc());
            System.out.println("Số tín chỉ: " + monHoc.getSoTinChi());
            System.out.println("Điểm: " + sinhVien.getDiem(monHoc)); 
            System.out.println("-----------------------------------------");
        }
    }

    public void suaDiemChoSinhVien(QuanLySinhVien quanLySinhVien, QuanLyMonHoc quanLyMonHoc, QuanLyDiem quanLyDiem) {
    	Scanner sc = new Scanner(System.in);
    	quanLySinhVien.displaySinhVienWithSubjects();

        System.out.print("Nhập mã số sinh viên cần sửa điểm (hoặc nhập 'cancel' để thoát): ");
        String maSinhVien = sc.nextLine().trim();

        if (maSinhVien.equalsIgnoreCase("cancel")) {
            System.out.println("Hủy bỏ sửa điểm.");
            return;
        }

        SinhVien sinhVien = quanLySinhVien.timSinhVienTheoMa(maSinhVien);

        if (sinhVien == null) {
            System.out.println("Không tìm thấy sinh viên với mã số " + maSinhVien);
            return;
        }

        this.hienThiDanhSachMonHocVaDiem(sinhVien,quanLyDiem);

        System.out.print("Nhập mã môn học cần sửa điểm: ");
        String maMonHoc = sc.nextLine().trim();

        MonHoc monHoc = sinhVien.timMonHocTheoMa(maMonHoc);

        if (monHoc == null) {
            System.out.println("Không tìm thấy môn học với mã " + maMonHoc);
            return;
        }

        System.out.print("Nhập điểm mới cho môn học " + monHoc.getTenMonHoc() + ": ");
        double diemMoi = Double.parseDouble(sc.nextLine().trim());

        sinhVien.setDiem(monHoc, diemMoi);

        System.out.println("Đã sửa điểm cho môn học " + monHoc.getTenMonHoc() + " của sinh viên " + sinhVien.getHoDem() + " " + sinhVien.getTen() + " thành " + diemMoi);
    }

    public void xuatDanhSachSinhVien(String tenFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tenFile))) {
            for (SinhVien sinhVien : danhSachSinhVien) {
                writer.write("Mã sinh viên: " + sinhVien.getMaSinhVien() + "\n");
                writer.write("Họ và tên: " + sinhVien.getHoDem() + " " + sinhVien.getTen() + "\n");
                writer.write("Ngày sinh: " + sinhVien.getNamSinh() + "\n");
                writer.write("Giới tính: " + sinhVien.getGioiTinh() + "\n");
                writer.write("\n");
            }
            System.out.println("Danh sách sinh viên đã được xuất ra tập tin " + tenFile);
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi danh sách sinh viên vào tập tin.");
        }
    }

}

	

           
