/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;

/**
 * A basic game object displayed as a yellow circle, starting in the upper left
 * corner of the game court.
 * 
 */
public class Ball extends GameObj {

	public static final int SIZE = 20;
	public static final int INIT_POS_X = GameCourt.COURT_WIDTH/2;
	public static final int INIT_POS_Y = GameCourt.COURT_HEIGHT/5 * 4;
	public static final int INIT_VEL_X = 4;
	public static final int INIT_VEL_Y = -5;
	
	public static int width;
	public static int height;
	

	public Ball(int courtWidth, int courtHeight) {
//		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE,
//				courtWidth, courtHeight);
		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE,
				courtWidth, courtHeight);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(pos_x, pos_y, SIZE, SIZE);
	}
	
	//reset position method
	public void resetBallPosition() {
		pos_x = INIT_POS_X;
		pos_y = INIT_POS_Y;
		v_x = INIT_VEL_X;
		v_y = INIT_VEL_Y;
	}

	
	public void intersectPaddle(Paddle paddle) {
		if (this.willIntersect(paddle) && v_y >0) {
		 v_y = -v_y;
		}
		
		if ((pos_x + width) > (paddle.pos_x + paddle.width)) {
			if (v_x <0) {
				v_x = -v_x +1;
			}
			else {
				v_x +=1; //v_x++;
			}
		}
		else if (pos_x < paddle.pos_x) {
			if (v_x >0) {
				v_x = -v_x -1;
			}
			else {
				v_x -= 1; //v_x --;
			}
		}
		
	}
	
//	@Override
//	public boolean intersects(GameObj obj) {
//
//		// get center of ball
//		int ballRadius = width / 2;
//		Point ballCenter = new Point(pos_x + SIZE / 2, pos_y + SIZE / 2);
//
//		return ((ballCenter.y >= obj.pos_y - ballRadius)
//				&& (ballCenter.x <= obj.pos_x + obj.width + ballRadius || ballCenter.x >= obj.pos_x
//						- obj.width - ballRadius)
//				&& (ballCenter.y <= obj.pos_y + obj.height + ballRadius));
//	}

	
	//if will intersect
}