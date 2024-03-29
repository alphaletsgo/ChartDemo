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

package cn.isif.chartview.view;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;

import torrap.com.chartdemo.R;
import cn.isif.chartview.model.Bar;
import cn.isif.chartview.model.BarSet;
import cn.isif.chartview.model.ChartSet;

/**
 * Implements a bar chart extending {@link ChartView}
 */
public class BarChartView extends ChartView {
	
	
	/** Style applied to Graph */
	private Style mStyle;

	
	/** Bar width */
	private float mBarWidth;
	
	
	/** Offset to control bar positions **/
	/** Added due to multiset charts **/
	private float mDrawingOffset;
	
	
	
	public BarChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mStyle = new Style(context.getTheme()
				.obtainStyledAttributes(attrs, R.styleable.ChartAttrs, 0, 0));
	}
	
	
	public BarChartView(Context context) {
		super(context);
		mStyle = new Style();
	}
	
	
	
	@Override
	public void onAttachedToWindow(){
		super.onAttachedToWindow();
		mStyle.init();
	}
	
	
	@Override
	public void onDetachedFromWindow(){
		super.onDetachedFromWindow();
		mStyle.clean();
	}
	
	
	
	/**
	 * Method responsible to draw bars with the parsed screen points.
	 * @param canvas
	 *   The canvas to draw on.
	 * @param data
	 *   The parsed screen points ready to be used/drawn.
	 */

	public void onDrawChart(Canvas canvas, ArrayList<ChartSet> data) {
		
		for (int i = 0; i < data.get(0).size(); i++) {
			
			// Set first offset to draw a group of bars
			float drawingOffset = data.get(0).getEntry(i).getX() - mDrawingOffset;
			
			for(int j = 0; j < data.size(); j++){
				
				final BarSet barSet = (BarSet) data.get(j);
				mStyle.mBarPaint.setColor(barSet.getColor());
				Bar bar = (Bar) barSet.getEntry(i);
				mStyle.mBarPaint.setColor(bar.getColor());
				
				canvas.drawRect(new Rect((int) drawingOffset, 
									(int) bar.getY(), 
										(int) (drawingOffset += mBarWidth),
											(int) this.getInnerChartBottom()), 
									mStyle.mBarPaint);
				
				// If last bar of group no set spacing is necessary
				if(j != data.size()-1)
					drawingOffset += mStyle.mSetSpacing;
			}	
		}
	}
	
	
	
	/**
	 * Calculates Bar width based on the distance of two horizontal labels
	 * @param n of sets
	 * @param x0
	 * @param x1
	 */
	private void calculateBarsWidth(int n, float x0, float x1) {
		mBarWidth = ((x1 - x0) - mStyle.mBarSpacing/2 - mStyle.mSetSpacing*(n-1))/n;
	}

	
	/**
	 * Having calculated previously bar width it gives the offset to know 
	 * where to start drawing the first bar of each group.
	 * @param n - Number of sets
	 */
	private void calculatePositionOffset(int n){
		if(n % 2 == 0){
			mDrawingOffset = n*mBarWidth/2 + (n-1)*(mStyle.mSetSpacing/2);
		}else{
			mDrawingOffset = n*mBarWidth/2 + ((n-1)/2)*mStyle.mSetSpacing;
		}
	}
	
	

	public ArrayList<ArrayList<Region>> defineRegions(ArrayList<ChartSet> data) {
		
		// Doing calculations here to avoid doing several times while drawing
		// in case of animation
		calculateBarsWidth(data.size(), data.get(0).getEntry(0).getX(), 
							data.get(0).getEntry(1).getX());
		calculatePositionOffset(data.size());
		
		// Define regions
		
		final ArrayList<ArrayList<Region>> result = new ArrayList<ArrayList<Region>>();
		for(int i = 0; i < data.size(); i++){
			result.add(new ArrayList<Region>());
		}
		
		for (int i = 0; i < data.get(0).size(); i++) {
			
			// Set first offset to draw a group of bars
			float drawingOffset = data.get(0).getEntry(i).getX() - mDrawingOffset;
			
			for(int j = 0; j < data.size(); j++){
				
				final BarSet barSet = (BarSet) data.get(j);
				Bar bar = (Bar) barSet.getEntry(i);
				
				result.get(j).add(new Region((int) drawingOffset, 
									(int) bar.getY(), 
										(int) (drawingOffset += mBarWidth), 
											(int)(this.getInnerChartBottom())));
				
				// If last bar of group no set spacing is necessary
				if(j != data.size()-1)
					drawingOffset += mStyle.mSetSpacing;
			}	
		}
		
		return result;
	}
	
	
	
	/*
	 * Setters
	 * 
	 */
	
	public void setBarSpacing(float spacing){
		mStyle.mBarSpacing = spacing;
	}
	public void setSetSpacing(float spacing){
		mStyle.mSetSpacing = spacing;
	}
	
	
	/** 
	 * Keeps the style to be applied to the BarChart.
	 *
	 */
	public class Style{
		
		/** Bars fill variables */
		private Paint mBarPaint;
		
		
		/** Spacing between bars */
		private float mBarSpacing;
		private float mSetSpacing;
		
		
		/** Shadow related variables */
		private final float mShadowRadius;
		private final float mShadowDx;
		private final float mShadowDy;
		private final int mShadowColor;
		
		
	    protected Style() {
	    	
	    	mBarSpacing = (float) getResources().getDimension(R.dimen.bar_spacing);
	    	mSetSpacing = (float) getResources().getDimension(R.dimen.set_spacing);
	    	
	    	mShadowRadius = getResources().getDimension(R.dimen.shadow_radius);
	    	mShadowDx = getResources().getDimension(R.dimen.shadow_dx);
	    	mShadowDy = getResources().getDimension(R.dimen.shadow_dy);
	    	mShadowColor = 0;
	    }
	    
	    protected Style(TypedArray attrs) {
	    	
	    	mBarSpacing = attrs.getDimension(
	    			R.styleable.BarChartAttrs_chart_barSpacing, 
	    				getResources().getDimension(R.dimen.bar_spacing));
	    	mSetSpacing = attrs.getDimension(
	    			R.styleable.BarChartAttrs_chart_barSpacing, 
	    				getResources().getDimension(R.dimen.set_spacing));
	    	
	    	mShadowRadius = attrs.getDimension(
	    			R.styleable.ChartAttrs_chart_shadowRadius, 
	    				getResources().getDimension(R.dimen.shadow_radius));
	    	mShadowDx = attrs.getDimension(
	    			R.styleable.ChartAttrs_chart_shadowDx, 
	    				getResources().getDimension(R.dimen.shadow_dx));
	    	mShadowDy = attrs.getDimension(
	    			R.styleable.ChartAttrs_chart_shadowDy, 
	    				getResources().getDimension(R.dimen.shadow_dy));
	    	mShadowColor = attrs.getColor(
	    			R.styleable.ChartAttrs_chart_shadowColor, 0);
	    }	
	    
	    
		private void init(){
	    	
	    	mBarPaint = new Paint();
	    	mBarPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	    	mBarPaint.setShadowLayer(mShadowRadius, mShadowDx, 
	    								mShadowDy, mShadowColor);
	    }
	    
		
	    private void clean(){
	    	
	    	mBarPaint = null;
	    }
	    
	}

}
