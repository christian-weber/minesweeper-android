package com.example.minesweeper;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.minesweeper.listener.AreaClickListener;
import com.example.minesweeper.listener.AreaLongClickListener;

public class MineField extends Activity {

	private int columnCount = 5;
	private int rowCount = 8;
	private MineArea mineAreas[][];
	private TableLayout mineField;
	private TextView mineCountView;
	private int numberOfTotalMines = 8;

	public int getColumnCount() {
		return columnCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public MineArea[][] getMineAreas() {
		return mineAreas;
	}

	public TableLayout getMineField() {
		return mineField;
	}

	public TextView getMineCountView() {
		return mineCountView;
	}

	public int getNumberOfTotalMines() {
		return numberOfTotalMines;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_field);

		mineField = (TableLayout) findViewById(R.id.Mine_Field);
		mineCountView = (TextView) findViewById(R.id.CountMinesIdentified);
		createMineGrid();
		placeMines();
		displayMineGrid();

		ImageButton newButton = (ImageButton) findViewById(R.id.NewGame);
		newButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				rebuildMineGrid();
				placeMines();
				mineField.setBackgroundResource(R.layout.cellshape);
			}
		});
	}

	/**
	 * Creates the mine grid. Registers click listeners for a simple as well as
	 * a long click.
	 */
	private void createMineGrid() {
		mineAreas = new MineArea[rowCount][columnCount];
		
		// for each row
		for (int row = 0; row < rowCount; ++row) {
			// for each column
			for (int col = 0; col < columnCount; ++col) {
				mineAreas[row][col] = new MineArea(this);

				// register an area click listener
				AreaClickListener listener1 = null;
				listener1 = new AreaClickListener(this, row, col);
				mineAreas[row][col].setOnClickListener(listener1);

				// register an area long click listener
				AreaLongClickListener listener2 = null;
				listener2 = new AreaLongClickListener(this, row, col);
				mineAreas[row][col].setOnLongClickListener(listener2);
			}
		}
	}

	/**
	 * Rebuilds the mine grid by clearing all mine areas.
	 */
	private void rebuildMineGrid() {
		for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < columnCount; ++columnIndex) {
				mineAreas[rowIndex][columnIndex].clear();
			}
		}
	}

	private void placeMines() {
		Random rand = new Random();

		int row = 0;
		int col = 0;

		// for each mine
		for (int i = 0; i < numberOfTotalMines; ++i) {
			
			// find a mine area where to place the mine
			while ((row == 0 && col == 0) || mineAreas[row][col].isMined()) {
				row = rand.nextInt(rowCount);
				col = rand.nextInt(columnCount);
			}
			
			// place the mine
			mineAreas[row][col].placeMine();
		}

		// for each row
		for (row = 0; row < rowCount; ++row) {
			// for each column
			for (col = 0; col < columnCount; ++col) {
				calculateMineCount(row, col);
			}
		}

		mineCountView.setText(Integer.toString(numberOfTotalMines));
	}

	private void calculateMineCount(int currentRow, int currentCol) {
		int mineCount = 0;
		for (int rowIndex = currentRow - 1; rowIndex <= currentRow + 1; ++rowIndex) {
			if (rowIndex < 0 || rowIndex >= rowCount) {
				continue;
			}

			for (int columnIndex = currentCol - 1; columnIndex <= currentCol + 1; ++columnIndex) {
				if (columnIndex < 0 || columnIndex >= columnCount) {
					continue;
				}

				if (columnIndex == currentCol && rowIndex == currentRow) {
					continue;
				}

				if (mineAreas[rowIndex][columnIndex].isMined() == true) {
					++mineCount;
				}
			}
		}
		mineAreas[currentRow][currentCol].setCountOfSurroundingMines(mineCount);
	}

	private void displayMineGrid() {
		for (int row = 0; row < rowCount; row++) {
			TableRow tableRow = new TableRow(this);

			for (int column = 0; column < columnCount; column++) {
				tableRow.addView(mineAreas[row][column]);
			}
			mineField.addView(tableRow);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mine_field, menu);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
