package com.ninetowns.tootoopluse.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.adapter.CreateActDownAdapter;
import com.ninetowns.tootoopluse.adapter.CreateActPwLvAdapter;
import com.ninetowns.tootoopluse.bean.CreateActStoryBean;
import com.ninetowns.tootoopluse.bean.CreateActiveUserBean;
import com.ninetowns.tootoopluse.helper.ChangeRowsView.ChangeRowsDownAndDltListener;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * 初始化添加的商家布局
 * @author huangchao
 *
 */
public class ChangeItemView extends LinearLayout {
	
	private LayoutInflater changeLayoutInflater;
	
	private LinearLayout change_item_layout;
	
	private int screen_height = 0;
	
	public interface ChangeItemDltAndAddListener{
		public void changeItemDelete();
		public void changeItemAdd();
	}
	
	private ChangeItemDltAndAddListener changeItemDltAndAddListener;

	public ChangeItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		changeLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View change_item_view = changeLayoutInflater.inflate(R.layout.change_item_view, null);
		//继承LinearLayout后实现系统的方法
		addView(change_item_view);
		
		change_item_layout = (LinearLayout)change_item_view.findViewById(R.id.change_item_layout);
		
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		//屏幕宽度
		screen_height = wm.getDefaultDisplay().getHeight();
		
	}

	public void initItemView(String store_info, final List<CreateActiveUserBean> store_user_list, final List<CreateActStoryBean> store_story_list, 
			CreateActStoryBean updateStoryBean, List<CreateActStoryBean> updateStorys){
		
		final View create_act_first_step_item = changeLayoutInflater.inflate(R.layout.create_act_first_step_item, null);
		
		TextView act_first_step_item_store = (TextView)create_act_first_step_item.findViewById(R.id.act_first_step_item_store);
		act_first_step_item_store.setText(R.string.create_act_store_info);
		
		final TextView act_first_step_item_tv = (TextView)create_act_first_step_item.findViewById(R.id.act_first_step_item_tv);
		
		final AutoCompleteTextView act_first_step_item_auto_tv = (AutoCompleteTextView)create_act_first_step_item.findViewById(R.id.act_first_step_item_auto_tv);
		
		act_first_step_item_auto_tv.setDropDownBackgroundResource(R.drawable.bg_white_corners_shape_drawable);
		//下拉框展示距控件位置
		act_first_step_item_auto_tv.setDropDownVerticalOffset(2);
		
		List<String> autoList = new ArrayList<String>();
		if(store_user_list != null && store_user_list.size() > 0){
			for(int i = 0; i < store_user_list.size(); i++){
				autoList.add(store_user_list.get(i).getCreate_act_user_name());
			}
		}
		act_first_step_item_auto_tv.setAdapter(new CreateActDownAdapter(getContext(), autoList, 10));
		if(change_item_layout.getChildCount() == 0){
			act_first_step_item_tv.setVisibility(View.VISIBLE);
			act_first_step_item_auto_tv.setVisibility(View.GONE);
			act_first_step_item_tv.setText(store_info);
		} else {
			act_first_step_item_tv.setVisibility(View.GONE);
			act_first_step_item_auto_tv.setVisibility(View.VISIBLE);
			act_first_step_item_auto_tv.setText(store_info);
		}
		
		final ChangeRowsView act_first_step_item_change_rows = (ChangeRowsView)create_act_first_step_item.findViewById(R.id.act_first_step_item_change_rows);
		LinearLayout act_first_step_item_delete_layout = (LinearLayout)create_act_first_step_item.findViewById(R.id.act_first_step_item_delete_layout);
		act_first_step_item_delete_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//删除
				change_item_layout.removeView(create_act_first_step_item);
				if(change_item_layout.getChildCount() == 1){
					change_item_layout.getChildAt(0).findViewById(R.id.act_first_step_item_add_wish_layout).setVisibility(View.VISIBLE);
				}
				if(changeItemDltAndAddListener != null){
					changeItemDltAndAddListener.changeItemDelete();
				}
			}
		});
		
		if(updateStoryBean != null){
			act_first_step_item_change_rows.initRowsView(updateStoryBean.getCreate_act_story_story_name(), updateStoryBean.getCreate_act_story_id(), updateStoryBean.getCreate_act_story_Cover_thumb());	
		} else if(updateStorys != null){ 
			for(int i = 0; i < updateStorys.size(); i++){
				act_first_step_item_change_rows.initRowsView(updateStorys.get(i).getCreate_act_story_story_name(), updateStorys.get(i).getCreate_act_story_id(), updateStorys.get(i).getCreate_act_story_Cover_thumb());
			}
		} else {
			if(store_story_list != null && store_story_list.size() > 0){
				act_first_step_item_change_rows.initRowsView(store_story_list.get(0).getCreate_act_story_story_name(), store_story_list.get(0).getCreate_act_story_id(), store_story_list.get(0).getCreate_act_story_Cover_thumb());
			} else {
				act_first_step_item_change_rows.initRowsView("", "", "");
			}
		}
		final LinearLayout act_first_step_item_add_wish_layout = (LinearLayout)create_act_first_step_item.findViewById(R.id.act_first_step_item_add_wish_layout);
		
		act_first_step_item_change_rows.setChangeRowsDownAndDltListener(new ChangeRowsDownAndDltListener() {
			
			@Override
			public void downOnClick(final View view, View clickView) {
				//下拉框
				PopupWindow popupWindow = new PopupWindow(getContext());
				if(act_first_step_item_tv.getVisibility() == View.VISIBLE){
					showDown(popupWindow, view, clickView, store_story_list);
				} else {
					String autoStoreInfo = act_first_step_item_auto_tv.getText().toString();
					act_first_step_item_auto_tv.addTextChangedListener(new TextWatcher() {
						
						@Override
						public void onTextChanged(CharSequence s, int start, int before, int count) {
							// TODO Auto-generated method stub
							((TextView)view.findViewById(R.id.change_rows_cont_tv)).setText("");
							((TextView)view.findViewById(R.id.change_rows_cont_story_id)).setText("");
							ImageLoader.getInstance().displayImage("", ((ImageView)view.findViewById(R.id.change_rows_count_story_cover)), CommonUtil.OPTIONS_LIKE_LIST);
						}
						
						@Override
						public void beforeTextChanged(CharSequence s, int start, int count,
								int after) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void afterTextChanged(Editable s) {
							// TODO Auto-generated method stub
							
						}
					});
					for(int i = 0; i < store_user_list.size(); i++){
						if(autoStoreInfo.equals(store_user_list.get(i).getCreate_act_user_name())){
							showDown(popupWindow, view, clickView, store_user_list.get(i).getCreate_act_story_list());
						}
					}
				}
					
				
			}
			
			@Override
			public void deleteOnClick(View view) {
				// TODO Auto-generated method stub
				
				if(act_first_step_item_change_rows.getChangeRowsContChildCount() < 5 && act_first_step_item_add_wish_layout.getVisibility() == View.GONE){
					act_first_step_item_add_wish_layout.setVisibility(View.VISIBLE);
				}
				
				
				
				if(act_first_step_item_change_rows.getChangeRowsContChildCount() == 1){
					if(changeItemDltAndAddListener != null){
						changeItemDltAndAddListener.changeItemDelete();
					}
				}
				
			}
		});
		
		LinearLayout act_first_step_item_add_wish = (LinearLayout)create_act_first_step_item.findViewById(R.id.act_first_step_item_add_wish);
		act_first_step_item_add_wish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//添加心愿
				if(change_item_layout.getChildCount() == 1){
					if(store_story_list != null && store_story_list.size() > 0){
						act_first_step_item_change_rows.initRowsView(store_story_list.get(0).getCreate_act_story_story_name(), store_story_list.get(0).getCreate_act_story_id(), store_story_list.get(0).getCreate_act_story_Cover_thumb());
					}
				} else {
					String autoStoreInfo = act_first_step_item_auto_tv.getText().toString();
					for(int i = 0; i < store_user_list.size(); i++){
						if(autoStoreInfo.equals(store_user_list.get(i).getCreate_act_user_name())){
							act_first_step_item_change_rows.initRowsView(store_user_list.get(i).getCreate_act_story_list().get(0).getCreate_act_story_story_name(), 
									store_user_list.get(i).getCreate_act_story_list().get(0).getCreate_act_story_id(), store_user_list.get(i).getCreate_act_story_list().get(0).getCreate_act_story_Cover_thumb());
						}
					}
				}
				
				if(changeItemDltAndAddListener != null){
					changeItemDltAndAddListener.changeItemAdd();
				}
				if(act_first_step_item_change_rows.getChangeRowsContChildCount() == 5 && act_first_step_item_add_wish_layout.getVisibility() == View.VISIBLE){
					act_first_step_item_add_wish_layout.setVisibility(View.GONE);
				}
			}
		});
		
		if(act_first_step_item_change_rows.getChangeRowsContChildCount() == 5 && act_first_step_item_add_wish_layout.getVisibility() == View.VISIBLE){
			act_first_step_item_add_wish_layout.setVisibility(View.GONE);
		}
		
		if(change_item_layout.getChildCount() == 0){
			act_first_step_item_delete_layout.setVisibility(View.INVISIBLE);
		} else {
			act_first_step_item_add_wish_layout.setVisibility(View.GONE);
			change_item_layout.getChildAt(0).findViewById(R.id.act_first_step_item_add_wish_layout).setVisibility(View.GONE);
		}
		change_item_layout.addView(create_act_first_step_item);
	}
	
	/**
	 * @author huangchao
	 * 获取添加多商家时，商家的个数
	 * @return
	 */
	public int getChangeItemChildCount(){
		return change_item_layout.getChildCount();
	}
	
	
	public void setChangeItemDltAndAddListener(ChangeItemDltAndAddListener changeItemDltAndAddListener){
		this.changeItemDltAndAddListener = changeItemDltAndAddListener;
	}
	
	
	private void showDown(final PopupWindow popupWindow, final View view, View clickView, final List<CreateActStoryBean> list){
		View pw_view = changeLayoutInflater.inflate(R.layout.create_act_wish_pw, null);
		
		((ImageView)view.findViewById(R.id.change_rows_cont_view_arrow)).setImageResource(R.drawable.icon_arrow_up);
		
		popupWindow.setContentView(pw_view);
		//窗口的宽带和高度根据情况定义
		popupWindow.setWidth(clickView.getWidth());
		/**购物车每个item布局**/
        View pw_item_view = LayoutInflater.from(getContext()).inflate(R.layout.create_act_down_item, null);
        LinearLayout create_act_down_item_layout = (LinearLayout)pw_item_view.findViewById(R.id.create_act_down_item_layout);
        LinearLayout.LayoutParams pw_item_layoutParams = (LinearLayout.LayoutParams)create_act_down_item_layout.getLayoutParams();
        int pw_item_height = pw_item_layoutParams.height;
        
        ListView create_act_wish_pw_lv = (ListView)pw_view.findViewById(R.id.create_act_wish_pw_lv);
		
        int pw_height = pw_item_height * list.size() + create_act_wish_pw_lv.getDividerHeight() * (list.size() - 1);
        
        if(pw_height > screen_height){
        	popupWindow.setHeight(screen_height);
        } else {
        	popupWindow.setHeight(pw_height);
        }
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_white_corners_shape_drawable));
		
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				((ImageView)view.findViewById(R.id.change_rows_cont_view_arrow)).setImageResource(R.drawable.icon_arrow_down);
			}
		});
		
		
//		//窗口进入和退出的效果,如果不设置默认有进入和退出效果
//		popupWindow.setAnimationStyle(R.style.win_ani_right_top_menu);
		int[] location = new int[2];

		clickView.getLocationOnScreen(location);
		
		//位置可以按要求定义
		popupWindow.showAtLocation(clickView, Gravity.NO_GRAVITY, location[0], location[1] + clickView.getHeight() + 2);
		
		
		create_act_wish_pw_lv.setAdapter(new CreateActPwLvAdapter(getContext(), list));
		create_act_wish_pw_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				((TextView)view.findViewById(R.id.change_rows_cont_tv)).setText(list.get(arg2).getCreate_act_story_story_name());
				((TextView)view.findViewById(R.id.change_rows_cont_story_id)).setText(list.get(arg2).getCreate_act_story_id());
				ImageLoader.getInstance().displayImage(list.get(arg2).getCreate_act_story_Cover_thumb(), ((ImageView)view.findViewById(R.id.change_rows_count_story_cover)), CommonUtil.OPTIONS_LIKE_LIST);
				
				popupWindow.dismiss();
			}
		});
	}
}
