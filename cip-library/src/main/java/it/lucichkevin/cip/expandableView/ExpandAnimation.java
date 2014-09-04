package it.lucichkevin.cip.expandableView;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
     @author    Kevin Lucich    29/05/2014
     @version	1.0.0
     @since     Cip v0.1.0
*/
public class ExpandAnimation extends Animation {

    private final int startHeight;
    private final int deltaHeight;
    private final View contentView;

    public ExpandAnimation( int startHeight, int endHeight, final View contentView ){

        this.setFillBefore(false);
        this.setFillAfter(false);
        this.setDuration(500);
//        this.setInterpolator( new Interpolator() {
//            @Override
//            public float getInterpolation( float input ){
//                return input;
//            }
//        });

        this.contentView = contentView;
        this.startHeight = startHeight;
        this.deltaHeight = endHeight - startHeight;

        this.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart( Animation animation ){

            }

            @Override
            public void onAnimationEnd( Animation animation ){
                contentView.clearAnimation();
            }

            @Override
            public void onAnimationRepeat( Animation animation ){

            }
        });

    }

    @Override
    protected void applyTransformation( float interpolatedTime, Transformation transformation ){
        LayoutParams layoutParams = (this.contentView).getLayoutParams();
        layoutParams.height = (int) (this.startHeight + this.deltaHeight*interpolatedTime);
        (this.contentView).setLayoutParams(layoutParams);
    }

}