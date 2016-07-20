package tw.com.chainsea.bruce_example;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import tw.com.chainsea.bruce.TitlebarActivity;
import tw.com.chainsea.bruce.base.BruceConstant;
import tw.com.chainsea.bruce.util.ImageViewerActivity;

public class MainActivity extends TitlebarActivity {


    @Override
    public Fragment addFragment() {
        return new MainFragment();
    }

    @Override
    public View rightView() {
        TextView textView = new TextView(this);
        textView.setTextColor(Color.WHITE);
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
