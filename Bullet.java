
import javax.swing.ImageIcon;

//public class Bullet extends GameObj {
public class Bullet extends GameObj {

//
		public static final int SIZE = 6;
		public static final int INIT_POS_X = GameCourt.COURT_WIDTH/3;
		public static final int INIT_POS_Y = GameCourt.COURT_HEIGHT/5 * 4;
		public static final int INIT_VEL_X = 0;
		public static final int INIT_VEL_Y = -5;
	
		public static int width;
		public static int height;
	
		public Bullet(int courtWidth, int courtHeight, int px, int py) {
			super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE,
					courtWidth, courtHeight);
		}
}
    
	     
	   