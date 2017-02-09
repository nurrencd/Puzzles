package Carpentry;

import javax.swing.JFrame;

public class GameFrame {
	private JFrame frame;
	private Game game;
	
	public GameFrame(){
		this.frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void run(){
		this.game = new Game(this.frame);
		this.game.run();
	}
	
}
