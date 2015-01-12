package it.lucichkevin.cip.expandableView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import it.lucichkevin.cip.R;

/**
    Create an expandable panel (LinearLayout): expands or compresses clicking the handle View (if the handle is not defined handle will be the panel).
    The contentView is compulsory: will must be a view inside the ExpandablePanel.

    <code>
    ExpandablePanel myPanel = ((ExpandablePanel) rootView.findViewById(R.id.my_panel));
    </code>

    @author     Kevin Lucich    27/05/2014
    @version	1.0.0
    @since      Cip v0.1.0
*/
public class ExpandablePanel extends LinearLayout {

    private int contentHeight = 1100;
    private int collapsedHeight = 1100;
    private boolean expanded = false;

    //  Who clicked
    private int handle_id = 0;

    //  The content animated
    private int contentView_id = 0;
    private View contentView = null;

    private OnClickListener handleOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Animation a;
            if (isExpanded()) {
                a = new ExpandAnimation(contentHeight, collapsedHeight, contentView);
            } else {
                a = new ExpandAnimation(collapsedHeight, contentHeight, contentView);
            }

            contentView.startAnimation(a);
            setExpanded(!isExpanded());
        }
    };

    public ExpandablePanel( Context context ){
        super(context);
    }

    public ExpandablePanel( Context context, AttributeSet attrs ){
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes( attrs, R.styleable.ExpandablePanel, 0, 0 );

        //  How high the contentView should be in "collapsed" state
        collapsedHeight = (int) typedArray.getDimension( R.styleable.ExpandablePanel_collapsedHeight, 50.0f );

        contentView_id = typedArray.getResourceId(R.styleable.ExpandablePanel_content, 0);
        handle_id = typedArray.getResourceId(R.styleable.ExpandablePanel_handle, 0);

        typedArray.recycle();
    }

    public ExpandablePanel( Context context, AttributeSet attrs, int defStyle ){
        super(context, attrs, defStyle);
    }

    public boolean isExpanded(){
        return expanded;
    }

    public void setExpanded( Boolean expanded ){
        this.expanded = expanded;
        refreshHeight();
    }

    //  Set the correct heigth to the contentView
    public void refreshHeight(){

        int height = (isExpanded() ? contentHeight : collapsedHeight);

        LayoutParams layoutParams = (LayoutParams) contentView.getLayoutParams();
        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics());
        contentView.setLayoutParams(layoutParams);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        contentView = findViewById(contentView_id);

        if( contentView != null ){
            refreshHeight();
        }else{
            throw new IllegalArgumentException("The content attribute is required and must refer to a valid child.");
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        View handle = null;
        if( handle_id == 0 ){
            handle = this;
        }else{
            handle = findViewById(handle_id);
        }

        handle.setOnClickListener(handleOnClickListener);
    }

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ){

        if( contentHeight == 0 ){
            contentHeight = contentView.getMeasuredHeight();
        }

        // Then let the usual thing happen
        super.onMeasure( widthMeasureSpec, heightMeasureSpec );
    }

}
