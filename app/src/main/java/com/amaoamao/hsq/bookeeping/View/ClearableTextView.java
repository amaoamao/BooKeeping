package com.amaoamao.hsq.bookeeping.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.amaoamao.hsq.bookeeping.R;

/**
 * Created by mao on 17-2-28.
 */

public class ClearableTextView extends TextView {
    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;
    private Drawable mClearDrawable;

    public ClearableTextView(Context context) {
        super(context);

    }

    public ClearableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ClearableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width())) {
                    if (!((Double) Double.parseDouble(getText().toString())).equals(0.0))
                        setText(getText().toString().substring(0, getText().length() - 1));
                    if (getText().toString().isEmpty())
                        setText(getContext().getString(R.string.zero));
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}