package tw.com.chainsea.bruce;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import tw.com.chainsea.bruce.base.SingleFragmentActivity;
import tw.com.chainsea.bruce.uitls.FilterOnView;

/**
 * title bar style
 * Created by 90Chris on 2014/11/7.
 */
public abstract class TitlebarActivity extends SingleFragmentActivity {
    public abstract Fragment addFragment();

    /**
     * title bar left content, if null, it will be gone
     * @return content, appear at left area; null, the area will be null
     */
    public String leftText() {
        return null;
    }

    public void setLeftText(String content) {
        TextView leftText = (TextView)findViewById(R.id.titlebar_left_text);
        leftText.setText(content);
    }

    public String rightText() {
        return null;
    }

    public String titleText() { return null; }

    public void setTitleText(String content) {
        TextView centerText = (TextView)findViewById(R.id.titlebar_center_text);
        centerText.setText(content);
    }

    public void rightAction() { }
    public void leftAction() {
        finish();
    }

    @Override
    public Fragment createFragment() {
        return addFragment();
    }


    @Override
    public void onActivityCreate() {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setTheme(R.style.BruceTitleBarTheme);
        setContentView(R.layout.bruce_activity_base);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.bruce_titlebar_base);

        /*deal with left area of title bar*/
        TextView leftText = (TextView)findViewById(R.id.titlebar_left_text);
        if (null != leftText()) {
            leftText.setVisibility(View.VISIBLE);
            leftText.setText(leftText());
            FilterOnView.addTouchColorChange(leftText, new FilterOnView.ClickAction() {
                @Override
                public void onClickAction() {
                    leftAction();
                }
            });
        }

        /*deal with right area of title bar*/
        TextView rightText = (TextView)findViewById(R.id.titlebar_right_text);
        if (null != rightText()) {
            rightText.setVisibility(View.VISIBLE);
            rightText.setText(rightText());
            FilterOnView.addTouchColorChange(rightText, new FilterOnView.ClickAction() {
                @Override
                public void onClickAction() {
                    rightAction();
                }
            });
        }

        /*deal with center area of titlebar*/
        TextView centerText = (TextView)findViewById(R.id.titlebar_center_text);
        if (null != titleText()) {
            centerText.setVisibility(View.VISIBLE);
            centerText.setText(titleText());
        }
    }
}
