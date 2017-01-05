package tw.com.chainsea.bruce;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tw.com.chainsea.bruce.base.SingleFragmentActivity;
import tw.com.chainsea.bruce.util.ActivityController;
import tw.com.chainsea.bruce.util.FilterOnView;


/**
 * 继承此Activity，会显示Title区域
 * Created by 90Chris on 2014/11/7.
 */
public abstract class TitlebarActivity extends SingleFragmentActivity {
    public abstract Fragment addFragment();

    /**
     * 标题栏目的左边内容，会包含一个返回箭头
     * @return content 如果未实现，则该区域不存在
     */
    public String leftText() {
        return null;
    }

    public void setLeftText(String content) {
        TextView leftText = (TextView)findViewById(R.id.titlebar_left_text);
        assert leftText != null;
        leftText.setText(content);
    }

    public View rightView() {
        return null;
    }

    public String titleText() { return null; }

    public void setTitleText(String content) {
        TextView centerText = (TextView)findViewById(R.id.titlebar_center_text);
        assert centerText != null;
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
    protected void setFeature() {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    }

    @Override
    public void onActivityCreate() {
        setContentView(R.layout.bruce_activity_base);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.bruce_titlebar_base);
        ActivityController.addActivity(this);
        if (null != leftText()) {
            TextView leftText = (TextView)findViewById(R.id.titlebar_left_text);
            assert leftText != null;
            leftText.setVisibility(View.VISIBLE);
            leftText.setText(leftText());
            FilterOnView.addTouchColorChange(leftText, new FilterOnView.ClickAction() {
                @Override
                public void onClickAction() {
                    leftAction();
                }
            });
        }

        if (null != rightView()) {
            RelativeLayout rightText = (RelativeLayout)findViewById(R.id.titlebar_right_text);
            assert rightText != null;
            rightText.setVisibility(View.VISIBLE);
            rightText.addView(rightView());
            rightText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightAction();
                }
            });
        }

        if (null != titleText()) {
            TextView centerText = (TextView)findViewById(R.id.titlebar_center_text);
            assert centerText != null;
            centerText.setVisibility(View.VISIBLE);
            centerText.setText(titleText());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}
