package Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

class gifPanel extends JPanel {
	BufferedImage drop[] = null;
	BufferedImage img = null;
	boolean start = true;

	public gifPanel() {
		try {
			drop = new BufferedImage[6];
			for (int i = 0; i < 6; i++) {
				String path = "./images/drop" + (i + 1) + ".png";
				drop[i] = ImageIO.read(new File(path));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fill(int i) {
		this.img = drop[i];
	}

	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, this);
	}
}