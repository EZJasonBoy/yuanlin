package com.ninetowns.tootoopluse.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ninetowns.library.util.ComponentUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.adapter.GatherImageAdapter;
import com.ninetowns.tootoopluse.adapter.GridViewApdapter;
import com.ninetowns.tootoopluse.adapter.GridViewApdapter.GridViewHolder;
import com.ninetowns.tootoopluse.bean.AlbumPhotoBean;
import com.ninetowns.tootoopluse.bean.GatherImageBean;
import com.ninetowns.tootoopluse.helper.ConstantsTooTooEHelper;

/**
 * 
 * @ClassName: GatherImageDisplayActivity
 * @Description: 展示采集到的图片
 * @author wuyulong
 * @date 2015-1-29 下午5:03:43
 * 
 */
public class GatherImageDisplayActivity extends BaseCreateStoryImageActivity {
	private Bundle bundle;
	private List<AlbumPhotoBean> listLocal = new ArrayList<AlbumPhotoBean>();
	private GatherImageAdapter moreGridViewAdapter;
	private Context context;
	private Map<Integer, AlbumPhotoBean> selectMap;
	private String storyid;
	private boolean isConvertView;
	private GatherImageBean gatherImageBean;
	private JSONArray jarray;
	private String titleName;

	@Override
	public void getType() {
		bundle = getIntent().getBundleExtra(ConstantsTooTooEHelper.BUNDLE);
		gatherImageBean = (GatherImageBean) bundle
				.getSerializable(ConstantsTooTooEHelper.GATHER_IMAGE_BEAN_KEY);
		titleName=gatherImageBean.getNetTitleName();
		listLocal = gatherImageBean.getGaterImageList();
		isConvertView = bundle.getBoolean(ConstantsTooTooEHelper.isConvertView);
	}



	@Override
    public GridViewApdapter initAdapter(Context context,
            List<AlbumPhotoBean> list, LinkedHashMap<Integer, AlbumPhotoBean> selectMap) {
    	if(!(list!=null&&list.size()>0)){//如果是null
    		list=listLocal;
    	}
    	this.context=context;
    	this.selectMap=selectMap;
        moreGridViewAdapter = new GatherImageAdapter(context, list, selectMap);
        return moreGridViewAdapter;
    }

	@Override
	public void onItemSelectPhoto(int position, TextView comit,View view) {
		if (isConvertView) {
			onePhoto(position, comit,view);
		} else {
			fivephoto(position, comit,view);
		}

	}

	private void fivephoto(int position, TextView comit,View view) {
		GridViewHolder viewHolder = (GridViewHolder)view.getTag();
		if (moreGridViewAdapter.selectMap.get(position) != null) {
			viewHolder.checkBox.setChecked(false);
			moreGridViewAdapter.selectMap.remove(position);
		} else {

			// 最多选5张
			if (moreGridViewAdapter.selectMap.size() < Integer
					.parseInt(ConstantsTooTooEHelper.MAX_UPLOAD_PHOTO)) {
				viewHolder.checkBox.setChecked(true);
				moreGridViewAdapter.selectMap.put(position,
						listLocal.get(position));
			} else {
				ComponentUtil.showToast(
						GatherImageDisplayActivity.this,
						getResources().getString(
								R.string.pass_max_more_update_photo));
			}
		}
		comit.setText(getResources().getString(
				R.string.mobile_forget_pwd_success_btn)
				+ "("
				+ moreGridViewAdapter.selectMap.size()
				+ "/"
				+ ConstantsTooTooEHelper.MAX_UPLOAD_PHOTO + ")");
	}

	private void onePhoto(int position, TextView comit,View view) {
		GridViewHolder viewHolder = (GridViewHolder)view.getTag();
		if (moreGridViewAdapter.selectMap.get(position) != null) {
			viewHolder.checkBox.setChecked(false);
			moreGridViewAdapter.selectMap.remove(position);
		} else {

			// 最多选5张
			if (moreGridViewAdapter.selectMap.size() < Integer
					.parseInt(ConstantsTooTooEHelper.MAX_UPLOAD_PHOTO_ONE)) {
				viewHolder.checkBox.setChecked(true);
				moreGridViewAdapter.selectMap.put(position,
						listLocal.get(position));
			} else {
				ComponentUtil.showToast(
						GatherImageDisplayActivity.this,
						getResources().getString(
								R.string.pass_max_more_update_photo));
			}
		}
		comit.setText(getResources().getString(
				R.string.mobile_forget_pwd_success_btn)
				+ "("
				+ moreGridViewAdapter.selectMap.size()
				+ "/"
				+ ConstantsTooTooEHelper.MAX_UPLOAD_PHOTO_ONE + ")");
	}

	@Override
	public String getCreateConvertType() {
		return "2";
	}

	@Override
	public String getPageType() {
		return "2";
	}

	@Override
    protected void onResume() {
    	super.onResume();
    	two_or_one_btn_head_title.setText(titleName);
    }
}
