import java.awt.Color;
import java.awt.Graphics;



public class Paddle extends Square {

	//private int paddleWidth;
	//private int paddleHeight;
	Color c = Color.BLACK;
	
	
	public Paddle(int courtWidth, int courtHeight, int paddleWidth, int paddleHeight) {
		
		super(courtWidth, courtHeight, paddleWidth, paddleHeight);
		// TODO Auto-generated constructor stub
		
//		this.paddleWidth = paddleWidth;
//		this.paddleHeight = paddleHeight;
	
		//this.width = paddleWidth;
		//this.height = paddleHeight;
	}
	
	//draw paddle
	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillRect(pos_x, pos_y, width, height);
	}
	
	
}
