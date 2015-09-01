package com.dc.smartcity.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.bean.user.*;
import com.dc.smartcity.dialog.DatePickDlg;
import com.dc.smartcity.dialog.DatePickDlg.IDateChange;
import com.dc.smartcity.dialog.DialogConfig;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.util.DateTools;
import com.dc.smartcity.util.Utils;

/**
 * 修改用户信息
 *
 * @author szsm_dyj
 */
public class ModifyUserInfoAct extends BaseActionBarActivity implements
        IDateChange, OnCheckedChangeListener {

    // 实名认证
    @ViewInject(R.id.tv_auth)
    private TextView tv_auth;
    // 真实姓名
    @ViewInject(R.id.et_realname)
    private EditText et_realname;
    // 居住地
    @ViewInject(R.id.et_live_place)
    private EditText et_live_place;
    // 男
    @ViewInject(R.id.rb_sex_male)
    private RadioButton rb_sex_male;
    // 女
    @ViewInject(R.id.rb_sex_female)
    private RadioButton rb_sex_female;
    // 出生日期
    @ViewInject(R.id.tv_brithday)
    private TextView tv_brithday;
    // 手机号码
    @ViewInject(R.id.tv_modify_phone)
    private TextView tv_modify_phone;
    // 邮箱
    @ViewInject(R.id.tv_mail)
    private TextView tv_mail;
    // 邮箱
    @ViewInject(R.id.rg_sex)
    private RadioGroup rg_sex;

    ModifyUserBean bean;
    boolean hasModify;

    @Override
    protected void setContentView() {
        setContentView(R.layout.act_modify_userinfo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();
        bean = new ModifyUserBean();
        updateUserInfo();
    }

    // 查询用户信息
    private void updateUserInfo() {
        if (!Utils.isLogon()) {
            return;
        }
        if (isNotAuth()) {
            tv_auth.setText("未认证");
            rg_sex.setOnCheckedChangeListener(this);
            et_realname.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    bean.name = s.toString().trim();
                    hasModify = true;
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else {
            tv_auth.setText("已认证");
            et_realname.setEnabled(false);

            rb_sex_female.setClickable(false);
            rb_sex_male.setClickable(false);
        }
        if (null != Utils.user.userLocal
                && !TextUtils.isEmpty(Utils.user.userLocal.residence)) {

            et_live_place.setText(Utils.user.userLocal.residence);
        }
        if (!TextUtils.isEmpty(Utils.user.userBase.name)) {
            et_realname.setText(Utils.user.userBase.name);
        }
        if ("02".equals(Utils.user.userBase.sex)) {
            rb_sex_female.setChecked(true);
        } else {
            rb_sex_male.setChecked(true);
        }
        tv_brithday.setText(DateTools.formatDate(Utils.user.userBase.birthday));
        if (!TextUtils.isEmpty(Utils.user.userAuth.email)) {

            tv_mail.setText(Utils.user.userAuth.email);
        } else {
            tv_mail.setText("--");
        }
        tv_modify_phone.setText(Utils.mixPhone(Utils.user.userAuth.mobilenum));
        et_live_place.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                bean.residence = s.toString().trim();
                hasModify = true;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean isNotAuth() {
        return "01".equals(Utils.user.userBase.level);
    }

    private void initActionBar() {
        iv_actionbar_left.setVisibility(View.VISIBLE);
        setActionBarTitle(getString(R.string.title_user_info));
    }

    @OnClick(value = {R.id.btnSave, R.id.tv_brithday})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                saveUserChange();
                break;
            case R.id.tv_brithday:
                if (isNotAuth()) {
                    showDatePicker();
                }
                break;
            default:
                break;
        }
    }

    private void saveUserChange() {
        if (hasModify) {
            sendRequestWithDialog(RequestPool.requestModifyUserInfo(bean),
                    new DialogConfig.Builder().build(), new RequestProxy() {

                        @Override
                        public void onSuccess(String msg, String result) {
                            queryUserInfo();
                        }

                    });
        } else {
            Utils.showToast("修改成功", ModifyUserInfoAct.this);
            finish();
        }
    }

    /**
     * 修改完信息之后，更新一下用户信息
     */
    private void queryUserInfo() {
        sendRequestWithDialog(RequestPool.requestUserInfo(),
                new DialogConfig.Builder().build(), new RequestProxy() {

                    @Override
                    public void onSuccess(String msg, String result) {
                        JSONObject obj = JSON.parseObject(result);
                        UserObj user = new UserObj();
                        user.userBase = JSON.parseObject(
                                obj.getString("USERBASIC"), UserBaseBean.class);
                        user.userAuth = JSON.parseObject(
                                obj.getString("USERAUTH"), UserAuthBean.class);
                        user.userLocal = JSON.parseObject(
                                obj.getString("LOCALUSER"), UserLocalBean.class);
                        if (null != user.userBase) {
                            Utils.user = user;
                        }
                        Utils.showToast("修改成功", ModifyUserInfoAct.this);
                        finish();
                    }
                });
    }

    private DatePickDlg pickDlg;

    private void showDatePicker() {
        if (null == pickDlg) {
            pickDlg = new DatePickDlg(this, this);
        }
        pickDlg.show();

    }

    @Override
    public void notifyDateChange(String time) {
        bean.birth = time;
        hasModify = true;
        tv_brithday.setText(time);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        hasModify = true;
        switch (checkedId) {
            case R.id.rb_sex_female:
                bean.sex = "02";
                break;
            case R.id.rb_sex_male:
                bean.sex = "01";
                break;

            default:
                break;
        }
    }

}
