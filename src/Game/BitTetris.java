package Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import Game.TetrisEx.TetrisPanel;

@SuppressWarnings("serial") // JFrame이 Serializable 상속해서 그렇다함, 중복 실행시 오류방지인듯
class BitTetris extends JFrame { // 게임 레이블, 전체 게임 영향
	public static boolean drop = false; // ->Beat에서 1이면 true, 0이면 false
	public static int Gamestate = 0; // 0:main / 1:game / 2:gameover
	public static Thread[] Th = new Thread[2];
	public static Music music = null;
	// Game 인스턴스 생성 && 초기화 :
	// 이때 game 변수는 단 하나의 게임만 진행가능하며 game 변수자체가 프로젝트 전체에서 사용되어야하기 때문에
	// static 으로 만들어줌
	Mainscr ms = null;
	public static Container mainc = null;
	gifPanel gifPanel = new gifPanel();

	String path = "./song/PathofTheWind.txt"; // 노래 정보를 담은 text1파일 경로
	// PrintReader.readtext1(path);// 노래 info출력
	String song = "./song/PathofTheWind.wav"; // 노래 경로

	public static void pause() { // 일시정지
		for (int i = 0; i < BitTetris.Th.length; i++)
			BitTetris.Th[i].interrupt();
	}

	public BitTetris() { // 테트리스 JFrame
		mainc = getContentPane();
		setTitle("BitTetris"); // 메인 창 띄우기
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false); // 한번 창이 생성되면 임의적으로 창 크기 변경 불가
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 게임 창을 종료 시 프로그램 전체 종료
		setLayout(null);
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		layeredPane.setLayout(null);

		addKeyListener(new keylistener() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (Gamestate == 0) { // 메인에서 아무 키 입력
					gifPanel.start = false;
					System.out.println("start");
				}
			}
		});

		try {
			myPanel panel = new myPanel(ImageIO.read(new File("./images/background.png")), Main.SCREEN_WIDTH,
					Main.SCREEN_HEIGHT);

			myPanel panel2 = new myPanel(ImageIO.read(new File("./images/title.png")), 300, 170);

			myPanel panel3 = new myPanel(ImageIO.read(new File("./images/press.png")), 350, 40);

			panel.setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
			panel2.setSize(300, 170);
			panel2.setLocation(Main.SCREEN_WIDTH / 6, Main.SCREEN_HEIGHT / 8);
			panel3.setSize(350, 40);
			panel3.setLocation(Main.SCREEN_WIDTH / 5, Main.SCREEN_HEIGHT - 200);

			layeredPane.add(panel, new Integer(0));
			layeredPane.add(panel2, new Integer(100));
			layeredPane.add(panel3, new Integer(200));

			ms = new Mainscr(panel2, panel3);
			ms.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		add(layeredPane);

		gifPanel.setVisible(false);
		setVisible(true); // 창 보이는 여부 => 반드시 true

		gifPanel.setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		add(gifPanel, new Integer(500));
		requestFocus();
	}

	public void invisible() {
		this.setVisible(false);
	}

	class myPanel extends JPanel {
		BufferedImage img = null;
		int i = 0;
		int j = 0;

		public myPanel(BufferedImage img, int i, int j) {
			this.i = i;
			this.j = j;
			this.img = img;
		}

		public void change(BufferedImage par) {
			this.img = par;
		}

		public void paint(Graphics g) {
			g.drawImage(img.getScaledInstance(i, j, Image.SCALE_SMOOTH), 0, 0, null);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, i, j, this);
		}
	}

	class Mainscr extends Thread {
		myPanel panel1 = null;
		myPanel panel2 = null;
		BufferedImage img1 = null;
		BufferedImage img2 = null;

		public Mainscr(myPanel p1, myPanel p2) {
			this.panel1 = p1;
			this.panel2 = p2;
		}

		@Override
		public void run() {
			int n = 0;
			try {
				img1 = ImageIO.read(new File("./images/press.png"));
				img2 = ImageIO.read(new File("./images/press_2.png"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (true) {
				try {
					n++;
					int rand = (int) (Math.random() * 5);
					int rand2 = (int) (Math.random() * 3);
					panel1.setLocation(Main.SCREEN_WIDTH / 6 + rand, Main.SCREEN_HEIGHT / 8 + rand2);
					sleep(250);

					if (gifPanel.start == false) {
						gifPanel.setVisible(true);
						for (int j = 0; j < 6; j++) {
							gifPanel.repaint();
							gifPanel.fill(j);
							gifPanel.setVisible(true);
							sleep(110);
						}
						sleep(120);

						new TetrisEx(); // 테트리스 창 띄우기

						Th[0] = TetrisEx.th; // 스레드1=테트리스 스레드

						Th[1] = new Beat(); // 스레드2=비트 스레드
						Th[1].start();

						music = new Music(path, song); // 음악 제어(오디오 스트림)
						music.play = true;

						gifPanel.start = true;
						dispose(); // 클래스 종료
						break;
					}
					if (n % 6 > 2) {
						panel2.setLocation(Main.SCREEN_WIDTH / 5 - 1, Main.SCREEN_HEIGHT - 200 - 1);
						panel2.change(img1);
					}
					if (n % 6 <= 2) {
						panel2.setLocation(Main.SCREEN_WIDTH / 5, Main.SCREEN_HEIGHT - 200);
						panel2.change(img2);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}