package tw.com.chainsea.bruce_example;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tw.com.chainsea.bruce.TitlebarActivity;
import tw.com.chainsea.bruce.base.BruceConstant;
import tw.com.chainsea.bruce.ife.PermissionListener;
import tw.com.chainsea.bruce.util.ImageViewerActivity;

public class MainActivity extends TitlebarActivity {


    @Override
    protected void init() {
        super.init();
        requestRuntimePermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "permission granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(List<String> permissionList) {
                Toast.makeText(MainActivity.this, "permission denied", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public Fragment addFragment() {
        return new MainFragment();
    }

    @Override
    public View rightView() {
        TextView textView = new TextView(this);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(20);
        textView.setPadding(10, 0, 10, 0);
        textView.setText("跳");
        return textView;
    }

    @Override
    public void rightAction() {
        Intent intent = new Intent(this, ImageViewerActivity.class);
        ArrayList<String> list = new ArrayList<>();
        list.add("http://e.hiphotos.baidu.com/image/pic/item/dcc451da81cb39db02807657d2160924ab18306a.jpg");
        list.add("http://g.hiphotos.baidu.com/image/pic/item/503d269759ee3d6d1b54569a41166d224e4aded5.jpg");
        intent.putExtra(BruceConstant.INTENT_IMAGE_URLS, list);
        startActivity(intent);
    }
}
