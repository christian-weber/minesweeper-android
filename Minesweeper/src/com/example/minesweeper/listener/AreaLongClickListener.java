package com.example.minesweeper.listener;

import android.app.Activity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

import com.example.minesweeper.MineArea;
import com.example.minesweeper.MineField;

public class AreaLongClickListener extends Activity implements
		OnLongClickListener {

	private final TextView mineCountView;
	private final MineField mineField;
	private final int rowIndex;
	private final int colIndex;

	public AreaLongClickListener(MineField mineField, int rowIndex, int colIndex) {
		this.mineCountView = mineField.getMineCountView();
		this.mineField = mineField;
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
	}

	@Override
	public boolean onLongClick(View v) {
		MineArea[][]mineAreas = mineField.getMineAreas();
		
		String text = mineCountView.getText().toString();
		int newMineCount = Integer.parseInt(text);

		if (mineAreas[rowIndex][colIndex].isFree()) {
			return true;
		}

		if (mineAreas[rowIndex][colIndex].isMarked()) {
			mineAreas[rowIndex][colIndex].removeMark();
			++newMineCount;
		}

		else {
			mineAreas[rowIndex][colIndex].mark();
			--newMineCount;
		}

		mineCountView.setText(Integer.toString(newMineCount));
		return true;
	}
}