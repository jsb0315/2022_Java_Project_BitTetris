package DB;

import java.sql.*;

class scoreboard {
	ResultSet rs = null;
	Statement stmt = null;
	Connection conn = null;

	public Connection initConnection(String db, String u, String p) { // 연결 생성
		if (conn == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");

				this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db + "", u, p);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	public void close() { // 연결 닫기
		try {
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void ExecuteRs(String sql) { // sql 실행, 쿼리용
		try {
			this.stmt = conn.createStatement();
			this.rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ExecuteRs(String sql, boolean S) { // sql 실행, 업데이트용
		try {
			this.stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet print() { // 테이블 출력
		ExecuteRs("set @rownum=0;", true);
		ExecuteRs("select @rownum := @rownum + 1 as ranking, name, score, date from player order by score desc; ");
		return rs;
	}

	public int Count() {
		ExecuteRs("select count(*) as c from player");
		try {
			rs.next();
			return rs.getInt("c");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void Add(String name, String score, String date) { // 이름, 점수, 날짜 추가(모두 문자열)
		String max = score; // 최대점수=전달된점수
		ExecuteRs("select score from player where name='" + name + "';"); // 이름 검색해서
		try {
			rs.next(); // 있으면 점수 문자열 비교
			max = rs.getString("score").compareTo(score) < 0 ? score : rs.getString("score"); // 검색점수랑 최대점수중 큰걸로 업데이트
			ExecuteRs("update player set score=" + max + " where name='" + name + "';", true);
		} catch (SQLException e) {
			ExecuteRs("insert into player(name, score, date) values('" + name + "', '" + max + "', '" + date + "');",
					true);
			// 데이터 없으면 추가
		}
	}

	public void Delete(String user) { // 유저이름으로 삭제
		ExecuteRs("delete from player where name='" + user + "';", true);
	}
}