/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {

	protected JLabel blocksLeftLabel;
	protected JLabel livesLabel;
	protected JLabel scoreLabel;

	public static int blocksLeft = 170;
	public static int lives = 3;
	public static int score = 0;

	public int getBlocksLeft() {
		return blocksLeft;
	}

	public void setBlocksLeft(int blocksLeft) {
		this.blocksLeft = blocksLeft;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void run() {
		// NOTE : recall that the 'final' keyword notes inmutability
		// even for local variables.

		// Top-level frame in which game components live
		// Be sure to change "TOP LEVEL FRAME" to the name of your game
		final JFrame frame = new JFrame("BREAKOUT");

		// give frame a card layout
		// frame.setLayout(new CardLayout());

		// frame.setLocation(300, 300);

		// Status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		final JLabel status = new JLabel("Game in progress...");
		status_panel.add(status);

		// Main Cing area
		final GameCourt court = new GameCourt(status);
		frame.add(court, BorderLayout.CENTER);

		// add flow layout to left and right (west/east)
		JPanel westPanel = new JPanel();
		JPanel eastPanel = new JPanel();

		westPanel.setLayout(new FlowLayout());
		// add 2 panels within panel - one set background to blue and add second

		westPanel.setBackground(Color.BLUE);
		eastPanel.setBackground(Color.BLUE);

		// frame.add(westPanel, BorderLayout.WEST);
		// frame.add(eastPanel, BorderLayout.EAST);

		// Reset button
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.NORTH);

		control_panel.setLayout(new FlowLayout());

		System.out.println(score);

		// JLabel blocksLeftLabel = new JLabel("BLOCKS LEFT:" + blocksLeft +
		// "         ");
		// JLabel livesLabel = new JLabel("LIVES:" + lives + "        ");
		// JLabel scoreLabel = new JLabel("SCORE:" + score);

		// control_panel.add(blocksLeftLabel);
		// control_panel.add(livesLabel);
		// control_panel.add(scoreLabel);

		// Note here that when we add an action listener to the reset
		// button, we define it as an anonymous inner class that is
		// an instance of ActionListener with its actionPerformed()
		// method overridden. When the button is pressed,
		// actionPerformed() will be called.

		final JButton reset = new JButton("Reset");
		final JButton instructions = new JButton("Instructions");
		final JButton quit = new JButton("Quit");

		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// pause game while instructions showing
				court.playing = false;
				// court.reset();

				// default title and icon
				JOptionPane
						.showMessageDialog(
								frame,
								"This is brickbreaker.\n You have three lives. \n Try to hit the bricks with the ball \n"
										+ "and don't let the ball hit the ground or you lose a life. There are powerups and other features. \n"
										+ "Good luck!");

				court.playing = true;
				court.requestFocusInWindow();

			}
		});

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// JFrame.EXIT_ON_CLOSE;
				//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				System.exit(frame.EXIT_ON_CLOSE);
			}
		});
		
		
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.reset();
			}
		});

		control_panel.add(reset);
		control_panel.add(instructions);
		control_panel.add(quit);

		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Start game
		court.reset();
	}

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
