package tw.com.chainsea.bruce.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import tw.com.chainsea.bruce.R;


/**
 *  Only used by Activities where a single Fragment is used and not changed (i.e. used by
 *  all Activities except HomeActivity
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    FragmentManager manager;
    /**
     * create fragment
     * @return fragment which is created by developer
     */
    protected abstract Fragment createFragment();

    /**
     * deal activity things before fragment added
     */
    protected abstract void onActivityCreate();

    protected abstract void setFeature();

    protected void init() {}

    /**
     * @return false, do not add fragment; true, add fragment
     */
    protected boolean isAddFragment() {
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setFeature();
        super.onCreate(savedInstanceState);
        init();
        onActivityCreate();

        if ( !isAddFragment() ) {
            return;
        }

        manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null)
        {
            fragment = createFragment();
            if ( null != fragment ) {
                manager.beginTransaction()
                        .add(R.id.fragmentContainer, fragment)
                        .commit();
            }
        }
    }

    /**
     * 获得当前fragment，在配置更改时，activity会被重新创建
     * fragment也会被重新创建，但此时fragment已经存在于FragmentManager中，activity重新创建后会从中直接取fragment然后attach
     * 提供此方法目的，是让上层Activity在配置变化后可以调用fragment方法
     * @return fragment
     */
    protected Fragment getFragment(){
        return manager.findFragmentById(R.id.fragmentContainer);
    }
}