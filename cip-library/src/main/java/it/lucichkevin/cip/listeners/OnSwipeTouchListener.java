package it.lucichkevin.cip.listeners;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


public abstract class OnSwipeTouchListener implements OnTouchListener {

	private Context context;
	private final GestureDetector gestureDetector;


	public OnSwipeTouchListener(Context context ){
		this.context = context;
		gestureDetector = new GestureDetector( context, new GestureListener());
	}

	@Override
	public boolean onTouch( View view, MotionEvent event ){
		return gestureDetector.onTouchEvent(event);
	}

	private final class GestureListener extends SimpleOnGestureListener {

		private static final int SWIPE_THRESHOLD = 10;
		private static final int SWIPE_VELOCITY_THRESHOLD = 10;

		@Override
		public boolean onDown( MotionEvent e ){
			return true;
		}

		@Override
		public boolean onSingleTapUp( MotionEvent e ){
			super.onSingleTapUp(e);
			return OnSwipeTouchListener.this.onSingleTapUp();
		}

		@Override
		public boolean onFling( MotionEvent e1, MotionEvent e2, float velocityX, float velocityY ){
			try {

				float diffY = e2.getY() - e1.getY();
				float diffX = e2.getX() - e1.getX();

				if( Math.abs(diffX) > Math.abs(diffY) ){
					if( Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD ){
						return (diffX > 0) ? onSwipeRight() : onSwipeLeft();
					}
				}else if( Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD ){
					return (diffY > 0) ? onSwipeBottom() : onSwipeTop();
				}
			}catch( Exception exception ){
				exception.printStackTrace();
			}

			return false;
		}
	}

	protected Context getContext(){
		return context;
	}


	public abstract boolean onSingleTapUp();

	public abstract boolean onSwipeTop();
	public abstract boolean onSwipeRight();
	public abstract boolean onSwipeBottom();
	public abstract boolean onSwipeLeft();

}