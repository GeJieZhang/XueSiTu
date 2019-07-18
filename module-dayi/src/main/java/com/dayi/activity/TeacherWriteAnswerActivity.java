package com.dayi.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dayi.R;
import com.lib.app.ARouterPathUtils;
import com.lib.ui.activity.BaseAppActivity;


@Route(path = ARouterPathUtils.Dayi_TeacherWriteAnswerActivity)
public class TeacherWriteAnswerActivity extends BaseAppActivity {
    @Override
    protected void onCreateView() {


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_teacher_write_answer;
    }
}
