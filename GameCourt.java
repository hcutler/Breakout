/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;

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
	private Ball snitch; // the Golden Snitch, bounces
	private Background image;

	// 2nd ball that shows up if active object is true
	public boolean secondBallActive = false;
	private Ball snitch2;

	private int numOfBricksAcross = 17;
	private int whiteSpaceAboveBricks = 50;

	// store bricks in arraylist
	ArrayList<Brick> bricks = new ArrayList<Brick>();

	public boolean playing = false; // whether the game is running
	private JLabel status; // Current status text (i.e. Running...)

	// flowlayout with 3 Jlabels

	// Game constants
	public static final int COURT_WIDTH = 900;
	public static final int COURT_HEIGHT = 600;
	public static final int SQUARE_VELOCITY = 8;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;

	private int spaceBetweenBricks = 4;
	private int brickWidth = COURT_WIDTH
			/ (numOfBricksAcross + spaceBetweenBricks);
	private int brickHeight = 20;
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
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					paddle.v_x = -SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					paddle.v_x = SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)
					paddle.v_y = SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_UP)
					paddle.v_y = -SQUARE_VELOCITY;
			}

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
			for (int j = 0; j < 10; j++) {
				// set offset from top of screen on gctx
				Brick brick = new Brick(COURT_WIDTH, COURT_HEIGHT, brickWidth,
						brickHeight);

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
					brick.setColor(Color.GREEN);
				} else if (j < 10) {
					brick.setColor(Color.DARK_GRAY);
				}

				bricks.add(brick); // add bricks
			}

		}
		// System.out.println(whiteSpace);
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {

		paddle = new Paddle(COURT_WIDTH, COURT_HEIGHT, 100, 30); // set width
																	// and
																	// height!
		image = new Background(COURT_WIDTH, COURT_HEIGHT);
		snitch = new Ball(COURT_WIDTH, COURT_HEIGHT);

		// if (secondBallActive == true) {
		// snitch2 = new Ball(COURT_WIDTH, COURT_HEIGHT);
		// }

		playing = true;
		status.setText("Game in progress...");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	// boolean to pre-check if intersection will occur
	public boolean willIntersect = false;

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */

	void tick() {
		if (playing) {
			// advance the square and snitch in their
			// current direction.

			paddle.move();
			snitch.move();

			if (snitch.hitWall() != Direction.DOWN) {
				// make the snitch bounce off walls...
				snitch.bounce(snitch.hitWall());
				// ...and the mushroom

			}

			else {
				Game.lives -= 1;
				// reset position here
				snitch.resetBallPosition();

			}

			// if (paddle.willIntersect(snitch)) {
			// willIntersect = true;
			// }

			// check for the game end conditions
			// if (paddle.intersects(snitch) && willIntersect) {
			if (paddle.intersects(snitch)) {
				snitch.bounce(snitch.hitObj(paddle));
				// willIntersect = false;
			}

			// temporary brick
			Brick brickToBeRemoved = null;
			for (Brick brick : bricks) {
				if (brick.intersects(snitch)) {
					brickToBeRemoved = brick;
					snitch.bounce(snitch.hitObj(brick));
					Game.score += 10;
					Game.blocksLeft -= 1;
				}

			}
			if (brickToBeRemoved != null) {
				bricks.remove(brickToBeRemoved);
			}

			while (secondBallActive) {
				// create 2nd ball
				snitch2 = new Ball(COURT_WIDTH, COURT_HEIGHT);

				// while active, do same thing for second ball
				paddle.move();
				snitch2.move();

				if (snitch2.hitWall() != Direction.DOWN) {
					// make the snitch bounce off walls...
					snitch2.bounce(snitch2.hitWall());
				}

				else {
					Game.lives -= 1;
					// reset position here
					snitch2.resetBallPosition();
				}

				// check for the game end conditions
				// if (paddle.intersects(snitch) && willIntersect) {
				if (paddle.intersects(snitch2)) {
					snitch2.bounce(snitch2.hitObj(paddle));
					// willIntersect = false;
				}

				// temporary brick
				for (Brick brick : bricks) {
					if (brick.intersects(snitch2)) {
						brickToBeRemoved = brick;
						snitch2.bounce(snitch2.hitObj(brick));
						Game.score += 10;
						Game.blocksLeft -= 1;
					}
				}
				if (brickToBeRemoved != null) {
					bricks.remove(brickToBeRemoved);
				}
			}

			// when active object true,second ball has same properties as first
			// ball
			// if (secondBallActive == true) {

			// }

			//
			// // get center of ball
			// int ballRadius = ball. / 2;
			// Point ballCenter = new Point(pos_x + width / 2, pos_y + height /
			// 2);
			//
			// if (ballCenter.y >= obj.pos_y - ballRadius) {
			// Game.lives -= 1;
			// }

			// Feature 1: 2 balls between 15-
			if (Game.blocksLeft >= 150 && Game.blocksLeft <= 160) {
				secondBallActive = true;
			}

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

	// draws paddle/snitch on screen - put bricks here
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		image.draw(g);
		paddle.draw(g);
		snitch.draw(g);

		// if (secondBallActive == true) {
		// snitch2.draw(g);
		// }

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
