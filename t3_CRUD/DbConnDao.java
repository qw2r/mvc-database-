package t3_CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DbConnDao {
	Connection conn = null; //데이터베이스 접근
	Statement stmt = null; // table 제어를 위한 sql문사용시 
	ResultSet rs = null; // table에서 값을 받아서 처리하기위한.
	String sql = "";
	
	public DbConnDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost:3306/works";
			String user = "green";
			String password = "1234";
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 검색 실패");
		} catch (Exception e ) {
			System.out.println("데이터베이스 연동 실패");
		}
	}
	
	public void dbClose() {
		try {
			if(conn != null) conn.close();
		} catch (SQLException e) {
		}
	}

	
	//자료 입력처리 메소드
	public void dbinput(DbConnVo vo) {
		try {
			stmt = conn.createStatement();
			sql = "insert into test1 values (default,'"+vo.getMid()+"','"+vo.getPwd()+"','"+vo.getName()+"',"+vo.getAge()+");" ;
			stmt.executeUpdate(sql);
			System.out.println(vo.getName() +"님의 자료가 등록되었습니다.");
		} catch (SQLException e) {
			System.out.println("SQL 오류 !!! " + e.getMessage());
//			e.printStackTrace();
		}finally {
			if(stmt != null) {
				try {
					stmt.close();
				}catch(SQLException e) {
					
				}
			}
		}
	}
	// 개별자료 검색처리
	public void getSearch(String mid) {
		try {
			stmt = conn.createStatement();
			sql = "select * from test1 where mid = '"+mid+"'; ";
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) { 
				int idx = rs.getInt("idx");
				//String mid = rs.getString("mid");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				//검색자료 출력
				System.out.println("일련번호 : " + idx);
				System.out.println("아이디 : " + mid);
				System.out.println("비밀번호 : " + pwd);
				System.out.println("성명 : " + name);
				System.out.println("나이 : " + age);
				System.out.println("=================");
			}
			else {
				System.out.println("검색한 자료가 없습니다.");
			}
		} catch (SQLException e) {	
			System.out.println("SQL에러" + e.getMessage());
		}finally {
			if(rs != null)
				try {
					rs.close();
					if(stmt != null) stmt.close();
				} catch (SQLException e) {}
		}
	}

	public void dbList() {
		int sw = 0;
		try {
			stmt = conn.createStatement();
			//sql = "select * from test1;";
			sql = "select * from test1 order by name;";
			rs = stmt.executeQuery(sql);
			System.out.println("SQL문 성공적으로 처리됨...");
			System.out.println("-----------------------");
			
			//검색한 자료 조회유무???
			while(true) { // 한건의 자료라도 있으면 수행한다.
				if(rs.next()) { 
					int idx = rs.getInt("idx");
					String mid = rs.getString("mid");
					String pwd = rs.getString("pwd");
					String name = rs.getString("name");
					int age = rs.getInt("age");
					//검색자료 출력
					System.out.println("일련번호 : " + idx);
					System.out.println("아이디 : " + mid);
					System.out.println("비밀번호 : " + pwd);
					System.out.println("성명 : " + name);
					System.out.println("나이 : " + age);
					System.out.println("==================");
					sw = 1;
				}
				else {
					if(sw == 0)	System.out.println("검색한 자료가 없습니다.");
					break;
				}
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류" + e.getMessage());
			e.printStackTrace();
		}finally {
			if(rs != null)
				try {
					rs.close();
					if(stmt != null) stmt.close();
				} catch (SQLException e) {}
		}
		
	}
	// '삭제/수정' 처리시 아이디 검색
	public String getDeleteSearch(String mid) {
		try {
			stmt = conn.createStatement();
			sql = "select * from test1 where mid = '"+mid+"'; ";
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) return rs.getString("name");
		} catch (Exception e) {
			System.out.println("SQL 오류" + e.getMessage());
		}finally {
			if(rs != null)
				try {
					rs.close();
					if(stmt != null) stmt.close();
				} catch (SQLException e) {}
		}
		return "";
	}

	public void dbDelete(String mid) {
		try {
			stmt = conn.createStatement();
			sql = "delete from test1 where mid = '"+mid+"';";
			stmt.executeUpdate(sql);
			System.out.println("삭제 처리 완료");
		} catch (SQLException e) {
			System.out.println("SQL 오류" + e.getMessage());
		}finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {}
			}
		}
	}

	public void dbUpdate(String mid) {
		try {
			stmt = conn.createStatement();
			sql = "select * from test1 where mid = '"+mid+"'; ";
			rs = stmt.executeQuery(sql);
			
			if(!rs.next()) {
				System.out.println("수정할(검색한) 자료가 없습니다.");
				return;
			}
			DbConnVo vo = new DbConnVo();
			vo.setMid(mid);
			vo.setPwd(rs.getString("pwd"));
			vo.setName(rs.getString("name"));
			vo.setAge(rs.getInt("age"));
			if(stmt != null) stmt.close();
			DbConnService service = new DbConnService();
			vo = service.updateList(vo);
			
			// 수정된 vo를 update처리한다.
			stmt = conn.createStatement();
			sql = "update test1 set pwd ='"+vo.getPwd()+"', name ='"+vo.getName()+"', age= "+vo.getAge()+" where mid ='"+mid+"'";
			stmt.executeUpdate(sql);
			System.out.println("자료가 수정처리 되었습니다.");
		} catch (Exception e) {
			System.out.println("SQL 오류" + e.getMessage());
		}finally {
			if(rs != null)
				try {
					rs.close();
					if(stmt != null) stmt.close();
				} catch (SQLException e) {}
		}
	}

	public ArrayList<DbConnVo> dbList2() {
		ArrayList<DbConnVo> vos = new ArrayList<DbConnVo>();
		try {
			stmt = conn.createStatement();
			sql = "select * from test1 order by name;";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				DbConnVo vo = new DbConnVo();
				
				vo.setIdx(rs.getInt("idx"));
				vo.setMid(rs.getString("mid"));
				vo.setPwd(rs.getString("pwd"));
				vo.setName(rs.getString("name"));
				vo.setAge(rs.getInt("age"));
				
				vos.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("SQL 오류" + e.getMessage());
		}
		return vos;
	}
}
