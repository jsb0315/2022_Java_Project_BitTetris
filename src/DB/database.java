package DB;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
/* DB 테이블 추가
set sql_safe_updates=0;

CREATE TABLE player (
  userid INT NOT NULL auto_increment,
  name VARCHAR(100) NULL,
  score INT NULL,
  date DATETIME NULL,
  PRIMARY KEY (userid),
  UNIQUE INDEX userid_UNIQUE (userid ASC) VISIBLE);
  */

public class database {
	public static void main(String[] args) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = simpleDateFormat.format(new Date());

		scoreboard score = new scoreboard(); // JDBC 객체
		score.initConnection("project", "root", "1234"); // DB 연결 생성

		score.Add("안녕", String.valueOf(456), date); // 추가 Add(배열{이름, 점수, 날짜}) 다 String임

		score.Add("어쩔", String.valueOf(1000), date); // 추가 Add(배열{이름, 점수, 날짜}) 다 String임

		score.Add("안녕", String.valueOf(123), date); // 추가 Add(배열{이름, 점수, 날짜}) 다 String임

		ResultSet rs = null;
		rs = score.print();

		String[][] arr = new String[score.Count()][4];

		int i = 0;

		try {
			while (rs.next()) {
				arr[i][0] = rs.getString("ranking");
				arr[i][1] = rs.getString("name");
				arr[i][2] = rs.getString("score");
				arr[i][3] = rs.getString("date"); // 칸마다 데이터 일회용임
				for (int j = 0; j < 4; j++)
					System.out.print(arr[i][j]);
				i++;
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		score.Delete("어쩔"); // 삭제 Delete(플레이어이름)

		rs = score.print();

		score.close(); // 연결 끊기
	}
}
