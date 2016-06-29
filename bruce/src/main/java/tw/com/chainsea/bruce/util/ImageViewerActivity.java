package tw.com.chainsea.bruce.util;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import tw.com.chainsea.bruce.FullScreenActivity;
import tw.com.chainsea.bruce.R;
import tw.com.chainsea.bruce.base.BruceConstant;


/**
 * ImageViewerActivity
 * Created by 90Chris on 2014/11/18.
 */
public class ImageViewerActivity extends FullScreenActivity {
    ImageViewerFragment mFragment = null;

    @Override
    public void init() {
        super.init();
        overridePendingTransition(R.anim.bruce_zoom_enter, R.anim.bruce_fade_out);
    }

    @Override
    public Fragment addFragment() {
        return mFragment;
    }

    @Override
    public void onActivityCreate() {
        super.onActivityCreate();
        ArrayList<String> urlList = getIntent().getStringArrayListExtra(BruceConstant.INTENT_IMAGE_URLS);
        mFragment = ImageViewerFragment.newInstance(urlList, 0);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.bruce_fade_in, R.anim.bruce_zoom_exit);
    }
}
