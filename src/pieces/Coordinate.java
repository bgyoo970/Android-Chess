package pieces;

import java.io.Serializable;

/*
 * CS 213 
 * Assignment 2: Chess
 * Sesh Venugopal
 * 
 * @author Brian Yoo bgy2
 */

public class Coordinate implements Serializable{
	private static final long serialVersionUID = 14793286592568313L;
	public int xCoord;
	public int yCoord;
	
	public Coordinate(int xCoord, int yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}
	
}
