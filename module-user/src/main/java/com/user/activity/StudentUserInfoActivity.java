package com.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.fastkit.utils.log.LogUtil;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.fastkit.utils.file_size.PcUtils;
import com.lib.fastkit.utils.json_deal.GetJsonDataUtil;
import com.lib.fastkit.utils.time_deal.TimeUtils;
import com.lib.fastkit.views.dialog.zenhui.AlertDialog;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.utls.glide.GlideConfig;
import com.lib.utls.picture_select.PhotoUtil;
import com.lib.utls.upload.QiNiuUploadTask;
import com.lib.utls.upload.initerface.FileUploadListener;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.user.R;
import com.user.R2;
import com.user.bean.CityNameBean;
import com.user.bean.StudentInfoBean;
import com.user.bean.UpdateBean;
import com.user.bean.UpdateInfoBean;
import com.user.fragment.ChooseSchoolFragment;
import com.user.fragment.ContactPhoneFragment;
import com.user.fragment.NickNameFragment;
import com.user.fragment.SexFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ARouterPathUtils.User_StudentUserInfoActivity)
public class StudentUserInfoActivity extends BaseAppActivity {


    @BindView(R2.id.iv_head)
    ImageView ivHead;
    @BindView(R2.id.lin_head)
    LinearLayout linHead;
    @BindView(R2.id.tv_nickname)
    TextView tvNickname;
    @BindView(R2.id.lin_nickname)
    LinearLayout linNickname;
    @BindView(R2.id.tv_sex)
    TextView tvSex;
    @BindView(R2.id.lin_sex)
    LinearLayout linSex;
    @BindView(R2.id.tv_birth)
    TextView tvBirth;
    @BindView(R2.id.lin_birth)
    LinearLayout linBirth;
    @BindView(R2.id.tv_grad)
    TextView tvGrad;
    @BindView(R2.id.lin_grad)
    LinearLayout linGrad;
    @BindView(R2.id.tv_shool)
    TextView tvShool;
    @BindView(R2.id.lin_school)
    LinearLayout linSchool;
    @BindView(R2.id.tv_city)
    TextView tvCity;
    @BindView(R2.id.lin_city)
    LinearLayout linCity;
    @BindView(R2.id.tv_contact)
    TextView tvContact;
    @BindView(R2.id.lin_contact)
    LinearLayout linContact;
    @BindView(R2.id.tv_share)
    TextView tvShare;
    @BindView(R2.id.lin_share)
    LinearLayout linShare;
    @BindView(R2.id.lin_student)
    LinearLayout linStudent;
    @BindView(R2.id.btn_sure)
    Button btnSure;
    List<UpdateInfoBean> updateInfoBeans = new ArrayList<>();

    @Override
    protected void onCreateView() {
        initTitle();
        initTimePicker();
        initCityPicker();
        initNoLinkOptionsPicker();
        initView();

    }

    private void initView() {
        initData();

    }


    private StudentInfoBean studentInfoBean;

    private void initData() {

        String token = SharedPreferenceManager.getInstance(this).getUserCache().getUserToken();

        HttpUtils.with(this)
                .post()
                .addParam("requestType", "STUDENT_PERSONAL_INFO")
                .addParam("token", token)
                .execute(new HttpNormalCallBack<StudentInfoBean>() {
                    @Override
                    public void onSuccess(StudentInfoBean result) {


                        if (result.getCode() == CodeUtil.CODE_200) {
                            studentInfoBean = result;
                            initStudentData(result);

                        }


                    }

                    @Override
                    public void onError(String e) {

                    }
                });

    }

    private void initStudentData(StudentInfoBean result) {


        Glide.with(this)
                .load(result.getObj().getPhoto_url())
                .apply(GlideConfig.getCircleOptions())
                .into(ivHead);

        tvNickname.setText(result.getObj().getName());


        String sex = result.getObj().getSex();
        if (sex.equals("0")) {
            tvSex.setText("男");
        } else if (sex.equals("1")) {
            tvSex.setText("女");
        } else {
            tvSex.setText("未填写");
        }


        String birth = result.getObj().getBirthday_date();
        tvBirth.setText(result.getObj().getBirthday_date());
        if (birth.equals("")) {
            tvBirth.setText("未填写");
        }


        String grade = result.getObj().getGrade_name();
        tvGrad.setText(grade);
        if (grade.equals("")) {
            tvGrad.setText("未填写");
        }


        String school = result.getObj().getSchool_name();
        tvShool.setText(school);
        if (school.equals("")) {
            tvShool.setText("未填写");
        }


        String city = result.getObj().getArea();
        tvCity.setText(result.getObj().getArea());
        if (city.equals("")) {
            tvCity.setText("未填写");
        }


        String phone = result.getObj().getAss_phone();
        tvContact.setText(phone);

        if (phone.equals("")) {
            tvContact.setText("未填写");
        }

    }

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("个人资料")
                .builder();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_student_user_info;
    }


    List<LocalMedia> listHead = new ArrayList<>();


    //头像选择
    private final int SELECT_HEAD = 4;

    private int SELECT_TYPE = 1;


    //=============================================================================================
    //===============================================================================性别选择=======
    //=============================================================================================


    SexFragment sexFragment;

    private void showSex() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_sex)

                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {


                        if (sexFragment != null) {
                            getSupportFragmentManager().beginTransaction().remove(sexFragment).commit();

                            sexFragment = null;
                        }


                    }
                })

                .fullWidth()
                .addDefaultAnimation()
                .show();


        sexFragment = (SexFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_sex);


        if (studentInfoBean != null) {
            if (studentInfoBean.getObj().getSex().equals("0")) {
                sexFragment.setSex(SexFragment.MEN);
            } else {
                sexFragment.setSex(SexFragment.WOMAN);
            }
        }


        sexFragment.setSexChooseListener(new SexFragment.SexChooseListener() {
            @Override
            public void onSexChoose(String str) {

                String sex = "0";

                if (str.equals("男")) {
                    sex = "0";
                } else {
                    sex = "1";
                }

                addUpdateInfo("sex", sex);
                tvSex.setText(str);
                dialog.dismiss();
            }
        });


    }

    //=============================================================================================
    //==============================================================================昵称=======
    //=============================================================================================

    NickNameFragment nickNameFragment;

    private void showNick() {


        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_nick)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (nickNameFragment != null) {
                            getSupportFragmentManager().beginTransaction().remove(nickNameFragment).commit();
                            nickNameFragment = null;
                        }

                    }
                })

                .fullWidth()
                .addDefaultAnimation()
                .show();

        nickNameFragment = (NickNameFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nick);

        if (studentInfoBean != null) {


            nickNameFragment.setNickName(studentInfoBean.getObj().getName());

        }


        nickNameFragment.setOnNomalChangeListener(new NickNameFragment.NormalChangeListener() {
            @Override
            public void onNomalChange(String str) {


                addUpdateInfo("name", str);

                tvNickname.setText(str);

                dialog.dismiss();

            }
        });
    }


    //=============================================================================================
    //==============================================================================关联手机=======
    //=============================================================================================

    ContactPhoneFragment contactPhoneFragment;

    private void showContactPhone() {


        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_contact)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (contactPhoneFragment != null) {
                            getSupportFragmentManager().beginTransaction().remove(contactPhoneFragment).commit();
                            contactPhoneFragment = null;
                        }

                    }
                })

                .fullWidth()
                .addDefaultAnimation()
                .show();

        contactPhoneFragment = (ContactPhoneFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_contact);

        if (studentInfoBean != null) {
            contactPhoneFragment.setContactInfo(studentInfoBean.getObj().getAss_person(), studentInfoBean.getObj().getAss_phone());
        }

        contactPhoneFragment.setOnNomalChangeListener(new ContactPhoneFragment.NormalChangeListener() {
            @Override
            public void onNomalChange(String name, String phone) {

                addUpdateInfo("ass_person", name);
                addUpdateInfo("ass_phone", phone);

                tvContact.setText(phone);
                dialog.dismiss();
            }


        });
    }
    //=============================================================================================
    //==============================================================================学校=======
    //=============================================================================================


    ChooseSchoolFragment schoolFragment;

    private void showSchool() {


        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_school)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (schoolFragment != null) {
                            getSupportFragmentManager().beginTransaction().remove(schoolFragment).commit();

                            schoolFragment = null;
                        }

                    }
                })

                .fullWidth()
                .addDefaultAnimation()
                .show();

        schoolFragment = (ChooseSchoolFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_school);


        if (studentInfoBean != null) {
            schoolFragment.setSchool(studentInfoBean.getObj().getSchool_name());
        }

        schoolFragment.setOnNomalChangeListener(new ChooseSchoolFragment.NormalChangeListener() {
            @Override
            public void onNomalChange(String str) {


                addUpdateInfo("school_name", str);

                tvShool.setText(str);
                dialog.dismiss();

            }
        });


    }

    //=============================================================================================
    //==============================================================================图片选择=======
    //=============================================================================================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

                    LocalMedia media = selectList.get(0);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的


                    if (media.isCompressed()) {

                        showToast(media.getCompressPath());
                        Bitmap bitmap = PcUtils.getBitmapFromFile(media.getCompressPath());

                        switch (SELECT_TYPE) {

                            case SELECT_HEAD: {

                                uploadHead(media.getCompressPath());


                                Glide.with(this)
                                        .load(bitmap)
                                        .apply(GlideConfig.getCircleOptions())
                                        .into(ivHead);
                                break;
                            }
                        }
                    }

                    break;
            }
        }

    }


    private void uploadHead(String compressPath) {
        String uploadToken = "cM06KhPDAs5mHoQD7j4qHl3FNazty-bFuQCnkuMW:6_Pjjl5WIUe8KajASrtJIyFHlKg=:eyJzY29wZSI6Inh1ZXNpdHUiLCJkZWFkbGluZSI6MTU1OTc5MDY5NH0=";
        QiNiuUploadTask qiNiuUploadTask = new QiNiuUploadTask();
        qiNiuUploadTask.setFileUploadListener(new FileUploadListener() {
            @Override
            public void onProgress(int progress) {

            }

            @Override
            public void onSuccess(String s) {


                addUpdateInfo("photo_url", s);
            }

            @Override
            public void onError(String e) {

            }
        });
        qiNiuUploadTask.execute(compressPath, uploadToken);


    }

    //=============================================================================================
    //==============================================================================城市选择=======
    //=============================================================================================

    private List<CityNameBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private OptionsPickerView cityPicker;

    private void initCityPicker() {// 弹出选择器
        initJsonData();
        cityPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;


                addUpdateInfo("area", opt3tx);
                tvCity.setText(opt3tx);

            }
        })


                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setTitleSize(20)//标题文字大小
                .setTitleText("城市")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(getResources().getColor(R.color.base_title))//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.base_blue))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.base_blue))//取消按钮文字颜色
                .setTitleBgColor(getResources().getColor(R.color.white))//标题背景颜色 Night mode
                .setBgColor(getResources().getColor(R.color.base_white))//滚轮背景颜色 Night mode
                .setOutSideCancelable(true)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        cityPicker.setPicker(options1Items, options2Items, options3Items);//三级选择器

    }


    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<CityNameBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }


    }


    public ArrayList<CityNameBean> parseData(String result) {//Gson 解析
        ArrayList<CityNameBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                CityNameBean entity = gson.fromJson(data.optJSONObject(i).toString(), CityNameBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }


    //------------------------------------------------------------------------------------生日
    private TimePickerView pvTime;

    private void initTimePicker() {

        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        //正确设置方式 原因：注意事项有说明
        startDate.set(2013, 0, 1);
        endDate.set(2020, 11, 31);

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                String birth = TimeUtils.getDateStr(date, TimeUtils.FORMAT_0);


                addUpdateInfo("birthday_date", birth);
                tvBirth.setText(birth);


            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒

                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setTitleSize(20)//标题文字大小
                .setTitleText("生日")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(getResources().getColor(R.color.base_title))//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.base_blue))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.base_blue))//取消按钮文字颜色
                .setTitleBgColor(getResources().getColor(R.color.white))//标题背景颜色 Night mode
                .setBgColor(getResources().getColor(R.color.base_white))//滚轮背景颜色 Night mode
                .setOutSideCancelable(true)
                .build();

        //设置默认时间
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DATE),
                c.get(Calendar.HOUR_OF_DAY) * 0,
                c.get(Calendar.MINUTE) * 0,
                c.get(Calendar.SECOND) * 0);
        pvTime.setDate(c);
    }


    //--------------------------------------------------------------------------------------点击事件

    @OnClick({R2.id.lin_head, R2.id.lin_nickname, R2.id.lin_sex, R2.id.lin_birth, R2.id.lin_grad, R2.id.lin_school, R2.id.lin_city, R2.id.lin_contact, R2.id.lin_share, R2.id.lin_student, R2.id.btn_sure})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.lin_head) {

            SELECT_TYPE = SELECT_HEAD;
            PhotoUtil.cutHeadPicture(this, listHead, 1);
        } else if (i == R.id.lin_nickname) {
            showNick();
        } else if (i == R.id.lin_sex) {
            showSex();
        } else if (i == R.id.lin_birth) {
            pvTime.show();
        } else if (i == R.id.lin_grad) {

            gradePicker.show();


        } else if (i == R.id.lin_school) {
            showSchool();
        } else if (i == R.id.lin_city) {
            cityPicker.show();
        } else if (i == R.id.lin_contact) {

            showContactPhone();
        } else if (i == R.id.lin_share) {
        } else if (i == R.id.lin_student) {
        } else if (i == R.id.btn_sure) {
            goUpdate();


        }
    }


    private List<String> gradeArray = new ArrayList<>();
    OptionsPickerView gradePicker;
    private String[] grade = {
            "一年级", "二年级", "三年级",
            "四年级", "五年级", "六年级",
            "初一", "初二", "初三",
            "高一", "高二", "高三"};

    private void initNoLinkOptionsPicker() {// 不联动的多级选项


        gradeArray.clear();
        for (String s : grade) {
            gradeArray.add(s);
        }

        gradePicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                String grade = gradeArray.get(options1);

                int gradeId = options1 + 1;


                addUpdateInfo("grade_id", gradeId + "");
                tvGrad.setText(grade);

            }


        }).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
            @Override
            public void onOptionsSelectChanged(int options1, int options2, int options3) {
                String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;

            }
        })
                .setSelectOptions(0)
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setTitleSize(20)//标题文字大小
                .setTitleText("年级")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(getResources().getColor(R.color.base_title))//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.base_blue))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.base_blue))//取消按钮文字颜色
                .setTitleBgColor(getResources().getColor(R.color.white))//标题背景颜色 Night mode
                .setBgColor(getResources().getColor(R.color.base_white))//滚轮背景颜色 Night mode
                .setOutSideCancelable(true)
                .build();

        gradePicker.setPicker(gradeArray);


    }


    private void addUpdateInfo(String key, String value) {
        boolean isExist = false;

        for (UpdateInfoBean updateInfoBean : updateInfoBeans) {

            if (updateInfoBean.getKey().equals(key)) {
                updateInfoBean.setValue(value);
                isExist = true;
            }

        }


        if (!isExist) {
            updateInfoBeans.add(new UpdateInfoBean(key, value));
        }


    }


    private void goUpdate() {


        String token = SharedPreferenceManager.getInstance(this).getUserCache().getUserToken();
        Map<String, Object> map = new HashMap<>();


        for (UpdateInfoBean updateInfoBean : updateInfoBeans) {
            map.put(updateInfoBean.getKey(), updateInfoBean.getValue());
        }


        HttpUtils.with(this)
                .addParam("requestType", "PERSONAL_INFO_UPDATE")
                .addParam("token", token)
                .addParams(map)
                .post()
                .execute(new HttpNormalCallBack<UpdateBean>() {
                    @Override
                    public void onSuccess(UpdateBean result) {
                        if (result.getCode() == CodeUtil.CODE_200) {

                            showToast(result.getMsg());

                            finish();

                        } else {
                            showToast(result.getMsg());
                        }


                    }

                    @Override
                    public void onError(String e) {

                    }
                });


    }
}
