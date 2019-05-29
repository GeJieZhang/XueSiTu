package com.user.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.app.ARouterPathUtils;
import com.lib.app.EventBusTagUtils;
import com.lib.bean.Event;
import com.lib.fastkit.views.dialog.zenhui.AlertDialog;
import com.lib.ui.activity.BaseAppActivity;
import com.user.R;
import com.user.R2;
import com.user.fragment.StudentChooseFragment;
import com.user.fragment.TeacherChooseFragment;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterPathUtils.User_IdentityActivity)
public class IdentityActivity extends BaseAppActivity {
    @BindView(R2.id.f_teacher)
    FrameLayout fTeacher;
    @BindView(R2.id.f_student)
    FrameLayout fStudent;

    @Override
    protected void onCreateView() {


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_identity;
    }


    private TeacherChooseFragment teacherChooseFragment;

    private StudentChooseFragment studentChooseFragment;

    @OnClick({R2.id.f_teacher, R2.id.f_student})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.f_teacher) {


            showTeacher();


        } else if (i == R.id.f_student) {


            showStudent();

        }
    }

    private void showStudent() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_student)

                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {


                        if (studentChooseFragment != null) {
                            getSupportFragmentManager().beginTransaction().remove(studentChooseFragment).commit();
                        }


                    }
                })

                .fullWidth()
                .addDefaultAnimation()
                .show();


        studentChooseFragment = (StudentChooseFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_teacher);
        studentChooseFragment.setGradeChooseListener(new StudentChooseFragment.GradeChooseListener() {
            @Override
            public void onGradeChoose(String str) {

                showToast(str);

            }
        });

    }

    private void showTeacher() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_teacher)

                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {


                        if (teacherChooseFragment != null) {
                            getSupportFragmentManager().beginTransaction().remove(teacherChooseFragment).commit();
                        }


                    }
                })

                .fullWidth()
                .addDefaultAnimation()
                .show();


        teacherChooseFragment = (TeacherChooseFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_teacher);
        teacherChooseFragment.setGradeChooseListener(new TeacherChooseFragment.GradeChooseListener() {
            @Override
            public void onGradeChoose(String str) {
                showToast(str);
            }
        });

    }


}
