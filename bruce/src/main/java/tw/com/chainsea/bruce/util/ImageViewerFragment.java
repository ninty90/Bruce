package tw.com.chainsea.bruce.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import tw.com.chainsea.bruce.R;
import tw.com.chainsea.bruce.dialog.ListDialog;
import tw.com.chainsea.bruce.view.HackyViewPager;
import tw.com.chainsea.bruce.view.photoview.PhotoView;
import tw.com.chainsea.bruce.view.photoview.PhotoViewAttacher;

/**
 * ImageViewerFragment
 * Created by Chris on 2014/9/8.
 */
public class ImageViewerFragment extends DialogFragment {
    private ArrayList<String> mImageUrls;
    private int mCurrentPos = 0;
    private ImageView[] mImageViews;
    private int totalPages = 0;

    final static String INTENT_URLS = "urls";
    final static String INTENT_POSITION = "pos";
    private View mView;

    public static ImageViewerFragment newInstance(ArrayList<String> imageUrls, int currentItem) {
        Bundle args = new Bundle();
        args.putStringArrayList(INTENT_URLS, imageUrls);
        args.putInt(INTENT_POSITION, currentItem);
        ImageViewerFragment fragment = new ImageViewerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrls = getArguments().getStringArrayList(INTENT_URLS);
        mCurrentPos = getArguments().getInt(INTENT_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.bruce_image_viewer, container, false);
        ViewPager mViewPager = (HackyViewPager) mView.findViewById(R.id.image_viewer_viewpager);
        totalPages = mImageUrls.size();
        LinearLayout bottomLayout = (LinearLayout) mView.findViewById(R.id.image_viewer_bottom);

        if (1 == totalPages) {
            bottomLayout.setVisibility(View.GONE);
        } else {
            mImageViews = new ImageView[totalPages];
            for (int i = 0; i < totalPages; ++i) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new ViewGroup.LayoutParams(40, 40));
                imageView.setPadding(15, 15, 15, 15);
                mImageViews[i] = imageView;
                if (i == mCurrentPos) {
                    mImageViews[i].setImageResource(R.drawable.bruce_oval_selected);
                } else {
                    mImageViews[i].setImageResource(R.drawable.bruce_oval_unselect);
                }
                bottomLayout.addView(mImageViews[i]);
            }
        }

        mViewPager.setAdapter(new SwitchPageAdapter());
        mViewPager.setCurrentItem(mCurrentPos);
        mViewPager.setOnPageChangeListener(new PageChangeListener());
        mView.setFocusableInTouchMode(true);
        return mView;
    }

    private class SwitchPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return totalPages;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    getActivity().finish();
                    getActivity().overridePendingTransition(R.anim.bruce_fade_in, R.anim.bruce_zoom_exit);
                }
            });
//            DaVinci.with(getActivity()).getImageLoader().load(mImageUrls.get(position)).into(photoView);
            Glide.with(getActivity()).load(mImageUrls.get(position)).into(photoView);

            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    afterPicLongClick(mImageUrls.get(position));
                    return true;
                }
            });

            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView((View) object);
        }
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            mCurrentPos = arg0;
            mImageViews[arg0].setImageResource(R.drawable.bruce_oval_selected);
            for (int i = 0; i < mImageViews.length; ++i) {
                if (i != mCurrentPos) {
                    mImageViews[i].setImageResource(R.drawable.bruce_oval_unselect);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    private void afterPicLongClick(final String url) {
//        ImageEntity entity = DaVinci.with(getActivity()).getImageLoader().getImage(url);
        ListDialog listDialog = new ListDialog(getActivity());
        listDialog.addItem(getActivity().getString(R.string.bruce_save_to_phone), new ListDialog.ListAction() {
            @Override
            public boolean onClick() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveImage(url);
                    }
                }).start();
                return false;
            }
        });
        listDialog.show();
    }

    private void saveImage(String url) {
        String name = System.currentTimeMillis() + ".jpg";
        ContentResolver cr = getActivity().getContentResolver();
        String photoUri = MediaStore.Images.Media.insertImage(cr, getBitmap(url), name, "this is a Photo");
        if (photoUri != null) {
            String path = getFilePathByContentResolver(getActivity(), Uri.parse(photoUri));
            showShortToast(R.string.bruce_photo_save, path);

            //refresh photo viewer
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.parse(photoUri));
            getActivity().sendBroadcast(intent);
        } else {
            showShortToast(R.string.bruce_photo_save_failed, null);
        }
    }

    private void showShortToast(final int resId, final String path) {
        mView.post(new Runnable() {
            @Override
            public void run() {
                if (path != null) {
                    Toast.makeText(getActivity(), getString(resId, path), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Bitmap getBitmap(final String url) {
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(getActivity())
                    .load(url)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private String getFilePathByContentResolver(Context context, Uri uri) {
        if (null == uri) {
            return null;
        }
        Cursor c = context.getContentResolver().query(uri, null, null, null, null);
        String filePath = null;
        if (null == c) {
            throw new IllegalArgumentException(
                    "Query on " + uri + " returns null result.");
        }
        try {
            if ((c.getCount() == 1) && c.moveToFirst()) {
                filePath = c.getString(
                        c.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            }
        } finally {
            c.close();
        }
        return filePath;
    }
}
