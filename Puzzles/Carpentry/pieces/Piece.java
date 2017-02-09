package pieces;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Piece implements Filler {
	public static final int BLOCK_WIDTH = 20;
	protected BufferedImage img;

	protected int xpos, ypos, xOrigin, yOrigin;
	protected int width, height; // 1-5 for each, switches on rotating through Graphics rotations
	public int xOffset, yOffset; // picees "picked up" by center of piece
	protected int rotationCount;

	protected boolean placed;
	protected boolean selected;
	protected boolean isFlipped;
	protected boolean isSelected;

	protected GrainOrientation grain;

	public Piece(int x, int y) {
		this.xpos = x/Piece.BLOCK_WIDTH;
		this.xOrigin = x;
		this.ypos = y/Piece.BLOCK_WIDTH;
		this.yOrigin = y;
		this.rotationCount = 0;
		this.placed = false;
		this.selected = false;
	}

	public void flip() {
		// flip only changes a boolean variable that affects rendering
		// only flips across the Y-axis
		if (this.isFlipped) {
			this.isFlipped = false;
		} else {
			this.isFlipped = true;
		}
	}

	public void rotate() {
		// rotation changes the width and height of the image, and the
		// GrainOrientation enum
		// includes swapping the x and y center offsets as well for consistency
		//^ is wrong

		this.rotationCount = (this.rotationCount + 1) % 4;
		System.out.println(this.rotationCount);
		if (this.grain==GrainOrientation.HORIZONTAL){
			this.grain = GrainOrientation.VERTICAL;
		}
		else{
			this.grain = GrainOrientation.HORIZONTAL;
		}
	}

	public void updatePosition(int x, int y) {
		this.xpos = x/BLOCK_WIDTH - xOffset;
		this.ypos = y/BLOCK_WIDTH - yOffset;
	}

	public int getWidth() {
		if (isFlipped && rotationCount % 2 == 0) {
			return -this.width;
		}
		return width;
	}

	public int getHeight() {
		if (isFlipped && rotationCount % 2 == 1) {
			return -height;
		}
		return height;
	}

	public void select(int x, int y) {
		this.isSelected = true;
	}

	public void deselect() {
		this.isSelected = false;
		this.isFlipped = false;
		this.xpos = this.xOrigin/Piece.BLOCK_WIDTH;
		this.ypos = this.yOrigin/Piece.BLOCK_WIDTH;
		this.xOffset = 0;
		this.yOffset = 0;
		while (this.rotationCount!=0){
			this.rotate();
		}
	}

	public void draw(Graphics2D g2) {
		//adjusts for offset via center of block
		int x = (this.xpos - this.xOffset);
		int y = (this.ypos - this.yOffset);
		//this block adjusts the translation due to rotation or flipping
		if (isFlipped) {
			if (rotationCount == 0) {
				x += width;
			}
			else if (rotationCount==1){
				
			}
			else if (rotationCount==2){
				y+=height;
			}
			else if (rotationCount==3){
				y+=width;
				x+=height;
			}
		} else {
			if (this.rotationCount == 1) {
				x += height;
			} else if (this.rotationCount == 2) {
				y += height;
				x += width;
			} else if (this.rotationCount == 3) {
				y += width;
			}
		}
		x*=Piece.BLOCK_WIDTH;
		y*=Piece.BLOCK_WIDTH;
		g2.translate(x, y);
		double angle = Math.PI / 2 * this.rotationCount;
		g2.rotate(angle);
		g2.drawImage(this.img, 0, 0, this.getWidth() * BLOCK_WIDTH, this.getHeight() * BLOCK_WIDTH, null);
		g2.rotate(-angle);
		g2.translate(-x, -y);
	}

	public boolean inbounds(int x, int y) {
		if (Math.sqrt((x - this.xpos) * (x - this.xpos) + (y - this.ypos) * (y - this.ypos)) < 4) {
			return true;
		}
		return false;
	}

	public GrainOrientation getGrainOrientation() {
		return this.grain;
	}

}
