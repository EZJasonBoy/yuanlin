package com.ninetowns.tootoopluse.adapter;

import java.util.LinkedHashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.ninetowns.library.util.ImageUtil;
import com.ninetowns.tootoopluse.R;
import com.ninetowns.tootoopluse.bean.AlbumPhotoBean;
import com.ninetowns.tootoopluse.util.CommonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
/**
 * 
* @Title: GridViewApdapter.java  
* @Description: 选择图片的适配器 
* @author wuyulong
* @date 2015-1-8 上午9:53:56  
* @version V1.0
 */
public class GridViewApdapter extends BaseAdapter {

    private LayoutInflater lInflater;

    private List<AlbumPhotoBean> list;
    
    public LinkedHashMap<Integer, AlbumPhotoBean> selectMap;
    
    public GridViewApdapter(Context context,List<AlbumPhotoBean> list, LinkedHashMap<Integer, AlbumPhotoBean> selectMap) {
    	this.list = list;
        lInflater = LayoutInflater.from(context);
        this.selectMap = selectMap;
    }
    
    

    @Override
    public int getCount() {
    	if(list != null && list.size() > 0){
    		return list.size();
    	} else {
    		return 0;
    	}
       
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final AlbumPhotoBean photoSbean = list.get(position);
        GridViewHolder holder;
        if (convertView == null) {
            holder = new GridViewHolder();
            convertView = lInflater.inflate(R.layout.gridview_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.poto_ItemImage);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.poto_checkbox);
            convertView.setTag(holder);
        } else {
            holder = (GridViewHolder) convertView.getTag();
        }
        if(selectMap.get(position) != null){
        	holder.checkBox.setChecked(true);
        } else {
        	holder.checkBox.setChecked(false);
        }
        
        setImagePath(photoSbean, holder);
        
        return convertView;
    }


/**
 * 
* @Title: setImagePath 
* @Description: 设置image地址
* @param  
* @return   
* @throws
 */
	public void setImagePath(final AlbumPhotoBean photoSbean,
			GridViewHolder holder) {
		if(!TextUtils.isEmpty(photoSbean.getAlbum_photo_path())){
            ImageLoader.getInstance().displayImage("file://" + photoSbean.getAlbum_photo_path(), new ImageViewAware(holder.imageView), CommonUtil.OPTIONS_ALBUM, new ImageLoadingListener() {
                
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    // TODO Auto-generated method stub
                    
                }
                
                @Override
                public void onLoadingFailed(String imageUri, View view,
                        FailReason failReason) {
                    // TODO Auto-generated method stub
                    
                }
                
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                	int angle =ImageUtil. readPictureDegree(photoSbean.getAlbum_photo_path());
	                if(angle != 0){
	                    // 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
	                    Matrix m = new Matrix();
	                    int width = loadedImage.getWidth();
	                    int height = loadedImage.getHeight();
	                    m.setRotate(angle); // 旋转angle度
	                    loadedImage = Bitmap.createBitmap(loadedImage, 0, 0, width, height, m, true);// 从新生成图片
	                }
                    ImageView imageView = (ImageView)view;
                    imageView.setImageBitmap(ImageUtil.cutSquareBmp(loadedImage));
                }
                
                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    // TODO Auto-generated method stub
                    
                }
            });
            }
	}


    public static class GridViewHolder {
        public ImageView imageView;
        public CheckBox checkBox;

    }
}
