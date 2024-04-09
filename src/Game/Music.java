package Game;

//노래 재생 및 비트파일 읽어와서 비트 출력
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

class Music { // 음악 스레드
	File music = null;
	Clip clip = null;
	AudioInputStream audio = null;
	boolean play = false;

	public Music(String path, String song) throws IOException {
		this.music = new File(song); // 노래파일 경로
		Reset();
		// String path = "./song/PathofTheWind.wav"; // 노래 확장자는 wav파일만 지원
	}

	public void Reset() {
		try {
			System.out.println("음악 초기화");
			audio = AudioSystem.getAudioInputStream(music);
			clip = AudioSystem.getClip();
			clip.open(audio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Play() {
		if (play == true) {
			play = false;
			// 노래 재생 준비 완료
			System.out.println("음악재생");
			clip.start();
		} else if (play == false) {
			System.out.println("음악정지");
			clip.stop();
			play = true;
		}
	}
}