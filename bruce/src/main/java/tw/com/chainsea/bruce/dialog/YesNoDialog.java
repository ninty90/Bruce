package tw.com.chainsea.bruce.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import tw.com.chainsea.bruce.R;


/**
 * confirm dialog style, it has content, ok and cancel button
 * Created by 90Chris on 2014/12/1.
 */
public class YesNoDialog {
    FlatDialog flatDialog;

    public YesNoDialog(Context context) {
        flatDialog = new FlatDialog(context, R.layout.bruce_dialog_yes_no);
        flatDialog.findViewById(R.id.dialog_confirm_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConfirmAction.onConfirm();
                flatDialog.dismiss();
            }
        });
        flatDialog.findViewById(R.id.dialog_confirm_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConfirmAction.onCancel();
                flatDialog.dismiss();
            }
        });
    }

    /**
     * is dialog can be dismissed,
     * default, it can be dismissed
     * @param b
     */
    public void enableDismiss(boolean b){
        flatDialog.setCanceledOnTouchOutside(b);
    }

    /**
     * set the dialog content
     * @param content
     */
    public void setContent(String content) {
        TextView textView = (TextView)flatDialog.findViewById(R.id.dialog_confirm_content);
        textView.setText(content);
    }

    public void setConfirmContent(String content) {
        TextView textView = (TextView)flatDialog.findViewById(R.id.dialog_confirm_ok);
        textView.setText(content);
    }

    public void setCancelContent(String content) {
        TextView textView = (TextView)flatDialog.findViewById(R.id.dialog_confirm_cancel);
        textView.setText(content);
    }

    /**
     * define the action after the ok button was clicked
     * @param confirmAction
     */
    public void setConfirmAction(ConfirmAction confirmAction) {
        mConfirmAction = confirmAction;
    }

    /**
     * display dialog
     */
    public void show() {
        flatDialog.show();
    }

    private ConfirmAction mConfirmAction;
    public static interface ConfirmAction{
        void onConfirm();
        void onCancel();
    }
}
