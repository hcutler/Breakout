import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PowerUp extends GameObj {

	// life image
	public static final String img_file1 = "lifeCereal.png";

	// increase paddle size
	public static final String img_file2 = "doubleArrow.png";

	// dimensions for life image
	public static final int SIZE_X = 26;
	public static final int SIZE_Y = 50;
	public static int INIT_X = ((int) (Math.random() * GameCourt.COURT_WIDTH));
	public static final int INIT_Y = 0;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 5;

	// life image
	private static BufferedImage img;

	// arrow image
	private static BufferedImage img2;

	// boolean - if true, drop extraLife, else drop extendPaddle
	static boolean isExtraLife;

	public PowerUp(int courtWidth, int courtHeight, boolean isExtraLife) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X,
				INIT_Y, SIZE_X, SIZE_Y, courtWidth, courtHeight);

		this.isExtraLife = isExtraLife;

		if (isExtraLife) {
			try {
				if (img == null) {
					// life
					img = ImageIO.read(new File(img_file1));
				}
			} catch (IOException e) {
				System.out.println("Internal Error:" + e.getMessage());
			}
		}

		else {
			try {
				if (img2 == null) {
					// life
					img2 = ImageIO.read(new File(img_file2));
				}
			} catch (IOException e) {
				System.out.println("Internal Error:" + e.getMessage());
			}
		}

	}

	@Override
	public void draw(Graphics g) {

		if (isExtraLife) {
			// life
			g.drawImage(img, pos_x, pos_y, width, height, null);

		} else {
			// arrow
			g.drawImage(img2, pos_x, pos_y, width, height, null);
		}
	}
}