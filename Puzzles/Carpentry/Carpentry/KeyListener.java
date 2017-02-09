package Carpentry;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {
	private Game game;

	public KeyListener(Game game) {
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 90) {
			// z flips
			this.game.flipSelectedPiece();
			System.out.println("flipping...");
		} else if (e.getKeyCode() == 88) {
			// x rotates
			this.game.rotateSelectedPiece();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub.

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub.

	}

}
