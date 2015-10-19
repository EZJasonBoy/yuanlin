package com.ninetowns.tootoopluse.helper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.adapter.TextAdapter;
import com.ninetowns.tootoopluse.bean.GoodsScreenMainBean;

/**
 * 
* @ClassName: CategaryHelper 
* @Description: 发布的时候分类
* @author wuyulong
* @date 2015-3-31 下午4:37:28 
*
 */
public class CategaryHelper extends LinearLayout implements ViewBaseActionHelper {
	
	private ListView regionListView;
	private ListView plateListView;
	private ArrayList<GoodsScreenMainBean> groups = new ArrayList<GoodsScreenMainBean>();
	private LinkedList<GoodsScreenMainBean> childrenItem = new LinkedList<GoodsScreenMainBean>();
	private SparseArray<LinkedList<GoodsScreenMainBean>> children = new SparseArray<LinkedList<GoodsScreenMainBean>>();
	private TextAdapter plateListViewAdapter;
	private TextAdapter earaListViewAdapter;
	private OnSelectListener mOnSelectListener;
	private int tEaraPosition = 0;
	private int tBlockPosition = 0;
	private String showString = "";
	protected int currentFirstPosition=0;//一级目录

	public CategaryHelper(Context context,List<GoodsScreenMainBean> goodsCateGoryList) {
		super(context);
		init(context,goodsCateGoryList);
	}

	public CategaryHelper(Context context, AttributeSet attrs,List<GoodsScreenMainBean> goodsCateGoryList) {
		super(context, attrs);
		init(context,null);
	}



	private LinkedList<GoodsScreenMainBean> getChildrenCateGoryData(GoodsScreenMainBean iterable_element) {
		LinkedList<GoodsScreenMainBean> tItem = new LinkedList<GoodsScreenMainBean>();
		String strJson = iterable_element.getCategorySub();

		JSONArray jsonArr;
		try {
			jsonArr = new JSONArray(strJson);
			for (int i = 0; i < jsonArr.length(); i++) {
				GoodsScreenMainBean subBean = new GoodsScreenMainBean();
				JSONObject obj = (JSONObject) jsonArr.get(i);
				if (obj.has("CategoryId")) {
					String subCatagoryId = obj.getString("CategoryId");
					subBean.setCategoryId(subCatagoryId);
				}
				if (obj.has("CategoryName")) {
					String subCatagoryName = obj.getString("CategoryName");
					subBean.setCategoryName(subCatagoryName);
				}
				tItem.add(subBean);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tItem;
	}
	@SuppressWarnings("deprecation")
	private void init(Context context,List<GoodsScreenMainBean> goodsCateGoryList) {
		if(goodsCateGoryList!=null){
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(R.layout.view_region, this, true);
			regionListView = (ListView) findViewById(R.id.listView);
			plateListView = (ListView) findViewById(R.id.listView2);
			setBackgroundDrawable(getResources().getDrawable(
					R.drawable.choosearea_bg_left));
			for(int i=0;i<goodsCateGoryList.size();i++){
				GoodsScreenMainBean goodsScreenMain=goodsCateGoryList.get(i);
				LinkedList<GoodsScreenMainBean> item=getChildrenCateGoryData(goodsScreenMain);
				groups.add(goodsScreenMain);
				children.put(i, item);//记录一级条目所对应的数据
				}
				
			}
			earaListViewAdapter = new TextAdapter(context, groups,
					R.color.choose_eara_item_press_color,
					R.drawable.choose_eara_item_selector,true);
			earaListViewAdapter.setTextSize(17);
			earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
			regionListView.setAdapter(earaListViewAdapter);
			earaListViewAdapter
					.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

						@Override
						public void onItemClick(View view, int position) {
							currentFirstPosition=position;
							if (position < children.size()) {
								childrenItem.clear();
								childrenItem.addAll(children.get(position));
								plateListViewAdapter.notifyDataSetChanged();
							}
						}
					});
			if (tEaraPosition < children.size())
				childrenItem.addAll(children.get(tEaraPosition));
			plateListViewAdapter = new TextAdapter(context, childrenItem,
					R.color.choose_eara_item_press_color,
					R.drawable.choose_plate_item_selector,false);
			plateListViewAdapter.setTextSize(15);
			plateListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
			plateListView.setAdapter(plateListViewAdapter);
			plateListViewAdapter
					.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

						@Override
						public void onItemClick(View view, final int position) {
							 GoodsScreenMainBean secondItem = childrenItem.get(position);
							
							showString = secondItem.getCategoryName();
							if (mOnSelectListener != null) {
								
								mOnSelectListener.getValue(showString,groups,currentFirstPosition,secondItem);
							}

						}
					});
			if (tBlockPosition < childrenItem.size())
				showString = childrenItem.get(tBlockPosition).getCategoryName();
			if (showString.contains("不限")) {
				showString = showString.replace("不限", "");
			}
			setDefaultSelect();
		}
		


	public void setDefaultSelect() {
		regionListView.setSelection(tEaraPosition);
		plateListView.setSelection(tBlockPosition);
	}

	public String getShowText() {
		return showString;
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String showText,ArrayList<GoodsScreenMainBean> groups,int firstPosition,GoodsScreenMainBean secondCategary);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
}
