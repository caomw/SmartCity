package com.dc.smartcity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
/**
 * 修改用户信息
 * @author szsm_dyj
 *
 */
public class ModifyUserInfoAct extends BaseActionBarActivity {

	//实名认证
	@ViewInject(R.id.tv_auth)
	private TextView tv_auth;
	//真实姓名
	@ViewInject(R.id.et_realname)
	private EditText et_realname;
	//居住地
	@ViewInject(R.id.et_live_place)
	private EditText et_live_place;
	//男
	@ViewInject(R.id.rb_sex_male)
	private RadioButton rb_sex_male;
	//女
	@ViewInject(R.id.rb_sex_female)
	private RadioButton rb_sex_female;
	//出生日期
	@ViewInject(R.id.tv_brithday)
	private TextView tv_brithday;
	//手机号码
	@ViewInject(R.id.tv_modify_phone)
	private TextView tv_modify_phone;
	//邮箱
	@ViewInject(R.id.tv_mail)
	private TextView tv_mail;
	
	@Override
	protected void setContentView() {
		setContentView(R.layout.act_modify_userinfo);
	}
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        initActionBar();
	        queryUserInfo();
	 }

	 
	 //查询用户信息
	private void queryUserInfo() {
		
	}

	private void initActionBar() {
		iv_actionbar_left.setVisibility(View.VISIBLE);
		setActionBarTitle(getString(R.string.title_user_info));
		
	}

	@OnClick(value={R.id.btnSave,R.id.tv_brithday})
	private void onClick(View v){
		switch (v.getId()) {
		case R.id.btnSave:
			
			break;
		case R.id.tv_brithday:
			
			break;
		default:
			break;
		}
	}
	
}
