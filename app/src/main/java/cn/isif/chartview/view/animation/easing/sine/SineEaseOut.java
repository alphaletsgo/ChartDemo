package cn.isif.chartview.view.animation.easing.sine;


import cn.isif.chartview.view.animation.easing.BaseEasingMethod;

public class SineEaseOut implements BaseEasingMethod {
    public SineEaseOut() {
        super();
    }

    @Override
    public float next(float time) {
        return (float)Math.sin(time * (Math.PI/2));
    }
}
