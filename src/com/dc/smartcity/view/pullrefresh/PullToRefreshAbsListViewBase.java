package com.dc.smartcity.view.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.*;
import android.widget.AbsListView.OnScrollListener;

public abstract class PullToRefreshAbsListViewBase<T extends AbsListView> extends PullToRefreshBase<T> implements
		OnScrollListener {
	
	/**
	 * ��������������AbsListView.OnScrollListener��������ͬ��
	 *
	 */
	public interface PullToRefreshOnScrollListener extends OnScrollListener{}

	private int lastSavedBottomVisibleItem=-1;
	private PullToRefreshOnScrollListener pullToRefreshOnScrollListener;
	private View emptyView;
	private FrameLayout refreshableViewHolder;
	private int mtotalItemCount;
	private int bottomVisibleItem;
	private int ADVANCE_COUNT=0;//Ԥ������ǰ��
//	private int firstItem = 0,pad,index;// ����֮ǰ��һ��item��λ��,ListView��paddingֵ,�����ĵ�һ���ɼ�Itemֵ
//	private static Animation translate_foot_bottom_to_top,//�ײ�TAB��ʾ����
//							translate_foot_top_to_bottom,//�ײ�TAB���ض���
//							translate_header_bottom_to_top,//ͷ��TAb���ض���
//							translate_header_top_to_bottom;//ͷ��TAb��ʾ����
//	private View tabViewBottom,tabViewHeader,myView;
//	private boolean inScroll = false,isHeaderAnimationFinish =true,isBottomAnimationFinish = true;//�Ƿ��Ѿ�������,�����Ƿ��Ѿ�����

	public PullToRefreshAbsListViewBase(Context context) {
		super(context);
	}

	public PullToRefreshAbsListViewBase(Context context, int mode) {
		super(context, mode);
	}

	public PullToRefreshAbsListViewBase(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	abstract public ContextMenuInfo getContextMenuInfo();

	@Override
	public  void onScroll( AbsListView view,  int firstVisibleItem,  int visibleItemCount,
		final int totalItemCount) {
		mtotalItemCount=totalItemCount;
		bottomVisibleItem=firstVisibleItem + visibleItemCount;
//		checkRefreshAble();
		if (pullToRefreshOnScrollListener!=null) {
				pullToRefreshOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
//		myView = view;
//		index = firstVisibleItem;
//		//�����б���ʱTAB���غ���ʾ�ļ���
//		if(tabViewHeader!=null){
//			tabViewHeader.bringToFront();//����ǰ��������������ϲ�
//		}
//		if(tabViewBottom!=null){
//			tabViewBottom.bringToFront();//����ǰ��������������ϲ�
//		}
//
//		if (!(view.getLastVisiblePosition() == totalItemCount - 1)) {
//			if (firstVisibleItem - firstItem > 0 ) {
//				if(tabViewHeader != null){
//					if(tabViewHeader.getVisibility() == View.VISIBLE){
//						tabViewHeader.clearAnimation();
//						tabViewHeader.startAnimation(translate_header_bottom_to_top);
//						tabViewHeader.setVisibility(View.GONE);
//						if(view.getPaddingTop() != 0){
//							pad = view.getPaddingTop();
//						}
//						view.setPadding(0, 0, 0, 0);
//					}
//				}
//				if(tabViewBottom != null){
//					if(tabViewBottom.getVisibility() == View.VISIBLE){
//						tabViewBottom.clearAnimation();
//						tabViewBottom.startAnimation(translate_foot_top_to_bottom);
//						tabViewBottom.setVisibility(View.GONE);
//					}
//				}
//			}
//		}
//		if(view.getFirstVisiblePosition() == 0 && inScroll){//����ͷ��
//			if(tabViewHeader != null){
//				if(tabViewHeader.getVisibility() == View.VISIBLE){
//					if(view.getPaddingTop() == 0){
//						view.setPadding(0, pad, 0, 0);
//					}
//				}
//			}
//
//			if(tabViewBottom != null){
//				tabViewBottom.setVisibility(View.VISIBLE);
//			}
//			view.setSelection(0);
//			inScroll = false;
//		}

	}

	@Override
	public  void onScrollStateChanged( AbsListView view,  int scrollState) {
		if ((getMode()==MODE_AUTO_REFRESH||getMode()==(MODE_AUTO_REFRESH|MODE_PULL_DOWN_TO_REFRESH))&&bottomVisibleItem == mtotalItemCount-ADVANCE_COUNT /*&& scrollState == OnScrollListener.SCROLL_STATE_IDLE*/) {//����ײ�&&�ڻ���
			if(lastSavedBottomVisibleItem!=bottomVisibleItem&&getOnRefreshListener()!=null){
				lastSavedBottomVisibleItem=bottomVisibleItem;
				if(getOnRefreshListener().onRefresh(MODE_AUTO_REFRESH))
					setRefreshingInternal(true);
			}
		}
		if (pullToRefreshOnScrollListener!=null) {
			pullToRefreshOnScrollListener.onScrollStateChanged(view, scrollState);
		}


//		firstItem = view.getFirstVisiblePosition();
//  		if (scrollState == SCROLL_STATE_IDLE) {
//  			inScroll =true;
//			if(tabViewHeader != null){
//				if(tabViewHeader.getVisibility() == View.GONE){
//					if(isHeaderAnimationFinish){
//						tabViewHeader.clearAnimation();
//						tabViewHeader.startAnimation(translate_header_top_to_bottom);
//						isHeaderAnimationFinish =false;
//					}
//				}
//			}
//			if(tabViewBottom != null){
//				if(tabViewBottom.getVisibility() == View.GONE){
////					if(isBottomAnimationFinish){
//						tabViewBottom.clearAnimation();
//						tabViewBottom.startAnimation(translate_foot_bottom_to_top);
//						isBottomAnimationFinish = false;
////					}
//				}
//			}
//		}
	}

//	/**
//	 * ����Ƿ���Լ�������
//	 */
//	private void checkRefreshAble(){
//		if(bottomVisibleItem == mtotalItemCount-ADVANCE_COUNT){
//			if(lastSavedBottomVisibleItem!=bottomVisibleItem&&getOnRefreshListener()!=null){
//				lastSavedBottomVisibleItem=bottomVisibleItem;
//			if(getOnRefreshListener().onRefresh())
//				showBottomRefreshing();
//			}
//		}
//	}

	/**
	 * ˢ����ǰ����Ĭ�ϵ��ײ�ˢ�£�
	 * @param count
	 */
	public void setAdvanceCount(int count){
		ADVANCE_COUNT=count;
	}

	/**
	 * ʹ�ó�����<br/><br/>
	 * 1	�б�ײ����غ�(�Ѽ�¼��λ���Ѽ���)���������������ԭ������δ���سɹ�,��Ҫ�ٴ����¼��أ���ʱ��Ҫ����
	 * �˷����������ô�λ�ÿɼ���<br/><br/>
	 *
	 * 2	�����ݼ������б���ǰ�˵�λ��,�б������1ҳ���ݺ�(����һҳ����Ϊ20��)���������ײ����ص�2ҳ����ʱposition=20��λ���Ѽ�¼ˢ�£�
	 * Ȼ���ٵ�������˵��������ݺ��б��ٴμ��ص�2ҳ����ʱ�Ͳ������Զ�ˢ���ˣ�
	 * ��Ϊ֮ǰ�˵�״̬�Ѽ�¼����λ��(20)ˢ�¹�����ʱ��Ҫ���ô˷����������øõײ��ɼ���<br/>
	 * <b>ps:����ÿ�ε���˵���ʱ������´˷���</b><br/><br/>
	 *
	 * 3	ҵ����Ҫ<br/>
	 *
	 * @param refreshAble
	 * 	true :��ǰ�ײ����Զ�ˢ�� ; false ��ǰ�ײ�����ˢ��
	 */
	public void setCurrentBottomAutoRefreshAble(boolean refreshAble){
		if(refreshAble){
			lastSavedBottomVisibleItem=-1;
		}else{
			if(refreshableView.getAdapter()!=null)
				lastSavedBottomVisibleItem=refreshableView.getAdapter().getCount()-ADVANCE_COUNT;
		}
	}

	public void setBackToTopView(ImageView mTopImageView){
		mTopImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (refreshableView instanceof ListView ) {
					refreshableView.setSelection(0);
				}else if(refreshableView instanceof GridView){
					refreshableView.setSelection(0);
				}
			}
		});
	}

	/**
	 * Sets the Empty View to be used by the Adapter View.
	 *
	 * We need it handle it ourselves so that we can Pull-to-Refresh when the
	 * Empty View is shown.
	 *
	 * Please note, you do <strong>not</strong> usually need to call this method
	 * yourself. Calling setEmptyView on the AdapterView will automatically call
	 * this method and set everything up. This includes when the Android
	 * Framework automatically sets the Empty View based on it's ID.
	 *
	 * @param newEmptyView
	 *            - Empty View to be used
	 */
	public final void setEmptyView(View newEmptyView) {
		// If we already have an Empty View, remove it
		if (null != emptyView) {
			refreshableViewHolder.removeView(emptyView);
		}

		if (null != newEmptyView) {
			ViewParent newEmptyViewParent = newEmptyView.getParent();
			if (null != newEmptyViewParent && newEmptyViewParent instanceof ViewGroup) {
				((ViewGroup) newEmptyViewParent).removeView(newEmptyView);
			}

			this.refreshableViewHolder.addView(newEmptyView, ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.FILL_PARENT);
		}

		if (refreshableView instanceof EmptyViewMethodAccessor) {
			((EmptyViewMethodAccessor) refreshableView).setEmptyViewInternal(newEmptyView);
		} else {
			this.refreshableView.setEmptyView(newEmptyView);
		}
	}



	public final PullToRefreshOnScrollListener getOnScrollListener() {
		return pullToRefreshOnScrollListener;
	}

	public final void setOnScrollListener(PullToRefreshOnScrollListener onScrollListener) {
		this.pullToRefreshOnScrollListener = onScrollListener;
		if(getMode()!=MODE_AUTO_REFRESH&&getMode()!=(MODE_AUTO_REFRESH|MODE_PULL_DOWN_TO_REFRESH))
			refreshableView.setOnScrollListener(pullToRefreshOnScrollListener);
	}

	protected void addRefreshableView(Context context, T refreshableView) {
		refreshableViewHolder = new FrameLayout(context);
		refreshableViewHolder.addView(refreshableView, ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT);
		addView(refreshableViewHolder, new LayoutParams(LayoutParams.FILL_PARENT, 0, 1.0f));
	}

	protected boolean isReadyForPullDown() {
		return isFirstItemVisible();
	}

	protected boolean isReadyForPullUp() {
		return isLastItemVisible();
	}

	private boolean isFirstItemVisible() {
		if (this.refreshableView.getCount() == 0) {
			return true;
		} else if (refreshableView.getFirstVisiblePosition() == 0) {

			final View firstVisibleChild = refreshableView.getChildAt(0);

			if (firstVisibleChild != null) {
				return firstVisibleChild.getTop() >= refreshableView.getTop();
			}
		}

		return false;
	}

	private boolean isLastItemVisible() {
		final int count = this.refreshableView.getCount();
		final int lastVisiblePosition = refreshableView.getLastVisiblePosition();

		if (count == 0) {
			return true;
		} else if (lastVisiblePosition == count - 1) {

			final int childIndex = lastVisiblePosition - refreshableView.getFirstVisiblePosition();
			final View lastVisibleChild = refreshableView.getChildAt(childIndex);

			if (lastVisibleChild != null) {
				return lastVisibleChild.getBottom() <= refreshableView.getBottom();
			}
		}
		return false;
	}


	/*
	 *���ǵ��б��������ԣ��ڲ���MODE_AUTO_REFRESH��ģʽ�²����ö�OnScroll�ļ���
	 */
	@Override
	public void setMode(int mode) {
		super.setMode(mode);
		switch (mode) {
		case MODE_AUTO_REFRESH|MODE_PULL_DOWN_TO_REFRESH:
		case MODE_AUTO_REFRESH:
			refreshableView.setOnScrollListener(this);
//			setPadding(0, -getHeaderHeight() ,0, getHeaderHeight());
//			if(getFooterLayout()!=null){
//				getFooterLayout().setVisibility(View.GONE);
//				getFooterLayout().refreshing();
//			}
			break;
		default:
			refreshableView.setOnScrollListener(pullToRefreshOnScrollListener);
//			setPadding(0, -getHeaderHeight() ,0, -getHeaderHeight());
//			if(getFooterLayout()!=null)
//				getFooterLayout().setVisibility(View.VISIBLE);
			break;
		}
	}
	/**
	 * �����б���TAB���غ���ʾ
	 * @param mContext
	 * @param tabViewBottom  �ײ����ذ�ť
	 * @param tabViewHeader ͷ�����ذ�ť
	 */
//	public void setTabView(Context mContext, final View tabViewBottom , final View tabViewHeader){
//		this.tabViewBottom = tabViewBottom;
//		this.tabViewHeader = tabViewHeader;
//		System.out.println(tabViewHeader);
//		if(tabViewHeader !=null ){//ͷ��TAB
//			translate_header_top_to_bottom = AnimationUtils.loadAnimation(
//					mContext, R.anim.tab_show_header_top_bottom);//ͷ��TAB��ʾ����
//			translate_header_bottom_to_top = AnimationUtils.loadAnimation(
//					mContext, R.anim.tab_show_header_bottom_top);//ͷ��TAB���ض���
//
//			//ͷ����������
//			translate_header_bottom_to_top.setAnimationListener(new AnimationListener() {
//
//				@Override
//				public void onAnimationStart(Animation animation) {
//					if(translate_header_bottom_to_top.hasStarted()){
//						translate_header_bottom_to_top.reset();
//
//					 }
//				}
//
//				@Override
//				public void onAnimationRepeat(Animation animation) {
//				}
//
//				@Override
//				public void onAnimationEnd(Animation animation) {
//					if(tabViewHeader != null){
//						tabViewHeader.setVisibility(View.GONE);
//					}
//
//				}
//			});
//			translate_header_top_to_bottom.setAnimationListener(new AnimationListener() {
//
//				@Override
//				public void onAnimationStart(Animation animation) {
//					 if(translate_header_top_to_bottom.hasStarted()){
//						 translate_header_top_to_bottom.reset();
//					 }
//				}
//
//				@Override
//				public void onAnimationRepeat(Animation animation) {
//				}
//
//				@Override
//				public void onAnimationEnd(Animation animation) {
//					isHeaderAnimationFinish = true;
//					if(tabViewHeader != null){
//						tabViewHeader.setVisibility(View.VISIBLE);
//					}
//					if(index == 0){
//						if(myView.getPaddingTop() == 0){
//							myView.setPadding(0, pad, 0, 0);
//						}
//					}
//				}
//			});
//		}
//	  if(tabViewBottom != null){//�ײ�TAB
//			translate_foot_bottom_to_top = AnimationUtils.loadAnimation(
//					mContext, R.anim.alpha_flight_sort_in);//�ײ�TAB��ʾ
//			translate_foot_top_to_bottom = AnimationUtils.loadAnimation(
//					mContext, R.anim.flight_sort_top_to_bottom);//�ײ�TAB���ض���
//
//			//�ײ���������
//			translate_foot_bottom_to_top.setAnimationListener(new AnimationListener() {
//				
//				@Override
//				public void onAnimationStart(Animation animation) {
//					if(translate_foot_bottom_to_top.hasStarted()){
//						translate_foot_bottom_to_top.reset();
//					}
//				}
//				
//				@Override
//				public void onAnimationRepeat(Animation animation) {
//					
//				}
//				
//				@Override
//				public void onAnimationEnd(Animation animation) {
//					isBottomAnimationFinish = true;
//					if(tabViewBottom != null){
//						tabViewBottom.setVisibility(View.VISIBLE);
//					}
//				}
//			});
//			
//			translate_foot_top_to_bottom.setAnimationListener(new AnimationListener() {
//				
//				@Override
//				public void onAnimationStart(Animation animation) {
//					if(translate_foot_top_to_bottom.hasStarted()){
//						translate_foot_top_to_bottom.reset();
//					}
//				}
//				
//				@Override
//				public void onAnimationRepeat(Animation animation) {
//					
//				}
//				
//				@Override
//				public void onAnimationEnd(Animation animation) {
//					if(tabViewBottom != null){
//						tabViewBottom.setVisibility(View.GONE);
//					}
//				}
//			});
//		}
//	}
}
