package tw.com.chainsea.bruce.dialog;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import tw.com.chainsea.bruce.R;


/**
 * ListDialog
 * Created by 90Chris on 2014/12/1.
 */
public class ListDialog {
    FlatDialog mFlatDialog;
    Context mContext;
    LinearLayout linearLayout;
    float density;

    public ListDialog(Context context) {
        mContext = context;
        linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        density = displayMetrics.density;
        linearLayout.setMinimumWidth((displayMetrics.widthPixels * 3) / 4);
        mFlatDialog = new FlatDialog(mContext, linearLayout);
    }

    /**
     * add a content and click action
     * @param content content
     * @param action action
     */
    public void addContent(String content, final ListAction action) {
        TextView textView = new TextView(mContext);
        textView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bruce_clickable_bg));
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(18);
        int padding = (int) (15 * density);
        textView.setPadding(padding, padding, 0, padding);
        textView.setText(content);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action.onClick();
                mFlatDialog.dismiss();
            }
        });
        linearLayout.addView(textView);
        /*add divider*/
        ImageView imageView = new ImageView(mContext);
        imageView.setBackgroundColor(Color.DKGRAY);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        linearLayout.addView(imageView);
    }

    /**
     * display dialog
     */
    public void show(){
        mFlatDialog.show();
    }

    public interface ListAction{
        void onClick();
    }
}
