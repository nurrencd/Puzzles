package pieces;
/**
 * 
 * enum to describe a spot in the Holes' arrays. A block
 * can be either part of the background (either within the array or outside),
 * an unfilled hole, or a filled hole.
 *
 * @author nurrencd.
 *         Created Feb 5, 2017.
 */
public enum HoleStatus {
	BACKGROUND, HOLE, FILLED,
}
