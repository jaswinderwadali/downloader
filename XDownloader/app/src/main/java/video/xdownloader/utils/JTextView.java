package video.xdownloader.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import video.xdownloader.R;

/**
 * Created by jaswinderwadali on 28/07/17.
 */

public class JTextView extends AppCompatTextView {
    public JTextView(Context context) {
        super(context);
        setTextColor(ContextCompat.getColor(context,R.color.colorText));
        setTypeface(Typeface.SERIF);
//        Utils.setDrawerFont(this);

    }

    public JTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextColor(ContextCompat.getColor(context,R.color.colorText));
        setTypeface(Typeface.SERIF);

//        Utils.setDrawerFont(this);
    }

    public JTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTextColor(ContextCompat.getColor(context,R.color.colorText));
        setTypeface(Typeface.SERIF);

//        Utils.setDrawerFont(this);
    }
}
