package com.ninetowns.tootoopluse.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ninetowns.library.util.LogUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.adapter.MobileAlbumLvAdapter;
import com.ninetowns.tootoopluse.bean.MobileAlbumBean;
import com.ninetowns.tootoopluse.util.AlbumUtil;
import com.ninetowns.ui.Activity.BaseActivity;

/**
 * @author zhoudongtao
 * 自定义相册
 *
 */
public class MobileAlbumActivity extends BaseActivity {

	private String storyId;

	private String upload_type;

	private String userid;

	private boolean isDraftOrStory;

	private String pageid;

	private String PageType;

	private String goodid;

	private boolean isEditorView;

	private String UpdateStoryId;

	private boolean isMiddleEditor;

	private boolean isCreateView;

	private String title;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.mobile_album);

		getType();
		init();
	}

	public void getType() {
		try {
			userid = this.getIntent().getExtras().getString("userid");
			isDraftOrStory = this.getIntent().getExtras()
					.getBoolean("isDraftOrStory");
			pageid = this.getIntent().getExtras().getString("pageid");
			PageType = this.getIntent().getExtras().getString("PageType");
			goodid = this.getIntent().getExtras().getString("goodid");
			storyId = this.getIntent().getExtras().getString("storyid");
			upload_type = getIntent().getStringExtra("upload_type");
			UpdateStoryId = getIntent().getStringExtra("UpdateStoryId");
			title = getIntent().getStringExtra("title");

			isMiddleEditor = getIntent().getExtras().getBoolean(
					"isMiddleEditor");
			isEditorView = this.getIntent().getExtras()
					.getBoolean("isEditorView");
			isCreateView = this.getIntent().getExtras()
					.getBoolean("isCreateView");
			// isEditorView=getIntent().getExtras.g("isEditorView");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<MobileAlbumBean> mobileAlbumBeans;

	private void init() {
		// 返回按钮
		LinearLayout two_or_one_btn_head_back = (LinearLayout) findViewById(R.id.two_or_one_btn_head_back);
		two_or_one_btn_head_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 标题
		TextView two_or_one_btn_head_title = (TextView) findViewById(R.id.two_or_one_btn_head_title);
		two_or_one_btn_head_title.setText(R.string.mobile_album_title);

		ListView mobile_album_lv = (ListView) findViewById(R.id.mobile_album_lv);

		mobileAlbumBeans = AlbumUtil.albumImageInfo(this);

		LogUtil.systemlogInfo("MobileAlbumActivity+++>", mobileAlbumBeans);

		mobile_album_lv.setAdapter(new MobileAlbumLvAdapter(this,
				mobileAlbumBeans));
		mobile_album_lv.setOnItemClickListener(new OnItemClickListener() {// 这个地方是选择图片的地方

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						onitemclick(mobileAlbumBeans, position);
					}

				});

	}

	/**
	 * 
	 * @Title: MobileAlbumActivity.java
	 * @Description: 子类实现
	 * @author wuyulong
	 * @date 2014-12-15 下午1:01:01
	 * @param
	 * @return void
	 */
	public void onitemclick(final List<MobileAlbumBean> mobileAlbumBeans,
			int position) {
		Intent photo_intent = new Intent(MobileAlbumActivity.this,
				AlbumPhotoActivity.class);
		photo_intent.putExtra("folder_name", mobileAlbumBeans.get(position)
				.getMob_album_folder_name());
		photo_intent.putExtra("upload_type", upload_type);
		photo_intent.putExtra("storyid", storyId);
		photo_intent.putExtra("isEditorView", isEditorView);
		photo_intent.putExtra("userid", userid);
		photo_intent.putExtra("goodid", goodid);
		photo_intent.putExtra("PageType", PageType);
		photo_intent.putExtra("isMiddleEditor", isMiddleEditor);
		photo_intent.putExtra("isDraftOrStory", isDraftOrStory);
		photo_intent.putExtra("pageid", pageid);
		photo_intent.putExtra("isCreateView", isCreateView);
		photo_intent.putExtra("UpdateStoryId", UpdateStoryId);
		photo_intent.putExtra("title", title);

		startActivity(photo_intent);
	}
}
