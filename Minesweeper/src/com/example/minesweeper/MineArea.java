package com.example.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Button;

/**
 * A mine area specifies the elements of a mine field. It can contain
 * a mine or a number of surrounding mines.
 */
public class MineArea extends Button {
	
	private boolean free;
	private boolean mined;
	private boolean marked;
	private int surroundingMines;

	public MineArea(Context context) {
		super(context);
		clear();
	}

	/**
	 * Explodes the mine area.
	 */
	private void explode() {
		this.setText("B");
		this.setTextColor(Color.RED);
		this.setTypeface(null, Typeface.BOLD);
	}

	/**
	 * Marks the mine area.
	 */
	public void mark() {
		marked = true;
		this.setText("F");
		this.setTextColor(Color.GREEN);
		this.setTypeface(null, Typeface.BOLD);
	}

	/**
	 * Removes the mark of the mine area.
	 */
	public void removeMark() {
		marked = false;
		this.setText("");
		this.setTextColor(Color.BLACK);
		this.setTypeface(null, Typeface.NORMAL);
	}

	/**
	 * Uncovers the mine area by exploding it when the area contains a mine or
	 * by displaying the number of surrounding mines.
	 * 
	 * @return boolean
	 */
	public boolean uncover() {
		if (mined == true) {
			explode();
			return false;
		}
		free = true;
		this.setBackgroundColor(Color.WHITE);
		this.setText(Integer.toString(surroundingMines));
		return true;
	}

	/**
	 * Indicates if the mine area is mined.
	 * 
	 * @return boolean
	 */
	public boolean isMined() {
		return mined;
	}

	/**
	 * Marks the mine area as mined.
	 */
	public void placeMine() {
		mined = true;
	}

	/**
	 * Clears the mine area by setting the properties to a default value.
	 */
	public void clear() {
		mined = false;
		free = false;
		marked = false;
		this.setText("");
		this.setTextColor(Color.BLACK);
		this.setTypeface(null, Typeface.NORMAL);
		this.setBackgroundResource(R.layout.cellshape);
	}

	/**
	 * Indicates if the mine area is free.
	 * 
	 * @return boolean
	 */
	public boolean isFree() {
		return free;
	}

	/**
	 * Indicates if the mine area is marked.
	 * 
	 * @return boolean
	 */
	public boolean isMarked() {
		return marked;
	}

	/**
	 * Sets the count of surrounding mines.
	 * 
	 * @param surroundingMines
	 */
	public void setCountOfSurroundingMines(int surroundingMines) {
		this.surroundingMines = surroundingMines;
	}

	/**
	 * Returns the count of surrounding mines.
	 * 
	 * @return int
	 */
	public int getCountSurroundingMines() {
		return surroundingMines;
	}
	
}
