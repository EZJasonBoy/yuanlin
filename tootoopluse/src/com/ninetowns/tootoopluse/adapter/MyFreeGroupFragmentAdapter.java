package com.ninetowns.tootoopluse.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ninetowns.tootoopluse.bean.MyJoinmemberAct;

public class MyFreeGroupFragmentAdapter extends BaseAdapter{
	private Context context;
	private List<MyJoinmemberAct> parserResult;
	public MyFreeGroupFragmentAdapter(Context context,List<MyJoinmemberAct> parserResult) {
		this.context=context;
		this.parserResult=parserResult;
	}
	@Override
	public int getCount() {
		return parserResult.size();
	}

	@Override
	public Object getItem(int position) {
		return parserResult.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
//			LayoutInflater.from(context).inflate(R.layout.i, root)
			
		}else{
			
		}
		return convertView;
	}

}
