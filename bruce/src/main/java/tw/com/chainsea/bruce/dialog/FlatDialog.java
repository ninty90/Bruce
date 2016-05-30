package tw.com.chainsea.bruce.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import tw.com.chainsea.bruce.R;


/**
 * use layout to create a custom style dialog
 * Created by 90Chris on 2014/12/1.
 */
public class FlatDialog extends Dialog {

    public FlatDialog(Context context, int resource) {
        super(context, R.style.BruceDialog);
        setContentView(resource);
        setCanceledOnTouchOutside(true);
    }

    public FlatDialog(Context context, View view) {
        super(context, R.style.BruceDialog);
        setContentView(view);
        setCanceledOnTouchOutside(true);
    }
}
