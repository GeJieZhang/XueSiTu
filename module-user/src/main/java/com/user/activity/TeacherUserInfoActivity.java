package com.user.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lib.app.ARouterPathUtils;
import com.lib.app.CodeUtil;
import com.lib.fastkit.db.shared_prefrences.SharedPreferenceManager;
import com.lib.fastkit.http.ok.HttpUtils;
import com.lib.http.call_back.HttpNormalCallBack;
import com.lib.fastkit.utils.file_size.PcUtils;
import com.lib.fastkit.utils.json_deal.GetJsonDataUtil;
import com.lib.fastkit.views.dialog.zenhui.AlertDialog;
import com.lib.ui.activity.BaseAppActivity;
import com.lib.utls.glide.GlideConfig;
import com.lib.utls.picture_select.PhotoUtil;
import com.lib.view.navigationbar.NomalNavigationBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.user.R;
import com.user.R2;
import com.user.bean.CityNameBean;
import com.user.bean.TeacherInfoBean;
import com.user.fragment.ChooseSchoolFragment;
import com.user.fragment.NickNameFragment;
import com.user.fragment.SexFragment;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ARouterPathUtils.User_TeacherUserInfoActivity)
public class TeacherUserInfoActivity extends BaseAppActivity {
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
    @BindView(R2.id.iv_id_z)
    ImageView ivIdZ;
    @BindView(R2.id.tv_id_z)
    TextView tvIdZ;
    @BindView(R2.id.f_id_z)
    FrameLayout fIdZ;
    @BindView(R2.id.iv_id_f)
    ImageView ivIdF;
    @BindView(R2.id.tv_id_f)
    TextView tvIdF;
    @BindView(R2.id.f_id_f)
    FrameLayout fIdF;
    @BindView(R2.id.iv_certificate)
    ImageView ivCertificate;
    @BindView(R2.id.tv_certificate)
    TextView tvCertificate;
    @BindView(R2.id.f_certificate)
    FrameLayout fCertificate;
    @BindView(R2.id.iv_video)
    ImageView ivVideo;
    @BindView(R2.id.tv_video)
    TextView tvVideo;
    @BindView(R2.id.f_video)
    FrameLayout fVideo;
    @BindView(R2.id.tv_shool)
    TextView tvShool;
    @BindView(R2.id.lin_school)
    LinearLayout linSchool;
    @BindView(R2.id.tv_age)
    TextView tvAge;
    @BindView(R2.id.lin_age)
    LinearLayout linAge;
    @BindView(R2.id.tv_city)
    TextView tvCity;
    @BindView(R2.id.lin_city)
    LinearLayout linCity;
    @BindView(R2.id.et_experience)
    EditText etExperience;
    @BindView(R2.id.lin_teacher)
    LinearLayout linTeacher;

    @BindView(R2.id.tv_size)
    TextView tvSize;
    @BindView(R2.id.btn_sure)
    Button btnSure;

    @BindView(R2.id.tv_tips)
    TextView tvTips;

    //1为学生
    private final int SET_PASSWORD = 1;
    //2为老师
    private final int CHANGE_PASSWORD = 2;
    //默认为学生
    private int TYPE = 1;


    @Override
    protected void onCreateView() {
        initTitle();
        initNoLinkOptionsPicker();
        initCityPicker();
        initView();

        initData();

    }

    private void initData() {

        String token = SharedPreferenceManager.getInstance(this).getUserCache().getUserToken();

        HttpUtils.with(this)
                .post()
                .addParam("requestType", "TEACHER_PERSONAL_INFO")
                .addParam("token", token)
                .execute(new HttpNormalCallBack<TeacherInfoBean>() {
                    @Override
                    public void onSuccess(TeacherInfoBean result) {


                        if (result.getCode() == CodeUtil.CODE_200) {

                            initTeacherData(result);

                        }


                    }

                    @Override
                    public void onError(String e) {

                    }
                });

    }


    private void initView() {
        etExperience.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String content = etExperience.getText().toString();
                tvSize.setText(content.length() + "/"
                        + 500);
            }

        });

    }

    protected void initTitle() {
        NomalNavigationBar navigationBar = new
                NomalNavigationBar.Builder(this)
                .setTitle("个人资料")
                .builder();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_teacher_user_info;
    }


    List<LocalMedia> lisId_Z = new ArrayList<>();
    List<LocalMedia> lisId_F = new ArrayList<>();
    List<LocalMedia> lisCertificate = new ArrayList<>();

    private final int SELECT_ID_Z = 1;
    private final int SELECT_ID_F = 2;
    //教师资格证
    private final int SELECT_CERTIFICATE = 3;
    //头像
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
        sexFragment.setSexChooseListener(new SexFragment.SexChooseListener() {
            @Override
            public void onSexChoose(String str) {
                showToast(str);
                dialog.dismiss();
            }
        });


    }

    //=============================================================================================
    //==============================================================================昵称=======
    //=============================================================================================

    NickNameFragment nickNameFragment;

    private void showNick() {


        AlertDialog dialog = new AlertDialog.Builder(this)
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

        nickNameFragment.setOnNomalChangeListener(new NickNameFragment.NormalChangeListener() {
            @Override
            public void onNomalChange(String str) {
                showToast(str);
            }
        });
    }
    //=============================================================================================
    //==============================================================================学校=======
    //=============================================================================================


    ChooseSchoolFragment schoolFragment;

    private void showSchool() {


        AlertDialog dialog = new AlertDialog.Builder(this)
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

        schoolFragment.setOnNomalChangeListener(new ChooseSchoolFragment.NormalChangeListener() {
            @Override
            public void onNomalChange(String str) {
                showToast(str);
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
                            case SELECT_ID_Z: {
                                ivIdZ.setImageBitmap(bitmap);
                                break;
                            }

                            case SELECT_ID_F: {
                                ivIdF.setImageBitmap(bitmap);
                                break;
                            }

                            case SELECT_CERTIFICATE: {

                                ivCertificate.setImageBitmap(bitmap);

                                break;
                            }

                            case SELECT_HEAD: {


                                ivHead.setImageBitmap(bitmap);
                                break;
                            }
                        }

                    }

                    break;
            }
        }

    }

    //=============================================================================================
    //==============================================================================城市选择=======
    //=============================================================================================

    private List<CityNameBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    OptionsPickerView cityPicker;

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
                showToast(tx);
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

    //=============================================================================================
    //==============================================================================教龄=======
    //=============================================================================================

    private String ageList[] = {
            "1年",
            "2年",
            "3年",
            "4年",
            "5年",
            "6年",
            "7年",
            "8年",
            "9年",
            "10年",
            "10年以上"


    };

    private List<String> ageArray = new ArrayList<>();
    OptionsPickerView agePicker;

    private void initNoLinkOptionsPicker() {// 不联动的多级选项


        ageArray.clear();
        for (String s : ageList) {
            ageArray.add(s);
        }

        agePicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                showToast(ageArray.get(options1));

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
                .setTitleText("教龄")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(getResources().getColor(R.color.base_title))//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.base_blue))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.base_blue))//取消按钮文字颜色
                .setTitleBgColor(getResources().getColor(R.color.white))//标题背景颜色 Night mode
                .setBgColor(getResources().getColor(R.color.base_white))//滚轮背景颜色 Night mode
                .setOutSideCancelable(true)
                .build();

        agePicker.setPicker(ageArray);


    }

    protected void addFragment(int containerId, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerId, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }


    //------------------------------------------------------------------------------------点击事件
    @OnClick({R2.id.lin_head, R2.id.lin_nickname, R2.id.lin_sex, R2.id.f_id_z, R2.id.f_id_f, R2.id.f_certificate, R2.id.f_video, R2.id.lin_school, R2.id.lin_age, R2.id.lin_city, R2.id.btn_sure})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.lin_head) {
        } else if (i == R.id.lin_nickname) {

            showNick();

        } else if (i == R.id.lin_sex) {
            showSex();

        } else if (i == R.id.f_id_z) {

            PhotoUtil.cutCertificatePicture(this, lisId_Z, 1);
            SELECT_TYPE = SELECT_ID_Z;

        } else if (i == R.id.f_id_f) {

            PhotoUtil.cutCertificatePicture(this, lisId_F, 1);
            SELECT_TYPE = SELECT_ID_F;

        } else if (i == R.id.f_certificate) {
            SELECT_TYPE = SELECT_CERTIFICATE;
            PhotoUtil.cutCertificatePicture(this, lisCertificate, 1);


        } else if (i == R.id.f_video) {
        } else if (i == R.id.lin_school) {
            showSchool();

        } else if (i == R.id.lin_age) {
            agePicker.show();

        } else if (i == R.id.lin_city) {
            cityPicker.show();

        } else if (i == R.id.btn_sure) {
        }
    }


    //----------------------------------------------------------------------------------初始化老师数据

    private void initTeacherData(TeacherInfoBean result) {

        Glide.with(this)
                .load(result.getObj().getPhoto_url())
                .apply(GlideConfig.getCircleOptions())
                .into(ivHead);


        tvNickname.setText(result.getObj().getName());


        if (result.getObj().getSex().equals("0")) {
            tvSex.setText("男");
        } else {
            tvSex.setText("女");
        }


        Glide.with(this)
                .load(result.getObj().getIdcard_p())
                .apply(GlideConfig.getRoundOptions(20))
                .into(ivIdZ);

        Glide.with(this)
                .load(result.getObj().getIdcard_n())
                .apply(GlideConfig.getRoundOptions(20))
                .into(ivIdF);


        Glide.with(this)
                .load(result.getObj().getCertificate())
                .apply(GlideConfig.getRoundOptions(20))
                .into(ivCertificate);


        Glide.with(this)
                .load(result.getObj().getSelf_video())
                .apply(GlideConfig.getRoundOptions(20))
                .into(ivCertificate);

        tvShool.setText(result.getObj().getSchool_name());

        tvAge.setText(result.getObj().getWork_year());

        tvCity.setText(result.getObj().getArea());

        etExperience.setText(result.getObj().getWork_exp());


    }


}
