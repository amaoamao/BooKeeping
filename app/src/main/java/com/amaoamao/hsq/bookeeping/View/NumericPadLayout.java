package com.amaoamao.hsq.bookeeping.View;

/**
 * Created by mao on 17-2-28.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.view.View;

import com.amaoamao.hsq.bookeeping.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class NumericPadLayout extends PadLayout {
    public NumericPadLayout(Context context) {
        this(context, null);
    }

    public NumericPadLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumericPadLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        Locale locale = getResources().getConfiguration().locale;
        final DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
        final char zeroDigit = symbols.getZeroDigit();
        for (int childIndex = getChildCount() - 1; childIndex >= 0; --childIndex) {
            final View v = getChildAt(childIndex);
            if (v instanceof Button) {
                final Button b = (Button) v;
                switch (b.getId()) {
                    case R.id.digit_0:
                        b.setText(String.valueOf(zeroDigit));
                        break;
                    case R.id.digit_1:
                        b.setText(String.valueOf((char) (zeroDigit + 1)));
                        break;
                    case R.id.digit_2:
                        b.setText(String.valueOf((char) (zeroDigit + 2)));
                        break;
                    case R.id.digit_3:
                        b.setText(String.valueOf((char) (zeroDigit + 3)));
                        break;
                    case R.id.digit_4:
                        b.setText(String.valueOf((char) (zeroDigit + 4)));
                        break;
                    case R.id.digit_5:
                        b.setText(String.valueOf((char) (zeroDigit + 5)));
                        break;
                    case R.id.digit_6:
                        b.setText(String.valueOf((char) (zeroDigit + 6)));
                        break;
                    case R.id.digit_7:
                        b.setText(String.valueOf((char) (zeroDigit + 7)));
                        break;
                    case R.id.digit_8:
                        b.setText(String.valueOf((char) (zeroDigit + 8)));
                        break;
                    case R.id.digit_9:
                        b.setText(String.valueOf((char) (zeroDigit + 9)));
                        break;
                    case R.id.dec_point:
                        b.setText(String.valueOf(symbols.getDecimalSeparator()));
                        break;
                }
            }
        }
    }
}