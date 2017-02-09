package Carpentry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import pieces.Hole;
import pieces.Piece;

public class GameComponent extends JComponent {
	private Game game;
	private static final Color BACKGROUND_COLOR = new Color(255,100,50);

	public GameComponent(Game game) {
		super();
		this.game = game;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(BACKGROUND_COLOR);
		g2.fillRect(0, 0, Game.WINDOW_SIZE.width, Game.WINDOW_SIZE.height);
		this.game.drawToolbox(g2);
		for (Hole h : this.game.holes){
			h.draw(g2);
		}
		for (Piece p : this.game.pieces){
			p.draw(g2);
		}
	}
}
