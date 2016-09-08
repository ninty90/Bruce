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

import java.util.ArrayList;
import java.util.List;

/**
 * TabHostNoTitleActivity
 * Created by 90Chris on 2015/1/14.
 */
public abstract class TabHostNoTitleActivity extends FragmentActivity{

    List<View> mViewList = new ArrayList<>();

    public class TabsInfo {
        Class fragment;
        String tag;
        String name;
        int drawable;
        boolean isChecked;

        public TabsInfo(String tag, String name, int drawable, Class fragment, boolean checked) {
            this.fragment = fragment;
            this.tag = tag;
            this.name = name;
            this.drawable = drawable;
            this.isChecked = checked;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //http://stackoverflow.com/questions/7469082/getting-exception-illegalstateexception-can-not-perform-this-action-after-onsa
        //super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bruce_activity_tabs);

        FragmentTabHost mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabs_content);
        mTabHost.getTabWidget().setDividerDrawable(R.color.bruce_transparent);   //divider 需要在加入tab前设置
        mTabHost.setBackgroundResource(R.drawable.bruce_tabhost_bg);
        for ( int i = 0; i < allTabsInfo().size(); ++i) {
            View view = LayoutInflater.from(this).inflate(R.layout.bruce_tab_item, null);
            mViewList.add(view);
            ImageView imageView = (ImageView) view.findViewById(R.id.tab_item_icon);
            imageView.setImageResource(allTabsInfo().get(i).drawable);
            TextView textView = (TextView) view.findViewById(R.id.tab_item_name);
            textView.setText(allTabsInfo().get(i).name);
            if ( allTabsInfo().get(i).isChecked ) {
                view.findViewById(R.id.tab_item_checked).setVisibility(View.VISIBLE);
            }

            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(allTabsInfo().get(i).tag).setIndicator(view);
            mTabHost.addTab(tabSpec, allTabsInfo().get(i).fragment, null);
        }

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if ( tabId.equals(allTabsInfo().get(1).tag)) {
                    mViewList.get(1).findViewById(R.id.tab_item_checked).setVisibility(View.GONE);
                }
            }
        });
    }

    public void displayActivityNotify(boolean display) {
        if ( display ) {
            mViewList.get(1).findViewById(R.id.tab_item_checked).setVisibility(View.VISIBLE);
        } else {
            mViewList.get(1).findViewById(R.id.tab_item_checked).setVisibility(View.GONE);
        }
    }

    public abstract List<TabsInfo> allTabsInfo();
}
