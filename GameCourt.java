/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
public class GameCourt extends JPanel {

	// the state of the game logic
	private Paddle paddle; // the Black Square, keyboard control
	// private Ball snitch; // the Golden Snitch, bounces
	private Background image;
	private PowerUp extraLife;
	private PowerUp extendPaddle;

	// private int dx;
	// private int dy;
	// private int x;
	// private int y;

	// private ArrayList bullets;

	// 2nd ball that shows up if active object is true

	// public boolean secondBallActive; // = true;

	// private Ball snitch2 = null;
	// private Ball snitch2;

	private int numOfBricksAcross = 17;
	private int whiteSpaceAboveBricks = 50;
	private int pointsPerBall = 50;

	// store bricks in arraylist
	ArrayList<Brick> bricks = new ArrayList<Brick>();

	// arraylist for balls
	Set<Ball> balls = new HashSet<Ball>();

	public boolean playing = false; // whether the game is running
	private JLabel status; // Current status text (i.e. Running...)

	// flowlayout with 3 Jlabels

	// Game constants
	public static final int COURT_WIDTH = 900;
	public static final int COURT_HEIGHT = 600;
	public static final int SQUARE_VELOCITY = 8;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;

	private int counter = 11;

	private int spaceBetweenBricks = 4;
	private int brickWidth = COURT_WIDTH
			/ (numOfBricksAcross + spaceBetweenBricks);
	private int brickHeight = 16;
	private int whiteSpace = (COURT_WIDTH - (brickWidth + spaceBetweenBricks)
			* numOfBricksAcross) / 2;

	public GameCourt(JLabel status) {

		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		System.out.println(brickWidth);

		setUpBricks();
		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();

			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// This key listener allows the square to move as long
		// as an arrow key is pressed, by changing the square's
		// velocity accordingly. (The tick method below actually
		// moves the square.)
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {

				// int key = e.getKeyCode();
				//
				// if (key == KeyEvent.VK_SPACE) {
				// fire();
				// }

				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					paddle.v_x = -SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					paddle.v_x = SQUARE_VELOCITY;
			}

			// public void fire() {
			// bullets.add(new Bullet(x + paddle.width/4, y + paddle.width/4));
			// }

			public void keyReleased(KeyEvent e) {
				paddle.v_x = 0;
				paddle.v_y = 0;
			}
		});

		this.status = status;
	}

	private void setUpBricks() {
		// TODO Auto-generated method stub

		for (int i = 0; i < numOfBricksAcross; i++) {
			for (int j = 0; j < 8; j++) {
				// set offset from top of screen on gctx
				// Brick brick = new Brick(COURT_WIDTH, COURT_HEIGHT,
				// brickWidth,
				// brickHeight);

				Brick brick = new Brick(0, 0, 0, 0, brickWidth, brickHeight,
						COURT_WIDTH, COURT_HEIGHT);

				brick.setPosition(i * (brickWidth + spaceBetweenBricks)
						+ whiteSpace, j * (brickHeight + spaceBetweenBricks)
						+ whiteSpaceAboveBricks);

				if (j < 2) { // top 2
					brick.setColor(Color.BLUE);
				} else if (j < 4) { // next 2
					brick.setColor(Color.ORANGE);
				} else if (j < 6) {
					brick.setColor(Color.YELLOW);
				} else if (j < 8) {
					brick.setColor(Color.DARK_GRAY);
				}

				bricks.add(brick); // add bricks
			}

		}
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {

		// paddle = new Paddle(COURT_WIDTH, COURT_HEIGHT, 100, 16); // set width
		// // and
		// // height!

		paddle = new Paddle(0, 0, COURT_WIDTH / 2, COURT_HEIGHT, 100, 16,
				COURT_WIDTH, COURT_HEIGHT);

		image = new Background(COURT_WIDTH, COURT_HEIGHT);
		// snitch = new Ball(COURT_WIDTH, COURT_HEIGHT, Ball.INIT_POS_X,
		// Ball.INIT_POS_Y);

		// add ball to set
		balls.add(new Ball(COURT_WIDTH, COURT_HEIGHT, Ball.INIT_POS_X,
				Ball.INIT_POS_Y));

		//
		// if (secondBallActive == true) {
		// snitch2 = new Ball(COURT_WIDTH, COURT_HEIGHT, 50, 20);
		// secondBallActive = false;
		// }

		playing = true;
		status.setText("Game in progress...");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();

		// clear
		bricks = new ArrayList<Brick>();

		// re-setup bricks
		setUpBricks();

	}

	// boolean to pre-check if intersection will occur
	public boolean willIntersect = false;

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */

	void tick() {
		if (playing) {

			// powerup (extra life) every 11 bricks
			if (extraLife == null && extendPaddle == null
					&& (Game.score % 11 == 0 && Game.score != 0)) {
				extraLife = new PowerUp(COURT_WIDTH, COURT_HEIGHT, true);
			}

			// powerup (long paddle) every 9 bricks
			if (counter > 10 && extraLife == null && extendPaddle == null
					&& (Game.score % 9 == 0 && Game.score != 0)) {
				extendPaddle = new PowerUp(COURT_WIDTH, COURT_HEIGHT, false);
			}
			//
			// if ((Game.blocksLeft >= 150 && Game.blocksLeft <= 160)
			// && snitch2 == null) {
			// secondBallActive = true;
			// }
			//

			if (Game.score % pointsPerBall == 0 && Game.score != 0) {
				balls.add(new Ball(COURT_WIDTH, COURT_HEIGHT, 50, 20));
				pointsPerBall *= 8;
				System.out.println(Game.score);
				// snitch2 = new Ball(COURT_WIDTH, COURT_HEIGHT, 50, 20);
				// secondBallActive = false;
			}

			// advance the square and snitch in their
			// current direction.
			// secondBallActive = true;

			for (Ball b : balls) {
				b.move();
			}
			paddle.move();

			//
			// if (snitch2 != null) {
			// snitch2.move();
			// }

			ArrayList<Ball> toBeRemoved = new ArrayList<Ball>();

			for (Ball b : balls) {
				if (b.hitWall() != Direction.DOWN || paddle.willIntersect(b)) {
					// make the snitch bounce off walls...
					b.bounce(b.hitWall());
					// ...and the mushroom

				}

				else {

					if (balls.size() > 1) {
						// REMOVE BALL
						toBeRemoved.add(b);
					} else {
						Game.lives -= 1;
						// reset position here
						b.resetBallPosition();
					}
				}
				// if (snitch2 != null) {
				// if (snitch2.hitWall() != Direction.DOWN
				// || paddle.willIntersect(snitch2)) {
				// // make the snitch bounce off walls...
				// snitch2.bounce(snitch2.hitWall());
				// // ...and the mushroom
				//
				// }
				//
				// else {
				// if (secondBallActive) {
				// secondBallActive = false;
				// } else {
				// Game.lives -= 1;
				// // reset position here
				// snitch2.resetBallPosition();
				// }
				// }
			}

			for (Ball b : toBeRemoved) {
				balls.remove(b);
			}
			toBeRemoved = null;

			// movement/collision for extraLife
			if (extraLife != null && extraLife.isExtraLife) {
				extraLife.move();
				intersectPowerUp();
			}

			// movement/collision for extendPaddle
			if (extendPaddle != null && !(extraLife.isExtraLife)) {
				extendPaddle.move();
				intersectPowerUp();
			}

			// check for the game end conditions
			// if (paddle.intersects(snitch) && willIntersect) {

			for (Ball b : balls) {
				if (paddle.willIntersect(b)) {
					b.bounce(b.hitObj(paddle));
					// willIntersect = false;
				}
			}

			// if (snitch2 != null) {
			// if (paddle.willIntersect(snitch2)) {
			// snitch2.bounce(snitch2.hitObj(paddle));
			// // willIntersect = false;
			// }
			// }

			// temporary brick
			Brick brickToBeRemoved = null;
			for (Brick brick : bricks) {
				for (Ball b : balls) {
					if (brick.intersects(b)) {
						brickToBeRemoved = brick;
						b.bounce(b.hitObj(brick));
						Game.score += 10;
						Game.blocksLeft -= 1;
					}
				}
				//
				// if (snitch2 != null) {
				// if (brick.intersects(snitch2)) {
				// brickToBeRemoved = brick;
				// snitch2.bounce(snitch2.hitObj(brick));
				// Game.score += 10;
				// Game.blocksLeft -= 1;
				// }
				//
				// }

			}

			if (brickToBeRemoved != null) {
				bricks.remove(brickToBeRemoved);
			}

			// Feature 1: 2 balls between 150-160 and 80-90 bricks left
			// if ((Game.blocksLeft >= 150 && Game.blocksLeft <= 160 ||
			// Game.blocksLeft >= 80
			// && Game.blocksLeft <= 90)
			// && snitch2 == null) {

			// if (snitch2 == null) {
			// secondBallActive = true;
			// }

			// Win vs. lose
			if (Game.blocksLeft == 0) {
				playing = false;
				status.setText("You win!");
			}
			if (Game.lives == 0) {
				playing = false;
				status.setText("You lose. Better luck next time!");
			}

			// update the display
			repaint();
		}
	}

	public void intersectPowerUp() {
		if (extraLife != null && extraLife.intersects(paddle)
				&& extraLife.isExtraLife) {
			Game.lives += 1;
			extraLife = null;
		}

		if (extendPaddle != null && extendPaddle.intersects(paddle)
				&& !(extraLife.isExtraLife)) {
			int temp_width = paddle.width;
			paddle.width = temp_width * 2;
			counter = 0;
			extendPaddle = null;
		}
		counter++;

		if (counter == 10) {
			int temp_width = paddle.width;
			paddle.width = temp_width / 2;
		}
	}

	// draws paddle/snitch on screen - put bricks here
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		image.draw(g);
		paddle.draw(g);

		// draw ball
		for (Ball b : balls) {
			b.draw(g);
			// snitch.draw(g);
		}

		// if (snitch2 != null) {
		// snitch2.draw(g);
		// }

		if (extraLife != null) {
			extraLife.draw(g);
		}

		if (extendPaddle != null) {
			extendPaddle.draw(g);
		}

		for (Brick brick : bricks) {
			brick.draw(g);

			// draw score
			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
			g.setColor(Color.WHITE);
			g.drawString("Score: " + Game.score, 140, 20);
			g.drawString("Lives Left: " + Game.lives, 330, 20);
			g.drawString("Blocks Left: " + Game.blocksLeft, 560, 20);
		}

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
