package com.ninetowns.tootooplus.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.ninetowns.library.net.RequestParamsNet;
import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.library.util.LogUtil;
import com.ninetowns.library.util.MyTextwatcherUtil;
import com.ninetowns.library.util.NetworkUtil;
import com.ninetowns.library.util.StringUtils;
import com.ninetowns.tootooplus.R;
import com.ninetowns.tootooplus.application.TootooPlusApplication;
import com.ninetowns.tootooplus.helper.ConstantsTooTooEHelper;
import com.ninetowns.tootooplus.helper.TootooeNetApiUrlHelper;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.ninetowns.ui.widget.dialog.ProgressiveDialog;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog;
import com.ninetowns.ui.widget.dialog.TooSureCancelDialog.TooDialogCallBack;

/**
 * 
 * @Title: TextContentStoryCreateFragment.java
 * @Description: 创建文字类型的商品故事
 * @author wuyulong
 * @date 2015-1-8 上午11:21:04
 * @version V1.0
 */
public abstract class TextContentStoryCreateFragment extends Fragment implements
		View.OnClickListener {
	private View view;
	private EditText mEdDes;
	private ImageButton mIBTNComp;
	private Bundle bundle;
	private String userid;
	private String goodid;
	private String title;
	private String storyid;
	private TextView mTitleBar;
	public String editorContent;
	// private String paramType = "";
	private String pageidEdit = "";
	private boolean isdraftEditory;
	private ImageButton back;
	private String tag = "TextContentStoryCreateFragment";
	private String UpdateStoryId = "0";
	private boolean isEditorView;
	private TextView mTextCount;
	private boolean isMiddleEditor;
	private String textMiddle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getType();
		bundle = this.getActivity().getIntent()
				.getBundleExtra(ConstantsTooTooEHelper.BUNDLE);
		storyid = bundle.getString("storyid");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.text_line_story_create_fragment, null);
		back = (ImageButton) getActivity()
				.findViewById(R.id.title_bar_left_btn);// 后退
		mIBTNComp = (ImageButton) this.getActivity().findViewById(
				R.id.title_bar_title_right);// 完成
		mIBTNComp.setVisibility(View.VISIBLE);
		back.setImageResource(R.drawable.btn_return_gray);
		mEdDes = (EditText) view.findViewById(R.id.et_text_des);
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) mEdDes.getLayoutParams();
		int height = CommonUtil.getHeight(TootooPlusApplication.getAppContext());
		params.height=height;
		if (editextSizeListener != null) {// 监听LineEditText
			editextSizeListener.OnEditextChangeTextSizeListener(mEdDes);
		}
		if (isMiddleEditor) {
			if (!StringUtils.isEmpty(textMiddle)) {
				mEdDes.setText(textMiddle);
			}
		}

		mEdDes.setSelection(mEdDes.length());
		mTextCount = (TextView) view.findViewById(R.id.count);
		setSize(mEdDes, mTextCount);
		mTitleBar = (TextView) this.getActivity().findViewById(
				R.id.title_bar_title);
		mTitleBar.setVisibility(View.VISIBLE);
		if (isdraftEditory || isMiddleEditor) {
			mTitleBar.setText(getResources().getString(R.string.editor_text));
		} else {
			mTitleBar.setText(getResources().getString(R.string.create_text));
		}

		mIBTNComp.setOnClickListener(this);
		back.setOnClickListener(this);
		return view;
	}

	public void setSize(EditText let, TextView tv) {
		let.addTextChangedListener(new MyTextwatcherUtil(this.getActivity(),
				let, tv, 500));// 最多输入1000个字符
	}

	/**
	 * 
	 * @Title: StoryDetailItemTypeActivity.java
	 * @Description: 根据跳转类型跳到相应的fragemnt中
	 * @author wuyulong
	 * @date 2014-8-1 下午4:51:02
	 * @param
	 * @return void
	 */
	public void getType() {
		try {
			bundle = this.getActivity().getIntent()
					.getBundleExtra(ConstantsTooTooEHelper.BUNDLE);
			UpdateStoryId = bundle.getString("UpdateStoryId");
			LogUtil.systemlogInfo(tag, UpdateStoryId);
			storyid = bundle.getString("storyid");
			title = bundle.getString("title");

			userid = bundle.getString("userid");
			goodid = bundle.getString("goodid");
			isdraftEditory = bundle.getBoolean("isDraftOrStory");
			isEditorView = bundle.getBoolean("isEditorView");
			isMiddleEditor = bundle.getBoolean("isMiddleEditor");
			textMiddle = bundle.getString("textcontent");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			pageidEdit = bundle.getString("pageid");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.title_bar_title_right:
			editorContent = mEdDes.getText().toString().trim();
			if(!TextUtils.isEmpty(editorContent)){
				saveText(editorContent);
			}
			

			break;

		case R.id.title_bar_left_btn:
			backview();

			break;
		}
	}

	public void backview() {
		if (isdraftEditory || isMiddleEditor) {
			justSaveContent2();
		} else {
			justSaveContent2();
		}
	}

	public void saveText(String editorContent) {

		// if(CommonUtil.isEditorStoryTextLength(editorContent)){//如过小于100个字符
		if (isdraftEditory || isMiddleEditor) {// 如果从故事里面跳转古来
			// 如果是编辑故事
			if (!StringUtils.isEmpty(editorContent)) {
				postDataEdit();
			} else {
				ComponentUtil.showToast(getActivity(), "您还没有修改内容！");
			}
		} else {// 从创建中传过来
			if (!StringUtils.isEmpty(editorContent)) {
				postData();
			} else {
				ComponentUtil.showToast(getActivity(), "您还没有编辑内容！");
			}

		}
	}

	/**
	 * 
	 * @Title: TextContentStoryCreateFragment.java
	 * @Description:
	 * @author wuyulong
	 * @date 2014-8-26 下午5:18:14
	 * @param
	 * @return void
	 */
	private void justSaveContent() {
		editorContent = mEdDes.getText().toString().trim();
		// if(CommonUtil.isEditorStoryTextLength(editorContent)){
		if (!StringUtils.isEmpty(editorContent)) {

			TooSureCancelDialog tooDialog = new TooSureCancelDialog(
					this.getActivity());
			tooDialog.setDialogTitle(R.string.notice_title);
			tooDialog.setDialogContent(R.string.is_complete_content);
			tooDialog.setDialogSureBtnName("完成");
			tooDialog.setTooDialogCallBack(new TooDialogCallBack() {
				@Override
				public void onTooDialogSure() {

					if (isdraftEditory || isMiddleEditor) {// 如果是编辑故事
						postDataEdit();
					} else {
						postData();
					}

				}

				@Override
				public void onTooDialogCancel() {
					getActivity().finish();

				}
			});
			tooDialog.show();

		} else {
			getActivity().finish();
		}

	}

	private void justSaveContent2() {
		editorContent = mEdDes.getText().toString().trim();
		// if(CommonUtil.isEditorStoryTextLength(editorContent)){
		if (!StringUtils.isEmpty(editorContent)) {

			TooSureCancelDialog tooDialog = new TooSureCancelDialog(
					this.getActivity());
			tooDialog.setDialogTitle(R.string.notice_title);
			tooDialog.setDialogContent(R.string.notice_content);
			tooDialog.setDialogSureBtnName("保存");
			tooDialog.setTooDialogCallBack(new TooDialogCallBack() {
				@Override
				public void onTooDialogSure() {

					if (isdraftEditory || isMiddleEditor) {// 如果是编辑故事
						postDataEdit();
					} else {
						postData();
					}

				}

				@Override
				public void onTooDialogCancel() {
					getActivity().finish();

				}
			});
			tooDialog.show();

		} else {
			getActivity().finish();
		}

	}

	/**
	 * 
	 * @Title: TextContentStoryCreateFragment.java
	 * @Description: 此处是编辑
	 * @author wuyulong
	 * @date 2014-8-8 下午5:01:14
	 * @param
	 * @return void
	 */
	private void postDataEdit() {/*
								 * if ((NetworkUtils.isNetworkAvaliable(this.
								 * getActivity()))) {// 有网络
								 * 
								 * // StoryId：故事Id (必填) // PageId：故事页Id (必填) //
								 * PageContent：故事页文字 // PageImg：故事页图片或直播录播封面图地址
								 * // PageVideoUrl：故事页录播视频地址 // RecordId：录制Id //
								 * PageDesc：故事页描述
								 * 
								 * RequestParams params = new RequestParams();
								 * params.put(NetConstantsTooTooEHelper.
								 * STORYCREATE_STORYID, storyid);
								 * params.put(NetConstantsTooTooEHelper
								 * .STORYCREATE_PAGEID, pageidEdit);
								 * params.put(NetConstantsTooTooEHelper
								 * .STORYCREATE_PAGE_CONTENT, editorContent);
								 * params.put(NetConstantsTooTooEHelper.
								 * STORYCREATE_PAGE_TYPE, type);
								 * NetUtil.post(getActivity(),
								 * NetConstantsTooTooEHelper
								 * .STORYCREATE_PAGE_UPDATE, params, new
								 * AsyncHttpResponseHandler() {
								 * 
								 * private String PageId;
								 * 
								 * @Override public void onSuccess(int arg0,
								 * Header[] arg1, byte[] arg2) { if (arg2 !=
								 * null) { String obj =
								 * StringUtils.ByteToString(arg2);
								 * LogUtil.systemlogInfo(tag + "修改故事页 ", obj);
								 * try { JSONObject jsobj = new JSONObject(obj);
								 * if (jsobj.has("Status")) { String status =
								 * jsobj.getString("Status"); if
								 * (status.equals("1")) { // 修改成功 // 1.发送广播通知更新
								 * // 2.销毁当前界面 Intent intent = new
								 * Intent(TextContentStoryCreateFragment
								 * .this.getActivity(),
								 * CreateStoryActivity.class); Bundle bundle =
								 * new Bundle();
								 * intent.setAction(ConstantsTooTooEHelper
								 * .CreateStoryTypeActivityAction);
								 * bundle.putString("editor", "editor");
								 * bundle.putString("storyid", storyid);
								 * bundle.putString("goodid", goodid);
								 * bundle.putString("userid", userid);
								 * bundle.putString("UpdateStoryId",
								 * UpdateStoryId); bundle.putString("title",
								 * title); bundle.putBoolean("isDraftOrStory",
								 * isdraftEditory); intent.putExtra("bundle",
								 * bundle);
								 * intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
								 * );
								 * TextContentStoryCreateFragment.this.getActivity
								 * ().startActivity(intent); }
								 * 
								 * }
								 * 
								 * } catch (JSONException e) {
								 * LogUtil.error("CreateStoryFragment",
								 * e.toString()); e.printStackTrace(); }
								 * 
								 * } }
								 * 
								 * @Override public void onFailure(int arg0,
								 * Header[] arg1, byte[] arg2, Throwable arg3) {
								 * LogUtil.error("CreateStoryFragment",
								 * arg3.toString()); } });
								 * 
								 * } else {
								 * ComponentUtil.showToast(getActivity(),
								 * this.getActivity
								 * ().getResources().getString(R.
								 * string.errcode_network_response_timeout)); }
								 */
	}

	/**
	 * 
	 * @Title: TextContentStoryCreateFragment.java
	 * @Description: 获得pageid
	 * @author wuyulong
	 * @date 2014-12-18 上午10:56:03
	 * @param
	 * @return void
	 */
	public abstract void getPageId(JSONObject storyjsonobj)
			throws JSONException;

	private String PageId;

	public void postData() {
		if ((NetworkUtil.isNetworkAvaliable(TootooPlusApplication
				.getAppContext()))) {
			// 显示进度
			showProgressDialog(getActivity());
			RequestParamsNet params = initParam();
			CommonUtil.xUtilsPostSend(
					TootooeNetApiUrlHelper.STORYCREATE_PAGE_API, params,
					new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							closeProgressDialog(getActivity());
							String strRespone = responseInfo.result;
							if (!TextUtils.isEmpty(strRespone)) {
								try {
									JSONObject jsobj = new JSONObject(
											strRespone);
									if (jsobj.has("Status")) {
										jsobj.getString("Status");

									}
									if (jsobj.has("Data")) {
										String st = jsobj.getString("Data");
										JSONObject storyjsonobj = new JSONObject(
												st);
										if (storyjsonobj.has("PageId")) {
											getPageId(storyjsonobj);

										}
									}

								} catch (JSONException e) {
									LogUtil.error("CreateStoryFragment",
											e.toString());
									e.printStackTrace();
								}
							}

						}

						@Override
						public void onFailure(HttpException error, String msg) {
							closeProgressDialog(getActivity());
							ComponentUtil
									.showToast(
											getActivity(),
											getResources()
													.getString(
															R.string.errcode_network_response_timeout));
						}
					});

		} else {
			ComponentUtil.showToast(getActivity(), this.getResources()
					.getString(R.string.errcode_network_response_timeout));
		}

	}

	public abstract RequestParamsNet initParam();

	private EditextSizeInterface editextSizeListener;
	private ProgressiveDialog progressDialog;

	public void setOnEditextChangeTextSizeListener(EditextSizeInterface esi) {
		this.editextSizeListener = esi;

	}

	public interface EditextSizeInterface {
		public void OnEditextChangeTextSizeListener(EditText editext);

	}

	/**
	 * 
	 * @Title: ComponentUtil.java
	 * @Description: 显示dialog
	 * @author wuyulong
	 * @date 2014-7-14 下午4:23:26
	 * @param
	 * @return void
	 */
	public void showProgressDialog(Context context) {
		if ((!((Activity) context).isFinishing()) && (progressDialog == null)) {
			progressDialog = new ProgressiveDialog(context);
		}
		if (progressDialog != null) {
			progressDialog.setMessage(R.string.loading);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.show();
		}

	}

	/**
	 * 
	 * @Title: ComponentUtil.java
	 * @Description: 取消dialog
	 * @author wuyulong
	 * @date 2014-7-14 下午4:23:48
	 * @param
	 * @return void
	 */
	public void closeProgressDialog(Context context) {
		if (progressDialog != null) {
			progressDialog.isShowing();
			progressDialog.dismiss();
		}
	}
}
