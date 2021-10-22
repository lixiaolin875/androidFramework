package com.example.duomiplatform;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;



/**
 * 自定义Toast显示，主要处理不同分辨率的toast弹出字体大小
 *
 * @type: ToastView
 */
public class ToastView {
    private final static int TOAST_TXT_SIZE = 15;
    public static Toast toast = null;
    private TextView textView = null;
    private LinearLayout linearLayout;
    private static final int POSTION_BOTTOM = 0;
    private static final int POSTION_CENTER = 1;
    private static int position = 0;


    public void show(String result, Context context) {
        if (TextUtils.isEmpty(result)) return;
        createView(context, POSTION_BOTTOM);
        position = POSTION_BOTTOM;
        textView.setText(result);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
        layoutParams.width = LayoutParams.WRAP_CONTENT;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        layoutParams.bottomMargin = 150;
        textView.setPadding(40, 30, 40, 30);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(layoutParams);
        toast.setView(linearLayout);
        //4.0版本才能使用图片读属性
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            textView.setAlpha(0.9f);
        }
        toast.show();
    }

    public void showCenter(String result, Context context) {
        if (TextUtils.isEmpty(result)) return;
        createView(context, POSTION_CENTER);
        position = POSTION_CENTER;
        LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
        layoutParams.width = LayoutParams.WRAP_CONTENT;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        layoutParams.bottomMargin = 0;
        textView.setLayoutParams(layoutParams);
        textView.setMaxLines(2);
        textView.setSingleLine(false);
        textView.setGravity(Gravity.CENTER);
        textView.setLineSpacing(TOAST_TXT_SIZE, 1.1f);
        textView.setText(result);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(linearLayout);
        textView.setPadding(40, 30, 40, 30);
        //4.0版本才能使用图片读属性
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            textView.setAlpha(0.9f);
        }
        toast.show();
    }


    private void createView(Context context, int showPosition) {
        if (null == textView || position != showPosition) {
            textView = new TextView(context.getApplicationContext());
//            textView.setBackgroundResource(R.drawable.dialog_bg);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, TOAST_TXT_SIZE);
//			textView.setTextColor(context.getResources().getColor(R.color.text_color_register));
            textView.setTextColor(context.getResources().getColor(R.color.white));
            textView.setPadding(20, 10, 20, 10);
            linearLayout = new LinearLayout(context.getApplicationContext());
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.bottomMargin = 100;
            linearLayout.addView(textView, layoutParams);
        }

        if (null == toast) {
            toast = new Toast(context.getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
        } else {
            toast.cancel();
            toast = new Toast(context.getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
        }

        linearLayout.setVisibility(View.VISIBLE);

    }

    public void cancel() {
        if (null != linearLayout) {
            linearLayout.setVisibility(View.GONE);
        }
    }

    public void show(int resId, Context context) {
        show(context.getString(resId), context);
    }

    public void showCenter(int resId, Context context) {
        showCenter(context.getString(resId), context);
    }

}
