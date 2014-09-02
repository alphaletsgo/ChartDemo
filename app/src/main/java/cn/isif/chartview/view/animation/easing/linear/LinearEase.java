package cn.isif.chartview.view.animation.easing.linear;


import cn.isif.chartview.view.animation.easing.BaseEasingMethod;

public class LinearEase implements BaseEasingMethod {

	@Override
	public float next(float normalizedTime) {
		return normalizedTime;
	}
	
}
