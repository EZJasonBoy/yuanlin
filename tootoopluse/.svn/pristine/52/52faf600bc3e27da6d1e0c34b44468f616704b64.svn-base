package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.custom.vg.list.CustomAdapter;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.bean.PostAresBean;

/**
 * 
 * @ClassName: CitysLayoutAdapter
 * @Description: 显示不规则城市
 * @author wuyulong
 * @date 2015-3-16 下午4:25:47
 * 
 */
public class CitysLayoutAdapter extends CustomAdapter {

	private List<PostAresBean> cityList;
	private Context con;
	private LayoutInflater inflater;

	public CitysLayoutAdapter(Context context, List<PostAresBean> list) {
		this.con = context;
		this.cityList = list;
		inflater = LayoutInflater.from(con);
	}

	@Override
	public int getCount() {
		return cityList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		PostAresBean entity = cityList.get(position);
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = inflater.inflate(R.layout.note_item, null);
			vh.tv = (CheckedTextView) convertView.findViewById(R.id.ct_city);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		vh.tv.setText(entity.getAreaName());
		return convertView;
	}

	public class ViewHolder {
		public CheckedTextView tv;
	}

}
