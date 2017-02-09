package pieces;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class P extends Piece {

	public P(int x, int y) {
		super(x, y);
		this.width = 2;
		this.height = 3;
		this.yOffset = 1;
		this.xOffset = 0;
		// generate image with proper grain
		String url = "C:\\EclipseWorkspaces\\csse230\\Puzzles\\Carpentry\\Images\\P";
		Random r = new Random();
		if (r.nextDouble() < .5) {
			url += "H.png";
			this.grain = GrainOrientation.HORIZONTAL;
		} else {
			url += "V.png";
			this.grain = GrainOrientation.VERTICAL;
		}
		try {
			this.img = ImageIO.read(new File(url));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public int[][] getPoints() {
		if (!isFlipped){
			if(rotationCount==0){
				return new int[][]{{0,0},{0,-1},{1,-1},{1,0},{0,1}};
			}
		}
		return null;
	}

}
