package tw.com.chainsea.bruce.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import tw.com.chainsea.bruce.R;


/**
 * confirm dialog style, it has content, ok and cancel button
 * Created by 90Chris on 2014/12/1.
 */
public class YesNoDialog extends Dialog {
    private TextView tvYes;
    private TextView tvNo;

    public YesNoDialog(Context context) {
        super(context, R.style.BruceDialog);
        setContentView(R.layout.bruce_dialog_yes_no);
        setCanceledOnTouchOutside(true);
        tvYes = (TextView)findViewById(R.id.dialog_confirm_ok);
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( mYesAction != null && mYesAction.onYes() ) {
                    return;
                }
                dismiss();
            }
        });

        tvNo = (TextView)findViewById(R.id.dialog_confirm_cancel);
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( mNoAction != null && mNoAction.onNo() ) {
                    return;
                }
                dismiss();
            }
        });
    }

    /**
     * set the dialog content
     * @param content content
     */
    public void setContent(String content) {
        TextView textView = (TextView)findViewById(R.id.dialog_confirm_content);
        textView.setText(content);
    }

    public void setYes(String content, YesAction yesAction) {
        tvYes.setText(content);
        mYesAction = yesAction;
    }

    public void setNo(String content, NoAction noAction) {
        tvNo.setText(content);
        mNoAction = noAction;
    }

    private YesAction mYesAction;
    public interface YesAction {
        boolean onYes();
    }

    private NoAction mNoAction;
    public interface NoAction {
        boolean onNo();
    }
}
