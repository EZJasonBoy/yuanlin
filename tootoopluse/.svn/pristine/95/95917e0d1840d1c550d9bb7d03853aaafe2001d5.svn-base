package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.bean.GoodsScreenMainBean;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TextAdapter extends ArrayAdapter<GoodsScreenMainBean> {

	private Context mContext;
	private List<GoodsScreenMainBean> mListData;
	private String[] mArrayData;
	private int selectedPos = -1;
	private String selectedText = "";
	private int normalDrawbleId;
	private float textSize;
	private OnClickListener onClickListener;
	private OnItemClickListener mOnItemClickListener;
	private int selectedDrawble;
	private boolean isGroup;

	public TextAdapter(Context context, List<GoodsScreenMainBean> listData, int sId, int nId,boolean isGroup) {
		super(context, R.string.no_data_select, listData);
		this.isGroup=isGroup;
		mContext = context;
		mListData = listData;
		selectedDrawble = mContext.getResources().getColor(sId);
		normalDrawbleId = nId;

		init();
	}

	private void init() {
		onClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				TextAdapterHolder textAdapterHolder=(TextAdapterHolder) view.getTag();
				selectedPos = (Integer) textAdapterHolder.mCTView.getTag();
				setSelectedPosition(selectedPos);
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(view, selectedPos);
				}
			}
		};
	}

/*	public TextAdapter(Context context, String[] arrayData, int sId, int nId) {
		super(context, R.string.no_data, arrayData);
		mContext = context;
		mArrayData = arrayData;
		selectedDrawble = mContext.getResources().getDrawable(sId);
		normalDrawbleId = nId;
		init();
	}*/

	/**
	 * 设置选中的position,并通知列表刷新
	 */
	public void setSelectedPosition(int pos) {
		if (mListData != null && pos < mListData.size()) {
			selectedPos = pos;
			selectedText = mListData.get(pos).getCategoryName();
			notifyDataSetChanged();
		} else if (mArrayData != null && pos < mArrayData.length) {
			selectedPos = pos;
			selectedText = mArrayData[pos];
			notifyDataSetChanged();
		}

	}

	/**
	 * 设置选中的position,但不通知刷新
	 */
	public void setSelectedPositionNoNotify(int pos) {
		selectedPos = pos;
		if (mListData != null && pos < mListData.size()) {
			selectedText = mListData.get(pos).getCategoryName();
		} else if (mArrayData != null && pos < mArrayData.length) {
			selectedText = mArrayData[pos];
		}
	}

	/**
	 * 获取选中的position
	 */
	public int getSelectedPosition() {
		if (mArrayData != null && selectedPos < mArrayData.length) {
			return selectedPos;
		}
		if (mListData != null && selectedPos < mListData.size()) {
			return selectedPos;
		}

		return -1;
	}

	/**
	 * 设置列表字体大小
	 */
	public void setTextSize(float tSize) {
		textSize = tSize;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextAdapterHolder textAdapterHolder=null;
		if (convertView == null) {
			convertView =  LayoutInflater.from(mContext).inflate(R.layout.choose_item, parent, false);
			 textAdapterHolder=new TextAdapterHolder();
			 ViewUtils.inject(textAdapterHolder, convertView);
			 convertView.setTag(textAdapterHolder);
		
		} else {
			textAdapterHolder = (TextAdapterHolder) convertView.getTag();
		}
		textAdapterHolder.mCTView.setTag(position);
		if(isGroup){
			textAdapterHolder.mCTView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
		}else{
			textAdapterHolder.mCTView.setBackgroundColor(mContext.getResources().getColor(R.color.choose_eara_item_press_color));
		}
		String mString = "";
		if (mListData != null) {
			if (position < mListData.size()) {
				GoodsScreenMainBean categoryBean = mListData.get(position);
				mString = categoryBean.getCategoryName();
				String imageUrl=categoryBean.getImgUrl();
				if(!TextUtils.isEmpty(imageUrl)){
					ImageLoader.getInstance().displayImage(imageUrl,textAdapterHolder.mIVCategory , CommonUtil.OPTIONS_ALBUM);
				}
			}
		} else if (mArrayData != null) {
			if (position < mArrayData.length) {
				mString = mArrayData[position];
			}
		}
		if (mString.contains("不限"))
			textAdapterHolder.mCTView.setText("不限");
		else
			textAdapterHolder.mCTView.setText(mString);
		textAdapterHolder.mCTView.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);

		if (selectedText != null && selectedText.equals(mString)) {
			convertView.setBackgroundColor(selectedDrawble);//设置选中的背景图片
		} else {
			convertView.setBackgroundDrawable(mContext.getResources().getDrawable(normalDrawbleId));//设置未选中状态背景图片
		}
		textAdapterHolder.mCTView.setPadding(20, 0, 0, 0);
		convertView.setOnClickListener(onClickListener);
		return convertView;
	}

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}
	public static class TextAdapterHolder{
		@ViewInject(R.id.ct_categoryname)
		public CheckedTextView mCTView;
		@ViewInject(R.id.iv_title)
		public ImageView mIVCategory;
		
	}

	/**
	 * 重新定义菜单选项单击接口
	 */
	public interface OnItemClickListener {
		public void onItemClick(View view, int position);
	}

}
