package pieces;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class Hole {
	private HoleStatus[][] array;
	private ArrayList<Piece> pieces;
	private int piecesNeeded, rowCount;
	private int xpos, ypos;
	private boolean grainPerfect;
	

	static final int MAX_ROWS = 10;
	static final int MAX_COLS = 9;

	public Hole(int x, int y) {
		this.grainPerfect = true;
		this.xpos = x/20;
		this.ypos = y/20;
		this.array = new HoleStatus[MAX_ROWS][MAX_COLS];
		this.generateHole();
		this.addVariation();
	}

	private void generateHole() {
		Random r = new Random();
		// generates 4 rows for sure, then up to 6 more
		this.rowCount = 4 + r.nextInt(7);
		int offset = (MAX_ROWS - rowCount) / 2;
		for (int i = offset; i < rowCount + offset; i++) {
			for (int j = 0; j < MAX_COLS; j++) {
				if (j < 7 && j > 1) {
					this.array[i][j] = HoleStatus.HOLE;
				} else {
					this.array[i][j] = HoleStatus.BACKGROUND;
				}
			}
		}
	}

	private void addVariation() {
		int offset = (MAX_ROWS - this.rowCount) / 2;
		int maxNum = (this.rowCount * 4) / 5; // limit of generated random
												// number for random
												// spots...

		Random r = new Random();
		int numAdded = r.nextInt(maxNum - 1) * 5;
		if (numAdded == 0 && this.rowCount > 6) {
			numAdded += 5;
		}
		int count = 0; // currently added variations...
		int row;

		while (count < numAdded) {
			System.out.println(count + " of " + numAdded);
			row = offset + r.nextInt(this.rowCount);
			// indecises per row holed already: 2 3 4 5 6
			// TODO: find a cleaner way to do this...
			if (r.nextDouble() > .5) { // start from right side
				if (this.array[row][7] != HoleStatus.HOLE) {
					this.array[row][7] = HoleStatus.HOLE;
					count += 1;
				} else {
					if (this.array[row][8] != HoleStatus.HOLE) {
						this.array[row][8] = HoleStatus.HOLE;
						count += 1;
					}
				}
			} else { // do left side
				if (this.array[row][1] != HoleStatus.HOLE) {
					this.array[row][1] = HoleStatus.HOLE;
					count += 1;
				} else {
					if (this.array[row][0] != HoleStatus.HOLE) {
						this.array[row][0] = HoleStatus.HOLE;
						count += 1;
					}
				}
			}
		}
	}

	public void draw(Graphics2D g2) {
		g2.setColor(new Color(0,0,0));
		g2.drawRect(xpos*Piece.BLOCK_WIDTH, ypos*Piece.BLOCK_WIDTH, Piece.BLOCK_WIDTH*MAX_COLS, Piece.BLOCK_WIDTH*MAX_ROWS);
		for (int i = 0; i < MAX_ROWS; i++) {
			for (int j = 0; j < MAX_COLS; j++) {
				if (this.array[i][j] == HoleStatus.HOLE) {
					g2.setColor(new Color(0, 0, 0));
					g2.fillRect((this.xpos + j) * Piece.BLOCK_WIDTH, (this.ypos + i) * Piece.BLOCK_WIDTH, Piece.BLOCK_WIDTH,
							Piece.BLOCK_WIDTH);
					g2.setColor(new Color(127,127,127));
					g2.drawRect((this.xpos + j) * Piece.BLOCK_WIDTH, (this.ypos + i) * Piece.BLOCK_WIDTH, Piece.BLOCK_WIDTH,
							Piece.BLOCK_WIDTH);
				} else if (this.array[i][j] == HoleStatus.FILLED) {
					g2.setColor(new Color(0, 192, 0));
					g2.fillRect((this.xpos + j) * Piece.BLOCK_WIDTH, (this.ypos + i) * Piece.BLOCK_WIDTH, Piece.BLOCK_WIDTH,
							Piece.BLOCK_WIDTH);
				}
			}
		}

	}

	public void fill(int[][] points, int x, int y, GrainOrientation go) {
		// sets the grain tracker to false if the submitted piece hsa vertical orientation
		if (grainPerfect){
			if (go==GrainOrientation.VERTICAL){
				this.grainPerfect = false;
				System.out.println("Lost grain bonus for this hole...");
			}
		}
		//fills points if possible
		for (int[] p : points) {
			
			int fillx = x - xpos + p[0];
			int filly = y - ypos + p[1];
			System.out.print("trying to fill " + filly + " " + fillx);
			if (this.checkStatus(fillx, filly) == HoleStatus.HOLE) {
				this.array[filly][fillx] = HoleStatus.FILLED;
				System.out.print(" SUCCESSFULLY");
			}
			System.out.print("\n");
		}
	}

	public boolean canFill(int[][] points, int cx, int cy) {
		int x = (cx - xpos);
		int y = (cy - ypos);
		System.out.println("checking " + x + " " + y);
		for (int[] p : points) {
			System.out.println("checking " + (p[1]+y) + " " + (p[0]+x));
			if (this.checkStatus(p[1] + y, p[0] + x) == HoleStatus.HOLE) {
				return true;
			}
		}
		return false;
	}

	public HoleStatus checkStatus(int x, int y) {
		if (x < 0 || x >= MAX_COLS || y < 0 || y >= MAX_ROWS) {
			System.out.println("Out of bounds of hole");
			return HoleStatus.BACKGROUND;
		} else {
			return this.array[y][x];
		}
	}

	public boolean inbounds(int x, int y) {
		System.out.println("Checking hole bounds at " + x + " " +  y);
		if (x <= xpos + MAX_COLS && x >= xpos) {
			if (y <= ypos + MAX_ROWS && y >= ypos) {
				return true;
			}
		}
		return false;
	}

}
