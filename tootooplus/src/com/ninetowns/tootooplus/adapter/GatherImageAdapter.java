package com.ninetowns.tootooplus.adapter;

import java.util.LinkedHashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.ninetowns.library.util.ImageUtil;
import com.ninetowns.tootooplus.bean.AlbumPhotoBean;
import com.ninetowns.tootooplus.util.CommonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
/**
 * 
* @ClassName: GatherImageAdapter 
* @Description: 采集照片的adapter
* @author wuyulong
* @date 2015-1-29 下午5:35:15 
*
 */
public class GatherImageAdapter extends GridViewApdapter{

	public GatherImageAdapter(Context context, List<AlbumPhotoBean> list,
			LinkedHashMap<Integer, AlbumPhotoBean> selectMap) {
		super(context, list, selectMap);
	}
	@Override
	public void setImagePath(final AlbumPhotoBean photoSbean, GridViewHolder holder) {

		if(!TextUtils.isEmpty(photoSbean.getAlbum_photo_path())){
            ImageLoader.getInstance().displayImage( photoSbean.getAlbum_photo_path(), new ImageViewAware(holder.imageView), CommonUtil.OPTIONS_ALBUM, new ImageLoadingListener() {
                

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
}
