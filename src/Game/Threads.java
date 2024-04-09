package Game;

class Threads extends Thread { // 정지재생 가능한 스레드 상속
	public boolean stop = false;

	public void Resume() {
		BitTetris.music.play = false;
		BitTetris.music.Play();
		ResumeF(); // 정지 시 메소드(1회)
		stop = true;
		System.out.println("일시정지");
		while (stop) { // 한번 interrupt->정지 상태(무한대기)
			try {
				Thread.sleep(1); // 여기 넣으면 정지상태에 계속실행
			} catch (InterruptedException er) {
				if (stop)
					try {
						for (int i = 3; i > 0; i--) {
							ResumeF(i); // 3초동안 실행 메소드
							Thread.sleep(1000);
						}
						if (BitTetris.music.play == true) // 노래 정지중이면 재생
							BitTetris.music.Play();
						stop = false; // 3초 지나야 stop 풀림
					} catch (InterruptedException e) {
						if (!stop)
							Resume();
					} // 무한대기 상태에서 interrupt->대기 break
			}
		}
		System.out.println("재시작");
	}

	public void Start() {
		for (int i = 3; i > 0; i--) {
			ResumeF(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Resume();
			}
		}
		ResumeF(0);
		System.out.println("스레드 시작");
		if (BitTetris.music.play == true)
			BitTetris.music.Play();
	}

	public void ResumeF() {
	} // 정지 화면

	public void ResumeF(int i) {
	} // 정지 후 3초 실행
}
