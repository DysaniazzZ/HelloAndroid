package com.example.dysaniazzz.chapter03;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 12/12/2016.
 * 第三章：常用控件页面
 */
public class CommonWidgetsActivity extends BaseActivity {

    @BindView(R.id.pb_widgets_horizontal_progress)
    ProgressBar mPbWidgetsHorizontalProgress;
    @BindView(R.id.pb_widgets_normal_progress)
    ProgressBar mPbWidgetsNormalProgress;

    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CommonWidgetsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_widgets);
        mUnbinder = ButterKnife.bind(this);

        //第一种兼容方案：可以设置成同一种颜色，不过在5.0之前的效果不太好
        //mPbWidgetsNormalProgress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(mContext, R.color.colorProgress), PorterDuff.Mode.SRC_IN);
        //第二种兼容方案：直接改成自己定义的Loading效果(虽然可以保证在多个版本保证统一，但是显示效果也不太好）
        //mPbWidgetsNormalProgress.setIndeterminateDrawable(getResources().getDrawable(R.drawable.rotate_progress));
    }

    @OnClick(R.id.btn_widgets_changeProgress)
    public void onChangeProgressClick() {
        int progress = mPbWidgetsHorizontalProgress.getProgress();
        int secondProgress = mPbWidgetsHorizontalProgress.getSecondaryProgress();
        progress += 10;
        secondProgress += 20;
        mPbWidgetsHorizontalProgress.setProgress(progress);
        mPbWidgetsHorizontalProgress.setSecondaryProgress(secondProgress);
    }

    @OnClick(R.id.btn_widgets_alert_dialog)
    public void onShowAlertDialogClick() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        //对话框标题
        dialog.setTitle("This is a alert dialog");
        //对话框图标
        dialog.setIcon(R.mipmap.ic_launcher);
        //对话框内容
        //dialog.setMessage("Something important");
        final String[] characters = new String[]{"Luffy", "Naturo", "Dysania"};
        //设置单选条目及单击事件
//        dialog.setItems(characters, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                UIUtils.createToast(mContext, characters[which]);
//            }
//        });
        //设置多选条目及点击事件
        dialog.setMultiChoiceItems(characters, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                UIUtils.createToast(mContext, characters[which] + "\t" + (isChecked ? "isChecked" : "isUnChecked"));
            }
        });
        //设置一个自定义的View作为对话框的内容
        //dialog.setView(your_custom_view);
        //设置是否可通过Back取消
        dialog.setCancelable(false);
        //设置确定按钮点击事件（默认就会dismiss掉dialog)
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        //设置中立按钮点击事件（默认就会dismiss掉dialog）
        dialog.setNeutralButton("Ignore", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        //设置取消按钮点击事件（默认就会dismiss掉dialog）
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }

    @OnClick(R.id.btn_widgets_progress_dialog)
    public void onShowProgressDialogClick() {
        ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setTitle("This is a progress dialog");
        dialog.setMessage("Loading...");
        dialog.setCancelable(true);
        dialog.show();
    }

    @OnClick(R.id.btn_widgets_recycler_view)
    public void onRecyclerViewClick() {
        RecyclerViewActivity.actionStart(mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
