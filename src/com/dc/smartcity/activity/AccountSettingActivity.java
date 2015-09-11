package com.dc.smartcity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.bean.user.UserAuthBean;
import com.dc.smartcity.bean.user.UserBaseBean;
import com.dc.smartcity.bean.user.UserLocalBean;
import com.dc.smartcity.bean.user.UserObj;
import com.dc.smartcity.dialog.DialogConfig;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.util.DateTools;
import com.dc.smartcity.util.Utils;

/**
 * 安全设置
 * Created by vincent on 2015/8/18.
 * edit by dyj on 2015/08/20
 */
public class AccountSettingActivity extends BaseActionBarActivity {

    //用户名
    @ViewInject(R.id.tv_username)
    private TextView tv_username;
    //手机号码
    @ViewInject(R.id.tv_mobile)
    private TextView tv_mobile;
    //邮箱
    @ViewInject(R.id.tv_mail)
    private TextView tv_mail;
    //上次登录
    @ViewInject(R.id.tv_lastlogintime)
    private TextView tv_lastlogintime;
    //实名认证
    @ViewInject(R.id.tv_authname)
    private TextView tv_authname;
    //实名认证按钮
    @ViewInject(R.id.btn_auth)
    private TextView btn_auth;
    //绑定手机号按钮
    @ViewInject(R.id.btn_bind)
    private TextView btn_bind;
    //登陆密码强度
    @ViewInject(R.id.tv_loadpass)
    private TextView tv_loadpass;
//	//登陆密码强度按钮
//	@ViewInject(R.id.btn_change_pass)
//	private TextView btn_change_pass;

    @Override
    protected void setContentView() {
        setContentView(R.layout.act_safe_setting);
    }

    @Override
    public void onResume() {
        super.onResume();
        //刷新本地保存的用户数据
        sendRequestWithDialog(RequestPool.requestUserInfo(), new DialogConfig.Builder().build(), new RequestProxy() {

            @Override
            public void onSuccess(String msg, String result) {
                JSONObject obj = JSON.parseObject(result);
                UserObj user = new UserObj();
                user.userBase = JSON.parseObject(obj.getString("USERBASIC"), UserBaseBean.class);
                user.userAuth = JSON.parseObject(obj.getString("USERAUTH"), UserAuthBean.class);
                user.userLocal = JSON.parseObject(obj.getString("LOCALUSER"), UserLocalBean.class);
                Utils.user = user;
                initUserInfo();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        initUserInfo();
    }

    private void initUserInfo() {
        if (null != Utils.user) {

            if (null != Utils.user.userBase && !TextUtils.isEmpty(Utils.user.userBase.name)) {
                tv_username.setText(Utils.user.userBase.login);
            }
            if (null != Utils.user.userAuth && !TextUtils.isEmpty(Utils.user.userAuth.mobilenum)) {
                tv_mobile.setText(Utils.mixPhone(Utils.user.userAuth.mobilenum));
            }
            if (null != Utils.user.userAuth && !TextUtils.isEmpty(Utils.user.userAuth.email)) {
                tv_mail.setText(Utils.user.userAuth.email);
            } else {
                tv_mail.setText("--");
            }
            if (null != Utils.user.userBase && !TextUtils.isEmpty(Utils.user.userBase.lastlogintime)) {
                tv_lastlogintime.setText(DateTools.formatDateTime(Utils.user.userBase.lastlogintime));
            }
            if (!Utils.isRealName()) {
                tv_authname.setText(R.string.tv_unauth);
            } else {
                tv_authname.setText(Utils.getRealNameStatus());
                btn_auth.setVisibility(View.GONE);
            }

            if (null != Utils.user.userAuth && "1".equals(Utils.user.userAuth.pwdstrength)) {
                tv_loadpass.setText("强度(弱)");
            } else if ((null != Utils.user.userAuth && "2".equals(Utils.user.userAuth.pwdstrength))) {
                tv_loadpass.setText("强度(强)");
            } else {
                tv_loadpass.setText("强度(中)");
            }
        }
    }

    private void initActionBar() {
        iv_actionbar_left.setVisibility(View.VISIBLE);
        setActionBarTitle("安全设置");
    }

    @OnClick(value = {R.id.btn_auth, R.id.btn_bind, R.id.btn_change_pass})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_auth:
                startActivity(new Intent(this, AuthAct.class));
                break;
            case R.id.btn_bind:
                startActivity(new Intent(this, ChangeBindMobileAct.class));
                break;
            case R.id.btn_change_pass:
                startActivity(new Intent(this, ChangePasswordAct.class));
                finish();
                break;

            default:
                break;
        }
    }
}
