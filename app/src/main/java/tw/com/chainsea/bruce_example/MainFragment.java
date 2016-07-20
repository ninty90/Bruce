package tw.com.chainsea.bruce_example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import tw.com.chainsea.bruce.dialog.ListDialog;
import tw.com.chainsea.bruce.dialog.YesNoDialog;

/**
 *
 * Created by 90Chris on 2016/7/20.
 */
public class MainFragment extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        view.findViewById(R.id.dialog_confirm).setOnClickListener(this);
        view.findViewById(R.id.dialog_list).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_confirm:
                YesNoDialog dialog = new YesNoDialog(getContext());
                dialog.setCanceledOnTouchOutside(false);
                dialog.setYes("确定", new YesNoDialog.YesAction() {
                    @Override
                    public boolean onYes() {
                        Toast.makeText(getContext(), "yes clicked", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
                dialog.setContent("222222");
                dialog.show();
                break;
            case R.id.dialog_list:
                ListDialog listDialog = new ListDialog(getContext());
                listDialog.addItem("点击窗口不关闭", new ListDialog.ListAction() {
                    @Override
                    public boolean onClick() {
                        return true;
                    }
                });
                listDialog.addItem("点击窗口关闭", new ListDialog.ListAction() {
                    @Override
                    public boolean onClick() {
                        return false;
                    }
                });
                listDialog.addItem("111111", new ListDialog.ListAction() {
                    @Override
                    public boolean onClick() {
                        return false;
                    }
                });
                listDialog.addItem("222222", new ListDialog.ListAction() {
                    @Override
                    public boolean onClick() {
                        return false;
                    }
                });
                listDialog.show();
                break;
        }
    }
}
