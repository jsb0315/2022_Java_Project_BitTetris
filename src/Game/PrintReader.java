package Game;

//화면상에 노래정보 띄우기위해 만든 코드
//텍스트 파일의 전체 문자가 아닌 "특정부분"만 출력하게하는 코드
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PrintReader {
    public static String readtext(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path)); // 여기에다가 텍스트파일 상대경로 입력
        String Lines = "";
        boolean find = false;
        String beatmap = "";
        while (true) {
            String line = br.readLine();
            Lines = line;

            if (line == null)
                break; // 더 이상 읽을 라인이 없을 경우 while 문을 빠져나간다.
            if (Lines.equals("[Info]")) // "텍스트 파일의 문장이 [Info]이면 그 다음줄부터 읽어봄"
                find = true; // 원하는 부분만 읽기
            if (find)
                System.out.println(line);
            if (Lines.equals("..."))
                break;
        }
        br.close();
        return beatmap;
    }
}
