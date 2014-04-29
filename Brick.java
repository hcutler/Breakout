import java.awt.Color;
import java.awt.Graphics;


//public class Brick extends GameObj {
//	public static final int SIZE = 20;
//	public static final int INIT_X = GameCourt.COURT_WIDTH/2;
//	public static final int INIT_Y = GameCourt.COURT_HEIGHT + 10;
//	public static final int INIT_VEL_X = 0;
//	public static final int INIT_VEL_Y = 0;
	
	
public class Brick extends Square {

	Color color = Color.BLACK;
	
	public Brick(int courtWidth, int courtHeight, int brickWidth, int brickHeight) {
		super(courtWidth, courtHeight, brickWidth, brickHeight);
		// TODO Auto-generated constructor stub
		
		width = brickWidth;
		height = brickHeight;
	}
	
	
	public void setColor(Color c) {
		color = c;		
	}

	//draw bricks
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(pos_x, pos_y, width, height);
	}
	
	public void setPosition(int pos_x, int pos_y) {
		this.pos_x = pos_x;
		this.pos_y = pos_y; //set brick's position to correct offset every time draw one
	}
}
