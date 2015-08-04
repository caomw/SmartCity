package com.dc.smartcity.view.advertisement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.*;
import com.dc.smartcity.R;
import com.dc.smartcity.bean.AdObj;
import com.sport365.badminton.activity.MyWebViewActivity;
import com.sport365.badminton.utils.BundleKeys;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 广告专用.
 */
public class AdvertisementView extends BaseImageSwitcher<AdObj> {

	private String mEventId;
	private String mParam;
	private int tagLevel = 0;

	public AdvertisementView(Context context) {
		super(context);
		setScreenRate(8, 3);
	}

	public AdvertisementView(Context context, int tagLevel) {
		super(context);
		this.tagLevel = tagLevel;
		setScreenRate(8, 3);
	}

	public void setAdvertisementData(ArrayList<AdObj> datas) {
		setData(datas);
	}

	/**
	 * 广告的比例
	 *
	 * @param widthRat
	 *            宽度
	 * @param heightRat
	 *            高度
	 */
	public void setAdvertisementRate(int widthRat, int heightRat) {
		setScreenRate(widthRat, heightRat);
	}

	public void setEventId(String eventId, String param) {
		this.mEventId = eventId;
		this.mParam = param;
	}

	@Override
	protected boolean performItemClick(AdapterView<?> parent, View view, int position, long id, int true_position) {
		if (!super.performItemClick(parent, view, position, id, true_position)) {

			final AdObj data_obj = mDatas.get(true_position);

			if (data_obj != null && !TextUtils.isEmpty(data_obj.redirectUrl)) {
				String tag = data_obj.tag;
				// NoticeTools.initNoticeUrl((Activity) mContext,
				// data_obj.redirectUrl, "");
				// NoticeTools替换 by Megatron King
				// URLPaserUtils.parseURL((Activity) mContext,
				// data_obj.redirectUrl);
				Toast.makeText(getContext(), "点击事件", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(getContext(), MyWebViewActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(BundleKeys.WEBVIEEW_LOADURL, data_obj.redirectUrl);
				bundle.putString(BundleKeys.WEBVIEEW_TITLE, "详情");
				intent.putExtras(bundle);
				getContext().startActivity(intent);

			}
		}
		return false;
	}

	@Override
	public String getUrlString(AdObj data) {
		return data.imageUrl;
	}

	/**
     * 下拉刷新ListView
     *
     * @author check_000
     */
    public static class SwipeDListView extends ListView implements AbsListView.OnScrollListener {

        private static final String TAG = SwipeDListView.class.getSimpleName();

        private float mFirstX;

        private float mFirstY;

        private LayoutInflater mInflater;

        private LinearLayout mHeadView;

        private TextView mTipsTextView;

        private TextView mLastUpdatedTextView;

        private ImageView mArrowImageView;

        private ProgressBar mProgressBar;

        private View mEndRootView;

        private ProgressBar mEndLoadProgressBar;

        private TextView mEndLoadTipsTextView;

        /**
         * headView动画
         */
        private RotateAnimation mArrowAnim;

        /**
         * headView反转动画
         */
        private RotateAnimation mArrowReverseAnim;

        /**
         * 用于保证startY的值在一个完整的touch事件中只被记录一次
         */
        private boolean mIsRecored;

        private int mHeadViewWidth;

        private int mHeadViewHeight;

        private int mStartY;

        private boolean mIsBack;

        private int mFirstItemIndex;

        private int mLastItemIndex;

        private int mCount;

        public SwipeDListView(Context context) {
            super(context);
            init(context);
        }

        public SwipeDListView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context);
        }

        public SwipeDListView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init(context);
        }

        /**
         * 初始化操作
         *
         * @param pContext
         * @date 2013-11-20 下午4:10:46
         * @change JohnWatson
         * @version 1.0
         */
        private void init(Context pContext) {
            setCacheColorHint(pContext.getResources().getColor(R.color.list_selected));
            mInflater = LayoutInflater.from(pContext);

            addHeadView();

            setOnScrollListener(this);

            initPullImageAnimation(0);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            float lastX = ev.getX();
            float lastY = ev.getY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    // mIsHorizontal = false;

                    mFirstX = lastX;
                    mFirstY = lastY;
                    int motionPosition = pointToPosition((int) mFirstX, (int) mFirstY);

                    Log.e(TAG, "onInterceptTouchEvent----->ACTION_DOWN position=" + motionPosition);

                    break;

                case MotionEvent.ACTION_MOVE:
                    float dx = lastX - mFirstX;
                    float dy = lastY - mFirstY;

                    if (Math.abs(dx) >= 5 && Math.abs(dy) >= 5) {
                        return true;
                    }
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:

                    Log.i(TAG, "onInterceptTouchEvent----->ACTION_UP");
                    break;
            }

            return super.onInterceptTouchEvent(ev);
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            if (mCanLoadMore && mEndState == ENDINT_LOADING) {
                // 如果存在加载更多功能，并且当前正在加载更多，默认不允许下拉刷新，必须加载完毕后才能使用。
                return super.onTouchEvent(ev);
            }

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mFirstItemIndex == 0 && !mIsRecored) {
                        mIsRecored = true;
                        mStartY = (int) ev.getY();
                    }
                    break;

                case MotionEvent.ACTION_MOVE:
                    int tempY = (int) ev.getY();

                    if (!mIsRecored && mFirstItemIndex == 0) {
                        mIsRecored = true;
                        mStartY = tempY;
                    }

                    if (mHeadState != REFRESHING && mIsRecored && mHeadState != LOADING) {

                        // 保证在设置padding的过程中，当前的位置一直是在head，
                        // 否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动
                        // 可以松手去刷新了
                        if (mHeadState == RELEASE_TO_REFRESH) {

                            setSelection(0);

                            // 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
                            if (((tempY - mStartY) / RATIO < mHeadViewHeight) && (tempY - mStartY) > 0) {
                                mHeadState = PULL_TO_REFRESH;
                                changeHeaderViewByState();
                            }
                            // 一下子推到顶了
                            else if (tempY - mStartY <= 0) {
                                mHeadState = DONE;
                                changeHeaderViewByState();
                            }
                            // 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
                        }
                        // 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
                        if (mHeadState == PULL_TO_REFRESH) {

                            setSelection(0);

                            // 下拉到可以进入RELEASE_TO_REFRESH的状态
                            if ((tempY - mStartY) / RATIO >= mHeadViewHeight) {
                                mHeadState = RELEASE_TO_REFRESH;
                                mIsBack = true;
                                changeHeaderViewByState();
                            } else if (tempY - mStartY <= 0) {
                                mHeadState = DONE;
                                changeHeaderViewByState();
                            }
                        }

                        if (mHeadState == DONE) {
                            if (tempY - mStartY > 0) {
                                mHeadState = PULL_TO_REFRESH;
                                changeHeaderViewByState();
                            }
                        }

                        if (mHeadState == PULL_TO_REFRESH) {
                            mHeadView.setPadding(0, -1 * mHeadViewHeight + (tempY - mStartY) / RATIO, 0, 0);

                        }

                        if (mHeadState == RELEASE_TO_REFRESH) {
                            mHeadView.setPadding(0, (tempY - mStartY) / RATIO - mHeadViewHeight, 0, 0);
                        }
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    if (mHeadState != REFRESHING && mHeadState != LOADING) {
                        if (mHeadState == DONE) {

                        }
                        if (mHeadState == PULL_TO_REFRESH) {
                            mHeadState = DONE;
                            changeHeaderViewByState();
                        }
                        if (mHeadState == RELEASE_TO_REFRESH) {
                            mHeadState = REFRESHING;
                            changeHeaderViewByState();
                            onRefresh();
                        }
                    }

                    mIsRecored = false;
                    mIsBack = false;
                    break;
            }

            return super.onTouchEvent(ev);
        }

        /**
         * 正在下拉刷新
         *
         * @date 2013-11-20 下午4:45:47
         * @change JohnWatson
         * @version 1.0
         */
        private void onRefresh() {
            if (mRefreshListener != null) {
                mRefreshListener.onRefresh();
            }
        }

        /**
         * 当HeadView状态改变时候，调用该方法，以更新界面
         *
         * @date 2013-11-20 下午4:29:44
         * @change JohnWatson
         * @version 1.0
         */
        private void changeHeaderViewByState() {

            switch (mHeadState) {
                case RELEASE_TO_REFRESH:
                    mArrowImageView.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    mTipsTextView.setVisibility(View.VISIBLE);
                    mLastUpdatedTextView.setVisibility(View.VISIBLE);

                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mArrowAnim);
                    // 松开刷新
                    mTipsTextView.setText(R.string.p2refresh_release_refresh);

                    break;
                case PULL_TO_REFRESH:
                    mProgressBar.setVisibility(View.GONE);
                    mTipsTextView.setVisibility(View.VISIBLE);
                    mLastUpdatedTextView.setVisibility(View.VISIBLE);
                    mArrowImageView.clearAnimation();
                    mArrowImageView.setVisibility(View.VISIBLE);
                    // 是由RELEASE_To_REFRESH状态转变来的
                    if (mIsBack) {
                        mIsBack = false;
                        mArrowImageView.clearAnimation();
                        mArrowImageView.startAnimation(mArrowReverseAnim);
                        // 下拉刷新
                        mTipsTextView.setText(R.string.p2refresh_pull_to_refresh);
                    } else {
                        // 下拉刷新
                        mTipsTextView.setText(R.string.p2refresh_pull_to_refresh);
                    }
                    break;

                case REFRESHING:
                    mHeadView.setPadding(0, 0, 0, 0);

                    // 华生的建议：
                    // 实际上这个的setPadding可以用动画来代替。我没有试，但是我见过。其实有的人也用Scroller可以实现这个效果，
                    // 我没时间研究了，后期再扩展，这个工作交给小伙伴你们啦~ 如果改进了记得发到我邮箱噢~
                    // 本人邮箱： xxzhaofeng5412@gmail.com

                    mProgressBar.setVisibility(View.VISIBLE);
                    mArrowImageView.clearAnimation();
                    mArrowImageView.setVisibility(View.GONE);
                    // 正在刷新...
                    mTipsTextView.setText(R.string.p2refresh_doing_head_refresh);
                    mLastUpdatedTextView.setVisibility(View.VISIBLE);

                    break;
                case DONE:
                    mHeadView.setPadding(0, -1 * mHeadViewHeight, 0, 0);

                    // 此处可以改进，同上所述。

                    mProgressBar.setVisibility(View.GONE);
                    mArrowImageView.clearAnimation();
                    mArrowImageView.setImageResource(R.drawable.ssdk_oks_ptr_ptr);
                    // 下拉刷新
                    mTipsTextView.setText(R.string.p2refresh_pull_to_refresh);
                    mLastUpdatedTextView.setVisibility(View.VISIBLE);

                    break;
            }
        }

        /**
         * 显示格式化日期模板
         */
        private final static String DATE_FORMAT_STR = "yyyy年MM月dd日 HH:mm";

        /**
         * 实际的padding的距离与界面上偏移距离的比例
         */
        private final static int RATIO = 3;

        private final static int RELEASE_TO_REFRESH = 0;

        private final static int PULL_TO_REFRESH = 1;

        private final static int REFRESHING = 2;

        private final static int DONE = 3;

        private final static int LOADING = 4;

        /**
         * 加载中
         */
        private final static int ENDINT_LOADING = 1;

        /**
         * 手动完成刷新
         */
        private final static int ENDINT_MANUAL_LOAD_DONE = 2;

        /**
         * 自动完成刷新
         */
        private final static int ENDINT_AUTO_LOAD_DONE = 3;

        /**
         * 0:RELEASE_TO_REFRESH;
         * <p>
         * 1:PULL_To_REFRESH;
         * <p>
         * 2:REFRESHING;
         * <p>
         * 3:DONE;
         * <p>
         * 4:LOADING
         */
        private int mHeadState;

        /**
         * 0:完成/等待刷新 ;
         * <p>
         * 1:加载中
         */
        private int mEndState;

        /**
         * 可以加载更多？
         */
        private boolean mCanLoadMore = false;

        /**
         * 可以下拉刷新？
         */
        private boolean mCanRefresh = false;

        /**
         * 可以自动加载更多吗？（注意，先判断是否有加载更多，如果没有，这个flag也没有意义）
         */
        private boolean mIsAutoLoadMore = true;

        // /** 下拉刷新后是否显示第一条Item */
        // private boolean mIsMoveToFirstItemAfterRefresh = false;

        public boolean isCanLoadMore() {
            return mCanLoadMore;
        }

        public void setCanLoadMore(boolean mCanLoadMore) {
            try {

                this.mCanLoadMore = mCanLoadMore;
                if (!mCanLoadMore) {
                    removeFooterView(mEndRootView);
                } else if (this.getFooterViewsCount() == 0) {
                    addFooterView();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void setCanRefresh(boolean mCanRefresh) {
            try {
                this.mCanRefresh = mCanRefresh;
                if (!mCanRefresh && getHeaderViewsCount() > 0) {

                    this.removeHeaderView(mHeadView);
                } else if (this.getHeaderViewsCount() == 0) {
                    addHeadView();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onScroll(AbsListView pView, int pFirstVisibleItem, int pVisibleItemCount, int pTotalItemCount) {
            mFirstItemIndex = pFirstVisibleItem;
            mLastItemIndex = pFirstVisibleItem + pVisibleItemCount - 2;
            mCount = pTotalItemCount - 2;
            // if (pTotalItemCount > pVisibleItemCount) {
            // mEnoughCount = true;
            // } else {
            // mEnoughCount = false;
            // }
        }

        @Override
        public void onScrollStateChanged(AbsListView pView, int pScrollState) {

            if (mCanLoadMore) {// 存在加载更多功能
                if (mLastItemIndex == mCount && pScrollState == SCROLL_STATE_IDLE) {
                    // SCROLL_STATE_IDLE=0，滑动停止
                    if (mEndState != ENDINT_LOADING) {
                        if (mIsAutoLoadMore) {// 自动加载更多，我们让FootView显示 “更 多”
                            if (mCanRefresh) {
                                // 存在下拉刷新并且HeadView没有正在刷新时，FootView可以自动加载更多。
                                if (mHeadState != REFRESHING) {
                                    // FootView显示 : 更 多 ---> 加载中...
                                    mEndState = ENDINT_LOADING;
                                    onLoadMore();
                                    changeEndViewByState();
                                }
                            } else {// 没有下拉刷新，我们直接进行加载更多。
                                // FootView显示 : 更 多 ---> 加载中...
                                mEndState = ENDINT_LOADING;
                                onLoadMore();
                                changeEndViewByState();
                            }
                        } else {// 不是自动加载更多，我们让FootView显示 “点击加载”
                            // FootView显示 : 点击加载 ---> 加载中...
                            mEndState = ENDINT_MANUAL_LOAD_DONE;
                            changeEndViewByState();
                        }
                    }
                }
            } else if (mEndRootView != null && mEndRootView.getVisibility() == VISIBLE) {
                try {
                    mEndRootView.setVisibility(View.GONE);
                    removeFooterView(mEndRootView);
                } catch (Exception e) {
                }

            }
        }

        /**
         * 实例化下拉刷新的箭头的动画效果
         *
         * @param pAnimDuration 动画运行时长
         * @date 2013-11-20 上午11:53:22
         * @change JohnWatson
         * @version 1.0
         */
        private void initPullImageAnimation(final int pAnimDuration) {

            int _Duration;

            if (pAnimDuration > 0) {
                _Duration = pAnimDuration;
            } else {
                _Duration = 250;
            }

            Interpolator _Interpolator = new LinearInterpolator();

            mArrowAnim = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            mArrowAnim.setInterpolator(_Interpolator);
            mArrowAnim.setDuration(_Duration);
            mArrowAnim.setFillAfter(true);

            mArrowReverseAnim = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            mArrowReverseAnim.setInterpolator(_Interpolator);
            mArrowReverseAnim.setDuration(_Duration);
            mArrowReverseAnim.setFillAfter(true);
        }

        /**
         * 正在加载更多，FootView显示 ： 加载中...
         *
         * @date 2013-11-20 下午4:35:51
         * @change JohnWatson
         * @version 1.0
         */
        private void onLoadMore() {
            if (mLoadMoreListener != null) {
                mEndRootView.setVisibility(View.VISIBLE);
                // 加载中...
                mEndLoadTipsTextView.setText(R.string.p2refresh_doing_end_refresh);
                mEndLoadTipsTextView.setVisibility(View.VISIBLE);
                mEndLoadProgressBar.setVisibility(View.VISIBLE);

                mLoadMoreListener.onLoadMore();
            }
        }

        /**
         * 下拉刷新监听接口
         *
         * @version 1.0
         * @date 2013-11-20 下午4:50:51
         * @change JohnWatson
         */
        public interface OnRefreshListener {

            public void onRefresh();
        }

        /**
         * 加载更多监听接口
         *
         * @version 1.0
         * @date 2013-11-20 下午4:50:51
         * @change JohnWatson
         */
        public interface OnLoadMoreListener {

            public void onLoadMore();
        }

        private OnRefreshListener mRefreshListener;

        private OnLoadMoreListener mLoadMoreListener;

        public void setOnRefreshListener(OnRefreshListener pRefreshListener) {
            if (pRefreshListener != null) {
                mCanRefresh = true;
            } else {
                mCanRefresh = false;
            }
            mRefreshListener = pRefreshListener;
        }

        public void setOnLoadListener(OnLoadMoreListener pLoadMoreListener) {
            if (null != pLoadMoreListener) {
                mLoadMoreListener = pLoadMoreListener;
                mCanLoadMore = true;
                if (mCanLoadMore && getFooterViewsCount() == 0) {
                    addFooterView();
                }
            } else {
                mCanLoadMore = false;
                if (mCanLoadMore && getFooterViewsCount() != 0) {
                    mEndRootView.setVisibility(View.GONE);
                }
            }
        }

        /**
         * 添加下拉刷新的HeadView
         *
         * @date 2013-11-11 下午9:48:26
         * @change JohnWatson
         * @version 1.0
         */
        private void addHeadView() {
            mHeadView = (LinearLayout) mInflater.inflate(R.layout.head, null);

            mArrowImageView = (ImageView) mHeadView.findViewById(R.id.head_arrowImageView);
            mArrowImageView.setMinimumWidth(70);
            mArrowImageView.setMinimumHeight(50);
            mProgressBar = (ProgressBar) mHeadView.findViewById(R.id.head_progressBar);
            mTipsTextView = (TextView) mHeadView.findViewById(R.id.head_tipsTextView);
            mLastUpdatedTextView = (TextView) mHeadView.findViewById(R.id.head_lastUpdatedTextView);

            measureView(mHeadView);
            mHeadViewHeight = mHeadView.getMeasuredHeight();
            mHeadViewWidth = mHeadView.getMeasuredWidth();

            mHeadView.setPadding(0, -1 * mHeadViewHeight, 0, 0);
            mHeadView.invalidate();

            Log.v("size", "width:" + mHeadViewWidth + " height:" + mHeadViewHeight);

            addHeaderView(mHeadView, null, false);

            mHeadState = DONE;
        }

        /**
         * 添加加载更多FootView
         *
         * @date 2013-11-11 下午9:52:37
         * @change JohnWatson
         * @version 1.0
         */
        private void addFooterView() {
            mEndRootView = mInflater.inflate(R.layout.listfooter_more, null);
            mEndRootView.setVisibility(View.GONE);
            mEndLoadProgressBar = (ProgressBar) mEndRootView.findViewById(R.id.pull_to_refresh_progress);
            mEndLoadTipsTextView = (TextView) mEndRootView.findViewById(R.id.load_more);
            mEndRootView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mCanLoadMore) {
                        if (mCanRefresh) {
                            // 当可以下拉刷新时，如果FootView没有正在加载，并且HeadView没有正在刷新，才可以点击加载更多。
                            if (mEndState != ENDINT_LOADING && mHeadState != REFRESHING) {
                                mEndState = ENDINT_LOADING;
                                onLoadMore();
                            }
                        } else if (mEndState != ENDINT_LOADING) {
                            // 当不能下拉刷新时，FootView不正在加载时，才可以点击加载更多。
                            mEndState = ENDINT_LOADING;
                            onLoadMore();
                        }
                    }
                }
            });

            addFooterView(mEndRootView);

            if (mIsAutoLoadMore) {
                mEndState = ENDINT_AUTO_LOAD_DONE;
            } else {
                mEndState = ENDINT_MANUAL_LOAD_DONE;
            }
        }

        /**
         * 测量HeadView宽高(注意：此方法仅适用于LinearLayout，请读者自己测试验证。)
         *
         * @param pChild
         * @date 2013-11-20 下午4:12:07
         * @change JohnWatson
         * @version 1.0
         */
        private void measureView(View pChild) {
            ViewGroup.LayoutParams p = pChild.getLayoutParams();
            if (p == null) {
                p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
            int lpHeight = p.height;

            int childHeightSpec;
            if (lpHeight > 0) {
                childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
            } else {
                childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            }
            pChild.measure(childWidthSpec, childHeightSpec);
        }

        /**
         * 下拉刷新完成
         *
         * @date 2013-11-20 下午4:44:12
         * @change JohnWatson
         * @version 1.0
         */
        public void onRefreshComplete() {
            // 下拉刷新后是否显示第一条Item
            setSelection(0);

            mHeadState = DONE;
            // 最近更新: Time
            mLastUpdatedTextView.setText(getResources().getString(R.string.p2refresh_refresh_lasttime)
                    + new SimpleDateFormat(DATE_FORMAT_STR, Locale.CHINA).format(new Date()));
            changeHeaderViewByState();
        }

        /**
         * 加载更多完成
         *
         * @date 2013-11-11 下午10:21:38
         * @change JohnWatson
         * @version 1.0
         */
        public void onLoadMoreComplete() {
            if (mIsAutoLoadMore) {
                mEndState = ENDINT_AUTO_LOAD_DONE;
            } else {
                mEndState = ENDINT_MANUAL_LOAD_DONE;
            }
            changeEndViewByState();
        }

        /**
         * 更新一下刷新时间
         *
         * @param adapter
         * @date 2013-11-20 下午5:35:51
         * @change JohnWatson
         * @version 1.0
         */
        public void setAdapter(ListAdapter adapter) {
            // 最近更新: Time
            mLastUpdatedTextView.setText(getResources().getString(R.string.p2refresh_refresh_lasttime)
                    + new SimpleDateFormat(DATE_FORMAT_STR, Locale.CHINA).format(new Date()));
            super.setAdapter(adapter);
        }

        /**
         * 改变加载更多状态
         *
         * @date 2013-11-11 下午10:05:27
         * @change JohnWatson
         * @version 1.0
         */
        private void changeEndViewByState() {
            if (mCanLoadMore) {
                // 允许加载更多
                switch (mEndState) {
                    case ENDINT_LOADING:// 刷新中
                        // 加载中...
                        if (mEndLoadTipsTextView.getText().equals(R.string.p2refresh_doing_end_refresh)) {
                            break;
                        }
                        mEndLoadTipsTextView.setText(R.string.p2refresh_doing_end_refresh);
                        mEndLoadTipsTextView.setVisibility(View.VISIBLE);
                        mEndLoadProgressBar.setVisibility(View.VISIBLE);
                        break;
                    case ENDINT_MANUAL_LOAD_DONE:// 手动刷新完成

                        // 点击加载
                        mEndLoadTipsTextView.setText(R.string.p2refresh_end_click_load_more);
                        mEndLoadTipsTextView.setVisibility(View.VISIBLE);
                        mEndLoadProgressBar.setVisibility(View.GONE);

                        mEndRootView.setVisibility(View.GONE);
                        break;
                    case ENDINT_AUTO_LOAD_DONE:// 自动刷新完成

                        // 更 多
                        mEndLoadTipsTextView.setText(R.string.p2refresh_end_load_more);
                        mEndLoadTipsTextView.setVisibility(View.VISIBLE);
                        mEndLoadProgressBar.setVisibility(View.GONE);

                        mEndRootView.setVisibility(View.GONE);
                        break;
                    default:
                        // 原来的代码是为了： 当所有item的高度小于ListView本身的高度时，
                        // 要隐藏掉FootView，大家自己去原作者的代码参考。

                        // if (mEnoughCount) {
                        // mEndRootView.setVisibility(View.VISIBLE);
                        // } else {
                        // mEndRootView.setVisibility(View.GONE);
                        // }
                        mEndRootView.setVisibility(View.GONE);
                        break;
                }
            }
            // else{
            // mEndRootView.setVisibility(View.GONE);
            // }
        }

    }
}
