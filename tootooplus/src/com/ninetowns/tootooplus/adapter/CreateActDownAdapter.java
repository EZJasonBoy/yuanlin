package com.ninetowns.tootooplus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ninetowns.tootooplus.R;

public class CreateActDownAdapter extends BaseAdapter implements Filterable{
	private ArrayFilter mFilter;   
	private final Object mLock = new Object();
	//所有的Item
	private List<String> allValues;
	//最多显示多少个选项,负数表示全部
	private int maxMatch=10; 
	//过滤后的item
	private List<String> filterValues;
	private Context context;
	
	
	public CreateActDownAdapter(Context context, List<String> allValues, int maxMatch){
		this.context = context;
		this.allValues = allValues;
		this.maxMatch = maxMatch;
	}
	@Override
	public Filter getFilter() {
		 if (mFilter == null) {     
			   mFilter = new ArrayFilter();     
		 }     
		return mFilter; 
	}

	private class ArrayFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();  
			  
            if (prefix == null || prefix.length() == 0) {  
                synchronized (mLock) {
                	Log.i("tag", "allValues.size="+allValues.size());
                    ArrayList<String> list = new ArrayList<String>(allValues);  
                    results.values = list;  
                    results.count = list.size(); 
                    return results;
                }  
            } else {
                String prefixString = prefix.toString().toLowerCase();  
  
                final int count = allValues.size();  
  
                final ArrayList<String> newValues = new ArrayList<String>(count);  
  
                for (int i = 0; i < count; i++) {
                    final String value = allValues.get(i);  
                    final String valueText = value.toLowerCase();  
  
                    if(valueText.contains(prefixString)){//匹配所有
                    	newValues.add(value);
                    }
                    // First match against the whole, non-splitted value  
//                    if (valueText.startsWith(prefixString)) {  //源码 ,匹配开头
//                        newValues.add(value);  
//                    } 
//                    else {  
//                        final String[] words = valueText.split(" ");//分隔符匹配，效率低
//                        final int wordCount = words.length;  
//  
//                        for (int k = 0; k < wordCount; k++) {  
//                            if (words[k].startsWith(prefixString)) {  
//                                newValues.add(value);  
//                                break;  
//                            }  
//                        }  
//                    }
                    
                    //有数量限制
                    if(maxMatch>0){ 
                    	//不要太多  
                        if(newValues.size()>maxMatch-1){
                            break;  
                        }  
                    }  
                }  
  
                results.values = newValues;  
                results.count = newValues.size();  
            }  
  
            return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			filterValues = (List<String>)results.values;  
            if (results.count > 0) {  
                notifyDataSetChanged();  
            } else {  
                notifyDataSetInvalidated();  
            }
		}
		
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(filterValues != null && filterValues.size() > 0){
			return filterValues.size();
		} else {
			return 0;
		}
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		//此方法有误，尽量不要使用。但此处必须写，才能点击某一条写入AutoCompleteTextView
		return filterValues.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.create_act_down_item, null);
		}
		TextView create_act_down_item_tv = (TextView)convertView.findViewById(R.id.create_act_down_item_tv);
		
		create_act_down_item_tv.setText(filterValues.get(position));
		return convertView;
	}

	
}
