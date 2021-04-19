package t3_CRUD;

import java.util.Scanner;

public class Menu {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		DbConnDao dbconnDao = new DbConnDao();
		DbConnService service = new DbConnService();
		int choice = 0;
		boolean flag = true;
		while(flag) {
			System.out.print("선택>1.입력 2.개별조회 3.전체조회 4.전체죄송 5.수정 6.삭제 0.종료");
			choice = sc.nextInt();
			switch (choice) {
			case 1: {
				service.dbInput();	// 자료 등록메소드 호출
				break;
			}
			case 2: {
				service.dbSearch();	// 자료 개별 조회 메소드 호출
				break;
			}
			case 3: {
				service.dbList();	// 자료 전체 조회 메소드 호출
				break;
			}
			case 4: {
				service.dbList2();	// 자료 전체 조회 메소드 호출(ArrayList객체 사용)
				break;
			}
			case 5: {
				service.dbUpdate();	// 자료 수정메소드 호출
				break;
			}
			case 6: {
				service.dbDelete();	// 자료 삭제메소드 호출
				break;
			}
			default:
				flag = false;
			}
		}
		System.out.println("==============================");
		System.out.println("작업끝...");
		
		
		dbconnDao.dbClose();	// Database 객체 Close
		sc.close();
	}
}
