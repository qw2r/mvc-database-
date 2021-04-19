package t3_CRUD;

import java.util.ArrayList;
import java.util.Scanner;

public class DbConnService { // 단순한 처리만 할 객체
	Scanner sc = new Scanner(System.in);
	
	DbConnDao dao = new DbConnDao();
	DbConnVo vo = new DbConnVo();
	
	// 자료 입력을 위한 메소드
	public void dbInput() {
		System.out.println("=======================");
		System.out.print("아이디 : "); vo.setMid(sc.next());
		System.out.print("비밀번호 : "); vo.setPwd(sc.next());
		System.out.print("성명 : "); vo.setName(sc.next());
		System.out.print("나이 : "); vo.setAge(sc.nextInt());
		dao.dbinput(vo);
	}
	
	// 개별 자료 검색
	public void dbSearch() {
		String mid = "";
		System.out.print("검색할 아이디를 입력하세요.");
		mid = sc.next();
		dao.getSearch(mid);
	}

	// 전체 자료 검색
	public void dbList() {
		dao.dbList();
	}

	// 자료 삭제
	public void dbDelete() {
		String mid = "";
		System.out.print("삭제할 아이디를 입력하세요.");
		mid = sc.next();
		
		//먼저 삭제할 자료를 검색한다.
		String res = dao.getDeleteSearch(mid);
		if(res.equals("")) {
			System.out.println("검색한 아이디가 존재하지 않습니다.");
		}
		else {
			System.out.println(res + "자료를 삭제하시겠습니까?(Y/N)");
			res = sc.next();
			if(res.toUpperCase().equals("Y")) {
				dao.dbDelete(mid); // 삭제처리
			}
			else {
				System.out.println("삭제가 취소되었습니다.");
			}
		}
	}
	// 자료 수정처리를 위한 준비
	public void dbUpdate() {
		String mid = "";
		System.out.println("수정할 아이디를 입력하세요.");
		mid = sc.next();
		dao.dbUpdate(mid);
	}

	// 자료 수정처리를 위한 개별자료 출력 후 개별필드 수정한다.
	public DbConnVo updateList(DbConnVo vo) {
		boolean flag = true;
		System.out.println("검색한 자료는?");
		System.out.println("아이디 : "+vo.getMid()+",비밀번호 : "+vo.getPwd()+", 성명 :"+vo.getName()+" , 나이 : "+vo.getAge()+"");
		
		while(flag){
			System.out.print("수정할 항목을 선택하세요? 1.비밀번호 , 2:성명 , 3:나이 기타:종료==> ");
			int no = sc.nextInt();
			switch (no) {
			case 1: {
				System.out.print("비밀번호를 입력하세요? >");
				vo.setPwd(sc.next());
				break;
			}
			case 2: {
				System.out.print("성명를 입력하세요? >");
				vo.setName(sc.next());
				break;
			}
			case 3: {
				System.out.print("나이를 입력하세요? >");
				vo.setAge(sc.nextInt());
				break;
			}
			default:
				flag = false;
			}
		}
		
		
		return vo;
	}

	public void dbList2() {
		ArrayList<DbConnVo> vos = dao.dbList2();
		System.out.println("===============================================================");
		System.out.println("번호\t아이디\t비밀번호\t성명\t나이");
		System.out.println("---------------------------------------------------------------");
		for(DbConnVo vo : vos) {
			System.out.println(vo.getIdx()+"\t"+vo.getMid()+"\t"+vo.getPwd()+"\t"+vo.getName()+"\t"+vo.getAge()+"\t");
		}
		System.out.println("===============================================================");
	}
}
