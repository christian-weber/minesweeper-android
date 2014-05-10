package com.example.minesweeper_poelzl_weber;

import java.util.Random;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MineField extends ActionBarActivity {
	
	private int _columnCount = 5;
	private int _rowCount = 8;
	private MineArea _mineAreas[][];
	private TableLayout _mineField;
	private TextView _mineCountView;
	private int _numberOfTotalMines = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_field);

        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
        _mineField = (TableLayout)findViewById(R.id.Mine_Field);
        _mineCountView = (TextView)findViewById(R.id.CountMinesIdentified);
        CreateMineGrid();
        PlaceMines();
        DisplayMineGrid();
        
        ImageButton newButton = (ImageButton)findViewById(R.id.NewGame);
        newButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
            	RebuildMineGrid();
                PlaceMines();                
                _mineField.setBackgroundResource(R.layout.cellshape);
            }
        });
    }
    
    
    private void CreateMineGrid()
    {
    	_mineAreas = new MineArea[_rowCount][_columnCount];
    	for (int rowIndex = 0; rowIndex < _rowCount; ++rowIndex)
    	{
       		for (int columnIndex = 0; columnIndex < _columnCount; ++columnIndex)
    		{
    			_mineAreas[rowIndex][columnIndex] = new MineArea(this);    			
    			//_mineAreas[rowIndex][columnIndex].setText(Integer.toString(rowIndex * 100 + columnIndex));
    			
    			AreaClickListener listener = new AreaClickListener(rowIndex, columnIndex);
    			_mineAreas[rowIndex][columnIndex].setOnClickListener(listener);
    			
    			AreaLongClickListener longListener = new AreaLongClickListener(rowIndex, columnIndex);
    			_mineAreas[rowIndex][columnIndex].setOnLongClickListener(longListener);
    		}
    	}
    }
    
    private void RebuildMineGrid()
    {
    	for (int rowIndex = 0; rowIndex < _rowCount; ++rowIndex)
    	{
       		for (int columnIndex = 0; columnIndex < _columnCount; ++columnIndex)
    		{
    			_mineAreas[rowIndex][columnIndex].ClearArea();
    		}
    	}
    }
    
    private void PlaceMines()
    {
    	Random rand = new Random();
    	   	
    	int rowIndex = 0;
    	int columnIndex = 0;
    	
    	for (int i = 0; i < _numberOfTotalMines; ++i)
    	{
    		while ( ( rowIndex == 0 && columnIndex == 0) || _mineAreas[rowIndex][columnIndex].IsMined() == true)
    		{
    			rowIndex = rand.nextInt(_rowCount);
    			columnIndex = rand.nextInt(_columnCount);
    			
    		}
    		_mineAreas[rowIndex][columnIndex].PlaceMine();
    	}
    	
    	for (rowIndex = 0; rowIndex < _rowCount; ++rowIndex)
    	{
    		for (columnIndex = 0; columnIndex < _columnCount; ++columnIndex)
    		{
    			CalculateMineCount(rowIndex, columnIndex);
    		}
    	}
    	
    	_mineCountView.setText(Integer.toString(_numberOfTotalMines));
    }
    
    private void CalculateMineCount(int currentRow, int currentCol)
    {
    	int mineCount = 0;
    	for (int rowIndex = currentRow - 1; rowIndex <= currentRow + 1; ++rowIndex)
    	{
    		if (rowIndex < 0 || rowIndex >= _rowCount)
			{
				continue;
			}
    		
    		for (int columnIndex = currentCol - 1; columnIndex <= currentCol + 1; ++columnIndex)
    		{
    			if (columnIndex < 0 || columnIndex >= _columnCount)
    			{
    				continue;
    			}
    			
    			if (columnIndex == currentCol && rowIndex == currentRow)
    			{
    				continue;
    			}
    			
    			if (_mineAreas[rowIndex][columnIndex].IsMined() == true)
    			{
    				++mineCount;
    			}
    		}
    	}
    	_mineAreas[currentRow][currentCol].SetCountOfSurroundingMines(mineCount);
    }
    
    private void DisplayMineGrid()
    {    	
    	for (int row = 0; row < _rowCount; row++)
        {
          TableRow tableRow = new TableRow(this);
     
          for (int column = 0; column < _columnCount; column++)
          {
        	  //_mineAreas[row][column].setLayoutParams(new LayoutParams(25 + 2 * 2, 25 + 2 * 2));
        	  tableRow.addView(_mineAreas[row][column]);
          }
          _mineField.addView(tableRow); 
        }
    }
    
    public void UncoverSurrounding(int currentRowIndex, int currentColIndex)
    {
    	for (int rowIndex = currentRowIndex - 1; rowIndex <= currentRowIndex + 1; ++rowIndex)
    	{
    		if (rowIndex < 0 || rowIndex >= _rowCount)
			{
				continue;
			}
    		
    		for (int colIndex = currentColIndex - 1; colIndex <= currentColIndex + 1; ++colIndex)
    		{ 
    			if (colIndex < 0 || colIndex >= _columnCount)
    			{
    				continue;
    			}
    			
		    	if (_mineAreas[rowIndex][colIndex].IsFree() == true ||
		    		_mineAreas[rowIndex][colIndex].IsMined() == true ||
		    		_mineAreas[rowIndex][colIndex].IsMarked() == true)
		    	{
		    		continue;
		    	}
		    	
		    	_mineAreas[rowIndex][colIndex].UncoverArea();
		    	
		    	if (_mineAreas[rowIndex][colIndex].GetCountSurroundingMines() == 0)
		    	{
		    		UncoverSurrounding(rowIndex, colIndex);
		    	}
    		}
    	}
    }
    
    public class AreaClickListener extends Activity implements OnClickListener
    {
    	int _rowIndex;
    	int _colIndex;

    	
    	public AreaClickListener(int rowIndex, int colIndex)
    	{
    		super();
    		_rowIndex = rowIndex;
    		_colIndex = colIndex;
    	}
    	

    	@Override
    	public void onClick(View v)
    	{
    		if (_mineAreas[_rowIndex][_colIndex].IsFree() == true || _mineAreas[_rowIndex][_colIndex].IsMarked() == true)
    		{
    			return;
    		}
    		if (_mineAreas[_rowIndex][_colIndex].UncoverArea() == true)
    		{
    			if (_mineAreas[_rowIndex][_colIndex].GetCountSurroundingMines() == 0)
    			{
    				UncoverSurrounding(_rowIndex, _colIndex);
    			}
    		}
    		else
    		{
    			// TODO: End game
    		}
    	}
    }

    
    public class AreaLongClickListener extends Activity implements OnLongClickListener
    {
    	int _rowIndex;
    	int _colIndex;
    	
    	public AreaLongClickListener(int rowIndex, int colIndex)
    	{
    		super();
    		_rowIndex = rowIndex;
    		_colIndex = colIndex;
    	}
    	

    	@Override
    	public boolean onLongClick(View v)
    	{
    		int newMineCount = Integer.parseInt(_mineCountView.getText().toString());
    		if (_mineAreas[_rowIndex][_colIndex].IsFree() == true)
    		{
    			return true;
    		}
    		
    		if (_mineAreas[_rowIndex][_colIndex].IsMarked() == true)
    		{
    			_mineAreas[_rowIndex][_colIndex].RemoveMark();
    			++newMineCount;
    		}
    		else
    		{
    			_mineAreas[_rowIndex][_colIndex].MarkArea();
    			--newMineCount;
    		}
    		_mineCountView.setText(Integer.toString(newMineCount));
    		return true;
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mine_field, menu);
        return true;
    }

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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_mine_field, container, false);
            return rootView;
        }
    }
}
