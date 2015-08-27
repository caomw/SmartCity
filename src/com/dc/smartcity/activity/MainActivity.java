package com.dc.smartcity.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.alibaba.fastjson.JSON;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnRadioGroupCheckedChange;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.base.BaseFragment;
import com.dc.smartcity.fragment.HomeAskFragment;
import com.dc.smartcity.fragment.HomeMyFragment;
import com.dc.smartcity.fragment.HomeNewsFragment;
import com.dc.smartcity.fragment.HomePageFragment;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.update.UpdateAg;
import com.dc.smartcity.update.UpdateBean;
import com.dc.smartcity.update.UpdateManager;
import com.dc.smartcity.util.Utils;

public class MainActivity extends BaseActionBarActivity implements OnCheckedChangeListener {
    /**
     * 再按一次退出应用
     */
    private long exitTime = 0;

    @ViewInject(R.id.rb_menu_service)
    public RadioButton rb_menu_service;
    @ViewInject(R.id.rb_menu_ask)
    public RadioButton rb_menu_ask;
    @ViewInject(R.id.rb_menu_my)
    private RadioButton rb_menu_my;
    @ViewInject(R.id.rb_menu_news)
    private RadioButton rb_menu_news;
    private RadioButton mCurrentButton;

    /**
     * 当前显示的fragment
     */
    private BaseFragment mCurrentFragment;
    /**
     * 服务超市fragment
     */
    private HomePageFragment mHomeFragment;
    /**
     * 有问必答fragment
     */
    private HomeAskFragment mAskFragment;
    /**
     * 我的fragment
     */
    private HomeMyFragment mMyFragment;
    /**
     * 新闻fragment
     */
    private HomeNewsFragment mServiceFragment;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rb_menu_service.setChecked(true);
        UpdateAg.init();
        checkUpdate();
    }

    //检查更新
    private void checkUpdate() {
		sendRequestWithNoDialog(RequestPool.checkUpdate(this), new RequestProxy() {
			
			@Override
			public void onSuccess(String msg, String result) {
				UpdateAg.update(MainActivity.this, result);
			}
		});
	}

	@Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnRadioGroupCheckedChange(R.id.rg_menu)
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentFragment != null) {
            mCurrentFragment.onPause();
            fragmentTransaction.hide(mCurrentFragment);
        }

        switch (checkedId) {
            case R.id.rb_menu_service:
                if (mHomeFragment != null) {
                    fragmentTransaction.show(mHomeFragment);
                } else {
                    mHomeFragment = new HomePageFragment(mActionBar);
                    fragmentTransaction.add(R.id.ll_fragment_container, mHomeFragment);
                }
                mCurrentFragment = mHomeFragment;
                mCurrentButton = rb_menu_service;
                break;
            case R.id.rb_menu_ask:
                if (mAskFragment != null) {
                    fragmentTransaction.show(mAskFragment);
                } else {
                    mAskFragment = new HomeAskFragment(mActionBar);
                    fragmentTransaction.add(R.id.ll_fragment_container, mAskFragment);
                }
                mCurrentFragment = mAskFragment;
                mCurrentButton = rb_menu_ask;
                break;
            case R.id.rb_menu_my:
            	if (mMyFragment != null) {
                    fragmentTransaction.show(mMyFragment);
                } else {
                    mMyFragment = new HomeMyFragment(mActionBar);
                    fragmentTransaction.add(R.id.ll_fragment_container, mMyFragment);
                }
                mCurrentFragment = mMyFragment;
                mCurrentButton = rb_menu_my;
                break;
            case R.id.rb_menu_news:
            	 if (mServiceFragment != null) {
                     fragmentTransaction.show(mServiceFragment);
                 } else {
                	 mServiceFragment = new HomeNewsFragment(mActionBar);
                     fragmentTransaction.add(R.id.ll_fragment_container, mServiceFragment);
                 }
                 mCurrentFragment = mServiceFragment;
                 mCurrentButton = rb_menu_news;
            	break;
            default:
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Utils.showToast(mContext.getResources().getString(R.string.press_more_exit), this);
                    exitTime = System.currentTimeMillis();
                } else {
                    MainActivity.this.finish();
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mCurrentButton) {
            mCurrentButton.setChecked(true);
        }
    }
}
