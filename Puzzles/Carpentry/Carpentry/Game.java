package Carpentry;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JFrame;

import pieces.GrainOrientation;
import pieces.Hole;
import pieces.P;
import pieces.Piece;

public class Game {
	private JFrame frame;
	private GameComponent comp;
	private boolean exit;

	public Hole[] holes;
	public Piece[] pieces;
	private Piece selectedPiece;

	static final int X_LEFT = 200;
	static final int X_RIGHT = 900;
	static final int Y_TOP = 80;
	static final int Y_BOTTOM = 420;

	static final Dimension WINDOW_SIZE = new Dimension(1280, 720);
	static final int TOOLBOX_Y = 320;
	static final int TOOLBOX_X = 450;
	static final int TOOLBOX_OFFSET = 120;
	static final int TOOLBOX_WIDTH = 400;
	static final int TOOLBOX_HEIGHT = 80;
	static final int TOOLBOX_BORDER = 30;
	static final Color TOOLBOX_COLOR = new Color(100, 100, 100);

	public Game(JFrame frame) {
		this.holes = new Hole[4];
		this.pieces = new Piece[4];
		for (int i = 0; i < 4; i++) {
			this.pieces[i] = new P(TOOLBOX_X + i * TOOLBOX_OFFSET, TOOLBOX_Y + TOOLBOX_BORDER);
		}
		this.holes[0] = new Hole(X_LEFT, Y_TOP);
		this.holes[1] = new Hole(X_RIGHT, Y_TOP);
		this.holes[2] = new Hole(X_LEFT, Y_BOTTOM);
		this.holes[3] = new Hole(X_RIGHT, Y_BOTTOM);

		this.selectedPiece = null;

		this.frame = frame;
		this.comp = new GameComponent(this);
		this.frame.add(this.comp);
		this.comp.validate();
		this.frame.addKeyListener(new KeyListener(this));
		this.comp.addMouseListener(new MouseHandler(this));
		this.frame.setVisible(true);
		this.frame.setSize(WINDOW_SIZE);

	}

	public void flipSelectedPiece() {
		if (this.selectedPiece != null) {
			this.selectedPiece.flip();
		}
	}

	public void rotateSelectedPiece() {
		if (this.selectedPiece != null) {
			this.selectedPiece.rotate();
		}
	}

	public void handleClick(int cx, int cy) {
		if (this.selectedPiece != null) {
			if (this.inboundsToolbox(cx, cy)) {
				this.selectedPiece.deselect();
				this.selectedPiece = null;
			} else {
				for (Hole h : this.holes) {
					if (h.inbounds(cx/Piece.BLOCK_WIDTH,cy/Piece.BLOCK_WIDTH)) {
						int[][] points = this.selectedPiece.getPoints();
						GrainOrientation go = this.selectedPiece.getGrainOrientation();
						if (h.canFill(points, cx/Piece.BLOCK_WIDTH,cy/Piece.BLOCK_WIDTH)) {
							h.fill(points, cx/Piece.BLOCK_WIDTH,cy/Piece.BLOCK_WIDTH, go);
						}
					}
				}
			}

		} else { // selected Piece == null
			for (Piece p : this.pieces) {
				if (p.inbounds(cx/Piece.BLOCK_WIDTH,cy/Piece.BLOCK_WIDTH)) {
					this.selectedPiece = p;
					p.select(cx/Piece.BLOCK_WIDTH,cy/Piece.BLOCK_WIDTH);
				}
			}
		}
	}

	public void drawToolbox(Graphics2D g2) {
		g2.setColor(TOOLBOX_COLOR);
		g2.fillRect(TOOLBOX_X - TOOLBOX_BORDER, TOOLBOX_Y - TOOLBOX_BORDER, TOOLBOX_WIDTH + 2 * TOOLBOX_BORDER,
				TOOLBOX_HEIGHT + 2 * TOOLBOX_BORDER);
	}

	public boolean inboundsToolbox(int x, int y) {
		if (x >= TOOLBOX_X - TOOLBOX_BORDER && x <= TOOLBOX_X + TOOLBOX_WIDTH + TOOLBOX_BORDER) {
			if (y >= TOOLBOX_Y - TOOLBOX_BORDER && y <= TOOLBOX_Y + TOOLBOX_HEIGHT + TOOLBOX_BORDER) {
				return true;
			}
		}
		return false;
	}

	public void updateSelectedPiecePosition() {
		if (this.selectedPiece != null) {
			Point p = this.frame.getMousePosition();
			if (p != null) {
				this.selectedPiece.updatePosition((int) p.getX(), (int) p.getY());
			}
		}

	}

	public void run() {
		// runs the game
		while (true) {
			this.updateSelectedPiecePosition();
			this.comp.repaint();
			try {
				Thread.sleep(33);
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}
	}

}
