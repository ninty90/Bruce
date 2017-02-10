package tw.com.chainsea.bruce.base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import tw.com.chainsea.bruce.R;
import tw.com.chainsea.bruce.ife.PermissionListener;


/**
 * Only used by Activities where a single Fragment is used and not changed (i.e. used by
 * all Activities except HomeActivity
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    FragmentManager manager;
    PermissionListener mListener;

    /**
     * create fragment
     *
     * @return fragment which is created by developer
     */
    protected abstract Fragment createFragment();

    /**
     * deal activity things before fragment added
     */
    protected abstract void onActivityCreate();

    protected abstract void setFeature();

    protected void init() {
    }

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

        if (!isAddFragment()) {
            return;
        }

        manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = createFragment();
            if (null != fragment) {
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
     *
     * @return fragment
     */
    protected Fragment getFragment() {
        return manager.findFragmentById(R.id.fragmentContainer);
    }

    /**
     * android 6.0以上运行时权限申请
     *
     * @param permissions 权限数组
     * @param listener    回调接口
     */
    public void requestRuntimePermission(String[] permissions, PermissionListener listener) {
        mListener = listener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            mListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> deniedPermission = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermission.add(permission);
                        }
                    }
                    if (deniedPermission.isEmpty()) {
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(deniedPermission);
                    }
                }
                break;
            default:
                break;
        }
    }
}