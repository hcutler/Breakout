import java.awt.Color;
import java.awt.Graphics;


//public class Brick extends GameObj {
//	public static final int SIZE = 20;
//	public static final int INIT_X = GameCourt.COURT_WIDTH/2;
//	public static final int INIT_Y = GameCourt.COURT_HEIGHT + 10;
//	public static final int INIT_VEL_X = 0;
//	public static final int INIT_VEL_Y = 0;
	
	
public class Brick extends GameObj {
	
	public static final int SIZE = 20;
	public static final int INIT_X = GameCourt.COURT_WIDTH/2;
	public static final int INIT_Y = GameCourt.COURT_HEIGHT + 10;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;	
	public Color c;
	

	
	

	public Brick(int v_x, int v_y, int pos_x, int pos_y, 
			int width, int height, int court_width, int court_height){
	
	super(v_x, v_y, pos_x, pos_y, width, height, court_width, court_height);	
	

	c = Color.BLACK;
	
	}
//	public Brick(int courtWidth, int courtHeight, int brickWidth, int brickHeight) {
//		super(courtWidth, courtHeight, brickWidth, brickHeight);
//		// TODO Auto-generated constructor stub
//		
//		width = brickWidth;
//		height = brickHeight;
//	}
	
	
	public void setColor(Color c) {
//		color = c;		
		this.c = c;
	}

	//draw bricks
	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillRect(pos_x, pos_y, width, height);
	}
	
	public void setPosition(int pos_x, int pos_y) {
		this.pos_x = pos_x;
		this.pos_y = pos_y; //set brick's position to correct offset every time draw one
	}
}
