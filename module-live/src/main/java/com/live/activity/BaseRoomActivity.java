package com.live.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.lib.fastkit.views.button_deal.click.ClickUtils;
import com.lib.ui.activity.kit.BaseActivity;

public class BaseRoomActivity extends AppCompatActivity {
    private Toast mToast;

    //横屏
    public static int screenHorization = Configuration.ORIENTATION_LANDSCAPE;
    //竖屏
    public static int screenVertical = Configuration.ORIENTATION_PORTRAIT;

    /**
     * 设置点击屏幕隐藏键盘
     * 防止二次点击
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            boolean b = ClickUtils.isFastDoubleClick();
            //LogUtil.e(b + "");
            if (b) {


                return true;
            }
            // get current focus,Generally it is EditText
            View view = getCurrentFocus();
            if (isShouldHideSoftKeyBoard(view, ev)) {
                hideSoftKeyBoard(view.getWindowToken());
            }


        }

        return super.dispatchTouchEvent(ev);
    }


    /**
     * Judge what situation hide the soft keyboard,click EditText view should show soft keyboard
     *
     * @param
     * @param event
     * @return
     */
    private boolean isShouldHideSoftKeyBoard(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {
            int[] l = {0, 0};
            view.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + view.getHeight(), right = left
                    + view.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // If click the EditText event ,ignore it
                return false;
            } else {
                return true;
            }
        }
        // if the focus is EditText,ignore it;
        return false;
    }

    /**
     * hide soft keyboard
     *
     * @param token
     */
    private void hideSoftKeyBoard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public void fullscreen(boolean enable) {
        if (enable) { //显示状态栏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else { //隐藏状态栏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    /**
     * Toast
     *
     * @param text
     */
    public void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
            }
            mToast.setText(text);
            mToast.show();
        }
    }

    //==============================================================================================
    //============================================================================显示与隐藏键盘====
    //==============================================================================================

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(
                        this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .toggleSoftInput(
                        InputMethodManager.RESULT_UNCHANGED_SHOWN,
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }




}
