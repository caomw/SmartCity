package com.dc.smartcity.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnRadioGroupCheckedChange;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.base.BaseFragment;
import com.dc.smartcity.fragment.HomeAskFragment;
import com.dc.smartcity.fragment.HomeMyFragment;
import com.dc.smartcity.fragment.HomePageFragment;
import com.dc.smartcity.util.ULog;
import com.dc.smartcity.util.Utils;

/**
 * 首页的4个fragment
 */
public class MainActivity extends BaseActionBarActivity implements OnCheckedChangeListener {
    /**
     * 再按一次退出应用
     */
    private long exitTime = 0;

    @ViewInject(R.id.rg_menu)
    public RadioGroup rg_menu;
    @ViewInject(R.id.rb_menu_service)
    public RadioButton rb_menu_service;
    @ViewInject(R.id.rb_menu_ask)
    public RadioButton rb_menu_ask;
    @ViewInject(R.id.rb_menu_my)
    private RadioButton rb_menu_my;
    private RadioButton mCurrentButton;

    /**
     * 当前显示的fragment
     */
    private BaseFragment mCurrentFragment;
    /**
     * 服务超市fragment
     */
    private BaseFragment mHomeFragment;
    /**
     * 有问必答fragment
     */
    private BaseFragment mAskFragment;
    /**
     * 我的fragment
     */
    private BaseFragment mMyFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        ULog.debug("--->onCreate");
        rb_menu_service.setChecked(true);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }


    private void initActionBar() {
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
                    mHomeFragment = new HomePageFragment();
                    fragmentTransaction.add(R.id.ll_fragment_container, mHomeFragment);
                }
                mCurrentFragment = mHomeFragment;
                mCurrentButton = rb_menu_service;
                break;
            case R.id.rb_menu_ask:
                if (mAskFragment != null) {
                    fragmentTransaction.show(mAskFragment);
                } else {
                    mAskFragment = new HomeAskFragment();
                    fragmentTransaction.add(R.id.ll_fragment_container, mAskFragment);
                }
                mCurrentFragment = mAskFragment;
                mCurrentButton = rb_menu_ask;
                break;
            case R.id.rb_menu_my:
                if (mMyFragment != null) {
                    fragmentTransaction.show(mMyFragment);
                } else {
                    mMyFragment = new HomeMyFragment();
                    fragmentTransaction.add(R.id.ll_fragment_container, mMyFragment);
                }
                mCurrentFragment = mMyFragment;
                mCurrentButton = rb_menu_my;
                break;
            default:
                break;
        }

        fragmentTransaction.commitAllowingStateLoss();
        ULog.debug("---->onCheckedChanged()");
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
