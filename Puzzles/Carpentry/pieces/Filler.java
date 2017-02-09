package pieces;

public interface Filler {

	/**
	 * 
	 * returns the points to fill relative to its base position, to be used by the
	 * Hole class
	 *
	 * @return int[5][2] points to fill
	 */
	int[][] getPoints();
}
