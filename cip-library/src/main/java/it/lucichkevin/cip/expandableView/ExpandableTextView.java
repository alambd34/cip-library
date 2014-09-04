package it.lucichkevin.cip.expandableView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import it.lucichkevin.cip.R;

/*
public class ExpandableTextView extends TextView {

    private int contentHeight = 0;
    private int collapsedHeight = 0;
    private boolean expanded = false;

    private boolean force_measure_layout = true;
    private TextView contentView = null;

    public ExpandableTextView( Context context ){
        super(context);
    }

    public ExpandableTextView( Context context, AttributeSet attrs ){
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes( attrs, R.styleable.ExpandableTextView, 0, 0 );

        //  How high the contentView should be in "collapsed" state
        collapsedHeight = (int) typedArray.getDimension( R.styleable.ExpandableTextView_collapsedHeight, 50.0f );

        typedArray.recycle();
    }

    public ExpandableTextView( Context context, AttributeSet attrs, int defStyle ){
        super(context, attrs, defStyle);
    }

    //  Set the correct height to the contentView
    public void refreshHeight(){
        int height = (isExpanded() ? getContentHeight() : getCollapsedHeight() );
        setContentHeight( height );
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        this.contentView = (TextView) this;

        (this.contentView).setOnClickListener( new OnClickListener(){
            @Override
            public void onClick( View view ){
                Animation a;
                if( isExpanded() ){
                    a = new ExpandAnimation( getContentHeight(), getCollapsedHeight(), contentView );
                } else {
                    a = new ExpandAnimation( getCollapsedHeight(), getContentHeight(), contentView );
                }

                contentView.startAnimation(a);
                setExpanded( !isExpanded() );
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        refreshHeight();
    }

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ){

        if( this.force_measure_layout ){
            Log.v( "it.lucichkevin.cip.expandableView", "onMeasure" );
            this.force_measure_layout = false;     //  Exlude the StackOverflowError =)
            contentView.measure( widthMeasureSpec, heightMeasureSpec );
            this.contentHeight = contentView.getMeasuredHeight();
        }

        // Then let the usual thing happen
        super.onMeasure( widthMeasureSpec, heightMeasureSpec );
    }



    ///////////////////////////////////////
    //  Getter and Setter


    public boolean isExpanded(){
        return expanded;
    }
    public void setExpanded( Boolean expanded ){
        this.expanded = expanded;
        refreshHeight();
    }

    public int getCollapsedHeight(){
        return this.collapsedHeight;
    }
    public void setCollapsedHeight( int height ){
        this.collapsedHeight = height;
    }

    public int getContentHeight(){
        return this.contentHeight;
    }
    public void setContentHeight( int contentHeight ){
        LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, contentHeight, getResources().getDisplayMetrics());
        contentView.setLayoutParams( layoutParams );
    }

    public boolean isForceMeasureLayout(){
        return force_measure_layout;
    }
    public void setForceMeasureLayout( boolean force_measure_layout ){
        this.force_measure_layout = force_measure_layout;
    }

    public void setText( String text ){
        super.setText( text );
        setForceMeasureLayout(true);
        this.requestLayout();
        this.invalidate();
    }

}
*/


/**
    Create an expandable Textview: expands or compresses clicking it

    <code>
    //
    ExpandableTextView myTextView = ((ExpandableTextView) rootView.findViewById(R.id.my_text_view));
    </code>

    @author     Kevin Lucich    30/05/2014
    @version	1.0.0
    @since      Cip v0.1.0
*/
public class ExpandableTextView extends TextView {

    private static final String APP_TAG = "ExpandableTextView";
    private static final int DEFAULT_TRIM_LENGTH = 200;
    private static final String DOTS = "...";

    private String originalText = null;
    private String trimmedText = null;
    private int trimLength;
    private boolean expanded = false;

    public ExpandableTextView( Context context ){
        this(context, null);
    }

    public ExpandableTextView( Context context, AttributeSet attrs ){
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        this.trimLength = typedArray.getInt(R.styleable.ExpandableTextView_trimLength, DEFAULT_TRIM_LENGTH );
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if( this.originalText == null ){
            this.originalText = getText().toString();
            this.trimmedText = getTrimmedText( this.originalText );

            Log.v( APP_TAG, "--------------------- onLayout() ---------------------" );
            Log.v( APP_TAG, "this.originalText = "+ this.originalText );
            Log.v( APP_TAG, "this.trimmedText = "+ this.trimmedText );
        }

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setExpanded( !isExpanded() );
                setText();
                requestFocusFromTouch();
            }
        });
    }

    ///////////////////////////////////////
    //  Getter and Setter


    public boolean isExpanded(){
        return expanded;
    }
    public void setExpanded( Boolean expanded ){
        this.expanded = expanded;
    }

    private void setOriginalText( String text ){
        this.originalText = text;
    }
    private String getOriginalText(){
        return originalText;
    }

    /**
     *  Return the current trimmed text
     *  @return String
     */
    public String getTrimmedText(){
        return this.trimmedText;
    }

    /**
     *  Return the trimmed text, using the length of trim "trimLength"
     *  @return String
     */
    private String getTrimmedText( String text ){
        if( text.length() < getTrimLength() ){
            return text;
        }
        return (text.subSequence( 0, getTrimLength() )).toString();
    }

    public int getTrimLength() {
        return this.trimLength;
    }
    public void setTrimLength( int trimLength ){
        this.trimLength = trimLength;
        this.trimmedText = getTrimmedText(getOriginalText());
        setText();
    }

    /**
     *  Private method: set the correct text to TextView, if expanded set originalText otherwise "trimmedText + DOTS"
     */
    private void setText(){
        String text = getTrimmedText() +""+ DOTS;
        if( isExpanded() || (originalText.length() <= getTrimLength()) ){
            text = originalText;
        }

        Log.v( APP_TAG, "--------------------- setText() ---------------------" );
        Log.v( APP_TAG, "isExpanded() = "+ String.valueOf(isExpanded()) );
        Log.v( APP_TAG, "text = "+ text );

        super.setText( text );
    }

    public void setText( String text ){
        setOriginalText(text);
        this.trimmedText = getTrimmedText(text);
        setText();
    }

    public void setText( StringBuilder text ){
        Log.v( APP_TAG, "--------------------- setText( StringBuilder text ) ---------------------" );
        Log.v( APP_TAG, "text = "+ text );
        this.setText( text.toString() );
    }

}