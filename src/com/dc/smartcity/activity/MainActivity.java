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
import com.dc.smartcity.fragment.HomeMyFragment;
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
    @ViewInject(R.id.rb_menu_mian)
    public RadioButton rb_menu_mian;
    @ViewInject(R.id.rb_menu_pay)
    public RadioButton rb_menu_pay;
    @ViewInject(R.id.rb_menu_ball_friend)
    public RadioButton rb_menu_ball_friend;
    @ViewInject(R.id.rb_menu_my)
    private RadioButton rb_menu_my;
    private RadioButton mCurrentButton;

    /**
     * 当前显示的fragment
     */
    private BaseFragment mCurrentFragment;
    /**
     * 首页fragment
     */
    private BaseFragment mHomeFragment;
    /**
     * 充值fragment
     */
    private BaseFragment mPayFragment;
    /**
     * fragment
     */
    private BaseFragment mBallfriendFragment;
    /**
     * 我的fragment
     */
    private BaseFragment mMyFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        ULog.debug("--->onCreate");
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
            case R.id.rb_menu_mian:
                if (mHomeFragment != null) {
                    fragmentTransaction.show(mHomeFragment);
                } else {
                    mHomeFragment = new HomeMyFragment();
                    fragmentTransaction.add(R.id.ll_fragment_container, mHomeFragment);
                }
                mCurrentFragment = mHomeFragment;
                mCurrentButton = rb_menu_mian;
                break;
            case R.id.rb_menu_pay:
                if (mPayFragment != null) {
                    fragmentTransaction.show(mPayFragment);
//                    mPayFragment.initData();
                } else {
                    mPayFragment = new HomeMyFragment();
                    fragmentTransaction.add(R.id.ll_fragment_container, mPayFragment);
                }
                mCurrentFragment = mPayFragment;
                mCurrentButton = rb_menu_pay;
                break;
            case R.id.rb_menu_ball_friend:

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
