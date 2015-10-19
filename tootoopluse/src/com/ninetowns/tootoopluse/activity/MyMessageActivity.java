package com.ninetowns.tootoopluse.activity;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.adapter.MyMsgFragmentPagerAdapter;
import com.ninetowns.tootoopluse.fragment.MessageFragment;
import com.ninetowns.tootoopluse.fragment.NoticeFragment;
import com.ninetowns.tootoopluse.fragment.PrivateLetterFragment;
import com.ninetowns.tootoopluse.util.UIUtils;
import com.ninetowns.ui.Activity.BaseActivity;
import com.ninetowns.ui.widget.ScrollControlViewPager;

/**
 * @ClassName: MyMessageActivity
 * @Description: 我的消息页面（包含 私信通知 和消息）
 * @author zhou
 * @date 2015-1-22 下午1:14:08
 * 
 */
// @ContentView(R.layout.mymessage_activity)
public class MyMessageActivity extends BaseActivity implements OnClickListener {
	/**
	 * @Fields mCustomViewPager : 可控制滑动的viewpager
	 */
	// @ViewInject(R.id.mymsg_vPager)
	private ScrollControlViewPager mCustomViewPager;

	/**
	 * 标题栏的三个按钮
	 */
	// @ViewInject(R.id.rb_mymsg_privateletter)
	private TextView mPrivateLetterRB;
	// @ViewInject(R.id.rb_mymsg_notice)
	private TextView mNoticeRB;
	// @ViewInject(R.id.rb_mymsg_msg)
	private TextView mMessageRB;

	// @ViewInject(R.id.rg_main_category)
	private LinearLayout mLLTabContainer;

	/**
	 * @Fields mFragList : 用来存放fragment
	 */
	private ArrayList<Fragment> mFragList;

	/**
	 * @Fields tabIsSelectedBg : tab被选中时的颜色
	 */
	private int tabIsSelectedBg, tabTextDefauleColor;

	/**
	 * @Fields tabIsUnSelectedBg : tab未被选中时的颜色
	 */
	private int tabIsUnSelectedBg;

	private int whiteId;

	/**
	 * @Fields mPrivateLetterFrag : 私信
	 */
	private PrivateLetterFragment mPrivateLetterFrag;
	private NoticeFragment mNoticeFrag;// 通知
	private MessageFragment mMsgFrag;// 消息

//	/**
//	 * @Fields mBackButton :返回
//	 */
//	@ViewInject(R.id.mymessage_back_btn)
//	private ImageView mBackButton;
//
//	/**
//	 * @Fields mClearAllButton : 清空
//	 */
//	@ViewInject(R.id.mymessage_clearall_btn)
//	private Button mClearAllButton;

	private PagerAdapter mMyMsgAdatpeter;

	/**
	 * @Title: onBackClick
	 * @Description: 返回
	 * @param @param v 设定文件
	 * @return void 返回类型
	 */
	@OnClick(R.id.mymessage_back_btn)
	public void onBackClick(View v) {
		this.finish();
	}

	/**
	 * @Title: onClearAllClick
	 * @Description: 清空
	 * @param @param v 设定文件
	 * @return void 返回类型
	 */
	@OnClick(R.id.mymessage_clearall_btn)
	public void onClearAllClick(View v) {

		UIUtils.showConfirmDialog(this, "删除消息", "是否清空所有数据",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (mCustomViewPager.getCurrentItem()) {
						case 0:
							mPrivateLetterFrag.clearAll();
							break;
						case 1:
							mNoticeFrag.clearAll();
							break;
						case 2:
							mMsgFrag.clearAll();
							break;

						}
					}

				});
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.mymessage_activity);
		// 注入view和事件
		ViewUtils.inject(this);

		initViews();
		tabTextDefauleColor = tabIsSelectedBg = getResources().getColor(
				R.color.tab_selected_bg);
		tabIsUnSelectedBg = R.drawable.mymessage_tab_bg;
		whiteId = getResources().getColor(R.color.white);
		initViewPager();
		selectPrivateLetter();
		initLister();
	}

	private void initViews() {
		mPrivateLetterRB = (TextView) findViewById(R.id.rb_mymsg_privateletter);
		mNoticeRB = (TextView) findViewById(R.id.rb_mymsg_notice);
		mMessageRB = (TextView) findViewById(R.id.rb_mymsg_msg);
		mLLTabContainer = (LinearLayout) findViewById(R.id.rg_main_category);
		mCustomViewPager = (ScrollControlViewPager) findViewById(R.id.mymsg_vPager);
	}

	private void initViewPager() {
		mFragList = new ArrayList<Fragment>();
		mPrivateLetterFrag = new PrivateLetterFragment();
		mNoticeFrag = new NoticeFragment();
		mMsgFrag = new MessageFragment();
		mFragList.add(mPrivateLetterFrag);
		mFragList.add(mNoticeFrag);
		mFragList.add(mMsgFrag);

		mMyMsgAdatpeter = new MyMsgFragmentPagerAdapter(
				getSupportFragmentManager(), mFragList);
		mCustomViewPager.setAdapter(mMyMsgAdatpeter);

		// 设置预加载的数量 可以缓存
		mCustomViewPager.setOffscreenPageLimit(0);
		mCustomViewPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});

		mCustomViewPager.setOnPageChangeListener(mPagerListener);
	}

	private void initLister() {
		mPrivateLetterRB.setOnClickListener(this);
		mNoticeRB.setOnClickListener(this);
		mMessageRB.setOnClickListener(this);
	}

	/**
	 * @Fields mPagerListener : 监听viewpager的变化
	 */
	private ViewPager.SimpleOnPageChangeListener mPagerListener = new ViewPager.SimpleOnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
			switch (position) {
			case 0:// 私信
				selectPrivateLetter();
				break;
			case 1:// 通知
				selectNotice();
				break;
			case 2:// 消息
				selectMessage();
				break;
			}
		}

	};

	private void selectNotice() {
		mCustomViewPager.setCurrentItem(1);
		mPrivateLetterRB.setTextColor(tabIsSelectedBg);
		mPrivateLetterRB.setBackgroundResource(tabIsUnSelectedBg);
		mNoticeRB.setTextColor(whiteId);
		mNoticeRB.setBackgroundColor(tabIsSelectedBg);
		mMessageRB.setTextColor(tabIsSelectedBg);
		mMessageRB.setBackgroundResource(tabIsUnSelectedBg);
	}

	private void selectMessage() {
		mCustomViewPager.setCurrentItem(2);
		mPrivateLetterRB.setTextColor(tabTextDefauleColor);
		mPrivateLetterRB.setBackgroundResource(tabIsUnSelectedBg);
		mNoticeRB.setTextColor(tabTextDefauleColor);
		mNoticeRB.setBackgroundResource(tabIsUnSelectedBg);
		mMessageRB.setTextColor(whiteId);
		mMessageRB.setBackgroundColor(tabIsSelectedBg);
	}

	private void selectPrivateLetter() {
		mCustomViewPager.setCurrentItem(0);
		mPrivateLetterRB.setTextColor(whiteId);
		mPrivateLetterRB.setBackgroundColor(tabIsSelectedBg);
		mNoticeRB.setTextColor(tabTextDefauleColor);
		mNoticeRB.setBackgroundResource(tabIsUnSelectedBg);
		mMessageRB.setTextColor(tabTextDefauleColor);
		mMessageRB.setBackgroundResource(tabIsUnSelectedBg);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rb_mymsg_privateletter:
			selectPrivateLetter();
			break;
		case R.id.rb_mymsg_notice:
			selectNotice();
			break;
		case R.id.rb_mymsg_msg:
			selectMessage();
			break;
		}

	}

}
