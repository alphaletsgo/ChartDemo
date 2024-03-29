/*
 * Copyright 2014 Diogo Bernardino
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.isif.chartview.model;

import android.util.Log;

import cn.isif.chartview.Tools;
import cn.isif.chartview.exception.ChartException;


/**
 * Data model containing a set of {@link Point} to be used by {@link LineChartView}.
 */
public class LineSet extends ChartSet{

	private static final String TAG = "com.db.chart.model.ChartSet";

	
	/** default style */
	private static final float LINE_THICKNESS = 4;
	private static final float DOTS_THICKNESS = 4;
	private static final float DOTS_RADIUS = 1;
	private static final int DEFAULT_COLOR = -16777216;
	
	
	/** line variables */
	private float mLineThickness;
	private int mLineColor;

	
	/** dot variables */
	private boolean mHasDots;
	private int mDotsColor;
	private float mDotsRadius;
	private boolean mHasDotsStroke;
	private float mDotsStrokeThickness;
	private int mDotsStrokeColor;
	
	
	/** line type */
	private boolean mIsDashed;
	private boolean mIsSmooth;
	
	/** background fill variables */
	private boolean mHasFill;
	private int mFillColor;
	
	
	
	public LineSet(){
		super();
		
		//Set defaults
		mLineThickness = Tools.fromDpToPx(LINE_THICKNESS);
		mLineColor = DEFAULT_COLOR;
		
		mHasDots = false;
		mDotsColor = DEFAULT_COLOR;
		mDotsRadius = Tools.fromDpToPx(DOTS_THICKNESS);
		
		mHasDotsStroke = false;
		mDotsStrokeThickness = Tools.fromDpToPx(DOTS_RADIUS);
		mDotsStrokeColor = DEFAULT_COLOR;
		
		mIsDashed = false;
		mIsSmooth = false;
		
		mHasFill = false;
		mFillColor = DEFAULT_COLOR;
		
	}

	
	
	public void addPoint(String label, float value){
		this.addPoint(new Point(label, value));
	}
	
	
	public void addPoint(Point point){
		this.addEntry(point);
	}
	
	
	
	public boolean hasDots() {
		return mHasDots;
	}

	public boolean hasDotsStroke() {
		return mHasDotsStroke;
	}
	
	
	
	public boolean isDashed() {
		return mIsDashed;
	}
	
	
	public boolean isSmooth() {
		return mIsSmooth;
	}

	
	
	/*
	 *Getters
	 *
	 */
	
	
	public float getDotsStrokeThickness() {
		return mDotsStrokeThickness;
	}

	
	public float getLineThickness() {
		return mLineThickness;
	}

	
	public int getLineColor() {
		return mLineColor;
	}

	
	public int getDotsColor() {
		return mDotsColor;
	}

	
	public float getDotsRadius() {
		return mDotsRadius;
	}

	
	public int getDotsStrokeColor() {
		return mDotsStrokeColor;
	}
	
	
	public int getFillColor() {
		return mFillColor;
	}
	
	
	public boolean hasFill() {
		return mHasFill;
	}
	
	
	
	/*
	 * Setters
	 * 
	 */

	
	public LineSet setDashed(boolean bool) {
		mIsDashed = bool;
		return this;
	}

	
	public LineSet setSmooth(boolean bool) {
		mIsSmooth = bool;
		return this;
	}

	
	/**
	 * Defines the thickness to be used when drawing the line. 
	 * @param thickness - Line thickness. Can't be equal or less than 0.
	 */
	public LineSet setLineThickness(float thickness) {
		try{
			if(thickness <= 0)
				throw new ChartException("Line thickness <= 0.");
		}catch(ChartException e){
			Log.e(TAG, "", e);
			System.exit(1);
		}
		mLineThickness = thickness;
		return this;
	}
	
	
	public LineSet setLineColor(int color){
		mLineColor = color;
		return this;
	}

	
	public LineSet setDots(boolean bool){
		mHasDots = bool;
		return this;
	}

	
	public LineSet setDotsColor(int color){
		mDotsColor = color;
		return this;
	}

	
	public LineSet setDotsRadius(float radius){
		mDotsRadius = radius;
		return this;
	}

	/**
	 * @param thickness - grid thickness. Can't be equal or less than 0.
	 */
	public LineSet setDotsStrokeThickness(float thickness){
		try{
			if(thickness <= 0)
				throw new ChartException("Grid thickness <= 0.");
		}catch(ChartException e){
			Log.e(TAG, "", e);
			System.exit(1);
		}
		mHasDotsStroke = true;
		mDotsStrokeThickness = thickness;
		return this;
	}

	
	public LineSet setDotsStrokeColor(int color){
		mDotsStrokeColor = color;
		return this;
	}
	
	
	public LineSet setLineDashed(boolean bool){
		mIsDashed = bool;
		return this;
	}
	
	
	public LineSet setLineSmooth(boolean bool){
		mIsSmooth = bool;
		return this;
	}
	
	
	public LineSet setFillColor(int color){
		mFillColor = color;
		return this;
	}
	
	
	public LineSet setFill(boolean bool){
		mHasFill = bool;
		return this;
	}
	
}
