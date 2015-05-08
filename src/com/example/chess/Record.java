package com.example.chess;
/*
 * CS 213 
 * Android Project
 * Sesh Venugopal
 * 
 * @author Brian Yoo bgy2
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import pieces.Piece;

public class Record implements Serializable{
	
	private static final long serialVersionUID = 6490560424523887398L;
	
	public String recordName;
	public ArrayList<String> srcList;
	public ArrayList<String> destList;
	public Calendar cal;
	public ArrayList<Piece[][]> moveList;
	
	// Constructor
	public Record(String recordName, ArrayList<String> srcList, ArrayList<String> destList) {
		this.recordName = recordName;
		this.srcList = srcList;
		this.destList = destList;
	}
	public Record(String recordName, ArrayList<Piece[][]> moveList) {
		this.recordName = recordName;
		this.moveList = moveList;
	}
}
