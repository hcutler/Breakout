import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;



public class Paddle extends GameObj {
	public static final int SIZE = 20;
	public static final int INIT_X = GameCourt.COURT_WIDTH/2;
	public static final int INIT_Y = GameCourt.COURT_HEIGHT + 10;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;	
	public Color c;
	

	public Paddle(int v_x, int v_y, int pos_x, int pos_y, 
			int width, int height, int court_width, int court_height){
	
	super(v_x, v_y, pos_x, pos_y, width, height, court_width, court_height);	
	
	
//public class Paddle extends Square {

	
	
	// private int paddleWidth;
	// private int paddleHeight;
	c = Color.LIGHT_GRAY;

//	public Paddle(int courtWidth, int courtHeight, int paddleWidth,
//			int paddleHeight) {
//
//		super(courtWidth, courtHeight, paddleWidth, paddleHeight);
//		// TODO Auto-generated constructor stub


	
		// this.paddleWidth = paddleWidth;
		// this.paddleHeight = paddleHeight;
		
		 this.width = width;
		 this.height = height;
		 
		// bullets = new ArrayList();
	}
	

	// draw paddle
	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillRect(pos_x, pos_y, width, height);
	}

	
//	 public ArrayList getBullets() {
//	        return bullets;
//	    }
	
	
}
