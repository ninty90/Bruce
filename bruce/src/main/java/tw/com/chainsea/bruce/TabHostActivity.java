package tw.com.chainsea.bruce;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.Map;

/**
 * MultiFragmentActivity
 * Created by 90Chris on 2015/1/14.
 */
public abstract class TabHostActivity extends FragmentActivity {

    public class TabsInfo {
        Class fragment;
        String name;
        int drawable;
        int rightDrawable;
        OnRightListener rightListener;

        public TabsInfo( String name, int drawable, Class fragment ) {
            this.fragment = fragment;
            this.name = name;
            this.drawable = drawable;
        }

        public TabsInfo( String name, int drawable, Class fragment, int rightDrawable, OnRightListener rightListener) {
            this.fragment = fragment;
            this.name = name;
            this.drawable = drawable;
            this.rightDrawable = rightDrawable;
            this.rightListener = rightListener;
        }
    }

    public interface OnRightListener {
        void onClick();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //http://stackoverflow.com/questions/7469082/getting-exception-illegalstateexception-can-not-perform-this-action-after-onsa
        //super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bruce_activity_tabs);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.bruce_titlebar_tab);

        FragmentTabHost mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabs_content);
        mTabHost.setBackgroundResource(R.drawable.bruce_tabhost_bg);
        final Map<String, TabsInfo> tabsMap = tabsInfoMap();
        for ( String key : tabsMap.keySet()) {
            View view = LayoutInflater.from(this).inflate(R.layout.bruce_tab_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.tab_item_icon);
            imageView.setImageResource(tabsMap.get(key).drawable);
            TextView textView = (TextView) view.findViewById(R.id.tab_item_name);
            textView.setText(tabsMap.get(key).name);
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(key).setIndicator(view);
            mTabHost.addTab(tabSpec, tabsMap.get(key).fragment, null);
        }

        final TextView tvTitle = (TextView)findViewById(R.id.tab_title_layout_center);
        final ImageView ivRight = (ImageView)findViewById(R.id.tab_title_layout_right);
        tvTitle.setText(tabsMap.get(tabsMap.keySet().iterator().next()).name);
        setRightTitleView(tabsMap, tabsMap.keySet().iterator().next(), ivRight);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(final String s) {
                TabsInfo info = tabsMap.get(s);
                tvTitle.setText(info.name);
                setRightTitleView(tabsMap, s, ivRight);
            }
        });
    }

    public abstract Map<String, TabsInfo> tabsInfoMap();

    /**
     * deal with right view of titlebar
     * @param tabsMap map of tabs
     * @param tag key
     * @param imageView layout
     */
    private void setRightTitleView(Map<String, TabsInfo> tabsMap, String tag, ImageView imageView) {
        final int imageId = tabsMap.get(tag).rightDrawable;
        imageView.setVisibility(View.VISIBLE);
        if ( imageId != 0) {
            final OnRightListener listener = tabsMap.get(tag).rightListener;
            imageView.setImageResource(imageId);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick();
                }
            });
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    public ImageView getRightView() {
        return (ImageView)findViewById(R.id.tab_title_layout_right);
    }
}
