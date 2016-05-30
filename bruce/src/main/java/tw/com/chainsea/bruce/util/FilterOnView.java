package tw.com.chainsea.bruce.util;

import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * set filter on view after being touched
 * @author 90Chris
 */
public class FilterOnView {

    /**
     * add touch effect on view. 1, image: become darker after being touched; 2, textview: become transparent after being touched
     * @param view view
     * @param clickAction action after view being clicked
     */
    public static void addTouchColorChange(View view, final ClickAction clickAction){
        view.setOnTouchListener( VIEW_TOUCH_DARK ) ;

        if(null != clickAction){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickAction.onClickAction();
                }
            });
        }
    }


    private static final OnTouchListener VIEW_TOUCH_DARK = new OnTouchListener() {
        /*set transparent level, number is smaller and it is more transparent*/
        public int transparent_level = 150;

        public final float[] DARKER = new float[] { 1, 0, 0, 0, -50, 0, 1,
            0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };
        public final float[] RESTORE = new float[] { 1, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };
        public final float[] TRANSPARENT = new float[] { 1, 0, 0, 0, 0,
                                                         0, 1, 0, 0, 0,
                                                         0, 0, 1, 0, 0,
                                                         0, 0, 0, 1, transparent_level - 255
        };
        int r, g, b;
        float x = 0, y = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                if(v instanceof ImageView){
                    ImageView iv = (ImageView) v;
                    iv.setColorFilter(new ColorMatrixColorFilter(DARKER)) ;
                } else if (v instanceof TextView){
                    TextView tv = (TextView) v;
                    String tvColor = Integer.toHexString(tv.getCurrentTextColor());
                    b = Integer.parseInt(tvColor.substring(6, 8), 16);
                    g = Integer.parseInt(tvColor.substring(4, 6), 16);
                    r = Integer.parseInt(tvColor.substring(2, 4), 16);
                    tv.setTextColor(Color.argb(transparent_level, r, g, b));
                    Drawable[] drawables = tv.getCompoundDrawables();
                    if(null != drawables[0]) {
                        drawables[0].setColorFilter(new ColorMatrixColorFilter(TRANSPARENT));
                    }
                } else{
                    v.getBackground().setColorFilter( new ColorMatrixColorFilter(DARKER) );
                    v.setBackgroundDrawable(v.getBackground());
                }
                break;
            case MotionEvent.ACTION_UP:
                if(v instanceof ImageView){
                    ImageView iv = (ImageView) v;
                    iv.setColorFilter( new ColorMatrixColorFilter(RESTORE) ) ;
                } else if (v instanceof TextView){
                    TextView tv = (TextView) v;
                    tv.setTextColor(Color.argb(255, r, g, b));
                    Drawable[] drawables = tv.getCompoundDrawables();
                    if(null != drawables[0]) {
                        drawables[0].setColorFilter(new ColorMatrixColorFilter(RESTORE));
                    }
                } else{
                    v.getBackground().setColorFilter(new ColorMatrixColorFilter(RESTORE));
                    v.setBackgroundDrawable(v.getBackground());
                }
                break;
            default:
                if(v instanceof ImageView){
                    ImageView iv = (ImageView) v;
                    iv.setColorFilter( new ColorMatrixColorFilter(RESTORE)) ;
                } else if ( !(v instanceof TextView) ) {
                    v.getBackground().setColorFilter(new ColorMatrixColorFilter(RESTORE));
                    v.setBackgroundDrawable(v.getBackground());
                }
                break;
            }
            return false;
        }
    };

    public static interface ClickAction{
        public void onClickAction();
    }
}  