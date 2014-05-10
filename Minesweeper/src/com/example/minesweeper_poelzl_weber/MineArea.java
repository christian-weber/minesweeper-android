package com.example.minesweeper_poelzl_weber;

import android.content.Context;
import android.widget.Button;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MineArea extends Button
{
	private boolean _isFree;
	private boolean _isMined;
	private boolean _isMarked;
	private int		_sourroundingMines;
	
	public MineArea(Context context)
	{
		super(context);
		ClearArea();
	}
	
	private void Explode()
	{
		this.setText("B");
		this.setTextColor(Color.RED);
		this.setTypeface(null, Typeface.BOLD);
	}
	
	public void MarkArea()
	{
		_isMarked = true;
		this.setText("F");
		this.setTextColor(Color.GREEN);
		this.setTypeface(null, Typeface.BOLD);
	}
	
	public void RemoveMark()
	{
		_isMarked = false;
		this.setText("");
		this.setTextColor(Color.BLACK);
		this.setTypeface(null, Typeface.NORMAL);
	}
	
	public boolean UncoverArea()
	{
		if (_isMined == true)
		{
			Explode();
			return false;
		}
		_isFree = true;
		this.setBackgroundColor(Color.WHITE);
		this.setText(Integer.toString(_sourroundingMines));
		return true;
	}
	
	public boolean IsMined()
	{
		return _isMined;
	}
	
	public void PlaceMine()
	{
		_isMined = true;
	}
	
	public void ClearArea()
	{
		_isMined = false;
		_isFree = false;
		_isMarked = false;
		this.setText("");
		this.setTextColor(Color.BLACK);
		this.setTypeface(null, Typeface.NORMAL);
		this.setBackgroundResource(R.layout.cellshape);		
	}
	
	public boolean IsFree()
	{
		return _isFree;
	}
	
	public boolean IsMarked()
	{
		return _isMarked;
	}
	
	public void SetCountOfSurroundingMines(int sourroundingMines)
	{
		_sourroundingMines = sourroundingMines;
	}
	
	public int GetCountSurroundingMines()
	{
		return _sourroundingMines;
	}
}
