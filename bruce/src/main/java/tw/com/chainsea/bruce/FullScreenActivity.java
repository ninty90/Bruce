package tw.com.chainsea.bruce;

import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

import tw.com.chainsea.bruce.base.SingleFragmentActivity;


/**
 * Activity title bar
 * Created by Chris on 2014/9/11.
 */
public  abstract class FullScreenActivity extends SingleFragmentActivity {

    public abstract Fragment addFragment();

    @Override
    public Fragment createFragment() {
        return addFragment();
    }

    @Override
    protected void setFeature() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void onActivityCreate() {
        setContentView(R.layout.bruce_activity_base);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
