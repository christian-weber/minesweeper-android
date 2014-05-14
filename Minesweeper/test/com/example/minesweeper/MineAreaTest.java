package com.example.minesweeper;

import junit.framework.Assert;
import android.graphics.Typeface;
import android.test.AndroidTestCase;

public class MineAreaTest extends AndroidTestCase {

	public void testNewInstantiatedMineAreaShouldBeCleared() {
		MineArea mineArea = new MineArea(getContext());
		
		Assert.assertFalse(mineArea.isMined());
		Assert.assertFalse(mineArea.isFree());
		Assert.assertFalse(mineArea.isMarked());
		Assert.assertEquals(mineArea.getText(), "");
	}
	
	public void testMarkAreaShouldChangeMineAreaDesign() {
		MineArea mineArea = new MineArea(getContext());
		mineArea.mark();
		
		Assert.assertTrue(mineArea.isMarked());
		Assert.assertEquals(mineArea.getText(), "F");
		Assert.assertEquals(mineArea.getTypeface().getStyle(), Typeface.BOLD);
	}
	
	public void testRemoveMarkAreaShouldChangeMineAreaDesign() {
		MineArea mineArea = new MineArea(getContext());
		mineArea.mark();
		mineArea.removeMark();
		
		Assert.assertFalse(mineArea.isMarked());
		Assert.assertEquals(mineArea.getText(), "");
	}
	
	public void testRemoveMarkShouldChangeMineAreaDesignBackToStandard() {
		MineArea mineArea = new MineArea(getContext());
		mineArea.mark();
		mineArea.removeMark();
		
		Assert.assertFalse(mineArea.isMarked());
		Assert.assertEquals(mineArea.getText(), "");
	}
	
	public void testUncoverOnMinedAreaShouldExplode() {
		MineArea mineArea = new MineArea(getContext());
		mineArea.placeMine();
		
		boolean result = mineArea.uncover();
		
		Assert.assertFalse(result);
		Assert.assertEquals(mineArea.getText(), "B");
		Assert.assertEquals(mineArea.getTypeface().getStyle(), Typeface.BOLD);
	}
	
	public void testUncoverOnNotMinedAreaShouldDisplaySurroundingMines() {
		MineArea mineArea = new MineArea(getContext());
		mineArea.setCountOfSurroundingMines(1);
		
		boolean result = mineArea.uncover();
		
		Assert.assertTrue(result);
		Assert.assertEquals(mineArea.getText(), "1");
		Assert.assertTrue(mineArea.isFree());
	}
	
}
