package cn.isif.chartview.view.animation.easing.quint;


import cn.isif.chartview.view.animation.easing.BaseEasingMethod;

public class QuintEaseOut implements BaseEasingMethod {


	public QuintEaseOut() {
        super();
    }

    @Override
    public float next(float time) {
        return (float) Math.pow(time - 1, 5) + 1;
    }
}
